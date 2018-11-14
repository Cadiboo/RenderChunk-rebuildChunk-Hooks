package net.optifine.shaders;

public class DefaultTexture extends cdf {
   public DefaultTexture() {
      this.a((cep)null);
   }

   public void a(cep resourcemanager) {
      int[] aint = ShadersTex.createAIntImage(1, -1);
      ShadersTex.setupTexture(this.getMultiTexID(), aint, 1, 1, false, false);
   }
}
