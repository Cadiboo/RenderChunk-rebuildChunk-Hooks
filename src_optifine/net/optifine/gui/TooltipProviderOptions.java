package net.optifine.gui;

import bid.a;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.optifine.Lang;

public class TooltipProviderOptions implements TooltipProvider {
   public Rectangle getTooltipBounds(blk guiScreen, int x, int y) {
      int x1 = guiScreen.l / 2 - 150;
      int y1 = guiScreen.m / 6 - 7;
      if (y <= y1 + 98) {
         y1 += 105;
      }

      int x2 = x1 + 150 + 150;
      int y2 = y1 + 84 + 10;
      return new Rectangle(x1, y1, x2 - x1, y2 - y1);
   }

   public boolean isRenderBorder() {
      return false;
   }

   public String[] getTooltipLines(bja btn, int width) {
      if (!(btn instanceof IOptionControl)) {
         return null;
      } else {
         IOptionControl ctl = (IOptionControl)btn;
         a option = ctl.getOption();
         String[] lines = getTooltipLines(option.d());
         return lines;
      }
   }

   public static String[] getTooltipLines(String key) {
      List list = new ArrayList();

      for(int i = 0; i < 10; ++i) {
         String lineKey = key + ".tooltip." + (i + 1);
         String line = Lang.get(lineKey, (String)null);
         if (line == null) {
            break;
         }

         list.add(line);
      }

      if (list.size() <= 0) {
         return null;
      } else {
         String[] lines = (String[])((String[])list.toArray(new String[list.size()]));
         return lines;
      }
   }
}
