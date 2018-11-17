package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkBlockEventSurfaceNetsTest.MODID, name = "RebuildChunkBlockEventSurfaceNetsTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkBlockEventSurfaceNetsTest {

	public static final String  MODID   = "rebuild_chunk_block_event_surface_nets_test";
	public static final boolean ENABLED = true;

	public static final int SURFACE_NETS_CUBE_EDGES_SIZE = 24;
	public static final int SURFACE_NETS_EDGE_TABLE_SIZE = 256;

	// Precompute edge table, like Paul Bourke does.
	// This saves a bit of time when computing the centroid of each boundary cell
	public static final int[] SURFACE_NETS_CUBE_EDGES = new int[SURFACE_NETS_CUBE_EDGES_SIZE];
	public static final int[] SURFACE_NETS_EDGE_TABLE = new int[SURFACE_NETS_EDGE_TABLE_SIZE];

	static {
		// Initialize the cube_edges table
		// This is just the vertex number of each cube
		int cubeEdgeIndex = 0;
		for (int i = 0; i < 8; ++ i) {
			for (int j = 1; j <= 4; j <<= 1) {
				final int p = i ^ j;
				if (i <= p) {
					SURFACE_NETS_CUBE_EDGES[cubeEdgeIndex++] = i;
					SURFACE_NETS_CUBE_EDGES[cubeEdgeIndex++] = p;
				}
			}
		}

		// Initialize the intersection table.
		// This is a 2^(cube configuration) -> 2^(edge configuration) map
		// There is one entry for each possible cube configuration, and the output is a 12-bit vector enumerating all edges crossing the 0-level.
		for (int edgeTableIndex = 0; edgeTableIndex < SURFACE_NETS_EDGE_TABLE_SIZE; ++ edgeTableIndex) {
			int em = 0;

			for (int j = 0; j < SURFACE_NETS_CUBE_EDGES_SIZE; j += 2) {
				final boolean a = (edgeTableIndex & (1 << SURFACE_NETS_CUBE_EDGES[j])) != 0;
				final boolean b = (edgeTableIndex & (1 << SURFACE_NETS_CUBE_EDGES[j + 1])) != 0;
				em |= a != b ? 1 << (j >> 1) : 0;
			}

			SURFACE_NETS_EDGE_TABLE[edgeTableIndex] = em;
		}
	}

	public static boolean shouldSmooth(final IBlockState state) {

		boolean smooth = false;

		if (false) {
			return state.getMaterial() != Material.AIR;
		}

		smooth |= state.getBlock() instanceof BlockGrass;
		smooth |= state.getBlock() instanceof BlockStone;
		smooth |= state.getBlock() instanceof BlockSand;
		//		smooth |= state.getBlock() instanceof BlockSandStone;
		smooth |= state == Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.DEFAULT);
		smooth |= state == Blocks.RED_SANDSTONE.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.DEFAULT);
		smooth |= state.getBlock() instanceof BlockGravel;
		smooth |= state.getBlock() instanceof BlockOre;
		smooth |= state.getBlock() instanceof BlockRedstoneOre;
		smooth |= state.getBlock() instanceof BlockSilverfish;
		smooth |= state.getBlock() instanceof BlockGrassPath;
		smooth |= state.getBlock() instanceof BlockDirt;
		smooth |= state.getBlock() instanceof BlockClay;
		smooth |= state.getBlock() instanceof BlockSnow;
		smooth |= state.getBlock() == Blocks.BEDROCK;

		smooth |= state.getBlock() instanceof BlockNetherrack;
		smooth |= state.getBlock() instanceof BlockGlowstone;

		smooth |= state.getBlock() == Blocks.END_STONE;

		smooth |= state.getBlock() instanceof BlockMycelium;

		return smooth;
	}

	public static float getBlockDensity(final BlockPos pos, final IBlockAccess cache) {

		float density = 0.0F;

		final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);

		for (int x = 0; x < 2; ++ x) {
			for (int y = 0; y < 2; ++ y) {
				for (int z = 0; z < 2; ++ z) {
					mutablePos.setPos(pos.getX() - x, pos.getY() - y, pos.getZ() - z);

					final IBlockState state = cache.getBlockState(mutablePos);

					if (shouldSmooth(state)) {
						density += 1;
					} else if (state.isNormalCube()) {

					} else if (state.getMaterial() == Material.VINE) {
						density -= 0.75;
					} else {
						density -= 1;
					}

					if (state.getBlock() == Blocks.BEDROCK) {
						density += 0.000000000000000000000000000000000000000000001f;
					}

				}
			}
		}

		return density;
	}

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildChunkBlockEvent event) {

		if (! ENABLED) {
			return;
		}

		if (! shouldSmooth(event.getBlockState())) {
			return;
		}

		event.setCanceled(true);

		final BlockPos position = event.getRenderChunkPosition();
		final ChunkCache cache = event.getWorldView();
		final BlockPos pos = event.getBlockPos().toImmutable();
		final BufferBuilder bufferBuilder = event.getBufferBuilder();

		final int red = 0xFF;
		final int green = 0xFF;
		final int blue = 0xFF;
		final int alpha = 0xFF;

		final int minU = 0;
		final int maxV = 0xFF;

		final int lightmapSkyLight = 15<<4;
		final int lightmapBlockLight = 15<<4;

		final float[] neighbourDensities = new float[8];

		//Read in 8 field values around this vertex and store them in an array
		//Also calculate 8-bit mask, like in marching cubes, so we can speed up sign checks later
		int mask = 0;
		int index = 0;
		for (BlockPos.MutableBlockPos currentPos : BlockPos.getAllInBoxMutable(pos.add(- 1, - 1, - 1), pos.add(1, 1, 1))) {
			if (currentPos.equals(pos)) {
				continue;
			}
			float density = getBlockDensity(currentPos, cache);

			neighbourDensities[index] = density;
			mask |= density > 0.0F ? 1 << index : 0;

			index++;
		}

		// Check for early termination if cell does not intersect boundary
		if ((mask == 0) || (mask == 0xFF)) {
			return;
		}

		// getRealStateAndPos

		// getBrightnessPos

		// Sum up edge intersections
		final int edge_mask = SURFACE_NETS_EDGE_TABLE[mask];
		int e_count = 0;
		final float[] v = new float[] { 0.0F, 0.0F, 0.0F };

		// For every edge of the cube...
		for (int i = 0; i < 12; ++i) {

			// Use edge mask to check if it is crossed
			if ((edge_mask & (1 << i)) == 0) {
				continue;
			}

			// If it did, increment number of edge crossings
			++e_count;

			// Now find the point of intersection
			final int e0 = SURFACE_NETS_CUBE_EDGES[i << 1]; // Unpack vertices
			final int e1 = SURFACE_NETS_CUBE_EDGES[(i << 1) + 1];
			final float g0 = neighbourDensities[e0]; // Unpack grid values
			final float g1 = neighbourDensities[e1];
			float t = g0 - g1; // Compute point of intersection
			if (Math.abs(t) > 0.0F) {
				t = g0 / t;
				int j = 0;

				// Interpolate vertices and add up intersections (this can be done without multiplying)
				for (int k = 1; j < 3; k <<= 1) {
					final int a = e0 & k;
					final int b = e1 & k;
					if (a != b) {
						v[j] += a != 0 ? 1.0F - t : t;
					} else {
						v[j] += a != 0 ? 1.0F : 0.0F;
					}

					++j;
				}

			}
		}

		final int[] blockPosA = {pos.getX(), pos.getY(), pos.getZ()};

		// Now we just average the edge intersections and add them to coordinate
		final float s = 1.0F / e_count;
		for (int i = 0; i < 3; ++i) {
			v[i] = blockPosA[i] + (s * v[i]);
		}

		final int[] blockPosMinusPositionA = {pos.getX() - position.getX(), pos.getY() - position.getY(), pos.getZ() - position.getZ()};

		final int tx = blockPosMinusPositionA[0] == 16 ? 0 : blockPosMinusPositionA[0];
		final int ty = blockPosMinusPositionA[1] == 16 ? 0 : blockPosMinusPositionA[1];
		final int tz = blockPosMinusPositionA[2] == 16 ? 0 : blockPosMinusPositionA[2];
		long i1 = (tx * 3129871) ^ (tz * 116129781L) ^ ty;
		i1 = (i1 * i1 * 42317861L) + (i1 * 11L);
		v[0] = (float) (v[0] - (((((i1 >> 16) & 15L) / 15.0F) - 0.5D) * 0.2D));
		v[1] = (float) (v[1] - (((((i1 >> 20) & 15L) / 15.0F) - 1.0D) * 0.2D));
		v[2] = (float) (v[2] - (((((i1 >> 24) & 15L) / 15.0F) - 0.5D) * 0.2D));



		// Remember to flip orientation depending on the sign of the corner.
		if ((mask & 1) != 0) {
			bufferBuilder.pos(v[0], v[1], v[2]).color(red, green, blue, alpha).tex(minU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
		} else {
			bufferBuilder.pos(v[0], v[1], v[2]).color(red, green, blue, alpha).tex(minU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
		}

	}

}