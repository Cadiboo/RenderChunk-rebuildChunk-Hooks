package mods.octarinecore.client.resource;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.HSB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u001e\u0010\u001b\u001a\u00020\u001c*\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00102\u0006\u0010\u001d\u001a\u00020\u0012\u001a\u001d\u0010\u001e\u001a\u00020\n*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\nH\u0086\u0002\u001a\u0017\u0010\u001e\u001a\u0004\u0018\u00010\u000b*\u00020!2\u0006\u0010\"\u001a\u00020\u001aH\u0086\u0002\u001a\u001f\u0010\u001e\u001a\u0004\u0018\u00010#*\u00020$2\u0006\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020\u001aH\u0086\u0002\u001a\u0017\u0010\u001e\u001a\u0004\u0018\u00010#*\u00020$2\u0006\u0010'\u001a\u00020\u0012H\u0086\u0002\u001a\u0010\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000f*\u00020#\u001a\f\u0010)\u001a\u0004\u0018\u00010\u0006*\u00020#\u001a\u0015\u0010*\u001a\u00020\u0012*\u00020\u00122\u0006\u0010+\u001a\u00020\u001aH\u0086\u0002\u001a\u0012\u0010,\u001a\u00020\u000b*\u00020!2\u0006\u0010\"\u001a\u00020\u001a\u001a%\u0010-\u001a\u00020.*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010/\u001a\u00020\nH\u0086\u0002\"\u0011\u0010\u0000\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r\"0\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\u000f*\u00020\u00138FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017¨\u00060"},
   d2 = {"resourceManager", "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;", "getResourceManager", "()Lnet/minecraft/client/resources/SimpleReloadableResourceManager;", "asStream", "Ljava/io/InputStream;", "Ljava/awt/image/BufferedImage;", "getAsStream", "(Ljava/awt/image/BufferedImage;)Ljava/io/InputStream;", "averageColor", "", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getAverageColor", "(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)Ljava/lang/Integer;", "modelBlockAndLoc", "", "Lkotlin/Pair;", "Lnet/minecraft/client/renderer/block/model/ModelBlock;", "Lnet/minecraft/util/ResourceLocation;", "Lnet/minecraftforge/client/model/IModel;", "modelBlockAndLoc$annotations", "(Lnet/minecraftforge/client/model/IModel;)V", "getModelBlockAndLoc", "(Lnet/minecraftforge/client/model/IModel;)Ljava/util/List;", "textureLocation", "iconName", "", "derivesFrom", "", "targetLocation", "get", "x", "y", "Lnet/minecraft/client/renderer/texture/TextureMap;", "name", "Lnet/minecraft/client/resources/IResource;", "Lnet/minecraft/client/resources/IResourceManager;", "domain", "path", "location", "getLines", "loadImage", "plus", "str", "registerSprite", "set", "", "value", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Utils"
)
public final class Utils {
   @NotNull
   public static final SimpleReloadableResourceManager getResourceManager() {
      Minecraft var10000 = Minecraft.func_71410_x();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Minecraft.getMinecraft()");
      IResourceManager var0 = var10000.func_110442_L();
      if (var0 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.resources.SimpleReloadableResourceManager");
      } else {
         return (SimpleReloadableResourceManager)var0;
      }
   }

