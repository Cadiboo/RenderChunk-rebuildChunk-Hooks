package net.optifine.reflect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FieldLocatorActionKeyF3 implements IFieldLocator {
   public Field getField() {
      Class mcClass = bib.class;
      Field fieldRenderChunksMany = this.getFieldRenderChunksMany();
      if (fieldRenderChunksMany == null) {
         .Config.log("(Reflector) Field not present: " + mcClass.getName() + ".actionKeyF3 (field renderChunksMany not found)");
         return null;
      } else {
         Field fieldActionkeyF3 = ReflectorRaw.getFieldAfter(bib.class, fieldRenderChunksMany, Boolean.TYPE, 0);
         if (fieldActionkeyF3 == null) {
            .Config.log("(Reflector) Field not present: " + mcClass.getName() + ".actionKeyF3");
            return null;
         } else {
            return fieldActionkeyF3;
         }
      }
   }

   private Field getFieldRenderChunksMany() {
      bib mc = bib.z();
      boolean oldRenderChunksMany = mc.H;
      Field[] fields = bib.class.getDeclaredFields();
      mc.H = true;
      Field[] fieldsTrue = ReflectorRaw.getFields(mc, fields, Boolean.TYPE, Boolean.TRUE);
      mc.H = false;
      Field[] fieldsFalse = ReflectorRaw.getFields(mc, fields, Boolean.TYPE, Boolean.FALSE);
      mc.H = oldRenderChunksMany;
      Set setTrue = new HashSet(Arrays.asList(fieldsTrue));
      Set setFalse = new HashSet(Arrays.asList(fieldsFalse));
      Set setFields = new HashSet(setTrue);
      setFields.retainAll(setFalse);
      Field[] fs = (Field[])((Field[])setFields.toArray(new Field[setFields.size()]));
      return fs.length != 1 ? null : fs[0];
   }
}
