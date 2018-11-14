package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterMagmaCube extends ModelAdapter {
   public ModelAdapterMagmaCube() {
      super(add.class, "magma_cube", 0.5F);
   }

   public bqf makeModel() {
      return new bqa();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqa)) {
         return null;
      } else {
         bqa modelMagmaCube = (bqa)model;
         if (modelPart.equals("core")) {
            return (brs)Reflector.getFieldValue(modelMagmaCube, Reflector.ModelMagmaCube_core);
         } else {
            String PREFIX_SEGMENTS = "segment";
            if (modelPart.startsWith(PREFIX_SEGMENTS)) {
               brs[] segments = (brs[])((brs[])Reflector.getFieldValue(modelMagmaCube, Reflector.ModelMagmaCube_segments));
               if (segments == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_SEGMENTS.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < segments.length ? segments[index] : null;
               }
            } else {
               return null;
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzx render = new bzx(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
