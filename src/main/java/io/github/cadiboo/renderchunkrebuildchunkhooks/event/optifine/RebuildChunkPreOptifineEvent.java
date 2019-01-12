package io.github.cadiboo.renderchunkrebuildchunkhooks.event.optifine;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooksOptifine;
import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.optifine.override.ChunkCacheOF;

import javax.annotation.Nonnull;

/**
 * Called when a {@link RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called when Optifine is present.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right after the CompiledChunk is generated and before any rebuilding is done.<br>
 * Canceling this event prevents all Blocks and Tile Entities from being rebuilt to the chunk (and therefore rendered)
 *
 * @author Cadiboo
 * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkPreOptifineEvent extends RebuildChunkPreEvent {

	private final ChunkCacheOF chunkCacheOF;

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param chunkCacheOF        the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 */
	public RebuildChunkPreOptifineEvent(RenderChunk renderChunk, RenderGlobal renderGlobal, ChunkCacheOF chunkCacheOF, ChunkCompileTaskGenerator generator, CompiledChunk compiledchunk, MutableBlockPos renderChunkPosition, float x, float y, float z) {
		super(renderChunk, renderGlobal, RenderChunkRebuildChunkHooksHooksOptifine.getChunkCacheFromChunkCacheOF(chunkCacheOF), generator, compiledchunk, renderChunkPosition, x, y, z);
		this.chunkCacheOF = chunkCacheOF;
	}

	/**
	 * @return the type of event
	 */
	@Nonnull
	public EnumEventType getType() {
		return EnumEventType.FORGE_OPTIFINE;
	}

	/**
	 * @return the {@link ChunkCacheOF} passed in
	 */
	public ChunkCacheOF getChunkCacheOF() {
		return chunkCacheOF;
	}

	/**
	 * @return the {@link ChunkCacheOF} passed in
	 */
	@Nonnull
	@Override
	public IBlockAccess getIBlockAccess() {
		return getChunkCacheOF();
	}

}
