package net.optifine.render;

public class Blender {
   public static final int BLEND_ALPHA = 0;
   public static final int BLEND_ADD = 1;
   public static final int BLEND_SUBSTRACT = 2;
   public static final int BLEND_MULTIPLY = 3;
   public static final int BLEND_DODGE = 4;
   public static final int BLEND_BURN = 5;
   public static final int BLEND_SCREEN = 6;
   public static final int BLEND_OVERLAY = 7;
   public static final int BLEND_REPLACE = 8;
   public static final int BLEND_DEFAULT = 1;

   public static int parseBlend(String str) {
      if (str == null) {
         return 1;
      } else {
         str = str.toLowerCase().trim();
         if (str.equals("alpha")) {
            return 0;
         } else if (str.equals("add")) {
            return 1;
         } else if (str.equals("subtract")) {
            return 2;
         } else if (str.equals("multiply")) {
            return 3;
         } else if (str.equals("dodge")) {
            return 4;
         } else if (str.equals("burn")) {
            return 5;
         } else if (str.equals("screen")) {
            return 6;
         } else if (str.equals("overlay")) {
            return 7;
         } else if (str.equals("replace")) {
            return 8;
         } else {
            .Config.warn("Unknown blend: " + str);
            return 1;
         }
      }
   }

   public static void setupBlend(int blend, float brightness) {
      switch(blend) {
      case 0:
         bus.d();
         bus.m();
         bus.b(770, 771);
         bus.c(1.0F, 1.0F, 1.0F, brightness);
         break;
      case 1:
         bus.d();
         bus.m();
         bus.b(770, 1);
         bus.c(1.0F, 1.0F, 1.0F, brightness);
         break;
      case 2:
         bus.d();
         bus.m();
         bus.b(775, 0);
         bus.c(brightness, brightness, brightness, 1.0F);
         break;
      case 3:
         bus.d();
         bus.m();
         bus.b(774, 771);
         bus.c(brightness, brightness, brightness, brightness);
         break;
      case 4:
         bus.d();
         bus.m();
         bus.b(1, 1);
         bus.c(brightness, brightness, brightness, 1.0F);
         break;
      case 5:
         bus.d();
         bus.m();
         bus.b(0, 769);
         bus.c(brightness, brightness, brightness, 1.0F);
         break;
      case 6:
         bus.d();
         bus.m();
         bus.b(1, 769);
         bus.c(brightness, brightness, brightness, 1.0F);
         break;
      case 7:
         bus.d();
         bus.m();
         bus.b(774, 768);
         bus.c(brightness, brightness, brightness, 1.0F);
         break;
      case 8:
         bus.e();
         bus.l();
         bus.c(1.0F, 1.0F, 1.0F, brightness);
      }

      bus.y();
   }

   public static void clearBlend(float rainBrightness) {
      bus.d();
      bus.m();
      bus.b(770, 1);
      bus.c(1.0F, 1.0F, 1.0F, rainBrightness);
   }
}
