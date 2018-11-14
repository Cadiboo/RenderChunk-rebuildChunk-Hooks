package net.optifine.shaders;

import ceb.a;

public class SVertexAttrib {
   public int index;
   public int count;
   public a type;
   public int offset;

   public SVertexAttrib(int index, int count, a type) {
      this.index = index;
      this.count = count;
      this.type = type;
   }
}
