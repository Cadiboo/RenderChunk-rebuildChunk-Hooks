package cadiboo.renderchunkrebuildchunkhooks.core;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.core.util.Names;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

/**
 * @author Cadiboo
 * @see <a href="http://www.egtry.com/java/bytecode/asm/tree_transform">http://www.egtry.com/java/bytecode/asm/tree_transform</a>
 */
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer, Opcodes, Names {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	public static final boolean DEBUG_EVERYTHING = true;

	public static final int CLASS_WRITER_FLAGS = ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
	// skip class reader reading frames if the class writer is going to compute them for us (you should get a warning that this being 0 is dead code)
	public static final int CLASS_READER_FLAGS = (CLASS_WRITER_FLAGS & ClassWriter.COMPUTE_FRAMES) == ClassWriter.COMPUTE_FRAMES ? ClassReader.SKIP_FRAMES : 0;

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {

		if (DEBUG_EVERYTHING) {
			if ((unTransformedName.startsWith("b") || unTransformedName.startsWith("net.minecraft.client.renderer.chunk.")) || (transformedName.startsWith("b") || transformedName.startsWith("net.minecraft.client.renderer.chunk."))) {
				LOGGER.info("unTransformedName: " + unTransformedName + ", transformedName: " + transformedName + ", unTransformedName equals: " + unTransformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME) + ", transformedName equals: " + transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME));
			}
		}

		if (!transformedName.equals(RENDER_CHUNK_TRANSFORMED_NAME)) {
			return basicClass;
		}

		final ClassReader renderChunkReader = new ClassReader(basicClass);
		final ClassWriter renderChunkWriter = new ClassWriter(renderChunkReader, CLASS_WRITER_FLAGS);

		final ClassVisitor renderChunkVisitor = new RenderChunkClassVisitor(ASM5, renderChunkWriter);

		try {
			// make renderChunkVisitor visit all the code in renderChunkReader
			renderChunkReader.accept(renderChunkVisitor, CLASS_READER_FLAGS);

			LOGGER.info("Injected hooks sucessfully!");
			return renderChunkWriter.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			return basicClass;
		}

//		final MethodNode renderChunk_rebuildChunkNode = new MethodNode(ASM5);
//		final InsnList renderChunk_rebuildChunkInstructions = renderChunk_rebuildChunkNode.accept(renderChunkVisitor, 0);

	}

	public static class RenderChunkClassVisitor extends ClassVisitor {

		public static final Logger LOGGER = LogManager.getLogger();

		public RenderChunkClassVisitor(final int api, final ClassVisitor cv) {
			super(api, cv);

			if (DEBUG_EVERYTHING) {
				LOGGER.info("RebuildChunk type: " + REBUILD_CHUNK_TYPE);
				LOGGER.info("RebuildChunk descriptor: " + REBUILD_CHUNK_DESCRIPTOR);
			}
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {

			final MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

			if (!desc.equals(REBUILD_CHUNK_DESCRIPTOR)) {
				if (DEBUG_EVERYTHING) {
					LOGGER.info("Method with name \"" + name + "\" and description \"" + desc + "\" did not match");
				}
				return originalVisitor;
			}

			if (DEBUG_EVERYTHING) {
				LOGGER.info("Method with name \"" + name + "\" and description \"" + desc + "\" matched!");
			}

			// make sure not to overwrite resortTransparency (it has the same description but it's name is "a" while rebuildChunk's name is "b")
			if (name.equals("a") || name.equals("resortTransparency")) {
				if (DEBUG_EVERYTHING) {
					LOGGER.info("Method with name \"" + name + "\" and description \"" + desc + "\" was rejected");
				}
				return originalVisitor;
			}

			if (DEBUG_EVERYTHING) {
				LOGGER.info("Method with name \"" + name + "\" and description \"" + desc + "\" matched and passed");
			}

			if (DEBUG_EVERYTHING) {
				LOGGER.info("Method with name \"" + name + "\" and description \"" + desc + "\" matched and passed");
			}

			return new MethodNode(this.api, access, name, desc, signature, exceptions) {
				@Override
				public void visitEnd() {
					LOGGER.info(this.instructions);
					this.accept(RenderChunkClassVisitor.this.cv);
					LOGGER.info(this.instructions);
				}
			};

		};

	}

}
