package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Called when a {@link RenderChunk#rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS} right before the usedBlockRenderLayers boolean[] is generated and before any rebuilding is done.
 * Canceling this event prevents all Blocks and Tile Entities from being rebuilt to the chunk (and therefore rendered)
 * It is possible to do your own rendering in this event, but you will need to manually do large amounts of logic that would otherwise be done for you in other events.
 * If you do your own rendering please not that you must manually
 * - Manage starting & offsetting {@link BufferBuilder}s
 * - Manage used {@link BlockRenderLayer}s
 * {@link RenderChunkRebuildChunkHooksHooks} provides methods for pre-rendering the {@link BufferBuilder}s
 * You can |= your used render layers in a later event such as the {@link RebuildChunkBlockRenderInLayerEvent} or the {@link RebuildChunkBlockEvent}
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkRenderTask)
 */
@Cancelable
public class RebuildChunkPreEvent extends RebuildChunkEvent {

	@Nonnull
	private final boolean[] usedBlockRenderLayers;

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk}
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkRenderTask} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache                      the {@link RenderChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal                    the {@link WorldRenderer} passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkPreEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkRenderTask generator,
			@Nonnull final CompiledChunk compiledChunk,
			@Nonnull final BlockPos renderChunkPosition,
			@Nonnull final RenderChunkCache chunkCache,
			@Nonnull final VisGraph visGraph,
			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
			@Nonnull final WorldRenderer renderGlobal,
			@Nonnull final boolean[] usedBlockRenderLayers
	) {
		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, chunkCache, visGraph, tileEntitiesWithGlobalRenderers, renderGlobal);
		this.usedBlockRenderLayers = usedBlockRenderLayers;
	}

	@Nonnull
	public boolean[] getUsedBlockRenderLayers() {
		return usedBlockRenderLayers;
	}

}
