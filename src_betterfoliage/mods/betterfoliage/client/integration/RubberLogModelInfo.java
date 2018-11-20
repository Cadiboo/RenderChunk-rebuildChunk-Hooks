package mods.betterfoliage.client.integration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\bHÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"},
   d2 = {"Lmods/betterfoliage/client/integration/RubberLogModelInfo;", "", "axis", "Lnet/minecraft/util/EnumFacing$Axis;", "spotDir", "Lnet/minecraft/util/EnumFacing;", "textures", "", "", "(Lnet/minecraft/util/EnumFacing$Axis;Lnet/minecraft/util/EnumFacing;Ljava/util/List;)V", "getAxis", "()Lnet/minecraft/util/EnumFacing$Axis;", "getSpotDir", "()Lnet/minecraft/util/EnumFacing;", "getTextures", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "BetterFoliage-MC1.12"}
)
public final class RubberLogModelInfo {
   @Nullable
   private final Axis axis;
   @Nullable
   private final EnumFacing spotDir;
   @NotNull
   private final List textures;

   @Nullable
   public final Axis getAxis() {
      return this.axis;
   }

   @Nullable
   public final EnumFacing getSpotDir() {
      return this.spotDir;
   }

   @NotNull
   public final List getTextures() {
      return this.textures;
   }

   public RubberLogModelInfo(@Nullable Axis axis, @Nullable EnumFacing spotDir, @NotNull List textures) {
      Intrinsics.checkParameterIsNotNull(textures, "textures");
      super();
      this.axis = axis;
      this.spotDir = spotDir;
      this.textures = textures;
   }

   @Nullable
   public final Axis component1() {
      return this.axis;
   }

   @Nullable
   public final EnumFacing component2() {
      return this.spotDir;
   }

   @NotNull
   public final List component3() {
      return this.textures;
   }

   @NotNull
   public final RubberLogModelInfo copy(@Nullable Axis axis, @Nullable EnumFacing spotDir, @NotNull List textures) {
      Intrinsics.checkParameterIsNotNull(textures, "textures");
      return new RubberLogModelInfo(axis, spotDir, textures);
   }

   public String toString() {
      return "RubberLogModelInfo(axis=" + this.axis + ", spotDir=" + this.spotDir + ", textures=" + this.textures + ")";
   }

   public int hashCode() {
      return ((this.axis != null ? this.axis.hashCode() : 0) * 31 + (this.spotDir != null ? this.spotDir.hashCode() : 0)) * 31 + (this.textures != null ? this.textures.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof RubberLogModelInfo) {
            RubberLogModelInfo var2 = (RubberLogModelInfo)var1;
            if (Intrinsics.areEqual(this.axis, var2.axis) && Intrinsics.areEqual(this.spotDir, var2.spotDir) && Intrinsics.areEqual(this.textures, var2.textures)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
