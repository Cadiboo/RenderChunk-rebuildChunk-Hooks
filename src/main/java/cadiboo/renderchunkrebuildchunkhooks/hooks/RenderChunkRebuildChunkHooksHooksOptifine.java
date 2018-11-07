package cadiboo.renderchunkrebuildchunkhooks.hooks;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Sets;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlocksEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.optifine.BlockPosM;
import net.optifine.override.ChunkCacheOF;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooksOptifine {

	// because Config is in the default package we need to use reflection to access it FFS Optifine
	public static final Class<?>		OPTIFINE_CONFIG;
	public static final MethodHandle	OPTIFINE_CONFIG_IS_SHADERS;

	static {
		// Class<?> optifineConfig = Class.forName("Config", false, Loader.instance().getModClassLoader());
		OPTIFINE_CONFIG = ReflectionHelper.getClass(Loader.instance().getModClassLoader(), "Config");
		OPTIFINE_CONFIG_IS_SHADERS = getMethod();
	}

	public static MethodHandle getMethod() {
		try {
			return MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(OPTIFINE_CONFIG, "isShaders", null));
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			// throw an error
			((Object) null).getClass();
			return null;
		}
	}

	public static boolean Config_isShaders() {
//		return Config.isShaders();
		try {
			return (boolean) OPTIFINE_CONFIG_IS_SHADERS.invokeExact();
		} catch (final Throwable e) {
			e.printStackTrace();
			// throw an error
			((Object) null).getClass();
			return false;
		}
	}

	/**
	 * FOR INTERNAL USE ONLY, CALLED BY INJECTED CODE IN RenderChunk#rebuildChunk if Optifine is present
	 *
	 * @param renderChunk               the {@link RenderChunk} we are hooking into
	 * @param x                         the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                         the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                         the translation Z passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal              the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param renderChunksUpdated       the "renderChunksUpdated" variable passed in from RenderChunk#rebuildChunk
	 * @param lockCompileTask           the {@link ReentrantLock} passed in from RenderChunk#rebuildChunk
	 * @param setTileEntities           the {@link HashSet} of {@link TileEntity TileEntities} passed in from RenderChunk#rebuildChunk
	 * @return renderChunksUpdated the amount of Render Chunks Updated (usually renderChunksUpdated+1)
	 */
	public static int rebuildChunkOptifine(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final MutableBlockPos renderChunkPosition, final ChunkCache worldView, final RenderGlobal renderGlobal, int renderChunksUpdated, final ReentrantLock lockCompileTask, final Set<TileEntity> setTileEntities) {

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

		if (!renderChunk.isChunkRegionEmpty(renderChunkPosition)) {
			renderChunksUpdated += 1;

			final ChunkCacheOF blockAccess = renderChunk.makeChunkCacheOF(renderChunkPosition);

			blockAccess.renderStart();

			final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

			final RebuildChunkBlocksEvent rebuildBlocksEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)), blockRendererDispatcher, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

			final boolean[] usedBlockRenderLayers = rebuildBlocksEvent.getUsedBlockRenderLayers();

			final Iterator<BlockPosM> iterBlocks = BlockPosM.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)).iterator();
			while (iterBlocks.hasNext()) {
				final BlockPosM currentPos = iterBlocks.next();
				final IBlockState blockState = blockAccess.getBlockState(currentPos);
				final Block block = blockState.getBlock();
				if (blockState.isOpaqueCube()) {
					visGraph.setOpaqueCube(currentPos);
				}
				if (ReflectorForge.blockHasTileEntity(blockState)) {
					final TileEntity tileentity = blockAccess.getTileEntity(currentPos, Chunk.EnumCreateEntityType.CHECK);
					if (tileentity != null) {
						final TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getRenderer(tileentity);
						if (tileentityspecialrenderer != null) {
							if (tileentityspecialrenderer.isGlobalRenderer(tileentity)) {
								tileEntitiesWithGlobalRenderers.add(tileentity);
							} else {
								compiledChunk.addTileEntity(tileentity);
							}
						}
					}
				}
				for (BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
					if (!block.canRenderInLayer(blockState, blockRenderLayer) && !canRenderInLayer(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, currentPos, blockState, blockRenderLayer)) {
						continue;
					}
					net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);
					blockRenderLayer = renderChunk.fixBlockLayer(blockState, blockRenderLayer);
					final int blockRenderLayerId = blockRenderLayer.ordinal();

					if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
						final BufferBuilder bufferbuilder = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(blockRenderLayerId);

						bufferbuilder.setBlockLayer(blockRenderLayer);

						final RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockAccess, blockState, currentPos);
						renderEnv.setRegionRenderCacheBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
						if (!compiledChunk.isLayerStarted(blockRenderLayer)) {
							compiledChunk.setLayerStarted(blockRenderLayer);
							renderChunk.preRenderBlocks(bufferbuilder, renderChunkPosition);
						}

						if (!rebuildBlocksEvent.isCanceled()) {
							final RebuildChunkBlockEvent rebuildBlockEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, blockState, new MutableBlockPos(currentPos), bufferbuilder, renderChunkPosition, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

							for (final BlockRenderLayer blockRenderLayer2 : BlockRenderLayer.values()) {
								final int blockRenderLayer2Id = blockRenderLayer2.ordinal();
								usedBlockRenderLayers[blockRenderLayer2Id] |= rebuildBlockEvent.getUsedBlockRenderLayers()[blockRenderLayer2Id];
							}

							if (!rebuildBlockEvent.isCanceled()) {
								usedBlockRenderLayers[blockRenderLayerId] |= blockRendererDispatcher.renderBlock(blockState, currentPos, blockAccess, bufferbuilder);
							}
						}
						if (renderEnv.isOverlaysRendered()) {
							renderChunk.postRenderOverlays(chunkCompileTaskGenerator.getRegionRenderCacheBuilder(), compiledChunk, usedBlockRenderLayers);
							renderEnv.setOverlaysRendered(false);
						}
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
			}
			for (final BlockRenderLayer blockrenderlayer2 : RenderChunk.ENUM_WORLD_BLOCK_LAYERS) {
				if (usedBlockRenderLayers[blockrenderlayer2.ordinal()]) {
					compiledChunk.setLayerUsed(blockrenderlayer2);
				}
				if (compiledChunk.isLayerStarted(blockrenderlayer2)) {
//					if (Config.isShaders()) {
					if (Config_isShaders()) {
						SVertexBuilder.calcNormalChunkLayer(chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer2));
					}
					final BufferBuilder bufferBuilder = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer2);
					renderChunk.postRenderBlocks(blockrenderlayer2, x, y, z, bufferBuilder, compiledChunk);
					if (bufferBuilder.animatedSprites != null) {
						compiledChunk.setAnimatedSprites(blockrenderlayer2, (BitSet) bufferBuilder.animatedSprites.clone());
					}
				} else {
					compiledChunk.setAnimatedSprites(blockrenderlayer2, (BitSet) null);
				}
			}
			blockAccess.renderFinish();
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

	private static boolean canRenderInLayer(final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final BlockPosM blockPos, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {
		return RenderChunkRebuildChunkHooksHooks.canRenderInLayer(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, new MutableBlockPos(blockPos), blockState, blockRenderLayer);
	}
}
