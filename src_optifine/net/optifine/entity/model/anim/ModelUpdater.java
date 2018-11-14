package net.optifine.entity.model.anim;

public class ModelUpdater {
   private ModelVariableUpdater[] modelVariableUpdaters;

   public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaters) {
      this.modelVariableUpdaters = modelVariableUpdaters;
   }

   public void update() {
      for(int i = 0; i < this.modelVariableUpdaters.length; ++i) {
         ModelVariableUpdater mvu = this.modelVariableUpdaters[i];
         mvu.update();
      }

   }

   public boolean initialize(IModelResolver mr) {
      for(int i = 0; i < this.modelVariableUpdaters.length; ++i) {
         ModelVariableUpdater mvu = this.modelVariableUpdaters[i];
         if (!mvu.initialize(mr)) {
            return false;
         }
      }

      return true;
   }
}
