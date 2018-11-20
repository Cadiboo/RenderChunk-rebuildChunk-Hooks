package mods.betterfoliage.client.render;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.OffsetBlockAccess;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"},
   d2 = {"Lmods/betterfoliage/client/render/RenderConnectedGrass;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderConnectedGrass extends AbstractBlockRenderingHandler {
   public boolean isEligible(@NotNull BlockContext ctx) {
      boolean var3;
      label29: {
         Intrinsics.checkParameterIsNotNull(ctx, "ctx");
         if (Config.INSTANCE.getEnabled() && Config.connectedGrass.INSTANCE.getEnabled() && Config.blocks.INSTANCE.getDirt().matchesClass(ctx.getBlock())) {
            ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getGrassClasses();
            Block var10001 = ctx.block(Utils.getUp1());
            Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.block(up1)");
            if (var10000.matchesClass(var10001)) {
               if (Config.connectedGrass.INSTANCE.getSnowEnabled()) {
                  break label29;
               }

               IBlockState var2 = ctx.blockState(Utils.getUp2());
               Intrinsics.checkExpressionValueIsNotNull(var2, "ctx.blockState(up2)");
               if (!Utils.isSnow(var2)) {
                  break label29;
               }
            }
         }

         var3 = false;
         return var3;
      }

      var3 = true;
      return var3;
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      Iterable $receiver$iv = (Iterable)GeometryKt.getForgeDirsHorizontal();
      boolean var21;
      if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
         var21 = true;
      } else {
         Iterator var6 = $receiver$iv.iterator();

         while(true) {
            if (!var6.hasNext()) {
               var21 = true;
               break;
            }

            Object element$iv = var6.next();
            EnumFacing it = (EnumFacing)element$iv;
            IBlockState var10000 = ctx.blockState(GeometryKt.getOffset(it));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(it.offset)");
            if (!var10000.func_185914_p()) {
               var21 = false;
               break;
            }
         }
      }

      if (var21) {
         return this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
      } else if (ctx.isSurroundedBy((Function1)null.INSTANCE)) {
         return false;
      } else {
         Int3 modded$iv = Int3.Companion.getZero();
         Int3 target$iv = Utils.getUp1();
         IBlockAccess var22 = ctx.getWorld();
         if (var22 == null) {
            Intrinsics.throwNpe();
         }

         IBlockAccess original$iv = var22;
         BlockPos var10004 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10004, "pos");
         var10004 = GeometryKt.plus(var10004, modded$iv);
         BlockPos var10005 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10005, "pos");
         ctx.setWorld((IBlockAccess)(new OffsetBlockAccess(original$iv, var10004, GeometryKt.plus(var10005, target$iv))));
         Int3 modded$iv = Utils.getUp1();
         Int3 target$iv = Utils.getUp2();
         var22 = ctx.getWorld();
         if (var22 == null) {
            Intrinsics.throwNpe();
         }

         IBlockAccess original$iv = var22;
         var10004 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10004, "pos");
         var10004 = GeometryKt.plus(var10004, modded$iv);
         var10005 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10005, "pos");
         ctx.setWorld((IBlockAccess)(new OffsetBlockAccess(original$iv, var10004, GeometryKt.plus(var10005, target$iv))));
         boolean result$iv = this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
         ctx.setWorld(original$iv);
         ctx.setWorld(original$iv);
         return result$iv;
      }
   }

   public RenderConnectedGrass() {
      super("betterfoliage");
   }
}
