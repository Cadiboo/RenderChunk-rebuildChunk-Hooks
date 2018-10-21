package cadiboo.renderchunkrebuildchunkhooks;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@SortingIndex(1010)
public class RenderChunkRebuildChunkHooksClassTransformer implements IClassTransformer {

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {

		try {
			return RenderChunkRebuildChunkHooksClassPatchManager.getInstance().applyPatch(name, transformedName, basicClass);
		} catch (final Exception e) {
			// TODO: handle exception
		}

		return basicClass;
	}

}
