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
				var/*Class*/ MethodNode = Java.type('org.objectweb.asm.tree.MethodNode')
				var/*Class/Interface*/ Opcodes = Java.type('org.objectweb.asm.Opcodes')

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

var DEBUG_METHODS = true;
var DEBUG_INSTRUCTIONS = true;

var RENDER_CHUNK_REBUILD_CHUNK = {
	'getName': function() {
		return "rebuildChunk"; //TODO obf func_178581_b
	},
	'getDescriptor': function() {
		return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V";
	}
}

var RENDER_CHUNK_RESORT_TRANSPARENCY = {
	'getName': function() {
		return "resortTransparency"; //TODO obf func_178570_a
	},
	'getDescriptor': function() {
		return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V"
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
	if (method.name.equals(RENDER_CHUNK_RESORT_TRANSPARENCY.getName())) {
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

}










//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//var s = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,"
//	+ "ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,"
//	+ "FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,"
//	+ "ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,"
//	+ "LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,"
//	+ "LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,"
//	+ "LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,"
//	+ "POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,"
//	+ "FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,"
//	+ "FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,"
//	+ "ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,"
//	+ "I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,"
//	+ "FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,"
//	+ "IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,"
//	+ "IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,"
//	+ "IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,"
//	+ "PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,"
//	+ "INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,"
//	+ "ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,"
//	+ "MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,"
//var opcodesLookup = s.split(",");
//
//s = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
//var typesLookup = s.split(",");
//
//s = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,"
//	+ "H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,"
//	+ "H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
//var handlesLookup = s.split(",");
//
//function insnToString(insn) {
//	try {
//		return opcodesLookup[insn.opcode];
//	} catch (e) {
//		print(e);
//	}
//}
