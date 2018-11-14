package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterGhast extends ModelAdapter {
   public ModelAdapterGhast() {
      super(acy.class, "ghast", 0.5F);
   }

   public bqf makeModel() {
      return new bpt();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpt)) {
         return null;
      } else {
         bpt modelGhast = (bpt)model;
         if (modelPart.equals("body")) {
            return (brs)Reflector.getFieldValue(modelGhast, Reflector.ModelGhast_body);
         } else {
            String PREFIX_TENTACLE = "tentacle";
            if (modelPart.startsWith(PREFIX_TENTACLE)) {
               brs[] tentacles = (brs[])((brs[])Reflector.getFieldValue(modelGhast, Reflector.ModelGhast_tentacles));
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
      bzn render = new bzn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
