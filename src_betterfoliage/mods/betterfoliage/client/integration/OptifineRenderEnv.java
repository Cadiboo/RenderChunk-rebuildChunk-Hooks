package mods.betterfoliage.client.integration;

import java.lang.reflect.Constructor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.loader.Refs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000e"},
   d2 = {"Lmods/betterfoliage/client/integration/OptifineRenderEnv;", "", "()V", "wrapped", "getWrapped", "()Ljava/lang/Object;", "reset", "", "blockAccess", "Lnet/minecraft/world/IBlockAccess;", "state", "Lnet/minecraft/block/state/IBlockState;", "pos", "Lnet/minecraft/util/math/BlockPos;", "BetterFoliage-MC1.12"}
)
public final class OptifineRenderEnv {
   @NotNull
   private final Object wrapped;

   @NotNull
   public final Object getWrapped() {
      return this.wrapped;
   }

   public final void reset(@NotNull IBlockAccess blockAccess, @NotNull IBlockState state, @NotNull BlockPos pos) {
      Intrinsics.checkParameterIsNotNull(blockAccess, "blockAccess");
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Refs.INSTANCE.getRenderEnv_reset().invoke(this.wrapped, blockAccess, state, pos);
   }

   public OptifineRenderEnv() {
      Object var10001 = Refs.INSTANCE.getRenderEnv().getElement();
      if (var10001 == null) {
         Intrinsics.throwNpe();
      }

      Constructor var1 = ((Class)var10001).getDeclaredConstructor((Class)Refs.INSTANCE.getIBlockAccess().getElement(), (Class)Refs.INSTANCE.getIBlockState().getElement(), (Class)Refs.INSTANCE.getBlockPos().getElement());
      Intrinsics.checkExpressionValueIsNotNull(var1, "it");
      var1.setAccessible(true);
      Object var10000 = var1.newInstance(null, null, null);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "it.newInstance(null, null, null)");
      Object var5 = var10000;
      Intrinsics.checkExpressionValueIsNotNull(var5, "Refs.RenderEnv.element!!…e(null, null, null)\n    }");
      this.wrapped = var5;
   }
}
