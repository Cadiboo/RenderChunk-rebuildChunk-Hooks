package mods.octarinecore.common;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 82\u00020\u0001:\u00018B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\n\u0012\u0006\u0010\u0005\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nJ\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000J\t\u0010\u001e\u001a\u00020\nHÆ\u0003J\t\u0010\u001f\u001a\u00020\nHÆ\u0003J\t\u0010 \u001a\u00020\nHÆ\u0003J'\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\n2\b\b\u0002\u0010\u0004\u001a\u00020\n2\b\b\u0002\u0010\u0005\u001a\u00020\nHÆ\u0001J\u0011\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\u0000H\u0086\u0004J\u0011\u0010$\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u0000H\u0086\u0004J\u0013\u0010%\u001a\u00020&2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\u0006\u0010)\u001a\u00020\u0000J\u0011\u0010*\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000H\u0086\u0002J\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\nJ\u000e\u0010+\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000J\u0011\u0010-\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000H\u0086\u0002J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u00020\u00002\u0006\u0010/\u001a\u000200J\u001e\u00102\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nJ\u001e\u00102\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0003J\u000e\u00102\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000J\u001e\u00103\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\nJ\u000e\u00103\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000J\u0011\u00104\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\nH\u0086\u0002J\u0011\u00104\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0000H\u0086\u0002J\t\u00105\u001a\u000206HÖ\u0001J\t\u00107\u001a\u00020\u0000H\u0086\u0002R\u0011\u0010\f\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0002\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\u0005\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0017¨\u00069"},
   d2 = {"Lmods/octarinecore/common/Double3;", "", "x", "", "y", "z", "(FFF)V", "dir", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/EnumFacing;)V", "", "(DDD)V", "length", "getLength", "()D", "nearestCardinal", "getNearestCardinal", "()Lnet/minecraft/util/EnumFacing;", "normalize", "getNormalize", "()Lmods/octarinecore/common/Double3;", "getX", "setX", "(D)V", "getY", "setY", "getZ", "setZ", "add", "other", "component1", "component2", "component3", "copy", "cross", "o", "dot", "equals", "", "hashCode", "", "invert", "minus", "mul", "scale", "plus", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "rotateMut", "setTo", "sub", "times", "toString", "", "unaryMinus", "Companion", "BetterFoliage-MC1.12"}
)
public final class Double3 {
   private double x;
   private double y;
   private double z;
   public static final Double3.Companion Companion = new Double3.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final Double3 plus(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Double3(this.x + other.x, this.y + other.y, this.z + other.z);
   }

   @NotNull
   public final Double3 unaryMinus() {
      return new Double3(-this.x, -this.y, -this.z);
   }

   @NotNull
   public final Double3 minus(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Double3(this.x - other.x, this.y - other.y, this.z - other.z);
   }

   @NotNull
   public final Double3 times(double scale) {
      return new Double3(this.x * scale, this.y * scale, this.z * scale);
   }

   @NotNull
   public final Double3 times(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Double3(this.x * other.x, this.y * other.y, this.z * other.z);
   }

