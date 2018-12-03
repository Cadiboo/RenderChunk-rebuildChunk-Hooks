package cadiboo.renderchunkrebuildchunkhooks.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;
import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class RenderChunkRebuildChunkHooksConfig {

	public static final String CONFIG_VERSION = "0.0.0";
	public static final String LANG_PREFIX = MOD_ID + ".config.";

	public static final String POST_EVENTS_CATEGORY = "hooks";
	public static final String TWEAK_CATEGORY = "tweaks";

	public static Configuration config;

	private static boolean enableHooks = true;
	private static boolean reloadChunksOnConfigChange = true;

	private static boolean enableRebuildChunkPreEvent = true;
	private static boolean enableRebuildChunkBlockRenderInLayerEvent = true;
	private static boolean enableRebuildChunkBlockRenderInTypeEvent = true;
	private static boolean enableRebuildChunkBlockEvent = true;
	private static boolean enableRebuildChunkPostEvent = true;

	private static boolean tweakRebuildChunkBlockRenderInType = true;

	public static void load(File file) {
		config = new Configuration(file, CONFIG_VERSION);
		sync();
	}

	public static void sync() {        //general
		enableHooks = config.get(
				CATEGORY_GENERAL, "enableHooks", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableHooks").getBoolean();
		reloadChunksOnConfigChange = config.get(
				CATEGORY_GENERAL, "reloadChunksOnConfigChange", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "reloadChunksOnConfigChange").getBoolean();

		//hooks
		enableRebuildChunkPreEvent = config.get(
				POST_EVENTS_CATEGORY, "enableRebuildChunkPreEvent", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkPreEvent").getBoolean();
		enableRebuildChunkBlockRenderInLayerEvent = config.get(
				POST_EVENTS_CATEGORY, "enableRebuildChunkBlockRenderInLayerEvent", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockRenderInLayerEvent").getBoolean();
		enableRebuildChunkBlockRenderInTypeEvent = config.get(
				POST_EVENTS_CATEGORY, "enableRebuildChunkBlockRenderInTypeEvent", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockRenderInTypeEvent").getBoolean();
		enableRebuildChunkBlockEvent = config.get(
				POST_EVENTS_CATEGORY, "enableRebuildChunkBlockEvent", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkBlockEvent").getBoolean();
		enableRebuildChunkPostEvent = config.get(
				POST_EVENTS_CATEGORY, "enableRebuildChunkPostEvent", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "enableRebuildChunkPostEvent").getBoolean();

		//tweaks
		tweakRebuildChunkBlockRenderInType = config.get(
				TWEAK_CATEGORY, "tweakRebuildChunkBlockRenderInType", true
		).setLanguageKey(RenderChunkRebuildChunkHooksConfig.LANG_PREFIX + "tweakRebuildChunkBlockRenderInType").getBoolean();

		if (config.hasChanged()) {
			config.save();
		}
	}

	//general

	public static boolean enableHooks() {
		return enableHooks;
	}

	public static boolean reloadChunksOnConfigChange() {
		return reloadChunksOnConfigChange;
	}

	//hooks

	public static boolean shouldPostRebuildChunkPreEvent() {
		return enableHooks && enableRebuildChunkPreEvent;
	}

	public static boolean shouldPostRebuildChunkBlockRenderInLayerEvent() {
		return enableHooks() && enableRebuildChunkBlockRenderInLayerEvent;
	}

	public static boolean shouldPostRebuildChunkBlockRenderInTypeEvent() {
		return enableHooks() && enableRebuildChunkBlockRenderInTypeEvent;
	}

	public static boolean shouldPostRebuildChunkBlockEvent() {
		return enableHooks() && enableRebuildChunkBlockEvent;
	}

	public static boolean shouldPostRebuildChunkPostEvent() {
		return enableHooks() && enableRebuildChunkPostEvent;
	}

	//tweaks

	public static boolean shouldTweakCanBlockRenderInType() {
		return tweakRebuildChunkBlockRenderInType;
	}

}
