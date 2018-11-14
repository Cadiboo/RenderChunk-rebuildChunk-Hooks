package net.optifine.override;

import net.minecraft.server.MinecraftServer;
import net.optifine.ClearWater;

public class WorldServerOF extends oo {
   private MinecraftServer mcServer;

   public WorldServerOF(MinecraftServer par1MinecraftServer, bfe par2iSaveHandler, bfd worldInfo, int par4, rl par6Profiler) {
      super(par1MinecraftServer, par2iSaveHandler, worldInfo, par4, par6Profiler);
      this.mcServer = par1MinecraftServer;
   }

   public void d() {
      super.d();
      if (!.Config.isTimeDefault()) {
         this.fixWorldTime();
      }

      if (.Config.waterOpacityChanged) {
         .Config.waterOpacityChanged = false;
         ClearWater.updateWaterOpacity(.Config.getGameSettings(), this);
      }

   }

   protected void t() {
      if (!.Config.isWeatherEnabled()) {
         this.fixWorldWeather();
      }

      super.t();
   }

   private void fixWorldWeather() {
      if (this.x.o() || this.x.m()) {
         this.x.g(0);
         this.x.b(false);
         this.k(0.0F);
         this.x.f(0);
         this.x.a(false);
         this.i(0.0F);
         this.mcServer.am().a(new jc(2, 0.0F));
         this.mcServer.am().a(new jc(7, 0.0F));
         this.mcServer.am().a(new jc(8, 0.0F));
      }

   }

   private void fixWorldTime() {
      if (this.x.q().a() == 1) {
         long time = this.S();
         long timeOfDay = time % 24000L;
         if (.Config.isTimeDayOnly()) {
            if (timeOfDay <= 1000L) {
               this.b(time - timeOfDay + 1001L);
            }

            if (timeOfDay >= 11000L) {
               this.b(time - timeOfDay + 24001L);
            }
         }

         if (.Config.isTimeNightOnly()) {
            if (timeOfDay <= 14000L) {
               this.b(time - timeOfDay + 14001L);
            }

            if (timeOfDay >= 22000L) {
               this.b(time - timeOfDay + 24000L + 14001L);
            }
         }

      }
   }
}
