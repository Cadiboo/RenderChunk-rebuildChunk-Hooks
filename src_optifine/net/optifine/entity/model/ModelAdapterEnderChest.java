package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterEnderChest extends ModelAdapter {
   public ModelAdapterEnderChest() {
      super(avs.class, "ender_chest", 0.0F);
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
      bwy renderer = dispatcher.a(avs.class);
      if (!(renderer instanceof bxb)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bxb();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityEnderChestRenderer_modelChest.exists()) {
            .Config.warn("Field not found: TileEntityEnderChestRenderer.modelChest");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityEnderChestRenderer_modelChest, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
