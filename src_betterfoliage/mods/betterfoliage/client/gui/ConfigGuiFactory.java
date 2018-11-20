package mods.betterfoliage.client.gui;

import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import mods.betterfoliage.client.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u0018\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u0010H\u0016¨\u0006\u0012"},
   d2 = {"Lmods/betterfoliage/client/gui/ConfigGuiFactory;", "Lnet/minecraftforge/fml/client/IModGuiFactory;", "()V", "createConfigGui", "Lnet/minecraftforge/fml/client/config/GuiConfig;", "parentScreen", "Lnet/minecraft/client/gui/GuiScreen;", "hasConfigGui", "", "initialize", "", "minecraftInstance", "Lnet/minecraft/client/Minecraft;", "runtimeGuiCategories", "Ljava/util/HashSet;", "Lnet/minecraftforge/fml/client/IModGuiFactory$RuntimeOptionCategoryElement;", "Lkotlin/collections/HashSet;", "Companion", "BetterFoliage-MC1.12"}
)
public final class ConfigGuiFactory implements IModGuiFactory {
   public static final ConfigGuiFactory.Companion Companion = new ConfigGuiFactory.Companion((DefaultConstructorMarker)null);

   public void initialize(@Nullable Minecraft minecraftInstance) {
   }

   public boolean hasConfigGui() {
      return true;
   }

   @NotNull
   public HashSet runtimeGuiCategories() {
      return new HashSet();
   }

   @NotNull
   public GuiConfig createConfigGui(@Nullable GuiScreen parentScreen) {
      return Companion.createBFConfigGui(parentScreen);
   }

   @JvmStatic
   @NotNull
   public static final GuiConfig createBFConfigGui(@Nullable GuiScreen parentScreen) {
      return Companion.createBFConfigGui(parentScreen);
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lmods/betterfoliage/client/gui/ConfigGuiFactory$Companion;", "", "()V", "createBFConfigGui", "Lnet/minecraftforge/fml/client/config/GuiConfig;", "parentScreen", "Lnet/minecraft/client/gui/GuiScreen;", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @JvmStatic
      @NotNull
      public final GuiConfig createBFConfigGui(@Nullable GuiScreen parentScreen) {
         return new GuiConfig(parentScreen, Config.INSTANCE.getRootGuiElements(), "betterfoliage", (String)null, false, false, "Better Foliage");
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
