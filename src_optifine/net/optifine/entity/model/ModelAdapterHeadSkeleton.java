package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterHeadSkeleton extends ModelAdapter {
   public ModelAdapterHeadSkeleton() {
      super(awd.class, "head_skeleton", 0.0F);
   }

   public bqf makeModel() {
      return new bqv(0, 0, 64, 32);
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqv)) {
         return null;
      } else {
         bqv modelSkeletonHead = (bqv)model;
         return modelPart.equals("head") ? modelSkeletonHead.a : null;
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
