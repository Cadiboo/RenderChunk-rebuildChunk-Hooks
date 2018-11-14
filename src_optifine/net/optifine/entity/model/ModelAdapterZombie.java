package net.optifine.entity.model;

public class ModelAdapterZombie extends ModelAdapterBiped {
   public ModelAdapterZombie() {
      super(adt.class, "zombie", 0.5F);
   }

   public bqf makeModel() {
      return new brl();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      cbn render = new cbn(renderManager);
      render.f = modelBase;
      render.c = shadowSize;
      return render;
   }
}
