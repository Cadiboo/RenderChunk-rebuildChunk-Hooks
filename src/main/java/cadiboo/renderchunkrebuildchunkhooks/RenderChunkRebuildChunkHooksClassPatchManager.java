package cadiboo.renderchunkrebuildchunkhooks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import LZMA.LzmaInputStream;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.patcher.ClassPatch;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.repackage.com.nothome.delta.GDiffPatcher;

public class RenderChunkRebuildChunkHooksClassPatchManager {

	private static final boolean	DEBUG	= true;
	private static final Logger		LOGGER	= LogManager.getLogger();

	private static RenderChunkRebuildChunkHooksClassPatchManager instance = null;

	private final GDiffPatcher					patcher	= new GDiffPatcher();
	private ListMultimap<String, ClassPatch>	patches;

	private final Map<String, byte[]>	patchedClasses	= Maps.newHashMap();
	private File						tempDir;

	public static RenderChunkRebuildChunkHooksClassPatchManager getInstance() {
		if (instance == null) {
			instance = new RenderChunkRebuildChunkHooksClassPatchManager();
		}
		return instance;
	}

	public byte[] getPatchedResource(final String name, final String mappedName, final LaunchClassLoader loader) throws IOException {
		final byte[] rawClassBytes = loader.getClassBytes(name);
		return this.applyPatch(name, mappedName, rawClassBytes);
	}

	public byte[] applyPatch(final String name, final String mappedName, byte[] inputData) {
		if (this.patches == null) {
			return inputData;
		}
		if (this.patchedClasses.containsKey(name)) {
			return this.patchedClasses.get(name);
		}
		final List<ClassPatch> list = this.patches.get(name);
		if (list.isEmpty()) {
			return inputData;
		}
		boolean ignoredError = false;
		if (DEBUG) {
			this.LOGGER.debug("Runtime patching class {} (input size {}), found {} patch{}", mappedName, (inputData == null ? 0 : inputData.length), list.size(), list.size() != 1 ? "es" : "");
		}
		for (final ClassPatch patch : list) {
			if (!patch.targetClassName.equals(mappedName) && !patch.sourceClassName.equals(name)) {
				this.LOGGER.warn("Binary patch found {} for wrong class {}", patch.targetClassName, mappedName);
			}
			if (!patch.existsAtTarget && ((inputData == null) || (inputData.length == 0))) {
				inputData = new byte[0];
			} else if (!patch.existsAtTarget) {
				this.LOGGER.warn("Patcher expecting empty class data file for {}, but received non-empty", patch.targetClassName);
			} else if (patch.existsAtTarget && ((inputData == null) || (inputData.length == 0))) {
				this.LOGGER.fatal("Patcher expecting non-empty class data file for {}, but received empty.", patch.targetClassName);
				throw new RuntimeException(String.format("Patcher expecting non-empty class data file for %s, but received empty, your vanilla jar may be corrupt.", patch.targetClassName));
			} else {
				final int inputChecksum = Hashing.adler32().hashBytes(inputData).asInt();
				if (patch.inputChecksum != inputChecksum) {
					this.LOGGER.fatal("There is a binary discrepancy between the expected input class {} ({}) and the actual class. Checksum on disk is {}, in patch {}. Things are probably about to go very wrong. Did you put something into the jar file?", mappedName, name, Integer.toHexString(inputChecksum), Integer.toHexString(patch.inputChecksum));
					if (!Boolean.parseBoolean(System.getProperty("fml.ignorePatchDiscrepancies", "false"))) {
						this.LOGGER.fatal("The game is going to exit, because this is a critical error, and it is very improbable that the modded game will work, please obtain clean jar files.");
						System.exit(1);
					} else {
						this.LOGGER.fatal("FML is going to ignore this error, note that the patch will not be applied, and there is likely to be a malfunctioning behaviour, including not running at all");
						ignoredError = true;
						continue;
					}
				}
			}
			synchronized (this.patcher) {
				try {
					inputData = this.patcher.patch(inputData, patch.patch);
				} catch (final IOException e) {
					this.LOGGER.error("Encountered problem runtime patching class {}", name, e);
					continue;
				}
			}
		}
		if (!ignoredError && DEBUG) {
			this.LOGGER.debug("Successfully applied runtime patches for {} (new size {})", mappedName, inputData.length);
		}
//		if (dumpPatched) {
//			try {
//				Files.write(inputData, new File(this.tempDir, mappedName));
//			} catch (final IOException e) {
//				LOGGER.error(LOGGER.getMessageFactory().newMessage("Failed to write {} to {}", mappedName, this.tempDir.getAbsolutePath()), e);
//			}
//		}
		this.patchedClasses.put(name, inputData);
		return inputData;
	}

