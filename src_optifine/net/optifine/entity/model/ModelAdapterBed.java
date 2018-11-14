package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBed extends ModelAdapter {
   public ModelAdapterBed() {
      super(avi.class, "bed", 0.0F);
   }

   public bqf makeModel() {
      return new bph();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bph)) {
         return null;
      } else {
         bph modelBed = (bph)model;
         if (modelPart.equals("head")) {
            return modelBed.a;
         } else if (modelPart.equals("foot")) {
            return modelBed.b;
         } else if (modelPart.equals("leg1")) {
            return modelBed.c[0];
         } else if (modelPart.equals("leg2")) {
            return modelBed.c[1];
         } else if (modelPart.equals("leg3")) {
            return modelBed.c[2];
         } else {
            return modelPart.equals("leg4") ? modelBed.c[3] : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(avi.class);
      if (!(renderer instanceof bww)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bww();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityBedRenderer_model.exists()) {
            .Config.warn("Field not found: TileEntityBedRenderer.model");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityBedRenderer_model, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
