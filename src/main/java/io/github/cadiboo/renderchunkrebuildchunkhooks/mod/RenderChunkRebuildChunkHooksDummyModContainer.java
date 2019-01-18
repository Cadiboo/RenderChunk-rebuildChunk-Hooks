package io.github.cadiboo.renderchunkrebuildchunkhooks.mod;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.github.cadiboo.renderchunkrebuildchunkhooks.compatibility.BetterFoliageCompatibilityEventSubscriber;
import io.github.cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfig;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import joptsimple.internal.Strings;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.CertificateHelper;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DependencyParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin.BETTER_FOLIAGE;

public final class RenderChunkRebuildChunkHooksDummyModContainer extends DummyModContainer {

	public static final String MOD_ID = "render_chunk_rebuild_chunk_hooks";
	public static final String MOD_NAME = "RenderChunk rebuildChunk Hooks";
	public static final String MOD_VERSION = "@VERSION@";
	public static final String CERTIFICATE_FINGERPRINT = "@FINGERPRINT@";
	public static final String DEPENDENCIES = "required-after:minecraft;" +
			"required-after:forge@[14.23.5.2768,);"
//			"required-after:forge@[14.23.5.2795,);"
			;
	// Directly reference a log4j logger.
	public static final Logger MOD_LOGGER = LogManager.getLogger(MOD_NAME);
	private static final URL UPDATE_JSON_URL;
	static {
		URL url = null;
		try {
			url = new URL("https://raw.githubusercontent.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/master/update.json");
		} catch (MalformedURLException e) {
			MOD_LOGGER.error("Error setting update.json url", e);
		}
		UPDATE_JSON_URL = url;
	}

	static {
		if (MOD_ID.length() > 64) {
			final IllegalStateException exception = new IllegalStateException("Mod Id is too long!");
			CrashReport crashReport = new CrashReport("Mod Id must be 64 characters or shorter!", exception);
			crashReport.makeCategory("Constructing Mod");
		}
	}
	private final List<ArtifactVersion> dependencies;
	private final Set<ArtifactVersion> requiredMods;
	private final List<ArtifactVersion> dependants;
	private Certificate certificate = null;

	public RenderChunkRebuildChunkHooksDummyModContainer() {
		super(new ModMetadata());

		final ArrayList<String> description = new ArrayList<>();
		description.add("A small(ish) coremod for 1.12.2 to inject hooks into RenderChunk#rebuildChunk to allow modders to add their own custom chunk rendering logic and other chunk rendering related modifications.");
		description.add("This mod provides configurable events that Modders can use for various chunk/world-related rendering logic");
		description.add(" - The RebuildChunkPreEvent is called before any chunk rebuilding is done");
		description.add("    - RebuildChunkPreOptifineEvent is the same as the RebuildChunkPreEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockRenderInLayerEvent allows modders to modify the BlockRenderLayers that blocks can render in");
		description.add("    - RebuildChunkBlockRenderInLayerOptifineEvent is the same as the RebuildChunkBlockRenderInLayerEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockRenderInTypeEvent allows modders to modify the EnumBlockRenderType that blocks can render in");
		description.add("    - RebuildChunkBlockRenderInTypeOptifineEvent is the same as the RebuildChunkBlockRenderInTypeEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkBlockEvent is called for each BlockRenderLayers of each block and allows Modders to add their own logic");
		description.add("    - RebuildChunkBlockOptifineEvent is the same as the RebuildChunkBlockEvent but allows access to Optifine-related objects");
		description.add(" - The RebuildChunkPostEvent is called after all chunk rebuilding logic is done");
		description.add("    - RebuildChunkPostOptifineEvent is the same as the RebuildChunkPostEvent but allows access to Optifine-related objects");

		final ModMetadata meta = this.getMetadata();
		meta.modId = MOD_ID;
		meta.name = MOD_NAME;
		meta.version = MOD_VERSION;
		meta.credits = "The Forge and FML guys for Forge and FML and Cadiboo for making the mod";
		meta.authorList = Arrays.asList("Cadiboo", "CosmicDan");
		meta.description = Strings.join(description, "\n");
		meta.url = "https://cadiboo.github.io/projects/" + MOD_ID;
		meta.updateJSON = UPDATE_JSON_URL.toString();
		meta.screenshots = new String[0];
		meta.logoFile = "/" + MOD_ID + "_logo.png";

		DependencyParser dependencyParser = new DependencyParser(MOD_ID, FMLCommonHandler.instance().getSide());
		DependencyParser.DependencyInfo info = dependencyParser.parseDependencies(DEPENDENCIES);

		meta.dependencies = join(meta.dependencies, info.dependencies);
		meta.requiredMods = join(meta.requiredMods, info.requirements);
		meta.dependants = join(meta.dependants, info.dependants);

		this.dependencies = meta.dependencies;
		this.requiredMods = meta.requiredMods;
		this.dependants = meta.dependants;

	}

	@SafeVarargs
	private static <T> ArrayList<T> join(List<T>... lists) {
		final ArrayList<T> arrayList = new ArrayList<>();
		for (final List<T> list : lists) {
			arrayList.addAll(list);
		}
		return arrayList;
	}

