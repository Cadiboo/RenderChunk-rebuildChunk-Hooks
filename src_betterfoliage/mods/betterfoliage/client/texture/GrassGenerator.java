package mods.betterfoliage.client.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.resource.ParameterList;
import mods.octarinecore.client.resource.ResourceType;
import mods.octarinecore.client.resource.TextureGenerator;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"},
   d2 = {"Lmods/betterfoliage/client/texture/GrassGenerator;", "Lmods/octarinecore/client/resource/TextureGenerator;", "domain", "", "(Ljava/lang/String;)V", "generate", "Ljava/awt/image/BufferedImage;", "params", "Lmods/octarinecore/client/resource/ParameterList;", "BetterFoliage-MC1.12"}
)
public final class GrassGenerator extends TextureGenerator {
   @Nullable
   public BufferedImage generate(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Pair var10000 = this.targetResource(params);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Pair target = var10000;
      String var19 = params.get("snowed");
      boolean var20;
      if (var19 != null) {
         String var4 = var19;
         var20 = Boolean.parseBoolean(var4);
      } else {
         var20 = false;
      }

      boolean isSnowed = var20;
      IResource var21 = mods.octarinecore.client.resource.Utils.get((IResourceManager)mods.octarinecore.client.resource.Utils.getResourceManager(), (ResourceLocation)target.getSecond());
      if (var21 != null) {
         BufferedImage var22 = mods.octarinecore.client.resource.Utils.loadImage(var21);
         if (var22 != null) {
            BufferedImage baseTexture = var22;
            BufferedImage result = new BufferedImage(baseTexture.getWidth(), baseTexture.getHeight(), 6);
            Graphics2D graphics = result.createGraphics();
            int size = baseTexture.getWidth();
            int frames = baseTexture.getHeight() / size;
            int x = 0;
            int var10 = frames - 1;
            if (x <= var10) {
               while(true) {
                  BufferedImage baseFrame = baseTexture.getSubimage(0, size * x, size, size);
                  BufferedImage grassFrame = new BufferedImage(size, size, 6);
                  Graphics2D var13 = grassFrame.createGraphics();
                  var13.drawImage((Image)baseFrame, 0, 3 * size / 8, (ImageObserver)null);
                  graphics.drawImage((Image)grassFrame, 0, size * x, (ImageObserver)null);
                  if (x == var10) {
                     break;
                  }

                  ++x;
               }
            }

            if (isSnowed && Intrinsics.areEqual((ResourceType)target.getFirst(), ResourceType.COLOR)) {
               x = 0;
               var10 = result.getWidth() - 1;
               if (x <= var10) {
                  while(true) {
                     int y = 0;
                     int var18 = result.getHeight() - 1;
                     if (y <= var18) {
                        while(true) {
                           mods.octarinecore.client.resource.Utils.set(result, x, y, Utils.blendRGB(mods.octarinecore.client.resource.Utils.get(result, x, y), 16777215, 2, 3));
                           if (y == var18) {
                              break;
                           }

                           ++y;
                        }
                     }

                     if (x == var10) {
                        break;
                     }

                     ++x;
                  }
               }
            }

            return result;
         }
      }

      return null;
   }

   public GrassGenerator(@NotNull String domain) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super(domain);
   }
}
