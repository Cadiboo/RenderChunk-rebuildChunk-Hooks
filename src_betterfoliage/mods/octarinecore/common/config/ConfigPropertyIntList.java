package mods.octarinecore.common.config;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002*\u00020\nH\u0016¢\u0006\u0002\u0010\u0012J\u001f\u0010\u0013\u001a\u00020\u0014*\u00020\n2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016¢\u0006\u0002\u0010\u0016R\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0017"},
   d2 = {"Lmods/octarinecore/common/config/ConfigPropertyIntList;", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "", "", "defaults", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "getDefaults", "()Lkotlin/jvm/functions/Function0;", "resolve", "Lnet/minecraftforge/common/config/Property;", "kotlin.jvm.PlatformType", "target", "Lnet/minecraftforge/common/config/Configuration;", "category", "", "name", "read", "(Lnet/minecraftforge/common/config/Property;)[Ljava/lang/Integer;", "write", "", "value", "(Lnet/minecraftforge/common/config/Property;[Ljava/lang/Integer;)V", "BetterFoliage-MC1.12"}
)
public final class ConfigPropertyIntList extends ConfigPropertyDelegate {
   @NotNull
   private final Function0 defaults;

   public Property resolve(@NotNull Configuration target, @NotNull String category, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(category, "category");
      Intrinsics.checkParameterIsNotNull(name, "name");
      return target.get(category, name, ArraysKt.toIntArray((Integer[])this.defaults.invoke()), (String)null);
   }

   @NotNull
   public Integer[] read(@NotNull Property $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      int[] var2 = var10000.getIntList();
      Intrinsics.checkExpressionValueIsNotNull(var2, "property!!.intList");
      return ArraysKt.toTypedArray(var2);
   }

   public void write(@NotNull Property $receiver, @NotNull Integer[] value) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(value, "value");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      var10000.set(ArraysKt.toIntArray(value));
   }

   @NotNull
   public final Function0 getDefaults() {
      return this.defaults;
   }

   public ConfigPropertyIntList(@NotNull Function0 defaults) {
      Intrinsics.checkParameterIsNotNull(defaults, "defaults");
      super();
      this.defaults = defaults;
   }
}
