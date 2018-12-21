package io.github.cadiboo.renderchunkrebuildchunkhooks.debug;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import com.google.common.base.Preconditions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod(modid = RebuildChunkBlockRenderInLayerEventTest.MODID, name = "RebuildChunkBlockRenderInLayerEventTest", version = "1.0", acceptableRemoteVersions = "*")
@Mod.EventBusSubscriber
public final class RebuildChunkBlockRenderInLayerEventTest {

	public static final String MODID = "rebuild_chunk_block_render_in_layer_event_test";
	public static final boolean ENABLED = false;

	@SubscribeEvent
	public static void onRebuildChunkBlockRenderInLayerEvent(final RebuildChunkBlockRenderInLayerEvent event) {
		if (!ENABLED) {
			return;
		}

		/**
		 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
		 * @param chunkCache                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition       the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param blockPos                  the {@link BlockPos.MutableBlockPos position} of the block being assessed
		 * @param blockState                the {@link IBlockState state} of the block being assessed
		 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
		 */

		Preconditions.checkNotNull(event.getRenderChunk());
		Preconditions.checkNotNull(event.getChunkCache());
		Preconditions.checkNotNull(event.getCompiledChunk());
		Preconditions.checkNotNull(event.getBlockRendererDispatcher());
		Preconditions.checkNotNull(event.getRenderChunkPosition());
		Preconditions.checkNotNull(event.getVisGraph());
		Preconditions.checkNotNull(event.getBlockPos());
		Preconditions.checkNotNull(event.getBlockState());
		Preconditions.checkNotNull(event.getBlockRenderLayer());

		if (new Random().nextBoolean()) {
			event.setResult(Event.Result.DENY);
		}

	}

}
