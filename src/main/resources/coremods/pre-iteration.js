function initializeCoreMod() {
	return {
		'pre': {
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
var ASTACK_lvt_11_1_VisGraph = 11;
var ASTACK_lvt_12_1__HashSetTileEntities = 12;
var ASTACK_aboolean_usedRenderLayers = 13;
var ASTACK_ = 12;
var ASTACK_random = 14;
var ASTACK_blockrendererdispatcher = 15;

// Finds the first instruction INVOKESTATIC BlockPos.getAllInBoxMutable
// then finds the previous label
// and inserts after the label and before the label's instructions.
function injectHooks(instructions) {

//	BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
//
//	// START HOOK
//	if (io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.preIteration(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher)) {
//		return;
//	}
//	// END HOOK
//	for(BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos1)) {


//	L31
//	LINENUMBER 137 L31
//	INVOKESTATIC net/minecraft/client/Minecraft.getInstance ()Lnet/minecraft/client/Minecraft;
//	INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher ()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//	ASTORE 15
//	L32
//	LINENUMBER 139 L32
//	ALOAD 7
//	ALOAD 8
//	INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
//	INVOKEINTERFACE java/lang/Iterable.iterator ()Ljava/util/Iterator; (itf)
//	ASTORE 16
//	L33

//	L41
//	LINENUMBER 162 L41
//	INVOKESTATIC net/minecraft/client/Minecraft.getInstance ()Lnet/minecraft/client/Minecraft;
//	INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher ()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//	ASTORE 17
//	L42
//	LINENUMBER 165 L42
//	ALOAD 0
//	FLOAD 1
//	FLOAD 2
//	FLOAD 3
//	ALOAD 4
//	ALOAD 5
//	ALOAD 7
//	ALOAD 8
//	ALOAD 9
//	ALOAD 11
//	ALOAD 12
//	ALOAD 13
//	ALOAD 15
//	ALOAD 16
//	ALOAD 17
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.preIteration (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;)Z
//	IFEQ L43
//	L44
//	LINENUMBER 166 L44
//	RETURN
//	L43
//	LINENUMBER 169 L43
//	FRAME APPEND [[Z java/util/Random net/minecraft/client/renderer/BlockRendererDispatcher]
//	ALOAD 7
//	ALOAD 8
//	INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
//	INVOKEINTERFACE java/lang/Iterable.iterator ()Ljava/util/Iterator; (itf)
//	ASTORE 18
//	Original code

	var first_INVOKESTATIC_getAllInBoxMutable;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == INVOKESTATIC) {
			if (instruction.owner == "net/minecraft/util/math/BlockPos") {
				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
				if (instruction.name == "func_177975_b" || instruction.name == "getAllInBoxMutable") {
					if (instruction.desc == "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;") {
						if (instruction.itf == false) {
							first_INVOKESTATIC_getAllInBoxMutable = instruction;
							log("Found injection point " + instruction);
							break;
						}
					}
				}
			}
		}
	}
	if (!first_INVOKESTATIC_getAllInBoxMutable) {
		throw "Error: Couldn't find injection point!";
	}

	var firstLabelBefore_first_INVOKESTATIC_getAllInBoxMutable;
	for (i = instructions.indexOf(first_INVOKESTATIC_getAllInBoxMutable); i <=; 0 --i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			firstLabelBefore_first_INVOKESTATIC_getAllInBoxMutable = instruction;
			log("Found label " + instruction);
			break;
		}
	}
	if (!firstLabelBefore_first_INVOKESTATIC_getAllInBoxMutable) {
		throw "Error: Couldn't find label!";
	}

	//FFS why
	var toInject = ASMAPI.getMethodNode().instructions;

	// Labels n stuff
	var originalInstructionsLabel = new LabelNode();

	// Make list of instructions to inject
	toInject.add(new VarInsnNode(ALOAD, ASTACK_this));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_x));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_y));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_z));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_generator));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_world));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_lvt_10_1__ChunkCache));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_lvt_11_1_VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_lvt_12_1__HashSetTileEntities));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_aboolean_usedRenderLayers));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_random));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockrendererdispatcher));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"preIteration",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;)Z",
			//boolean isInterface
			false
	));
	toInject.add(new JumpInsnNode(IFEQ, originalInstructionsLabel));

	toInject.add(new LabelNode());
	toInject.add(new InsnNode(RETURN));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(firstLabelBefore_first_INVOKESTATIC_getAllInBoxMutable, toInject);

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
	print("[preIteration Transformer] " + msg);
}
