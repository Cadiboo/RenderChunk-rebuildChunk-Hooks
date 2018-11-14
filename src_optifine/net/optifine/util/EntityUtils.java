package net.optifine.util;

import java.util.HashMap;
import java.util.Map;

public class EntityUtils {
   private static final Map mapIdByClass = new HashMap();
   private static final Map mapIdByLocation = new HashMap();
   private static final Map mapIdByName = new HashMap();

   public static int getEntityIdByClass(vg entity) {
      return entity == null ? -1 : getEntityIdByClass(entity.getClass());
   }

   public static int getEntityIdByClass(Class cls) {
      Integer id = (Integer)mapIdByClass.get(cls);
      return id == null ? -1 : id.intValue();
   }

   public static int getEntityIdByLocation(String locStr) {
      Integer id = (Integer)mapIdByLocation.get(locStr);
      return id == null ? -1 : id.intValue();
   }

   public static int getEntityIdByName(String name) {
      Integer id = (Integer)mapIdByName.get(name);
      return id == null ? -1 : id.intValue();
   }

   static {
      for(int i = 0; i < 1000; ++i) {
         Class cls = vi.a(i);
         if (cls != null) {
            nf loc = vi.a(cls);
            if (loc != null) {
               String locStr = loc.toString();
               String name = vi.a(loc);
               if (name != null) {
                  if (mapIdByClass.containsKey(cls)) {
                     .Config.warn("Duplicate entity class: " + cls + ", id1: " + mapIdByClass.get(cls) + ", id2: " + i);
                  }

                  if (mapIdByLocation.containsKey(locStr)) {
                     .Config.warn("Duplicate entity location: " + locStr + ", id1: " + mapIdByLocation.get(locStr) + ", id2: " + i);
                  }

                  if (mapIdByName.containsKey(locStr)) {
                     .Config.warn("Duplicate entity name: " + name + ", id1: " + mapIdByName.get(name) + ", id2: " + i);
                  }

                  mapIdByClass.put(cls, i);
                  mapIdByLocation.put(locStr, i);
                  mapIdByName.put(name, i);
               }
            }
         }
      }

   }
}
