package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterEnderCrystalNoBase extends ModelAdapterEnderCrystal {
   public ModelAdapterEnderCrystalNoBase() {
      super("end_crystal_no_base");
   }

   public bqf makeModel() {
      return new bro(0.0F, false);
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzg renderObj = (bzg)renderManager.getEntityRenderMap().get(abc.class);
      if (!(renderObj instanceof bzb)) {
         .Config.warn("Not an instance of RenderEnderCrystal: " + renderObj);
         return null;
      } else {
         bzb render = (bzb)renderObj;
         if (!Reflector.RenderEnderCrystal_modelEnderCrystalNoBase.exists()) {
            .Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystalNoBase");
            return null;
         } else {
            Reflector.setFieldValue(render, Reflector.RenderEnderCrystal_modelEnderCrystalNoBase, modelBase);
            render.c = shadowSize;
            return render;
         }
      }
   }
}
