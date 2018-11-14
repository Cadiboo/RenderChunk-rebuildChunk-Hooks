package net.optifine.gui;

import bid.a;

public class GuiOptionSliderOF extends bjs implements IOptionControl {
   private a option = null;

   public GuiOptionSliderOF(int id, int x, int y, a option) {
      super(id, x, y, option);
      this.option = option;
   }

   public a getOption() {
      return this.option;
   }
}
