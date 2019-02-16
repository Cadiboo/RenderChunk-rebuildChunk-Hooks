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

//                var DEBUG_METHODS = true;

				var methods = classNode.methods;

				var arrayLength = methods.length;
				for (var i = 0; i < arrayLength; i++) {
					var method = methods[i];

                    if(methodMatches(method)) {
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
        "rebuildChunk"; //TODO obf func_178581_b
    },
    'getDescriptor': function() {
        return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V";
    }
}

var RENDER_CHUNK_RESORT_TRANSPARENCY = {
    'getName': function() {
        "resortTransparency"; //TODO obf func_178570_a
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

    var instruction;
    for (instruction in instructions) {
        print(instruction);
    }

}
