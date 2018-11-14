package net.optifine;

public class ClearWater {
   public static void updateWaterOpacity(bid settings, amu world) {
      if (settings != null) {
         int opacity = 3;
         if (settings.ofClearWater) {
            opacity = 1;
         }

         aom.setLightOpacity(aox.j, opacity);
         aom.setLightOpacity(aox.i, opacity);
      }

      if (world != null) {
         axr cp = world.B();
         if (cp != null) {
            vg rve = .Config.getMinecraft().aa();
            if (rve != null) {
               int cViewX = (int)rve.p / 16;
               int cViewZ = (int)rve.r / 16;
               int cXMin = cViewX - 512;
               int cXMax = cViewX + 512;
               int cZMin = cViewZ - 512;
               int cZMax = cViewZ + 512;
               int countUpdated = 0;

               for(int cx = cXMin; cx < cXMax; ++cx) {
                  for(int cz = cZMin; cz < cZMax; ++cz) {
                     axw c = cp.a(cx, cz);
                     if (c != null && !(c instanceof axt)) {
                        int x0 = cx << 4;
                        int z0 = cz << 4;
                        int x1 = x0 + 16;
                        int z1 = z0 + 16;
                        BlockPosM posXZ = new BlockPosM(0, 0, 0);
                        BlockPosM posXYZ = new BlockPosM(0, 0, 0);

                        for(int x = x0; x < x1; ++x) {
                           for(int z = z0; z < z1; ++z) {
                              posXZ.setXyz(x, 0, z);
                              et posH = world.p(posXZ);

                              for(int y = 0; y < posH.q(); ++y) {
                                 posXYZ.setXyz(x, y, z);
                                 awt bs = world.o(posXYZ);
                                 if (bs.a() == bcz.h) {
                                    world.a(x, z, posXYZ.q(), posH.q());
                                    ++countUpdated;
                                    break;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               if (countUpdated > 0) {
                  String threadName = "server";
                  if (.Config.isMinecraftThread()) {
                     threadName = "client";
                  }

                  .Config.dbg("ClearWater (" + threadName + ") relighted " + countUpdated + " chunks");
               }

            }
         }
      }
   }
}
