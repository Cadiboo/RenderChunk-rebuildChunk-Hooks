package mods.octarinecore.common;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u0000 -2\u00020\u0001:\u0001-B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\bB\u001d\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fJ\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J'\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÖ\u0001J\u0006\u0010\u001e\u001a\u00020\u0000J\u0011\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\u001d\u0010\"\u001a\u00020\u00002\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u0006H\u0086\u0002J\u0011\u0010\"\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u00002\u0006\u0010$\u001a\u00020%J\u001e\u0010'\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\u000e\u0010(\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\u0011\u0010)\u001a\u00020\u00002\u0006\u0010!\u001a\u00020\u0007H\u0086\u0002J\u0011\u0010)\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0086\u0002J\t\u0010*\u001a\u00020+HÖ\u0001J\t\u0010,\u001a\u00020\u0000H\u0086\u0002R\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\n\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u000b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010¨\u0006."},
   d2 = {"Lmods/octarinecore/common/Int3;", "", "dir", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/EnumFacing;)V", "offset", "Lkotlin/Pair;", "", "(Lkotlin/Pair;)V", "x", "y", "z", "(III)V", "getX", "()I", "setX", "(I)V", "getY", "setY", "getZ", "setZ", "add", "other", "component1", "component2", "component3", "copy", "equals", "", "hashCode", "invert", "minus", "mul", "scale", "plus", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "rotateMut", "setTo", "sub", "times", "toString", "", "unaryMinus", "Companion", "BetterFoliage-MC1.12"}
)
public final class Int3 {
   private int x;
   private int y;
   private int z;
   @NotNull
   private static final Int3 zero = new Int3(0, 0, 0);
   public static final Int3.Companion Companion = new Int3.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final Int3 plus(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Int3(this.x + other.x, this.y + other.y, this.z + other.z);
   }

   @NotNull
   public final Int3 plus(@NotNull Pair other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      int var10002 = this.x;
      int var10003 = ((Number)other.getFirst()).intValue();
      Vec3i var10004 = ((EnumFacing)other.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "other.second.directionVec");
      var10002 += var10003 * var10004.func_177958_n();
      var10003 = this.y;
      int var3 = ((Number)other.getFirst()).intValue();
      Vec3i var10005 = ((EnumFacing)other.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10005, "other.second.directionVec");
      var10003 += var3 * var10005.func_177956_o();
      var3 = this.z;
      int var2 = ((Number)other.getFirst()).intValue();
      Vec3i var10006 = ((EnumFacing)other.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10006, "other.second.directionVec");
      return new Int3(var10002, var10003, var3 + var2 * var10006.func_177952_p());
   }

   @NotNull
   public final Int3 unaryMinus() {
      return new Int3(-this.x, -this.y, -this.z);
   }

   @NotNull
   public final Int3 minus(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Int3(this.x - other.x, this.y - other.y, this.z - other.z);
   }

   @NotNull
   public final Int3 times(int scale) {
      return new Int3(this.x * scale, this.y * scale, this.z * scale);
   }

   @NotNull
   public final Int3 times(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new Int3(this.x * other.x, this.y * other.y, this.z * other.z);
   }

