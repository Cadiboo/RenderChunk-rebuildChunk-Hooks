package mods.betterfoliage.client.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.integration.OptifineCTM;
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
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b¢\u0006\u0002\u0010\tJ\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0005HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00050\bHÆ\u0003J9\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bHÆ\u0001J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*HÖ\u0003J\t\u0010+\u001a\u00020\u000fHÖ\u0001J\t\u0010,\u001a\u00020-HÖ\u0001R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR2\u0010\f\u001a \u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00050\rj\u0002`\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0017¢\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R2\u0010\u001b\u001a \u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00050\rj\u0002`\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR2\u0010\u001f\u001a \u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00050\rj\u0002`\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015¨\u0006."},
   d2 = {"Lmods/betterfoliage/client/render/StaticColumnInfo;", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "axis", "Lnet/minecraft/util/EnumFacing$Axis;", "topTexture", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "bottomTexture", "sideTextures", "", "(Lnet/minecraft/util/EnumFacing$Axis;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Ljava/util/List;)V", "getAxis", "()Lnet/minecraft/util/EnumFacing$Axis;", "bottom", "Lkotlin/Function3;", "Lmods/octarinecore/client/render/ShadingContext;", "", "Lmods/octarinecore/client/render/Quad;", "Lmods/octarinecore/client/render/QuadIconResolver;", "getBottom", "()Lkotlin/jvm/functions/Function3;", "getBottomTexture", "()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "dirToIdx", "", "getDirToIdx", "()[Ljava/lang/Integer;", "[Ljava/lang/Integer;", "side", "getSide", "getSideTextures", "()Ljava/util/List;", "top", "getTop", "getTopTexture", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "toString", "", "BetterFoliage-MC1.12"}
)
public final class StaticColumnInfo implements IColumnTextureInfo {
   @NotNull
   private final Integer[] dirToIdx;
   @NotNull
   private final Function3 top;
   @NotNull
   private final Function3 bottom;
   @NotNull
   private final Function3 side;
   @Nullable
   private final Axis axis;
   @NotNull
   private final TextureAtlasSprite topTexture;
   @NotNull
   private final TextureAtlasSprite bottomTexture;
   @NotNull
   private final List sideTextures;

   @NotNull
   public final Integer[] getDirToIdx() {
      return this.dirToIdx;
   }

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
   public final TextureAtlasSprite getTopTexture() {
      return this.topTexture;
   }

   @NotNull
   public final TextureAtlasSprite getBottomTexture() {
      return this.bottomTexture;
   }

   @NotNull
   public final List getSideTextures() {
      return this.sideTextures;
   }

   public StaticColumnInfo(@Nullable Axis axis, @NotNull TextureAtlasSprite topTexture, @NotNull TextureAtlasSprite bottomTexture, @NotNull List sideTextures) {
      Intrinsics.checkParameterIsNotNull(topTexture, "topTexture");
      Intrinsics.checkParameterIsNotNull(bottomTexture, "bottomTexture");
      Intrinsics.checkParameterIsNotNull(sideTextures, "sideTextures");
      super();
      this.axis = axis;
      this.topTexture = topTexture;
      this.bottomTexture = bottomTexture;
      this.sideTextures = sideTextures;
      this.dirToIdx = new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(5)};
      this.top = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int $noName_1, @NotNull Quad $noName_2) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
            return OptifineCTM.INSTANCE.override(StaticColumnInfo.this.getTopTexture(), RendererHolder.getBlockContext(), GeometryKt.rotate(EnumFacing.UP, ctx.getRotation()));
         }
      });
      this.bottom = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int $noName_1, @NotNull Quad $noName_2) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
            return OptifineCTM.INSTANCE.override(StaticColumnInfo.this.getBottomTexture(), RendererHolder.getBlockContext(), GeometryKt.rotate(EnumFacing.DOWN, ctx.getRotation()));
         }
      });
      this.side = (Function3)(new Function3() {
         @NotNull
         public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int idx, @NotNull Quad $noName_2) {
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
            EnumFacing worldFace = GeometryKt.rotate((idx & 1) == 0 ? EnumFacing.SOUTH : EnumFacing.EAST, ctx.getRotation());
            TextureAtlasSprite baseTexture = (TextureAtlasSprite)StaticColumnInfo.this.getSideTextures().get((RendererHolder.getBlockContext().random(1) + StaticColumnInfo.this.getDirToIdx()[worldFace.ordinal()].intValue()) % StaticColumnInfo.this.getSideTextures().size());
            return OptifineCTM.INSTANCE.override(baseTexture, RendererHolder.getBlockContext(), worldFace);
         }
      });
   }

   @Nullable
   public final Axis component1() {
      return this.getAxis();
   }

   @NotNull
   public final TextureAtlasSprite component2() {
      return this.topTexture;
   }

   @NotNull
   public final TextureAtlasSprite component3() {
      return this.bottomTexture;
   }

   @NotNull
   public final List component4() {
      return this.sideTextures;
   }

   @NotNull
   public final StaticColumnInfo copy(@Nullable Axis axis, @NotNull TextureAtlasSprite topTexture, @NotNull TextureAtlasSprite bottomTexture, @NotNull List sideTextures) {
      Intrinsics.checkParameterIsNotNull(topTexture, "topTexture");
      Intrinsics.checkParameterIsNotNull(bottomTexture, "bottomTexture");
      Intrinsics.checkParameterIsNotNull(sideTextures, "sideTextures");
      return new StaticColumnInfo(axis, topTexture, bottomTexture, sideTextures);
   }

   public String toString() {
      return "StaticColumnInfo(axis=" + this.getAxis() + ", topTexture=" + this.topTexture + ", bottomTexture=" + this.bottomTexture + ", sideTextures=" + this.sideTextures + ")";
   }

   public int hashCode() {
      Axis var10000 = this.getAxis();
      return (((var10000 != null ? var10000.hashCode() : 0) * 31 + (this.topTexture != null ? this.topTexture.hashCode() : 0)) * 31 + (this.bottomTexture != null ? this.bottomTexture.hashCode() : 0)) * 31 + (this.sideTextures != null ? this.sideTextures.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof StaticColumnInfo) {
            StaticColumnInfo var2 = (StaticColumnInfo)var1;
            if (Intrinsics.areEqual(this.getAxis(), var2.getAxis()) && Intrinsics.areEqual(this.topTexture, var2.topTexture) && Intrinsics.areEqual(this.bottomTexture, var2.bottomTexture) && Intrinsics.areEqual(this.sideTextures, var2.sideTextures)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
