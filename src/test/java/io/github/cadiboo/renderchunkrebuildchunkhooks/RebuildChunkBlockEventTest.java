package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;
import java.util.Random;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.RebuildChunkBlockEventTest.Config.ENABLED;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkBlockEventTest.MODID, name = "RebuildChunkBlockEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public final class RebuildChunkBlockEventTest {

	public static final String MODID = "rebuild_chunk_block_event_test";

	@net.minecraftforge.common.config.Config(modid = MODID)
	public static class Config {

		public static boolean ENABLED = true;

	}

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildChunkBlockEvent event) {
		if (!ENABLED) {
			return;
		}

		/*
		 * @param renderChunk                     the instance of {@link RenderChunk}
		 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param chunkCache                      the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param usedBlockRenderLayers           the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher}
		 * @param blockPos                        the {@link BlockPos} of the block the event is firing for
		 * @param blockState                      the {@link IBlockState} of the block the event is firing for
		 * @param block                           the {@link Block} the event is firing for
		 * @param blockRenderLayer                the {@link BlockRenderLayer} the event is firing for
		 * @param blockRenderLayerOrdinal         the ordinal of the {@link BlockRenderLayer} the event is firing for
		 * @param bufferBuilder                   the {@link BufferBuilder} for the {@link BlockRenderLayer} the event is firing for
		 */
		try {
			Objects.requireNonNull(event.getRenderChunk());
			Objects.requireNonNull(event.getGenerator());
			Objects.requireNonNull(event.getCompiledChunk());
			Objects.requireNonNull(event.getRenderChunkPosition());
			Objects.requireNonNull(event.getChunkCache());
			Objects.requireNonNull(event.getVisGraph());
			Objects.requireNonNull(event.getTileEntitiesWithGlobalRenderers());
			Objects.requireNonNull(event.getRenderGlobal());
			Objects.requireNonNull(event.getUsedBlockRenderLayers());
			Objects.requireNonNull(event.getBlockRendererDispatcher());
			Objects.requireNonNull(event.getBlockPos());
			Objects.requireNonNull(event.getBlockState());
			Objects.requireNonNull(event.getBlock());
			Objects.requireNonNull(event.getBlockRenderLayer());
			Objects.requireNonNull(event.getBufferBuilder());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (new Random().nextBoolean()) {
			//			event.setCanceled(true);
			for (int i = 0; i < event.getUsedBlockRenderLayers().length; i++) {
				event.getUsedBlockRenderLayers()[i] = false;
			}
		}

		//		if (!event.getBlockState().getMaterial().blocksLight()) {
		//			event.setCanceled(false);
		//			event.getBlockRendererDispatcher().renderBlock(Blocks.GLASS.getDefaultState(), event.getBlockPos(), event.getWorldView(), event.getBufferBuilder());
		//		}

	}

}