   @NotNull
   public final Int3 rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      Int3 var10000 = new Int3;
      EnumFacing dir$iv = EnumFacing.EAST;
      int x$iv = this.x;
      int y$iv = this.y;
      int z$iv = this.z;
      Int3 var9 = var10000;
      Int3 var8 = var10000;
      int var13;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var13 = x$iv;
         break;
      case 2:
         var13 = -x$iv;
         break;
      case 3:
         var13 = y$iv;
         break;
      case 4:
         var13 = -y$iv;
         break;
      case 5:
         var13 = z$iv;
         break;
      case 6:
         var13 = -z$iv;
         break;
      default:
         var13 = 0;
      }

      int var10 = var13;
      dir$iv = EnumFacing.UP;
      x$iv = this.x;
      y$iv = this.y;
      z$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var13 = x$iv;
         break;
      case 2:
         var13 = -x$iv;
         break;
      case 3:
         var13 = y$iv;
         break;
      case 4:
         var13 = -y$iv;
         break;
      case 5:
         var13 = z$iv;
         break;
      case 6:
         var13 = -z$iv;
         break;
      default:
         var13 = 0;
      }

      int var11 = var13;
      dir$iv = EnumFacing.SOUTH;
      x$iv = this.x;
      y$iv = this.y;
      z$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
      case 1:
         var13 = x$iv;
         break;
      case 2:
         var13 = -x$iv;
         break;
      case 3:
         var13 = y$iv;
         break;
      case 4:
         var13 = -y$iv;
         break;
      case 5:
         var13 = z$iv;
         break;
      case 6:
         var13 = -z$iv;
         break;
      default:
         var13 = 0;
      }

      int var12 = var13;
      var9.<init>(var10, var11, var12);
      return var8;
   }

   @NotNull
   public final Int3 setTo(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x = other.x;
      this.y = other.y;
      this.z = other.z;
      return this;
   }

   @NotNull
   public final Int3 setTo(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      return this;
   }

   @NotNull
   public final Int3 add(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x += other.x;
      this.y += other.y;
      this.z += other.z;
      return this;
   }

   @NotNull
   public final Int3 sub(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x -= other.x;
      this.y -= other.y;
      this.z -= other.z;
      return this;
   }

   @NotNull
   public final Int3 invert() {
      this.x = -this.x;
      this.y = -this.y;
      this.z = -this.z;
      return this;
   }

   @NotNull
   public final Int3 mul(int scale) {
      this.x *= scale;
      this.y *= scale;
      this.z *= scale;
      return this;
   }

   @NotNull
   public final Int3 mul(@NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      this.x *= other.x;
      this.y *= other.y;
      this.z *= other.z;
      return this;
   }

   @NotNull
   public final Int3 rotateMut(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      EnumFacing dir$iv = EnumFacing.EAST;
      int x$iv = this.x;
      int x$iv = this.y;
      int x$iv = this.z;
      int var10000;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
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
         var10000 = x$iv;
         break;
      case 6:
         var10000 = -x$iv;
         break;
      default:
         var10000 = 0;
      }

      int rotX = var10000;
      EnumFacing dir$iv = EnumFacing.UP;
      x$iv = this.x;
      x$iv = this.y;
      int y$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
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
         var10000 = 0;
      }

      int rotY = var10000;
      EnumFacing dir$iv = EnumFacing.SOUTH;
      x$iv = this.x;
      y$iv = this.y;
      int z$iv = this.z;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[rot.getReverse()[dir$iv.ordinal()].ordinal()]) {
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
         var10000 = 0;
      }

      int rotZ = var10000;
      return this.setTo(rotX, rotY, rotZ);
   }

   public final int getX() {
      return this.x;
   }

   public final void setX(int var1) {
      this.x = var1;
   }

   public final int getY() {
      return this.y;
   }

   public final void setY(int var1) {
      this.y = var1;
   }

   public final int getZ() {
      return this.z;
   }

   public final void setZ(int var1) {
      this.z = var1;
   }

   public Int3(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Int3(@NotNull EnumFacing dir) {
      Intrinsics.checkParameterIsNotNull(dir, "dir");
      Vec3i var10001 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "dir.directionVec");
      int var2 = var10001.func_177958_n();
      Vec3i var10002 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "dir.directionVec");
      int var3 = var10002.func_177956_o();
      Vec3i var10003 = dir.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "dir.directionVec");
      this(var2, var3, var10003.func_177952_p());
   }

   public Int3(@NotNull Pair offset) {
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      int var10001 = ((Number)offset.getFirst()).intValue();
      Vec3i var10002 = ((EnumFacing)offset.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "offset.second.directionVec");
      var10001 *= var10002.func_177958_n();
      int var2 = ((Number)offset.getFirst()).intValue();
      Vec3i var10003 = ((EnumFacing)offset.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "offset.second.directionVec");
      var2 *= var10003.func_177956_o();
      int var3 = ((Number)offset.getFirst()).intValue();
      Vec3i var10004 = ((EnumFacing)offset.getSecond()).func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "offset.second.directionVec");
      this(var10001, var2, var3 * var10004.func_177952_p());
   }

   public final int component1() {
      return this.x;
   }

   public final int component2() {
      return this.y;
   }

   public final int component3() {
      return this.z;
   }

   @NotNull
   public final Int3 copy(int x, int y, int z) {
      return new Int3(x, y, z);
   }

   public String toString() {
      return "Int3(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
   }

   public int hashCode() {
      return (this.x * 31 + this.y) * 31 + this.z;
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (!(var1 instanceof Int3)) {
            return false;
         }

         Int3 var2 = (Int3)var1;
         if (this.x != var2.x || this.y != var2.y || this.z != var2.z) {
            return false;
         }
      }

      return true;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lmods/octarinecore/common/Int3$Companion;", "", "()V", "zero", "Lmods/octarinecore/common/Int3;", "getZero", "()Lmods/octarinecore/common/Int3;", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final Int3 getZero() {
         return Int3.zero;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
