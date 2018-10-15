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

package cadiboo.renderchunkrebuildchunkhooks.hooks;

import java.util.HashSet;
import java.util.logging.Logger;

import cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooksDummyContainer;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;

public class RenderChunkRebuildChunkHooksHooks {

	public static void dummyEvent() {
		final Logger logger = Logger.getLogger(RenderChunkRebuildChunkHooksDummyContainer.MOD_ID);
		logger.info("dummyEvent");
	}
	
	
	// public static RenderBlockRenderLayerEvent onRenderBlockRenderLayerEvent(final RenderGlobal renderGlobal, final BlockRenderLayer blockRenderLayer, final double partialTicks, final int pass, final Entity entity, final int chunksRendered) {
	// final RenderBlockRenderLayerEvent event = new RenderBlockRenderLayerEvent(renderGlobal, blockRenderLayer, partialTicks, pass, entity, chunksRendered);
	// MinecraftForge.EVENT_BUS.post(event);
	// return event;
	// }

	public static RebuildChunkEvent onRebuildChunkEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final MutableBlockPos position, final float x, final float y, final float z) {
		final RebuildChunkEvent event = new RebuildChunkEvent(renderGlobal, worldView, generator, compiledChunk, position, x, y, z);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static RebuildChunkBlocksEvent onRebuildChunkBlocksEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final Iterable<MutableBlockPos> chunkBlockPositions, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos position, final float x, final float y, final float z, final HashSet tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		final RebuildChunkBlocksEvent event = new RebuildChunkBlocksEvent(renderGlobal, worldView, generator, compiledChunk, chunkBlockPositions, blockRendererDispatcher, position, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

}
