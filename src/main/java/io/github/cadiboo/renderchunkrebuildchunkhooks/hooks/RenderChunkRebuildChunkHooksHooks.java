package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooks {

	//return if rendering _should happen_
	public static boolean rebuildChunkCanRenderingHappenHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_) {
		return true;
	}

	public static void rebuildChunkPreRenderHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
	}

	//return if the fluid _can_ render in the layer
	public static boolean rebuildChunkCanFluidRenderInLayerHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1) {
		return true;
	}

	//return if the fluid _should be rendered by vanilla_
	public static boolean rebuildChunkCanFluidBeRenderedHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IFluidState ifluidstate, final BlockRenderLayer blockrenderlayer1, final int j, final BufferBuilder bufferbuilder) {
		return true;
	}

	//return if the block _can_ render in the type (iblockstate.getRenderType()) and layer
	public static boolean rebuildChunkCanBlockRenderInTypeAndLayerHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final BlockRenderLayer blockrenderlayer1) {
		return true;
	}

	//return if the block _should be rendered by vanilla_
	public static boolean rebuildChunkCanBlockBeRenderedHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher, final IBlockState iblockstate, final Block block, final BlockRenderLayer blockrenderlayer1, final int k, final BufferBuilder bufferbuilder1) {
		return true;
	}

	public static void rebuildChunkPostRenderHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world, final RenderChunkCache lvt_10_1_, final VisGraph lvt_11_1_, final HashSet lvt_12_1_, final boolean[] aboolean, final Random random, final BlockRendererDispatcher blockrendererdispatcher) {
	}

	public static void rebuildChunkPostHook(final RenderChunk renderChunk, final float x, final float y, final float z, final ChunkRenderTask generator, final CompiledChunk compiledchunk, final BlockPos blockpos, final BlockPos blockpos1, final World world) {
	}

}
