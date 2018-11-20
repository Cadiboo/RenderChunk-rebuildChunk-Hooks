package mods.betterfoliage.client.texture;

import kotlin.Metadata;
import kotlin.jvm.JvmName;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001¨\u0006\u0006"},
   d2 = {"blendRGB", "", "rgb1", "rgb2", "weight1", "weight2", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Utils"
)
public final class Utils {
   public static final int blendRGB(int rgb1, int rgb2, int weight1, int weight2) {
      int r = ((rgb1 >> 16 & 255) * weight1 + (rgb2 >> 16 & 255) * weight2) / (weight1 + weight2);
      int g = ((rgb1 >> 8 & 255) * weight1 + (rgb2 >> 8 & 255) * weight2) / (weight1 + weight2);
      int b = ((rgb1 & 255) * weight1 + (rgb2 & 255) * weight2) / (weight1 + weight2);
      int a = rgb1 >> 24 & 255;
      int result = a << 24 | r << 16 | g << 8 | b;
      return result;
   }
}
