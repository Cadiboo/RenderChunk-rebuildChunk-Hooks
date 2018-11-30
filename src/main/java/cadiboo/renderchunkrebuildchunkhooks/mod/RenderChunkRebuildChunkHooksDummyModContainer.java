package cadiboo.renderchunkrebuildchunkhooks.mod;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import joptsimple.internal.Strings;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.BETTER_FOLIAGE;

public class RenderChunkRebuildChunkHooksDummyModContainer extends DummyModContainer {

	public static final String MOD_ID = "render_chunk_rebuild_chunk_hooks";

	static {
		if (MOD_ID.length() > 64) {
			final IllegalStateException exception = new IllegalStateException("Mod Id is too long!");
			CrashReport crashReport = new CrashReport("Mod Id must be 64 characters or shorter!", exception);
			crashReport.makeCategory("Constructing Mod");
		}
	}

	public static final String MOD_NAME    = "RenderChunk rebuildChunk Hooks";
	public static final String MOD_VERSION = "0.0.0.0";

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public RenderChunkRebuildChunkHooksDummyModContainer() {

		super(new ModMetadata());

		final ArrayList<String> description = new ArrayList<>();
		description.add("A small(ish) coremod for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow modders to add their own custom chunk rendering logic and other chunk rendering related modifications.");
		description.add("This mod provides configurable events that Modders can use for various chunk/world-related rendering logic");
		description.add(" - The RebuildChunkPreEvent is called before any chunk rebuilding is done");
		description.add("    - RebuildChunkPreOptifineEvent is the same as the RebuildChunkPreEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockRenderInLayerEvent allows modders to modify the BlockRenderLayers that blocks can render in");
		description.add("    - RebuildChunkBlockRenderInLayerOptifineEvent is the same as the RebuildChunkBlockRenderInLayerEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockRenderInTypeEvent allows modders to modify the EnumBlockRenderType that blocks can render in");
		description.add("    - RebuildChunkBlockRenderInTypeOptifineEvent is the same as the RebuildChunkBlockRenderInTypeEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockEvent is called for each BlockRenderLayers of each block and allows Modders to add their own logic");
		description.add("    - RebuildChunkBlockOptifineEvent is the same as the RebuildChunkBlockEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkPostEvent is called after all chunk rebuilding logic is done");
		description.add("    - RebuildChunkPostOptifineEvent is the same as the RebuildChunkPostEvent but allows access to Optifine-related objects");

		final ModMetadata meta = this.getMetadata();
		meta.modId = MOD_ID;
		meta.name = MOD_NAME;
		meta.version = MOD_VERSION;
		meta.credits = "The Forge and FML guys for Forge and FML and Cadiboo for making the mod";
		meta.authorList = Arrays.asList("Cadiboo", "CosmicDan");
		meta.description = Strings.join(description, "\n");
		meta.url = "https://cadiboo.github.io/projects/" + MOD_ID;
		meta.updateJSON = "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/update.json";
		meta.screenshots = new String[0];
		meta.logoFile = "/" + MOD_ID + "_logo.png";

	}

	@Override
	public boolean shouldLoadInEnvironment() {

		return FMLCommonHandler.instance().getSide().isClient();
	}

	@Subscribe
	public void preInit(final FMLPreInitializationEvent event) {

		if (event.getSide().isClient()) {

			ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

			MinecraftForge.EVENT_BUS.register(new RenderChunkRebuildChunkHooksEventSubscriber());

			tryPreloadRenderChunk();

			tryRegisterBetterFoliageCompatibleEventSubscriber();

		}

	}

	private void tryPreloadRenderChunk() {

		LOGGER.info("Preloading RenderChunk...");
		{
			RenderChunk.class.getName();
			LOGGER.info("Loaded RenderChunk, initialising...");
			final int unused_renderChunksUpdated = RenderChunk.renderChunksUpdated;
			LOGGER.info("Initialised RenderChunk");
		}
		LOGGER.info("Successfully preloaded RenderChunk!");

	}

	private void tryRegisterBetterFoliageCompatibleEventSubscriber() {

		if (BETTER_FOLIAGE) {
			LOGGER.info("Registering BetterFoliage compatibility EventSubscriber...");
			final Class<?> betterFoliageCompatibilityEventSubscriberClass;
			try {
				betterFoliageCompatibilityEventSubscriberClass = Class.forName("cadiboo.renderchunkrebuildchunkhooks.mod.BetterFoliageCompatibilityEventSubscriber");

				final Object betterFoliageCompatibilityEventSubscriber = betterFoliageCompatibilityEventSubscriberClass.getConstructors()[0].newInstance();

				MinecraftForge.EVENT_BUS.register(betterFoliageCompatibilityEventSubscriber);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
				CrashReport crashReport = new CrashReport("Error finding or registering BetterFoliage compatible EventSubscriber", exception);
				crashReport.makeCategory("Registering BetterFoliage compatibility EventSubscriber");
				throw new ReportedException(crashReport);
			}
			LOGGER.info("Registered BetterFoliage compatibility EventSubscriber");
		}

	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {

		bus.register(this);
		return true;

	}

	@Override
	public String getGuiClassName() {

		return GuiFactory.class.getName();

	}

}
