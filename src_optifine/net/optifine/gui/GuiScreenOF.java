package net.optifine.gui;

import java.io.IOException;
import java.util.List;

public class GuiScreenOF extends blk {
   protected void actionPerformedRightClick(bja button) throws IOException {
   }

   protected void a(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.a(mouseX, mouseY, mouseButton);
      if (mouseButton == 1) {
         bja btn = getSelectedButton(mouseX, mouseY, this.n);
         if (btn != null && btn.l) {
            btn.a(this.j.U());
            this.actionPerformedRightClick(btn);
         }
      }

   }

   public static bja getSelectedButton(int x, int y, List listButtons) {
      for(int i = 0; i < listButtons.size(); ++i) {
         bja btn = (bja)listButtons.get(i);
         if (btn.m) {
            int btnWidth = bls.getButtonWidth(btn);
            int btnHeight = bls.getButtonHeight(btn);
            if (x >= btn.h && y >= btn.i && x < btn.h + btnWidth && y < btn.i + btnHeight) {
               return btn;
            }
         }
      }

      return null;
   }
}
