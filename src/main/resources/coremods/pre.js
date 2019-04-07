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

//stack var indices
var ASTACK_this = 0;
var FSTACK_x = 1;
var FSTACK_y = 2;
var FSTACK_z = 3;
var ASTACK_generator = 4;

// Finds the first instruction (NEW net/minecraft/client/renderer/chunk/CompiledChunk)
// then finds the previous label
// and inserts after the label and before the label's instructions.
function injectHooks(instructions) {

//public void rebuildChunk(float x, float y, float z, ChunkRenderTask generator) {
//	// START HOOK
//	if (io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.pre(this, x, y, z, generator)) {
//		return;
//	}
//	// END HOOK
//	CompiledChunk compiledchunk = new CompiledChunk();

//	L10
//	LINENUMBER 110 L10
//	NEW net/minecraft/client/renderer/chunk/CompiledChunk
//	DUP
//	INVOKESPECIAL net/minecraft/client/renderer/chunk/CompiledChunk.<init> ()V
//	ASTORE 5

//	L10
//	LINENUMBER 114 L10
//	ALOAD 0
//	FLOAD 1
//	FLOAD 2
//	FLOAD 3
//	ALOAD 4
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.pre (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)Z
//	IFEQ L11
//	L12
//	LINENUMBER 115 L12
//	RETURN
//	L11
//	LINENUMBER 118 L11
//	FRAME SAME
//	NEW net/minecraft/client/renderer/chunk/CompiledChunk
//	Original code

	var NEW_CompiledChunk;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == NEW) {
			NEW_CompiledChunk = instruction;
			log("Found injection point " + instruction);
			break;
		}
	}
	if (!NEW_CompiledChunk) {
		throw "Error: Couldn't find injection point!";
	}

	var NEW_CompiledChunk_Label;
	for (i = instructions.indexOf(NEW_CompiledChunk); i >= 0; i--) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			NEW_CompiledChunk_Label = instruction;
			log("Found label " + instruction);
			break;
		}
	}
	if (!NEW_CompiledChunk_Label) {
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
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"pre",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)Z",
			//boolean isInterface
			false
	));
	toInject.add(new JumpInsnNode(IFEQ, originalInstructionsLabel));

	toInject.add(new LabelNode());
	toInject.add(new InsnNode(RETURN));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(NEW_CompiledChunk_Label, toInject);

	log("Successfully inserted instructions!");

}


function log(msg) {
	print("[pre Transformer] " + msg);
}
