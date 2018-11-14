package net.optifine.entity.model;

public class ModelAdapterZombieHorse extends ModelAdapterHorse {
   public ModelAdapterZombieHorse() {
      super(aau.class, "skeleton_horse", 0.75F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byn render = new byn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
