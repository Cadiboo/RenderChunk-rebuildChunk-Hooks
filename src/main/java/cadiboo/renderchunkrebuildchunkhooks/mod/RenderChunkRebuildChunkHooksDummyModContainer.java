package cadiboo.renderchunkrebuildchunkhooks.mod;

import cadiboo.renderchunkrebuildchunkhooks.compatibility.BetterFoliageCompatibilityEventSubscriber;
import cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfig;
import cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2;
import cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import joptsimple.internal.Strings;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.BETTER_FOLIAGE;

public class RenderChunkRebuildChunkHooksDummyModContainer extends DummyModContainer {

	public static final String MOD_ID = "render_chunk_rebuild_chunk_hooks";
	public static final String MOD_NAME = "RenderChunk rebuildChunk Hooks";
	public static final String MOD_VERSION = "1.0.1.0";
	public static final String MOD_FULL_VERSION = "1.12.2-" + MOD_VERSION + "";
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
	static {
		if (MOD_ID.length() > 64) {
			final IllegalStateException exception = new IllegalStateException("Mod Id is too long!");
			CrashReport crashReport = new CrashReport("Mod Id must be 64 characters or shorter!", exception);
			crashReport.makeCategory("Constructing Mod");
		}
	}

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
		meta.version = MOD_FULL_VERSION;
		meta.credits = "The Forge and FML guys for Forge and FML and Cadiboo for making the mod";
		meta.authorList = Arrays.asList("Cadiboo", "CosmicDan");
		meta.description = Strings.join(description, "\n");
		meta.url = "https://cadiboo.github.io/projects/" + MOD_ID;
		meta.updateJSON = "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/update.json";
		meta.screenshots = new String[0];
		meta.logoFile = "/" + MOD_ID + "_logo.png";
	}

	@Subscribe
	public void preInit(final FMLPreInitializationEvent event) {
		if (!event.getSide().isClient()) {
			return;
		}

		RenderChunkRebuildChunkHooksConfig.load(event.getSuggestedConfigurationFile());

		MinecraftForge.EVENT_BUS.register(new RenderChunkRebuildChunkHooksEventSubscriber());

		tryPreloadRenderChunk();

		tryRegisterBetterFoliageCompatibleEventSubscriber();
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
				MinecraftForge.EVENT_BUS.register(new BetterFoliageCompatibilityEventSubscriber());
			} catch (Exception exception) {
				CrashReport crashReport = new CrashReport("Error Registering BetterFoliage compatibility EventSubscriber", exception);
				crashReport.makeCategory("Registering BetterFoliage compatibility EventSubscriber");
				throw new ReportedException(crashReport);
			}
			LOGGER.info("Registered BetterFoliage compatibility EventSubscriber");
		}
	}

	//will always be null in dev environment, will never be null in release environment
	@Override
	public File getSource() {
		return RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.MOD_LOCATION;
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}

	// load our lang file
	@Override
	public Class<?> getCustomResourcePackClass() {
		// without this it crashes in dev, even though it works perfectly in release environment
		if (RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.OBFUSCATION_LEVEL == ObfuscationHelper.ObfuscationLevel.DEOBFUSCATED)
			return super.getCustomResourcePackClass();
		return getSource().isDirectory() ? FMLFolderResourcePack.class : FMLFileResourcePack.class;
	}

	@Override
	public String getGuiClassName() {
		return RenderChunkRebuildChunkHooksGuiFactory.class.getName();
	}

	@Override
	public boolean shouldLoadInEnvironment() {
		return FMLCommonHandler.instance().getSide().isClient();
	}

}
