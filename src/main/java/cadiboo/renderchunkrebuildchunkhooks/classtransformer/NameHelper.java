package cadiboo.renderchunkrebuildchunkhooks.classtransformer;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class NameHelper {

	// Deobfuscated Name | SRG Name | Obfuscated Name (Optifine Name)
	public static final Table<String, String, String> names = HashBasedTable.create();

	static {
		/** fields */
		{
			// net/minecraft/client/renderer/chunk/RenderChunk.position
			names.put("position", "field_178586_f", "o");
			// net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal
			names.put("renderGlobal", "field_178589_e", "e");
			// net/minecraft/client/renderer/chunk/RenderChunk.worldView
			names.put("worldView", "field_189564_r", "r");
			// net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask
			names.put("lockCompileTask", "field_178587_g", "f");
			// net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities
			names.put("setTileEntities", "field_181056_j", "i");
		}

		/** static fields */
		{
			// net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated
			names.put("renderChunksUpdated", "field_178592_a", "a");
			// net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance
			names.put("instance", "field_147556_a", "a");
		}

	}

	public static String getName(final String deObfName) {
		return deObfName;
	}

}
