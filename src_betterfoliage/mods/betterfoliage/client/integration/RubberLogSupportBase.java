package mods.betterfoliage.client.integration;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.render.IColumnRegistry;
import mods.betterfoliage.client.render.IColumnTextureInfo;
import mods.betterfoliage.client.render.StaticColumnInfo;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.client.resource.ModelProcessor;
import mods.octarinecore.client.resource.ModelVariant;
import mods.octarinecore.client.resource.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u001b\u0010\u001a\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0096\u0002J\"\u0010\u001e\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR&\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R&\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R,\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00170\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010¨\u0006#"},
   d2 = {"Lmods/betterfoliage/client/integration/RubberLogSupportBase;", "Lmods/octarinecore/client/resource/ModelProcessor;", "Lmods/betterfoliage/client/integration/RubberLogModelInfo;", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "Lmods/betterfoliage/client/render/IColumnRegistry;", "()V", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "variantToKey", "", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "()Ljava/util/Map;", "setVariantToKey", "(Ljava/util/Map;)V", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "get", "state", "rand", "", "processStitch", "variant", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public abstract class RubberLogSupportBase implements ModelProcessor, IColumnRegistry {
   @NotNull
   private Map variants;
   @NotNull
   private Map variantToKey;
   @NotNull
   private Map variantToValue;
   @NotNull
   private final Logger logger;

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

   @Nullable
   public IColumnTextureInfo processStitch(@NotNull ModelVariant variant, @NotNull RubberLogModelInfo key, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      Intrinsics.checkParameterIsNotNull(key, "key");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      TextureAtlasSprite topTex = Utils.registerSprite(atlas, (String)key.getTextures().get(0));
      TextureAtlasSprite bottomTex = Utils.registerSprite(atlas, (String)key.getTextures().get(1));
      TextureAtlasSprite sideTex = Utils.registerSprite(atlas, (String)key.getTextures().get(2));
      if (key.getSpotDir() == null) {
         return (IColumnTextureInfo)(new StaticColumnInfo(key.getAxis(), topTex, bottomTex, CollectionsKt.listOf(sideTex)));
      } else {
         TextureAtlasSprite spotTex = Utils.registerSprite(atlas, (String)key.getTextures().get(3));
         return (IColumnTextureInfo)(new RubberLogColumnInfo(key.getAxis(), key.getSpotDir(), topTex, bottomTex, sideTex, spotTex));
      }
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

   public RubberLogSupportBase() {
      Map var2 = (Map)(new LinkedHashMap());
      this.variants = var2;
      var2 = (Map)(new LinkedHashMap());
      this.variantToKey = var2;
      var2 = MapsKt.emptyMap();
      this.variantToValue = var2;
      this.logger = BetterFoliageMod.INSTANCE.getLogDetail();
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.handleLoadModelData(this, event);
   }

   @Nullable
   public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return ModelProcessor.DefaultImpls.getVariant(this, state, rand);
   }

   public void onPostLoad() {
      ModelProcessor.DefaultImpls.onPostLoad(this);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.handlePreStitch(this, event);
   }

   public void onPreStitch() {
      ModelProcessor.DefaultImpls.onPreStitch(this);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      ModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
   }

   public void addVariant(@NotNull IBlockState state, @NotNull ModelVariant variant) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      ModelProcessor.DefaultImpls.addVariant(this, state, variant);
   }

   public void putKeySingle(@NotNull IBlockState state, @NotNull RubberLogModelInfo key) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(key, "key");
      ModelProcessor.DefaultImpls.putKeySingle(this, state, key);
   }
}
