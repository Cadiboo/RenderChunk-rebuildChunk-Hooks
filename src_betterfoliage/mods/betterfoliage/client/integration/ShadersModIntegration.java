package mods.betterfoliage.client.integration;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import mods.octarinecore.metaprog.Reflection;
import mods.octarinecore.metaprog.Resolvable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTallGrass.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J)\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00042\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cH\u0086\bJ)\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00042\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cH\u0086\bJ1\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00042\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cH\u0086\bJ1\u0010\u001e\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00042\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cH\u0086\bR$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0003\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u00020\n8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\r¨\u0006!"},
   d2 = {"Lmods/betterfoliage/client/integration/ShadersModIntegration;", "", "()V", "isPresent", "", "isPresent$annotations", "()Z", "setPresent", "(Z)V", "leavesEntityData", "", "leavesEntityData$annotations", "getLeavesEntityData", "()J", "tallGrassEntityData", "tallGrassEntityData$annotations", "getTallGrassEntityData", "entityDataFor", "blockState", "Lnet/minecraft/block/state/IBlockState;", "getBlockIdOverride", "original", "grass", "", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "enabled", "func", "Lkotlin/Function0;", "leaves", "renderAs", "blockEntityData", "state", "BetterFoliage-MC1.12"}
)
public final class ShadersModIntegration {
   private static boolean isPresent;
   private static final long tallGrassEntityData;
   private static final long leavesEntityData;
   public static final ShadersModIntegration INSTANCE;

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void isPresent$annotations() {
   }

   public static final boolean isPresent() {
      return isPresent;
   }

   public static final void setPresent(boolean var0) {
      isPresent = var0;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void tallGrassEntityData$annotations() {
   }

   public static final long getTallGrassEntityData() {
      return tallGrassEntityData;
   }

   /** @deprecated */
   // $FF: synthetic method
   @JvmStatic
   public static void leavesEntityData$annotations() {
   }

   public static final long getLeavesEntityData() {
      return leavesEntityData;
   }

   public final long entityDataFor(@NotNull IBlockState blockState) {
      Intrinsics.checkParameterIsNotNull(blockState, "blockState");
      return (long)Block.field_149771_c.func_148757_b(blockState.func_177230_c()) & 65535L | ((long)blockState.func_185901_i().ordinal() & 65535L) << 16 | (long)blockState.func_177230_c().func_176201_c(blockState) << 32;
   }

   @JvmStatic
   public static final long getBlockIdOverride(long original, @NotNull IBlockState blockState) {
      Intrinsics.checkParameterIsNotNull(blockState, "blockState");
      ConfigurableBlockMatcher var10000 = Config.blocks.INSTANCE.getLeavesClasses();
      Block var10001 = blockState.func_177230_c();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "blockState.block");
      if (var10000.matchesClass(var10001)) {
         return leavesEntityData;
      } else {
         var10000 = Config.blocks.INSTANCE.getCrops();
         var10001 = blockState.func_177230_c();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "blockState.block");
         return var10000.matchesClass(var10001) ? tallGrassEntityData : original;
      }
   }

   public final void renderAs(long blockEntityData, @NotNull BufferBuilder renderer, boolean enabled, @NotNull Function0 func) {
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(func, "func");
      if (isPresent() && enabled) {
         Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         Object vertexBuilder = var10000;
         Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder, blockEntityData);
         func.invoke();
         Refs.INSTANCE.getPopEntity().invoke(vertexBuilder);
      } else {
         func.invoke();
      }

   }

   public final void renderAs(@NotNull IBlockState state, @NotNull BufferBuilder renderer, boolean enabled, @NotNull Function0 func) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(func, "func");
      long blockEntityData$iv = this.entityDataFor(state);
      if (isPresent() && enabled) {
         Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         Object vertexBuilder$iv = var10000;
         Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv, blockEntityData$iv);
         func.invoke();
         Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv);
      } else {
         func.invoke();
      }

   }

   public final void grass(@NotNull BufferBuilder renderer, boolean enabled, @NotNull Function0 func) {
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(func, "func");
      long blockEntityData$iv = getTallGrassEntityData();
      if (isPresent() && enabled) {
         Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         Object vertexBuilder$iv = var10000;
         Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv, blockEntityData$iv);
         func.invoke();
         Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv);
      } else {
         func.invoke();
      }

   }

   public final void leaves(@NotNull BufferBuilder renderer, boolean enabled, @NotNull Function0 func) {
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(func, "func");
      long blockEntityData$iv = getLeavesEntityData();
      if (isPresent() && enabled) {
         Object var10000 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         Object vertexBuilder$iv = var10000;
         Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv, blockEntityData$iv);
         func.invoke();
         Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv);
      } else {
         func.invoke();
      }

   }

   static {
      ShadersModIntegration var0 = new ShadersModIntegration();
      INSTANCE = var0;
      BlockTallGrass var10001 = Blocks.field_150329_H;
      Intrinsics.checkExpressionValueIsNotNull(Blocks.field_150329_H, "Blocks.TALLGRASS");
      IBlockState var1 = var10001.func_176223_P().func_177226_a((IProperty)BlockTallGrass.field_176497_a, (Comparable)EnumType.GRASS);
      Intrinsics.checkExpressionValueIsNotNull(var1, "Blocks.TALLGRASS.default…TallGrass.EnumType.GRASS)");
      tallGrassEntityData = var0.entityDataFor(var1);
      BlockLeaves var2 = Blocks.field_150362_t;
      Intrinsics.checkExpressionValueIsNotNull(Blocks.field_150362_t, "Blocks.LEAVES");
      var1 = var2.func_176223_P();
      Intrinsics.checkExpressionValueIsNotNull(var1, "Blocks.LEAVES.defaultState");
      leavesEntityData = var0.entityDataFor(var1);
      if (Reflection.allAvailable((Resolvable)Refs.INSTANCE.getSVertexBuilder(), (Resolvable)Refs.INSTANCE.getPushEntity_state(), (Resolvable)Refs.INSTANCE.getPushEntity_num(), (Resolvable)Refs.INSTANCE.getPopEntity())) {
         Client var10000 = Client.INSTANCE;
         Level var3 = Level.INFO;
         Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
         var10000.log(var3, "ShadersMod integration enabled");
         isPresent = true;
      }

   }
}
