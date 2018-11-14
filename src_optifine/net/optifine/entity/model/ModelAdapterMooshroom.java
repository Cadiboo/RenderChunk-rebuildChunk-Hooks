package net.optifine.entity.model;

public class ModelAdapterMooshroom extends ModelAdapterQuadruped {
   public ModelAdapterMooshroom() {
      super(aaa.class, "mooshroom", 0.7F);
   }

   public bqf makeModel() {
      return new bpn();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cag render = new cag(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
