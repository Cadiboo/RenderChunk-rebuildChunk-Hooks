package net.optifine.gui;

import bid.a;

public class GuiOptionButtonOF extends bjn implements IOptionControl {
   private a option = null;

   public GuiOptionButtonOF(int id, int x, int y, a option, String text) {
      super(id, x, y, option, text);
      this.option = option;
   }

   public a getOption() {
      return this.option;
   }
}
