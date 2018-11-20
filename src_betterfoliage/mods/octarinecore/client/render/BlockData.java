package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.state.IBlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"},
   d2 = {"Lmods/octarinecore/client/render/BlockData;", "", "state", "Lnet/minecraft/block/state/IBlockState;", "color", "", "brightness", "(Lnet/minecraft/block/state/IBlockState;II)V", "getBrightness", "()I", "getColor", "getState", "()Lnet/minecraft/block/state/IBlockState;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "BetterFoliage-MC1.12"}
)
public final class BlockData {
   @NotNull
   private final IBlockState state;
   private final int color;
   private final int brightness;

   @NotNull
   public final IBlockState getState() {
      return this.state;
   }

   public final int getColor() {
      return this.color;
   }

   public final int getBrightness() {
      return this.brightness;
   }

   public BlockData(@NotNull IBlockState state, int color, int brightness) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      super();
      this.state = state;
      this.color = color;
      this.brightness = brightness;
   }

   @NotNull
   public final IBlockState component1() {
      return this.state;
   }

   public final int component2() {
      return this.color;
   }

   public final int component3() {
      return this.brightness;
   }

   @NotNull
   public final BlockData copy(@NotNull IBlockState state, int color, int brightness) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return new BlockData(state, color, brightness);
   }

   public String toString() {
      return "BlockData(state=" + this.state + ", color=" + this.color + ", brightness=" + this.brightness + ")";
   }

   public int hashCode() {
      return ((this.state != null ? this.state.hashCode() : 0) * 31 + this.color) * 31 + this.brightness;
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof BlockData) {
            BlockData var2 = (BlockData)var1;
            if (Intrinsics.areEqual(this.state, var2.state) && this.color == var2.color && this.brightness == var2.brightness) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
