package mods.octarinecore.client.resource;

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
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.loader.Refs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\nH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0017J\u001a\u0010\u001f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0017J\u0010\u0010#\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020$H\u0017J\b\u0010%\u001a\u00020\u0019H\u0016J\b\u0010&\u001a\u00020\u0019H\u0016J \u0010'\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H&J'\u0010,\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010-\u001a\u00028\u00002\u0006\u0010.\u001a\u00020/H&¢\u0006\u0002\u00100J\u001d\u00101\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010-\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00102R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R$\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00000\tX¦\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00028\u00010\u0010X¦\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\f\"\u0004\b\u0012\u0010\u000eR*\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00150\tX¦\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u00063"},
   d2 = {"Lmods/octarinecore/client/resource/ModelProcessor;", "T1", "T2", "", "logger", "Lorg/apache/logging/log4j/Logger;", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "variantToKey", "", "Lmods/octarinecore/client/resource/ModelVariant;", "getVariantToKey", "()Ljava/util/Map;", "setVariantToKey", "(Ljava/util/Map;)V", "variantToValue", "", "getVariantToValue", "setVariantToValue", "variants", "Lnet/minecraft/block/state/IBlockState;", "", "getVariants", "setVariants", "addVariant", "", "state", "variant", "clearBeforeLoadModelData", "event", "Lmods/octarinecore/client/resource/LoadModelDataEvent;", "getVariant", "rand", "", "handleLoadModelData", "handlePreStitch", "Lnet/minecraftforge/client/event/TextureStitchEvent$Pre;", "onPostLoad", "onPreStitch", "processModelLoad", "modelLoc", "Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;", "model", "Lnet/minecraftforge/client/model/IModel;", "processStitch", "key", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "(Lmods/octarinecore/client/resource/ModelVariant;Ljava/lang/Object;Lnet/minecraft/client/renderer/texture/TextureMap;)Ljava/lang/Object;", "putKeySingle", "(Lnet/minecraft/block/state/IBlockState;Ljava/lang/Object;)V", "BetterFoliage-MC1.12"}
)
public interface ModelProcessor {
   @Nullable
   Logger getLogger();

   @NotNull
   Map getVariants();

   void setVariants(@NotNull Map var1);

   @NotNull
   Map getVariantToKey();

   void setVariantToKey(@NotNull Map var1);

   @NotNull
   Map getVariantToValue();

   void setVariantToValue(@NotNull Map var1);

   void addVariant(@NotNull IBlockState var1, @NotNull ModelVariant var2);

   @Nullable
   ModelVariant getVariant(@NotNull IBlockState var1, int var2);

   void putKeySingle(@NotNull IBlockState var1, Object var2);

   void onPostLoad();

   void onPreStitch();

   void processModelLoad(@NotNull IBlockState var1, @NotNull ModelResourceLocation var2, @NotNull IModel var3);

   @Nullable
   Object processStitch(@NotNull ModelVariant var1, Object var2, @NotNull TextureMap var3);

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   void clearBeforeLoadModelData(@NotNull LoadModelDataEvent var1);

