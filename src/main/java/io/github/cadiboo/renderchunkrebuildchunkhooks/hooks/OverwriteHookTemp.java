package io.github.cadiboo.renderchunkrebuildchunkhooks.hooks;

import com.google.common.annotations.Beta;
import com.google.common.collect.Sets;
import io.github.cadiboo.renderchunkrebuildchunkhooks.util.RenderChunkCacheReference;
import io.github.cadiboo.renderchunkrebuildchunkhooks.util.WorldReference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static net.minecraft.client.renderer.chunk.RenderChunk.renderChunksUpdated;

/**
 * @author Cadiboo
 */
@Beta
@Deprecated
public final class OverwriteHookTemp {

	public void rebuildChunk(final RenderChunk renderChunk, float x, float y, float z, ChunkRenderTask generator) {
		// START HOOK
		if (Hooks.pre(renderChunk, x, y, z, generator)) {
			return;
		}
		// END HOOK
		CompiledChunk compiledchunk = new CompiledChunk();
		int i = 1;
		BlockPos blockpos = renderChunk.position.toImmutable();
		BlockPos blockpos1 = blockpos.add(15, 15, 15);
		World world = renderChunk.world;
		// START HOOK
		final WorldReference worldRef = new WorldReference(world);
		if (Hooks.checkWorld(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, worldRef)) {
			return;
		}
		world = worldRef.get();
		// END HOOK
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
			MinecraftForgeClient.onRebuildChunk(renderChunk.world, renderChunk.position, lvt_10_1_);
			VisGraph lvt_11_1_ = new VisGraph();
			HashSet lvt_12_1_ = Sets.newHashSet();
			// START HOOK
			final RenderChunkCacheReference cacheRef = new RenderChunkCacheReference(lvt_10_1_);
			if (Hooks.checkCache(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, cacheRef)) {
				return;
			}
			lvt_10_1_ = cacheRef.get();
			// END HOOK
			if (lvt_10_1_ != null) {
				++renderChunksUpdated;
				boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
				BlockModelRenderer.enableCache();
				Random random = new Random();
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

				// START HOOK
				if (Hooks.preIteration(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher)) {
					return;
				}
				// END HOOK
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
						ForgeHooksClient.setRenderLayer(blockrenderlayer1);
						// HOOKED IF
						if (Hooks.canFluidRender(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, (!ifluidstate.isEmpty() && ifluidstate.canRenderInLayer(blockrenderlayer1)))) {
							int j = blockrenderlayer1.ordinal();
							BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getBuilder(j);
							if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
								compiledchunk.setLayerStarted(blockrenderlayer1);
								renderChunk.preRenderBlocks(bufferbuilder, blockpos);
							}

							// START HOOK
							if (Hooks.preRenderFluid(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, j, bufferbuilder)) {
								continue;
							}
							// END HOOK
							aboolean[j] |= blockrendererdispatcher.renderFluid(blockpos$mutableblockpos, lvt_10_1_, bufferbuilder, ifluidstate);
						}

						// HOOKED IF
						if (Hooks.canBlockRender(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE && iblockstate.canRenderInLayer(blockrenderlayer1)))) {
							int k = blockrenderlayer1.ordinal();
							BufferBuilder bufferbuilder1 = generator.getRegionRenderCacheBuilder().getBuilder(k);
							if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
								compiledchunk.setLayerStarted(blockrenderlayer1);
								renderChunk.preRenderBlocks(bufferbuilder1, blockpos);
							}

							// START HOOK
							if (Hooks.preRenderBlock(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, k, bufferbuilder1)) {
								continue;
							}
							// END HOOK
							aboolean[k] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, lvt_10_1_, bufferbuilder1, random);
						}
					}
					ForgeHooksClient.setRenderLayer(null);
				}
				// START HOOK
				Hooks.postIteration(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
				// END HOOK

				for (BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
					if (aboolean[blockrenderlayer.ordinal()]) {
						compiledchunk.setLayerUsed(blockrenderlayer);
					}

					if (compiledchunk.isLayerStarted(blockrenderlayer)) {
						renderChunk.postRenderBlocks(blockrenderlayer, x, y, z, generator.getRegionRenderCacheBuilder().getBuilder(blockrenderlayer), compiledchunk);
					}
				}

				BlockModelRenderer.disableCache();
			}
			// START HOOK
			Hooks.postRender(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_);
			// END HOOK

			compiledchunk.setVisibility(lvt_11_1_.computeVisibility());
			renderChunk.lockCompileTask.lock();

			try {
				Set<TileEntity> set = Sets.newHashSet(lvt_12_1_);
				Set<TileEntity> set1 = Sets.newHashSet(renderChunk.setTileEntities);
				set.removeAll(renderChunk.setTileEntities);
				set1.removeAll(lvt_12_1_);
				renderChunk.setTileEntities.clear();
				renderChunk.setTileEntities.addAll(lvt_12_1_);
				renderChunk.renderGlobal.updateTileEntities(set1, set);
			} finally {
				renderChunk.lockCompileTask.unlock();
			}

			// START HOOK
			Hooks.post(renderChunk, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_);
			// END HOOK
		}
	}

}
