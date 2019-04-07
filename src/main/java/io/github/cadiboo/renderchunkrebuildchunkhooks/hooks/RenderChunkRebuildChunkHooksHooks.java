package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderWithTypeEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanFluidRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkFluidEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreRenderEvent;
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
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;
import java.util.Random;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkRenderBlockEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkCanBlockRenderInLayerEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkCanBlockRenderWithTypeEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkCanFluidRenderInLayerEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkRenderFluidEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPostEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPostRenderEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPreEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooks.HookConfig.shouldPostRebuildChunkPreRenderEvent;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooks {

	//return if rendering should _not_ happen
	public static boolean rebuildChunkCancelRenderingPreGeneratingCompiledChunkHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final WorldReference worldReference) {
		if (!shouldPostRebuildChunkPreEvent()) {
			return false;
		}
		final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, worldReference);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isCanceled();
	}

	public static void rebuildChunkPreRenderHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (!shouldPostRebuildChunkPreRenderEvent()) {
			return;
		}
		final RebuildChunkPreRenderEvent event = new RebuildChunkPreRenderEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
		MinecraftForge.EVENT_BUS.post(event);
	}

	//return if the fluid _can_ render in the layer
	public static boolean rebuildChunkCanFluidRenderInLayerHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final BlockPos blockpos$mutableblockpos, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		if (!shouldPostRebuildChunkCanFluidRenderInLayerEvent()) {
			//TODO: keep in sync with un-hooked behaviour
			return ifluidstate.canRenderInLayer(blockrenderlayer1);
		}
		final RebuildChunkCanFluidRenderInLayerEvent event = new RebuildChunkCanFluidRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, blockpos$mutableblockpos, ifluidstate, blockrenderlayer1);
		MinecraftForge.EVENT_BUS.post(event);
		switch (event.getResult()) {
			case DENY:
				return false;
			default:
			case DEFAULT:
				//TODO: keep in sync with un-hooked behaviour
				return ifluidstate.canRenderInLayer(blockrenderlayer1);
			case ALLOW:
				return true;
		}
	}

	//return if the fluid _should be rendered by vanilla_
	public static boolean rebuildChunkCanFluidBeRenderedHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final BlockPos blockpos$mutableblockpos, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		if (!shouldPostRebuildChunkRenderFluidEvent()) {
			return true;
		}
		final RebuildChunkFluidEvent event = new RebuildChunkFluidEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, blockpos$mutableblockpos, ifluidstate, blockrenderlayer1, j, bufferbuilder);
		MinecraftForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	//return if the block _can_ render in the type (iblockstate.getRenderType()) and layer
	public static boolean rebuildChunkCanBlockRenderWithTypeAndInLayerHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final BlockPos blockpos$mutableblockpos, final IBlockState iblockstate, final Block block, final BlockRenderLayer blockrenderlayer1) {
		boolean canRenderInType = false;
		if (!shouldPostRebuildChunkCanBlockRenderWithTypeEvent()) {
			//TODO: keep in sync with un-hooked behaviour
			canRenderInType = iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE;
		} else {
			final EnumBlockRenderType renderType = iblockstate.getRenderType();
			final RebuildChunkCanBlockRenderWithTypeEvent event = new RebuildChunkCanBlockRenderWithTypeEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, renderType);
			MinecraftForge.EVENT_BUS.post(event);
			switch (event.getResult()) {
				case DENY:
					return false;
				default:
				case DEFAULT:
					//TODO: keep in sync with un-hooked behaviour
					canRenderInType = renderType != EnumBlockRenderType.INVISIBLE;
					break;
				case ALLOW:
					canRenderInType = true;
					break;
			}
		}

		boolean canRenderInLayer = false;
		if (!shouldPostRebuildChunkCanBlockRenderInLayerEvent()) {
			//TODO: keep in sync with un-hooked behaviour
			canRenderInLayer = iblockstate.canRenderInLayer(blockrenderlayer1);
		} else {
			final RebuildChunkCanBlockRenderInLayerEvent event = new RebuildChunkCanBlockRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, blockrenderlayer1);
			MinecraftForge.EVENT_BUS.post(event);
			switch (event.getResult()) {
				case DENY:
					return false;
				default:
				case DEFAULT:
					//TODO: keep in sync with un-hooked behaviour
					canRenderInLayer = iblockstate.canRenderInLayer(blockrenderlayer1);
					break;
				case ALLOW:
					canRenderInLayer = true;
					break;
			}
		}

		return canRenderInType && canRenderInLayer;
	}

	//return if the block _should be rendered by vanilla_
	public static boolean rebuildChunkCanBlockBeRenderedHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final BlockPos blockpos$mutableblockpos, final IBlockState iblockstate, final Block block, final BlockRenderLayer blockrenderlayer1, final int k, final BufferBuilder bufferbuilder1) {
		if (!shouldPostRebuildChunkRenderBlockEvent()) {
			return true;
		}
		final RebuildChunkBlockEvent event = new RebuildChunkBlockEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, blockpos$mutableblockpos, iblockstate, block, blockrenderlayer1, k, bufferbuilder1);
		MinecraftForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	public static void rebuildChunkPostRenderHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (!shouldPostRebuildChunkPostRenderEvent()) {
			return;
		}
		final RebuildChunkPostRenderEvent event = new RebuildChunkPostRenderEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void rebuildChunkPostHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world) {
		if (!shouldPostRebuildChunkPostEvent()) {
			return;
		}
		final RebuildChunkPostEvent event = new RebuildChunkPostEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world);
		MinecraftForge.EVENT_BUS.post(event);
	}

}
