package net.optifine.shaders;

public class CustomTexture implements ICustomTexture {
   private int textureUnit = -1;
   private String path = null;
   private cds texture = null;

   public CustomTexture(int textureUnit, String path, cds texture) {
      this.textureUnit = textureUnit;
      this.path = path;
      this.texture = texture;
   }

   public int getTextureUnit() {
      return this.textureUnit;
   }

   public String getPath() {
      return this.path;
   }

   public cds getTexture() {
      return this.texture;
   }

   public int getTextureId() {
      return this.texture.b();
   }

   public void deleteTexture() {
      cdt.a(this.texture.b());
   }

   public String toString() {
      return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.getTextureId();
   }
}
