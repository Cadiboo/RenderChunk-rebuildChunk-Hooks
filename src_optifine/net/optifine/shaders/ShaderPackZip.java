package net.optifine.shaders;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.optifine.util.StrUtils;

public class ShaderPackZip implements IShaderPack {
   protected File packFile;
   protected ZipFile packZipFile;
   protected String baseFolder;

   public ShaderPackZip(String name, File file) {
      this.packFile = file;
      this.packZipFile = null;
      this.baseFolder = "";
   }

   public void close() {
      if (this.packZipFile != null) {
         try {
            this.packZipFile.close();
         } catch (Exception var2) {
            ;
         }

         this.packZipFile = null;
      }
   }

   public InputStream getResourceAsStream(String resName) {
      try {
         if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
            this.baseFolder = this.detectBaseFolder(this.packZipFile);
         }

         String name = StrUtils.removePrefix(resName, "/");
         if (name.contains("..")) {
            name = this.resolveRelative(name);
         }

         ZipEntry entry = this.packZipFile.getEntry(this.baseFolder + name);
         return entry == null ? null : this.packZipFile.getInputStream(entry);
      } catch (Exception var4) {
         return null;
      }
   }

   private String resolveRelative(String name) {
      Deque stack = new ArrayDeque();
      String[] parts = .Config.tokenize(name, "/");

      for(int i = 0; i < parts.length; ++i) {
         String part = parts[i];
         if (part.equals("..")) {
            if (stack.isEmpty()) {
               return "";
            }

            stack.removeLast();
         } else {
            stack.add(part);
         }
      }

      String path = Joiner.on('/').join(stack);
      return path;
   }

   private String detectBaseFolder(ZipFile zip) {
      ZipEntry entryShaders = zip.getEntry("shaders/");
      if (entryShaders != null && entryShaders.isDirectory()) {
         return "";
      } else {
         Pattern patternFolderShaders = Pattern.compile("([^/]+/)shaders/");
         Enumeration entries = zip.entries();

         while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String name = entry.getName();
            Matcher matcher = patternFolderShaders.matcher(name);
            if (matcher.matches()) {
               String base = matcher.group(1);
               if (base != null) {
                  if (base.equals("shaders/")) {
                     return "";
                  }

                  return base;
               }
            }
         }

         return "";
      }
   }

   public boolean hasDirectory(String resName) {
      try {
         if (this.packZipFile == null) {
            this.packZipFile = new ZipFile(this.packFile);
            this.baseFolder = this.detectBaseFolder(this.packZipFile);
         }

         String name = StrUtils.removePrefix(resName, "/");
         ZipEntry entry = this.packZipFile.getEntry(this.baseFolder + name);
         return entry != null;
      } catch (IOException var4) {
         return false;
      }
   }

   public String getName() {
      return this.packFile.getName();
   }
}
