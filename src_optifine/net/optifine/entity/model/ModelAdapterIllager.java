package net.optifine.entity.model;

public abstract class ModelAdapterIllager extends ModelAdapter {
   public ModelAdapterIllager(Class entityClass, String name, float shadowSize) {
      super(entityClass, name, shadowSize);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpy)) {
         return null;
      } else {
         bpy modelVillager = (bpy)model;
         if (modelPart.equals("head")) {
            return modelVillager.a;
         } else if (modelPart.equals("body")) {
            return modelVillager.c;
         } else if (modelPart.equals("arms")) {
            return modelVillager.d;
         } else if (modelPart.equals("left_leg")) {
            return modelVillager.f;
         } else if (modelPart.equals("right_leg")) {
            return modelVillager.e;
         } else if (modelPart.equals("nose")) {
            return modelVillager.g;
         } else if (modelPart.equals("left_arm")) {
            return modelVillager.i;
         } else {
            return modelPart.equals("right_arm") ? modelVillager.h : null;
         }
      }
   }
}
