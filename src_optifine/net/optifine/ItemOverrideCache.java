package net.optifine;

import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.optifine.reflect.Reflector;
import net.optifine.util.CompoundKey;

public class ItemOverrideCache {
   private ItemOverrideProperty[] itemOverrideProperties;
   private Map mapValueModels = new HashMap();
   public static final nf LOCATION_NULL = new nf("");

   public ItemOverrideCache(ItemOverrideProperty[] itemOverrideProperties) {
      this.itemOverrideProperties = itemOverrideProperties;
   }

   public nf getModelLocation(aip stack, amu world, vp entity) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      return valueKey == null ? null : (nf)this.mapValueModels.get(valueKey);
   }

   public void putModelLocation(aip stack, amu world, vp entity, nf location) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      if (valueKey != null) {
         this.mapValueModels.put(valueKey, location);
      }
   }

   private CompoundKey getValueKey(aip stack, amu world, vp entity) {
      Integer[] indexes = new Integer[this.itemOverrideProperties.length];

      for(int i = 0; i < indexes.length; ++i) {
         Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
         if (index == null) {
            return null;
         }

         indexes[i] = index;
      }

      return new CompoundKey(indexes);
   }

   public static ItemOverrideCache make(List overrides) {
      if (overrides.isEmpty()) {
         return null;
      } else if (!Reflector.ItemOverride_mapResourceValues.exists()) {
         return null;
      } else {
         Map propertyValues = new LinkedHashMap();
         Iterator it = overrides.iterator();

         while(it.hasNext()) {
            bvz itemOverride = (bvz)it.next();
            Map mapResourceValues = (Map)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_mapResourceValues);
            Set setLocs = mapResourceValues.keySet();
            Iterator itloc = setLocs.iterator();

            while(itloc.hasNext()) {
               nf loc = (nf)itloc.next();
               Float val = (Float)mapResourceValues.get(loc);
               if (val != null) {
                  Set setValues = (Set)propertyValues.get(loc);
                  if (setValues == null) {
                     setValues = new HashSet();
                     propertyValues.put(loc, setValues);
                  }

                  ((Set)setValues).add(val);
               }
            }
         }

         List listProps = new ArrayList();
         Set setPropertyLocations = propertyValues.keySet();
         Iterator it = setPropertyLocations.iterator();

         while(it.hasNext()) {
            nf loc = (nf)it.next();
            Set setValues = (Set)propertyValues.get(loc);
            float[] values = Floats.toArray(setValues);
            ItemOverrideProperty prop = new ItemOverrideProperty(loc, values);
            listProps.add(prop);
         }

         ItemOverrideProperty[] props = (ItemOverrideProperty[])((ItemOverrideProperty[])listProps.toArray(new ItemOverrideProperty[listProps.size()]));
         ItemOverrideCache cache = new ItemOverrideCache(props);
         logCache(props, overrides);
         return cache;
      }
   }

   private static void logCache(ItemOverrideProperty[] props, List overrides) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < props.length; ++i) {
         ItemOverrideProperty prop = props[i];
         if (sb.length() > 0) {
            sb.append(", ");
         }

         sb.append("" + prop.getLocation() + "=" + prop.getValues().length);
      }

      if (overrides.size() > 0) {
         sb.append(" -> " + ((bvz)overrides.get(0)).a() + " ...");
      }

      .Config.dbg("ItemOverrideCache: " + sb.toString());
   }

   public String toString() {
      return "properties: " + this.itemOverrideProperties.length + ", models: " + this.mapValueModels.size();
   }
}
