package net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator {
   private double yDelta;
   private double zDelta;
   private int xStart;
   private int xEnd;
   private double yStart;
   private double yEnd;
   private double zStart;
   private double zEnd;
   private int xNext;
   private double yNext;
   private double zNext;
   private BlockPosM pos = new BlockPosM(0, 0, 0);
   private boolean hasNext = false;

   public IteratorAxis(et posStart, et posEnd, double yDelta, double zDelta) {
      this.yDelta = yDelta;
      this.zDelta = zDelta;
      this.xStart = posStart.p();
      this.xEnd = posEnd.p();
      this.yStart = (double)posStart.q();
      this.yEnd = (double)posEnd.q() - 0.5D;
      this.zStart = (double)posStart.r();
      this.zEnd = (double)posEnd.r() - 0.5D;
      this.xNext = this.xStart;
      this.yNext = this.yStart;
      this.zNext = this.zStart;
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
   }

   public boolean hasNext() {
      return this.hasNext;
   }

   public et next() {
      if (!this.hasNext) {
         throw new NoSuchElementException();
      } else {
         this.pos.setXyz((double)this.xNext, this.yNext, this.zNext);
         this.nextPos();
         this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
         return this.pos;
      }
   }

   private void nextPos() {
      ++this.zNext;
      if (this.zNext >= this.zEnd) {
         this.zNext = this.zStart;
         ++this.yNext;
         if (this.yNext >= this.yEnd) {
            this.yNext = this.yStart;
            this.yStart += this.yDelta;
            this.yEnd += this.yDelta;
            this.yNext = this.yStart;
            this.zStart += this.zDelta;
            this.zEnd += this.zDelta;
            this.zNext = this.zStart;
            ++this.xNext;
            if (this.xNext >= this.xEnd) {
               ;
            }
         }
      }
   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }

   public static void main(String[] args) throws Exception {
      et posStart = new et(-2, 10, 20);
      et posEnd = new et(2, 12, 22);
      double yDelta = -0.5D;
      double zDelta = 0.5D;
      IteratorAxis it = new IteratorAxis(posStart, posEnd, yDelta, zDelta);
      System.out.println("Start: " + posStart + ", end: " + posEnd + ", yDelta: " + yDelta + ", zDelta: " + zDelta);

      while(it.hasNext()) {
         et blockPos = it.next();
         System.out.println("" + blockPos);
      }

   }
}
