package cadiboo.renderchunkrebuildchunkhooks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;

@Config(modid = MOD_ID)
@LangKey(MOD_ID + ".config.title")
public class ModConfig {

	@LangKey(MOD_ID + ".config.enabled")
	public static boolean isEnabled = true;

	//

	@LangKey(MOD_ID + ".config.enable_rebuild_chunk_pre_event_hook")
	public static boolean enableRebuildChunkPreEvent = true;

	@LangKey(MOD_ID + ".config.enable_rebuild_chunk_block_render_in_layer_event_hook")
	public static boolean enableRebuildChunkBlockRenderInLayerEvent = true;

	@LangKey(MOD_ID + ".config.enable_rebuild_chunk_block_render_in_type_event_hook")
	public static boolean enableRebuildChunkBlockRenderInTypeEvent = true;

	@LangKey(MOD_ID + ".config.tweak_rebuild_chunk_block_render_in_type")
	public static boolean tweakRebuildChunkBlockRenderInType = true;

	@LangKey(MOD_ID + ".config.enable_rebuild_chunk_block_event_hook")
	public static boolean enableRebuildChunkBlockEvent = true;

	@LangKey(MOD_ID + ".config.enable_rebuild_chunk_post_event_hook")
	public static boolean enableRebuildChunkPostEvent = true;

	//

	public static boolean shouldPostRebuildChunkPreEvent() {

		return isEnabled && enableRebuildChunkPreEvent;

	}

	public static boolean shouldPostRebuildChunkBlockRenderInLayerEvent() {

		return isEnabled && enableRebuildChunkBlockRenderInLayerEvent;

	}

	public static boolean shouldPostRebuildChunkBlockRenderInTypeEvent() {

		return isEnabled && enableRebuildChunkBlockRenderInTypeEvent;

	}

	public static boolean shouldTweakCanBlockRenderInType() {

		return tweakRebuildChunkBlockRenderInType;

	}

	public static boolean shouldPostRebuildChunkBlockEvent() {

		return isEnabled && enableRebuildChunkBlockEvent;

	}

	public static boolean shouldPostRebuildChunkPostEvent() {

		return isEnabled && enableRebuildChunkPostEvent;

	}

}