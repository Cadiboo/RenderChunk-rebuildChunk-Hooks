package net.optifine.entity.model;

public class ModelAdapterVillager extends ModelAdapter {
   public ModelAdapterVillager() {
      super(ady.class, "villager", 0.5F);
   }

   public bqf makeModel() {
      return new brg(0.0F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brg)) {
         return null;
      } else {
         brg modelVillager = (brg)model;
         if (modelPart.equals("head")) {
            return modelVillager.a;
         } else if (modelPart.equals("body")) {
            return modelVillager.b;
         } else if (modelPart.equals("arms")) {
            return modelVillager.c;
         } else if (modelPart.equals("left_leg")) {
            return modelVillager.e;
         } else if (modelPart.equals("right_leg")) {
            return modelVillager.d;
         } else {
            return modelPart.equals("nose") ? modelVillager.f : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbg render = new cbg(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
