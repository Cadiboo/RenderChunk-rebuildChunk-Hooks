package cadiboo.renderchunkrebuildchunkhooks;

import java.util.List;

import org.objectweb.asm.Type;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.launchwrapper.IClassTransformer;

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer2 implements IClassTransformer {

	private static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (!name.equals("net.minecraft.client.renderer.chunk.RenderChunk") && !name.equals("cws")) {
			return basicClass;
		}

		final boolean deobfuscated = name.equals("net.minecraft.client.renderer.chunk.RenderChunk");

		final Type rebuildChunkType = Type.getMethodType(Type.VOID_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.getObjectType(ChunkCompileTaskGenerator.class.get)));
		final String rebuildChunkDescriptor = rebuildChunkType.getDescriptor();
		return basicClass;
	}

	private byte[] addRebuildChunkEvent(final String name, final byte[] basicClass, final boolean deobfuscated) {
		// TODO Auto-generated method stub
		return basicClass;
	}

	private byte[] addRebuildChunkBlocksEvent(final String name, final byte[] hookRebuildChunkEvent, final boolean deobfuscated) {
		// TODO Auto-generated method stub
		return hookRebuildChunkEvent;
	}

	private byte[] addRebuildChunkBlockEvent(final String name, final byte[] hookRebuildChunkBlocksEvent, final boolean deobfuscated) {
		// TODO Auto-generated method stub
		return hookRebuildChunkBlocksEvent;
	}

}
