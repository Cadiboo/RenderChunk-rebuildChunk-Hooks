package net.optifine.util;

import axw.a;
import java.util.UUID;

public class IntegratedServerUtils {
   public static oo getWorldServer() {
      bib mc = .Config.getMinecraft();
      amu world = mc.f;
      if (world == null) {
         return null;
      } else if (!mc.D()) {
         return null;
      } else {
         chd is = mc.F();
         if (is == null) {
            return null;
         } else {
            aym wp = world.s;
            if (wp == null) {
               return null;
            } else {
               ayn wd = wp.q();

               try {
                  oo ws = is.a(wd.a());
                  return ws;
               } catch (NullPointerException var6) {
                  return null;
               }
            }
         }
      }
   }

   public static vg getEntity(UUID uuid) {
      oo ws = getWorldServer();
      if (ws == null) {
         return null;
      } else {
         vg e = ws.a(uuid);
         return e;
      }
   }

   public static avj getTileEntity(et pos) {
      oo ws = getWorldServer();
      if (ws == null) {
         return null;
      } else {
         axw chunk = ws.r().a(pos.p() >> 4, pos.r() >> 4);
         if (chunk == null) {
            return null;
         } else {
            avj te = chunk.a(pos, a.c);
            return te;
         }
      }
   }
}
