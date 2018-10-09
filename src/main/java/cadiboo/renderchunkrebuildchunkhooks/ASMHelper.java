package cadiboo.renderchunkrebuildchunkhooks;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public final class ASMHelper {
	/** A boolean which is true when we are in a development environment **/
	public static boolean isMCP = false;

	/**
	 * Creates a byte-array representation of the supplied ClassNode.
	 * @param  cnode   the ClassNode to be converted into a byte array
	 * @param  cwFlags the flags to be supplied to the ClassWriter. You almost always want both COMPUTE_FRAMES and COMPUTE_MAXS
	 * @return         a byte array representation of the ClassNode to be written back to the ClassLoader
	 */
	public static byte[] createBytes(final ClassNode cnode, final int cwFlags) {
		final ClassWriter cw = new ClassWriter(cwFlags);
		cnode.accept(cw);
		final byte[] bArr = cw.toByteArray();
		ManPackLoadingPlugin.MOD_LOG.log(Level.INFO, String.format("Class %s successfully transformed!", cnode.name));
		return bArr;
	}

	/**
	 * Creates a ClassNode from a byte array to be used for ASM modifications.
	 * @param  bytes A byte array representing the class to be modified by ASM
	 * @return       a new ClassNode instance
	 */
	public static ClassNode createClassNode(final byte[] bytes) {
		final ClassNode cnode = new ClassNode();
		final ClassReader reader = new ClassReader(bytes);
		reader.accept(cnode, ClassReader.EXPAND_FRAMES);
		return cnode;
	}

	/**
	 * Searches for the instruction set (needle) inside an another instruction set (haystack) and returns the first instruction node from the found needle.
	 * @param  haystack The instruction set to be searched in
	 * @param  needle   The instruction set to search for
	 * @return          The first instruction node from the haystack on the found position
	 * @throws          de.sanandrew.core.manpack.transformer.ASMHelper.InvalidNeedleException when the needle was not found or was found multiple times
	 */
	public static AbstractInsnNode findFirstNodeFromNeedle(final InsnList haystack, final InsnList needle) {
		final List<AbstractInsnNode> ret = InstructionComparator.insnListFindStart(haystack, needle);

		if (ret.size() != 1) {
			throw new InvalidNeedleException(ret.size());
		}

		return ret.get(0);
	}

	/**
	 * Searches for the instruction set (needle) inside an another instruction set (haystack) and returns the last instruction node from the found needle.
	 * @param  haystack The instruction set to be searched in
	 * @param  needle   The instruction set to search for
	 * @return          The last instruction node from the haystack on the found position
	 * @throws          de.sanandrew.core.manpack.transformer.ASMHelper.InvalidNeedleException when the needle was not found or was found multiple times
	 */
	public static AbstractInsnNode findLastNodeFromNeedle(final InsnList haystack, final InsnList needle) {
		final List<AbstractInsnNode> ret = InstructionComparator.insnListFindEnd(haystack, needle);

		if (ret.size() != 1) {
			throw new InvalidNeedleException(ret.size());
		}

		return ret.get(0);
	}

	/**
	 * Scans the ClassNode for a method name
	 * @param  cn     the ClassNode to be searched in
	 * @param  method The method name to search for
	 * @return        true, if the name was found, or else false
	 */
	public static boolean hasClassMethod(final ClassNode cn, final String method) {
		final Triple<String, String, String[]> methodDesc = ASMNames.getSrgNameMd(method);

		for (final MethodNode methodNd : cn.methods) {
			if (methodNd.name.equals(methodDesc.getMiddle()) && methodNd.desc.equals(methodDesc.getRight()[0])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Scans the ClassNode for a method name and descriptor
	 * @param  cnode the ClassNode to be searched in
	 * @param  name  The method name to search for
	 * @param  desc  The method descriptor to search for
	 * @return       true, if the name was found, or else false
	 * @throws       de.sanandrew.core.manpack.transformer.ASMHelper.MethodNotFoundException when the method name and descriptor couldn't be found
	 */
	private static MethodNode findMethodNode(final ClassNode cnode, final String name, final String desc) {
		for (final MethodNode mnode : cnode.methods) {
			if (name.equals(mnode.name) && desc.equals(mnode.desc)) {
				return mnode;
			}
		}
		throw new MethodNotFoundException(name, desc);
	}

	/**
	 * Gets the appropriate field/method name for either it is called in a development or productive environment
	 * @param  mcp The name to be used in a development environment
	 * @param  srg The name to be used in a productive environment
	 * @return     The right name appropriate to the environment
	 */
	public static String getRemappedMF(final String mcp, final String srg) {
		if (ASMHelper.isMCP) {
			return mcp;
		}
		return srg;
	}

	/**
	 * removes an entire instruction set in the haystack.
	 * @param haystack The instruction set to be searched in
	 * @param needle   The instruction set to search for and to be removed
	 */
	public static void removeNeedleFromHaystack(final InsnList haystack, final InsnList needle) {
		final int firstInd = haystack.indexOf(findFirstNodeFromNeedle(haystack, needle));
		final int lastInd = haystack.indexOf(findLastNodeFromNeedle(haystack, needle));
		final List<AbstractInsnNode> realNeedle = new ArrayList<>();

		for (int i = firstInd; i <= lastInd; i++) {
			realNeedle.add(haystack.get(i));
		}

		for (final AbstractInsnNode node : realNeedle) {
			haystack.remove(node);
		}
	}

	/**
	 * Writes the class bytes into a file. Helpful for debugging ASM transformations.<br>
	 * Note: this will write the bytes as compiled .class file! Use JD-GUI to look into the class.
	 * @param classBytes The class bytes to be written as a class file
	 * @param file       The filename (inclusive path) the bytes will be saved in
	 */
	public static void writeClassToFile(final byte[] classBytes, final String file) {
		try (FileOutputStream out = new FileOutputStream(file)) {
			out.write(classBytes);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
	}

	public static MethodNode getMethodNode(final int access, final String method) {
		final Triple<String, String, String[]> methodDesc = ASMNames.getSrgNameMd(method);
		final String sig = methodDesc.getRight().length > 1 ? methodDesc.getRight()[1] : null;
		final String throwing[] = methodDesc.getRight().length > 2 ? methodDesc.getRight()[2].split(";") : null;

		return new MethodNode(access, methodDesc.getMiddle(), methodDesc.getRight()[0], sig, throwing);
	}

	public static MethodInsnNode getMethodInsnNode(final int opcode, final String method, final boolean intf) {
		final Triple<String, String, String[]> methodDesc = ASMNames.getSrgNameMd(method);

		return new MethodInsnNode(opcode, methodDesc.getLeft(), methodDesc.getMiddle(), methodDesc.getRight()[0], intf);
	}

	public static void visitMethodInsn(final MethodNode node, final int opcode, final String method, final boolean intf) {
		final MethodInsnNode mdiNode = getMethodInsnNode(opcode, method, intf);
		node.visitMethodInsn(opcode, mdiNode.owner, mdiNode.name, mdiNode.desc, intf);
	}

	public static MethodNode findMethod(final ClassNode clazz, final String method) {
		final Triple<String, String, String[]> methodDesc = ASMNames.getSrgNameMd(method);

		return findMethodNode(clazz, methodDesc.getMiddle(), methodDesc.getRight()[0]);
	}

	public static FieldInsnNode getFieldInsnNode(final int opcode, final String field) {
		final Triple<String, String, String> fieldDesc = ASMNames.getSrgNameFd(field);

		return new FieldInsnNode(opcode, fieldDesc.getLeft(), fieldDesc.getMiddle(), fieldDesc.getRight());
	}

	public static void visitFieldInsn(final MethodNode node, final int opcode, final String field) {
		final FieldInsnNode fdiNode = getFieldInsnNode(opcode, field);
		node.visitFieldInsn(opcode, fdiNode.owner, fdiNode.name, fdiNode.desc);
	}

	public static class InvalidNeedleException extends RuntimeException {
		private static final long serialVersionUID = -913530798954926801L;

		public InvalidNeedleException(final int count) {
			super(count > 1 ? "Multiple Needles found in Haystack!" : count < 1 ? "Needle not found in Haystack!" : "Wait, Needle was found!? o.O");
		}
	}

	public static class MethodNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 7439846361566319105L;

		public MethodNotFoundException(final String methodName, final String methodDesc) {
			super(String.format("Could not find any method matching the name < %s > and description < %s >", methodName, methodDesc));
		}
	}
}