	@SafeVarargs
	private static <T> Set<T> join(Set<T>... sets) {
		final Set<T> newSet = Sets.newHashSet();
		for (final Set<T> set : sets) {
			newSet.addAll(set);
		}
		return newSet;
	}

	@Override
	public List<ArtifactVersion> getDependants() {
		return dependants;
	}

	@Override
	public Set<ArtifactVersion> getRequirements() {
		return requiredMods;
	}

	@Override
	public List<ArtifactVersion> getDependencies() {
		return dependencies;
	}

	@Subscribe
	public void preInit(final FMLPreInitializationEvent event) {
		if (!event.getSide().isClient()) {
			return;
		}

		RenderChunkRebuildChunkHooksConfig.load(event.getSuggestedConfigurationFile());

		MinecraftForge.EVENT_BUS.register(new RenderChunkRebuildChunkHooksEventSubscriber());

		tryPreloadRenderChunk();

		tryRegisterBetterFoliageCompatibleEventSubscriber();
	}

	private void tryPreloadRenderChunk() {
		MOD_LOGGER.info("Preloading RenderChunk...");
		{
			RenderChunk.class.getName();
			MOD_LOGGER.info("Loaded RenderChunk, initialising...");
			final int unused_renderChunksUpdated = RenderChunk.renderChunksUpdated;
			MOD_LOGGER.info("Initialised RenderChunk");
		}
		MOD_LOGGER.info("Successfully preloaded RenderChunk!");
	}

	private void tryRegisterBetterFoliageCompatibleEventSubscriber() {
		if (BETTER_FOLIAGE) {
			MOD_LOGGER.info("Registering BetterFoliage compatibility EventSubscriber...");
			final Class<?> betterFoliageCompatibilityEventSubscriberClass;
			try {
				MinecraftForge.EVENT_BUS.register(new BetterFoliageCompatibilityEventSubscriber());
			} catch (Exception exception) {
				CrashReport crashReport = new CrashReport("Error Registering BetterFoliage compatibility EventSubscriber", exception);
				crashReport.makeCategory("Registering BetterFoliage compatibility EventSubscriber");
				throw new ReportedException(crashReport);
			}
			MOD_LOGGER.info("Registered BetterFoliage compatibility EventSubscriber");
		}
	}

	//will always be null in dev environment, will never be null in release environment
	@Override
	public File getSource() {
		if (RenderChunkRebuildChunkHooksLoadingPlugin.OBFUSCATION_LEVEL == ObfuscationHelper.ObfuscationLevel.DEOBFUSCATED) {
			return super.getSource();
		}
		return RenderChunkRebuildChunkHooksLoadingPlugin.MOD_LOCATION;
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}

	// load our lang file
	@Override
	public Class<?> getCustomResourcePackClass() {
		// without this it crashes in dev, even though it works perfectly in release environment
		if (RenderChunkRebuildChunkHooksLoadingPlugin.OBFUSCATION_LEVEL == ObfuscationHelper.ObfuscationLevel.DEOBFUSCATED) {
			return super.getCustomResourcePackClass();
		}
		return getSource().isDirectory() ? FMLFolderResourcePack.class : FMLFileResourcePack.class;
	}

	@Override
	public String getGuiClassName() {
		return RenderChunkRebuildChunkHooksGuiFactory.class.getName();
	}

	@Override
	public boolean shouldLoadInEnvironment() {
		return FMLCommonHandler.instance().getSide().isClient();
	}

	@Nullable
	@Override
	public Certificate getSigningCertificate() {
		return certificate;
	}

	//TODO: copy more stuff from FMLModContainer and see if that fixes some hacks I'm doing & problems I have
	@Subscribe
	public void constructMod(FMLConstructionEvent event) {
		final File source = Loader.instance().activeModContainer().getSource();

		FMLModContainer.Disableable.values();

//		Fingerprint stuff, coppied from {@link FMLModContainer }
		Certificate[] certificates = getClass().getProtectionDomain().getCodeSource().getCertificates();
		ImmutableList<String> certList = CertificateHelper.getFingerprints(certificates);
		Set<String> sourceFingerprints = ImmutableSet.copyOf(certList);

		String expectedFingerprint = CERTIFICATE_FINGERPRINT;

		boolean fingerprintNotPresent = true;

		if (!expectedFingerprint.isEmpty()) {
			if (!sourceFingerprints.contains(expectedFingerprint)) {
				Level warnLevel = source.isDirectory() ? Level.TRACE : Level.ERROR;
//				FMLLog.log.log(warnLevel, "The mod {} is expecting signature {} for source {}, however there is no signature matching that description", getModId(), expectedFingerprint, source.getName());
			} else {
				this.certificate = certificates[certList.indexOf(expectedFingerprint)];
				fingerprintNotPresent = false;
			}
		}

		if (fingerprintNotPresent) {
			MOD_LOGGER.error("Certificate Fingerprint {} not present for source {}!", expectedFingerprint, source.getName());
		}

	}

	@Override
	public URL getUpdateUrl() {
		return UPDATE_JSON_URL;
	}

	@Override
	public Disableable canBeDisabled() {
		return Disableable.YES;
	}

}
