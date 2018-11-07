package cadiboo.renderchunkrebuildchunkhooks.loadingplugin;

import java.util.Map;

import org.apache.logging.log4j.LogManager;

import cadiboo.renderchunkrebuildchunkhooks.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformer;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name(RenderChunkRebuildChunkHooksDummyContainer.MOD_NAME)
@MCVersion("1.12.2")
@TransformerExclusions(value = "cadiboo.renderchunkrebuildchunkhooks.")
/** How early your core mod is called - Use > 1000 to work with srg names */
//@SortingIndex(value = 1001)
@SortingIndex(value = 0xBADC0DE)
//put in _VM_ arguments -Dfml.coreMods.load=cadiboo.renderchunkrebuildchunkhooks.loadingplugin.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2
public class RenderChunkRebuildChunkHooksLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

	public static final String CORE_MARKER = RenderChunkRebuildChunkHooksDummyContainer.MOD_NAME + " Loaded";

	public RenderChunkRebuildChunkHooksLoadingPlugin1_12_2() {
		LogManager.getLogger().info(this.getClass().getSimpleName() + " at version " + this.getVersion());
		Launch.blackboard.put(CORE_MARKER, this.getVersion());
	}

	public String getVersion() {
		return this.getClass().getSimpleName().replace(this.getClass().getSimpleName().split("1")[1], "").replace("_", ".");
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { RenderChunkRebuildChunkHooksRenderChunkClassTransformer.class.getName() };
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
