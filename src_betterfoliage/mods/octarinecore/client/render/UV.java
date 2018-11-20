package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import mods.octarinecore.Utils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J.\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\u0016\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0018J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006 "},
   d2 = {"Lmods/octarinecore/client/render/UV;", "", "u", "", "v", "(DD)V", "rotate", "getRotate", "()Lmods/octarinecore/client/render/UV;", "getU", "()D", "getV", "clamp", "minU", "maxU", "minV", "maxV", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "mirror", "mirrorU", "mirrorV", "n", "toString", "", "Companion", "BetterFoliage-MC1.12"}
)
public final class UV {
   private final double u;
   private final double v;
   @NotNull
   private static final UV topLeft = new UV(-0.5D, -0.5D);
   @NotNull
   private static final UV topRight = new UV(0.5D, -0.5D);
   @NotNull
   private static final UV bottomLeft = new UV(-0.5D, 0.5D);
   @NotNull
   private static final UV bottomRight = new UV(0.5D, 0.5D);
   public static final UV.Companion Companion = new UV.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final UV getRotate() {
      return new UV(this.v, -this.u);
   }

   @NotNull
   public final UV rotate(int n) {
      UV var10000;
      switch(n % 4) {
      case 0:
         var10000 = copy$default(this, 0.0D, 0.0D, 3, (Object)null);
         break;
      case 1:
         var10000 = new UV(this.v, -this.u);
         break;
      case 2:
         var10000 = new UV(-this.u, -this.v);
         break;
      default:
         var10000 = new UV(-this.v, this.u);
      }

      return var10000;
   }

   @NotNull
   public final UV clamp(double minU, double maxU, double minV, double maxV) {
      return new UV(Utils.minmax(this.u, minU, maxU), Utils.minmax(this.v, minV, maxV));
   }

   @NotNull
   public final UV mirror(boolean mirrorU, boolean mirrorV) {
      return new UV(mirrorU ? -this.u : this.u, mirrorV ? -this.v : this.v);
   }

   public final double getU() {
      return this.u;
   }

   public final double getV() {
      return this.v;
   }

   public UV(double u, double v) {
      this.u = u;
      this.v = v;
   }

   public final double component1() {
      return this.u;
   }

   public final double component2() {
      return this.v;
   }

   @NotNull
   public final UV copy(double u, double v) {
      return new UV(u, v);
   }

   public String toString() {
      return "UV(u=" + this.u + ", v=" + this.v + ")";
   }

   public int hashCode() {
      long var10000 = Double.doubleToLongBits(this.u);
      int var1 = (int)(var10000 ^ var10000 >>> 32) * 31;
      long var10001 = Double.doubleToLongBits(this.v);
      return var1 + (int)(var10001 ^ var10001 >>> 32);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof UV) {
            UV var2 = (UV)var1;
            if (Double.compare(this.u, var2.u) == 0 && Double.compare(this.v, var2.v) == 0) {
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
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006¨\u0006\r"},
      d2 = {"Lmods/octarinecore/client/render/UV$Companion;", "", "()V", "bottomLeft", "Lmods/octarinecore/client/render/UV;", "getBottomLeft", "()Lmods/octarinecore/client/render/UV;", "bottomRight", "getBottomRight", "topLeft", "getTopLeft", "topRight", "getTopRight", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final UV getTopLeft() {
         return UV.topLeft;
      }

      @NotNull
      public final UV getTopRight() {
         return UV.topRight;
      }

      @NotNull
      public final UV getBottomLeft() {
         return UV.bottomLeft;
      }

      @NotNull
      public final UV getBottomRight() {
         return UV.bottomRight;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
