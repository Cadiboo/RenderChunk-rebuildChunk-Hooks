package mods.octarinecore.metaprog;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u000e\u0010\f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005H\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"},
   d2 = {"Lmods/octarinecore/metaprog/ClassRefPrimitive;", "Lmods/octarinecore/metaprog/ClassRef;", "name", "", "clazz", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "getClazz", "()Ljava/lang/Class;", "asmDescriptor", "namespace", "Lmods/octarinecore/metaprog/Namespace;", "resolve", "BetterFoliage-MC1.12"}
)
public final class ClassRefPrimitive extends ClassRef {
   @Nullable
   private final Class clazz;

   @NotNull
   public String asmDescriptor(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      return this.getName();
   }

   @Nullable
   public Class resolve() {
      return this.clazz;
   }

   @Nullable
   public final Class getClazz() {
      return this.clazz;
   }

   public ClassRefPrimitive(@NotNull String name, @Nullable Class clazz) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      super(name);
      this.clazz = clazz;
   }
}
