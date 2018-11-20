package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.Quad;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"},
   d2 = {"<anonymous>", "Lmods/octarinecore/client/render/Quad;", "it", "invoke"}
)
final class RenderReeds$reedModels$1$$special$$inlined$forEach$lambda$1 extends Lambda implements Function1 {
   // $FF: synthetic field
   final Model receiver$0$inlined;
   // $FF: synthetic field
   final int $modelIdx$inlined;

   RenderReeds$reedModels$1$$special$$inlined$forEach$lambda$1(Model var1, int var2) {
      super(1);
      this.receiver$0$inlined = var1;
      this.$modelIdx$inlined = var2;
   }

   @NotNull
   public final Quad invoke(@NotNull Quad it) {
      Intrinsics.checkParameterIsNotNull(it, "it");
      return it.move(Utils.xzDisk(this.$modelIdx$inlined).times(Config.reed.INSTANCE.getHOffset()));
   }
}
