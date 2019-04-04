function initializeCoreMod() {
	return {
		'pre': {
			'target': {
				'type': 'CLASS',
				'name': 'net.minecraft.client.renderer.chunk.RenderChunk'
			},
			'transformer': function(classNode) {
				print("Class RenderChunk: ", classNode.name);

				var methods = classNode.methods;

				for (var i in methods) {
					var method = methods[i];
					var methodName = method.name;

					var deobfNameEquals = "rebuildChunk".equals(methodName);
					var mcpNameEquals = "func_178581_b".equals(methodName);

					if (!deobfNameEquals && !mcpNameEquals) {
						print("Did not match method " + methodName);
						continue;
					}

					print("Matched method " + methodName);

					print(deobfNameEquals ? "Matched a deobfuscated name - we are in a DEOBFUSCATED/MCP-NAMED DEVELOPER Environment" : "Matched an SRG name - We are in an SRG-NAMED PRODUCTION Environment")

					print("Injecting hooks...");
					injectHooks(method.instructions);
					print("Successfully injected hooks!");
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

var ALOAD = Opcodes.ALOAD;
var FLOAD = Opcodes.FLOAD;
var RETURN = Opcodes.RETURN;
var INVOKESTATIC = Opcodes.INVOKESTATIC;
var IFNE = Opcodes.IFNE;
var IFEQ = Opcodes.IFEQ;

var ALOAD_this = 0;
var FLOAD_x = 1;
var FLOAD_y = 2;
var FLOAD_z = 3;
var ALOAD_generator = 4;

// Finds the first instruction (NEW net/minecraft/client/renderer/chunk/CompiledChunk)
// and inserts before it.
function injectHooks(instructions) {

//	L10
//	LINENUMBER 110 L10
//	NEW net/minecraft/client/renderer/chunk/CompiledChunk
//	DUP
//	INVOKESPECIAL net/minecraft/client/renderer/chunk/CompiledChunk.<init> ()V
//	ASTORE 5

//	L10
//	ALOAD 0
//	ALOAD 1
//	FLOAD 2
//	FLOAD 3
//	FLOAD 4
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.pre (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)Z
//	IFEQ L11
//	L12
//	RETURN
//	L11
//	Original code

	var NEW_CompiledChunk;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == Opcodes.NEW) {
			NEW_CompiledChunk = instruction;
			print("Found injection point " + instruction);
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
			print("Found label " + instruction);
			break;
		}
	}
	if (!NEW_CompiledChunk_Label) {
		throw "Error: Couldn't find label!";
	}

	var originalInstructionsLabel = new LabelNode();

	instructions.insert(NEW_CompiledChunk_Label, new VarInsnNode(ALOAD, ALOAD_this)); // this
	print("Injected instruction ALOAD this");
	instructions.insert(NEW_CompiledChunk_Label, new VarInsnNode(FLOAD, FLOAD_x)); // x
	print("Injected instruction FLOAD x");
	instructions.insert(NEW_CompiledChunk_Label, new VarInsnNode(FLOAD, FLOAD_y)); // y
	print("Injected instruction FLOAD y");
	instructions.insert(NEW_CompiledChunk_Label, new VarInsnNode(FLOAD, FLOAD_z)); // z
	print("Injected instruction FLOAD z");
	instructions.insert(NEW_CompiledChunk_Label, new VarInsnNode(ALOAD, ALOAD_generator)); // generator
	print("Injected instruction ALOAD generator");
	instructions.insert(NEW_CompiledChunk_Label,
		new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"pre",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V",
			//boolean isInterface
			false
		)
	);
	print("Injected instruction INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/OverwriteHookTemp rebuildChunk (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V false");

	instructions.insert(NEW_CompiledChunk_Label, new JumpInsnNode(IFEQ, originalInstructionsLabel));
	print("Injected instruction IFEQ originalInstructionsLabel");

	instructions.insert(NEW_CompiledChunk_Label, new InsnNode(RETURN));
	print("Injected instruction RETURN");

	instructions.insert(NEW_CompiledChunk_Label, originalInstructionsLabel);
	print("Injected instruction originalInstructionsLabel");

	print("Successfully inserted instructions!");

}
