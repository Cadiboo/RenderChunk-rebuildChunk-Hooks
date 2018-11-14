package net.optifine;

import java.util.UUID;

public class RandomEntity implements IRandomEntity {
   private vg entity;

   public int getId() {
      UUID uuid = this.entity.bm();
      long uuidLow = uuid.getLeastSignificantBits();
      int id = (int)(uuidLow & 2147483647L);
      return id;
   }

   public et getSpawnPosition() {
      return this.entity.V().spawnPosition;
   }

   public anh getSpawnBiome() {
      return this.entity.V().spawnBiome;
   }

   public String getName() {
      return this.entity.n_() ? this.entity.bq() : null;
   }

   public vg getEntity() {
      return this.entity;
   }

   public void setEntity(vg entity) {
      this.entity = entity;
   }
}
