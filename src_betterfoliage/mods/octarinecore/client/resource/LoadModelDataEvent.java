package mods.octarinecore.client.resource;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lmods/octarinecore/client/resource/LoadModelDataEvent;", "Lnet/minecraftforge/fml/common/eventhandler/Event;", "loader", "Lnet/minecraftforge/client/model/ModelLoader;", "(Lnet/minecraftforge/client/model/ModelLoader;)V", "getLoader", "()Lnet/minecraftforge/client/model/ModelLoader;", "BetterFoliage-MC1.12"}
)
public final class LoadModelDataEvent extends Event {
   @NotNull
   private final ModelLoader loader;

   @NotNull
   public final ModelLoader getLoader() {
      return this.loader;
   }

   public LoadModelDataEvent(@NotNull ModelLoader loader) {
      Intrinsics.checkParameterIsNotNull(loader, "loader");
      super();
      this.loader = loader;
   }
}
