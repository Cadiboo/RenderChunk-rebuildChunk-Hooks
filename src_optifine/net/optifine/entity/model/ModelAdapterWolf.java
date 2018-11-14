package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterWolf extends ModelAdapter {
   public ModelAdapterWolf() {
      super(aam.class, "wolf", 0.5F);
   }

   public bqf makeModel() {
      return new brk();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brk)) {
         return null;
      } else {
         brk modelWolf = (brk)model;
         if (modelPart.equals("head")) {
            return modelWolf.a;
         } else if (modelPart.equals("body")) {
            return modelWolf.b;
         } else if (modelPart.equals("leg1")) {
            return modelWolf.c;
         } else if (modelPart.equals("leg2")) {
            return modelWolf.d;
         } else if (modelPart.equals("leg3")) {
            return modelWolf.e;
         } else if (modelPart.equals("leg4")) {
            return modelWolf.f;
         } else if (modelPart.equals("tail")) {
            return (brs)Reflector.getFieldValue(modelWolf, Reflector.ModelWolf_tail);
         } else {
            return modelPart.equals("mane") ? (brs)Reflector.getFieldValue(modelWolf, Reflector.ModelWolf_mane) : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbm render = new cbm(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
