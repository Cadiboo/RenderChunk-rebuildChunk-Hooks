package cadiboo.renderchunkrebuildchunkhooks;

import org.apache.commons.logging.Log;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

public class RenderChunkRebuildChunkHooksRenderChunkClassVisitor extends ClassVisitor {

	private final MethodMatcher modifiedMethod;

	public RenderChunkRebuildChunkHooksRenderChunkClassVisitor(final ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
		final Type injectedMethodType = Type.getMethodType(Type.VOID_TYPE, Type.INT_TYPE, Type.FLOAT_TYPE, Type.LONG_TYPE);
		this.modifiedMethod = new MethodMatcher(rendererTypeCls, injectedMethodType.getDescriptor(), "renderWorldPass", "func_175068_a");
	}

	@Override
	public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
		final MethodVisitor parent = super.visitMethod(access, name, desc, signature, exceptions);
		return this.modifiedMethod.match(name, desc) ? new InjectorMethodVisitor(parent) : parent;
	}

	private class InjectorMethodVisitor extends MethodVisitor {

		private final Type hookCls;

		private final Method hookMethod;

		private boolean isPrimed;

		public InjectorMethodVisitor(final MethodVisitor mv) {
			super(Opcodes.ASM5, mv);

			try {
				this.hookCls = Type.getType(PreWorldRenderHookVisitor.class);
				this.hookMethod = Method.getMethod(PreWorldRenderHookVisitor.class.getMethod("callHook"));
			} catch (final Throwable t) {
				throw new RuntimeException(t);
			}

			Log.debug("Injecting hook %s.%s into EntityRenderer.renderWorldPass", PreWorldRenderHookVisitor.class, this.hookMethod);
		}

		@Override
		public void visitLdcInsn(final Object cst) {
			if ("prepareterrain".equals(cst)) {
				this.isPrimed = true;
			}
			super.visitLdcInsn(cst);
		}

		@Override
		public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean intf) {
			super.visitMethodInsn(opcode, owner, name, desc, intf);

			if (this.isPrimed) {
				this.isPrimed = false;

				this.visitMethodInsn(Opcodes.INVOKESTATIC, this.hookCls.getInternalName(), this.hookMethod.getName(), this.hookMethod.getDescriptor(), false);
				listener.onSuccess();
				active = true;
			}
		}
	}

}
