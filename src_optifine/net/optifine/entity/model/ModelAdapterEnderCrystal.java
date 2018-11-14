package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterEnderCrystal extends ModelAdapter {
   public ModelAdapterEnderCrystal() {
      this("end_crystal");
   }

   protected ModelAdapterEnderCrystal(String name) {
      super(abc.class, name, 0.5F);
   }

   public bqf makeModel() {
      return new bro(0.0F, true);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bro)) {
         return null;
      } else {
         bro modelEnderCrystal = (bro)model;
         if (modelPart.equals("cube")) {
            return (brs)Reflector.getFieldValue(modelEnderCrystal, Reflector.ModelEnderCrystal_ModelRenderers, 0);
         } else if (modelPart.equals("glass")) {
            return (brs)Reflector.getFieldValue(modelEnderCrystal, Reflector.ModelEnderCrystal_ModelRenderers, 1);
         } else {
            return modelPart.equals("base") ? (brs)Reflector.getFieldValue(modelEnderCrystal, Reflector.ModelEnderCrystal_ModelRenderers, 2) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzg renderObj = (bzg)renderManager.getEntityRenderMap().get(abc.class);
      if (!(renderObj instanceof bzb)) {
         .Config.warn("Not an instance of RenderEnderCrystal: " + renderObj);
         return null;
      } else {
         bzb render = (bzb)renderObj;
         if (!Reflector.RenderEnderCrystal_modelEnderCrystal.exists()) {
            .Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
            return null;
         } else {
            Reflector.setFieldValue(render, Reflector.RenderEnderCrystal_modelEnderCrystal, modelBase);
            render.c = shadowSize;
            return render;
         }
      }
   }
}
