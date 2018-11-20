package mods.betterfoliage.client.render;

import kotlin.Metadata;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u001b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H¦\u0002¨\u0006\b"},
   d2 = {"Lmods/betterfoliage/client/render/IColumnRegistry;", "", "get", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "BetterFoliage-MC1.12"}
)
public interface IColumnRegistry {
   @Nullable
   IColumnTextureInfo get(@NotNull IBlockState var1, int var2);
}
