package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1f extends ShaderUniformBase {
   private float[] programValues;
   private static final float VALUE_UNKNOWN = -3.4028235E38F;

   public ShaderUniform1f(String name) {
      super(name);
      this.resetValue();
   }

   public void setValue(float valueNew) {
      int program = this.getProgram();
      float valueOld = this.programValues[program];
      if (valueNew != valueOld) {
         this.programValues[program] = valueNew;
         int location = this.getLocation();
         if (location >= 0) {
            ARBShaderObjects.glUniform1fARB(location, valueNew);
            this.checkGLError();
         }
      }
   }

   public float getValue() {
      int program = this.getProgram();
      float value = this.programValues[program];
      return value;
   }

   protected void onProgramSet(int program) {
      if (program >= this.programValues.length) {
         float[] valuesOld = this.programValues;
         float[] valuesNew = new float[program + 10];
         System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);

         for(int i = valuesOld.length; i < valuesNew.length; ++i) {
            valuesNew[i] = -3.4028235E38F;
         }

         this.programValues = valuesNew;
      }

   }

   protected void resetValue() {
      this.programValues = new float[]{-3.4028235E38F};
   }
}
