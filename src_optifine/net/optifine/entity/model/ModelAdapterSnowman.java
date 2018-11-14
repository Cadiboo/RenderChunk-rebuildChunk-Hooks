package net.optifine.entity.model;

public class ModelAdapterSnowman extends ModelAdapter {
   public ModelAdapterSnowman() {
      super(aai.class, "snow_golem", 0.5F);
   }

   public bqf makeModel() {
      return new bqz();
   }

   public brs getModelRenderer(bqf model, String modelPart) {
      if (!(model instanceof bqz)) {
         return null;
      } else {
         bqz modelSnowman = (bqz)model;
         if (modelPart.equals("body")) {
            return modelSnowman.a;
         } else if (modelPart.equals("body_bottom")) {
            return modelSnowman.b;
         } else if (modelPart.equals("head")) {
            return modelSnowman.c;
         } else if (modelPart.equals("left_hand")) {
            return modelSnowman.e;
         } else {
            return modelPart.equals("right_hand") ? modelSnowman.d : null;
         }
      }
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cau render = new cau(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
