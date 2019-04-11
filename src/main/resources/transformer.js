function initializeCoreMod() {
	return {
		'RenderChunk rebuildChunk Hooks Transformer': {
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

					var instructions = method.instructions;

					log("Injecting hooks...");
					try {
						start("injectPreHook");
						injectPreHook(instructions);
						finish();
						start("injectCheckWorldHook");
						injectCheckWorldHook(instructions);
						finish();
						start("injectPreRenderHook");
						injectPreRenderHook(instructions);
						finish();
						start("injectPreIterationHook");
						injectPreIterationHook(instructions);
						finish();
						start("injectFluidHooks");
						injectFluidHooks(instructions);
						finish();
						start("injectBlockHooks");
						injectBlockHooks(instructions);
						finish();
						start("injectPostIterationHook");
							injectPostIterationHook(instructions);
						finish();
						start("injectPostRenderHook");
							injectPostRenderHook(instructions);
						finish();
						start("injectPostHook");
							injectPostHook(instructions);
						finish();
					} catch (exception) {
						var name = currentlyRunning;
						finish();
						log("Caught exception from " + name);
						throw exception;
					}
					log("Successfully injected hook!");
					break;

				}

				return classNode;
			}
		}
	}
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

function removeBetweenInclusive(instructions, startInstruction, endInstruction) {
	var start = instructions.indexOf(startInstruction);
	var end = instructions.indexOf(endInstruction);
	for (var i = start; i < end; ++i) {
		instructions.remove(instructions.get(start));
	}
}

var currentlyRunning;

function start(name) {
	log("Starting " + name);
	currentlyRunning = name;
}

function finish() {
	var name = currentlyRunning;
	currentlyRunning = undefined;
	log("Finished " + name);
}

