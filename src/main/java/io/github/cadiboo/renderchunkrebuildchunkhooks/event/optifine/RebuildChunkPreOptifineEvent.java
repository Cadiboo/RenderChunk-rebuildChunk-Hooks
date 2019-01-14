//package io.github.cadiboo.renderchunkrebuildchunkhooks.event.optifine;
//
//import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
//import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
//import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
//import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
//import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.EnumEventType;
//import net.minecraft.client.renderer.BufferBuilder;
//import net.minecraft.client.renderer.RenderGlobal;
//import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
//import net.minecraft.client.renderer.chunk.CompiledChunk;
//import net.minecraft.client.renderer.chunk.RenderChunk;
//import net.minecraft.client.renderer.chunk.VisGraph;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.BlockRenderLayer;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.fml.common.eventhandler.Cancelable;
//import net.optifine.override.ChunkCacheOF;
//
//import javax.annotation.Nonnull;
//import java.util.HashSet;
//
//import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooksOptifine.getChunkCacheFromChunkCacheOF;
//
///**
// * Called when a {@link RenderChunk#rebuildChunk} is called when Optifine is present.
// * This event is fired on the {@link MinecraftForge#EVENT_BUS} right before the usedBlockRenderLayers boolean[] is generated and before any rebuilding is done.
// * Canceling this event prevents all Blocks and Tile Entities from being rebuilt to the chunk (and therefore rendered)
// * It is possible to do your own rendering in this event, but you will need to manually do large amounts of logic that would otherwise be done for you in other events.
// * If you do your own rendering please not that you must manually
// * - Manage starting & offsetting {@link BufferBuilder}s
// * - Manage used {@link BlockRenderLayer}s
// * {@link RenderChunkRebuildChunkHooksHooks} provides methods for pre-rendering the {@link BufferBuilder}s
// * You can |= your used render layers in a later event such as the {@link RebuildChunkBlockRenderInLayerEvent} or the {@link RebuildChunkBlockEvent}
// *
// * @author Cadiboo
// * @see RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
// */
//@Cancelable
//public class RebuildChunkPreOptifineEvent extends RebuildChunkPreEvent {
//
//	private final ChunkCacheOF chunkCacheOF;
//
//	/**
//	 * @param renderChunk                     the instance of {@link RenderChunk}
//	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
//	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
//	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
//	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
//	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
//	 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
//	 * @param chunkCacheOF                    the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
//	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
//	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
//	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
//	 */
//	public RebuildChunkPreOptifineEvent(
//			@Nonnull final RenderChunk renderChunk,
//			final float x,
//			final float y,
//			final float z,
//			@Nonnull final ChunkCompileTaskGenerator generator,
//			@Nonnull final CompiledChunk compiledChunk,
//			@Nonnull final BlockPos renderChunkPosition,
//			@Nonnull final ChunkCacheOF chunkCacheOF,
//			@Nonnull final VisGraph visGraph,
//			@Nonnull final HashSet<TileEntity> tileEntitiesWithGlobalRenderers,
//			@Nonnull final RenderGlobal renderGlobal) {
//		super(renderChunk, x, y, z, generator, compiledChunk, renderChunkPosition, getChunkCacheFromChunkCacheOF(chunkCacheOF), visGraph, tileEntitiesWithGlobalRenderers, renderGlobal);
//		this.chunkCacheOF = chunkCacheOF;
//	}
//
//	/**
//	 * @return the type of event
//	 */
//	@Nonnull
//	@Override
//	public EnumEventType getType() {
//		return EnumEventType.FORGE_OPTIFINE;
//	}
//
//	@Nonnull
//	public ChunkCacheOF getChunkCacheOF() {
//		return chunkCacheOF;
//	}
//
//	/**
//	 * @return the {@link ChunkCacheOF} passed in
//	 */
//	@Nonnull
//	@Override
//	public IBlockAccess getIBlockAccess() {
//		return getChunkCacheOF();
//	}
//
//}
