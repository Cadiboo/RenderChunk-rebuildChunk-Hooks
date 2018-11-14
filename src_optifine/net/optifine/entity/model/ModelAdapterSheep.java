package net.optifine.entity.model;

public class ModelAdapterSheep extends ModelAdapterQuadruped {
   public ModelAdapterSheep() {
      super(aag.class, "sheep", 0.7F);
   }

   public bqf makeModel() {
      return new bqp();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cao render = new cao(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
