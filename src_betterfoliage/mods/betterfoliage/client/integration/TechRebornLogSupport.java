package mods.betterfoliage.client.integration;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.resource.Utils;
import mods.octarinecore.metaprog.ClassRef;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"},
   d2 = {"Lmods/betterfoliage/client/integration/TechRebornLogSupport;", "Lmods/betterfoliage/client/integration/RubberLogSupportBase;", "()V", "processModelLoad", "", "state", "Lnet/minecraft/block/state/IBlockState;", "modelLoc", "Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;", "model", "Lnet/minecraftforge/client/model/IModel;", "BetterFoliage-MC1.12"}
)
public final class TechRebornLogSupport extends RubberLogSupportBase {
   public static final TechRebornLogSupport INSTANCE;

   public void processModelLoad(@NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, @NotNull IModel model) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
      Intrinsics.checkParameterIsNotNull(model, "model");
      ClassRef var10000 = TechRebornIntegration.INSTANCE.getBlockRubberLog();
      Block var10001 = state.func_177230_c();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
      if (var10000.isInstance(var10001)) {
         Pair var27 = (Pair)CollectionsKt.firstOrNull(Utils.getModelBlockAndLoc(model));
         if (var27 != null) {
            Pair blockLoc = var27;
            ImmutableSet var28 = (ImmutableSet)state.func_177228_b().entrySet();
            Intrinsics.checkExpressionValueIsNotNull(var28, "state.properties.entries");
            Iterable var6 = (Iterable)var28;
            Iterator var8 = var6.iterator();

            Object var29;
            while(true) {
               if (var8.hasNext()) {
                  Object var9 = var8.next();
                  Entry it = (Entry)var9;
                  if (!Intrinsics.areEqual(((IProperty)it.getKey()).func_177701_a(), "hassap")) {
                     continue;
                  }

                  var29 = var9;
                  break;
               }

               var29 = null;
               break;
            }

            Comparable var30 = (Entry)var29 != null ? (Comparable)((Entry)var29).getValue() : null;
            if (!(var30 instanceof Boolean)) {
               var30 = null;
            }

            Boolean var31 = (Boolean)var30;
            if (var31 != null) {
               boolean hasSap = var31.booleanValue();
               var28 = (ImmutableSet)state.func_177228_b().entrySet();
               Intrinsics.checkExpressionValueIsNotNull(var28, "state.properties.entries");
               Iterable var7 = (Iterable)var28;
               Iterator var22 = var7.iterator();

               Object element$iv;
               while(true) {
                  if (var22.hasNext()) {
                     element$iv = var22.next();
                     Entry it = (Entry)element$iv;
                     if (!Intrinsics.areEqual(((IProperty)it.getKey()).func_177701_a(), "sapside")) {
                        continue;
                     }

                     var29 = element$iv;
                     break;
                  }

                  var29 = null;
                  break;
               }

               var30 = (Entry)var29 != null ? (Comparable)((Entry)var29).getValue() : null;
               if (!(var30 instanceof EnumFacing)) {
                  var30 = null;
               }

               EnumFacing var32 = (EnumFacing)var30;
               if (var32 != null) {
                  EnumFacing sapSide = var32;
                  Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{"end", "end", "side", "sapside"});
                  Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                  Iterator var25 = $receiver$iv.iterator();

                  while(var25.hasNext()) {
                     Object item$iv$iv = var25.next();
                     String it = (String)item$iv$iv;
                     String var18 = ((ModelBlock)blockLoc.getFirst()).func_178308_c(it);
                     destination$iv$iv.add(var18);
                  }

                  List textureNames = (List)destination$iv$iv;
                  this.getLogger().log(Level.DEBUG, "TechRebornLogSupport: block state " + state.toString());
                  String it;
                  boolean var33;
                  if (hasSap) {
                     this.getLogger().log(Level.DEBUG, "TechRebornLogSupport:             spotDir=" + sapSide + ", end=" + (String)textureNames.get(0) + ", side=" + (String)textureNames.get(2) + ", spot=" + (String)textureNames.get(3));
                     $receiver$iv = (Iterable)textureNames;
                     if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
                        var33 = true;
                     } else {
                        var22 = $receiver$iv.iterator();

                        while(true) {
                           if (!var22.hasNext()) {
                              var33 = true;
                              break;
                           }

                           element$iv = var22.next();
                           it = (String)element$iv;
                           if (!(Intrinsics.areEqual(it, "missingno") ^ true)) {
                              var33 = false;
                              break;
                           }
                        }
                     }

                     if (var33) {
                        this.putKeySingle(state, new RubberLogModelInfo(Axis.Y, sapSide, textureNames));
                     }
                  } else {
                     this.getLogger().log(Level.DEBUG, "TechRebornLogSupport:             end=" + (String)textureNames.get(0) + ", side=" + (String)textureNames.get(2));
                     $receiver$iv = (Iterable)textureNames;
                     if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
                        var33 = true;
                     } else {
                        var22 = $receiver$iv.iterator();

                        while(true) {
                           if (!var22.hasNext()) {
                              var33 = true;
                              break;
                           }

                           element$iv = var22.next();
                           it = (String)element$iv;
                           if (!(Intrinsics.areEqual(it, "missingno") ^ true)) {
                              var33 = false;
                              break;
                           }
                        }
                     }

                     if (var33) {
                        this.putKeySingle(state, new RubberLogModelInfo(Axis.Y, (EnumFacing)null, textureNames));
                     }
                  }

               }
            }
         }
      }
   }

   static {
      TechRebornLogSupport var0 = new TechRebornLogSupport();
      INSTANCE = var0;
   }
}
