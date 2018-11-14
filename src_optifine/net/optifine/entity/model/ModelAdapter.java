package net.optifine.entity.model;

public abstract class ModelAdapter {
   private Class entityClass;
   private String name;
   private float shadowSize;

   public ModelAdapter(Class entityClass, String name, float shadowSize) {
      this.entityClass = entityClass;
      this.name = name;
      this.shadowSize = shadowSize;
   }

   public Class getEntityClass() {
      return this.entityClass;
   }

   public String getName() {
      return this.name;
   }

   public float getShadowSize() {
      return this.shadowSize;
   }

   public abstract bqf makeModel();

   public abstract brs getModelRenderer(bqf var1, String var2);

   public abstract IEntityRenderer makeEntityRender(bqf var1, float var2);
}
