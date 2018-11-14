package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterChestLarge extends ModelAdapter {
   public ModelAdapterChestLarge() {
      super(avl.class, "chest_large", 0.0F);
   }

   public bqf makeModel() {
      return new bpz();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpl)) {
         return null;
      } else {
         bpl modelChest = (bpl)model;
         if (modelPart.equals("lid")) {
            return modelChest.a;
         } else if (modelPart.equals("base")) {
            return modelChest.b;
         } else {
            return modelPart.equals("knob") ? modelChest.c : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(avl.class);
      if (!(renderer instanceof bwz)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bwz();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityChestRenderer_largeChest.exists()) {
            .Config.warn("Field not found: TileEntityChestRenderer.largeChest");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityChestRenderer_largeChest, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
