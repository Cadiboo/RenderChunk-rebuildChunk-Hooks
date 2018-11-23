package cadiboo.renderchunkrebuildchunkhooks.core;

import cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge;
import cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyContainer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
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

	public static ObfuscationHelper.ObfuscationLevel OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.OBFUSCATED;

	public static boolean OPTIFINE          = false;
	public static boolean OptifineCheckDone = false;

	public static boolean BETTER_FOLIAGE         = false;
	public static boolean BetterFoliageCheckDone = false;

	public RenderChunkRebuildChunkHooksLoadingPlugin1_12_2() {

		LOGGER.info(this.getClass().getSimpleName() + " at version " + this.getVersion());
		Launch.blackboard.put(CORE_MARKER, this.getVersion());
	}

	public String getVersion() {

		return "1.12.2";
	}

	@Override
	public String[] getASMTransformerClass() {

		detectOtherCoremods();

		if (OPTIFINE) {
			return new String[] { "cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine" };
		} else {
			return new String[] { RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge.class.getName() };
		}
		//		return new String[0];
	}

	private void detectOtherCoremods() {

		if (! OptifineCheckDone) {
			try {
				final Class<?> optifineConfig = Class.forName("Config", false, Loader.instance().getModClassLoader());
				OPTIFINE = true;
				OptifineCheckDone = true;
				LOGGER.info(this.getClass().getSimpleName() + " has detected Optifine, using Optifine compatible ClassTransformer");
			} catch (final Exception e) {
				OPTIFINE = false;
				OptifineCheckDone = true;
				LOGGER.info(this.getClass().getSimpleName() + " has not found Optifine, using normal (Forge) ClassTransformer");
			}
		}

		if (! BetterFoliageCheckDone) {
			try {
				final Class<?> betterFollageClient = Class.forName("mods.betterfoliage.client.Hooks", false, Loader.instance().getModClassLoader());
				BETTER_FOLIAGE = true;
				BetterFoliageCheckDone = true;
				LOGGER.info(this.getClass().getSimpleName() + " has detected BetterFolliage, compatibility features will be enabled");
			} catch (final Exception e) {
				BETTER_FOLIAGE = false;
				BetterFoliageCheckDone = true;
				LOGGER.info(this.getClass().getSimpleName() + " has not found BetterFolliage");
			}
		}

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

		final boolean runtimeDeobfuscationEnabled = (boolean) data.get("runtimeDeobfuscationEnabled");
		final boolean developerEnvironment = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

		if (runtimeDeobfuscationEnabled) {
			OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.SRG;
		} else if (developerEnvironment) {
			OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.DEOBFUSCATED;
		} else {
			OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.OBFUSCATED;
		}

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
