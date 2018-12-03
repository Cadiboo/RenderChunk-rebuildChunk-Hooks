package cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInTypeEvent;
import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooksOptifine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;
import net.optifine.BlockPosM;
import net.optifine.override.ChunkCacheOF;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called when Optifine is present.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link EnumBlockRenderType BlockRenderLayer} the block could render in.<br>
 * Setting the result of this event to {@link Result#DENY} prevents the block from being rebuilt to the chunk (and therefore rendered).<br>
 * You should not perform your own rendering in this event. Perform your rendering in the {@link RebuildChunkBlockEvent}<br>
 * Cancel the event to stop mods further down the listener list from receiving the event
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@HasResult
public class RebuildChunkBlockRenderInTypeOptifineEvent extends RebuildChunkBlockRenderInTypeEvent {

	private final ChunkCacheOF chunkCacheOF;
	private final BlockPosM blockPosM;

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param chunkCacheOF              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPosM                 the {@link MutableBlockPos position} of the block being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @param blockRenderType           the {@link EnumBlockRenderType} of the block being assessed
	 */
	public RebuildChunkBlockRenderInTypeOptifineEvent(final RenderChunk renderChunk, final ChunkCacheOF chunkCacheOF, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledchunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final BlockPosM blockPosM, final IBlockState blockState, final EnumBlockRenderType blockRenderType) {
		super(renderChunk, RenderChunkRebuildChunkHooksHooksOptifine.getChunkCacheFromChunkCacheOF(chunkCacheOF), chunkCompileTaskGenerator, compiledchunk, blockRendererDispatcher, renderChunkPosition, visGraph, new MutableBlockPos(blockPosM), blockState, blockRenderType);
		this.chunkCacheOF = chunkCacheOF;
		this.blockPosM = blockPosM;
	}

	/**
	 * @return the {@link ChunkCacheOF} passed in
	 */
	public ChunkCacheOF getChunkCacheOF() {
		return chunkCacheOF;
	}

	/**
	 * @return the {@link BlockPosM} passed in
	 */
	public BlockPosM getBlockPosM() {
		return blockPosM;
	}

}
