package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterVex extends ModelAdapterBiped {
   public ModelAdapterVex() {
      super(adp.class, "vex", 0.3F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bre)) {
         return null;
      } else {
         brs modelRenderer = super.getModelRenderer(model, modelPart);
         if (modelRenderer != null) {
            return modelRenderer;
         } else {
            bre modelVex = (bre)model;
            if (modelPart.equals("left_wing")) {
               return (brs)Reflector.getFieldValue(modelVex, Reflector.ModelVex_leftWing);
            } else {
               return modelPart.equals("right_wing") ? (brs)Reflector.getFieldValue(modelVex, Reflector.ModelVex_rightWing) : null;
            }
         }
      }
   }

   public bqf makeModel() {
      return new bre();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbe render = new cbe(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
