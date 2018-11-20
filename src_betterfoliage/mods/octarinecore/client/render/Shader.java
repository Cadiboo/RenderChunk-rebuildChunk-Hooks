package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Rotation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004H&J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u001c\u0010\u0005\u001a\u00020\u0006*\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J\u0014\u0010\u0005\u001a\u00020\u0006*\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J0\u0010\u0005\u001a\u00020\u0006*\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0016¨\u0006\u0015"},
   d2 = {"Lmods/octarinecore/client/render/Shader;", "", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "brightness", "", "color", "shading", "Lmods/octarinecore/client/render/AoData;", "shading1", "shading2", "weight1", "", "weight2", "BetterFoliage-MC1.12"}
)
public interface Shader {
   void shade(@NotNull ShadingContext var1, @NotNull RenderVertex var2);

   @NotNull
   Shader rotate(@NotNull Rotation var1);

   void shade(@NotNull RenderVertex var1, @NotNull AoData var2);

   void shade(@NotNull RenderVertex var1, @NotNull AoData var2, @NotNull AoData var3, float var4, float var5);

   void shade(@NotNull RenderVertex var1, int var2, int var3);

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 3
   )
   public static final class DefaultImpls {
      public static void shade(@NotNull Shader $this, @NotNull RenderVertex $receiver, AoData shading) {
         Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
         Intrinsics.checkParameterIsNotNull(shading, "shading");
         $receiver.setBrightness(shading.getBrightness());
         $receiver.setRed(shading.getRed());
         $receiver.setGreen(shading.getGreen());
         $receiver.setBlue(shading.getBlue());
      }

      public static void shade(@NotNull Shader $this, @NotNull RenderVertex $receiver, @NotNull AoData shading1, AoData shading2, float weight1, float weight2) {
         Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
         Intrinsics.checkParameterIsNotNull(shading1, "shading1");
         Intrinsics.checkParameterIsNotNull(shading2, "shading2");
         $receiver.setRed(Math.min(shading1.getRed() * weight1 + shading2.getRed() * weight2, 1.0F));
         $receiver.setGreen(Math.min(shading1.getGreen() * weight1 + shading2.getGreen() * weight2, 1.0F));
         $receiver.setBlue(Math.min(shading1.getBlue() * weight1 + shading2.getBlue() * weight2, 1.0F));
         $receiver.setBrightness(PixelFormat.brWeighted(shading1.getBrightness(), weight1, shading2.getBrightness(), weight2));
      }

      public static void shade(@NotNull Shader $this, RenderVertex $receiver, int brightness, int color) {
         Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
         $receiver.setBrightness(brightness);
         $receiver.setRed((float)(color >> 16 & 255) / 256.0F);
         $receiver.setGreen((float)(color >> 8 & 255) / 256.0F);
         $receiver.setBlue((float)(color & 255) / 256.0F);
      }
   }
}
