package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterMinecartTnt extends ModelAdapterMinecart {
   public ModelAdapterMinecartTnt() {
      super(afm.class, "tnt_minecart", 0.5F);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbc render = new cbc(renderManager);
      if (!Reflector.RenderMinecart_modelMinecart.exists()) {
         .Config.warn("Field not found: RenderMinecart.modelMinecart");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderMinecart_modelMinecart, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
