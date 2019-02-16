package io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer;

import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.InjectionHelper;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.ArrayList;

import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationField.ENUM_BLOCK_RENDER_TYPE_INVISIBLE;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_CHUNKS_UPDATED;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_GLOBAL;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.CAN_BLOCK_RENDER_IN_LAYER_OPTIFINE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.DOES_BLOCK_TYPE_ALLOW_RENDER_OPTIFINE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.ON_REBUILD_CHUNK_PRE_OPTIFINE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.REBUILD_CHUNK_BLOCK_OPTIFINE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.REBUILD_CHUNK_POST_OPTIFINE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.RENDER_CHUNK_MAKE_CHUNK_CACHE_OF;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_LOGGER;

/**
 * @author Cadiboo
 * @see "http://www.egtry.com/java/bytecode/asm/tree_transform"
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerForgeOptifine extends RenderChunkRebuildChunkHooksRenderChunkClassTransformerForge {

	private static final int ALOAD_blockAccess_chunkCacheOF = 11;
	private static final int ALOAD_aboolean = 12;
	private static final int ALOAD_blockrendererdispatcher = 13;
	private static final int ALOAD_blockpos$mutableblockpos = 17;
	private static final int ALOAD_iblockstate = 18;
	private static final int ALOAD_block = 19;
	private static final int ALOAD_blockrenderlayer1 = 22;
	private static final int ALOAD_bufferbuilder = 24;

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {
		return super.transform(unTransformedName, transformedName, basicClass);
	}

	@Override
	public void injectHooks(InsnList instructions) {
		super.injectHooks(instructions);
	}

	/**
	 * get "++renderChunksUpdated;"
	 * inject after
	 * get line number for nice debug
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkPreEventHook(InsnList instructions) {
		FieldInsnNode PUTSTATIC_renderChunksUpdated_Node = null;

		// Find the bytecode instruction for "++renderChunksUpdated" ("PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I")
		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (RENDER_CHUNK_RENDER_CHUNKS_UPDATED.matches(instruction)) {
				PUTSTATIC_renderChunksUpdated_Node = (FieldInsnNode) instruction;
				break;
			}
		}

		if (PUTSTATIC_renderChunksUpdated_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LabelNode preExistingLabelNode = null;

		// go down the instructions until we find the next Label
		for (int i = instructions.indexOf(PUTSTATIC_renderChunksUpdated_Node); i < instructions.size() - 1; i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNode = (LabelNode) instructions.get(i);
			break;
		}

		if (preExistingLabelNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		final LabelNode oldPreExistingLabelNode = preExistingLabelNode;

		// go down the instructions until we find the next Label
		for (int i = instructions.indexOf(oldPreExistingLabelNode) + 1; i < instructions.size() - 1; i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNode = (LabelNode) instructions.get(i);
			break;
		}

		if (preExistingLabelNode == oldPreExistingLabelNode) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the Line Number Node
		for (int i = instructions.indexOf(PUTSTATIC_renderChunksUpdated_Node) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructions.get(i);
			break;
		}

		if (preExistingLineNumberNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		final InsnList tempInstructionList = new InsnList();

		LabelNode ourLabel = new LabelNode(new Label());
		tempInstructionList.add(ourLabel);
		tempInstructionList.add(new LineNumberNode(preExistingLineNumberNode.line, ourLabel));

		/*
		 * @param renderChunk   the instance of {@link RenderChunk}
		 * @param x             the translation X passed in from RenderChunk#rebuildChunk
		 * @param y             the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z             the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator     the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos      the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param blockAccess   the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_      the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_     the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal  the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @return If vanilla rendering should be stopped
		 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		ON_REBUILD_CHUNK_PRE_OPTIFINE_HOOK.visit(tempInstructionList);
		tempInstructionList.add(new LabelNode(new Label()));
		tempInstructionList.add(new JumpInsnNode(IFEQ, preExistingLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

		instructions.insertBefore(preExistingLabelNode, tempInstructionList);

		return true;
	}

	/**
	 * find "Reflector.ForgeBlock_canRenderInLayer" "GETSTATIC net/optifine/reflect/Reflector.ForgeBlock_canRenderInLayer : Lnet/optifine/reflect/ReflectorMethod;"
	 * make sure the instruction directly after it isn't "ReflectorMethod.exists()"
	 * get the line number for the instruction (so we don't remove it)
	 * get the ISTORE instruction afterwards (so we don't remove it)
	 * remove all instructions between the line number and the ISTORE instruction
	 * inject our hook BEFORE the ISTORE instruction
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions) {
		final AbstractInsnNode canRenderInLayer = InjectionHelper.getCanRenderInBlockInjectionPoint(instructions);

		if (canRenderInLayer == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the line number for the instruction (so we don't remove it)
		for (int i = instructions.indexOf(canRenderInLayer) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructions.get(i);
			break;
		}

		if (preExistingLineNumberNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		VarInsnNode preExistingISTOREInstructionNode = null;

		// go back down the instructions until we find the ISTORE instruction (so we don't remove it)
		for (int i = instructions.indexOf(canRenderInLayer); i < instructions.indexOf(canRenderInLayer) + 20; i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.VAR_INSN) {
				continue;
			}
			if (instructions.get(i).getOpcode() != ISTORE) {
				continue;
			}

			//WTF. BF appears to be injecting their hook twice
			// store 1st occurrence and keep searching for second
			if (preExistingISTOREInstructionNode != null) {
				preExistingISTOREInstructionNode = (VarInsnNode) instructions.get(i);
				break;
			}
			preExistingISTOREInstructionNode = (VarInsnNode) instructions.get(i);
		}

		if (preExistingISTOREInstructionNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		//remove all instructions between the line number and the ISTORE instruction
		ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
		for (int i = instructions.indexOf(preExistingLineNumberNode) + 1; i < instructions.indexOf(preExistingISTOREInstructionNode); ++i) {
			instructionsToRemove.add(instructions.get(i));
		}
		for (AbstractInsnNode instructionToRemove : instructionsToRemove) {
			if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_INSTRUCTIONS) {
				MOD_LOGGER.warn("removing instruction :" + insnToString(instructionToRemove));
			}
			instructions.remove(instructionToRemove);
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		 * @param renderChunk              the instance of {@link RenderChunk}
		 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param blockAccess              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPosM} of the block the event is firing for
		 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
		 * @param block                    the {@link Block} the event is firing for
		 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
		 * @return If the block can render in the layer
		 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		CAN_BLOCK_RENDER_IN_LAYER_OPTIFINE_HOOK.visit(tempInstructionList);

		// Inject our instructions right BEFORE the ISTORE instruction
		instructions.insertBefore(preExistingISTOREInstructionNode, tempInstructionList);

		return true;
	}

	/**
	 * find GETSTATIC net/minecraft/util/EnumBlockRenderType.INVISIBLE : Lnet/minecraft/util/EnumBlockRenderType;
	 * get line number for GETSTATIC instruction
	 * get next IF_ACMPEQ (jump if objects are equal) instruction
	 * delete everything between line number and IF_ACMPEQz
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkBlockRenderInTypeEventHook(InsnList instructions) {
		FieldInsnNode GETSTATIC_EnumBlockRenderType_INVISIBLE_Node = null;

		// Find the bytecode instruction for "BlockRendererDispatcher.renderBlock" ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (ENUM_BLOCK_RENDER_TYPE_INVISIBLE.matches(instruction)) {
				GETSTATIC_EnumBlockRenderType_INVISIBLE_Node = (FieldInsnNode) instruction;
				break;
			}

		}

		if (GETSTATIC_EnumBlockRenderType_INVISIBLE_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the Line Number Node
		for (int i = instructions.indexOf(GETSTATIC_EnumBlockRenderType_INVISIBLE_Node) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructions.get(i);
			break;
		}

		if (preExistingLineNumberNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		JumpInsnNode preExistingIF_ACMPEQNode = null;

		// go back down the instructions until we find the first IF_ACMPEQ instruction
		for (int i = instructions.indexOf(GETSTATIC_EnumBlockRenderType_INVISIBLE_Node); i < instructions.indexOf(GETSTATIC_EnumBlockRenderType_INVISIBLE_Node) + 5; i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.JUMP_INSN) {
				continue;
			}
			preExistingIF_ACMPEQNode = (JumpInsnNode) instructions.get(i);
			break;
		}

		if (preExistingIF_ACMPEQNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LabelNode returnLabel = preExistingIF_ACMPEQNode.label;

		//remove all instructions between the line number and the IF_ACMPEQ instruction
		ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
		for (int i = instructions.indexOf(preExistingLineNumberNode) + 1; i < instructions.indexOf(preExistingIF_ACMPEQNode); ++i) {
			instructionsToRemove.add(instructions.get(i));
		}
		for (AbstractInsnNode instructionToRemove : instructionsToRemove) {
			if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer.DEBUG_INSTRUCTIONS) {
				MOD_LOGGER.warn("removing instruction :" + insnToString(instructionToRemove));
			}
			instructions.remove(instructionToRemove);
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		 * @param renderChunk              the instance of {@link RenderChunk}
		 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param blockAccess              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPosM} of the block the event is firing for
		 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
		 * @param block                    the {@link Block} the event is firing for
		 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
		 * @param j                        the ordinal of the {@link BlockRenderLayer} the event is firing for
		 * @return if the block should be rendered
		 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(ILOAD, ILOAD_j)); // j
		DOES_BLOCK_TYPE_ALLOW_RENDER_OPTIFINE_HOOK.visit(tempInstructionList);
		tempInstructionList.add(new JumpInsnNode(IFEQ, returnLabel));

		// Inject our instructions right BEFORE IF_ACMPEQ
		instructions.insertBefore(preExistingIF_ACMPEQNode, tempInstructionList);

		instructions.remove(preExistingIF_ACMPEQNode);

		return true;
	}

	/**
	 * find "blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);"
	 * "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z"
	 * get return label
	 * get line number for nice debug
	 * inject before
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkBlockEventHook(InsnList instructions) {
		MethodInsnNode INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = null;

		// Find the bytecode instruction for "BlockRendererDispatcher.renderBlock" ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.matches(instruction)) {
				INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = (MethodInsnNode) instruction;
				break;
			}
		}

		if (INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LabelNode preExistingLabelNode = null;

		// go back up the instructions until we find the Label for the "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z" instruction
		for (int i = instructions.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNode = (LabelNode) instructions.get(i);
			break;
		}

		if (preExistingLabelNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		VarInsnNode preExistingVarInsNode = null;

		// go back down the instructions until we find the first VarInsnNode instruction
		for (int i = instructions.indexOf(preExistingLabelNode); i < instructions.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node); i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.VAR_INSN) {
				continue;
			}
			preExistingVarInsNode = (VarInsnNode) instructions.get(i);
			break;
		}

		if (preExistingVarInsNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		LabelNode returnLabel = null;

		// go back down the instructions until we find the LabelNode for all the following instructions
		for (int i = instructions.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node); i < (instructions.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node) + 25); i++) {
			if (instructions.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			returnLabel = (LabelNode) instructions.get(i);
			break;
		}

		if (returnLabel == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		 * @param renderChunk              the instance of {@link RenderChunk}
		 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param blockAccess              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPosM} of the block the event is firing for
		 * @param iblockstate              the {@link IBlockState} of the block the event is firing for
		 * @param block                    the {@link Block} the event is firing for
		 * @param blockrenderlayer1        the {@link BlockRenderLayer} the event is firing for
		 * @param j                        the ordinal of the {@link BlockRenderLayer} the event is firing for
		 * @param bufferbuilder            the {@link BufferBuilder} for the {@link BlockRenderLayer} the event is firing for
		 * @return If the block should NOT be rebuilt to the chunk by vanilla
		 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(ILOAD, ILOAD_j)); // j
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_bufferbuilder)); // bufferbuilder
		REBUILD_CHUNK_BLOCK_OPTIFINE_HOOK.visit(tempInstructionList);
		tempInstructionList.add(new JumpInsnNode(IFNE, returnLabel));

		// Inject our instructions right BEFORE first VarsInsNode
		instructions.insertBefore(preExistingVarInsNode, tempInstructionList);

		return true;
	}

	/**
	 * find last return statement in method
	 * inject before
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkPostEventHook(InsnList instructions) {
		InsnNode RETURN_Node = null;

		//Iterate backwards over the instructions to get the last return statement in the method
		for (int i = instructions.size() - 1; i > 0; i--) {
			AbstractInsnNode instruction = instructions.get(i);

			if (instruction.getOpcode() == RETURN) {
				if (instruction.getType() == AbstractInsnNode.INSN) {
					RETURN_Node = (InsnNode) instruction;
					break;
				}
			}

		}

		if (RETURN_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		 * @param renderChunk   the instance of {@link RenderChunk}
		 * @param x             the translation X passed in from RenderChunk#rebuildChunk
		 * @param y             the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z             the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator     the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos      the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param blockAccess   the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_      the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_     the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal  the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @see io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
		{
//		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
			// this doesn't exist on the stack anymore, so we need to re-create it
			// TODO: store it somewhere accessible on the stack so we don't need to re-create it

			//this.makeChunkCacheOF(position);  // worldView | blockAccess | chunkCacheOF
			tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
			tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos)); // blockpos | position
			RENDER_CHUNK_MAKE_CHUNK_CACHE_OF.visit(tempInstructionList);
		}
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		REBUILD_CHUNK_POST_OPTIFINE_HOOK.visit(tempInstructionList);

		// Inject our instructions right BEFORE the RETURN instruction
		instructions.insertBefore(RETURN_Node, tempInstructionList);

		return true;
	}

}