   @NotNull
   public static final ResourceLocation plus(@NotNull ResourceLocation $receiver, @NotNull String str) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(str, "str");
      return new ResourceLocation($receiver.func_110624_b(), $receiver.func_110623_a() + str);
   }

   @Nullable
   public static final IResource get(@NotNull IResourceManager $receiver, @NotNull String domain, @NotNull String path) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      return get($receiver, new ResourceLocation(domain, path));
   }

   @Nullable
   public static final IResource get(@NotNull final IResourceManager $receiver, @NotNull final ResourceLocation location) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(location, "location");
      return (IResource)mods.octarinecore.Utils.tryDefault((Object)null, (Function0)(new Function0() {
         public final IResource invoke() {
            return $receiver.func_110536_a(location);
         }
      }));
   }

   @Nullable
   public static final TextureAtlasSprite get(@NotNull TextureMap $receiver, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(name, "name");
      return $receiver.getTextureExtry((new ResourceLocation(name)).toString());
   }

   @NotNull
   public static final TextureAtlasSprite registerSprite(@NotNull TextureMap $receiver, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(name, "name");
      TextureAtlasSprite var10000 = $receiver.func_174942_a(new ResourceLocation(name));
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   @Nullable
   public static final BufferedImage loadImage(@NotNull IResource $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return ImageIO.read($receiver.func_110527_b());
   }

   @NotNull
   public static final List getLines(@NotNull IResource $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      ArrayList result = new ArrayList();
      InputStream var10000 = $receiver.func_110527_b();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "inputStream");
      InputStream var2 = var10000;
      Charset var3 = Charsets.UTF_8;
      Reader var5 = (Reader)(new InputStreamReader(var2, var3));
      short var6 = 8192;
      Reader $receiver$iv = (Reader)(var5 instanceof BufferedReader ? (BufferedReader)var5 : new BufferedReader(var5, var6));
      short var4 = 8192;
      Closeable var21 = (Closeable)($receiver$iv instanceof BufferedReader ? (BufferedReader)$receiver$iv : new BufferedReader($receiver$iv, var4));
      Throwable var22 = (Throwable)null;

      try {
         BufferedReader it$iv = (BufferedReader)var21;
         Sequence it = TextStreamsKt.lineSequence(it$iv);
         Iterator var8 = it.iterator();

         while(var8.hasNext()) {
            Object element$iv = var8.next();
            String it = (String)element$iv;
            result.add(it);
         }

         Unit var24 = Unit.INSTANCE;
         return (List)result;
      } catch (Throwable var18) {
         var22 = var18;
         throw var18;
      } finally {
         CloseableKt.closeFinally(var21, var22);
      }
   }

   public static final int get(@NotNull BufferedImage $receiver, int x, int y) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return $receiver.getRGB(x, y);
   }

   public static final void set(@NotNull BufferedImage $receiver, int x, int y, int value) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      $receiver.setRGB(x, y, value);
   }

   @NotNull
   public static final InputStream getAsStream(@NotNull BufferedImage $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      ByteArrayInputStream var10000 = new ByteArrayInputStream;
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      ByteArrayInputStream var5 = var10000;
      ByteArrayInputStream var4 = var10000;
      ImageIO.write((RenderedImage)$receiver, "PNG", (OutputStream)var1);
      byte[] var6 = var1.toByteArray();
      var5.<init>(var6);
      return (InputStream)var4;
   }

   @Nullable
   public static final Integer getAverageColor(@NotNull TextureAtlasSprite $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      ResourceLocation $receiver$iv = new ResourceLocation($receiver.func_94215_i());
      String str$iv = "blocks/";
      ResourceLocation var10000 = new ResourceLocation;
      String var10002 = $receiver$iv.func_110624_b();
      String var10003 = $receiver$iv.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "path");
      String $receiver$iv$iv = var10003;
      String var5 = var10002;
      ResourceLocation var6 = var10000;
      ResourceLocation var7 = var10000;
      String var30;
      if (StringsKt.startsWith$default($receiver$iv$iv, str$iv, false, 2, (Object)null)) {
         int var9 = str$iv.length();
         if ($receiver$iv$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var30 = $receiver$iv$iv.substring(var9);
         Intrinsics.checkExpressionValueIsNotNull(var30, "(this as java.lang.String).substring(startIndex)");
      } else {
         var30 = $receiver$iv$iv;
      }

      String var10 = var30;
      var6.<init>(var5, var10);
      var10000 = new ResourceLocation;
      var10002 = var7.func_110624_b();
      str$iv = "textures/blocks/%s.png";
      Object[] var23 = new Object[]{var7.func_110623_a()};
      String var20 = var10002;
      ResourceLocation var19 = var10000;
      ResourceLocation var18 = var10000;
      var30 = String.format(str$iv, Arrays.copyOf(var23, var23.length));
      Intrinsics.checkExpressionValueIsNotNull(var30, "java.lang.String.format(this, *args)");
      String var21 = var30;
      var19.<init>(var20, var21);
      IResource var31 = get((IResourceManager)getResourceManager(), var18);
      if (var31 != null) {
         BufferedImage var32 = loadImage(var31);
         if (var32 != null) {
            BufferedImage image = var32;
            int numOpaque = 0;
            double sumHueX = 0.0D;
            double sumHueY = 0.0D;
            float sumSaturation = 0.0F;
            float sumBrightness = 0.0F;
            int x = 0;
            int var12 = image.getWidth() - 1;
            if (x <= var12) {
               while(true) {
                  int y = 0;
                  int var14 = image.getHeight() - 1;
                  if (y <= var14) {
                     while(true) {
                        int pixel = get(image, x, y);
                        int alpha = pixel >> 24 & 255;
                        HSB hsb = HSB.Companion.fromColor(pixel);
                        if (alpha == 255) {
                           ++numOpaque;
                           sumHueX += Math.cos(((double)hsb.getHue() - 0.5D) * 6.283185307179586D);
                           sumHueY += Math.sin(((double)hsb.getHue() - 0.5D) * 6.283185307179586D);
                           sumSaturation += hsb.getSaturation();
                           sumBrightness += hsb.getBrightness();
                        }

                        if (y == var14) {
                           break;
                        }

                        ++y;
                     }
                  }

                  if (x == var12) {
                     break;
                  }

                  ++x;
               }
            }

            float avgHue = (float)(Math.atan2(sumHueY, sumHueX) / 6.283185307179586D + 0.5D);
            return (new HSB(avgHue, sumSaturation / (float)numOpaque, sumBrightness / (float)numOpaque)).getAsColor();
         }
      }

      return null;
   }

   @NotNull
   public static final ResourceLocation textureLocation(@NotNull String iconName) {
      Intrinsics.checkParameterIsNotNull(iconName, "iconName");
      ResourceLocation var1 = new ResourceLocation(iconName);
      String var10000 = var1.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "it.path");
      return StringsKt.startsWith$default(var10000, "mcpatcher", false, 2, (Object)null) ? var1 : new ResourceLocation(var1.func_110624_b(), "textures/" + var1.func_110623_a());
   }

   /** @deprecated */
   // $FF: synthetic method
   public static void modelBlockAndLoc$annotations(IModel var0) {
   }

   @NotNull
   public static final List getModelBlockAndLoc(@NotNull IModel $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      if (Refs.INSTANCE.getVanillaModelWrapper().isInstance($receiver)) {
         Pair var17 = new Pair;
         Object var10002 = Refs.INSTANCE.getModel_VMW().get($receiver);
         if (var10002 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.renderer.block.model.ModelBlock");
         } else {
            ModelBlock var18 = (ModelBlock)var10002;
            Object var10003 = Refs.INSTANCE.getLocation_VMW().get($receiver);
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.util.ResourceLocation");
            } else {
               var17.<init>(var18, (ResourceLocation)var10003);
               return CollectionsKt.listOf(var17);
            }
         }
      } else {
         Object var1;
         Collection destination$iv$iv;
         Object var10000;
         if (Refs.INSTANCE.getWeightedRandomModel().isInstance($receiver)) {
            var10000 = Refs.INSTANCE.getModels_WRM().get($receiver);
            if (var10000 != null) {
               var1 = var10000;
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<net.minecraftforge.client.model.IModel>");
               }

               Iterable $receiver$iv = (Iterable)((List)var1);
               destination$iv$iv = (Collection)(new ArrayList());
               Iterator var6 = $receiver$iv.iterator();

               while(var6.hasNext()) {
                  Object element$iv$iv = var6.next();
                  Iterable list$iv$iv = (Iterable)getModelBlockAndLoc((IModel)element$iv$iv);
                  CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
               }

               return (List)destination$iv$iv;
            }
         } else if (Refs.INSTANCE.getMultiModel().isInstance($receiver)) {
            var10000 = Refs.INSTANCE.getBase_MM().get($receiver);
            if (var10000 != null) {
               var1 = var10000;
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type net.minecraftforge.client.model.IModel");
               }

               return getModelBlockAndLoc((IModel)var1);
            }
         } else if (Refs.INSTANCE.getMultipartModel().isInstance($receiver)) {
            var10000 = Refs.INSTANCE.getPartModels_MPM().get($receiver);
            if (var10000 != null) {
               var1 = var10000;
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<kotlin.Any, net.minecraftforge.client.model.IModel>");
               }

               Map $receiver$iv = (Map)var1;
               destination$iv$iv = (Collection)(new ArrayList());
               Iterator var16 = $receiver$iv.entrySet().iterator();

               while(var16.hasNext()) {
                  Entry element$iv$iv = (Entry)var16.next();
                  Iterable list$iv$iv = (Iterable)getModelBlockAndLoc((IModel)element$iv$iv.getValue());
                  CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
               }

               return (List)destination$iv$iv;
            }
         }

         return CollectionsKt.emptyList();
      }
   }

   public static final boolean derivesFrom(@NotNull Pair $receiver, @NotNull ResourceLocation targetLocation) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(targetLocation, "targetLocation");
      ResourceLocation $receiver$iv = (ResourceLocation)$receiver.getSecond();
      String str$iv = "models/";
      ResourceLocation var10000 = new ResourceLocation;
      String var10002 = $receiver$iv.func_110624_b();
      String var10003 = $receiver$iv.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "path");
      String $receiver$iv$iv = var10003;
      String var5 = var10002;
      ResourceLocation var6 = var10000;
      ResourceLocation var7 = var10000;
      String var13;
      if (StringsKt.startsWith$default($receiver$iv$iv, str$iv, false, 2, (Object)null)) {
         int var9 = str$iv.length();
         if ($receiver$iv$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var13 = $receiver$iv$iv.substring(var9);
         Intrinsics.checkExpressionValueIsNotNull(var13, "(this as java.lang.String).substring(startIndex)");
      } else {
         var13 = $receiver$iv$iv;
      }

      String var10 = var13;
      var6.<init>(var5, var10);
      if (Intrinsics.areEqual(var7, targetLocation)) {
         return true;
      } else if (((ModelBlock)$receiver.getFirst()).field_178315_d != null && ((ModelBlock)$receiver.getFirst()).func_178305_e() != null) {
         Pair var14 = new Pair;
         ModelBlock var15 = ((ModelBlock)$receiver.getFirst()).field_178315_d;
         ResourceLocation var16 = ((ModelBlock)$receiver.getFirst()).func_178305_e();
         if (var16 == null) {
            Intrinsics.throwNpe();
         }

         var14.<init>(var15, var16);
         return derivesFrom(var14, targetLocation);
      } else {
         return false;
      }
   }
}
