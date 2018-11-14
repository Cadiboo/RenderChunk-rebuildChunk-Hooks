package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBat extends ModelAdapter {
   public ModelAdapterBat() {
      super(zt.class, "bat", 0.25F);
   }

   public bqf makeModel() {
      return new bpg();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpg)) {
         return null;
      } else {
         bpg modelBat = (bpg)model;
         if (modelPart.equals("head")) {
            return (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 0);
         } else if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 1);
         } else if (modelPart.equals("right_wing")) {
            return (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 2);
         } else if (modelPart.equals("left_wing")) {
            return (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 3);
         } else if (modelPart.equals("outer_right_wing")) {
            return (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 4);
         } else {
            return modelPart.equals("outer_left_wing") ? (brs)Reflector.getFieldValue(modelBat, Reflector.ModelBat_ModelRenderers, 5) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byr render = new byr(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
