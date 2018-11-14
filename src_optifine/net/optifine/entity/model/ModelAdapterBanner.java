package net.optifine.entity.model;

import net.optifine.reflect.Reflector;

public class ModelAdapterBanner extends ModelAdapter {
   public ModelAdapterBanner() {
      super(avf.class, "banner", 0.0F);
   }

   public bqf makeModel() {
      return new bpf();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpf)) {
         return null;
      } else {
         bpf modelBanner = (bpf)model;
         if (modelPart.equals("slate")) {
            return modelBanner.a;
         } else if (modelPart.equals("stand")) {
            return modelBanner.b;
         } else {
            return modelPart.equals("top") ? modelBanner.c : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bwx dispatcher = bwx.a;
      bwy renderer = dispatcher.a(avf.class);
      if (!(renderer instanceof bwu)) {
         return null;
      } else {
         if (((bwy)renderer).getEntityClass() == null) {
            renderer = new bwu();
            ((bwy)renderer).a(dispatcher);
         }

         if (!Reflector.TileEntityBannerRenderer_bannerModel.exists()) {
            .Config.warn("Field not found: TileEntityBannerRenderer.bannerModel");
            return null;
         } else {
            Reflector.setFieldValue(renderer, Reflector.TileEntityBannerRenderer_bannerModel, modelBase);
            return (IEntityRenderer)renderer;
         }
      }
   }
}
