package net.optifine.entity.model;

public class ModelAdapterStray extends ModelAdapterBiped {
   public ModelAdapterStray() {
      super(ado.class, "stray", 0.7F);
   }

   public bqf makeModel() {
      return new bqw();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cay render = new cay(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
