package net.optifine.entity.model;

public class ModelAdapterMule extends ModelAdapterHorse {
   public ModelAdapterMule() {
      super(aat.class, "mule", 0.75F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byn render = new byn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
