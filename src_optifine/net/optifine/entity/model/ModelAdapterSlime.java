package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterSlime extends ModelAdapter {
   public ModelAdapterSlime() {
      super(adl.class, "slime", 0.25F);
   }

   public bqf makeModel() {
      return new bqy(16);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqy)) {
         return null;
      } else {
         bqy modelSlime = (bqy)model;
         if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelSlime, Reflector.ModelSlime_ModelRenderers, 0);
         } else if (modelPart.equals("left_eye")) {
            return (brs)Reflector.getFieldValue(modelSlime, Reflector.ModelSlime_ModelRenderers, 1);
         } else if (modelPart.equals("right_eye")) {
            return (brs)Reflector.getFieldValue(modelSlime, Reflector.ModelSlime_ModelRenderers, 2);
         } else {
            return modelPart.equals("mouth") ? (brs)Reflector.getFieldValue(modelSlime, Reflector.ModelSlime_ModelRenderers, 3) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cat render = new cat(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
