package cadiboo.renderchunkrebuildchunkhooks.core.classtransformer;

import cadiboo.renderchunkrebuildchunkhooks.core.util.INames;
import cadiboo.renderchunkrebuildchunkhooks.core.util.IStacks;
import com.google.common.collect.ImmutableList;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public abstract class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, INames, IStacks {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader rJeading frames if the class writer is going to compute them for us (if it is you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final Logger LOGGER = LogManager.getLogger();

	public static final boolean DEBUG_DUMP_BYTECODE = true;

	public static final boolean DEBUG_EVERYTHING   = false;
	public static final boolean DEBUG_CLASSES      = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_NAMES        = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_TYPES        = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_STACKS       = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_METHODS      = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_INSTRUCTIONS = DEBUG_EVERYTHING | true;

	static {
		if (DEBUG_NAMES) {
			for (final Field field : INames.class.getFields()) {
				Object value;
				try {
					value = field.get(INames.class);

					LOGGER.info(field.getName() + ": " + value);

				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("Error logging names!");
					e.printStackTrace();
				}
			}
		}

		if (DEBUG_STACKS) {
			for (final Field field : IStacks.class.getFields()) {
				Object value;
				try {
					value = field.get(IStacks.class);

					LOGGER.info(field.getName() + ": " + value);

				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("Error logging stacks!");
					e.printStackTrace();
				}
			}
		}
	}

	public static final Printer            PRINTER              = new Textifier();
	public static final TraceMethodVisitor TRACE_METHOD_VISITOR = new TraceMethodVisitor(PRINTER);

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {

		if (DEBUG_CLASSES) {
			if ((unTransformedName.startsWith("b") || unTransformedName.startsWith("net.minecraft.client.renderer.chunk.")) || (transformedName.startsWith("b") || transformedName.startsWith("net.minecraft.client.renderer.chunk."))) {
				LOGGER.info("unTransformedName: " + unTransformedName + ", transformedName: " + transformedName + ", unTransformedName equals: " + unTransformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME) + ", transformedName equals: " + transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME));
			}
		}

		if (! transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME)) {
			return basicClass;
		}

		if (DEBUG_DUMP_BYTECODE) {
			try {
				final Path pathToFile = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/before_hooks" + /* (System.nanoTime() & 0xFF) + */ ".txt");
				final PrintWriter filePrinter = new PrintWriter(pathToFile.toFile());
				final ClassReader reader = new ClassReader(basicClass);
				final TraceClassVisitor tracingVisitor = new TraceClassVisitor(filePrinter);
				reader.accept(tracingVisitor, 0);

				final Path pathToClass = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/before_hooks" + /* (System.nanoTime() & 0xFF) + */ ".class");
				final FileOutputStream fileOutputStream = new FileOutputStream(pathToClass.toFile());
				fileOutputStream.write(basicClass);
				fileOutputStream.close();
			} catch (final Exception e) {
				// TODO: handle exception
			}
		}

		LOGGER.info("Preparing to inject hooks into \"" + transformedName + "\" (RenderChunk)");

		// Build classNode & get instruction list
		final ClassNode classNode = new ClassNode();
		final ClassReader cr = new ClassReader(basicClass);
		cr.accept(classNode, CLASS_READER_FLAGS);

		if (DEBUG_TYPES) {
			LOGGER.info("RebuildChunk type: " + REBUILD_CHUNK_TYPE);
			LOGGER.info("RebuildChunk descriptor: " + REBUILD_CHUNK_DESCRIPTOR);
		}

		// peek at classNode and modifier
		for (final MethodNode method : classNode.methods) {

			if (! method.desc.equals(REBUILD_CHUNK_DESCRIPTOR)) {
				if (DEBUG_METHODS) {
					LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" did not match");
				}
				continue;
			}

			if (DEBUG_METHODS) {
				LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched!");
			}

			// make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
			if (method.name.equals("a") || method.name.equals("resortTransparency")) {
				if (DEBUG_METHODS) {
					LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" was rejected");
				}
				continue;
			}

			if (DEBUG_METHODS) {
				LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched and passed");
			}

			this.injectHooks(method.instructions);

		}

		// write classNode
		try {
			final ClassWriter out = new ClassWriter(CLASS_WRITER_FLAGS);

			// make the ClassWriter visit all the code in classNode
			classNode.accept(out);

			LOGGER.info("Injected hooks sucessfully!");

			if (DEBUG_DUMP_BYTECODE) {
				try {
					final byte[] bytes = out.toByteArray();

					final Path pathToFile = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/after_hooks" + /* (System.nanoTime() & 0xFF) + */ ".txt");
					final PrintWriter filePrinter = new PrintWriter(pathToFile.toFile());
					final ClassReader reader = new ClassReader(bytes);
					final TraceClassVisitor tracingVisitor = new TraceClassVisitor(filePrinter);
					reader.accept(tracingVisitor, 0);

					final Path pathToClass = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/after_hooks" + /* (System.nanoTime() & 0xFF) + */ ".class");
					final FileOutputStream fileOutputStream = new FileOutputStream(pathToClass.toFile());
					fileOutputStream.write(bytes);
					fileOutputStream.close();
				} catch (final Exception e) {
					// TODO: handle exception
				}
			}

			return out.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			LOGGER.warn("Any mods that depend on the hooks provided by this mod will break");
			return basicClass;
		}

	}

	public void injectHooks(InsnList instructions) {

		{
			LOGGER.info("injecting RebuildChunkPreEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			this.injectRebuildChunkPreEventHook(instructions);
			LOGGER.info("injected RebuildChunkPreEvent Hook");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		{
			LOGGER.info("injecting RebuildChunkBlockRenderInLayerEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			this.injectRebuildChunkBlockRenderInLayerEventHook(instructions);
			LOGGER.info("injected RebuildChunkBlockRenderInLayerEvent Hook");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		{
			LOGGER.info("injecting RebuildRebuildChunkBlockEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			this.injectRebuildChunkBlockEventHook(instructions);
			LOGGER.info("injected RebuildRebuildChunkBlockEvent Hook");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		{
			LOGGER.info("injecting RebuildChunkPostEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			this.injectRebuildChunkPostEventHook(instructions);
			LOGGER.info("injected RebuildChunkPostEvent Hook");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

	}

	public abstract void injectRebuildChunkPreEventHook(InsnList instructions);

	public abstract void injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions);

	public abstract void injectRebuildChunkBlockEventHook(InsnList instructions);

	public abstract void injectRebuildChunkPostEventHook(InsnList instructions);

	public static String insnToString(final AbstractInsnNode insn) {

		insn.accept(TRACE_METHOD_VISITOR);
		final StringWriter sw = new StringWriter();
		PRINTER.print(new PrintWriter(sw));
		PRINTER.getText().clear();
		return sw.toString().trim();
	}

	public static String fieldToString(final FieldNode field) {

		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		field.accept(new TraceClassVisitor(pw));
		PRINTER.print(pw);
		PRINTER.getText().clear();
		return sw.toString().trim();
	}

}
