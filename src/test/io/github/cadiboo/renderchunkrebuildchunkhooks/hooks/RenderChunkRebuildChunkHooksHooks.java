package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfig;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderTypeAllowsRenderEvent;
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

import javax.annotation.Nonnull;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooks {

	private static final MethodHandle compiledChunk_setLayerUsed;
	private static final MethodHandle renderChunk_preRenderBlocks;
	private static final MethodHandle renderChunk_postRenderBlocks;

	static {
		final CrashReport crashReport;
		try {
			//grr, backwards compatibility
			compiledChunk_setLayerUsed = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(CompiledChunk.class, "setLayerUsed", "func_178486_a", BlockRenderLayer.class));
//			compiledChunk_setLayerUsed = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(CompiledChunk.class, "func_178486_a", void.class, BlockRenderLayer.class));
		} catch (final IllegalAccessException | UnableToFindMethodException e) {
			crashReport = new CrashReport("Error getting method handle for CompiledChunk#setLayerUsed!", e);
			crashReport.makeCategory("Reflectively Accessing CompiledChunk#setLayerUsed");
			throw new ReportedException(crashReport);
		}
		try {
			renderChunk_preRenderBlocks = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(RenderChunk.class, "preRenderBlocks", "func_178573_a", BufferBuilder.class, BlockPos.class));
//			renderChunk_preRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178573_a", void.class, BufferBuilder.class, BlockPos.class));
		} catch (IllegalAccessException e) {
			crashReport = new CrashReport("Error getting method handle for RenderChunk#preRenderBlocks!", e);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#preRenderBlocks");
			throw new ReportedException(crashReport);
		}
		try {
			renderChunk_postRenderBlocks = MethodHandles.publicLookup().unreflect(ReflectionHelper.findMethod(RenderChunk.class, "postRenderBlocks", "func_178584_a", BlockRenderLayer.class, float.class, float.class, float.class, BufferBuilder.class, CompiledChunk.class));
//			renderChunk_postRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178584_a", void.class, BlockRenderLayer.class, float.class, float.class, float.class, BufferBuilder.class, CompiledChunk.class));
		} catch (IllegalAccessException e) {
			crashReport = new CrashReport("Error getting method handle for RenderChunk#postRenderBlocks!", e);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#postRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Invokes {@link CompiledChunk#setLayerUsed(BlockRenderLayer)}
	 *
	 * @param compiledChunk    the instance of {@link CompiledChunk}
	 * @param blockRenderLayer the layer param
	 * @deprecated You probably don't want this, you probably want something like <code>event.getUsedBlockRenderLayers()[blockRenderLayer.ordinal()] = true;</code>
	 */
	@Deprecated
	public static void compiledChunk_setLayerUsed(final CompiledChunk compiledChunk, final BlockRenderLayer blockRenderLayer) {
		try {
			compiledChunk_setLayerUsed.invokeExact(compiledChunk, blockRenderLayer);
		} catch (final Throwable throwable) {
			CrashReport crashReport = new CrashReport("Error invoking method handle for CompiledChunk#setLayerUsed!", throwable);
			crashReport.makeCategory("Reflectively Accessing CompiledChunk#setLayerUsed");
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
			renderChunk_postRenderBlocks.invokeExact(renderChunk, blockRenderLayer, x, y, z, bufferBuilder, compiledChunk);
		} catch (Throwable throwable) {
			CrashReport crashReport = new CrashReport("Error invoking method handle for RenderChunk#postRenderBlocks!", throwable);
			crashReport.makeCategory("Reflectively Accessing RenderChunk#postRenderBlocks");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Returns a started {@link BufferBuilder}
	 *
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
	 *
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
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @param generator        the generator to get the {@link BufferBuilder} from
	 * @param renderChunk      the instance of {@link RenderChunk}
	 * @param compiledChunk    the {@link CompiledChunk}
	 * @param renderChunkPos   the position of the render chunk
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 * @deprecated You probably don't want this, you probably want something like <code>event.getUsedBlockRenderLayers()[blockRenderLayer.ordinal()] = true;</code>
	 */
	@Deprecated
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
	 * @param renderChunk   the instance of {@link RenderChunk}
	 * @param x             the translation X passed in from RenderChunk#rebuildChunk
	 * @param y             the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z             the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator     the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockpos      the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
	 * @param worldView     the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param lvt_9_1_      the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param lvt_10_1_     the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal  the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @return If vanilla rendering should be stopped
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean rebuildChunkPreHook(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final ChunkCache worldView,
			@Nonnull final VisGraph lvt_9_1_,
			@Nonnull final HashSet<TileEntity> lvt_10_1_,
			@Nonnull final RenderGlobal renderGlobal
	) {
		final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, worldView, lvt_9_1_, lvt_10_1_, renderGlobal);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkPreEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

	/**
	 * @param renderChunk              the instance of {@link RenderChunk}
	 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
	 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
	 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
	 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
	 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
	 * @param block                    the {@link Block} the event is firing for
	 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
	 * @return If the block can render in the layer
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean canBlockRenderInLayerHook(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final ChunkCache worldView,
			@Nonnull final VisGraph lvt_9_1_,
			@Nonnull final HashSet<TileEntity> lvt_10_1_,
			@Nonnull final RenderGlobal renderGlobal,
			@Nonnull final boolean[] aboolean,
			@Nonnull final BlockRendererDispatcher blockrendererdispatcher,
			@Nonnull final BlockPos blockpos$mutableblockpos,
			@Nonnull final IBlockState iblockstate,
			@Nonnull final Block block,
			@Nonnull final BlockRenderLayer blockrenderlayer1) {
		final RebuildChunkBlockRenderInLayerEvent event = new RebuildChunkBlockRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, worldView, lvt_9_1_, lvt_10_1_, renderGlobal, aboolean, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, blockrenderlayer1);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockRenderInLayerEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			//TODO: keep this in sync with non-hooked behaviour
			return block.canRenderInLayer(iblockstate, blockrenderlayer1);
		} else {
			return false;
		}
	}

	/**
	 * @param renderChunk              the instance of {@link RenderChunk}
	 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
	 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
	 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
	 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
	 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
	 * @param block                    the {@link Block} the event is firing for
	 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
	 * @param j                        the ordinal of the {@link BlockRenderLayer} the event is firing for
	 * @return if the block should be rendered
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean doesBlockTypeAllowRenderHook(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final ChunkCache worldView,
			@Nonnull final VisGraph lvt_9_1_,
			@Nonnull final HashSet<TileEntity> lvt_10_1_,
			@Nonnull final RenderGlobal renderGlobal,
			@Nonnull final boolean[] aboolean,
			@Nonnull final BlockRendererDispatcher blockrendererdispatcher,
			@Nonnull final BlockPos blockpos$mutableblockpos,
			@Nonnull final IBlockState iblockstate,
			@Nonnull final Block block,
			@Nonnull final BlockRenderLayer blockrenderlayer1,
			final int j
	) {
		final RebuildChunkBlockRenderTypeAllowsRenderEvent event = new RebuildChunkBlockRenderTypeAllowsRenderEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, worldView, lvt_9_1_, lvt_10_1_, renderGlobal, aboolean, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, blockrenderlayer1, j);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockRenderInTypeEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			if (RenderChunkRebuildChunkHooksConfig.shouldTweakCanBlockRenderInType()) {
				return iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE;
			} else {
				//TODO: keep this in sync with non-hooked behaviour
				return block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE;
			}
		} else {
			return false;
		}
	}

	/**
	 * @param renderChunk              the instance of {@link RenderChunk}
	 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
	 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
	 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
	 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
	 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
	 * @param block                    the {@link Block} the event is firing for
	 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
	 * @param j                        the ordinal of the {@link BlockRenderLayer} the event is firing for
	 * @param bufferbuilder            the {@link BufferBuilder} for the {@link BlockRenderLayer} the event is firing for
	 * @return If the block should NOT be rebuilt to the chunk by vanilla
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean rebuildChunkBlockHook(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final ChunkCache worldView,
			@Nonnull final VisGraph lvt_9_1_,
			@Nonnull final HashSet<TileEntity> lvt_10_1_,
			@Nonnull final RenderGlobal renderGlobal,
			@Nonnull final boolean[] aboolean,
			@Nonnull final BlockRendererDispatcher blockrendererdispatcher,
			@Nonnull final BlockPos blockpos$mutableblockpos,
			@Nonnull final IBlockState iblockstate,
			@Nonnull final Block block,
			@Nonnull final BlockRenderLayer blockrenderlayer1,
			final int j,
			@Nonnull final BufferBuilder bufferbuilder
	) {
		final RebuildChunkBlockEvent event = new RebuildChunkBlockEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, worldView, lvt_9_1_, lvt_10_1_, renderGlobal, aboolean, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, blockrenderlayer1, j, bufferbuilder);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkBlockEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

	/**
	 * @param renderChunk   the instance of {@link RenderChunk}
	 * @param x             the translation X passed in from RenderChunk#rebuildChunk
	 * @param y             the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z             the translation Z passed in from RenderChunk#rebuildChunk
	 * @param generator     the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledchunk the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockpos      the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
	 * @param worldView     the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param lvt_9_1_      the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param lvt_10_1_     the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param renderGlobal  the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static void rebuildChunkPostHook(
			@Nonnull final RenderChunk renderChunk,
			final float x,
			final float y,
			final float z,
			@Nonnull final ChunkCompileTaskGenerator generator,
			@Nonnull final CompiledChunk compiledchunk,
			@Nonnull final BlockPos blockpos,
			@Nonnull final ChunkCache worldView,
			@Nonnull final VisGraph lvt_9_1_,
			@Nonnull final HashSet<TileEntity> lvt_10_1_,
			@Nonnull final RenderGlobal renderGlobal
	) {
		final RebuildChunkPostEvent event = new RebuildChunkPostEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, worldView, lvt_9_1_, lvt_10_1_, renderGlobal);

		if (RenderChunkRebuildChunkHooksConfig.shouldPostRebuildChunkPostEvent()) {
			MinecraftForge.EVENT_BUS.post(event);
		}
	}

}
