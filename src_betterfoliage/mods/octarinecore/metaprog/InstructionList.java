package mods.octarinecore.metaprog;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012J\u0018\u0010\u0013\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\rJ\u0016\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0018"},
   d2 = {"Lmods/octarinecore/metaprog/InstructionList;", "", "environment", "Lmods/octarinecore/metaprog/Namespace;", "(Lmods/octarinecore/metaprog/Namespace;)V", "getEnvironment", "()Lmods/octarinecore/metaprog/Namespace;", "list", "", "Lorg/objectweb/asm/tree/AbstractInsnNode;", "getList", "()Ljava/util/List;", "getField", "", "target", "Lmods/octarinecore/metaprog/FieldRef;", "insn", "opcode", "", "invokeStatic", "Lmods/octarinecore/metaprog/MethodRef;", "isInterface", "varinsn", "idx", "BetterFoliage-MC1.12"}
)
public final class InstructionList {
   @NotNull
   private final List list;
   @NotNull
   private final Namespace environment;

   public final boolean insn(int opcode) {
      return this.list.add(new InsnNode(opcode));
   }

   @NotNull
   public final List getList() {
      return this.list;
   }

   public final boolean varinsn(int opcode, int idx) {
      return this.list.add(new VarInsnNode(opcode, idx));
   }

   public final boolean invokeStatic(@NotNull MethodRef target, boolean isInterface) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      return this.list.add(new MethodInsnNode(184, StringsKt.replace$default(target.getParentClass().getName(), ".", "/", false, 4, (Object)null), target.name(this.environment), target.asmDescriptor(this.environment), isInterface));
   }

   public final boolean getField(@NotNull FieldRef target) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      return this.list.add(new FieldInsnNode(180, StringsKt.replace$default(target.getParentClass().getName(), ".", "/", false, 4, (Object)null), target.name(this.environment), target.asmDescriptor(this.environment)));
   }

   @NotNull
   public final Namespace getEnvironment() {
      return this.environment;
   }

   public InstructionList(@NotNull Namespace environment) {
      Intrinsics.checkParameterIsNotNull(environment, "environment");
      super();
      this.environment = environment;
      ArrayList var3 = new ArrayList();
      this.list = (List)var3;
   }
}
