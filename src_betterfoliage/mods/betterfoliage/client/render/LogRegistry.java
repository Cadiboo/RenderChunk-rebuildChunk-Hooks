package mods.betterfoliage.client.render;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"},
   d2 = {"Lmods/betterfoliage/client/render/LogRegistry;", "Lmods/betterfoliage/client/render/IColumnRegistry;", "()V", "subRegistries", "", "getSubRegistries", "()Ljava/util/List;", "get", "Lmods/betterfoliage/client/render/IColumnTextureInfo;", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "BetterFoliage-MC1.12"}
)
public final class LogRegistry implements IColumnRegistry {
   @NotNull
   private static final List subRegistries;
   public static final LogRegistry INSTANCE;

   @NotNull
   public final List getSubRegistries() {
      return subRegistries;
   }

   @Nullable
   public IColumnTextureInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Iterable $receiver$iv = (Iterable)subRegistries;
      Iterator var5 = $receiver$iv.iterator();

      IColumnTextureInfo var10000;
      while(true) {
         if (!var5.hasNext()) {
            var10000 = null;
            break;
         }

         Object element$iv$iv = var5.next();
         IColumnRegistry it = (IColumnRegistry)element$iv$iv;
         var10000 = it.get(state, rand);
         if (var10000 != null) {
            IColumnTextureInfo var10 = var10000;
            var10000 = var10;
            break;
         }
      }

      return var10000;
   }

   static {
      LogRegistry var0 = new LogRegistry();
      INSTANCE = var0;
      subRegistries = (List)(new ArrayList());
   }
}
