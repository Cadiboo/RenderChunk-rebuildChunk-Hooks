package net.optifine.shaders.uniform;

import java.util.HashMap;
import java.util.Map;
import net.optifine.util.CounterInt;
import net.optifine.util.SmoothFloat;

public class Smoother {
   private static Map mapSmoothValues = new HashMap();
   private static CounterInt counterIds = new CounterInt(1);

   public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
      Map var4 = mapSmoothValues;
      synchronized(mapSmoothValues) {
         Integer key = id;
         SmoothFloat sf = (SmoothFloat)mapSmoothValues.get(key);
         if (sf == null) {
            sf = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
            mapSmoothValues.put(key, sf);
         }

         float valueSmooth = sf.getSmoothValue(value, timeFadeUpSec, timeFadeDownSec);
         return valueSmooth;
      }
   }

   public static int getNextId() {
      CounterInt var0 = counterIds;
      synchronized(counterIds) {
         return counterIds.nextValue();
      }
   }

   public static void resetValues() {
      Map var0 = mapSmoothValues;
      synchronized(mapSmoothValues) {
         mapSmoothValues.clear();
      }
   }
}
