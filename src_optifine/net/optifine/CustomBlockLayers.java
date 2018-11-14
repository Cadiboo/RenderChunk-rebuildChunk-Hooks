package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;

public class CustomBlockLayers {
   private static amm[] renderLayers = null;
   public static boolean active = false;

   public static amm getRenderLayer(awt blockState) {
      if (renderLayers == null) {
         return null;
      } else if (blockState.p()) {
         return null;
      } else if (!(blockState instanceof awp)) {
         return null;
      } else {
         awp bsb = (awp)blockState;
         int id = bsb.getBlockId();
         return id > 0 && id < renderLayers.length ? renderLayers[id] : null;
      }
   }

   public static void update() {
      renderLayers = null;
      active = false;
      List list = new ArrayList();
      String pathProps = "optifine/block.properties";
      Properties props = ResUtils.readProperties(pathProps, "CustomBlockLayers");
      if (props != null) {
         readLayers(pathProps, props, list);
      }

      if (.Config.isShaders()) {
         String pathPropsShaders = "/shaders/block.properties";

         try {
            InputStream in = Shaders.getShaderPackResourceStream(pathPropsShaders);
            if (in != null) {
               Properties propsShaders = new PropertiesOrdered();
               propsShaders.load(in);
               in.close();
               readLayers(pathPropsShaders, propsShaders, list);
            }
         } catch (IOException var6) {
            .Config.warn("CustomBlocks: Error reading " + pathPropsShaders);
         }
      }

      if (!list.isEmpty()) {
         renderLayers = (amm[])list.toArray(new amm[list.size()]);
         active = true;
      }
   }

   private static void readLayers(String pathProps, Properties props, List list) {
      .Config.dbg("CustomBlockLayers: " + pathProps);
      readLayer("solid", amm.a, props, list);
      readLayer("cutout", amm.c, props, list);
      readLayer("cutout_mipped", amm.b, props, list);
      readLayer("translucent", amm.d, props, list);
   }

   private static void readLayer(String name, amm layer, Properties props, List listLayers) {
      String key = "layer." + name;
      String val = props.getProperty(key);
      if (val != null) {
         ConnectedParser cp = new ConnectedParser("CustomBlockLayers");
         MatchBlock[] mbs = cp.parseMatchBlocks(val);
         if (mbs != null) {
            for(int i = 0; i < mbs.length; ++i) {
               MatchBlock mb = mbs[i];
               int blockId = mb.getBlockId();
               if (blockId > 0) {
                  while(listLayers.size() < blockId + 1) {
                     listLayers.add((Object)null);
                  }

                  if (listLayers.get(blockId) != null) {
                     .Config.warn("CustomBlockLayers: Block layer is already set, block: " + blockId + ", layer: " + name);
                  }

                  listLayers.set(blockId, layer);
               }
            }

         }
      }
   }

   public static boolean isActive() {
      return active;
   }
}
