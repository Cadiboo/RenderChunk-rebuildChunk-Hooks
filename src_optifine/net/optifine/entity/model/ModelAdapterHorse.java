package net.optifine.entity.model;

import java.util.HashMap;
import java.util.Map;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse extends ModelAdapter {
   private static Map mapPartFields = null;

   public ModelAdapterHorse() {
      super(aaq.class, "horse", 0.75F);
   }

   protected ModelAdapterHorse(Class entityClass, String name, float shadowSize) {
      super(entityClass, name, shadowSize);
   }

   public bqf makeModel() {
      return new bpv();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpv)) {
         return null;
      } else {
         bpv modelHorse = (bpv)model;
         Map mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = ((Integer)mapParts.get(modelPart)).intValue();
            return (brs)Reflector.getFieldValue(modelHorse, Reflector.ModelHorse_ModelRenderers, index);
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
         mapPartFields.put("head", Integer.valueOf(0));
         mapPartFields.put("upper_mouth", Integer.valueOf(1));
         mapPartFields.put("lower_mouth", Integer.valueOf(2));
         mapPartFields.put("horse_left_ear", Integer.valueOf(3));
         mapPartFields.put("horse_right_ear", Integer.valueOf(4));
         mapPartFields.put("mule_left_ear", Integer.valueOf(5));
         mapPartFields.put("mule_right_ear", Integer.valueOf(6));
         mapPartFields.put("neck", Integer.valueOf(7));
         mapPartFields.put("horse_face_ropes", Integer.valueOf(8));
         mapPartFields.put("mane", Integer.valueOf(9));
         mapPartFields.put("body", Integer.valueOf(10));
         mapPartFields.put("tail_base", Integer.valueOf(11));
         mapPartFields.put("tail_middle", Integer.valueOf(12));
         mapPartFields.put("tail_tip", Integer.valueOf(13));
         mapPartFields.put("back_left_leg", Integer.valueOf(14));
         mapPartFields.put("back_left_shin", Integer.valueOf(15));
         mapPartFields.put("back_left_hoof", Integer.valueOf(16));
         mapPartFields.put("back_right_leg", Integer.valueOf(17));
         mapPartFields.put("back_right_shin", Integer.valueOf(18));
         mapPartFields.put("back_right_hoof", Integer.valueOf(19));
         mapPartFields.put("front_left_leg", Integer.valueOf(20));
         mapPartFields.put("front_left_shin", Integer.valueOf(21));
         mapPartFields.put("front_left_hoof", Integer.valueOf(22));
         mapPartFields.put("front_right_leg", Integer.valueOf(23));
         mapPartFields.put("front_right_shin", Integer.valueOf(24));
         mapPartFields.put("front_right_hoof", Integer.valueOf(25));
         mapPartFields.put("mule_left_chest", Integer.valueOf(26));
         mapPartFields.put("mule_right_chest", Integer.valueOf(27));
         mapPartFields.put("horse_saddle_bottom", Integer.valueOf(28));
         mapPartFields.put("horse_saddle_front", Integer.valueOf(29));
         mapPartFields.put("horse_saddle_back", Integer.valueOf(30));
         mapPartFields.put("horse_left_saddle_rope", Integer.valueOf(31));
         mapPartFields.put("horse_left_saddle_metal", Integer.valueOf(32));
         mapPartFields.put("horse_right_saddle_rope", Integer.valueOf(33));
         mapPartFields.put("horse_right_saddle_metal", Integer.valueOf(34));
         mapPartFields.put("horse_left_face_metal", Integer.valueOf(35));
         mapPartFields.put("horse_right_face_metal", Integer.valueOf(36));
         mapPartFields.put("horse_left_rein", Integer.valueOf(37));
         mapPartFields.put("horse_right_rein", Integer.valueOf(38));
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzq render = new bzq(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
