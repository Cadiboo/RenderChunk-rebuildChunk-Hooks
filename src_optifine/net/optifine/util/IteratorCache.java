package net.optifine.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IteratorCache {
   private static Deque dequeIterators = new ArrayDeque();

   public static Iterator getReadOnly(List list) {
      Deque var1 = dequeIterators;
      synchronized(dequeIterators) {
         IteratorCache.IteratorReusable it = (IteratorCache.IteratorReusable)dequeIterators.pollFirst();
         if (it == null) {
            it = new IteratorCache.IteratorReadOnly();
         }

         ((IteratorCache.IteratorReusable)it).setList(list);
         return (Iterator)it;
      }
   }

   private static void finished(IteratorCache.IteratorReusable iterator) {
      Deque var1 = dequeIterators;
      synchronized(dequeIterators) {
         if (dequeIterators.size() <= 1000) {
            dequeIterators.addLast(iterator);
         }
      }
   }

   static {
      for(int i = 0; i < 1000; ++i) {
         IteratorCache.IteratorReadOnly it = new IteratorCache.IteratorReadOnly();
         dequeIterators.add(it);
      }

   }

   public static class IteratorReadOnly implements IteratorCache.IteratorReusable {
      private List list;
      private int index;
      private boolean hasNext;

      public void setList(List list) {
         if (this.hasNext) {
            throw new RuntimeException("Iterator still used, oldList: " + this.list + ", newList: " + list);
         } else {
            this.list = list;
            this.index = 0;
            this.hasNext = this.index < list.size();
         }
      }

      public Object next() {
         if (!this.hasNext) {
            return null;
         } else {
            Object obj = this.list.get(this.index);
            ++this.index;
            this.hasNext = this.index < this.list.size();
            return obj;
         }
      }

      public boolean hasNext() {
         if (!this.hasNext) {
            IteratorCache.finished(this);
            return false;
         } else {
            return this.hasNext;
         }
      }
   }

   public interface IteratorReusable extends Iterator {
      void setList(List var1);
   }
}
