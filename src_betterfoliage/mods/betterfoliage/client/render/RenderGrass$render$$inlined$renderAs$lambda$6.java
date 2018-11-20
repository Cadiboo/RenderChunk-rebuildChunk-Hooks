package mods.betterfoliage.client.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import mods.betterfoliage.client.texture.GrassInfo;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RenderVertex;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.client.render.Vertex;
import net.minecraft.client.renderer.BufferBuilder;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\n¢\u0006\u0002\b\f"},
   d2 = {"<anonymous>", "", "Lmods/octarinecore/client/render/RenderVertex;", "ctx", "Lmods/octarinecore/client/render/ShadingContext;", "<anonymous parameter 1>", "", "<anonymous parameter 2>", "Lmods/octarinecore/client/render/Quad;", "<anonymous parameter 3>", "<anonymous parameter 4>", "Lmods/octarinecore/client/render/Vertex;", "invoke"}
)
final class RenderGrass$render$$inlined$renderAs$lambda$6 extends Lambda implements Function6 {
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;
   // $FF: synthetic field
   final List $isHidden$inlined;
   // $FF: synthetic field
   final GrassInfo $grassInfo$inlined;
   // $FF: synthetic field
   final boolean $isSnowed$inlined;
   // $FF: synthetic field
   final int $blockColor$inlined;

   RenderGrass$render$$inlined$renderAs$lambda$6(BufferBuilder var1, List var2, GrassInfo var3, boolean var4, int var5) {
      super(6);
      this.$renderer$inlined = var1;
      this.$isHidden$inlined = var2;
      this.$grassInfo$inlined = var3;
      this.$isSnowed$inlined = var4;
      this.$blockColor$inlined = var5;
   }

   public final void invoke(@NotNull RenderVertex $receiver, @NotNull ShadingContext ctx, int $noName_2, @NotNull Quad $noName_3, int $noName_4, @NotNull Vertex $noName_5) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull($noName_3, "<anonymous parameter 2>");
      Intrinsics.checkParameterIsNotNull($noName_5, "<anonymous parameter 4>");
      int n$iv = 2;
      double t$iv;
      switch(n$iv % 4) {
      case 1:
         t$iv = $receiver.getV();
         $receiver.setV(-$receiver.getU());
         $receiver.setU(t$iv);
         break;
      case 2:
         $receiver.setU(-$receiver.getU());
         $receiver.setV(-$receiver.getV());
         break;
      case 3:
         t$iv = -$receiver.getV();
         $receiver.setV($receiver.getU());
         $receiver.setU(t$iv);
      }

      if (this.$isSnowed$inlined) {
         if (!ctx.getAoEnabled()) {
            float level$iv = 1.4F;
            float grey$iv = Math.min(($receiver.getRed() + $receiver.getGreen() + $receiver.getBlue()) * 0.333F * level$iv, 1.0F);
            $receiver.setRed(grey$iv);
            $receiver.setGreen(grey$iv);
            $receiver.setBlue(grey$iv);
         }
      } else if (ctx.getAoEnabled() && this.$grassInfo$inlined.getOverrideColor() == null) {
         int color$iv = this.$blockColor$inlined;
         $receiver.setRed($receiver.getRed() * ((float)(color$iv >> 16 & 255) / 256.0F));
         $receiver.setGreen($receiver.getGreen() * ((float)(color$iv >> 8 & 255) / 256.0F));
         $receiver.setBlue($receiver.getBlue() * ((float)(color$iv & 255) / 256.0F));
      }

   }
}
