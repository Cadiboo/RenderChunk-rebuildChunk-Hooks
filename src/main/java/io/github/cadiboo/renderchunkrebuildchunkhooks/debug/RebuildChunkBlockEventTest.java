package io.github.cadiboo.renderchunkrebuildchunkhooks.debug;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildCanBlockBeRenderedEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.debug.RebuildChunkBlockEventTest.Config.ENABLED;

@Mod.EventBusSubscriber
@Mod("rebuild_chunk_block_event_test")
public final class RebuildChunkBlockEventTest {

	public static class Config {

		public static boolean ENABLED = true;

	}

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildCanBlockBeRenderedEvent event) {
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
