package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapter {
   public ModelAdapterHeadDragon() {
      super(awd.class, "head_dragon", 0.0F);
   }

   public bqf makeModel() {
      return new brm(0.0F);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof brm)) {
         return null;
      } else {
         brm modelDragonHead = (brm)model;
         if (modelPart.equals("head")) {
            return (brs)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head);
         } else {
            return modelPart.equals("jaw") ? (brs)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw) : null;
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

         if (!Reflector.TileEntitySkullRenderer_dragonHead.exists()) {
            .Config.warn("Field not found: TileEntitySkullRenderer.dragonHead");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntitySkullRenderer_dragonHead, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
