package cadiboo.renderchunkrebuildchunkhooks.loadingplugin;

import java.util.Map;

import cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooksDummyContainer;
import cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooksRenderChunkClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
//import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.*;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;

@Name("Rebuild Chunk Hooks")
@MCVersion("1.12")
public class RenderChunkRebuildChunkHooksLoadingPlugin1_12 implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{RenderChunkRebuildChunkHooksRenderChunkClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return RenderChunkRebuildChunkHooksDummyContainer.class.getName();
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
