package mods.octarinecore.common.config;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.metaprog.Reflection;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010\t\u001a\u00020\u0005H\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000f"},
   d2 = {"Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "Lmods/octarinecore/common/config/IBlockMatcher;", "Lmods/octarinecore/common/config/BlackWhiteListConfigOption;", "Ljava/lang/Class;", "domain", "", "path", "(Ljava/lang/String;Ljava/lang/String;)V", "convertValue", "line", "matchesClass", "", "block", "Lnet/minecraft/block/Block;", "matchingClass", "BetterFoliage-MC1.12"}
)
public final class ConfigurableBlockMatcher extends BlackWhiteListConfigOption implements IBlockMatcher {
   @Nullable
   public Class convertValue(@NotNull String line) {
      Intrinsics.checkParameterIsNotNull(line, "line");
      return Reflection.getJavaClass(line);
   }

   public boolean matchesClass(@NotNull Block block) {
      Intrinsics.checkParameterIsNotNull(block, "block");
      Class blockClass = block.getClass();
      Iterable $receiver$iv = (Iterable)this.getBlackList();
      Iterator var4 = $receiver$iv.iterator();

      Class it;
      do {
         Object element$iv;
         if (!var4.hasNext()) {
            $receiver$iv = (Iterable)this.getWhiteList();
            var4 = $receiver$iv.iterator();

            do {
               if (!var4.hasNext()) {
                  return false;
               }

               element$iv = var4.next();
               it = (Class)element$iv;
            } while(!it.isAssignableFrom(blockClass));

            return true;
         }

         element$iv = var4.next();
         it = (Class)element$iv;
      } while(!it.isAssignableFrom(blockClass));

      return false;
   }

   @Nullable
   public Class matchingClass(@NotNull Block block) {
      Intrinsics.checkParameterIsNotNull(block, "block");
      Class blockClass = block.getClass();
      Iterable $receiver$iv = (Iterable)this.getBlackList();
      Iterator var4 = $receiver$iv.iterator();

      Class it;
      do {
         Object element$iv;
         if (!var4.hasNext()) {
            $receiver$iv = (Iterable)this.getWhiteList();
            var4 = $receiver$iv.iterator();

            do {
               if (!var4.hasNext()) {
                  return null;
               }

               element$iv = var4.next();
               it = (Class)element$iv;
            } while(!it.isAssignableFrom(blockClass));

            return it;
         }

         element$iv = var4.next();
         it = (Class)element$iv;
      } while(!it.isAssignableFrom(blockClass));

      return null;
   }

   public ConfigurableBlockMatcher(@NotNull String domain, @NotNull String path) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      super(domain, path);
   }
}
