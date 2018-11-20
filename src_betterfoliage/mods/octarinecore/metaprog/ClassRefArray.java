package mods.octarinecore.metaprog;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u000e\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tH\u0016¨\u0006\n"},
   d2 = {"Lmods/octarinecore/metaprog/ClassRefArray;", "Lmods/octarinecore/metaprog/ClassRef;", "name", "", "(Ljava/lang/String;)V", "asmDescriptor", "namespace", "Lmods/octarinecore/metaprog/Namespace;", "resolve", "Ljava/lang/Class;", "BetterFoliage-MC1.12"}
)
public final class ClassRefArray extends ClassRef {
   @NotNull
   public String asmDescriptor(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      return "[" + super.asmDescriptor(namespace);
   }

   @Nullable
   public Class resolve() {
      return Reflection.getJavaClass("[L" + this.getName() + ';');
   }

   public ClassRefArray(@NotNull String name) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      super(name);
   }
}
