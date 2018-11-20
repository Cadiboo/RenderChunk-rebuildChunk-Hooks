package mods.octarinecore.common.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"},
   d2 = {"Lmods/octarinecore/common/config/ModelTextureList;", "", "modelLocation", "Lnet/minecraft/util/ResourceLocation;", "textureNames", "", "", "(Lnet/minecraft/util/ResourceLocation;Ljava/util/List;)V", "getModelLocation", "()Lnet/minecraft/util/ResourceLocation;", "getTextureNames", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "BetterFoliage-MC1.12"}
)
public final class ModelTextureList {
   @NotNull
   private final ResourceLocation modelLocation;
   @NotNull
   private final List textureNames;

   @NotNull
   public final ResourceLocation getModelLocation() {
      return this.modelLocation;
   }

   @NotNull
   public final List getTextureNames() {
      return this.textureNames;
   }

   public ModelTextureList(@NotNull ResourceLocation modelLocation, @NotNull List textureNames) {
      Intrinsics.checkParameterIsNotNull(modelLocation, "modelLocation");
      Intrinsics.checkParameterIsNotNull(textureNames, "textureNames");
      super();
      this.modelLocation = modelLocation;
      this.textureNames = textureNames;
   }

   @NotNull
   public final ResourceLocation component1() {
      return this.modelLocation;
   }

   @NotNull
   public final List component2() {
      return this.textureNames;
   }

   @NotNull
   public final ModelTextureList copy(@NotNull ResourceLocation modelLocation, @NotNull List textureNames) {
      Intrinsics.checkParameterIsNotNull(modelLocation, "modelLocation");
      Intrinsics.checkParameterIsNotNull(textureNames, "textureNames");
      return new ModelTextureList(modelLocation, textureNames);
   }

   public String toString() {
      return "ModelTextureList(modelLocation=" + this.modelLocation + ", textureNames=" + this.textureNames + ")";
   }

   public int hashCode() {
      return (this.modelLocation != null ? this.modelLocation.hashCode() : 0) * 31 + (this.textureNames != null ? this.textureNames.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof ModelTextureList) {
            ModelTextureList var2 = (ModelTextureList)var1;
            if (Intrinsics.areEqual(this.modelLocation, var2.modelLocation) && Intrinsics.areEqual(this.textureNames, var2.textureNames)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
