package net.optifine.entity.model;

public class ModelAdapterPigZombie extends ModelAdapterBiped {
   public ModelAdapterPigZombie() {
      super(adf.class, "zombie_pigman", 0.5F);
   }

   public bqf makeModel() {
      return new brl();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cal render = new cal(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
