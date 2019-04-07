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

					print("Injecting hook...");
					injectHooks(method.instructions);
					print("Successfully injected hook!");
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

// Finds the first instruction GETFIELD world
// then finds the next label
// and inserts after the label and before the label's instructions.
function injectHooks(instructions) {

//	World world = this.world;
//	// START HOOK
//	final io.github.cadiboo.renderchunkrebuildchunkhooks.util.WorldReference worldRef = new io.github.cadiboo.renderchunkrebuildchunkhooks.util.WorldReference(world);
//	if (io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.checkWorld(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, worldRef)) {
//		return;
//	}
//	world = worldRef.get();
//	// END HOOK
//	if (world != null) {

//	L14
//	LINENUMBER 114 L14
//	ALOAD 0
//	GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.world : Lnet/minecraft/world/World;
//	ASTORE 9
//	L15
//	LINENUMBER 115 L15
//	ALOAD 9
//	IFNULL L16

//	L16
//	LINENUMBER 122 L16
//	ALOAD 0
//	GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.world : Lnet/minecraft/world/World;
//	ASTORE 9
//	L17
//	LINENUMBER 124 L17
//	NEW io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference
//	DUP
//	ALOAD 9
//	INVOKESPECIAL io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference.<init> (Lnet/minecraft/world/World;)V
//	ASTORE 10
//	L18
//	LINENUMBER 125 L18
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
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.checkWorld (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lio/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference;)Z
//	IFEQ L19
//	L20
//	LINENUMBER 126 L20
//	RETURN
//	L19
//	LINENUMBER 128 L19
//	FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkRenderTask net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/world/World io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference] []
//	ALOAD 10
//	INVOKEVIRTUAL io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference.get ()Lnet/minecraft/world/World;
//	ASTORE 9
//	L21
//	LINENUMBER 130 L21
//	ALOAD 9
//	IFNULL L22
//	Original code

	var first_GETFIELD_world;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == GETFIELD) {
			if (instruction.owner == "net/minecraft/client/renderer/chunk/RenderChunk") {
				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
				if (instruction.name == "field_178588_d" || instruction.name == "world") {
					if (instruction.desc == "Lnet/minecraft/world/World;") {
						first_GETFIELD_world = instruction;
						print("Found injection point " + instruction);
						break;
					}
				}
			}
		}
	}
	if (!first_GETFIELD_world) {
		throw "Error: Couldn't find injection point!";
	}

	var firstLabelAfter_first_GETFIELD_world;
	for (i = instructions.indexOf(first_GETFIELD_world); i < instructions.size(); ++i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			firstLabelAfter_first_GETFIELD_world = instruction;
			print("Found label " + instruction);
			break;
		}
	}
	if (!firstLabelAfter_first_GETFIELD_world) {
		throw "Error: Couldn't find label!";
	}

	//FFS why
	var toInject = ASMAPI.getMethodNode().instructions;

	// Labels n stuff
	var originalInstructionsLabel = new LabelNode();
	var ASTACK_worldRef = getMaxStack(instructions) + 1;
	var worldEqualsWorldRefLabel = new LabelNode();

	// Make list of instructions to inject
	toInject.add(new TypeInsnNode(NEW, "io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference"));
    toInject.add(new InsnNode(DUP));
    toInject.add(new VarInsnNode(ALOAD, ASTACK_world));
    toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESPECIAL,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference",
			//String name
			"<init>",
			//String descriptor
			"(Lnet/minecraft/world/World;)V",
			//boolean isInterface
			false
	));
    toInject.add(new VarInsnNode(ASTORE, ASTACK_worldRef));

    toInject.add(new LabelNode());
    toInject.add(new VarInsnNode(ALOAD, ASTACK_this));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_x));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_y));
	toInject.add(new VarInsnNode(FLOAD, FSTACK_z));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_generator));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_world));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_worldRef));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"checkWorld",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lio/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference;)Z",
			//boolean isInterface
			false
	));
	toInject.add(new JumpInsnNode(IFEQ, worldEqualsWorldRefLabel));

	toInject.add(new LabelNode());
	toInject.add(new InsnNode(RETURN));

	toInject.add(worldEqualsWorldRefLabel);
	toInject.add(new VarInsnNode(ALOAD, ASTACK_worldRef));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKEVIRTUAL,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference",
			//String name
			"get",
			//String descriptor
			"()Lnet/minecraft/world/World;",
			//boolean isInterface
			false
	));
	toInject.add(new VarInsnNode(ASTORE, ASTACK_world));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(firstLabelAfter_first_GETFIELD_world, toInject);

	print("Successfully inserted instructions!");

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
