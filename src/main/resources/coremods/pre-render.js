function initializeCoreMod() {
	return {
		'preRender': {
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
var ASTACK_lvt_11_1__VisGraph = 11;
var ASTACK_lvt_12_1__HashSetTileEntities = 12;

// Finds the first instruction Sets.newHashSet
// then inserts right after the next label (before said label's instructions)
function injectHooks(instructions) {

//	L24
//	LINENUMBER 131 L24
//	INVOKESTATIC com/google/common/collect/Sets.newHashSet ()Ljava/util/HashSet;
//	ASTORE 12
//	L25

	var first_newHashSet;
	for (var i = instructions.size() - 1; i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == INVOKESTATIC) {
			if (instruction.owner == "com/google/common/collect/Sets") {
				//Library method, no SRG name
				if (instruction.name == "newHashSet") {
					if (instruction.desc == "()Ljava/util/HashSet;") {
						if (instruction.itf == false) {
							first_newHashSet = instruction;
							log("Found injection point " + instruction);
							break;
						}
					}
				}
			}
		}
	}
	if (!first_newHashSet) {
		throw "Error: Couldn't find injection point!";
	}

	var firstLabelAfter_first_newHashSet;
	var lookStart = instructions.indexOf(first_newHashSet);
	var lookEnd = lookStart + 20;
	for (i = lookStart; i < lookEnd; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			firstLabelAfter_first_newHashSet = instruction;
			log("Found label " + instruction);
			break;
		}
	}
	if (!firstLabelAfter_first_newHashSet) {
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
	toInject.add(new VarInsnNode(ALOAD, ASTACK_lvt_11_1__VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_lvt_12_1__HashSetTileEntities));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"preRender",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;)Z",
			//boolean isInterface
			false
	));
	toInject.add(new JumpInsnNode(IFEQ, originalInstructionsLabel));

	toInject.add(new LabelNode());
	toInject.add(new InsnNode(RETURN));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(firstLabelAfter_first_newHashSet, toInject);

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
	print("[preRender Transformer] " + msg);
}
