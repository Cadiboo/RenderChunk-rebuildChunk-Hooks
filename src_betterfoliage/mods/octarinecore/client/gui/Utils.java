package mods.octarinecore.client.gui;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t¨\u0006\n"},
   d2 = {"stripTooltipDefaultText", "", "tooltip", "", "", "textComponent", "Lnet/minecraft/util/text/TextComponentString;", "msg", "color", "Lnet/minecraft/util/text/TextFormatting;", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Utils"
)
public final class Utils {
   public static final void stripTooltipDefaultText(@NotNull List tooltip) {
      Intrinsics.checkParameterIsNotNull(tooltip, "tooltip");
      boolean defaultRows = false;
      Iterator iter = tooltip.iterator();

      while(iter.hasNext()) {
         String var10000 = (String)iter.next();
         String var10001 = TextFormatting.AQUA.toString();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "AQUA.toString()");
         if (StringsKt.startsWith$default(var10000, var10001, false, 2, (Object)null)) {
            defaultRows = true;
         }

         if (defaultRows) {
            iter.remove();
         }
      }

   }

   @NotNull
   public static final TextComponentString textComponent(@NotNull String msg, @NotNull TextFormatting color) {
      Intrinsics.checkParameterIsNotNull(msg, "msg");
      Intrinsics.checkParameterIsNotNull(color, "color");
      Style var3 = new Style();
      var3.func_150238_a(color);
      Style style = var3;
      TextComponentString var6 = new TextComponentString(msg);
      var6.func_150255_a(style);
      return var6;
   }
}
