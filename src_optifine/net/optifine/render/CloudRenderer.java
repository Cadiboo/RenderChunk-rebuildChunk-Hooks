package net.optifine.render;

import org.lwjgl.opengl.GL11;

public class CloudRenderer {
   private bib mc;
   private boolean updated = false;
   private boolean renderFancy = false;
   int cloudTickCounter;
   private bhe cloudColor;
   float partialTicks;
   private boolean updateRenderFancy = false;
   private int updateCloudTickCounter = 0;
   private bhe updateCloudColor = new bhe(-1.0D, -1.0D, -1.0D);
   private double updatePlayerX = 0.0D;
   private double updatePlayerY = 0.0D;
   private double updatePlayerZ = 0.0D;
   private int glListClouds = -1;

   public CloudRenderer(bib mc) {
      this.mc = mc;
      this.glListClouds = bia.a(1);
   }

   public void prepareToRender(boolean renderFancy, int cloudTickCounter, float partialTicks, bhe cloudColor) {
      this.renderFancy = renderFancy;
      this.cloudTickCounter = cloudTickCounter;
      this.partialTicks = partialTicks;
      this.cloudColor = cloudColor;
   }

   public boolean shouldUpdateGlList() {
      if (!this.updated) {
         return true;
      } else if (this.renderFancy != this.updateRenderFancy) {
         return true;
      } else if (this.cloudTickCounter >= this.updateCloudTickCounter + 20) {
         return true;
      } else if (Math.abs(this.cloudColor.b - this.updateCloudColor.b) > 0.003D) {
         return true;
      } else if (Math.abs(this.cloudColor.c - this.updateCloudColor.c) > 0.003D) {
         return true;
      } else if (Math.abs(this.cloudColor.d - this.updateCloudColor.d) > 0.003D) {
         return true;
      } else {
         vg rve = this.mc.aa();
         boolean belowCloudsPrev = this.updatePlayerY + (double)rve.by() < 128.0D + (double)(this.mc.t.ofCloudsHeight * 128.0F);
         boolean belowClouds = rve.n + (double)rve.by() < 128.0D + (double)(this.mc.t.ofCloudsHeight * 128.0F);
         return belowClouds != belowCloudsPrev;
      }
   }

   public void startUpdateGlList() {
      GL11.glNewList(this.glListClouds, 4864);
   }

   public void endUpdateGlList() {
      GL11.glEndList();
      this.updateRenderFancy = this.renderFancy;
      this.updateCloudTickCounter = this.cloudTickCounter;
      this.updateCloudColor = this.cloudColor;
      this.updatePlayerX = this.mc.aa().m;
      this.updatePlayerY = this.mc.aa().n;
      this.updatePlayerZ = this.mc.aa().o;
      this.updated = true;
      bus.I();
   }

   public void renderGlList() {
      vg entityliving = this.mc.aa();
      double exactPlayerX = entityliving.m + (entityliving.p - entityliving.m) * (double)this.partialTicks;
      double exactPlayerY = entityliving.n + (entityliving.q - entityliving.n) * (double)this.partialTicks;
      double exactPlayerZ = entityliving.o + (entityliving.r - entityliving.o) * (double)this.partialTicks;
      double dc = (double)((float)(this.cloudTickCounter - this.updateCloudTickCounter) + this.partialTicks);
      float cdx = (float)(exactPlayerX - this.updatePlayerX + dc * 0.03D);
      float cdy = (float)(exactPlayerY - this.updatePlayerY);
      float cdz = (float)(exactPlayerZ - this.updatePlayerZ);
      bus.G();
      if (this.renderFancy) {
         bus.c(-cdx / 12.0F, -cdy, -cdz / 12.0F);
      } else {
         bus.c(-cdx, -cdy, -cdz);
      }

      bus.s(this.glListClouds);
      bus.H();
      bus.I();
   }

   public void reset() {
      this.updated = false;
   }
}
