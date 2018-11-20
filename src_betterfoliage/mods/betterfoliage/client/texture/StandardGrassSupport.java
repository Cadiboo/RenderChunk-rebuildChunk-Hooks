package mods.betterfoliage.client.texture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.OptifineCTM;
import mods.octarinecore.client.render.HSB;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.client.resource.ModelVariant;
import mods.octarinecore.client.resource.TextureListModelProcessor;
import mods.octarinecore.client.resource.TextureMediatedRegistry;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00060\u00032\u00020\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\bJ\u001b\u0010+\u001a\u0004\u0018\u00010\u00062\u0006\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0096\u0002J3\u0010+\u001a\u0004\u0018\u00010\u00062\u0006\u0010,\u001a\u00020'2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u0010-\u001a\u00020.H\u0096\u0002J&\u00105\u001a\u00020\u00022\u0006\u00106\u001a\u00020\u001f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u00108\u001a\u000209H\u0016J&\u0010:\u001a\u00020;2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00042\u0006\u0010<\u001a\u00020\u00022\u0006\u00108\u001a\u000209H\u0016J\u0016\u0010=\u001a\u00020;2\u0006\u0010<\u001a\u00020\u00022\u0006\u00108\u001a\u000209R\u0014\u0010\t\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R&\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR,\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001b\"\u0004\b!\u0010\u001dR&\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00020#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001b\"\u0004\b%\u0010\u001dR,\u0010&\u001a\u0014\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0(0\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001b\"\u0004\b*\u0010\u001d¨\u0006>"},
   d2 = {"Lmods/betterfoliage/client/texture/StandardGrassSupport;", "Lmods/octarinecore/client/resource/TextureListModelProcessor;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "Lmods/octarinecore/client/resource/TextureMediatedRegistry;", "", "", "Lmods/betterfoliage/client/texture/GrassInfo;", "Lmods/betterfoliage/client/texture/IGrassRegistry;", "()V", "logName", "getLogName", "()Ljava/lang/String;", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "matchClasses", "Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "getMatchClasses", "()Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "modelTextures", "Lmods/octarinecore/common/config/ModelTextureList;", "getModelTextures", "()Ljava/util/List;", "textureToValue", "", "getTextureToValue", "()Ljava/util/Map;", "setTextureToValue", "(Ljava/util/Map;)V", "variantToKey", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "setVariantToKey", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "get", "state", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "face", "Lnet/minecraft/util/EnumFacing;", "processStitch", "variant", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "processTexture", "", "texture", "registerGrass", "BetterFoliage-MC1.12"}
)
public final class StandardGrassSupport implements TextureListModelProcessor, TextureMediatedRegistry, IGrassRegistry {
   @NotNull
   private static Map variants;
   @NotNull
   private static Map variantToKey;
   @NotNull
   private static Map variantToValue;
   @NotNull
   private static Map textureToValue;
   @NotNull
   private static final Logger logger;
   @NotNull
   private static final String logName = "StandardGrassSupport";
   public static final StandardGrassSupport INSTANCE;

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
   public Map getTextureToValue() {
      return textureToValue;
   }

   public void setTextureToValue(@NotNull Map var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      textureToValue = var1;
   }

   @NotNull
   public Logger getLogger() {
      return logger;
   }

   @NotNull
   public String getLogName() {
      return logName;
   }

   @NotNull
   public ConfigurableBlockMatcher getMatchClasses() {
      return Config.blocks.INSTANCE.getGrassClasses();
   }

   @NotNull
   public List getModelTextures() {
      return Config.blocks.INSTANCE.getGrassModels().getList();
   }

