package net.optifine.shaders;

import ceb.a;
import ceb.b;

public class SVertexFormat {
   public static final int vertexSizeBlock = 14;
   public static final int offsetMidTexCoord = 8;
   public static final int offsetTangent = 10;
   public static final int offsetEntity = 12;
   public static final cea defVertexFormatTextured = makeDefVertexFormatTextured();

   public static cea makeDefVertexFormatBlock() {
      cea vf = new cea();
      vf.a(new ceb(0, a.a, b.a, 3));
      vf.a(new ceb(0, a.b, b.c, 4));
      vf.a(new ceb(0, a.a, b.d, 2));
      vf.a(new ceb(1, a.e, b.d, 2));
      vf.a(new ceb(0, a.c, b.b, 3));
      vf.a(new ceb(0, a.c, b.g, 1));
      vf.a(new ceb(0, a.a, b.g, 2));
      vf.a(new ceb(0, a.e, b.g, 4));
      vf.a(new ceb(0, a.e, b.g, 4));
      return vf;
   }

   public static cea makeDefVertexFormatItem() {
      cea vf = new cea();
      vf.a(new ceb(0, a.a, b.a, 3));
      vf.a(new ceb(0, a.b, b.c, 4));
      vf.a(new ceb(0, a.a, b.d, 2));
      vf.a(new ceb(0, a.e, b.g, 2));
      vf.a(new ceb(0, a.c, b.b, 3));
      vf.a(new ceb(0, a.c, b.g, 1));
      vf.a(new ceb(0, a.a, b.g, 2));
      vf.a(new ceb(0, a.e, b.g, 4));
      vf.a(new ceb(0, a.e, b.g, 4));
      return vf;
   }

   public static cea makeDefVertexFormatTextured() {
      cea vf = new cea();
      vf.a(new ceb(0, a.a, b.a, 3));
      vf.a(new ceb(0, a.b, b.g, 4));
      vf.a(new ceb(0, a.a, b.d, 2));
      vf.a(new ceb(0, a.e, b.g, 2));
      vf.a(new ceb(0, a.c, b.b, 3));
      vf.a(new ceb(0, a.c, b.g, 1));
      vf.a(new ceb(0, a.a, b.g, 2));
      vf.a(new ceb(0, a.e, b.g, 4));
      vf.a(new ceb(0, a.e, b.g, 4));
      return vf;
   }

   public static void setDefBakedFormat(cea vf) {
      if (vf != null) {
         vf.a();
         vf.a(new ceb(0, a.a, b.a, 3));
         vf.a(new ceb(0, a.b, b.c, 4));
         vf.a(new ceb(0, a.a, b.d, 2));
         vf.a(new ceb(0, a.e, b.g, 2));
         vf.a(new ceb(0, a.c, b.b, 3));
         vf.a(new ceb(0, a.c, b.g, 1));
         vf.a(new ceb(0, a.a, b.g, 2));
         vf.a(new ceb(0, a.e, b.g, 4));
         vf.a(new ceb(0, a.e, b.g, 4));
      }
   }

   public static cea duplicate(cea src) {
      if (src == null) {
         return null;
      } else {
         cea dst = new cea();
         copy(src, dst);
         return dst;
      }
   }

   public static void copy(cea src, cea dst) {
      if (src != null && dst != null) {
         dst.a();

         for(int i = 0; i < src.i(); ++i) {
            dst.a(src.c(i));
         }

      }
   }
}
