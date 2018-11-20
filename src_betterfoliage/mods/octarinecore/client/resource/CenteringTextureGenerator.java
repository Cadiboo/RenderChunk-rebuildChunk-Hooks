package mods.octarinecore.client.resource;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u000f"},
   d2 = {"Lmods/octarinecore/client/resource/CenteringTextureGenerator;", "Lmods/octarinecore/client/resource/TextureGenerator;", "domain", "", "aspectWidth", "", "aspectHeight", "(Ljava/lang/String;II)V", "getAspectHeight", "()I", "getAspectWidth", "generate", "Ljava/awt/image/BufferedImage;", "params", "Lmods/octarinecore/client/resource/ParameterList;", "BetterFoliage-MC1.12"}
)
public final class CenteringTextureGenerator extends TextureGenerator {
   private final int aspectWidth;
   private final int aspectHeight;

   @Nullable
   public BufferedImage generate(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Pair var10000 = this.targetResource(params);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Pair target = var10000;
      IResource var17 = Utils.get((IResourceManager)Utils.getResourceManager(), (ResourceLocation)target.getSecond());
      if (var17 != null) {
         BufferedImage var18 = Utils.loadImage(var17);
         if (var18 != null) {
            BufferedImage baseTexture = var18;
            int frameWidth = baseTexture.getWidth();
            int frameHeight = baseTexture.getWidth() * this.aspectHeight / this.aspectWidth;
            int frames = baseTexture.getHeight() / frameHeight;
            int size = Math.max(frameWidth, frameHeight);
            BufferedImage resultTexture = new BufferedImage(size, size * frames, 6);
            Graphics2D graphics = resultTexture.createGraphics();
            int frame = 0;
            int var11 = frames - 1;
            if (frame <= var11) {
               while(true) {
                  BufferedImage baseFrame = baseTexture.getSubimage(0, size * frame, frameWidth, frameHeight);
                  BufferedImage resultFrame = new BufferedImage(size, size, 6);
                  Graphics2D var14 = resultFrame.createGraphics();
                  var14.drawImage((Image)baseFrame, (size - frameWidth) / 2, (size - frameHeight) / 2, (ImageObserver)null);
                  graphics.drawImage((Image)resultFrame, 0, size * frame, (ImageObserver)null);
                  if (frame == var11) {
                     break;
                  }

                  ++frame;
               }
            }

            return resultTexture;
         }
      }

      return null;
   }

   public final int getAspectWidth() {
      return this.aspectWidth;
   }

   public final int getAspectHeight() {
      return this.aspectHeight;
   }

   public CenteringTextureGenerator(@NotNull String domain, int aspectWidth, int aspectHeight) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super(domain);
      this.aspectWidth = aspectWidth;
      this.aspectHeight = aspectHeight;
   }
}
