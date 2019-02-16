package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

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
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Called when a {@link RenderChunk#rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link BlockRenderLayer} the block renders in.
 * Canceling this event prevents the block from being rebuilt to the chunk (and therefore rendered).
 * You can perform your own rendering in this event.
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkBlockEvent extends RebuildChunkEvent {

	@Nonnull
	private final boolean[] usedBlockRenderLayers;
	@Nonnull
	private final BlockRendererDispatcher blockRendererDispatcher;
	@Nonnull
	private final BlockPos blockPos;
	@Nonnull
	private final IBlockState blockState;
	@Nonnull
	private final Block block;
	@Nonnull
	private final BlockRenderLayer blockRenderLayer;
	private final int blockRenderLayerOrdinal;
	@Nonnull
	private final BufferBuilder bufferBuilder;

	/**
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
	public RebuildChunkBlockEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledChunk,
			@Nonnull final BlockPos renderChunkPosition,
			@Nonnull final ChunkCache chunkCache,
			@Nonnull final VisGraph visGraph,
			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
			@Nonnull final RenderGlobal renderGlobal,
			@Nonnull final boolean[] usedBlockRenderLayers,
			@Nonnull final BlockRendererDispatcher blockRendererDispatcher,
			@Nonnull final BlockPos blockPos,
			@Nonnull final IBlockState blockState,
			@Nonnull final Block block,
			@Nonnull final BlockRenderLayer blockRenderLayer,
			final int blockRenderLayerOrdinal,
			@Nonnull final BufferBuilder bufferBuilder) {
		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, chunkCache, visGraph, tileEntitiesWithGlobalRenderers, renderGlobal);
		this.usedBlockRenderLayers = usedBlockRenderLayers;
		this.blockRendererDispatcher = blockRendererDispatcher;
		this.blockPos = blockPos;
		this.blockState = blockState;
		this.block = block;
		this.blockRenderLayer = blockRenderLayer;
		this.blockRenderLayerOrdinal = blockRenderLayerOrdinal;
		this.bufferBuilder = bufferBuilder;
	}

	@Nonnull
	public boolean[] getUsedBlockRenderLayers() {
		return usedBlockRenderLayers;
	}

	@Nonnull
	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return blockRendererDispatcher;
	}

	@Nonnull
	public BlockPos getBlockPos() {
		return blockPos;
	}

	@Nonnull
	public IBlockState getBlockState() {
		return blockState;
	}

	@Nonnull
	public Block getBlock() {
		return block;
	}

	@Nonnull
	public BlockRenderLayer getBlockRenderLayer() {
		return blockRenderLayer;
	}

	public int getBlockRenderLayerOrdinal() {
		return blockRenderLayerOrdinal;
	}

	@Nonnull
	public BufferBuilder getBufferBuilder() {
		return bufferBuilder;
	}

}
