package net.optifine.entity.model;

public class ModelAdapterPig extends ModelAdapterQuadruped {
   public ModelAdapterPig() {
      super(aad.class, "pig", 0.7F);
   }

   public bqf makeModel() {
      return new bqi();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cak render = new cak(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
