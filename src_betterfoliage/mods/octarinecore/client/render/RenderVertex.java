package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Rotation;
import mods.octarinecore.common.Rotation$WhenMappings;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020-J\u001e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201J\u0019\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u000205H\u0086\bJ\u0011\u00107\u001a\u0002032\u0006\u00108\u001a\u00020\nH\u0086\bJ\u000e\u00109\u001a\u00020\u00002\u0006\u0010.\u001a\u00020/J\u0011\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\nH\u0086\bJ\u0011\u0010<\u001a\u0002032\u0006\u00108\u001a\u00020\nH\u0086\bJ\u0011\u0010=\u001a\u0002032\u0006\u0010>\u001a\u00020\u0004H\u0086\bJ\u0011\u0010?\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AH\u0086\bJ\u000e\u0010B\u001a\u00020\u00002\u0006\u00100\u001a\u000201R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0006\"\u0004\b\u0018\u0010\bR\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\u001a\u0010\"\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u001c\"\u0004\b$\u0010\u001eR\u001a\u0010%\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001c\"\u0004\b'\u0010\u001eR\u001a\u0010(\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001c\"\u0004\b*\u0010\u001e¨\u0006C"},
   d2 = {"Lmods/octarinecore/client/render/RenderVertex;", "", "()V", "blue", "", "getBlue", "()F", "setBlue", "(F)V", "brightness", "", "getBrightness", "()I", "setBrightness", "(I)V", "green", "getGreen", "setGreen", "rawData", "", "getRawData", "()[I", "red", "getRed", "setRed", "u", "", "getU", "()D", "setU", "(D)V", "v", "getV", "setV", "x", "getX", "setX", "y", "getY", "setY", "z", "getZ", "setZ", "init", "vertex", "Lmods/octarinecore/client/render/Vertex;", "rot", "Lmods/octarinecore/common/Rotation;", "trans", "Lmods/octarinecore/common/Double3;", "mirrorUV", "", "mirrorU", "", "mirrorV", "multiplyColor", "color", "rotate", "rotateUV", "n", "setColor", "setGrey", "level", "setIcon", "icon", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "translate", "BetterFoliage-MC1.12"}
)
public final class RenderVertex {
   private double x;
   private double y;
   private double z;
   private double u;
   private double v;
   private int brightness;
   private float red;
   private float green;
   private float blue;
   @NotNull
   private final int[] rawData = new int[7];

   public final double getX() {
      return this.x;
   }

   public final void setX(double var1) {
      this.x = var1;
   }

   public final double getY() {
      return this.y;
   }

   public final void setY(double var1) {
      this.y = var1;
   }

   public final double getZ() {
      return this.z;
   }

   public final void setZ(double var1) {
      this.z = var1;
   }

   public final double getU() {
      return this.u;
   }

   public final void setU(double var1) {
      this.u = var1;
   }

   public final double getV() {
      return this.v;
   }

