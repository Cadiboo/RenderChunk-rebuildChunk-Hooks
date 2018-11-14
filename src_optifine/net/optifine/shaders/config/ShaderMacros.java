package net.optifine.shaders.config;

import h.a;
import net.optifine.shaders.Shaders;

public class ShaderMacros {
   private static String PREFIX_MACRO = "MC_";
   public static final String MC_VERSION = "MC_VERSION";
   public static final String MC_GL_VERSION = "MC_GL_VERSION";
   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
   public static final String MC_OS_MAC = "MC_OS_MAC";
   public static final String MC_OS_LINUX = "MC_OS_LINUX";
   public static final String MC_OS_OTHER = "MC_OS_OTHER";
   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
   public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
   public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
   public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
   public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
   public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
   public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
   public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
   public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
   private static ShaderMacro[] extensionMacros;

   public static String getOs() {
      a os = h.a();
      switch(os) {
      case c:
         return "MC_OS_WINDOWS";
      case d:
         return "MC_OS_MAC";
      case a:
         return "MC_OS_LINUX";
      default:
         return "MC_OS_OTHER";
      }
   }

   public static String getVendor() {
      String vendor = .Config.openGlVendor;
      if (vendor == null) {
         return "MC_GL_VENDOR_OTHER";
      } else {
         vendor = vendor.toLowerCase();
         if (vendor.startsWith("ati")) {
            return "MC_GL_VENDOR_ATI";
         } else if (vendor.startsWith("intel")) {
            return "MC_GL_VENDOR_INTEL";
         } else if (vendor.startsWith("nvidia")) {
            return "MC_GL_VENDOR_NVIDIA";
         } else {
            return vendor.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER";
         }
      }
   }

   public static String getRenderer() {
      String renderer = .Config.openGlRenderer;
      if (renderer == null) {
         return "MC_GL_RENDERER_OTHER";
      } else {
         renderer = renderer.toLowerCase();
         if (renderer.startsWith("amd")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("ati")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("radeon")) {
            return "MC_GL_RENDERER_RADEON";
         } else if (renderer.startsWith("gallium")) {
            return "MC_GL_RENDERER_GALLIUM";
         } else if (renderer.startsWith("intel")) {
            return "MC_GL_RENDERER_INTEL";
         } else if (renderer.startsWith("geforce")) {
            return "MC_GL_RENDERER_GEFORCE";
         } else if (renderer.startsWith("nvidia")) {
            return "MC_GL_RENDERER_GEFORCE";
         } else if (renderer.startsWith("quadro")) {
            return "MC_GL_RENDERER_QUADRO";
         } else if (renderer.startsWith("nvs")) {
            return "MC_GL_RENDERER_QUADRO";
         } else {
            return renderer.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER";
         }
      }
   }

   public static String getPrefixMacro() {
      return PREFIX_MACRO;
   }

   public static ShaderMacro[] getExtensions() {
      if (extensionMacros == null) {
         String[] exts = .Config.getOpenGlExtensions();
         ShaderMacro[] extMacros = new ShaderMacro[exts.length];

         for(int i = 0; i < exts.length; ++i) {
            extMacros[i] = new ShaderMacro(PREFIX_MACRO + exts[i], "");
         }

         extensionMacros = extMacros;
      }

      return extensionMacros;
   }

   public static String getMacroLines() {
      StringBuilder sb = new StringBuilder();
      addMacroLine(sb, "MC_VERSION", .Config.getMinecraftVersionInt());
      addMacroLine(sb, "MC_GL_VERSION " + .Config.getGlVersion().toInt());
      addMacroLine(sb, "MC_GLSL_VERSION " + .Config.getGlslVersion().toInt());
      addMacroLine(sb, getOs());
      addMacroLine(sb, getVendor());
      addMacroLine(sb, getRenderer());
      if (Shaders.configAntialiasingLevel > 0) {
         addMacroLine(sb, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
      }

      if (Shaders.configNormalMap) {
         addMacroLine(sb, "MC_NORMAL_MAP");
      }

      if (Shaders.configSpecularMap) {
         addMacroLine(sb, "MC_SPECULAR_MAP");
      }

      addMacroLine(sb, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
      addMacroLine(sb, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
      addMacroLine(sb, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
      if (Shaders.isOldHandLight()) {
         addMacroLine(sb, "MC_OLD_HAND_LIGHT");
      }

      if (Shaders.isOldLighting()) {
         addMacroLine(sb, "MC_OLD_LIGHTING");
      }

      return sb.toString();
   }

   private static void addMacroLine(StringBuilder sb, String name, int value) {
      sb.append("#define ");
      sb.append(name);
      sb.append(" ");
      sb.append(value);
      sb.append("\n");
   }

   private static void addMacroLine(StringBuilder sb, String name, float value) {
      sb.append("#define ");
      sb.append(name);
      sb.append(" ");
      sb.append(value);
      sb.append("\n");
   }

   private static void addMacroLine(StringBuilder sb, String name) {
      sb.append("#define ");
      sb.append(name);
      sb.append("\n");
   }
}
