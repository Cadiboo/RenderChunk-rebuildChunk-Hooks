package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBullet extends ModelAdapter {
   public ModelAdapterShulkerBullet() {
      super(aer.class, "shulker_bullet", 0.0F);
   }

   public bqf makeModel() {
      return new bqr();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqr)) {
         return null;
      } else {
         bqr modelShulkerBullet = (bqr)model;
         return modelPart.equals("bullet") ? modelShulkerBullet.a : null;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cap render = new cap(renderManager);
      if (!Reflector.RenderShulkerBullet_model.exists()) {
         .Config.warn("Field not found: RenderShulkerBullet.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderShulkerBullet_model, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
