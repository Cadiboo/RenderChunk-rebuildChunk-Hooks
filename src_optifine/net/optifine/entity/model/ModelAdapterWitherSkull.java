package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull extends ModelAdapter {
   public ModelAdapterWitherSkull() {
      super(afb.class, "wither_skull", 0.0F);
   }

   public bqf makeModel() {
      return new bqv();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqv)) {
         return null;
      } else {
         bqv modelSkeletonHead = (bqv)model;
         return modelPart.equals("head") ? modelSkeletonHead.a : null;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbl render = new cbl(renderManager);
      if (!Reflector.RenderWitherSkull_model.exists()) {
         .Config.warn("Field not found: RenderWitherSkull_model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderWitherSkull_model, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
