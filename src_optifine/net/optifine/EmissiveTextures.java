package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class EmissiveTextures {
   private static String suffixEmissive = null;
   private static String suffixEmissivePng = null;
   private static boolean active = false;
   private static boolean render = false;
   private static boolean hasEmissive = false;
   private static boolean renderEmissive = false;
   private static cdr textureManager = .Config.getTextureManager();
   private static float lightMapX;
   private static float lightMapY;
   private static final String SUFFIX_PNG = ".png";
   private static final nf LOCATION_EMPTY = new nf("mcpatcher/ctm/default/empty.png");

   public static boolean isActive() {
      return active;
   }

   public static String getSuffixEmissive() {
      return suffixEmissive;
   }

   public static void beginRender() {
      render = true;
      hasEmissive = false;
   }

   public static cds getEmissiveTexture(cds texture, Map mapTextures) {
      if (!render) {
         return texture;
      } else if (!(texture instanceof cdm)) {
         return texture;
      } else {
         cdm simpleTexture = (cdm)texture;
         nf locationEmissive = simpleTexture.locationEmissive;
         if (!renderEmissive) {
            if (locationEmissive != null) {
               hasEmissive = true;
            }

            return texture;
         } else {
            if (locationEmissive == null) {
               locationEmissive = LOCATION_EMPTY;
            }

            cds textureEmissive = (cds)mapTextures.get(locationEmissive);
            if (textureEmissive == null) {
               textureEmissive = new cdm(locationEmissive);
               textureManager.a(locationEmissive, (cds)textureEmissive);
            }

            return (cds)textureEmissive;
         }
      }
   }

   public static boolean hasEmissive() {
      return hasEmissive;
   }

   public static void beginRenderEmissive() {
      lightMapX = cii.lastBrightnessX;
      lightMapY = cii.lastBrightnessY;
      cii.a(cii.r, 240.0F, lightMapY);
      renderEmissive = true;
   }

   public static void endRenderEmissive() {
      renderEmissive = false;
      cii.a(cii.r, lightMapX, lightMapY);
   }

   public static void endRender() {
      render = false;
      hasEmissive = false;
   }

   public static void update() {
      active = false;
      suffixEmissive = null;
      suffixEmissivePng = null;
      if (.Config.isEmissiveTextures()) {
         try {
            String fileName = "optifine/emissive.properties";
            nf loc = new nf(fileName);
            InputStream in = .Config.getResourceStream(loc);
            if (in == null) {
               return;
            }

            dbg("Loading " + fileName);
            Properties props = new Properties();
            props.load(in);
            in.close();
            suffixEmissive = props.getProperty("suffix.emissive");
            if (suffixEmissive != null) {
               suffixEmissivePng = suffixEmissive + ".png";
            }

            active = suffixEmissive != null;
         } catch (FileNotFoundException var4) {
            return;
         } catch (IOException var5) {
            var5.printStackTrace();
         }

      }
   }

   private static void dbg(String str) {
      .Config.dbg("EmissiveTextures: " + str);
   }

   private static void warn(String str) {
      .Config.warn("EmissiveTextures: " + str);
   }

   public static boolean isEmissive(nf loc) {
      return suffixEmissivePng == null ? false : loc.a().endsWith(suffixEmissivePng);
   }

   public static void loadTexture(nf loc, cdm tex) {
      if (loc != null && tex != null) {
         tex.isEmissive = false;
         tex.locationEmissive = null;
         if (suffixEmissivePng != null) {
            String path = loc.a();
            if (path.endsWith(".png")) {
               if (path.endsWith(suffixEmissivePng)) {
                  tex.isEmissive = true;
               } else {
                  String pathEmPng = path.substring(0, path.length() - ".png".length()) + suffixEmissivePng;
                  nf locEmPng = new nf(loc.b(), pathEmPng);
                  if (.Config.hasResource(locEmPng)) {
                     tex.locationEmissive = locEmPng;
                  }
               }
            }
         }
      }
   }
}
