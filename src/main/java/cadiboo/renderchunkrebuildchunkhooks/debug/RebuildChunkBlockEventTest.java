package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import com.google.common.base.Preconditions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkBlockEventTest.MODID, name = "RebuildChunkBlockEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkBlockEventTest {

	public static final String  MODID   = "rebuild_chunk_block_event_test";
	public static final boolean ENABLED = false;

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildChunkBlockEvent event) {

		if (! ENABLED) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk());
		Preconditions.checkNotNull(event.getRenderGlobal());
		Preconditions.checkNotNull(event.getChunkCache());
		Preconditions.checkNotNull(event.getGenerator());
		Preconditions.checkNotNull(event.getCompiledChunk());
		Preconditions.checkNotNull(event.getBlockRendererDispatcher());
		Preconditions.checkNotNull(event.getBlockState());
		Preconditions.checkNotNull(event.getBlockPos());
		Preconditions.checkNotNull(event.getBufferBuilder());
		Preconditions.checkNotNull(event.getRenderChunkPosition());
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers());
		Preconditions.checkNotNull(event.getX());
		Preconditions.checkNotNull(event.getY());
		Preconditions.checkNotNull(event.getZ());
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers());
		Preconditions.checkNotNull(event.getVisGraph());

		if (new Random().nextBoolean()) {
			//			event.setCanceled(true);
			for (int i = 0; i < event.getUsedBlockRenderLayers().length; i++) {
				event.getUsedBlockRenderLayers()[i] = false;
			}
		}

		//		if (!event.getBlockState().getMaterial().blocksLight()) {
		//			event.setCanceled(false);
		//			event.getBlockRendererDispatcher().renderBlock(Blocks.GLASS.getDefaultState(), event.getBlockPos(), event.getWorldView(), event.getBufferBuilder());
		//		}

	}

}