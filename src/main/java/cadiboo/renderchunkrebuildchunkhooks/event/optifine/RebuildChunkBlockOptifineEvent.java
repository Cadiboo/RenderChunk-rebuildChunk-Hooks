package cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
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
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.optifine.override.ChunkCacheOF;

import java.util.HashSet;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link BlockRenderLayer BlockRenderLayer} the block renders in.<br>
 * Canceling this event prevents the block from being rebuilt to the chunk (and therefore rendered).<br>
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkBlockOptifineEvent extends RebuildChunkBlockEvent {

	private final ChunkCacheOF chunkCacheOf;

	public RebuildChunkBlockOptifineEvent(RenderChunk renderChunk, RenderGlobal renderGlobal, ChunkCacheOF chunkCacheOf, ChunkCompileTaskGenerator generator, CompiledChunk compiledChunk, BlockRendererDispatcher blockRendererDispatcher, IBlockState blockState, MutableBlockPos blockPos, BufferBuilder bufferBuilder, MutableBlockPos renderChunkPosition, boolean[] usedBlockRenderLayers, BlockRenderLayer blockRenderLayer, float x, float y, float z, HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
		VisGraph visGraph) {
		super(renderChunk, renderGlobal, chunkCacheOf.chunkCache, generator, compiledChunk, blockRendererDispatcher, blockState, blockPos, bufferBuilder, renderChunkPosition, usedBlockRenderLayers, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
		this.chunkCacheOf = chunkCacheOf;
	}

	public ChunkCacheOF getChunkCacheOf() {

		return chunkCacheOf;
	}

}