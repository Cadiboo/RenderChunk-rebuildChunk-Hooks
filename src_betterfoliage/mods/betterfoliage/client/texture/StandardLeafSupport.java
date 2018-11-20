package mods.betterfoliage.client.texture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.BetterFoliageMod;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.OptifineCTM;
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
   d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00060\u00032\u00020\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\bJ\u001b\u0010+\u001a\u0004\u0018\u00010\u00062\u0006\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0096\u0002J3\u0010+\u001a\u0004\u0018\u00010\u00062\u0006\u0010,\u001a\u00020'2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u0010-\u001a\u00020.H\u0096\u0002J&\u00105\u001a\u00020\u00022\u0006\u00106\u001a\u00020\u001f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u00108\u001a\u000209H\u0016J&\u0010:\u001a\u00020;2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00042\u0006\u0010<\u001a\u00020\u00022\u0006\u00108\u001a\u000209H\u0016J\u0016\u0010=\u001a\u00020;2\u0006\u0010<\u001a\u00020\u00022\u0006\u00108\u001a\u000209R\u0014\u0010\t\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R&\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR,\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001b\"\u0004\b!\u0010\u001dR&\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00020#X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001b\"\u0004\b%\u0010\u001dR,\u0010&\u001a\u0014\u0012\u0004\u0012\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0(0\u0019X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001b\"\u0004\b*\u0010\u001d¨\u0006>"},
   d2 = {"Lmods/betterfoliage/client/texture/StandardLeafSupport;", "Lmods/octarinecore/client/resource/TextureListModelProcessor;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "Lmods/octarinecore/client/resource/TextureMediatedRegistry;", "", "", "Lmods/betterfoliage/client/texture/LeafInfo;", "Lmods/betterfoliage/client/texture/ILeafRegistry;", "()V", "logName", "getLogName", "()Ljava/lang/String;", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "matchClasses", "Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "getMatchClasses", "()Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "modelTextures", "Lmods/octarinecore/common/config/ModelTextureList;", "getModelTextures", "()Ljava/util/List;", "textureToValue", "", "getTextureToValue", "()Ljava/util/Map;", "setTextureToValue", "(Ljava/util/Map;)V", "variantToKey", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "setVariantToKey", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "get", "state", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "face", "Lnet/minecraft/util/EnumFacing;", "processStitch", "variant", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "processTexture", "", "texture", "registerLeaf", "BetterFoliage-MC1.12"}
)
public final class StandardLeafSupport implements TextureListModelProcessor, TextureMediatedRegistry, ILeafRegistry {
   @NotNull
   private static final String logName = "StandardLeafSupport";
   @NotNull
   private static Map variants;
   @NotNull
   private static Map variantToKey;
   @NotNull
   private static Map variantToValue;
   @NotNull
   private static Map textureToValue;
   public static final StandardLeafSupport INSTANCE;

   @NotNull
   public String getLogName() {
      return logName;
   }

   @NotNull
   public ConfigurableBlockMatcher getMatchClasses() {
      return Config.blocks.INSTANCE.getLeavesClasses();
   }

   @NotNull
   public List getModelTextures() {
      return Config.blocks.INSTANCE.getLeavesModels().getList();
   }

   @Nullable
   public Logger getLogger() {
      return BetterFoliageMod.INSTANCE.getLogDetail();
   }

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

