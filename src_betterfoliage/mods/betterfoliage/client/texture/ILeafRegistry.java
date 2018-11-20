package mods.betterfoliage.client.texture;

import kotlin.Metadata;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H¦\u0002J3\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u0007H¦\u0002¨\u0006\u000e"},
   d2 = {"Lmods/betterfoliage/client/texture/ILeafRegistry;", "", "get", "Lmods/betterfoliage/client/texture/LeafInfo;", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "face", "Lnet/minecraft/util/EnumFacing;", "BetterFoliage-MC1.12"}
)
public interface ILeafRegistry {
   @Nullable
   LeafInfo get(@NotNull IBlockState var1, int var2);

   @Nullable
   LeafInfo get(@NotNull IBlockState var1, @NotNull IBlockAccess var2, @NotNull BlockPos var3, @NotNull EnumFacing var4, int var5);
}
