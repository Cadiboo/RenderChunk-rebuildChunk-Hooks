package cadiboo.renderchunkrebuildchunkhooks;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = RebuildChunkAllBlocksEventTest2.MODID, name = "RebuildChunkAllBlocksEventTest2", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
@EventBusSubscriber(modid = RebuildChunkAllBlocksEventTest2.MODID, value = Side.CLIENT)
public class RebuildChunkAllBlocksEventTest2 {

	public static final String	MODID	= "rebuild_chunk_all_blocks_event_test2";
	public static final boolean	ENABLED	= true;

	@SubscribeEvent
	public static void onRebuildChunkAllBlocksEvent(final RebuildChunkAllBlocksEvent event) {
		if (!ENABLED) {
			return;
		}

		event.setCanceled(true);

	}

}
