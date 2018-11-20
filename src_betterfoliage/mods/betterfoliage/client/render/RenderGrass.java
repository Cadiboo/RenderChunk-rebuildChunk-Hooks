package mods.betterfoliage.client.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.OptifineCTM;
import mods.betterfoliage.client.integration.ShadersModIntegration;
import mods.betterfoliage.client.texture.GrassInfo;
import mods.betterfoliage.client.texture.GrassRegistry;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.ModelKt;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.render.ShadersKt;
import mods.octarinecore.client.render.ShadingKt;
import mods.octarinecore.client.resource.IconHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ModelSet;
import mods.octarinecore.client.resource.SimplexNoise;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
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
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J(\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012¨\u0006%"},
   d2 = {"Lmods/betterfoliage/client/render/RenderGrass;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "grassModels", "Lmods/octarinecore/client/resource/ModelSet;", "getGrassModels", "()Lmods/octarinecore/client/resource/ModelSet;", "noise", "Lmods/octarinecore/client/resource/SimplexNoise;", "getNoise", "()Lmods/octarinecore/client/resource/SimplexNoise;", "normalGenIcon", "Lmods/octarinecore/client/resource/IconHolder;", "getNormalGenIcon", "()Lmods/octarinecore/client/resource/IconHolder;", "normalIcons", "Lmods/octarinecore/client/resource/IconSet;", "getNormalIcons", "()Lmods/octarinecore/client/resource/IconSet;", "snowedGenIcon", "getSnowedGenIcon", "snowedIcons", "getSnowedIcons", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "Companion", "BetterFoliage-MC1.12"}
)
public final class RenderGrass extends AbstractBlockRenderingHandler {
   @NotNull
   private final SimplexNoise noise = this.simplexNoise();
   @NotNull
   private final IconSet normalIcons = this.iconSet("bettergrassandleaves", "blocks/better_grass_long_%d");
   @NotNull
   private final IconSet snowedIcons = this.iconSet("bettergrassandleaves", "blocks/better_grass_snowed_%d");
   @NotNull
   private final IconHolder normalGenIcon;
   @NotNull
   private final IconHolder snowedGenIcon;
   @NotNull
   private final ModelSet grassModels;
   public static final RenderGrass.Companion Companion = new RenderGrass.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final SimplexNoise getNoise() {
      return this.noise;
   }

   @NotNull
   public final IconSet getNormalIcons() {
      return this.normalIcons;
   }

   @NotNull
   public final IconSet getSnowedIcons() {
      return this.snowedIcons;
   }

   @NotNull
   public final IconHolder getNormalGenIcon() {
      return this.normalGenIcon;
   }

   @NotNull
   public final IconHolder getSnowedGenIcon() {
      return this.snowedGenIcon;
   }

   @NotNull
   public final ModelSet getGrassModels() {
      return this.grassModels;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.normalIcons.getNum() + " grass textures");
      var10000 = Client.INSTANCE;
      var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + this.snowedIcons.getNum() + " snowed grass textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      return Config.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.shortGrass.INSTANCE.getDistance() && (Config.shortGrass.INSTANCE.getGrassEnabled() || Config.connectedGrass.INSTANCE.getEnabled()) && GrassRegistry.INSTANCE.get(ctx, EnumFacing.UP) != null;
   }

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      if (!Utils.isCutout(layer)) {
         return false;
      } else {
         Block var6 = ctx.block(Utils.getDown1());
         ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getDirt();
         Intrinsics.checkExpressionValueIsNotNull(var6, "it");
         boolean isConnected = var10000.matchesClass(var6) || Config.blocks.INSTANCE.getGrassClasses().matchesClass(var6);
         IBlockState var33 = ctx.blockState(Utils.getUp1());
         Intrinsics.checkExpressionValueIsNotNull(var33, "ctx.blockState(up1)");
         boolean isSnowed = Utils.isSnow(var33);
         boolean connectedGrass = isConnected && Config.connectedGrass.INSTANCE.getEnabled() && (!isSnowed || Config.connectedGrass.INSTANCE.getSnowEnabled());
         GrassInfo grassInfo = GrassRegistry.INSTANCE.get(ctx, EnumFacing.UP);
         if (grassInfo == null) {
            Client var38 = Client.INSTANCE;
            IBlockState var35 = ctx.blockState(Int3.Companion.getZero());
            Intrinsics.checkExpressionValueIsNotNull(var35, "ctx.blockState(Int3.zero)");
            BlockPos var10002 = ctx.getPos();
            Intrinsics.checkExpressionValueIsNotNull(var10002, "ctx.pos");
            var38.logRenderError(var35, var10002);
            return this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
         } else {
            int blockColor = OptifineCTM.INSTANCE.getBlockColor(ctx);
            Object vertexBuilder$iv$iv;
            long blockEntityData$iv$iv;
            Object var36;
            if (connectedGrass) {
               RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
               Object[] $receiver$iv = (Object[])GeometryKt.getForgeDirs();
               Object[] $receiver$iv$iv = $receiver$iv;
               Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
               int var14 = $receiver$iv.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  Object item$iv$iv = $receiver$iv$iv[var15];
                  EnumFacing it = (EnumFacing)item$iv$iv;
                  var33 = ctx.blockState(GeometryKt.getOffset(it));
                  Intrinsics.checkExpressionValueIsNotNull(var33, "ctx.blockState(it.offset)");
                  Boolean var23 = var33.func_185914_p();
                  destination$iv$iv.add(var23);
               }

               List isHidden = (List)destination$iv$iv;
               ShadersModIntegration this_$iv = ShadersModIntegration.INSTANCE;
               var33 = ctx.blockState(Int3.Companion.getZero());
               Intrinsics.checkExpressionValueIsNotNull(var33, "ctx.blockState(Int3.zero)");
               IBlockState state$iv = var33;
               boolean enabled$iv = true;
               blockEntityData$iv$iv = this_$iv.entityDataFor(state$iv);
               if (ShadersModIntegration.isPresent()) {
                  var36 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
                  if (var36 == null) {
                     Intrinsics.throwNpe();
                  }

                  vertexBuilder$iv$iv = var36;
                  Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, ModelKt.getFullCube(), (Rotation)null, (Double3)null, false, (Function2)(new RenderGrass$render$$inlined$renderAs$lambda$1(renderer, isHidden, grassInfo, isSnowed, blockColor)), (Function3)(new RenderGrass$render$$inlined$renderAs$lambda$2(renderer, isHidden, grassInfo, isSnowed, blockColor)), (Function6)(new RenderGrass$render$$inlined$renderAs$lambda$3(renderer, isHidden, grassInfo, isSnowed, blockColor)), 28, (Object)null);
                  Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
               } else {
                  ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, ModelKt.getFullCube(), (Rotation)null, (Double3)null, false, (Function2)(new RenderGrass$render$$inlined$renderAs$lambda$4(renderer, isHidden, grassInfo, isSnowed, blockColor)), (Function3)(new RenderGrass$render$$inlined$renderAs$lambda$5(renderer, isHidden, grassInfo, isSnowed, blockColor)), (Function6)(new RenderGrass$render$$inlined$renderAs$lambda$6(renderer, isHidden, grassInfo, isSnowed, blockColor)), 28, (Object)null);
               }
            } else {
               this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
               RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getTopOnly());
            }

            if (!Config.shortGrass.INSTANCE.getGrassEnabled()) {
               return true;
            } else if (isSnowed && !Config.shortGrass.INSTANCE.getSnowEnabled()) {
               return true;
            } else {
               var33 = ctx.blockState(Utils.getUp1());
               Intrinsics.checkExpressionValueIsNotNull(var33, "ctx.blockState(up1)");
               if (var33.func_185914_p()) {
                  return true;
               } else {
                  if (Config.shortGrass.INSTANCE.getPopulation() < 64) {
                     SimplexNoise var37 = this.noise;
                     BlockPos var10001 = ctx.getPos();
                     Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.pos");
                     if (var37.get(var10001) >= Config.shortGrass.INSTANCE.getPopulation()) {
                        return true;
                     }
                  }

                  IconSet iconset = isSnowed ? this.snowedIcons : this.normalIcons;
                  IconHolder iconGen = isSnowed ? this.snowedGenIcon : this.normalGenIcon;
                  Integer[] rand = ctx.semiRandomArray(2);
                  ShadersModIntegration var31 = ShadersModIntegration.INSTANCE;
                  boolean enabled$iv = Config.shortGrass.INSTANCE.getShaderWind();
                  blockEntityData$iv$iv = ShadersModIntegration.getTallGrassEntityData();
                  if (ShadersModIntegration.isPresent() && enabled$iv) {
                     var36 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
                     if (var36 == null) {
                        Intrinsics.throwNpe();
                     }

                     vertexBuilder$iv$iv = var36;
                     Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.grassModels.get(rand[0].intValue()), Rotation.Companion.getIdentity(), ctx.getBlockCenter().plus(isSnowed ? Utils.getSnowOffset() : Double3.Companion.getZero()), false, (Function2)null, (Function3)(new RenderGrass$render$$inlined$grass$lambda$1(this, renderer, rand, ctx, isSnowed, iconGen, iconset, grassInfo, blockColor)), (Function6)(new RenderGrass$render$$inlined$grass$lambda$2(this, renderer, rand, ctx, isSnowed, iconGen, iconset, grassInfo, blockColor)), 48, (Object)null);
                     Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
                  } else {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.grassModels.get(rand[0].intValue()), Rotation.Companion.getIdentity(), ctx.getBlockCenter().plus(isSnowed ? Utils.getSnowOffset() : Double3.Companion.getZero()), false, (Function2)null, (Function3)(new RenderGrass$render$$inlined$grass$lambda$3(this, renderer, rand, ctx, isSnowed, iconGen, iconset, grassInfo, blockColor)), (Function6)(new RenderGrass$render$$inlined$grass$lambda$4(this, renderer, rand, ctx, isSnowed, iconGen, iconset, grassInfo, blockColor)), 48, (Object)null);
                  }

                  return true;
               }
            }
         }
      }
   }

   public RenderGrass() {
      super("betterfoliage");
      this.normalGenIcon = this.iconStatic(Client.INSTANCE.getGenGrass().generatedResource("minecraft:blocks/tallgrass", new Pair[]{TuplesKt.to("snowed", false)}));
      this.snowedGenIcon = this.iconStatic(Client.INSTANCE.getGenGrass().generatedResource("minecraft:blocks/tallgrass", new Pair[]{TuplesKt.to("snowed", true)}));
      this.grassModels = this.modelSet(64, Companion.grassTopQuads(Config.shortGrass.INSTANCE.getHeightMin(), Config.shortGrass.INSTANCE.getHeightMax()));
   }

   @JvmStatic
   @NotNull
   public static final Function2 grassTopQuads(double heightMin, double heightMax) {
      return Companion.grassTopQuads(heightMin, heightMax);
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u0019\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0004¢\u0006\u0002\b\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007¨\u0006\f"},
      d2 = {"Lmods/betterfoliage/client/render/RenderGrass$Companion;", "", "()V", "grassTopQuads", "Lkotlin/Function2;", "Lmods/octarinecore/client/render/Model;", "", "", "Lkotlin/ExtensionFunctionType;", "heightMin", "", "heightMax", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @JvmStatic
      @NotNull
      public final Function2 grassTopQuads(final double heightMin, final double heightMax) {
         return (Function2)(new Function2() {
            public final void invoke(@NotNull Model $receiver, final int modelIdx) {
               Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
               $receiver.addAll((Iterable)Utils.toCross(Quad.setFlatShader$default(Quad.setAoShader$default($receiver.verticalRectangle(-0.5D, 0.5D, 0.5D, -0.5D, 0.5D, 0.5D + mods.octarinecore.Utils.random(heightMin, heightMax)), ShadingKt.faceOrientedAuto$default(EnumFacing.UP, ShadersKt.cornerAo(Axis.Y), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null), ShadingKt.faceOrientedAuto$default(EnumFacing.UP, ShadersKt.getCornerFlat(), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null), EnumFacing.UP, (Function1)(new Function1() {
                  @NotNull
                  public final Quad invoke(@NotNull Quad it) {
                     Intrinsics.checkParameterIsNotNull(it, "it");
                     return it.move(Utils.xzDisk(modelIdx).times(Config.shortGrass.INSTANCE.getHOffset()));
                  }
               })));
            }
         });
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
