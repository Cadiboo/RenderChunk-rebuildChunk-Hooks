package io.github.cadiboo.renderchunkrebuildchunkhooks.core;

import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformer;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer;
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
import java.lang.reflect.Field;
import java.util.Map;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_VERSION;

@Name(RenderChunkRebuildChunkHooksDummyModContainer.MOD_NAME)
@MCVersion("1.12.2")
@TransformerExclusions({"io.github.cadiboo.renderchunkrebuildchunkhooks.core."})
/* How early your core mod is called - Use > 1000 to work with srg names */
//??? needs higher than 1001??? 0xBADC0DE works
//@SortingIndex(value = 1001)
@SortingIndex(value = 0xBAD_C0DE)
//put in _VM_ arguments -Dfml.coreMods.load=io.github.cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin
public final class RenderChunkRebuildChunkHooksLoadingPlugin implements IFMLLoadingPlugin {

	public static final String CORE_MARKER = MOD_ID;

	private static final Logger LOGGER = LogManager.getLogger(RenderChunkRebuildChunkHooksDummyModContainer.MOD_NAME + " Core Plugin");
	public static File MOD_LOCATION = null;

	public static ObfuscationHelper.ObfuscationLevel OBFUSCATION_LEVEL = ObfuscationHelper.ObfuscationLevel.OBFUSCATED;

	public static boolean OPTIFINE = false;
	public static boolean BETTER_FOLIAGE = false;
	private static boolean OptifineCheckDone = false;
	private static boolean BetterFoliageCheckDone = false;

	public RenderChunkRebuildChunkHooksLoadingPlugin() {
		LOGGER.debug("Initialising " + this.getClass().getSimpleName() + " version: " + MOD_VERSION);
		Launch.blackboard.put(CORE_MARKER, MOD_VERSION);
	}

	@Override
	public String[] getASMTransformerClass() {
		detectOtherCoremods();

		if (OPTIFINE) {
			return new String[]{RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine.class.getName()};
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

		final boolean debugEverything = getArgsBoolean("debugEverything");
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_EVERYTHING = debugEverything;

		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_DUMP_BYTECODE = getArgsBoolean("dumpBytecode") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_DUMP_BYTECODE_DIR = data.get("mcLocation") + "/" + MOD_ID + "/dumps/";

		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_CLASSES = getArgsBoolean("debugClasses") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_FIELDS = getArgsBoolean("debugFields") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_TYPES = getArgsBoolean("debugTypes") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_STACKS = getArgsBoolean("debugStacks") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_METHODS = getArgsBoolean("debugMethods") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_INSTRUCTIONS = getArgsBoolean("debugInstructions") | debugEverything;

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

	private boolean getArgsBoolean(final String arg) {
		return Boolean.valueOf(System.getProperty("D" + arg)) | Boolean.valueOf(System.getProperty(arg));
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

	/**
	 * Logs all {@link java.lang.reflect.Field Field}s and their values of an object with the {@link org.apache.logging.log4j.Level#INFO INFO} level.
	 *
	 * @param objects the objects to dump.
	 */
	public static void dump(final Object... objects) {
		for (final Object obj : objects) {
			final Field[] fields = obj.getClass().getDeclaredFields();
			LOGGER.info("Dump of " + obj + ":");
			for (int i = 0; i < fields.length; i++) {
				try {
					fields[i].setAccessible(true);
					LOGGER.info(fields[i].getName() + " - " + fields[i].get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.info("Error getting field " + fields[i].getName());
					LOGGER.info(e.getLocalizedMessage());
				}
			}
		}
	}

}
