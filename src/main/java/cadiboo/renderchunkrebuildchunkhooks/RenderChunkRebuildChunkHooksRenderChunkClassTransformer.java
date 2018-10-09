package cadiboo.renderchunkrebuildchunkhooks;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer {

	public static final int ALOAD = Opcodes.ALOAD;
	public static final int INVOKESTATIC = Opcodes.INVOKESTATIC;
	public static final int GETFIELD = Opcodes.GETFIELD;
	public static final int FLOAD = Opcodes.FLOAD;
	public static final int INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
	public static final int IFEQ = Opcodes.IFEQ;
	public static final int RETURN = Opcodes.RETURN;
	public static final int F_SAME = Opcodes.F_SAME;
	public static final int GETSTATIC = Opcodes.GETSTATIC;

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (name.equals("net.minecraft.client.renderer.chunk.RenderChunk")) {
			System.out.println("********* INSIDE DEOBFUSCATED RENDER_CHUNK TRANSFORMER ABOUT TO PATCH: " + name);
			final byte[] hookRebuildChunkEvent = this.addRebuildChunkEvent(name, basicClass, true);
			final byte[] hookRebuildChunkBlocksEvent = this.addRebuildChunkBlocksEvent(name, hookRebuildChunkEvent, true);
			return hookRebuildChunkBlocksEvent;
		}
		return basicClass;
	}

	private byte[] addRebuildChunkEvent(final String name, final byte[] bytes, final boolean deobfuscated) {
		final String targetMethodName;
		if (deobfuscated == false) {
			targetMethodName = "NULL";
		} else {
			targetMethodName = "rebuildChunk";
		}

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		final ClassNode classNodeRenderChunk = new ClassNode();
		final ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNodeRenderChunk, 0);

		// loop over all of the methods declared inside the class until we get to the @link{targetMethodName}
		final Iterator<MethodNode> methods = classNodeRenderChunk.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode methodRebuildChunk = methods.next();
			if (!methodRebuildChunk.name.equals(targetMethodName)) {
				continue;
			}
			if (!methodRebuildChunk.desc.equals("(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V")) {
				continue;
			}

			System.out.println("********* Inside target method! " + targetMethodName + " | " + methodRebuildChunk.name);

			AbstractInsnNode targetNode = null;

			// Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
			final Iterator<AbstractInsnNode> iter = methodRebuildChunk.instructions.iterator();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			iter.next();
			while (iter.hasNext()) {
				final AbstractInsnNode currentNode = iter.next();

				// Found it! save the index location of instruction FDIV and the node for this instruction
				if (currentNode.getOpcode() == Opcodes.NEW) {
					targetNode = currentNode.getPrevious().getPrevious();
					break;
				}
			}

			// RENDER CHUNK METHOD ->
			// LINE - CODE
			// 147 - }
			// 148 -
			// 149 - VisGraph lvt_9_1_ = new VisGraph();

			// AFTER ASM
			// LINE - CODE
			// 147 - }
			// 148 - if (net.minecraftforge.client.ForgeHooksClient.onRebuildChunkEvent(field_178589_e, field_189564_r, p_178581_4_, compiledchunk, this.field_178586_f, p_178581_1_, p_178581_2_, p_178581_3_).isCanceled()) return;
			// 149 - VisGraph lvt_9_1_ = new VisGraph();

			//@formatter:off
