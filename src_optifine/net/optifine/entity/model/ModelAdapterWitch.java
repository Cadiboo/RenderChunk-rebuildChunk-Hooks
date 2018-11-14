package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterWitch extends ModelAdapter {
   public ModelAdapterWitch() {
      super(adr.class, "witch", 0.5F);
   }

   public bqf makeModel() {
      return new bri(0.0F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bri)) {
         return null;
      } else {
         bri modelWitch = (bri)model;
         if (modelPart.equals("mole")) {
            return (brs)Reflector.getFieldValue(modelWitch, Reflector.ModelWitch_mole);
         } else if (modelPart.equals("hat")) {
            return (brs)Reflector.getFieldValue(modelWitch, Reflector.ModelWitch_hat);
         } else if (modelPart.equals("head")) {
            return modelWitch.a;
         } else if (modelPart.equals("body")) {
            return modelWitch.b;
         } else if (modelPart.equals("arms")) {
            return modelWitch.c;
         } else if (modelPart.equals("left_leg")) {
            return modelWitch.e;
         } else if (modelPart.equals("right_leg")) {
            return modelWitch.d;
         } else {
            return modelPart.equals("nose") ? modelWitch.f : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbi render = new cbi(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
