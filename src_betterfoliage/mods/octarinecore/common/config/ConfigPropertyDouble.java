package mods.octarinecore.common.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J(\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\u0011\u0010\u0013\u001a\u00020\u0002*\u00020\fH\u0016¢\u0006\u0002\u0010\u0014J\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0002H\u0016R\u0011\u0010\u0005\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0018"},
   d2 = {"Lmods/octarinecore/common/config/ConfigPropertyDouble;", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "", "min", "max", "default", "(DDD)V", "getDefault", "()D", "getMax", "getMin", "resolve", "Lnet/minecraftforge/common/config/Property;", "kotlin.jvm.PlatformType", "target", "Lnet/minecraftforge/common/config/Configuration;", "category", "", "name", "read", "(Lnet/minecraftforge/common/config/Property;)Ljava/lang/Double;", "write", "", "value", "BetterFoliage-MC1.12"}
)
public final class ConfigPropertyDouble extends ConfigPropertyDelegate {
   private final double min;
   private final double max;
   private final double default;

   public Property resolve(@NotNull Configuration target, @NotNull String category, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(category, "category");
      Intrinsics.checkParameterIsNotNull(name, "name");
      Property var4 = target.get(category, name, this.default, (String)null);
      var4.setMinValue(this.min);
      var4.setMaxValue(this.max);
      return var4;
   }

   @NotNull
   public Double read(@NotNull Property $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000.getDouble();
   }

   public void write(@NotNull Property $receiver, double value) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      var10000.set(value);
   }

   public final double getMin() {
      return this.min;
   }

   public final double getMax() {
      return this.max;
   }

   public final double getDefault() {
      return this.default;
   }

   public ConfigPropertyDouble(double min, double max, double var5) {
      this.min = min;
      this.max = max;
      this.default = var5;
   }
}
