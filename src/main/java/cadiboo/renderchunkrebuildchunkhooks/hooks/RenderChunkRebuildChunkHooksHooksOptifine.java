package cadiboo.renderchunkrebuildchunkhooks.hooks;

import cadiboo.renderchunkrebuildchunkhooks.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.optifine.override.ChunkCacheOF;

import java.util.HashSet;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooksOptifine extends RenderChunkRebuildChunkHooksHooks {

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF        the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 *
	 * @return If vanilla rendering should be stopped
	 *
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean onRebuildChunkPreEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCacheOF chunkCacheOF, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final BlockPos.MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {

		final RebuildChunkPreOptifineEvent event = new RebuildChunkPreOptifineEvent(renderChunk, renderGlobal, chunkCacheOF, generator, compiledChunk, renderChunkPosition, x, y, z);

		if (ModConfig.enableRebuildChunkPreEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param chunkCacheOF              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPos                  the {@link BlockPos.MutableBlockPos position} of the block being assessed
	 * @param block                     the {@link Block block} being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
	 *
	 * @return If the block can render in the layer
	 *
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean canBlockRenderInLayer(final RenderChunk renderChunk, final ChunkCacheOF chunkCacheOF, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final BlockPos.MutableBlockPos renderChunkPosition, final VisGraph visGraph, final BlockPos.MutableBlockPos blockPos, final Block block, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {

		final RebuildChunkBlockRenderInLayerOptifineEvent event = new RebuildChunkBlockRenderInLayerOptifineEvent(renderChunk, chunkCacheOF, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockPos, blockState, blockRenderLayer);

		if (ModConfig.enableRebuildChunkBlockRenderInLayerEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			return block.canRenderInLayer(blockState, blockRenderLayer);
		} else {
			return false;
		}
	}

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF                    the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param blockState                      the {@link IBlockState state} of the block being rendered
	 * @param blockPos                        the {@link BlockPos.MutableBlockPos position} of the block being rendered
	 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
	 * @param renderChunkPosition             the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
	 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 *
	 * @return If the block should NOT be rebuilt to the chunk by vanilla
	 *
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean onRebuildChunkBlockEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCacheOF chunkCacheOF, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final BlockPos.MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final BlockPos.MutableBlockPos renderChunkPosition, boolean[] usedBlockRenderLayers,
		final BlockRenderLayer blockRenderLayer, final float x, final float y, final float z, final HashSet<TileEntity> tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {

		final RebuildChunkBlockOptifineEvent event = new RebuildChunkBlockOptifineEvent(renderChunk, renderGlobal, chunkCacheOF, generator, compiledChunk, blockRendererDispatcher, blockState, blockPos, bufferBuilder, renderChunkPosition, usedBlockRenderLayers, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

		if (ModConfig.enableRebuildChunkBlockEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

}