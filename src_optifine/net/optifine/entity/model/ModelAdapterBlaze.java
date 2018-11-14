package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBlaze extends ModelAdapter {
   public ModelAdapterBlaze() {
      super(acq.class, "blaze", 0.5F);
   }

   public bqf makeModel() {
      return new bpi();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpi)) {
         return null;
      } else {
         bpi modelBlaze = (bpi)model;
         if (modelPart.equals("head")) {
            return (brs)Reflector.getFieldValue(modelBlaze, Reflector.ModelBlaze_blazeHead);
         } else {
            String PREFIX_STICK = "stick";
            if (modelPart.startsWith(PREFIX_STICK)) {
               brs[] sticks = (brs[])((brs[])Reflector.getFieldValue(modelBlaze, Reflector.ModelBlaze_blazeSticks));
               if (sticks == null) {
                  return null;
               } else {
                  String numStr = modelPart.substring(PREFIX_STICK.length());
                  int index = .Config.parseInt(numStr, -1);
                  --index;
                  return index >= 0 && index < sticks.length ? sticks[index] : null;
               }
            } else {
               return null;
            }
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bys render = new bys(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
