package net.optifine.entity.model;

public class ModelAdapterVindicator extends ModelAdapterIllager {
   public ModelAdapterVindicator() {
      super(adq.class, "vindication_illager", 0.5F);
   }

   public bqf makeModel() {
      return new bpy(0.0F, 0.0F, 64, 64);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbh render = new cbh(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
