package cadiboo.renderchunkrebuildchunkhooks.core;

import org.objectweb.asm.tree.InsnList;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
// useful links:
// https://text-compare.com
// http://www.minecraftforge.net/forum/topic/32600-1710-strange-error-with-custom-event-amp-event-handler/?do=findComment&comment=172480
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerOptifine extends RenderChunkRebuildChunkHooksRenderChunkClassTransformerVanillaForge {

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {

		return super.transform(unTransformedName, transformedName, basicClass);

	}

	@Override
	public void injectHooks(InsnList instructions) {

		super.injectHooks(instructions);

	}

	/**
	 * get "++renderChunksUpdated;"
	 * inject after
	 * get line number for nice debug
	 *
	 * @param instructions the instructions for the method
	 */
	@Override
	public void injectRebuildChunkPreEventHook(InsnList instructions) {

		super.injectRebuildChunkPreEventHook(instructions);

	}

	/**
	 * get "block.canRenderInLayer(iblockstate, blockrenderlayer);"<br>
	 * "INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z"<br>
	 * replace it with our hook and inject before & after (let the existing ALOADs stay because we use the same variables)<br>
	 *
	 * @param instructions the instructions for the method
	 */
	@Override
	public void injectRebuildChunkBlockRenderInLayerEventHook(InsnList instructions) {

		super.injectRebuildChunkBlockRenderInLayerEventHook(instructions);

	}

	/**
	 * find "blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);"<br>
	 * "INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z"<br>
	 * get return label<br>
	 * get line number for nice debug<br>
	 * inject before<br>
	 *
	 * @param instructions
	 */
	@Override
	public void injectRebuildChunkBlockEventHook(InsnList instructions) {

		super.injectRebuildChunkBlockEventHook(instructions);

	}

	/**
	 * find last return statement in method<br>
	 * get line number for nice debug<br>
	 * inject before<br>
	 *
	 * @param instructions
	 */
	@Override
	public void injectRebuildChunkPostEventHook(InsnList instructions) {

		super.injectRebuildChunkPostEventHook(instructions);

	}

}
