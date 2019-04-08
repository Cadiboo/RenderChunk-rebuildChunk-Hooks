package io.github.cadiboo.renderchunkrebuildchunkhooks.nocubes;

import io.github.cadiboo.renderchunkrebuildchunkhooks.nocubes.pooled.cache.DensityCache;
import io.github.cadiboo.renderchunkrebuildchunkhooks.nocubes.pooled.cache.SmoothableCache;
import io.github.cadiboo.renderchunkrebuildchunkhooks.nocubes.pooled.cache.StateCache;
import net.minecraft.block.BlockSnowLayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

import static net.minecraft.init.Blocks.BEDROCK;
import static net.minecraft.init.Blocks.SNOW;

/**
 * @author Cadiboo
 */
//TODO: javadocs
public final class CacheUtil {

	public static StateCache generateStateCache(
			final int startPosX, final int startPosY, final int startPosZ,
			final int cacheSizeX, final int cacheSizeY, final int cacheSizeZ,
			@Nonnull final IBlockReader cache,
			@Nonnull PooledMutableBlockPos pooledMutableBlockPos
	) {
//		try (ModProfiler ignored = NoCubes.getProfiler().start("generate stateCache"))
		{
			final StateCache stateCache = StateCache.retain(cacheSizeX, cacheSizeY, cacheSizeZ);
			final IBlockState[] blockCacheArray = stateCache.getBlockStateCache();
			final IFluidState[] fluidCacheArray = stateCache.getFluidStateCache();

			int index = 0;
			for (int z = 0; z < cacheSizeZ; ++z) {
				for (int y = 0; y < cacheSizeY; ++y) {
					for (int x = 0; x < cacheSizeX; ++x, ++index) {
						pooledMutableBlockPos.setPos(startPosX + x, startPosY + y, startPosZ + z);
						blockCacheArray[index] = cache.getBlockState(pooledMutableBlockPos);
						fluidCacheArray[index] = cache.getFluidState(pooledMutableBlockPos);
					}
				}
			}

			return stateCache;
		}
	}

	public static SmoothableCache generateSmoothableCache(
			@Nonnull final StateCache stateCache
//			,
//			@Nonnull final IIsSmoothable isStateSmoothable
	) {
//		try (ModProfiler ignored = NoCubes.getProfiler().start("generate smoothableCache"))
		{
			final int cacheSizeX = stateCache.sizeX;
			final int cacheSizeY = stateCache.sizeY;
			final int cacheSizeZ = stateCache.sizeZ;

			final SmoothableCache smoothableCache = SmoothableCache.retain(cacheSizeX, cacheSizeY, cacheSizeZ);
			final boolean[] smoothableCacheArray = smoothableCache.getSmoothableCache();

			final IBlockState[] stateCacheArray = stateCache.getBlockStateCache();

			int index = 0;
			for (int z = 0; z < cacheSizeZ; ++z) {
				for (int y = 0; y < cacheSizeY; ++y) {
					for (int x = 0; x < cacheSizeX; ++x, ++index) {
						smoothableCacheArray[index] = NoCubesTest.isSmoothable(stateCacheArray[index]);
					}
				}
			}

			return smoothableCache;
		}
	}

	public static DensityCache generateDensityCache(
			final int startPosX, final int startPosY, final int startPosZ,
			@Nonnull final StateCache stateCache,
			@Nonnull final SmoothableCache smoothableCache,
			@Nonnull final IBlockReader blockAccess,
			@Nonnull final PooledMutableBlockPos pooledMutableBlockPos
	) {
//		try (ModProfiler ignored = NoCubes.getProfiler().start("generate densityCache"))
		{
			final int densityCacheSizeX = stateCache.sizeX - 1;
			final int densityCacheSizeY = stateCache.sizeY - 1;
			final int densityCacheSizeZ = stateCache.sizeZ - 1;

			final DensityCache densityCache = DensityCache.retain(densityCacheSizeX, densityCacheSizeY, densityCacheSizeZ);
			final float[] densityCacheArray = densityCache.getDensityCache();

			int index = 0;
			for (int z = 0; z < densityCacheSizeZ; ++z) {
				for (int y = 0; y < densityCacheSizeY; ++y) {
					for (int x = 0; x < densityCacheSizeX; ++x, ++index) {
						densityCacheArray[index] = getBlockDensity(
								startPosX, startPosY, startPosZ,
								x, y, z,
								stateCache, smoothableCache,
								blockAccess, pooledMutableBlockPos
						);
					}
				}
			}
			return densityCache;
		}
	}

	private static float getBlockDensity(
			final int startPosX, final int startPosY, final int startPosZ,
			final int posX, final int posY, final int posZ,
			@Nonnull final StateCache stateCache,
			@Nonnull final SmoothableCache smoothableCache,
			@Nonnull final IBlockReader cache,
			@Nonnull final PooledMutableBlockPos pooledMutableBlockPos
	) {
		final int cacheSizeX = smoothableCache.sizeX;
		final int cacheSizeY = smoothableCache.sizeY;
		final boolean[] smoothableCacheArray = smoothableCache.getSmoothableCache();
		final IBlockState[] stateCacheArray = stateCache.getBlockStateCache();

		float density = 0;
		for (int zOffset = 0; zOffset < 2; ++zOffset) {
			for (int yOffset = 0; yOffset < 2; ++yOffset) {
				for (int xOffset = 0; xOffset < 2; ++xOffset) {

					// Flat[x + WIDTH * (y + HEIGHT * z)] = Original[x, y, z]
					final int index = (posX + xOffset) + cacheSizeX * ((posY + yOffset) + cacheSizeY * (posZ + zOffset));

//					pooledMutableBlockPos.setPos(
//							startPosX + posX - xOffset,
//							startPosY + posY - yOffset,
//							startPosZ + posZ - zOffset
//					);

					density += getIndividualBlockDensity(smoothableCacheArray[index], stateCacheArray[index], cache, pooledMutableBlockPos);
				}
			}
		}
		return density;
	}

	/**
	 * @return negative density if the block is smoothable (inside the isosurface), positive if it isn't
	 */
	public static float getIndividualBlockDensity(final boolean shouldSmooth, final IBlockState state, final IBlockReader cache, final BlockPos pos) {
		if (state.getBlock() == SNOW && shouldSmooth) {
			final int value = state.get(BlockSnowLayer.LAYERS);
			if (value == 1) { // zero-height snow layer
				return 1;
			} else { // snow height between 0-8 to between -0.25F and -1
				return -((value - 1) * 0.125F);
			}
		} else if (shouldSmooth) {
			return state.getBlock() == BEDROCK ? -1.0005F : -1;
		} else if (state.isNormalCube() || state.isBlockNormalCube()) {
			return 0;
		} else {
			return 1;
		}
	}

}
