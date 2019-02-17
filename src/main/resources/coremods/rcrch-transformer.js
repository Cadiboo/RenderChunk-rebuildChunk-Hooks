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

	if(DEBUG_INSTRUCTIONS) {
		var instructionsArray = instructions.toArray();
		var arrayLength = instructionsArray.length;
		for (var i = 0; i < arrayLength; i++) {
			var instruction = instructionsArray[i];
			print(insnToString(instruction));
		}
	}

}

var/*Class*/ MethodNode = Java.type('org.objectweb.asm.tree.MethodNode')

//imports
var Printer = Java.type('org.objectweb.asm.util.Printer');
var Textifier = Java.type('org.objectweb.asm.util.Textifier');
var TraceClassVisitor = Java.type('org.objectweb.asm.util.TraceClassVisitor');
var TraceMethodVisitor = Java.type('org.objectweb.asm.util.TraceMethodVisitor');
var StringWriter = Java.type('org.objectweb.asm.repackagehacks.'+'java.io.StringWriter');
var PrintWriter = Java.type('org.objectweb.asm.repackagehacks.'+'java.io.PrintWriter');


var /*private static final Printer*/ PRINTER = new Textifier();
var /*private static final TraceMethodVisitor*/ TRACE_METHOD_VISITOR = new TraceMethodVisitor(PRINTER);

function insnToString(/*final AbstractInsnNode*/ insn) {
	insn.accept(TRACE_METHOD_VISITOR);
	var /*final StringWriter*/ sw = new StringWriter();
	PRINTER.print(new PrintWriter(sw));
	PRINTER.getText().clear();
	return sw.toString().trim();
}
