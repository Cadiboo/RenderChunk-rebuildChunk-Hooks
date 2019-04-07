function initializeCoreMod() {
	return {
		'Block Hooks': {
			'target': {
				'type': 'CLASS',
				'name': 'net.minecraft.client.renderer.chunk.RenderChunk'
			},
			'transformer': function(classNode) {
				log("Class RenderChunk: ", classNode.name);

				var methods = classNode.methods;

				for (var i in methods) {
					var method = methods[i];
					var methodName = method.name;

					var deobfNameEquals = "rebuildChunk".equals(methodName);
					var mcpNameEquals = "func_178581_b".equals(methodName);

					if (!deobfNameEquals && !mcpNameEquals) {
						log("Did not match method " + methodName);
						continue;
					}

					log("Matched method " + methodName);

					log(deobfNameEquals ? "Matched a deobfuscated name - we are in a DEOBFUSCATED/MCP-NAMED DEVELOPER Environment" : "Matched an SRG name - We are in an SRG-NAMED PRODUCTION Environment")

					log("Injecting hook...");
					injectHooks(method.instructions);
					log("Successfully injected hook!");
					break;

				}

				return classNode;
			}
		}
	}
}

var/*Class/Interface*/ Opcodes = Java.type('org.objectweb.asm.Opcodes');
var/*Class*/ MethodNode = Java.type('org.objectweb.asm.tree.MethodNode');
var/*Class*/ MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
var/*Class*/ InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var/*Class*/ VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var/*Class*/ AbstractInsnNode = Java.type('org.objectweb.asm.tree.AbstractInsnNode');
var/*Class*/ JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
var/*Class*/ LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var/*Class*/ TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');
//var/*Class*/ InsnList = Java.type('org.objectweb.asm.tree.InsnList');

var/*Class*/ ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

//opcodes
var ALOAD = Opcodes.ALOAD;
var FLOAD = Opcodes.FLOAD;
var ILOAD = Opcodes.ILOAD;
var RETURN = Opcodes.RETURN;
var INVOKESTATIC = Opcodes.INVOKESTATIC;
var IFNE = Opcodes.IFNE;
var IFEQ = Opcodes.IFEQ;
var NEW = Opcodes.NEW;
var GETFIELD = Opcodes.GETFIELD;
var ASTORE = Opcodes.ASTORE;
var DUP = Opcodes.DUP;
var INVOKESPECIAL = Opcodes.INVOKESPECIAL;
var INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
var INVOKEINTERFACE = Opcodes.INVOKEINTERFACE;

//stack var indices
var ASTACK_this = 0;
var FSTACK_x = 1;
var FSTACK_y = 2;
var FSTACK_z = 3;
var ASTACK_generator = 4;
var ASTACK_compiledchunk = 5;
var ASTACK_blockpos_startPos = 7;
var ASTACK_blockpos1_endPos = 8;
var ASTACK_world = 9;
var ASTACK_lvt_10_1__ChunkCache = 10;
var ASTACK_lvt_11_1__VisGraph = 11;
var ASTACK_lvt_12_1__HashSetTileEntities = 12;
var ASTACK_aboolean_usedRenderLayers = 13;
var ASTACK_random = 14;
var ASTACK_blockrendererdispatcher = 15;
var ASTACK_Blockstate = 20;
var ISTACK_j_blockrenderlayer1_ordinal = 25;
var ASTACK_bufferbuilder = 26;
//var ASTACK_ = 12;
//var ASTACK_ = 12;

