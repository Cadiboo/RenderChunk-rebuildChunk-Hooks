package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Cadiboo
 */
@Cancelable
public class RebuildChunkIsFluidEmptyEvent extends RebuildChunkEvent {

	@Nonnull
	CompiledChunk compiledChunk;
	@Nonnull
	BlockPos startPosition;
	@Nonnull
	BlockPos endPosition;
	@Nonnull
	World world;
	@Nonnull
	RenderChunkCache renderChunkCache;
	@Nonnull
	VisGraph visGraph;
	@Nonnull
	HashSet tileEntitiesWithGlobalRenderers;
	@Nonnull
	boolean[] usedBlockRenderLayers;
	@Nonnull
	Random random;
	@Nonnull
	BlockRendererDispatcher blockRendererDispatcher;
	@Nonnull
	IBlockState iBlockState;
	@Nonnull
	Block block;
	@Nonnull
	IFluidState iFluidState;

	public RebuildChunkIsFluidEmptyEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final BlockPos blockpos1,
			@Nonnull final World world,
			@Nonnull final RenderChunkCache lvt_10_1_,
			@Nonnull final VisGraph lvt_11_1_,
			@Nonnull final HashSet lvt_12_1_,
			@Nonnull final boolean[] aboolean,
			@Nonnull final Random random,
			@Nonnull final BlockRendererDispatcher blockrendererdispatcher,
			@Nonnull final IBlockState iblockstate,
			@Nonnull final Block block,
			@Nonnull final IFluidState ifluidstate
	) {
		super(renderChunk, x, y, z, generator);
		this.compiledChunk = compiledchunk;
		this.startPosition = blockpos;
		this.endPosition = blockpos1;
		this.world = world;
		this.renderChunkCache = lvt_10_1_;
		this.visGraph = lvt_11_1_;
		this.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		this.usedBlockRenderLayers = aboolean;
		this.random = random;
		this.blockRendererDispatcher = blockrendererdispatcher;
		this.iBlockState = iblockstate;
		this.block = block;
		this.iFluidState = ifluidstate;
	}

	// Forge adds their own public no-args ctor so we can't have a no-args package-private ctor :(
	RebuildChunkIsFluidEmptyEvent(final boolean dummy) {
		super(dummy);
	}

	@Nonnull
	public CompiledChunk getCompiledChunk() {
		return compiledChunk;
	}

	@Nonnull
	public BlockPos getStartPosition() {
		return startPosition;
	}

	@Nonnull
	public BlockPos getEndPosition() {
		return endPosition;
	}

	@Nonnull
	public World getWorld() {
		return world;
	}

	/**
	 * @deprecated not compatible with OptiFine's dynamic lights, use {@link #getIWorldReader()} instead if possible
	 */
	@Nonnull
	@Deprecated
	public RenderChunkCache getRenderChunkCache() {
		return renderChunkCache;
	}

	@Nonnull
	public IWorldReader getIWorldReader() {
		return renderChunkCache;
	}

	@Nonnull
	public VisGraph getVisGraph() {
		return visGraph;
	}

	@Nonnull
	public HashSet getTileEntitiesWithGlobalRenderers() {
		return tileEntitiesWithGlobalRenderers;
	}

	@Nonnull
	public boolean[] getUsedBlockRenderLayers() {
		return usedBlockRenderLayers;
	}

	@Nonnull
	public Random getRandom() {
		return random;
	}

	@Nonnull
	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return blockRendererDispatcher;
	}

	@Nonnull
	public IBlockState getIBlockState() {
		return iBlockState;
	}

	@Nonnull
	public Block getBlock() {
		return block;
	}

	@Nonnull
	public IFluidState getIFluidState() {
		return iFluidState;
	}

}
