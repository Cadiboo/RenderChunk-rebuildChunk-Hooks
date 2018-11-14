package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterSquid extends ModelAdapter {
   public ModelAdapterSquid() {
      super(aaj.class, "squid", 0.7F);
   }

   public bqf makeModel() {
      return new brb();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brb)) {
         return null;
      } else {
         brb modelSquid = (brb)model;
         if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelSquid, Reflector.ModelSquid_body);
         } else {
            String PREFIX_TENTACLE = "tentacle";
            if (modelPart.startsWith(PREFIX_TENTACLE)) {
               brs[] tentacles = (brs[])((brs[])Reflector.getFieldValue(modelSquid, Reflector.ModelSquid_tentacles));
               if (tentacles == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_TENTACLE.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < tentacles.length ? tentacles[index] : null;
               }
            } else {
               return null;
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cax render = new cax(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