	public void setup(final Side side) {

		final File directory = FileUtils.getTempDirectory();

		final Collection<File> files = FileUtils.listFiles(directory, new String[] { "java" }, true);

		LOGGER.info(files);

		final Pattern binpatchMatcher = Pattern.compile(String.format("binpatch/%s/.*.binpatch", side.toString().toLowerCase(Locale.ENGLISH)));
		JarInputStream jis;
		try {
			final InputStream binpatchesCompressed = this.getClass().getResourceAsStream("/binpatches.pack.lzma");
			if (binpatchesCompressed == null) {
				if (!((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment"))) {
					this.LOGGER.fatal("The binary patch set is missing, things are not going to work!");
				}
				return;
			}
			final LzmaInputStream binpatchesDecompressed = new LzmaInputStream(binpatchesCompressed);
			final ByteArrayOutputStream jarBytes = new ByteArrayOutputStream();
			final JarOutputStream jos = new JarOutputStream(jarBytes);
			Pack200.newUnpacker().unpack(binpatchesDecompressed, jos);
			jis = new JarInputStream(new ByteArrayInputStream(jarBytes.toByteArray()));
		} catch (final Exception e) {
			throw new RuntimeException("Error occurred reading binary patches. Expect severe problems!", e);
		}

		this.patches = ArrayListMultimap.create();

		do {
			try {
				final JarEntry entry = jis.getNextJarEntry();
				if (entry == null) {
					break;
				}
				if (binpatchMatcher.matcher(entry.getName()).matches()) {
					final ClassPatch cp = this.readPatch(entry, jis);
					if (cp != null) {
						this.patches.put(cp.sourceClassName, cp);
					}
				} else {
					jis.closeEntry();
				}
			} catch (final IOException e) {
			}
		} while (true);
		this.LOGGER.debug("Read {} binary patches", this.patches.size());
		if (DEBUG) {
			this.LOGGER.debug("Patch list :\n\t{}", Joiner.on("\t\n").join(this.patches.asMap().entrySet()));
		}
		this.patchedClasses.clear();
	}

	private ClassPatch readPatch(final JarEntry patchEntry, final JarInputStream jis) {
		if (DEBUG) {
			this.LOGGER.trace("Reading patch data from {}", patchEntry.getName());
		}
		ByteArrayDataInput input;
		try {
			input = ByteStreams.newDataInput(ByteStreams.toByteArray(jis));
		} catch (final IOException e) {
			this.LOGGER.warn(this.LOGGER.getMessageFactory().newMessage("Unable to read binpatch file {} - ignoring", patchEntry.getName()), e);
			return null;
		}
		final String name = input.readUTF();
		final String sourceClassName = input.readUTF();
		final String targetClassName = input.readUTF();
		final boolean exists = input.readBoolean();
		int inputChecksum = 0;
		if (exists) {
			inputChecksum = input.readInt();
		}
		final int patchLength = input.readInt();
		final byte[] patchBytes = new byte[patchLength];
		input.readFully(patchBytes);

		return new ClassPatch(name, sourceClassName, targetClassName, exists, inputChecksum, patchBytes);
	}

}
