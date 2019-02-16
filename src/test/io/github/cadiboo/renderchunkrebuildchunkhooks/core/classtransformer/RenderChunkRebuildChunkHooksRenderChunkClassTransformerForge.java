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
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationField.RENDER_CHUNK_WORLD_VIEW;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.CAN_BLOCK_RENDER_IN_LAYER_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.DOES_BLOCK_TYPE_ALLOW_RENDER_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.ON_REBUILD_CHUNK_PRE_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.REBUILD_CHUNK_BLOCK_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationMethod.REBUILD_CHUNK_POST_HOOK;
import static io.github.cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_LOGGER;

/**
 * @author Cadiboo
 * @see "http://www.egtry.com/java/bytecode/asm/tree_transform"
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerForge extends RenderChunkRebuildChunkHooksRenderChunkClassTransformer {

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
		 * @param worldView     the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
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
		RENDER_CHUNK_WORLD_VIEW.get(tempInstructionList); // worldView
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		ON_REBUILD_CHUNK_PRE_HOOK.visit(tempInstructionList);
		tempInstructionList.add(new LabelNode(new Label()));
		tempInstructionList.add(new JumpInsnNode(IFEQ, preExistingLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

		instructions.insertBefore(preExistingLabelNode, tempInstructionList);

		return true;
	}

	/**
	 * get "block.canRenderInLayer(iblockstate, blockrenderlayer);"
	 * "INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z"
	 * replace it with our hook and inject before & after (let the existing ALOADs stay because we use the same variables)
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions) {
		final AbstractInsnNode INVOKEVIRTUAL_Block_canRenderInLayer_Node = InjectionHelper.getCanRenderInBlockInjectionPoint(instructions);

		if (INVOKEVIRTUAL_Block_canRenderInLayer_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
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

		// TODO dynamically find ALOAD references instead of hardcoding them?
		// build a map of localVariableName->VarInsnNode.var

		// Inject a call to RenderChunkRebuildChunkHooksHooks#canBlockRenderInLayerHook()} AFTER the call to block.canRenderInLayer(iblockstate, blockrenderlayer1)
		// add our hook

		/*
		 * @param renderChunk              the instance of {@link RenderChunk}
		 * @param x                        the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                        the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                        the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator                the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk            the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockpos                 the {@link BlockPos position} stored in a local variable and passed in from RenderChunk#rebuildChunk
		 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
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
		RENDER_CHUNK_WORLD_VIEW.get(tempInstructionList); // worldView
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); //iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); //block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); //blockrenderlayer1
		CAN_BLOCK_RENDER_IN_LAYER_HOOK.visit(tempInstructionList);

		// Inject our instructions right AFTER the Label for the "ALOAD ?: blockrenderlayer1" instruction
		instructions.insert(ALOAD_blockrenderlayer1_Node, tempInstructionList);

		// Remove the call to block.canRenderInLayer()
		instructions.remove(INVOKEVIRTUAL_Block_canRenderInLayer_Node);
		// Remove the calls to loading the variables
		instructions.remove(ALOAD_blockrenderlayer1_Node);
		instructions.remove(ALOAD_iblockstate_Node);
		instructions.remove(ALOAD_block_Node);

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

		// Find the bytecode instruction for "EnumBlockRenderType.INVISIBLE"  net/minecraft/util/EnumBlockRenderType.INVISIBLE : Lnet/minecraft/util/EnumBlockRenderType;
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
		 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
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
		RENDER_CHUNK_WORLD_VIEW.get(tempInstructionList); // worldView
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); //iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); //block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); //blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(ILOAD, ILOAD_j)); //j
		DOES_BLOCK_TYPE_ALLOW_RENDER_HOOK.visit(tempInstructionList);
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
		 * @param worldView                the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param lvt_9_1_                 the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param lvt_10_1_                the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal             the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param aboolean                 the boolean[] of used {@link BlockRenderLayer}s
		 * @param blockrendererdispatcher  the {@link BlockRendererDispatcher}
		 * @param blockpos$mutableblockpos the {@link BlockPos} of the block the event is firing for
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
		RENDER_CHUNK_WORLD_VIEW.get(tempInstructionList); // worldView
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_aboolean));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); //iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); //block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); //blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(ILOAD, ILOAD_j)); //j
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_bufferbuilder)); //bufferbuilder
		REBUILD_CHUNK_BLOCK_HOOK.visit(tempInstructionList);
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
		 * @param worldView     the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
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
		RENDER_CHUNK_WORLD_VIEW.get(tempInstructionList); // worldView
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		RENDER_CHUNK_RENDER_GLOBAL.get(tempInstructionList);
		REBUILD_CHUNK_POST_HOOK.visit(tempInstructionList);

		// Inject our instructions right BEFORE the RETURN instruction
		instructions.insertBefore(RETURN_Node, tempInstructionList);

		return true;
	}

}
