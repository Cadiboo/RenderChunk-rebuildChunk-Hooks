package cadiboo.renderchunkrebuildchunkhooks.mod;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import joptsimple.internal.Strings;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class RenderChunkRebuildChunkHooksDummyContainer extends DummyModContainer {

	public static final String MOD_ID      = "renderchunkrebuildchunkhooks";
	public static final String MOD_NAME    = "RenderChunk rebuildChunk Hooks";
	public static final String MOD_VERSION = "0.0.0.0";

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public RenderChunkRebuildChunkHooksDummyContainer() {

		super(new ModMetadata());

		final ArrayList<String> description = new ArrayList<>();
		description.add("A small(ish) coremod for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow modders to add their own custom chunk rendering logic and other chunk rendering related modifications.");
		description.add("This mod provides (WIP disableable) events that Modders can use for various chunk/world-related rendering logic");
		description.add(" - The RebuildChunkPreEvent is called before any chunk rebuilding is done");
		description.add("    - RebuildChunkPreOptifineEvent is the same as the RebuildChunkPreEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockRenderInLayerEvent allows modders to modify the BlockRenderLayers that blocks can render in");
		description.add("    - RebuildChunkBlockRenderInLayerOptifineEvent is the same as the RebuildChunkBlockRenderInLayerEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockEvent is called for each BlockRenderLayers of each block and allows Modders to add their own logic");
		description.add("    - RebuildChunkBlockOptifineEvent is the same as the RebuildChunkBlockEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkPostEvent is called after all chunk rebuilding logic is done");
		description.add("    - RebuildChunkPostOptifineEvent is the same as the RebuildChunkPostEvent but allows access to Optifine-related objects");

		final ModMetadata meta = this.getMetadata();
		meta.modId = MOD_ID;
		meta.name = MOD_NAME;
		meta.version = MOD_VERSION;
		meta.credits = "Me, Myself and I";
		meta.authorList = Arrays.asList("Cadiboo", "CosmicDan");
		meta.description = Strings.join(description, "\n");
		meta.url = "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks";
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
			LOGGER.info("Preloading RenderChunk...");
			RenderChunk.class.getName();
			final int unused_renderChunksUpdated = RenderChunk.renderChunksUpdated;
			LOGGER.info("Sucessfully preloaded RenderChunk!");
		}
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {

		bus.register(this);
		return true;
	}

}