function log(msg) {
	if (currentlyRunning == undefined) {
		print("[RenderChunk rebuildChunk Hooks Transformer]: " + msg);
	} else {
		print("[RenderChunk rebuildChunk Hooks Transformer] [" + currentlyRunning + "]: " + msg);
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

// Opcodes

// Access flags values, defined in
// - https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.1-200-E.1
// - https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.5-200-A.1
// - https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.6-200-A.1
// - https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.7.25
	var ACC_PUBLIC = Opcodes.ACC_PUBLIC; // class, field, method
	var ACC_PRIVATE = Opcodes.ACC_PRIVATE; // class, field, method
	var ACC_PROTECTED = Opcodes.ACC_PROTECTED; // class, field, method
	var ACC_STATIC = Opcodes.ACC_STATIC; // field, method
	var ACC_FINAL = Opcodes.ACC_FINAL; // class, field, method, parameter
	var ACC_SUPER = Opcodes.ACC_SUPER; // class
	var ACC_SYNCHRONIZED = Opcodes.ACC_SYNCHRONIZED; // method
	var ACC_OPEN = Opcodes.ACC_OPEN; // module
	var ACC_TRANSITIVE = Opcodes.ACC_TRANSITIVE; // module requires
	var ACC_VOLATILE = Opcodes.ACC_VOLATILE; // field
	var ACC_BRIDGE = Opcodes.ACC_BRIDGE; // method
	var ACC_STATIC_PHASE = Opcodes.ACC_STATIC_PHASE; // module requires
	var ACC_VARARGS = Opcodes.ACC_VARARGS; // method
	var ACC_TRANSIENT = Opcodes.ACC_TRANSIENT; // field
	var ACC_NATIVE = Opcodes.ACC_NATIVE; // method
	var ACC_INTERFACE = Opcodes.ACC_INTERFACE; // class
	var ACC_ABSTRACT = Opcodes.ACC_ABSTRACT; // class, method
	var ACC_STRICT = Opcodes.ACC_STRICT; // method
	var ACC_SYNTHETIC = Opcodes.ACC_SYNTHETIC; // class, field, method, parameter, module *
	var ACC_ANNOTATION = Opcodes.ACC_ANNOTATION; // class
	var ACC_ENUM = Opcodes.ACC_ENUM; // class(?) field inner
	var ACC_MANDATED = Opcodes.ACC_MANDATED; // parameter, module, module *
	var ACC_MODULE = Opcodes.ACC_MODULE; // class
	
// ASM specific access flags.
// WARNING: the 16 least significant bits must NOT be used, to avoid conflicts with standard
// access flags, and also to make sure that these flags are automatically filtered out when
// written in class files (because access flags are stored using 16 bits only).
	
	var ACC_DEPRECATED = Opcodes.ACC_DEPRECATED; // class, field, method
	
// Possible values for the type operand of the NEWARRAY instruction.
// See https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-6.html#jvms-6.5.newarray.
	
	var T_BOOLEAN = Opcodes.T_BOOLEAN;
	var T_CHAR = Opcodes.T_CHAR;
	var T_FLOAT = Opcodes.T_FLOAT;
	var T_DOUBLE = Opcodes.T_DOUBLE;
	var T_BYTE = Opcodes.T_BYTE;
	var T_SHORT = Opcodes.T_SHORT;
	var T_INT = Opcodes.T_INT;
	var T_LONG = Opcodes.T_LONG;

// Possible values for the reference_kind field of CONSTANT_MethodHandle_info structures.
// See https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-4.html#jvms-4.4.8.

	var H_GETFIELD = Opcodes.H_GETFIELD;
	var H_GETSTATIC = Opcodes.H_GETSTATIC;
	var H_PUTFIELD = Opcodes.H_PUTFIELD;
	var H_PUTSTATIC = Opcodes.H_PUTSTATIC;
	var H_INVOKEVIRTUAL = Opcodes.H_INVOKEVIRTUAL;
	var H_INVOKESTATIC = Opcodes.H_INVOKESTATIC;
	var H_INVOKESPECIAL = Opcodes.H_INVOKESPECIAL;
	var H_NEWINVOKESPECIAL = Opcodes.H_NEWINVOKESPECIAL;
	var H_INVOKEINTERFACE = Opcodes.H_INVOKEINTERFACE;

// ASM specific stack map frame types, used in {@link ClassVisitor#visitFrame}.

	/** An expanded frame. See {@link ClassReader#EXPAND_FRAMES}. */
	var F_NEW = Opcodes.F_NEW;

	/** A compressed frame with complete frame data. */
	var F_FULL = Opcodes.F_FULL;

	/**
	* A compressed frame where locals are the same as the locals in the previous frame, except that
	* additional 1-3 locals are defined, and with an empty stack.
	*/
	var F_APPEND = Opcodes.F_APPEND;

	/**
	* A compressed frame where locals are the same as the locals in the previous frame, except that
	* the last 1-3 locals are absent and with an empty stack.
	*/
	var F_CHOP = Opcodes.F_CHOP;

	/**
	* A compressed frame with exactly the same locals as the previous frame and with an empty stack.
	*/
	var F_SAME = Opcodes.F_SAME;

	/**
	* A compressed frame with exactly the same locals as the previous frame and with a single value
	* on the stack.
	*/
	var F_SAME1 = Opcodes.F_SAME1;

// The JVM opcode values (with the MethodVisitor method name used to visit them in comment, and
// where '-' means 'same method name as on the previous line').
// See https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-6.html.

	var NOP = Opcodes.NOP; // visitInsn
	var ACONST_NULL = Opcodes.ACONST_NULL; // -
	var ICONST_M1 = Opcodes.ICONST_M1; // -
	var ICONST_0 = Opcodes.ICONST_0; // -
	var ICONST_1 = Opcodes.ICONST_1; // -
	var ICONST_2 = Opcodes.ICONST_2; // -
	var ICONST_3 = Opcodes.ICONST_3; // -
	var ICONST_4 = Opcodes.ICONST_4; // -
	var ICONST_5 = Opcodes.ICONST_5; // -
	var LCONST_0 = Opcodes.LCONST_0; // -
	var LCONST_1 = Opcodes.LCONST_1; // -
	var FCONST_0 = Opcodes.FCONST_0; // -
	var FCONST_1 = Opcodes.FCONST_1; // -
	var FCONST_2 = Opcodes.FCONST_2; // -
	var DCONST_0 = Opcodes.DCONST_0; // -
	var DCONST_1 = Opcodes.DCONST_1; // -
	var BIPUSH = Opcodes.BIPUSH; // visitIntInsn
	var SIPUSH = Opcodes.SIPUSH; // -
	var LDC = Opcodes.LDC; // visitLdcInsn
	var ILOAD = Opcodes.ILOAD; // visitVarInsn
	var LLOAD = Opcodes.LLOAD; // -
	var FLOAD = Opcodes.FLOAD; // -
	var DLOAD = Opcodes.DLOAD; // -
	var ALOAD = Opcodes.ALOAD; // -
	var IALOAD = Opcodes.IALOAD; // visitInsn
	var LALOAD = Opcodes.LALOAD; // -
	var FALOAD = Opcodes.FALOAD; // -
	var DALOAD = Opcodes.DALOAD; // -
	var AALOAD = Opcodes.AALOAD; // -
	var BALOAD = Opcodes.BALOAD; // -
	var CALOAD = Opcodes.CALOAD; // -
	var SALOAD = Opcodes.SALOAD; // -
	var ISTORE = Opcodes.ISTORE; // visitVarInsn
	var LSTORE = Opcodes.LSTORE; // -
	var FSTORE = Opcodes.FSTORE; // -
	var DSTORE = Opcodes.DSTORE; // -
	var ASTORE = Opcodes.ASTORE; // -
	var IASTORE = Opcodes.IASTORE; // visitInsn
	var LASTORE = Opcodes.LASTORE; // -
	var FASTORE = Opcodes.FASTORE; // -
	var DASTORE = Opcodes.DASTORE; // -
	var AASTORE = Opcodes.AASTORE; // -
	var BASTORE = Opcodes.BASTORE; // -
	var CASTORE = Opcodes.CASTORE; // -
	var SASTORE = Opcodes.SASTORE; // -
	var POP = Opcodes.POP; // -
	var POP2 = Opcodes.POP2; // -
	var DUP = Opcodes.DUP; // -
	var DUP_X1 = Opcodes.DUP_X1; // -
	var DUP_X2 = Opcodes.DUP_X2; // -
	var DUP2 = Opcodes.DUP2; // -
	var DUP2_X1 = Opcodes.DUP2_X1; // -
	var DUP2_X2 = Opcodes.DUP2_X2; // -
	var SWAP = Opcodes.SWAP; // -
	var IADD = Opcodes.IADD; // -
	var LADD = Opcodes.LADD; // -
	var FADD = Opcodes.FADD; // -
	var DADD = Opcodes.DADD; // -
	var ISUB = Opcodes.ISUB; // -
	var LSUB = Opcodes.LSUB; // -
	var FSUB = Opcodes.FSUB; // -
	var DSUB = Opcodes.DSUB; // -
	var IMUL = Opcodes.IMUL; // -
	var LMUL = Opcodes.LMUL; // -
	var FMUL = Opcodes.FMUL; // -
	var DMUL = Opcodes.DMUL; // -
	var IDIV = Opcodes.IDIV; // -
	var LDIV = Opcodes.LDIV; // -
	var FDIV = Opcodes.FDIV; // -
	var DDIV = Opcodes.DDIV; // -
	var IREM = Opcodes.IREM; // -
	var LREM = Opcodes.LREM; // -
	var FREM = Opcodes.FREM; // -
	var DREM = Opcodes.DREM; // -
	var INEG = Opcodes.INEG; // -
	var LNEG = Opcodes.LNEG; // -
	var FNEG = Opcodes.FNEG; // -
	var DNEG = Opcodes.DNEG; // -
	var ISHL = Opcodes.ISHL; // -
	var LSHL = Opcodes.LSHL; // -
	var ISHR = Opcodes.ISHR; // -
	var LSHR = Opcodes.LSHR; // -
	var IUSHR = Opcodes.IUSHR; // -
	var LUSHR = Opcodes.LUSHR; // -
	var IAND = Opcodes.IAND; // -
	var LAND = Opcodes.LAND; // -
	var IOR = Opcodes.IOR; // -
	var LOR = Opcodes.LOR; // -
	var IXOR = Opcodes.IXOR; // -
	var LXOR = Opcodes.LXOR; // -
	var IINC = Opcodes.IINC; // visitIincInsn
	var I2L = Opcodes.I2L; // visitInsn
	var I2F = Opcodes.I2F; // -
	var I2D = Opcodes.I2D; // -
	var L2I = Opcodes.L2I; // -
	var L2F = Opcodes.L2F; // -
	var L2D = Opcodes.L2D; // -
	var F2I = Opcodes.F2I; // -
	var F2L = Opcodes.F2L; // -
	var F2D = Opcodes.F2D; // -
	var D2I = Opcodes.D2I; // -
	var D2L = Opcodes.D2L; // -
	var D2F = Opcodes.D2F; // -
	var I2B = Opcodes.I2B; // -
	var I2C = Opcodes.I2C; // -
	var I2S = Opcodes.I2S; // -
	var LCMP = Opcodes.LCMP; // -
	var FCMPL = Opcodes.FCMPL; // -
	var FCMPG = Opcodes.FCMPG; // -
	var DCMPL = Opcodes.DCMPL; // -
	var DCMPG = Opcodes.DCMPG; // -
	var IFEQ = Opcodes.IFEQ; // visitJumpInsn
	var IFNE = Opcodes.IFNE; // -
	var IFLT = Opcodes.IFLT; // -
	var IFGE = Opcodes.IFGE; // -
	var IFGT = Opcodes.IFGT; // -
	var IFLE = Opcodes.IFLE; // -
	var IF_ICMPEQ = Opcodes.IF_ICMPEQ; // -
	var IF_ICMPNE = Opcodes.IF_ICMPNE; // -
	var IF_ICMPLT = Opcodes.IF_ICMPLT; // -
	var IF_ICMPGE = Opcodes.IF_ICMPGE; // -
	var IF_ICMPGT = Opcodes.IF_ICMPGT; // -
	var IF_ICMPLE = Opcodes.IF_ICMPLE; // -
	var IF_ACMPEQ = Opcodes.IF_ACMPEQ; // -
	var IF_ACMPNE = Opcodes.IF_ACMPNE; // -
	var GOTO = Opcodes.GOTO; // -
	var JSR = Opcodes.JSR; // -
	var RET = Opcodes.RET; // visitVarInsn
	var TABLESWITCH = Opcodes.TABLESWITCH; // visiTableSwitchInsn
	var LOOKUPSWITCH = Opcodes.LOOKUPSWITCH; // visitLookupSwitch
	var IRETURN = Opcodes.IRETURN; // visitInsn
	var LRETURN = Opcodes.LRETURN; // -
	var FRETURN = Opcodes.FRETURN; // -
	var DRETURN = Opcodes.DRETURN; // -
	var ARETURN = Opcodes.ARETURN; // -
	var RETURN = Opcodes.RETURN; // -
	var GETSTATIC = Opcodes.GETSTATIC; // visitFieldInsn
	var PUTSTATIC = Opcodes.PUTSTATIC; // -
	var GETFIELD = Opcodes.GETFIELD; // -
	var PUTFIELD = Opcodes.PUTFIELD; // -
	var INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL; // visitMethodInsn
	var INVOKESPECIAL = Opcodes.INVOKESPECIAL; // -
	var INVOKESTATIC = Opcodes.INVOKESTATIC; // -
	var INVOKEINTERFACE = Opcodes.INVOKEINTERFACE; // -
	var INVOKEDYNAMIC = Opcodes.INVOKEDYNAMIC; // visitInvokeDynamicInsn
	var NEW = Opcodes.NEW; // visitTypeInsn
	var NEWARRAY = Opcodes.NEWARRAY; // visitIntInsn
	var ANEWARRAY = Opcodes.ANEWARRAY; // visitTypeInsn
	var ARRAYLENGTH = Opcodes.ARRAYLENGTH; // visitInsn
	var ATHROW = Opcodes.ATHROW; // -
	var CHECKCAST = Opcodes.CHECKCAST; // visitTypeInsn
	var INSTANCEOF = Opcodes.INSTANCEOF; // -
	var MONITORENTER = Opcodes.MONITORENTER; // visitInsn
	var MONITOREXIT = Opcodes.MONITOREXIT; // -
	var MULTIANEWARRAY = Opcodes.MULTIANEWARRAY; // visitMultiANewArrayInsn
	var IFNULL = Opcodes.IFNULL; // visitJumpInsn
	var IFNONNULL = Opcodes.IFNONNULL; // -

// Local variable indexes
var ALOCALVARIABLE_this = 0;
var FLOCALVARIABLE_x = 1;
var FLOCALVARIABLE_y = 2;
var FLOCALVARIABLE_z = 3;
var ALOCALVARIABLE_generator = 4;
var ALOCALVARIABLE_compiledchunk = 5;
var ILOCALVARIABLE_i_unused = 6;
var ALOCALVARIABLE_blockpos_startPos = 7;
var ALOCALVARIABLE_blockpos1_endPos = 8;
var ALOCALVARIABLE_world = 9;
var ALOCALVARIABLE_lvt_10_1__ChunkCache = 10;
var ALOCALVARIABLE_lvt_11_1__VisGraph = 11;
var ALOCALVARIABLE_lvt_12_1__HashSetTileEntities = 12;
var ALOCALVARIABLE_aboolean_usedBlockRenderLayers = 13;
var ALOCALVARIABLE_set_TileEntities = 13;
    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
    // declaration: set extends java.util.Set<net.minecraft.tileentity.TileEntity>
var ALOCALVARIABLE_random = 14;
var ALOCALVARIABLE_set1_TileEntities = 14;
    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
    // declaration: set1 extends java.util.Set<net.minecraft.tileentity.TileEntity>
var ALOCALVARIABLE_blockrendererdispatcher = 15;
var ALOCALVARIABLE_blockpos$mutableblockpos = 17;
var ALOCALVARIABLE_iblockstate = 18;
var ALOCALVARIABLE_block = 19;
var ALOCALVARIABLE_blockrenderlayer = 19;
var ALOCALVARIABLE_tileentity = 20;
var ALOCALVARIABLE_ifluidstate = 20;
var ALOCALVARIABLE_tileentityrenderer = 21;
    // signature Lnet/minecraft/client/renderer/tileentity/TileEntityRenderer<Lnet/minecraft/tileentity/TileEntity;>;
    // declaration: tileentityrenderer extends net.minecraft.client.renderer.tileentity.TileEntityRenderer<net.minecraft.tileentity.TileEntity>
var ALOCALVARIABLE_blockrenderlayer1 = 24;
var ILOCALVARIABLE_j = 25;
var ILOCALVARIABLE_k = 25;
var ALOCALVARIABLE_bufferbuilder = 26;
var ALOCALVARIABLE_bufferbuilder1 = 26;










// Finds the first instruction (NEW net/minecraft/client/renderer/chunk/CompiledChunk)
// then finds the previous label
// and inserts after the label and before the label's instructions.
function injectPreHook(instructions) {

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
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
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

}

// Finds the first instruction GETFIELD world
// then finds the next label
// and inserts after the label and before the label's instructions.
function injectCheckWorldHook(instructions) {

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
						log("Found injection point " + instruction);
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
			log("Found label " + instruction);
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
	var ALOCALVARIABLE_worldRef = getMaxStack(instructions) + 1;
	var worldEqualsWorldRefLabel = new LabelNode();

	// Make list of instructions to inject
	toInject.add(new TypeInsnNode(NEW, "io/github/cadiboo/renderchunkrebuildchunkhooks/util/WorldReference"));
    toInject.add(new InsnNode(DUP));
    toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
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
    toInject.add(new VarInsnNode(ASTORE, ALOCALVARIABLE_worldRef));

    toInject.add(new LabelNode());
    toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_worldRef));
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
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_worldRef));
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
	toInject.add(new VarInsnNode(ASTORE, ALOCALVARIABLE_world));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insert(firstLabelAfter_first_GETFIELD_world, toInject);

}

