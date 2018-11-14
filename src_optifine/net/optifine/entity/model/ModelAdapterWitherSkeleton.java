package net.optifine.entity.model;

public class ModelAdapterWitherSkeleton extends ModelAdapterBiped {
   public ModelAdapterWitherSkeleton() {
      super(ads.class, "wither_skeleton", 0.7F);
   }

   public bqf makeModel() {
      return new bqw();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbk render = new cbk(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
