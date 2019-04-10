package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event.HasResult;

import javax.annotation.Nonnull;

/**
 * @author Cadiboo
 */
@Cancelable
@HasResult
public class RebuildChunkPreEvent extends RebuildChunkEvent {

	public RebuildChunkPreEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator
	) {
		super(renderChunk, x, y, z, generator);
	}

}
