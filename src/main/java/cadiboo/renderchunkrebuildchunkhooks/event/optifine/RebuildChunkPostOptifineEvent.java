package cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.optifine.override.ChunkCacheOF;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right before RenderChunk#rebuildChunk returns.<br>
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
//TODO make this
public class RebuildChunkPostOptifineEvent extends RebuildChunkPostEvent {

	final ChunkCacheOF chunkCacheOF;

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF           the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkPostOptifineEvent(RenderChunk renderChunk, RenderGlobal renderGlobal, ChunkCacheOF chunkCacheOF, ChunkCompileTaskGenerator generator, CompiledChunk compiledChunk, MutableBlockPos renderChunkPosition, float x, float y, float z) {

		super(renderChunk, renderGlobal, chunkCacheOF.chunkCache, generator, compiledChunk, renderChunkPosition, x, y, z);

		this.chunkCacheOF = chunkCacheOF;
	}

}