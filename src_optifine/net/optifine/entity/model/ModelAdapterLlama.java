package net.optifine.entity.model;

public class ModelAdapterLlama extends ModelAdapterQuadruped {
   public ModelAdapterLlama() {
      super(aas.class, "llama", 0.7F);
   }

   public bqf makeModel() {
      return new bqc(0.0F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cab render = new cab(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
