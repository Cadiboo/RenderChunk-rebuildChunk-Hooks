package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import com.google.common.base.Preconditions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
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

		/**
		 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
		 * @param renderGlobal                    the {@link RenderGlobal} passed in
		 * @param chunkCache                      the {@link ChunkCache} passed in
		 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in
		 * @param compiledchunk                   the {@link CompiledChunk} passed in
		 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in
		 * @param blockState                      the {@link IBlockState state} of the block being rendered
		 * @param blockPos                        the {@link BlockPos.MutableBlockPos position} of the block being rendered
		 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
		 * @param renderChunkPosition             the {@link BlockPos.MutableBlockPos position} passed in
		 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
		 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
		 * @param x                               the translation X passed in
		 * @param y                               the translation Y passed in
		 * @param z                               the translation Z passed in
		 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in
		 * @param visGraph                        the {@link VisGraph} passed in
		 */

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