package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.ShadersModIntegration;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelSet;
import mods.octarinecore.client.resource.SimplexNoise;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
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
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J(\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"},
   d2 = {"Lmods/betterfoliage/client/render/RenderAlgae;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "algaeIcons", "Lmods/octarinecore/client/resource/IconSet;", "getAlgaeIcons", "()Lmods/octarinecore/client/resource/IconSet;", "algaeModels", "Lmods/octarinecore/client/resource/ModelSet;", "getAlgaeModels", "()Lmods/octarinecore/client/resource/ModelSet;", "noise", "Lmods/octarinecore/client/resource/SimplexNoise;", "getNoise", "()Lmods/octarinecore/client/resource/SimplexNoise;", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderAlgae extends AbstractBlockRenderingHandler {
   @NotNull
   private final SimplexNoise noise = this.simplexNoise();
   @NotNull
   private final IconSet algaeIcons = this.iconSet("bettergrassandleaves", "blocks/better_algae_%d");
   @NotNull
   private final ModelSet algaeModels;

   @NotNull
   public final SimplexNoise getNoise() {
      return this.noise;
   }

   @NotNull
   public final IconSet getAlgaeIcons() {
      return this.algaeIcons;
   }

   @NotNull
   public final ModelSet getAlgaeModels() {
      return this.algaeModels;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.algaeIcons.getNum() + " algae textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      boolean var3;
      if (Config.INSTANCE.getEnabled() && Config.algae.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.algae.INSTANCE.getDistance()) {
         IBlockState var10000 = ctx.blockState(Utils.getUp2());
         Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(up2)");
         if (Intrinsics.areEqual(var10000.func_185904_a(), Material.field_151586_h)) {
            var10000 = ctx.blockState(Utils.getUp1());
            Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(up1)");
            if (Intrinsics.areEqual(var10000.func_185904_a(), Material.field_151586_h) && Config.blocks.INSTANCE.getDirt().matchesClass(ctx.getBlock()) && ArraysKt.contains((Object[])Config.algae.INSTANCE.getBiomes(), ctx.getBiomeId())) {
               SimplexNoise var2 = this.noise;
               BlockPos var10001 = ctx.getPos();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.pos");
               if (var2.get(var10001) < Config.algae.INSTANCE.getPopulation()) {
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
         Integer[] rand = ctx.semiRandomArray(3);
         ShadersModIntegration var7 = ShadersModIntegration.INSTANCE;
         boolean enabled$iv = Config.algae.INSTANCE.getShaderWind();
         long blockEntityData$iv$iv = ShadersModIntegration.getTallGrassEntityData();
         if (ShadersModIntegration.isPresent() && enabled$iv) {
            Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            Object vertexBuilder$iv$iv = var10000;
            Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.algaeModels.get(rand[2].intValue()), Rotation.Companion.getIdentity(), (Double3)null, false, (Function2)null, (Function3)(new RenderAlgae$render$$inlined$grass$lambda$1(this, renderer, rand)), ModelRendererKt.getNoPost(), 56, (Object)null);
            Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
         } else {
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.algaeModels.get(rand[2].intValue()), Rotation.Companion.getIdentity(), (Double3)null, false, (Function2)null, (Function3)(new RenderAlgae$render$$inlined$grass$lambda$2(this, renderer, rand)), ModelRendererKt.getNoPost(), 56, (Object)null);
         }

         return true;
      }
   }

   public RenderAlgae() {
      super("betterfoliage");
      this.algaeModels = this.modelSet(64, RenderGrass.Companion.grassTopQuads(Config.algae.INSTANCE.getHeightMin(), Config.algae.INSTANCE.getHeightMax()));
   }
}
