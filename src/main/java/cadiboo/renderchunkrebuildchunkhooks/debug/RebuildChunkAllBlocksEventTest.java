package cadiboo.renderchunkrebuildchunkhooks.debug;

import java.util.Random;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkAllBlocksEventTest.MODID, name = "RebuildChunkAllBlocksEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkAllBlocksEventTest {

	public static final String	MODID	= "rebuild_chunk_all_blocks_event_test";
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

	public static float getBlockDensity(final BlockPos startPos, final IBlockAccess cache) {
		float density = 0.0F;

		for (final MutableBlockPos pos : BlockPos.getAllInBoxMutable(startPos.add(-1, -1, -1), startPos.add(1, 1, 1))) {
			final IBlockState state = cache.getBlockState(pos);
			if (shouldSmooth(state)) {
				density += state.getBoundingBox(cache, pos).maxY;
			} else {
				density -= 1;
			}
		}

		return density;
	}

	@SubscribeEvent
	public static void rebuildChunkSurfaceNetsOOP(final RebuildChunkAllBlocksEvent event) {
		if (!ENABLED) {
			return;
		}

		event.setCanceled(true);

		for (final BlockPos.MutableBlockPos blockpos$mutableblockpos : event.getChunkBlockPositions()) {
			final IBlockState state = event.getWorldView().getBlockState(blockpos$mutableblockpos);
			final Block block = state.getBlock();

			if (shouldSmooth(state) || new Random().nextBoolean()) {
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

}
