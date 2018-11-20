package mods.octarinecore.common.config;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"},
   d2 = {"modelTextures", "Lmods/octarinecore/common/config/ModelTextureList;", "args", "", "", "([Ljava/lang/String;)Lmods/octarinecore/common/config/ModelTextureList;", "BetterFoliage-MC1.12"}
)
public final class MatchersKt {
   @NotNull
   public static final ModelTextureList modelTextures(@NotNull String... args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      return new ModelTextureList(new ResourceLocation(args[0]), CollectionsKt.drop((Iterable)CollectionsKt.listOf((String[])Arrays.copyOf(args, args.length)), 1));
   }
}
