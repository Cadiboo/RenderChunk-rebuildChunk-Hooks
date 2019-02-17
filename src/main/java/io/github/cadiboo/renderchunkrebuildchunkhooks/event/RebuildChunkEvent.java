package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Base class for all events this mod provides
 * Called when a {@link RenderChunk#rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}
 */
public class RebuildChunkEvent extends Event {

	@Nonnull
	private final RenderChunk renderChunk;
	private final float x;
	private final float y;
	private final float z;
	@Nonnull
	private final ChunkRenderTask generator;
	@Nonnull
	private final CompiledChunk compiledChunk;
	@Nonnull
	private final BlockPos renderChunkPosition;
	@Nonnull
	private final RenderChunkCache chunkCache;
	@Nonnull
	private final VisGraph visGraph;
	@Nonnull
	private final HashSet<TileEntity> tileEntitiesWithGlobalRenderers;
	@Nonnull
	private final WorldRenderer worldRenderer;

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
	public RebuildChunkEvent(
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
			@Nonnull final WorldRenderer renderGlobal
	) {

		this.renderChunk = renderChunk;
		this.x = x;
		this.y = y;
		this.z = z;
		this.generator = generator;
		this.compiledChunk = compiledChunk;
		this.renderChunkPosition = renderChunkPosition;
		this.chunkCache = chunkCache;
		this.visGraph = visGraph;
		this.tileEntitiesWithGlobalRenderers = tileEntitiesWithGlobalRenderers;
		this.worldRenderer = renderGlobal;
	}

	/**
	 * @return the type of event
	 */
	@Nonnull
	public EnumEventType getType() {
		return EnumEventType.FORGE;
	}

	/**
	 * @return the {@link IWorldReader}
	 */
	@Nonnull
	public IWorldReader getIBlockAccess() {
		return getRenderChunkCache();
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

	@Nonnull
	public CompiledChunk getCompiledChunk() {
		return compiledChunk;
	}

	@Nonnull
	public BlockPos getRenderChunkPosition() {
		return renderChunkPosition;
	}

	@Nonnull
	public RenderChunkCache getRenderChunkCache() {
		return chunkCache;
	}

	@Nonnull
	public VisGraph getVisGraph() {
		return visGraph;
	}

	@Nonnull
	public HashSet<TileEntity> getTileEntitiesWithGlobalRenderers() {
		return tileEntitiesWithGlobalRenderers;
	}

	@Nonnull
	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}

}
