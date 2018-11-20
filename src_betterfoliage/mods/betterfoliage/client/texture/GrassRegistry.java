package mods.betterfoliage.client.texture;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.common.Int3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0002J3\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"},
   d2 = {"Lmods/betterfoliage/client/texture/GrassRegistry;", "Lmods/betterfoliage/client/texture/IGrassRegistry;", "()V", "subRegistries", "", "getSubRegistries", "()Ljava/util/List;", "get", "Lmods/betterfoliage/client/texture/GrassInfo;", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "face", "Lnet/minecraft/util/EnumFacing;", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "BetterFoliage-MC1.12"}
)
public final class GrassRegistry implements IGrassRegistry {
   @NotNull
   private static final List subRegistries;
   public static final GrassRegistry INSTANCE;

   @NotNull
   public final List getSubRegistries() {
      return subRegistries;
   }

   @Nullable
   public GrassInfo get(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(face, "face");
      Iterable $receiver$iv = (Iterable)subRegistries;
      Iterator var8 = $receiver$iv.iterator();

      GrassInfo var10000;
      while(true) {
         if (!var8.hasNext()) {
            var10000 = null;
            break;
         }

         Object element$iv$iv = var8.next();
         IGrassRegistry it = (IGrassRegistry)element$iv$iv;
         var10000 = it.get(state, world, pos, face, rand);
         if (var10000 != null) {
            GrassInfo var13 = var10000;
            var10000 = var13;
            break;
         }
      }

      return var10000;
   }

   @Nullable
   public final GrassInfo get(@NotNull BlockContext ctx, @NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(face, "face");
      IBlockState var10001 = ctx.blockState(Int3.Companion.getZero());
      Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
      IBlockAccess var10002 = ctx.getWorld();
      if (var10002 == null) {
         Intrinsics.throwNpe();
      }

      BlockPos var10003 = ctx.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "ctx.pos");
      return this.get(var10001, var10002, var10003, face, ctx.random(0));
   }

   @Nullable
   public GrassInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Iterable $receiver$iv = (Iterable)subRegistries;
      Iterator var5 = $receiver$iv.iterator();

      GrassInfo var10000;
      while(true) {
         if (!var5.hasNext()) {
            var10000 = null;
            break;
         }

         Object element$iv$iv = var5.next();
         IGrassRegistry it = (IGrassRegistry)element$iv$iv;
         var10000 = it.get(state, rand);
         if (var10000 != null) {
            GrassInfo var10 = var10000;
            var10000 = var10;
            break;
         }
      }

      return var10000;
   }

   static {
      GrassRegistry var0 = new GrassRegistry();
      INSTANCE = var0;
      subRegistries = CollectionsKt.mutableListOf(new IGrassRegistry[]{(IGrassRegistry)StandardGrassSupport.INSTANCE});
   }
}
