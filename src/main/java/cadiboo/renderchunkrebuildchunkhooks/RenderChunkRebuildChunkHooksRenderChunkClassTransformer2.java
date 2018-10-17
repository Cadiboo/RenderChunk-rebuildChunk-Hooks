package cadiboo.renderchunkrebuildchunkhooks;

import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.launchwrapper.IClassTransformer;

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer2 implements IClassTransformer {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int FLAGS = 0;

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (!name.equals("net.minecraft.client.renderer.chunk.RenderChunk") && !name.equals("cws")) {
			return basicClass;
		}

		final ClassReader classReader = new ClassReader(basicClass);
		final ClassWriter classWriter = new ClassWriter(classReader, FLAGS);
		final ClassVisitor classVisitor = new RebuildChunkHooksClassVisitor(classWriter);

		try {
			classReader.accept(classVisitor, 0);
			return classWriter.toByteArray();
		} catch (final Exception e) {
			// TODO: handle exception
		}

		return basicClass;
	}

	public class RebuildChunkHooksClassVisitor extends ClassVisitor {

		public final Type	REBUILD_CHUNK_TYPE			= Type.getMethodType(Type.VOID_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)));
		public final String	REBUILD_CHUNK_DESCRIPTOR	= this.REBUILD_CHUNK_TYPE.getDescriptor();

		public RebuildChunkHooksClassVisitor(final ClassVisitor classVisitor) {
			super(Opcodes.ASM5, classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
			final MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

			if (!name.equals("rebuildChunk") && !name.equals("func_178581_b")) {
				return originalVisitor;
			}

			return new RebuildChunkHooksMethodVisitor(originalVisitor);

		}

	}

	public class RebuildChunkHooksMethodVisitor extends MethodVisitor {

		public final Type	VIS_GRAPH_TYPE			= Type.getObjectType(Type.getInternalName(VisGraph.class));
		public final String	VIS_GRAPH_INTERNAL_NAME	= Type.getInternalName(VisGraph.class);

		private boolean shouldInjectHook;

		public RebuildChunkHooksMethodVisitor(final MethodVisitor mv) {
			super(Opcodes.ASM5, mv);
		}

		// problem, because its in a finally block getLock().unlock() exists once in the source code & multiple times in the bytecode
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//		Label l16 = new Label();
//		mv.visitLabel(l16);
//		mv.visitLineNumber(145, l16);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//		mv.visitVarInsn(ALOAD, 4);
//		mv.visitVarInsn(ALOAD, 5);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//		mv.visitVarInsn(FLOAD, 1);
//		mv.visitVarInsn(FLOAD, 2);
//		mv.visitVarInsn(FLOAD, 3);
//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false);
//		Label l17 = new Label();
//		mv.visitJumpInsn(IFNE, l17);
//		mv.visitInsn(RETURN);
//		mv.visitLabel(l17);
//		mv.visitLineNumber(147, l17);
//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//		mv.visitInsn(DUP);

		// problem, because its in a finally block getLock().unlock() exists once in the source code & multiple times in the bytecode
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//		Label l16 = new Label();
//		mv.visitLabel(l16);
//		mv.visitLineNumber(146, l16);
//		mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "dummyEventBoolean", "()Z", false);
//		Label l17 = new Label();
//		mv.visitJumpInsn(IFNE, l17);
//		mv.visitInsn(RETURN);
//		mv.visitLabel(l17);
//		mv.visitLineNumber(148, l17);
//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//		mv.visitInsn(DUP);

		// problem, because its in a finally block getLock().unlock() exists once in the source code & multiple times in the bytecode
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//		Label l16 = new Label();
//		mv.visitLabel(l16);
//		mv.visitLineNumber(147, l16);
//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//		mv.visitInsn(DUP);

		@Override
		public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean intf) {
			super.visitMethodInsn(opcode, owner, name, desc, intf);

//			if(this.mv.ins)
//
//			if ((opcode == Opcodes.INVOKEVIRTUAL) && owner.equals("java/util/concurrent/locks/ReentrantLock") && name.equals("unlock") && desc.equals("()V") && (intf == false)) {
//
//				final Label l16 = new Label();
//				this.visitLabel(l16);
//				this.visitLineNumber(146, l16);
//				this.visitMethodInsn(Opcodes.INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "dummyEventBoolean", "()Z", false);
//				final Label l17 = new Label();
//				this.visitJumpInsn(Opcodes.IFNE, l17);
//				this.visitInsn(Opcodes.RETURN);
//				this.visitLabel(l17);
//				this.visitLineNumber(148, l17);
//				this.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//
//			}
		}

		public final String HOOKS_CLASS_INTERNAL_NAME = Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);

		@Override
		public void visitTypeInsn(final int opcode, final String type) {

			if ((opcode == Opcodes.NEW) && type.equals(this.VIS_GRAPH_INTERNAL_NAME)) {

				this.visitMethodInsn(Opcodes.INVOKESTATIC, this.HOOKS_CLASS_INTERNAL_NAME, "dummyEventBoolean", "()Z", false);
				final Label l17 = new Label();
				this.visitJumpInsn(Opcodes.IFEQ, l17); // should be IFNE but... its inverted???
				this.visitInsn(Opcodes.RETURN);
				this.visitLabel(l17);
				this.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

			}
			super.visitTypeInsn(opcode, type);

			RenderChunkRebuildChunkHooksHooks.dummyEventVoid();
		}

//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");

		@Override
		public void visitLdcInsn(final Object cst) {

//			NEW net/minecraft/client/renderer/chunk/VisGraph

//			if ("prepareterrain".equals(cst)) {
//				this.shouldInjectHook = true;
//			}
			super.visitLdcInsn(cst);
		}
	}

}
