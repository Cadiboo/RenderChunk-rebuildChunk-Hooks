package mods.octarinecore.client.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u0003J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0017J&\u0010\u000f\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015H&R$\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00010\u0006X¦\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0016"},
   d2 = {"Lmods/octarinecore/client/resource/TextureMediatedRegistry;", "T1", "T3", "Lmods/octarinecore/client/resource/ModelProcessor;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "textureToValue", "", "getTextureToValue", "()Ljava/util/Map;", "setTextureToValue", "(Ljava/util/Map;)V", "handlePreStitch", "", "event", "Lnet/minecraftforge/client/event/TextureStitchEvent$Pre;", "processTexture", "states", "", "Lmods/octarinecore/client/resource/ModelVariant;", "texture", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public interface TextureMediatedRegistry extends ModelProcessor {
   @NotNull
   Map getTextureToValue();

   void setTextureToValue(@NotNull Map var1);

   void handlePreStitch(@NotNull Pre var1);

   void processTexture(@NotNull List var1, @NotNull TextureAtlasSprite var2, @NotNull TextureMap var3);

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 3
   )
   public static final class DefaultImpls {
      public static void handlePreStitch(@NotNull TextureMediatedRegistry $this, Pre event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         $this.getTextureToValue().clear();
         ModelProcessor.DefaultImpls.handlePreStitch((ModelProcessor)$this, event);
         Iterable $receiver$iv = (Iterable)$this.getVariantToValue().entrySet();
         Map destination$iv$iv = (Map)(new LinkedHashMap());
         Iterator var6 = $receiver$iv.iterator();

         while(var6.hasNext()) {
            Object element$iv$iv = var6.next();
            Entry it = (Entry)element$iv$iv;
            Object key$iv$iv = (TextureAtlasSprite)it.getValue();
            Object value$iv$iv$iv = destination$iv$iv.get(key$iv$iv);
            Object var10000;
            if (value$iv$iv$iv == null) {
               Object answer$iv$iv$iv = new ArrayList();
               destination$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
               var10000 = answer$iv$iv$iv;
            } else {
               var10000 = value$iv$iv$iv;
            }

            List list$iv$iv = (List)var10000;
            it = (Entry)element$iv$iv;
            ModelVariant var19 = (ModelVariant)it.getKey();
            list$iv$iv.add(var19);
         }

         Map textureToVariants = destination$iv$iv;
         $receiver$iv = (Iterable)CollectionsKt.toSet((Iterable)$this.getVariantToValue().values());
         Iterator var4 = $receiver$iv.iterator();

         while(var4.hasNext()) {
            Object element$iv = var4.next();
            TextureAtlasSprite it = (TextureAtlasSprite)element$iv;
            Object var10001 = textureToVariants.get(it);
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            List var22 = (List)var10001;
            TextureMap var10003 = event.getMap();
            Intrinsics.checkExpressionValueIsNotNull(var10003, "event.map");
            $this.processTexture(var22, it, var10003);
         }

      }

      @SubscribeEvent
      public static void handleLoadModelData(@NotNull TextureMediatedRegistry $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         ModelProcessor.DefaultImpls.handleLoadModelData((ModelProcessor)$this, event);
      }

      @SubscribeEvent(
         priority = EventPriority.HIGHEST
      )
      public static void clearBeforeLoadModelData(@NotNull TextureMediatedRegistry $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         ModelProcessor.DefaultImpls.clearBeforeLoadModelData((ModelProcessor)$this, event);
      }

      public static void onPreStitch(TextureMediatedRegistry $this) {
         ModelProcessor.DefaultImpls.onPreStitch((ModelProcessor)$this);
      }

      public static void onPostLoad(TextureMediatedRegistry $this) {
         ModelProcessor.DefaultImpls.onPostLoad((ModelProcessor)$this);
      }

      @Nullable
      public static ModelVariant getVariant(@NotNull TextureMediatedRegistry $this, IBlockState state, int rand) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         return ModelProcessor.DefaultImpls.getVariant((ModelProcessor)$this, state, rand);
      }

      public static void addVariant(@NotNull TextureMediatedRegistry $this, @NotNull IBlockState state, ModelVariant variant) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         Intrinsics.checkParameterIsNotNull(variant, "variant");
         ModelProcessor.DefaultImpls.addVariant((ModelProcessor)$this, state, variant);
      }

      public static void putKeySingle(@NotNull TextureMediatedRegistry $this, IBlockState state, Object key) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         ModelProcessor.DefaultImpls.putKeySingle((ModelProcessor)$this, state, key);
      }
   }
}
