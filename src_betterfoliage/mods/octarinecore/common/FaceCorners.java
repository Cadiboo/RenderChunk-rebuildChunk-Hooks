package mods.octarinecore.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005BU\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\u000bJ\u0015\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003J\u0015\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003J\u0015\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003J\u0015\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0003Ja\u0010\u001e\u001a\u00020\u00002\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00072\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00072\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00072\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007HÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020#HÖ\u0001J\t\u0010$\u001a\u00020%HÖ\u0001R%\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00070\r¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR#\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00070\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016¨\u0006&"},
   d2 = {"Lmods/octarinecore/common/FaceCorners;", "", "top", "Lnet/minecraft/util/EnumFacing;", "left", "(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;)V", "topLeft", "Lkotlin/Pair;", "topRight", "bottomLeft", "bottomRight", "(Lkotlin/Pair;Lkotlin/Pair;Lkotlin/Pair;Lkotlin/Pair;)V", "asArray", "", "getAsArray", "()[Lkotlin/Pair;", "[Lkotlin/Pair;", "asList", "", "getAsList", "()Ljava/util/List;", "getBottomLeft", "()Lkotlin/Pair;", "getBottomRight", "getTopLeft", "getTopRight", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "BetterFoliage-MC1.12"}
)
public final class FaceCorners {
   @NotNull
   private final Pair[] asArray;
   @NotNull
   private final List asList;
   @NotNull
   private final Pair topLeft;
   @NotNull
   private final Pair topRight;
   @NotNull
   private final Pair bottomLeft;
   @NotNull
   private final Pair bottomRight;

   @NotNull
   public final Pair[] getAsArray() {
      return this.asArray;
   }

   @NotNull
   public final List getAsList() {
      return this.asList;
   }

   @NotNull
   public final Pair getTopLeft() {
      return this.topLeft;
   }

   @NotNull
   public final Pair getTopRight() {
      return this.topRight;
   }

   @NotNull
   public final Pair getBottomLeft() {
      return this.bottomLeft;
   }

   @NotNull
   public final Pair getBottomRight() {
      return this.bottomRight;
   }

   public FaceCorners(@NotNull Pair topLeft, @NotNull Pair topRight, @NotNull Pair bottomLeft, @NotNull Pair bottomRight) {
      Intrinsics.checkParameterIsNotNull(topLeft, "topLeft");
      Intrinsics.checkParameterIsNotNull(topRight, "topRight");
      Intrinsics.checkParameterIsNotNull(bottomLeft, "bottomLeft");
      Intrinsics.checkParameterIsNotNull(bottomRight, "bottomRight");
      super();
      this.topLeft = topLeft;
      this.topRight = topRight;
      this.bottomLeft = bottomLeft;
      this.bottomRight = bottomRight;
      this.asArray = new Pair[]{this.topLeft, this.topRight, this.bottomLeft, this.bottomRight};
      this.asList = CollectionsKt.listOf(new Pair[]{this.topLeft, this.topRight, this.bottomLeft, this.bottomRight});
   }

   public FaceCorners(@NotNull EnumFacing top, @NotNull EnumFacing left) {
      Intrinsics.checkParameterIsNotNull(top, "top");
      Intrinsics.checkParameterIsNotNull(left, "left");
      this(TuplesKt.to(top, left), TuplesKt.to(top, left.func_176734_d()), TuplesKt.to(top.func_176734_d(), left), TuplesKt.to(top.func_176734_d(), left.func_176734_d()));
   }

   @NotNull
   public final Pair component1() {
      return this.topLeft;
   }

   @NotNull
   public final Pair component2() {
      return this.topRight;
   }

   @NotNull
   public final Pair component3() {
      return this.bottomLeft;
   }

   @NotNull
   public final Pair component4() {
      return this.bottomRight;
   }

   @NotNull
   public final FaceCorners copy(@NotNull Pair topLeft, @NotNull Pair topRight, @NotNull Pair bottomLeft, @NotNull Pair bottomRight) {
      Intrinsics.checkParameterIsNotNull(topLeft, "topLeft");
      Intrinsics.checkParameterIsNotNull(topRight, "topRight");
      Intrinsics.checkParameterIsNotNull(bottomLeft, "bottomLeft");
      Intrinsics.checkParameterIsNotNull(bottomRight, "bottomRight");
      return new FaceCorners(topLeft, topRight, bottomLeft, bottomRight);
   }

   public String toString() {
      return "FaceCorners(topLeft=" + this.topLeft + ", topRight=" + this.topRight + ", bottomLeft=" + this.bottomLeft + ", bottomRight=" + this.bottomRight + ")";
   }

   public int hashCode() {
      return (((this.topLeft != null ? this.topLeft.hashCode() : 0) * 31 + (this.topRight != null ? this.topRight.hashCode() : 0)) * 31 + (this.bottomLeft != null ? this.bottomLeft.hashCode() : 0)) * 31 + (this.bottomRight != null ? this.bottomRight.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof FaceCorners) {
            FaceCorners var2 = (FaceCorners)var1;
            if (Intrinsics.areEqual(this.topLeft, var2.topLeft) && Intrinsics.areEqual(this.topRight, var2.topRight) && Intrinsics.areEqual(this.bottomLeft, var2.bottomLeft) && Intrinsics.areEqual(this.bottomRight, var2.bottomRight)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
