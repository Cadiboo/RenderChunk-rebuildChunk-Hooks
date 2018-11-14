package net.optifine.shaders.gui;

import net.optifine.shaders.config.ShaderOption;

public class GuiButtonShaderOption extends bja {
   private ShaderOption shaderOption = null;

   public GuiButtonShaderOption(int buttonId, int x, int y, int widthIn, int heightIn, ShaderOption shaderOption, String text) {
      super(buttonId, x, y, widthIn, heightIn, text);
      this.shaderOption = shaderOption;
   }

   public ShaderOption getShaderOption() {
      return this.shaderOption;
   }

   public void valueChanged() {
   }
}
