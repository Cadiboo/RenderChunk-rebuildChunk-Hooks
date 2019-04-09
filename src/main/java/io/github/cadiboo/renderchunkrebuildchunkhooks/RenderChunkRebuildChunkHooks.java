package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.util.PrivateUtils;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.util.Refs.MOD_ID;

/**
 * @author Cadiboo
 * @see "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/blob/master/diffs/RenderChunk.class.diff"
 */
@Mod(MOD_ID)
public final class RenderChunkRebuildChunkHooks {

	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public RenderChunkRebuildChunkHooks() {
		preLoadRenderChunk();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

	}

	private void preLoadRenderChunk() {
		LOGGER.info("Pre-loading RenderChunk");
		RenderChunk.class.getName();
		LOGGER.info("Successfully Pre-loaded RenderChunk");
		LOGGER.info("Initialising RenderChunk");
		final int unused = RenderChunk.renderChunksUpdated;
		LOGGER.info("Successfully initialised RenderChunk");
	}

	public void setup(final FMLCommonSetupEvent event) {
		PrivateUtils.launchUpdateDaemon(ModList.get().getModContainerById(MOD_ID).get());
	}

}
