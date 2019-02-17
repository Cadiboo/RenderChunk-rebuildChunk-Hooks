package io.github.cadiboo.renderchunkrebuildchunkhooks;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderTask;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunkCache;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.HooksTemp.compiledchunk_setLayerUsed;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.HooksTemp.renderChunk_postRenderBlocks;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.HooksTemp.renderChunk_preRenderBlocks;

/**
 * @author Cadiboo
 */
public class HooksTempReflection {

	public static void rebuildChunk(
			final RenderChunk renderChunk,
			float x, float y, float z, ChunkRenderTask generator
	) {

		CompiledChunk compiledchunk = new CompiledChunk();
		int i = 1;
//		BlockPos blockpos = renderChunk.position.toImmutable();
		BlockPos blockpos = ((BlockPos) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178586_f")).toImmutable();
		BlockPos blockpos1 = blockpos.add(15, 15, 15);
//		World world = renderChunk.world;
		World world = ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178588_d");
		if (world != null) {
			generator.getLock().lock();

			try {
				if (generator.getStatus() != ChunkRenderTask.Status.COMPILING) {
					return;
				}

				generator.setCompiledChunk(compiledchunk);
			} finally {
				generator.getLock().unlock();
			}

			RenderChunkCache lvt_10_1_ = renderChunk.createRegionRenderCache(world, blockpos.add(-1, -1, -1), blockpos.add(16, 16, 16), 1);
//			net.minecraftforge.client.MinecraftForgeClient.onRebuildChunk(renderChunk.world, renderChunk.position, lvt_10_1_);
			net.minecraftforge.client.MinecraftForgeClient.onRebuildChunk(
					ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178588_d"),
					ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178586_f"),
					lvt_10_1_
			);
			VisGraph lvt_11_1_ = new VisGraph();
			HashSet lvt_12_1_ = Sets.newHashSet();
			if (lvt_10_1_ != null) {
				++RenderChunk.renderChunksUpdated;
				boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
				BlockModelRenderer.enableCache();
				Random random = new Random();
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

				for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos1)) {
					IBlockState iblockstate = lvt_10_1_.getBlockState(blockpos$mutableblockpos);
					Block block = iblockstate.getBlock();
					if (iblockstate.isOpaqueCube(lvt_10_1_, blockpos$mutableblockpos)) {
						lvt_11_1_.setOpaqueCube(blockpos$mutableblockpos);
					}

					if (iblockstate.hasTileEntity()) {
						TileEntity tileentity = lvt_10_1_.func_212399_a(blockpos$mutableblockpos, Chunk.EnumCreateEntityType.CHECK);
						if (tileentity != null) {
							TileEntityRenderer<TileEntity> tileentityrenderer = TileEntityRendererDispatcher.instance.getRenderer(tileentity);
							if (tileentityrenderer != null) {
								if (tileentityrenderer.isGlobalRenderer(tileentity)) {
									lvt_12_1_.add(tileentity);
								} else compiledchunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
							}
						}
					}

					IFluidState ifluidstate = lvt_10_1_.getFluidState(blockpos$mutableblockpos);
					for (BlockRenderLayer blockrenderlayer1 : BlockRenderLayer.values()) {
						net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
						if (!ifluidstate.isEmpty() && ifluidstate.canRenderInLayer(blockrenderlayer1)) {
							int j = blockrenderlayer1.ordinal();
							BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getBuilder(j);
							if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
								compiledchunk.setLayerStarted(blockrenderlayer1);
//								renderChunk.preRenderBlocks(bufferbuilder, blockpos);
								renderChunk_preRenderBlocks(renderChunk, bufferbuilder, blockpos);
							}

							aboolean[j] |= blockrendererdispatcher.renderFluid(blockpos$mutableblockpos, lvt_10_1_, bufferbuilder, ifluidstate);
						}

						if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && iblockstate.canRenderInLayer(blockrenderlayer1)) {
							int k = blockrenderlayer1.ordinal();
							BufferBuilder bufferbuilder1 = generator.getRegionRenderCacheBuilder().getBuilder(k);
							if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
								compiledchunk.setLayerStarted(blockrenderlayer1);
//								renderChunk.preRenderBlocks(bufferbuilder1, blockpos);
								renderChunk_preRenderBlocks(renderChunk, bufferbuilder1, blockpos);
							}

							aboolean[k] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, lvt_10_1_, bufferbuilder1, random);
						}
					}
					net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
				}

				for (BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
					if (aboolean[blockrenderlayer.ordinal()]) {
//						compiledchunk.setLayerUsed(blockrenderlayer);
						compiledchunk_setLayerUsed(renderChunk, blockrenderlayer);
					}

					if (compiledchunk.isLayerStarted(blockrenderlayer)) {
//						renderChunk.postRenderBlocks(blockrenderlayer, x, y, z, generator.getRegionRenderCacheBuilder().getBuilder(blockrenderlayer), compiledchunk);
						renderChunk_postRenderBlocks(renderChunk, blockrenderlayer, x, y, z, generator.getRegionRenderCacheBuilder().getBuilder(blockrenderlayer), compiledchunk);
					}
				}

				BlockModelRenderer.disableCache();
			}

			compiledchunk.setVisibility(lvt_11_1_.computeVisibility());
//			renderChunk.lockCompileTask.lock();
			((ReentrantLock) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178587_g")).lock();

			try {
				Set<TileEntity> set = Sets.newHashSet(lvt_12_1_);
//				Set<TileEntity> set1 = Sets.newHashSet(renderChunk.setTileEntities);
				Set<TileEntity> set1 = ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_181056_j");
//				set.removeAll(renderChunk.setTileEntities);
				set.removeAll(ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_181056_j"));
				set1.removeAll(lvt_12_1_);
//				renderChunk.setTileEntities.clear();
				((Set) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_181056_j")).clear();
//				renderChunk.setTileEntities.addAll(lvt_12_1_);
				((Set) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_181056_j")).addAll(lvt_12_1_);
//				renderChunk.renderGlobal.updateTileEntities(set1, set);
				((WorldRenderer) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178589_e")).updateTileEntities(set1, set);
			} finally {
//				renderChunk.lockCompileTask.unlock();
				((ReentrantLock) ObfuscationReflectionHelper.getPrivateValue(RenderChunk.class, renderChunk, "field_178587_g")).unlock();
			}

		}

	}

}
