package net.optifine.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.optifine.util.LinkedList;

public class VboRegion {
   private amm layer = null;
   private int glBufferId = cii.e();
   private int capacity = 4096;
   private int positionTop = 0;
   private int sizeUsed;
   private LinkedList rangeList = new LinkedList();
   private VboRange compactRangeLast = null;
   private IntBuffer bufferIndexVertex;
   private IntBuffer bufferCountVertex;
   private final int vertexBytes;

   public VboRegion(amm layer) {
      this.bufferIndexVertex = bia.f(this.capacity);
      this.bufferCountVertex = bia.f(this.capacity);
      this.vertexBytes = cdy.a.g();
      this.layer = layer;
      this.bindBuffer();
      long capacityBytes = this.toBytes(this.capacity);
      cii.glBufferData(cii.R, capacityBytes, cii.S);
      this.unbindBuffer();
   }

   public void bufferData(ByteBuffer data, VboRange range) {
      int posOld = range.getPosition();
      int sizeOld = range.getSize();
      int sizeNew = this.toVertex((long)data.limit());
      if (sizeNew <= 0) {
         if (posOld >= 0) {
            range.setPosition(-1);
            range.setSize(0);
            this.rangeList.remove(range.getNode());
            this.sizeUsed -= sizeOld;
         }

      } else {
         if (sizeNew > sizeOld) {
            range.setPosition(this.positionTop);
            range.setSize(sizeNew);
            this.positionTop += sizeNew;
            if (posOld >= 0) {
               this.rangeList.remove(range.getNode());
            }

            this.rangeList.addLast(range.getNode());
         }

         range.setSize(sizeNew);
         this.sizeUsed += sizeNew - sizeOld;
         this.checkVboSize(range.getPositionNext());
         long positionBytes = this.toBytes(range.getPosition());
         this.bindBuffer();
         cii.glBufferSubData(cii.R, positionBytes, data);
         this.unbindBuffer();
         if (this.positionTop > this.sizeUsed * 11 / 10) {
            this.compactRanges(1);
         }

      }
   }

   private void compactRanges(int countMax) {
      if (!this.rangeList.isEmpty()) {
         VboRange range = this.compactRangeLast;
         if (range == null || !this.rangeList.contains(range.getNode())) {
            range = (VboRange)this.rangeList.getFirst().getItem();
         }

         int posCompact = range.getPosition();
         VboRange rangePrev = range.getPrev();
         if (rangePrev == null) {
            posCompact = 0;
         } else {
            posCompact = rangePrev.getPositionNext();
         }

         int count = 0;

         while(range != null && count < countMax) {
            ++count;
            if (range.getPosition() == posCompact) {
               posCompact += range.getSize();
               range = range.getNext();
            } else {
               int sizeFree = range.getPosition() - posCompact;
               if (range.getSize() <= sizeFree) {
                  this.copyVboData(range.getPosition(), posCompact, range.getSize());
                  range.setPosition(posCompact);
                  posCompact += range.getSize();
                  range = range.getNext();
               } else {
                  this.checkVboSize(this.positionTop + range.getSize());
                  this.copyVboData(range.getPosition(), this.positionTop, range.getSize());
                  range.setPosition(this.positionTop);
                  this.positionTop += range.getSize();
                  VboRange rangeNext = range.getNext();
                  this.rangeList.remove(range.getNode());
                  this.rangeList.addLast(range.getNode());
                  range = rangeNext;
               }
            }
         }

         if (range == null) {
            this.positionTop = ((VboRange)this.rangeList.getLast().getItem()).getPositionNext();
         }

         this.compactRangeLast = range;
      }
   }

