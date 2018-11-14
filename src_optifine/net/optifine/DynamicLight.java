package net.optifine;

import et.a;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DynamicLight {
   private vg entity = null;
   private double offsetY = 0.0D;
   private double lastPosX = -2.147483648E9D;
   private double lastPosY = -2.147483648E9D;
   private double lastPosZ = -2.147483648E9D;
   private int lastLightLevel = 0;
   private boolean underwater = false;
   private long timeCheckMs = 0L;
   private Set setLitChunkPos = new HashSet();
   private a blockPosMutable = new a();

   public DynamicLight(vg entity) {
      this.entity = entity;
      this.offsetY = (double)entity.by();
   }

   public void update(buy renderGlobal) {
      if (.Config.isDynamicLightsFast()) {
         long timeNowMs = System.currentTimeMillis();
         if (timeNowMs < this.timeCheckMs + 500L) {
            return;
         }

         this.timeCheckMs = timeNowMs;
      }

      double posX = this.entity.p - 0.5D;
      double posY = this.entity.q - 0.5D + this.offsetY;
      double posZ = this.entity.r - 0.5D;
      int lightLevel = DynamicLights.getLightLevel(this.entity);
      double dx = posX - this.lastPosX;
      double dy = posY - this.lastPosY;
      double dz = posZ - this.lastPosZ;
      double delta = 0.1D;
      if (Math.abs(dx) > delta || Math.abs(dy) > delta || Math.abs(dz) > delta || this.lastLightLevel != lightLevel) {
         this.lastPosX = posX;
         this.lastPosY = posY;
         this.lastPosZ = posZ;
         this.lastLightLevel = lightLevel;
         this.underwater = false;
         amu world = renderGlobal.getWorld();
         if (world != null) {
            this.blockPosMutable.c(rk.c(posX), rk.c(posY), rk.c(posZ));
            awt state = world.o(this.blockPosMutable);
            aow block = state.u();
            this.underwater = block == aox.j;
         }

         Set setNewPos = new HashSet();
         if (lightLevel > 0) {
            fa dirX = (rk.c(posX) & 15) >= 8 ? fa.f : fa.e;
            fa dirY = (rk.c(posY) & 15) >= 8 ? fa.b : fa.a;
            fa dirZ = (rk.c(posZ) & 15) >= 8 ? fa.d : fa.c;
            et chunkPos = new et(posX, posY, posZ);
            bxr chunk = renderGlobal.getRenderChunk(chunkPos);
            et chunkPosX = this.getChunkPos(chunk, chunkPos, dirX);
            bxr chunkX = renderGlobal.getRenderChunk(chunkPosX);
            et chunkPosZ = this.getChunkPos(chunk, chunkPos, dirZ);
            bxr chunkZ = renderGlobal.getRenderChunk(chunkPosZ);
            et chunkPosXZ = this.getChunkPos(chunkX, chunkPosX, dirZ);
            bxr chunkXZ = renderGlobal.getRenderChunk(chunkPosXZ);
            et chunkPosY = this.getChunkPos(chunk, chunkPos, dirY);
            bxr chunkY = renderGlobal.getRenderChunk(chunkPosY);
            et chunkPosYX = this.getChunkPos(chunkY, chunkPosY, dirX);
            bxr chunkYX = renderGlobal.getRenderChunk(chunkPosYX);
            et chunkPosYZ = this.getChunkPos(chunkY, chunkPosY, dirZ);
            bxr chunkYZ = renderGlobal.getRenderChunk(chunkPosYZ);
            et chunkPosYXZ = this.getChunkPos(chunkYX, chunkPosYX, dirZ);
            bxr chunkYXZ = renderGlobal.getRenderChunk(chunkPosYXZ);
            this.updateChunkLight(chunk, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkXZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkY, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYX, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYZ, this.setLitChunkPos, setNewPos);
            this.updateChunkLight(chunkYXZ, this.setLitChunkPos, setNewPos);
         }

         this.updateLitChunks(renderGlobal);
         this.setLitChunkPos = setNewPos;
      }
   }

   private et getChunkPos(bxr renderChunk, et pos, fa facing) {
      return renderChunk != null ? renderChunk.a(facing) : pos.a(facing, 16);
   }

   private void updateChunkLight(bxr renderChunk, Set setPrevPos, Set setNewPos) {
      if (renderChunk != null) {
         bxo compiledChunk = renderChunk.h();
         if (compiledChunk != null && !compiledChunk.a()) {
            renderChunk.a(false);
         }

         et pos = renderChunk.k().h();
         if (setPrevPos != null) {
            setPrevPos.remove(pos);
         }

         if (setNewPos != null) {
            setNewPos.add(pos);
         }

      }
   }

   public void updateLitChunks(buy renderGlobal) {
      Iterator it = this.setLitChunkPos.iterator();

      while(it.hasNext()) {
         et posOld = (et)it.next();
         bxr chunkOld = renderGlobal.getRenderChunk(posOld);
         this.updateChunkLight(chunkOld, (Set)null, (Set)null);
      }

   }

   public vg getEntity() {
      return this.entity;
   }

   public double getLastPosX() {
      return this.lastPosX;
   }

   public double getLastPosY() {
      return this.lastPosY;
   }

   public double getLastPosZ() {
      return this.lastPosZ;
   }

   public int getLastLightLevel() {
      return this.lastLightLevel;
   }

   public boolean isUnderwater() {
      return this.underwater;
   }

   public double getOffsetY() {
      return this.offsetY;
   }

   public String toString() {
      return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
   }
}
