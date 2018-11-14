package net.optifine.entity.model;

public class ModelAdapterHusk extends ModelAdapterBiped {
   public ModelAdapterHusk() {
      super(adb.class, "husk", 0.5F);
   }

   public bqf makeModel() {
      return new brl();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzs render = new bzs(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
