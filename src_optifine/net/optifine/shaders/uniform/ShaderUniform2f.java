package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform2f extends ShaderUniformBase {
   private float[][] programValues;
   private static final float VALUE_UNKNOWN = -3.4028235E38F;

   public ShaderUniform2f(String name) {
      super(name);
      this.resetValue();
   }

   public void setValue(float v0, float v1) {
      int program = this.getProgram();
      float[] valueOld = this.programValues[program];
      if (valueOld[0] != v0 || valueOld[1] != v1) {
         valueOld[0] = v0;
         valueOld[1] = v1;
         int location = this.getLocation();
         if (location >= 0) {
            ARBShaderObjects.glUniform2fARB(location, v0, v1);
            this.checkGLError();
         }
      }
   }

   public float[] getValue() {
      int program = this.getProgram();
      float[] value = this.programValues[program];
      return value;
   }

   protected void onProgramSet(int program) {
      if (program >= this.programValues.length) {
         float[][] valuesOld = this.programValues;
         float[][] valuesNew = new float[program + 10][];
         System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);
         this.programValues = valuesNew;
      }

      if (this.programValues[program] == null) {
         this.programValues[program] = new float[]{-3.4028235E38F, -3.4028235E38F};
      }

   }

   protected void resetValue() {
      this.programValues = new float[][]{{-3.4028235E38F, -3.4028235E38F}};
   }
}
