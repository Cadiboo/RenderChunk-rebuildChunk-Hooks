package mods.octarinecore.common.config;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\"\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0007\u001a\"\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0007\u001a \u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0006\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\r\u001a\u001a\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00120\u0011¨\u0006\u0013"},
   d2 = {"boolean", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "default", "", "double", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "min", "", "max", "float", "Lmods/octarinecore/common/config/ConfigPropertyFloat;", "int", "Lmods/octarinecore/common/config/ConfigPropertyInt;", "", "intList", "Lmods/octarinecore/common/config/ConfigPropertyIntList;", "defaults", "Lkotlin/Function0;", "", "BetterFoliage-MC1.12"}
)
public final class DelegatingConfigKt {
   @NotNull
   public static final ConfigPropertyDouble double(double min, double max, double var4) {
      return new ConfigPropertyDouble(min, max, var4);
   }

   @NotNull
   public static final ConfigPropertyFloat float(double min, double max, double var4) {
      return new ConfigPropertyFloat(min, max, var4);
   }

   @NotNull
   public static final ConfigPropertyInt int(int min, int max, int var2) {
      return new ConfigPropertyInt(min, max, var2);
   }

   @NotNull
   public static final ConfigPropertyIntList intList(@NotNull Function0 defaults) {
      Intrinsics.checkParameterIsNotNull(defaults, "defaults");
      return new ConfigPropertyIntList(defaults);
   }

   @NotNull
   public static final ConfigPropertyBoolean boolean(boolean var0) {
      return new ConfigPropertyBoolean(var0);
   }
}
