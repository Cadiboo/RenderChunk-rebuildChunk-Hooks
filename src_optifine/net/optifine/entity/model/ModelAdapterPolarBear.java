package net.optifine.entity.model;

public class ModelAdapterPolarBear extends ModelAdapterQuadruped {
   public ModelAdapterPolarBear() {
      super(aae.class, "polar_bear", 0.7F);
   }

   public bqf makeModel() {
      return new bqk();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cam render = new cam(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
