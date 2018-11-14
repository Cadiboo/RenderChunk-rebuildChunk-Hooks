package net.optifine.shaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;

public class SimpleShaderTexture extends cdf {
   private String texturePath;
   private static final cfg METADATA_SERIALIZER = makeMetadataSerializer();

   public SimpleShaderTexture(String texturePath) {
      this.texturePath = texturePath;
   }

   public void a(cep resourceManager) throws IOException {
      this.c();
      InputStream inputStream = Shaders.getShaderPackResourceStream(this.texturePath);
      if (inputStream == null) {
         throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
      } else {
         try {
            BufferedImage bufferedimage = cdt.a(inputStream);
            cfv tms = this.loadTextureMetadataSection();
            cdt.a(this.b(), bufferedimage, tms.a(), tms.b());
         } finally {
            IOUtils.closeQuietly(inputStream);
         }

      }
   }

   private cfv loadTextureMetadataSection() {
      String pathMeta = this.texturePath + ".mcmeta";
      String sectionName = "texture";
      InputStream inMeta = Shaders.getShaderPackResourceStream(pathMeta);
      if (inMeta != null) {
         cfg ms = METADATA_SERIALIZER;
         BufferedReader brMeta = new BufferedReader(new InputStreamReader(inMeta));

         try {
            JsonObject jsonMeta = (new JsonParser()).parse(brMeta).getAsJsonObject();
            cfv meta = (cfv)ms.a(sectionName, jsonMeta);
            if (meta != null) {
               cfv var8 = meta;
               return var8;
            }
         } catch (RuntimeException var12) {
            SMCLog.warning("Error reading metadata: " + pathMeta);
            SMCLog.warning("" + var12.getClass().getName() + ": " + var12.getMessage());
         } finally {
            IOUtils.closeQuietly(brMeta);
            IOUtils.closeQuietly(inMeta);
         }
      }

      return new cfv(false, false);
   }

   private static cfg makeMetadataSerializer() {
      cfg ms = new cfg();
      ms.a(new cfw(), cfv.class);
      ms.a(new cfm(), cfl.class);
      ms.a(new cfj(), cfi.class);
      ms.a(new cfs(), cfr.class);
      ms.a(new cfp(), cfo.class);
      return ms;
   }
}
