package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RebuildChunkEvent extends Event {

	private final RenderChunk renderChunk;

	/**
	 * @param renderChunk the instance of {@link RenderChunk} the event is being fired for
	 */
	public RebuildChunkEvent(final RenderChunk renderChunk) {
		this.renderChunk = renderChunk;
	}

	/**
	 * @return the instance of {@link RenderChunk} the event is being fired for
	 */
	public RenderChunk getRenderChunk() {
		return this.renderChunk;
	}

	/**
	 * @return the type of event
	 */
	public EnumEventType getType() {
		return EnumEventType.FORGE;
	}

}
