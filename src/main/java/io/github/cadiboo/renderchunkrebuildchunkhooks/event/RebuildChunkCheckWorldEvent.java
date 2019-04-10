package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import io.github.cadiboo.renderchunkrebuildchunkhooks.util.WorldReference;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event.HasResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
@Cancelable
@HasResult
public class RebuildChunkCheckWorldEvent extends RebuildChunkEvent {

	@Nonnull
	private final CompiledChunk compiledChunk;
	@Nonnull
	private final BlockPos startPosition;
	@Nonnull
	private final BlockPos endPosition;
	@Nullable
	private final World originalWorld;
	@Nonnull
	private final WorldReference worldReference;

	public RebuildChunkCheckWorldEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final BlockPos blockpos1,
			@Nullable final World world,
			@Nonnull final WorldReference worldRef
	) {
		super(renderChunk, x, y, z, generator);
		this.compiledChunk = compiledchunk;
		this.startPosition = blockpos;
		this.endPosition = blockpos1;
		this.originalWorld = world;
		this.worldReference = worldRef;
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
	public World getOriginalWorld() {
		return originalWorld;
	}

	@Nullable
	public World getWorld() {
		return worldReference.get();
	}

	@Nullable
	public World setWorld(@Nullable final World world) {
		return worldReference.set(world);
	}

}
