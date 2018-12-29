package io.github.cadiboo.renderchunkrebuildchunkhooks.core.classtransformer;

import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.InjectionHelper;
import io.github.cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper;
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

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerForgeOptifine extends RenderChunkRebuildChunkHooksRenderChunkClassTransformerForge {

	private static final int ALOAD_iblockstate = 18;
	private static final int ALOAD_blockrenderlayer1 = 22;
	private static final int ALOAD_blockrendererdispatcher = 13;
	private static final int ALOAD_blockpos$mutableblockpos = 17;
	private static final int ALOAD_bufferbuilder = 24;
	private static final int ALOAD_array = 12;
	private static final int ALOAD_blockAccess_chunkCacheOF = 11;
	private static final int ALOAD_block = 19;

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
			if (instruction.getOpcode() == PUTSTATIC) {
				if (instruction.getType() == AbstractInsnNode.FIELD_INSN) {
					if (ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_CHUNKS_UPDATED.matches((FieldInsnNode) instruction)) {
						PUTSTATIC_renderChunksUpdated_Node = (FieldInsnNode) instruction;
						break;
					}
				}
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
			if (instructions.get(i).getType() != preExistingLabelNode.LABEL) {
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
		 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
		 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param chunkCacheOF        the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledChunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
		 *
		 * @return If vanilla rendering should be stopped
		 *
		 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // renderGlobal
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_GLOBAL.getName(), "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z)); // z
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkPreEvent", "(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false));
		tempInstructionList.add(new LabelNode(new Label()));
		tempInstructionList.add(new JumpInsnNode(IFEQ, preExistingLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

		instructions.insertBefore(preExistingLabelNode, tempInstructionList);

		return true;

	}

	/**
	 * find "Reflector.ForgeBlock_canRenderInLayer" "GETSTATIC net/optifine/reflect/Reflector.ForgeBlock_canRenderInLayer : Lnet/optifine/reflect/ReflectorMethod;"<br>
	 * make sure the instruction directly after it isn't "ReflectorMethod.exists()"<br>
	 * get the line number for the instruction (so we don't remove it)<br>
	 * get the ISTORE instruction afterwards (so we don't remove it)<br>
	 * remove all instructions between the line number and the ISTORE instruction<br>
	 * inject our hook BEFORE the ISTORE instruction<br>
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

			preExistingISTOREInstructionNode = (VarInsnNode) instructions.get(i);
			break;
		}

		if (preExistingISTOREInstructionNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return false;
		}

		//remove all instructions between the line number and the ISTORE instruction<br>
		ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
		for (int i = instructions.indexOf(preExistingLineNumberNode) + 1; i < instructions.indexOf(preExistingISTOREInstructionNode); ++i) {
			instructionsToRemove.add(instructions.get(i));
		}
		for (AbstractInsnNode instructionToRemove : instructionsToRemove) {
			//			MOD_LOGGER.warn("removing instrcution :"+insnToString(instructionToRemove));
			instructions.remove(instructionToRemove);
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		  @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
		 * @param chunkCacheOF              the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledChunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition       the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param blockPos                  the {@link BlockPos.MutableBlockPos position} of the block being assessed
		 * @param block                     the {@link Block block} being assessed
		 * @param blockState                the {@link IBlockState state} of the block being assessed
		 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
		 *
		 * @return If the block can render in the layer
		 *
		 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "canBlockRenderInLayer",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/chunk/VisGraph;Lnet/optifine/BlockPosM;Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z",
				false));

		// Inject our instructions right BEFORE the ISTORE instruction
		instructions.insertBefore(preExistingISTOREInstructionNode, tempInstructionList);

		return true;

	}

	/**
	 * find "blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);"<br>
	 * "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z"<br>
	 * get return label<br>
	 * get line number for nice debug<br>
	 * inject before<br>
	 *
	 * @param instructions the instructions for the method
	 * @return if the injection was successful
	 */
	@Override
	public boolean injectRebuildChunkBlockEventHook(InsnList instructions) {
		MethodInsnNode INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = null;

		// Find the bytecode instruction for "BlockRendererDispatcher.renderBlock" ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (instruction.getOpcode() == INVOKEVIRTUAL) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (ObfuscationHelper.ObfuscationMethod.BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK.matches((MethodInsnNode) instruction)) {
						INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = (MethodInsnNode) instruction;
						break;
					}
				}
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
		 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
		 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param chunkCacheOF                    the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
		 * @param blockState                      the {@link IBlockState state} of the block being rendered
		 * @param blockPos                        the {@link BlockPos.MutableBlockPos position} of the block being rendered
		 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
		 * @param renderChunkPosition             the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
		 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
		 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
		 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
		 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 *
		 * @return If the block should NOT be rebuilt to the chunk by vanilla
		 *
		 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // renderGlobal
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_GLOBAL.getName(), "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_bufferbuilder)); // bufferbuilder
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_array)); // array | aboolean
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkBlockEvent",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/optifine/BlockPosM;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;[ZLnet/minecraft/util/BlockRenderLayer;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Z",
				false));
		tempInstructionList.add(new JumpInsnNode(IFNE, returnLabel));

		// Inject our instructions right BEFORE first VarsInsNode
		instructions.insertBefore(preExistingVarInsNode, tempInstructionList);

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
			if (instruction.getOpcode() == GETSTATIC) {
				if (instruction.getType() == AbstractInsnNode.FIELD_INSN) {
					if (ObfuscationHelper.ObfuscationField.ENUM_BLOCK_RENDER_TYPE_INVISIBLE.matches((FieldInsnNode) instruction)) {
						GETSTATIC_EnumBlockRenderType_INVISIBLE_Node = (FieldInsnNode) instruction;
						break;
					}
				}
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

		//remove all instructions between the line number and the IF_ACMPEQ instruction<br>
		ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
		for (int i = instructions.indexOf(preExistingLineNumberNode) + 1; i < instructions.indexOf(preExistingIF_ACMPEQNode); ++i) {
			instructionsToRemove.add(instructions.get(i));
		}
		for (AbstractInsnNode instructionToRemove : instructionsToRemove) {
			//			MOD_LOGGER.warn("removing instruction :" + insnToString(instructionToRemove));
			instructions.remove(instructionToRemove);
		}

		final InsnList tempInstructionList = new InsnList();

		/*
		 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
		 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
		 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition       the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
		 * @param blockPos                  the {@link BlockPos.MutableBlockPos position} of the block being assessed
		 * @param block                     the {@link Block block} being assessed
		 * @param blockState                the {@link IBlockState state} of the block being assessed
		 *
		 * @return if the block should be rendered
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // RenderChunk - renderChunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockRendererDispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // MutableBlockPos - position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // visGraph
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockPos
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate

		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "canBlockRenderInType",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/chunk/VisGraph;Lnet/optifine/BlockPosM;Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;)Z", false));
		tempInstructionList.add(new JumpInsnNode(IFEQ, returnLabel));

		// Inject our instructions right BEFORE IF_ACMPEQ
		instructions.insertBefore(preExistingIF_ACMPEQNode, tempInstructionList);

		instructions.remove(preExistingIF_ACMPEQNode);

		return true;

	}

	/**
	 * find last return statement in method<br>
	 * inject before<br>
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
		 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
		 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
		 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
		 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
		 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
		 * @param compiledchunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
		 * @param renderChunkPosition the {@link BlockPos.MutableBlockPos position} passed in from RenderChunk#rebuildChunk
		 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
		 * @param chunkCacheOF        the {@link ChunkCacheOF} passed in from RenderChunk#rebuildChunk
		 * @param visGraph            the {@link VisGraph} passed in
		 * @param setTileEntities     the {@link Set} of {@link TileEntity TileEntities} with global renderers passed in
		 * @param lockCompileTask     the {@link ReentrantLock} for the compile task passed in
		 */
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // renderGlobal
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_RENDER_GLOBAL.getName(), "Lnet/minecraft/client/renderer/RenderGlobal;"));

		//this.makeChunkCacheOF(position);  // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_POSITION.getName(), "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new MethodInsnNode(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "makeChunkCacheOF", "(Lnet/minecraft/util/math/BlockPos;)Lnet/optifine/override/ChunkCacheOF;", false));

		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // setTileEntities
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_SET_TILE_ENTITIES.getName(), "Ljava/util/Set;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // lockCompileTask
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", ObfuscationHelper.ObfuscationField.RENDER_CHUNK_LOCK_COMPILE_TASK.getName(), "Ljava/util/concurrent/locks/ReentrantLock;"));
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkPostEvent",
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/Set;Ljava/util/concurrent/locks/ReentrantLock;)V", false));

		// Inject our instructions right BEFORE the RETURN instruction
		instructions.insertBefore(RETURN_Node, tempInstructionList);

		return true;

	}

}
