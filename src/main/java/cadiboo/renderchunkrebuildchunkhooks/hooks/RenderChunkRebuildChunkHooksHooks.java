package cadiboo.renderchunkrebuildchunkhooks.hooks;

import java.util.HashSet;
import java.util.logging.Logger;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlocksEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;

public class RenderChunkRebuildChunkHooksHooks {

	public static boolean dummyEventBoolean() {
		final Logger logger = Logger.getLogger(RenderChunkRebuildChunkHooksDummyContainer.MOD_ID);
		logger.info("dummyEvent");
		return Boolean.valueOf("false").booleanValue();
	}

	public static void dummyEventVoid() {
		final Logger logger = Logger.getLogger(RenderChunkRebuildChunkHooksDummyContainer.MOD_ID);
		logger.info("dummyEvent");
	}

	// public static RenderBlockRenderLayerEvent onRenderBlockRenderLayerEvent(final RenderGlobal renderGlobal, final BlockRenderLayer blockRenderLayer, final double partialTicks, final int pass, final Entity entity, final int chunksRendered) {
	// final RenderBlockRenderLayerEvent event = new RenderBlockRenderLayerEvent(renderGlobal, blockRenderLayer, partialTicks, pass, entity, chunksRendered);
	// MinecraftForge.EVENT_BUS.post(event);
	// return event;
	// }

	/**
	 * @return if vanilla rendering should be stopped
	 */
	public static boolean onRebuildChunkEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {
		final RebuildChunkEvent event = new RebuildChunkEvent(renderGlobal, worldView, generator, compiledChunk, renderChunkPosition, x, y, z);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isCanceled();
	}

	public static RebuildChunkBlocksEvent onRebuildChunkBlocksEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final Iterable<MutableBlockPos> chunkBlockPositions, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z, final HashSet tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		final RebuildChunkBlocksEvent event = new RebuildChunkBlocksEvent(renderGlobal, worldView, generator, compiledChunk, chunkBlockPositions, blockRendererDispatcher, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static RebuildChunkBlockEvent onRebuildChunkBlockEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z, final HashSet tileEntitiesWithGlobalRenderers,
			final VisGraph visGraph) {
		final RebuildChunkBlockEvent event = new RebuildChunkBlockEvent(renderGlobal, worldView, generator, compiledChunk, blockRendererDispatcher, blockState, blockPos, bufferBuilder, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

}