// 1) find IBlockState.getRenderType
// 2) find label for IBlockState.getRenderType
// 3) find label that IBlockState.getRenderType would jump to
// 4) find IBlockState.canRenderInLayer
// 5) find next label after IBlockState.canRenderInLayer
// 6) delete everything (exclusive) between IBlockState.getRenderType label and the next label after IBlockState.canRenderInLayer
// 7) insert canRender hook after IBlockState.getRenderType label
//
// 8) find renderBlock
// 9) find label for renderBlock
// 10) insert preRender hook after renderBlock label
function injectHooks(instructions) {

// everything is copied from Block-hooks

	var blockCannotRenderLabel;
	{
		var IBlockState_getRenderType;
		var arrayLength = instructions.size();
		for (var i = 0; i < arrayLength; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == INVOKEINTERFACE) {
				if (instruction.owner == "net/minecraft/block/state/IBlockState") {
					//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
					if (instruction.name == "func_185901_i" || instruction.name == "getRenderType") {
						if (instruction.desc == "()Lnet/minecraft/util/EnumBlockRenderType;") {
							if (instruction.itf == true) {
								IBlockState_getRenderType = instruction;
								log("Found injection point " + instruction);
								break;
							}
						}
					}
				}
			}
		}
		if (!IBlockState_getRenderType) {
			throw "Error: Couldn't find injection point!";
		}

		var firstLabelBefore_IBlockState_getRenderType;
		for (i = instructions.indexOf(IBlockState_getRenderType); i >=0; --i) {
			var instruction = instructions.get(i);
			if (instruction.getType() == AbstractInsnNode.LABEL) {
				firstLabelBefore_IBlockState_getRenderType = instruction;
				log("Found label " + instruction);
				break;
			}
		}
		if (!firstLabelBefore_IBlockState_getRenderType) {
			throw "Error: Couldn't find label!";
		}

		var lookStart = instructions.indexOf(IBlockState_getRenderType);
		var lookMax = lookStart + 10;
		for (var i = lookStart; i < lookMax; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == IFEQ || instruction.getOpcode() == IFNE) {
				blockCannotRenderLabel = instruction.label;
				log("Found blockCannotRenderLabel " + instruction.label);
				break;
			}
		}
		if (!blockCannotRenderLabel) {
			throw "Error: Couldn't find blockCannotRenderLabel!";
		}

		var IBlockState_canRenderInLayer;
	//	var arrayLength = instructions.size();
		for (var i = 0; i < arrayLength; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == INVOKEINTERFACE) {
				if (instruction.owner == "net/minecraft/block/state/IBlockState") {
					//forge added method
					if (instruction.name == "canRenderInLayer") {
						if (instruction.desc == "(Lnet/minecraft/util/BlockRenderLayer;)Z") {
							if (instruction.itf == true) {
								IBlockState_canRenderInLayer = instruction;
								log("Found injection point " + instruction);
								break;
							}
						}
					}
				}
			}
		}
		if (!IBlockState_canRenderInLayer) {
			throw "Error: Couldn't find injection point!";
		}

		var firstLabelAfter_IBlockState_canRenderInLayer;
		for (i = instructions.indexOf(IBlockState_canRenderInLayer); i >=0; --i) {
			var instruction = instructions.get(i);
			if (instruction.getType() == AbstractInsnNode.LABEL) {
				firstLabelAfter_IBlockState_canRenderInLayer = instruction;
				log("Found label " + instruction);
				break;
			}
		}
		if (!firstLabelAfter_IBlockState_canRenderInLayer) {
			throw "Error: Couldn't find label!";
		}

		removeBetweenInclusive(instructions, firstLabelBefore_IBlockState_getRenderType.getNext(), firstLabelAfter_IBlockState_canRenderInLayer.getPrevious())

		//FFS why
		var toInject = ASMAPI.getMethodNode().instructions;

		// Labels n stuff
		var originalInstructionsLabel = new LabelNode();

		// Make list of instructions to inject
		toInject.add(new VarInsnNode(ALOAD, 0));
		toInject.add(new VarInsnNode(FLOAD, 1));
		toInject.add(new VarInsnNode(FLOAD, 2));
		toInject.add(new VarInsnNode(FLOAD, 3));
		toInject.add(new VarInsnNode(ALOAD, 4));
		toInject.add(new VarInsnNode(ALOAD, 5));
		toInject.add(new VarInsnNode(ALOAD, 7));
		toInject.add(new VarInsnNode(ALOAD, 8));
		toInject.add(new VarInsnNode(ALOAD, 9));
		toInject.add(new VarInsnNode(ALOAD, 10));
		toInject.add(new VarInsnNode(ALOAD, 11));
		toInject.add(new VarInsnNode(ALOAD, 12));
		toInject.add(new VarInsnNode(ALOAD, 13));
		toInject.add(new VarInsnNode(ALOAD, 14));
		toInject.add(new VarInsnNode(ALOAD, 15));
		toInject.add(new VarInsnNode(ALOAD, 18));
		toInject.add(new VarInsnNode(ALOAD, 19));
		toInject.add(new VarInsnNode(ALOAD, 20));
		toInject.add(new VarInsnNode(ALOAD, 24));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"canBlockRender",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFEQ, blockCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_IBlockState_getRenderType, toInject);
	}

	// 8) find renderBlock
    // 9) find label for renderBlock
    // 10) insert preRender hook after renderBlock label
	{
		var INVOKEVIRTUAL_renderBlock;
    	var arrayLength = instructions.size();
    	for (var i = 0; i < arrayLength; ++i) {
    		var instruction = instructions.get(i);
    		if (instruction.getOpcode() == INVOKEVIRTUAL) {
    			if (instruction.owner == "net/minecraft/client/renderer/BlockRendererDispatcher") {
    				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
    				if (instruction.name == "func_195475_a" || instruction.name == "renderBlock") {
    					if (instruction.desc == "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/client/renderer/BufferBuilder;Ljava/util/Random;)Z") {
    						if (instruction.itf == false) {
    							INVOKEVIRTUAL_renderBlock = instruction;
    							log("Found injection point " + instruction);
    							break;
    						}
    					}
    				}
    			}
    		}
    	}
    	if (!INVOKEVIRTUAL_renderBlock) {
    		throw "Error: Couldn't find injection point!";
    	}

    	var firstLabelBefore_INVOKEVIRTUAL_renderBlock;
    	for (i = instructions.indexOf(INVOKEVIRTUAL_renderBlock); i >=0; --i) {
    		var instruction = instructions.get(i);
    		if (instruction.getType() == AbstractInsnNode.LABEL) {
    			firstLabelBefore_INVOKEVIRTUAL_renderBlock = instruction;
    			log("Found label " + instruction);
    			break;
    		}
    	}
    	if (!firstLabelBefore_INVOKEVIRTUAL_renderBlock) {
    		throw "Error: Couldn't find label!";
    	}

        //FFS why
		var toInject = ASMAPI.getMethodNode().instructions;

		// Labels n stuff
		var originalInstructionsLabel = new LabelNode();

		// Make list of instructions to inject
		toInject.add(new VarInsnNode(ALOAD, 0));
		toInject.add(new VarInsnNode(FLOAD, 1));
		toInject.add(new VarInsnNode(FLOAD, 2));
		toInject.add(new VarInsnNode(FLOAD, 3));
		toInject.add(new VarInsnNode(ALOAD, 4));
		toInject.add(new VarInsnNode(ALOAD, 5));
		toInject.add(new VarInsnNode(ALOAD, 7));
		toInject.add(new VarInsnNode(ALOAD, 8));
		toInject.add(new VarInsnNode(ALOAD, 9));
		toInject.add(new VarInsnNode(ALOAD, 10));
		toInject.add(new VarInsnNode(ALOAD, 11));
		toInject.add(new VarInsnNode(ALOAD, 12));
		toInject.add(new VarInsnNode(ALOAD, 13));
		toInject.add(new VarInsnNode(ALOAD, 14));
		toInject.add(new VarInsnNode(ALOAD, 15));
		toInject.add(new VarInsnNode(ALOAD, 18));
		toInject.add(new VarInsnNode(ALOAD, 19));
		toInject.add(new VarInsnNode(ALOAD, 20));
		toInject.add(new VarInsnNode(ALOAD, 24));
		toInject.add(new VarInsnNode(ILOAD, 25));
		toInject.add(new VarInsnNode(ALOAD, 26));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"preRenderBlock",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;ILnet/minecraft/client/renderer/BufferBuilder;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFNE, blockCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_INVOKEVIRTUAL_renderBlock, toInject);


	}

	log("Successfully inserted instructions!");

}

function getMaxStack(instructions) {
	var maxStack = 0;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.VAR_INSN) {
			maxStack = max(maxStack, instruction.var);
		}
	}
	return maxStack;
}

function max(a, b) {
	return a > b ? a : b;
}

function log(msg) {
	print("[Block Hooks Transformer] " + msg);
}

function removeBetweenInclusive(instructions, startInstruction, endInstruction) {
	var start = instructions.indexOf(startInstruction);
	var end = instructions.indexOf(endInstruction);
	for (var i = start; i < end; ++i) {
		instructions.remove(instructions.get(start));
	}
}
