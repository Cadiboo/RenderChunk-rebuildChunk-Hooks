package net.optifine;

import java.util.Properties;

public class CustomLoadingScreen {
   private nf locationTexture;
   private int scaleMode = 0;
   private int scale = 2;
   private boolean center;
   private static final int SCALE_DEFAULT = 2;
   private static final int SCALE_MODE_FIXED = 0;
   private static final int SCALE_MODE_FULL = 1;
   private static final int SCALE_MODE_STRETCH = 2;

   public CustomLoadingScreen(nf locationTexture, int scaleMode, int scale, boolean center) {
      this.locationTexture = locationTexture;
      this.scaleMode = scaleMode;
      this.scale = scale;
      this.center = center;
   }

   public static CustomLoadingScreen parseScreen(String path, int dimId, Properties props) {
      nf loc = new nf(path);
      int scaleMode = parseScaleMode(getProperty("scaleMode", dimId, props));
      int scaleDef = scaleMode == 0 ? 2 : 1;
      int scale = parseScale(getProperty("scale", dimId, props), scaleDef);
      boolean center = .Config.parseBoolean(getProperty("center", dimId, props), false);
      CustomLoadingScreen scr = new CustomLoadingScreen(loc, scaleMode, scale, center);
      return scr;
   }

   private static String getProperty(String key, int dim, Properties props) {
      if (props == null) {
         return null;
      } else {
         String val = props.getProperty("dim" + dim + "." + key);
         if (val != null) {
            return val;
         } else {
            val = props.getProperty(key);
            return val;
         }
      }
   }

   private static int parseScaleMode(String str) {
      if (str == null) {
         return 0;
      } else {
         str = str.toLowerCase().trim();
         if (str.equals("fixed")) {
            return 0;
         } else if (str.equals("full")) {
            return 1;
         } else if (str.equals("stretch")) {
            return 2;
         } else {
            CustomLoadingScreens.warn("Invalid scale mode: " + str);
            return 0;
         }
      }
   }

   private static int parseScale(String str, int def) {
      if (str == null) {
         return def;
      } else {
         str = str.trim();
         int val = .Config.parseInt(str, -1);
         if (val < 1) {
            CustomLoadingScreens.warn("Invalid scale: " + str);
            return def;
         } else {
            return val;
         }
      }
   }

   public void drawBackground(int width, int height) {
      bus.g();
      bus.p();
      bve tessellator = bve.a();
      buk bufferbuilder = tessellator.c();
      .Config.getTextureManager().a(this.locationTexture);
      bus.c(1.0F, 1.0F, 1.0F, 1.0F);
      double div = (double)(16 * this.scale);
      double uMax = (double)width / div;
      double vMax = (double)height / div;
      double du = 0.0D;
      double dv = 0.0D;
      if (this.center) {
         du = (div - (double)width) / (div * 2.0D);
         dv = (div - (double)height) / (div * 2.0D);
      }

      switch(this.scaleMode) {
      case 1:
         div = (double)Math.max(width, height);
         uMax = (double)(this.scale * width) / div;
         vMax = (double)(this.scale * height) / div;
         if (this.center) {
            du = (double)this.scale * (div - (double)width) / (div * 2.0D);
            dv = (double)this.scale * (div - (double)height) / (div * 2.0D);
         }
         break;
      case 2:
         uMax = (double)this.scale;
         vMax = (double)this.scale;
         du = 0.0D;
         dv = 0.0D;
      }

      bufferbuilder.a(7, cdy.i);
      bufferbuilder.b(0.0D, (double)height, 0.0D).a(du, dv + vMax).b(255, 255, 255, 255).d();
      bufferbuilder.b((double)width, (double)height, 0.0D).a(du + uMax, dv + vMax).b(255, 255, 255, 255).d();
      bufferbuilder.b((double)width, 0.0D, 0.0D).a(du + uMax, dv).b(255, 255, 255, 255).d();
      bufferbuilder.b(0.0D, 0.0D, 0.0D).a(du, dv).b(255, 255, 255, 255).d();
      tessellator.b();
   }
}
