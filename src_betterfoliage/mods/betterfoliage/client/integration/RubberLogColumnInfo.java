package mods.betterfoliage.client.integration;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.render.IColumnTextureInfo;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.common.GeometryKt;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0007HÆ\u0003J\t\u0010#\u001a\u00020\u0007HÆ\u0003J\t\u0010$\u001a\u00020\u0007HÆ\u0003J\t\u0010%\u001a\u00020\u0007HÆ\u0003JG\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u0007HÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*HÖ\u0003J\t\u0010+\u001a\u00020\u0011HÖ\u0001J\t\u0010,\u001a\u00020-HÖ\u0001R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR,\u0010\u000e\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00070\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R,\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00070\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R,\u0010\u001d\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00070\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0016¨\u0006."},
   d2 = {"Lmods/betterfoliage/client/integration/RubberLogColumnInfo;", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "axis", "Lnet/minecraft/util/EnumFacing$Axis;", "spotDir", "Lnet/minecraft/util/EnumFacing;", "topTexture", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "bottomTexture", "sideTexture", "spotTexture", "(Lnet/minecraft/util/EnumFacing$Axis;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V", "getAxis", "()Lnet/minecraft/util/EnumFacing$Axis;", "bottom", "Lkotlin/Function3;", "Lmods/octarinecore/client/render/ShadingContext;", "", "Lmods/octarinecore/client/render/Quad;", "getBottom", "()Lkotlin/jvm/functions/Function3;", "getBottomTexture", "()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "side", "getSide", "getSideTexture", "getSpotDir", "()Lnet/minecraft/util/EnumFacing;", "getSpotTexture", "top", "getTop", "getTopTexture", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "", "hashCode", "toString", "", "BetterFoliage-MC1.12"}
)
public final class RubberLogColumnInfo implements IColumnTextureInfo {
   @NotNull
   private final Function3 top;
   @NotNull
   private final Function3 bottom;
   @NotNull
   private final Function3 side;
   @Nullable
   private final Axis axis;
   @NotNull
   private final EnumFacing spotDir;
   @NotNull
   private final TextureAtlasSprite topTexture;
   @NotNull
   private final TextureAtlasSprite bottomTexture;
   @NotNull
   private final TextureAtlasSprite sideTexture;
   @NotNull
   private final TextureAtlasSprite spotTexture;

   @NotNull
   public Function3 getTop() {
      return this.top;
   }

   @NotNull
   public Function3 getBottom() {
      return this.bottom;
   }

   @NotNull
   public Function3 getSide() {
      return this.side;
   }

   @Nullable
   public Axis getAxis() {
      return this.axis;
   }

   @NotNull
   public final EnumFacing getSpotDir() {
      return this.spotDir;
   }

   @NotNull
   public final TextureAtlasSprite getTopTexture() {
      return this.topTexture;
   }

   @NotNull
   public final TextureAtlasSprite getBottomTexture() {
      return this.bottomTexture;
   }

   @NotNull
   public final TextureAtlasSprite getSideTexture() {
      return this.sideTexture;
   }

   @NotNull
   public final TextureAtlasSprite getSpotTexture() {
      return this.spotTexture;
   }

