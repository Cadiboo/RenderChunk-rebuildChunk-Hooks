package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.RebuildChunkPostEventTest.Config.ENABLED;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkPostEventTest.MODID, name = "RebuildChunkPostEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public final class RebuildChunkPostEventTest {

	public static final String MODID = "rebuild_chunk_post_event_test";

	@net.minecraftforge.common.config.Config(modid = MODID)
	public static class Config {

		public static boolean ENABLED = true;

	}

	@SubscribeEvent
	public static void onRebuildChunkPostEvent(final RebuildChunkPostEvent event) {
		if (!ENABLED) {
			return;
		}

		/*
		 * @param renderChunk                     the instance of {@link RenderChunk}
		 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition             the {@link BlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param chunkCache                      the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 */
		try {
			Objects.requireNonNull(event.getRenderChunk());
			Objects.requireNonNull(event.getGenerator());
			Objects.requireNonNull(event.getCompiledChunk());
			Objects.requireNonNull(event.getRenderChunkPosition());
			Objects.requireNonNull(event.getChunkCache());
			Objects.requireNonNull(event.getVisGraph());
			Objects.requireNonNull(event.getTileEntitiesWithGlobalRenderers());
			Objects.requireNonNull(event.getRenderGlobal());
			Objects.requireNonNull(event.getBufferBuilderByLayer(BlockRenderLayer.SOLID));
			Objects.requireNonNull(event.getBufferBuilderById(BlockRenderLayer.CUTOUT_MIPPED.ordinal()));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}



	}

}
