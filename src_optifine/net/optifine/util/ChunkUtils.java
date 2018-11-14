package net.optifine.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;

public class ChunkUtils {
   private static ReflectorClass chunkClass = new ReflectorClass(axw.class);
   private static ReflectorField fieldHasEntities = findFieldHasEntities();
   private static ReflectorField fieldPrecipitationHeightMap;

   public static boolean hasEntities(axw chunk) {
      return Reflector.getFieldValueBoolean(chunk, fieldHasEntities, true);
   }

   public static int getPrecipitationHeight(axw chunk, et pos) {
      int[] precipitationHeightMap = (int[])((int[])Reflector.getFieldValue(chunk, fieldPrecipitationHeightMap));
      if (precipitationHeightMap != null && precipitationHeightMap.length == 256) {
         int cx = pos.p() & 15;
         int cz = pos.r() & 15;
         int ix = cx | cz << 4;
         int y = precipitationHeightMap[ix];
         if (y >= 0) {
            return y;
         } else {
            et posPrep = chunk.f(pos);
            return posPrep.q();
         }
      } else {
         return -1;
      }
   }

   private static ReflectorField findFieldHasEntities() {
      try {
         axw chunk = new axw((amu)null, 0, 0);
         List listBoolFields = new ArrayList();
         List listBoolValuesPre = new ArrayList();
         Field[] fields = axw.class.getDeclaredFields();

         for(int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            if (field.getType() == Boolean.TYPE) {
               field.setAccessible(true);
               listBoolFields.add(field);
               listBoolValuesPre.add(field.get(chunk));
            }
         }

         chunk.g(false);
         List listBoolValuesFalse = new ArrayList();
         Iterator it = listBoolFields.iterator();

         while(it.hasNext()) {
            Field field = (Field)it.next();
            listBoolValuesFalse.add(field.get(chunk));
         }

         chunk.g(true);
         List listBoolValuesTrue = new ArrayList();
         Iterator it = listBoolFields.iterator();

         Field field;
         while(it.hasNext()) {
            field = (Field)it.next();
            listBoolValuesTrue.add(field.get(chunk));
         }

         List listMatchingFields = new ArrayList();

         for(int i = 0; i < listBoolFields.size(); ++i) {
            Field field = (Field)listBoolFields.get(i);
            Boolean valFalse = (Boolean)listBoolValuesFalse.get(i);
            Boolean valTrue = (Boolean)listBoolValuesTrue.get(i);
            if (!valFalse.booleanValue() && valTrue.booleanValue()) {
               listMatchingFields.add(field);
               Boolean valPre = (Boolean)listBoolValuesPre.get(i);
               field.set(chunk, valPre);
            }
         }

         if (listMatchingFields.size() == 1) {
            field = (Field)listMatchingFields.get(0);
            return new ReflectorField(field);
         }
      } catch (Exception var12) {
         .Config.warn(var12.getClass().getName() + " " + var12.getMessage());
      }

      .Config.warn("Error finding Chunk.hasEntities");
      return new ReflectorField(new ReflectorClass(axw.class), "hasEntities");
   }

   static {
      fieldPrecipitationHeightMap = new ReflectorField(chunkClass, int[].class, 0);
   }
}
