package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * Base class for all events this mod provides
 * Called when a {@link RenderChunk#rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}
 */
@Cancelable
public class RebuildChunkEvent extends Event {

	@Nonnull
	RenderChunk renderChunk;
	float x;
	float y;
	float z;
	@Nonnull
	ChunkRenderTask generator;

	public RebuildChunkEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator
	) {
		this.renderChunk = renderChunk;
		this.x = x;
		this.y = y;
		this.z = z;
		this.generator = generator;
	}

	RebuildChunkEvent(final boolean dummy) {
	}

	@Nonnull
	public RenderChunk getRenderChunk() {
		return renderChunk;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	@Nonnull
	public ChunkRenderTask getGenerator() {
		return generator;
	}

	/**
	 * Nice helper method
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer} layer to get the {@link BufferBuilder} for
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	@Nonnull
	public BufferBuilder getBufferBuilderByLayer(final BlockRenderLayer blockRenderLayer) {
		return this.getGenerator().getRegionRenderCacheBuilder().getBuilder(blockRenderLayer);
	}

	/**
	 * Nice helper method
	 *
	 * @param blockRenderLayerOrdinal the ordinal of a {@link BlockRenderLayer} layer to get the {@link BufferBuilder} for
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer} the ordinal corresponds to
	 */
	@Nonnull
	public BufferBuilder getBufferBuilderById(final int blockRenderLayerOrdinal) {
		return this.getGenerator().getRegionRenderCacheBuilder().getBuilder(blockRenderLayerOrdinal);
	}

	/**
	 * Allows checking the type of event without instanceof
	 *
	 * @return the {@link EnumEventType}
	 */
	@Nonnull
	public EnumEventType getEventType() {
		return EnumEventType.NORMAL;
	}

}
