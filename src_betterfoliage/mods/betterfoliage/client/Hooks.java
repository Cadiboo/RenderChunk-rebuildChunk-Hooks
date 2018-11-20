package mods.betterfoliage.client;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.render.EntityFallingLeavesFX;
import mods.betterfoliage.client.render.EntityRisingSoulFX;
import mods.betterfoliage.client.render.Utils;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.resource.LoadModelDataEvent;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import mods.octarinecore.metaprog.Reflection;
import mods.octarinecore.metaprog.Resolvable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007\u001a&\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019\u001a\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u001b2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0016\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u0016\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!\u001a\u001e\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017\u001a6\u0010%\u001a\u00020\u00012\u0006\u0010&\u001a\u00020'2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010(\u001a\u00020)2\u0006\u0010\u0011\u001a\u00020\u0007\"\u001a\u0010\u0000\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0000\u0010\u0002\"\u0004\b\u0003\u0010\u0004\"\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0002\"\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0011\u0010\n\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006*"},
   d2 = {"isAfterPostInit", "", "()Z", "setAfterPostInit", "(Z)V", "isOptifinePresent", "otherCutoutLayer", "Lnet/minecraft/util/BlockRenderLayer;", "getOtherCutoutLayer", "()Lnet/minecraft/util/BlockRenderLayer;", "targetCutoutLayer", "getTargetCutoutLayer", "canRenderBlockInLayer", "block", "Lnet/minecraft/block/Block;", "state", "Lnet/minecraft/block/state/IBlockState;", "layer", "doesSideBlockRenderingOverride", "original", "blockAccess", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "side", "Lnet/minecraft/util/EnumFacing;", "getAmbientOcclusionLightValueOverride", "", "getUseNeighborBrightnessOverride", "isOpaqueCubeOverride", "onAfterLoadModelDefinitions", "", "loader", "Lnet/minecraftforge/client/model/ModelLoader;", "onRandomDisplayTick", "world", "Lnet/minecraft/world/World;", "renderWorldBlock", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "worldRenderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Hooks"
)
public final class Hooks {
   private static boolean isAfterPostInit;
   private static final boolean isOptifinePresent;

   public static final boolean isAfterPostInit() {
      return isAfterPostInit;
   }

   public static final void setAfterPostInit(boolean var0) {
      isAfterPostInit = var0;
   }

   public static final boolean isOptifinePresent() {
      return isOptifinePresent;
   }

   public static final boolean doesSideBlockRenderingOverride(boolean original, @NotNull IBlockAccess blockAccess, @NotNull BlockPos pos, @NotNull EnumFacing side) {
      boolean var4;
      label27: {
         Intrinsics.checkParameterIsNotNull(blockAccess, "blockAccess");
         Intrinsics.checkParameterIsNotNull(pos, "pos");
         Intrinsics.checkParameterIsNotNull(side, "side");
         if (original) {
            if (!Config.INSTANCE.getEnabled() || !Config.roundLogs.INSTANCE.getEnabled()) {
               break label27;
            }

            ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
            IBlockState var10001 = blockAccess.func_180495_p(pos);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "blockAccess.getBlockState(pos)");
            Block var5 = var10001.func_177230_c();
            Intrinsics.checkExpressionValueIsNotNull(var5, "blockAccess.getBlockState(pos).block");
            if (!var10000.matchesClass(var5)) {
               break label27;
            }
         }

         var4 = false;
         return var4;
      }

