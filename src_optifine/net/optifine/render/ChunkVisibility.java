package net.optifine.render;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkVisibility {
   public static final int MASK_FACINGS = 63;
   public static final fa[][] enumFacingArrays = makeEnumFacingArrays(false);
   public static final fa[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
   private static int counter = 0;
   private static int iMaxStatic = -1;
   private static int iMaxStaticFinal = 16;
   private static amu worldLast = null;
   private static int pcxLast = Integer.MIN_VALUE;
   private static int pczLast = Integer.MIN_VALUE;

   public static int getMaxChunkY(amu world, vg viewEntity, int renderDistanceChunks) {
      int pcx = rk.c(viewEntity.p) >> 4;
      int pcz = rk.c(viewEntity.r) >> 4;
      axw playerChunk = world.a(pcx, pcz);
      int cxStart = pcx - renderDistanceChunks;
      int cxEnd = pcx + renderDistanceChunks;
      int czStart = pcz - renderDistanceChunks;
      int czEnd = pcz + renderDistanceChunks;
      if (world != worldLast || pcx != pcxLast || pcz != pczLast) {
         counter = 0;
         iMaxStaticFinal = 16;
         worldLast = world;
         pcxLast = pcx;
         pczLast = pcz;
      }

      if (counter == 0) {
         iMaxStatic = -1;
      }

      int iMax = iMaxStatic;
      switch(counter) {
      case 0:
         cxEnd = pcx;
         czEnd = pcz;
         break;
      case 1:
         cxStart = pcx;
         czEnd = pcz;
         break;
      case 2:
         cxEnd = pcx;
         czStart = pcz;
         break;
      case 3:
         cxStart = pcx;
         czStart = pcz;
      }

      for(int cx = cxStart; cx < cxEnd; ++cx) {
         for(int cz = czStart; cz < czEnd; ++cz) {
            axw chunk = world.a(cx, cz);
            if (!chunk.f()) {
               axx[] ebss = chunk.h();

               for(int i = ebss.length - 1; i > iMax; --i) {
                  axx ebs = ebss[i];
                  if (ebs != null && !ebs.a()) {
                     if (i > iMax) {
                        iMax = i;
                     }
                     break;
                  }
               }

               try {
                  Map mapTileEntities = chunk.s();
                  if (!mapTileEntities.isEmpty()) {
                     Set keys = mapTileEntities.keySet();
                     Iterator it = keys.iterator();

                     while(it.hasNext()) {
                        et pos = (et)it.next();
                        int i = pos.q() >> 4;
                        if (i > iMax) {
                           iMax = i;
                        }
                     }
                  }
               } catch (ConcurrentModificationException var20) {
                  ;
               }

               qx[] cimms = chunk.t();

               for(int i = cimms.length - 1; i > iMax; --i) {
                  qx cimm = cimms[i];
                  if (!cimm.isEmpty() && (chunk != playerChunk || cimm.size() != 1)) {
                     if (i > iMax) {
                        iMax = i;
                     }
                     break;
                  }
               }
            }
         }
      }

      if (counter < 3) {
         iMaxStatic = iMax;
         iMax = iMaxStaticFinal;
      } else {
         iMaxStaticFinal = iMax;
         iMaxStatic = -1;
      }

      counter = (counter + 1) % 4;
      return iMax << 4;
   }

   public static boolean isFinished() {
      return counter == 0;
   }

   private static fa[][] makeEnumFacingArrays(boolean opposite) {
      int count = 64;
      fa[][] arrs = new fa[count][];

      for(int i = 0; i < count; ++i) {
         List list = new ArrayList();

         for(int ix = 0; ix < fa.n.length; ++ix) {
            fa facing = fa.n[ix];
            fa facingMask = opposite ? facing.d() : facing;
            int mask = 1 << facingMask.ordinal();
            if ((i & mask) != 0) {
               list.add(facing);
            }
         }

         fa[] fs = (fa[])list.toArray(new fa[list.size()]);
         arrs[i] = fs;
      }

      return arrs;
   }

   public static fa[] getFacingsNotOpposite(int setDisabled) {
      int index = ~setDisabled & 63;
      return enumFacingOppositeArrays[index];
   }
}
