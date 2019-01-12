package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import java.util.HashSet;

/**
 * Base class for all events this mod provides
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.
 * This event is fired on the {@link MinecraftForge#EVENT_BUS EVENT_BUS}
 */
public class RebuildChunkEvent extends Event {

	@Nonnull
	private final RenderChunk renderChunk;
	private final int x;
	private final int y;
	private final int z;
	@Nonnull
	private final ChunkCompileTaskGenerator generator;
	@Nonnull
	private final CompiledChunk compiledchunk;
	@Nonnull
	private final MutableBlockPos renderChunkPosition;
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
	 * @param renderChunkPosition             the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache                      the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkEvent(
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
			@Nonnull final RenderGlobal renderGlobal
	) {

		this.renderChunk = renderChunk;
		this.x = x;
		this.y = y;
		this.z = z;
		this.generator = generator;
		this.compiledchunk = compiledChunk;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Nonnull
	public ChunkCompileTaskGenerator getGenerator() {
		return generator;
	}

	@Nonnull
	public CompiledChunk getCompiledchunk() {
		return compiledchunk;
	}

	@Nonnull
	public MutableBlockPos getRenderChunkPosition() {
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
