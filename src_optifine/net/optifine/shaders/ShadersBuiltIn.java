package net.optifine.shaders;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ShadersBuiltIn {
   public static Reader getShaderReader(String filename) {
      if (filename.endsWith("/deferred_last.vsh")) {
         return getCompositeShaderReader(true, true);
      } else if (filename.endsWith("/composite_last.vsh")) {
         return getCompositeShaderReader(false, true);
      } else if (filename.endsWith("/deferred_last.fsh")) {
         return getCompositeShaderReader(true, false);
      } else {
         return filename.endsWith("/composite_last.fsh") ? getCompositeShaderReader(false, false) : null;
      }
   }

   private static Reader getCompositeShaderReader(boolean deferred, boolean vertex) {
      if (!hasDeferredPrograms() && !hasSkipClear()) {
         return null;
      } else {
         int[] flipBuffers = getLastFlipBuffers(deferred);
         if (flipBuffers == null) {
            return null;
         } else {
            String shader;
            if (!vertex) {
               shader = deferred ? "deferred" : "composite";
               SMCLog.info("flipped buffers after " + shader + ": " + .Config.arrayToString(flipBuffers));
            }

            if (vertex) {
               shader = getCompositeVertexShader(flipBuffers);
            } else {
               shader = getCompositeFragmentShader(flipBuffers);
            }

            return new StringReader(shader);
         }
      }
   }

   private static boolean hasDeferredPrograms() {
      for(int i = 0; i < Shaders.ProgramsDeferred.length; ++i) {
         if (Shaders.ProgramsDeferred[i].getId() != 0) {
            return true;
         }
      }

      return false;
   }

   private static boolean hasSkipClear() {
      for(int i = 0; i < Shaders.gbuffersClear.length; ++i) {
         if (!Shaders.gbuffersClear[i]) {
            return true;
         }
      }

      return false;
   }

   private static String getCompositeVertexShader(int[] buffers) {
      List list = new ArrayList();
      list.add("#version 120                        ");
      list.add("varying vec2 texcoord;              ");
      list.add("void main()                         ");
      list.add("{                                   ");
      list.add("  gl_Position = ftransform();       ");
      list.add("  texcoord = gl_MultiTexCoord0.xy;  ");
      list.add("}                                   ");
      return .Config.listToString(list, "\n");
   }

   private static String getCompositeFragmentShader(int[] buffers) {
      List list = new ArrayList();
      String drawBuffers = .Config.arrayToString(buffers, "");
      list.add("#version 120                                           ");

      int i;
      for(i = 0; i < buffers.length; ++i) {
         list.add("uniform sampler2D colortex" + buffers[i] + ";        ");
      }

      list.add("varying vec2 texcoord;                                 ");
      list.add("/* DRAWBUFFERS:" + drawBuffers + " */                  ");
      list.add("void main()                                            ");
      list.add("{                                                      ");

      for(i = 0; i < buffers.length; ++i) {
         list.add("  gl_FragData[" + i + "] = texture2D(colortex" + buffers[i] + ", texcoord);     ");
      }

      list.add("}                                                      ");
      return .Config.listToString(list, "\n");
   }

   private static int[] getLastFlipBuffers(boolean deferred) {
      return deferred ? getLastFlipBuffers(Shaders.ProgramsDeferred) : getLastFlipBuffers(Shaders.ProgramsComposite);
   }

   private static int[] getLastFlipBuffers(Program[] programs) {
      List list = new ArrayList();
      boolean[] toggled = new boolean[8];

      int t;
      for(t = 0; t < programs.length; ++t) {
         if (programs[t].getId() != 0) {
            boolean[] togglesTexture = programs[t].getToggleColorTextures();

            for(int t = 0; t < togglesTexture.length; ++t) {
               boolean toggle = togglesTexture[t];
               if (toggle) {
                  toggled[t] = !toggled[t];
               }
            }
         }
      }

      for(t = 0; t < toggled.length; ++t) {
         boolean toggle = toggled[t];
         if (toggle) {
            list.add(new Integer(t));
         }
      }

      if (list.isEmpty()) {
         return null;
      } else {
         Integer[] arr = (Integer[])list.toArray(new Integer[list.size()]);
         return .Config.toPrimitive(arr);
      }
   }
}