      var4 = true;
      return var4;
   }

   public static final boolean isOpaqueCubeOverride(boolean original, @NotNull IBlockState state) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      if (!isAfterPostInit) {
         return original;
      } else {
         boolean var2;
         label33: {
            if (original) {
               if (!Config.INSTANCE.getEnabled() || !Config.roundLogs.INSTANCE.getEnabled()) {
                  break label33;
               }

               ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
               Block var10001 = state.func_177230_c();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
               if (!var10000.matchesClass(var10001)) {
                  break label33;
               }
            }

            var2 = false;
            return var2;
         }

         var2 = true;
         return var2;
      }
   }

   public static final float getAmbientOcclusionLightValueOverride(float original, @NotNull IBlockState state) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      if (Config.INSTANCE.getEnabled() && Config.roundLogs.INSTANCE.getEnabled()) {
         ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
         Block var10001 = state.func_177230_c();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
         if (var10000.matchesClass(var10001)) {
            return Config.roundLogs.INSTANCE.getDimming();
         }
      }

      return original;
   }

   public static final boolean getUseNeighborBrightnessOverride(boolean original, @NotNull IBlockState state) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      boolean var2;
      if (!original) {
         label28: {
            if (Config.INSTANCE.getEnabled() && Config.roundLogs.INSTANCE.getEnabled()) {
               ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLogClasses();
               Block var10001 = state.func_177230_c();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
               if (var10000.matchesClass(var10001)) {
                  break label28;
               }
            }

            var2 = false;
            return var2;
         }
      }

      var2 = true;
      return var2;
   }

   public static final void onRandomDisplayTick(@NotNull World world, @NotNull IBlockState state, @NotNull BlockPos pos) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      if (Config.INSTANCE.getEnabled() && Config.risingSoul.INSTANCE.getEnabled() && Intrinsics.areEqual(state.func_177230_c(), Blocks.field_150425_aM) && world.func_175623_d(GeometryKt.plus(pos, Utils.getUp1())) && Math.random() < Config.risingSoul.INSTANCE.getChance()) {
         (new EntityRisingSoulFX(world, pos)).addIfValid();
      }

      if (Config.INSTANCE.getEnabled() && Config.fallingLeaves.INSTANCE.getEnabled()) {
         ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLeavesClasses();
         Block var10001 = state.func_177230_c();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "state.block");
         if (var10000.matchesClass(var10001) && world.func_175623_d(GeometryKt.plus(pos, Utils.getDown1())) && Math.random() < Config.fallingLeaves.INSTANCE.getChance()) {
            (new EntityFallingLeavesFX(world, pos)).addIfValid();
         }
      }

   }

   public static final void onAfterLoadModelDefinitions(@NotNull ModelLoader loader) {
      Intrinsics.checkParameterIsNotNull(loader, "loader");
      MinecraftForge.EVENT_BUS.post((Event)(new LoadModelDataEvent(loader)));
   }

   public static final boolean renderWorldBlock(@NotNull BlockRendererDispatcher dispatcher, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull IBlockAccess blockAccess, @NotNull BufferBuilder worldRenderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(blockAccess, "blockAccess");
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      boolean doBaseRender = Utils.canRenderInLayer(state, layer) || Intrinsics.areEqual(layer, getTargetCutoutLayer()) && Utils.canRenderInLayer(state, getOtherCutoutLayer());
      BlockContext var7 = RendererHolder.getBlockContext();
      BlockContext ctx = var7;
      var7.set(blockAccess, pos);
      Iterable $receiver$iv = (Iterable)Client.INSTANCE.getRenderers();
      Iterator var10 = $receiver$iv.iterator();

      AbstractBlockRenderingHandler renderer;
      do {
         if (!var10.hasNext()) {
            return doBaseRender ? dispatcher.func_175018_a(state, pos, blockAccess, worldRenderer) : false;
         }

         Object element$iv = var10.next();
         renderer = (AbstractBlockRenderingHandler)element$iv;
      } while(!renderer.isEligible(ctx) || !doBaseRender && (!renderer.getAddToCutout() || !Intrinsics.areEqual(layer, getTargetCutoutLayer())));

      return renderer.render(ctx, dispatcher, worldRenderer, layer);
   }

   public static final boolean canRenderBlockInLayer(@NotNull Block block, @NotNull IBlockState state, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(block, "block");
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      return block.canRenderInLayer(state, layer) || Intrinsics.areEqual(layer, getTargetCutoutLayer());
   }

   @NotNull
   public static final BlockRenderLayer getTargetCutoutLayer() {
      return Minecraft.func_71410_x().field_71474_y.field_151442_I > 0 ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.CUTOUT;
   }

   @NotNull
   public static final BlockRenderLayer getOtherCutoutLayer() {
      return Minecraft.func_71410_x().field_71474_y.field_151442_I > 0 ? BlockRenderLayer.CUTOUT : BlockRenderLayer.CUTOUT_MIPPED;
   }

   static {
      isOptifinePresent = Reflection.allAvailable((Resolvable)Refs.INSTANCE.getOptifineClassTransformer());
   }
}
