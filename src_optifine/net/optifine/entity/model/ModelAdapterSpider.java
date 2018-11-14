package net.optifine.entity.model;

public class ModelAdapterSpider extends ModelAdapter {
   public ModelAdapterSpider() {
      super(adn.class, "spider", 1.0F);
   }

   protected ModelAdapterSpider(Class entityClass, String name, float shadowSize) {
      super(entityClass, name, shadowSize);
   }

   public bqf makeModel() {
      return new bra();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bra)) {
         return null;
      } else {
         bra modelSpider = (bra)model;
         if (modelPart.equals("head")) {
            return modelSpider.a;
         } else if (modelPart.equals("neck")) {
            return modelSpider.b;
         } else if (modelPart.equals("body")) {
            return modelSpider.c;
         } else if (modelPart.equals("leg1")) {
            return modelSpider.d;
         } else if (modelPart.equals("leg2")) {
            return modelSpider.e;
         } else if (modelPart.equals("leg3")) {
            return modelSpider.f;
         } else if (modelPart.equals("leg4")) {
            return modelSpider.g;
         } else if (modelPart.equals("leg5")) {
            return modelSpider.h;
         } else if (modelPart.equals("leg6")) {
            return modelSpider.i;
         } else if (modelPart.equals("leg7")) {
            return modelSpider.j;
         } else {
            return modelPart.equals("leg8") ? modelSpider.k : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      caw render = new caw(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
