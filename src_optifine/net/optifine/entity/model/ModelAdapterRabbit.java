package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.optifine.reflect.Reflector;

public class ModelAdapterRabbit extends ModelAdapter {
   private static Map mapPartFields = null;

   public ModelAdapterRabbit() {
      super(aaf.class, "rabbit", 0.3F);
   }

   public bqf makeModel() {
      return new bqn();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqn)) {
         return null;
      } else {
         bqn modelRabbit = (bqn)model;
         Map mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = ((Integer)mapParts.get(modelPart)).intValue();
            return (brs)Reflector.getFieldValue(modelRabbit, Reflector.ModelRabbit_renderers, index);
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
         mapPartFields.put("left_foot", Integer.valueOf(0));
         mapPartFields.put("right_foot", Integer.valueOf(1));
         mapPartFields.put("left_thigh", Integer.valueOf(2));
         mapPartFields.put("right_thigh", Integer.valueOf(3));
         mapPartFields.put("body", Integer.valueOf(4));
         mapPartFields.put("left_arm", Integer.valueOf(5));
         mapPartFields.put("right_arm", Integer.valueOf(6));
         mapPartFields.put("head", Integer.valueOf(7));
         mapPartFields.put("right_ear", Integer.valueOf(8));
         mapPartFields.put("left_ear", Integer.valueOf(9));
         mapPartFields.put("tail", Integer.valueOf(10));
         mapPartFields.put("nose", Integer.valueOf(11));
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      can render = new can(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
