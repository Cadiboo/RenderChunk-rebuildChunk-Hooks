package mods.octarinecore.client.render;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.Utils;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\n \u0015*\u0004\u0018\u00010\b0\b2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010 \u001a\u00020!J\u0016\u0010$\u001a\n \u0015*\u0004\u0018\u00010%0%2\u0006\u0010 \u001a\u00020!J\u001a\u0010&\u001a\u00020'2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020'0)J\u000e\u0010*\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0004J\u0019\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040-2\u0006\u0010.\u001a\u00020\u0004¢\u0006\u0002\u0010/J\u0016\u00100\u001a\u0002012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u0014R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000eR\"\u0010\u0013\u001a\n \u0015*\u0004\u0018\u00010\u00140\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u00062"},
   d2 = {"Lmods/octarinecore/client/render/BlockContext;", "", "()V", "biomeId", "", "getBiomeId", "()I", "block", "Lnet/minecraft/block/Block;", "getBlock", "()Lnet/minecraft/block/Block;", "blockCenter", "Lmods/octarinecore/common/Double3;", "getBlockCenter", "()Lmods/octarinecore/common/Double3;", "cameraDistance", "getCameraDistance", "chunkBase", "getChunkBase", "pos", "Lnet/minecraft/util/math/BlockPos;", "kotlin.jvm.PlatformType", "getPos", "()Lnet/minecraft/util/math/BlockPos;", "setPos", "(Lnet/minecraft/util/math/BlockPos;)V", "world", "Lnet/minecraft/world/IBlockAccess;", "getWorld", "()Lnet/minecraft/world/IBlockAccess;", "setWorld", "(Lnet/minecraft/world/IBlockAccess;)V", "offset", "Lmods/octarinecore/common/Int3;", "blockData", "Lmods/octarinecore/client/render/BlockData;", "blockState", "Lnet/minecraft/block/state/IBlockState;", "isSurroundedBy", "", "predicate", "Lkotlin/Function1;", "random", "seed", "semiRandomArray", "", "num", "(I)[Ljava/lang/Integer;", "set", "", "BetterFoliage-MC1.12"}
)
public final class BlockContext {
   @Nullable
   private IBlockAccess world;
   private BlockPos pos;

   @Nullable
   public final IBlockAccess getWorld() {
      return this.world;
   }

   public final void setWorld(@Nullable IBlockAccess var1) {
      this.world = var1;
   }

   public final BlockPos getPos() {
      return this.pos;
   }

   public final void setPos(BlockPos var1) {
      this.pos = var1;
   }

