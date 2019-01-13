package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

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
	private final ChunkCompileTaskGenerator generator;
	@Nonnull
	private final CompiledChunk compiledChunk;
	@Nonnull
	private final BlockPos renderChunkPosition;
	@Nonnull
	private final ChunkCache chunkCache;
	@Nonnull
	private final VisGraph visGraph;
	@Nonnull
	private final HashSet<TileEntity> tileEntitiesWithGlobalRenderers;
	@Nonnull
	private final RenderGlobal renderGlobal;

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk}
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache                      the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkEvent(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledChunk,
			@Nonnull final BlockPos renderChunkPosition,
			@Nonnull final ChunkCache chunkCache,
			@Nonnull final VisGraph visGraph,
			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
			@Nonnull final RenderGlobal renderGlobal
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
		this.renderGlobal = renderGlobal;
	}

	/**
	 * @return the type of event
	 */
	@Nonnull
	public EnumEventType getType() {
		return EnumEventType.FORGE;
	}

	/**
	 * @return the {@link IBlockAccess}
	 */
	@Nonnull
	public IBlockAccess getIBlockAccess() {
		return getChunkCache();
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
	public ChunkCompileTaskGenerator getGenerator() {
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
	public ChunkCache getChunkCache() {
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
	public RenderGlobal getRenderGlobal() {
		return renderGlobal;
	}

}
