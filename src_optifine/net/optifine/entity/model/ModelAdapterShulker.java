package net.optifine.entity.model;

public class ModelAdapterShulker extends ModelAdapter {
   public ModelAdapterShulker() {
      super(adi.class, "shulker", 0.0F);
   }

   public bqf makeModel() {
      return new bqs();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqs)) {
         return null;
      } else {
         bqs modelShulker = (bqs)model;
         if (modelPart.equals("head")) {
            return modelShulker.c;
         } else if (modelPart.equals("base")) {
            return modelShulker.a;
         } else {
            return modelPart.equals("lid") ? modelShulker.b : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      caq render = new caq(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
