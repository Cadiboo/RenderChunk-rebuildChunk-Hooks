package cadiboo.renderchunkrebuildchunkhooks.debug;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import com.google.common.base.Preconditions;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
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
		Preconditions.checkNotNull(event.getWorldView());
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