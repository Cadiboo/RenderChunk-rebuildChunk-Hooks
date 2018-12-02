package cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Called when a {@link net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right after the CompiledChunk is generated and before any rebuilding is done.<br>
 * Canceling this event prevents all Blocks and Tile Entities from being rebuilt to the chunk (and therefore rendered)
 *
 * @author Cadiboo
 * @see net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkPreEvent extends Event {

	private final RenderChunk renderChunk;
	private final RenderGlobal renderGlobal;
	private final ChunkCache chunkCache;
	private final ChunkCompileTaskGenerator generator;
	private final CompiledChunk compiledchunk;
	private final MutableBlockPos renderChunkPosition;
	private final float x;
	private final float y;
	private final float z;

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCache          the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkPreEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache chunkCache, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledchunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {

		this.renderChunk = renderChunk;
		this.renderGlobal = renderGlobal;
		this.chunkCache = chunkCache;
		this.generator = generator;
		this.compiledchunk = compiledchunk;
		this.renderChunkPosition = renderChunkPosition;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the instance of {@link RenderChunk} the event is being fired for
	 */
	public RenderChunk getRenderChunk() {

		return this.renderChunk;
	}

	/**
	 * @return the {@link RenderGlobal} passed in
	 */
	public RenderGlobal getRenderGlobal() {

		return this.renderGlobal;
	}

	/**
	 * @return the {@link ChunkCache} passed in
	 */
	public ChunkCache getChunkCache() {

		return this.chunkCache;
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

}