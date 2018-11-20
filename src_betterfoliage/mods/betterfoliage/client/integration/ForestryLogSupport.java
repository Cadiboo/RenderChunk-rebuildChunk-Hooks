package mods.betterfoliage.client.integration;

import com.google.common.collect.ImmutableSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.render.IColumnRegistry;
import mods.betterfoliage.client.render.IColumnTextureInfo;
import mods.betterfoliage.client.render.StandardLogSupport;
import mods.betterfoliage.client.render.StaticColumnInfo;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.client.resource.ModelProcessor;
import mods.octarinecore.client.resource.ModelVariant;
import mods.octarinecore.client.resource.Utils;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import mods.octarinecore.metaprog.ClassRef;
import mods.octarinecore.metaprog.MethodRef;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
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
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u00012\u00020\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0006J\u001b\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001eH\u0096\u0002J \u0010\u001f\u001a\u00020 2\u0006\u0010\u001c\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0016J(\u0010%\u001a\u0004\u0018\u00010\u00042\u0006\u0010&\u001a\u00020\r2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010(\u001a\u00020)H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR,\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R&\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R,\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00180\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000f\"\u0004\b\u001a\u0010\u0011¨\u0006*"},
   d2 = {"Lmods/betterfoliage/client/integration/ForestryLogSupport;", "Lmods/octarinecore/client/resource/ModelProcessor;", "", "", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "Lmods/betterfoliage/client/render/IColumnRegistry;", "()V", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "variantToKey", "", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "()Ljava/util/Map;", "setVariantToKey", "(Ljava/util/Map;)V", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "get", "state", "rand", "", "processModelLoad", "", "modelLoc", "Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;", "model", "Lnet/minecraftforge/client/model/IModel;", "processStitch", "variant", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public final class ForestryLogSupport implements ModelProcessor, IColumnRegistry {
   @NotNull
   private static Map variants;
   @NotNull
   private static Map variantToKey;
   @NotNull
   private static Map variantToValue;
   @NotNull
   private static final Logger logger;
   public static final ForestryLogSupport INSTANCE;

   @NotNull
   public Map getVariants() {
      return variants;
   }

   public void setVariants(@NotNull Map var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      variants = var1;
   }

   @NotNull
   public Map getVariantToKey() {
      return variantToKey;
   }

   public void setVariantToKey(@NotNull Map var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      variantToKey = var1;
   }

   @NotNull
   public Map getVariantToValue() {
      return variantToValue;
   }

   public void setVariantToValue(@NotNull Map var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      variantToValue = var1;
   }

   @NotNull
   public Logger getLogger() {
      return logger;
   }

   public void processModelLoad(@NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, @NotNull IModel model) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
      Intrinsics.checkParameterIsNotNull(model, "model");
      ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
      Block var10001 = state.func_177230_c();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
      if (var10000.matchesClass(var10001)) {
         ImmutableSet var12 = (ImmutableSet)state.func_177228_b().entrySet();
         Intrinsics.checkExpressionValueIsNotNull(var12, "state.properties.entries");
         Iterable var5 = (Iterable)var12;
         Iterator var7 = var5.iterator();

         Object var14;
         Object var16;
         while(true) {
            if (!var7.hasNext()) {
               var16 = null;
               break;
            }

            Object var8;
            boolean var15;
            label36: {
               var8 = var7.next();
               Entry it = (Entry)var8;
               ClassRef var13 = ForestryIntegration.INSTANCE.getPropertyWoodType();
               var14 = it.getKey();
               Intrinsics.checkExpressionValueIsNotNull(var14, "it.key");
               if (var13.isInstance(var14)) {
                  var13 = ForestryIntegration.INSTANCE.getIWoodType();
                  var14 = it.getValue();
                  Intrinsics.checkExpressionValueIsNotNull(var14, "it.value");
                  if (var13.isInstance(var14)) {
                     var15 = true;
                     break label36;
                  }
               }

               var15 = false;
            }

            if (var15) {
               var16 = var8;
               break;
            }
         }

         Entry var17 = (Entry)var16;
         if (var17 != null) {
            Entry woodType = var17;
            this.getLogger().log(Level.DEBUG, "ForestryLogSupport: block state " + state.toString());
            this.getLogger().log(Level.DEBUG, "ForestryLogSupport:     variant " + woodType.getValue().toString());
            MethodRef var18 = ForestryIntegration.INSTANCE.getBarkTex();
            var14 = woodType.getValue();
            Intrinsics.checkExpressionValueIsNotNull(var14, "woodType.value");
            String bark = (String)var18.invoke(var14);
            var18 = ForestryIntegration.INSTANCE.getHeartTex();
            var14 = woodType.getValue();
            Intrinsics.checkExpressionValueIsNotNull(var14, "woodType.value");
            String heart = (String)var18.invoke(var14);
            this.getLogger().log(Level.DEBUG, "ForestryLogSupport:    textures [heart=" + heart + ", bark=" + bark + ']');
            if (bark != null && heart != null) {
               this.putKeySingle(state, (Object)CollectionsKt.listOf(new String[]{heart, bark}));
            }

         }
      }
   }

   @Nullable
   public IColumnTextureInfo processStitch(@NotNull ModelVariant variant, @NotNull List key, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      Intrinsics.checkParameterIsNotNull(key, "key");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      TextureAtlasSprite heart = Utils.registerSprite(atlas, (String)key.get(0));
      TextureAtlasSprite bark = Utils.registerSprite(atlas, (String)key.get(1));
      return (IColumnTextureInfo)(new StaticColumnInfo(StandardLogSupport.INSTANCE.getAxis(variant.getState()), heart, heart, CollectionsKt.listOf(bark)));
   }

   @Nullable
   public IColumnTextureInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      List var10000 = (List)this.getVariants().get(state);
      IColumnTextureInfo var6;
      if (var10000 != null) {
         List var3 = var10000;
         var6 = (IColumnTextureInfo)INSTANCE.getVariantToValue().get(var3.get(0));
      } else {
         var6 = null;
      }

      return var6;
   }

   static {
      ForestryLogSupport var0 = new ForestryLogSupport();
      INSTANCE = var0;
      variants = (Map)(new LinkedHashMap());
      variantToKey = (Map)(new LinkedHashMap());
      variantToValue = MapsKt.emptyMap();
      logger = BetterFoliageMod.INSTANCE.getLogDetail();
      MinecraftForge.EVENT_BUS.register(var0);
   }

   @SubscribeEvent
   public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.handleLoadModelData(this, event);
   }

   public void onPostLoad() {
      ModelProcessor.DefaultImpls.onPostLoad(this);
   }

   public void putKeySingle(@NotNull IBlockState state, @NotNull List key) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(key, "key");
      ModelProcessor.DefaultImpls.putKeySingle(this, state, key);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.handlePreStitch(this, event);
   }

   @Nullable
   public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return ModelProcessor.DefaultImpls.getVariant(this, state, rand);
   }

   public void onPreStitch() {
      ModelProcessor.DefaultImpls.onPreStitch(this);
   }

   public void addVariant(@NotNull IBlockState state, @NotNull ModelVariant variant) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      ModelProcessor.DefaultImpls.addVariant(this, state, variant);
   }
}
