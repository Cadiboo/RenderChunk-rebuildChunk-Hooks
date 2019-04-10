package io.github.cadiboo.renderchunkrebuildchunkhooks;

import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkCanBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.HookConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.util.ModRefs.MOD_ID;
import static net.minecraft.util.BlockRenderLayer.CUTOUT;
import static net.minecraft.util.BlockRenderLayer.CUTOUT_MIPPED;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;
import static net.minecraftforge.eventbus.api.Event.Result.ALLOW;

@EventBusSubscriber(modid = MOD_ID, value = CLIENT)
public final class CorrectBlockRenderLayerTester {

	private static boolean enabled = true;
	static {
		if (enabled) {
			HookConfig.enableRebuildChunkCanBlockRenderInLayerEvent();
		}
	}

	@SubscribeEvent
	public static void onRebuildChunkCanBlockRenderInLayerEvent(final RebuildChunkCanBlockRenderInLayerEvent event) {
		if (!enabled) {
			return;
		}

		if (event.getBlockRenderLayer() == getTargetLayer()) {
			event.setResult(ALLOW);
		}
	}

	private static BlockRenderLayer getTargetLayer() {
		return Minecraft.getInstance().gameSettings.mipmapLevels > 0 ? CUTOUT : CUTOUT_MIPPED;
	}

}
