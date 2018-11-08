package cadiboo.renderchunkrebuildchunkhooks.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.core.util.Names;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, Names {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final boolean DEBUG_EVERYTHING = true;

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader reading frames if the class writer is going to compute them for us (if it is you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final Logger LOGGER = LogManager.getLogger();

	static {
		if (DEBUG_EVERYTHING) {
			for (final Field field : Names.class.getFields()) {
				Object value;
				try {
					value = field.get(Names.class);

					LOGGER.info(field.getName() + ": " + value);

				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("Error in <clinit>!");
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {

		if (DEBUG_EVERYTHING) {
			if ((unTransformedName.startsWith("b") || unTransformedName.startsWith("net.minecraft.client.renderer.chunk.")) || (transformedName.startsWith("b") || transformedName.startsWith("net.minecraft.client.renderer.chunk."))) {
				LOGGER.info("unTransformedName: " + unTransformedName + ", transformedName: " + transformedName + ", unTransformedName equals: " + unTransformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME) + ", transformedName equals: " + transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME));
			}
		}

		if (!transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME)) {
			return basicClass;
		}

		LOGGER.info("Preparing to inject hooks into \"" + transformedName + "\" (RenderChunk)");

		// read in, build classNode
		final ClassNode classNode = new ClassNode();
		final ClassReader cr = new ClassReader(basicClass);
		cr.accept(classNode, CLASS_READER_FLAGS);

		// peek at classNode and modifier
		final List<MethodNode> methods = classNode.methods;
		if (DEBUG_EVERYTHING) {
			LOGGER.info("RebuildChunk type: " + REBUILD_CHUNK_TYPE);
			LOGGER.info("RebuildChunk descriptor: " + REBUILD_CHUNK_DESCRIPTOR);
		}
		for (final MethodNode method : methods) {

			if (!method.desc.equals(REBUILD_CHUNK_DESCRIPTOR)) {
				if (DEBUG_EVERYTHING) {
					LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" did not match");
				}
				continue;
			}

			if (DEBUG_EVERYTHING) {
				LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched!");
			}

			// make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
			if (method.name.equals("a") || method.name.equals("resortTransparency")) {
				if (DEBUG_EVERYTHING) {
					LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" was rejected");
				}
				continue;
			}

			if (DEBUG_EVERYTHING) {
				LOGGER.info("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched and passed");
			}

			final InsnList insnList = method.instructions;

			this.injectRebuildChunkPreEvent(insnList);
			this.injectRebuildChunkAllBlocksEvent(insnList);
			this.injectRebuildChunkBlockRenderInLayerEvent(insnList);
			this.injectRebuildChunkBlockEvent(insnList);

		}

		// write classNode
		try {
			final ClassWriter out = new ClassWriter(CLASS_WRITER_FLAGS);

			// make the ClassWriter visit all the code in classNode
			classNode.accept(out);

			LOGGER.info("Injected hooks sucessfully!");
			return out.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			LOGGER.warn("Any mods that depend on the hooks provided by this mod will break");
			return basicClass;
		}

	}

//	generator.getLock().unlock();
//	}
//>	if(cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkPreEvent(this, renderGlobal, worldView, generator, compiledchunk, position, x, y, z))return;
//	VisGraph lvt_9_1_ = new VisGraph();
	private void injectRebuildChunkPreEvent(final InsnList instructionList) {

		final ListIterator<AbstractInsnNode> instructions = instructionList.iterator();

		TypeInsnNode NEW_VisGraph_Node = null;

		while (instructions.hasNext()) {
			final AbstractInsnNode instruction = instructions.next();

//			L16
//		    LINENUMBER 146 L16
//#		    NEW net/minecraft/client/renderer/chunk/VisGraph //injection point
//		    DUP

			if (instruction.getOpcode() == NEW) {
				if (instruction.getType() == AbstractInsnNode.TYPE_INSN) {
					if (((TypeInsnNode) instruction).desc.equals(VIS_GRAPH_INTERNAL_NAME)) {
						NEW_VisGraph_Node = (TypeInsnNode) instruction;
						break;
					}
				}
			}

		}

		if (NEW_VisGraph_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		tempInstructionList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		tempInstructionList.add(new LdcInsnNode("Derek"));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

		// add label
		final LabelNode executionLabelNode = new LabelNode();
		tempInstructionList.add(executionLabelNode);
		// add line number
		final int line = ((LineNumberNode) NEW_VisGraph_Node.getPrevious()).line - 1;
		tempInstructionList.add(new LineNumberNode(line, executionLabelNode));

		tempInstructionList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		tempInstructionList.add(new LdcInsnNode("Jeff"));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

		// execute method
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 4));
		tempInstructionList.add(new VarInsnNode(ALOAD, 5));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(FLOAD, 1));
		tempInstructionList.add(new VarInsnNode(FLOAD, 2));
		tempInstructionList.add(new VarInsnNode(FLOAD, 3));
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkPreEvent", "(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false));

		// return if required
		final LabelNode continueLabelNode = (LabelNode) NEW_VisGraph_Node.getPrevious().getPrevious();

		tempInstructionList.add(new JumpInsnNode(IFEQ, continueLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

//#		whatever instruction is here
//		L16
//	    LINENUMBER 146 L16
//	    NEW net/minecraft/client/renderer/chunk/VisGraph //injection point
//	    DUP
		// inject instructions
		final AbstractInsnNode injectInstructionsAfter = NEW_VisGraph_Node.getPrevious().getPrevious().getPrevious();
		instructionList.insert(injectInstructionsAfter, tempInstructionList);

		for (int i = 0; i < instructionList.size(); i++) {
			LOGGER.info(insnToString(instructionList.get(i)));
		}

	}

	private void injectRebuildChunkAllBlocksEvent(final InsnList instructionList) {
		// TODO Auto-generated method stub

	}

	private void injectRebuildChunkBlockRenderInLayerEvent(final InsnList instructionList) {
		// TODO Auto-generated method stub

	}

	private void injectRebuildChunkBlockEvent(final InsnList instructionList) {
		// TODO Auto-generated method stub

	}

	public static String insnToString(final AbstractInsnNode insn) {
		insn.accept(mp);
		final StringWriter sw = new StringWriter();
		printer.print(new PrintWriter(sw));
		printer.getText().clear();
		return sw.toString().trim();
	}

	private static final Printer			printer	= new Textifier();
	private static final TraceMethodVisitor	mp		= new TraceMethodVisitor(printer);

}
