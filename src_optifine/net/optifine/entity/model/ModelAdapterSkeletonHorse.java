package net.optifine.entity.model;

public class ModelAdapterSkeletonHorse extends ModelAdapterHorse {
   public ModelAdapterSkeletonHorse() {
      super(aaw.class, "zombie_horse", 0.75F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byn render = new byn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
