package cadiboo.renderchunkrebuildchunkhooks.core;

import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Name(RenderChunkRebuildChunkHooksDummyContainer.MOD_NAME)
@MCVersion("1.12.2")
@TransformerExclusions({ "cadiboo.renderchunkrebuildchunkhooks.core." })
/** How early your core mod is called - Use > 1000 to work with srg names */
//@SortingIndex(value = 1001)
@SortingIndex(value = 0xBADC0DE)
//put in _VM_ arguments -Dfml.coreMods.load=cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2
public class RenderChunkRebuildChunkHooksLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

	public static final String CORE_MARKER = RenderChunkRebuildChunkHooksDummyContainer.MOD_NAME + " Loaded";

	public static final Logger LOGGER = LogManager.getLogger();

	public static boolean DEOBFUSCATED = false;
	public static boolean OPTIFINE     = false;

	public RenderChunkRebuildChunkHooksLoadingPlugin1_12_2() {

		LOGGER.info(this.getClass().getSimpleName() + " at version " + this.getVersion());
		Launch.blackboard.put(CORE_MARKER, this.getVersion());
	}

	public String getVersion() {

		return "1.12.2";
	}

	@Override
	public String[] getASMTransformerClass() {
		//		if (OPTIFINE) {
		return new String[] { RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine.class.getName() };
		//		}
		//		return new String[] { RenderChunkRebuildChunkHooksRenderChunkClassTransformer.class.getName() };
		//		return new String[0];
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

		DEOBFUSCATED = (boolean) data.get("runtimeDeobfuscationEnabled");
		//		try {
		//			final Class<?> optifineConfig = Class.forName("Config", false, Loader.instance().getModClassLoader());
		//			final String optifineVersion = (String) optifineConfig.getField("VERSION").get(null);
		//			OPTIFINE = true;
		//			LOGGER.info("RenderChunkRebuildChunkHooksLoadingPlugin1_12_2 has detected optifine {}, enabling compatibility features", optifineVersion);
		//		} catch (final Exception e) {
		//			OPTIFINE = false;
		//		}

		LOGGER.info("Pre-loading event classes...");
		RebuildChunkPreEvent.class.getName();
		RebuildChunkBlockRenderInLayerEvent.class.getName();
		RebuildChunkBlockEvent.class.getName();
		LOGGER.info("Sucessfully Pre-loaded event classes");
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
