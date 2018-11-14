package net.optifine;

import apy.a;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.optifine.model.BlockModelUtils;

public class BetterGrass {
   private static boolean betterGrass = true;
   private static boolean betterGrassPath = true;
   private static boolean betterMycelium = true;
   private static boolean betterPodzol = true;
   private static boolean betterGrassSnow = true;
   private static boolean betterMyceliumSnow = true;
   private static boolean betterPodzolSnow = true;
   private static boolean grassMultilayer = false;
   private static cdq spriteGrass = null;
   private static cdq spriteGrassSide = null;
   private static cdq spriteGrassPath = null;
   private static cdq spriteMycelium = null;
   private static cdq spritePodzol = null;
   private static cdq spriteSnow = null;
   private static boolean spritesLoaded = false;
   private static cfy modelCubeGrass = null;
   private static cfy modelGrassPath = null;
   private static cfy modelCubeGrassPath = null;
   private static cfy modelCubeMycelium = null;
   private static cfy modelCubePodzol = null;
   private static cfy modelCubeSnow = null;
   private static boolean modelsLoaded = false;
   private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
   private static final String TEXTURE_GRASS_PATH_DEFAULT = "blocks/grass_path_top";
   private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
   private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
   private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";

   public static void updateIcons(cdp textureMap) {
      spritesLoaded = false;
      modelsLoaded = false;
      loadProperties(textureMap);
   }

   public static void update() {
      if (spritesLoaded) {
         modelCubeGrass = BlockModelUtils.makeModelCube((cdq)spriteGrass, 0);
         if (grassMultilayer) {
            cfy modelCubeGrassSide = BlockModelUtils.makeModelCube((cdq)spriteGrassSide, -1);
            modelCubeGrass = BlockModelUtils.joinModelsCube(modelCubeGrassSide, modelCubeGrass);
         }

         cdq spriteGrassPathSide = .Config.getTextureMap().a(new nf("blocks/grass_path_side"));
         modelGrassPath = BlockModelUtils.makeModel("grass_path", spriteGrassPathSide, spriteGrassPath);
         modelCubeGrassPath = BlockModelUtils.makeModelCube((cdq)spriteGrassPath, -1);
         modelCubeMycelium = BlockModelUtils.makeModelCube((cdq)spriteMycelium, -1);
         modelCubePodzol = BlockModelUtils.makeModelCube((cdq)spritePodzol, 0);
         modelCubeSnow = BlockModelUtils.makeModelCube((cdq)spriteSnow, -1);
         modelsLoaded = true;
      }
   }

   private static void loadProperties(cdp textureMap) {
      betterGrass = true;
      betterGrassPath = true;
      betterMycelium = true;
      betterPodzol = true;
      betterGrassSnow = true;
      betterMyceliumSnow = true;
      betterPodzolSnow = true;
      spriteGrass = textureMap.a(new nf("blocks/grass_top"));
      spriteGrassSide = textureMap.a(new nf("blocks/grass_side"));
      spriteGrassPath = textureMap.a(new nf("blocks/grass_path_top"));
      spriteMycelium = textureMap.a(new nf("blocks/mycelium_top"));
      spritePodzol = textureMap.a(new nf("blocks/dirt_podzol_top"));
      spriteSnow = textureMap.a(new nf("blocks/snow"));
      spritesLoaded = true;
      String name = "optifine/bettergrass.properties";

      try {
         nf locFile = new nf(name);
         if (!.Config.hasResource(locFile)) {
            return;
         }

         InputStream in = .Config.getResourceStream(locFile);
         if (in == null) {
            return;
         }

         boolean defaultConfig = .Config.isFromDefaultResourcePack(locFile);
         if (defaultConfig) {
            .Config.dbg("BetterGrass: Parsing default configuration " + name);
         } else {
            .Config.dbg("BetterGrass: Parsing configuration " + name);
         }

         Properties props = new Properties();
         props.load(in);
         betterGrass = getBoolean(props, "grass", true);
         betterGrassPath = getBoolean(props, "grass_path", true);
         betterMycelium = getBoolean(props, "mycelium", true);
         betterPodzol = getBoolean(props, "podzol", true);
         betterGrassSnow = getBoolean(props, "grass.snow", true);
         betterMyceliumSnow = getBoolean(props, "mycelium.snow", true);
         betterPodzolSnow = getBoolean(props, "podzol.snow", true);
         grassMultilayer = getBoolean(props, "grass.multilayer", false);
         spriteGrass = registerSprite(props, "texture.grass", "blocks/grass_top", textureMap);
         spriteGrassSide = registerSprite(props, "texture.grass_side", "blocks/grass_side", textureMap);
         spriteGrassPath = registerSprite(props, "texture.grass_path", "blocks/grass_path_top", textureMap);
         spriteMycelium = registerSprite(props, "texture.mycelium", "blocks/mycelium_top", textureMap);
         spritePodzol = registerSprite(props, "texture.podzol", "blocks/dirt_podzol_top", textureMap);
         spriteSnow = registerSprite(props, "texture.snow", "blocks/snow", textureMap);
      } catch (IOException var6) {
         .Config.warn("Error reading: " + name + ", " + var6.getClass().getName() + ": " + var6.getMessage());
      }

   }

