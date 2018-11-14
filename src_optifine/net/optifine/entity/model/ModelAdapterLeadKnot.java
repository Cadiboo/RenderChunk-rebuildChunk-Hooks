package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot extends ModelAdapter {
   public ModelAdapterLeadKnot() {
      super(acc.class, "lead_knot", 0.0F);
   }

   public bqf makeModel() {
      return new bqb();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqb)) {
         return null;
      } else {
         bqb modelLeashKnot = (bqb)model;
         return modelPart.equals("knot") ? modelLeashKnot.a : null;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzy render = new bzy(renderManager);
      if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
         .Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
         render.c = shadowSize;
         return render;
      }
   }
}
