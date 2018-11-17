package cadiboo.renderchunkrebuildchunkhooks.core.classtransformer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.optifine.override.ChunkCacheOF;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine extends RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge {

	private static final int ALOAD_blockAccess_chunkCacheOF = 11;
	public static final  int ALOAD_iblockstate              = 18;
	public static final  int ALOAD_blockrenderlayer1        = 22;
	public static final  int ALOAD_blockrendererdispatcher  = 13;
	public static final  int ALOAD_blockpos$mutableblockpos = 17;
	public static final  int ALOAD_bufferbuilder            = 24;
	public static final  int ALOAD_array                    = 12;
	private static final int ALOAD_block                    = 19;

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
	 */
	@Override
	public void injectRebuildChunkPreEventHook(InsnList instructions) {

		FieldInsnNode PUTSTATIC_renderChunksUpdated_Node = null;

		// Find the bytecode instruction for "++renderChunksUpdated" ("PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I")
		for (AbstractInsnNode instruction : instructions.toArray()) {

			if (instruction.getOpcode() == PUTSTATIC) {
				if (instruction.getType() == AbstractInsnNode.FIELD_INSN) {
					final FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;
					if (fieldInsnNode.desc.equals(Type.INT_TYPE.getDescriptor())) {
						if (fieldInsnNode.name.equals(STATIC_FIELD_renderChunksUpdated)) {
							PUTSTATIC_renderChunksUpdated_Node = fieldInsnNode;
							break;
						}
					}
				}
			}
		}

		if (PUTSTATIC_renderChunksUpdated_Node == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
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
			return;
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
			return;
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
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		LabelNode ourLabel = new LabelNode(new Label());
		tempInstructionList.add(ourLabel);
		tempInstructionList.add(new LineNumberNode(preExistingLineNumberNode.line, ourLabel));
		/**
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
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z)); // z
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkPreEvent", "(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false));
		tempInstructionList.add(new LabelNode(new Label()));
		tempInstructionList.add(new JumpInsnNode(IFEQ, preExistingLabelNode));
		tempInstructionList.add(new InsnNode(RETURN));

		instructions.insertBefore(preExistingLabelNode, tempInstructionList);

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
	 */
	@Override
	public void injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions) {

		FieldInsnNode GETSTATIC_Reflector_ForgeBlock_canRenderInLayer = null;

		// Find the bytecode instruction for "block.canRenderInLayer(iblockstate, blockrenderlayer);" ("INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")
		find_GETSTATIC_Reflector_ForgeBlock_canRenderInLayer:
		for (AbstractInsnNode instruction : instructions.toArray()) {

			//NO! Not the right one
			//			LINENUMBER 237 L26
			//#			GETSTATIC net/optifine/reflect/Reflector.ForgeBlock_canRenderInLayer : Lnet/optifine/reflect/ReflectorMethod;
			//			INVOKEVIRTUAL net/optifine/reflect/ReflectorMethod.exists ()Z
			//			ISTORE 14

			//YES! this is the one
			//			LINENUMBER 299 L55
			//			ALOAD 19
			//#			GETSTATIC net/optifine/reflect/Reflector.ForgeBlock_canRenderInLayer : Lnet/optifine/reflect/ReflectorMethod;
			//			ICONST_2
			//			ANEWARRAY java/lang/Object

			if (instruction.getOpcode() != GETSTATIC) {
				continue;
			}

			if (instruction.getType() != AbstractInsnNode.FIELD_INSN) {
				continue;
			}

			final FieldInsnNode fieldInsnNode = (FieldInsnNode) instruction;

			if (! fieldInsnNode.owner.equals("net/optifine/reflect/Reflector")) {
				continue;
			}
			if (! fieldInsnNode.name.equals("ForgeBlock_canRenderInLayer")) {
				continue;
			}
			if (! fieldInsnNode.desc.equals("Lnet/optifine/reflect/ReflectorMethod;")) {
				continue;
			}

			//make sure the instruction directly after it isnt "ReflectorMethod.exists()"

			final int indexOfFieldInsnNode = instructions.indexOf(fieldInsnNode);

			for (int i = indexOfFieldInsnNode; i < indexOfFieldInsnNode + 10; i++) {

				final AbstractInsnNode instruction2 = instructions.get(i);

				if (instruction2.getOpcode() == INVOKEVIRTUAL) {
					if (instruction2.getType() == AbstractInsnNode.METHOD_INSN) {
						final MethodInsnNode methodInsnNode = (MethodInsnNode) instruction2;
						if (methodInsnNode.owner.equals("net/optifine/reflect/ReflectorMethod")) {
							if (methodInsnNode.name.equals("exists")) {
								if (methodInsnNode.desc.equals("()Z")) {
									continue find_GETSTATIC_Reflector_ForgeBlock_canRenderInLayer;
								}
							}
						}
					}
				}

			}

			//we made sure the instruction directly after it wasnt "ReflectorMethod.exists()"

			GETSTATIC_Reflector_ForgeBlock_canRenderInLayer = (FieldInsnNode) instruction;
			break find_GETSTATIC_Reflector_ForgeBlock_canRenderInLayer;

		}

		if (GETSTATIC_Reflector_ForgeBlock_canRenderInLayer == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		LOGGER.info(GETSTATIC_Reflector_ForgeBlock_canRenderInLayer);

		LineNumberNode preExistingLineNumberNode = null;

		// go back up the instructions until we find the line number for the instruction (so we don't remove it)
		for (int i = instructions.indexOf(GETSTATIC_Reflector_ForgeBlock_canRenderInLayer) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LINE) {
				continue;
			}
			preExistingLineNumberNode = (LineNumberNode) instructions.get(i);
			break;
		}

		if (preExistingLineNumberNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
		}

		VarInsnNode preExistingISTOREInstructionNode = null;

		// go back down the instructions until we find the ISTORE instruction (so we don't remove it)
		for (int i = instructions.indexOf(GETSTATIC_Reflector_ForgeBlock_canRenderInLayer); i < instructions.indexOf(GETSTATIC_Reflector_ForgeBlock_canRenderInLayer) + 20; i++) {
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
			return;
		}

		//remove all instructions between the line number and the ISTORE instruction<br>
		ArrayList<AbstractInsnNode> instructionsToRemove = new ArrayList<>();
		for (int i = instructions.indexOf(preExistingLineNumberNode) + 1; i < instructions.indexOf(preExistingISTOREInstructionNode); ++ i) {
			instructionsToRemove.add(instructions.get(i));
		}
		for (AbstractInsnNode instructionToRemove : instructionsToRemove) {
			//			LOGGER.warn("removing instrcution :"+insnToString(instructionToRemove));
			instructions.remove(instructionToRemove);
		}

		final InsnList tempInstructionList = new InsnList();

		/**
		 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
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
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_block)); // block
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "canBlockRenderInLayer",
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/chunk/VisGraph;Lnet/optifine/BlockPosM;Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z",
			false));

		// Inject our instructions right BEFORE the ISTORE instruction
		instructions.insertBefore(preExistingISTOREInstructionNode, tempInstructionList);

	}

	/**
	 * find "blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);"<br>
	 * "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z"<br>
	 * get return label<br>
	 * get line number for nice debug<br>
	 * inject before<br>
	 *
	 * @param instructions
	 */
	@Override
	public void injectRebuildChunkBlockEventHook(InsnList instructions) {

		MethodInsnNode INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node = null;

		// Find the bytecode instruction for "BlockRendererDispatcher.renderBlock" ("INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {

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
		for (int i = instructions.indexOf(INVOKEVIRTUAL_BlockRendererDispatcher_renderBlock_Node) - 1; i >= 0; i--) {
			if (instructions.get(i).getType() != AbstractInsnNode.LABEL) {
				continue;
			}
			preExistingLabelNode = (LabelNode) instructions.get(i);
			break;
		}

		if (preExistingLabelNode == null) {
			new RuntimeException("Couldn't find injection point!").printStackTrace();
			return;
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
			return;
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
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		/**
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
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockAccess_chunkCacheOF)); // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk)); // compiledchunk
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrendererdispatcher)); // blockrendererdispatcher
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_iblockstate)); // iblockstate
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockpos$mutableblockpos)); // blockpos$mutableblockpos (currentBlockPos) | BlockPosM
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_bufferbuilder)); // bufferbuilder
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_array)); // array | aboolean
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_blockrenderlayer1)); // blockrenderlayer1
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x)); // x
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y)); // y
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z)); // z
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_10_1_tileEntitiesWithGlobalRenderers)); // lvt_10_1_ (tileEntitiesWithGlobalRenderers)
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph)); // lvt_9_1_ (visGraph)
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkBlockEvent",
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/optifine/BlockPosM;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;[ZLnet/minecraft/util/BlockRenderLayer;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Z",
			false));
		tempInstructionList.add(new JumpInsnNode(IFNE, returnLabel));

		// Inject our instructions right BEFORE first VarsInsNode
		instructions.insertBefore(preExistingVarInsNode, tempInstructionList);

	}

	/**
	 * find last return statement in method<br>
	 * inject before<br>
	 *
	 * @param instructions the instructions for the method
	 */
	@Override
	public void injectRebuildChunkPostEventHook(InsnList instructions) {

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
			return;
		}

		final InsnList tempInstructionList = new InsnList();

		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_x));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_y));
		tempInstructionList.add(new VarInsnNode(FLOAD, ALOAD_z));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator));
		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_compiledchunk));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // renderGlobal
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;"));

		//this.makeChunkCacheOF(position);  // worldView | blockAccess | chunkCacheOF
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // this
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // position
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"));
		tempInstructionList.add(new MethodInsnNode(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "makeChunkCacheOF", "(Lnet/minecraft/util/math/BlockPos;)Lnet/optifine/override/ChunkCacheOF;", false));

		tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_lvt_9_1_visGraph));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // setTileEntities
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_setTileEntities, "Ljava/util/Set;"));
		tempInstructionList.add(new VarInsnNode(ALOAD, 0)); // lockCompileTask
		tempInstructionList.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_lockCompileTask, "Ljava/util/concurrent/locks/ReentrantLock;"));
		tempInstructionList.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooksOptifine", "onRebuildChunkPostEvent",
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/RenderGlobal;Lnet/optifine/override/ChunkCacheOF;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/Set;Ljava/util/concurrent/locks/ReentrantLock;)V", false));

		// Inject our instructions right BEFORE the RETURN instruction
		instructions.insertBefore(RETURN_Node, tempInstructionList);

	}

}
