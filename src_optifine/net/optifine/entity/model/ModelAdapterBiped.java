package net.optifine.entity.model;

public abstract class ModelAdapterBiped extends ModelAdapter {
   public ModelAdapterBiped(Class entityClass, String name, float shadowSize) {
      super(entityClass, name, shadowSize);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpx)) {
         return null;
      } else {
         bpx modelBiped = (bpx)model;
         if (modelPart.equals("head")) {
            return modelBiped.e;
         } else if (modelPart.equals("headwear")) {
            return modelBiped.f;
         } else if (modelPart.equals("body")) {
            return modelBiped.g;
         } else if (modelPart.equals("left_arm")) {
            return modelBiped.i;
         } else if (modelPart.equals("right_arm")) {
            return modelBiped.h;
         } else if (modelPart.equals("left_leg")) {
            return modelBiped.k;
         } else {
            return modelPart.equals("right_leg") ? modelBiped.j : null;
         }
      }
   }
}
