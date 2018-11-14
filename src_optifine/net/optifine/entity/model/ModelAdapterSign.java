package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
   public ModelAdapterSign() {
      super(awc.class, "sign", 0.0F);
   }

   public bqf makeModel() {
      return new bqt();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqt)) {
         return null;
      } else {
         bqt modelSign = (bqt)model;
         if (modelPart.equals("board")) {
            return modelSign.a;
         } else {
            return modelPart.equals("stick") ? modelSign.b : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(awc.class);
      if (!(renderer instanceof bxf)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bxf();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntitySignRenderer_model.exists()) {
            .Config.warn("Field not found: TileEntitySignRenderer.model");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntitySignRenderer_model, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
