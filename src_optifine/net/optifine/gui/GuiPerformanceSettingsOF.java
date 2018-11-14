package net.optifine.gui;

import bid.a;

public class GuiPerformanceSettingsOF extends blk {
   private blk prevScreen;
   protected String title;
   private bid settings;
   private static a[] enumOptions;
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiPerformanceSettingsOF(blk guiscreen, bid gamesettings) {
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   public void b() {
      this.title = cey.a("of.options.performanceTitle", new Object[0]);
      this.n.clear();

      for(int i = 0; i < enumOptions.length; ++i) {
         a enumoptions = enumOptions[i];
         int x = this.l / 2 - 155 + i % 2 * 160;
         int y = this.m / 6 + 21 * (i / 2) - 12;
         if (!enumoptions.a()) {
            this.n.add(new GuiOptionButtonOF(enumoptions.c(), x, y, enumoptions, this.settings.c(enumoptions)));
         } else {
            this.n.add(new GuiOptionSliderOF(enumoptions.c(), x, y, enumoptions));
         }
      }

      this.n.add(new bja(200, this.l / 2 - 100, this.m / 6 + 168 + 11, cey.a("gui.done", new Object[0])));
   }

   protected void a(bja guibutton) {
      if (guibutton.l) {
         if (guibutton.k < 200 && guibutton instanceof bjn) {
            this.settings.a(((bjn)guibutton).c(), 1);
            guibutton.j = this.settings.c(a.a(guibutton.k));
         }

         if (guibutton.k == 200) {
            this.j.t.b();
            this.j.a(this.prevScreen);
         }

      }
   }

   public void a(int x, int y, float f) {
      this.c();
      this.a(this.q, this.title, this.l / 2, 15, 16777215);
      super.a(x, y, f);
      this.tooltipManager.drawTooltips(x, y, this.n);
   }

   static {
      enumOptions = new a[]{a.SMOOTH_FPS, a.SMOOTH_WORLD, a.FAST_RENDER, a.FAST_MATH, a.CHUNK_UPDATES, a.CHUNK_UPDATES_DYNAMIC, a.RENDER_REGIONS, a.LAZY_CHUNK_LOADING, a.SMART_ANIMATIONS};
   }
}
