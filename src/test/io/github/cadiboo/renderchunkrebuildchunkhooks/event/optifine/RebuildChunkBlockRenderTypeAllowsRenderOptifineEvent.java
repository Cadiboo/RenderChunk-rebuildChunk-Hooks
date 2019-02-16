package io.github.cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderTypeAllowsRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;
import net.optifine.BlockPosM;
import net.optifine.override.ChunkCacheOF;

import javax.annotation.Nonnull;
import java.util.HashSet;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooksOptifine.getChunkCacheFromChunkCacheOF;

/**
 * Called when a {@link RenderChunk#rebuildChunk} is called when Optifine is present.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link BlockRenderLayer} the block could render in.
 * Setting the result of this event to {@link Result#DENY} prevents the block from being rebuilt to the chunk (and therefore rendered).
 * Setting this to {@link Result#DENY} can allow you to stuff including rendering models for blocks with render types of {@link EnumBlockRenderType#INVISIBLE} or {@link EnumBlockRenderType#LIQUID}
 * You should not perform your own rendering in this event. Perform your rendering in the {@link RebuildChunkBlockEvent}
 * Cancel the event to stop mods further down the listener list from receiving the event
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@HasResult
public class RebuildChunkBlockRenderTypeAllowsRenderOptifineEvent extends RebuildChunkBlockRenderTypeAllowsRenderEvent {

	private final ChunkCacheOF chunkCacheOF;
	private final BlockPosM blockPosM;

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk}
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF                    the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param usedBlockRenderLayers           the boolean[] of used {@link BlockRenderLayer}s
	 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher}
	 * @param blockPosM                       the {@link BlockPosM} of the block the event is firing for
	 * @param blockState                      the {@link IBlockState} of the block the event is firing for
	 * @param block                           the {@link Block} the event is firing for
	 * @param blockRenderLayer                the {@link BlockRenderLayer} the event is firing for
	 * @param blockRenderLayerOrdinal         the ordinal of the {@link BlockRenderLayer} the event is firing for
	 */
	public RebuildChunkBlockRenderTypeAllowsRenderOptifineEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledChunk,
			@Nonnull final BlockPos renderChunkPosition,
			@Nonnull final ChunkCacheOF chunkCacheOF,
			@Nonnull final VisGraph visGraph,
			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
			@Nonnull final RenderGlobal renderGlobal,
			@Nonnull final boolean[] usedBlockRenderLayers,
			@Nonnull final BlockRendererDispatcher blockRendererDispatcher,
			@Nonnull final BlockPosM blockPosM,
			@Nonnull final IBlockState blockState,
			@Nonnull final Block block,
			@Nonnull final BlockRenderLayer blockRenderLayer,
			final int blockRenderLayerOrdinal) {
		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, getChunkCacheFromChunkCacheOF(chunkCacheOF), visGraph, tileEntitiesWithGlobalRenderers, renderGlobal, usedBlockRenderLayers, blockRendererDispatcher, blockPosM, blockState, block, blockRenderLayer, blockRenderLayerOrdinal);
		this.chunkCacheOF = chunkCacheOF;
		this.blockPosM = blockPosM;
	}

	/**
	 * @return the type of event
	 */
	@Nonnull
	@Override
	public EnumEventType getType() {
		return EnumEventType.FORGE_OPTIFINE;
	}

	@Nonnull
	public ChunkCacheOF getChunkCacheOF() {
		return chunkCacheOF;
	}

	@Nonnull
	public BlockPosM getBlockPosM() {
		return blockPosM;
	}

	/**
	 * @return the {@link ChunkCacheOF} passed in
	 */
	@Nonnull
	@Override
	public IBlockAccess getIBlockAccess() {
		return getChunkCacheOF();
	}

}
