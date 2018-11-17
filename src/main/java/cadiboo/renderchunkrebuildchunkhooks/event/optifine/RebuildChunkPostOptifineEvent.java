package cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.optifine.override.ChunkCacheOF;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called when Optifine is present.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right before RenderChunk#rebuildChunk returns.<br>
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
public class RebuildChunkPostOptifineEvent extends RebuildChunkPostEvent {

	final ChunkCacheOF chunkCacheOF;

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF        the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param visGraph            the {@link VisGraph} passed in
	 * @param setTileEntities     the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
	 * @param lockCompileTask     the {@link ReentrantLock} for the compile task passed in
	 */
	public RebuildChunkPostOptifineEvent(RenderChunk renderChunk, float x, float y, float z, ChunkCompileTaskGenerator generator, CompiledChunk compiledchunk, MutableBlockPos renderChunkPosition, RenderGlobal renderGlobal, ChunkCacheOF chunkCacheOF, VisGraph visGraph, Set<TileEntity> setTileEntities, ReentrantLock lockCompileTask) {

		super(renderChunk, x, y, z, generator, compiledchunk, renderChunkPosition, renderGlobal, chunkCacheOF.chunkCache, visGraph, setTileEntities, lockCompileTask);
		this.chunkCacheOF = chunkCacheOF;
	}

	/**
	 * @return the {@link ChunkCacheOF} passed in
	 */
	public ChunkCacheOF getChunkCacheOF() {

		return chunkCacheOF;
	}

}