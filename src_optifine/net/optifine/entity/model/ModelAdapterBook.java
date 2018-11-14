package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBook extends ModelAdapter {
   public ModelAdapterBook() {
      super(avr.class, "book", 0.0F);
   }

   public bqf makeModel() {
      return new bpk();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpk)) {
         return null;
      } else {
         bpk modelBook = (bpk)model;
         if (modelPart.equals("cover_right")) {
            return modelBook.a;
         } else if (modelPart.equals("cover_left")) {
            return modelBook.b;
         } else if (modelPart.equals("pages_right")) {
            return modelBook.c;
         } else if (modelPart.equals("pages_left")) {
            return modelBook.d;
         } else if (modelPart.equals("flipping_page_right")) {
            return modelBook.e;
         } else if (modelPart.equals("flipping_page_left")) {
            return modelBook.f;
         } else {
            return modelPart.equals("book_spine") ? modelBook.g : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(avr.class);
      if (!(renderer instanceof bxa)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bxa();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
            .Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
