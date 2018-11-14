package net.optifine.shaders;

import java.util.Iterator;
import net.optifine.BlockPosM;

public class IteratorRenderChunks implements Iterator {
   private bvh viewFrustum;
   private Iterator3d Iterator3d;
   private BlockPosM posBlock = new BlockPosM(0, 0, 0);

   public IteratorRenderChunks(bvh viewFrustum, et posStart, et posEnd, int width, int height) {
      this.viewFrustum = viewFrustum;
      this.Iterator3d = new Iterator3d(posStart, posEnd, width, height);
   }

   public boolean hasNext() {
      return this.Iterator3d.hasNext();
   }

   public bxr next() {
      et pos = this.Iterator3d.next();
      this.posBlock.setXyz(pos.p() << 4, pos.q() << 4, pos.r() << 4);
      bxr rc = this.viewFrustum.a(this.posBlock);
      return rc;
   }

   public void remove() {
      throw new RuntimeException("Not implemented");
   }
}
