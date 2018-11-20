package mods.octarinecore.common.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"},
   d2 = {"Lmods/octarinecore/common/config/ObsoleteConfigProperty;", "Lmods/octarinecore/common/config/ConfigPropertyBase;", "()V", "guiProperties", "", "Lnet/minecraftforge/common/config/Property;", "getGuiProperties", "()Ljava/util/List;", "hasChanged", "", "getHasChanged", "()Z", "attach", "", "target", "Lnet/minecraftforge/common/config/Configuration;", "langPrefix", "", "categoryName", "propertyName", "BetterFoliage-MC1.12"}
)
public final class ObsoleteConfigProperty extends ConfigPropertyBase {
   @NotNull
   private final List guiProperties = CollectionsKt.emptyList();

   public void attach(@NotNull Configuration target, @NotNull String langPrefix, @NotNull String categoryName, @NotNull String propertyName) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(langPrefix, "langPrefix");
      Intrinsics.checkParameterIsNotNull(categoryName, "categoryName");
      Intrinsics.checkParameterIsNotNull(propertyName, "propertyName");
      ConfigCategory var10000 = target.getCategory(categoryName);
      if (var10000 != null) {
         Property var5 = (Property)var10000.remove(propertyName);
      }

   }

   @NotNull
   public List getGuiProperties() {
      return this.guiProperties;
   }

   public boolean getHasChanged() {
      return false;
   }
}
