package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.util.PrivateUtils;
import io.github.cadiboo.renderchunkrebuildchunkhooks.util.Refs;
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
 */
@Mod(MOD_ID)
public final class RenderChunkRebuildChunkHooks {

	private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public RenderChunkRebuildChunkHooks() {
		LOGGER.info("Pre-loading RenderChunk");
		RenderChunk.class.getName();
		LOGGER.info("Successfully Pre-loaded RenderChunk");
		LOGGER.info("Initialising RenderChunk");
		final int unused = RenderChunk.renderChunksUpdated;
		LOGGER.info("Successfully initialised RenderChunk");

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

	}

	public void setup(final FMLCommonSetupEvent event) {
		PrivateUtils.launchUpdateDaemon(ModList.get().getModContainerById(MOD_ID).get());
	}

	/**
	 * Reduces overhead by not creating or posting events if no mods require them
	 */
	public static class HookConfig {

		//	RebuildChunkPreEvent
		//	RebuildChunkPreRenderSetupEvent
		//	RebuildChunkPreRenderEvent
		//	RebuildChunkCanFluidRenderInLayerEvent
		//	RebuildChunkFluidEvent
		//	RebuildChunkCanBlockRenderWithTypeEvent
		//	RebuildChunkCanBlockRenderInLayerEvent
		//	RebuildChunkBlockEvent
		//	RebuildChunkPostRenderEvent
		//	RebuildChunkPostEvent

		private static boolean postRebuildChunkPreEvent = false;
		private static boolean postRebuildChunkPreRenderSetupEvent = false;
		private static boolean postRebuildChunkPreRenderEvent = false;
		private static boolean postRebuildChunkCanFluidRenderInLayerEvent = false;
		private static boolean postRebuildChunkFluidEvent = false;
		private static boolean postRebuildChunkCanBlockRenderWithTypeEvent = false;
		private static boolean postRebuildChunkCanBlockRenderInLayerEvent = false;
		private static boolean postRebuildChunkBlockEvent = false;
		private static boolean postRebuildChunkPostEvent = false;
		private static boolean postRebuildChunkPostRenderEvent = false;

		public static void enableRebuildChunkPreEvent() {
			postRebuildChunkPreEvent = true;
		}

		public static boolean shouldPostRebuildChunkPreEvent() {
			return postRebuildChunkPreEvent;
		}

		public static void enableRebuildChunkPreRenderSetupEvent() {
			postRebuildChunkPreRenderSetupEvent = true;
		}

		public static boolean shouldPostRebuildChunkPreRenderSetupEvent() {
			return postRebuildChunkPreRenderSetupEvent;
		}

		public static void enableRebuildChunkPreRenderEvent() {
			postRebuildChunkPreRenderEvent = true;
		}

		public static boolean shouldPostRebuildChunkPreRenderEvent() {
			return postRebuildChunkPreRenderEvent;
		}

		public static void enableRebuildChunkCanFluidRenderInLayerEvent() {
			postRebuildChunkCanFluidRenderInLayerEvent = true;
		}

		public static boolean shouldPostRebuildChunkCanFluidRenderInLayerEvent() {
			return postRebuildChunkCanFluidRenderInLayerEvent;
		}

		public static void enableRebuildChunkFluidEvent() {
			postRebuildChunkFluidEvent = true;
		}

		public static boolean shouldPostRebuildChunkRenderFluidEvent() {
			return postRebuildChunkFluidEvent;
		}

		public static void enableRebuildChunkCanBlockRenderWithTypeEvent() {
			postRebuildChunkCanBlockRenderWithTypeEvent = true;
		}

		public static boolean shouldPostRebuildChunkCanBlockRenderWithTypeEvent() {
			return postRebuildChunkCanBlockRenderWithTypeEvent;
		}

		public static void enableRebuildChunkCanBlockRenderInLayerEvent() {
			postRebuildChunkCanBlockRenderInLayerEvent = true;
		}

		public static boolean shouldPostRebuildChunkCanBlockRenderInLayerEvent() {
			return postRebuildChunkCanBlockRenderInLayerEvent;
		}

		public static void enableRebuildChunkBlockEvent() {
			postRebuildChunkBlockEvent = true;
		}

		public static boolean shouldPostRebuildChunkRenderBlockEvent() {
			return postRebuildChunkBlockEvent;
		}

		public static void enableRebuildChunkPostRenderEvent() {
			postRebuildChunkPostRenderEvent = true;
		}

		public static boolean shouldPostRebuildChunkPostRenderEvent() {
			return postRebuildChunkPostRenderEvent;
		}

		public static void enableRebuildChunkPostEvent() {
			postRebuildChunkPostEvent = true;
		}

		public static boolean shouldPostRebuildChunkPostEvent() {
			return postRebuildChunkPostEvent;
		}

	}

}
