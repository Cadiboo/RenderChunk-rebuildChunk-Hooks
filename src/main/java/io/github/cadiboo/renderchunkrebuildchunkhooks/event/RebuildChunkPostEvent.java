package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right before RenderChunk#rebuildChunk returns.<br>
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
public class RebuildChunkPostEvent extends Event {

	private final RenderChunk renderChunk;
	private final float x;
	private final float y;
	private final float z;
	private final ChunkCompileTaskGenerator generator;
	private final CompiledChunk compiledchunk;
	private final MutableBlockPos renderChunkPosition;
	private final RenderGlobal renderGlobal;
	private final ChunkCache chunkCache;
	private final VisGraph visGraph;
	private final Set<TileEntity> setTileEntities;
	private final ReentrantLock lockCompileTask;

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache          the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph            the {@link VisGraph} passed in
	 * @param setTileEntities     the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
	 * @param lockCompileTask     the {@link ReentrantLock} for the compile task passed in
	 */
	public RebuildChunkPostEvent(RenderChunk renderChunk, float x, float y, float z, ChunkCompileTaskGenerator generator, CompiledChunk compiledchunk, MutableBlockPos renderChunkPosition, RenderGlobal renderGlobal, ChunkCache chunkCache, VisGraph visGraph, Set<TileEntity> setTileEntities, ReentrantLock lockCompileTask) {
		this.renderChunk = renderChunk;
		this.x = x;
		this.y = y;
		this.z = z;
		this.generator = generator;
		this.compiledchunk = compiledchunk;
		this.renderChunkPosition = renderChunkPosition;
		this.renderGlobal = renderGlobal;
		this.chunkCache = chunkCache;
		this.visGraph = visGraph;
		this.setTileEntities = setTileEntities;
		this.lockCompileTask = lockCompileTask;
	}

	/**
	 * @return the instance of {@link RenderChunk} the event is being fired for
	 */
	public RenderChunk getRenderChunk() {
		return this.renderChunk;
	}

	/**
	 * @return the X passed in
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * @return the Y passed in
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * @return the Z passed in
	 */
	public float getZ() {
		return this.z;
	}

	/**
	 * @return the {@link ChunkCompileTaskGenerator} passed in
	 */
	public ChunkCompileTaskGenerator getGenerator() {
		return this.generator;
	}

	/**
	 * @return the {@link CompiledChunk} passed in
	 */
	public CompiledChunk getCompiledChunk() {
		return this.compiledchunk;
	}

	/**
	 * @return the position passed in
	 */
	public MutableBlockPos getRenderChunkPosition() {
		return this.renderChunkPosition;
	}

	/**
	 * @return the {@link RenderGlobal} passed in
	 */
	public RenderGlobal getRenderGlobal() {
		return renderGlobal;
	}

	/**
	 * @return the {@link ChunkCache} passed in
	 */
	public ChunkCache getChunkCache() {
		return this.chunkCache;
	}

	/**
	 * @return the {@link VisGraph} passed in
	 */
	public VisGraph getVisGraph() {
		return visGraph;
	}

	/**
	 * @return the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
	 */
	public Set<TileEntity> getSetTileEntities() {
		return setTileEntities;
	}

	/**
	 * @return the {@link ReentrantLock} for the compile task passed in
	 */
	public ReentrantLock getLockCompileTask() {
		return lockCompileTask;
	}

}
