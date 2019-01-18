package io.github.cadiboo.renderchunkrebuildchunkhooks.core;

import io.github.cadiboo.renderchunkrebuildchunkhooks.compatibility.BetterFoliageCompatibilityEventSubscriber;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformer;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerForge;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer.RenderChunkRebuildChunkHooksRenderChunkClassTransformerForgeOptifine;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderTypeAllowsRenderEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPostEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooksOptifine;
import io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer;
import net.minecraft.crash.CrashReport;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ReportedException;
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
			return new String[]{RenderChunkRebuildChunkHooksRenderChunkClassTransformerForgeOptifine.class.getName()};
		} else {
			return new String[]{RenderChunkRebuildChunkHooksRenderChunkClassTransformerForge.class.getName()};
		}

//		return new String[0];

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

		final boolean debugEverything = getArgsBoolean("debugEverything") | developerEnvironment;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_EVERYTHING = debugEverything;

		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_DUMP_BYTECODE = getArgsBoolean("dumpBytecode") | debugEverything;
		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_DUMP_BYTECODE_DIR = data.get("mcLocation") + "/" + MOD_ID + "/dumps/";

		RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_CLASSES = getArgsBoolean("debugClasses") | debugEverything;
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

		preloadEvents();
		preloadHooksForge();

	}

	private void preloadEvents() throws ReportedException {
		LOGGER.info("Pre-loading event classes...");
		RebuildChunkPreEvent.class.getName();
		RebuildChunkBlockRenderInLayerEvent.class.getName();
		RebuildChunkBlockRenderTypeAllowsRenderEvent.class.getName();
		RebuildChunkBlockEvent.class.getName();
		RebuildChunkPostEvent.class.getName();
		LOGGER.info("Loaded event classes, initialising...");
		try {
			Class.forName(RebuildChunkPreEvent.class.getName(), true, Loader.instance().getModClassLoader());
			Class.forName(RebuildChunkBlockRenderInLayerEvent.class.getName(), true, Loader.instance().getModClassLoader());
			Class.forName(RebuildChunkBlockRenderTypeAllowsRenderEvent.class.getName(), true, Loader.instance().getModClassLoader());
			Class.forName(RebuildChunkBlockEvent.class.getName(), true, Loader.instance().getModClassLoader());
			Class.forName(RebuildChunkPostEvent.class.getName(), true, Loader.instance().getModClassLoader());
		} catch (ClassNotFoundException e) {
			final CrashReport crashReport = new CrashReport("Error initialising event classes!", e);
			crashReport.makeCategory("Reflectively accessing event classes");
			throw new ReportedException(crashReport);
		}
		LOGGER.info("Successfully Pre-loaded event classes");
	}

	private boolean getArgsBoolean(final String arg) {
		final boolean result = Boolean.valueOf(System.getProperty("Drcrch." + arg)) | Boolean.valueOf(System.getProperty("rcrch." + arg));
		LOGGER.debug("Argument " + arg + ": " + result);
		return result;
	}

	@Override
	@Nullable
	public String getAccessTransformerClass() {
		return null;
	}

	private void detectOtherCoremods() {
		if (!OptifineCheckDone) {
			try {
				Class.forName("Config", false, Loader.instance().getModClassLoader());
				OPTIFINE = true;
				OptifineCheckDone = true;
				LOGGER.info("detected Optifine, using Optifine compatible ClassTransformer");
				preloadHooksForgeOptifine();
			} catch (final ClassNotFoundException e) {
				OPTIFINE = false;
				OptifineCheckDone = true;
				LOGGER.info("did not detect Optifine, using normal (Forge) ClassTransformer");
			}
		}

		if (!BetterFoliageCheckDone) {
			try {
				Class.forName("mods.betterfoliage.client.Hooks", false, Loader.instance().getModClassLoader());
				BETTER_FOLIAGE = true;
				BetterFoliageCheckDone = true;
				LOGGER.info("detected BetterFoliage, compatibility features will be enabled");
				preloadBFCompatibilityEventSubscriber();
			} catch (final ClassNotFoundException e) {
				BETTER_FOLIAGE = false;
				BetterFoliageCheckDone = true;
				LOGGER.info("did not detect BetterFoliage, proceeding normally");
			}
		}
	}

	private void preloadBFCompatibilityEventSubscriber() throws ReportedException {
		LOGGER.info("Preloading BetterFoliageCompatibilityEventSubscriber...");
		{
			BetterFoliageCompatibilityEventSubscriber.class.getName();
			LOGGER.info("Loaded BetterFoliageCompatibilityEventSubscriber, initialising...");
			try {
				Class.forName(BetterFoliageCompatibilityEventSubscriber.class.getName(), true, Loader.instance().getModClassLoader());
			} catch (ClassNotFoundException e) {
				final CrashReport crashReport = new CrashReport("Error initialising BetterFoliageCompatibilityEventSubscriber!", e);
				crashReport.makeCategory("Reflectively accessing BetterFoliageCompatibilityEventSubscriber");
				throw new ReportedException(crashReport);
			}
			LOGGER.info("Initialised BetterFoliageCompatibilityEventSubscriber");
		}
		LOGGER.info("Successfully preloaded BetterFoliageCompatibilityEventSubscriber!");
	}

	private void preloadHooksForge() throws ReportedException {
		LOGGER.info("Preloading RenderChunkRebuildChunkHooksHooks...");
		{
			RenderChunkRebuildChunkHooksHooks.class.getName();
			LOGGER.info("Loaded RenderChunkRebuildChunkHooksHooks, initialising...");
			try {
				Class.forName(RenderChunkRebuildChunkHooksHooks.class.getName(), true, Loader.instance().getModClassLoader());
			} catch (ClassNotFoundException e) {
				final CrashReport crashReport = new CrashReport("Error initialising RenderChunkRebuildChunkHooksHooks!", e);
				crashReport.makeCategory("Reflectively accessing RenderChunkRebuildChunkHooksHooks");
				throw new ReportedException(crashReport);
			}
			LOGGER.info("Initialised RenderChunkRebuildChunkHooksHooks");
		}
		LOGGER.info("Successfully preloaded RenderChunkRebuildChunkHooksHooks!");
	}

	private void preloadHooksForgeOptifine() throws ReportedException {
		LOGGER.info("Preloading RenderChunkRebuildChunkHooksHooksOptifine...");
		{
			RenderChunkRebuildChunkHooksHooksOptifine.class.getName();
			LOGGER.info("Loaded RenderChunkRebuildChunkHooksHooksOptifine, initialising...");
			try {
				Class.forName(RenderChunkRebuildChunkHooksHooksOptifine.class.getName(), true, Loader.instance().getModClassLoader());
			} catch (ClassNotFoundException e) {
				final CrashReport crashReport = new CrashReport("Error initialising RenderChunkRebuildChunkHooksHooksOptifine!", e);
				crashReport.makeCategory("Reflectively accessing RenderChunkRebuildChunkHooksHooksOptifine");
				throw new ReportedException(crashReport);
			}
			LOGGER.info("Initialised RenderChunkRebuildChunkHooksHooksOptifine");
		}
		LOGGER.info("Successfully preloaded RenderChunkRebuildChunkHooksHooksOptifine!");
	}

}
