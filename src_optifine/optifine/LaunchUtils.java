package optifine;

import java.lang.reflect.Field;
import java.util.Map;

public class LaunchUtils {
   private static Boolean forgeServer = null;

   public static boolean isForgeServer() {
      if (forgeServer == null) {
         try {
            Class cls = Class.forName("net.minecraft.launchwrapper.Launch");
            Field fieldBlackboard = cls.getField("blackboard");
            Map blackboard = (Map)fieldBlackboard.get((Object)null);
            Map launchArgs = (Map)blackboard.get("launchArgs");
            String accessToken = (String)launchArgs.get("--accessToken");
            String version = (String)launchArgs.get("--version");
            boolean onServer = accessToken == null && Utils.equals(version, "UnknownFMLProfile");
            forgeServer = onServer;
         } catch (Throwable var7) {
            System.out.println("Error checking Forge server: " + var7.getClass().getName() + ": " + var7.getMessage());
            forgeServer = Boolean.FALSE;
         }
      }

      return forgeServer.booleanValue();
   }
}