   private static cdq registerSprite(Properties props, String key, String textureDefault, cdp textureMap) {
      String texture = props.getProperty(key);
      if (texture == null) {
         texture = textureDefault;
      }

      nf locPng = new nf("textures/" + texture + ".png");
      if (!.Config.hasResource(locPng)) {
         .Config.warn("BetterGrass texture not found: " + locPng);
         texture = textureDefault;
      }

      nf locSprite = new nf(texture);
      cdq sprite = textureMap.a(locSprite);
      return sprite;
   }

   public static List getFaceQuads(amy blockAccess, awt blockState, et blockPos, fa facing, List quads) {
      if (facing != fa.b && facing != fa.a) {
         if (!modelsLoaded) {
            return quads;
         } else {
            aow block = blockState.u();
            if (block instanceof asc) {
               return getFaceQuadsMycelium(blockAccess, blockState, blockPos, facing, quads);
            } else if (block instanceof arc) {
               return getFaceQuadsGrassPath(blockAccess, blockState, blockPos, facing, quads);
            } else if (block instanceof apy) {
               return getFaceQuadsDirt(blockAccess, blockState, blockPos, facing, quads);
            } else {
               return block instanceof arb ? getFaceQuadsGrass(blockAccess, blockState, blockPos, facing, quads) : quads;
            }
         }
      } else {
         return quads;
      }
   }

   private static List getFaceQuadsMycelium(amy blockAccess, awt blockState, et blockPos, fa facing, List quads) {
      aow blockUp = blockAccess.o(blockPos.a()).u();
      boolean snowy = blockUp == aox.aJ || blockUp == aox.aH;
      if (.Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterMyceliumSnow && getBlockAt(blockPos, facing, blockAccess) == aox.aH) {
               return modelCubeSnow.a(blockState, facing, 0L);
            }
         } else if (betterMycelium && getBlockAt(blockPos.b(), facing, blockAccess) == aox.bw) {
            return modelCubeMycelium.a(blockState, facing, 0L);
         }
      } else if (snowy) {
         if (betterMyceliumSnow) {
            return modelCubeSnow.a(blockState, facing, 0L);
         }
      } else if (betterMycelium) {
         return modelCubeMycelium.a(blockState, facing, 0L);
      }

      return quads;
   }

   private static List getFaceQuadsGrassPath(amy blockAccess, awt blockState, et blockPos, fa facing, List quads) {
      if (!betterGrassPath) {
         return quads;
      } else if (.Config.isBetterGrassFancy()) {
         return getBlockAt(blockPos.b(), facing, blockAccess) == aox.da ? modelGrassPath.a(blockState, facing, 0L) : quads;
      } else {
         return modelGrassPath.a(blockState, facing, 0L);
      }
   }

   private static List getFaceQuadsDirt(amy blockAccess, awt blockState, et blockPos, fa facing, List quads) {
      aow blockTop = getBlockAt(blockPos, fa.b, blockAccess);
      if (blockState.c(apy.a) != a.c) {
         if (blockTop == aox.da) {
            return betterGrassPath && getBlockAt(blockPos, facing, blockAccess) == aox.da ? modelCubeGrassPath.a(blockState, facing, 0L) : quads;
         } else {
            return quads;
         }
      } else {
         boolean snowy = blockTop == aox.aJ || blockTop == aox.aH;
         if (.Config.isBetterGrassFancy()) {
            if (snowy) {
               if (betterPodzolSnow && getBlockAt(blockPos, facing, blockAccess) == aox.aH) {
                  return modelCubeSnow.a(blockState, facing, 0L);
               }
            } else if (betterPodzol) {
               et posSide = blockPos.b().a(facing);
               awt stateSide = blockAccess.o(posSide);
               if (stateSide.u() == aox.d && stateSide.c(apy.a) == a.c) {
                  return modelCubePodzol.a(blockState, facing, 0L);
               }
            }
         } else if (snowy) {
            if (betterPodzolSnow) {
               return modelCubeSnow.a(blockState, facing, 0L);
            }
         } else if (betterPodzol) {
            return modelCubePodzol.a(blockState, facing, 0L);
         }

         return quads;
      }
   }

   private static List getFaceQuadsGrass(amy blockAccess, awt blockState, et blockPos, fa facing, List quads) {
      aow blockUp = blockAccess.o(blockPos.a()).u();
      boolean snowy = blockUp == aox.aJ || blockUp == aox.aH;
      if (.Config.isBetterGrassFancy()) {
         if (snowy) {
            if (betterGrassSnow && getBlockAt(blockPos, facing, blockAccess) == aox.aH) {
               return modelCubeSnow.a(blockState, facing, 0L);
            }
         } else if (betterGrass && getBlockAt(blockPos.b(), facing, blockAccess) == aox.c) {
            return modelCubeGrass.a(blockState, facing, 0L);
         }
      } else if (snowy) {
         if (betterGrassSnow) {
            return modelCubeSnow.a(blockState, facing, 0L);
         }
      } else if (betterGrass) {
         return modelCubeGrass.a(blockState, facing, 0L);
      }

      return quads;
   }

   private static aow getBlockAt(et blockPos, fa facing, amy blockAccess) {
      et pos = blockPos.a(facing);
      aow block = blockAccess.o(pos).u();
      return block;
   }

   private static boolean getBoolean(Properties props, String key, boolean def) {
      String str = props.getProperty(key);
      return str == null ? def : Boolean.parseBoolean(str);
   }
}
