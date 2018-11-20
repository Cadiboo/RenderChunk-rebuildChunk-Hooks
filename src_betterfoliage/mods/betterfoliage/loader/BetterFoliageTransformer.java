package mods.betterfoliage.loader;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.metaprog.InstructionList;
import mods.octarinecore.metaprog.MethodTransformContext;
import mods.octarinecore.metaprog.Reflection;
import mods.octarinecore.metaprog.Resolvable;
import mods.octarinecore.metaprog.Transformer;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\b"},
   d2 = {"Lmods/betterfoliage/loader/BetterFoliageTransformer;", "Lmods/octarinecore/metaprog/Transformer;", "()V", "isOptifinePresent", "", "()Z", "setupClient", "", "BetterFoliage-MC1.12"}
)
public final class BetterFoliageTransformer extends Transformer {
   private final boolean isOptifinePresent;

   public final boolean isOptifinePresent() {
      return this.isOptifinePresent;
   }

   public final void setupClient() {
      this.transformMethod(Refs.INSTANCE.getShowBarrierParticles(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find($receiver.invokeRef(Refs.INSTANCE.getRandomDisplayTick()));
            if (var10000 == null || $receiver.insertAfter(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying random display tick call hook");
                  $receiver.varinsn(25, 0);
                  $receiver.varinsn(25, 11);
                  $receiver.varinsn(25, 7);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getOnRandomDisplayTick(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply random display tick call hook!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getGetAmbientOcclusionLightValue(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find(174);
            if (var10000 == null || $receiver.insertBefore(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying getAmbientOcclusionLightValue() override");
                  $receiver.varinsn(25, 0);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getGetAmbientOcclusionLightValueOverride(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply getAmbientOcclusionLightValue() override!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getUseNeighborBrightness(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find(172);
            if (var10000 == null || $receiver.insertBefore(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying useNeighborBrightness() override");
                  $receiver.varinsn(25, 0);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getUseNeighborBrightnessOverride(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply useNeighborBrightness() override!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getDoesSideBlockRendering(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find(172);
            if (var10000 == null || $receiver.insertBefore(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying doesSideBlockRendering() override");
                  $receiver.varinsn(25, 1);
                  $receiver.varinsn(25, 2);
                  $receiver.varinsn(25, 3);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getDoesSideBlockRenderingOverride(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply doesSideBlockRendering() override!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.isOpaqueCube(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find(172);
            if (var10000 == null || $receiver.insertBefore(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying isOpaqueCube() override");
                  $receiver.varinsn(25, 0);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.isOpaqueCubeOverride(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply isOpaqueCube() override!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getSetupModelRegistry(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find($receiver.invokeName("addAll"));
            if (var10000 == null || $receiver.insertAfter(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying ModelLoader lifecycle callback");
                  $receiver.varinsn(25, 0);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getOnAfterLoadModelDefinitions(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply ModelLoader lifecycle callback!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getRebuildChunk(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            $receiver.applyWriterFlags(2, 1);
            AbstractInsnNode var10000 = $receiver.find($receiver.invokeRef(Refs.INSTANCE.getRenderBlock()));
            if (var10000 != null) {
               $receiver.replace(var10000, (Function1)(new Function1() {
                  public final void invoke(@NotNull InstructionList $receiver) {
                     Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                     BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying RenderChunk block render override");
                     $receiver.varinsn(25, BetterFoliageTransformer.this.isOptifinePresent() ? 22 : 20);
                     InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getRenderWorldBlock(), false, 2, (Object)null);
                  }
               }));
            }

            if (BetterFoliageTransformer.this.isOptifinePresent()) {
               var10000 = $receiver.find($receiver.varinsn(54, 23));
               if (var10000 != null) {
                  $receiver.insertAfter(var10000, (Function1)(new Function1() {
                     public final void invoke(@NotNull InstructionList $receiver) {
                        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                        BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying RenderChunk block layer override");
                        $receiver.varinsn(25, 19);
                        $receiver.varinsn(25, 18);
                        $receiver.varinsn(25, 22);
                        InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getCanRenderBlockInLayer(), false, 2, (Object)null);
                        $receiver.varinsn(54, 23);
                     }
                  }));
               }
            } else {
               var10000 = $receiver.find($receiver.invokeRef(Refs.INSTANCE.getCanRenderInLayer()));
               if (var10000 != null) {
                  $receiver.replace(var10000, (Function1)(new Function1() {
                     public final void invoke(@NotNull InstructionList $receiver) {
                        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                        BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying RenderChunk block layer override");
                        InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getCanRenderBlockInLayer(), false, 2, (Object)null);
                     }
                  }));
               }
            }

         }
      }));
      this.transformMethod(Refs.INSTANCE.getAOF_constructor(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Setting AmbientOcclusionFace constructor public");
            $receiver.makePublic();
         }
      }));
      this.transformMethod(Refs.INSTANCE.getPushEntity_state(), (Function1)(new Function1() {
         public final void invoke(@NotNull MethodTransformContext $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            AbstractInsnNode var10000 = $receiver.find($receiver.invokeRef(Refs.INSTANCE.getPushEntity_num()));
            if (var10000 == null || $receiver.insertBefore(var10000, (Function1)(new Function1() {
               public final void invoke(@NotNull InstructionList $receiver) {
                  Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
                  BetterFoliageTransformer.this.getLog().info("[BetterFoliageLoader] Applying SVertexBuilder.pushEntity() block ID override");
                  $receiver.varinsn(25, 0);
                  InstructionList.invokeStatic$default($receiver, Refs.INSTANCE.getGetBlockIdOverride(), false, 2, (Object)null);
               }
            })) == null) {
               BetterFoliageTransformer.this.getLog().warn("[BetterFoliageLoader] Failed to apply SVertexBuilder.pushEntity() block ID override!");
               Unit var2 = Unit.INSTANCE;
            }

         }
      }));
   }

   public BetterFoliageTransformer() {
      this.isOptifinePresent = Reflection.allAvailable((Resolvable)Refs.INSTANCE.getOptifineClassTransformer());
      Side var10000 = FMLLaunchHandler.side();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "FMLLaunchHandler.side()");
      if (var10000.isClient()) {
         this.setupClient();
      }

   }
}
