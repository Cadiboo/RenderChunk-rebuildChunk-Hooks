package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBoat extends ModelAdapter {
   public ModelAdapterBoat() {
      super(afd.class, "boat", 0.5F);
   }

   public bqf makeModel() {
      return new bpj();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpj)) {
         return null;
      } else {
         bpj modelBoat = (bpj)model;
         if (modelPart.equals("bottom")) {
            return modelBoat.a[0];
         } else if (modelPart.equals("back")) {
            return modelBoat.a[1];
         } else if (modelPart.equals("front")) {
            return modelBoat.a[2];
         } else if (modelPart.equals("right")) {
            return modelBoat.a[3];
         } else if (modelPart.equals("left")) {
            return modelBoat.a[4];
         } else if (modelPart.equals("paddle_left")) {
            return modelBoat.b[0];
         } else if (modelPart.equals("paddle_right")) {
            return modelBoat.b[1];
         } else {
            return modelPart.equals("bottom_no_water") ? modelBoat.c : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byt render = new byt(renderManager);
      if (!Reflector.RenderBoat_modelBoat.exists()) {
         .Config.warn("Field not found: RenderBoat.modelBoat");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderBoat_modelBoat, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
