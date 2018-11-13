package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkAllBlocksEventTest.MODID, name = "RebuildChunkAllBlocksEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkAllBlocksEventTest {

	public static final String	MODID	= "rebuild_chunk_all_blocks_event_test";
	public static final boolean	ENABLED	= false;

	@SubscribeEvent
	public static void rebuildChunkSurfaceNetsOOP(final RebuildChunkAllBlocksEvent event) {
		if (!ENABLED) {
			return;
		}

//		event.setCanceled(true);

	}

}
