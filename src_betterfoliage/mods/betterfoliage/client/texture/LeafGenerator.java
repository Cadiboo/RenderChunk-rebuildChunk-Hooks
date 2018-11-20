package mods.betterfoliage.client.texture;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
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
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"},
   d2 = {"Lmods/betterfoliage/client/texture/LeafGenerator;", "Lmods/octarinecore/client/resource/TextureGenerator;", "domain", "", "(Ljava/lang/String;)V", "generate", "Ljava/awt/image/BufferedImage;", "params", "Lmods/octarinecore/client/resource/ParameterList;", "getLeafMask", "Lnet/minecraft/client/resources/IResource;", "type", "maxSize", "", "BetterFoliage-MC1.12"}
)
public final class LeafGenerator extends TextureGenerator {
   @Nullable
   public BufferedImage generate(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Pair var10000 = this.targetResource(params);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Pair target = var10000;
      String var31 = params.get("type");
      if (var31 == null) {
         var31 = "default";
      }

      String leafType = var31;
      ResourceLocation $receiver$iv = (ResourceLocation)target.getSecond();
      String str$iv = "textures/";
      ResourceLocation var33 = new ResourceLocation;
      String var10002 = $receiver$iv.func_110624_b();
      String var10003 = $receiver$iv.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "path");
      String $receiver$iv$iv = var10003;
      String var8 = var10002;
      ResourceLocation var9 = var33;
      ResourceLocation var10 = var33;
      int frame;
      if (StringsKt.startsWith$default($receiver$iv$iv, str$iv, false, 2, (Object)null)) {
         frame = str$iv.length();
         if ($receiver$iv$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var31 = $receiver$iv$iv.substring(frame);
         Intrinsics.checkExpressionValueIsNotNull(var31, "(this as java.lang.String).substring(startIndex)");
      } else {
         var31 = $receiver$iv$iv;
      }

      String var13 = var31;
      var9.<init>(var8, var13);
      str$iv = "blocks/";
      var33 = new ResourceLocation;
      var10002 = var10.func_110624_b();
      var10003 = var10.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "path");
      $receiver$iv$iv = var10003;
      var8 = var10002;
      var9 = var33;
      var10 = var33;
      if (StringsKt.startsWith$default($receiver$iv$iv, str$iv, false, 2, (Object)null)) {
         frame = str$iv.length();
         if ($receiver$iv$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var31 = $receiver$iv$iv.substring(frame);
         Intrinsics.checkExpressionValueIsNotNull(var31, "(this as java.lang.String).substring(startIndex)");
      } else {
         var31 = $receiver$iv$iv;
      }

      var13 = var31;
      var9.<init>(var8, var13);
      ResourceLocation handDrawnLoc = new ResourceLocation("betterfoliage", "" + var10.func_110624_b() + "/textures/blocks/" + var10.func_110623_a());
      IResource var34 = mods.octarinecore.client.resource.Utils.get((IResourceManager)mods.octarinecore.client.resource.Utils.getResourceManager(), handDrawnLoc);
      BufferedImage baseTexture;
      BufferedImage var35;
      if (var34 != null) {
         var35 = mods.octarinecore.client.resource.Utils.loadImage(var34);
         if (var35 != null) {
            baseTexture = var35;
            return baseTexture;
         }
      }

      var34 = mods.octarinecore.client.resource.Utils.get((IResourceManager)mods.octarinecore.client.resource.Utils.getResourceManager(), (ResourceLocation)target.getSecond());
      if (var34 != null) {
         var35 = mods.octarinecore.client.resource.Utils.loadImage(var34);
         if (var35 != null) {
            baseTexture = var35;
            final int size = baseTexture.getWidth();
            int frames = baseTexture.getHeight() / size;
            var34 = this.getLeafMask(leafType, size * 2);
            if (var34 == null) {
               var34 = this.getLeafMask("default", size * 2);
            }

            final BufferedImage maskTexture = var34 != null ? mods.octarinecore.client.resource.Utils.loadImage(var34) : null;
            <undefinedtype> scale$ = new Function1() {
               public final int invoke(int i) {
                  BufferedImage var10001 = maskTexture;
                  if (maskTexture == null) {
                     Intrinsics.throwNpe();
                  }

                  return i * var10001.getWidth() / (size * 2);
               }
            };
            BufferedImage leafTexture = new BufferedImage(size * 2, size * 2 * frames, 6);
            Graphics2D graphics = leafTexture.createGraphics();
            frame = 0;
            int var30 = frames - 1;
            if (frame <= var30) {
               while(true) {
                  BufferedImage baseFrame = baseTexture.getSubimage(0, size * frame, size, size);
                  BufferedImage leafFrame = new BufferedImage(size * 2, size * 2, 6);
                  Graphics2D var16 = leafFrame.createGraphics();
                  var16.drawImage((Image)baseFrame, 0, 0, (ImageObserver)null);
                  var16.drawImage((Image)baseFrame, 0, size, (ImageObserver)null);
                  var16.drawImage((Image)baseFrame, size, 0, (ImageObserver)null);
                  var16.drawImage((Image)baseFrame, size, size, (ImageObserver)null);
                  if (Intrinsics.areEqual((ResourceType)target.getFirst(), ResourceType.COLOR) && maskTexture != null) {
                     int x = 0;
                     int var17 = size * 2 - 1;
                     if (x <= var17) {
                        while(true) {
                           int y = 0;
                           int var19 = size * 2 - 1;
                           if (y <= var19) {
                              while(true) {
                                 long basePixel = (long)mods.octarinecore.client.resource.Utils.get(leafFrame, x, y) & 4294967295L;
                                 long maskPixel = (long)mods.octarinecore.client.resource.Utils.get(maskTexture, scale$.invoke(x), scale$.invoke(y)) & 4278190080L | 16777215L;
                                 mods.octarinecore.client.resource.Utils.set(leafFrame, x, y, (int)(basePixel & maskPixel));
                                 if (y == var19) {
                                    break;
                                 }

                                 ++y;
                              }
                           }

                           if (x == var17) {
                              break;
                           }

                           ++x;
                        }
                     }
                  }

                  graphics.drawImage((Image)leafFrame, 0, size * frame * 2, (ImageObserver)null);
                  if (frame == var30) {
                     break;
                  }

                  ++frame;
               }
            }

            return leafTexture;
         }
      }

      return null;
   }

   @Nullable
   public final IResource getLeafMask(@NotNull final String type, int maxSize) {
      Intrinsics.checkParameterIsNotNull(type, "type");
      return this.getMultisizeTexture(maxSize, (Function1)(new Function1() {
         @NotNull
         public final ResourceLocation invoke(int size) {
            return new ResourceLocation("betterfoliage", "textures/blocks/leafmask_" + size + '_' + type + ".png");
         }
      }));
   }

   public LeafGenerator(@NotNull String domain) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super(domain);
   }
}
