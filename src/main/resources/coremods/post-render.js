function initializeCoreMod() {
	return {
		'postRender': {
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

// Finds the last instruction computeVisibility
// then inserts before it
function injectHooks(instructions) {

//	}
//	// START HOOK
//	io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.postRender(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_);
//	// END HOOK
//
//	compiledchunk.setVisibility(lvt_11_1_.computeVisibility());

//	L16
//	LINENUMBER 216 L16
//	FRAME CHOP 3
//	RETURN

//	FRAME CHOP 3
//	INVOKESTATIC net/minecraft/client/renderer/BlockModelRenderer.disableCache ()V
//	L32
//	LINENUMBER 245 L32
//	FRAME CHOP 3
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
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.postRender (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;)V
//	L88
//	LINENUMBER 248 L88
//	ALOAD 5
//	ALOAD 12
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/VisGraph.computeVisibility ()Lnet/minecraft/client/renderer/chunk/SetVisibility;
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setVisibility (Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
//	L89

	var last_computeVisibility;
	for (var i = instructions.size() - 1; i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == INVOKEVIRTUAL) {
			if (instruction.owner == "net/minecraft/client/renderer/chunk/VisGraph") {
				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
				if (instruction.name == "func_178607_a" || instruction.name == "computeVisibility") {
					if (instruction.desc == "()Lnet/minecraft/client/renderer/chunk/SetVisibility;") {
						if (instruction.itf == false) {
							last_computeVisibility = instruction;
							log("Found injection point " + instruction);
							break;
						}
					}
				}
			}
		}
	}
	if (!last_computeVisibility) {
		throw "Error: Couldn't find injection point!";
	}

	var firstLabelBefore_last_computeVisibility;
	for (i = instructions.indexOf(last_computeVisibility); i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			firstLabelBefore_last_computeVisibility = instruction;
			log("Found label " + instruction);
			break;
		}
	}
	if (!firstLabelBefore_last_computeVisibility) {
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
			"postRender",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;)V",
			//boolean isInterface
			false
	));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insertBefore(last_computeVisibility, toInject);

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
	print("[postRender Transformer] " + msg);
}
