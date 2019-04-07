package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.util.WorldReference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPostEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPostRenderEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkRenderBlockEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkCanFluidRenderInLayerEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkRenderFluidEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPreEvent;

/**
 * @author Cadiboo
 * @deprecated Modders should not touch this, the only thing that should touch this is the injected hooks
 */
@Deprecated
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Hooks {

	//return if rebuildChunk should return early
	public static boolean pre(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator) {
		if (shouldPostRebuildChunkPreEvent()) {
			return event;
		} else {
			return false;
		}
	}

	//return if rebuildChunk should return early
	public static boolean checkWorld(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final WorldReference worldRef) {
		if (shouldPostRebuildChunkCheckWorldEvent()) {
			return event;
		} else {
			return false;
		}
	}

	//return if rebuildChunk should return early
	public static boolean preIteration(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (shouldPostRebuildChunkPreIterationEvent()) {
			return event;
		} else {
			return false;
		}
	}

	//return if fluid can render
	public static boolean canFluidRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		if (shouldPostRebuildChunkCanFluidRenderInLayerEvent()) {
			if (shouldPostRebuildChunkIsFluidEmptyEvent()) {
				return fluidstateisemptyevent && fluidcanRenderInLayerevent;
			} else {
				return !ifluidstate.isEmpty() && fluidcanRenderInLayerevent;
			}
		} else {
			if (shouldPostRebuildChunkIsFluidEmptyEvent()) {
				return fluidstateisemptyevent && ifluidstate.canRenderInLayer(blockrenderlayer1);
			} else {
				return !ifluidstate.isEmpty() && ifluidstate.canRenderInLayer(blockrenderlayer1);
			}
		}
	}

	//return if fluid rendering should be cancelled
	public static boolean preRenderFluid(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		if (shouldPostRebuildChunkRenderFluidEvent()) {
			return event;
		} else {
			return false;
		}
	}

	//return if block can render
	public static boolean canBlockRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		if (shouldPostRebuildChunkCanFluidRenderInLayerEvent()) {
			if (shouldPostRebuildChunkCanBlockRenderTypeBeRenderedEvent()) {
				return blockstaterendertypeevent && fluidcanRenderInLayerevent;
			} else {
				return iblockstate.getRenderType()!= EnumBlockRenderType.INVISIBLE && fluidcanRenderInLayerevent;
			}
		} else {
			if (shouldPostRebuildChunkCanBlockRenderTypeBeRenderedEvent()) {
				return blockstaterendertypeevent && iblockstate.canRenderInLayer(blockrenderlayer1);
			} else {
				return iblockstate.getRenderType()!= EnumBlockRenderType.INVISIBLE && iblockstate.canRenderInLayer(blockrenderlayer1);
			}
		}
	}

	//return if block rendering should be cancelled
	public static boolean preRenderBlock(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		if (shouldPostRebuildChunkRenderBlockEvent()) {
			return event;
		} else {
			return false;
		}
	}

	public static void postIteration(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (shouldPostRebuildChunkPostIterationEvent()) {
			event;
		}
	}

	public static void postRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_) {
		if (shouldPostRebuildChunkPostRenderEvent()) {
			event;
		}
	}

	public static void post(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world) {
		if (shouldPostRebuildChunkPostEvent()) {
			event;
		}
	}

}
