package net.optifine.player;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private nf textureLocation = null;
   private BufferedImage textureImage = null;
   private cdg texture = null;
   private nf locationMissing = new nf("textures/blocks/wool_colored_red.png");
   public static final int ATTACH_BODY = 0;
   public static final int ATTACH_HEAD = 1;
   public static final int ATTACH_LEFT_ARM = 2;
   public static final int ATTACH_RIGHT_ARM = 3;
   public static final int ATTACH_LEFT_LEG = 4;
   public static final int ATTACH_RIGHT_LEG = 5;
   public static final int ATTACH_CAPE = 6;

   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
      this.textureSize = textureSize;
      this.usePlayerTexture = usePlayerTexture;
      this.modelRenderers = modelRenderers;
   }

   public void render(bpx modelBiped, bua player, float scale, float partialTicks) {
      cdr textureManager = .Config.getTextureManager();
      if (this.usePlayerTexture) {
         textureManager.a(player.m());
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new cdg(this.textureImage);
            bib.z().N().a(this.textureLocation, this.texture);
         }

         textureManager.a(this.textureLocation);
      } else {
         textureManager.a(this.locationMissing);
      }

      for(int i = 0; i < this.modelRenderers.length; ++i) {
         PlayerItemRenderer pir = this.modelRenderers[i];
         bus.G();
         if (player.aU()) {
            bus.c(0.0F, 0.2F, 0.0F);
         }

         pir.render(modelBiped, scale);
         bus.H();
      }

   }

   public static brs getAttachModel(bpx modelBiped, int attachTo) {
      switch(attachTo) {
      case 0:
         return modelBiped.g;
      case 1:
         return modelBiped.e;
      case 2:
         return modelBiped.i;
      case 3:
         return modelBiped.h;
      case 4:
         return modelBiped.k;
      case 5:
         return modelBiped.j;
      default:
         return null;
      }
   }

   public BufferedImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(BufferedImage textureImage) {
      this.textureImage = textureImage;
   }

   public cdg getTexture() {
      return this.texture;
   }

   public nf getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(nf textureLocation) {
      this.textureLocation = textureLocation;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
