package cadiboo.renderchunkrebuildchunkhooks.core;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.core.util.INames;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, INames {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final boolean DEBUG_EVERYTHING = true;

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader reading frames if the class writer is going to compute them for us (if it is you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final Logger LOGGER = LogManager.getLogger();

	static {
		if (DEBUG_EVERYTHING) {
			for (final Field field : INames.class.getFields()) {
				Object value;
				try {
					value = field.get(INames.class);

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

		LOGGER.info("Preparing to inject hooks into \"" + transformedName + "\" (RenderChunk)");

		// read in, build classNode
		final ClassNode classNode = new ClassNode();
		final ClassReader cr = new ClassReader(basicClass);
		cr.accept(classNode, CLASS_READER_FLAGS);

//		for (final MethodNode method : classNode.methods) {
//			LOGGER.info("name=" + method.name + " desc=" + method.desc);
//			final InsnList insnList = method.instructions;
//			final ListIterator<AbstractInsnNode> ite = insnList.iterator();
//			while (ite.hasNext()) {
//				final AbstractInsnNode insn = ite.next();
//				final int opcode = insn.getOpcode();
//				// add before return: System.out.println("Returning...")
//				if (opcode == RETURN) {
//					final InsnList tempList = new InsnList();
//					tempList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//					tempList.add(new LdcInsnNode("Returning..."));
//					tempList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
//					insnList.insert(insn.getPrevious(), tempList);
//				}
//			}
//		}

//		for (final MethodNode method : classNode.methods) {
//			LOGGER.info("name=" + method.name + " desc=" + method.desc);
//			final InsnList insnList = method.instructions;
//			final ListIterator<AbstractInsnNode> ite = insnList.iterator();
//			AbstractInsnNode injectInsn = null;
//			while (ite.hasNext()) {
//				final AbstractInsnNode insn = ite.next();
//
//				if (insn.getOpcode() == NEW) {
//					if (insn.getType() == AbstractInsnNode.TYPE_INSN) {
//						if (((TypeInsnNode) insn).desc.equals(VIS_GRAPH_INTERNAL_NAME)) {
//							injectInsn = insn;
//							break;
//						}
//					}
//				}
//			}
//
//			if (injectInsn == null) {
//				continue;
//			}
//
//			final InsnList tempList = new InsnList();
//			tempList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//			tempList.add(new LdcInsnNode("VIS_GRAPH_NEW..."));
//			tempList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
//			insnList.insert(injectInsn.getPrevious(), tempList);
//		}

		if (DEBUG_EVERYTHING) {
			LOGGER.info("RebuildChunk type: " + REBUILD_CHUNK_TYPE);
			LOGGER.info("RebuildChunk descriptor: " + REBUILD_CHUNK_DESCRIPTOR);
		}

		// peek at classNode and modifier
		for (final MethodNode method : classNode.methods) {

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

			this.injectRebuildChunkPreEvent(method.instructions);
			this.injectRebuildChunkAllBlocksEventLocalVariableAndRenderLayersUsedChange(method.instructions);
			this.injectRebuildChunkBlockRenderInLayerEvent(method.instructions);
			this.injectRebuildChunkAllBlocksEventAndRebuildChunkBlockEventLogic(method.instructions);

		}

		// write classNode
		try {
			final ClassWriter out = new ClassWriter(CLASS_WRITER_FLAGS);

			// make the ClassWriter visit all the code in classNode
			classNode.accept(out);

			LOGGER.info("Injected hooks sucessfully!");

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

			return out.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			LOGGER.warn("Any mods that depend on the hooks provided by this mod will break");
			return basicClass;
		}

	}

	/**
	 * This method inserts the the hook for our {@link RebuildChunkPreEvent} into {@link RenderChunk#rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) RenderChunk.rebuildChunk}<br>
	 * <br>
	 * Original code:
	 *
	 * <pre>
	 * finally
	 * {
	 *	generator.getLock().unlock();
	 * }
	 *
	 * VisGraph lvt_9_1_ = new VisGraph();
	 * HashSet lvt_10_1_ = Sets.newHashSet();
	 * </pre>
	 *
	 * <br>
	 * Code after hook insertion:
	 *
	 * <pre>
	 * finally
	 * {
	 *	generator.getLock().unlock();
	 * }
	 * if(cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkPreEvent(this, renderGlobal, worldView, generator, compiledchunk, position, x, y, z))return;
	 * VisGraph lvt_9_1_ = new VisGraph();
	 * HashSet lvt_10_1_ = Sets.newHashSet();
	 * </pre>
	 *
	 * <br>
	 * The way we do this is by<br>
	 * 1) Finding the bytecode instruction for "<code>new VisGraph()</code>" ("<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>")<br>
	 * 2) Finding the Label(L0) for the "<code>NEW VisGraph</code>" instruction<br>
	 * 3) Injecting a new Label(L1) for the instructions following our instructions (all the instructions after the Label(L0) for the "<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>" instruction)<br>
	 * 4) Setting the Label(L3) of the Line Number following our injected instructions Label(L1) we just added <br>
	 * 5) Injecting our instructions right AFTER the Label(L0) for the "<code>NEW VisGraph</code>" instruction<br>
	 * <br>
	 * We have to do it in this way because if we just inject our instructions above the Label for the "<code>NEW VisGraph</code>" instruction they never get called and get turned into <code>NOP</code>s <br>
	 * https://asm.ow2.io/developer-guide.html (Section: 3.5.4 Dead code)
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkPreEvent(final InsnList instructionList) {

		TypeInsnNode NEW_VisGraph_Node = null;

		// Find the bytecode instruction for "new VisGraph()" ("NEW net/minecraft/client/renderer/chunk/VisGraph")
		final ListIterator<AbstractInsnNode> instructions = instructionList.iterator();
		while (instructions.hasNext()) {
			final AbstractInsnNode instruction = instructions.next();

			// L16
			// LINENUMBER 146 L16
			// # NEW net/minecraft/client/renderer/chunk/VisGraph //INJECTION POINT
			// DUP

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

		LabelNode preExistingLabelNodeThatWeRepurpose = null;

		// go back up the instructions until we find the Label for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction
		for (int i = instructionList.indexOf(NEW_VisGraph_Node) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNodeThatWeRepurpose = (LabelNode) instructionList.get(i);
			break;
		}

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the Line Number for the "NEW VisGraph" instruction
		for (int i = instructionList.indexOf(NEW_VisGraph_Node) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructionList.get(i);
			break;
		}

		final InsnList tempInstructionList = new InsnList();

		// add a line number node with a line 1 above that of the original instructions
		final int injectionLineNumber = preExistingLineNumberNode.line - 1;
		tempInstructionList.add(new LineNumberNode(injectionLineNumber, preExistingLabelNodeThatWeRepurpose));

		// add our hook (currently debug code)
		tempInstructionList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		tempInstructionList.add(new LdcInsnNode("Derek"));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

		// Inject a new label for the instructions following our instructions (all the instructions after the Label for the "NEW VisGraph" instruction)
		final LabelNode injectedLabelNode = new LabelNode(new Label());
		tempInstructionList.add(injectedLabelNode);

		// Set the line number of the following instructions to the new node
		preExistingLineNumberNode.start = injectedLabelNode;

		// Inject our instructions right AFTER the Label for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction
		instructionList.insert(preExistingLabelNodeThatWeRepurpose, tempInstructionList);

	}

	/**
	 * This method inserts the the hook for our {@link RebuildChunkAllBlocksEvent} into {@link RenderChunk#rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) RenderChunk.rebuildChunk}<br>
	 * <br>
	 * Original code:
	 *
	 * <pre>
	 * VisGraph lvt_9_1_ = new VisGraph();
	 * HashSet lvt_10_1_ = Sets.newHashSet();
	 *
	 * if (!this.worldView.isEmpty())
	 * {
	 *     ++renderChunksUpdated;
	 *     boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
	 *     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
	 * </pre>
	 *
	 * <br>
	 * Code after hook insertion:
	 *
	 * <pre>
	 * VisGraph lvt_9_1_ = new VisGraph();
	 * HashSet lvt_10_1_ = Sets.newHashSet();
	 * final RebuildChunkAllBlocksEvent rebuildChunkAllBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkAllBlocksEvent(this, this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
	 * if (!this.worldView.isEmpty())
	 * {
	 *     ++renderChunksUpdated;
	 *     boolean[] aboolean = rebuildChunkAllBlocksEvent.getUsedBlockRenderLayers();
	 *     BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
	 * </pre>
	 *
	 * <br>
	 * The way we do this is by<br>
	 * 1) Finding the bytecode instruction for "<code>++renderChunksUpdated</code>" ("<code>GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I</code>")<br>
	 * 2) Finding the Label(L0) for the "<code>++renderChunksUpdated</code>" instruction<br>
	 * 3) Finding the Label(L1) for the instructions right before the Label(L0) for the "<code>++renderChunksUpdated</code>" instruction<br>
	 * 4) Injecting a new Label(L2) for the instructions following our instructions<br>
	 * 5) Setting the Label(L3) of the Line Number for the instructions following our instructions to the Label(L2) we injected for the instructions following our instructions<br>
	 * 6) Injecting our instructions right AFTER the Label(L1) for the "<code>++renderChunksUpdated</code>" instruction<br>
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkAllBlocksEventLocalVariableAndRenderLayersUsedChange(final InsnList instructionList) {

		FieldInsnNode GETSTATIC_renderChunksUpdated_Node = null;

		// Find the bytecode instruction for "++renderChunksUpdated" ("GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I")
		final ListIterator<AbstractInsnNode> instructions = instructionList.iterator();
		while (instructions.hasNext()) {
			final AbstractInsnNode instruction = instructions.next();

			// L21
			// LINENUMBER 154 L21
			// # GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I // OPTIFINE CONSCIOUS INECTION POINT
			// ICONST_1
			// IADD
			// PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I

			if (instruction.getOpcode() == GETSTATIC) {
				if (instruction.getType() == AbstractInsnNode.FIELD_INSN) {
					final FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
					if (fieldInsnNode.desc.equals(Type.INT_TYPE.getDescriptor())) {
						if (fieldInsnNode.name.equals(STATIC_FIELD_renderChunksUpdated)) {
							GETSTATIC_renderChunksUpdated_Node = fieldInsnNode;
							break;
						}
					}
				}
			}

		}

		if (GETSTATIC_renderChunksUpdated_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		LabelNode preExistingLabelNodeFor_renderChunksUpdated = null;

		// go back up the instructions until we find the Label for the "GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I" instruction
		for (int i = instructionList.indexOf(GETSTATIC_renderChunksUpdated_Node) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNodeFor_renderChunksUpdated = (LabelNode) instructionList.get(i);
			break;
		}

		LabelNode preExistingLabelNodeForIfStatementThatWeRepurpose = null;

		// go back up the instructions until we find the Label for the instructions above the renderChunksUpdated Label
		for (int i = instructionList.indexOf(preExistingLabelNodeFor_renderChunksUpdated) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNodeForIfStatementThatWeRepurpose = (LabelNode) instructionList.get(i);
			break;
		}

		LineNumberNode preExistingLineNumberNode = null;

		LOGGER.info(preExistingLabelNodeForIfStatementThatWeRepurpose);
		LOGGER.info(instructionList.indexOf(preExistingLabelNodeForIfStatementThatWeRepurpose));
		LOGGER.info(preExistingLabelNodeFor_renderChunksUpdated);
		LOGGER.info(instructionList.indexOf(preExistingLabelNodeFor_renderChunksUpdated));

		// go down through the instructions until we find the Line number node
		for (int i = instructionList.indexOf(preExistingLabelNodeForIfStatementThatWeRepurpose); i < instructionList.indexOf(preExistingLabelNodeFor_renderChunksUpdated); i++) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructionList.get(i);
			break;
		}

		final InsnList tempInstructionList = new InsnList();

		// add a line number node with a line 1 above that of the original instructions
		final int injectionLineNumber = preExistingLineNumberNode.line - 1;
		tempInstructionList.add(new LineNumberNode(injectionLineNumber, preExistingLabelNodeForIfStatementThatWeRepurpose));

		// add our hook (currently debug code)
		tempInstructionList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
		tempInstructionList.add(new LdcInsnNode("JEFF"));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

		// Inject a new label for the instructions following our instructions (all the instructions after the Label for the "NEW VisGraph" instruction)
		final LabelNode injectedLabelNode = new LabelNode(new Label());
		tempInstructionList.add(injectedLabelNode);

		// Set the line number of the following instructions to the new node
		preExistingLineNumberNode.start = injectedLabelNode;

		// Inject our instructions right AFTER the Label for the "GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I" instruction
		instructionList.insert(preExistingLabelNodeForIfStatementThatWeRepurpose, tempInstructionList);

	}

	private void injectRebuildChunkBlockRenderInLayerEvent(final InsnList instructionList) {
		// TODO Auto-generated method stub

	}

	private void injectRebuildChunkAllBlocksEventAndRebuildChunkBlockEventLogic(final InsnList instructionList) {
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
