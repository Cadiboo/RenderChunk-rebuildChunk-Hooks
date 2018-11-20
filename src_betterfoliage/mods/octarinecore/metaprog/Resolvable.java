package mods.octarinecore.metaprog;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000f\u0010\t\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0006R\u001d\u0010\u0004\u001a\u0004\u0018\u00018\u00008FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"},
   d2 = {"Lmods/octarinecore/metaprog/Resolvable;", "T", "", "()V", "element", "getElement", "()Ljava/lang/Object;", "element$delegate", "Lkotlin/Lazy;", "resolve", "BetterFoliage-MC1.12"}
)
public abstract class Resolvable {
   // $FF: synthetic field
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)kotlin.jvm.internal.Reflection.property1(new PropertyReference1Impl(kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(Resolvable.class), "element", "getElement()Ljava/lang/Object;"))};
   @Nullable
   private final Lazy element$delegate = LazyKt.lazy((Function0)(new Function0() {
      @Nullable
      public final Object invoke() {
         return Resolvable.this.resolve();
      }
   }));

   @Nullable
   public abstract Object resolve();

   @Nullable
   public final Object getElement() {
      Lazy var1 = this.element$delegate;
      KProperty var3 = $$delegatedProperties[0];
      return var1.getValue();
   }
}
