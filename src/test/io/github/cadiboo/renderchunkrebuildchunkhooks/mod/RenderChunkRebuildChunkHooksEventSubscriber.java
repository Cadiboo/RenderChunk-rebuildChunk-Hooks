package io.github.cadiboo.renderchunkrebuildchunkhooks.mod;

import io.github.cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.ModReference.*;

@SideOnly(Side.CLIENT)
public final class RenderChunkRebuildChunkHooksEventSubscriber {

	/**
	 * Inject the new values and save to the config file when the config has been changed from the GUI.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MOD_ID)) {
			RenderChunkRebuildChunkHooksConfig.sync();
			if (RenderChunkRebuildChunkHooksConfig.shouldReloadChunksOnConfigChange()) {
				if (Minecraft.getMinecraft().renderGlobal != null) {
					Minecraft.getMinecraft().renderGlobal.loadRenderers();
				}
			}
		}
	}

}
