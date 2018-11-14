package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterDragon extends ModelAdapter {
   public ModelAdapterDragon() {
      super(abd.class, "dragon", 0.5F);
   }

   public bqf makeModel() {
      return new brn(0.0F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brn)) {
         return null;
      } else {
         brn modelDragon = (brn)model;
         if (modelPart.equals("head")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 0);
         } else if (modelPart.equals("spine")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 1);
         } else if (modelPart.equals("jaw")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 2);
         } else if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 3);
         } else if (modelPart.equals("rear_leg")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 4);
         } else if (modelPart.equals("front_leg")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 5);
         } else if (modelPart.equals("rear_leg_tip")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 6);
         } else if (modelPart.equals("front_leg_tip")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 7);
         } else if (modelPart.equals("rear_foot")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 8);
         } else if (modelPart.equals("front_foot")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 9);
         } else if (modelPart.equals("wing")) {
            return (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 10);
         } else {
            return modelPart.equals("wing_tip") ? (brs)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 11) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzc render = new bzc(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
