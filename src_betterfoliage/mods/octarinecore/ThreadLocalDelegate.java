package mods.octarinecore;

import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J$\u0010\r\u001a\u00028\u00002\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0086\u0002¢\u0006\u0002\u0010\u0011J,\u0010\u0012\u001a\u00020\u00132\b\u0010\u000e\u001a\u0004\u0018\u00010\u00022\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0014\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u0015R>\u0010\u0006\u001a&\u0012\f\u0012\n \b*\u0004\u0018\u00018\u00008\u0000 \b*\u0012\u0012\f\u0012\n \b*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00070\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0016"},
   d2 = {"Lmods/octarinecore/ThreadLocalDelegate;", "T", "", "init", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "tlVal", "Ljava/lang/ThreadLocal;", "kotlin.jvm.PlatformType", "getTlVal", "()Ljava/lang/ThreadLocal;", "setTlVal", "(Ljava/lang/ThreadLocal;)V", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "BetterFoliage-MC1.12"}
)
public final class ThreadLocalDelegate {
   private ThreadLocal tlVal;

   public final ThreadLocal getTlVal() {
      return this.tlVal;
   }

   public final void setTlVal(ThreadLocal var1) {
      this.tlVal = var1;
   }

   public final Object getValue(@Nullable Object thisRef, @NotNull KProperty property) {
      Intrinsics.checkParameterIsNotNull(property, "property");
      return this.tlVal.get();
   }

   public final void setValue(@Nullable Object thisRef, @NotNull KProperty property, Object value) {
      Intrinsics.checkParameterIsNotNull(property, "property");
      this.tlVal.set(value);
   }

   public ThreadLocalDelegate(@NotNull Function0 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      super();
      this.tlVal = ThreadLocal.withInitial((Supplier)(new Utils$sam$Supplier$0cd6bf9e(init)));
   }
}
