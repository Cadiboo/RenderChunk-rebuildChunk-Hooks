package net.optifine.entity.model;

public class ModelAdapterCreeper extends ModelAdapter {
   public ModelAdapterCreeper() {
      super(acs.class, "creeper", 0.5F);
   }

   public bqf makeModel() {
      return new bpo();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpo)) {
         return null;
      } else {
         bpo modelCreeper = (bpo)model;
         if (modelPart.equals("head")) {
            return modelCreeper.a;
         } else if (modelPart.equals("armor")) {
            return modelCreeper.b;
         } else if (modelPart.equals("body")) {
            return modelCreeper.c;
         } else if (modelPart.equals("leg1")) {
            return modelCreeper.d;
         } else if (modelPart.equals("leg2")) {
            return modelCreeper.e;
         } else if (modelPart.equals("leg3")) {
            return modelCreeper.f;
         } else {
            return modelPart.equals("leg4") ? modelCreeper.g : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byx render = new byx(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
