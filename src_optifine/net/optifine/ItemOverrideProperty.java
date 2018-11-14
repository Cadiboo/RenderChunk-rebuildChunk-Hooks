package net.optifine;

import java.util.Arrays;

public class ItemOverrideProperty {
   private nf location;
   private float[] values;

   public ItemOverrideProperty(nf location, float[] values) {
      this.location = location;
      this.values = (float[])values.clone();
      Arrays.sort(this.values);
   }

   public Integer getValueIndex(aip stack, amu world, vp entity) {
      ain item = stack.c();
      aiq itemPropertyGetter = item.a(this.location);
      if (itemPropertyGetter == null) {
         return null;
      } else {
         float val = itemPropertyGetter.a(stack, world, entity);
         int index = Arrays.binarySearch(this.values, val);
         return index;
      }
   }

   public nf getLocation() {
      return this.location;
   }

   public float[] getValues() {
      return this.values;
   }

   public String toString() {
      return "location: " + this.location + ", values: [" + .Config.arrayToString(this.values) + "]";
   }
}