   @NotNull
   public final Double3 rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      Double3 var10000 = new Double3;
      EnumFacing dir$iv = EnumFacing.EAST;
      double x$iv = this.x;
      double y$iv = this.y;
      double z$iv = this.z;
      Double3 var12 = var10000;
      Double3 var11 = var10000;
      double var19;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var19 = x$iv;
         break;
      case 2:
         var19 = -x$iv;
         break;
      case 3:
         var19 = y$iv;
         break;
      case 4:
         var19 = -y$iv;
         break;
      case 5:
         var19 = z$iv;
         break;
      case 6:
         var19 = -z$iv;
         break;
      default:
         var19 = 0.0D;
      }

      double var13 = var19;
      dir$iv = EnumFacing.UP;
      x$iv = this.x;
      y$iv = this.y;
      z$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var19 = x$iv;
         break;
      case 2:
         var19 = -x$iv;
         break;
      case 3:
         var19 = y$iv;
         break;
      case 4:
         var19 = -y$iv;
         break;
      case 5:
         var19 = z$iv;
         break;
      case 6:
         var19 = -z$iv;
         break;
      default:
         var19 = 0.0D;
      }

      double var15 = var19;
      dir$iv = EnumFacing.SOUTH;
      x$iv = this.x;
      y$iv = this.y;
      z$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$1[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var19 = x$iv;
         break;
      case 2:
         var19 = -x$iv;
         break;
      case 3:
         var19 = y$iv;
         break;
      case 4:
         var19 = -y$iv;
         break;
      case 5:
         var19 = z$iv;
         break;
      case 6:
         var19 = -z$iv;
         break;
      default:
         var19 = 0.0D;
      }

      double var17 = var19;
      var12.<init>(var13, var15, var17);
      return var11;
   }

   @NotNull
   public final Double3 setTo(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x = other.x;
      this.y = other.y;
      this.z = other.z;
      return this;
   }

   @NotNull
   public final Double3 setTo(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   @NotNull
   public final Double3 setTo(float x, float y, float z) {
      return this.setTo((double)x, (double)y, (double)z);
   }

   @NotNull
   public final Double3 add(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x += other.x;
      this.y += other.y;
      this.z += other.z;
      return this;
   }

   @NotNull
   public final Double3 add(double x, double y, double z) {
      this.x += x;
      this.y += y;
      this.z += z;
      return this;
   }

   @NotNull
   public final Double3 sub(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x -= other.x;
      this.y -= other.y;
      this.z -= other.z;
      return this;
   }

   @NotNull
   public final Double3 sub(double x, double y, double z) {
      this.x -= x;
      this.y -= y;
      this.z -= z;
      return this;
   }

   @NotNull
   public final Double3 invert() {
      this.x = -this.x;
      this.y = -this.y;
      this.z = -this.z;
      return this;
   }

   @NotNull
   public final Double3 mul(double scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
      return this;
   }

   @NotNull
   public final Double3 mul(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x *= other.x;
      this.y *= other.y;
      this.z *= other.z;
      return this;
   }

   @NotNull
   public final Double3 rotateMut(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
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
      return this.setTo(rotX, rotY, rotZ);
   }

   public final double dot(@NotNull Double3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return this.x * other.x + this.y * other.y + this.z * other.z;
   }

   @NotNull
   public final Double3 cross(@NotNull Double3 o) {
      Intrinsics.checkParameterIsNotNull(o, "o");
      return new Double3(this.y * o.z - this.z * o.y, this.z * o.x - this.x * o.z, this.x * o.y - this.y * o.x);
   }

   public final double getLength() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   @NotNull
   public final Double3 getNormalize() {
      double var1 = 1.0D / this.getLength();
      return new Double3(this.x * var1, this.y * var1, this.z * var1);
   }

   @NotNull
   public final EnumFacing getNearestCardinal() {
      return (EnumFacing)GeometryKt.nearestAngle(this, ArraysKt.asIterable((Object[])GeometryKt.getForgeDirs()), (Function1)null.INSTANCE).getFirst();
   }

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

   public Double3(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Double3(float x, float y, float z) {
      this((double)x, (double)y, (double)z);
   }

   public Double3(@NotNull EnumFacing dir) {
      Intrinsics.checkParameterIsNotNull(dir, "dir");
      Vec3i var10001 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "dir.directionVec");
      double var2 = (double)var10001.func_177958_n();
      Vec3i var10002 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "dir.directionVec");
      double var3 = (double)var10002.func_177956_o();
      Vec3i var10003 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "dir.directionVec");
      this(var2, var3, (double)var10003.func_177952_p());
   }

   public final double component1() {
      return this.x;
   }

   public final double component2() {
      return this.y;
   }

   public final double component3() {
      return this.z;
   }

   @NotNull
   public final Double3 copy(double x, double y, double z) {
      return new Double3(x, y, z);
   }

   public String toString() {
      return "Double3(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
   }

   public int hashCode() {
      long var10000 = Double.doubleToLongBits(this.x);
      int var1 = (int)(var10000 ^ var10000 >>> 32) * 31;
      long var10001 = Double.doubleToLongBits(this.y);
      var1 = (var1 + (int)(var10001 ^ var10001 >>> 32)) * 31;
      var10001 = Double.doubleToLongBits(this.z);
      return var1 + (int)(var10001 ^ var10001 >>> 32);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof Double3) {
            Double3 var2 = (Double3)var1;
            if (Double.compare(this.x, var2.x) == 0 && Double.compare(this.y, var2.y) == 0 && Double.compare(this.z, var2.z) == 0) {
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
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\nR\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"},
      d2 = {"Lmods/octarinecore/common/Double3$Companion;", "", "()V", "zero", "Lmods/octarinecore/common/Double3;", "getZero", "()Lmods/octarinecore/common/Double3;", "weight", "v1", "weight1", "", "v2", "weight2", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final Double3 getZero() {
         return new Double3(0.0D, 0.0D, 0.0D);
      }

      @NotNull
      public final Double3 weight(@NotNull Double3 v1, double weight1, @NotNull Double3 v2, double weight2) {
         Intrinsics.checkParameterIsNotNull(v1, "v1");
         Intrinsics.checkParameterIsNotNull(v2, "v2");
         return new Double3(v1.getX() * weight1 + v2.getX() * weight2, v1.getY() * weight1 + v2.getY() * weight2, v1.getZ() * weight1 + v2.getZ() * weight2);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
