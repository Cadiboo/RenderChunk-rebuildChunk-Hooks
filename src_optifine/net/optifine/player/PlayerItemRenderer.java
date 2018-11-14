package net.optifine.player;

public class PlayerItemRenderer {
   private int attachTo = 0;
   private brs modelRenderer = null;

   public PlayerItemRenderer(int attachTo, brs modelRenderer) {
      this.attachTo = attachTo;
      this.modelRenderer = modelRenderer;
   }

   public brs getModelRenderer() {
      return this.modelRenderer;
   }

   public void render(bpx modelBiped, float scale) {
      brs attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
      if (attachModel != null) {
         attachModel.c(scale);
      }

      this.modelRenderer.a(scale);
   }
}
