package net.optifine.override;

import axw.a;
import java.util.Arrays;
import net.optifine.DynamicLights;
import net.optifine.reflect.Reflector;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements amy {
   private final and chunkCache;
   private final int posX;
   private final int posY;
   private final int posZ;
   private final int sizeX;
   private final int sizeY;
   private final int sizeZ;
   private final int sizeXY;
   private int[] combinedLights;
   private awt[] blockStates;
   private final int arraySize;
   private final boolean dynamicLights = .Config.isDynamicLights();
   private static final ArrayCache cacheCombinedLights;
   private static final ArrayCache cacheBlockStates;

   public ChunkCacheOF(and chunkCache, et posFromIn, et posToIn, int subIn) {
      this.chunkCache = chunkCache;
      int minChunkX = posFromIn.p() - subIn >> 4;
      int minChunkY = posFromIn.q() - subIn >> 4;
      int minChunkZ = posFromIn.r() - subIn >> 4;
      int maxChunkX = posToIn.p() + subIn >> 4;
      int maxChunkY = posToIn.q() + subIn >> 4;
      int maxChunkZ = posToIn.r() + subIn >> 4;
      this.sizeX = maxChunkX - minChunkX + 1 << 4;
      this.sizeY = maxChunkY - minChunkY + 1 << 4;
      this.sizeZ = maxChunkZ - minChunkZ + 1 << 4;
      this.sizeXY = this.sizeX * this.sizeY;
      this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
      this.posX = minChunkX << 4;
      this.posY = minChunkY << 4;
      this.posZ = minChunkZ << 4;
   }

   private int getPositionIndex(et pos) {
      int dx = pos.p() - this.posX;
      if (dx >= 0 && dx < this.sizeX) {
         int dy = pos.q() - this.posY;
         if (dy >= 0 && dy < this.sizeY) {
            int dz = pos.r() - this.posZ;
            return dz >= 0 && dz < this.sizeZ ? dz * this.sizeXY + dy * this.sizeX + dx : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public int b(et pos, int lightValue) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.combinedLights != null) {
         int light = this.combinedLights[index];
         if (light == -1) {
            light = this.getCombinedLightRaw(pos, lightValue);
            this.combinedLights[index] = light;
         }

         return light;
      } else {
         return this.getCombinedLightRaw(pos, lightValue);
      }
   }

   private int getCombinedLightRaw(et pos, int lightValue) {
      int light = this.chunkCache.b(pos, lightValue);
      if (this.dynamicLights && !this.o(pos).p()) {
         light = DynamicLights.getCombinedLight(pos, light);
      }

      return light;
   }

   public awt o(et pos) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.blockStates != null) {
         awt iblockstate = this.blockStates[index];
         if (iblockstate == null) {
            iblockstate = this.chunkCache.o(pos);
            this.blockStates[index] = iblockstate;
         }

         return iblockstate;
      } else {
         return this.chunkCache.o(pos);
      }
   }

   public void renderStart() {
      if (this.combinedLights == null) {
         this.combinedLights = (int[])((int[])cacheCombinedLights.allocate(this.arraySize));
      }

      Arrays.fill(this.combinedLights, -1);
      if (this.blockStates == null) {
         this.blockStates = (awt[])((awt[])cacheBlockStates.allocate(this.arraySize));
      }

      Arrays.fill(this.blockStates, (Object)null);
   }

   public void renderFinish() {
      cacheCombinedLights.free(this.combinedLights);
      this.combinedLights = null;
      cacheBlockStates.free(this.blockStates);
      this.blockStates = null;
   }

   public boolean isEmpty() {
      return this.chunkCache.ac();
   }

   public anh b(et pos) {
      return this.chunkCache.b(pos);
   }

   public int a(et pos, fa direction) {
      return this.chunkCache.a(pos, direction);
   }

   public avj r(et pos) {
      return this.chunkCache.a(pos, a.c);
   }

   public avj getTileEntity(et pos, a type) {
      return this.chunkCache.a(pos, type);
   }

   public amz N() {
      return this.chunkCache.N();
   }

   public boolean d(et pos) {
      return this.chunkCache.d(pos);
   }

   public boolean isSideSolid(et pos, fa side, boolean _default) {
      return Reflector.callBoolean(this.chunkCache, Reflector.ForgeChunkCache_isSideSolid, pos, side, _default);
   }

   static {
      cacheCombinedLights = new ArrayCache(Integer.TYPE, 16);
      cacheBlockStates = new ArrayCache(awt.class, 16);
   }
}
