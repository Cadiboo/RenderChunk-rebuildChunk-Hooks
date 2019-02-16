function initializeCoreMod() {
    return {
        'coremodone': {
            'target': {
                'type': 'CLASS',
                'name': 'net.minecraft.client.renderer.chunk.RenderChunk'
            },
            'transformer': function(classNode) {
                print("RenderChunk: ", classNode.name);
                var/*Class*/ MethodNode = Java.type('org.objectweb.asm.tree.MethodNode')
                var/*Interface*/ Opcodes = Java.type('org.objectweb.asm.Opcodes')

                for (var method in classNode.methods) {

                    if (!method.desc.equals(RENDER_CHUNK_REBUILD_CHUNK.getDescriptor())) {
                        if (DEBUG_METHODS) {
                            print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" did not match");
                        }
                        continue;
                    }

                    if (DEBUG_METHODS) {
                        print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched!");
                    }

                    // make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
                    if (method.name.equals(RENDER_CHUNK_RESORT_TRANSPARENCY.getName())) {
                        if (DEBUG_METHODS) {
                            print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" was rejected");
                        }
                        continue;
                    }

                    if (DEBUG_METHODS) {
                        print("Method with name \"" + method.name + "\" and description \"" + method.desc + "\" matched and passed");
                    }

                    injectHooks(method.instructions);

                }


                return classNode;
            }
        }
    }
}

let DEBUG_METHODS = true;

let RENDER_CHUNK_REBUILD_CHUNK = {
    'getName': function() {
        "rebuildChunk"; //TODO obf func_178581_b
    },
    'getDescriptor': function() {
        return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V";
    }
}

let RENDER_CHUNK_RESORT_TRANSPARENCY = {
    'getName': function() {
        "resortTransparency"; //TODO obf func_178570_a
    },
    'getDescriptor': function() {
        return "(FFFLnet/minecraft/client/renderer/chunk/ChunkRenderTask;)V"
    }
}


function injectHooks(instructions) {

    for (instruction in instructions) {
        print(instruction);
    }

}
