package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Event.HasResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;

/**
 * @author Cadiboo
 */
@Cancelable
@HasResult
public class RebuildChunkPreRenderEvent extends RebuildChunkEvent {

	@Nonnull
	private final CompiledChunk compiledChunk;
	@Nonnull
	private final BlockPos startPosition;
	@Nonnull
	private final BlockPos endPosition;
	@Nonnull
	private final World world;
	@Nullable
	private final RenderChunkCache renderChunkCache;
	@Nonnull
	private final VisGraph visGraph;
	@Nonnull
	private final HashSet tileEntitiesWithGlobalRenderers;

	public RebuildChunkPreRenderEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final BlockPos blockpos1,
			@Nonnull final World world,
			@Nullable final RenderChunkCache lvt_10_1_,
			@Nonnull final VisGraph lvt_11_1_,
			@Nonnull final HashSet lvt_12_1_
	) {
		super(renderChunk, x, y, z, generator);
		this.compiledChunk = compiledchunk;
		this.startPosition = blockpos;
		this.endPosition = blockpos1;
		this.world = world;
		this.renderChunkCache = lvt_10_1_;
		this.visGraph = lvt_11_1_;
		this.tileEntitiesWithGlobalRenderers = lvt_12_1_;
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
	@Nullable
	@Deprecated
	public RenderChunkCache getRenderChunkCache() {
		return renderChunkCache;
	}

	@Nullable
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

}
