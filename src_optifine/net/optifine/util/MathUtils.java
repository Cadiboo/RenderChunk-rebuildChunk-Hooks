package net.optifine.util;

public class MathUtils {
   public static final float PI = 3.1415927F;
   public static final float PI2 = 6.2831855F;
   public static final float PId2 = 1.5707964F;
   private static final float[] ASIN_TABLE = new float[65536];

   public static float asin(float value) {
      return ASIN_TABLE[(int)((double)(value + 1.0F) * 32767.5D) & '\uffff'];
   }

   public static float acos(float value) {
      return 1.5707964F - ASIN_TABLE[(int)((double)(value + 1.0F) * 32767.5D) & '\uffff'];
   }

   public static int getAverage(int[] vals) {
      if (vals.length <= 0) {
         return 0;
      } else {
         int sum = getSum(vals);
         int avg = sum / vals.length;
         return avg;
      }
   }

   public static int getSum(int[] vals) {
      if (vals.length <= 0) {
         return 0;
      } else {
         int sum = 0;

         for(int i = 0; i < vals.length; ++i) {
            int val = vals[i];
            sum += val;
         }

         return sum;
      }
   }

   public static int roundDownToPowerOfTwo(int val) {
      int po2 = rk.c(val);
      return val == po2 ? po2 : po2 / 2;
   }

   public static boolean equalsDelta(float f1, float f2, float delta) {
      return Math.abs(f1 - f2) <= delta;
   }

   public static float toDeg(float angle) {
      return angle * 180.0F / 3.1415927F;
   }

   public static float toRad(float angle) {
      return angle / 180.0F * 3.1415927F;
   }

   static {
      int i;
      for(i = 0; i < 65536; ++i) {
         ASIN_TABLE[i] = (float)Math.asin((double)i / 32767.5D - 1.0D);
      }

      for(i = -1; i < 2; ++i) {
         ASIN_TABLE[(int)(((double)i + 1.0D) * 32767.5D) & '\uffff'] = (float)Math.asin((double)i);
      }

   }
}
