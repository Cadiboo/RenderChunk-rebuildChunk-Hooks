package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterEndermite extends ModelAdapter {
   public ModelAdapterEndermite() {
      super(acv.class, "endermite", 0.3F);
   }

   public bqf makeModel() {
      return new bpr();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpr)) {
         return null;
      } else {
         bpr modelEnderMite = (bpr)model;
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            brs[] bodyParts = (brs[])((brs[])Reflector.getFieldValue(modelEnderMite, Reflector.ModelEnderMite_bodyParts));
            if (bodyParts == null) {
               return null;
            } else {
               String numStr = modelPart.substring(PREFIX_BODY.length());
               int index = .Config.parseInt(numStr, -1);
               --index;
               return index >= 0 && index < bodyParts.length ? bodyParts[index] : null;
            }
         } else {
            return null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bze render = new bze(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
