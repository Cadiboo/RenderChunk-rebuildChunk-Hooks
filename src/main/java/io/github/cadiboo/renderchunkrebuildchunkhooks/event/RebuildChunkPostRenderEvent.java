package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

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

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Called when a {@link RenderChunk#rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS} right before RenderChunk#rebuildChunk returns.
 * This event has access to all the filled {@link BufferBuilder}s and could allow you to export or modify that data
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkRenderTask)
 */
public class RebuildChunkPostRenderEvent extends RebuildChunkEvent {

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
	public RebuildChunkPostRenderEvent(
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
			@Nonnull final WorldRenderer renderGlobal) {
		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, chunkCache, visGraph, tileEntitiesWithGlobalRenderers, renderGlobal);
	}

	public BufferBuilder getBufferBuilderByLayer(final BlockRenderLayer blockRenderLayer) {
		return getGenerator().getRegionRenderCacheBuilder().getBuilder(blockRenderLayer);
	}

	public BufferBuilder getBufferBuilderById(final int blockRenderLayerOrdinal) {
		return getGenerator().getRegionRenderCacheBuilder().getBuilder(blockRenderLayerOrdinal);
	}

}
