package cadiboo.renderchunkrebuildchunkhooks.core.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.BETTER_FOLIAGE;
import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.OPTIFINE;

public class InjectionHelper implements Opcodes, INames {

	public static AbstractInsnNode getCanRenderInBlockInjectionPoint(InsnList instructions) {

		if (OPTIFINE && BETTER_FOLIAGE) {
			return getCanRenderInBlockInjectionPointOptifineBetterFoliage(instructions);
		} else if (OPTIFINE) {
			return getCanRenderInBlockInjectionPointOptifine(instructions);
		} else if (BETTER_FOLIAGE) {
			return getCanRenderInBlockInjectionPointBetterFoliage(instructions);
		} else {
			return getCanRenderInBlockInjectionPointVanillaForge(instructions);
		}

//		// where: RenderChunk.rebuildChunk()
//		// what: invoke code to overrule result of Block.canRenderInLayer()
//		// why: allows us to render transparent quads for blocks which are only on the SOLID layer
//		transformMethod(Refs.rebuildChunk) {
//			if (isOptifinePresent) {
//				find(varinsn(ISTORE, 23))?.insertAfter {
//					log.info("[BetterFoliageLoader] Applying RenderChunk block layer override")
//					varinsn(ALOAD, 19)
//					varinsn(ALOAD, 18)
//					varinsn(ALOAD, 22)
//					invokeStatic(Refs.canRenderBlockInLayer)
//					varinsn(ISTORE, 23)
//				}
//			} else {
//				find(invokeRef(Refs.canRenderInLayer))?.replace {
//					log.info("[BetterFoliageLoader] Applying RenderChunk block layer override")
//					invokeStatic(Refs.canRenderBlockInLayer)
//				}
//			}
//		}

	}

	private static AbstractInsnNode getCanRenderInBlockInjectionPointVanillaForge(InsnList instructions) {

		MethodInsnNode INVOKEVIRTUAL_Block_canRenderInLayer_Node = null;

		// Find the bytecode instruction for "block.canRenderInLayer(iblockstate, blockrenderlayer);" ("INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {

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
		return INVOKEVIRTUAL_Block_canRenderInLayer_Node;
	}

	private static FieldInsnNode getCanRenderInBlockInjectionPointOptifine(InsnList instructions) {

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

		return GETSTATIC_Reflector_ForgeBlock_canRenderInLayer;
	}

	private static AbstractInsnNode getCanRenderInBlockInjectionPointBetterFoliage(InsnList instructions) {

		MethodInsnNode INVOKESTATIC_Hooks_canRenderBlockInLayer_Node = null;

		// Find the bytecode instruction for "block.canRenderInLayer(iblockstate, blockrenderlayer);" ("INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {

//			INVOKESTATIC net/optifine/reflect/Reflector.callBoolean (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
//			ISTORE 23
//			ALOAD 19 : block
//			ALOAD 18 : iblockstate
//			ALOAD 22 : blockrenderlayer1
//			INVOKESTATIC mods/betterfoliage/client/Hooks.canRenderBlockInLayer (Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z
//			ISTORE 23


			if (instruction.getOpcode() == INVOKESTATIC) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (((MethodInsnNode) instruction).name.equals("canRenderBlockInLayer")) {
						if (((MethodInsnNode) instruction).desc.equals("(Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")) {
							INVOKESTATIC_Hooks_canRenderBlockInLayer_Node = (MethodInsnNode) instruction;
							break;
						}
					}
				}
			}
		}
		return INVOKESTATIC_Hooks_canRenderBlockInLayer_Node;

	}

	private static AbstractInsnNode getCanRenderInBlockInjectionPointOptifineBetterFoliage(InsnList instructions) {

		MethodInsnNode INVOKESTATIC_Hooks_canRenderBlockInLayer_Node = null;

		// Find the bytecode instruction for "block.canRenderInLayer(iblockstate, blockrenderlayer);" ("INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")
		for (AbstractInsnNode instruction : instructions.toArray()) {

			//			INVOKESTATIC net/optifine/reflect/Reflector.callBoolean (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
			//			ISTORE 23
			//			ALOAD 19 : block
			//			ALOAD 18 : iblockstate
			//			ALOAD 22 : blockrenderlayer1
			//			INVOKESTATIC mods/betterfoliage/client/Hooks.canRenderBlockInLayer (Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z
			//			ISTORE 23


			if (instruction.getOpcode() == INVOKESTATIC) {
				if (instruction.getType() == AbstractInsnNode.METHOD_INSN) {
					if (((MethodInsnNode) instruction).name.equals("canRenderBlockInLayer")) {
						if (((MethodInsnNode) instruction).desc.equals("(Lnet/minecraft/block/Block;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")) {
							INVOKESTATIC_Hooks_canRenderBlockInLayer_Node = (MethodInsnNode) instruction;
							break;
						}
					}
				}
			}
		}
		return INVOKESTATIC_Hooks_canRenderBlockInLayer_Node;

	}

}
