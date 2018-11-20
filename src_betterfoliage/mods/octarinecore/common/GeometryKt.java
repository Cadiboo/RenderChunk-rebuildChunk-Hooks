package mods.octarinecore.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.Utils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000t\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aB\u00104\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002060\u000e\"\u0004\b\u0000\u001052\u0006\u00107\u001a\u0002012\f\u00108\u001a\b\u0012\u0004\u0012\u0002H5092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002010;\u001aB\u0010<\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002060\u000e\"\u0004\b\u0000\u001052\u0006\u0010=\u001a\u0002012\f\u00108\u001a\b\u0012\u0004\u0012\u0002H5092\u0012\u0010>\u001a\u000e\u0012\u0004\u0012\u0002H5\u0012\u0004\u0012\u0002010;\u001a\u000e\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020\u000f\u001a\u0015\u0010B\u001a\u00020C*\u00020C2\u0006\u0010D\u001a\u00020\u0015H\u0086\u0002\u001a\u0012\u0010E\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010?\u001a\u00020@\u001a\u0015\u0010F\u001a\u000201*\u00020\u000f2\u0006\u0010G\u001a\u000206H\u0086\u0002\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\"\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t\"#\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\t\"\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\t\"\u0019\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019\"\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\t\"\u0015\u0010\u001d\u001a\u00020\u000b*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f\"!\u0010 \u001a\u00020\u000f*\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\u000e8F¢\u0006\u0006\u001a\u0004\b!\u0010\"\"\u0015\u0010#\u001a\u00020\u0015*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b$\u0010%\"\u001b\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b'\u0010(\"\u001b\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b*\u0010+\"\u0015\u0010,\u001a\u00020-*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b.\u0010/\"\u0015\u00100\u001a\u000201*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b2\u00103¨\u0006H"},
   d2 = {"ROTATION_MATRIX", "", "", "getROTATION_MATRIX", "()[[I", "axes", "", "Lnet/minecraft/util/EnumFacing$Axis;", "getAxes", "()Ljava/util/List;", "axisDirs", "Lnet/minecraft/util/EnumFacing$AxisDirection;", "getAxisDirs", "boxEdges", "Lkotlin/Pair;", "Lnet/minecraft/util/EnumFacing;", "getBoxEdges", "faceCorners", "Lmods/octarinecore/common/FaceCorners;", "getFaceCorners", "forgeDirOffsets", "Lmods/octarinecore/common/Int3;", "getForgeDirOffsets", "forgeDirs", "getForgeDirs", "()[Lnet/minecraft/util/EnumFacing;", "[Lnet/minecraft/util/EnumFacing;", "forgeDirsHorizontal", "getForgeDirsHorizontal", "dir", "getDir", "(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/EnumFacing$AxisDirection;", "face", "getFace", "(Lkotlin/Pair;)Lnet/minecraft/util/EnumFacing;", "offset", "getOffset", "(Lnet/minecraft/util/EnumFacing;)Lmods/octarinecore/common/Int3;", "perpendiculars", "getPerpendiculars", "(Lnet/minecraft/util/EnumFacing;)Ljava/util/List;", "rotations", "getRotations", "(Lnet/minecraft/util/EnumFacing;)[Lnet/minecraft/util/EnumFacing;", "sign", "", "getSign", "(Lnet/minecraft/util/EnumFacing$AxisDirection;)Ljava/lang/String;", "vec", "Lmods/octarinecore/common/Double3;", "getVec", "(Lnet/minecraft/util/EnumFacing;)Lmods/octarinecore/common/Double3;", "nearestAngle", "T", "", "vector", "objs", "", "objAngle", "Lkotlin/Function1;", "nearestPosition", "vertex", "objPos", "rot", "Lmods/octarinecore/common/Rotation;", "axis", "plus", "Lnet/minecraft/util/math/BlockPos;", "other", "rotate", "times", "scale", "BetterFoliage-MC1.12"}
)
public final class GeometryKt {
   @NotNull
   private static final List axes;
   @NotNull
   private static final List axisDirs;
   @NotNull
   private static final EnumFacing[] forgeDirs;
   @NotNull
   private static final List forgeDirsHorizontal;
   @NotNull
   private static final List forgeDirOffsets;
   @NotNull
   private static final List boxEdges;
   @NotNull
   private static final List faceCorners;

