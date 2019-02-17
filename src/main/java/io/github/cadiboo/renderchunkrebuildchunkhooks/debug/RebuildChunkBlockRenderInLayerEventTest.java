package io.github.cadiboo.renderchunkrebuildchunkhooks.debug;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildCanBlockRenderInLayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.Random;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.debug.RebuildChunkBlockRenderInLayerEventTest.Config.ENABLED;

@Mod("rebuild_chunk_block_render_in_layer_event_test")
@Mod.EventBusSubscriber
public final class RebuildChunkBlockRenderInLayerEventTest {

	public static class Config {

		public static boolean ENABLED = true;

	}

	@SubscribeEvent
	public static void onRebuildChunkBlockRenderInLayerEvent(final RebuildCanBlockRenderInLayerEvent event) {
		if (!ENABLED) {
			return;
		}

//		@Nonnull final RenderChunk renderChunk,
//		final float x,
//		final float y,
//		final float z,
//		@Nonnull final ChunkRenderTask generator,
//		@Nonnull final CompiledChunk compiledChunk,
//		@Nonnull final BlockPos renderChunkStartPosition,
//		@Nonnull final BlockPos renderChunkEndPosition,
//		@Nonnull final World world,
//		@Nonnull final RenderChunkCache renderChunkCache,
//		@Nonnull final VisGraph visGraph,
//		@Nonnull final HashSet tileEntitiesWithGlobalRenderers,
//		@Nonnull final boolean[] usedBlockRenderLayers,
//		@Nonnull final Random random,
//		@Nonnull final BlockRendererDispatcher blockRendererDispatcher,
//		@Nonnull final BlockPos blockPos,
//		@Nonnull final IBlockState blockState,
//		@Nonnull final Block block,
//		@Nonnull final BlockRenderLayer blockRenderLayer

		try {
			Objects.requireNonNull(event.getRenderChunk());
			Objects.requireNonNull(event.getGenerator());
			Objects.requireNonNull(event.getCompiledChunk());
			Objects.requireNonNull(event.getRenderChunkStartPosition());
			Objects.requireNonNull(event.getRenderChunkEndPosition());
			Objects.requireNonNull(event.getWorld());
			Objects.requireNonNull(event.getRenderChunkCache());
			Objects.requireNonNull(event.getVisGraph());
			Objects.requireNonNull(event.getTileEntitiesWithGlobalRenderers());
			Objects.requireNonNull(event.getUsedBlockRenderLayers());
			Objects.requireNonNull(event.getRandom());
			Objects.requireNonNull(event.getBlockRendererDispatcher());
			Objects.requireNonNull(event.getBlockPos());
			Objects.requireNonNull(event.getBlockState());
			Objects.requireNonNull(event.getBlock());
			Objects.requireNonNull(event.getBlockRenderLayer());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		if (new Random().nextInt(10) == 0) {
			event.setResult(Event.Result.DENY);
		}

	}

}
