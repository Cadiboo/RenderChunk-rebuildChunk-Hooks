package cadiboo.renderchunkrebuildchunkhooks.core;

import org.objectweb.asm.tree.InsnList;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine extends RenderChunkRebuildChunkHooksRenderChunkClassTransformer {

    @Override
    public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {

        return super.transform(unTransformedName, transformedName, basicClass);

    }

    public void injectHooks(InsnList instructions) {

        super.injectHooks(instructions);

    }

    public void injectRebuildChunkPreEventHook(InsnList instructions) {

        // get "++renderChunksUpdated;"
        // inject after
        // get line number for nice debug

//        GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//                ICONST_1
//        IADD
//        PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I

    }

    public void injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions) {

        // get "block.canRenderInLayer(iblockstate, blockrenderlayer);"
        // "INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z"
        // replace it with our hook and inject before & after (let the existing ALOADs stay because we use the same variables)

    }

    public void injectRebuildRebuildChunkBlockEventHook(InsnList instructions) {

        // find "blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);"
        // "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z"
        // get return label
        // get line number for nice debug
        // inject before
    }

}