   public final void set(@NotNull IBlockAccess world, @NotNull BlockPos pos) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      this.world = world;
      this.pos = pos;
   }

   @NotNull
   public final Block getBlock() {
      Block var10000 = this.block(Int3.Companion.getZero());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "block(Int3.zero)");
      return var10000;
   }

   public final Block block(@NotNull Int3 offset) {
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      IBlockState var10000 = this.blockState(offset);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "blockState(offset)");
      return var10000.func_177230_c();
   }

   public final IBlockState blockState(@NotNull Int3 offset) {
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      BlockPos var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      BlockPos var2 = GeometryKt.plus(var10000, offset);
      IBlockAccess var5 = this.world;
      if (this.world == null) {
         Intrinsics.throwNpe();
      }

      return var5.func_180495_p(var2);
   }

   @NotNull
   public final BlockData blockData(@NotNull Int3 offset) {
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      BlockPos var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      BlockPos var2 = GeometryKt.plus(var10000, offset);
      IBlockAccess var8 = this.world;
      if (this.world == null) {
         Intrinsics.throwNpe();
      }

      IBlockState var4 = var8.func_180495_p(var2);
      BlockData var9 = new BlockData;
      Intrinsics.checkExpressionValueIsNotNull(var4, "state");
      Minecraft var10003 = Minecraft.func_71410_x();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "Minecraft.getMinecraft()");
      BlockColors var10 = var10003.func_184125_al();
      IBlockAccess var10005 = this.world;
      if (this.world == null) {
         Intrinsics.throwNpe();
      }

      int var11 = var10.func_186724_a(var4, var10005, var2, 0);
      Block var10004 = var4.func_177230_c();
      IBlockAccess var10006 = this.world;
      if (this.world == null) {
         Intrinsics.throwNpe();
      }

      var9.<init>(var4, var11, var10004.func_185484_c(var4, var10006, var2));
      return var9;
   }

   public final int getBiomeId() {
      IBlockAccess var10000 = this.world;
      if (this.world == null) {
         Intrinsics.throwNpe();
      }

      return Biome.func_185362_a(var10000.func_180494_b(this.pos));
   }

   @NotNull
   public final Double3 getBlockCenter() {
      BlockPos var10002 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      double var1 = (double)var10002.func_177958_n() + 0.5D;
      BlockPos var10003 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      double var2 = (double)var10003.func_177956_o() + 0.5D;
      BlockPos var10004 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      return new Double3(var1, var2, (double)var10004.func_177952_p() + 0.5D);
   }

   @NotNull
   public final Double3 getChunkBase() {
      BlockPos var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      int var4;
      if (var10000.func_177958_n() >= 0) {
         var10000 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         var4 = var10000.func_177958_n() / 16;
      } else {
         var10000 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         var4 = (var10000.func_177958_n() + 1) / 16 - 1;
      }

      int cX = var4;
      var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      int cY = var10000.func_177956_o() / 16;
      var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      if (var10000.func_177952_p() >= 0) {
         var10000 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         var4 = var10000.func_177952_p() / 16;
      } else {
         var10000 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         var4 = (var10000.func_177952_p() + 1) / 16 - 1;
      }

      int cZ = var4;
      return new Double3((double)cX * 16.0D, (double)cY * 16.0D, (double)cZ * 16.0D);
   }

   public final boolean isSurroundedBy(@NotNull Function1 predicate) {
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Iterable $receiver$iv = (Iterable)GeometryKt.getForgeDirOffsets();
      boolean var10000;
      if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
         var10000 = true;
      } else {
         Iterator var3 = $receiver$iv.iterator();

         while(true) {
            if (!var3.hasNext()) {
               var10000 = true;
               break;
            }

            Object element$iv = var3.next();
            Int3 it = (Int3)element$iv;
            IBlockState var10001 = this.blockState(it);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "blockState(it)");
            if (!((Boolean)predicate.invoke(var10001)).booleanValue()) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }

   public final int random(int seed) {
      BlockPos var10000 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      int var2 = var10000.func_177958_n();
      BlockPos var10001 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      int var3 = var10001.func_177956_o();
      BlockPos var10002 = this.pos;
      Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
      return Utils.semiRandom(var2, var3, var10002.func_177952_p(), seed);
   }

   @NotNull
   public final Integer[] semiRandomArray(int num) {
      Object[] result$iv = new Integer[num];
      int i$iv = 0;

      for(int var4 = result$iv.length; i$iv < var4; ++i$iv) {
         Integer var10 = this.random(i$iv);
         result$iv[i$iv] = var10;
      }

      return result$iv;
   }

   public final int getCameraDistance() {
      Minecraft var10000 = Minecraft.func_71410_x();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Minecraft.getMinecraft()");
      Entity var2 = var10000.func_175606_aa();
      if (var2 != null) {
         Entity camera = var2;
         BlockPos var3 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         int var4 = Math.abs(var3.func_177958_n() - MathHelper.func_76128_c(camera.field_70165_t));
         BlockPos var10001 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         var4 += Math.abs(var10001.func_177956_o() - MathHelper.func_76128_c(camera.field_70163_u));
         var10001 = this.pos;
         Intrinsics.checkExpressionValueIsNotNull(this.pos, "pos");
         return var4 + Math.abs(var10001.func_177952_p() - MathHelper.func_76128_c(camera.field_70161_v));
      } else {
         return 0;
      }
   }

   public BlockContext() {
      this.pos = BlockPos.field_177992_a;
   }
}
