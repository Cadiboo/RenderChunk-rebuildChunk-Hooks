package mods.betterfoliage.client.render;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.client.resource.ModelVariant;
import mods.octarinecore.client.resource.TextureListModelProcessor;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.IModel;
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
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\u001b\u0010&\u001a\u0004\u0018\u00010\u00022\u0006\u0010'\u001a\u00020\"2\u0006\u0010(\u001a\u00020)H\u0096\u0002J\u0010\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010'\u001a\u00020\"J(\u0010,\u001a\u0004\u0018\u00010\u00022\u0006\u0010-\u001a\u00020\u00182\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010/\u001a\u000200H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R,\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00120\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR&\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00020\u001eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u001a\"\u0004\b \u0010\u001cR,\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180#0\u0017X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001a\"\u0004\b%\u0010\u001c¨\u00061"},
   d2 = {"Lmods/betterfoliage/client/render/StandardLogSupport;", "Lmods/octarinecore/client/resource/TextureListModelProcessor;", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "Lmods/betterfoliage/client/render/IColumnRegistry;", "()V", "logName", "", "getLogName", "()Ljava/lang/String;", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "matchClasses", "Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "getMatchClasses", "()Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "modelTextures", "", "Lmods/octarinecore/common/config/ModelTextureList;", "getModelTextures", "()Ljava/util/List;", "variantToKey", "", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "()Ljava/util/Map;", "setVariantToKey", "(Ljava/util/Map;)V", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "get", "state", "rand", "", "getAxis", "Lnet/minecraft/util/EnumFacing$Axis;", "processStitch", "variant", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public final class StandardLogSupport implements TextureListModelProcessor, IColumnRegistry {
   @NotNull
   private static Map variants;
   @NotNull
   private static Map variantToKey;
   @NotNull
   private static Map variantToValue;
   @NotNull
   private static final Logger logger;
   @NotNull
   private static final String logName = "StandardLogSupport";
   public static final StandardLogSupport INSTANCE;

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

   @NotNull
   public String getLogName() {
      return logName;
   }

   @NotNull
   public ConfigurableBlockMatcher getMatchClasses() {
      return Config.blocks.INSTANCE.getLogClasses();
   }

   @NotNull
   public List getModelTextures() {
      return Config.blocks.INSTANCE.getLogModels().getList();
   }

   @Nullable
   public IColumnTextureInfo processStitch(@NotNull ModelVariant variant, @NotNull List key, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      Intrinsics.checkParameterIsNotNull(key, "key");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      TextureAtlasSprite topTex = mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(0));
      TextureAtlasSprite bottomTex = mods.octarinecore.client.resource.Utils.registerSprite(atlas, (String)key.get(1));
      Iterable $receiver$iv = (Iterable)CollectionsKt.drop((Iterable)key, 2);
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var10 = $receiver$iv.iterator();

      while(var10.hasNext()) {
         Object item$iv$iv = var10.next();
         String it = (String)item$iv$iv;
         TextureAtlasSprite var17 = mods.octarinecore.client.resource.Utils.registerSprite(atlas, it);
         destination$iv$iv.add(var17);
      }

      List sideTexList = (List)destination$iv$iv;
      return sideTexList.isEmpty() ? null : (IColumnTextureInfo)(new StaticColumnInfo(this.getAxis(variant.getState()), topTex, bottomTex, sideTexList));
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

   @Nullable
   public final Axis getAxis(@NotNull final IBlockState state) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      String var10000 = (String)mods.octarinecore.Utils.tryDefault((Object)null, (Function0)(new Function0() {
         @NotNull
         public final String invoke() {
            return ((EnumAxis)state.func_177229_b((IProperty)BlockLog.field_176299_a)).toString();
         }
      }));
      if (var10000 == null) {
         label59: {
            ImmutableSet var10 = (ImmutableSet)state.func_177228_b().entrySet();
            Intrinsics.checkExpressionValueIsNotNull(var10, "state.properties.entries");
            Iterable var3 = (Iterable)var10;
            Iterator var5 = var3.iterator();

            Object var11;
            label53: {
               while(var5.hasNext()) {
                  Object var6 = var5.next();
                  Entry it = (Entry)var6;
                  var10000 = ((IProperty)it.getKey()).func_177701_a();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "it.key.getName()");
                  String var8 = var10000;
                  if (var8 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                  }

                  var10000 = var8.toLowerCase();
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
                  if (Intrinsics.areEqual(var10000, "axis")) {
                     var11 = var6;
                     break label53;
                  }
               }

               var11 = null;
            }

            Entry var12 = (Entry)var11;
            if (var12 != null) {
               Comparable var13 = (Comparable)var12.getValue();
               if (var13 != null) {
                  var10000 = var13.toString();
                  break label59;
               }
            }

            var10000 = null;
         }
      }

      String axis = var10000;
      Axis var14;
      if (axis != null) {
         switch(axis.hashCode()) {
         case 120:
            if (axis.equals("x")) {
               var14 = Axis.X;
               return var14;
            }
            break;
         case 121:
            if (axis.equals("y")) {
               var14 = Axis.Y;
               return var14;
            }
            break;
         case 122:
            if (axis.equals("z")) {
               var14 = Axis.Z;
               return var14;
            }
         }
      }

      var14 = null;
      return var14;
   }

   static {
      StandardLogSupport var0 = new StandardLogSupport();
      INSTANCE = var0;
      LogRegistry.INSTANCE.getSubRegistries().add(var0);
      MinecraftForge.EVENT_BUS.register(var0);
      variants = (Map)(new LinkedHashMap());
      variantToKey = (Map)(new LinkedHashMap());
      variantToValue = MapsKt.emptyMap();
      logger = BetterFoliageMod.INSTANCE.getLogDetail();
      logName = "StandardLogSupport";
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

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
   }

   @Nullable
   public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return TextureListModelProcessor.DefaultImpls.getVariant(this, state, rand);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.handlePreStitch(this, event);
   }

   @SubscribeEvent
   public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.handleLoadModelData(this, event);
   }

   public void onPreStitch() {
      TextureListModelProcessor.DefaultImpls.onPreStitch(this);
   }

   public void onPostLoad() {
      TextureListModelProcessor.DefaultImpls.onPostLoad(this);
   }

   public void putKeySingle(@NotNull IBlockState state, @NotNull List key) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(key, "key");
      TextureListModelProcessor.DefaultImpls.putKeySingle(this, state, key);
   }
}
