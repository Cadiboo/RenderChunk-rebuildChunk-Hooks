package net.optifine.player;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PlayerItemsLayer implements ccg {
   private cct renderPlayer = null;

   public PlayerItemsLayer(cct renderPlayer) {
      this.renderPlayer = renderPlayer;
   }

   public void a(vp entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ticksExisted, float headYaw, float rotationPitch, float scale) {
      this.renderEquippedItems(entityLiving, scale, partialTicks);
   }

   protected void renderEquippedItems(vp entityLiving, float scale, float partialTicks) {
      if (.Config.isShowCapes()) {
         if (entityLiving instanceof bua) {
            bua player = (bua)entityLiving;
            bus.c(1.0F, 1.0F, 1.0F, 1.0F);
            bus.E();
            bus.q();
            bpx modelBipedMain = this.renderPlayer.h();
            PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
            bus.r();
         }
      }
   }

   public boolean a() {
      return false;
   }

   public static void register(Map renderPlayerMap) {
      Set keys = renderPlayerMap.keySet();
      boolean registered = false;
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         Object key = it.next();
         Object renderer = renderPlayerMap.get(key);
         if (renderer instanceof cct) {
            cct renderPlayer = (cct)renderer;
            renderPlayer.a(new PlayerItemsLayer(renderPlayer));
            registered = true;
         }
      }

      if (!registered) {
         .Config.warn("PlayerItemsLayer not registered");
      }

   }
}
