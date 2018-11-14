package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterGuardian extends ModelAdapter {
   public ModelAdapterGuardian() {
      super(ada.class, "guardian", 0.5F);
   }

   public bqf makeModel() {
      return new bpu();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpu)) {
         return null;
      } else {
         bpu modelGuardian = (bpu)model;
         if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelGuardian, Reflector.ModelGuardian_body);
         } else if (modelPart.equals("eye")) {
            return (brs)Reflector.getFieldValue(modelGuardian, Reflector.ModelGuardian_eye);
         } else {
            String PREFIX_SPINE = "spine";
            if (modelPart.startsWith(PREFIX_SPINE)) {
               brs[] spines = (brs[])((brs[])Reflector.getFieldValue(modelGuardian, Reflector.ModelGuardian_spines));
               if (spines == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_SPINE.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < spines.length ? spines[index] : null;
               }
            } else {
               String PREFIX_TAIL = "tail";
               if (modelPart.startsWith(PREFIX_TAIL)) {
                  brs[] tails = (brs[])((brs[])Reflector.getFieldValue(modelGuardian, Reflector.ModelGuardian_tail));
                  if (tails == null) {
                     return null;
                  } else {
                     String numStr = modelPart.substring(PREFIX_TAIL.length());
                     int index = .Config.parseInt(numStr, -1);
                     --index;
                     return index >= 0 && index < tails.length ? tails[index] : null;
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzp render = new bzp(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
