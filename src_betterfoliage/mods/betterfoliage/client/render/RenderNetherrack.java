package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelSet;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.block.state.IBlockState;
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
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J(\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018"},
   d2 = {"Lmods/betterfoliage/client/render/RenderNetherrack;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "netherrackIcon", "Lmods/octarinecore/client/resource/IconSet;", "getNetherrackIcon", "()Lmods/octarinecore/client/resource/IconSet;", "netherrackModel", "Lmods/octarinecore/client/resource/ModelSet;", "getNetherrackModel", "()Lmods/octarinecore/client/resource/ModelSet;", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderNetherrack extends AbstractBlockRenderingHandler {
   @NotNull
   private final IconSet netherrackIcon = this.iconSet("bettergrassandleaves", "blocks/better_netherrack_%d");
   @NotNull
   private final ModelSet netherrackModel;

   @NotNull
   public final IconSet getNetherrackIcon() {
      return this.netherrackIcon;
   }

   @NotNull
   public final ModelSet getNetherrackModel() {
      return this.netherrackModel;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.netherrackIcon.getNum() + " netherrack textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      if (Config.INSTANCE.getEnabled() && Config.netherrack.INSTANCE.getEnabled()) {
         return Config.blocks.INSTANCE.getNetherrack().matchesClass(ctx.getBlock()) && ctx.getCameraDistance() < Config.netherrack.INSTANCE.getDistance();
      } else {
         return false;
      }
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
         IBlockState var10000 = ctx.blockState(Utils.getDown1());
         Intrinsics.checkExpressionValueIsNotNull(var10000, "ctx.blockState(down1)");
         if (var10000.func_185914_p()) {
            return baseRender;
         } else {
            RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
            final Integer[] rand = ctx.semiRandomArray(2);
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.netherrackModel.get(rand[0].intValue()), Rotation.Companion.getIdentity(), (Double3)null, false, (Function2)null, (Function3)(new Function3() {
               @NotNull
               public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int qi, @NotNull Quad $noName_2) {
                  Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                  Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
                  TextureAtlasSprite var10000 = RenderNetherrack.this.getNetherrackIcon().get(rand[qi & 1].intValue());
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  return var10000;
               }
            }), ModelRendererKt.getNoPost(), 56, (Object)null);
            return true;
         }
      }
   }

   public RenderNetherrack() {
      super("betterfoliage");
      this.netherrackModel = this.modelSet(64, (Function2)null.INSTANCE);
   }
}
