package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.octarinecore.client.resource.IconHolder;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.client.resource.ResourceHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"},
   d2 = {"Lmods/betterfoliage/client/render/RisingSoulTextures;", "Lmods/octarinecore/client/resource/ResourceHandler;", "()V", "headIcons", "Lmods/octarinecore/client/resource/IconSet;", "getHeadIcons", "()Lmods/octarinecore/client/resource/IconSet;", "trackIcon", "Lmods/octarinecore/client/resource/IconHolder;", "getTrackIcon", "()Lmods/octarinecore/client/resource/IconHolder;", "afterStitch", "", "BetterFoliage-MC1.12"}
)
public final class RisingSoulTextures extends ResourceHandler {
   @NotNull
   private static final IconSet headIcons;
   @NotNull
   private static final IconHolder trackIcon;
   public static final RisingSoulTextures INSTANCE;

   @NotNull
   public final IconSet getHeadIcons() {
      return headIcons;
   }

   @NotNull
   public final IconHolder getTrackIcon() {
      return trackIcon;
   }

   public void afterStitch() {
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Registered " + headIcons.getNum() + " soul particle textures");
   }

   private RisingSoulTextures() {
      super("betterfoliage");
   }

   static {
      RisingSoulTextures var0 = new RisingSoulTextures();
      INSTANCE = var0;
      headIcons = var0.iconSet("bettergrassandleaves", "blocks/rising_soul_%d");
      trackIcon = var0.iconStatic("bettergrassandleaves", "blocks/soul_track");
   }
}
