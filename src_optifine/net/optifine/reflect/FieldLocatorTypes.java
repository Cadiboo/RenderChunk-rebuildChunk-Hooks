package net.optifine.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FieldLocatorTypes implements IFieldLocator {
   private Field field = null;

   public FieldLocatorTypes(Class cls, Class[] preTypes, Class type, Class[] postTypes, String errorName) {
      Field[] fs = cls.getDeclaredFields();
      List types = new ArrayList();

      for(int i = 0; i < fs.length; ++i) {
         Field field = fs[i];
         types.add(field.getType());
      }

      List typesMatch = new ArrayList();
      typesMatch.addAll(Arrays.asList(preTypes));
      typesMatch.add(type);
      typesMatch.addAll(Arrays.asList(postTypes));
      int index = Collections.indexOfSubList(types, typesMatch);
      if (index < 0) {
         .Config.warn("Field not found: " + errorName);
      } else {
         int index2 = Collections.indexOfSubList(types.subList(index + 1, types.size()), typesMatch);
         if (index2 >= 0) {
            .Config.warn("More than one match found for field: " + errorName);
         } else {
            int indexField = index + preTypes.length;
            this.field = fs[indexField];
         }
      }
   }

   public Field getField() {
      return this.field;
   }
}