   private void checkRanges() {
      int count = 0;
      int size = 0;

      for(VboRange range = (VboRange)this.rangeList.getFirst().getItem(); range != null; range = range.getNext()) {
         ++count;
         size += range.getSize();
         if (range.getPosition() < 0 || range.getSize() <= 0 || range.getPositionNext() > this.positionTop) {
            throw new RuntimeException("Invalid range: " + range);
         }

         VboRange rangePrev = range.getPrev();
         if (rangePrev != null && range.getPosition() < rangePrev.getPositionNext()) {
            throw new RuntimeException("Invalid range: " + range);
         }

         VboRange rangeNext = range.getNext();
         if (rangeNext != null && range.getPositionNext() > rangeNext.getPosition()) {
            throw new RuntimeException("Invalid range: " + range);
         }
      }

      if (count != this.rangeList.getSize()) {
         throw new RuntimeException("Invalid count: " + count + " <> " + this.rangeList.getSize());
      } else if (size != this.sizeUsed) {
         throw new RuntimeException("Invalid size: " + size + " <> " + this.sizeUsed);
      }
   }

   private void checkVboSize(int sizeMin) {
      if (this.capacity < sizeMin) {
         this.expandVbo(sizeMin);
      }

   }

   private void copyVboData(int posFrom, int posTo, int size) {
      long posFromBytes = this.toBytes(posFrom);
      long posToBytes = this.toBytes(posTo);
      long sizeBytes = this.toBytes(size);
      cii.g(cii.GL_COPY_READ_BUFFER, this.glBufferId);
      cii.g(cii.GL_COPY_WRITE_BUFFER, this.glBufferId);
      cii.glCopyBufferSubData(cii.GL_COPY_READ_BUFFER, cii.GL_COPY_WRITE_BUFFER, posFromBytes, posToBytes, sizeBytes);
      .Config.checkGlError("Copy VBO range");
      cii.g(cii.GL_COPY_READ_BUFFER, 0);
      cii.g(cii.GL_COPY_WRITE_BUFFER, 0);
   }

   private void expandVbo(int sizeMin) {
      int capacityNew;
      for(capacityNew = this.capacity * 6 / 4; capacityNew < sizeMin; capacityNew = capacityNew * 6 / 4) {
         ;
      }

      long capacityBytes = this.toBytes(this.capacity);
      long capacityNewBytes = this.toBytes(capacityNew);
      int glBufferIdNew = cii.e();
      cii.g(cii.R, glBufferIdNew);
      cii.glBufferData(cii.R, capacityNewBytes, cii.S);
      .Config.checkGlError("Expand VBO");
      cii.g(cii.R, 0);
      cii.g(cii.GL_COPY_READ_BUFFER, this.glBufferId);
      cii.g(cii.GL_COPY_WRITE_BUFFER, glBufferIdNew);
      cii.glCopyBufferSubData(cii.GL_COPY_READ_BUFFER, cii.GL_COPY_WRITE_BUFFER, 0L, 0L, capacityBytes);
      .Config.checkGlError("Copy VBO: " + capacityNewBytes);
      cii.g(cii.GL_COPY_READ_BUFFER, 0);
      cii.g(cii.GL_COPY_WRITE_BUFFER, 0);
      cii.g(this.glBufferId);
      this.bufferIndexVertex = bia.f(capacityNew);
      this.bufferCountVertex = bia.f(capacityNew);
      this.glBufferId = glBufferIdNew;
      this.capacity = capacityNew;
   }

   public void bindBuffer() {
      cii.g(cii.R, this.glBufferId);
   }

   public void drawArrays(VboRange range) {
      this.bufferIndexVertex.put(range.getPosition());
      this.bufferCountVertex.put(range.getSize());
   }

   public void finishDraw(bvf vboRenderList) {
      this.bindBuffer();
      vboRenderList.a();
      this.bufferIndexVertex.flip();
      this.bufferCountVertex.flip();
      bus.glMultiDrawArrays(7, this.bufferIndexVertex, this.bufferCountVertex);
      this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
      this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
      if (this.positionTop > this.sizeUsed * 11 / 10) {
         this.compactRanges(1);
      }

   }

   public void unbindBuffer() {
      cii.g(cii.R, 0);
   }

   public void deleteGlBuffers() {
      if (this.glBufferId >= 0) {
         cii.g(this.glBufferId);
         this.glBufferId = -1;
      }

   }

   private long toBytes(int vertex) {
      return (long)vertex * (long)this.vertexBytes;
   }

   private int toVertex(long bytes) {
      return (int)(bytes / (long)this.vertexBytes);
   }

   public int getPositionTop() {
      return this.positionTop;
   }
}
