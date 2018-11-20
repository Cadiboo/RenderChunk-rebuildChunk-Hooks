package mods.octarinecore.client.resource;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"},
   d2 = {"Lmods/octarinecore/client/resource/ModelVariant;", "", "state", "Lnet/minecraft/block/state/IBlockState;", "modelLocation", "Lnet/minecraft/util/ResourceLocation;", "weight", "", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/ResourceLocation;I)V", "getModelLocation", "()Lnet/minecraft/util/ResourceLocation;", "getState", "()Lnet/minecraft/block/state/IBlockState;", "getWeight", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "BetterFoliage-MC1.12"}
)
public final class ModelVariant {
   @NotNull
   private final IBlockState state;
   @Nullable
   private final ResourceLocation modelLocation;
   private final int weight;

   @NotNull
   public final IBlockState getState() {
      return this.state;
   }

   @Nullable
   public final ResourceLocation getModelLocation() {
      return this.modelLocation;
   }

   public final int getWeight() {
      return this.weight;
   }

   public ModelVariant(@NotNull IBlockState state, @Nullable ResourceLocation modelLocation, int weight) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      super();
      this.state = state;
      this.modelLocation = modelLocation;
      this.weight = weight;
   }

   @NotNull
   public final IBlockState component1() {
      return this.state;
   }

   @Nullable
   public final ResourceLocation component2() {
      return this.modelLocation;
   }

   public final int component3() {
      return this.weight;
   }

   @NotNull
   public final ModelVariant copy(@NotNull IBlockState state, @Nullable ResourceLocation modelLocation, int weight) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return new ModelVariant(state, modelLocation, weight);
   }

   public String toString() {
      return "ModelVariant(state=" + this.state + ", modelLocation=" + this.modelLocation + ", weight=" + this.weight + ")";
   }

   public int hashCode() {
      return ((this.state != null ? this.state.hashCode() : 0) * 31 + (this.modelLocation != null ? this.modelLocation.hashCode() : 0)) * 31 + this.weight;
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof ModelVariant) {
            ModelVariant var2 = (ModelVariant)var1;
            if (Intrinsics.areEqual(this.state, var2.state) && Intrinsics.areEqual(this.modelLocation, var2.modelLocation) && this.weight == var2.weight) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
