package cadiboo.renderchunkrebuildchunkhooks;

import net.minecraft.launchwrapper.IClassTransformer;

public class RenderChunkRebuildChunkHooksClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		return basicClass;
	}

}
