package mods.octarinecore.client;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\tH\u0007R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015¨\u0006\u0019"},
   d2 = {"Lmods/octarinecore/client/KeyHandler;", "", "modId", "", "defaultKey", "", "lang", "action", "Lkotlin/Function1;", "Lnet/minecraftforge/fml/common/gameevent/InputEvent$KeyInputEvent;", "", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getAction", "()Lkotlin/jvm/functions/Function1;", "getDefaultKey", "()I", "keyBinding", "Lnet/minecraft/client/settings/KeyBinding;", "getKeyBinding", "()Lnet/minecraft/client/settings/KeyBinding;", "getLang", "()Ljava/lang/String;", "getModId", "handleKeyPress", "event", "BetterFoliage-MC1.12"}
)
public final class KeyHandler {
   @NotNull
   private final KeyBinding keyBinding;
   @NotNull
   private final String modId;
   private final int defaultKey;
   @NotNull
   private final String lang;
   @NotNull
   private final Function1 action;

   @NotNull
   public final KeyBinding getKeyBinding() {
      return this.keyBinding;
   }

   @SubscribeEvent
   public final void handleKeyPress(@NotNull KeyInputEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (this.keyBinding.func_151468_f()) {
         this.action.invoke(event);
      }

   }

   @NotNull
   public final String getModId() {
      return this.modId;
   }

   public final int getDefaultKey() {
      return this.defaultKey;
   }

   @NotNull
   public final String getLang() {
      return this.lang;
   }

   @NotNull
   public final Function1 getAction() {
      return this.action;
   }

   public KeyHandler(@NotNull String modId, int defaultKey, @NotNull String lang, @NotNull Function1 action) {
      Intrinsics.checkParameterIsNotNull(modId, "modId");
      Intrinsics.checkParameterIsNotNull(lang, "lang");
      Intrinsics.checkParameterIsNotNull(action, "action");
      super();
      this.modId = modId;
      this.defaultKey = defaultKey;
      this.lang = lang;
      this.action = action;
      this.keyBinding = new KeyBinding(this.lang, this.defaultKey, this.modId);
      ClientRegistry.registerKeyBinding(this.keyBinding);
      FMLCommonHandler.instance().bus().register(this);
   }
}
