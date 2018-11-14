package net.optifine.util;

public class RenderChunkUtils {
   public static int getCountBlocks(bxr renderChunk) {
      axx[] ebss = renderChunk.getChunk().h();
      if (ebss == null) {
         return 0;
      } else {
         int indexEbs = renderChunk.k().q() >> 4;
         axx ebs = ebss[indexEbs];
         return ebs == null ? 0 : ebs.getBlockRefCount();
      }
   }

   public static double getRelativeBufferSize(bxr renderChunk) {
      int blockCount = getCountBlocks(renderChunk);
      double vertexCountRel = getRelativeBufferSize(blockCount);
      return vertexCountRel;
   }

   public static double getRelativeBufferSize(int blockCount) {
      double countRel = (double)blockCount / 4096.0D;
      countRel *= 0.995D;
      double weight = countRel * 2.0D - 1.0D;
      weight = rk.a(weight, -1.0D, 1.0D);
      return (double)rk.a(1.0D - weight * weight);
   }
}
