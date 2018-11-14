package net.optifine.util;

import java.util.Iterator;

public class LinkedList {
   private LinkedList.Node first;
   private LinkedList.Node last;
   private int size;

   public void addFirst(LinkedList.Node node) {
      this.checkNoParent(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         LinkedList.Node nodeNext = this.first;
         node.setNext(nodeNext);
         nodeNext.setPrev(node);
         this.first = node;
      }

      node.setParent(this);
      ++this.size;
   }

   public void addLast(LinkedList.Node node) {
      this.checkNoParent(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         LinkedList.Node nodePrev = this.last;
         node.setPrev(nodePrev);
         nodePrev.setNext(node);
         this.last = node;
      }

      node.setParent(this);
      ++this.size;
   }

   public void addAfter(LinkedList.Node nodePrev, LinkedList.Node node) {
      if (nodePrev == null) {
         this.addFirst(node);
      } else if (nodePrev == this.last) {
         this.addLast(node);
      } else {
         this.checkParent(nodePrev);
         this.checkNoParent(node);
         LinkedList.Node nodeNext = nodePrev.getNext();
         nodePrev.setNext(node);
         node.setPrev(nodePrev);
         nodeNext.setPrev(node);
         node.setNext(nodeNext);
         node.setParent(this);
         ++this.size;
      }
   }

   public LinkedList.Node remove(LinkedList.Node node) {
      this.checkParent(node);
      LinkedList.Node prev = node.getPrev();
      LinkedList.Node next = node.getNext();
      if (prev != null) {
         prev.setNext(next);
      } else {
         this.first = next;
      }

      if (next != null) {
         next.setPrev(prev);
      } else {
         this.last = prev;
      }

      node.setPrev((LinkedList.Node)null);
      node.setNext((LinkedList.Node)null);
      node.setParent((LinkedList)null);
      --this.size;
      return node;
   }

   public void moveAfter(LinkedList.Node nodePrev, LinkedList.Node node) {
      this.remove(node);
      this.addAfter(nodePrev, node);
   }

   public boolean find(LinkedList.Node nodeFind, LinkedList.Node nodeFrom, LinkedList.Node nodeTo) {
      this.checkParent(nodeFrom);
      if (nodeTo != null) {
         this.checkParent(nodeTo);
      }

      LinkedList.Node node;
      for(node = nodeFrom; node != null && node != nodeTo; node = node.getNext()) {
         if (node == nodeFind) {
            return true;
         }
      }

      if (node != nodeTo) {
         throw new IllegalArgumentException("Sublist is not linked, from: " + nodeFrom + ", to: " + nodeTo);
      } else {
         return false;
      }
   }

   private void checkParent(LinkedList.Node node) {
      if (node.parent != this) {
         throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
      }
   }

   private void checkNoParent(LinkedList.Node node) {
      if (node.parent != null) {
         throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
      }
   }

   public boolean contains(LinkedList.Node node) {
      return node.parent == this;
   }

   public Iterator iterator() {
      Iterator it = new Iterator() {
         LinkedList.Node node = LinkedList.this.getFirst();

         public boolean hasNext() {
            return this.node != null;
         }

         public LinkedList.Node next() {
            LinkedList.Node ret = this.node;
            if (this.node != null) {
               this.node = this.node.next;
            }

            return ret;
         }
      };
      return it;
   }

   public LinkedList.Node getFirst() {
      return this.first;
   }

   public LinkedList.Node getLast() {
      return this.last;
   }

   public int getSize() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size <= 0;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      LinkedList.Node node;
      for(Iterator it = this.iterator(); it.hasNext(); sb.append(node.getItem())) {
         node = (LinkedList.Node)it.next();
         if (sb.length() > 0) {
            sb.append(", ");
         }
      }

      return "" + this.size + " [" + sb.toString() + "]";
   }

   public static class Node {
      private final Object item;
      private LinkedList.Node prev;
      private LinkedList.Node next;
      private LinkedList parent;

      public Node(Object item) {
         this.item = item;
      }

      public Object getItem() {
         return this.item;
      }

      public LinkedList.Node getPrev() {
         return this.prev;
      }

      public LinkedList.Node getNext() {
         return this.next;
      }

      private void setPrev(LinkedList.Node prev) {
         this.prev = prev;
      }

      private void setNext(LinkedList.Node next) {
         this.next = next;
      }

      private void setParent(LinkedList parent) {
         this.parent = parent;
      }

      public String toString() {
         return "" + this.item;
      }
   }
}
