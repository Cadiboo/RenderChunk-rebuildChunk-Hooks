package cadiboo.renderchunkrebuildchunkhooks.core;

import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
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

		// read in, build classNode
		final ClassNode classNode = new ClassNode();
		final ClassReader cr = new ClassReader(basicClass);
		cr.accept(classNode, CLASS_READER_FLAGS);

		// peek at classNode and modifier
		final List<MethodNode> methods = classNode.methods;
		for (final MethodNode method : methods) {
			LOGGER.info("name=" + method.name + " desc=" + method.desc);
			final InsnList insnList = method.instructions;
			final ListIterator<AbstractInsnNode> ite = insnList.iterator();
			while (ite.hasNext()) {
				final AbstractInsnNode insn = ite.next();
				final int opcode = insn.getOpcode();
				// add before return: System.out.println("Returning...")
				if (opcode == RETURN) {
					final InsnList tempList = new InsnList();
					tempList.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
					tempList.add(new LdcInsnNode("Returning..."));
					tempList.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
					insnList.insert(insn.getPrevious(), tempList);
					method.maxStack += 2;
				}
			}
		}

		// write classNode
		try {
			final ClassWriter out = new ClassWriter(CLASS_WRITER_FLAGS);

			// make the ClassWriter visit all the code in classNode
			classNode.accept(out);

			LOGGER.info("Injected hooks sucessfully!");
			return out.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.error("FAILED to inject hooks!!! Discarding changes.");
			LOGGER.warn("Any mods that depend on the hooks provided by this mod will break");
			return basicClass;
		}

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

			return originalVisitor;

		};

	}

}
