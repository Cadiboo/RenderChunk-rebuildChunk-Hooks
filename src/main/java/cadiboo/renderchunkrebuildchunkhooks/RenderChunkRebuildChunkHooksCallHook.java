package cadiboo.renderchunkrebuildchunkhooks;

import java.util.Map;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@SortingIndex(1010)
public class RenderChunkRebuildChunkHooksCallHook implements IFMLCallHook {

	@Override
	public Void call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {
		RenderChunkRebuildChunkHooksClassPatchManager.getInstance().setup(FMLLaunchHandler.side());
	}

}
