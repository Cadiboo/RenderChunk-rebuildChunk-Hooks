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
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\u0011\u0010\u000f\u001a\u00020\u0002*\u00020\bH\u0016¢\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"},
   d2 = {"Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "", "default", "(Z)V", "getDefault", "()Z", "resolve", "Lnet/minecraftforge/common/config/Property;", "kotlin.jvm.PlatformType", "target", "Lnet/minecraftforge/common/config/Configuration;", "category", "", "name", "read", "(Lnet/minecraftforge/common/config/Property;)Ljava/lang/Boolean;", "write", "", "value", "BetterFoliage-MC1.12"}
)
public final class ConfigPropertyBoolean extends ConfigPropertyDelegate {
   private final boolean default;

   public Property resolve(@NotNull Configuration target, @NotNull String category, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(category, "category");
      Intrinsics.checkParameterIsNotNull(name, "name");
      return target.get(category, name, this.default, (String)null);
   }

   @NotNull
   public Boolean read(@NotNull Property $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000.getBoolean();
   }

   public void write(@NotNull Property $receiver, boolean value) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Property var10000 = this.getProperty();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      var10000.set(value);
   }

   public final boolean getDefault() {
      return this.default;
   }

   public ConfigPropertyBoolean(boolean var1) {
      this.default = var1;
   }
}
