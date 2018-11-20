package mods.octarinecore.metaprog;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00020\u0014\"\u00020\u0007J\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00190\u0018J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001a\u001a\u00020\u0007J$\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001b\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00190\u0018J\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001d\u001a\u00020\u001eJ\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020\u0012J\"\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u0007J#\u0010%\u001a\u00020&*\u00020\u00162\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00120\u0018¢\u0006\u0002\b(J#\u0010)\u001a\u00020&*\u00020\u00162\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00120\u0018¢\u0006\u0002\b(J\u0016\u0010*\u001a\u00020\u0012*\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160+J/\u0010,\u001a\u00020\u0012*\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00160+2\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00120\u0018¢\u0006\u0002\b(J#\u0010,\u001a\u00020&*\u00020\u00162\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00120\u0018¢\u0006\u0002\b(R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006-"},
   d2 = {"Lmods/octarinecore/metaprog/MethodTransformContext;", "", "method", "Lorg/objectweb/asm/tree/MethodNode;", "environment", "Lmods/octarinecore/metaprog/Namespace;", "writerFlags", "", "(Lorg/objectweb/asm/tree/MethodNode;Lmods/octarinecore/metaprog/Namespace;I)V", "getEnvironment", "()Lmods/octarinecore/metaprog/Namespace;", "getMethod", "()Lorg/objectweb/asm/tree/MethodNode;", "getWriterFlags", "()I", "setWriterFlags", "(I)V", "applyWriterFlags", "", "flagValue", "", "find", "Lorg/objectweb/asm/tree/AbstractInsnNode;", "predicate", "Lkotlin/Function1;", "", "opcode", "start", "invokeName", "name", "", "invokeRef", "ref", "Lmods/octarinecore/metaprog/MethodRef;", "makePublic", "varinsn", "idx", "insertAfter", "Lmods/octarinecore/metaprog/InstructionList;", "init", "Lkotlin/ExtensionFunctionType;", "insertBefore", "remove", "Lkotlin/Pair;", "replace", "BetterFoliage-MC1.12"}
)
public final class MethodTransformContext {
   @NotNull
   private final MethodNode method;
   @NotNull
   private final Namespace environment;
   private int writerFlags;

