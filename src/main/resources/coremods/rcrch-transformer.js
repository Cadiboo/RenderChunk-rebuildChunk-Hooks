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













                






                return classNode;
            }
        }
    }
}