   @Nullable
   public GrassInfo get(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(face, "face");
      ModelVariant var10000 = this.getVariant(state, rand);
      if (var10000 != null) {
         ModelVariant variant = var10000;
         TextureAtlasSprite var8 = (TextureAtlasSprite)this.getVariantToValue().get(variant);
         if (var8 != null) {
            TextureAtlasSprite baseTexture = var8;
            GrassInfo var9 = (GrassInfo)this.getTextureToValue().get(OptifineCTM.INSTANCE.override(baseTexture, world, pos, face));
            if (var9 == null) {
               var9 = (GrassInfo)this.getTextureToValue().get(baseTexture);
            }

            return var9;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   public GrassInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      ModelVariant var10000 = this.getVariant(state, rand);
      if (var10000 != null) {
         ModelVariant variant = var10000;
         Object var4 = this.getVariantToValue().get(variant);
         TextureAtlasSprite it = (TextureAtlasSprite)var4;
         return it == null ? null : (GrassInfo)INSTANCE.getTextureToValue().get(it);
      } else {
         return null;
      }
   }

   @NotNull
   public TextureAtlasSprite processStitch(@NotNull ModelVariant variant, @NotNull List key, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      Intrinsics.checkParameterIsNotNull(key, "key");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      return mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(0));
   }

   public void processTexture(@NotNull List variants, @NotNull TextureAtlasSprite texture, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(variants, "variants");
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      this.registerGrass(texture, atlas);
      Iterable $receiver$iv = (Iterable)variants;
      OptifineCTM var13 = OptifineCTM.INSTANCE;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var7 = $receiver$iv.iterator();

      while(var7.hasNext()) {
         Object item$iv$iv = var7.next();
         ModelVariant it = (ModelVariant)item$iv$iv;
         IBlockState var15 = it.getState();
         destination$iv$iv.add(var15);
      }

      List var14 = (List)destination$iv$iv;
      $receiver$iv = (Iterable)var13.getAllCTM(var14, texture);
      Iterator var5 = $receiver$iv.iterator();

      while(var5.hasNext()) {
         Object element$iv = var5.next();
         TextureAtlasSprite it = (TextureAtlasSprite)element$iv;
         INSTANCE.registerGrass(it, atlas);
      }

   }

   public final void registerGrass(@NotNull TextureAtlasSprite texture, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      this.getLogger().log(Level.DEBUG, "" + this.getLogName() + ": texture " + texture.func_94215_i());
      HSB.Companion var10000 = HSB.Companion;
      Integer var10001 = mods.octarinecore.client.resource.Utils.getAverageColor(texture);
      HSB hsb = var10000.fromColor(var10001 != null ? var10001.intValue() : 0);
      Integer var7;
      if ((double)hsb.getSaturation() >= Config.shortGrass.INSTANCE.getSaturationThreshold()) {
         this.getLogger().log(Level.DEBUG, "" + this.getLogName() + ":         brightness " + hsb.getBrightness());
         this.getLogger().log(Level.DEBUG, "" + this.getLogName() + ":         saturation " + hsb.getSaturation() + " >= " + Config.shortGrass.INSTANCE.getSaturationThreshold() + ", using texture color");
         var7 = HSB.copy$default(hsb, 0.0F, 0.0F, Math.min(0.9F, hsb.getBrightness() * 2.0F), 3, (Object)null).getAsColor();
      } else {
         this.getLogger().log(Level.DEBUG, "" + this.getLogName() + ":         saturation " + hsb.getSaturation() + " < " + Config.shortGrass.INSTANCE.getSaturationThreshold() + ", using block color");
         var7 = null;
      }

      Integer overrideColor = var7;
      Map var5 = this.getTextureToValue();
      GrassInfo var6 = new GrassInfo(texture, overrideColor);
      var5.put(texture, var6);
   }

   static {
      StandardGrassSupport var0 = new StandardGrassSupport();
      INSTANCE = var0;
      MinecraftForge.EVENT_BUS.register(var0);
      variants = (Map)(new LinkedHashMap());
      variantToKey = (Map)(new LinkedHashMap());
      variantToValue = MapsKt.emptyMap();
      textureToValue = (Map)(new LinkedHashMap());
      logger = BetterFoliageMod.INSTANCE.getLogDetail();
      logName = "StandardGrassSupport";
   }

   public void onPreStitch() {
      TextureListModelProcessor.DefaultImpls.onPreStitch(this);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
   }

   public void addVariant(@NotNull IBlockState state, @NotNull ModelVariant variant) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      TextureListModelProcessor.DefaultImpls.addVariant(this, state, variant);
   }

   public void processModelLoad(@NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, @NotNull IModel model) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
      Intrinsics.checkParameterIsNotNull(model, "model");
      TextureListModelProcessor.DefaultImpls.processModelLoad(this, state, modelLoc, model);
   }

   @SubscribeEvent
   public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.handleLoadModelData(this, event);
   }

   public void putKeySingle(@NotNull IBlockState state, @NotNull List key) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(key, "key");
      TextureListModelProcessor.DefaultImpls.putKeySingle(this, state, key);
   }

   public void onPostLoad() {
      TextureListModelProcessor.DefaultImpls.onPostLoad(this);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureMediatedRegistry.DefaultImpls.handlePreStitch(this, event);
   }

   @Nullable
   public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return TextureListModelProcessor.DefaultImpls.getVariant(this, state, rand);
   }
}
