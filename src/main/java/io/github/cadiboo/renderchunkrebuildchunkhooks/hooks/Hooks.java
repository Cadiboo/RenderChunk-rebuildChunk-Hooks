package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import com.google.common.annotations.Beta;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.PooledHandler;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderTypeBeRenderedEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanFluidRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCheckWorldEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkIsFluidEmptyEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderFluidEvent;
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

import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkCanBlockRenderInLayerEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkCanBlockRenderTypeBeRenderedEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkCanFluidRenderInLayerEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkCheckWorldEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkIsFluidEmptyEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPostEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPostIterationEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPostRenderEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPreEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPreIterationEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkPreRenderEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkRenderBlockEvent;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig.postRebuildChunkRenderFluidEvent;
import static net.minecraftforge.eventbus.api.Event.Result.DENY;

/**
 * @author Cadiboo
 * @see "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/blob/master/diffs/RenderChunk.class.diff"
 * @deprecated Modders should not touch this, the only thing that should touch this is the injected hooks
 */
@Beta
@Deprecated
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Hooks {

	//return if rebuildChunk should return early
	public static boolean pre(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator) {
		if (postRebuildChunkPreEvent) {
			final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderChunk, x, y, z, generator);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	//return if rebuildChunk should return early
	public static boolean checkWorld(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final WorldReference worldRef) {
		if (postRebuildChunkCheckWorldEvent) {
			final RebuildChunkCheckWorldEvent event = new RebuildChunkCheckWorldEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, worldRef);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	//return if rebuildChunk should return early
	public static boolean preRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_) {
		if (postRebuildChunkPreRenderEvent) {
			final RebuildChunkPreRenderEvent event = new RebuildChunkPreRenderEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	//return if rebuildChunk should return early
	public static boolean preIteration(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (postRebuildChunkPreIterationEvent) {
			final RebuildChunkPreIterationEvent event = new RebuildChunkPreIterationEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	//return if fluid can render
	public static boolean canFluidRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		if (postRebuildChunkIsFluidEmptyEvent) {
			if (postRebuildChunkCanFluidRenderInLayerEvent) {
				final RebuildChunkIsFluidEmptyEvent isFluidEmptyEvent = PooledHandler.setupRebuildChunkIsFluidEmptyEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate);
				MinecraftForge.EVENT_BUS.post(isFluidEmptyEvent);
				PooledHandler.tearDown(isFluidEmptyEvent);
				final RebuildChunkCanFluidRenderInLayerEvent canFluidRenderInLayerEvent = PooledHandler.setupRebuildChunkCanFluidRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1);
				MinecraftForge.EVENT_BUS.post(canFluidRenderInLayerEvent);
				PooledHandler.tearDown(canFluidRenderInLayerEvent);
				return isFluidEmptyEvent.getResult() != DENY && canFluidRenderInLayerEvent.getResult() != DENY;
			} else {
				final RebuildChunkIsFluidEmptyEvent isFluidEmptyEvent = PooledHandler.setupRebuildChunkIsFluidEmptyEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate);
				MinecraftForge.EVENT_BUS.post(isFluidEmptyEvent);
				PooledHandler.tearDown(isFluidEmptyEvent);
				return isFluidEmptyEvent.getResult() != DENY && ifluidstate.canRenderInLayer(blockrenderlayer1);
			}
		} else {
			if (postRebuildChunkCanFluidRenderInLayerEvent) {
				final RebuildChunkCanFluidRenderInLayerEvent canFluidRenderInLayerEvent = PooledHandler.setupRebuildChunkCanFluidRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1);
				MinecraftForge.EVENT_BUS.post(canFluidRenderInLayerEvent);
				PooledHandler.tearDown(canFluidRenderInLayerEvent);
				return !ifluidstate.isEmpty() && canFluidRenderInLayerEvent.getResult() != DENY;
			} else {
				return !ifluidstate.isEmpty() && ifluidstate.canRenderInLayer(blockrenderlayer1);
			}
		}
	}

	//return if fluid rendering should be cancelled
	public static boolean preRenderFluid(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		if (postRebuildChunkRenderFluidEvent) {
			final RebuildChunkRenderFluidEvent event = PooledHandler.setupRebuildChunkRenderFluidEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, j, bufferbuilder);
			MinecraftForge.EVENT_BUS.post(event);
			PooledHandler.tearDown(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	//return if block can render
	public static boolean canBlockRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		if (postRebuildChunkCanBlockRenderTypeBeRenderedEvent) {
			if (postRebuildChunkCanBlockRenderInLayerEvent) {
				final RebuildChunkCanBlockRenderTypeBeRenderedEvent canBlockRenderTypeBeRenderedEvent = PooledHandler.setupRebuildChunkCanBlockRenderTypeBeRenderedEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate);
				MinecraftForge.EVENT_BUS.post(canBlockRenderTypeBeRenderedEvent);
				PooledHandler.tearDown(canBlockRenderTypeBeRenderedEvent);
				final RebuildChunkCanBlockRenderInLayerEvent canBlockRenderInLayerEvent = PooledHandler.setupRebuildChunkCanBlockRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1);
				MinecraftForge.EVENT_BUS.post(canBlockRenderInLayerEvent);
				PooledHandler.tearDown(canBlockRenderInLayerEvent);
				return canBlockRenderTypeBeRenderedEvent.getResult() != DENY && canBlockRenderInLayerEvent.getResult() != DENY;
			} else {
				final RebuildChunkCanBlockRenderTypeBeRenderedEvent canBlockRenderTypeBeRenderedEvent = PooledHandler.setupRebuildChunkCanBlockRenderTypeBeRenderedEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate);
				MinecraftForge.EVENT_BUS.post(canBlockRenderTypeBeRenderedEvent);
				PooledHandler.tearDown(canBlockRenderTypeBeRenderedEvent);
				return canBlockRenderTypeBeRenderedEvent.getResult() != DENY && iblockstate.canRenderInLayer(blockrenderlayer1);
			}
		} else {
			if (postRebuildChunkCanBlockRenderInLayerEvent) {
				final RebuildChunkCanBlockRenderInLayerEvent canBlockRenderInLayerEvent = PooledHandler.setupRebuildChunkCanBlockRenderInLayerEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1);
				MinecraftForge.EVENT_BUS.post(canBlockRenderInLayerEvent);
				PooledHandler.tearDown(canBlockRenderInLayerEvent);
				return iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && canBlockRenderInLayerEvent.getResult() != DENY;
			} else {
				return iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && iblockstate.canRenderInLayer(blockrenderlayer1);
			}
		}
	}

	//return if block rendering should be cancelled
	public static boolean preRenderBlock(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int k, final BufferBuilder bufferbuilder1) {
		if (postRebuildChunkRenderBlockEvent) {
			final RebuildChunkRenderBlockEvent event = PooledHandler.setupRebuildChunkRenderBlockEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, k, bufferbuilder1);
			MinecraftForge.EVENT_BUS.post(event);
			PooledHandler.tearDown(event);
			return event.getResult() != DENY;
		} else {
			return false;
		}
	}

	public static void postIteration(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
		if (postRebuildChunkPostIterationEvent) {
			final RebuildChunkPostIterationEvent event = new RebuildChunkPostIterationEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
			MinecraftForge.EVENT_BUS.post(event);
		}
	}

	public static void postRender(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_) {
		if (postRebuildChunkPostRenderEvent) {
			final RebuildChunkPostRenderEvent event = new RebuildChunkPostRenderEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_);
			MinecraftForge.EVENT_BUS.post(event);
		}
	}

	public static void post(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world) {
		if (postRebuildChunkPostEvent) {
			final RebuildChunkPostEvent event = new RebuildChunkPostEvent(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world);
			MinecraftForge.EVENT_BUS.post(event);
		}
	}

}