   public final void applyWriterFlags(@NotNull int... flagValue) {
      Intrinsics.checkParameterIsNotNull(flagValue, "flagValue");
      int[] $receiver$iv = flagValue;
      int var3 = flagValue.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int element$iv = $receiver$iv[var4];
         this.writerFlags |= element$iv;
      }

   }

   public final void makePublic() {
      this.method.access = (this.method.access | 1) & ~6;
   }

   @Nullable
   public final AbstractInsnNode find(@NotNull AbstractInsnNode start, @NotNull Function1 predicate) {
      Intrinsics.checkParameterIsNotNull(start, "start");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");

      AbstractInsnNode current;
      for(current = start; current != null && !((Boolean)predicate.invoke(current)).booleanValue(); current = current.getNext()) {
         ;
      }

      return current;
   }

   @Nullable
   public final AbstractInsnNode find(@NotNull Function1 predicate) {
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      InsnList var10001 = this.method.instructions;
      Intrinsics.checkExpressionValueIsNotNull(this.method.instructions, "method.instructions");
      AbstractInsnNode var2 = var10001.getFirst();
      Intrinsics.checkExpressionValueIsNotNull(var2, "method.instructions.first");
      return this.find(var2, predicate);
   }

   @Nullable
   public final AbstractInsnNode find(final int opcode) {
      return this.find((Function1)(new Function1() {
         public final boolean invoke(@NotNull AbstractInsnNode it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return it.getOpcode() == opcode;
         }
      }));
   }

   @NotNull
   public final InstructionList insertAfter(@NotNull AbstractInsnNode $receiver, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(init, "init");
      InstructionList var3 = new InstructionList(this.environment);
      init.invoke(var3);
      Iterable $receiver$iv = (Iterable)CollectionsKt.reversed((Iterable)var3.getList());
      Iterator var6 = $receiver$iv.iterator();

      while(var6.hasNext()) {
         Object element$iv = var6.next();
         AbstractInsnNode it = (AbstractInsnNode)element$iv;
         this.method.instructions.insert($receiver, it);
      }

      return var3;
   }

   @NotNull
   public final InstructionList insertBefore(@NotNull AbstractInsnNode $receiver, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(init, "init");
      InstructionList var3 = new InstructionList(this.environment);
      AbstractInsnNode insertBeforeNode = $receiver;
      init.invoke(var3);
      Iterable $receiver$iv = (Iterable)var3.getList();
      Iterator var7 = $receiver$iv.iterator();

      while(var7.hasNext()) {
         Object element$iv = var7.next();
         AbstractInsnNode it = (AbstractInsnNode)element$iv;
         this.method.instructions.insertBefore(insertBeforeNode, it);
      }

      return var3;
   }

   @NotNull
   public final InstructionList replace(@NotNull AbstractInsnNode $receiver, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(init, "init");
      InstructionList var3 = new InstructionList(this.environment);
      this.insertAfter($receiver, init);
      this.method.instructions.remove($receiver);
      return var3;
   }

   public final void remove(@NotNull Pair $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");

      AbstractInsnNode current;
      AbstractInsnNode next;
      for(current = (AbstractInsnNode)$receiver.getFirst(); current != null && Intrinsics.areEqual(current, (AbstractInsnNode)$receiver.getSecond()) ^ true; current = next) {
         next = current.getNext();
         this.method.instructions.remove(current);
      }

      if (current != null) {
         this.method.instructions.remove(current);
      }

   }

   public final void replace(@NotNull Pair $receiver, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(init, "init");
      AbstractInsnNode beforeInsn = ((AbstractInsnNode)$receiver.getFirst()).getPrevious();
      this.remove($receiver);
      Intrinsics.checkExpressionValueIsNotNull(beforeInsn, "beforeInsn");
      this.insertAfter(beforeInsn, init);
   }

   @NotNull
   public final Function1 varinsn(final int opcode, final int idx) {
      return (Function1)(new Function1() {
         public final boolean invoke(@NotNull AbstractInsnNode insn) {
            Intrinsics.checkParameterIsNotNull(insn, "insn");
            return insn.getOpcode() == opcode && insn instanceof VarInsnNode && ((VarInsnNode)insn).var == idx;
         }
      });
   }

   @NotNull
   public final Function1 invokeName(@NotNull final String name) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      return (Function1)(new Function1() {
         public final boolean invoke(@NotNull AbstractInsnNode insn) {
            Intrinsics.checkParameterIsNotNull(insn, "insn");
            AbstractInsnNode var10000 = insn;
            if (!(insn instanceof MethodInsnNode)) {
               var10000 = null;
            }

            return Intrinsics.areEqual((MethodInsnNode)var10000 != null ? ((MethodInsnNode)var10000).name : null, name);
         }
      });
   }

   @NotNull
   public final Function1 invokeRef(@NotNull final MethodRef ref) {
      Intrinsics.checkParameterIsNotNull(ref, "ref");
      return (Function1)(new Function1() {
         public final boolean invoke(@NotNull AbstractInsnNode insn) {
            Intrinsics.checkParameterIsNotNull(insn, "insn");
            AbstractInsnNode var10000 = insn;
            if (!(insn instanceof MethodInsnNode)) {
               var10000 = null;
            }

            MethodInsnNode var5 = (MethodInsnNode)var10000;
            boolean var6;
            if (var5 != null) {
               MethodInsnNode var2 = var5;
               var6 = Intrinsics.areEqual(var2.name, ref.name(MethodTransformContext.this.getEnvironment())) && Intrinsics.areEqual(var2.owner, StringsKt.replace$default(ref.getParentClass().getName(), ".", "/", false, 4, (Object)null));
            } else {
               var6 = false;
            }

            return var6;
         }
      });
   }

   @NotNull
   public final MethodNode getMethod() {
      return this.method;
   }

   @NotNull
   public final Namespace getEnvironment() {
      return this.environment;
   }

   public final int getWriterFlags() {
      return this.writerFlags;
   }

   public final void setWriterFlags(int var1) {
      this.writerFlags = var1;
   }

   public MethodTransformContext(@NotNull MethodNode method, @NotNull Namespace environment, int writerFlags) {
      Intrinsics.checkParameterIsNotNull(method, "method");
      Intrinsics.checkParameterIsNotNull(environment, "environment");
      super();
      this.method = method;
      this.environment = environment;
      this.writerFlags = writerFlags;
   }
}
