package net.optifine.entity.model;

public class ModelAdapterCaveSpider extends ModelAdapterSpider {
   public ModelAdapterCaveSpider() {
      super(acr.class, "cave_spider", 0.7F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byu render = new byu(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
