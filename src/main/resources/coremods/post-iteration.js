function initializeCoreMod() {
	return {
		'postIteration': {
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
var ARRAYLENGTH = Opcodes.ARRAYLENGTH;

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
var ASTACK_ = 12;
var ASTACK_random = 14;
var ASTACK_blockrendererdispatcher = 15;

// Finds the first instruction INVOKESTATIC BlockPos.getAllInBoxMutable
// then finds the previous label
// and inserts after the label and before the label's instructions.
//1) find setLayerUsed
//2) find prev ARRAYLENGTH
//3) find prev label
//4) insert _after_ said label and _before_ normal insns
function injectHooks(instructions) {

//	// START HOOK
//	io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.postIteration(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_, aboolean, random, blockrendererdispatcher);
//	// END HOOK
//	for(BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
//		if (aboolean[blockrenderlayer.ordinal()]) {
//			compiledchunk.setLayerUsed(blockrenderlayer);
//		}

//	L58
//	LINENUMBER 226 L58
//	FRAME CHOP 3
//	ACONST_NULL
//	INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer (Lnet/minecraft/util/BlockRenderLayer;)V
//	L79
//	LINENUMBER 227 L79
//	GOTO L41
//	L42
//	LINENUMBER 229 L42
//	FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkRenderTask net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/world/World io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference net/minecraft/client/renderer/chunk/RenderChunkCache net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet [Z java/util/Random net/minecraft/client/renderer/BlockRendererDispatcher] []
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
//	ALOAD 14
//	ALOAD 15
//	ALOAD 16
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.postIteration (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;)V
//	L80
//	LINENUMBER 232 L80
//	INVOKESTATIC net/minecraft/util/BlockRenderLayer.values ()[Lnet/minecraft/util/BlockRenderLayer;
//	ASTORE 17
//	ALOAD 17
//	ARRAYLENGTH
//	ISTORE 18
//	ICONST_0
//	ISTORE 19
//	L81
//	FRAME APPEND [[Lnet/minecraft/util/BlockRenderLayer; I I]
//	ILOAD 19
//	ILOAD 18
//	IF_ICMPGE L82
//	ALOAD 17
//	ILOAD 19
//	AALOAD
//	ASTORE 20
//	L83
//	LINENUMBER 233 L83
//	ALOAD 14
//	ALOAD 20
//	INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal ()I
//	BALOAD
//	IFEQ L84
//	L85
//	LINENUMBER 234 L85
//	ALOAD 5
//	ALOAD 20
//	INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerUsed (Lnet/minecraft/util/BlockRenderLayer;)V
//	Original code

	var INVOKEVIRTUAL_CompiledChunk_setLayerUsed;
	var arrayLength = instructions.size();
	for (var i = 0; i < arrayLength; ++i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == INVOKEVIRTUAL) {
			if (instruction.owner == "net/minecraft/client/renderer/chunk/CompiledChunk") {
				//CPW PLS GIVE ME A WAY TO REMAP SRG TO NAMES FOR DEV
				if (instruction.name == "func_178486_a" || instruction.name == "setLayerUsed") {
					if (instruction.desc == "(Lnet/minecraft/util/BlockRenderLayer;)V") {
						if (instruction.itf == false) {
							INVOKEVIRTUAL_CompiledChunk_setLayerUsed = instruction;
							log("Found injection point " + instruction);
							break;
						}
					}
				}
			}
		}
	}
	if (!INVOKEVIRTUAL_CompiledChunk_setLayerUsed) {
		throw "Error: Couldn't find injection point!";
	}

	var firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed;
	for (i = instructions.indexOf(INVOKEVIRTUAL_CompiledChunk_setLayerUsed); i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.INSN) {
			if (instruction.getOpcode() == ARRAYLENGTH) {
				firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed = instruction;
				log("Found ARRAYLENGTH " + instruction);
				break;
			}
		}
	}
	if (!firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed) {
		throw "Error: Couldn't find ARRAYLENGTH!";
	}

	var firstLabelBefore_firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed;
	for (i = instructions.indexOf(firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed); i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getType() == AbstractInsnNode.LABEL) {
			firstLabelBefore_firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed = instruction;
			log("Found label " + instruction);
			break;
		}
	}
	if (!firstLabelBefore_firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed) {
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
	toInject.add(new VarInsnNode(ALOAD, ASTACK_aboolean_usedRenderLayers));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_random));
	toInject.add(new VarInsnNode(ALOAD, ASTACK_blockrendererdispatcher));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"postIteration",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;)V",
			//boolean isInterface
			false
	));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(firstLabelBefore_firstARRAYLENGTHBefore_INVOKEVIRTUAL_CompiledChunk_setLayerUsed, toInject);

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
	print("[postIteration Transformer] " + msg);
}
