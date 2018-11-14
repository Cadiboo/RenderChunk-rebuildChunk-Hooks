package net.optifine.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectorRaw {
   public static Field getField(Class cls, Class fieldType) {
      try {
         Field[] fileds = cls.getDeclaredFields();

         for(int i = 0; i < fileds.length; ++i) {
            Field field = fileds[i];
            if (field.getType() == fieldType) {
               field.setAccessible(true);
               return field;
            }
         }

         return null;
      } catch (Exception var5) {
         return null;
      }
   }

   public static Field[] getFields(Class cls, Class fieldType) {
      try {
         Field[] fields = cls.getDeclaredFields();
         return getFields(fields, fieldType);
      } catch (Exception var3) {
         return null;
      }
   }

   public static Field[] getFields(Field[] fields, Class fieldType) {
      try {
         List list = new ArrayList();

         for(int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            if (field.getType() == fieldType) {
               field.setAccessible(true);
               list.add(field);
            }
         }

         Field[] fs = (Field[])((Field[])list.toArray(new Field[list.size()]));
         return fs;
      } catch (Exception var5) {
         return null;
      }
   }

   public static Field[] getFieldsAfter(Class cls, Field field, Class fieldType) {
      try {
         Field[] fields = cls.getDeclaredFields();
         List list = Arrays.asList(fields);
         int posStart = list.indexOf(field);
         if (posStart < 0) {
            return new Field[0];
         } else {
            List listAfter = list.subList(posStart + 1, list.size());
            Field[] fieldsAfter = (Field[])((Field[])listAfter.toArray(new Field[listAfter.size()]));
            return getFields(fieldsAfter, fieldType);
         }
      } catch (Exception var8) {
         return null;
      }
   }

   public static Field[] getFields(Object obj, Field[] fields, Class fieldType, Object value) {
      try {
         List list = new ArrayList();

         for(int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            if (field.getType() == fieldType) {
               boolean staticField = Modifier.isStatic(field.getModifiers());
               if ((obj != null || staticField) && (obj == null || !staticField)) {
                  field.setAccessible(true);
                  Object fieldVal = field.get(obj);
                  if (fieldVal == value) {
                     list.add(field);
                  } else if (fieldVal != null && value != null && fieldVal.equals(value)) {
                     list.add(field);
                  }
               }
            }
         }

         Field[] fs = (Field[])((Field[])list.toArray(new Field[list.size()]));
         return fs;
      } catch (Exception var9) {
         return null;
      }
   }

   public static Field getField(Class cls, Class fieldType, int index) {
      Field[] fields = getFields(cls, fieldType);
      return index >= 0 && index < fields.length ? fields[index] : null;
   }

   public static Field getFieldAfter(Class cls, Field field, Class fieldType, int index) {
      Field[] fields = getFieldsAfter(cls, field, fieldType);
      return index >= 0 && index < fields.length ? fields[index] : null;
   }

   public static Object getFieldValue(Object obj, Class cls, Class fieldType) {
      ReflectorField field = getReflectorField(cls, fieldType);
      if (field == null) {
         return null;
      } else {
         return !field.exists() ? null : Reflector.getFieldValue(obj, field);
      }
   }

   public static Object getFieldValue(Object obj, Class cls, Class fieldType, int index) {
      ReflectorField field = getReflectorField(cls, fieldType, index);
      if (field == null) {
         return null;
      } else {
         return !field.exists() ? null : Reflector.getFieldValue(obj, field);
      }
   }

   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, Object value) {
      ReflectorField field = getReflectorField(cls, fieldType);
      if (field == null) {
         return false;
      } else {
         return !field.exists() ? false : Reflector.setFieldValue(obj, field, value);
      }
   }

   public static boolean setFieldValue(Object obj, Class cls, Class fieldType, int index, Object value) {
      ReflectorField field = getReflectorField(cls, fieldType, index);
      if (field == null) {
         return false;
      } else {
         return !field.exists() ? false : Reflector.setFieldValue(obj, field, value);
      }
   }

   public static ReflectorField getReflectorField(Class cls, Class fieldType) {
      Field field = getField(cls, fieldType);
      if (field == null) {
         return null;
      } else {
         ReflectorClass rc = new ReflectorClass(cls);
         return new ReflectorField(rc, field.getName());
      }
   }

   public static ReflectorField getReflectorField(Class cls, Class fieldType, int index) {
      Field field = getField(cls, fieldType, index);
      if (field == null) {
         return null;
      } else {
         ReflectorClass rc = new ReflectorClass(cls);
         return new ReflectorField(rc, field.getName());
      }
   }
}
