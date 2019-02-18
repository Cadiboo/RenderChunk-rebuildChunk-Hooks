package io.github.cadiboo.renderchunkrebuildchunkhooks.debug;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.debug.RebuildChunkBlockEventTest.Config.ENABLED;

@Mod.EventBusSubscriber
@Mod("rebuild_chunk_block_event_test")
public final class RebuildChunkBlockEventTest {

	public static class Config {

		public static boolean ENABLED = true;

	}

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildChunkBlockEvent event) {
		if (!ENABLED) {
			return;
		}

//		@Nonnull final RenderChunk renderChunk,
//		final float x,
//		final float y,
//		final float z,
//		@Nonnull final ChunkRenderTask generator,
//		@Nonnull final CompiledChunk compiledChunk,
//		@Nonnull final BlockPos renderChunkStartPosition,
//		@Nonnull final BlockPos renderChunkEndPosition,
//		@Nonnull final World world,
//		@Nonnull final RenderChunkCache renderChunkCache,
//		@Nonnull final VisGraph visGraph,
//		@Nonnull final HashSet tileEntitiesWithGlobalRenderers,
//		@Nonnull final boolean[] usedBlockRenderLayers,
//		@Nonnull final Random random,
//		@Nonnull final BlockRendererDispatcher blockRendererDispatcher,
//		@Nonnull final BlockPos blockPos,
//		@Nonnull final IBlockState blockState,
//		@Nonnull final Block block,
//		@Nonnull final BlockRenderLayer blockRenderLayer,
//		final int blockRenderLayerOrdinal,
//		@Nonnull final BufferBuilder bufferBuilder

		try {
			Objects.requireNonNull(event.getRenderChunk());
			Objects.requireNonNull(event.getGenerator());
			Objects.requireNonNull(event.getCompiledChunk());
			Objects.requireNonNull(event.getRenderChunkStartPosition());
			Objects.requireNonNull(event.getRenderChunkEndPosition());
			Objects.requireNonNull(event.getWorld());
			Objects.requireNonNull(event.getRenderChunkCache());
			Objects.requireNonNull(event.getVisGraph());
			Objects.requireNonNull(event.getTileEntitiesWithGlobalRenderers());
			Objects.requireNonNull(event.getUsedBlockRenderLayers());
			Objects.requireNonNull(event.getRandom());
			Objects.requireNonNull(event.getBlockRendererDispatcher());
			Objects.requireNonNull(event.getBlockPos());
			Objects.requireNonNull(event.getBlockState());
			Objects.requireNonNull(event.getBlock());
			Objects.requireNonNull(event.getBlockRenderLayer());
			final int blockRenderLayerOrdinal = event.getBlockRenderLayerOrdinal();
			Objects.requireNonNull(event.getBufferBuilder());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (!event.getBlockState().getMaterial().isOpaque()) {
			event.setCanceled(false);
			event.getBlockRendererDispatcher().renderBlock(Blocks.GLASS.getDefaultState(), event.getBlockPos(), event.getRenderChunkCache(), event.getBufferBuilder(), event.getRandom());
		}

	}

}
