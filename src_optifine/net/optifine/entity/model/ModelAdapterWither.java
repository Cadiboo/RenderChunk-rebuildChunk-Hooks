package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterWither extends ModelAdapter {
   public ModelAdapterWither() {
      super(abx.class, "wither", 0.5F);
   }

   public bqf makeModel() {
      return new brj(0.0F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brj)) {
         return null;
      } else {
         brj modelWither = (brj)model;
         String PREFIX_BODY = "body";
         if (modelPart.startsWith(PREFIX_BODY)) {
            brs[] bodyParts = (brs[])((brs[])Reflector.getFieldValue(modelWither, Reflector.ModelWither_bodyParts));
            if (bodyParts == null) {
               return null;
            } else {
               String numStr = modelPart.substring(PREFIX_BODY.length());
               int index = .Config.parseInt(numStr, -1);
               --index;
               return index >= 0 && index < bodyParts.length ? bodyParts[index] : null;
            }
         } else {
            String PREFIX_HEAD = "head";
            if (modelPart.startsWith(PREFIX_HEAD)) {
               brs[] heads = (brs[])((brs[])Reflector.getFieldValue(modelWither, Reflector.ModelWither_heads));
               if (heads == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_HEAD.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < heads.length ? heads[index] : null;
               }
            } else {
               return null;
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbj render = new cbj(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
