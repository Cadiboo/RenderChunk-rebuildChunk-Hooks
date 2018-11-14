package net.optifine.player;

import java.util.HashMap;
import java.util.Map;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;

public class PlayerConfigurations {
   private static Map mapConfigurations = null;
   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();

   public static void renderPlayerItems(bpx modelBiped, bua player, float scale, float partialTicks) {
      PlayerConfiguration cfg = getPlayerConfiguration(player);
      if (cfg != null) {
         cfg.renderPlayerItems(modelBiped, player, scale, partialTicks);
      }

   }

   public static synchronized PlayerConfiguration getPlayerConfiguration(bua player) {
      if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
         bua currentPlayer = bib.z().h;
         if (currentPlayer != null) {
            setPlayerConfiguration(currentPlayer.getNameClear(), (PlayerConfiguration)null);
            timeReloadPlayerItemsMs = System.currentTimeMillis();
         }
      }

      String name = player.getNameClear();
      if (name == null) {
         return null;
      } else {
         PlayerConfiguration pc = (PlayerConfiguration)getMapConfigurations().get(name);
         if (pc == null) {
            pc = new PlayerConfiguration();
            getMapConfigurations().put(name, pc);
            PlayerConfigurationReceiver pcl = new PlayerConfigurationReceiver(name);
            String url = HttpUtils.getPlayerItemsUrl() + "/users/" + name + ".cfg";
            FileDownloadThread fdt = new FileDownloadThread(url, pcl);
            fdt.start();
         }

         return pc;
      }
   }

   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
      getMapConfigurations().put(player, pc);
   }

   private static Map getMapConfigurations() {
      if (mapConfigurations == null) {
         mapConfigurations = new HashMap();
      }

      return mapConfigurations;
   }
}
