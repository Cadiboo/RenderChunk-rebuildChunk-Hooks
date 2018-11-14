package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs extends ModelAdapter {
   public ModelAdapterEvokerFangs() {
      super(aej.class, "evocation_fangs", 0.0F);
   }

   public bqf makeModel() {
      return new bps();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bps)) {
         return null;
      } else {
         bps modelEvokerFangs = (bps)model;
         if (modelPart.equals("base")) {
            return (brs)Reflector.getFieldValue(modelEvokerFangs, Reflector.ModelEvokerFangs_ModelRenderers, 0);
         } else if (modelPart.equals("upper_jaw")) {
            return (brs)Reflector.getFieldValue(modelEvokerFangs, Reflector.ModelEvokerFangs_ModelRenderers, 1);
         } else {
            return modelPart.equals("lower_jaw") ? (brs)Reflector.getFieldValue(modelEvokerFangs, Reflector.ModelEvokerFangs_ModelRenderers, 2) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzh render = new bzh(renderManager);
      if (!Reflector.RenderEvokerFangs_model.exists()) {
         .Config.warn("Field not found: RenderEvokerFangs.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderEvokerFangs_model, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
