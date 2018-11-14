package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterParrot extends ModelAdapter {
   public ModelAdapterParrot() {
      super(aac.class, "parrot", 0.3F);
   }

   public bqf makeModel() {
      return new bqh();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqh)) {
         return null;
      } else {
         bqh modelParrot = (bqh)model;
         if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 0);
         } else if (modelPart.equals("tail")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 1);
         } else if (modelPart.equals("left_wing")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 2);
         } else if (modelPart.equals("right_wing")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 3);
         } else if (modelPart.equals("head")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 4);
         } else if (modelPart.equals("left_leg")) {
            return (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 9);
         } else {
            return modelPart.equals("right_leg") ? (brs)Reflector.getFieldValue(modelParrot, Reflector.ModelParrot_ModelRenderers, 10) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      caj render = new caj(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
