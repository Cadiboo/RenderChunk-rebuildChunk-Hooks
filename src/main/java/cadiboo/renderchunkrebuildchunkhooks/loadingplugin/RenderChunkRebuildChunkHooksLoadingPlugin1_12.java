package cadiboo.renderchunkrebuildchunkhooks.loadingplugin;

import java.util.Map;

import org.apache.logging.log4j.LogManager;

import cadiboo.renderchunkrebuildchunkhooks.RenderChunkRebuildChunkHooksRenderChunkClassTransformer;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
//import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.*;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name("Rebuild Chunk Hooks")
@MCVersion("1.12")
@TransformerExclusions(value = "cadiboo.renderchunkrebuildchunkhooks.")
/** How early your core mod is called - Use > 1000 to work with srg names */
//@SortingIndex(value = 1001)
@SortingIndex(value = 0xBADC0DE)
public class RenderChunkRebuildChunkHooksLoadingPlugin1_12 implements IFMLLoadingPlugin {

	public static final String CORE_MARKER = "RenderChunkRebuildChunkHooksLoaded";

	public RenderChunkRebuildChunkHooksLoadingPlugin1_12() {
		LogManager.getLogger().info("RenderChunkRebuildChunkHooksLoadingPlugin at version " + this.getVersion());
		Launch.blackboard.put(CORE_MARKER, this.getVersion());
	}

	public String getVersion() {
		return this.getClass().getSimpleName().replace("RenderChunkRebuildChunkHooksLoadingPlugin", "").replace("_", ".");
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
