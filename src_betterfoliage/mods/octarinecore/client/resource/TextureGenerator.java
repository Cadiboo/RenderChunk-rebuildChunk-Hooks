package mods.octarinecore.client.resource;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
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
   d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH&J?\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032*\u0010\f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u000e0\r\"\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0016J$\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\n0\u0018J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\n\u0018\u00010\u000e2\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u001d"},
   d2 = {"Lmods/octarinecore/client/resource/TextureGenerator;", "Lmods/octarinecore/client/resource/ParameterBasedGenerator;", "domain", "", "(Ljava/lang/String;)V", "generate", "Ljava/awt/image/BufferedImage;", "params", "Lmods/octarinecore/client/resource/ParameterList;", "generatedResource", "Lnet/minecraft/util/ResourceLocation;", "iconName", "extraParams", "", "Lkotlin/Pair;", "", "(Ljava/lang/String;[Lkotlin/Pair;)Lnet/minecraft/util/ResourceLocation;", "getInputStream", "Ljava/io/InputStream;", "getMultisizeTexture", "Lnet/minecraft/client/resources/IResource;", "maxSize", "", "maskPath", "Lkotlin/Function1;", "resourceExists", "", "targetResource", "Lmods/octarinecore/client/resource/ResourceType;", "BetterFoliage-MC1.12"}
)
public abstract class TextureGenerator extends ParameterBasedGenerator {
   @NotNull
   public final ResourceLocation generatedResource(@NotNull String iconName, @NotNull Pair... extraParams) {
      Intrinsics.checkParameterIsNotNull(iconName, "iconName");
      Intrinsics.checkParameterIsNotNull(extraParams, "extraParams");
      ResourceLocation var10000 = new ResourceLocation;
      String var10002 = this.getDomain();
      ResourceLocation var3 = Utils.textureLocation(iconName);
      String var23 = var10002;
      ResourceLocation var22 = var10000;
      ResourceLocation var21 = var10000;
      ParameterList var25 = new ParameterList;
      Map var26 = MapsKt.mapOf(new Pair[]{TuplesKt.to("dom", var3.func_110624_b()), TuplesKt.to("path", var3.func_110623_a())});
      Object[] $receiver$iv = (Object[])extraParams;
      Map var6 = var26;
      ParameterList var7 = var25;
      ParameterList var8 = var25;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      int var11 = $receiver$iv.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         Object item$iv$iv = $receiver$iv$iv[var12];
         Pair it = (Pair)item$iv$iv;
         Pair var16 = new Pair(it.getFirst(), it.getSecond().toString());
         destination$iv$iv.add(var16);
      }

      List var15 = (List)destination$iv$iv;
      var7.<init>(MapsKt.plus(var6, (Iterable)var15), "generate");
      String var24 = var8.toString();
      var22.<init>(var23, var24);
      return var21;
   }

   @Nullable
   public final Pair targetResource(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{"dom", "path"});
      boolean var10000;
      if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
         var10000 = true;
      } else {
         Iterator var4 = $receiver$iv.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = true;
               break;
            }

            Object element$iv = var4.next();
            String it = (String)element$iv;
            if (!params.contains(it)) {
               var10000 = false;
               break;
            }
         }
      }

      if (var10000) {
         ResourceLocation var11 = new ResourceLocation;
         String var10002 = params.get("dom");
         if (var10002 == null) {
            Intrinsics.throwNpe();
         }

         String var10003 = params.get("path");
         if (var10003 == null) {
            Intrinsics.throwNpe();
         }

         var11.<init>(var10002, var10003);
         ResourceLocation baseTexture = var11;
         String var12 = params.getValue();
         if (var12 != null) {
            String var10 = var12;
            if (var10 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var12 = var10.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var12, "(this as java.lang.String).toLowerCase()");
         } else {
            var12 = null;
         }

         String var9 = var12;
         Pair var13;
         if (var9 != null) {
            switch(var9.hashCode()) {
            case -1774288545:
               if (var9.equals("generate_n.png")) {
                  var13 = TuplesKt.to(ResourceType.NORMAL, Utils.plus(baseTexture, "_n.png"));
                  return var13;
               }
               break;
            case -1769670940:
               if (var9.equals("generate_s.png")) {
                  var13 = TuplesKt.to(ResourceType.SPECULAR, Utils.plus(baseTexture, "_s.png"));
                  return var13;
               }
               break;
            case 219728409:
               if (var9.equals("generate.png.mcmeta")) {
                  var13 = TuplesKt.to(ResourceType.METADATA, Utils.plus(baseTexture, ".png.mcmeta"));
                  return var13;
               }
               break;
            case 1717366320:
               if (var9.equals("generate.png")) {
                  var13 = TuplesKt.to(ResourceType.COLOR, Utils.plus(baseTexture, ".png"));
                  return var13;
               }
            }
         }

         var13 = null;
         return var13;
      } else {
         return null;
      }
   }

   public boolean resourceExists(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Pair var10000 = this.targetResource(params);
      boolean var6;
      if (var10000 != null) {
         ResourceLocation var5 = (ResourceLocation)var10000.getSecond();
         if (var5 != null) {
            ResourceLocation var2 = var5;
            var6 = Utils.get((IResourceManager)Utils.getResourceManager(), var2) != null;
            return var6;
         }
      }

      var6 = false;
      return var6;
   }

   @Nullable
   public InputStream getInputStream(@NotNull ParameterList params) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      Pair target = this.targetResource(params);
      ResourceType var10000 = target != null ? (ResourceType)target.getFirst() : null;
      InputStream var3;
      if (var10000 == null) {
         var3 = null;
      } else {
         switch(TextureGenerator$WhenMappings.$EnumSwitchMapping$0[var10000.ordinal()]) {
         case 1:
            IResourceManager var4 = (IResourceManager)Utils.getResourceManager();
            if (target == null) {
               Intrinsics.throwNpe();
            }

            IResource var5 = Utils.get(var4, (ResourceLocation)target.getSecond());
            var3 = var5 != null ? var5.func_110527_b() : null;
            break;
         default:
            BufferedImage var6 = this.generate(params);
            var3 = var6 != null ? Utils.getAsStream(var6) : null;
         }
      }

      return var3;
   }

   @Nullable
   public abstract BufferedImage generate(@NotNull ParameterList var1);

   @Nullable
   public final IResource getMultisizeTexture(int maxSize, @NotNull Function1 maskPath) {
      Intrinsics.checkParameterIsNotNull(maskPath, "maskPath");
      int size = maxSize;

      List sizes;
      for(sizes = (List)(new ArrayList()); size > 2; size /= 2) {
         sizes.add(size);
      }

      Iterable $receiver$iv = (Iterable)sizes;
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var8 = $receiver$iv.iterator();

      while(var8.hasNext()) {
         Object item$iv$iv = var8.next();
         int it = ((Number)item$iv$iv).intValue();
         IResource var15 = Utils.get((IResourceManager)Utils.getResourceManager(), (ResourceLocation)maskPath.invoke(it));
         destination$iv$iv.add(var15);
      }

      return (IResource)CollectionsKt.firstOrNull(CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv)));
   }

   public TextureGenerator(@NotNull String domain) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      super(domain);
   }
}
