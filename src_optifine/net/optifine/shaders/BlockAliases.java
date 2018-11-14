package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class BlockAliases {
   private static BlockAlias[][] blockAliases = (BlockAlias[][])null;
   private static boolean updateOnResourcesReloaded;

   public static int getMappedBlockId(int blockId, int metadata) {
      if (blockAliases == null) {
         return blockId;
      } else if (blockId >= 0 && blockId < blockAliases.length) {
         BlockAlias[] aliases = blockAliases[blockId];
         if (aliases == null) {
            return blockId;
         } else {
            for(int i = 0; i < aliases.length; ++i) {
               BlockAlias ba = aliases[i];
               if (ba.matches(blockId, metadata)) {
                  return ba.getBlockId();
               }
            }

            return blockId;
         }
      } else {
         return blockId;
      }
   }

   public static void resourcesReloaded() {
      if (updateOnResourcesReloaded) {
         updateOnResourcesReloaded = false;
         update(Shaders.getShaderPack());
      }
   }

   public static void update(IShaderPack shaderPack) {
      reset();
      if (shaderPack != null) {
         if (Reflector.Loader_getActiveModList.exists() && bib.z().P() == null) {
            .Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
            updateOnResourcesReloaded = true;
         } else {
            List listBlockAliases = new ArrayList();
            String path = "/shaders/block.properties";
            InputStream in = shaderPack.getResourceAsStream(path);
            if (in != null) {
               loadBlockAliases(in, path, listBlockAliases);
            }

            loadModBlockAliases(listBlockAliases);
            if (listBlockAliases.size() > 0) {
               blockAliases = toArrays(listBlockAliases);
            }
         }
      }
   }

   private static void loadModBlockAliases(List listBlockAliases) {
      String[] modIds = ReflectorForge.getForgeModIds();

      for(int i = 0; i < modIds.length; ++i) {
         String modId = modIds[i];

         try {
            nf loc = new nf(modId, "shaders/block.properties");
            InputStream in = .Config.getResourceStream(loc);
            loadBlockAliases(in, loc.toString(), listBlockAliases);
         } catch (IOException var6) {
            ;
         }
      }

   }

   private static void loadBlockAliases(InputStream in, String path, List listBlockAliases) {
      if (in != null) {
         try {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            .Config.dbg("[Shaders] Parsing block mappings: " + path);
            ConnectedParser cp = new ConnectedParser("Shaders");
            Set keys = props.keySet();
            Iterator it = keys.iterator();

            while(true) {
               while(it.hasNext()) {
                  String key = (String)it.next();
                  String val = props.getProperty(key);
                  String prefix = "block.";
                  if (!key.startsWith(prefix)) {
                     .Config.warn("[Shaders] Invalid block ID: " + key);
                  } else {
                     String blockIdStr = StrUtils.removePrefix(key, prefix);
                     int blockId = .Config.parseInt(blockIdStr, -1);
                     if (blockId < 0) {
                        .Config.warn("[Shaders] Invalid block ID: " + key);
                     } else {
                        MatchBlock[] matchBlocks = cp.parseMatchBlocks(val);
                        if (matchBlocks != null && matchBlocks.length >= 1) {
                           BlockAlias ba = new BlockAlias(blockId, matchBlocks);
                           addToList(listBlockAliases, ba);
                        } else {
                           .Config.warn("[Shaders] Invalid block ID mapping: " + key + "=" + val);
                        }
                     }
                  }
               }

               return;
            }
         } catch (IOException var14) {
            .Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static void addToList(List blocksAliases, BlockAlias ba) {
      int[] blockIds = ba.getMatchBlockIds();

      for(int i = 0; i < blockIds.length; ++i) {
         int blockId = blockIds[i];

         while(blockId >= blocksAliases.size()) {
            blocksAliases.add((Object)null);
         }

         List blockAliases = (List)blocksAliases.get(blockId);
         if (blockAliases == null) {
            blockAliases = new ArrayList();
            blocksAliases.set(blockId, blockAliases);
         }

         BlockAlias baBlock = new BlockAlias(ba.getBlockId(), ba.getMatchBlocks(blockId));
         ((List)blockAliases).add(baBlock);
      }

   }

   private static BlockAlias[][] toArrays(List listBlocksAliases) {
      BlockAlias[][] bas = new BlockAlias[listBlocksAliases.size()][];

      for(int i = 0; i < bas.length; ++i) {
         List listBlockAliases = (List)listBlocksAliases.get(i);
         if (listBlockAliases != null) {
            bas[i] = (BlockAlias[])((BlockAlias[])listBlockAliases.toArray(new BlockAlias[listBlockAliases.size()]));
         }
      }

      return bas;
   }

   public static void reset() {
      blockAliases = (BlockAlias[][])null;
   }
}
