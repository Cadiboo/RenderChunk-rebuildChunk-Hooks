package mods.octarinecore.common.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u001a\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003\"\u0006\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001f\u0010\u0002\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"},
   d2 = {"Lmods/octarinecore/common/config/SimpleBlockMatcher;", "Lmods/octarinecore/common/config/IBlockMatcher;", "classes", "", "Ljava/lang/Class;", "([Ljava/lang/Class;)V", "getClasses", "()[Ljava/lang/Class;", "[Ljava/lang/Class;", "matchesClass", "", "block", "Lnet/minecraft/block/Block;", "matchingClass", "BetterFoliage-MC1.12"}
)
public final class SimpleBlockMatcher implements IBlockMatcher {
   @NotNull
   private final Class[] classes;

   public boolean matchesClass(@NotNull Block block) {
      Intrinsics.checkParameterIsNotNull(block, "block");
      return this.matchingClass(block) != null;
   }

   @Nullable
   public Class matchingClass(@NotNull Block block) {
      Intrinsics.checkParameterIsNotNull(block, "block");
      Class blockClass = block.getClass();
      Object[] $receiver$iv = (Object[])this.classes;
      int var4 = $receiver$iv.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object element$iv = $receiver$iv[var5];
         Class it = (Class)element$iv;
         if (it.isAssignableFrom(blockClass)) {
            return it;
         }
      }

      return null;
   }

   @NotNull
   public final Class[] getClasses() {
      return this.classes;
   }

   public SimpleBlockMatcher(@NotNull Class... classes) {
      Intrinsics.checkParameterIsNotNull(classes, "classes");
      super();
      this.classes = classes;
   }
}
