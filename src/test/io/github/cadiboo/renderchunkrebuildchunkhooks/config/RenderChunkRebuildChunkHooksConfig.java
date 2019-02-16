package io.github.cadiboo.renderchunkrebuildchunkhooks.config;

import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.ModReference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public final class RenderChunkRebuildChunkHooksConfig {

	public static final String CONFIG_VERSION = "1.0.0";
	public static final String LANG_PREFIX = ModReference.MOD_ID + ".config.";

	public static final String POST_EVENTS_CATEGORY = "hooks";
	public static final String TWEAK_CATEGORY = "tweaks";

	public static Configuration config;

	private static boolean enableHooks = true;
	private static boolean reloadChunksOnConfigChange = true;

	private static boolean enableRebuildChunkPreEvent = true;
	private static boolean enableRebuildChunkBlockRenderInLayerEvent = true;
	private static boolean enableRebuildChunkBlockTypeAllowsRenderEvent = true;
	private static boolean enableRebuildChunkBlockEvent = true;
	private static boolean enableRebuildChunkPostEvent = true;

	private static boolean tweakRebuildChunkBlockRenderInType = true;

	public static void load(File file) {
		config = new Configuration(file, CONFIG_VERSION);
		sync();
	}

	public static void sync() {
		//general
		{
			enableHooks = config.get(
					CATEGORY_GENERAL, "areHooksEnabled", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "areHooksEnabled").getBoolean();
			reloadChunksOnConfigChange = config.get(
					CATEGORY_GENERAL, "shouldReloadChunksOnConfigChange", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "shouldReloadChunksOnConfigChange").getBoolean();
		}

		//hooks
		{
			enableRebuildChunkPreEvent = config.get(
					POST_EVENTS_CATEGORY, "enableRebuildChunkPreEvent", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkPreEvent").getBoolean();
			enableRebuildChunkBlockRenderInLayerEvent = config.get(
					POST_EVENTS_CATEGORY, "enableRebuildChunkBlockRenderInLayerEvent", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockRenderInLayerEvent").getBoolean();
			enableRebuildChunkBlockTypeAllowsRenderEvent = config.get(
					POST_EVENTS_CATEGORY, "enableRebuildChunkBlockRenderInTypeEvent", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockRenderInTypeEvent").getBoolean();
			enableRebuildChunkBlockEvent = config.get(
					POST_EVENTS_CATEGORY, "enableRebuildChunkBlockEvent", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockEvent").getBoolean();
			enableRebuildChunkPostEvent = config.get(
					POST_EVENTS_CATEGORY, "enableRebuildChunkPostEvent", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkPostEvent").getBoolean();
		}

		//tweaks
		{
			tweakRebuildChunkBlockRenderInType = config.get(
					TWEAK_CATEGORY, "tweakRebuildChunkBlockRenderInType", true
			).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "tweakRebuildChunkBlockRenderInType").getBoolean();
		}

		if (config.hasChanged()) {
			config.save();
		}
	}

	//general

	public static boolean areHooksEnabled() {
		return enableHooks;
	}

	public static boolean shouldReloadChunksOnConfigChange() {
		return reloadChunksOnConfigChange;
	}

	//hooks

	public static boolean shouldPostRebuildChunkPreEvent() {
		return enableHooks && enableRebuildChunkPreEvent;
	}

	public static boolean shouldPostRebuildChunkBlockRenderInLayerEvent() {
		return areHooksEnabled() && enableRebuildChunkBlockRenderInLayerEvent;
	}

	public static boolean shouldPostRebuildChunkBlockRenderInTypeEvent() {
		return areHooksEnabled() && enableRebuildChunkBlockTypeAllowsRenderEvent;
	}

	public static boolean shouldPostRebuildChunkBlockEvent() {
		return areHooksEnabled() && enableRebuildChunkBlockEvent;
	}

	public static boolean shouldPostRebuildChunkPostEvent() {
		return areHooksEnabled() && enableRebuildChunkPostEvent;
	}

	//tweaks

	public static boolean shouldTweakCanBlockRenderInType() {
		return tweakRebuildChunkBlockRenderInType;
	}

}
