package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfig;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInTypeEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.crash.CrashReport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindMethodException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooks {

	private static final MethodHandle compiledChunk_setLayerUsed;

	static {
		try {
			// newer forge versions
//			compiledChunk_setLayerUsed = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178486_a", Void.class, BlockRenderLayer.class));
			compiledChunk_setLayerUsed = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(CompiledChunk.class, "setLayerUsed", "func_178486_a", BlockRenderLayer.class));
		} catch (final IllegalAccessException | UnableToFindMethodException e) {
			CrashReport crashReport = new CrashReport("Error getting method handle for CompiledChunk#setLayerUsed!", e);
			crashReport.makeCategory("Reflectively Accessing CompiledChunk#setLayerUsed");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Invokes {@link CompiledChunk#setLayerUsed(BlockRenderLayer)}
	 *
	 * @param compiledChunk    the instance of {@link CompiledChunk}
	 * @param blockRenderLayer the layer param
	 */
	public static void compiledChunk_setLayerUsed(final CompiledChunk compiledChunk, final BlockRenderLayer blockRenderLayer) {
		try {
			compiledChunk_setLayerUsed.invokeExact(compiledChunk, blockRenderLayer);
		} catch (final Throwable throwable) {
			CrashReport crashReport = new CrashReport("Error invoking method handle for CompiledChunk#setLayerUsed!", throwable);
			crashReport.makeCategory("Reflectively Accessing CompiledChunk#setLayerUsed");
			throw new ReportedException(crashReport);
		}
	}

	private static final MethodHandle renderChunk_preRenderBlocks;
	static {
		try {
			// newer forge versions
//			renderChunk_preRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178573_a", Void.class, BufferBuilder.class, BlockPos.class));
			renderChunk_preRenderBlocks = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(RenderChunk.class, "preRenderBlocks", "func_178573_a", BufferBuilder.class, BlockPos.class));
		} catch (IllegalAccessException e) {
			CrashReport crashReport = new CrashReport("Error getting method handle for RenderChunk#preRenderBlocks!", e);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#preRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Invokes {@link RenderChunk#preRenderBlocks(BufferBuilder, BlockPos)}
	 *
	 * @param renderChunk   the instance of {@link RenderChunk}
	 * @param bufferBuilder the bufferBuilderIn param
	 * @param pos           the pos param
	 */
	public static void renderChunk_preRenderBlocks(final RenderChunk renderChunk, final BufferBuilder bufferBuilder, final BlockPos pos) {
		try {
			renderChunk_preRenderBlocks.invokeExact(renderChunk, bufferBuilder, pos);
		} catch (Throwable throwable) {
			CrashReport crashReport = new CrashReport("Error invoking method handle for RenderChunk#preRenderBlocks!", throwable);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#preRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	private static final MethodHandle renderChunk_postRenderBlocks;
	static {
		try {
			// newer forge versions
//			renderChunk_postRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178584_a", Void.class, BlockRenderLayer.class, Float.class, Float.class, Float.class, BufferBuilder.class, CompiledChunk.class));
			renderChunk_postRenderBlocks = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(RenderChunk.class, "postRenderBlocks", "func_178584_a", BlockRenderLayer.class, Float.class, Float.class, Float.class, BufferBuilder.class, CompiledChunk.class));
		} catch (IllegalAccessException e) {
			CrashReport crashReport = new CrashReport("Error getting method handle for RenderChunk#postRenderBlocks!", e);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#postRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Invokes {@link RenderChunk#postRenderBlocks(BlockRenderLayer, float, float, float, BufferBuilder, CompiledChunk)}
	 *
	 * @param renderChunk      the instance of {@link RenderChunk}
	 * @param blockRenderLayer the layer param
	 * @param x                the x param
	 * @param y                the y param
	 * @param z                the z param
	 * @param bufferBuilder    the bufferBuilderIn param
	 * @param compiledChunk    the compiledChunkIn param
	 */
	public static void renderChunk_postRenderBlocks(final RenderChunk renderChunk, BlockRenderLayer blockRenderLayer, float x, float y, float z, BufferBuilder bufferBuilder, CompiledChunk compiledChunk) {
		try {
			renderChunk_preRenderBlocks.invokeExact(renderChunk, blockRenderLayer, x, y, z, bufferBuilder, compiledChunk);
		} catch (Throwable throwable) {
			CrashReport crashReport = new CrashReport("Error invoking method handle for RenderChunk#postRenderBlocks!", throwable);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#postRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Returns a started {@link BufferBuilder}
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @param generator        the generator to get the {@link BufferBuilder} from
	 * @param renderChunk      the instance of {@link RenderChunk}
	 * @param compiledChunk    the {@link CompiledChunk}
	 * @param renderChunkPos   the position of the render chunk
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	public BufferBuilder startOrContinueLayer(final BlockRenderLayer blockRenderLayer, final ChunkCompileTaskGenerator generator, RenderChunk renderChunk, CompiledChunk compiledChunk, MutableBlockPos renderChunkPos) {
		return useAndStartOrContinueLayer(blockRenderLayer, generator, renderChunk, compiledChunk, renderChunkPos, false);
	}

	/**
	 * Returns a started {@link BufferBuilder} and sets the appropriate index of usedBlockRenderLayers to true
	 * @param usedBlockRenderLayers the boolean array of used {@link BlockRenderLayer}s
	 * @param blockRenderLayer      the {@link BlockRenderLayer}
	 * @param generator             the generator to get the {@link BufferBuilder} from
	 * @param renderChunk           the instance of {@link RenderChunk}
	 * @param compiledChunk         the {@link CompiledChunk}
	 * @param renderChunkPos        the position of the render chunk
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	public BufferBuilder useAndStartOrContinueLayer(final boolean[] usedBlockRenderLayers, final BlockRenderLayer blockRenderLayer, final ChunkCompileTaskGenerator generator, RenderChunk renderChunk, CompiledChunk compiledChunk, MutableBlockPos renderChunkPos) {
		usedBlockRenderLayers[blockRenderLayer.ordinal()] = true;
		return startOrContinueLayer(blockRenderLayer, generator, renderChunk, compiledChunk, renderChunkPos);
	}

	/**
	 * Returns a started {@link BufferBuilder} and invokes compiledChunk_setLayerUsed directly.
	 * Less efficient than other methods as this gets done for all layers who's index in usedBlockRenderLayers is true
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @param generator        the generator to get the {@link BufferBuilder} from
	 * @param renderChunk      the instance of {@link RenderChunk}
	 * @param compiledChunk    the {@link CompiledChunk}
	 * @param renderChunkPos   the position of the render chunk
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	public BufferBuilder useAndStartOrContinueLayerDirect(final BlockRenderLayer blockRenderLayer, final ChunkCompileTaskGenerator generator, RenderChunk renderChunk, CompiledChunk compiledChunk, MutableBlockPos renderChunkPos) {
		return useAndStartOrContinueLayer(blockRenderLayer, generator, renderChunk, compiledChunk, renderChunkPos, true);
	}

	private BufferBuilder useAndStartOrContinueLayer(final BlockRenderLayer blockRenderLayer, final ChunkCompileTaskGenerator generator, RenderChunk renderChunk, CompiledChunk compiledChunk, MutableBlockPos renderChunkPos, final boolean setLayerUsedDirect) {
		final BufferBuilder bufferBuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer);
		if (!compiledChunk.isLayerStarted(blockRenderLayer)) {
			compiledChunk.setLayerStarted(blockRenderLayer);
			if (setLayerUsedDirect) {
				compiledChunk_setLayerUsed(compiledChunk, blockRenderLayer);
			}
			renderChunk_preRenderBlocks(renderChunk, bufferBuilder, renderChunkPos);
		}

		return bufferBuilder;
	}

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param worldView           the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 * @return If vanilla rendering should be stopped
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean onRebuildChunkPreEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledchunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {
		final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderChunk, renderGlobal, worldView, generator, compiledchunk, renderChunkPosition, x, y, z);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkPreEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();

	}

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPos                  the {@link MutableBlockPos position} of the block being assessed
	 * @param block                     the {@link Block block} being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
	 * @return If the block can render in the layer
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean canBlockRenderInLayer(final RenderChunk renderChunk, final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledchunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final MutableBlockPos blockPos, final Block block, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {
		final RebuildChunkBlockRenderInLayerEvent event = new RebuildChunkBlockRenderInLayerEvent(renderChunk, worldView, chunkCompileTaskGenerator, compiledchunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockPos, blockState, blockRenderLayer);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockRenderInLayerEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			return block.canRenderInLayer(blockState, blockRenderLayer);
		} else {
			return false;
		}

	}

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPos                  the {@link MutableBlockPos position} of the block being assessed
	 * @param block                     the {@link Block block} being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @return if the block should be rendered
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean canBlockRenderInType(final RenderChunk renderChunk, final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledchunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final MutableBlockPos blockPos, final Block block, final IBlockState blockState) {
		final RebuildChunkBlockRenderInTypeEvent event = new RebuildChunkBlockRenderInTypeEvent(renderChunk, worldView, chunkCompileTaskGenerator, compiledchunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockPos, blockState, blockState.getRenderType());

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockRenderInTypeEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			if (RenderChunkRebuildChunkHooksConfig.shouldTweakCanBlockRenderInType()) {
				return blockState.getRenderType() != EnumBlockRenderType.INVISIBLE;
			} else {
				return block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE;
			}
		} else {
			return false;
		}

	}

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param worldView                       the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param blockState                      the {@link IBlockState state} of the block being rendered
	 * @param blockPos                        the {@link MutableBlockPos position} of the block being rendered
	 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
	 * @param renderChunkPosition             the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
	 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @return If the block should NOT be rebuilt to the chunk by vanilla
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean onRebuildChunkBlockEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledchunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final MutableBlockPos renderChunkPosition, boolean[] usedBlockRenderLayers, final BlockRenderLayer blockRenderLayer,
	                                               final float x, final float y, final float z, final HashSet<TileEntity> tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		final RebuildChunkBlockEvent event = new RebuildChunkBlockEvent(renderChunk, renderGlobal, worldView, generator, compiledchunk, blockRendererDispatcher, blockState, blockPos, bufferBuilder, renderChunkPosition, usedBlockRenderLayers, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();

	}

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param worldView           the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param visGraph            the {@link VisGraph} passed in
	 * @param setTileEntities     the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
	 * @param lockCompileTask     the {@link ReentrantLock} for the compile task passed in
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static void onRebuildChunkPostEvent(RenderChunk renderChunk, float x, float y, float z, ChunkCompileTaskGenerator generator, CompiledChunk compiledchunk, MutableBlockPos renderChunkPosition, RenderGlobal renderGlobal, ChunkCache worldView, VisGraph visGraph, Set<TileEntity> setTileEntities, ReentrantLock lockCompileTask) {
		final RebuildChunkPostEvent event = new RebuildChunkPostEvent(renderChunk, x, y, z, generator, compiledchunk, renderChunkPosition, renderGlobal, worldView, visGraph, setTileEntities, lockCompileTask);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkPostEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

	}

}
