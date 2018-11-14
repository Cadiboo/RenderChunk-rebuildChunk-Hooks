package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterMinecart extends ModelAdapter {
   public ModelAdapterMinecart() {
      super(afe.class, "minecart", 0.5F);
   }

   protected ModelAdapterMinecart(Class entityClass, String name, float shadow) {
      super(entityClass, name, shadow);
   }

   public bqf makeModel() {
      return new bqe();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqe)) {
         return null;
      } else {
         bqe modelMinecart = (bqe)model;
         if (modelPart.equals("bottom")) {
            return modelMinecart.a[0];
         } else if (modelPart.equals("back")) {
            return modelMinecart.a[1];
         } else if (modelPart.equals("front")) {
            return modelMinecart.a[2];
         } else if (modelPart.equals("right")) {
            return modelMinecart.a[3];
         } else if (modelPart.equals("left")) {
            return modelMinecart.a[4];
         } else {
            return modelPart.equals("dirt") ? modelMinecart.a[5] : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cad render = new cad(renderManager);
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
