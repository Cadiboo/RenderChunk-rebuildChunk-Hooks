package net.optifine.entity.model;

public class ModelAdapterEvoker extends ModelAdapterIllager {
   public ModelAdapterEvoker() {
      super(acx.class, "evocation_illager", 0.5F);
   }

   public bqf makeModel() {
      return new bpy(0.0F, 0.0F, 64, 64);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzi render = new bzi(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
