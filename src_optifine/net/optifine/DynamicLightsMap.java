package net.optifine;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;

public class DynamicLightsMap {
   private Int2ObjectMap map = new Int2ObjectOpenHashMap();
   private List list = new ArrayList();
   private boolean dirty = false;

   public DynamicLight put(int id, DynamicLight dynamicLight) {
      DynamicLight old = (DynamicLight)this.map.put(id, dynamicLight);
      this.setDirty();
      return old;
   }

   public DynamicLight get(int id) {
      return (DynamicLight)this.map.get(id);
   }

   public int size() {
      return this.map.size();
   }

   public DynamicLight remove(int id) {
      DynamicLight old = (DynamicLight)this.map.remove(id);
      if (old != null) {
         this.setDirty();
      }

      return old;
   }

   public void clear() {
      this.map.clear();
      this.setDirty();
   }

   private void setDirty() {
      this.dirty = true;
   }

   public List valueList() {
      if (this.dirty) {
         this.list.clear();
         this.list.addAll(this.map.values());
         this.dirty = false;
      }

      return this.list;
   }
}
