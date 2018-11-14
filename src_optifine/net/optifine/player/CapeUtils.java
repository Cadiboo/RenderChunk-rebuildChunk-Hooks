package net.optifine.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

public class CapeUtils {
   public static void downloadCape(bua player) {
      String username = player.getNameClear();
      if (username != null && !username.isEmpty() && !username.contains("\u0000")) {
         String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
         String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
         nf rl = new nf("capeof/" + mptHash);
         cdr textureManager = bib.z().N();
         cds tex = textureManager.b(rl);
         if (tex != null && tex instanceof cdh) {
            cdh tdid = (cdh)tex;
            if (tdid.imageFound != null) {
               if (tdid.imageFound.booleanValue()) {
                  player.setLocationOfCape(rl);
               }

               return;
            }
         }

         CapeImageBuffer cib = new CapeImageBuffer(player, rl);
         cdh textureCape = new cdh((File)null, ofCapeUrl, (nf)null, cib);
         textureCape.pipeline = true;
         textureManager.a(rl, textureCape);
      }

   }

   public static BufferedImage parseCape(BufferedImage img) {
      int imageWidth = 64;
      int imageHeight = 32;
      int srcWidth = img.getWidth();

      for(int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight; imageHeight *= 2) {
         imageWidth *= 2;
      }

      BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
      Graphics g = imgNew.getGraphics();
      g.drawImage(img, 0, 0, (ImageObserver)null);
      g.dispose();
      return imgNew;
   }
}
