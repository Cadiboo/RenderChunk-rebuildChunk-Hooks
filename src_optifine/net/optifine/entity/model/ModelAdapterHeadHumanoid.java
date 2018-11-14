package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterHeadHumanoid extends ModelAdapter {
   public ModelAdapterHeadHumanoid() {
      super(awd.class, "head_humanoid", 0.0F);
   }

   public bqf makeModel() {
      return new bpw();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpw)) {
         return null;
      } else {
         bpw modelHumanoidHead = (bpw)model;
         if (modelPart.equals("head")) {
            return modelHumanoidHead.a;
         } else if (modelPart.equals("head2")) {
            return !Reflector.ModelHumanoidHead_head.exists() ? null : (brs)Reflector.getFieldValue(modelHumanoidHead, Reflector.ModelHumanoidHead_head);
         } else {
            return null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(awd.class);
      if (!(renderer instanceof bxg)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bxg();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntitySkullRenderer_humanoidHead.exists()) {
            .Config.warn("Field not found: TileEntitySkullRenderer.humanoidHead");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntitySkullRenderer_humanoidHead, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
