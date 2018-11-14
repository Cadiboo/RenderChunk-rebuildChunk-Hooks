package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox extends ModelAdapter {
   public ModelAdapterShulkerBox() {
      super(awb.class, "shulker_box", 0.0F);
   }

   public bqf makeModel() {
      return new bqs();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqs)) {
         return null;
      } else {
         bqs modelShulker = (bqs)model;
         if (modelPart.equals("head")) {
            return modelShulker.c;
         } else if (modelPart.equals("base")) {
            return modelShulker.a;
         } else {
            return modelPart.equals("lid") ? modelShulker.b : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(awb.class);
      if (!(renderer instanceof bxe)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bxe((bqs)modelBase);
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
            .Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