//			L16
//		    LINENUMBER 145 L16
//		    ALOAD 0
//		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//		    ALOAD 0
//		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//		    ALOAD 4
//		    ALOAD 5
//		    ALOAD 0
//		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//		    FLOAD 1
//		    FLOAD 2
//		    FLOAD 3
//		    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.onRebuildChunkEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Lnet/minecraftforge/client/event/RebuildChunkEvent;
//		    INVOKEVIRTUAL net/minecraftforge/client/event/RebuildChunkEvent.isCanceled()Z
//		    IFEQ L17
//		    RETURN
//		   L17
			//@formatter:on

			System.out.println("********* PATCHING");

			final InsnList toInject = new InsnList();

			// final Label l16 = new Label();
			// toInject.add(new Label(l16));
			// toInject.add(new LineNumberNode(145 + 3, l16));
			// toInject.add(new VarInsnNode(ALOAD, 0);
			// toInject.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
			// toInject.add(new VarInsnNode(ALOAD, 0);
			// toInject.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
			// toInject.add(new VarInsnNode(ALOAD, 4);
			// toInject.add(new VarInsnNode(ALOAD, 5);
			// toInject.add(new VarInsnNode(ALOAD, 0);
			// toInject.add(new FieldInsnNode(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			// toInject.add(new VarInsnNode(FLOAD, 1);
			// toInject.add(new VarInsnNode(FLOAD, 2);
			// toInject.add(new VarInsnNode(FLOAD, 3);
			// toInject.add(new MethodInsnNode(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Lnet/minecraftforge/client/event/RebuildChunkEvent;", false);
			// toInject.add(new MethodInsnNode(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent", "isCanceled", "()Z", false);
			// final Label l17 = new Label();
			// toInject.add(new JumpInsnNode(IFEQ, l17);
			// toInject.add(new InsnNode(RETURN);

			final Label l17 = new Label();
			final LabelNode l17node = new LabelNode(l17);
			toInject.add(l17node);
			toInject.add(new LineNumberNode(148, l17node));
			toInject.add(new FrameNode(F_SAME, 0, null, 0, null));
			toInject.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
			toInject.add(new LdcInsnNode("fuk"));
			toInject.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

			// inject new instruction list into method instruction list
			methodRebuildChunk.instructions.insertBefore(targetNode, toInject);

			System.out.println("********* FINISHED PATCHING");

			// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

			/*
			 * To add new instructions, such as calling a static method can be done like so:
			 *
			 * // make new instruction list InsnList toInject = new InsnList();
			 *
			 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
			 *
			 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
			 */

			// final InsnList toInject = new InsnList();
			// toInject.add(new VarInsnNode(ALOAD, 0));
			// toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));

			break;
		}
		return bytes;
	}

	private byte[] addRebuildChunkBlocksEvent(final String name, final byte[] bytes, final boolean deobfuscated) {
		// TODO Auto-generated method stub
		return bytes;
	}

	public byte[] patchClassASM(final String name, final byte[] bytes, final boolean deobfuscated) {

		String targetMethodName = "";

		// Our target method
		if (deobfuscated == false) {
			targetMethodName = "NULL";
		} else {
			targetMethodName = "rebuildChunk";
		}

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		final ClassNode classNodeRenderChunk = new ClassNode();
		final ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNodeRenderChunk, 0);

		// Now we loop over all of the methods declared inside the Explosion class until we get to the targetMethodName "doExplosionB"

		final Iterator<MethodNode> methods = classNodeRenderChunk.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode methodRebuildChunk = methods.next();
			int fdiv_index = -1;

			// Check if this is doExplosionB and it's method signature is (FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V which means that it accepts a 3 floats (F), and an Object (L) of type ChunkCompileTaskGenerator and returns a void (V)
			if ((methodRebuildChunk.name.equals(targetMethodName) && methodRebuildChunk.desc.equals("(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V"))) {
				System.out.println("********* Inside target method!");

				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				// RENDER CHUNK METHOD ->
				// LINE - CODE
				// 147 - }
				// 148 -
				// 149 - VisGraph lvt_9_1_ = new VisGraph();

				// AFTER ASM
				// LINE - CODE
				// 147 - }
				// 148 - if (net.minecraftforge.client.ForgeHooksClient.onRebuildChunkEvent(field_178589_e, field_189564_r, p_178581_4_, compiledchunk, this.field_178586_f, p_178581_1_, p_178581_2_, p_178581_3_).isCanceled()) return;
				// 149 - VisGraph lvt_9_1_ = new VisGraph();

				// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

				/*
				 * To add new instructions, such as calling a static method can be done like so:
				 *
				 * // make new instruction list InsnList toInject = new InsnList();
				 *
				 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
				 *
				 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
				 */

				final InsnList toInject = new InsnList();
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));

				final Iterator<AbstractInsnNode> iter = methodRebuildChunk.instructions.iterator();

				int index = -1;

				// Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
				while (iter.hasNext()) {
					index++;
					currentNode = iter.next();

					final int FDIV = Opcodes.FDIV;
					// Found it! save the index location of instruction FDIV and the node for this instruction
					if (currentNode.getOpcode() == FDIV) {
						targetNode = currentNode;
						fdiv_index = index;
					}
				}

				// now we want the save nods that load the variable explosionSize and the division instruction:

				/*
				 * mv.visitInsn(FCONST_1); mv.visitVarInsn(ALOAD, 0); mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F"); mv.visitInsn(FDIV); mv.visitInsn(ICONST_0); mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/src/Block", "dropBlockAsItemWithChance", "(Lnet/minecraft/src/World;IIIIFI)V");
				 */

				final AbstractInsnNode remNode1 = methodRebuildChunk.instructions.get(fdiv_index - 2); // mv.visitVarInsn(ALOAD, 0);
				final AbstractInsnNode remNode2 = methodRebuildChunk.instructions.get(fdiv_index - 1); // mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F");
				final AbstractInsnNode remNode3 = methodRebuildChunk.instructions.get(fdiv_index); // mv.visitInsn(FDIV);

				// just remove these nodes from the instruction set, this will prevent the instruction FCONST_1 to be divided.

				methodRebuildChunk.instructions.remove(remNode1);
				methodRebuildChunk.instructions.remove(remNode2);
				methodRebuildChunk.instructions.remove(remNode3);

				// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

				/*
				 * To add new instructions, such as calling a static method can be done like so:
				 *
				 * // make new instruction list InsnList toInject = new InsnList();
				 *
				 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
				 *
				 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
				 */

				System.out.println("Patching Complete!");
				break;
			}
		}

		// ASM specific for cleaning up and returning the final bytes for JVM processing.
		final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNodeRenderChunk.accept(writer);
		return writer.toByteArray();
	}
}