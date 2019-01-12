package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link BlockRenderLayer BlockRenderLayer} the block could render in.
 * Setting the result of this event to {@link Result#DENY} prevents the parts of the block in this {@link BlockRenderLayer BlockRenderLayer} from being rebuilt to the chunk (and therefore rendered).
 * You should not perform your own rendering in this event. Perform your rendering in the {@link RebuildChunkBlockEvent}
 * Cancel the event to stop mods further down the listener list from receiving the event
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@HasResult
@Cancelable
public class RebuildChunkBlockRenderInLayerEvent extends RebuildChunkEvent {

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk}
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition             the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache                      the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkBlockRenderInLayerEvent(
			@Nonnull final RenderChunk renderChunk,
			final int x,
			final int y,
			final int z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledChunk,
			@Nonnull final MutableBlockPos renderChunkPosition,
			@Nonnull final ChunkCache chunkCache,
			@Nonnull final VisGraph visGraph,
			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
			@Nonnull final RenderGlobal renderGlobal) {
		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, chunkCache, visGraph, tileEntitiesWithGlobalRenderers, renderGlobal);
	}

}
