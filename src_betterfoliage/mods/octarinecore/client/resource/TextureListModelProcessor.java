package mods.octarinecore.client.resource;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.config.IBlockMatcher;
import mods.octarinecore.common.config.ModelTextureList;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.IModel;
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
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0004\u0012\u0002H\u00010\u0002J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0012\u0010\u0005\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0018"},
   d2 = {"Lmods/octarinecore/client/resource/TextureListModelProcessor;", "T2", "Lmods/octarinecore/client/resource/ModelProcessor;", "", "", "logName", "getLogName", "()Ljava/lang/String;", "matchClasses", "Lmods/octarinecore/common/config/IBlockMatcher;", "getMatchClasses", "()Lmods/octarinecore/common/config/IBlockMatcher;", "modelTextures", "Lmods/octarinecore/common/config/ModelTextureList;", "getModelTextures", "()Ljava/util/List;", "processModelLoad", "", "state", "Lnet/minecraft/block/state/IBlockState;", "modelLoc", "Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;", "model", "Lnet/minecraftforge/client/model/IModel;", "BetterFoliage-MC1.12"}
)
public interface TextureListModelProcessor extends ModelProcessor {
   @NotNull
   String getLogName();

   @NotNull
   IBlockMatcher getMatchClasses();

   @NotNull
   List getModelTextures();

