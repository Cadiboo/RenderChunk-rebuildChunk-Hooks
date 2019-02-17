package io.github.cadiboo.renderchunkrebuildchunkhooks;

import com.google.common.collect.Sets;
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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cadiboo
 */
public class HooksTemp {

	@SuppressWarnings("WeakerAccess")
	public static final MethodHandle renderChunk_preRenderBlocks;
	@SuppressWarnings("WeakerAccess")
	public static final MethodHandle compiledchunk_setLayerUsed;
	@SuppressWarnings("WeakerAccess")
	public static final MethodHandle renderChunk_postRenderBlocks;

	static {
		try {
			renderChunk_preRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178573_a",
					BufferBuilder.class, BlockPos.class
			));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		try {
			compiledchunk_setLayerUsed = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178486_a",
					BlockRenderLayer.class
			));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		try {
			renderChunk_postRenderBlocks = MethodHandles.publicLookup().unreflect(ObfuscationReflectionHelper.findMethod(RenderChunk.class, "func_178584_a",
					BlockRenderLayer.class, float.class, float.class, float.class, BufferBuilder.class, CompiledChunk.class
			));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void rebuildChunk(
			final RenderChunk renderChunk,
			float x, float y, float z, ChunkRenderTask generator
	) {

		CompiledChunk compiledchunk = new CompiledChunk();
		int i = 1;
		BlockPos blockpos = renderChunk.position.toImmutable();
		BlockPos blockpos1 = blockpos.add(15, 15, 15);
		World world = renderChunk.world;
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
			net.minecraftforge.client.MinecraftForgeClient.onRebuildChunk(renderChunk.world, renderChunk.position, lvt_10_1_);
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

		}

	}

	@SuppressWarnings("WeakerAccess")
	public static void renderChunk_preRenderBlocks(final RenderChunk renderChunk, final BufferBuilder bufferbuilder, final BlockPos blockpos) {
		try {
			renderChunk_preRenderBlocks.invokeExact(renderChunk, bufferbuilder, blockpos);
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static void compiledchunk_setLayerUsed(final RenderChunk renderChunk, final BlockRenderLayer blockRenderLayer) {
		try {
			compiledchunk_setLayerUsed.invokeExact(renderChunk, blockRenderLayer);
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public static void renderChunk_postRenderBlocks(final RenderChunk renderChunk, final BlockRenderLayer blockrenderlayer, final float x, final float y, final float z, final BufferBuilder builder, final CompiledChunk compiledchunk) {
		try {
			renderChunk_postRenderBlocks.invokeExact(renderChunk, blockrenderlayer, x, y, z, builder, compiledchunk);
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

}
