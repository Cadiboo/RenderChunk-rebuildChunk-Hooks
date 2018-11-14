package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorRaw;
import net.optifine.util.IntegratedServerUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class RandomEntities {
   private static Map mapProperties = new HashMap();
   private static boolean active = false;
   private static buy renderGlobal;
   private static RandomEntity randomEntity = new RandomEntity();
   private static bwx tileEntityRendererDispatcher;
   private static RandomTileEntity randomTileEntity = new RandomTileEntity();
   private static boolean working = false;
   public static final String SUFFIX_PNG = ".png";
   public static final String SUFFIX_PROPERTIES = ".properties";
   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
   public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
   public static final String PREFIX_TEXTURES = "textures/";
   public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
   private static final String[] DEPENDANT_SUFFIXES = new String[]{"_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar"};
   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
   private static final String[] HORSE_TEXTURES = (String[])((String[])ReflectorRaw.getFieldValue((Object)null, aaq.class, String[].class, 0));
   private static final String[] HORSE_TEXTURES_ABBR = (String[])((String[])ReflectorRaw.getFieldValue((Object)null, aaq.class, String[].class, 1));

   public static void entityLoaded(vg entity, amu world) {
      if (world != null) {
         nb edm = entity.V();
         edm.spawnPosition = entity.c();
         edm.spawnBiome = world.b(edm.spawnPosition);
         if (entity instanceof aah) {
            aah esr = (aah)entity;
            checkEntityShoulder(esr, false);
         }

         UUID uuid = entity.bm();
         if (entity instanceof ady) {
            updateEntityVillager(uuid, (ady)entity);
         }

      }
   }

   public static void entityUnloaded(vg entity, amu world) {
      if (entity instanceof aah) {
         aah esr = (aah)entity;
         checkEntityShoulder(esr, true);
      }

   }

   private static void checkEntityShoulder(aah entity, boolean attach) {
      vp owner = entity.do();
      if (owner == null) {
         owner = .Config.getMinecraft().h;
      }

      if (owner instanceof bua) {
         bua player = (bua)owner;
         UUID entityUuid = entity.bm();
         if (attach) {
            fy nbtLeft = player.dp();
            if (nbtLeft != null && .Config.equals(nbtLeft.a("UUID"), entityUuid)) {
               player.entityShoulderLeft = entity;
            }

            fy nbtRight = player.dq();
            if (nbtRight != null && .Config.equals(nbtRight.a("UUID"), entityUuid)) {
               player.entityShoulderRight = entity;
            }
         } else {
            nb edm = entity.V();
            nb edmShoulderRight;
            if (player.entityShoulderLeft != null && .Config.equals(player.entityShoulderLeft.bm(), entityUuid)) {
               edmShoulderRight = player.entityShoulderLeft.V();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderLeft = null;
            }

            if (player.entityShoulderRight != null && .Config.equals(player.entityShoulderRight.bm(), entityUuid)) {
               edmShoulderRight = player.entityShoulderRight.V();
               edm.spawnPosition = edmShoulderRight.spawnPosition;
               edm.spawnBiome = edmShoulderRight.spawnBiome;
               player.entityShoulderRight = null;
            }
         }

      }
   }

   private static void updateEntityVillager(UUID uuid, ady ev) {
      vg se = IntegratedServerUtils.getEntity(uuid);
      if (se instanceof ady) {
         ady sev = (ady)se;
         int profSev = sev.dl();
         ev.g(profSev);
         int careerId = Reflector.getFieldValueInt(sev, Reflector.EntityVillager_careerId, 0);
         Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerId, careerId);
         int careerLevel = Reflector.getFieldValueInt(sev, Reflector.EntityVillager_careerLevel, 0);
         Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerLevel, careerLevel);
      }
   }

   public static void worldChanged(amu oldWorld, amu newWorld) {
      if (newWorld != null) {
         List entityList = newWorld.L();

         for(int e = 0; e < entityList.size(); ++e) {
            vg entity = (vg)entityList.get(e);
            entityLoaded(entity, newWorld);
         }
      }

   }

   public static nf getTextureLocation(nf loc) {
      if (!active) {
         return loc;
      } else if (working) {
         return loc;
      } else {
         nf var4;
         try {
            working = true;
            IRandomEntity re = getRandomEntityRendered();
            if (re == null) {
               nf var8 = loc;
               return var8;
            }

            String name = loc.a();
            if (name.startsWith("horse/")) {
               name = getHorseTexturePath(name, "horse/".length());
            }

            if (!name.startsWith("textures/entity/") && !name.startsWith("textures/painting/")) {
               nf var9 = loc;
               return var9;
            }

            RandomEntityProperties props = (RandomEntityProperties)mapProperties.get(name);
            if (props == null) {
               var4 = loc;
               return var4;
            }

            var4 = props.getTextureLocation(loc, re);
         } finally {
            working = false;
         }

         return var4;
      }
   }

   private static String getHorseTexturePath(String path, int pos) {
      if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
         for(int i = 0; i < HORSE_TEXTURES_ABBR.length; ++i) {
            String abbr = HORSE_TEXTURES_ABBR[i];
            if (path.startsWith(abbr, pos)) {
               return HORSE_TEXTURES[i];
            }
         }

         return path;
      } else {
         return path;
      }
   }

   private static IRandomEntity getRandomEntityRendered() {
      if (renderGlobal.renderedEntity != null) {
         randomEntity.setEntity(renderGlobal.renderedEntity);
         return randomEntity;
      } else {
         if (tileEntityRendererDispatcher.tileEntityRendered != null) {
            avj te = tileEntityRendererDispatcher.tileEntityRendered;
            if (te.D() != null) {
               randomTileEntity.setTileEntity(te);
               return randomTileEntity;
            }
         }

         return null;
      }
   }

   private static RandomEntityProperties makeProperties(nf loc, boolean mcpatcher) {
      String path = loc.a();
      nf locProps = getLocationProperties(loc, mcpatcher);
      if (locProps != null) {
         RandomEntityProperties props = parseProperties(locProps, loc);
         if (props != null) {
            return props;
         }
      }

      nf[] variants = getLocationsVariants(loc, mcpatcher);
      return variants == null ? null : new RandomEntityProperties(path, variants);
   }

   private static RandomEntityProperties parseProperties(nf propLoc, nf resLoc) {
      try {
         String path = propLoc.a();
         dbg(resLoc.a() + ", properties: " + path);
         InputStream in = .Config.getResourceStream(propLoc);
         if (in == null) {
            warn("Properties not found: " + path);
            return null;
         } else {
            Properties props = new Properties();
            props.load(in);
            in.close();
            RandomEntityProperties rmp = new RandomEntityProperties(props, path, resLoc);
            return !rmp.isValid(path) ? null : rmp;
         }
      } catch (FileNotFoundException var6) {
         warn("File not found: " + resLoc.a());
         return null;
      } catch (IOException var7) {
         var7.printStackTrace();
         return null;
      }
   }

   private static nf getLocationProperties(nf loc, boolean mcpatcher) {
      nf locMcp = getLocationRandom(loc, mcpatcher);
      if (locMcp == null) {
         return null;
      } else {
         String domain = locMcp.b();
         String path = locMcp.a();
         String pathBase = StrUtils.removeSuffix(path, ".png");
         String pathProps = pathBase + ".properties";
         nf locProps = new nf(domain, pathProps);
         if (.Config.hasResource(locProps)) {
            return locProps;
         } else {
            String pathParent = getParentTexturePath(pathBase);
            if (pathParent == null) {
               return null;
            } else {
               nf locParentProps = new nf(domain, pathParent + ".properties");
               return .Config.hasResource(locParentProps) ? locParentProps : null;
            }
         }
      }
   }

   protected static nf getLocationRandom(nf loc, boolean mcpatcher) {
      String domain = loc.b();
      String path = loc.a();
      String prefixTextures = "textures/";
      String prefixRandom = "optifine/random/";
      if (mcpatcher) {
         prefixTextures = "textures/entity/";
         prefixRandom = "mcpatcher/mob/";
      }

      if (!path.startsWith(prefixTextures)) {
         return null;
      } else {
         String pathRandom = StrUtils.replacePrefix(path, prefixTextures, prefixRandom);
         return new nf(domain, pathRandom);
      }
   }

   private static String getPathBase(String pathRandom) {
      if (pathRandom.startsWith("optifine/random/")) {
         return StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/");
      } else {
         return pathRandom.startsWith("mcpatcher/mob/") ? StrUtils.replacePrefix(pathRandom, "mcpatcher/mob/", "textures/entity/") : null;
      }
   }

   protected static nf getLocationIndexed(nf loc, int index) {
      if (loc == null) {
         return null;
      } else {
         String path = loc.a();
         int pos = path.lastIndexOf(46);
         if (pos < 0) {
            return null;
         } else {
            String prefix = path.substring(0, pos);
            String suffix = path.substring(pos);
            String pathNew = prefix + index + suffix;
            nf locNew = new nf(loc.b(), pathNew);
            return locNew;
         }
      }
   }

   private static String getParentTexturePath(String path) {
      for(int i = 0; i < DEPENDANT_SUFFIXES.length; ++i) {
         String suffix = DEPENDANT_SUFFIXES[i];
         if (path.endsWith(suffix)) {
            String pathParent = StrUtils.removeSuffix(path, suffix);
            return pathParent;
         }
      }

      return null;
   }

   private static nf[] getLocationsVariants(nf loc, boolean mcpatcher) {
      List list = new ArrayList();
      list.add(loc);
      nf locRandom = getLocationRandom(loc, mcpatcher);
      if (locRandom == null) {
         return null;
      } else {
         for(int i = 1; i < list.size() + 10; ++i) {
            int index = i + 1;
            nf locIndex = getLocationIndexed(locRandom, index);
            if (.Config.hasResource(locIndex)) {
               list.add(locIndex);
            }
         }

         if (list.size() <= 1) {
            return null;
         } else {
            nf[] locs = (nf[])((nf[])list.toArray(new nf[list.size()]));
            dbg(loc.a() + ", variants: " + locs.length);
            return locs;
         }
      }
   }

   public static void update() {
      mapProperties.clear();
      active = false;
      if (.Config.isRandomEntities()) {
         initialize();
      }
   }

   private static void initialize() {
      renderGlobal = .Config.getRenderGlobal();
      tileEntityRendererDispatcher = bwx.a;
      String[] prefixes = new String[]{"optifine/random/", "mcpatcher/mob/"};
      String[] suffixes = new String[]{".png", ".properties"};
      String[] pathsRandom = ResUtils.collectFiles(prefixes, suffixes);
      Set basePathsChecked = new HashSet();

      for(int i = 0; i < pathsRandom.length; ++i) {
         String path = pathsRandom[i];
         path = StrUtils.removeSuffix(path, suffixes);
         path = StrUtils.trimTrailing(path, "0123456789");
         path = path + ".png";
         String pathBase = getPathBase(path);
         if (!basePathsChecked.contains(pathBase)) {
            basePathsChecked.add(pathBase);
            nf locBase = new nf(pathBase);
            if (.Config.hasResource(locBase)) {
               RandomEntityProperties props = (RandomEntityProperties)mapProperties.get(pathBase);
               if (props == null) {
                  props = makeProperties(locBase, false);
                  if (props == null) {
                     props = makeProperties(locBase, true);
                  }

                  if (props != null) {
                     mapProperties.put(pathBase, props);
                  }
               }
            }
         }
      }

      active = !mapProperties.isEmpty();
   }

   public static void dbg(String str) {
      .Config.dbg("RandomEntities: " + str);
   }

   public static void warn(String str) {
      .Config.warn("RandomEntities: " + str);
   }
}
