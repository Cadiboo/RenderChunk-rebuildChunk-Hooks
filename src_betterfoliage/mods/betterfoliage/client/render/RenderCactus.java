package mods.betterfoliage.client.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.render.ShadersKt;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.client.render.ShadingKt;
import mods.octarinecore.client.resource.IconHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.client.resource.ModelHolder;
import mods.octarinecore.client.resource.ModelSet;
import mods.octarinecore.client.resource.ModelVariant;
import mods.octarinecore.client.resource.TextureListModelProcessor;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import mods.octarinecore.common.config.MatchersKt;
import mods.octarinecore.common.config.SimpleBlockMatcher;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J(\u0010(\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\tX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u001e\u001a\u00020\u001f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u0006/"},
   d2 = {"Lmods/betterfoliage/client/render/RenderCactus;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "()V", "cactusArmRotation", "", "Lmods/octarinecore/common/Rotation;", "getCactusArmRotation", "()Ljava/util/List;", "cactusStemRadius", "", "getCactusStemRadius", "()D", "cactusTextures", "Lmods/betterfoliage/client/render/IColumnRegistry;", "getCactusTextures", "()Lmods/betterfoliage/client/render/IColumnRegistry;", "iconArm", "Lmods/octarinecore/client/resource/IconSet;", "getIconArm", "()Lmods/octarinecore/client/resource/IconSet;", "iconCross", "Lmods/octarinecore/client/resource/IconHolder;", "getIconCross", "()Lmods/octarinecore/client/resource/IconHolder;", "modelArm", "Lmods/octarinecore/client/resource/ModelSet;", "getModelArm", "()Lmods/octarinecore/client/resource/ModelSet;", "modelCross", "getModelCross", "modelStem", "Lmods/octarinecore/client/resource/ModelHolder;", "getModelStem", "()Lmods/octarinecore/client/resource/ModelHolder;", "afterStitch", "", "isEligible", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "BetterFoliage-MC1.12"}
)
public final class RenderCactus extends AbstractBlockRenderingHandler {
   private final double cactusStemRadius = 0.4375D;
   @NotNull
   private final List cactusArmRotation;
   @NotNull
   private final IconHolder iconCross;
   @NotNull
   private final IconSet iconArm;
   @NotNull
   private final IColumnRegistry cactusTextures;
   @NotNull
   private final ModelHolder modelStem;
   @NotNull
   private final ModelSet modelCross;
   @NotNull
   private final ModelSet modelArm;

   public final double getCactusStemRadius() {
      return this.cactusStemRadius;
   }

   @NotNull
   public final List getCactusArmRotation() {
      return this.cactusArmRotation;
   }

   @NotNull
   public final IconHolder getIconCross() {
      return this.iconCross;
   }

   @NotNull
   public final IconSet getIconArm() {
      return this.iconArm;
   }

   @NotNull
   public final IColumnRegistry getCactusTextures() {
      return this.cactusTextures;
   }

   @NotNull
   public final ModelHolder getModelStem() {
      return this.modelStem;
   }

   @NotNull
   public final ModelSet getModelCross() {
      return this.modelCross;
   }

   @NotNull
   public final ModelSet getModelArm() {
      return this.modelArm;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "Level.INFO");
      var10000.log(var10001, "Registered " + this.iconArm.getNum() + " cactus arm textures");
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      return Config.INSTANCE.getEnabled() && Config.cactus.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.cactus.INSTANCE.getDistance() && Config.blocks.INSTANCE.getCactus().matchesClass(ctx.getBlock());
   }

   public boolean render(@NotNull final BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      if (!Utils.isCutout(layer)) {
         return false;
      } else {
         RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
         IColumnRegistry var10000 = this.cactusTextures;
         IBlockState var10001 = ctx.blockState(Int3.Companion.getZero());
         Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
         IColumnTextureInfo var6 = var10000.get(var10001, ctx.random(0));
         if (var6 != null) {
            final IColumnTextureInfo icons = var6;
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.modelStem.getModel(), Rotation.Companion.getIdentity(), (Double3)null, false, (Function2)null, (Function3)(new Function3() {
               @Nullable
               public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int qi, @NotNull Quad q) {
                  Intrinsics.checkParameterIsNotNull(ctx, "ctx");
                  Intrinsics.checkParameterIsNotNull(q, "q");
                  TextureAtlasSprite var10000;
                  switch(qi) {
                  case 0:
                     var10000 = (TextureAtlasSprite)icons.getBottom().invoke(ctx, qi, q);
                     break;
                  case 1:
                     var10000 = (TextureAtlasSprite)icons.getTop().invoke(ctx, qi, q);
                     break;
                  default:
                     var10000 = (TextureAtlasSprite)icons.getSide().invoke(ctx, qi, q);
                  }

                  return var10000;
               }
            }), ModelRendererKt.getNoPost(), 56, (Object)null);
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.modelCross.get(ctx.random(0)), Rotation.Companion.getIdentity(), (Double3)null, false, (Function2)null, (Function3)(new Function3() {
               @NotNull
               public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int $noName_1, @NotNull Quad $noName_2) {
                  Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                  Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
                  TextureAtlasSprite var10000 = RenderCactus.this.getIconCross().getIcon();
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  return var10000;
               }
            }), ModelRendererKt.getNoPost(), 56, (Object)null);
            ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, this.modelArm.get(ctx.random(1)), (Rotation)this.cactusArmRotation.get(ctx.random(2) % 4), (Double3)null, false, (Function2)null, (Function3)(new Function3() {
               @NotNull
               public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int $noName_1, @NotNull Quad $noName_2) {
                  Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
                  Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
                  TextureAtlasSprite var10000 = RenderCactus.this.getIconArm().get(ctx.random(3));
                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  return var10000;
               }
            }), ModelRendererKt.getNoPost(), 56, (Object)null);
            return true;
         } else {
            return this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
         }
      }
   }

   public RenderCactus() {
      super("betterfoliage");
      Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new EnumFacing[]{EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST});
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var4 = $receiver$iv.iterator();

      while(var4.hasNext()) {
         Object item$iv$iv = var4.next();
         EnumFacing it = (EnumFacing)item$iv$iv;
         Rotation var12 = Rotation.Companion.getRot90()[it.ordinal()];
         destination$iv$iv.add(var12);
      }

      List var11 = (List)destination$iv$iv;
      this.cactusArmRotation = var11;
      this.iconCross = this.iconStatic("bettergrassandleaves", "blocks/better_cactus");
      this.iconArm = this.iconSet("bettergrassandleaves", "blocks/better_cactus_arm_%d");
      this.cactusTextures = (IColumnRegistry)(new TextureListModelProcessor() {
         @NotNull
         private Map variants;
         @NotNull
         private Map variantToKey;
         @NotNull
         private Map variantToValue;
         @NotNull
         private final Logger logger;
         @NotNull
         private final String logName;
         @NotNull
         private final SimpleBlockMatcher matchClasses;
         @NotNull
         private final List modelTextures;

         @NotNull
         public Map getVariants() {
            return this.variants;
         }

         public void setVariants(@NotNull Map var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.variants = var1;
         }

         @NotNull
         public Map getVariantToKey() {
            return this.variantToKey;
         }

         public void setVariantToKey(@NotNull Map var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.variantToKey = var1;
         }

         @NotNull
         public Map getVariantToValue() {
            return this.variantToValue;
         }

         public void setVariantToValue(@NotNull Map var1) {
            Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
            this.variantToValue = var1;
         }

         @NotNull
         public Logger getLogger() {
            return this.logger;
         }

         @NotNull
         public String getLogName() {
            return this.logName;
         }

         @NotNull
         public SimpleBlockMatcher getMatchClasses() {
            return this.matchClasses;
         }

         @NotNull
         public List getModelTextures() {
            return this.modelTextures;
         }

         @Nullable
         public IColumnTextureInfo processStitch(@NotNull ModelVariant variant, @NotNull List key, @NotNull TextureMap atlas) {
            Intrinsics.checkParameterIsNotNull(variant, "variant");
            Intrinsics.checkParameterIsNotNull(key, "key");
            Intrinsics.checkParameterIsNotNull(atlas, "atlas");
            TextureAtlasSprite topTex = mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(0));
            TextureAtlasSprite bottomTex = mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(1));
            TextureAtlasSprite sideTex = mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(2));
            return (IColumnTextureInfo)(new StaticColumnInfo(Axis.Y, topTex, bottomTex, CollectionsKt.listOf(sideTex)));
         }

         @Nullable
         public IColumnTextureInfo get(@NotNull IBlockState state, int rand) {
            Intrinsics.checkParameterIsNotNull(state, "state");
            ModelVariant var10000 = this.getVariant(state, rand);
            if (var10000 != null) {
               ModelVariant variant = var10000;
               return (IColumnTextureInfo)this.getVariantToValue().get(variant);
            } else {
               return null;
            }
         }

         {
            MinecraftForge.EVENT_BUS.register(this);
            Map var2 = (Map)(new LinkedHashMap());
            this.variants = var2;
            var2 = (Map)(new LinkedHashMap());
            this.variantToKey = var2;
            var2 = MapsKt.emptyMap();
            this.variantToValue = var2;
            this.logger = BetterFoliageMod.INSTANCE.getLogDetail();
            this.logName = "CactusTextures";
            this.matchClasses = new SimpleBlockMatcher(new Class[]{BlockCactus.class});
            this.modelTextures = CollectionsKt.listOf(MatchersKt.modelTextures("block/cactus", "top", "bottom", "side"));
         }

         public void onPreStitch() {
            TextureListModelProcessor.DefaultImpls.onPreStitch(this);
         }

         public void putKeySingle(@NotNull IBlockState state, @NotNull List key) {
            Intrinsics.checkParameterIsNotNull(state, "state");
            Intrinsics.checkParameterIsNotNull(key, "key");
            TextureListModelProcessor.DefaultImpls.putKeySingle(this, state, key);
         }

         public void processModelLoad(@NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, @NotNull IModel model) {
            Intrinsics.checkParameterIsNotNull(state, "state");
            Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
            Intrinsics.checkParameterIsNotNull(model, "model");
            TextureListModelProcessor.DefaultImpls.processModelLoad(this, state, modelLoc, model);
         }

         @SubscribeEvent(
            priority = EventPriority.HIGHEST
         )
         public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
            Intrinsics.checkParameterIsNotNull(event, "event");
            TextureListModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
         }

         public void onPostLoad() {
            TextureListModelProcessor.DefaultImpls.onPostLoad(this);
         }

         @SubscribeEvent
         public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
            Intrinsics.checkParameterIsNotNull(event, "event");
            TextureListModelProcessor.DefaultImpls.handleLoadModelData(this, event);
         }

         @Nullable
         public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
            Intrinsics.checkParameterIsNotNull(state, "state");
            return TextureListModelProcessor.DefaultImpls.getVariant(this, state, rand);
         }

         public void addVariant(@NotNull IBlockState state, @NotNull ModelVariant variant) {
            Intrinsics.checkParameterIsNotNull(state, "state");
            Intrinsics.checkParameterIsNotNull(variant, "variant");
            TextureListModelProcessor.DefaultImpls.addVariant(this, state, variant);
         }

         @SubscribeEvent(
            priority = EventPriority.LOW
         )
         public void handlePreStitch(@NotNull Pre event) {
            Intrinsics.checkParameterIsNotNull(event, "event");
            TextureListModelProcessor.DefaultImpls.handlePreStitch(this, event);
         }
      });
      this.modelStem = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            double var10001 = -RenderCactus.this.getCactusStemRadius();
            double var10002 = RenderCactus.this.getCactusStemRadius();
            double var10003 = -RenderCactus.this.getCactusStemRadius();
            double var10004 = RenderCactus.this.getCactusStemRadius();
            double var2 = 0.5D;
            double var4 = var10004;
            double var6 = var10003;
            double var8 = var10002;
            Quad var10 = $receiver.horizontalRectangle(var10001, var6, var8, var4, var2).scaleUV(RenderCactus.this.getCactusStemRadius() * 2.0D);
            Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new Quad[]{var10.getFlipped().move(TuplesKt.to(1.0D, EnumFacing.DOWN)), var10});
            Iterator var3 = $receiver$iv.iterator();

            while(var3.hasNext()) {
               Object element$iv = var3.next();
               Quad it = (Quad)element$iv;
               $receiver.add(Quad.setAoShader$default(it, ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 1, (Object)null), (Function2)null, 2, (Object)null));
            }

            $receiver.addAll((Iterable)Utils.toCross(Quad.setAoShader$default($receiver.verticalRectangle(-0.5D, RenderCactus.this.getCactusStemRadius(), 0.5D, RenderCactus.this.getCactusStemRadius(), -0.5D, 0.5D), ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 1, (Object)null), (Function2)null, 2, (Object)null), EnumFacing.UP));
         }
      }));
      this.modelCross = this.modelSet(64, (Function2)null.INSTANCE);
      this.modelArm = this.modelSet(64, (Function2)null.INSTANCE);
   }
}
