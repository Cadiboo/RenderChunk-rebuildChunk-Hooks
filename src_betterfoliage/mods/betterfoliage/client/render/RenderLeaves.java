package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.OptifineCTM;
import mods.betterfoliage.client.integration.ShadersModIntegration;
import mods.betterfoliage.client.texture.LeafInfo;
import mods.betterfoliage.client.texture.LeafRegistry;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelHolder;
import mods.octarinecore.client.resource.VectorSet;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J(\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"},
   d2 = {"Lmods/betterfoliage/client/render/RenderLeaves;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "leavesModel", "Lmods/octarinecore/client/resource/ModelHolder;", "getLeavesModel", "()Lmods/octarinecore/client/resource/ModelHolder;", "perturbs", "Lmods/octarinecore/client/resource/VectorSet;", "getPerturbs", "()Lmods/octarinecore/client/resource/VectorSet;", "snowedIcon", "Lmods/octarinecore/client/resource/IconSet;", "getSnowedIcon", "()Lmods/octarinecore/client/resource/IconSet;", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderLeaves extends AbstractBlockRenderingHandler {
   @NotNull
   private final ModelHolder leavesModel;
   @NotNull
   private final IconSet snowedIcon;
   @NotNull
   private final VectorSet perturbs;

   @NotNull
   public final ModelHolder getLeavesModel() {
      return this.leavesModel;
   }

   @NotNull
   public final IconSet getSnowedIcon() {
      return this.snowedIcon;
   }

   @NotNull
   public final VectorSet getPerturbs() {
      return this.perturbs;
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      return Config.INSTANCE.getEnabled() && Config.leaves.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.leaves.INSTANCE.getDistance() && LeafRegistry.INSTANCE.get(ctx, EnumFacing.DOWN) != null;
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      IBlockState var10000 = ctx.blockState(Utils.getUp1());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(up1)");
      Material var6 = var10000.func_185904_a();
      boolean isSnowed = Intrinsics.areEqual(var6, Material.field_151597_y) || Intrinsics.areEqual(var6, Material.field_151596_z);
      LeafInfo leafInfo = LeafRegistry.INSTANCE.get(ctx, EnumFacing.DOWN);
      if (leafInfo == null) {
         Client var27 = Client.INSTANCE;
         IBlockState var10001 = ctx.blockState(Int3.Companion.getZero());
         Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
         BlockPos var10002 = ctx.getPos();
         Intrinsics.checkExpressionValueIsNotNull(var10002, "ctx.pos");
         var27.logRenderError(var10001, var10002);
         return this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
      } else {
         int blockColor = OptifineCTM.INSTANCE.getBlockColor(ctx);
         this.renderWorldBlockBase(ctx, dispatcher, renderer, layer);
         if (!Utils.isCutout(layer)) {
            return true;
         } else {
            RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
            ShadersModIntegration this_$iv = ShadersModIntegration.INSTANCE;
            boolean enabled$iv = true;
            long blockEntityData$iv$iv = ShadersModIntegration.getLeavesEntityData();
            Integer[] rand;
            Object[] $receiver$iv;
            int var17;
            int var18;
            Object element$iv;
            Rotation rotation;
            if (ShadersModIntegration.isPresent()) {
               Object var26 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
               if (var26 == null) {
                  Intrinsics.throwNpe();
               }

               Object vertexBuilder$iv$iv = var26;
               Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
               rand = ctx.semiRandomArray(2);
               $receiver$iv = (Object[])(Config.leaves.INSTANCE.getDense() ? Utils.getDenseLeavesRot() : Utils.getNormalLeavesRot());
               var17 = $receiver$iv.length;

               for(var18 = 0; var18 < var17; ++var18) {
                  element$iv = $receiver$iv[var18];
                  rotation = (Rotation)element$iv;
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.leavesModel.getModel(), rotation, ctx.getBlockCenter().plus(this.perturbs.get(rand[0].intValue())), false, (Function2)null, (Function3)(new RenderLeaves$render$$inlined$leaves$lambda$1(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), (Function6)(new RenderLeaves$render$$inlined$leaves$lambda$2(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), 48, (Object)null);
               }

               if (isSnowed && Config.leaves.INSTANCE.getSnowEnabled()) {
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.leavesModel.getModel(), Rotation.Companion.getIdentity(), ctx.getBlockCenter().plus(this.perturbs.get(rand[0].intValue())), false, (Function2)null, (Function3)(new RenderLeaves$render$$inlined$leaves$lambda$3(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), Utils.getWhitewash(), 48, (Object)null);
               }

               Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
            } else {
               rand = ctx.semiRandomArray(2);
               $receiver$iv = (Object[])(Config.leaves.INSTANCE.getDense() ? Utils.getDenseLeavesRot() : Utils.getNormalLeavesRot());
               var17 = $receiver$iv.length;

               for(var18 = 0; var18 < var17; ++var18) {
                  element$iv = $receiver$iv[var18];
                  rotation = (Rotation)element$iv;
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.leavesModel.getModel(), rotation, ctx.getBlockCenter().plus(this.perturbs.get(rand[0].intValue())), false, (Function2)null, (Function3)(new RenderLeaves$render$$inlined$leaves$lambda$4(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), (Function6)(new RenderLeaves$render$$inlined$leaves$lambda$5(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), 48, (Object)null);
               }

               if (isSnowed && Config.leaves.INSTANCE.getSnowEnabled()) {
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.leavesModel.getModel(), Rotation.Companion.getIdentity(), ctx.getBlockCenter().plus(this.perturbs.get(rand[0].intValue())), false, (Function2)null, (Function3)(new RenderLeaves$render$$inlined$leaves$lambda$6(rand, this, ctx, renderer, leafInfo, blockColor, isSnowed)), Utils.getWhitewash(), 48, (Object)null);
               }
            }

            return true;
         }
      }
   }

   public RenderLeaves() {
      super("betterfoliage");
      this.leavesModel = this.model((Function1)null.INSTANCE);
      this.snowedIcon = this.iconSet("bettergrassandleaves", "blocks/better_leaves_snowed_%d");
      this.perturbs = this.vectorSet(64, (Function1)null.INSTANCE);
   }
}
