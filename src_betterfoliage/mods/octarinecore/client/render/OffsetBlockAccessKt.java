package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u0002H\u0001\"\u0006\b\u0000\u0010\u0001\u0018\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0007H\u0086\b¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"withOffset", "T", "Lmods/octarinecore/client/render/BlockContext;", "modded", "Lmods/octarinecore/common/Int3;", "target", "func", "Lkotlin/Function0;", "(Lmods/octarinecore/client/render/BlockContext;Lmods/octarinecore/common/Int3;Lmods/octarinecore/common/Int3;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "BetterFoliage-MC1.12"}
)
public final class OffsetBlockAccessKt {
   private static final Object withOffset(@NotNull BlockContext $receiver, Int3 modded, Int3 target, Function0 func) {
      IBlockAccess var10000 = $receiver.getWorld();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      IBlockAccess original = var10000;
      BlockPos var10004 = $receiver.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10004, "pos");
      var10004 = GeometryKt.plus(var10004, modded);
      BlockPos var10005 = $receiver.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10005, "pos");
      $receiver.setWorld((IBlockAccess)(new OffsetBlockAccess(original, var10004, GeometryKt.plus(var10005, target))));
      Object result = func.invoke();
      $receiver.setWorld(original);
      return result;
   }
}
