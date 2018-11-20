package mods.betterfoliage.client.render;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.OffsetBlockAccess;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.Block;
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
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0013"},
   d2 = {"Lmods/betterfoliage/client/render/RenderConnectedGrassLog;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "grassCheckDirs", "", "Lnet/minecraft/util/EnumFacing;", "getGrassCheckDirs", "()Ljava/util/List;", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderConnectedGrassLog extends AbstractBlockRenderingHandler {
   @NotNull
   private final List grassCheckDirs;

   @NotNull
   public final List getGrassCheckDirs() {
      return this.grassCheckDirs;
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      boolean var2;
      if (Config.INSTANCE.getEnabled() && Config.roundLogs.INSTANCE.getEnabled() && Config.roundLogs.INSTANCE.getConnectGrass() && Config.blocks.INSTANCE.getDirt().matchesClass(ctx.getBlock())) {
         ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
         Block var10001 = ctx.block(Utils.getUp1());
         Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.block(up1)");
         if (var10000.matchesClass(var10001)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      Iterable var6 = (Iterable)this.grassCheckDirs;
      Iterator var8 = var6.iterator();

      Object var10000;
      while(true) {
         if (var8.hasNext()) {
            Object var9 = var8.next();
            EnumFacing it = (EnumFacing)var9;
            ConfigurableBlockMatcher var13 = Config.blocks.INSTANCE.getGrassClasses();
            Block var10001 = ctx.block(GeometryKt.getOffset(it));
            Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.block(it.offset)");
            if (!var13.matchesClass(var10001)) {
               continue;
            }

            var10000 = var9;
            break;
         }

         var10000 = null;
         break;
      }

      EnumFacing var14 = (EnumFacing)var10000;
      if (var14 != null) {
         EnumFacing grassDir = var14;
         Int3 modded$iv = Int3.Companion.getZero();
         Int3 target$iv = GeometryKt.getOffset(grassDir);
         IBlockAccess var15 = ctx.getWorld();
         if (var15 == null) {
            Intrinsics.throwNpe();
         }

         IBlockAccess original$iv = var15;
         BlockPos var10004 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10004, "pos");
         var10004 = GeometryKt.plus(var10004, modded$iv);
         BlockPos var10005 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10005, "pos");
         ctx.setWorld((IBlockAccess)(new OffsetBlockAccess(original$iv, var10004, GeometryKt.plus(var10005, target$iv))));
         boolean result$iv = this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
         ctx.setWorld(original$iv);
         return result$iv;
      } else {
         return this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
      }
   }

   public RenderConnectedGrassLog() {
      super("betterfoliage");
      this.grassCheckDirs = CollectionsKt.listOf(new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH});
   }
}
