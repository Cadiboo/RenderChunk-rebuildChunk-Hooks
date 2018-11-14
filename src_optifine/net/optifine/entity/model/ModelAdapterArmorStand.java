package net.optifine.entity.model;

public class ModelAdapterArmorStand extends ModelAdapter {
   public ModelAdapterArmorStand() {
      super(abz.class, "armor_stand", 0.7F);
   }

   public bqf makeModel() {
      return new bpe();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bpe)) {
         return null;
      } else {
         bpe modelArmorStand = (bpe)model;
         if (modelPart.equals("right")) {
            return modelArmorStand.a;
         } else if (modelPart.equals("left")) {
            return modelArmorStand.b;
         } else if (modelPart.equals("waist")) {
            return modelArmorStand.c;
         } else {
            return modelPart.equals("base") ? modelArmorStand.d : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      byp render = new byp(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
