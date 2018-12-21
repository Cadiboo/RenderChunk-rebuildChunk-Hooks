package io.github.cadiboo.renderchunkrebuildchunkhooks.compatibility;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import mods.betterfoliage.client.Hooks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterFoliageCompatibilityEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger();

	public BetterFoliageCompatibilityEventSubscriber() {
		LOGGER.debug("Instantiating " + this.getClass().getSimpleName());
	}

	//because BetterFoliage's name starts with "b" it will get called early anyway, and I want to keep HIGHEST and HIGH for mods that actually need them
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public void callBetterFoliageHooks_renderWorldBlock_onRebuildChunkBlockEvent(final RebuildChunkBlockEvent event) {
		final BlockRenderLayer blockRenderLayer = event.getBlockRenderLayer();

		boolean used = Hooks.renderWorldBlock(event.getBlockRendererDispatcher(), event.getBlockState(), event.getBlockPos(), event.getChunkCache(), event.getBufferBuilder(), blockRenderLayer);

		event.getUsedBlockRenderLayers()[blockRenderLayer.ordinal()] |= used;

		event.setCanceled(true);
	}

	//because BetterFoliage's name starts with "b" it will get called early anyway, and I want to keep HIGHEST and HIGH for mods that actually need them
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public void callBetterFoliageHooks_canRenderBlockInLayer_onRebuildChunkBlockRenderInLayerEvent(final RebuildChunkBlockRenderInLayerEvent event) {
		boolean shouldRender = Hooks.canRenderBlockInLayer(event.getBlockState().getBlock(), event.getBlockState(), event.getBlockRenderLayer());

		if (shouldRender) {
			event.setResult(Event.Result.ALLOW);
		} else {
			event.setResult(Event.Result.DENY);
		}
	}

}