// Finds the first instruction Sets.newHashSet
// then inserts right after the next label (before said label's instructions)
function injectPreRenderHook(instructions) {

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
    toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
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

}

// Finds the first instruction INVOKESTATIC BlockPos.getAllInBoxMutable
// then finds the previous label
// and inserts after the label and before the label's instructions.
function injectPreIterationHook(instructions) {

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
	for (i = instructions.indexOf(first_INVOKESTATIC_getAllInBoxMutable); i >=0; --i) {
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
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
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

}

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
function injectFluidHooks(instructions) {

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
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos$mutableblockpos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_iblockstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_block));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_ifluidstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrenderlayer1));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"canFluidRender",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;)Z",
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
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos$mutableblockpos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_iblockstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_block));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_ifluidstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrenderlayer1));
		toInject.add(new VarInsnNode(ILOAD, ILOCALVARIABLE_j));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_bufferbuilder));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"preRenderFluid",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;ILnet/minecraft/client/renderer/BufferBuilder;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFNE, fluidCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_INVOKEVIRTUAL_renderFluid, toInject);

	}

}

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
function injectBlockHooks(instructions) {

// Everything is copied from fluid-hooks

	var blockCannotRenderLabel;

	// 1) find IBlockState.getRenderType
    // 2) find label for IBlockState.getRenderType
    // 3) find label that IBlockState.getRenderType would jump to
    // 4) find IBlockState.canRenderInLayer
    // 5) find next label after IBlockState.canRenderInLayer
    // 6) delete everything (exclusive) between IBlockState.getRenderType label and the next label after IBlockState.canRenderInLayer
    // 7) insert canRender hook after IBlockState.getRenderType label
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
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos$mutableblockpos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_iblockstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_block));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_ifluidstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrenderlayer1));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"canBlockRender",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;)Z",
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
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
		toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos$mutableblockpos));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_iblockstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_block));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_ifluidstate));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrenderlayer1));
		toInject.add(new VarInsnNode(ILOAD, ILOCALVARIABLE_k));
		toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_bufferbuilder1));
		toInject.add(new MethodInsnNode(
				//int opcode
				INVOKESTATIC,
				//String owner
				"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
				//String name
				"preRenderBlock",
				//String descriptor
				"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;[ZLjava/util/Random;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/Block;Lnet/minecraft/fluid/IFluidState;Lnet/minecraft/util/BlockRenderLayer;ILnet/minecraft/client/renderer/BufferBuilder;)Z",
				//boolean isInterface
				false
		));
		toInject.add(new JumpInsnNode(IFNE, blockCannotRenderLabel));

		toInject.add(originalInstructionsLabel);

		// Inject instructions
		instructions.insert(firstLabelBefore_INVOKEVIRTUAL_renderBlock, toInject);

	}

}

