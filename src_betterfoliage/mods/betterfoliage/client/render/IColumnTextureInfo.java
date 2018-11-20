package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
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
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R0\u0010\u0006\u001a \u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007j\u0002`\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR0\u0010\u000f\u001a \u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007j\u0002`\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR0\u0010\u0011\u001a \u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007j\u0002`\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0013"},
   d2 = {"Lmods/betterfoliage/client/render/IColumnTextureInfo;", "", "axis", "Lnet/minecraft/util/EnumFacing$Axis;", "getAxis", "()Lnet/minecraft/util/EnumFacing$Axis;", "bottom", "Lkotlin/Function3;", "Lmods/octarinecore/client/render/ShadingContext;", "", "Lmods/octarinecore/client/render/Quad;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "Lmods/octarinecore/client/render/QuadIconResolver;", "getBottom", "()Lkotlin/jvm/functions/Function3;", "side", "getSide", "top", "getTop", "BetterFoliage-MC1.12"}
)
public interface IColumnTextureInfo {
   @Nullable
   Axis getAxis();

   @NotNull
   Function3 getTop();

   @NotNull
   Function3 getBottom();

   @NotNull
   Function3 getSide();
}
