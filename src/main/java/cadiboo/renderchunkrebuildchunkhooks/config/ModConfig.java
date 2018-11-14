package cadiboo.renderchunkrebuildchunkhooks.config;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer.MOD_ID;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid = MOD_ID)
@LangKey(MOD_ID + ".config.title")
public class ModConfig {

	// FIXME TODO why doesn't this work??? Do I need to use the old system without annotations?

	@Comment("Numbers")
	public static final Numbers numbers = new Numbers(1, 2.5f, 3.1d);

	public static class Numbers {

		public Numbers(final int int_, final float float_, final double double_) {
			this.int_ = int_;
			this.float_ = float_;
			this.double_ = double_;
		}

		@Comment("An Integer (int) type number")
		public int		int_;
		@Comment("A Float (floating point) type number")
		public float	float_;
		@Comment("A Double (double length floating point) type number")
		public double	double_;
	}

	@Name("Enabled")
	@Comment("If any of the events provieded by this mod will be posted to the Forge EventBus")
	public static boolean isEnabled = true;

	@Name("Enable RebuildChunkPreEvent Hook")
	@Comment({

			"If the RebuildChunkPreEvent will be posted to the Forge EventBus"

	})
	public static boolean enableRebuildChunkPreEvent = true;

	@Name("Enable RebuildChunkAllBlocksEvent Hook")
	@Comment({

			"If the RebuildChunkAllBlocksEvent will be posted to the Forge EventBus"

	})
	public static boolean enableRebuildChunkAllBlocksEvent = true;

	@Name("Enable RebuildChunkBlockRenderInLayerEvent Hook")
	@Comment({

			"If the RebuildChunkBlockRenderInLayerEvent will be posted to the Forge EventBus"

	})
	public static boolean enableRebuildChunkBlockRenderInLayerEvent = true;

	@Name("Enable RebuildChunkBlockEvent Hook")
	@Comment({

			"If the RebuildChunkBlockEvent should be will to the Forge EventBus"

	})
	public static boolean enableRebuildChunkBlockEvent = true;

	@Mod.EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
	public static class ConfigEventSubscriber {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MOD_ID)) {
				ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
			}
		}
	}

}