   @Nullable
   public LeafInfo get(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face, int rand) {
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
            LeafInfo var9 = (LeafInfo)this.getTextureToValue().get(OptifineCTM.INSTANCE.override(baseTexture, world, pos, face));
            if (var9 == null) {
               var9 = (LeafInfo)this.getTextureToValue().get(baseTexture);
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
   public LeafInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      ModelVariant var10000 = this.getVariant(state, rand);
      if (var10000 != null) {
         ModelVariant variant = var10000;
         Object var4 = this.getVariantToValue().get(variant);
         TextureAtlasSprite it = (TextureAtlasSprite)var4;
         return it == null ? null : (LeafInfo)INSTANCE.getTextureToValue().get(it);
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
      Logger var10000 = this.getLogger();
      if (var10000 != null) {
         var10000.log(Level.DEBUG, "" + this.getLogName() + ": leaf texture   " + texture.func_94215_i());
      }

      var10000 = this.getLogger();
      if (var10000 != null) {
         var10000.log(Level.DEBUG, "" + this.getLogName() + ":      #variants " + variants.size());
      }

      var10000 = this.getLogger();
      Iterable $receiver$iv;
      Iterator var7;
      Object e$iv;
      ModelVariant it;
      if (var10000 != null) {
         Level var10001 = Level.DEBUG;
         StringBuilder var10002 = (new StringBuilder()).append("").append(this.getLogName()).append(":      #states   ");
         $receiver$iv = (Iterable)variants;
         StringBuilder var15 = var10002;
         Level var14 = var10001;
         Logger var13 = var10000;
         HashSet set$iv = new HashSet();
         ArrayList list$iv = new ArrayList();
         var7 = $receiver$iv.iterator();

         while(var7.hasNext()) {
            e$iv = var7.next();
            it = (ModelVariant)e$iv;
            Object key$iv = it.getState();
            if (set$iv.add(key$iv)) {
               list$iv.add(e$iv);
            }
         }

         List var16 = (List)list$iv;
         var13.log(var14, var15.append(var16.size()).toString());
      }

      this.registerLeaf(texture, atlas);
      $receiver$iv = (Iterable)variants;
      OptifineCTM var22 = OptifineCTM.INSTANCE;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      var7 = $receiver$iv.iterator();

      while(var7.hasNext()) {
         e$iv = var7.next();
         it = (ModelVariant)e$iv;
         IBlockState var24 = it.getState();
         destination$iv$iv.add(var24);
      }

      List var23 = (List)destination$iv$iv;
      $receiver$iv = (Iterable)var22.getAllCTM(var23, texture);

      TextureAtlasSprite it;
      for(Iterator var17 = $receiver$iv.iterator(); var17.hasNext(); INSTANCE.registerLeaf(it, atlas)) {
         Object element$iv = var17.next();
         it = (TextureAtlasSprite)element$iv;
         var10000 = INSTANCE.getLogger();
         if (var10000 != null) {
            var10000.log(Level.DEBUG, "" + INSTANCE.getLogName() + ":        CTM " + texture.func_94215_i());
         }
      }

   }

   public final void registerLeaf(@NotNull TextureAtlasSprite texture, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      String var10000 = LeafRegistry.INSTANCE.getTypeMappings().getType(texture);
      if (var10000 == null) {
         var10000 = "default";
      }

      String leafType = var10000;
      Logger var7 = this.getLogger();
      if (var7 != null) {
         var7.log(Level.DEBUG, "" + this.getLogName() + ":      particle " + leafType);
      }

      LeafGenerator var10001 = Client.INSTANCE.getGenLeaves();
      String var10002 = texture.func_94215_i();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "texture.iconName");
      TextureAtlasSprite generated = atlas.func_174942_a(var10001.generatedResource(var10002, new Pair[]{TuplesKt.to("type", leafType)}));
      Map var5 = this.getTextureToValue();
      Intrinsics.checkExpressionValueIsNotNull(generated, "generated");
      LeafInfo var6 = new LeafInfo(generated, LeafRegistry.INSTANCE.getParticleType(texture, atlas), 0, 4, (DefaultConstructorMarker)null);
      var5.put(texture, var6);
   }

   static {
      StandardLeafSupport var0 = new StandardLeafSupport();
      INSTANCE = var0;
      MinecraftForge.EVENT_BUS.register(var0);
      logName = "StandardLeafSupport";
      variants = (Map)(new LinkedHashMap());
      variantToKey = (Map)(new LinkedHashMap());
      variantToValue = MapsKt.emptyMap();
      textureToValue = (Map)(new LinkedHashMap());
   }

   public void addVariant(@NotNull IBlockState state, @NotNull ModelVariant variant) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(variant, "variant");
      TextureListModelProcessor.DefaultImpls.addVariant(this, state, variant);
   }

   public void onPreStitch() {
      TextureListModelProcessor.DefaultImpls.onPreStitch(this);
   }

   @SubscribeEvent
   public void handleLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.handleLoadModelData(this, event);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void clearBeforeLoadModelData(@NotNull LoadModelDataEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureListModelProcessor.DefaultImpls.clearBeforeLoadModelData(this, event);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      TextureMediatedRegistry.DefaultImpls.handlePreStitch(this, event);
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

   @Nullable
   public ModelVariant getVariant(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return TextureListModelProcessor.DefaultImpls.getVariant(this, state, rand);
   }

   public void onPostLoad() {
      TextureListModelProcessor.DefaultImpls.onPostLoad(this);
   }
}
