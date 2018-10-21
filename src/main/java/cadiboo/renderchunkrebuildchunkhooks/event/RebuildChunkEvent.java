package cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Called when a {@link net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right after the CompiledChunk is generated and before any rebuilding is done.<br>
 * Canceling this event prevents all Blocks and Tile Entities from being rebuilt to the chunk (and therefore rendered)
 */
@Cancelable
public class RebuildChunkEvent extends Event {

	private final RenderGlobal				context;
	private final ChunkCache				worldView;
	private final ChunkCompileTaskGenerator	generator;
	private final CompiledChunk				compiledChunk;
	private final MutableBlockPos			renderChunkPosition;
	private final float						x;
	private final float						y;
	private final float						z;

	public RebuildChunkEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {
		this.context = renderGlobal;
		this.worldView = worldView;
		this.generator = generator;
		this.compiledChunk = compiledChunk;
		this.renderChunkPosition = renderChunkPosition;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public RenderGlobal getContext() {
		return this.context;
	}

	public ChunkCache getWorldView() {
		return this.worldView;
	}

	public ChunkCompileTaskGenerator getGenerator() {
		return this.generator;
	}

	public CompiledChunk getCompiledChunk() {
		return this.compiledChunk;
	}

	public MutableBlockPos getRenderChunkPosition() {
		return this.renderChunkPosition;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

}