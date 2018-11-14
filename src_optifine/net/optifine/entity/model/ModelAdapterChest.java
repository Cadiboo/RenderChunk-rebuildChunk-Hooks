package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterChest extends ModelAdapter {
   public ModelAdapterChest() {
      super(avl.class, "chest", 0.0F);
   }

   public bqf makeModel() {
      return new bpl();
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

         if (!Reflector.TileEntityChestRenderer_simpleChest.exists()) {
            .Config.warn("Field not found: TileEntityChestRenderer.simpleChest");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityChestRenderer_simpleChest, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
