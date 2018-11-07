package cadiboo.renderchunkrebuildchunkhooks.core.util;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class DisableableMethodVisitor extends MethodVisitor {

	public DisableableMethodVisitor(final int api, final MethodVisitor mv) {
		super(api, mv);
	}

	public boolean isEnabled() {
		return true;
	}

	@Override
	public void visitAttribute(final Attribute attr) {
		if (this.isEnabled()) {
			super.visitAttribute(attr);
		}
	}

	@Override
	public void visitCode() {
		if (this.isEnabled()) {
			super.visitCode();
		}
	}

	@Override
	public void visitEnd() {
		if (this.isEnabled()) {
			super.visitEnd();
		}
	}

	@Override
	public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
		if (this.isEnabled()) {
			super.visitFieldInsn(opcode, owner, name, desc);
		}
	}

	@Override
	public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
		if (this.isEnabled()) {
			super.visitFrame(type, nLocal, local, nStack, stack);
		}
	}

	@Override
	public void visitIincInsn(final int var, final int increment) {
		if (this.isEnabled()) {
			super.visitIincInsn(var, increment);
		}
	}

	@Override
	public void visitInsn(final int opcode) {
		if (this.isEnabled()) {
			super.visitInsn(opcode);
		}
	}

	@Override
	public void visitIntInsn(final int opcode, final int operand) {
		if (this.isEnabled()) {
			super.visitIntInsn(opcode, operand);
		}
	}

	@Override
	public void visitInvokeDynamicInsn(final String name, final String desc, final Handle bsm, final Object... bsmArgs) {
		if (this.isEnabled()) {
			super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
		}
	}

	@Override
	public void visitJumpInsn(final int opcode, final Label label) {
		if (this.isEnabled()) {
			super.visitJumpInsn(opcode, label);
		}
	}

	@Override
	public void visitLabel(final Label label) {
		if (this.isEnabled()) {
			super.visitLabel(label);
		}
	}

	@Override
	public void visitLdcInsn(final Object cst) {
		if (this.isEnabled()) {
			super.visitLdcInsn(cst);
		}
	}

	@Override
	public void visitLineNumber(final int line, final Label start) {
		if (this.isEnabled()) {
			super.visitLineNumber(line, start);
		}
	}

	@Override
	public void visitLocalVariable(final String name, final String desc, final String signature, final Label start, final Label end, final int index) {
		if (this.isEnabled()) {
			super.visitLocalVariable(name, desc, signature, start, end, index);
		}
	}

	@Override
	public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
		if (this.isEnabled()) {
			super.visitLookupSwitchInsn(dflt, keys, labels);
		}
	}

	@Override
	public void visitMaxs(final int maxStack, final int maxLocals) {
		if (this.isEnabled()) {
			super.visitMaxs(maxStack, maxLocals);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
		if (this.isEnabled()) {
			super.visitMethodInsn(opcode, owner, name, desc);
		}
	}

	@Override
	public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc, final boolean itf) {
		if (this.isEnabled()) {
			super.visitMethodInsn(opcode, owner, name, desc, itf);
		}
	}

	@Override
	public void visitMultiANewArrayInsn(final String desc, final int dims) {
		if (this.isEnabled()) {
			super.visitMultiANewArrayInsn(desc, dims);
		}
	}

	@Override
	public void visitParameter(final String name, final int access) {
		if (this.isEnabled()) {
			super.visitParameter(name, access);
		}
	}

	@Override
	public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label... labels) {
		if (this.isEnabled()) {
			super.visitTableSwitchInsn(min, max, dflt, labels);
		}
	}

	@Override
	public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String type) {
		if (this.isEnabled()) {
			super.visitTryCatchBlock(start, end, handler, type);
		}
	}

	@Override
	public void visitTypeInsn(final int opcode, final String type) {
		if (this.isEnabled()) {
			super.visitTypeInsn(opcode, type);
		}
	}

	@Override
	public void visitVarInsn(final int opcode, final int var) {
		if (this.isEnabled()) {
			super.visitVarInsn(opcode, var);
		}
	}

}