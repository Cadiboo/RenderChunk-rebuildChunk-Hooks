package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelHolder;
import mods.octarinecore.client.resource.VectorSet;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J(\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\n¨\u0006 "},
   d2 = {"Lmods/betterfoliage/client/render/RenderLilypad;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "flowerIcon", "Lmods/octarinecore/client/resource/IconSet;", "getFlowerIcon", "()Lmods/octarinecore/client/resource/IconSet;", "flowerModel", "Lmods/octarinecore/client/resource/ModelHolder;", "getFlowerModel", "()Lmods/octarinecore/client/resource/ModelHolder;", "perturbs", "Lmods/octarinecore/client/resource/VectorSet;", "getPerturbs", "()Lmods/octarinecore/client/resource/VectorSet;", "rootIcon", "getRootIcon", "rootModel", "getRootModel", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderLilypad extends AbstractBlockRenderingHandler {
   @NotNull
   private final ModelHolder rootModel;
   @NotNull
   private final ModelHolder flowerModel;
   @NotNull
   private final IconSet rootIcon;
   @NotNull
   private final IconSet flowerIcon;
   @NotNull
   private final VectorSet perturbs;

   @NotNull
   public final ModelHolder getRootModel() {
      return this.rootModel;
   }

   @NotNull
   public final ModelHolder getFlowerModel() {
      return this.flowerModel;
   }

   @NotNull
   public final IconSet getRootIcon() {
      return this.rootIcon;
   }

   @NotNull
   public final IconSet getFlowerIcon() {
      return this.flowerIcon;
   }

   @NotNull
   public final VectorSet getPerturbs() {
      return this.perturbs;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "Level.INFO");
      var10000.log(var10001, "Registered " + this.rootIcon.getNum() + " lilypad root textures");
      var10000 = Client.INSTANCE;
      var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "Level.INFO");
      var10000.log(var10001, "Registered " + this.flowerIcon.getNum() + " lilypad flower textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      return Config.INSTANCE.getEnabled() && Config.lilypad.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.lilypad.INSTANCE.getDistance() && Config.blocks.INSTANCE.getLilypad().matchesClass(ctx.getBlock());
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      if (!Utils.isCutout(layer)) {
         return false;
      } else {
         this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
         RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
         final Integer[] rand = ctx.semiRandomArray(5);
         ShadersModIntegration this_$iv = ShadersModIntegration.INSTANCE;
         boolean enabled$iv = true;
         long blockEntityData$iv$iv = ShadersModIntegration.getTallGrassEntityData();
         if (ShadersModIntegration.isPresent()) {
            Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            Object vertexBuilder$iv$iv = var10000;
            Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.rootModel.getModel(), Rotation.Companion.getIdentity(), ctx.getBlockCenter().add(this.perturbs.get(rand[2].intValue())), true, (Function2)null, (Function3)(new RenderLilypad$render$$inlined$grass$lambda$1(this, renderer, ctx, rand)), ModelRendererKt.getNoPost(), 32, (Object)null);
            Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
         } else {
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.rootModel.getModel(), Rotation.Companion.getIdentity(), ctx.getBlockCenter().add(this.perturbs.get(rand[2].intValue())), true, (Function2)null, (Function3)(new RenderLilypad$render$$inlined$grass$lambda$2(this, renderer, ctx, rand)), ModelRendererKt.getNoPost(), 32, (Object)null);
         }

         if (rand[3].intValue() < Config.lilypad.INSTANCE.getFlowerChance()) {
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.flowerModel.getModel(), Rotation.Companion.getIdentity(), ctx.getBlockCenter().add(this.perturbs.get(rand[4].intValue())), true, (Function2)null, (Function3)(new Function3() {
               @NotNull
               public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int $noName_1, @NotNull Quad $noName_2) {
                  Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                  Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
                  TextureAtlasSprite var10000 = RenderLilypad.this.getFlowerIcon().get(rand[0].intValue());
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  return var10000;
               }
            }), ModelRendererKt.getNoPost(), 32, (Object)null);
         }

         return true;
      }
   }

   public RenderLilypad() {
      super("betterfoliage");
      this.rootModel = this.model((Function1)null.INSTANCE);
      this.flowerModel = this.model((Function1)null.INSTANCE);
      this.rootIcon = this.iconSet("bettergrassandleaves", "blocks/better_lilypad_roots_%d");
      this.flowerIcon = this.iconSet("bettergrassandleaves", "blocks/better_lilypad_flower_%d");
      this.perturbs = this.vectorSet(64, (Function1)null.INSTANCE);
   }
}
