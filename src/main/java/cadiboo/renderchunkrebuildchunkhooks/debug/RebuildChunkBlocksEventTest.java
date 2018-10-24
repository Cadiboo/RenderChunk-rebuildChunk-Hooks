package cadiboo.renderchunkrebuildchunkhooks.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlocksEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkBlocksEventTest.MODID, name = "RebuildChunkBlocksEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkBlocksEventTest {

	public static final String	MODID	= "rebuild_chunk_blocks_event_test";
	public static final boolean	ENABLED	= true;

	public static final int	SURFACE_NETS_CUBE_EDGES_SIZE	= 24;
	public static final int	SURFACE_NETS_EDGE_TABLE_SIZE	= 256;

	// Precompute edge table, like Paul Bourke does.
	// This saves a bit of time when computing the centroid of each boundary cell
	public static final int[]	SURFACE_NETS_CUBE_EDGES	= new int[SURFACE_NETS_CUBE_EDGES_SIZE];
	public static final int[]	SURFACE_NETS_EDGE_TABLE	= new int[SURFACE_NETS_EDGE_TABLE_SIZE];

	static {
		// Initialize the cube_edges table
		// This is just the vertex number of each cube
		int cubeEdgeIndex = 0;
		for (int i = 0; i < 8; ++i) {
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
		for (int edgeTableIndex = 0; edgeTableIndex < SURFACE_NETS_EDGE_TABLE_SIZE; ++edgeTableIndex) {
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

		smooth |= state.getBlock() instanceof BlockGrass;
		smooth |= state.getBlock() instanceof BlockStone;
		smooth |= state.getBlock() instanceof BlockSand;
		smooth |= state.getBlock() instanceof BlockSandStone;
		smooth |= state.getBlock() instanceof BlockGravel;
		smooth |= state.getBlock() instanceof BlockOre;
		smooth |= state.getBlock() instanceof BlockRedstoneOre;
		smooth |= state.getBlock() instanceof BlockSilverfish;
		smooth |= state.getBlock() instanceof BlockGrassPath;
		smooth |= state.getBlock() instanceof BlockDirt;
		smooth |= state.getBlock() instanceof BlockClay;
		smooth |= state.getBlock() instanceof BlockSnow;

		return smooth;
	}

	public static float getBlockDensity(final BlockPos pos, final IBlockAccess cache) {
		float density = 0.0F;

		final MutableBlockPos mutablePos = new MutableBlockPos(pos);

		for (int x = 0; x < 2; ++x) {
			for (int y = 0; y < 2; ++y) {
				for (int z = 0; z < 2; ++z) {
					mutablePos.setPos(pos.getX() - x, pos.getY() - y, pos.getZ() - z);

					final IBlockState state = cache.getBlockState(mutablePos);

					if (shouldSmooth(state)) {
						density += state.getBoundingBox(cache, mutablePos).maxY - y;
					} else {
						density -= 1;
					}

				}
			}
		}

		return density;
	}

	@SubscribeEvent
	public static void rebuildChunkSurfaceNetsOOP(final RebuildChunkBlocksEvent event) {
		if (!ENABLED) {
			return;
		}

		event.setCanceled(true);

		final ChunkCache cache = event.getWorldView();
		final BlockPos position = event.getRenderChunkPosition();
		final BiFunction<BlockPos, IBlockAccess, Float> potential = (positionOfPotentiallySmoothableBlock, blockAccess) -> {
			return getBlockDensity(positionOfPotentiallySmoothableBlock, blockAccess);
		};

		final SurfaceNet surfaceNet = generateSurfaceNet(position, cache, potential);

		for (final BlockPosSurfaceNetInfo posInfo : surfaceNet.posInfos) {
			final BlockPos pos = posInfo.pos;
			final IBlockState state = posInfo.state;
			final BlockPos brightnessBlockPos = posInfo.brightnessPos;
			final Block block = state.getBlock();

			// get model
			final IBakedModel model = event.getBlockRendererDispatcher().getModelForState(state);
			if (model == null) {
				continue;
			}

			// get up quads OR null quads
			List<BakedQuad> quads = model.getQuads(state, EnumFacing.UP, MathHelper.getPositionRandom(pos));
			if ((quads == null) || quads.isEmpty()) {
				quads = model.getQuads(state, null, MathHelper.getPositionRandom(pos));
				if ((quads == null) || quads.isEmpty()) {
					continue;
				}
			}

			// get first quad
			final BakedQuad quad = quads.get(0);
			if (quad == null) {
				continue;
			}

			// get sprite
			final TextureAtlasSprite sprite = quad.getSprite();
			if (sprite == null) {
				continue;
			}

			final float redFloat;
			final float greenFloat;
			final float blueFloat;

			if (quad.hasTintIndex()) {
				final int colorMultiplier = Minecraft.getMinecraft().getBlockColors().colorMultiplier(state, cache, pos, 0);
				redFloat = ((colorMultiplier >> 16) & 255) / 255.0F;
				greenFloat = ((colorMultiplier >> 8) & 255) / 255.0F;
				blueFloat = (colorMultiplier & 255) / 255.0F;
			} else {
				redFloat = 1;
				greenFloat = 1;
				blueFloat = 1;
			}

			final int red = (int) (0xFF * redFloat);
			final int green = (int) (0xFF * greenFloat);
			final int blue = (int) (0xFF * blueFloat);
			final int alpha = 0xFF;
			final double minU = sprite.getMinU();
			final double maxU = sprite.getMaxU();
			final double minV = sprite.getMinV();
			final double maxV = sprite.getMaxV();

			final IBlockState brightnessState = cache.getBlockState(brightnessBlockPos);

			final int brightness = brightnessState.getPackedLightmapCoords(cache, brightnessBlockPos);
			final int lightmapSkyLight = (brightness >> 16) & 65535;
			final int lightmapBlockLight = brightness & 65535;

			for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
				if (!block.canRenderInLayer(state, blockRenderLayer)) {
					continue;
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);

				if (state.getRenderType() != EnumBlockRenderType.INVISIBLE) {
					final BufferBuilder bufferBuilder = event.startOrContinueLayer(blockRenderLayer);

					boolean wasLayerUsed = false;
					for (final QuadVertexList vertexList : posInfo.vertexList) {

						bufferBuilder.pos(vertexList.vertex1.x, vertexList.vertex1.y, vertexList.vertex1.z).color(red, green, blue, alpha).tex(minU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						bufferBuilder.pos(vertexList.vertex2.x, vertexList.vertex2.y, vertexList.vertex2.z).color(red, green, blue, alpha).tex(maxU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						bufferBuilder.pos(vertexList.vertex3.x, vertexList.vertex3.y, vertexList.vertex3.z).color(red, green, blue, alpha).tex(maxU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						bufferBuilder.pos(vertexList.vertex4.x, vertexList.vertex4.y, vertexList.vertex4.z).color(red, green, blue, alpha).tex(minU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						wasLayerUsed = true;
					}
					event.setBlockRenderLayerUsedWithOrOpperation(blockRenderLayer, wasLayerUsed);
				}
			}
			net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
		}

		for (final BlockPos.MutableBlockPos blockpos$mutableblockpos : event.getChunkBlockPositions()) {
			final IBlockState state = event.getWorldView().getBlockState(blockpos$mutableblockpos);
			final Block block = state.getBlock();

			if (shouldSmooth(state)) {
				continue;
			}

			for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
				if (!block.canRenderInLayer(state, blockRenderLayer)) {
					continue;
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);

				if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
					final BufferBuilder bufferbuilder = event.startOrContinueLayer(blockRenderLayer);

					final boolean used = event.getBlockRendererDispatcher().renderBlock(state, blockpos$mutableblockpos, event.getWorldView(), bufferbuilder);

					event.setBlockRenderLayerUsedWithOrOpperation(blockRenderLayer, used);

				}
			}
			net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
		}
	}

	private static class SurfaceNet {
		final List<BlockPosSurfaceNetInfo> posInfos;

		protected SurfaceNet(final List<BlockPosSurfaceNetInfo> posInfos) {
			this.posInfos = posInfos;
		}
	}

	private static class BlockPosSurfaceNetInfo {
		final BlockPos				pos;
		final IBlockState			state;
		final BlockPos				brightnessPos;
		final List<QuadVertexList>	vertexList;

		protected BlockPosSurfaceNetInfo(final BlockPos pos, final IBlockState state, final BlockPos brightnessPos, final List<QuadVertexList> quadVertexList) {
			this.pos = pos;
			this.state = state;
			this.brightnessPos = brightnessPos;
			this.vertexList = quadVertexList;
		}
	}

	private static class QuadVertexList {
		final Vec3d	vertex1;
		final Vec3d	vertex2;
		final Vec3d	vertex3;
		final Vec3d	vertex4;

		protected QuadVertexList(final Vec3d vertex1, final Vec3d vertex2, final Vec3d vertex3, final Vec3d vertex4) {
			this.vertex1 = vertex1;
			this.vertex2 = vertex2;
			this.vertex3 = vertex3;
			this.vertex4 = vertex4;
		}

		protected Vec3d[] getVertexes() {
			return new Vec3d[] { this.vertex1, this.vertex2, this.vertex3, this.vertex4 };
		}
	}

	private static SurfaceNet generateSurfaceNet(final BlockPos startingPositionIn, final IBlockAccess cache, final BiFunction<BlockPos, IBlockAccess, Float> potential) {
		// dims: "A 3D vector of integers representing the resolution of the isosurface". Resolution in our context means size
		final int[] dims = new int[] { 16, 16, 16 };
		final int[] startPos = new int[] { startingPositionIn.getX(), startingPositionIn.getY(), startingPositionIn.getZ() };
		final int[] currentPos = new int[3];
		final int[] edgesIThink = new int[] { 1, dims[0] + 3, (dims[0] + 3) * (dims[1] + 3) };
		final float[] grid = new float[8];
		final float[][] buffer = new float[edgesIThink[2] * 2][3];
		int bufno = 1;

		// "Resize buffer if necessary" is what mikolalysenko said, but Click_Me seems to have removed this code. This is probably because the buffer should never (and actually
		// can't be in java) be resized
		final ArrayList<BlockPosSurfaceNetInfo> posInfos = new ArrayList<>();
		// March over the voxel grid
		for (currentPos[2] = 0; currentPos[2] < (dims[2] + 1); edgesIThink[2] = -edgesIThink[2], ++currentPos[2], bufno ^= 1) {

			// m is the pointer into the buffer we are going to use.
			// "This is slightly obtuse because javascript does not have good support for packed data structures, so we must use typed arrays :(" is what mikolalysenko said, it
			// obviously doesn't apply here
			// The contents of the buffer will be the indices of the vertices on the previous x/y slice of the volume
			int m = 1 + ((dims[0] + 3) * (1 + (bufno * (dims[1] + 3))));

			for (currentPos[1] = 0; currentPos[1] < (dims[1] + 1); ++currentPos[1], m += 2) {
				for (currentPos[0] = 0; currentPos[0] < (dims[0] + 1); ++currentPos[0], ++m) {

					// Read in 8 field values around this vertex and store them in an array
					// Also calculate 8-bit mask, like in marching cubes, so we can speed up sign checks later
					int mask = 0;
					int g = 0;

					for (int z = 0; z < 2; ++z) {
						for (int y = 0; y < 2; ++y) {
							for (int x = 0; x < 2; ++g) {
								// TODO: mutableblockpos?
								final float p = potential.apply(new BlockPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z), cache);

								// final float p = getBlockDensity(new BlockPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z),
								// cache);
								grid[g] = p;
								mask |= p > 0.0F ? 1 << g : 0;
								++x;

							}
						}
					}

					// Check for early termination if cell does not intersect boundary
					if ((mask == 0) || (mask == 0xFF)) {
						continue;
					}

					IBlockState stateActual = Blocks.AIR.getDefaultState();

					final MutableBlockPos pos = new MutableBlockPos();
					getStateAndPos: for (int y = -1; y < 2; ++y) {
						for (int z = -1; z < 2; ++z) {
							for (int x = -1; x < 2; ++x) {
								pos.setPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z);
								final IBlockState tempStateActual = cache.getBlockState(pos).getActualState(cache, pos);

								// if (shouldSmooth(tempState) && (state.getBlock() != Blocks.GRASS))
								// {
								// state = tempState;
								// if ((tempState.getBlock() == Blocks.GRASS))
								// {
								// break getStateAndPos;
								// }
								// }

								if (shouldSmooth(tempStateActual) && (stateActual.getBlock() != Blocks.SNOW_LAYER) && (stateActual.getBlock() != Blocks.GRASS)) {
									stateActual = tempStateActual;
									if ((tempStateActual.getBlock() == Blocks.SNOW_LAYER) || (tempStateActual.getBlock() == Blocks.GRASS)) {
										break getStateAndPos;
									}
								}
							}
						}
					}

					final int[] brightnessPos = new int[] { startPos[0] + currentPos[0], startPos[1] + currentPos[1] + 1, startPos[2] + currentPos[2] };

					getBrightnessPos: for (int y = -1; y < 2; ++y) {
						for (int z = -2; z < 3; ++z) {
							for (int x = -1; x < 2; ++x) {
								// TODO: mutableblockpos?
								final IBlockState tempState = cache.getBlockState(new BlockPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z));
								if (!tempState.isOpaqueCube()) {
									brightnessPos[0] = startPos[0] + currentPos[0] + x;
									brightnessPos[1] = startPos[1] + currentPos[1] + y;
									brightnessPos[2] = startPos[2] + currentPos[2] + z;
									break getBrightnessPos;
								}
							}
						}
					}

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
						final float g0 = grid[e0]; // Unpack grid values
						final float g1 = grid[e1];
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

					// Now we just average the edge intersections and add them to coordinate
					final float s = 1.0F / e_count;
					for (int i = 0; i < 3; ++i) {
						v[i] = startPos[i] + currentPos[i] + (s * v[i]);
					}

					final int tx = currentPos[0] == 16 ? 0 : currentPos[0];
					final int ty = currentPos[1] == 16 ? 0 : currentPos[1];
					final int tz = currentPos[2] == 16 ? 0 : currentPos[2];
					long i1 = (tx * 3129871) ^ (tz * 116129781L) ^ ty;
					i1 = (i1 * i1 * 42317861L) + (i1 * 11L);
					v[0] = (float) (v[0] - (((((i1 >> 16) & 15L) / 15.0F) - 0.5D) * 0.2D));
					v[1] = (float) (v[1] - (((((i1 >> 20) & 15L) / 15.0F) - 1.0D) * 0.2D));
					v[2] = (float) (v[2] - (((((i1 >> 24) & 15L) / 15.0F) - 0.5D) * 0.2D));

					// "Add vertex to buffer, store pointer to vertex index in buffer" is what mikolalysenko said, but Click_Me seems to have changed something

					buffer[m] = v;

					final BlockPos brightnessBlockPos = new BlockPos(brightnessPos[0], brightnessPos[1], brightnessPos[2]);

					final ArrayList<QuadVertexList> vertexes = new ArrayList<>();

					// Now we need to add faces together, to do this we just loop over 3 basis components
					for (int axis = 0; axis < 3; ++axis) {
						// The first three entries of the edge_mask count the crossings along the edge
						if ((edge_mask & (1 << axis)) == 0) {
							continue;
						}

						// i = axes we are point along. iu, iv = orthogonal axes
						final int iu = (axis + 1) % 3;
						final int iv = (axis + 2) % 3;

						// If we are on a boundary, skip it
						if ((currentPos[iu] == 0) || (currentPos[iv] == 0)) {
							continue;
						}

						// Otherwise, look up adjacent edges in buffer
						final int du = edgesIThink[iu];
						final int dv = edgesIThink[iv];

						final float[] v0 = buffer[m];
						final float[] v1 = buffer[m - du];
						final float[] v2 = buffer[m - du - dv];
						final float[] v3 = buffer[m - dv];

						final QuadVertexList vertexList;

						// Remember to flip orientation depending on the sign of the corner.
						if ((mask & 1) != 0) {
							vertexList = new QuadVertexList(new Vec3d(v0[0], v0[1], v0[2]), new Vec3d(v1[0], v1[1], v1[2]), new Vec3d(v2[0], v2[1], v2[2]), new Vec3d(v3[0], v3[1], v3[2]));

						} else {
							vertexList = new QuadVertexList(new Vec3d(v0[0], v0[1], v0[2]), new Vec3d(v3[0], v3[1], v3[2]), new Vec3d(v2[0], v2[1], v2[2]), new Vec3d(v1[0], v1[1], v1[2]));

						}

						vertexes.add(vertexList);
					}

					final BlockPosSurfaceNetInfo posInfo = new BlockPosSurfaceNetInfo(pos, stateActual, brightnessBlockPos, vertexes);

					posInfos.add(posInfo);

				}

			}
		}
		return new SurfaceNet(posInfos);
	}

}
