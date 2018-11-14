package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot extends ModelAdapter {
   private static Map mapPartFields = null;

   public ModelAdapterOcelot() {
      super(aab.class, "ocelot", 0.4F);
   }

   public bqf makeModel() {
      return new bqg();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqg)) {
         return null;
      } else {
         bqg modelOcelot = (bqg)model;
         Map mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = ((Integer)mapParts.get(modelPart)).intValue();
            return (brs)Reflector.getFieldValue(modelOcelot, Reflector.ModelOcelot_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   private static Map getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new HashMap();
         mapPartFields.put("back_left_leg", Integer.valueOf(0));
         mapPartFields.put("back_right_leg", Integer.valueOf(1));
         mapPartFields.put("front_left_leg", Integer.valueOf(2));
         mapPartFields.put("front_right_leg", Integer.valueOf(3));
         mapPartFields.put("tail", Integer.valueOf(4));
         mapPartFields.put("tail2", Integer.valueOf(5));
         mapPartFields.put("head", Integer.valueOf(6));
         mapPartFields.put("body", Integer.valueOf(7));
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cah render = new cah(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
