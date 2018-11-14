package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityClassLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;

public class DynamicLights {
   private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
   private static Map mapEntityLightLevels = new HashMap();
   private static Map mapItemLightLevels = new HashMap();
   private static long timeUpdateMs = 0L;
   private static final double MAX_DIST = 7.5D;
   private static final double MAX_DIST_SQ = 56.25D;
   private static final int LIGHT_LEVEL_MAX = 15;
   private static final int LIGHT_LEVEL_FIRE = 15;
   private static final int LIGHT_LEVEL_BLAZE = 10;
   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
   private static final my PARAMETER_ITEM_STACK;
   private static boolean initialized;

   public static void entityAdded(vg entityIn, buy renderGlobal) {
   }

   public static void entityRemoved(vg entityIn, buy renderGlobal) {
      DynamicLightsMap var2 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.S());
         if (dynamicLight != null) {
            dynamicLight.updateLitChunks(renderGlobal);
         }

      }
   }

   public static void update(buy renderGlobal) {
      long timeNowMs = System.currentTimeMillis();
      if (timeNowMs >= timeUpdateMs + 50L) {
         timeUpdateMs = timeNowMs;
         if (!initialized) {
            initialize();
         }

         DynamicLightsMap var3 = mapDynamicLights;
         synchronized(mapDynamicLights) {
            updateMapDynamicLights(renderGlobal);
            if (mapDynamicLights.size() > 0) {
               List dynamicLights = mapDynamicLights.valueList();

               for(int i = 0; i < dynamicLights.size(); ++i) {
                  DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
                  dynamicLight.update(renderGlobal);
               }

            }
         }
      }
   }

   private static void initialize() {
      initialized = true;
      mapEntityLightLevels.clear();
      mapItemLightLevels.clear();
      String[] modIds = ReflectorForge.getForgeModIds();

      for(int i = 0; i < modIds.length; ++i) {
         String modId = modIds[i];

         try {
            nf loc = new nf(modId, "optifine/dynamic_lights.properties");
            InputStream in = .Config.getResourceStream(loc);
            loadModConfiguration(in, loc.toString(), modId);
         } catch (IOException var5) {
            ;
         }
      }

      if (mapEntityLightLevels.size() > 0) {
         .Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
      }

      if (mapItemLightLevels.size() > 0) {
         .Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
      }

   }

   private static void loadModConfiguration(InputStream in, String path, String modId) {
      if (in != null) {
         try {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            .Config.dbg("DynamicLights: Parsing " + path);
            ConnectedParser cp = new ConnectedParser("DynamicLights");
            loadModLightLevels(props.getProperty("entities"), mapEntityLightLevels, new EntityClassLocator(), cp, path, modId);
            loadModLightLevels(props.getProperty("items"), mapItemLightLevels, new ItemLocator(), cp, path, modId);
         } catch (IOException var5) {
            .Config.warn("DynamicLights: Error reading " + path);
         }

      }
   }

   private static void loadModLightLevels(String prop, Map mapLightLevels, IObjectLocator ol, ConnectedParser cp, String path, String modId) {
      if (prop != null) {
         String[] parts = .Config.tokenize(prop, " ");

         for(int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            String[] tokens = .Config.tokenize(part, ":");
            if (tokens.length != 2) {
               cp.warn("Invalid entry: " + part + ", in:" + path);
            } else {
               String name = tokens[0];
               String light = tokens[1];
               String nameFull = modId + ":" + name;
               nf loc = new nf(nameFull);
               Object obj = ol.getObject(loc);
               if (obj == null) {
                  cp.warn("Object not found: " + nameFull);
               } else {
                  int lightLevel = cp.parseInt(light, -1);
                  if (lightLevel >= 0 && lightLevel <= 15) {
                     mapLightLevels.put(obj, new Integer(lightLevel));
                  } else {
                     cp.warn("Invalid light level: " + part);
                  }
               }
            }
         }

      }
   }

   private static void updateMapDynamicLights(buy renderGlobal) {
      amu world = renderGlobal.getWorld();
      if (world != null) {
         List entities = world.L();
         Iterator it = entities.iterator();

         while(it.hasNext()) {
            vg entity = (vg)it.next();
            int lightLevel = getLightLevel(entity);
            int key;
            DynamicLight dynamicLight;
            if (lightLevel > 0) {
               key = entity.S();
               dynamicLight = mapDynamicLights.get(key);
               if (dynamicLight == null) {
                  dynamicLight = new DynamicLight(entity);
                  mapDynamicLights.put(key, dynamicLight);
               }
            } else {
               key = entity.S();
               dynamicLight = mapDynamicLights.remove(key);
               if (dynamicLight != null) {
                  dynamicLight.updateLitChunks(renderGlobal);
               }
            }
         }

      }
   }

   public static int getCombinedLight(et pos, int combinedLight) {
      double lightPlayer = getLightLevel(pos);
      combinedLight = getCombinedLight(lightPlayer, combinedLight);
      return combinedLight;
   }

   public static int getCombinedLight(vg entity, int combinedLight) {
      double lightPlayer = (double)getLightLevel(entity);
      combinedLight = getCombinedLight(lightPlayer, combinedLight);
      return combinedLight;
   }

   public static int getCombinedLight(double lightPlayer, int combinedLight) {
      if (lightPlayer > 0.0D) {
         int lightPlayerFF = (int)(lightPlayer * 16.0D);
         int lightBlockFF = combinedLight & 255;
         if (lightPlayerFF > lightBlockFF) {
            combinedLight &= -256;
            combinedLight |= lightPlayerFF;
         }
      }

      return combinedLight;
   }

   public static double getLightLevel(et pos) {
      double lightLevelMax = 0.0D;
      DynamicLightsMap var3 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         List dynamicLights = mapDynamicLights.valueList();

         for(int i = 0; i < dynamicLights.size(); ++i) {
            DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
            int dynamicLightLevel = dynamicLight.getLastLightLevel();
            if (dynamicLightLevel > 0) {
               double px = dynamicLight.getLastPosX();
               double py = dynamicLight.getLastPosY();
               double pz = dynamicLight.getLastPosZ();
               double dx = (double)pos.p() - px;
               double dy = (double)pos.q() - py;
               double dz = (double)pos.r() - pz;
               double distSq = dx * dx + dy * dy + dz * dz;
               if (dynamicLight.isUnderwater() && !.Config.isClearWater()) {
                  dynamicLightLevel = .Config.limit(dynamicLightLevel - 2, 0, 15);
                  distSq *= 2.0D;
               }

               if (distSq <= 56.25D) {
                  double dist = Math.sqrt(distSq);
                  double light = 1.0D - dist / 7.5D;
                  double lightLevel = light * (double)dynamicLightLevel;
                  if (lightLevel > lightLevelMax) {
                     lightLevelMax = lightLevel;
                  }
               }
            }
         }
      }

      double lightPlayer = .Config.limit(lightLevelMax, 0.0D, 15.0D);
      return lightPlayer;
   }

   public static int getLightLevel(aip itemStack) {
      if (itemStack == null) {
         return 0;
      } else {
         ain item = itemStack.c();
         if (item instanceof ahb) {
            ahb itemBlock = (ahb)item;
            aow block = itemBlock.d();
            if (block != null) {
               return block.o(block.t());
            }
         }

         if (item == air.aB) {
            return aox.l.o(aox.l.t());
         } else if (item != air.bD && item != air.bO) {
            if (item == air.bb) {
               return 8;
            } else if (item == air.cO) {
               return 8;
            } else if (item == air.bP) {
               return 8;
            } else if (item == air.ck) {
               return aox.bY.o(aox.bY.t()) / 2;
            } else {
               if (!mapItemLightLevels.isEmpty()) {
                  Integer level = (Integer)mapItemLightLevels.get(item);
                  if (level != null) {
                     return level.intValue();
                  }
               }

               return 0;
            }
         } else {
            return 10;
         }
      }
   }

   public static int getLightLevel(vg entity) {
      if (entity == .Config.getMinecraft().aa() && !.Config.isDynamicHandLight()) {
         return 0;
      } else {
         if (entity instanceof aed) {
            aed player = (aed)entity;
            if (player.y()) {
               return 0;
            }
         }

         if (entity.aR()) {
            return 15;
         } else {
            if (!mapEntityLightLevels.isEmpty()) {
               Integer level = (Integer)mapEntityLightLevels.get(entity.getClass());
               if (level != null) {
                  return level.intValue();
               }
            }

            if (entity instanceof ael) {
               return 15;
            } else if (entity instanceof acm) {
               return 15;
            } else if (entity instanceof acq) {
               acq entityBlaze = (acq)entity;
               return entityBlaze.p() ? 15 : 10;
            } else if (entity instanceof add) {
               add emc = (add)entity;
               return (double)emc.b > 0.6D ? 13 : 8;
            } else {
               if (entity instanceof acs) {
                  acs entityCreeper = (acs)entity;
                  if ((double)entityCreeper.a(0.0F) > 0.001D) {
                     return 15;
                  }
               }

               aip itemStack;
               if (entity instanceof vp) {
                  vp player = (vp)entity;
                  itemStack = player.co();
                  int levelMain = getLightLevel(itemStack);
                  aip stackOff = player.cp();
                  int levelOff = getLightLevel(stackOff);
                  aip stackHead = player.b(vl.f);
                  int levelHead = getLightLevel(stackHead);
                  int levelHandMax = Math.max(levelMain, levelOff);
                  return Math.max(levelHandMax, levelHead);
               } else if (entity instanceof acl) {
                  acl entityItem = (acl)entity;
                  itemStack = getItemStack(entityItem);
                  return getLightLevel(itemStack);
               } else {
                  return 0;
               }
            }
         }
      }
   }

   public static void removeLights(buy renderGlobal) {
      DynamicLightsMap var1 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         List dynamicLights = mapDynamicLights.valueList();

         for(int i = 0; i < dynamicLights.size(); ++i) {
            DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
            dynamicLight.updateLitChunks(renderGlobal);
         }

         dynamicLights.clear();
      }
   }

   public static void clear() {
      DynamicLightsMap var0 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         mapDynamicLights.clear();
      }
   }

   public static int getCount() {
      DynamicLightsMap var0 = mapDynamicLights;
      synchronized(mapDynamicLights) {
         return mapDynamicLights.size();
      }
   }

   public static aip getItemStack(acl entityItem) {
      aip itemstack = (aip)entityItem.V().a(PARAMETER_ITEM_STACK);
      return itemstack;
   }

   static {
      PARAMETER_ITEM_STACK = new my(6, na.f);
   }
}
