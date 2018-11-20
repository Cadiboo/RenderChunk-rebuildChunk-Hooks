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
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH&J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\nH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"},
   d2 = {"Lmods/octarinecore/client/resource/GeneratorBase;", "", "domain", "", "(Ljava/lang/String;)V", "getDomain", "()Ljava/lang/String;", "getInputStream", "Ljava/io/InputStream;", "location", "Lnet/minecraft/util/ResourceLocation;", "resourceExists", "", "BetterFoliage-MC1.12"}
)
public abstract class GeneratorBase {
   @NotNull
   private final String domain;

   public abstract boolean resourceExists(@Nullable ResourceLocation var1);

   @Nullable
   public abstract InputStream getInputStream(@Nullable ResourceLocation var1);

   @NotNull
   public final String getDomain() {
      return this.domain;
   }

   public GeneratorBase(@NotNull String domain) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super();
      this.domain = domain;
   }
}
