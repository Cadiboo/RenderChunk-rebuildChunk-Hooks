package net.optifine.shaders;

import java.util.Iterator;
import net.optifine.BlockPosM;

public class Iterator3d implements Iterator {
   private IteratorAxis iteratorAxis;
   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
   private int axis = 0;
   private int kX;
   private int kY;
   private int kZ;
   private static final int AXIS_X = 0;
   private static final int AXIS_Y = 1;
   private static final int AXIS_Z = 2;

   public Iterator3d(et posStart, et posEnd, int width, int height) {
      boolean revX = posStart.p() > posEnd.p();
      boolean revY = posStart.q() > posEnd.q();
      boolean revZ = posStart.r() > posEnd.r();
      posStart = this.reverseCoord(posStart, revX, revY, revZ);
      posEnd = this.reverseCoord(posEnd, revX, revY, revZ);
      this.kX = revX ? -1 : 1;
      this.kY = revY ? -1 : 1;
      this.kZ = revZ ? -1 : 1;
      bhe vec = new bhe((double)(posEnd.p() - posStart.p()), (double)(posEnd.q() - posStart.q()), (double)(posEnd.r() - posStart.r()));
      bhe vecN = vec.a();
      bhe vecX = new bhe(1.0D, 0.0D, 0.0D);
      double dotX = vecN.b(vecX);
      double dotXabs = Math.abs(dotX);
      bhe vecY = new bhe(0.0D, 1.0D, 0.0D);
      double dotY = vecN.b(vecY);
      double dotYabs = Math.abs(dotY);
      bhe vecZ = new bhe(0.0D, 0.0D, 1.0D);
      double dotZ = vecN.b(vecZ);
      double dotZabs = Math.abs(dotZ);
      et pos1;
      et pos2;
      int countX;
      double deltaY;
      double deltaZ;
      if (dotZabs >= dotYabs && dotZabs >= dotXabs) {
         this.axis = 2;
         pos1 = new et(posStart.r(), posStart.q() - width, posStart.p() - height);
         pos2 = new et(posEnd.r(), posStart.q() + width + 1, posStart.p() + height + 1);
         countX = posEnd.r() - posStart.r();
         deltaY = (double)(posEnd.q() - posStart.q()) / (1.0D * (double)countX);
         deltaZ = (double)(posEnd.p() - posStart.p()) / (1.0D * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else if (dotYabs >= dotXabs && dotYabs >= dotZabs) {
         this.axis = 1;
         pos1 = new et(posStart.q(), posStart.p() - width, posStart.r() - height);
         pos2 = new et(posEnd.q(), posStart.p() + width + 1, posStart.r() + height + 1);
         countX = posEnd.q() - posStart.q();
         deltaY = (double)(posEnd.p() - posStart.p()) / (1.0D * (double)countX);
         deltaZ = (double)(posEnd.r() - posStart.r()) / (1.0D * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      } else {
         this.axis = 0;
         pos1 = new et(posStart.p(), posStart.q() - width, posStart.r() - height);
         pos2 = new et(posEnd.p(), posStart.q() + width + 1, posStart.r() + height + 1);
         countX = posEnd.p() - posStart.p();
         deltaY = (double)(posEnd.q() - posStart.q()) / (1.0D * (double)countX);
         deltaZ = (double)(posEnd.r() - posStart.r()) / (1.0D * (double)countX);
         this.iteratorAxis = new IteratorAxis(pos1, pos2, deltaY, deltaZ);
      }

   }

   private et reverseCoord(et pos, boolean revX, boolean revY, boolean revZ) {
      if (revX) {
         pos = new et(-pos.p(), pos.q(), pos.r());
      }

      if (revY) {
         pos = new et(pos.p(), -pos.q(), pos.r());
      }

      if (revZ) {
         pos = new et(pos.p(), pos.q(), -pos.r());
      }

      return pos;
   }

   public boolean hasNext() {
      return this.iteratorAxis.hasNext();
   }

   public et next() {
      et pos = this.iteratorAxis.next();
      switch(this.axis) {
      case 0:
         this.blockPos.setXyz(pos.p() * this.kX, pos.q() * this.kY, pos.r() * this.kZ);
         return this.blockPos;
      case 1:
         this.blockPos.setXyz(pos.q() * this.kX, pos.p() * this.kY, pos.r() * this.kZ);
         return this.blockPos;
      case 2:
         this.blockPos.setXyz(pos.r() * this.kX, pos.q() * this.kY, pos.p() * this.kZ);
         return this.blockPos;
      default:
         this.blockPos.setXyz(pos.p() * this.kX, pos.q() * this.kY, pos.r() * this.kZ);
         return this.blockPos;
      }
   }

   public void remove() {
      throw new RuntimeException("Not supported");
   }

   public static void main(String[] args) {
      et posStart = new et(10, 20, 30);
      et posEnd = new et(30, 40, 20);
      Iterator3d it = new Iterator3d(posStart, posEnd, 1, 1);

      while(it.hasNext()) {
         et blockPos = it.next();
         System.out.println("" + blockPos);
      }

   }
}
