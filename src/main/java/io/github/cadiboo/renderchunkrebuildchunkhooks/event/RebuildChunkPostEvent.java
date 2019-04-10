package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
@Cancelable
public class RebuildChunkPostEvent extends RebuildChunkEvent {

	@Nonnull
	private final CompiledChunk compiledChunk;
	@Nonnull
	private final BlockPos startPosition;
	@Nonnull
	private final BlockPos endPosition;
	@Nullable
	private final World world;

	public RebuildChunkPostEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final BlockPos blockpos1,
			@Nullable final World world
	) {
		super(renderChunk, x, y, z, generator);
		this.compiledChunk = compiledchunk;
		this.startPosition = blockpos;
		this.endPosition = blockpos1;
		this.world = world;
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

	@Nullable
	public World getWorld() {
		return world;
	}

}
