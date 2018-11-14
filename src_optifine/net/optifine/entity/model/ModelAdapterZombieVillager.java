package net.optifine.entity.model;

public class ModelAdapterZombieVillager extends ModelAdapterBiped {
   public ModelAdapterZombieVillager() {
      super(adu.class, "zombie_villager", 0.5F);
   }

   public bqf makeModel() {
      return new brh();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbo render = new cbo(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
