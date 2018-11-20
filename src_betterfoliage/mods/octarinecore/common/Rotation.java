package mods.octarinecore.common;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0000H\u0086\u0002J)\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000eH\u0086\bJ)\u0010\r\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013H\u0086\bJ\u0011\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0013H\u0086\u0002J\t\u0010\u0016\u001a\u00020\u0000H\u0086\u0002R\u0019\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u0018"},
   d2 = {"Lmods/octarinecore/common/Rotation;", "", "forward", "", "Lnet/minecraft/util/EnumFacing;", "reverse", "([Lnet/minecraft/util/EnumFacing;[Lnet/minecraft/util/EnumFacing;)V", "getForward", "()[Lnet/minecraft/util/EnumFacing;", "[Lnet/minecraft/util/EnumFacing;", "getReverse", "plus", "other", "rotatedComponent", "", "dir", "x", "y", "z", "", "times", "num", "unaryMinus", "Companion", "BetterFoliage-MC1.12"}
)
public final class Rotation {
   @NotNull
   private final EnumFacing[] forward;
   @NotNull
   private final EnumFacing[] reverse;
   @NotNull
   private static final Rotation[] rot90;
   @NotNull
   private static final Rotation identity;
   public static final Rotation.Companion Companion = new Rotation.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final Rotation plus(@NotNull Rotation other) {
      Intrinsics.checkParameterIsNotNull(other, "other");
      Rotation var10000 = new Rotation;
      int size$iv = 6;
      Rotation var10 = var10000;
      Rotation var9 = var10000;
      Object[] result$iv = new EnumFacing[size$iv];
      int i$iv = 0;

      int var5;
      for(var5 = result$iv.length; i$iv < var5; ++i$iv) {
         EnumFacing var13 = this.forward[other.forward[i$iv].ordinal()];
         result$iv[i$iv] = var13;
      }

      size$iv = 6;
      EnumFacing[] var11 = result$iv;
      result$iv = new EnumFacing[size$iv];
      i$iv = 0;

      for(var5 = result$iv.length; i$iv < var5; ++i$iv) {
         EnumFacing var14 = other.reverse[this.reverse[i$iv].ordinal()];
         result$iv[i$iv] = var14;
      }

      var10.<init>(var11, result$iv);
      return var9;
   }

   @NotNull
   public final Rotation unaryMinus() {
      return new Rotation(this.reverse, this.forward);
   }

   @NotNull
   public final Rotation times(int num) {
      Rotation var10000;
      switch(num % 4) {
      case 1:
         var10000 = this;
         break;
      case 2:
         var10000 = this.plus(this);
         break;
      case 3:
         var10000 = this.unaryMinus();
         break;
      default:
         var10000 = Companion.getIdentity();
      }

      return var10000;
   }

   public final int rotatedComponent(@NotNull EnumFacing dir, int x, int y, int z) {
      Intrinsics.checkParameterIsNotNull(dir, "dir");
      int var10000;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$0[this.getReverse()[dir.ordinal()].ordinal()]) {
      case 1:
         var10000 = x;
         break;
      case 2:
         var10000 = -x;
         break;
      case 3:
         var10000 = y;
         break;
      case 4:
         var10000 = -y;
         break;
      case 5:
         var10000 = z;
         break;
      case 6:
         var10000 = -z;
         break;
      default:
         var10000 = 0;
      }

      return var10000;
   }

   public final double rotatedComponent(@NotNull EnumFacing dir, double x, double y, double z) {
      Intrinsics.checkParameterIsNotNull(dir, "dir");
      double var10000;
      switch(Rotation$WhenMappings.$EnumSwitchMapping$1[this.getReverse()[dir.ordinal()].ordinal()]) {
      case 1:
         var10000 = x;
         break;
      case 2:
         var10000 = -x;
         break;
      case 3:
         var10000 = y;
         break;
      case 4:
         var10000 = -y;
         break;
      case 5:
         var10000 = z;
         break;
      case 6:
         var10000 = -z;
         break;
      default:
         var10000 = 0.0D;
      }

      return var10000;
   }

   @NotNull
   public final EnumFacing[] getForward() {
      return this.forward;
   }

   @NotNull
   public final EnumFacing[] getReverse() {
      return this.reverse;
   }

   public Rotation(@NotNull EnumFacing[] forward, @NotNull EnumFacing[] reverse) {
      Intrinsics.checkParameterIsNotNull(forward, "forward");
      Intrinsics.checkParameterIsNotNull(reverse, "reverse");
      super();
      this.forward = forward;
      this.reverse = reverse;
   }

   static {
      int size$iv = 6;
      Object[] result$iv = new Rotation[size$iv];
      int i$iv = 0;

      for(int var3 = result$iv.length; i$iv < var3; ++i$iv) {
         EnumFacing var10002 = GeometryKt.getForgeDirs()[i$iv].func_176734_d();
         Intrinsics.checkExpressionValueIsNotNull(var10002, "forgeDirs[idx].opposite");
         Rotation var9 = new Rotation(GeometryKt.getRotations(var10002), GeometryKt.getRotations(GeometryKt.getForgeDirs()[i$iv]));
         result$iv[i$iv] = var9;
      }

      rot90 = result$iv;
      identity = new Rotation(GeometryKt.getForgeDirs(), GeometryKt.getForgeDirs());
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0019\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\f"},
      d2 = {"Lmods/octarinecore/common/Rotation$Companion;", "", "()V", "identity", "Lmods/octarinecore/common/Rotation;", "getIdentity", "()Lmods/octarinecore/common/Rotation;", "rot90", "", "getRot90", "()[Lmods/octarinecore/common/Rotation;", "[Lmods/octarinecore/common/Rotation;", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final Rotation[] getRot90() {
         return Rotation.rot90;
      }

      @NotNull
      public final Rotation getIdentity() {
         return Rotation.identity;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
