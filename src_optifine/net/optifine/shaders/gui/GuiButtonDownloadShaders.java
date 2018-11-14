package net.optifine.shaders.gui;

public class GuiButtonDownloadShaders extends bja {
   public GuiButtonDownloadShaders(int buttonID, int xPos, int yPos) {
      super(buttonID, xPos, yPos, 22, 20, "");
   }

   public void a(bib mc, int mouseX, int mouseY, float partialTicks) {
      if (this.m) {
         super.a(mc, mouseX, mouseY, partialTicks);
         nf locTexture = new nf("optifine/textures/icons.png");
         mc.N().a(locTexture);
         bus.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.b(this.h + 3, this.i + 2, 0, 0, 16, 16);
      }
   }
}
