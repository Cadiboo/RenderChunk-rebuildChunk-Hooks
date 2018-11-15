package cadiboo.renderchunkrebuildchunkhooks.core;

import com.sun.tools.doclint.Entity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.HashSet;

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

	public void injectHooks(InsnList instructions) {

		super.injectHooks(instructions);

	}

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
	public void injectRebuildRebuildChunkBlockEventHook(InsnList instructions) {

		super.injectRebuildRebuildChunkBlockEventHook(instructions);

	}

}
