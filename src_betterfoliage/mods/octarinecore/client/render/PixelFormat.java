package mods.octarinecore.client.render;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0015\n\u0002\b\n\u001a!\u0010\u0007\u001a\u00020\u00022\b\u0010\b\u001a\u0004\u0018\u00010\t2\n\u0010\n\u001a\u00020\u000b\"\u00020\u0002¢\u0006\u0002\u0010\f\u001a&\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\t\u001a\u0015\u0010\u0012\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\tH\u0086\u0004\" \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006¨\u0006\u0015"},
   d2 = {"brightnessComponents", "", "", "getBrightnessComponents", "()Ljava/util/List;", "setBrightnessComponents", "(Ljava/util/List;)V", "brSum", "multiplier", "", "brightness", "", "(Ljava/lang/Float;[I)I", "brWeighted", "br1", "weight1", "br2", "weight2", "brMul", "f", "colorMul", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "PixelFormat"
)
public final class PixelFormat {
   @NotNull
   private static List brightnessComponents = CollectionsKt.listOf(new Integer[]{Integer.valueOf(20), Integer.valueOf(4)});

   @NotNull
   public static final List getBrightnessComponents() {
      return brightnessComponents;
   }

   public static final void setBrightnessComponents(@NotNull List var0) {
      Intrinsics.checkParameterIsNotNull(var0, "<set-?>");
      brightnessComponents = var0;
   }

   public static final int brMul(int $receiver, float f) {
      int weight = (int)(f * 256.0F);
      int result = 0;
      Iterable $receiver$iv = (Iterable)brightnessComponents;

      int shift;
      int weighted;
      for(Iterator var5 = $receiver$iv.iterator(); var5.hasNext(); result |= weighted << shift) {
         Object element$iv = var5.next();
         shift = ((Number)element$iv).intValue();
         int raw = $receiver >> shift & 15;
         weighted = raw * weight / 256;
      }

      return result;
   }

   public static final int colorMul(int $receiver, float f) {
      int weight = (int)(f * 256.0F);
      int red = ($receiver >> 16 & 255) * weight / 256;
      int green = ($receiver >> 8 & 255) * weight / 256;
      int blue = ($receiver & 255) * weight / 256;
      return red << 16 | green << 8 | blue;
   }

   public static final int brSum(@Nullable Float multiplier, @NotNull int... brightness) {
      Intrinsics.checkParameterIsNotNull(brightness, "brightness");
      int result = brightnessComponents.size();
      Object[] result$iv = new Integer[result];
      int index$iv = 0;

      for(int var6 = result$iv.length; index$iv < var6; ++index$iv) {
         Integer var21 = Integer.valueOf(0);
         result$iv[index$iv] = var21;
      }

      Integer[] sum = result$iv;
      Iterable $receiver$iv = (Iterable)brightnessComponents;
      int index$iv = 0;
      Iterator var25 = $receiver$iv.iterator();

      int shift;
      int comp;
      int var10000;
      while(var25.hasNext()) {
         Object item$iv = var25.next();
         var10000 = index$iv++;
         int shift = ((Number)item$iv).intValue();
         shift = var10000;
         int[] $receiver$iv = brightness;
         comp = brightness.length;

         for(int var11 = 0; var11 < comp; ++var11) {
            int element$iv = $receiver$iv[var11];
            int comp = element$iv >> shift & 15;
            sum[shift] = sum[shift].intValue() + comp;
         }
      }

      result = 0;
      Iterable $receiver$iv = (Iterable)brightnessComponents;
      index$iv = 0;

      for(Iterator var27 = $receiver$iv.iterator(); var27.hasNext(); result |= comp) {
         Object item$iv = var27.next();
         var10000 = index$iv++;
         shift = ((Number)item$iv).intValue();
         int idx = var10000;
         comp = multiplier == null ? sum[idx].intValue() << shift : (int)((float)sum[idx].intValue() * multiplier.floatValue()) << shift;
      }

      return result;
   }

   public static final int brWeighted(int br1, float weight1, int br2, float weight2) {
      int w1int = (int)(weight1 * 256.0F + 0.5F);
      int w2int = (int)(weight2 * 256.0F + 0.5F);
      int result = 0;
      Iterable $receiver$iv = (Iterable)brightnessComponents;
      int index$iv = 0;

      int shift;
      int compWeighted;
      for(Iterator var9 = $receiver$iv.iterator(); var9.hasNext(); result |= (compWeighted & 15) << shift) {
         Object item$iv = var9.next();
         ++index$iv;
         shift = ((Number)item$iv).intValue();
         int comp1 = br1 >> shift & 15;
         int comp2 = br2 >> shift & 15;
         compWeighted = (comp1 * w1int + comp2 * w2int) / 256;
      }

      return result;
   }
}
