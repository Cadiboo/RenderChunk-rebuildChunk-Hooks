package net.optifine.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomItems;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomPanorama;
import net.optifine.CustomSky;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartLeaves;
import net.optifine.TextureAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.Shaders;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class TextureUtils {
   public static final String texGrassTop = "grass_top";
   public static final String texStone = "stone";
   public static final String texDirt = "dirt";
   public static final String texCoarseDirt = "coarse_dirt";
   public static final String texGrassSide = "grass_side";
   public static final String texStoneslabSide = "stone_slab_side";
   public static final String texStoneslabTop = "stone_slab_top";
   public static final String texBedrock = "bedrock";
   public static final String texSand = "sand";
   public static final String texGravel = "gravel";
   public static final String texLogOak = "log_oak";
   public static final String texLogBigOak = "log_big_oak";
   public static final String texLogAcacia = "log_acacia";
   public static final String texLogSpruce = "log_spruce";
   public static final String texLogBirch = "log_birch";
   public static final String texLogJungle = "log_jungle";
   public static final String texLogOakTop = "log_oak_top";
   public static final String texLogBigOakTop = "log_big_oak_top";
   public static final String texLogAcaciaTop = "log_acacia_top";
   public static final String texLogSpruceTop = "log_spruce_top";
   public static final String texLogBirchTop = "log_birch_top";
   public static final String texLogJungleTop = "log_jungle_top";
   public static final String texLeavesOak = "leaves_oak";
   public static final String texLeavesBigOak = "leaves_big_oak";
   public static final String texLeavesAcacia = "leaves_acacia";
   public static final String texLeavesBirch = "leaves_birch";
   public static final String texLeavesSpuce = "leaves_spruce";
   public static final String texLeavesJungle = "leaves_jungle";
   public static final String texGoldOre = "gold_ore";
   public static final String texIronOre = "iron_ore";
   public static final String texCoalOre = "coal_ore";
   public static final String texObsidian = "obsidian";
   public static final String texGrassSideOverlay = "grass_side_overlay";
   public static final String texSnow = "snow";
   public static final String texGrassSideSnowed = "grass_side_snowed";
   public static final String texMyceliumSide = "mycelium_side";
   public static final String texMyceliumTop = "mycelium_top";
   public static final String texDiamondOre = "diamond_ore";
   public static final String texRedstoneOre = "redstone_ore";
   public static final String texLapisOre = "lapis_ore";
   public static final String texCactusSide = "cactus_side";
   public static final String texClay = "clay";
   public static final String texFarmlandWet = "farmland_wet";
   public static final String texFarmlandDry = "farmland_dry";
   public static final String texNetherrack = "netherrack";
   public static final String texSoulSand = "soul_sand";
   public static final String texGlowstone = "glowstone";
   public static final String texLeavesSpruce = "leaves_spruce";
   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
   public static final String texEndStone = "end_stone";
   public static final String texSandstoneTop = "sandstone_top";
   public static final String texSandstoneBottom = "sandstone_bottom";
   public static final String texRedstoneLampOff = "redstone_lamp_off";
   public static final String texRedstoneLampOn = "redstone_lamp_on";
   public static final String texWaterStill = "water_still";
   public static final String texWaterFlow = "water_flow";
   public static final String texLavaStill = "lava_still";
   public static final String texLavaFlow = "lava_flow";
   public static final String texFireLayer0 = "fire_layer_0";
   public static final String texFireLayer1 = "fire_layer_1";
   public static final String texPortal = "portal";
   public static final String texGlass = "glass";
   public static final String texGlassPaneTop = "glass_pane_top";
   public static final String texCompass = "compass";
   public static final String texClock = "clock";
   public static cdq iconGrassTop;
   public static cdq iconGrassSide;
   public static cdq iconGrassSideOverlay;
   public static cdq iconSnow;
   public static cdq iconGrassSideSnowed;
   public static cdq iconMyceliumSide;
   public static cdq iconMyceliumTop;
   public static cdq iconWaterStill;
   public static cdq iconWaterFlow;
   public static cdq iconLavaStill;
   public static cdq iconLavaFlow;
   public static cdq iconPortal;
   public static cdq iconFireLayer0;
   public static cdq iconFireLayer1;
   public static cdq iconGlass;
   public static cdq iconGlassPaneTop;
   public static cdq iconCompass;
   public static cdq iconClock;
   public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
   public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
   private static IntBuffer staticBuffer = bia.f(256);

   public static void update() {
      cdp mapBlocks = getTextureMapBlocks();
      if (mapBlocks != null) {
         String prefix = "minecraft:blocks/";
         iconGrassTop = mapBlocks.getSpriteSafe(prefix + "grass_top");
         iconGrassSide = mapBlocks.getSpriteSafe(prefix + "grass_side");
         iconGrassSideOverlay = mapBlocks.getSpriteSafe(prefix + "grass_side_overlay");
         iconSnow = mapBlocks.getSpriteSafe(prefix + "snow");
         iconGrassSideSnowed = mapBlocks.getSpriteSafe(prefix + "grass_side_snowed");
         iconMyceliumSide = mapBlocks.getSpriteSafe(prefix + "mycelium_side");
         iconMyceliumTop = mapBlocks.getSpriteSafe(prefix + "mycelium_top");
         iconWaterStill = mapBlocks.getSpriteSafe(prefix + "water_still");
         iconWaterFlow = mapBlocks.getSpriteSafe(prefix + "water_flow");
         iconLavaStill = mapBlocks.getSpriteSafe(prefix + "lava_still");
         iconLavaFlow = mapBlocks.getSpriteSafe(prefix + "lava_flow");
         iconFireLayer0 = mapBlocks.getSpriteSafe(prefix + "fire_layer_0");
         iconFireLayer1 = mapBlocks.getSpriteSafe(prefix + "fire_layer_1");
         iconPortal = mapBlocks.getSpriteSafe(prefix + "portal");
         iconGlass = mapBlocks.getSpriteSafe(prefix + "glass");
         iconGlassPaneTop = mapBlocks.getSpriteSafe(prefix + "glass_pane_top");
         String prefixItems = "minecraft:items/";
         iconCompass = mapBlocks.getSpriteSafe(prefixItems + "compass");
         iconClock = mapBlocks.getSpriteSafe(prefixItems + "clock");
      }
   }

   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
      if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
         int width = bi.getWidth();
         int height = bi.getHeight();
         if (width == height * 2) {
            BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
            Graphics2D gr = scaledImage.createGraphics();
            gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr.drawImage(bi, 0, 0, width, height, (ImageObserver)null);
            return scaledImage;
         }
      }

      return bi;
   }

   public static int ceilPowerOfTwo(int val) {
      int i;
      for(i = 1; i < val; i *= 2) {
         ;
      }

      return i;
   }

   public static int getPowerOfTwo(int val) {
      int i = 1;

      int po2;
      for(po2 = 0; i < val; ++po2) {
         i *= 2;
      }

      return po2;
   }

   public static int twoToPower(int power) {
      int val = 1;

      for(int i = 0; i < power; ++i) {
         val *= 2;
      }

      return val;
   }

   public static cds getTexture(nf loc) {
      cds tex = .Config.getTextureManager().b(loc);
      if (tex != null) {
         return tex;
      } else if (!.Config.hasResource(loc)) {
         return null;
      } else {
         cds tex = new cdm(loc);
         .Config.getTextureManager().a(loc, tex);
         return tex;
      }
   }

   public static void resourcesReloaded(cep rm) {
      if (getTextureMapBlocks() != null) {
         .Config.dbg("*** Reloading custom textures ***");
         CustomSky.reset();
         TextureAnimations.reset();
         update();
         NaturalTextures.update();
         BetterGrass.update();
         BetterSnow.update();
         TextureAnimations.update();
         CustomColors.update();
         CustomSky.update();
         RandomEntities.update();
         CustomItems.updateModels();
         CustomEntityModels.update();
         Shaders.resourcesReloaded();
         Lang.resourcesReloaded();
         .Config.updateTexturePackClouds();
         SmartLeaves.updateLeavesModels();
         CustomPanorama.update();
         CustomGuis.update();
         cce.update();
         CustomLoadingScreens.update();
         CustomBlockLayers.update();
         .Config.getTextureManager().e();
      }
   }

   public static cdp getTextureMapBlocks() {
      return bib.z().R();
   }

   public static void registerResourceListener() {
      cep rm = .Config.getResourceManager();
      if (rm instanceof cen) {
         cen rrm = (cen)rm;
         ceq rl = new ceq() {
            public void a(cep var1) {
               TextureUtils.resourcesReloaded(var1);
            }
         };
         rrm.a(rl);
      }

      cdv tto = new cdv() {
         public void e() {
            TextureAnimations.updateAnimations();
         }

         public void a(cep var1) throws IOException {
         }

         public int b() {
            return 0;
         }

         public void b(boolean p_174936_1, boolean p_174936_2) {
         }

         public void a() {
         }

         public MultiTexID getMultiTexID() {
            return null;
         }
      };
      nf ttol = new nf("optifine/TickableTextures");
      .Config.getTextureManager().a(ttol, tto);
   }

   public static nf fixResourceLocation(nf loc, String basePath) {
      if (!loc.b().equals("minecraft")) {
         return loc;
      } else {
         String path = loc.a();
         String pathFixed = fixResourcePath(path, basePath);
         if (pathFixed != path) {
            loc = new nf(loc.b(), pathFixed);
         }

         return loc;
      }
   }

   public static String fixResourcePath(String path, String basePath) {
      String strAssMc = "assets/minecraft/";
      if (path.startsWith(strAssMc)) {
         path = path.substring(strAssMc.length());
         return path;
      } else if (path.startsWith("./")) {
         path = path.substring(2);
         if (!basePath.endsWith("/")) {
            basePath = basePath + "/";
         }

         path = basePath + path;
         return path;
      } else {
         if (path.startsWith("/~")) {
            path = path.substring(1);
         }

         String strMcpatcher = "mcpatcher/";
         if (path.startsWith("~/")) {
            path = path.substring(2);
            path = strMcpatcher + path;
            return path;
         } else if (path.startsWith("/")) {
            path = strMcpatcher + path.substring(1);
            return path;
         } else {
            return path;
         }
      }
   }

   public static String getBasePath(String path) {
      int pos = path.lastIndexOf(47);
      return pos < 0 ? "" : path.substring(0, pos);
   }

   public static void applyAnisotropicLevel() {
      if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
         float maxLevel = GL11.glGetFloat(34047);
         float level = (float).Config.getAnisotropicFilterLevel();
         level = Math.min(level, maxLevel);
         GL11.glTexParameterf(3553, 34046, level);
      }

   }

   public static void bindTexture(int glTexId) {
      bus.i(glTexId);
   }

   public static boolean isPowerOfTwo(int x) {
      int x2 = rk.c(x);
      return x2 == x;
   }

   public static BufferedImage scaleImage(BufferedImage bi, int w2) {
      int w = bi.getWidth();
      int h = bi.getHeight();
      int h2 = h * w2 / w;
      BufferedImage bi2 = new BufferedImage(w2, h2, 2);
      Graphics2D g2 = bi2.createGraphics();
      Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
      if (w2 < w || w2 % w != 0) {
         method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
      }

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
      g2.drawImage(bi, 0, 0, w2, h2, (ImageObserver)null);
      return bi2;
   }

   public static BufferedImage scaleToPowerOfTwo(BufferedImage bi, int minSize) {
      if (bi == null) {
         return bi;
      } else {
         int w = bi.getWidth();
         int w2 = scaleToPowerOfTwo(w, minSize);
         if (w2 == w) {
            return bi;
         } else {
            BufferedImage bi2 = scaleImage(bi, w2);
            return bi2;
         }
      }
   }

   public static int scaleToPowerOfTwo(int size, int minSize) {
      int size2 = Math.max(size, minSize);
      size2 = rk.c(size2);
      return size2;
   }

   public static int scaleMinTo(int size, int minSize) {
      if (size >= minSize) {
         return size;
      } else {
         int size2;
         for(size2 = size; size2 < minSize; size2 *= 2) {
            ;
         }

         return size2;
      }
   }

   public static Dimension getImageSize(InputStream in, String suffix) {
      Iterator iter = ImageIO.getImageReadersBySuffix(suffix);

      while(true) {
         if (iter.hasNext()) {
            ImageReader reader = (ImageReader)iter.next();

            Dimension var7;
            try {
               ImageInputStream iis = ImageIO.createImageInputStream(in);
               reader.setInput(iis);
               int width = reader.getWidth(reader.getMinIndex());
               int height = reader.getHeight(reader.getMinIndex());
               var7 = new Dimension(width, height);
            } catch (IOException var11) {
               continue;
            } finally {
               reader.dispose();
            }

            return var7;
         }

         return null;
      }
   }

   public static void dbgMipmaps(cdq textureatlassprite) {
      int[][] data = textureatlassprite.a(0);

      for(int l = 0; l < data.length; ++l) {
         int[] ints = data[l];
         if (ints == null) {
            .Config.dbg("" + l + ": " + ints);
         } else {
            .Config.dbg("" + l + ": " + ints.length);
         }
      }

   }

   public static void saveGlTexture(String name, int textureId, int mipmapLevels, int width, int height) {
      bindTexture(textureId);
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      File fileBase = new File(name);
      File dir = fileBase.getParentFile();
      if (dir != null) {
         dir.mkdirs();
      }

      int level;
      File filePng;
      for(level = 0; level < 16; ++level) {
         filePng = new File(name + "_" + level + ".png");
         filePng.delete();
      }

      for(level = 0; level <= mipmapLevels; ++level) {
         filePng = new File(name + "_" + level + ".png");
         int widthLevel = width >> level;
         int heightLevel = height >> level;
         int sizeLevel = widthLevel * heightLevel;
         IntBuffer buf = BufferUtils.createIntBuffer(sizeLevel);
         int[] data = new int[sizeLevel];
         GL11.glGetTexImage(3553, level, 32993, 33639, buf);
         buf.get(data);
         BufferedImage image = new BufferedImage(widthLevel, heightLevel, 2);
         image.setRGB(0, 0, widthLevel, heightLevel, data, 0, widthLevel);

         try {
            ImageIO.write(image, "png", filePng);
            .Config.dbg("Exported: " + filePng);
         } catch (Exception var16) {
            .Config.warn("Error writing: " + filePng);
            .Config.warn("" + var16.getClass().getName() + ": " + var16.getMessage());
         }
      }

   }

   public static void generateCustomMipmaps(cdq tas, int mipmaps) {
      int w = tas.c();
      int h = tas.d();
      ArrayList listDatas2;
      if (tas.k() < 1) {
         listDatas2 = new ArrayList();
         int[][] datas = new int[mipmaps + 1][];
         int[] data = new int[w * h];
         datas[0] = data;
         listDatas2.add(datas);
         tas.a(listDatas2);
      }

      listDatas2 = new ArrayList();
      int frameCount = tas.k();

      for(int i = 0; i < frameCount; ++i) {
         int[] data = getFrameData(tas, i, 0);
         if (data == null || data.length < 1) {
            data = new int[w * h];
         }

         if (data.length != w * h) {
            int dim = (int)Math.round(Math.sqrt((double)data.length));
            if (dim * dim != data.length) {
               data = new int[1];
               dim = 1;
            }

            BufferedImage image = new BufferedImage(dim, dim, 2);
            image.setRGB(0, 0, dim, dim, data, 0, dim);
            BufferedImage image2 = scaleImage(image, w);
            int[] data2 = new int[w * h];
            image2.getRGB(0, 0, w, h, data2, 0, w);
            data = data2;
         }

         int[][] datas2 = new int[mipmaps + 1][];
         datas2[0] = data;
         listDatas2.add(datas2);
      }

      tas.a(listDatas2);
      tas.d(mipmaps);
   }

   public static int[] getFrameData(cdq tas, int frame, int level) {
      List listDatas = tas.getFramesTextureData();
      if (listDatas.size() <= frame) {
         return null;
      } else {
         int[][] datas = (int[][])listDatas.get(frame);
         if (datas != null && datas.length > level) {
            int[] data = datas[level];
            return data;
         } else {
            return null;
         }
      }
   }

   public static int getGLMaximumTextureSize() {
      for(int i = 65536; i > 0; i >>= 1) {
         bus.a(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
         int err = GL11.glGetError();
         int width = bus.c(32868, 0, 4096);
         if (width != 0) {
            return i;
         }
      }

      return -1;
   }
}
