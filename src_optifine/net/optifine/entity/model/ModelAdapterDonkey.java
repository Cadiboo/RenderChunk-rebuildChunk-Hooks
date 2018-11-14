package net.optifine.entity.model;

public class ModelAdapterDonkey extends ModelAdapterHorse {
   public ModelAdapterDonkey() {
      super(aap.class, "donkey", 0.75F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byn render = new byn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
