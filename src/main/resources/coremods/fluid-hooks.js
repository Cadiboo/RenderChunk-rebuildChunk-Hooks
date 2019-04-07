function initializeCoreMod() {
	return {
		'Fluid Hooks': {
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
var ASTACK_fluidstate = 20;
var ISTACK_j_blockrenderlayer1_ordinal = 25;
var ASTACK_bufferbuilder = 26;
//var ASTACK_ = 12;
//var ASTACK_ = 12;

// 1) find IFluidState.isEmpty
// 2) find label for IFluidState.isEmpty
// 3) find label that IFluidState.isEmpty would jump to
// 4) find ifluidstate.canRenderInLayer
// 5) find next label after ifluidstate.canRenderInLayer
// 6) delete everything (exclusive) between IFluidState.isEmpty label and the next label after ifluidstate.canRenderInLayer
// 7) insert canRender hook after IFluidState.isEmpty label
//
// 8) find renderFluid
// 9) find label for renderFluid
// 10) insert preRender hook after renderFluid label
function injectHooks(instructions) {

//	net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
//	if (!ifluidstate.isEmpty() && ifluidstate.canRenderInLayer(blockrenderlayer1)) {
//		int j = blockrenderlayer1.ordinal();
//		BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getBuilder(j);
//		if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
//			compiledchunk.setLayerStarted(blockrenderlayer1);
//			this.preRenderBlocks(bufferbuilder, blockpos);
//		}
//
//		aboolean[j] |= blockrendererdispatcher.renderFluid(blockpos$mutableblockpos, lvt_10_1_, bufferbuilder, ifluidstate);
//	}

//	net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
//	// HOOKED IF
//	fluidRender: if (io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.canFluidRender(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1)) {
//		int j = blockrenderlayer1.ordinal();
//		BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getBuilder(j);
//		if (!compiledchunk.isLayerStarted(blockrenderlayer1)) {
//			compiledchunk.setLayerStarted(blockrenderlayer1);
//			this.preRenderBlocks(bufferbuilder, blockpos);
//		}
//
//		// START HOOK
//		if (io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.preRenderFluid(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher, iblockstate, block, ifluidstate, blockrenderlayer1, j, bufferbuilder)) {
//			break fluidRender;
//		}
//		// END HOOK
//		aboolean[j] |= blockrendererdispatcher.renderFluid(blockpos$mutableblockpos, lvt_10_1_, bufferbuilder, ifluidstate);
//	}


//	L51
//	LINENUMBER 161 L51
//	ALOAD 24
//	INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer (Lnet/minecraft/util/BlockRenderLayer;)V
//	L52
//	LINENUMBER 162 L52
//	ALOAD 20
//	INVOKEINTERFACE net/minecraft/fluid/IFluidState.isEmpty ()Z (itf)
//	IFNE L53
//	ALOAD 20
//	ALOAD 24
//	INVOKEINTERFACE net/minecraft/fluid/IFluidState.canRenderInLayer (Lnet/minecraft/util/BlockRenderLayer;)Z (itf)
//	IFEQ L53
//	L54
//	LINENUMBER 163 L54
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal ()I
//	ISTORE 25
//	L55
//	LINENUMBER 164 L55
//	ALOAD 4
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkRenderTask.getRegionRenderCacheBuilder ()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
//	ILOAD 25
//	INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getBuilder (I)Lnet/minecraft/client/renderer/BufferBuilder;
//	ASTORE 26
//	L56
//	LINENUMBER 165 L56
//	ALOAD 5
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted (Lnet/minecraft/util/BlockRenderLayer;)Z
//	IFNE L57
//	L58
//	LINENUMBER 166 L58
//	ALOAD 5
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted (Lnet/minecraft/util/BlockRenderLayer;)V
//	L59
//	LINENUMBER 167 L59
//	ALOAD 0
//	ALOAD 26
//	ALOAD 7
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks (Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
//	L57
//	LINENUMBER 170 L57
//	FRAME APPEND [net/minecraft/util/BlockRenderLayer I net/minecraft/client/renderer/BufferBuilder]
//	ALOAD 13
//	ILOAD 25
//	DUP2
//	BALOAD
//	ALOAD 15
//	ALOAD 17
//	ALOAD 10
//	ALOAD 26
//	ALOAD 20
//	INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderFluid (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/fluid/IFluidState;)Z
//	IOR
//	BASTORE

//	L55
//	LINENUMBER 191 L55
//	ALOAD 24
//	INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer (Lnet/minecraft/util/BlockRenderLayer;)V
//	L56
//	LINENUMBER 193 L56
//	ALOAD 0
//	FLOAD 1
//	FLOAD 2
//	FLOAD 3
//	ALOAD 4
//	ALOAD 5
//	ALOAD 7
//	ALOAD 8
//	ALOAD 9
//	ALOAD 10
//	ALOAD 11
//	ALOAD 12
//	ALOAD 13
//	ALOAD 14
//	ALOAD 15
//	ALOAD 18
//	ALOAD 19
//	ALOAD 20
//	ALOAD 24
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.canFluidRender (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;)Z
//	IFEQ L57
//	L58
//	LINENUMBER 194 L58
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal ()I
//	ISTORE 25
//	L59
//	LINENUMBER 195 L59
//	ALOAD 4
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkRenderTask.getRegionRenderCacheBuilder ()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
//	ILOAD 25
//	INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getBuilder (I)Lnet/minecraft/client/renderer/BufferBuilder;
//	ASTORE 26
//	L60
//	LINENUMBER 196 L60
//	ALOAD 5
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted (Lnet/minecraft/util/BlockRenderLayer;)Z
//	IFNE L61
//	L62
//	LINENUMBER 197 L62
//	ALOAD 5
//	ALOAD 24
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted (Lnet/minecraft/util/BlockRenderLayer;)V
//	L63
//	LINENUMBER 198 L63
//	ALOAD 0
//	ALOAD 26
//	ALOAD 7
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks (Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
//	L61
//	LINENUMBER 202 L61
//	FRAME APPEND [net/minecraft/util/BlockRenderLayer I net/minecraft/client/renderer/BufferBuilder]
//	ALOAD 0
//	FLOAD 1
//	FLOAD 2
//	FLOAD 3
//	ALOAD 4
//	ALOAD 5
//	ALOAD 7
//	ALOAD 8
//	ALOAD 9
//	ALOAD 10
//	ALOAD 11
//	ALOAD 12
//	ALOAD 13
//	ALOAD 14
//	ALOAD 15
//	ALOAD 18
//	ALOAD 19
//	ALOAD 20
//	ALOAD 24
//	ILOAD 25
//	ALOAD 26
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.preRenderFluid (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;ILnet/minecraft/client/renderer/BufferBuilder;)Z
//	IFEQ L64
//	L65
//	LINENUMBER 203 L65
//	GOTO L57
//	L64
//	LINENUMBER 206 L64
//	FRAME SAME
//	ALOAD 13
//	ILOAD 25
//	DUP2
//	BALOAD
//	ALOAD 15
//	ALOAD 17
//	ALOAD 10
//	ALOAD 26
//	ALOAD 20
//	INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderFluid (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/fluid/IFluidState;)Z
//	IOR
//	BASTORE
//	Original code

	var fluidCannotRenderLabel;
	{
		var IFluidState_isEmpty;
		var arrayLength = instructions.size();
		for (var i = 0; i < arrayLength; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == INVOKEINTERFACE) {
				if (instruction.owner == "net/minecraft/fluid/IFluidState") {
					//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
					if (instruction.name == "func_206888_e" || instruction.name == "isEmpty") {
						if (instruction.desc == "()Z") {
							if (instruction.itf == true) {
								IFluidState_isEmpty = instruction;
								log("Found injection point " + instruction);
								break;
							}
						}
					}
				}
			}
		}
		if (!IFluidState_isEmpty) {
			throw "Error: Couldn't find injection point!";
		}

		var firstLabelBefore_IFluidState_isEmpty;
		for (i = instructions.indexOf(IFluidState_isEmpty); i >=0; --i) {
			var instruction = instructions.get(i);
			if (instruction.getType() == AbstractInsnNode.LABEL) {
				firstLabelBefore_IFluidState_isEmpty = instruction;
				log("Found label " + instruction);
				break;
			}
		}
		if (!firstLabelBefore_IFluidState_isEmpty) {
			throw "Error: Couldn't find label!";
		}

		var lookStart = instructions.indexOf(IFluidState_isEmpty);
		var lookMax = lookStart + 10;
		for (var i = lookStart; i < lookMax; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == IFEQ || instruction.getOpcode() == IFNE) {
				fluidCannotRenderLabel = instruction.label;
				log("Found fluidCannotRenderLabel " + instruction.label);
				break;
			}
		}
		if (!fluidCannotRenderLabel) {
			throw "Error: Couldn't find fluidCannotRenderLabel!";
		}

		var IFluidState_canRenderInLayer;
	//	var arrayLength = instructions.size();
		for (var i = 0; i < arrayLength; ++i) {
			var instruction = instructions.get(i);
			if (instruction.getOpcode() == INVOKEINTERFACE) {
				if (instruction.owner == "net/minecraft/fluid/IFluidState") {
					//forge added method
					if (instruction.name == "canRenderInLayer") {
						if (instruction.desc == "(Lnet/minecraft/util/BlockRenderLayer;)Z") {
							if (instruction.itf == true) {
								IFluidState_canRenderInLayer = instruction;
								log("Found injection point " + instruction);
								break;
							}
						}
					}
				}
			}
		}
		if (!IFluidState_canRenderInLayer) {
			throw "Error: Couldn't find injection point!";
		}

		var firstLabelAfter_IFluidState_canRenderInLayer;
		for (i = instructions.indexOf(IFluidState_canRenderInLayer); i >=0; --i) {
			var instruction = instructions.get(i);
			if (instruction.getType() == AbstractInsnNode.LABEL) {
				firstLabelAfter_IFluidState_canRenderInLayer = instruction;
				log("Found label " + instruction);
				break;
			}
		}
		if (!firstLabelAfter_IFluidState_canRenderInLayer) {
			throw "Error: Couldn't find label!";
		}

		removeBetweenInclusive(instructions, firstLabelBefore_IFluidState_isEmpty.getNext(), firstLabelAfter_IFluidState_canRenderInLayer.getPrevious())

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
				"canFluidRender",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFEQ, fluidCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_IFluidState_isEmpty, toInject);
	}

	// 8) find renderFluid
    // 9) find label for renderFluid
    // 10) insert preRender hook after renderFluid label
	{
		var INVOKEVIRTUAL_renderFluid;
    	var arrayLength = instructions.size();
    	for (var i = 0; i < arrayLength; ++i) {
    		var instruction = instructions.get(i);
    		if (instruction.getOpcode() == INVOKEVIRTUAL) {
    			if (instruction.owner == "net/minecraft/client/renderer/BlockRendererDispatcher") {
    				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
    				if (instruction.name == "func_205318_a" || instruction.name == "renderFluid") {
    					if (instruction.desc == "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IWorldReader;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/fluid/IFluidState;)Z") {
    						if (instruction.itf == false) {
    							INVOKEVIRTUAL_renderFluid = instruction;
    							log("Found injection point " + instruction);
    							break;
    						}
    					}
    				}
    			}
    		}
    	}
    	if (!INVOKEVIRTUAL_renderFluid) {
    		throw "Error: Couldn't find injection point!";
    	}

    	var firstLabelBefore_INVOKEVIRTUAL_renderFluid;
    	for (i = instructions.indexOf(INVOKEVIRTUAL_renderFluid); i >=0; --i) {
    		var instruction = instructions.get(i);
    		if (instruction.getType() == AbstractInsnNode.LABEL) {
    			firstLabelBefore_INVOKEVIRTUAL_renderFluid = instruction;
    			log("Found label " + instruction);
    			break;
    		}
    	}
    	if (!firstLabelBefore_INVOKEVIRTUAL_renderFluid) {
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
				"preRenderFluid",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;ILnet/minecraft/client/renderer/BufferBuilder;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFNE, fluidCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_INVOKEVIRTUAL_renderFluid, toInject);


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
	print("[Fluid Hooks Transformer] " + msg);
}

function removeBetweenInclusive(instructions, startInstruction, endInstruction) {
	var start = instructions.indexOf(startInstruction);
	var end = instructions.indexOf(endInstruction);
	for (var i = start; i < end; ++i) {
		instructions.remove(instructions.get(start));
	}
}
