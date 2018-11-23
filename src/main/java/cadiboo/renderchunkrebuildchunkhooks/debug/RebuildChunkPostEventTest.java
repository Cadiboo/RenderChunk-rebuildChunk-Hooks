package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import com.google.common.base.Preconditions;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkPostEventTest.MODID, name = "RebuildChunkPostEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkPostEventTest {

	public static final String MODID = "rebuild_chunk_post_event_test";

	public static final boolean ENABLED = false;

	@SubscribeEvent
	public static void onRebuildChunkPostEvent(final RebuildChunkPostEvent event) {

		if (! ENABLED) {
			return;
		}

		/**
		 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
		 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param chunkCache          the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param visGraph            the {@link VisGraph} passed in
		 * @param setTileEntities     the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
		 * @param lockCompileTask     the {@link ReentrantLock} for the compile task passed in
		 */

		Preconditions.checkNotNull(event.getRenderChunk());
		Preconditions.checkNotNull(event.getX());
		Preconditions.checkNotNull(event.getY());
		Preconditions.checkNotNull(event.getZ());
		Preconditions.checkNotNull(event.getGenerator());
		Preconditions.checkNotNull(event.getCompiledChunk());
		Preconditions.checkNotNull(event.getRenderChunkPosition());
		Preconditions.checkNotNull(event.getRenderGlobal());
		Preconditions.checkNotNull(event.getChunkCache());
		Preconditions.checkNotNull(event.getVisGraph());
		Preconditions.checkNotNull(event.getSetTileEntities());
		Preconditions.checkNotNull(event.getLockCompileTask());

		//		BufferBuilder bufferBuilderForSolidBlockRenderLayer = event.getGenerator().getRegionRenderCacheBuilder().getWorldRendererByLayer(BlockRenderLayer.SOLID);

		//		solid.toString();

	}

}
