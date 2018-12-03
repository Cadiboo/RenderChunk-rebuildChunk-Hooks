package cadiboo.renderchunkrebuildchunkhooks.core.classtransformer;

import cadiboo.renderchunkrebuildchunkhooks.core.util.IStacks;
import cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.BETTER_FOLIAGE;
import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.OBFUSCATION_LEVEL;
import static cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationClass.RENDER_CHUNK;
import static cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.BETTER_FOLIAGE_RENDER_WORLD_BLOCK;
import static cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK;
import static cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.RENDER_CHUNK_REBUILD_CHUNK;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public abstract class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, IStacks {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader reading frames if the class writer is going to compute them for us (if it is you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final Logger LOGGER = LogManager.getLogger();

	// TODO dump it in the Minecraft directory?
	//	public static final boolean DEBUG_DUMP_BYTECODE = false;

	public static final boolean DEBUG_EVERYTHING = false;
	public static final boolean DEBUG_CLASSES = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_FIELDS = DEBUG_EVERYTHING | false;
	//may cause issues, use with care
	public static final boolean DEBUG_NAMES = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_TYPES = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_STACKS = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_METHODS = DEBUG_EVERYTHING | false;
	public static final boolean DEBUG_INSTRUCTIONS = DEBUG_EVERYTHING | false;

	public static final boolean REMOVE_BetterFoliagesModifications = BETTER_FOLIAGE & true;
	public static final boolean INJECT_RebuildChunkPreEvent = true;
	public static final boolean INJECT_RebuildChunkBlockRenderInLayerEvent = true;
	public static final boolean INJECT_RebuildChunkBlockRenderInTypeEvent = true;
	public static final boolean INJECT_RebuildChunkBlockEvent = true;
	public static final boolean INJECT_RebuildChunkPostEvent = true;
	public static final Printer PRINTER = new Textifier();
	public static final TraceMethodVisitor TRACE_METHOD_VISITOR = new TraceMethodVisitor(PRINTER);
	static {
		if (DEBUG_NAMES) {
			ObfuscationHelper.ObfuscationLevel oldObfuscationLevel = OBFUSCATION_LEVEL;
			for (ObfuscationHelper.ObfuscationLevel obfuscationLevel : ObfuscationHelper.ObfuscationLevel.values()) {
				LOGGER.warn("Debbuging names for " + obfuscationLevel.name());
				OBFUSCATION_LEVEL = obfuscationLevel;
				for (final ObfuscationHelper.ObfuscationClass obfuscationClass : ObfuscationHelper.ObfuscationClass.values()) {
					LOGGER.info("ObfuscationClass {}: {}, {}", obfuscationClass.name(), obfuscationClass.getClassName(), obfuscationClass.getInternalName());
				}
				for (final ObfuscationHelper.ObfuscationField obfuscationField : ObfuscationHelper.ObfuscationField.values()) {
					LOGGER.info("ObfuscationField {}: {}, {}, {}", obfuscationField.name(), obfuscationField.getOwner(), obfuscationField.getName(), obfuscationField.getDescriptor());
				}
				for (final ObfuscationHelper.ObfuscationMethod obfuscationMethod : ObfuscationHelper.ObfuscationMethod.values()) {
					LOGGER.info("ObfuscationMethod {}: {}, {}, {}, {}", obfuscationMethod.name(), obfuscationMethod.getOwner(), obfuscationMethod.getName(), obfuscationMethod.getDescriptor(), obfuscationMethod.isInterface());
				}
			}
			OBFUSCATION_LEVEL = oldObfuscationLevel;
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

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {
		if (DEBUG_CLASSES) {
			if ((unTransformedName.startsWith("b") || unTransformedName.startsWith("net.minecraft.client.renderer.chunk.")) || (transformedName.startsWith("b") || transformedName.startsWith("net.minecraft.client.renderer.chunk."))) {
				LOGGER.info("unTransformedName: " + unTransformedName + ", transformedName: " + transformedName + ", unTransformedName equals: " + unTransformedName.equals(RENDER_CHUNK.getClassName()) + ", transformedName equals: " + transformedName.equals(RENDER_CHUNK.getClassName()));
			}
		}

		if (!transformedName.equals(RENDER_CHUNK.getClassName())) {
			return basicClass;
		}

		//		if (DEBUG_DUMP_BYTECODE) {
		//			try {
		//				final Path pathToFile = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/before_hooks" + /* (System.nanoTime() & 0xFF) + */ ".txt");
		//				final PrintWriter filePrinter = new PrintWriter(pathToFile.toFile());
		//				final ClassReader reader = new ClassReader(basicClass);
		//				final TraceClassVisitor tracingVisitor = new TraceClassVisitor(filePrinter);
		//				reader.accept(tracingVisitor, 0);
		//
		//				final Path pathToClass = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/before_hooks" + /* (System.nanoTime() & 0xFF) + */ ".class");
		//				final FileOutputStream fileOutputStream = new FileOutputStream(pathToClass.toFile());
		//				fileOutputStream.write(basicClass);
		//				fileOutputStream.close();
		//			} catch (final Exception exception) {
		//				LOGGER.error("Failed to dump bytecode of classes before injecting hooks!");
		//				exception.printStackTrace();
		//			}
		//		}

		LOGGER.info("Preparing to inject hooks into \"" + transformedName + "\" (RenderChunk)");

		// Build classNode & get instruction list
		final ClassNode classNode = new ClassNode();
		final ClassReader cr = new ClassReader(basicClass);
		cr.accept(classNode, CLASS_READER_FLAGS);

		if (DEBUG_FIELDS) {
			for (final FieldNode field : classNode.fields) {
				LOGGER.info(fieldToString(field));
			}
		}

		if (DEBUG_TYPES) {
			LOGGER.info("RebuildChunk type: " + RENDER_CHUNK_REBUILD_CHUNK.getType());
			LOGGER.info("RebuildChunk descriptor: " + RENDER_CHUNK_REBUILD_CHUNK.getDescriptor());
		}

		for (final MethodNode method : classNode.methods) {            //TODO RENDER_CHUNK_REBUILD_CHUNK.matches()

			if (!method.desc.equals(RENDER_CHUNK_REBUILD_CHUNK.getDescriptor())) {
				if (DEBUG_METHODS) {
					LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" did not match");
				}
				continue;
			}

			if (DEBUG_METHODS) {
				LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched!");
			}

			// make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
			if (method.name.equals("a") || method.name.equals("func_178570_a") || method.name.equals("resortTransparency")) {
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

			LOGGER.info("Injected hooks successfully!");

			//			if (DEBUG_DUMP_BYTECODE) {
			//				try {
			//					final byte[] bytes = out.toByteArray();
			//
			//					final Path pathToFile = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/after_hooks" + /* (System.nanoTime() & 0xFF) + */ ".txt");
			//					final PrintWriter filePrinter = new PrintWriter(pathToFile.toFile());
			//					final ClassReader reader = new ClassReader(bytes);
			//					final TraceClassVisitor tracingVisitor = new TraceClassVisitor(filePrinter);
			//					reader.accept(tracingVisitor, 0);
			//
			//					final Path pathToClass = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/after_hooks" + /* (System.nanoTime() & 0xFF) + */ ".class");
			//					final FileOutputStream fileOutputStream = new FileOutputStream(pathToClass.toFile());
			//					fileOutputStream.write(bytes);
			//					fileOutputStream.close();
			//				} catch (final Exception exception) {
			//					LOGGER.error("Failed to dump bytecode of classes after injecting hooks!");
			//					exception.printStackTrace();
			//				}
			//			}

			return out.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			LOGGER.warn("Any mods that depend on the hooks provided by this mod will not work");
			return basicClass;
		}

	}

	public void injectHooks(InsnList instructions) {
		if (INJECT_RebuildChunkPreEvent) {
			LOGGER.info("injecting RebuildChunkPreEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.injectRebuildChunkPreEventHook(instructions)) {
				LOGGER.info("injected RebuildChunkPreEvent Hook");
			} else {
				LOGGER.error("failed to inject RebuildChunkPreEvent Hook");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		if (INJECT_RebuildChunkBlockRenderInLayerEvent) {
			LOGGER.info("injecting RebuildChunkBlockRenderInLayerEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.injectRebuildChunkBlockRenderInLayerEventHook(instructions)) {
				LOGGER.info("injected RebuildChunkBlockRenderInLayerEvent Hook");
			} else {
				LOGGER.error("failed to inject RebuildChunkBlockRenderInLayerEvent Hook");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		if (BETTER_FOLIAGE && REMOVE_BetterFoliagesModifications) {
			LOGGER.info("removing BetterFoliage's modifications...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.removeBetterFoliagesModifications(instructions)) {
				LOGGER.info("removed BetterFoliage's modifications");
			} else {
				LOGGER.error("failed to remove BetterFoliage's modifications");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		if (INJECT_RebuildChunkBlockRenderInTypeEvent) {
			LOGGER.info("injecting RebuildChunkBlockRenderInTypeEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.injectRebuildChunkBlockRenderInTypeEventHook(instructions)) {
				LOGGER.info("injected RebuildChunkBlockRenderInTypeEvent Hook");
			} else {
				LOGGER.error("failed to inject RebuildChunkBlockRenderInTypeEvent Hook");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		if (INJECT_RebuildChunkBlockEvent) {
			LOGGER.info("injecting RebuildChunkBlockEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.injectRebuildChunkBlockEventHook(instructions)) {
				LOGGER.info("injected RebuildChunkBlockEvent Hook");
			} else {
				LOGGER.error("failed to inject RebuildChunkBlockEvent Hook");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

		if (INJECT_RebuildChunkPostEvent) {
			LOGGER.info("injecting RebuildChunkPostEvent Hook...");
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
			if (this.injectRebuildChunkPostEventHook(instructions)) {
				LOGGER.info("injected RebuildChunkPostEvent Hook");
			} else {
				LOGGER.error("failed to inject RebuildChunkPostEvent Hook");
			}
			if (DEBUG_INSTRUCTIONS) {
				for (int i = 0; i < instructions.size(); i++) {
					LOGGER.info(insnToString(instructions.get(i)));
				}
			}
		}

	}

	public abstract boolean injectRebuildChunkPreEventHook(InsnList instructions);

	public abstract boolean injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions);

	public boolean removeBetterFoliagesModifications(InsnList instructions) {        //		// where: RenderChunk.rebuildChunk()
		//		// what: replace call to BlockRendererDispatcher.renderBlock()
		//		// why: allows us to perform additional rendering for each block
		//		transformMethod(Refs.rebuildChunk) {
		//			find(invokeRef(Refs.renderBlock))?.replace {
		//				log.info("[BetterFoliageLoader] Applying RenderChunk block render override")
		//				varinsn(ALOAD, if (isOptifinePresent) 22 else 20)
		//				invokeStatic(Refs.renderWorldBlock)
		//			}
		//		}

		//		ALOAD 13
		//		ALOAD 18
		//		ALOAD 17
		//		ALOAD 11
		//		ALOAD 24
		//-		INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z
		//+		ALOAD 22 | 20
		//+		INVOKESTATIC mods/betterfoliage/client/Hooks.renderWorldBlock (Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/BlockRenderLayer;)Z

		MethodInsnNode INVOKESTATIC_Hooks_renderWorldBlock_Node = null;

		// Find the bytecode instruction for "Hooks.renderWorldBlock" ("INVOKESTATIC mods/betterfoliage/client/Hooks.renderWorldBlock (Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/BlockRenderLayer;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (instruction.getOpcode() == INVOKESTATIC) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (BETTER_FOLIAGE_RENDER_WORLD_BLOCK.matches((MethodInsnNode) instruction)) {
						INVOKESTATIC_Hooks_renderWorldBlock_Node = (MethodInsnNode) instruction;
						break;
					}
				}
			}

		}

		if (INVOKESTATIC_Hooks_renderWorldBlock_Node == null) {
			new RuntimeException("Couldn't find BetterFoliage's modifications!").printStackTrace();
			return false;
		}

		// add back BlockRenderDispatcher.renderBlock ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		instructions.insert(INVOKESTATIC_Hooks_renderWorldBlock_Node, new MethodInsnNode(INVOKEVIRTUAL, BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.getOwner().getInternalName(), BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.getName(), BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.getDescriptor(), BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.isInterface()));

		instructions.remove(INVOKESTATIC_Hooks_renderWorldBlock_Node.getPrevious());
		instructions.remove(INVOKESTATIC_Hooks_renderWorldBlock_Node);

		return true;

	}

	public abstract boolean injectRebuildChunkBlockEventHook(InsnList instructions);

	public abstract boolean injectRebuildChunkBlockRenderInTypeEventHook(InsnList instructions);

	public abstract boolean injectRebuildChunkPostEventHook(InsnList instructions);

}
