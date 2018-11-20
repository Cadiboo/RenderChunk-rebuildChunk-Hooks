package mods.octarinecore.common.config;

import kotlin.Metadata;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\b"},
   d2 = {"Lmods/octarinecore/common/config/IBlockMatcher;", "", "matchesClass", "", "block", "Lnet/minecraft/block/Block;", "matchingClass", "Ljava/lang/Class;", "BetterFoliage-MC1.12"}
)
public interface IBlockMatcher {
   boolean matchesClass(@NotNull Block var1);

   @Nullable
   Class matchingClass(@NotNull Block var1);
}
