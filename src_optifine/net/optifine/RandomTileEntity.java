package net.optifine;

import net.optifine.util.TileEntityUtils;

public class RandomTileEntity implements IRandomEntity {
   private avj tileEntity;

   public int getId() {
      return .Config.getRandom(this.tileEntity.w(), this.tileEntity.v());
   }

   public et getSpawnPosition() {
      return this.tileEntity.w();
   }

   public String getName() {
      String name = TileEntityUtils.getTileEntityName(this.tileEntity);
      return name;
   }

   public anh getSpawnBiome() {
      return this.tileEntity.D().b(this.tileEntity.w());
   }

   public avj getTileEntity() {
      return this.tileEntity;
   }

   public void setTileEntity(avj tileEntity) {
      this.tileEntity = tileEntity;
   }
}
