package net.optifine.player;

import java.awt.image.BufferedImage;

public class CapeImageBuffer extends buz {
   private bua player;
   private nf resourceLocation;

   public CapeImageBuffer(bua player, nf resourceLocation) {
      this.player = player;
      this.resourceLocation = resourceLocation;
   }

   public BufferedImage a(BufferedImage var1) {
      return CapeUtils.parseCape(var1);
   }

   public void a() {
      if (this.player != null) {
         this.player.setLocationOfCape(this.resourceLocation);
      }

   }

   public void cleanup() {
      this.player = null;
   }
}
