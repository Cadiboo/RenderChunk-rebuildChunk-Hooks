package net.optifine.entity.model;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
   public ModelAdapterQuadruped(Class entityClass, String name, float shadowSize) {
      super(entityClass, name, shadowSize);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqm)) {
         return null;
      } else {
         bqm modelQuadruped = (bqm)model;
         if (modelPart.equals("head")) {
            return modelQuadruped.a;
         } else if (modelPart.equals("body")) {
            return modelQuadruped.b;
         } else if (modelPart.equals("leg1")) {
            return modelQuadruped.c;
         } else if (modelPart.equals("leg2")) {
            return modelQuadruped.d;
         } else if (modelPart.equals("leg3")) {
            return modelQuadruped.e;
         } else {
            return modelPart.equals("leg4") ? modelQuadruped.f : null;
         }
      }
   }
}
