package net.optifine.shaders.uniform;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1i extends ShaderUniformBase {
   private int[] programValues;
   private static final int VALUE_UNKNOWN = Integer.MIN_VALUE;

   public ShaderUniform1i(String name) {
      super(name);
      this.resetValue();
   }

   public void setValue(int valueNew) {
      int program = this.getProgram();
      int valueOld = this.programValues[program];
      if (valueNew != valueOld) {
         this.programValues[program] = valueNew;
         int location = this.getLocation();
         if (location >= 0) {
            ARBShaderObjects.glUniform1iARB(location, valueNew);
            this.checkGLError();
         }
      }
   }

   public int getValue() {
      int program = this.getProgram();
      int value = this.programValues[program];
      return value;
   }

   protected void onProgramSet(int program) {
      if (program >= this.programValues.length) {
         int[] valuesOld = this.programValues;
         int[] valuesNew = new int[program + 10];
         System.arraycopy(valuesOld, 0, valuesNew, 0, valuesOld.length);

         for(int i = valuesOld.length; i < valuesNew.length; ++i) {
            valuesNew[i] = Integer.MIN_VALUE;
         }

         this.programValues = valuesNew;
      }

   }

   protected void resetValue() {
      this.programValues = new int[]{Integer.MIN_VALUE};
   }
}
