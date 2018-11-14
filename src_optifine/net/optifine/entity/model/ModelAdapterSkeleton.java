package net.optifine.entity.model;

public class ModelAdapterSkeleton extends ModelAdapterBiped {
   public ModelAdapterSkeleton() {
      super(adk.class, "skeleton", 0.7F);
   }

   public bqf makeModel() {
      return new bqw();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cas render = new cas(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
