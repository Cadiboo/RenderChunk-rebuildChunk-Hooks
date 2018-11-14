package net.optifine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ModelUtils {
   public static void dbgModel(cfy model) {
      if (model != null) {
         .Config.dbg("Model: " + model + ", ao: " + model.a() + ", gui3d: " + model.b() + ", builtIn: " + model.c() + ", particle: " + model.d());
         fa[] faces = fa.n;

         for(int i = 0; i < faces.length; ++i) {
            fa face = faces[i];
            List faceQuads = model.a((awt)null, face, 0L);
            dbgQuads(face.m(), faceQuads, "  ");
         }

         List generalQuads = model.a((awt)null, (fa)null, 0L);
         dbgQuads("General", generalQuads, "  ");
      }
   }

   private static void dbgQuads(String name, List quads, String prefix) {
      Iterator it = quads.iterator();

      while(it.hasNext()) {
         bvp quad = (bvp)it.next();
         dbgQuad(name, quad, prefix);
      }

   }

   public static void dbgQuad(String name, bvp quad, String prefix) {
      .Config.dbg(prefix + "Quad: " + quad.getClass().getName() + ", type: " + name + ", face: " + quad.e() + ", tint: " + quad.d() + ", sprite: " + quad.a());
      dbgVertexData(quad.b(), "  " + prefix);
   }

   public static void dbgVertexData(int[] vd, String prefix) {
      int step = vd.length / 4;
      .Config.dbg(prefix + "Length: " + vd.length + ", step: " + step);

      for(int i = 0; i < 4; ++i) {
         int pos = i * step;
         float x = Float.intBitsToFloat(vd[pos + 0]);
         float y = Float.intBitsToFloat(vd[pos + 1]);
         float z = Float.intBitsToFloat(vd[pos + 2]);
         int col = vd[pos + 3];
         float u = Float.intBitsToFloat(vd[pos + 4]);
         float v = Float.intBitsToFloat(vd[pos + 5]);
         .Config.dbg(prefix + i + " xyz: " + x + "," + y + "," + z + " col: " + col + " u,v: " + u + "," + v);
      }

   }

   public static cfy duplicateModel(cfy model) {
      List generalQuads2 = duplicateQuadList(model.a((awt)null, (fa)null, 0L));
      fa[] faces = fa.n;
      Map faceQuads2 = new HashMap();

      for(int i = 0; i < faces.length; ++i) {
         fa face = faces[i];
         List quads = model.a((awt)null, face, 0L);
         List quads2 = duplicateQuadList(quads);
         faceQuads2.put(face, quads2);
      }

      cgf model2 = new cgf(generalQuads2, faceQuads2, model.a(), model.b(), model.d(), model.e(), model.f());
      return model2;
   }

   public static List duplicateQuadList(List list) {
      List list2 = new ArrayList();
      Iterator it = list.iterator();

      while(it.hasNext()) {
         bvp quad = (bvp)it.next();
         bvp quad2 = duplicateQuad(quad);
         list2.add(quad2);
      }

      return list2;
   }

   public static bvp duplicateQuad(bvp quad) {
      bvp quad2 = new bvp((int[])quad.b().clone(), quad.d(), quad.e(), quad.a());
      return quad2;
   }
}
