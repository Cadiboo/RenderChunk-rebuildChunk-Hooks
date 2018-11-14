package net.optifine.util;

public class MathUtilsTest {
   public static void main(String[] args) throws Exception {
      MathUtilsTest.OPER[] values = MathUtilsTest.OPER.values();

      for(int i = 0; i < values.length; ++i) {
         MathUtilsTest.OPER oper = values[i];
         dbg("******** " + oper + " ***********");
         test(oper, false);
      }

   }

   private static void test(MathUtilsTest.OPER oper, boolean fast) {
      float min;
      float max;
      switch(oper) {
      case SIN:
      case COS:
         min = -3.1415927F;
         max = 3.1415927F;
         break;
      case ASIN:
      case ACOS:
         min = -1.0F;
         max = 1.0F;
         break;
      default:
         return;
      }

      int count = 10;

      for(int i = 0; i <= count; ++i) {
         float val = min + (float)i * (max - min) / (float)count;
         float res1;
         float res2;
         switch(oper) {
         case SIN:
            res1 = (float)Math.sin((double)val);
            res2 = rk.a(val);
            break;
         case COS:
            res1 = (float)Math.cos((double)val);
            res2 = rk.b(val);
            break;
         case ASIN:
            res1 = (float)Math.asin((double)val);
            res2 = MathUtils.asin(val);
            break;
         case ACOS:
            res1 = (float)Math.acos((double)val);
            res2 = MathUtils.acos(val);
            break;
         default:
            return;
         }

         dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", val, res1, res2, Math.abs(res1 - res2)));
      }

   }

   public static void dbg(String str) {
      System.out.println(str);
   }

   private static enum OPER {
      SIN,
      COS,
      ASIN,
      ACOS;
   }
}
