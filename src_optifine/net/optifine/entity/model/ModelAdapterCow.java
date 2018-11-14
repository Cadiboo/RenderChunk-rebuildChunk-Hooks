package net.optifine.entity.model;

public class ModelAdapterCow extends ModelAdapterQuadruped {
   public ModelAdapterCow() {
      super(zx.class, "cow", 0.7F);
   }

   public bqf makeModel() {
      return new bpn();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byw render = new byw(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
