package cadiboo.renderchunkrebuildchunkhooks.hooks;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Sets;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlocksEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public final class RenderChunkRebuildChunkHooksHooks {

	public static final Method			COMPILED_CHUNK_SET_LAYER_USED			= getMethodAndMakeItAccessable();
	public static final MethodHandle	COMPILED_CHUNK_SET_LAYER_USED_HANDLE	= getMethodHandle();

	private static Method getMethodAndMakeItAccessable() {
		final Method method = ReflectionHelper.findMethod(CompiledChunk.class, "setLayerUsed", "func_178486_a", BlockRenderLayer.class);
		method.setAccessible(true);
		return method;
	}

	static MethodHandle getMethodHandle() {
		try {
			return MethodHandles.publicLookup().unreflect(COMPILED_CHUNK_SET_LAYER_USED);
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			// throw an error
			((Object) null).getClass();
			return null;
		}
	}

	/**
	 * FOR INTERNAL USE ONLY, CALLED BY INJECTED CODE IN RenderChunk#rebuildChunk
	 *
	 * @return renderChunksUpdated
	 */
	public static int rebuildChunk(final float x, final float y, final float z, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final MutableBlockPos renderChunkPosition, final ChunkCache worldView, final RenderGlobal renderGlobal, int renderChunksUpdated, final ReentrantLock lockCompileTask, final Set<TileEntity> setTileEntities) {

		final CompiledChunk compiledChunk = new CompiledChunk();

		chunkCompileTaskGenerator.getLock().lock();
		try {
			if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
				return renderChunksUpdated;
			}
			chunkCompileTaskGenerator.setCompiledChunk(compiledChunk);
		} finally {
			chunkCompileTaskGenerator.getLock().unlock();
		}

		if (RenderChunkRebuildChunkHooksHooks.onRebuildChunkEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, renderChunkPosition, x, y, z)) {
			return renderChunksUpdated;
		}

		final VisGraph visGraph = new VisGraph();
		final HashSet<TileEntity> tileEntitiesWithGlobalRenderers = Sets.<TileEntity>newHashSet();

		if (!worldView.isEmpty()) {
			++renderChunksUpdated;

			final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

			final RebuildChunkBlocksEvent rebuildBlocksEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)), blockRendererDispatcher, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

			final boolean[] usedBlockRenderLayers = rebuildBlocksEvent.getUsedBlockRenderLayers();

			for (final MutableBlockPos currentPos : BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15))) {
				final IBlockState blockState = worldView.getBlockState(currentPos);
				final Block block = blockState.getBlock();

				if (blockState.isOpaqueCube()) {
					visGraph.setOpaqueCube(currentPos);
				}

				if (block.hasTileEntity(blockState)) {
					final TileEntity tileentity = worldView.getTileEntity(currentPos, Chunk.EnumCreateEntityType.CHECK);

					if (tileentity != null) {
						final TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.<TileEntity>getRenderer(tileentity);

						if (tileentityspecialrenderer != null) {

							if (tileentityspecialrenderer.isGlobalRenderer(tileentity)) {
								tileEntitiesWithGlobalRenderers.add(tileentity);
							} else {
								compiledChunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
							}
						}
					}
				}

				for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
					if (!block.canRenderInLayer(blockState, blockRenderLayer) && !RenderChunkRebuildChunkHooksHooks.canRenderInLayer(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockState, blockRenderLayer)) {
						continue;
					}
					net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);
					final int blockRenderLayerId = blockRenderLayer.ordinal();

					if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
						final BufferBuilder bufferbuilder = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(blockRenderLayerId);

						if (!compiledChunk.isLayerStarted(blockRenderLayer)) {
							compiledChunk.setLayerStarted(blockRenderLayer);
							preRenderBlocks(bufferbuilder, renderChunkPosition);
						}

						if (!rebuildBlocksEvent.isCanceled()) {
							final RebuildChunkBlockEvent rebuildBlockEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, blockState, currentPos, bufferbuilder, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

							for (final BlockRenderLayer blockRenderLayer2 : BlockRenderLayer.values()) {
								final int blockRenderLayer2Id = blockRenderLayer2.ordinal();
								usedBlockRenderLayers[blockRenderLayer2Id] |= rebuildBlockEvent.getUsedBlockRenderLayers()[blockRenderLayer2Id];
							}

							if (!rebuildBlockEvent.isCanceled()) {
								usedBlockRenderLayers[blockRenderLayerId] |= blockRendererDispatcher.renderBlock(blockState, currentPos, worldView, bufferbuilder);
							}
						}
						rebuildBlocksEvent.isCanceled();
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
			}

			for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
				if (usedBlockRenderLayers[blockRenderLayer.ordinal()]) {
//					compiledChunk.setLayerUsed(blockRenderLayer);
					try {
						COMPILED_CHUNK_SET_LAYER_USED_HANDLE.invokeExact(compiledChunk, blockRenderLayer);
					} catch (final Throwable e) {
						e.printStackTrace();
						// throw an error
						((Object) null).getClass();
					}
				}

				if (compiledChunk.isLayerStarted(blockRenderLayer)) {
					postRenderBlocks(blockRenderLayer, x, y, z, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer), compiledChunk);
				}
			}
		}

		compiledChunk.setVisibility(visGraph.computeVisibility());
		lockCompileTask.lock();

		try {
			final Set<TileEntity> set = Sets.newHashSet(tileEntitiesWithGlobalRenderers);
			final Set<TileEntity> set1 = Sets.newHashSet(setTileEntities);
			set.removeAll(setTileEntities);
			set1.removeAll(tileEntitiesWithGlobalRenderers);
			setTileEntities.clear();
			setTileEntities.addAll(tileEntitiesWithGlobalRenderers);
			renderGlobal.updateTileEntities(set1, set);
		} finally {
			lockCompileTask.unlock();
		}

		return renderChunksUpdated;

	}

	private static void preRenderBlocks(final BufferBuilder bufferBuilderIn, final BlockPos pos) {
		bufferBuilderIn.begin(7, DefaultVertexFormats.BLOCK);
		bufferBuilderIn.setTranslation((-pos.getX()), (-pos.getY()), (-pos.getZ()));
	}

	private static void postRenderBlocks(final BlockRenderLayer layer, final float x, final float y, final float z, final BufferBuilder bufferBuilderIn, final CompiledChunk compiledChunkIn) {
		if ((layer == BlockRenderLayer.TRANSLUCENT) && !compiledChunkIn.isLayerEmpty(layer)) {
			bufferBuilderIn.sortVertexData(x, y, z);
			compiledChunkIn.setState(bufferBuilderIn.getVertexState());
		}

		bufferBuilderIn.finishDrawing();
	}

	// public static RenderBlockRenderLayerEvent onRenderBlockRenderLayerEvent(final RenderGlobal renderGlobal, final BlockRenderLayer blockRenderLayer, final double partialTicks, final int pass, final Entity entity, final int chunksRendered) {
	// final RenderBlockRenderLayerEvent event = new RenderBlockRenderLayerEvent(renderGlobal, blockRenderLayer, partialTicks, pass, entity, chunksRendered);
	// MinecraftForge.EVENT_BUS.post(event);
	// return event;
	// }

	public static boolean canRenderInLayer(final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {
		final RebuildChunkBlockRenderInLayerEvent event = new RebuildChunkBlockRenderInLayerEvent(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockState, blockRenderLayer);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getResult() == Event.Result.ALLOW;
	}

	/**
	 * @return if vanilla rendering should be stopped
	 */
	public static boolean onRebuildChunkEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {
		final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderGlobal, worldView, generator, compiledChunk, renderChunkPosition, x, y, z);
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
