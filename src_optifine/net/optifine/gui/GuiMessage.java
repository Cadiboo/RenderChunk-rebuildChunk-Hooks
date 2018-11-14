package net.optifine.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GuiMessage extends blk {
   private blk parentScreen;
   private String messageLine1;
   private String messageLine2;
   private final List listLines2 = Lists.newArrayList();
   protected String confirmButtonText;
   private int ticksUntilEnable;

   public GuiMessage(blk parentScreen, String line1, String line2) {
      this.parentScreen = parentScreen;
      this.messageLine1 = line1;
      this.messageLine2 = line2;
      this.confirmButtonText = cey.a("gui.done", new Object[0]);
   }

   public void b() {
      this.n.add(new bjn(0, this.l / 2 - 74, this.m / 6 + 96, this.confirmButtonText));
      this.listLines2.clear();
      this.listLines2.addAll(this.q.c(this.messageLine2, this.l - 50));
   }

   protected void a(bja button) throws IOException {
      .Config.getMinecraft().a(this.parentScreen);
   }

   public void a(int mouseX, int mouseY, float partialTicks) {
      this.c();
      this.a(this.q, this.messageLine1, this.l / 2, 70, 16777215);
      int var4 = 90;

      for(Iterator var5 = this.listLines2.iterator(); var5.hasNext(); var4 += this.q.a) {
         String var6 = (String)var5.next();
         this.a(this.q, var6, this.l / 2, var4, 16777215);
      }

      super.a(mouseX, mouseY, partialTicks);
   }

   public void setButtonDelay(int ticksUntilEnable) {
      this.ticksUntilEnable = ticksUntilEnable;

      bja var3;
      for(Iterator var2 = this.n.iterator(); var2.hasNext(); var3.l = false) {
         var3 = (bja)var2.next();
      }

   }

   public void e() {
      super.e();
      bja var2;
      if (--this.ticksUntilEnable == 0) {
         for(Iterator var1 = this.n.iterator(); var1.hasNext(); var2.l = true) {
            var2 = (bja)var1.next();
         }
      }

   }
}
