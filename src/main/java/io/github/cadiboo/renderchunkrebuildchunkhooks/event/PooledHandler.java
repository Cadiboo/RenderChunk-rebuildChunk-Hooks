package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import com.google.common.annotations.Beta;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Random;

/**
 * INTERNAL
 *
 * @author Cadiboo
 */
@Beta
@Deprecated
public final class PooledHandler {

	private static final Field Event_phase;
	private static final ThreadLocal<RebuildChunkIsFluidEmptyEvent> threadRebuildChunkIsFluidEmptyEvent = ThreadLocal.withInitial(() -> new RebuildChunkIsFluidEmptyEvent(false));
	private static final ThreadLocal<RebuildChunkCanFluidRenderInLayerEvent> threadRebuildChunkCanFluidRenderInLayerEvent = ThreadLocal.withInitial(() -> new RebuildChunkCanFluidRenderInLayerEvent(false));
	private static final ThreadLocal<RebuildChunkRenderFluidEvent> threadRebuildChunkRenderFluidEvent = ThreadLocal.withInitial(() -> new RebuildChunkRenderFluidEvent(false));
	private static final ThreadLocal<RebuildChunkCanBlockRenderTypeBeRenderedEvent> threadRebuildChunkCanBlockRenderTypeBeRenderedEvent = ThreadLocal.withInitial(() -> new RebuildChunkCanBlockRenderTypeBeRenderedEvent(false));
	private static final ThreadLocal<RebuildChunkCanBlockRenderInLayerEvent> threadRebuildChunkCanBlockRenderInLayerEvent = ThreadLocal.withInitial(() -> new RebuildChunkCanBlockRenderInLayerEvent(false));
	private static final ThreadLocal<RebuildChunkRenderBlockEvent> threadRebuildChunkRenderBlockEvent = ThreadLocal.withInitial(() -> new RebuildChunkRenderBlockEvent(false));
	static {
		try {
			Field f = Event.class.getDeclaredField("phase");
			f.setAccessible(true);
			Event_phase = f;
		} catch (Exception e) {
			throw new ReportedException(
					CrashReport.makeCrashReport(e, "Failed to find field Event.phase")
			);
		}
	}

	private static void reset(final Event event) {
		try {
			Event_phase.set(event, null);
		} catch (IllegalAccessException e) {
			throw new ReportedException(
					CrashReport.makeCrashReport(e, "Failed to set field Event.phase")
			);
		}
		if (event.isCancelable()) {
			event.setCanceled(false);
		}
		if (event.hasResult()) {
			event.setResult(Event.Result.DEFAULT);
		}
	}

	public static RebuildChunkIsFluidEmptyEvent setupRebuildChunkIsFluidEmptyEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate) {
		final RebuildChunkIsFluidEmptyEvent event = threadRebuildChunkIsFluidEmptyEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		return event;
	}

	public static void tearDown(final RebuildChunkIsFluidEmptyEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
	}

	public static RebuildChunkCanFluidRenderInLayerEvent setupRebuildChunkCanFluidRenderInLayerEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		final RebuildChunkCanFluidRenderInLayerEvent event = threadRebuildChunkCanFluidRenderInLayerEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		event.blockRenderLayer = blockrenderlayer1;
		return event;
	}

	public static void tearDown(final RebuildChunkCanFluidRenderInLayerEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
		event.blockRenderLayer = null;
	}

	public static RebuildChunkRenderFluidEvent setupRebuildChunkRenderFluidEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		final RebuildChunkRenderFluidEvent event = threadRebuildChunkRenderFluidEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		event.blockRenderLayer = blockrenderlayer1;
		event.blockRenderLayerOrdinal = j;
		event.bufferBuilder = bufferbuilder;
		return event;
	}

	public static void tearDown(final RebuildChunkRenderFluidEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
		event.blockRenderLayer = null;
		event.bufferBuilder = null;
	}

	public static RebuildChunkCanBlockRenderTypeBeRenderedEvent setupRebuildChunkCanBlockRenderTypeBeRenderedEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate) {
		final RebuildChunkCanBlockRenderTypeBeRenderedEvent event = threadRebuildChunkCanBlockRenderTypeBeRenderedEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		return event;
	}

	public static void tearDown(final RebuildChunkCanBlockRenderTypeBeRenderedEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
	}

	public static RebuildChunkCanBlockRenderInLayerEvent setupRebuildChunkCanBlockRenderInLayerEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		final RebuildChunkCanBlockRenderInLayerEvent event = threadRebuildChunkCanBlockRenderInLayerEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		event.blockRenderLayer = blockrenderlayer1;
		return event;
	}

	public static void tearDown(final RebuildChunkCanBlockRenderInLayerEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
		event.blockRenderLayer = null;
	}

	public static RebuildChunkRenderBlockEvent setupRebuildChunkRenderBlockEvent(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int k, final BufferBuilder bufferbuilder1) {
		final RebuildChunkRenderBlockEvent event = threadRebuildChunkRenderBlockEvent.get();
		reset(event);
		event.renderChunk = renderChunk;
		event.x = x;
		event.y = y;
		event.z = z;
		event.generator = generator;
		event.compiledChunk = compiledchunk;
		event.startPosition = blockpos;
		event.endPosition = blockpos1;
		event.world = world;
		event.renderChunkCache = lvt_10_1_;
		event.visGraph = lvt_11_1_;
		event.tileEntitiesWithGlobalRenderers = lvt_12_1_;
		event.usedBlockRenderLayers = aboolean;
		event.random = random;
		event.blockRendererDispatcher = blockrendererdispatcher;
		event.iBlockState = iblockstate;
		event.block = block;
		event.iFluidState = ifluidstate;
		event.blockRenderLayer = blockrenderlayer1;
		event.blockRenderLayerOrdinal = k;
		event.bufferBuilder = bufferbuilder1;
		return event;
	}

	public static void tearDown(final RebuildChunkRenderBlockEvent event) {
		event.renderChunk = null;
		event.generator = null;
		event.compiledChunk = null;
		event.startPosition = null;
		event.endPosition = null;
		event.world = null;
		event.renderChunkCache = null;
		event.visGraph = null;
		event.tileEntitiesWithGlobalRenderers = null;
		event.usedBlockRenderLayers = null;
		event.random = null;
		event.blockRendererDispatcher = null;
		event.iBlockState = null;
		event.block = null;
		event.iFluidState = null;
		event.blockRenderLayer = null;
		event.bufferBuilder = null;
	}

}
