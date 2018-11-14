package net.optifine;

import art.a;

public class BetterSnow {
   private static cfy modelSnowLayer = null;

   public static void update() {
      modelSnowLayer = .Config.getMinecraft().ab().a().b(aox.aH.t());
   }

   public static cfy getModelSnowLayer() {
      return modelSnowLayer;
   }

   public static awt getStateSnowLayer() {
      return aox.aH.t();
   }

   public static boolean shouldRender(amy blockAccess, awt blockState, et blockPos) {
      aow block = blockState.u();
      return !checkBlock(block, blockState) ? false : hasSnowNeighbours(blockAccess, blockPos);
   }

   private static boolean hasSnowNeighbours(amy blockAccess, et pos) {
      aow blockSnow = aox.aH;
      return blockAccess.o(pos.c()).u() != blockSnow && blockAccess.o(pos.d()).u() != blockSnow && blockAccess.o(pos.e()).u() != blockSnow && blockAccess.o(pos.f()).u() != blockSnow ? false : blockAccess.o(pos.b()).p();
   }

   private static boolean checkBlock(aow block, awt blockState) {
      if (blockState.g()) {
         return false;
      } else if (blockState.p()) {
         return false;
      } else if (block instanceof atw) {
         return false;
      } else if (block instanceof apc && (block instanceof aqb || block instanceof aqr || block instanceof asb || block instanceof atp || block instanceof aun)) {
         return true;
      } else if (!(block instanceof aqo) && !(block instanceof aqp) && !(block instanceof aqs) && !(block instanceof auo) && !(block instanceof ati) && !(block instanceof auv)) {
         if (block instanceof ath && blockState.c(auq.a) == fa.b) {
            return true;
         } else {
            if (block instanceof art) {
               Object orient = blockState.c(art.a);
               if (orient == a.g || orient == a.f) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }
}
