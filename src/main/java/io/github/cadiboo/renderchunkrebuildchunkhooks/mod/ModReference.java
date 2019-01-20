package io.github.cadiboo.renderchunkrebuildchunkhooks.mod;

import io.github.cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin;

import java.net.MalformedURLException;
import java.net.URL;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_LOGGER;

/**
 * @author Cadiboo
 */
@SuppressWarnings("WeakerAccess")
public class ModReference {

	public static final String MOD_ID = "render_chunk-rebuild_chunk-hooks";
	public static final String MOD_NAME = "RenderChunk rebuildChunk Hooks";
	public static final String MOD_VERSION = "@VERSION@";
	public static final String CERTIFICATE_FINGERPRINT = "@FINGERPRINT@";
	public static final String DEPENDENCIES = "required-after:minecraft;" +
			"required-after:forge@[14.23.5.2768,);"
//			"required-after:forge@[14.23.5.2795,);"
			;
	public static final URL UPDATE_JSON_URL;
	static {
		URL url = null;
		try {
			url = new URL("https://raw.githubusercontent.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/master/update.json");
		} catch (MalformedURLException e) {
			MOD_LOGGER.error("Error setting update.json url", e);
		}
		UPDATE_JSON_URL = url;
	}

	/* Other Coremods can set these variables and call our hooks with reflection, if they so choose*/
	//START MODIFIABLE FIELDS
	/**
	 * Required by RebuildChunkBlockEvent if BetterFoliage is present
	 * Does NOT disable removing BetterFoliage's modifications relayed to BlockRenderInLayer
	 */
	public static final boolean REMOVE_BetterFoliagesBlockRenderModifications = RenderChunkRebuildChunkHooksLoadingPlugin.BETTER_FOLIAGE;
	public static final boolean INJECT_RebuildChunkPreEvent = true;
	public static final boolean INJECT_RebuildChunkBlockRenderInLayerEvent = true;
	public static final boolean INJECT_RebuildChunkBlockRenderTypeAllowsRenderEvent = true;
	public static final boolean INJECT_RebuildChunkBlockEvent = true;
	public static final boolean INJECT_RebuildChunkPostEvent = true;
	//END MODIFIABLE FIELDS

}
