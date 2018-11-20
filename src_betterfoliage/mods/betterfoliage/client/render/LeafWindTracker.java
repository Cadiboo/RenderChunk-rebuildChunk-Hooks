package mods.betterfoliage.client.render;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.common.Double3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001dH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006¨\u0006\u001e"},
   d2 = {"Lmods/betterfoliage/client/render/LeafWindTracker;", "", "()V", "current", "Lmods/octarinecore/common/Double3;", "getCurrent", "()Lmods/octarinecore/common/Double3;", "nextChange", "", "getNextChange", "()J", "setNextChange", "(J)V", "random", "Ljava/util/Random;", "getRandom", "()Ljava/util/Random;", "setRandom", "(Ljava/util/Random;)V", "target", "getTarget", "changeWind", "", "world", "Lnet/minecraft/world/World;", "handleWorldLoad", "event", "Lnet/minecraftforge/event/world/WorldEvent$Load;", "handleWorldTick", "Lnet/minecraftforge/fml/common/gameevent/TickEvent$ClientTickEvent;", "BetterFoliage-MC1.12"}
)
public final class LeafWindTracker {
   @NotNull
   private static Random random;
   @NotNull
   private static final Double3 target;
   @NotNull
   private static final Double3 current;
   private static long nextChange;
   public static final LeafWindTracker INSTANCE;

   @NotNull
   public final Random getRandom() {
      return random;
   }

   public final void setRandom(@NotNull Random var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      random = var1;
   }

   @NotNull
   public final Double3 getTarget() {
      return target;
   }

   @NotNull
   public final Double3 getCurrent() {
      return current;
   }

   public final long getNextChange() {
      return nextChange;
   }

   public final void setNextChange(long var1) {
      nextChange = var1;
   }

   public final void changeWind(@NotNull World world) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      WorldInfo var10000 = world.func_72912_H();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "world.worldInfo");
      nextChange = var10000.func_76073_f() + (long)120 + (long)random.nextInt(80);
      double direction = 6.283185307179586D * random.nextDouble();
      double speed = Math.abs(random.nextGaussian()) * Config.fallingLeaves.INSTANCE.getWindStrength() + (!world.func_72896_J() ? 0.0D : Math.abs(random.nextGaussian()) * Config.fallingLeaves.INSTANCE.getStormStrength());
      target.setTo(Math.cos(direction) * speed, 0.0D, Math.sin(direction) * speed);
   }

   @SubscribeEvent
   public final void handleWorldTick(@NotNull ClientTickEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (Intrinsics.areEqual(event.phase, Phase.START)) {
         WorldClient var10000 = Minecraft.func_71410_x().field_71441_e;
         if (var10000 != null) {
            WorldClient var2 = var10000;
            WorldInfo var7 = var2.func_72912_H();
            Intrinsics.checkExpressionValueIsNotNull(var7, "world.worldInfo");
            if (var7.func_76073_f() >= nextChange) {
               INSTANCE.changeWind((World)var2);
            }

            double changeRate = var2.func_72896_J() ? 0.015D : 0.005D;
            current.add(mods.octarinecore.Utils.minmax(target.getX() - current.getX(), -changeRate, changeRate), 0.0D, mods.octarinecore.Utils.minmax(target.getZ() - current.getZ(), -changeRate, changeRate));
         }
      }

   }

   @SubscribeEvent
   public final void handleWorldLoad(@NotNull Load event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (event.getWorld().field_72995_K) {
         World var10001 = event.getWorld();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "event.world");
         this.changeWind(var10001);
      }

   }

   static {
      LeafWindTracker var0 = new LeafWindTracker();
      INSTANCE = var0;
      random = new Random();
      target = Double3.Companion.getZero();
      current = Double3.Companion.getZero();
      MinecraftForge.EVENT_BUS.register(var0);
   }
}
