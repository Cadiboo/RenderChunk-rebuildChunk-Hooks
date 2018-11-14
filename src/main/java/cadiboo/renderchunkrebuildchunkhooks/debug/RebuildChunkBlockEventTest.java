package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkBlockEventTest.MODID, name = "RebuildChunkBlockEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkBlockEventTest {

	public static final String	MODID	= "rebuild_chunk_block_event_test";
	public static final boolean	ENABLED	= true;

	@SubscribeEvent
	public static void onRebuildChunkBlock(final RebuildChunkBlockEvent event) {

		if (!ENABLED) {
			return;
		}

		if (!event.getBlockState().getMaterial().blocksLight()) {
			event.setCanceled(false);
			event.getBlockRendererDispatcher().renderBlock(Blocks.GLASS.getDefaultState(), event.getBlockPos(), event.getWorldView(), event.getBufferBuilder());
		}

	}

}