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
import net.minecraft.util.ResourceLocation;
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
   d2 = {"Lmods/betterfoliage/client/integration/IC2LogSupport;", "Lmods/betterfoliage/client/integration/RubberLogSupportBase;", "()V", "processModelLoad", "", "state", "Lnet/minecraft/block/state/IBlockState;", "modelLoc", "Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;", "model", "Lnet/minecraftforge/client/model/IModel;", "BetterFoliage-MC1.12"}
)
public final class IC2LogSupport extends RubberLogSupportBase {
   public static final IC2LogSupport INSTANCE;

   public void processModelLoad(@NotNull IBlockState state, @NotNull ModelResourceLocation modelLoc, @NotNull IModel model) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(modelLoc, "modelLoc");
      Intrinsics.checkParameterIsNotNull(model, "model");
      ClassRef var10000 = IC2Integration.INSTANCE.getBlockRubWood();
      Block var10001 = state.func_177230_c();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
      if (var10000.isInstance(var10001)) {
         Pair var26 = (Pair)CollectionsKt.firstOrNull(Utils.getModelBlockAndLoc(model));
         if (var26 != null) {
            Pair blockLoc = var26;
            ImmutableSet var27 = (ImmutableSet)state.func_177228_b().entrySet();
            Intrinsics.checkExpressionValueIsNotNull(var27, "state.properties.entries");
            Iterable var6 = (Iterable)var27;
            Iterator var8 = var6.iterator();

            Object var28;
            while(true) {
               if (var8.hasNext()) {
                  Object var9 = var8.next();
                  Entry it = (Entry)var9;
                  if (!Intrinsics.areEqual(((IProperty)it.getKey()).func_177701_a(), "state")) {
                     continue;
                  }

                  var28 = var9;
                  break;
               }

               var28 = null;
               break;
            }

            Entry var29 = (Entry)var28;
            if (var29 != null) {
               Comparable var30 = (Comparable)var29.getValue();
               if (var30 != null) {
                  String var31 = var30.toString();
                  if (var31 != null) {
                     String type = var31;
                     List textureNames;
                     Iterator var11;
                     Object item$iv$iv;
                     String it;
                     String var18;
                     Iterable $receiver$iv;
                     Iterator var22;
                     Collection destination$iv$iv;
                     Object element$iv;
                     String it;
                     boolean var33;
                     if (Utils.derivesFrom(blockLoc, new ResourceLocation("block/cube_column"))) {
                        Axis var34;
                        label113: {
                           switch(type.hashCode()) {
                           case -494036157:
                              if (type.equals("plain_x")) {
                                 var34 = Axis.X;
                                 break label113;
                              }
                              break;
                           case -494036156:
                              if (type.equals("plain_y")) {
                                 var34 = Axis.Y;
                                 break label113;
                              }
                              break;
                           case -494036155:
                              if (type.equals("plain_z")) {
                                 var34 = Axis.Z;
                                 break label113;
                              }
                           }

                           var34 = null;
                        }

                        Axis axis = var34;
                        $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{"end", "end", "side"});
                        destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                        var11 = $receiver$iv.iterator();

                        while(var11.hasNext()) {
                           item$iv$iv = var11.next();
                           it = (String)item$iv$iv;
                           var18 = ((ModelBlock)blockLoc.getFirst()).func_178308_c(it);
                           destination$iv$iv.add(var18);
                        }

                        textureNames = (List)destination$iv$iv;
                        this.getLogger().log(Level.DEBUG, "IC2LogSupport: block state " + state.toString());
                        this.getLogger().log(Level.DEBUG, "IC2LogSupport:             axis=" + axis + ", end=" + (String)textureNames.get(0) + ", side=" + (String)textureNames.get(2));
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
                           this.putKeySingle(state, new RubberLogModelInfo(axis, (EnumFacing)null, textureNames));
                        }

                        return;
                     }

                     EnumFacing var32;
                     label153: {
                        label152: {
                           label151: {
                              label150: {
                                 label149: {
                                    switch(type.hashCode()) {
                                    case -2007510292:
                                       if (type.equals("wet_north")) {
                                          break label152;
                                       }
                                       break;
                                    case -2002889804:
                                       if (type.equals("wet_south")) {
                                          break label151;
                                       }
                                       break;
                                    case -701364879:
                                       if (type.equals("dry_north")) {
                                          break label152;
                                       }
                                       break;
                                    case -696744391:
                                       if (type.equals("dry_south")) {
                                          break label151;
                                       }
                                       break;
                                    case -203587274:
                                       if (type.equals("wet_east")) {
                                          break label149;
                                       }
                                       break;
                                    case -203047192:
                                       if (type.equals("wet_west")) {
                                          break label150;
                                       }
                                       break;
                                    case -161453551:
                                       if (type.equals("dry_east")) {
                                          break label149;
                                       }
                                       break;
                                    case -160913469:
                                       if (type.equals("dry_west")) {
                                          break label150;
                                       }
                                    }

                                    var32 = null;
                                    break label153;
                                 }

                                 var32 = EnumFacing.EAST;
                                 break label153;
                              }

                              var32 = EnumFacing.WEST;
                              break label153;
                           }

                           var32 = EnumFacing.SOUTH;
                           break label153;
                        }

                        var32 = EnumFacing.NORTH;
                     }

                     EnumFacing spotDir = var32;
                     $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{"up", "down", "south", "north"});
                     destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
                     var11 = $receiver$iv.iterator();

                     while(var11.hasNext()) {
                        item$iv$iv = var11.next();
                        it = (String)item$iv$iv;
                        var18 = ((ModelBlock)blockLoc.getFirst()).func_178308_c(it);
                        destination$iv$iv.add(var18);
                     }

                     textureNames = (List)destination$iv$iv;
                     this.getLogger().log(Level.DEBUG, "IC2LogSupport: block state " + state.toString());
                     this.getLogger().log(Level.DEBUG, "IC2LogSupport:             spotDir=" + spotDir + ", up=" + (String)textureNames.get(0) + ", down=" + (String)textureNames.get(1) + ", side=" + (String)textureNames.get(2) + ", spot=" + (String)textureNames.get(3));
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
                        this.putKeySingle(state, new RubberLogModelInfo(Axis.Y, spotDir, textureNames));
                     }

                     return;
                  }
               }
            }

         }
      }
   }

   static {
      IC2LogSupport var0 = new IC2LogSupport();
      INSTANCE = var0;
   }
}
