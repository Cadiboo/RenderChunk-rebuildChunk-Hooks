package mods.octarinecore.client.gui;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.config.GuiConfigEntries.ArrayEntry;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"},
   d2 = {"Lmods/octarinecore/client/gui/NonVerboseArrayEntry;", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries$ArrayEntry;", "owningScreen", "Lnet/minecraftforge/fml/client/config/GuiConfig;", "owningEntryList", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries;", "configElement", "Lnet/minecraftforge/fml/client/config/IConfigElement;", "(Lnet/minecraftforge/fml/client/config/GuiConfig;Lnet/minecraftforge/fml/client/config/GuiConfigEntries;Lnet/minecraftforge/fml/client/config/IConfigElement;)V", "updateValueButtonText", "", "BetterFoliage-MC1.12"}
)
public final class NonVerboseArrayEntry extends ArrayEntry {
   public void updateValueButtonText() {
      GuiButtonExt var10000 = this.btnValue;
      StringBuilder var10001 = (new StringBuilder()).append("");
      IConfigElement var10002 = this.configElement;
      Intrinsics.checkExpressionValueIsNotNull(this.configElement, "configElement");
      var10000.field_146126_j = I18n.func_135052_a(var10001.append(var10002.getLanguageKey()).append(".arrayEntry").toString(), new Object[]{this.currentValues.length});
   }

   public NonVerboseArrayEntry(@NotNull GuiConfig owningScreen, @NotNull GuiConfigEntries owningEntryList, @NotNull IConfigElement configElement) {
      Intrinsics.checkParameterIsNotNull(owningScreen, "owningScreen");
      Intrinsics.checkParameterIsNotNull(owningEntryList, "owningEntryList");
      Intrinsics.checkParameterIsNotNull(configElement, "configElement");
      super(owningScreen, owningEntryList, configElement);
      List var10000 = this.toolTip;
      if (this.toolTip == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<kotlin.String>");
      } else {
         Utils.stripTooltipDefaultText(TypeIntrinsics.asMutableList(var10000));
         String shortDefaults = I18n.func_135052_a("" + configElement.getLanguageKey() + ".arrayEntry", new Object[]{configElement.getDefaults().length});
         var10000 = this.toolTip;
         List var10001 = this.mc.field_71466_p.func_78271_c("" + TextFormatting.AQUA + "" + I18n.func_135052_a("fml.configgui.tooltip.default", new Object[]{shortDefaults}), 300);
         Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.fontRenderer.listForm…\", shortDefaults)}\", 300)");
         var10000.addAll((Collection)var10001);
      }
   }
}
