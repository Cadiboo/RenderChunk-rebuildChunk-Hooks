package net.optifine.entity.model;

public class ModelAdapterChicken extends ModelAdapter {
   public ModelAdapterChicken() {
      super(zw.class, "chicken", 0.3F);
   }

   public bqf makeModel() {
      return new bpm();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpm)) {
         return null;
      } else {
         bpm modelChicken = (bpm)model;
         if (modelPart.equals("head")) {
            return modelChicken.a;
         } else if (modelPart.equals("body")) {
            return modelChicken.b;
         } else if (modelPart.equals("right_leg")) {
            return modelChicken.c;
         } else if (modelPart.equals("left_leg")) {
            return modelChicken.d;
         } else if (modelPart.equals("right_wing")) {
            return modelChicken.e;
         } else if (modelPart.equals("left_wing")) {
            return modelChicken.f;
         } else if (modelPart.equals("bill")) {
            return modelChicken.g;
         } else {
            return modelPart.equals("chin") ? modelChicken.h : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byv render = new byv(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
