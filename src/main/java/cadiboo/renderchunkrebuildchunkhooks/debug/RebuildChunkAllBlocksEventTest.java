package cadiboo.renderchunkrebuildchunkhooks.debug;

import java.util.Random;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
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

		for (final BlockPos.MutableBlockPos blockpos$mutableblockpos : event.getChunkBlockPositions()) {
			final IBlockState state = event.getWorldView().getBlockState(blockpos$mutableblockpos);
			final Block block = state.getBlock();

			if (new Random().nextBoolean()) {
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
