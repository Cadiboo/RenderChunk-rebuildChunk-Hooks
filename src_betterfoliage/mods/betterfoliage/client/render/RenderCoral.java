package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelSet;
import mods.octarinecore.client.resource.SimplexNoise;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J(\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001e"},
   d2 = {"Lmods/betterfoliage/client/render/RenderCoral;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "coralIcons", "Lmods/octarinecore/client/resource/IconSet;", "getCoralIcons", "()Lmods/octarinecore/client/resource/IconSet;", "coralModels", "Lmods/octarinecore/client/resource/ModelSet;", "getCoralModels", "()Lmods/octarinecore/client/resource/ModelSet;", "crustIcons", "getCrustIcons", "noise", "Lmods/octarinecore/client/resource/SimplexNoise;", "getNoise", "()Lmods/octarinecore/client/resource/SimplexNoise;", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderCoral extends AbstractBlockRenderingHandler {
   @NotNull
   private final SimplexNoise noise = this.simplexNoise();
   @NotNull
   private final IconSet coralIcons = this.iconSet("bettergrassandleaves", "blocks/better_coral_%d");
   @NotNull
   private final IconSet crustIcons = this.iconSet("bettergrassandleaves", "blocks/better_crust_%d");
   @NotNull
   private final ModelSet coralModels;

   @NotNull
   public final SimplexNoise getNoise() {
      return this.noise;
   }

   @NotNull
   public final IconSet getCoralIcons() {
      return this.coralIcons;
   }

   @NotNull
   public final IconSet getCrustIcons() {
      return this.crustIcons;
   }

   @NotNull
   public final ModelSet getCoralModels() {
      return this.coralModels;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.coralIcons.getNum() + " coral textures");
      var10000 = Client.INSTANCE;
      var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.crustIcons.getNum() + " coral crust textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      boolean var3;
      if (Config.INSTANCE.getEnabled() && Config.coral.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.coral.INSTANCE.getDistance()) {
         IBlockState var10000 = ctx.blockState(Utils.getUp2());
         Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(up2)");
         if (Intrinsics.areEqual(var10000.func_185904_a(), Material.field_151586_h) || Config.coral.INSTANCE.getShallowWater()) {
            var10000 = ctx.blockState(Utils.getUp1());
            Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(up1)");
            if (Intrinsics.areEqual(var10000.func_185904_a(), Material.field_151586_h) && Config.blocks.INSTANCE.getSand().matchesClass(ctx.getBlock()) && ArraysKt.contains((Object[])Config.coral.INSTANCE.getBiomes(), ctx.getBiomeId())) {
               SimplexNoise var2 = this.noise;
               BlockPos var10001 = ctx.getPos();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.pos");
               if (var2.get(var10001) < Config.coral.INSTANCE.getPopulation()) {
                  var3 = true;
                  return var3;
               }
            }
         }
      }

      var3 = false;
      return var3;
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      boolean baseRender = this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
      if (!Utils.isCutout(layer)) {
         return baseRender;
      } else {
         RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
         Object[] $receiver$iv = (Object[])GeometryKt.getForgeDirs();
         int index$iv = 0;
         int var8 = $receiver$iv.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Object item$iv = $receiver$iv[var9];
            int var10000 = index$iv++;
            EnumFacing face = (EnumFacing)item$iv;
            int idx = var10000;
            IBlockState var17 = ctx.blockState((Int3)GeometryKt.getForgeDirOffsets().get(idx));
            Intrinsics.checkExpressionValueIsNotNull(var17, "ctx.blockState(forgeDirOffsets[idx])");
            if (!var17.func_185914_p() && RendererHolder.getBlockContext().random(idx) < Config.coral.INSTANCE.getChance()) {
               IntRef variation = new IntRef();
               variation.element = RendererHolder.getBlockContext().random(6);
               ModelRenderer var18 = RendererHolder.getModelRenderer();
               ModelSet var10002 = this.coralModels;
               int var14 = variation.element++;
               ModelRenderer.render$default(var18, renderer, var10002.get(var14), Utils.getRotationFromUp()[idx], (Double3)null, false, (Function2)null, (Function3)(new RenderCoral$render$$inlined$forEachIndexed$lambda$1(variation, this, ctx, renderer)), ModelRendererKt.getNoPost(), 56, (Object)null);
            }
         }

         return true;
      }
   }

   public RenderCoral() {
      super("betterfoliage");
      this.coralModels = this.modelSet(64, (Function2)null.INSTANCE);
   }
}
