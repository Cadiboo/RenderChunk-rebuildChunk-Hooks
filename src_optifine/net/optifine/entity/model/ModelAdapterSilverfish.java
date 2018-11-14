package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterSilverfish extends ModelAdapter {
   public ModelAdapterSilverfish() {
      super(adj.class, "silverfish", 0.3F);
   }

   public bqf makeModel() {
      return new bqu();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqu)) {
         return null;
      } else {
         bqu modelSilverfish = (bqu)model;
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            brs[] bodyParts = (brs[])((brs[])Reflector.getFieldValue(modelSilverfish, Reflector.ModelSilverfish_bodyParts));
            if (bodyParts == null) {
               return null;
            } else {
               String numStr = modelPart.substring(PREFIX_BODY.length());
               int index = .Config.parseInt(numStr, -1);
               --index;
               return index >= 0 && index < bodyParts.length ? bodyParts[index] : null;
            }
         } else {
            String PREFIX_WINGS = "wing";
            if (modelPart.startsWith(PREFIX_WINGS)) {
               brs[] wings = (brs[])((brs[])Reflector.getFieldValue(modelSilverfish, Reflector.ModelSilverfish_wingParts));
               if (wings == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_WINGS.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < wings.length ? wings[index] : null;
               }
            } else {
               return null;
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      car render = new car(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