   public RubberLogColumnInfo(@Nullable Axis axis, @NotNull EnumFacing spotDir, @NotNull TextureAtlasSprite topTexture, @NotNull TextureAtlasSprite bottomTexture, @NotNull TextureAtlasSprite sideTexture, @NotNull TextureAtlasSprite spotTexture) {
      Intrinsics.checkParameterIsNotNull(spotDir, "spotDir");
      Intrinsics.checkParameterIsNotNull(topTexture, "topTexture");
      Intrinsics.checkParameterIsNotNull(bottomTexture, "bottomTexture");
      Intrinsics.checkParameterIsNotNull(sideTexture, "sideTexture");
      Intrinsics.checkParameterIsNotNull(spotTexture, "spotTexture");
      super();
      this.axis = axis;
      this.spotDir = spotDir;
      this.topTexture = topTexture;
      this.bottomTexture = bottomTexture;
      this.sideTexture = sideTexture;
      this.spotTexture = spotTexture;
      this.top = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int idx, @NotNull Quad quad) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            return OptifineCTM.INSTANCE.override(RubberLogColumnInfo.this.getTopTexture(), RendererHolder.getBlockContext(), GeometryKt.rotate(EnumFacing.UP, ctx.getRotation()));
         }
      });
      this.bottom = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int idx, @NotNull Quad quad) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            return OptifineCTM.INSTANCE.override(RubberLogColumnInfo.this.getBottomTexture(), RendererHolder.getBlockContext(), GeometryKt.rotate(EnumFacing.DOWN, ctx.getRotation()));
         }
      });
      this.side = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int idx, @NotNull Quad quad) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            EnumFacing worldRelativeSide = GeometryKt.rotate((idx & 1) == 0 ? EnumFacing.SOUTH : EnumFacing.EAST, ctx.getRotation());
            TextureAtlasSprite texture = Intrinsics.areEqual(worldRelativeSide, RubberLogColumnInfo.this.getSpotDir()) ? RubberLogColumnInfo.this.getSpotTexture() : RubberLogColumnInfo.this.getSideTexture();
            return OptifineCTM.INSTANCE.override(texture, RendererHolder.getBlockContext(), worldRelativeSide);
         }
      });
   }

   @Nullable
   public final Axis component1() {
      return this.getAxis();
   }

   @NotNull
   public final EnumFacing component2() {
      return this.spotDir;
   }

   @NotNull
   public final TextureAtlasSprite component3() {
      return this.topTexture;
   }

   @NotNull
   public final TextureAtlasSprite component4() {
      return this.bottomTexture;
   }

   @NotNull
   public final TextureAtlasSprite component5() {
      return this.sideTexture;
   }

   @NotNull
   public final TextureAtlasSprite component6() {
      return this.spotTexture;
   }

   @NotNull
   public final RubberLogColumnInfo copy(@Nullable Axis axis, @NotNull EnumFacing spotDir, @NotNull TextureAtlasSprite topTexture, @NotNull TextureAtlasSprite bottomTexture, @NotNull TextureAtlasSprite sideTexture, @NotNull TextureAtlasSprite spotTexture) {
      Intrinsics.checkParameterIsNotNull(spotDir, "spotDir");
      Intrinsics.checkParameterIsNotNull(topTexture, "topTexture");
      Intrinsics.checkParameterIsNotNull(bottomTexture, "bottomTexture");
      Intrinsics.checkParameterIsNotNull(sideTexture, "sideTexture");
      Intrinsics.checkParameterIsNotNull(spotTexture, "spotTexture");
      return new RubberLogColumnInfo(axis, spotDir, topTexture, bottomTexture, sideTexture, spotTexture);
   }

   public String toString() {
      return "RubberLogColumnInfo(axis=" + this.getAxis() + ", spotDir=" + this.spotDir + ", topTexture=" + this.topTexture + ", bottomTexture=" + this.bottomTexture + ", sideTexture=" + this.sideTexture + ", spotTexture=" + this.spotTexture + ")";
   }

   public int hashCode() {
      Axis var10000 = this.getAxis();
      return (((((var10000 != null ? var10000.hashCode() : 0) * 31 + (this.spotDir != null ? this.spotDir.hashCode() : 0)) * 31 + (this.topTexture != null ? this.topTexture.hashCode() : 0)) * 31 + (this.bottomTexture != null ? this.bottomTexture.hashCode() : 0)) * 31 + (this.sideTexture != null ? this.sideTexture.hashCode() : 0)) * 31 + (this.spotTexture != null ? this.spotTexture.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof RubberLogColumnInfo) {
            RubberLogColumnInfo var2 = (RubberLogColumnInfo)var1;
            if (Intrinsics.areEqual(this.getAxis(), var2.getAxis()) && Intrinsics.areEqual(this.spotDir, var2.spotDir) && Intrinsics.areEqual(this.topTexture, var2.topTexture) && Intrinsics.areEqual(this.bottomTexture, var2.bottomTexture) && Intrinsics.areEqual(this.sideTexture, var2.sideTexture) && Intrinsics.areEqual(this.spotTexture, var2.spotTexture)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
