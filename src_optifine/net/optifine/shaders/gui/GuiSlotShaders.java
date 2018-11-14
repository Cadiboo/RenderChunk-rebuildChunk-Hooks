package net.optifine.shaders.gui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.optifine.Lang;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.util.ResUtils;

class GuiSlotShaders extends bjr {
   private ArrayList shaderslist;
   private int selectedIndex;
   private long lastClickedCached = 0L;
   final GuiShaders shadersGui;

   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
      super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
      this.shadersGui = par1GuiShaders;
      this.updateList();
      this.n = 0.0F;
      int posYSelected = this.selectedIndex * slotHeight;
      int wMid = (bottom - top) / 2;
      if (posYSelected > wMid) {
         this.h(posYSelected - wMid);
      }

   }

   public int c() {
      return this.b - 20;
   }

   public void updateList() {
      this.shaderslist = Shaders.listOfShaders();
      this.selectedIndex = 0;
      int i = 0;

      for(int n = this.shaderslist.size(); i < n; ++i) {
         if (((String)this.shaderslist.get(i)).equals(Shaders.currentShaderName)) {
            this.selectedIndex = i;
            break;
         }
      }

   }

   protected int b() {
      return this.shaderslist.size();
   }

   protected void a(int index, boolean doubleClicked, int mouseX, int mouseY) {
      if (index != this.selectedIndex || this.p != this.lastClickedCached) {
         String name = (String)this.shaderslist.get(index);
         IShaderPack sp = Shaders.getShaderPack(name);
         if (this.checkCompatible(sp, index)) {
            this.selectIndex(index);
         }
      }
   }

   private void selectIndex(int index) {
      this.selectedIndex = index;
      this.lastClickedCached = this.p;
      Shaders.setShaderPack((String)this.shaderslist.get(index));
      Shaders.uninit();
      this.shadersGui.updateButtons();
   }

   private boolean checkCompatible(IShaderPack sp, final int index) {
      if (sp == null) {
         return true;
      } else {
         InputStream in = sp.getResourceAsStream("/shaders/shaders.properties");
         Properties props = ResUtils.readProperties(in, "Shaders");
         if (props == null) {
            return true;
         } else {
            String keyVer = "version.1.12.2";
            String relMin = props.getProperty(keyVer);
            if (relMin == null) {
               return true;
            } else {
               relMin = relMin.trim();
               String rel = "E2";
               int res = .Config.compareRelease(rel, relMin);
               if (res >= 0) {
                  return true;
               } else {
                  String verMin = ("HD_U_" + relMin).replace('_', ' ');
                  String msg1 = cey.a("of.message.shaders.nv1", new Object[]{verMin});
                  String msg2 = cey.a("of.message.shaders.nv2", new Object[0]);
                  bkp callback = new bkp() {
                     public void a(boolean result, int id) {
                        if (result) {
                           GuiSlotShaders.this.selectIndex(index);
                        }

                        GuiSlotShaders.this.a.a(GuiSlotShaders.this.shadersGui);
                     }
                  };
                  bkq guiYesNo = new bkq(callback, msg1, msg2, 0);
                  this.a.a(guiYesNo);
                  return false;
               }
            }
         }
      }
   }

   protected boolean a(int index) {
      return index == this.selectedIndex;
   }

   protected int d() {
      return this.b - 6;
   }

   protected int k() {
      return this.b() * 18;
   }

   protected void a() {
   }

   protected void a(int index, int posX, int posY, int contentY, int mouseX, int mouseY, float partialTicks) {
      String label = (String)this.shaderslist.get(index);
      if (label.equals("OFF")) {
         label = Lang.get("of.options.shaders.packNone");
      } else if (label.equals("(internal)")) {
         label = Lang.get("of.options.shaders.packDefault");
      }

      this.shadersGui.drawCenteredString(label, this.b / 2, posY + 1, 14737632);
   }

   public int getSelectedIndex() {
      return this.selectedIndex;
   }
}
