package mods.octarinecore.client.resource;

import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH&J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\r"},
   d2 = {"Lmods/octarinecore/client/resource/ParameterBasedGenerator;", "Lmods/octarinecore/client/resource/GeneratorBase;", "domain", "", "(Ljava/lang/String;)V", "getInputStream", "Ljava/io/InputStream;", "params", "Lmods/octarinecore/client/resource/ParameterList;", "location", "Lnet/minecraft/util/ResourceLocation;", "resourceExists", "", "BetterFoliage-MC1.12"}
)
public abstract class ParameterBasedGenerator extends GeneratorBase {
   public abstract boolean resourceExists(@NotNull ParameterList var1);

   @Nullable
   public abstract InputStream getInputStream(@NotNull ParameterList var1);

   public boolean resourceExists(@Nullable ResourceLocation location) {
      ParameterList.Companion var10001 = ParameterList.Companion;
      String var10002;
      if (location != null) {
         var10002 = location.func_110623_a();
         if (var10002 != null) {
            return this.resourceExists(var10001.fromString(var10002));
         }
      }

      var10002 = "";
      return this.resourceExists(var10001.fromString(var10002));
   }

   @Nullable
   public InputStream getInputStream(@Nullable ResourceLocation location) {
      ParameterList.Companion var10001 = ParameterList.Companion;
      String var10002;
      if (location != null) {
         var10002 = location.func_110623_a();
         if (var10002 != null) {
            return this.getInputStream(var10001.fromString(var10002));
         }
      }

      var10002 = "";
      return this.getInputStream(var10001.fromString(var10002));
   }

   public ParameterBasedGenerator(@NotNull String domain) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super(domain);
   }
}
