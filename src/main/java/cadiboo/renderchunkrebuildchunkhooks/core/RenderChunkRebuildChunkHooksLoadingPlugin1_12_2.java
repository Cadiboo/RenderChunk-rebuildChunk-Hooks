package cadiboo.renderchunkrebuildchunkhooks.core;

import cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge;
import cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;
import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_NAME;

@Name(MOD_NAME)
@MCVersion("1.12.2")
@TransformerExclusions({"cadiboo.renderchunkrebuildchunkhooks.core."})
/* How early your core mod is called - Use > 1000 to work with srg names */
//??? needs higher than 1001??? 0xBADC0DE works
//@SortingIndex(value = 1001)
@SortingIndex(value = 0xBADC0DE)
//put in _VM_ arguments -Dfml.coreMods.load=cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2
public class RenderChunkRebuildChunkHooksLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

	public static final String CORE_MARKER = MOD_ID + "_loaded";

	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + " Core Plugin");
	public static File MOD_LOCATION = null;

	public static ObfuscationHelper.ObfuscationLevel OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.OBFUSCATED;

	public static boolean OPTIFINE = false;
	public static boolean BETTER_FOLIAGE = false;
	private static boolean OptifineCheckDone = false;
	private static boolean BetterFoliageCheckDone = false;

	public RenderChunkRebuildChunkHooksLoadingPlugin1_12_2() {
		LOGGER.info("Initialising " + this.getClass().getSimpleName() + " at version " + this.getVersion());
		Launch.blackboard.put(CORE_MARKER, this.getVersion());
	}

	public String getVersion() {
		return "1.12.2";
	}

	@Override
	public String[] getASMTransformerClass() {
		detectOtherCoremods();

		if (OPTIFINE) {
			return new String[]{"cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine"};
		} else {
			return new String[]{RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge.class.getName()};
		}
	}

	@Override
	public String getModContainerClass() {
		return RenderChunkRebuildChunkHooksDummyModContainer.class.getName();
	}

	@Override
	@Nullable
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(final Map<String, Object> data) {
		final boolean runtimeDeobfuscationEnabled = (boolean) data.get("runtimeDeobfuscationEnabled");
		final boolean developerEnvironment = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

		MOD_LOCATION = (File) data.get("coremodLocation");

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
		LOGGER.info("Successfully Pre-loaded event classes");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	private void detectOtherCoremods() {
		if (!OptifineCheckDone) {
			try {
				final Class<?> optifineConfig = Class.forName("Config", false, Loader.instance().getModClassLoader());
				OPTIFINE = true;
				OptifineCheckDone = true;
				LOGGER.info("detected Optifine, using Optifine compatible ClassTransformer");
			} catch (final Exception e) {
				OPTIFINE = false;
				OptifineCheckDone = true;
				LOGGER.info("did not detect Optifine, using normal (Forge) ClassTransformer");
			}
		}

		if (!BetterFoliageCheckDone) {
			try {
				final Class<?> betterFoliageClient = Class.forName("mods.betterfoliage.client.Hooks", false, Loader.instance().getModClassLoader());
				BETTER_FOLIAGE = true;
				BetterFoliageCheckDone = true;
				LOGGER.info("detected BetterFoliage, compatibility features will be enabled");
			} catch (final Exception e) {
				BETTER_FOLIAGE = false;
				BetterFoliageCheckDone = true;
				LOGGER.info("did not detect BetterFoliage, proceeding normally");
			}
		}
	}

}
