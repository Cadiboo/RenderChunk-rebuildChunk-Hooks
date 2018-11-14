package net.optifine.player;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.optifine.http.IFileDownloadListener;

public class PlayerConfigurationReceiver implements IFileDownloadListener {
   private String player = null;

   public PlayerConfigurationReceiver(String player) {
      this.player = player;
   }

   public void fileDownloadFinished(String url, byte[] bytes, Throwable exception) {
      if (bytes != null) {
         try {
            String str = new String(bytes, "ASCII");
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(str);
            PlayerConfigurationParser pcp = new PlayerConfigurationParser(this.player);
            PlayerConfiguration pc = pcp.parsePlayerConfiguration(je);
            if (pc != null) {
               pc.setInitialized(true);
               PlayerConfigurations.setPlayerConfiguration(this.player, pc);
            }
         } catch (Exception var9) {
            .Config.dbg("Error parsing configuration: " + url + ", " + var9.getClass().getName() + ": " + var9.getMessage());
         }

      }
   }
}
