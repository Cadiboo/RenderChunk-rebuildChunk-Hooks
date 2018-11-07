package cadiboo.renderchunkrebuildchunkhooks.classtransformer;

import java.util.HashMap;

import net.minecraft.util.Tuple;

public class NameHelper {

	// Deobfuscated Name, SRG Name, Obfuscated Name (Optifine Name)
	public static final HashMap<String, Tuple<String, String>>	names			= new HashMap<>();
	private static boolean										optifineExists	= false;

	static {
		/** fields */
		{
			// net/minecraft/client/renderer/chunk/RenderChunk.position
			names.put("position", new Tuple<>("field_178586_f", "o"));
			// net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal
			names.put("renderGlobal", new Tuple<>("field_178589_e", "e"));
			// net/minecraft/client/renderer/chunk/RenderChunk.worldView
			names.put("worldView", new Tuple<>("field_189564_r", "r"));
			// net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask
			names.put("lockCompileTask", new Tuple<>("field_178587_g", "f"));
			// net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities
			names.put("setTileEntities", new Tuple<>("field_181056_j", "i"));
		}

		/** static fields */
		{
			// net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated
			names.put("renderChunksUpdated", new Tuple<>("field_178592_a", "a"));
			// net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance
			names.put("instance", new Tuple<>("field_147556_a", "a"));
		}

	}

	public static String getName(final String deObfName) {
		final Tuple<String, String> namePair = names.get(deObfName);

		if (namePair == null) {
			return deObfName;
		}

		if (optifineExists) {
			return namePair.getSecond();
		}

		if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEOBFUSCATED) {
			return deObfName;
		}

		return namePair.getFirst();

	}

}