   @SubscribeEvent
   void handleLoadModelData(@NotNull LoadModelDataEvent var1);

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   void handlePreStitch(@NotNull Pre var1);

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 3
   )
   public static final class DefaultImpls {
      public static void addVariant(@NotNull ModelProcessor $this, @NotNull IBlockState state, ModelVariant variant) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         Intrinsics.checkParameterIsNotNull(variant, "variant");
         Map $receiver$iv = $this.getVariants();
         Object value$iv = $receiver$iv.get(state);
         Object var10000;
         if (value$iv == null) {
            Object answer$iv = (List)(new ArrayList());
            $receiver$iv.put(state, answer$iv);
            var10000 = answer$iv;
         } else {
            var10000 = value$iv;
         }

         ((List)var10000).add(variant);
      }

      @Nullable
      public static ModelVariant getVariant(@NotNull ModelProcessor $this, IBlockState state, int rand) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         List var10000 = (List)$this.getVariants().get(state);
         ModelVariant var6;
         if (var10000 != null) {
            List var3 = var10000;
            var6 = (ModelVariant)var3.get(rand % var3.size());
         } else {
            var6 = null;
         }

         return var6;
      }

      public static void putKeySingle(@NotNull ModelProcessor $this, IBlockState state, Object key) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         ModelVariant variant = new ModelVariant(state, (ResourceLocation)null, 1);
         Map var4 = $this.getVariants();
         List var5 = CollectionsKt.mutableListOf(new ModelVariant[]{variant});
         var4.put(state, var5);
         var4 = $this.getVariantToKey();
         var4.put(variant, key);
      }

      public static void onPostLoad(ModelProcessor $this) {
      }

      public static void onPreStitch(ModelProcessor $this) {
      }

      @SubscribeEvent(
         priority = EventPriority.HIGHEST
      )
      public static void clearBeforeLoadModelData(@NotNull ModelProcessor $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         $this.getVariants().clear();
         $this.getVariantToKey().clear();
      }

      @SubscribeEvent
      public static void handleLoadModelData(@NotNull ModelProcessor $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         $this.onPostLoad();
         RegistryNamespacedDefaultedByKey var10000 = Block.field_149771_c;
         Intrinsics.checkExpressionValueIsNotNull(Block.field_149771_c, "Block.REGISTRY");
         Iterable $receiver$iv = (Iterable)var10000;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         Iterator var6 = $receiver$iv.iterator();

         Object var20;
         while(var6.hasNext()) {
            Object element$iv$iv = var6.next();
            Block block = (Block)element$iv$iv;
            BlockModelShapes var19 = event.getLoader().field_177610_k;
            Intrinsics.checkExpressionValueIsNotNull(var19, "event.loader.blockModelShapes");
            var20 = var19.func_178120_a().field_178450_a.get(block);
            if (!(var20 instanceof IStateMapper)) {
               var20 = null;
            }

            IStateMapper var21 = (IStateMapper)var20;
            if (var21 == null) {
               var21 = (IStateMapper)(new DefaultStateMapper());
            }

            IStateMapper mapper = var21;
            if (block == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.block.Block");
            }

            Map var22 = mapper.func_178130_a(block);
            if (var22 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<net.minecraft.block.state.IBlockState, net.minecraft.client.renderer.block.model.ModelResourceLocation>");
            }

            Iterable list$iv$iv = (Iterable)var22.entrySet();
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
         }

         List stateMappings = (List)destination$iv$iv;
         var20 = Refs.INSTANCE.getStateModels().get(event.getLoader());
         if (var20 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<net.minecraft.client.renderer.block.model.ModelResourceLocation, net.minecraftforge.client.model.IModel>");
         } else {
            Map stateModels = (Map)var20;
            Iterable $receiver$iv = (Iterable)stateMappings;
            Iterator var15 = $receiver$iv.iterator();

            while(var15.hasNext()) {
               Object element$iv = var15.next();
               Entry mapping = (Entry)element$iv;
               if (((IBlockState)mapping.getKey()).func_177230_c() != null) {
                  IModel var23 = (IModel)stateModels.get(mapping.getValue());
                  if (var23 != null) {
                     IModel var18 = var23;
                     $this.processModelLoad((IBlockState)mapping.getKey(), (ModelResourceLocation)mapping.getValue(), var18);
                  }
               }
            }

         }
      }

      @SubscribeEvent(
         priority = EventPriority.LOW
      )
      public static void handlePreStitch(@NotNull ModelProcessor $this, Pre event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         $this.onPreStitch();
         Map $receiver$iv = $this.getVariantToKey();
         Map destination$iv$iv = (Map)(new LinkedHashMap(MapsKt.mapCapacity($receiver$iv.size())));
         Iterable $receiver$iv$iv$iv = (Iterable)$receiver$iv.entrySet();
         Iterator var6 = $receiver$iv$iv$iv.iterator();

         while(var6.hasNext()) {
            Object element$iv$iv$iv = var6.next();
            Entry it$iv$iv = (Entry)element$iv$iv$iv;
            Object var10 = it$iv$iv.getKey();
            Entry it = (Entry)element$iv$iv$iv;
            ModelVariant var10001 = (ModelVariant)it.getKey();
            Object var10002 = it.getValue();
            TextureMap var10003 = event.getMap();
            Intrinsics.checkExpressionValueIsNotNull(var10003, "event.map");
            Object var20 = $this.processStitch(var10001, var10002, var10003);
            destination$iv$iv.put(var10, var20);
         }

         Map $receiver$iv$iv = destination$iv$iv;
         LinkedHashMap result$iv$iv = new LinkedHashMap();
         var6 = $receiver$iv$iv.entrySet().iterator();

         while(var6.hasNext()) {
            Entry entry$iv$iv = (Entry)var6.next();
            Object it$iv = entry$iv$iv.getValue();
            if (it$iv != null) {
               result$iv$iv.put(entry$iv$iv.getKey(), entry$iv$iv.getValue());
            }
         }

         Map var18 = (Map)result$iv$iv;
         $this.setVariantToValue(var18);
      }
   }
}
