package net.optifine.entity.model;

public class ModelAdapterIronGolem extends ModelAdapter {
   public ModelAdapterIronGolem() {
      super(aak.class, "iron_golem", 0.5F);
   }

   public bqf makeModel() {
      return new brf();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brf)) {
         return null;
      } else {
         brf modelIronGolem = (brf)model;
         if (modelPart.equals("head")) {
            return modelIronGolem.a;
         } else if (modelPart.equals("body")) {
            return modelIronGolem.b;
         } else if (modelPart.equals("left_arm")) {
            return modelIronGolem.d;
         } else if (modelPart.equals("right_arm")) {
            return modelIronGolem.c;
         } else if (modelPart.equals("left_leg")) {
            return modelIronGolem.e;
         } else {
            return modelPart.equals("right_leg") ? modelIronGolem.f : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbf render = new cbf(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