// Finds the first instruction INVOKESTATIC BlockPos.getAllInBoxMutable
// then finds the previous label
// and inserts after the label and before the label's instructions.
//1) find setLayerUsed
//2) find prev ARRAYLENGTH
//3) find prev label
//4) insert _after_ said label and _before_ normal insns
function injectPostIterationHook(instructions) {

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
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_aboolean_usedBlockRenderLayers));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_random));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockrendererdispatcher));
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

}

// Finds the last instruction computeVisibility
// then inserts before it
function injectPostRenderHook(instructions) {

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
    toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_10_1__ChunkCache));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_11_1__VisGraph));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_lvt_12_1__HashSetTileEntities));
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

}

// Finds the last instruction RETURN
// then inserts before it
function injectPostHook(instructions) {

//			}
//
//		}
//		// START HOOK
//		io.github.cadiboo.renderchunkrebuildchunkhooks.hooks.Hooks.post(this, x, y, z, generator, compiledchunk, blockpos, blockpos1, world, lvt_10_1_, lvt_11_1_, lvt_12_1_);
//		// END HOOK
//	}

//	L16
//	LINENUMBER 216 L16
//	FRAME CHOP 3
//	RETURN

//	L97
//	LINENUMBER 264 L97
//	FRAME SAME
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
//	INVOKESTATIC io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks.post (Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/chunk/RenderChunkCache;Lnet/minecraft/client/renderer/chunk/VisGraph;Ljava/util/HashSet;)V
//	L22
//	LINENUMBER 267 L22
//	FRAME CHOP 3
//	RETURN

	var last_RETURN;
	for (var i = instructions.size() - 1; i >=0; --i) {
		var instruction = instructions.get(i);
		if (instruction.getOpcode() == RETURN) {
			last_RETURN = instruction;
			log("Found injection point " + instruction);
			break;
		}
	}
	if (!last_RETURN) {
		throw "Error: Couldn't find injection point!";
	}

	//FFS why
	var toInject = ASMAPI.getMethodNode().instructions;

	// Labels n stuff
	var originalInstructionsLabel = new LabelNode();

	// Make list of instructions to inject
    toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_this));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_x));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_y));
	toInject.add(new VarInsnNode(FLOAD, FLOCALVARIABLE_z));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_generator));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_compiledchunk));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos_startPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_blockpos1_endPos));
	toInject.add(new VarInsnNode(ALOAD, ALOCALVARIABLE_world));
	toInject.add(new MethodInsnNode(
			//int opcode
			INVOKESTATIC,
			//String owner
			"io/github/cadiboo/renderchunkrebuildchunkhooks/hooks/Hooks",
			//String name
			"post",
			//String descriptor
			"(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/World;)V",
			//boolean isInterface
			false
	));

	toInject.add(originalInstructionsLabel);

	// Inject instructions
	instructions.insertBefore(last_RETURN, toInject);

}
