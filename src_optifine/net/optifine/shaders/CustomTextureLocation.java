package net.optifine.shaders;

public class CustomTextureLocation implements ICustomTexture {
   private int textureUnit = -1;
   private nf location;
   private cds texture;

   public CustomTextureLocation(int textureUnit, nf location) {
      this.textureUnit = textureUnit;
      this.location = location;
   }

   public cds getTexture() {
      if (this.texture == null) {
         cdr textureManager = bib.z().N();
         this.texture = textureManager.b(this.location);
         if (this.texture == null) {
            this.texture = new cdm(this.location);
            textureManager.a(this.location, this.texture);
            this.texture = textureManager.b(this.location);
         }
      }

      return this.texture;
   }

   public int getTextureId() {
      return this.getTexture().b();
   }

   public int getTextureUnit() {
      return this.textureUnit;
   }

   public void deleteTexture() {
   }

   public String toString() {
      return "textureUnit: " + this.textureUnit + ", location: " + this.location + ", glTextureId: " + (this.texture != null ? this.texture.b() : "");
   }
}
