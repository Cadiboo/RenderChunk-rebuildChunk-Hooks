package cadiboo.renderchunkrebuildchunkhooks.debug;

import java.util.List;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkAllBlocksEventTest.MODID, name = "RebuildChunkAllBlocksEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkAllBlocksEventTest {

	public static final String	MODID	= "rebuild_chunk_all_blocks_event_test";
	public static final boolean	ENABLED	= true;

	@SubscribeEvent
	public static void rebuildChunkSurfaceNetsOOP(final RebuildChunkAllBlocksEvent event) {
		if (!ENABLED) {
			return;
		}

		event.setCanceled(true);

		final ChunkCache cache = event.getWorldView();
		final BlockPos position = event.getRenderChunkPosition();
		// final BiFunction<BlockPos, ChunkCache, Float> potential = (pos, cache2) -> {
		// return getBlockDensity(position, cache2);
		// };

		// do stuff

		// dims: "A 3D vector of integers representing the resolution of the isosurface". Resolution in our context means size
		final int[] dims = new int[] { 16, 16, 16 };
		final int[] startPos = new int[] { position.getX(), position.getY(), position.getZ() };
		final int[] currentPos = new int[3];
		final int[] edgesIThink = new int[] { 1, dims[0] + 3, (dims[0] + 3) * (dims[1] + 3) };
		final float[] grid = new float[8];
		final float[][] buffer = new float[edgesIThink[2] * 2][3];
		int bufno = 1;

		// "Resize buffer if necessary" is what mikolalysenko said, but Click_Me seems to have removed this code. This is probably because the buffer should never (and actually
		// can't be in java) be resized

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
								// final float p = potential.apply(new BlockPos(c[0] + x[0] + i, c[1] + x[1] + j, c[2] + x[2] + k), cache);

								final float p = getBlockDensity(new BlockPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z), cache);
								grid[g] = p;
								mask |= p > 0.0F ? 1 << g : 0;
								++x;

							}
						}
					}

					// Check for early termination if cell does not intersect boundary
					if ((mask == 0) || (mask == 0xff)) {
						continue;
					}

					IBlockState state = Blocks.AIR.getDefaultState();

					final MutableBlockPos pos = new MutableBlockPos();
					getStateAndPos: for (int y = -1; y < 2; ++y) {
						for (int z = -1; z < 2; ++z) {
							for (int x = -1; x < 2; ++x) {
								pos.setPos(startPos[0] + currentPos[0] + x, startPos[1] + currentPos[1] + y, startPos[2] + currentPos[2] + z);
								final IBlockState tempState = cache.getBlockState(pos);

								// if (shouldSmooth(tempState) && (state.getBlock() != Blocks.GRASS))
								// {
								// state = tempState;
								// if ((tempState.getBlock() == Blocks.GRASS))
								// {
								// break getStateAndPos;
								// }
								// }

								if (shouldSmooth(tempState) && (state.getBlock() != Blocks.SNOW_LAYER) && (state.getBlock() != Blocks.GRASS)) {
									state = tempState;
									if ((tempState.getBlock() == Blocks.SNOW_LAYER) || (tempState.getBlock() == Blocks.GRASS)) {
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

					// get model
					final IBakedModel model = event.getBlockRendererDispatcher().getModelForState(state);
					if (model == null) {
						continue;
					}

					List<BakedQuad> quads = model.getQuads(state, EnumFacing.UP, MathHelper.getPositionRandom(pos));
					if ((quads == null) || quads.isEmpty()) {
						quads = model.getQuads(state, null, MathHelper.getPositionRandom(pos));
						if ((quads == null) || quads.isEmpty()) {
							continue;
						}
					}
					final BakedQuad quad = quads.get(0);
					if (quad == null) {
						continue;
					}

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

					final BlockRenderLayer blockRenderLayer = state.getBlock().getRenderLayer();
					final BufferBuilder bufferBuilder = event.startOrContinueLayer(blockRenderLayer);
					event.setBlockRenderLayerUsedWithOrOpperation(blockRenderLayer, true);

					final int red = (int) (0xFF * redFloat);
					final int green = (int) (0xFF * greenFloat);
					final int blue = (int) (0xFF * blueFloat);
					final int alpha = 0xFF;
					final double minU = sprite.getMinU();
					final double maxU = sprite.getMaxU();
					final double minV = sprite.getMinV();
					final double maxV = sprite.getMaxV();

					final BlockPos brightnessBlockPos = new BlockPos(brightnessPos[0], brightnessPos[1], brightnessPos[2]);
					final IBlockState brightnessState = cache.getBlockState(brightnessBlockPos);

					final int brightness = brightnessState.getPackedLightmapCoords(cache, brightnessBlockPos);
					final int lightmapSkyLight = (brightness >> 16) & 65535;
					final int lightmapBlockLight = brightness & 65535;

					// Now we need to add faces together, to do this we just loop over 3 basis components
					for (int i = 0; i < 3; ++i) {
						// The first three entries of the edge_mask count the crossings along the edge
						if ((edge_mask & (1 << i)) == 0) {
							continue;
						}

						// i = axes we are point along. iu, iv = orthogonal axes
						final int iu = (i + 1) % 3;
						final int iv = (i + 2) % 3;

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

						// Remember to flip orientation depending on the sign of the corner.
						if ((mask & 1) != 0) {
							bufferBuilder.pos(v0[0], v0[1], v0[2]).color(red, green, blue, alpha).tex(minU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v1[0], v1[1], v1[2]).color(red, green, blue, alpha).tex(maxU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v2[0], v2[1], v2[2]).color(red, green, blue, alpha).tex(maxU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v3[0], v3[1], v3[2]).color(red, green, blue, alpha).tex(minU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						} else {
							bufferBuilder.pos(v0[0], v0[1], v0[2]).color(red, green, blue, alpha).tex(minU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v3[0], v3[1], v3[2]).color(red, green, blue, alpha).tex(maxU, maxV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v2[0], v2[1], v2[2]).color(red, green, blue, alpha).tex(maxU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
							bufferBuilder.pos(v1[0], v1[1], v1[2]).color(red, green, blue, alpha).tex(minU, minV).lightmap(lightmapSkyLight, lightmapBlockLight).endVertex();
						}
					}

				}

			}
		}

		for (final MutableBlockPos pos : event.getChunkBlockPositions()) {
			final IBlockState state = cache.getBlockState(pos);

			final Block block = state.getBlock();

			if (shouldSmooth(state)) {
				continue;
			}

			for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
				if (!block.canRenderInLayer(state, blockRenderLayer)) {
					continue;
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);

				if (state.getRenderType() != EnumBlockRenderType.INVISIBLE) {
					final BufferBuilder bufferBuilder = event.startOrContinueLayer(blockRenderLayer);

					if (RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(event.getRenderChunk(), event.getContext(), event.getWorldView(), event.getGenerator(), event.getCompiledChunk(), event.getBlockRendererDispatcher(), state, pos, bufferBuilder, event.getRenderChunkPosition(), blockRenderLayer, event.getX(), event.getY(), event.getZ(), event.getTileEntitiesWithGlobalRenderers(), event.getVisGraph()).isCanceled()) {
						continue;
					}
					final boolean wasLayerUsed = event.getBlockRendererDispatcher().renderBlock(state, pos, event.getWorldView(), bufferBuilder);

					event.setBlockRenderLayerUsedWithOrOpperation(blockRenderLayer, wasLayerUsed);

				}
			}
			net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
		}

//		event.setCanceled(true);

	}

	public static boolean shouldSmooth(final IBlockState state) {
		boolean smooth = false;

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

		final MutableBlockPos mutablePos = new MutableBlockPos(pos);

		for (int x = 0; x < 2; ++x) {
			for (int y = 0; y < 2; ++y) {
				for (int z = 0; z < 2; ++z) {
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

}
