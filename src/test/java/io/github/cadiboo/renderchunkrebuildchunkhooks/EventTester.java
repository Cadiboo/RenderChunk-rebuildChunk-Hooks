package io.github.cadiboo.renderchunkrebuildchunkhooks;

import com.google.common.base.Preconditions;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderTypeBeRenderedEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanFluidRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCheckWorldEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkIsFluidEmptyEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreIterationEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkRenderFluidEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.util.Refs.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public final class EventTester {

	private static boolean enabled = true;
	static {
		HookConfig.enableAll();
	}

	@SubscribeEvent
	public static void onRebuildChunkPreEvent(final RebuildChunkPreEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
	}

	@SubscribeEvent
	public static void onRebuildChunkCheckWorldEvent(final RebuildChunkCheckWorldEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
//		Preconditions.checkNotNull(event.getOriginalWorld(), "world");
//		Preconditions.checkNotNull(event.getWorld(), "worldRef");
	}

	@SubscribeEvent
	public static void onRebuildChunkPreRenderEvent(final RebuildChunkPreRenderEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
//		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
	}

	@SubscribeEvent
	public static void onRebuildChunkPreIterationEvent(final RebuildChunkPreIterationEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
	}

	@SubscribeEvent
	public static void onRebuildChunkIsFluidEmptyEvent(final RebuildChunkIsFluidEmptyEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");

		event.setCanceled(true);
	}

	@SubscribeEvent
	public static void onRebuildChunkCanFluidRenderInLayerEvent(final RebuildChunkCanFluidRenderInLayerEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");
		Preconditions.checkNotNull(event.getBlockRenderLayer(), "blockrenderlayer1");
	}

	@SubscribeEvent
	public static void onRebuildChunkRenderFluidEvent(final RebuildChunkRenderFluidEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");
		Preconditions.checkNotNull(event.getBlockRenderLayer(), "blockrenderlayer1");

		Preconditions.checkNotNull(event.getBufferBuilder(), "bufferbuilder1");
	}

	@SubscribeEvent
	public static void onRebuildChunkCanBlockRenderTypeBeRenderedEvent(final RebuildChunkCanBlockRenderTypeBeRenderedEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");
	}

	@SubscribeEvent
	public static void onRebuildChunkCanBlockRenderInLayerEvent(final RebuildChunkCanBlockRenderInLayerEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");
		Preconditions.checkNotNull(event.getBlockRenderLayer(), "blockrenderlayer1");
	}

	@SubscribeEvent
	public static void onRebuildChunkRenderBlockEvent(final RebuildChunkRenderBlockEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
		Preconditions.checkNotNull(event.getIBlockState(), "iblockstate");
		Preconditions.checkNotNull(event.getBlock(), "block");
		Preconditions.checkNotNull(event.getIFluidState(), "ifluidstate");
		Preconditions.checkNotNull(event.getBlockRenderLayer(), "blockrenderlayer1");

		Preconditions.checkNotNull(event.getBufferBuilder(), "bufferbuilder1");
	}

	@SubscribeEvent
	public static void onRebuildChunkPostIterationEvent(final RebuildChunkPostIterationEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
		Preconditions.checkNotNull(event.getUsedBlockRenderLayers(), "aboolean");
		Preconditions.checkNotNull(event.getRandom(), "random");
		Preconditions.checkNotNull(event.getBlockRendererDispatcher(), "blockrendererdispatcher");
	}

	@SubscribeEvent
	public static void onRebuildChunkPostRenderEvent(final RebuildChunkPostRenderEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
//		Preconditions.checkNotNull(event.getRenderChunkCache(), "lvt_10_1_");
		Preconditions.checkNotNull(event.getVisGraph(), "lvt_11_1_");
		Preconditions.checkNotNull(event.getTileEntitiesWithGlobalRenderers(), "lvt_12_1_");
	}

	@SubscribeEvent
	public static void onRebuildChunkPostEvent(final RebuildChunkPostEvent event) {
		if (!enabled) {
			return;
		}

		Preconditions.checkNotNull(event.getRenderChunk(), "renderChunk");

		Preconditions.checkNotNull(event.getGenerator(), "generator");
		Preconditions.checkNotNull(event.getCompiledChunk(), "compiledchunk");
		Preconditions.checkNotNull(event.getStartPosition(), "blockpos");
		Preconditions.checkNotNull(event.getEndPosition(), "blockpos1");
		Preconditions.checkNotNull(event.getWorld(), "world");
	}

}
