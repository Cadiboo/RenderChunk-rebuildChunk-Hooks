package net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ShadowUtils {
   public static Iterator makeShadowChunkIterator(bsb world, double partialTicks, vg viewEntity, int renderDistanceChunks, bvh viewFrustum) {
      float shadowRenderDistance = Shaders.getShadowRenderDistance();
      if (shadowRenderDistance > 0.0F && shadowRenderDistance < (float)((renderDistanceChunks - 1) * 16)) {
         int shadowDistanceChunks = rk.f(shadowRenderDistance / 16.0F) + 1;
         float car = world.d((float)partialTicks);
         float sunTiltRad = Shaders.sunPathRotation * 0.017453292F;
         float sar = car > 1.5707964F && car < 4.712389F ? car + 3.1415927F : car;
         float dx = -rk.a(sar);
         float dy = rk.b(sar) * rk.b(sunTiltRad);
         float dz = -rk.b(sar) * rk.a(sunTiltRad);
         et posEntity = new et(rk.c(viewEntity.p) >> 4, rk.c(viewEntity.q) >> 4, rk.c(viewEntity.r) >> 4);
         et posStart = posEntity.a((double)(-dx * (float)shadowDistanceChunks), (double)(-dy * (float)shadowDistanceChunks), (double)(-dz * (float)shadowDistanceChunks));
         et posEnd = posEntity.a((double)(dx * (float)renderDistanceChunks), (double)(dy * (float)renderDistanceChunks), (double)(dz * (float)renderDistanceChunks));
         IteratorRenderChunks it = new IteratorRenderChunks(viewFrustum, posStart, posEnd, shadowDistanceChunks, shadowDistanceChunks);
         return it;
      } else {
         List listChunks = Arrays.asList(viewFrustum.f);
         Iterator it = listChunks.iterator();
         return it;
      }
   }
}
