function initializeCoreMod() {
	return {
		'coremodone': {
			'target': {
				'type': 'CLASS',
				'name': 'net.minecraft.client.renderer.chunk.RenderChunk'
			},
			'transformer': function(classNode) {
				print("krack");
				print("RenderChunk: ", classNode.name);

				var methods = classNode.methods;

				var arrayLength = methods.length;
				for (var i = 0; i < arrayLength; i++) {
					var method = methods[i];

					if(methodMatches(method)) {
						print("Calling injectHooks");
						injectHooks(method.instructions);
					}

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
var/*Class*/ InsnList = Java.type('org.objectweb.asm.tree.InsnList');

var ALOAD = Opcodes.ALOAD;
var FLOAD = Opcodes.FLOAD;
var RETURN = Opcodes.RETURN;
var INVOKESTATIC = Opcodes.INVOKESTATIC;

var DEBUG_METHODS = true;
var DEBUG_INSTRUCTIONS = false;

var RENDER_CHUNK_REBUILD_CHUNK = {
	'getName': function() {
		//TODO (de)obf
		return "rebuildChunk";
//		return "func_178581_b";
	},
	'getDescriptor': function() {
		return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V";
	}
}

var RENDER_CHUNK_RESORT_TRANSPARENCY = {
	'getName': function() {
		//TODO (de)obf
		return "resortTransparency";
//		return "func_178570_a";
	},
	'getDescriptor': function() {
		return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V"
	}
}

var REBUILD_CHUNK_REDIRECT_TEMP =  {
	'visit': function(insnList) {
		// invokestatic io/github/cadiboo/renderchunkrebuildchunkhooks/HooksTemp(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V
		insnList.add(
			new MethodInsnNode(INVOKESTATIC,
				"io/github/cadiboo/renderchunkrebuildchunkhooks",
				 "HooksTemp",
				 "(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V",
				 false
			)
		);

//		public MethodInsnNode(
//              final int opcode,
//              final String owner,
//              final String name,
//              final String descriptor,
//              final boolean isInterface) {

	}
}

function methodMatches(method) {
	if (!method.desc.equals(RENDER_CHUNK_REBUILD_CHUNK.getDescriptor())) {
		if (DEBUG_METHODS) {
			print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" did not match");
		}
		return false;
	}

	if (DEBUG_METHODS) {
		print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched!");
	}

	// make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
	if (!method.name.equals(RENDER_CHUNK_REBUILD_CHUNK.getName())) {
		if (DEBUG_METHODS) {
			print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" was rejected");
		}
		return false;
	}

	if (DEBUG_METHODS) {
		print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched and passed");
	}

	return true;
}

function injectHooks(instructions) {

	print("Inside injectHooks");
	print(instructions);

	if (DEBUG_INSTRUCTIONS) {
		var instructionsArray = instructions.toArray();
		var arrayLength = instructionsArray.length;
		for (var i = 0; i < arrayLength; i++) {
			var instruction = instructionsArray[i];
			print(insnToString(instruction));
		}
	}

	overwriteMethodTemp(instructions);

}

var ALOAD_this = 0;
var FLOAD_x = 1;
var FLOAD_y = 2;
var FLOAD_z = 3;
var ALOAD_generator = 4;

// Finds the first instruction (NEW net/minecraft/client/renderer/chunk/CompiledChunk)
// and inserts before it. Ugly, effective, incompatible
function overwriteMethodTemp(instructions) {

	var NEW_CompiledChunk;

	var instructionsArray = instructions.toArray();

	var instruction;
	for (instruction in instructionsArray) {

		print(instruction);
		if(instruction.getOpcode() == Opcodes.NEW) {
			NEW_CompiledChunk = instruction;
			print(instruction);
			print(NEW_CompiledChunk);
			break;
		}

	}

	var tempInstructionList = new InsnList();

	tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_this)); // this
	tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_x)); // x
	tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_y)); // y
	tempInstructionList.add(new VarInsnNode(FLOAD, FLOAD_z)); // z
	tempInstructionList.add(new VarInsnNode(ALOAD, ALOAD_generator)); // generator
	REBUILD_CHUNK_REDIRECT_TEMP.visit(tempInstructionList);
	tempInstructionList.add(new InsnNode(RETURN));

	instructions.insertBefore(NEW_CompiledChunk, tempInstructionList);

	print("INJECTED SUCCESSFULLY!!!");
	print("INJECTED SUCCESSFULLY!!!");
	print("INJECTED SUCCESSFULLY!!!");
	print("INJECTED SUCCESSFULLY!!!");

	// aload0
	// float1
	// float2
	// float3
	// aload4
	// invokestatic io/github/cadiboo/renderchunkrebuildchunkhooks/HooksTemp(Lnet/minecraft/client/renderer/chunk/RenderChunk;FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V
	//return

	// io.github.cadiboo.renderchunkrebuildchunkhooks.HooksTemp

	//public static void rebuildChunk(
	//			final RenderChunk renderChunk,
	//			float x, float y, float z, ChunkRenderTask generator
	//	) {

}



























var splittableString = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,"
	+ "ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,"
	+ "FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,"
	+ "ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,"
	+ "LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,"
	+ "LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,"
	+ "LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,"
	+ "POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,"
	+ "FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,"
	+ "FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,"
	+ "ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,"
	+ "I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,"
	+ "FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,"
	+ "IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,"
	+ "IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,"
	+ "IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,"
	+ "PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,"
	+ "INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,"
	+ "ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,"
	+ "MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
var opcodesLookupArray = splittableString.split(",");
//
//splittableString = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
//var typesLookupArray = splittableString.split(",");
//
//splittableString = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,"
//	+ "H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,"
//	+ "H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
//var handlesLookupArray = splittableString.split(",");

function insnToString(insn) {
	try {
		return opcodesLookupArray[insn.opcode];
	} catch (e) {
		print(e);
	}
	return "";
}
