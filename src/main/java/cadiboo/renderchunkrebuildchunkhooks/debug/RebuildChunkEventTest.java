/*
 * Minecraft Forge
 * Copyright (c) 2016-2018.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package cadiboo.renderchunkrebuildchunkhooks.debug;

import java.util.Random;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Mod(modid = RebuildChunkEventTest.MODID, name = "RebuildChunkEventTest", version = "1.0", acceptableRemoteVersions = "*", clientSideOnly = true)
public class RebuildChunkEventTest {

	public static final String MODID = "rebuild_chunk_event_test";

	public static final boolean ENABLED = true;

	@SubscribeEvent
	public static void onRebuildChunkEvent(final RebuildChunkPreEvent event) {
		if (!ENABLED) {
			return;
		}

		if (new Random().nextInt(10) == 0) {
			event.setCanceled(true);

			final BlockPos pos = event.getRenderChunkPosition();

			final BufferBuilder bufferBuilder = event.getGenerator().getRegionRenderCacheBuilder().getWorldRendererByLayer(BlockRenderLayer.SOLID);
			bufferBuilder.begin(7, DefaultVertexFormats.BLOCK);
			bufferBuilder.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
			Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(Blocks.STONE.getDefaultState(), pos, event.getWorldView(), bufferBuilder);
			bufferBuilder.finishDrawing();

			RenderChunkRebuildChunkHooksHooks.compiledChunkSetLayerUsed(event.getCompiledChunk(), BlockRenderLayer.SOLID);

			final SetVisibility setVisibility = new SetVisibility();
			setVisibility.setAllVisible(true);

			event.getCompiledChunk().setVisibility(setVisibility);

//			final BlockPos chunkRenderPos = event.getRenderChunkPosition();
//			final ChunkCompileTaskGenerator generator = event.getGenerator();
//			final BlockRenderLayer blockRenderLayer = BlockRenderLayer.TRANSLUCENT;
////			final IBlockState glass = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.GREEN);
//			final IBlockState glass = Blocks.STAINED_GLASS.getDefaultState();
//			final ChunkCache worldView = event.getWorldView();
//
//			BlockPos.getAllInBox(chunkRenderPos, chunkRenderPos.add(15, 15, 15)).forEach((pos) -> {
//				final BufferBuilder bufferBuilder = startOrContinueLayer(chunkRenderPos, generator, blockRenderLayer);
//				Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(glass, pos, worldView, bufferBuilder);
//			});
//
//			final BufferBuilder bufferBuilder = startOrContinueLayer(chunkRenderPos, generator, blockRenderLayer);
//			postRenderBlocks(blockRenderLayer, chunkRenderPos.getX(), chunkRenderPos.getY(), chunkRenderPos.getZ(), bufferBuilder, event.getCompiledChunk());
		}

	}

	/**
	 * @param pos
	 * @param generator
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	private static BufferBuilder startOrContinueLayer(final BlockPos pos, final ChunkCompileTaskGenerator generator, final BlockRenderLayer blockRenderLayer) {
		final BufferBuilder bufferbuilder = getBufferBuilderForBlockRenderLayer(generator, blockRenderLayer);

		if (!generator.getCompiledChunk().isLayerStarted(blockRenderLayer)) {
			generator.getCompiledChunk().setLayerStarted(blockRenderLayer);
			preRenderBlocks(bufferbuilder, pos);
		}

		return bufferbuilder;
	}

	/**
	 * FOR INTERNAL USE ONLY<br>
	 * Sets translation for and tarts the {@link BufferBuilder}
	 *
	 * @param bufferBuilderIn the {@link BufferBuilder} to set translation for and start
	 * @param pos             the pos to get translations from
	 */
	private static void preRenderBlocks(final BufferBuilder bufferBuilderIn, final BlockPos pos) {
		bufferBuilderIn.begin(7, DefaultVertexFormats.BLOCK);
		bufferBuilderIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
	}

	/**
	 * FOR INTERNAL USE ONLY<br>
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer} to get the {@link BufferBuilder}
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	private static BufferBuilder getBufferBuilderForBlockRenderLayer(final ChunkCompileTaskGenerator generator, final BlockRenderLayer blockRenderLayer) {
		return generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer);
	}

	private static void postRenderBlocks(final BlockRenderLayer layer, final float x, final float y, final float z, final BufferBuilder bufferBuilderIn, final CompiledChunk compiledChunkIn) {
		if ((layer == BlockRenderLayer.TRANSLUCENT) && !compiledChunkIn.isLayerEmpty(layer)) {
			bufferBuilderIn.sortVertexData(x, y, z);
			compiledChunkIn.setState(bufferBuilderIn.getVertexState());
		}

		bufferBuilderIn.finishDrawing();
	}

}
