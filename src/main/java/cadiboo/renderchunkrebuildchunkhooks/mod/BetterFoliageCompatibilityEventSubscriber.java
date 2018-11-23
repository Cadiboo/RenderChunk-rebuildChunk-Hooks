package cadiboo.renderchunkrebuildchunkhooks.mod;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BetterFoliageCompatibilityEventSubscriber {

	public static final Logger LOGGER = LogManager.getLogger();

	public BetterFoliageCompatibilityEventSubscriber() {

		LOGGER.info("instantiating " + this.getClass().getSimpleName());

	}

	//because BetterFoliage's name starts with "b" it will get called early anyway, and I want to keep HIGHEST and HIGH for mods that actually need them
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public static void callBetterFoliageHooks_renderWorldBlock_onRebuildChunkBlockEvent(final RebuildChunkBlockEvent event) {

		mods.betterfoliage.client.Hooks.renderWorldBlock(event.getBlockRendererDispatcher(), event.getBlockState(), event.getBlockPos(), event.getChunkCache(), event.getBufferBuilder(), event.getBlockRenderLayer());

		event.setCanceled(true);
	}

	//because BetterFoliage's name starts with "b" it will get called early anyway, and I want to keep HIGHEST and HIGH for mods that actually need them
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = false)
	public static void callBetterFoliageHooks_canRenderBlockInLayer_onRebuildChunkBlockRenderInLayerEvent(final RebuildChunkBlockRenderInLayerEvent event) {

		mods.betterfoliage.client.Hooks.canRenderBlockInLayer(event.getBlockState().getBlock(), event.getBlockState(), event.getBlockRenderLayer());

		event.setCanceled(true);
	}

}
