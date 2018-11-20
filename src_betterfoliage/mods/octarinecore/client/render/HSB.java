package mods.octarinecore.client.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\bHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\f\"\u0004\b\u0010\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\f\"\u0004\b\u0012\u0010\u000e¨\u0006\u001e"},
   d2 = {"Lmods/octarinecore/client/render/HSB;", "", "hue", "", "saturation", "brightness", "(FFF)V", "asColor", "", "getAsColor", "()I", "getBrightness", "()F", "setBrightness", "(F)V", "getHue", "setHue", "getSaturation", "setSaturation", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "Companion", "BetterFoliage-MC1.12"}
)
public final class HSB {
   private float hue;
   private float saturation;
   private float brightness;
   public static final HSB.Companion Companion = new HSB.Companion((DefaultConstructorMarker)null);

   public final int getAsColor() {
      return Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
   }

   public final float getHue() {
      return this.hue;
   }

   public final void setHue(float var1) {
      this.hue = var1;
   }

   public final float getSaturation() {
      return this.saturation;
   }

   public final void setSaturation(float var1) {
      this.saturation = var1;
   }

   public final float getBrightness() {
      return this.brightness;
   }

   public final void setBrightness(float var1) {
      this.brightness = var1;
   }

   public HSB(float hue, float saturation, float brightness) {
      this.hue = hue;
      this.saturation = saturation;
      this.brightness = brightness;
   }

   public final float component1() {
      return this.hue;
   }

   public final float component2() {
      return this.saturation;
   }

   public final float component3() {
      return this.brightness;
   }

   @NotNull
   public final HSB copy(float hue, float saturation, float brightness) {
      return new HSB(hue, saturation, brightness);
   }

   public String toString() {
      return "HSB(hue=" + this.hue + ", saturation=" + this.saturation + ", brightness=" + this.brightness + ")";
   }

   public int hashCode() {
      return (Float.floatToIntBits(this.hue) * 31 + Float.floatToIntBits(this.saturation)) * 31 + Float.floatToIntBits(this.brightness);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof HSB) {
            HSB var2 = (HSB)var1;
            if (Float.compare(this.hue, var2.hue) == 0 && Float.compare(this.saturation, var2.saturation) == 0 && Float.compare(this.brightness, var2.brightness) == 0) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lmods/octarinecore/client/render/HSB$Companion;", "", "()V", "fromColor", "Lmods/octarinecore/client/render/HSB;", "color", "", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final HSB fromColor(int color) {
         float[] hsbVals = Color.RGBtoHSB(color >> 16 & 255, color >> 8 & 255, color & 255, (float[])null);
         return new HSB(hsbVals[0], hsbVals[1], hsbVals[2]);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
