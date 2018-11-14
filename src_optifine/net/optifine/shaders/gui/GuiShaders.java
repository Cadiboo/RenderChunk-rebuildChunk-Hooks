package net.optifine.shaders.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import net.optifine.Lang;
import net.optifine.gui.TooltipManager;
import net.optifine.gui.TooltipProviderEnumShaderOptions;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.config.EnumShaderOption;
import org.lwjgl.Sys;

public class GuiShaders extends blk {
   protected blk parentGui;
   protected String screenTitle = "Shaders";
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderEnumShaderOptions());
   private int updateTimer = -1;
   private GuiSlotShaders shaderList;
   private boolean saved = false;
   private static float[] QUALITY_MULTIPLIERS = new float[]{0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F};
   private static String[] QUALITY_MULTIPLIER_NAMES = new String[]{"0.5x", "0.7x", "1x", "1.5x", "2x"};
   private static float[] HAND_DEPTH_VALUES = new float[]{0.0625F, 0.125F, 0.25F};
   private static String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};
   public static final int EnumOS_UNKNOWN = 0;
   public static final int EnumOS_WINDOWS = 1;
   public static final int EnumOS_OSX = 2;
   public static final int EnumOS_SOLARIS = 3;
   public static final int EnumOS_LINUX = 4;

   public GuiShaders(blk par1GuiScreen, bid par2GameSettings) {
      this.parentGui = par1GuiScreen;
   }

   public void b() {
      this.screenTitle = cey.a("of.options.shadersTitle", new Object[0]);
      if (Shaders.shadersConfig == null) {
         Shaders.loadConfig();
      }

      int btnWidth = 120;
      int btnHeight = 20;
      int btnX = this.l - btnWidth - 10;
      int baseY = 30;
      int stepY = 20;
      int shaderListWidth = this.l - btnWidth - 20;
      this.shaderList = new GuiSlotShaders(this, shaderListWidth, this.m, baseY, this.m - 50, 16);
      this.shaderList.d(7, 8);
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, btnX, 0 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, btnX, 1 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, btnX, 2 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, btnX, 3 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, btnX, 4 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, btnX, 5 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, btnX, 6 * stepY + baseY, btnWidth, btnHeight));
      this.n.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, btnX, 7 * stepY + baseY, btnWidth, btnHeight));
      int btnFolderWidth = Math.min(150, shaderListWidth / 2 - 10);
      int xFolder = shaderListWidth / 4 - btnFolderWidth / 2;
      int yFolder = this.m - 25;
      this.n.add(new bja(201, xFolder, yFolder, btnFolderWidth - 22 + 1, btnHeight, Lang.get("of.options.shaders.shadersFolder")));
      this.n.add(new GuiButtonDownloadShaders(210, xFolder + btnFolderWidth - 22 - 1, yFolder));
      this.n.add(new bja(202, shaderListWidth / 4 * 3 - btnFolderWidth / 2, this.m - 25, btnFolderWidth, btnHeight, cey.a("gui.done", new Object[0])));
      this.n.add(new bja(203, btnX, this.m - 25, btnWidth, btnHeight, Lang.get("of.options.shaders.shaderOptions")));
      this.updateButtons();
   }

   public void updateButtons() {
      boolean shaderActive = .Config.isShaders();
      Iterator it = this.n.iterator();

      while(it.hasNext()) {
         bja button = (bja)it.next();
         if (button.k != 201 && button.k != 202 && button.k != 210 && button.k != EnumShaderOption.ANTIALIASING.ordinal()) {
            button.l = shaderActive;
         }
      }

   }

   public void k() throws IOException {
      super.k();
      this.shaderList.p();
   }

   protected void a(bja button) {
      if (button.l) {
         if (button instanceof GuiButtonEnumShaderOption) {
            GuiButtonEnumShaderOption gbeso = (GuiButtonEnumShaderOption)button;
            int index;
            float val;
            float[] values;
            String[] names;
            switch(gbeso.getEnumShaderOption()) {
            case ANTIALIASING:
               Shaders.nextAntialiasingLevel();
               Shaders.uninit();
               break;
            case NORMAL_MAP:
               Shaders.configNormalMap = !Shaders.configNormalMap;
               Shaders.uninit();
               this.j.A();
               break;
            case SPECULAR_MAP:
               Shaders.configSpecularMap = !Shaders.configSpecularMap;
               Shaders.uninit();
               this.j.A();
               break;
            case RENDER_RES_MUL:
               val = Shaders.configRenderResMul;
               values = QUALITY_MULTIPLIERS;
               names = QUALITY_MULTIPLIER_NAMES;
               index = getValueIndex(val, values);
               if (s()) {
                  --index;
                  if (index < 0) {
                     index = values.length - 1;
                  }
               } else {
                  ++index;
                  if (index >= values.length) {
                     index = 0;
                  }
               }

               Shaders.configRenderResMul = values[index];
               Shaders.uninit();
               Shaders.scheduleResize();
               break;
            case SHADOW_RES_MUL:
               val = Shaders.configShadowResMul;
               values = QUALITY_MULTIPLIERS;
               names = QUALITY_MULTIPLIER_NAMES;
               index = getValueIndex(val, values);
               if (s()) {
                  --index;
                  if (index < 0) {
                     index = values.length - 1;
                  }
               } else {
                  ++index;
                  if (index >= values.length) {
                     index = 0;
                  }
               }

               Shaders.configShadowResMul = values[index];
               Shaders.uninit();
               Shaders.scheduleResizeShadow();
               break;
            case HAND_DEPTH_MUL:
               val = Shaders.configHandDepthMul;
               values = HAND_DEPTH_VALUES;
               names = HAND_DEPTH_NAMES;
               index = getValueIndex(val, values);
               if (s()) {
                  --index;
                  if (index < 0) {
                     index = values.length - 1;
                  }
               } else {
                  ++index;
                  if (index >= values.length) {
                     index = 0;
                  }
               }

               Shaders.configHandDepthMul = values[index];
               Shaders.uninit();
               break;
            case OLD_HAND_LIGHT:
               Shaders.configOldHandLight.nextValue();
               Shaders.uninit();
               break;
            case OLD_LIGHTING:
               Shaders.configOldLighting.nextValue();
               Shaders.updateBlockLightLevel();
               Shaders.uninit();
               this.j.A();
               break;
            case TWEAK_BLOCK_DAMAGE:
               Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
               break;
            case CLOUD_SHADOW:
               Shaders.configCloudShadow = !Shaders.configCloudShadow;
               break;
            case TEX_MIN_FIL_B:
               Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
               Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
               button.j = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
               ShadersTex.updateTextureMinMagFilter();
               break;
            case TEX_MAG_FIL_N:
               Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
               button.j = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
               ShadersTex.updateTextureMinMagFilter();
               break;
            case TEX_MAG_FIL_S:
               Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
               button.j = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
               ShadersTex.updateTextureMinMagFilter();
               break;
            case SHADOW_CLIP_FRUSTRUM:
               Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
               button.j = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
               ShadersTex.updateTextureMinMagFilter();
            }

            gbeso.updateButtonText();
         } else {
            switch(button.k) {
            case 201:
               switch(getOSType()) {
               case 1:
                  String var2 = String.format("cmd.exe /C start \"Open file\" \"%s\"", Shaders.shaderPacksDir.getAbsolutePath());

                  try {
                     Runtime.getRuntime().exec(var2);
                     return;
                  } catch (IOException var9) {
                     var9.printStackTrace();
                     break;
                  }
               case 2:
                  try {
                     Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath()});
                     return;
                  } catch (IOException var10) {
                     var10.printStackTrace();
                  }
               }

               boolean var8 = false;

               try {
                  Class var3 = Class.forName("java.awt.Desktop");
                  Object var4 = var3.getMethod("getDesktop").invoke((Object)null);
                  var3.getMethod("browse", URI.class).invoke(var4, (new File(this.j.w, "shaderpacks")).toURI());
               } catch (Throwable var8) {
                  var8.printStackTrace();
                  var8 = true;
               }

               if (var8) {
                  .Config.dbg("Opening via system class!");
                  Sys.openURL("file://" + Shaders.shaderPacksDir.getAbsolutePath());
               }
               break;
            case 202:
               Shaders.storeConfig();
               this.saved = true;
               this.j.a(this.parentGui);
               break;
            case 203:
               GuiShaderOptions gui = new GuiShaderOptions(this, .Config.getGameSettings());
               .Config.getMinecraft().a(gui);
               break;
            case 210:
               try {
                  Class oclass = Class.forName("java.awt.Desktop");
                  Object object = oclass.getMethod("getDesktop").invoke((Object)null);
                  oclass.getMethod("browse", URI.class).invoke(object, new URI("http://optifine.net/shaderPacks"));
               } catch (Throwable var7) {
                  var7.printStackTrace();
               }
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            default:
               this.shaderList.a(button);
            }

         }
      }
   }

   public void m() {
      super.m();
      if (!this.saved) {
         Shaders.storeConfig();
      }

   }

   public void a(int mouseX, int mouseY, float partialTicks) {
      this.c();
      this.shaderList.a(mouseX, mouseY, partialTicks);
      if (this.updateTimer <= 0) {
         this.shaderList.updateList();
         this.updateTimer += 20;
      }

      this.a(this.q, this.screenTitle + " ", this.l / 2, 15, 16777215);
      String info = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
      int infoWidth = this.q.a(info);
      if (infoWidth < this.l - 5) {
         this.a(this.q, info, this.l / 2, this.m - 40, 8421504);
      } else {
         this.c(this.q, info, 5, this.m - 40, 8421504);
      }

      super.a(mouseX, mouseY, partialTicks);
      this.tooltipManager.drawTooltips(mouseX, mouseY, this.n);
   }

   public void e() {
      super.e();
      --this.updateTimer;
   }

   public bib getMc() {
      return this.j;
   }

   public void drawCenteredString(String text, int x, int y, int color) {
      this.a(this.q, text, x, y, color);
   }

   public static String toStringOnOff(boolean value) {
      String on = Lang.getOn();
      String off = Lang.getOff();
      return value ? on : off;
   }

   public static String toStringAa(int value) {
      if (value == 2) {
         return "FXAA 2x";
      } else {
         return value == 4 ? "FXAA 4x" : Lang.getOff();
      }
   }

   public static String toStringValue(float val, float[] values, String[] names) {
      int index = getValueIndex(val, values);
      return names[index];
   }

   public static int getValueIndex(float val, float[] values) {
      for(int i = 0; i < values.length; ++i) {
         float value = values[i];
         if (value >= val) {
            return i;
         }
      }

      return values.length - 1;
   }

   public static String toStringQuality(float val) {
      return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
   }

   public static String toStringHandDepth(float val) {
      return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
   }

   public static int getOSType() {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.contains("win")) {
         return 1;
      } else if (osName.contains("mac")) {
         return 2;
      } else if (osName.contains("solaris")) {
         return 3;
      } else if (osName.contains("sunos")) {
         return 3;
      } else if (osName.contains("linux")) {
         return 4;
      } else {
         return osName.contains("unix") ? 4 : 0;
      }
   }
}
