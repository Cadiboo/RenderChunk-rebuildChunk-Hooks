package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import net.optifine.util.MathUtils;

public class CustomPanorama {
   private static CustomPanoramaProperties customPanoramaProperties = null;
   private static final Random random = new Random();

   public static CustomPanoramaProperties getCustomPanoramaProperties() {
      return customPanoramaProperties;
   }

   public static void update() {
      customPanoramaProperties = null;
      String[] folders = getPanoramaFolders();
      if (folders.length > 1) {
         Properties[] properties = getPanoramaProperties(folders);
         int[] weights = getWeights(properties);
         int index = getRandomIndex(weights);
         String folder = folders[index];
         Properties props = properties[index];
         if (props == null) {
            props = properties[0];
         }

         if (props == null) {
            props = new Properties();
         }

         CustomPanoramaProperties cpp = new CustomPanoramaProperties(folder, props);
         customPanoramaProperties = cpp;
      }
   }

   private static String[] getPanoramaFolders() {
      List listFolders = new ArrayList();
      listFolders.add("textures/gui/title/background");

      for(int i = 0; i < 100; ++i) {
         String folder = "optifine/gui/background" + i;
         String path = folder + "/panorama_0.png";
         nf loc = new nf(path);
         if (.Config.hasResource(loc)) {
            listFolders.add(folder);
         }
      }

      String[] folders = (String[])((String[])listFolders.toArray(new String[listFolders.size()]));
      return folders;
   }

   private static Properties[] getPanoramaProperties(String[] folders) {
      Properties[] propsArr = new Properties[folders.length];

      for(int i = 0; i < folders.length; ++i) {
         String folder = folders[i];
         if (i == 0) {
            folder = "optifine/gui";
         } else {
            .Config.dbg("CustomPanorama: " + folder);
         }

         nf propLoc = new nf(folder + "/background.properties");

         try {
            InputStream in = .Config.getResourceStream(propLoc);
            if (in != null) {
               Properties props = new Properties();
               props.load(in);
               .Config.dbg("CustomPanorama: " + propLoc.a());
               propsArr[i] = props;
               in.close();
            }
         } catch (IOException var7) {
            ;
         }
      }

      return propsArr;
   }

   private static int[] getWeights(Properties[] properties) {
      int[] weights = new int[properties.length];

      for(int i = 0; i < weights.length; ++i) {
         Properties prop = properties[i];
         if (prop == null) {
            prop = properties[0];
         }

         if (prop == null) {
            weights[i] = 1;
         } else {
            String str = prop.getProperty("weight", (String)null);
            weights[i] = .Config.parseInt(str, 1);
         }
      }

      return weights;
   }

   private static int getRandomIndex(int[] weights) {
      int sumWeights = MathUtils.getSum(weights);
      int r = random.nextInt(sumWeights);
      int sum = 0;

      for(int i = 0; i < weights.length; ++i) {
         sum += weights[i];
         if (sum > r) {
            return i;
         }
      }

      return weights.length - 1;
   }
}