   void processModelLoad(@NotNull IBlockState var1, @NotNull ModelResourceLocation var2, @NotNull IModel var3);

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 3
   )
   public static final class DefaultImpls {
      public static void processModelLoad(@NotNull TextureListModelProcessor $this, @NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, IModel model) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
         Intrinsics.checkParameterIsNotNull(model, "model");
         IBlockMatcher var10000 = $this.getMatchClasses();
         Block var10001 = state.func_177230_c();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
         Class var46 = var10000.matchingClass(var10001);
         if (var46 != null) {
            Class matchClass = var46;
            Logger var49 = $this.getLogger();
            if (var49 != null) {
               var49.log(Level.DEBUG, "" + $this.getLogName() + ": block state " + state.toString());
            }

            var49 = $this.getLogger();
            if (var49 != null) {
               var49.log(Level.DEBUG, "" + $this.getLogName() + ":       class " + state.func_177230_c().getClass().getName() + " matches " + matchClass.getName());
            }

            Iterable $receiver$iv = (Iterable)Utils.getModelBlockAndLoc(model);
            HashSet set$iv = new HashSet();
            ArrayList list$iv = new ArrayList();
            Iterator var9 = $receiver$iv.iterator();

            while(var9.hasNext()) {
               Object e$iv = var9.next();
               Pair it = (Pair)e$iv;
               Object key$iv = (ResourceLocation)it.getSecond();
               if (set$iv.add(key$iv)) {
                  list$iv.add(e$iv);
               }
            }

            List allModels = (List)list$iv;
            if (allModels.isEmpty()) {
               var49 = $this.getLogger();
               if (var49 != null) {
                  var49.log(Level.DEBUG, "" + $this.getLogName() + ":       no models found");
               }

            } else {
               $receiver$iv = (Iterable)allModels;
               Iterator var29 = $receiver$iv.iterator();

               while(var29.hasNext()) {
                  Object element$iv = var29.next();
                  Pair blockLoc = (Pair)element$iv;
                  Iterable $receiver$iv = (Iterable)$this.getModelTextures();
                  Iterator var35 = $receiver$iv.iterator();

                  Object var51;
                  while(true) {
                     if (!var35.hasNext()) {
                        var51 = null;
                        break;
                     }

                     Object element$iv = var35.next();
                     ModelTextureList it = (ModelTextureList)element$iv;
                     if (Utils.derivesFrom(blockLoc, it.getModelLocation())) {
                        var51 = element$iv;
                        break;
                     }
                  }

                  ModelTextureList modelMatch = (ModelTextureList)var51;
                  if (modelMatch != null) {
                     var49 = $this.getLogger();
                     if (var49 != null) {
                        var49.log(Level.DEBUG, "" + $this.getLogName() + ":       model " + (ResourceLocation)blockLoc.getSecond() + " matches " + modelMatch.getModelLocation());
                     }

                     Iterable $receiver$iv = (Iterable)modelMatch.getTextureNames();
                     Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                     Iterator var15 = $receiver$iv.iterator();

                     while(var15.hasNext()) {
                        Object item$iv$iv = var15.next();
                        String it = (String)item$iv$iv;
                        Pair var19 = TuplesKt.to(it, ((ModelBlock)blockLoc.getFirst()).func_178308_c(it));
                        destination$iv$iv.add(var19);
                     }

                     List textures = (List)destination$iv$iv;
                     Joiner var54 = Joiner.on(", ");
                     Iterable $receiver$iv = (Iterable)textures;
                     Joiner var18 = var54;
                     Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                     Iterator var47 = $receiver$iv.iterator();

                     while(var47.hasNext()) {
                        Object item$iv$iv = var47.next();
                        Pair it = (Pair)item$iv$iv;
                        String var21 = "" + (String)it.getFirst() + '=' + (String)it.getSecond();
                        destination$iv$iv.add(var21);
                     }

                     List var55 = (List)destination$iv$iv;
                     String texMapString = var18.join((Iterable)var55);
                     var49 = $this.getLogger();
                     if (var49 != null) {
                        var49.log(Level.DEBUG, "" + $this.getLogName() + ":       textures [" + texMapString + ']');
                     }

                     $receiver$iv = (Iterable)textures;
                     boolean var56;
                     if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
                        var56 = true;
                     } else {
                        Iterator var41 = $receiver$iv.iterator();

                        while(true) {
                           if (!var41.hasNext()) {
                              var56 = true;
                              break;
                           }

                           Object element$iv = var41.next();
                           Pair it = (Pair)element$iv;
                           if (!(Intrinsics.areEqual((String)it.getSecond(), "missingno") ^ true)) {
                              var56 = false;
                              break;
                           }
                        }
                     }

                     if (var56) {
                        ModelVariant variant = new ModelVariant(state, (ResourceLocation)blockLoc.getSecond(), 1);
                        $this.addVariant(state, variant);
                        Map var42 = $this.getVariantToKey();
                        Iterable $receiver$iv = (Iterable)textures;
                        Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                        Iterator var58 = $receiver$iv.iterator();

                        while(var58.hasNext()) {
                           Object item$iv$iv = var58.next();
                           Pair it = (Pair)item$iv$iv;
                           String var57 = (String)it.getSecond();
                           destination$iv$iv.add(var57);
                        }

                        List var48 = (List)destination$iv$iv;
                        var42.put(variant, var48);
                     }
                  }
               }

            }
         }
      }

      public static void addVariant(@NotNull TextureListModelProcessor $this, @NotNull IBlockState state, ModelVariant variant) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         Intrinsics.checkParameterIsNotNull(variant, "variant");
         ModelProcessor.DefaultImpls.addVariant((ModelProcessor)$this, state, variant);
      }

      @SubscribeEvent(
         priority = EventPriority.LOW
      )
      public static void handlePreStitch(@NotNull TextureListModelProcessor $this, Pre event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         ModelProcessor.DefaultImpls.handlePreStitch((ModelProcessor)$this, event);
      }

      @SubscribeEvent(
         priority = EventPriority.HIGHEST
      )
      public static void clearBeforeLoadModelData(@NotNull TextureListModelProcessor $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         ModelProcessor.DefaultImpls.clearBeforeLoadModelData((ModelProcessor)$this, event);
      }

      public static void onPostLoad(TextureListModelProcessor $this) {
         ModelProcessor.DefaultImpls.onPostLoad((ModelProcessor)$this);
      }

      @SubscribeEvent
      public static void handleLoadModelData(@NotNull TextureListModelProcessor $this, LoadModelDataEvent event) {
         Intrinsics.checkParameterIsNotNull(event, "event");
         ModelProcessor.DefaultImpls.handleLoadModelData((ModelProcessor)$this, event);
      }

      public static void putKeySingle(@NotNull TextureListModelProcessor $this, @NotNull IBlockState state, List key) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         Intrinsics.checkParameterIsNotNull(key, "key");
         ModelProcessor.DefaultImpls.putKeySingle((ModelProcessor)$this, state, key);
      }

      public static void onPreStitch(TextureListModelProcessor $this) {
         ModelProcessor.DefaultImpls.onPreStitch((ModelProcessor)$this);
      }

      @Nullable
      public static ModelVariant getVariant(@NotNull TextureListModelProcessor $this, IBlockState state, int rand) {
         Intrinsics.checkParameterIsNotNull(state, "state");
         return ModelProcessor.DefaultImpls.getVariant((ModelProcessor)$this, state, rand);
      }
   }
}
