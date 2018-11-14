package net.optifine;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.util.EntityUtils;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {
   private static String paletteFormatDefault = "vanilla";
   private static CustomColormap waterColors = null;
   private static CustomColormap foliagePineColors = null;
   private static CustomColormap foliageBirchColors = null;
   private static CustomColormap swampFoliageColors = null;
   private static CustomColormap swampGrassColors = null;
   private static CustomColormap[] colorsBlockColormaps = null;
   private static CustomColormap[][] blockColormaps = (CustomColormap[][])null;
   private static CustomColormap skyColors = null;
   private static CustomColorFader skyColorFader = new CustomColorFader();
   private static CustomColormap fogColors = null;
   private static CustomColorFader fogColorFader = new CustomColorFader();
   private static CustomColormap underwaterColors = null;
   private static CustomColorFader underwaterColorFader = new CustomColorFader();
   private static CustomColormap underlavaColors = null;
   private static CustomColorFader underlavaColorFader = new CustomColorFader();
   private static LightMapPack[] lightMapPacks = null;
   private static int lightmapMinDimensionId = 0;
   private static CustomColormap redstoneColors = null;
   private static CustomColormap xpOrbColors = null;
   private static int xpOrbTime = -1;
   private static CustomColormap durabilityColors = null;
   private static CustomColormap stemColors = null;
   private static CustomColormap stemMelonColors = null;
   private static CustomColormap stemPumpkinColors = null;
   private static CustomColormap myceliumParticleColors = null;
   private static boolean useDefaultGrassFoliageColors = true;
   private static int particleWaterColor = -1;
   private static int particlePortalColor = -1;
   private static int lilyPadColor = -1;
   private static int expBarTextColor = -1;
   private static int bossTextColor = -1;
   private static int signTextColor = -1;
   private static bhe fogColorNether = null;
   private static bhe fogColorEnd = null;
   private static bhe skyColorEnd = null;
   private static int[] spawnEggPrimaryColors = null;
   private static int[] spawnEggSecondaryColors = null;
   private static float[][] wolfCollarColors = (float[][])null;
   private static float[][] sheepColors = (float[][])null;
   private static int[] textColors = null;
   private static int[] mapColorsOriginal = null;
   private static int[] potionColors = null;
   private static final awt BLOCK_STATE_DIRT;
   private static final awt BLOCK_STATE_WATER;
   public static Random random;
   private static final CustomColors.IColorizer COLORIZER_GRASS;
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE;
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_PINE;
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_BIRCH;
   private static final CustomColors.IColorizer COLORIZER_WATER;

   public static void update() {
      paletteFormatDefault = "vanilla";
      waterColors = null;
      foliageBirchColors = null;
      foliagePineColors = null;
      swampGrassColors = null;
      swampFoliageColors = null;
      skyColors = null;
      fogColors = null;
      underwaterColors = null;
      underlavaColors = null;
      redstoneColors = null;
      xpOrbColors = null;
      xpOrbTime = -1;
      durabilityColors = null;
      stemColors = null;
      myceliumParticleColors = null;
      lightMapPacks = null;
      particleWaterColor = -1;
      particlePortalColor = -1;
      lilyPadColor = -1;
      expBarTextColor = -1;
      bossTextColor = -1;
      signTextColor = -1;
      fogColorNether = null;
      fogColorEnd = null;
      skyColorEnd = null;
      colorsBlockColormaps = null;
      blockColormaps = (CustomColormap[][])null;
      useDefaultGrassFoliageColors = true;
      spawnEggPrimaryColors = null;
      spawnEggSecondaryColors = null;
      wolfCollarColors = (float[][])null;
      sheepColors = (float[][])null;
      textColors = null;
      setMapColors(mapColorsOriginal);
      potionColors = null;
      paletteFormatDefault = getValidProperty("mcpatcher/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
      String mcpColormap = "mcpatcher/colormap/";
      String[] waterPaths = new String[]{"water.png", "watercolorX.png"};
      waterColors = getCustomColors(mcpColormap, waterPaths, 256, 256);
      updateUseDefaultGrassFoliageColors();
      if (.Config.isCustomColors()) {
         String[] pinePaths = new String[]{"pine.png", "pinecolor.png"};
         foliagePineColors = getCustomColors(mcpColormap, pinePaths, 256, 256);
         String[] birchPaths = new String[]{"birch.png", "birchcolor.png"};
         foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 256, 256);
         String[] swampGrassPaths = new String[]{"swampgrass.png", "swampgrasscolor.png"};
         swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 256, 256);
         String[] swampFoliagePaths = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
         swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 256, 256);
         String[] sky0Paths = new String[]{"sky0.png", "skycolor0.png"};
         skyColors = getCustomColors(mcpColormap, sky0Paths, 256, 256);
         String[] fog0Paths = new String[]{"fog0.png", "fogcolor0.png"};
         fogColors = getCustomColors(mcpColormap, fog0Paths, 256, 256);
         String[] underwaterPaths = new String[]{"underwater.png", "underwatercolor.png"};
         underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 256, 256);
         String[] underlavaPaths = new String[]{"underlava.png", "underlavacolor.png"};
         underlavaColors = getCustomColors(mcpColormap, underlavaPaths, 256, 256);
         String[] redstonePaths = new String[]{"redstone.png", "redstonecolor.png"};
         redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16, 1);
         xpOrbColors = getCustomColors(mcpColormap + "xporb.png", -1, -1);
         durabilityColors = getCustomColors(mcpColormap + "durability.png", -1, -1);
         String[] stemPaths = new String[]{"stem.png", "stemcolor.png"};
         stemColors = getCustomColors(mcpColormap, stemPaths, 8, 1);
         stemPumpkinColors = getCustomColors(mcpColormap + "pumpkinstem.png", 8, 1);
         stemMelonColors = getCustomColors(mcpColormap + "melonstem.png", 8, 1);
         String[] myceliumPaths = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
         myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1, -1);
         Pair lightMaps = parseLightMapPacks();
         lightMapPacks = (LightMapPack[])lightMaps.getLeft();
         lightmapMinDimensionId = ((Integer)lightMaps.getRight()).intValue();
         readColorProperties("mcpatcher/color.properties");
         blockColormaps = readBlockColormaps(new String[]{mcpColormap + "custom/", mcpColormap + "blocks/"}, colorsBlockColormaps, 256, 256);
         updateUseDefaultGrassFoliageColors();
      }
   }

   private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
      try {
         nf loc = new nf(fileName);
         InputStream in = .Config.getResourceStream(loc);
         if (in == null) {
            return valDef;
         } else {
            Properties props = new Properties();
            props.load(in);
            in.close();
            String val = props.getProperty(key);
            if (val == null) {
               return valDef;
            } else {
               List listValidValues = Arrays.asList(validValues);
               if (!listValidValues.contains(val)) {
                  warn("Invalid value: " + key + "=" + val);
                  warn("Expected values: " + .Config.arrayToString((Object[])validValues));
                  return valDef;
               } else {
                  dbg("" + key + "=" + val);
                  return val;
               }
            }
         }
      } catch (FileNotFoundException var9) {
         return valDef;
      } catch (IOException var10) {
         var10.printStackTrace();
         return valDef;
      }
   }

   private static Pair parseLightMapPacks() {
      String lightmapPrefix = "mcpatcher/lightmap/world";
      String lightmapSuffix = ".png";
      String[] pathsLightmap = ResUtils.collectFiles(lightmapPrefix, lightmapSuffix);
      Map mapLightmaps = new HashMap();

      int maxDimId;
      for(int i = 0; i < pathsLightmap.length; ++i) {
         String path = pathsLightmap[i];
         String dimIdStr = StrUtils.removePrefixSuffix(path, lightmapPrefix, lightmapSuffix);
         maxDimId = .Config.parseInt(dimIdStr, Integer.MIN_VALUE);
         if (maxDimId == Integer.MIN_VALUE) {
            warn("Invalid dimension ID: " + dimIdStr + ", path: " + path);
         } else {
            mapLightmaps.put(maxDimId, path);
         }
      }

      Set setDimIds = mapLightmaps.keySet();
      Integer[] dimIds = (Integer[])setDimIds.toArray(new Integer[setDimIds.size()]);
      Arrays.sort(dimIds);
      if (dimIds.length <= 0) {
         return new ImmutablePair((Object)null, Integer.valueOf(0));
      } else {
         int minDimId = dimIds[0].intValue();
         maxDimId = dimIds[dimIds.length - 1].intValue();
         int countDim = maxDimId - minDimId + 1;
         CustomColormap[] colormaps = new CustomColormap[countDim];

         for(int i = 0; i < dimIds.length; ++i) {
            Integer dimId = dimIds[i];
            String path = (String)mapLightmaps.get(dimId);
            CustomColormap colors = getCustomColors(path, -1, -1);
            if (colors != null) {
               if (colors.getWidth() < 16) {
                  warn("Invalid lightmap width: " + colors.getWidth() + ", path: " + path);
               } else {
                  int lightmapIndex = dimId.intValue() - minDimId;
                  colormaps[lightmapIndex] = colors;
               }
            }
         }

         LightMapPack[] lmps = new LightMapPack[colormaps.length];

         for(int i = 0; i < colormaps.length; ++i) {
            CustomColormap cm = colormaps[i];
            if (cm != null) {
               String name = cm.name;
               String basePath = cm.basePath;
               CustomColormap cmRain = getCustomColors(basePath + "/" + name + "_rain.png", -1, -1);
               CustomColormap cmThunder = getCustomColors(basePath + "/" + name + "_thunder.png", -1, -1);
               LightMap lm = new LightMap(cm);
               LightMap lmRain = cmRain != null ? new LightMap(cmRain) : null;
               LightMap lmThunder = cmThunder != null ? new LightMap(cmThunder) : null;
               LightMapPack lmp = new LightMapPack(lm, lmRain, lmThunder);
               lmps[i] = lmp;
            }
         }

         return new ImmutablePair(lmps, minDimId);
      }
   }

   private static int getTextureHeight(String path, int defHeight) {
      try {
         InputStream in = .Config.getResourceStream(new nf(path));
         if (in == null) {
            return defHeight;
         } else {
            BufferedImage bi = ImageIO.read(in);
            in.close();
            return bi == null ? defHeight : bi.getHeight();
         }
      } catch (IOException var4) {
         return defHeight;
      }
   }

   private static void readColorProperties(String fileName) {
      try {
         nf loc = new nf(fileName);
         InputStream in = .Config.getResourceStream(loc);
         if (in == null) {
            return;
         }

         dbg("Loading " + fileName);
         Properties props = new Properties();
         props.load(in);
         in.close();
         particleWaterColor = readColor(props, new String[]{"particle.water", "drop.water"});
         particlePortalColor = readColor(props, "particle.portal");
         lilyPadColor = readColor(props, "lilypad");
         expBarTextColor = readColor(props, "text.xpbar");
         bossTextColor = readColor(props, "text.boss");
         signTextColor = readColor(props, "text.sign");
         fogColorNether = readColorVec3(props, "fog.nether");
         fogColorEnd = readColorVec3(props, "fog.end");
         skyColorEnd = readColorVec3(props, "sky.end");
         colorsBlockColormaps = readCustomColormaps(props, fileName);
         spawnEggPrimaryColors = readSpawnEggColors(props, fileName, "egg.shell.", "Spawn egg shell");
         spawnEggSecondaryColors = readSpawnEggColors(props, fileName, "egg.spots.", "Spawn egg spot");
         wolfCollarColors = readDyeColors(props, fileName, "collar.", "Wolf collar");
         sheepColors = readDyeColors(props, fileName, "sheep.", "Sheep");
         textColors = readTextColors(props, fileName, "text.code.", "Text");
         int[] mapColors = readMapColors(props, fileName, "map.", "Map");
         if (mapColors != null) {
            if (mapColorsOriginal == null) {
               mapColorsOriginal = getMapColors();
            }

            setMapColors(mapColors);
         }

         potionColors = readPotionColors(props, fileName, "potion.", "Potion");
         xpOrbTime = .Config.parseInt(props.getProperty("xporb.time"), -1);
      } catch (FileNotFoundException var5) {
         return;
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
      List list = new ArrayList();
      String palettePrefix = "palette.block.";
      Map map = new HashMap();
      Set keys = props.keySet();
      Iterator iter = keys.iterator();

      String name;
      while(iter.hasNext()) {
         String key = (String)iter.next();
         name = props.getProperty(key);
         if (key.startsWith(palettePrefix)) {
            map.put(key, name);
         }
      }

      String[] propNames = (String[])((String[])map.keySet().toArray(new String[map.size()]));

      for(int i = 0; i < propNames.length; ++i) {
         name = propNames[i];
         String value = props.getProperty(name);
         dbg("Block palette: " + name + " = " + value);
         String path = name.substring(palettePrefix.length());
         String basePath = TextureUtils.getBasePath(fileName);
         path = TextureUtils.fixResourcePath(path, basePath);
         CustomColormap colors = getCustomColors(path, 256, 256);
         if (colors == null) {
            warn("Colormap not found: " + path);
         } else {
            ConnectedParser cp = new ConnectedParser("CustomColors");
            MatchBlock[] mbs = cp.parseMatchBlocks(value);
            if (mbs != null && mbs.length > 0) {
               for(int m = 0; m < mbs.length; ++m) {
                  MatchBlock mb = mbs[m];
                  colors.addMatchBlock(mb);
               }

               list.add(colors);
            } else {
               warn("Invalid match blocks: " + value);
            }
         }
      }

      if (list.size() <= 0) {
         return null;
      } else {
         CustomColormap[] cms = (CustomColormap[])((CustomColormap[])list.toArray(new CustomColormap[list.size()]));
         return cms;
      }
   }

   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
      String[] paths = ResUtils.collectFiles(basePaths, new String[]{".properties"});
      Arrays.sort(paths);
      List blockList = new ArrayList();

      int i;
      for(i = 0; i < paths.length; ++i) {
         String path = paths[i];
         dbg("Block colormap: " + path);

         try {
            nf locFile = new nf("minecraft", path);
            InputStream in = .Config.getResourceStream(locFile);
            if (in == null) {
               warn("File not found: " + path);
            } else {
               Properties props = new Properties();
               props.load(in);
               CustomColormap cm = new CustomColormap(props, path, width, height, paletteFormatDefault);
               if (cm.isValid(path) && cm.isValidMatchBlocks(path)) {
                  addToBlockList(cm, blockList);
               }
            }
         } catch (FileNotFoundException var12) {
            warn("File not found: " + path);
         } catch (Exception var13) {
            var13.printStackTrace();
         }
      }

      if (basePalettes != null) {
         for(i = 0; i < basePalettes.length; ++i) {
            CustomColormap cm = basePalettes[i];
            addToBlockList(cm, blockList);
         }
      }

      if (blockList.size() <= 0) {
         return (CustomColormap[][])null;
      } else {
         CustomColormap[][] cmArr = blockListToArray(blockList);
         return cmArr;
      }
   }

   private static void addToBlockList(CustomColormap cm, List blockList) {
      int[] ids = cm.getMatchBlockIds();
      if (ids != null && ids.length > 0) {
         for(int i = 0; i < ids.length; ++i) {
            int blockId = ids[i];
            if (blockId < 0) {
               warn("Invalid block ID: " + blockId);
            } else {
               addToList(cm, blockList, blockId);
            }
         }

      } else {
         warn("No match blocks: " + .Config.arrayToString(ids));
      }
   }

   private static void addToList(CustomColormap cm, List list, int id) {
      while(id >= list.size()) {
         list.add((Object)null);
      }

      List subList = (List)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      ((List)subList).add(cm);
   }

   private static CustomColormap[][] blockListToArray(List list) {
      CustomColormap[][] colArr = new CustomColormap[list.size()][];

      for(int i = 0; i < list.size(); ++i) {
         List subList = (List)list.get(i);
         if (subList != null) {
            CustomColormap[] subArr = (CustomColormap[])((CustomColormap[])subList.toArray(new CustomColormap[subList.size()]));
            colArr[i] = subArr;
         }
      }

      return colArr;
   }

   private static int readColor(Properties props, String[] names) {
      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         int col = readColor(props, name);
         if (col >= 0) {
            return col;
         }
      }

      return -1;
   }

   private static int readColor(Properties props, String name) {
      String str = props.getProperty(name);
      if (str == null) {
         return -1;
      } else {
         str = str.trim();
         int color = parseColor(str);
         if (color < 0) {
            warn("Invalid color: " + name + " = " + str);
            return color;
         } else {
            dbg(name + " = " + str);
            return color;
         }
      }
   }

   private static int parseColor(String str) {
      if (str == null) {
         return -1;
      } else {
         str = str.trim();

         try {
            int val = Integer.parseInt(str, 16) & 16777215;
            return val;
         } catch (NumberFormatException var2) {
            return -1;
         }
      }
   }

   private static bhe readColorVec3(Properties props, String name) {
      int col = readColor(props, name);
      if (col < 0) {
         return null;
      } else {
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         return new bhe((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
      for(int i = 0; i < paths.length; ++i) {
         String path = paths[i];
         path = basePath + path;
         CustomColormap cols = getCustomColors(path, width, height);
         if (cols != null) {
            return cols;
         }
      }

      return null;
   }

   public static CustomColormap getCustomColors(String pathImage, int width, int height) {
      try {
         nf loc = new nf(pathImage);
         if (!.Config.hasResource(loc)) {
            return null;
         } else {
            dbg("Colormap " + pathImage);
            Properties props = new Properties();
            String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
            nf locProps = new nf(pathProps);
            if (.Config.hasResource(locProps)) {
               InputStream in = .Config.getResourceStream(locProps);
               props.load(in);
               in.close();
               dbg("Colormap properties: " + pathProps);
            } else {
               props.put("format", paletteFormatDefault);
               props.put("source", pathImage);
               pathProps = pathImage;
            }

            CustomColormap cm = new CustomColormap(props, pathProps, width, height, paletteFormatDefault);
            return !cm.isValid(pathProps) ? null : cm;
         }
      } catch (Exception var8) {
         var8.printStackTrace();
         return null;
      }
   }

   public static void updateUseDefaultGrassFoliageColors() {
      useDefaultGrassFoliageColors = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null && .Config.isSwampColors() && .Config.isSmoothBiomes();
   }

   public static int getColorMultiplier(bvp quad, awt blockState, amy blockAccess, et blockPos, RenderEnv renderEnv) {
      aow block = blockState.u();
      awt bs = renderEnv.getBlockState();
      if (blockColormaps != null) {
         if (!quad.c()) {
            if (block == aox.c) {
               bs = BLOCK_STATE_DIRT;
            }

            if (block == aox.af) {
               return -1;
            }
         }

         if (block == aox.cF && renderEnv.getMetadata() >= 8) {
            blockPos = blockPos.b();
            bs = blockAccess.o(blockPos);
         }

         CustomColormap cm = getBlockColormap(bs);
         if (cm != null) {
            if (.Config.isSmoothBiomes() && !cm.isColorConstant()) {
               return getSmoothColorMultiplier(blockState, blockAccess, blockPos, cm, renderEnv.getColorizerBlockPosM());
            }

            return cm.getColor(blockAccess, blockPos);
         }
      }

      if (!quad.c()) {
         return -1;
      } else if (block == aox.bx) {
         return getLilypadColorMultiplier(blockAccess, blockPos);
      } else if (block == aox.af) {
         return getRedstoneColor(renderEnv.getBlockState());
      } else if (block instanceof aug) {
         return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
      } else if (useDefaultGrassFoliageColors) {
         return -1;
      } else {
         int metadata = renderEnv.getMetadata();
         CustomColors.IColorizer colorizer;
         if (block != aox.c && block != aox.H && block != aox.cF) {
            if (block == aox.cF) {
               colorizer = COLORIZER_GRASS;
               if (metadata >= 8) {
                  blockPos = blockPos.b();
               }
            } else if (block == aox.t) {
               switch(metadata & 3) {
               case 0:
                  colorizer = COLORIZER_FOLIAGE;
                  break;
               case 1:
                  colorizer = COLORIZER_FOLIAGE_PINE;
                  break;
               case 2:
                  colorizer = COLORIZER_FOLIAGE_BIRCH;
                  break;
               default:
                  colorizer = COLORIZER_FOLIAGE;
               }
            } else if (block == aox.u) {
               colorizer = COLORIZER_FOLIAGE;
            } else {
               if (block != aox.bn) {
                  return -1;
               }

               colorizer = COLORIZER_FOLIAGE;
            }
         } else {
            colorizer = COLORIZER_GRASS;
         }

         return .Config.isSmoothBiomes() && !colorizer.isColorConstant() ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM()) : colorizer.getColor(bs, blockAccess, blockPos);
      }
   }

   protected static anh getColorBiome(amy blockAccess, et blockPos) {
      anh biome = blockAccess.b(blockPos);
      if (biome == anm.h && !.Config.isSwampColors()) {
         biome = anm.c;
      }

      return biome;
   }

   private static CustomColormap getBlockColormap(awt blockState) {
      if (blockColormaps == null) {
         return null;
      } else if (!(blockState instanceof awp)) {
         return null;
      } else {
         awp bs = (awp)blockState;
         int blockId = bs.getBlockId();
         if (blockId >= 0 && blockId < blockColormaps.length) {
            CustomColormap[] cms = blockColormaps[blockId];
            if (cms == null) {
               return null;
            } else {
               for(int i = 0; i < cms.length; ++i) {
                  CustomColormap cm = cms[i];
                  if (cm.matchesBlock(bs)) {
                     return cm;
                  }
               }

               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static int getSmoothColorMultiplier(awt blockState, amy blockAccess, et blockPos, CustomColors.IColorizer colorizer, BlockPosM blockPosM) {
      int sumRed = 0;
      int sumGreen = 0;
      int sumBlue = 0;
      int x = blockPos.p();
      int y = blockPos.q();
      int z = blockPos.r();
      BlockPosM posM = blockPosM;

      int ix;
      int iz;
      int col;
      for(ix = x - 1; ix <= x + 1; ++ix) {
         for(iz = z - 1; iz <= z + 1; ++iz) {
            posM.setXyz(ix, y, iz);
            col = colorizer.getColor(blockState, blockAccess, posM);
            sumRed += col >> 16 & 255;
            sumGreen += col >> 8 & 255;
            sumBlue += col & 255;
         }
      }

      ix = sumRed / 9;
      iz = sumGreen / 9;
      col = sumBlue / 9;
      return ix << 16 | iz << 8 | col;
   }

   public static int getFluidColor(amy blockAccess, awt blockState, et blockPos, RenderEnv renderEnv) {
      aow block = blockState.u();
      CustomColors.IColorizer colorizer = getBlockColormap(blockState);
      if (colorizer == null && blockState.a() == bcz.h) {
         colorizer = COLORIZER_WATER;
      }

      if (colorizer == null) {
         return getBlockColors().a(blockState, blockAccess, blockPos, 0);
      } else {
         return .Config.isSmoothBiomes() && !((CustomColors.IColorizer)colorizer).isColorConstant() ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, (CustomColors.IColorizer)colorizer, renderEnv.getColorizerBlockPosM()) : ((CustomColors.IColorizer)colorizer).getColor(blockState, blockAccess, blockPos);
      }
   }

   public static bik getBlockColors() {
      return bib.z().al();
   }

   public static void updatePortalFX(btf fx) {
      if (particlePortalColor >= 0) {
         int col = particlePortalColor;
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.a(redF, greenF, blueF);
      }
   }

   public static void updateMyceliumFX(btf fx) {
      if (myceliumParticleColors != null) {
         int col = myceliumParticleColors.getColorRandom();
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.a(redF, greenF, blueF);
      }
   }

   private static int getRedstoneColor(awt blockState) {
      if (redstoneColors == null) {
         return -1;
      } else {
         int level = getRedstoneLevel(blockState, 15);
         int col = redstoneColors.getColor(level);
         return col;
      }
   }

   public static void updateReddustFX(btf fx, amy blockAccess, double x, double y, double z) {
      if (redstoneColors != null) {
         awt state = blockAccess.o(new et(x, y, z));
         int level = getRedstoneLevel(state, 15);
         int col = redstoneColors.getColor(level);
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.a(redF, greenF, blueF);
      }
   }

   private static int getRedstoneLevel(awt state, int def) {
      aow block = state.u();
      if (!(block instanceof atf)) {
         return def;
      } else {
         Object val = state.c(atf.e);
         if (!(val instanceof Integer)) {
            return def;
         } else {
            Integer valInt = (Integer)val;
            return valInt.intValue();
         }
      }
   }

   public static float getXpOrbTimer(float timer) {
      if (xpOrbTime <= 0) {
         return timer;
      } else {
         float kt = 628.0F / (float)xpOrbTime;
         return timer * kt;
      }
   }

   public static int getXpOrbColor(float timer) {
      if (xpOrbColors == null) {
         return -1;
      } else {
         int index = (int)Math.round((double)((rk.a(timer) + 1.0F) * (float)(xpOrbColors.getLength() - 1)) / 2.0D);
         int col = xpOrbColors.getColor(index);
         return col;
      }
   }

   public static int getDurabilityColor(float dur, int color) {
      if (durabilityColors == null) {
         return color;
      } else {
         int index = (int)(dur * (float)durabilityColors.getLength());
         int col = durabilityColors.getColor(index);
         return col;
      }
   }

   public static void updateWaterFX(btf fx, amy blockAccess, double x, double y, double z, RenderEnv renderEnv) {
      if (waterColors != null || blockColormaps != null) {
         et blockPos = new et(x, y, z);
         renderEnv.reset(blockAccess, BLOCK_STATE_WATER, blockPos);
         int col = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockPos, renderEnv);
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         if (particleWaterColor >= 0) {
            int redDrop = particleWaterColor >> 16 & 255;
            int greenDrop = particleWaterColor >> 8 & 255;
            int blueDrop = particleWaterColor & 255;
            redF *= (float)redDrop / 255.0F;
            greenF *= (float)greenDrop / 255.0F;
            blueF *= (float)blueDrop / 255.0F;
         }

         fx.a(redF, greenF, blueF);
      }
   }

   private static int getLilypadColorMultiplier(amy blockAccess, et blockPos) {
      return lilyPadColor < 0 ? getBlockColors().a(aox.bx.t(), blockAccess, blockPos, 0) : lilyPadColor;
   }

   private static bhe getFogColorNether(bhe col) {
      return fogColorNether == null ? col : fogColorNether;
   }

   private static bhe getFogColorEnd(bhe col) {
      return fogColorEnd == null ? col : fogColorEnd;
   }

   private static bhe getSkyColorEnd(bhe col) {
      return skyColorEnd == null ? col : skyColorEnd;
   }

   public static bhe getSkyColor(bhe skyColor3d, amy blockAccess, double x, double y, double z) {
      if (skyColors == null) {
         return skyColor3d;
      } else {
         int col = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         float cRed = (float)skyColor3d.b / 0.5F;
         float cGreen = (float)skyColor3d.c / 0.66275F;
         float cBlue = (float)skyColor3d.d;
         redF *= cRed;
         greenF *= cGreen;
         blueF *= cBlue;
         bhe newCol = skyColorFader.getColor((double)redF, (double)greenF, (double)blueF);
         return newCol;
      }
   }

   private static bhe getFogColor(bhe fogColor3d, amy blockAccess, double x, double y, double z) {
      if (fogColors == null) {
         return fogColor3d;
      } else {
         int col = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         float cRed = (float)fogColor3d.b / 0.753F;
         float cGreen = (float)fogColor3d.c / 0.8471F;
         float cBlue = (float)fogColor3d.d;
         redF *= cRed;
         greenF *= cGreen;
         blueF *= cBlue;
         bhe newCol = fogColorFader.getColor((double)redF, (double)greenF, (double)blueF);
         return newCol;
      }
   }

   public static bhe getUnderwaterColor(amy blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
   }

   public static bhe getUnderlavaColor(amy blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
   }

   public static bhe getUnderFluidColor(amy blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader) {
      if (underFluidColors == null) {
         return null;
      } else {
         int col = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 255;
         int green = col >> 8 & 255;
         int blue = col & 255;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         bhe newCol = underFluidColorFader.getColor((double)redF, (double)greenF, (double)blueF);
         return newCol;
      }
   }

   private static int getStemColorMultiplier(aow blockStem, amy blockAccess, et blockPos, RenderEnv renderEnv) {
      CustomColormap colors = stemColors;
      if (blockStem == aox.bl && stemPumpkinColors != null) {
         colors = stemPumpkinColors;
      }

      if (blockStem == aox.bm && stemMelonColors != null) {
         colors = stemMelonColors;
      }

      if (colors == null) {
         return -1;
      } else {
         int level = renderEnv.getMetadata();
         return colors.getColor(level);
      }
   }

   public static boolean updateLightmap(amu world, float torchFlickerX, int[] lmColors, boolean nightvision, float partialTicks) {
      if (world == null) {
         return false;
      } else if (lightMapPacks == null) {
         return false;
      } else {
         int dimensionId = world.s.q().a();
         int lightMapIndex = dimensionId - lightmapMinDimensionId;
         if (lightMapIndex >= 0 && lightMapIndex < lightMapPacks.length) {
            LightMapPack lightMapPack = lightMapPacks[lightMapIndex];
            return lightMapPack == null ? false : lightMapPack.updateLightmap(world, torchFlickerX, lmColors, nightvision, partialTicks);
         } else {
            return false;
         }
      }
   }

   public static bhe getWorldFogColor(bhe fogVec, amu world, vg renderViewEntity, float partialTicks) {
      ayn worldType = world.s.q();
      bib mc = bib.z();
      if (worldType == ayn.b) {
         return getFogColorNether(fogVec);
      } else if (worldType == ayn.a) {
         return getFogColor(fogVec, mc.f, renderViewEntity.p, renderViewEntity.q + 1.0D, renderViewEntity.r);
      } else {
         return worldType == ayn.c ? getFogColorEnd(fogVec) : fogVec;
      }
   }

   public static bhe getWorldSkyColor(bhe skyVec, amu world, vg renderViewEntity, float partialTicks) {
      ayn worldType = world.s.q();
      bib mc = bib.z();
      if (worldType == ayn.a) {
         return getSkyColor(skyVec, mc.f, renderViewEntity.p, renderViewEntity.q + 1.0D, renderViewEntity.r);
      } else {
         return worldType == ayn.c ? getSkyColorEnd(skyVec) : skyVec;
      }
   }

   private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
      List list = new ArrayList();
      Set keys = props.keySet();
      int countColors = 0;
      Iterator iter = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               if (!iter.hasNext()) {
                  if (countColors <= 0) {
                     return null;
                  }

                  dbg(logName + " colors: " + countColors);
                  int[] colors = new int[list.size()];

                  for(int i = 0; i < colors.length; ++i) {
                     colors[i] = ((Integer)list.get(i)).intValue();
                  }

                  return colors;
               }

               key = (String)iter.next();
               value = props.getProperty(key);
            } while(!key.startsWith(prefix));

            String name = StrUtils.removePrefix(key, prefix);
            int id = EntityUtils.getEntityIdByName(name);
            if (id < 0) {
               id = EntityUtils.getEntityIdByLocation((new nf(name)).toString());
            }

            if (id < 0) {
               warn("Invalid spawn egg name: " + key);
            } else {
               int color = parseColor(value);
               if (color < 0) {
                  warn("Invalid spawn egg color: " + key + " = " + value);
               } else {
                  while(list.size() <= id) {
                     list.add(Integer.valueOf(-1));
                  }

                  list.set(id, color);
                  ++countColors;
               }
            }
         }
      }
   }

   private static int getSpawnEggColor(ajv item, aip itemStack, int layer, int color) {
      if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null) {
         return color;
      } else {
         fy nbt = itemStack.p();
         if (nbt == null) {
            return color;
         } else {
            fy tag = nbt.p("EntityTag");
            if (tag == null) {
               return color;
            } else {
               String loc = tag.l("id");
               int id = EntityUtils.getEntityIdByLocation(loc);
               int[] eggColors = layer == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;
               if (eggColors == null) {
                  return color;
               } else if (id >= 0 && id < eggColors.length) {
                  int eggColor = eggColors[id];
                  return eggColor < 0 ? color : eggColor;
               } else {
                  return color;
               }
            }
         }
      }
   }

   public static int getColorFromItemStack(aip itemStack, int layer, int color) {
      if (itemStack == null) {
         return color;
      } else {
         ain item = itemStack.c();
         if (item == null) {
            return color;
         } else {
            return item instanceof ajv ? getSpawnEggColor((ajv)item, itemStack, layer, color) : color;
         }
      }
   }

   private static float[][] readDyeColors(Properties props, String fileName, String prefix, String logName) {
      ahs[] dyeValues = ahs.values();
      Map mapDyes = new HashMap();

      for(int i = 0; i < dyeValues.length; ++i) {
         ahs dye = dyeValues[i];
         mapDyes.put(dye.m(), dye);
      }

      float[][] colors = new float[dyeValues.length][];
      int countColors = 0;
      Set keys = props.keySet();
      Iterator iter = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               if (!iter.hasNext()) {
                  if (countColors <= 0) {
                     return (float[][])null;
                  }

                  dbg(logName + " colors: " + countColors);
                  return colors;
               }

               key = (String)iter.next();
               value = props.getProperty(key);
            } while(!key.startsWith(prefix));

            String name = StrUtils.removePrefix(key, prefix);
            if (name.equals("lightBlue")) {
               name = "light_blue";
            }

            ahs dye = (ahs)mapDyes.get(name);
            int color = parseColor(value);
            if (dye != null && color >= 0) {
               float[] rgb = new float[]{(float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F};
               colors[dye.ordinal()] = rgb;
               ++countColors;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }
   }

   private static float[] getDyeColors(ahs dye, float[][] dyeColors, float[] colors) {
      if (dyeColors == null) {
         return colors;
      } else if (dye == null) {
         return colors;
      } else {
         float[] customColors = dyeColors[dye.ordinal()];
         return customColors == null ? colors : customColors;
      }
   }

   public static float[] getWolfCollarColors(ahs dye, float[] colors) {
      return getDyeColors(dye, wolfCollarColors, colors);
   }

   public static float[] getSheepColors(ahs dye, float[] colors) {
      return getDyeColors(dye, sheepColors, colors);
   }

   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[32];
      Arrays.fill(colors, -1);
      int countColors = 0;
      Set keys = props.keySet();
      Iterator iter = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               if (!iter.hasNext()) {
                  if (countColors <= 0) {
                     return null;
                  }

                  dbg(logName + " colors: " + countColors);
                  return colors;
               }

               key = (String)iter.next();
               value = props.getProperty(key);
            } while(!key.startsWith(prefix));

            String name = StrUtils.removePrefix(key, prefix);
            int code = .Config.parseInt(name, -1);
            int color = parseColor(value);
            if (code >= 0 && code < colors.length && color >= 0) {
               colors[code] = color;
               ++countColors;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }
   }

   public static int getTextColor(int index, int color) {
      if (textColors == null) {
         return color;
      } else if (index >= 0 && index < textColors.length) {
         int customColor = textColors[index];
         return customColor < 0 ? color : customColor;
      } else {
         return color;
      }
   }

   private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[bda.a.length];
      Arrays.fill(colors, -1);
      int countColors = 0;
      Set keys = props.keySet();
      Iterator iter = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               if (!iter.hasNext()) {
                  if (countColors <= 0) {
                     return null;
                  }

                  dbg(logName + " colors: " + countColors);
                  return colors;
               }

               key = (String)iter.next();
               value = props.getProperty(key);
            } while(!key.startsWith(prefix));

            String name = StrUtils.removePrefix(key, prefix);
            int index = getMapColorIndex(name);
            int color = parseColor(value);
            if (index >= 0 && index < colors.length && color >= 0) {
               colors[index] = color;
               ++countColors;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }
   }

   private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[getMaxPotionId()];
      Arrays.fill(colors, -1);
      int countColors = 0;
      Set keys = props.keySet();
      Iterator iter = keys.iterator();

      while(true) {
         while(true) {
            String key;
            String value;
            do {
               if (!iter.hasNext()) {
                  if (countColors <= 0) {
                     return null;
                  }

                  dbg(logName + " colors: " + countColors);
                  return colors;
               }

               key = (String)iter.next();
               value = props.getProperty(key);
            } while(!key.startsWith(prefix));

            int index = getPotionId(key);
            int color = parseColor(value);
            if (index >= 0 && index < colors.length && color >= 0) {
               colors[index] = color;
               ++countColors;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }
   }

   private static int getMaxPotionId() {
      int maxId = 0;
      Set keys = uz.b.c();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         nf rl = (nf)it.next();
         uz potion = (uz)uz.b.c(rl);
         int id = uz.a(potion);
         if (id > maxId) {
            maxId = id;
         }
      }

      return maxId;
   }

   private static int getPotionId(String name) {
      if (name.equals("potion.water")) {
         return 0;
      } else {
         name = StrUtils.replacePrefix(name, "potion.", "effect.");
         Set keys = uz.b.c();
         Iterator it = keys.iterator();

         uz potion;
         do {
            if (!it.hasNext()) {
               return -1;
            }

            nf rl = (nf)it.next();
            potion = (uz)uz.b.c(rl);
         } while(!potion.a().equals(name));

         return uz.a(potion);
      }
   }

   public static int getPotionColor(uz potion, int color) {
      int potionId = 0;
      if (potion != null) {
         potionId = uz.a(potion);
      }

      return getPotionColor(potionId, color);
   }

   public static int getPotionColor(int potionId, int color) {
      if (potionColors == null) {
         return color;
      } else if (potionId >= 0 && potionId < potionColors.length) {
         int potionColor = potionColors[potionId];
         return potionColor < 0 ? color : potionColor;
      } else {
         return color;
      }
   }

   private static int getMapColorIndex(String name) {
      if (name == null) {
         return -1;
      } else if (name.equals("air")) {
         return bda.c.ad;
      } else if (name.equals("grass")) {
         return bda.d.ad;
      } else if (name.equals("sand")) {
         return bda.e.ad;
      } else if (name.equals("cloth")) {
         return bda.f.ad;
      } else if (name.equals("tnt")) {
         return bda.g.ad;
      } else if (name.equals("ice")) {
         return bda.h.ad;
      } else if (name.equals("iron")) {
         return bda.i.ad;
      } else if (name.equals("foliage")) {
         return bda.j.ad;
      } else if (name.equals("clay")) {
         return bda.l.ad;
      } else if (name.equals("dirt")) {
         return bda.m.ad;
      } else if (name.equals("stone")) {
         return bda.n.ad;
      } else if (name.equals("water")) {
         return bda.o.ad;
      } else if (name.equals("wood")) {
         return bda.p.ad;
      } else if (name.equals("quartz")) {
         return bda.q.ad;
      } else if (name.equals("gold")) {
         return bda.G.ad;
      } else if (name.equals("diamond")) {
         return bda.H.ad;
      } else if (name.equals("lapis")) {
         return bda.I.ad;
      } else if (name.equals("emerald")) {
         return bda.J.ad;
      } else if (name.equals("podzol")) {
         return bda.K.ad;
      } else if (name.equals("netherrack")) {
         return bda.L.ad;
      } else if (!name.equals("snow") && !name.equals("white")) {
         if (!name.equals("adobe") && !name.equals("orange")) {
            if (name.equals("magenta")) {
               return bda.s.ad;
            } else if (!name.equals("light_blue") && !name.equals("lightBlue")) {
               if (name.equals("yellow")) {
                  return bda.u.ad;
               } else if (name.equals("lime")) {
                  return bda.v.ad;
               } else if (name.equals("pink")) {
                  return bda.w.ad;
               } else if (name.equals("gray")) {
                  return bda.x.ad;
               } else if (name.equals("silver")) {
                  return bda.y.ad;
               } else if (name.equals("cyan")) {
                  return bda.z.ad;
               } else if (name.equals("purple")) {
                  return bda.A.ad;
               } else if (name.equals("blue")) {
                  return bda.B.ad;
               } else if (name.equals("brown")) {
                  return bda.C.ad;
               } else if (name.equals("green")) {
                  return bda.D.ad;
               } else if (name.equals("red")) {
                  return bda.E.ad;
               } else {
                  return name.equals("black") ? bda.F.ad : -1;
               }
            } else {
               return bda.t.ad;
            }
         } else {
            return bda.r.ad;
         }
      } else {
         return bda.k.ad;
      }
   }

   private static int[] getMapColors() {
      bda[] mapColors = bda.a;
      int[] colors = new int[mapColors.length];
      Arrays.fill(colors, -1);

      for(int i = 0; i < mapColors.length && i < colors.length; ++i) {
         bda mapColor = mapColors[i];
         if (mapColor != null) {
            colors[i] = mapColor.ac;
         }
      }

      return colors;
   }

   private static void setMapColors(int[] colors) {
      if (colors != null) {
         bda[] mapColors = bda.a;
         boolean changed = false;

         for(int i = 0; i < mapColors.length && i < colors.length; ++i) {
            bda mapColor = mapColors[i];
            if (mapColor != null) {
               int color = colors[i];
               if (color >= 0 && mapColor.ac != color) {
                  mapColor.ac = color;
                  changed = true;
               }
            }
         }

         if (changed) {
            bib.z().N().reloadBannerTextures();
         }

      }
   }

   private static void dbg(String str) {
      .Config.dbg("CustomColors: " + str);
   }

   private static void warn(String str) {
      .Config.warn("CustomColors: " + str);
   }

   public static int getExpBarTextColor(int color) {
      return expBarTextColor < 0 ? color : expBarTextColor;
   }

   public static int getBossTextColor(int color) {
      return bossTextColor < 0 ? color : bossTextColor;
   }

   public static int getSignTextColor(int color) {
      return signTextColor < 0 ? color : signTextColor;
   }

   static {
      BLOCK_STATE_DIRT = aox.d.t();
      BLOCK_STATE_WATER = aox.j.t();
      random = new Random();
      COLORIZER_GRASS = new CustomColors.IColorizer() {
         public int getColor(awt blockState, amy blockAccess, et blockPos) {
            anh biome = CustomColors.getColorBiome(blockAccess, blockPos);
            return CustomColors.swampGrassColors != null && biome == anm.h ? CustomColors.swampGrassColors.getColor(biome, blockPos) : biome.b(blockPos);
         }

         public boolean isColorConstant() {
            return false;
         }
      };
      COLORIZER_FOLIAGE = new CustomColors.IColorizer() {
         public int getColor(awt blockState, amy blockAccess, et blockPos) {
            anh biome = CustomColors.getColorBiome(blockAccess, blockPos);
            return CustomColors.swampFoliageColors != null && biome == anm.h ? CustomColors.swampFoliageColors.getColor(biome, blockPos) : biome.c(blockPos);
         }

         public boolean isColorConstant() {
            return false;
         }
      };
      COLORIZER_FOLIAGE_PINE = new CustomColors.IColorizer() {
         public int getColor(awt blockState, amy blockAccess, et blockPos) {
            return CustomColors.foliagePineColors != null ? CustomColors.foliagePineColors.getColor(blockAccess, blockPos) : amq.a();
         }

         public boolean isColorConstant() {
            return CustomColors.foliagePineColors == null;
         }
      };
      COLORIZER_FOLIAGE_BIRCH = new CustomColors.IColorizer() {
         public int getColor(awt blockState, amy blockAccess, et blockPos) {
            return CustomColors.foliageBirchColors != null ? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos) : amq.b();
         }

         public boolean isColorConstant() {
            return CustomColors.foliageBirchColors == null;
         }
      };
      COLORIZER_WATER = new CustomColors.IColorizer() {
         public int getColor(awt blockState, amy blockAccess, et blockPos) {
            anh biome = CustomColors.getColorBiome(blockAccess, blockPos);
            if (CustomColors.waterColors != null) {
               return CustomColors.waterColors.getColor(biome, blockPos);
            } else {
               return Reflector.ForgeBiome_getWaterColorMultiplier.exists() ? Reflector.callInt(biome, Reflector.ForgeBiome_getWaterColorMultiplier) : biome.o();
            }
         }

         public boolean isColorConstant() {
            return false;
         }
      };
   }

   public interface IColorizer {
      int getColor(awt var1, amy var2, et var3);

      boolean isColorConstant();
   }
}
