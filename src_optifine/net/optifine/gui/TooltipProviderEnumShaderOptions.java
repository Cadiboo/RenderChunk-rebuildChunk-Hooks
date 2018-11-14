package net.optifine.gui;

import java.awt.Rectangle;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;

public class TooltipProviderEnumShaderOptions implements TooltipProvider {
   public Rectangle getTooltipBounds(blk guiScreen, int x, int y) {
      int x1 = guiScreen.l - 450;
      int y1 = 35;
      if (x1 < 10) {
         x1 = 10;
      }

      if (y <= y1 + 94) {
         y1 += 100;
      }

      int x2 = x1 + 150 + 150;
      int y2 = y1 + 84 + 10;
      return new Rectangle(x1, y1, x2 - x1, y2 - y1);
   }

   public boolean isRenderBorder() {
      return true;
   }

   public String[] getTooltipLines(bja btn, int width) {
      if (btn instanceof GuiButtonDownloadShaders) {
         return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
      } else if (!(btn instanceof GuiButtonEnumShaderOption)) {
         return null;
      } else {
         GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)btn;
         EnumShaderOption option = gbeso.getEnumShaderOption();
         String[] lines = this.getTooltipLines(option);
         return lines;
      }
   }

   private String[] getTooltipLines(EnumShaderOption option) {
      return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
   }
}