   public final void setV(double var1) {
      this.v = var1;
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

   @NotNull
   public final int[] getRawData() {
      return this.rawData;
   }

   @NotNull
   public final RenderVertex init(@NotNull Vertex vertex, @NotNull Rotation rot, @NotNull Double3 trans) {
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      Double3 result = vertex.getXyz().rotate(rot).plus(trans);
      this.x = result.getX();
      this.y = result.getY();
      this.z = result.getZ();
      return this;
   }

   @NotNull
   public final RenderVertex init(@NotNull Vertex vertex) {
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      this.x = vertex.getXyz().getX();
      this.y = vertex.getXyz().getY();
      this.z = vertex.getXyz().getZ();
      this.u = vertex.getUv().getU();
      this.v = vertex.getUv().getV();
      return this;
   }

   @NotNull
   public final RenderVertex translate(@NotNull Double3 trans) {
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      this.x += trans.getX();
      this.y += trans.getY();
      this.z += trans.getZ();
      return this;
   }

   @NotNull
   public final RenderVertex rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      if (rot == Rotation.Companion.getIdentity()) {
         return this;
      } else {
         EnumFacing dir$iv = EnumFacing.EAST;
         double rotZ = this.x;
         double x$iv = this.y;
         double x$iv = this.z;
         double var10000;
         switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
         case 1:
            var10000 = rotZ;
            break;
         case 2:
            var10000 = -rotZ;
            break;
         case 3:
            var10000 = x$iv;
            break;
         case 4:
            var10000 = -x$iv;
            break;
         case 5:
            var10000 = x$iv;
            break;
         case 6:
            var10000 = -x$iv;
            break;
         default:
            var10000 = 0.0D;
         }

         double rotX = var10000;
         EnumFacing dir$iv = EnumFacing.UP;
         x$iv = this.x;
         x$iv = this.y;
         double y$iv = this.z;
         switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
         case 1:
            var10000 = x$iv;
            break;
         case 2:
            var10000 = -x$iv;
            break;
         case 3:
            var10000 = x$iv;
            break;
         case 4:
            var10000 = -x$iv;
            break;
         case 5:
            var10000 = y$iv;
            break;
         case 6:
            var10000 = -y$iv;
            break;
         default:
            var10000 = 0.0D;
         }

         double rotY = var10000;
         EnumFacing dir$iv = EnumFacing.SOUTH;
         x$iv = this.x;
         y$iv = this.y;
         double z$iv = this.z;
         switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
         case 1:
            var10000 = x$iv;
            break;
         case 2:
            var10000 = -x$iv;
            break;
         case 3:
            var10000 = y$iv;
            break;
         case 4:
            var10000 = -y$iv;
            break;
         case 5:
            var10000 = z$iv;
            break;
         case 6:
            var10000 = -z$iv;
            break;
         default:
            var10000 = 0.0D;
         }

         rotZ = var10000;
         this.x = rotX;
         this.y = rotY;
         this.z = rotZ;
         return this;
      }
   }

   @NotNull
   public final RenderVertex rotateUV(int n) {
      double t;
      switch(n % 4) {
      case 1:
         t = this.getV();
         this.setV(-this.getU());
         this.setU(t);
         return this;
      case 2:
         this.setU(-this.getU());
         this.setV(-this.getV());
         return this;
      case 3:
         t = -this.getV();
         this.setV(this.getU());
         this.setU(t);
         return this;
      default:
         return this;
      }
   }

   public final void mirrorUV(boolean mirrorU, boolean mirrorV) {
      if (mirrorU) {
         this.setU(-this.getU());
      }

      if (mirrorV) {
         this.setV(-this.getV());
      }

   }

   @NotNull
   public final RenderVertex setIcon(@NotNull TextureAtlasSprite icon) {
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      this.setU((double)(icon.func_94212_f() - icon.func_94209_e()) * (this.getU() + 0.5D) + (double)icon.func_94209_e());
      this.setV((double)(icon.func_94210_h() - icon.func_94206_g()) * (this.getV() + 0.5D) + (double)icon.func_94206_g());
      return this;
   }

   public final void setGrey(float level) {
      float grey = Math.min((this.getRed() + this.getGreen() + this.getBlue()) * 0.333F * level, 1.0F);
      this.setRed(grey);
      this.setGreen(grey);
      this.setBlue(grey);
   }

   public final void multiplyColor(int color) {
      this.setRed(this.getRed() * ((float)(color >> 16 & 255) / 256.0F));
      this.setGreen(this.getGreen() * ((float)(color >> 8 & 255) / 256.0F));
      this.setBlue(this.getBlue() * ((float)(color & 255) / 256.0F));
   }

   public final void setColor(int color) {
      this.setRed((float)(color >> 16 & 255) / 256.0F);
      this.setGreen((float)(color >> 8 & 255) / 256.0F);
      this.setBlue((float)(color & 255) / 256.0F);
   }
}
