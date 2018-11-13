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
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;
import org.objectweb.asm.util.TraceMethodVisitor;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.core.util.INames;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkAllBlocksEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.BlockRenderLayer;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, INames {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader reading frames if the class writer is going to compute them for us (if it is you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final int	rebuildChunkAllBlocksEvent_STACK	= 50;
	public static final int	rebuildChunkBlockEvent_STACK		= rebuildChunkAllBlocksEvent_STACK + 1;

	public static final Logger LOGGER = LogManager.getLogger();

	private static final boolean DEBUG_DUMP_BYTECODE = true;

	public static final boolean	DEBUG_EVERYTHING	= false;
	public static final boolean	DEBUG_CLASSES		= DEBUG_EVERYTHING | false;
	public static final boolean	DEBUG_TYPES			= DEBUG_EVERYTHING | false;
	public static final boolean	DEBUG_METHODS		= DEBUG_EVERYTHING | false;
	public static final boolean	DEBUG_INSTRUCTIONS	= DEBUG_EVERYTHING | false;

	static {
		if (DEBUG_TYPES) {
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

		if (DEBUG_CLASSES) {
			if ((unTransformedName.startsWith("b") || unTransformedName.startsWith("net.minecraft.client.renderer.chunk.")) || (transformedName.startsWith("b") || transformedName.startsWith("net.minecraft.client.renderer.chunk."))) {
				LOGGER.info("unTransformedName: " + unTransformedName + ", transformedName: " + transformedName + ", unTransformedName equals: " + unTransformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME) + ", transformedName equals: " + transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME));
			}
		}

		if (!transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME)) {
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

		this.injectRebuildChunkAllBlocksEventField(classNode.fields);
		this.injectRebuildChunkBlockEventField(classNode.fields);

		// peek at classNode and modifier
		for (final MethodNode method : classNode.methods) {

			if (!method.desc.equals(REBUILD_CHUNK_DESCRIPTOR)) {
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

	private void injectRebuildChunkAllBlocksEventField(final List<FieldNode> fields) {
		fields.add(new FieldNode(ACC_PUBLIC + ACC_SYNTHETIC, "rebuildChunkAllBlocksEvent", Type.getDescriptor(RebuildChunkAllBlocksEvent.class), null, null));
	}

	private void injectRebuildChunkBlockEventField(final List<FieldNode> fields) {
		fields.add(new FieldNode(ACC_PUBLIC + ACC_SYNTHETIC, "rebuildChunkBlockEvent", Type.getDescriptor(RebuildChunkBlockEvent.class), null, null));
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
	 * <font color="#FDCA42">if(cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkPreEvent(this, renderGlobal, worldView, generator, compiledchunk, position, x, y, z))return;</font>
	 * VisGraph lvt_9_1_ = new VisGraph();
	 * HashSet lvt_10_1_ = Sets.newHashSet();
	 * </pre>
	 *
	 * <br>
	 * The way we do this is by<br>
	 * 1) Finding the bytecode instruction for "<code>new VisGraph()</code>" ("<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>")<br>
	 * 2) Finding the Label(L0) for the "<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>" instruction<br>
	 * 3) Injecting a new Label(L1) for the instructions following our instructions (all the instructions after the Label(L0) for the "<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>" instruction)<br>
	 * 4) Setting the Label(L3) of the Line Number following our injected instructions Label(L1) we just added <br>
	 * 5) Injecting our instructions right AFTER the Label(L0) for the "<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>" instruction<br>
	 * <br>
	 * We have to do it in this way because if we just inject our instructions above the Label for the "<code>NEW net/minecraft/client/renderer/chunk/VisGraph</code>" instruction they never get called and get turned into <code>NOP</code>s <br>
	 * https://asm.ow2.io/developer-guide.html (Section: 3.5.4 Dead code)
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkPreEvent(final InsnList instructionList) {

		LOGGER.info("injecting RebuildChunkPreEvent Hook...");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

		TypeInsnNode NEW_VisGraph_Node = null;

		// Find the bytecode instruction for "new VisGraph()" ("NEW net/minecraft/client/renderer/chunk/VisGraph")
		final ListIterator<AbstractInsnNode> instructionsIterator = instructionList.iterator();
		while (instructionsIterator.hasNext()) {
			final AbstractInsnNode instruction = instructionsIterator.next();

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

		if (preExistingLabelNodeThatWeRepurpose == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the Line Number for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction
		for (int i = instructionList.indexOf(NEW_VisGraph_Node) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructionList.get(i);
			break;
		}

		if (preExistingLineNumberNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		// add a line number node with a line 1 above that of the original instructions
		final int injectionLineNumber = preExistingLineNumberNode.line - 1;
		tempInstructionList.add(new LineNumberNode(injectionLineNumber, preExistingLabelNodeThatWeRepurpose));

		// Create a new label for the instructions following our instructions (all the instructions after the Label for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction)
		final LabelNode injectedLabelNode = new LabelNode(new Label());

		// add our hook
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
		tempInstructionList.add(new JumpInsnNode(IFEQ, injectedLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

		// Inject the new label for the instructions following our instructions (all the instructions after the Label for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction)
		tempInstructionList.add(injectedLabelNode);
		tempInstructionList.add(new FrameNode(F_SAME, 0, null, 0, null));

		// Inject our instructions right AFTER the Label for the "NEW net/minecraft/client/renderer/chunk/VisGraph" instruction
		instructionList.insert(preExistingLabelNodeThatWeRepurpose, tempInstructionList);

		// Set the line number of the following instructions to the new node
		preExistingLineNumberNode.start = injectedLabelNode;

		LOGGER.info("injected RebuildChunkPreEvent Hook");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

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
	 * <font color="#FDCA42">final RebuildChunkAllBlocksEvent rebuildChunkAllBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkAllBlocksEvent(this, this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);</font>
	 * if (!this.worldView.isEmpty())
	 * {
	 *     ++renderChunksUpdated;
	 *<font color="#FDCA42">     boolean[] aboolean = rebuildChunkAllBlocksEvent.getUsedBlockRenderLayers();</font>
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
	 * <br>
	 * 7) Finding the bytecode instruction for "<code>new boolean[BlockRenderLayer.values().length]</code>" ("<code>NEWARRAY T_BOOLEAN</code>")<br>
	 * 8) Injecting a call to {@link RebuildChunkAllBlocksEvent#getUsedBlockRenderLayers() rebuildChunkAllBlocksEvent.getUsedBlockRenderLayers()} BEFORE the call to {@link BlockRenderLayer#values()}<br>
	 * 9) Removing the call to {@link BlockRenderLayer#values()} and the next 2 instructions<br>
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkAllBlocksEventLocalVariableAndRenderLayersUsedChange(final InsnList instructionList) {

		LOGGER.info("injecting RebuildChunkAllBlocksEvent LocalVariable and RenderLayersUsed Changes...");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

		// add local variable
		// (steps 1-6)
		{

			LOGGER.info("injecting RebuildChunkAllBlocksEvent LocalVariable...");

			FieldInsnNode GETSTATIC_renderChunksUpdated_Node = null;

			// Find the bytecode instruction for "++renderChunksUpdated" ("GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I")
			final ListIterator<AbstractInsnNode> instructionsIterator = instructionList.iterator();
			while (instructionsIterator.hasNext()) {
				final AbstractInsnNode instruction = instructionsIterator.next();

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

			if (preExistingLabelNodeFor_renderChunksUpdated == null) {
				new RuntimeException("Couldn't find injection point!").printStackTrace();
				return;
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

			if (preExistingLabelNodeForIfStatementThatWeRepurpose == null) {
				new RuntimeException("Couldn't find injection point!").printStackTrace();
				return;
			}

			LineNumberNode preExistingLineNumberNode = null;

			// go down through the instructions until we find the Line number node
			for (int i = instructionList.indexOf(preExistingLabelNodeForIfStatementThatWeRepurpose); i < instructionList.indexOf(preExistingLabelNodeFor_renderChunksUpdated); i++) {
				if (instructionList.get(i).getType() != AbstractInsnNode.LINE) {
					continue;
				}
				preExistingLineNumberNode = (LineNumberNode) instructionList.get(i);
				break;
			}

			if (preExistingLineNumberNode == null) {
				new RuntimeException("Couldn't find injection point!").printStackTrace();
				return;
			}

			final InsnList tempInstructionList = new InsnList();

			// add a line number node with a line 1 above that of the original instructions
			final int injectionLineNumber = preExistingLineNumberNode.line - 1;
			tempInstructionList.add(new LineNumberNode(injectionLineNumber, preExistingLabelNodeForIfStatementThatWeRepurpose));

//			// add our rebuildChunkAllBlocksEvent local variable hook
//			tempInstructionList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//			tempInstructionList.add(new LdcInsnNode("RebuildChunkAllBlocksEvent Placeholder Code"));
//			tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

			// final instructions for when I figure out how
			tempInstructionList.add(new VarInsnNode(ALOAD, 0));
			tempInstructionList.add(new VarInsnNode(ALOAD, 0));
			tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;"));
			tempInstructionList.add(new VarInsnNode(ALOAD, 0));
			tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;"));
			tempInstructionList.add(new VarInsnNode(ALOAD, 4));
			tempInstructionList.add(new VarInsnNode(ALOAD, 5));
			tempInstructionList.add(new VarInsnNode(ALOAD, 7));
			tempInstructionList.add(new VarInsnNode(ALOAD, 8));
			tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false));
			tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false));
			tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false));
			tempInstructionList.add(new VarInsnNode(ALOAD, 0));
			tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
			tempInstructionList.add(new VarInsnNode(FLOAD, 1));
			tempInstructionList.add(new VarInsnNode(FLOAD, 2));
			tempInstructionList.add(new VarInsnNode(FLOAD, 3));
			tempInstructionList.add(new VarInsnNode(ALOAD, 10));
			tempInstructionList.add(new VarInsnNode(ALOAD, 9));
			tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkAllBlocksEvent",
					"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkAllBlocksEvent;",
					false));
			tempInstructionList.add(new VarInsnNode(ASTORE, rebuildChunkAllBlocksEvent_STACK));

			// Inject a new label for the original instructions following our instructions
			final LabelNode injectedLabelNode = new LabelNode(new Label());
			tempInstructionList.add(injectedLabelNode);

			// Set the line number of the following instructions to the new node
			preExistingLineNumberNode.start = injectedLabelNode;

			// Inject our instructions right AFTER the Label for the original instructions
			instructionList.insert(preExistingLabelNodeForIfStatementThatWeRepurpose, tempInstructionList);

			LOGGER.info("injected RebuildChunkAllBlocksEvent LocalVariable");

		}

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

		// change
		// boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
		// to
		// boolean[] aboolean = rebuildChunkAllBlocksEvent.getUsedBlockRenderLayers();
		// (steps 7-9)
		{

			LOGGER.info("injecting RenderLayersUsed Changes...");

			IntInsnNode NEWARRAY_T_BOOLEAN_Node = null;

			// Find the bytecode instruction for "new boolean[BlockRenderLayer.values().length]" ("NEWARRAY T_BOOLEAN")
			final ListIterator<AbstractInsnNode> instructionsIterator = instructionList.iterator();
			while (instructionsIterator.hasNext()) {
				final AbstractInsnNode instruction = instructionsIterator.next();

				// L22
				// LINENUMBER 155 L22
				// INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
				// ARRAYLENGTH
				// # NEWARRAY T_BOOLEAN //INJECTION POINT
				// ASTORE 12

				if (instruction.getOpcode() == NEWARRAY) {
					if (instruction.getType() == AbstractInsnNode.INT_INSN) {
						final IntInsnNode fieldInsnNode = (IntInsnNode) instruction;
						if (fieldInsnNode.operand == T_BOOLEAN) {
							NEWARRAY_T_BOOLEAN_Node = fieldInsnNode;
							break;
						}
					}
				}

			}

			if (NEWARRAY_T_BOOLEAN_Node == null) {
				new RuntimeException("Couldn't find injection point!").printStackTrace();
				return;
			}

			MethodInsnNode INVOKESTATIC_BlockRenderLayer_values_Node = null;

			// go back up the instructions until we find the MethodInsn for the BlockRenderLayer.values() call
			for (int i = instructionList.indexOf(NEWARRAY_T_BOOLEAN_Node) - 1; i >= 0; i--) {
				if (instructionList.get(i).getType() != AbstractInsnNode.METHOD_INSN) {
					continue;
				}
				INVOKESTATIC_BlockRenderLayer_values_Node = (MethodInsnNode) instructionList.get(i);
				break;
			}

			if (INVOKESTATIC_BlockRenderLayer_values_Node == null) {
				new RuntimeException("Couldn't find injection point!").printStackTrace();
				return;
			}

			final InsnList tempInstructionList = new InsnList();

			// L22
			// LINENUMBER 155 L22
			// ALOAD 11 : rebuildChunkAllBlocksEvent
			// INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkAllBlocksEvent.getUsedBlockRenderLayers()[Z
			// ASTORE 12

//			 Inject a call to rebuildChunkAllBlocksEvent.getUsedBlockRenderLayers() BEFORE the call to BlockRenderLayer.values()
			tempInstructionList.add(new VarInsnNode(ALOAD, rebuildChunkAllBlocksEvent_STACK));
			tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkAllBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false));

			instructionList.insertBefore(INVOKESTATIC_BlockRenderLayer_values_Node, tempInstructionList);

			// cast to be notified early (by crashing) if they arent the variables we want
			final InsnNode ARRAYLENGTH_Node = (InsnNode) INVOKESTATIC_BlockRenderLayer_values_Node.getNext();

			// Remove the call to BlockRenderLayer.values() and the next 2 instructions
			instructionList.remove(INVOKESTATIC_BlockRenderLayer_values_Node);
			instructionList.remove(ARRAYLENGTH_Node);
			instructionList.remove(NEWARRAY_T_BOOLEAN_Node);

			LOGGER.info("injected RenderLayersUsed Changes");

		}

		LOGGER.info("injected RebuildChunkAllBlocksEvent LocalVariable and RenderLayersUsed Changes");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

	}

	/**
	 * This method inserts the the hook for our {@link RebuildChunkAllBlocksEvent} into {@link RenderChunk#rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) RenderChunk.rebuildChunk}<br>
	 * <br>
	 * Original code:
	 *
	 * <pre>
	 * }
	 *
	 * for(BlockRenderLayer blockrenderlayer1 : BlockRenderLayer.values()) {
	 *    if(!block.canRenderInLayer(iblockstate, blockrenderlayer1)) continue;
	 *    net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
	 * int j = blockrenderlayer1.ordinal();
	 *
	 * if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	 * </pre>
	 *
	 * <br>
	 * Code after hook insertion:
	 *
	 * <pre>
	 * }
	 *
	 * for(BlockRenderLayer blockrenderlayer1 : BlockRenderLayer.values()) {
	 *<font color="#FDCA42">    if(!cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.canBlockRenderInLayer(this, this.worldView, generator, compiledchunk, blockrendererdispatcher, this.position, lvt_9_1_, blockpos$mutableblockpos, block, iblockstate, blockrenderlayer1)) continue;</font>
	 *     net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
	 * int j = blockrenderlayer1.ordinal();
	 *
	 * if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	 * </pre>
	 *
	 * <br>
	 * The way we do this is by<br>
	 * 1) Finding the bytecode instruction for "<code>block.canRenderInLayer(iblockstate, blockrenderlayer1)</code>" ("<code>INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z</code>")<br>
	 * 2) Finding the ALOAD instructions before the instruction for "<code>block.canRenderInLayer(iblockstate, blockrenderlayer1)</code>"<br>
	 * 3) Injecting part of our ALOAD calls BEFORE the original ALOAD instructions<br>
	 * 2) Injecting a call to {@link RenderChunkRebuildChunkHooksHooks#canBlockRenderInLayer(RenderChunk, net.minecraft.world.ChunkCache, ChunkCompileTaskGenerator, net.minecraft.client.renderer.chunk.CompiledChunk, net.minecraft.client.renderer.BlockRendererDispatcher, net.minecraft.util.math.BlockPos.MutableBlockPos, net.minecraft.client.renderer.chunk.VisGraph, net.minecraft.util.math.BlockPos.MutableBlockPos, net.minecraft.block.Block, net.minecraft.block.state.IBlockState, BlockRenderLayer)
	 * RenderChunkRebuildChunkHooksHooks#canBlockRenderInLayer()} AFTER the original ALOAD instructions<br>
	 * 3) Removing the call to block.canRenderInLayer(iblockstate, blockrenderlayer1)
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkBlockRenderInLayerEvent(final InsnList instructionList) {

		LOGGER.info("injecting RebuildChunkBlockRenderInLayerEvent Hook...");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

		MethodInsnNode INVOKEVIRTUAL_Block_canRenderInLayer_Node = null;

		// Find the bytecode instruction for "block.canRenderInLayer(iblockstate, blockrenderlayer1)" ("NEW net/minecraft/client/renderer/chunk/VisGraph")
		final ListIterator<AbstractInsnNode> instructionsIterator = instructionList.iterator();
		while (instructionsIterator.hasNext()) {
			final AbstractInsnNode instruction = instructionsIterator.next();

			// L44
			// LINENUMBER 191 L44
			// ALOAD 17: block
			// ALOAD 16: iblockstate
			// ALOAD 18: blockrenderlayer1
			// # INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z //INJECTION POINT
			// IFNE L45
			// GOTO L46

			if (instruction.getOpcode() == INVOKEVIRTUAL) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (((MethodInsnNode) instruction).desc.equals(Block_canRenderInLayer_DESC)) {
						INVOKEVIRTUAL_Block_canRenderInLayer_Node = (MethodInsnNode) instruction;
						break;
					}
				}
			}

		}

		if (INVOKEVIRTUAL_Block_canRenderInLayer_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		// ALOAD 17: block
		// ALOAD 16: iblockstate
		// ALOAD 18: blockrenderlayer1
		// INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z

		// cast to be notified early (by crashing) if they arent the variables we want
		final VarInsnNode ALOAD_blockrenderlayer1_Node = (VarInsnNode) INVOKEVIRTUAL_Block_canRenderInLayer_Node.getPrevious();
		final VarInsnNode ALOAD_iblockstate_Node = (VarInsnNode) ALOAD_blockrenderlayer1_Node.getPrevious();
		final VarInsnNode ALOAD_block_Node = (VarInsnNode) ALOAD_iblockstate_Node.getPrevious();

		final InsnList tempInstructionList = new InsnList();

		// TODO find ALOAD refrences instead of hardcoding them

		// Inject a call to RenderChunkRebuildChunkHooksHooks#canBlockRenderInLayer()} AFTER the call to block.canRenderInLayer(iblockstate, blockrenderlayer1)
		// add our hook

		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 4));
		tempInstructionList.add(new VarInsnNode(ALOAD, 5));
		tempInstructionList.add(new VarInsnNode(ALOAD, 12));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 9));
		tempInstructionList.add(new VarInsnNode(ALOAD, 14));

		// Inject our instructions right BEFORE the Label for the "ALOAD ?: block" instruction
		instructionList.insertBefore(ALOAD_block_Node, tempInstructionList);

		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "canBlockRenderInLayer",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/chunk/VisGraph;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z",
				false));

		// Inject our instructions right AFTER the Label for the "ALOAD ?: blockrenderlayer1" instruction
		instructionList.insert(ALOAD_blockrenderlayer1_Node, tempInstructionList);

		// Remove the call to block.canRenderInLayer()
		instructionList.remove(INVOKEVIRTUAL_Block_canRenderInLayer_Node);

		LOGGER.info("injected RebuildChunkBlockRenderInLayerEvent Hook");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

	}

	/**
	 * This method inserts the the hook for our {@link RebuildChunkAllBlocksEvent} into {@link RenderChunk#rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) RenderChunk.rebuildChunk}<br>
	 * <br>
	 * Original code:
	 *
	 * <pre>
	 * if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	 * {
	 *     BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(j);
	 *
	 *     if (!compiledchunk.isLayerStarted(blockrenderlayer1))
	 *     {
	 *         compiledchunk.setLayerStarted(blockrenderlayer1);
	 *         this.preRenderBlocks(bufferbuilder, blockpos);
	 *     }
	 *
	 *     aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
	 * }
	 * }
	 * net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
	 * </pre>
	 *
	 * <br>
	 * Code after hook insertion:
	 *
	 * <pre>
	 * if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	 * {
	 *     BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(j);
	 *
	 *     if (!compiledchunk.isLayerStarted(blockrenderlayer1))
	 *     {
	 *         compiledchunk.setLayerStarted(blockrenderlayer1);
	 *         this.preRenderBlocks(bufferbuilder, blockpos);
	 *     }
	 *
	 * <font color="#FDCA42">
	 *     if (!rebuildChunkAllBlocksEvent.isCanceled()) {
	 *         final RebuildChunkBlockEvent rebuildBlockEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(this, this.renderGlobal, this.worldView, generator, this.compiledChunk, blockrendererdispatcher, iblockstate, blockpos$mutableblockpos, bufferbuilder, this.position, blockrenderlayer1, x, y, z, lvt_10_1_, lvt_9_1_);
	 *
	 *         for (final BlockRenderLayer blockRenderLayer2 : BlockRenderLayer.values()) {
	 *             final int blockRenderLayer2Id = blockRenderLayer2.ordinal();
	 *             aboolean[blockRenderLayer2Id] |= rebuildBlockEvent.getUsedBlockRenderLayers()[blockRenderLayer2Id];
	 *         }
	 *
	 *         if (!rebuildBlockEvent.isCanceled()) {
	 *             </font>aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);<font color="#FDCA42">
	 *         }
	 *     }</font>
	 * }
	 * }
	 * net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
	 * </pre>
	 *
	 * <br>
	 * The way we do this is by<br>
	 * 1) Finding the bytecode instruction for "<code>BlockRendererDispatcher.renderBlock</code>" ("<code>INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z</code>")<br>
	 * 2) going up from the instruction and finding it's label<br>
	 * 3) going down from the label and finding the first VarInsnNode instruction<br>
	 * 4) inserting our hook before the VarInsnNode
	 *
	 * @param instructionList the instruction list of RenderChunk#rebuildChunk
	 */
	private void injectRebuildChunkAllBlocksEventAndRebuildChunkBlockEventLogic(final InsnList instructionList) {

		LOGGER.info("injecting RebuildChunkAllBlocksEvent and RebuildChunkBlockEvent logic...");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
			}
		}

		MethodInsnNode INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = null;

		// Find the bytecode instruction for "BlockRendererDispatcher.renderBlock" ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		final ListIterator<AbstractInsnNode> instructionsIterator = instructionList.iterator();
		while (instructionsIterator.hasNext()) {
			final AbstractInsnNode instruction = instructionsIterator.next();

			// L44
			// LINENUMBER 191 L44
			// ALOAD 17: block
			// ALOAD 16: iblockstate
			// ALOAD 18: blockrenderlayer1
			// # INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z //INJECTION POINT
			// IFNE L45
			// GOTO L46

			if (instruction.getOpcode() == INVOKEVIRTUAL) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (((MethodInsnNode) instruction).name.equals(BlockRendererDispatcher_renderBlock)) {
						INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = (MethodInsnNode) instruction;
						break;
					}
				}
			}

		}

		if (INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		LabelNode preExistingLabelNode = null;

		// go back up the instructions until we find the Label for the "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z" instruction
		for (int i = instructionList.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node) - 1; i >= 0; i--) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNode = (LabelNode) instructionList.get(i);
			break;
		}

		if (preExistingLabelNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		VarInsnNode preExistingVarInsNode = null;

		// go back down the instructions until we find the first VarInsnNode instruction
		for (int i = instructionList.indexOf(preExistingLabelNode); i < instructionList.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node); i++) {
			if (instructionList.get(i).getType() != AbstractInsnNode.VAR_INSN) {
				continue;
			}
			preExistingVarInsNode = (VarInsnNode) instructionList.get(i);
			break;
		}

		if (preExistingVarInsNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		LabelNode returnLabel = null;

		// go back down the instructions until we find the LabelNode for all the following instructions
		for (int i = instructionList.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node); i < (instructionList.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node) + 25); i++) {
			if (instructionList.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			returnLabel = (LabelNode) instructionList.get(i);
			break;
		}

		if (returnLabel == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		// add our hook
		tempInstructionList.add(new VarInsnNode(ALOAD, rebuildChunkAllBlocksEvent_STACK));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkAllBlocksEvent", "isCanceled", "()Z", false));
		tempInstructionList.add(new JumpInsnNode(IFNE, returnLabel));
		final LabelNode l54 = new LabelNode(new Label());
		tempInstructionList.add(l54);
		tempInstructionList.add(new LineNumberNode(206, l54));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));// this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));// this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 4));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));// this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "compiledChunk", "Lnet/minecraft/client/renderer/chunk/CompiledChunk;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 13));
		tempInstructionList.add(new VarInsnNode(ALOAD, 16));
		tempInstructionList.add(new VarInsnNode(ALOAD, 14));
		tempInstructionList.add(new VarInsnNode(ALOAD, 23));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0));// this
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 18));
		tempInstructionList.add(new VarInsnNode(FLOAD, 1));
		tempInstructionList.add(new VarInsnNode(FLOAD, 2));
		tempInstructionList.add(new VarInsnNode(FLOAD, 3));
		tempInstructionList.add(new VarInsnNode(ALOAD, 10));
		tempInstructionList.add(new VarInsnNode(ALOAD, 9));
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlockEvent",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/util/BlockRenderLayer;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent;",
				false));
		tempInstructionList.add(new VarInsnNode(ASTORE, rebuildChunkBlockEvent_STACK));
		final LabelNode l55 = new LabelNode(new Label());
		tempInstructionList.add(l55);
		tempInstructionList.add(new LineNumberNode(208, l55));
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false));
		tempInstructionList.add(new InsnNode(DUP));
		tempInstructionList.add(new VarInsnNode(ASTORE, 28));
		tempInstructionList.add(new InsnNode(ARRAYLENGTH));
		tempInstructionList.add(new VarInsnNode(ISTORE, 27));
		tempInstructionList.add(new InsnNode(ICONST_0));
		tempInstructionList.add(new VarInsnNode(ISTORE, 26));
		final LabelNode l56 = new LabelNode(new Label());
		tempInstructionList.add(new JumpInsnNode(GOTO, l56));
		final LabelNode l57 = new LabelNode(new Label());
		tempInstructionList.add(l57);
		tempInstructionList.add(new FrameNode(F_FULL, 29,
				new Object[] { "net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkAllBlocksEvent", "[Z",
						"net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent", Opcodes.TOP,
						Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" },
				0, new Object[] {}));
		tempInstructionList.add(new VarInsnNode(ALOAD, 28));
		tempInstructionList.add(new VarInsnNode(ILOAD, 26));
		tempInstructionList.add(new InsnNode(AALOAD));
		tempInstructionList.add(new VarInsnNode(ASTORE, 25));
		final LabelNode l58 = new LabelNode(new Label());
		tempInstructionList.add(l58);
		tempInstructionList.add(new LineNumberNode(209, l58));
		tempInstructionList.add(new VarInsnNode(ALOAD, 25));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false));
		tempInstructionList.add(new VarInsnNode(ISTORE, 29));
		final LabelNode l59 = new LabelNode(new Label());
		tempInstructionList.add(l59);
		tempInstructionList.add(new LineNumberNode(210, l59));
		tempInstructionList.add(new VarInsnNode(ALOAD, 12));
		tempInstructionList.add(new VarInsnNode(ILOAD, 29));
		tempInstructionList.add(new InsnNode(DUP2));
		tempInstructionList.add(new InsnNode(BALOAD));
		tempInstructionList.add(new VarInsnNode(ALOAD, rebuildChunkBlockEvent_STACK));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent", "getUsedBlockRenderLayers", "()[Z", false));
		tempInstructionList.add(new VarInsnNode(ILOAD, 29));
		tempInstructionList.add(new InsnNode(BALOAD));
		tempInstructionList.add(new InsnNode(IOR));
		tempInstructionList.add(new InsnNode(BASTORE));
		final LabelNode l60 = new LabelNode(new Label());
		tempInstructionList.add(l60);
		tempInstructionList.add(new LineNumberNode(208, l60));
		tempInstructionList.add(new IincInsnNode(26, 1));
		tempInstructionList.add(l56);
		tempInstructionList.add(new FrameNode(F_SAME, 0, null, 0, null));
		tempInstructionList.add(new VarInsnNode(ILOAD, 26));
		tempInstructionList.add(new VarInsnNode(ILOAD, 27));
		tempInstructionList.add(new JumpInsnNode(IF_ICMPLT, l57));
		final LabelNode l61 = new LabelNode(new Label());
		tempInstructionList.add(l61);
		tempInstructionList.add(new LineNumberNode(213, l61));
		tempInstructionList.add(new VarInsnNode(ALOAD, rebuildChunkBlockEvent_STACK));
		tempInstructionList.add(new MethodInsnNode(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent", "isCanceled", "()Z", false));
		tempInstructionList.add(new JumpInsnNode(IFNE, returnLabel));
		final LabelNode l62 = new LabelNode(new Label());
		tempInstructionList.add(l62);
		tempInstructionList.add(new LineNumberNode(214, l62));

		// Inject our instructions right BEFORE first VarsInsNode
		instructionList.insertBefore(preExistingVarInsNode, tempInstructionList);

		LOGGER.info("injected RebuildChunkAllBlocksEvent and RebuildChunkBlockEvent logic");

		if (DEBUG_INSTRUCTIONS) {
			for (int i = 0; i < instructionList.size(); i++) {
				LOGGER.info(insnToString(instructionList.get(i)));
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

	private static final Printer			PRINTER					= new Textifier();
	private static final TraceMethodVisitor	TRACE_METHOD_VISITOR	= new TraceMethodVisitor(PRINTER);

}
