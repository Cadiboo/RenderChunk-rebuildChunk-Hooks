package net.optifine.render;

import net.optifine.util.LinkedList;

public class VboRange {
   private int position = -1;
   private int size = 0;
   private LinkedList.Node node = new LinkedList.Node(this);

   public int getPosition() {
      return this.position;
   }

   public int getSize() {
      return this.size;
   }

   public int getPositionNext() {
      return this.position + this.size;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public LinkedList.Node getNode() {
      return this.node;
   }

   public VboRange getPrev() {
      LinkedList.Node nodePrev = this.node.getPrev();
      return nodePrev == null ? null : (VboRange)nodePrev.getItem();
   }

   public VboRange getNext() {
      LinkedList.Node nodeNext = this.node.getNext();
      return nodeNext == null ? null : (VboRange)nodeNext.getItem();
   }

   public String toString() {
      return "" + this.position + "/" + this.size + "/" + (this.position + this.size);
   }
}
