package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u0004J&\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006 "},
   d2 = {"Lmods/octarinecore/client/render/AoData;", "", "()V", "blue", "", "getBlue", "()F", "setBlue", "(F)V", "brightness", "", "getBrightness", "()I", "setBrightness", "(I)V", "green", "getGreen", "setGreen", "red", "getRed", "setRed", "valid", "", "getValid", "()Z", "setValid", "(Z)V", "reset", "", "set", "colorMultiplier", "Companion", "BetterFoliage-MC1.12"}
)
public final class AoData {
   private boolean valid;
   private int brightness;
   private float red;
   private float green;
   private float blue;
   @NotNull
   private static final AoData black = new AoData();
   public static final AoData.Companion Companion = new AoData.Companion((DefaultConstructorMarker)null);

   public final boolean getValid() {
      return this.valid;
   }

   public final void setValid(boolean var1) {
      this.valid = var1;
   }

   public final int getBrightness() {
      return this.brightness;
   }

   public final void setBrightness(int var1) {
      this.brightness = var1;
   }

   public final float getRed() {
      return this.red;
   }

   public final void setRed(float var1) {
      this.red = var1;
   }

   public final float getGreen() {
      return this.green;
   }

   public final void setGreen(float var1) {
      this.green = var1;
   }

   public final float getBlue() {
      return this.blue;
   }

   public final void setBlue(float var1) {
      this.blue = var1;
   }

   public final void reset() {
      this.valid = false;
   }

   public final void set(int brightness, float red, float green, float blue) {
      if (!this.valid) {
         this.valid = true;
         this.brightness = brightness;
         this.red = red;
         this.green = green;
         this.blue = blue;
      }
   }

   public final void set(int brightness, float colorMultiplier) {
      this.valid = true;
      this.brightness = brightness;
      this.red = colorMultiplier;
      this.green = colorMultiplier;
      this.blue = colorMultiplier;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lmods/octarinecore/client/render/AoData$Companion;", "", "()V", "black", "Lmods/octarinecore/client/render/AoData;", "getBlack", "()Lmods/octarinecore/client/render/AoData;", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final AoData getBlack() {
         return AoData.black;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
