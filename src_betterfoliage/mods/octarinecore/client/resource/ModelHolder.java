package mods.octarinecore.client.resource;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.render.Model;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u001e\u0012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000f\u001a\u00020\u0005H\u0016R\"\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0010"},
   d2 = {"Lmods/octarinecore/client/resource/ModelHolder;", "Lmods/octarinecore/client/resource/IConfigChangeListener;", "init", "Lkotlin/Function1;", "Lmods/octarinecore/client/render/Model;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)V", "getInit", "()Lkotlin/jvm/functions/Function1;", "model", "getModel", "()Lmods/octarinecore/client/render/Model;", "setModel", "(Lmods/octarinecore/client/render/Model;)V", "onConfigChange", "BetterFoliage-MC1.12"}
)
public final class ModelHolder implements IConfigChangeListener {
   @NotNull
   private Model model;
   @NotNull
   private final Function1 init;

   @NotNull
   public final Model getModel() {
      return this.model;
   }

   public final void setModel(@NotNull Model var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.model = var1;
   }

   public void onConfigChange() {
      Model var1 = new Model();
      Function1 var2 = this.init;
      var2.invoke(var1);
      this.model = var1;
   }

   @NotNull
   public final Function1 getInit() {
      return this.init;
   }

   public ModelHolder(@NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      super();
      this.init = init;
      Model var2 = new Model();
      Function1 var3 = this.init;
      var3.invoke(var2);
      this.model = var2;
   }
}
