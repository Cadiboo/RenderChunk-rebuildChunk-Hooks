package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import com.google.common.base.Preconditions;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkPreEventTest.MODID, name = "RebuildChunkPreEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkPreEventTest {

	public static final String MODID = "rebuild_chunk_pre_event_test";

	public static final boolean ENABLED = false;

	@SubscribeEvent
	public static void onRebuildChunkPreEvent(final RebuildChunkPreEvent event) {
		if (!ENABLED) {
			return;
		}

		/**
		 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
		 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param chunkCache          the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
		 */

		Preconditions.checkNotNull(event.getRenderChunk());
		Preconditions.checkNotNull(event.getRenderGlobal());
		Preconditions.checkNotNull(event.getChunkCache());
		Preconditions.checkNotNull(event.getGenerator());
		Preconditions.checkNotNull(event.getCompiledChunk());
		Preconditions.checkNotNull(event.getRenderChunkPosition());
		Preconditions.checkNotNull(event.getX());
		Preconditions.checkNotNull(event.getY());
		Preconditions.checkNotNull(event.getZ());

		if (new Random().nextInt(16) == 0) {
			event.setCanceled(true);

		}

	}

}
