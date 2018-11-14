package net.optifine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;

public class LinkedListTest {
   public static void main(String[] args) throws Exception {
      LinkedList linkedList = new LinkedList();
      List listFree = new ArrayList();
      List listUsed = new ArrayList();
      Random random = new Random();
      int count = 100;

      int i;
      VboRange range;
      for(i = 0; i < count; ++i) {
         range = new VboRange();
         range.setPosition(i);
         listFree.add(range);
      }

      for(i = 0; i < 100000; ++i) {
         checkLists(listFree, listUsed, count);
         checkLinkedList(linkedList, listUsed.size());
         if (i % 5 == 0) {
            dbgLinkedList(linkedList);
         }

         LinkedList.Node node;
         if (random.nextBoolean()) {
            if (!listFree.isEmpty()) {
               range = (VboRange)listFree.get(random.nextInt(listFree.size()));
               node = range.getNode();
               if (random.nextBoolean()) {
                  linkedList.addFirst(node);
                  dbg("Add first: " + range.getPosition());
               } else if (random.nextBoolean()) {
                  linkedList.addLast(node);
                  dbg("Add last: " + range.getPosition());
               } else {
                  if (listUsed.isEmpty()) {
                     continue;
                  }

                  VboRange rangePrev = (VboRange)listUsed.get(random.nextInt(listUsed.size()));
                  LinkedList.Node nodePrev = rangePrev.getNode();
                  linkedList.addAfter(nodePrev, node);
                  dbg("Add after: " + rangePrev.getPosition() + ", " + range.getPosition());
               }

               listFree.remove(range);
               listUsed.add(range);
            }
         } else if (!listUsed.isEmpty()) {
            range = (VboRange)listUsed.get(random.nextInt(listUsed.size()));
            node = range.getNode();
            linkedList.remove(node);
            dbg("Remove: " + range.getPosition());
            listUsed.remove(range);
            listFree.add(range);
         }
      }

   }

   private static void dbgLinkedList(LinkedList linkedList) {
      StringBuffer sb = new StringBuffer();

      VboRange range;
      for(Iterator it = linkedList.iterator(); it.hasNext(); sb.append(range.getPosition())) {
         LinkedList.Node node = (LinkedList.Node)it.next();
         range = (VboRange)node.getItem();
         if (sb.length() > 0) {
            sb.append(", ");
         }
      }

      dbg("List: " + sb);
   }

   private static void checkLinkedList(LinkedList linkedList, int used) {
      if (linkedList.getSize() != used) {
         throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used);
      } else {
         int count = 0;

         for(LinkedList.Node node = linkedList.getFirst(); node != null; node = node.getNext()) {
            ++count;
         }

         if (linkedList.getSize() != count) {
            throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + count);
         } else {
            int countBack = 0;

            for(LinkedList.Node nodeBack = linkedList.getLast(); nodeBack != null; nodeBack = nodeBack.getPrev()) {
               ++countBack;
            }

            if (linkedList.getSize() != countBack) {
               throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + countBack);
            }
         }
      }
   }

   private static void checkLists(List listFree, List listUsed, int count) {
      int total = listFree.size() + listUsed.size();
      if (total != count) {
         throw new RuntimeException("Total size: " + total);
      }
   }

   private static void dbg(String str) {
      System.out.println(str);
   }
}
