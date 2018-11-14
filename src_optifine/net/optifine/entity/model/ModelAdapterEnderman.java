package net.optifine.entity.model;

public class ModelAdapterEnderman extends ModelAdapterBiped {
   public ModelAdapterEnderman() {
      super(acu.class, "enderman", 0.5F);
   }

   public bqf makeModel() {
      return new bpq(0.0F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzd render = new bzd(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