   @NotNull
   public static final List getAxes() {
      return axes;
   }

   @NotNull
   public static final List getAxisDirs() {
      return axisDirs;
   }

   @NotNull
   public static final AxisDirection getDir(@NotNull EnumFacing $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      AxisDirection var10000 = $receiver.func_176743_c();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "axisDirection");
      return var10000;
   }

   @NotNull
   public static final String getSign(@NotNull AxisDirection $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      String var10000;
      switch(GeometryKt$WhenMappings.$EnumSwitchMapping$0[$receiver.ordinal()]) {
      case 1:
         var10000 = "+";
         break;
      case 2:
         var10000 = "-";
         break;
      default:
         throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   @NotNull
   public static final EnumFacing[] getForgeDirs() {
      return forgeDirs;
   }

   @NotNull
   public static final List getForgeDirsHorizontal() {
      return forgeDirsHorizontal;
   }

   @NotNull
   public static final List getForgeDirOffsets() {
      return forgeDirOffsets;
   }

   @NotNull
   public static final EnumFacing getFace(@NotNull Pair $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return Intrinsics.areEqual($receiver, TuplesKt.to(Axis.X, AxisDirection.POSITIVE)) ? EnumFacing.EAST : (Intrinsics.areEqual($receiver, TuplesKt.to(Axis.X, AxisDirection.NEGATIVE)) ? EnumFacing.WEST : (Intrinsics.areEqual($receiver, TuplesKt.to(Axis.Y, AxisDirection.POSITIVE)) ? EnumFacing.UP : (Intrinsics.areEqual($receiver, TuplesKt.to(Axis.Y, AxisDirection.NEGATIVE)) ? EnumFacing.DOWN : (Intrinsics.areEqual($receiver, TuplesKt.to(Axis.Z, AxisDirection.POSITIVE)) ? EnumFacing.SOUTH : EnumFacing.NORTH))));
   }

   @NotNull
   public static final List getPerpendiculars(@NotNull EnumFacing $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Iterable $receiver$iv = (Iterable)axes;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      Iterator var4 = $receiver$iv.iterator();

      Object item$iv$iv;
      while(var4.hasNext()) {
         item$iv$iv = var4.next();
         Axis it = (Axis)item$iv$iv;
         if (Intrinsics.areEqual(it, $receiver.func_176740_k()) ^ true) {
            destination$iv$iv.add(item$iv$iv);
         }
      }

      $receiver$iv = (Iterable)Utils.cross((Iterable)((List)destination$iv$iv), (Iterable)axisDirs);
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      var4 = $receiver$iv.iterator();

      while(var4.hasNext()) {
         item$iv$iv = var4.next();
         Pair it = (Pair)item$iv$iv;
         EnumFacing var11 = getFace(it);
         destination$iv$iv.add(var11);
      }

      return (List)destination$iv$iv;
   }

   @NotNull
   public static final Int3 getOffset(@NotNull EnumFacing $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return (Int3)forgeDirOffsets.get($receiver.ordinal());
   }

   @NotNull
   public static final int[][] getROTATION_MATRIX() {
      return (int[][])(new int[][]{{0, 1, 4, 5, 3, 2, 6}, {0, 1, 5, 4, 2, 3, 6}, {5, 4, 2, 3, 0, 1, 6}, {4, 5, 2, 3, 1, 0, 6}, {2, 3, 1, 0, 4, 5, 6}, {3, 2, 0, 1, 4, 5, 6}});
   }

   @NotNull
   public static final Double3 times(@NotNull EnumFacing $receiver, double scale) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Vec3i var10002 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "directionVec");
      double var3 = (double)var10002.func_177958_n() * scale;
      Vec3i var10003 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "directionVec");
      double var4 = (double)var10003.func_177956_o() * scale;
      Vec3i var10004 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "directionVec");
      return new Double3(var3, var4, (double)var10004.func_177952_p() * scale);
   }

   @NotNull
   public static final Double3 getVec(@NotNull EnumFacing $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Vec3i var10002 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "directionVec");
      double var1 = (double)var10002.func_177958_n();
      Vec3i var10003 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "directionVec");
      double var2 = (double)var10003.func_177956_o();
      Vec3i var10004 = $receiver.func_176730_m();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "directionVec");
      return new Double3(var1, var2, (double)var10004.func_177952_p());
   }

   @NotNull
   public static final BlockPos plus(@NotNull BlockPos $receiver, @NotNull Int3 other) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return new BlockPos($receiver.func_177958_n() + other.getX(), $receiver.func_177956_o() + other.getY(), $receiver.func_177952_p() + other.getZ());
   }

   @NotNull
   public static final EnumFacing[] getRotations(@NotNull EnumFacing $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      int size$iv = 6;
      Object[] result$iv = new EnumFacing[size$iv];
      int i$iv = 0;

      for(int var4 = result$iv.length; i$iv < var4; ++i$iv) {
         EnumFacing var10 = EnumFacing.values()[getROTATION_MATRIX()[$receiver.ordinal()][i$iv]];
         result$iv[i$iv] = var10;
      }

      return result$iv;
   }

   @NotNull
   public static final EnumFacing rotate(@NotNull EnumFacing $receiver, @NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return rot.getForward()[$receiver.ordinal()];
   }

   @NotNull
   public static final Rotation rot(@NotNull EnumFacing axis) {
      Intrinsics.checkParameterIsNotNull(axis, "axis");
      return Rotation.Companion.getRot90()[axis.ordinal()];
   }

   @NotNull
   public static final List getBoxEdges() {
      return boxEdges;
   }

   @NotNull
   public static final Pair nearestPosition(@NotNull Double3 vertex, @NotNull Iterable objs, @NotNull Function1 objPos) {
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      Intrinsics.checkParameterIsNotNull(objs, "objs");
      Intrinsics.checkParameterIsNotNull(objPos, "objPos");
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(objs, 10)));
      Iterator var6 = objs.iterator();

      Object e$iv;
      while(var6.hasNext()) {
         e$iv = var6.next();
         Pair var15 = TuplesKt.to(e$iv, ((Double3)objPos.invoke(e$iv)).minus(vertex).getLength());
         destination$iv$iv.add(var15);
      }

      Iterable $receiver$iv = (Iterable)((List)destination$iv$iv);
      Iterator iterator$iv = $receiver$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object minElem$iv = iterator$iv.next();
         Pair it = (Pair)minElem$iv;
         double minValue$iv = ((Number)it.getSecond()).doubleValue();

         while(iterator$iv.hasNext()) {
            e$iv = iterator$iv.next();
            Pair it = (Pair)e$iv;
            double v$iv = ((Number)it.getSecond()).doubleValue();
            if (Double.compare(minValue$iv, v$iv) > 0) {
               minElem$iv = e$iv;
               minValue$iv = v$iv;
            }
         }

         var10000 = minElem$iv;
      }

      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return (Pair)var10000;
   }

   @NotNull
   public static final Pair nearestAngle(@NotNull Double3 vector, @NotNull Iterable objs, @NotNull Function1 objAngle) {
      Intrinsics.checkParameterIsNotNull(vector, "vector");
      Intrinsics.checkParameterIsNotNull(objs, "objs");
      Intrinsics.checkParameterIsNotNull(objAngle, "objAngle");
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(objs, 10)));
      Iterator var6 = objs.iterator();

      Object e$iv;
      while(var6.hasNext()) {
         e$iv = var6.next();
         Pair var15 = TuplesKt.to(e$iv, ((Double3)objAngle.invoke(e$iv)).dot(vector));
         destination$iv$iv.add(var15);
      }

      Iterable $receiver$iv = (Iterable)((List)destination$iv$iv);
      Iterator iterator$iv = $receiver$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object maxElem$iv = iterator$iv.next();
         Pair it = (Pair)maxElem$iv;
         double maxValue$iv = ((Number)it.getSecond()).doubleValue();

         while(iterator$iv.hasNext()) {
            e$iv = iterator$iv.next();
            Pair it = (Pair)e$iv;
            double v$iv = ((Number)it.getSecond()).doubleValue();
            if (Double.compare(maxValue$iv, v$iv) < 0) {
               maxElem$iv = e$iv;
               maxValue$iv = v$iv;
            }
         }

         var10000 = maxElem$iv;
      }

      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return (Pair)var10000;
   }

   @NotNull
   public static final List getFaceCorners() {
      return faceCorners;
   }

   static {
      axes = CollectionsKt.listOf(new Axis[]{Axis.X, Axis.Y, Axis.Z});
      axisDirs = CollectionsKt.listOf(new AxisDirection[]{AxisDirection.POSITIVE, AxisDirection.NEGATIVE});
      forgeDirs = EnumFacing.values();
      forgeDirsHorizontal = CollectionsKt.listOf(new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST});
      Object[] $receiver$iv = (Object[])forgeDirs;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      int var3 = $receiver$iv.length;

      int var4;
      Object item$iv$iv;
      EnumFacing face1;
      for(var4 = 0; var4 < var3; ++var4) {
         item$iv$iv = $receiver$iv$iv[var4];
         face1 = (EnumFacing)item$iv$iv;
         Int3 var24 = new Int3(face1);
         destination$iv$iv.add(var24);
      }

      forgeDirOffsets = (List)destination$iv$iv;
      $receiver$iv = (Object[])forgeDirs;
      $receiver$iv$iv = $receiver$iv;
      destination$iv$iv = (Collection)(new ArrayList());
      var3 = $receiver$iv.length;

      for(var4 = 0; var4 < var3; ++var4) {
         item$iv$iv = $receiver$iv$iv[var4];
         face1 = (EnumFacing)item$iv$iv;
         Object[] $receiver$iv = (Object[])forgeDirs;
         Object[] $receiver$iv$iv = $receiver$iv;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int var10 = $receiver$iv.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Object element$iv$iv = $receiver$iv$iv[var11];
            EnumFacing it = (EnumFacing)element$iv$iv;
            if (it.func_176740_k().compareTo((Enum)face1.func_176740_k()) > 0) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         Iterable $receiver$iv = (Iterable)((List)destination$iv$iv);
         destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
         Iterator var26 = $receiver$iv.iterator();

         while(var26.hasNext()) {
            Object item$iv$iv = var26.next();
            EnumFacing it = (EnumFacing)item$iv$iv;
            Pair var15 = TuplesKt.to(face1, it);
            destination$iv$iv.add(var15);
         }

         Iterable list$iv$iv = (Iterable)((List)destination$iv$iv);
         CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
      }

      boxEdges = (List)destination$iv$iv;
      $receiver$iv = (Object[])forgeDirs;
      $receiver$iv$iv = $receiver$iv;
      destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      var3 = $receiver$iv.length;

      for(var4 = 0; var4 < var3; ++var4) {
         item$iv$iv = $receiver$iv$iv[var4];
         face1 = (EnumFacing)item$iv$iv;
         FaceCorners var10000;
         switch(GeometryKt$WhenMappings.$EnumSwitchMapping$1[face1.ordinal()]) {
         case 1:
            var10000 = new FaceCorners(EnumFacing.SOUTH, EnumFacing.WEST);
            break;
         case 2:
            var10000 = new FaceCorners(EnumFacing.SOUTH, EnumFacing.EAST);
            break;
         case 3:
            var10000 = new FaceCorners(EnumFacing.WEST, EnumFacing.UP);
            break;
         case 4:
            var10000 = new FaceCorners(EnumFacing.UP, EnumFacing.WEST);
            break;
         case 5:
            var10000 = new FaceCorners(EnumFacing.SOUTH, EnumFacing.UP);
            break;
         case 6:
            var10000 = new FaceCorners(EnumFacing.SOUTH, EnumFacing.DOWN);
            break;
         default:
            throw new NoWhenBranchMatchedException();
         }

         FaceCorners var29 = var10000;
         destination$iv$iv.add(var29);
      }

      faceCorners = (List)destination$iv$iv;
   }
}
