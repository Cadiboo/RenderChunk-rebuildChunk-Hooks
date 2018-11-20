package mods.betterfoliage.client.texture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"},
   d2 = {"Lmods/betterfoliage/client/texture/TextureMatcher;", "", "()V", "mappings", "", "Lmods/betterfoliage/client/texture/TextureMatcher$Mapping;", "getMappings", "()Ljava/util/List;", "getType", "", "icon", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "loadMappings", "", "mappingLocation", "Lnet/minecraft/util/ResourceLocation;", "Mapping", "BetterFoliage-MC1.12"}
)
public final class TextureMatcher {
   @NotNull
   private final List mappings;

   @NotNull
   public final List getMappings() {
      return this.mappings;
   }

   @Nullable
   public final String getType(@NotNull TextureAtlasSprite icon) {
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      Iterable $receiver$iv = (Iterable)this.mappings;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      Iterator var5 = $receiver$iv.iterator();

      Object item$iv$iv;
      TextureMatcher.Mapping it;
      while(var5.hasNext()) {
         item$iv$iv = var5.next();
         it = (TextureMatcher.Mapping)item$iv$iv;
         if (it.matches(icon)) {
            destination$iv$iv.add(item$iv$iv);
         }
      }

      $receiver$iv = (Iterable)((List)destination$iv$iv);
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      var5 = $receiver$iv.iterator();

      while(var5.hasNext()) {
         item$iv$iv = var5.next();
         it = (TextureMatcher.Mapping)item$iv$iv;
         String var12 = it.getType();
         destination$iv$iv.add(var12);
      }

      return (String)CollectionsKt.firstOrNull((List)destination$iv$iv);
   }

   public final void loadMappings(@NotNull ResourceLocation mappingLocation) {
      Intrinsics.checkParameterIsNotNull(mappingLocation, "mappingLocation");
      this.mappings.clear();
      IResource var10000 = mods.octarinecore.client.resource.Utils.get((IResourceManager)mods.octarinecore.client.resource.Utils.getResourceManager(), mappingLocation);
      if (var10000 != null) {
         List var25 = mods.octarinecore.client.resource.Utils.getLines(var10000);
         if (var25 != null) {
            List var2 = var25;
            Iterable $receiver$iv = (Iterable)var2;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            Iterator var7 = $receiver$iv.iterator();

            Object element$iv$iv;
            String it;
            while(var7.hasNext()) {
               element$iv$iv = var7.next();
               it = (String)element$iv$iv;
               if (!StringsKt.startsWith$default(it, "//", false, 2, (Object)null)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $receiver$iv = (Iterable)((List)destination$iv$iv);
            destination$iv$iv = (Collection)(new ArrayList());
            var7 = $receiver$iv.iterator();

            while(var7.hasNext()) {
               element$iv$iv = var7.next();
               it = (String)element$iv$iv;
               CharSequence var10 = (CharSequence)it;
               if (var10.length() != 0) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $receiver$iv = (Iterable)((List)destination$iv$iv);
            Iterator var5 = $receiver$iv.iterator();

            while(var5.hasNext()) {
               Object element$iv = var5.next();
               String line = (String)element$iv;
               if (line == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
               }

               List line2 = StringsKt.split$default((CharSequence)StringsKt.trim((CharSequence)line).toString(), new char[]{'='}, false, 0, 6, (Object)null);
               if (line2.size() == 2) {
                  String var24 = (String)line2.get(0);
                  if (var24 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                  }

                  List mapping = StringsKt.split$default((CharSequence)StringsKt.trim((CharSequence)var24).toString(), new char[]{':'}, false, 0, 6, (Object)null);
                  String var11;
                  TextureMatcher.Mapping var12;
                  TextureMatcher.Mapping var13;
                  List var14;
                  String var15;
                  String var16;
                  TextureMatcher.Mapping var10001;
                  if (mapping.size() == 1) {
                     var25 = this.mappings;
                     var10001 = new TextureMatcher.Mapping;
                     var24 = (String)mapping.get(0);
                     var11 = null;
                     var12 = var10001;
                     var13 = var10001;
                     var14 = var25;
                     if (var24 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var15 = StringsKt.trim((CharSequence)var24).toString();
                     var24 = (String)line2.get(1);
                     if (var24 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var16 = StringsKt.trim((CharSequence)var24).toString();
                     var12.<init>(var11, var15, var16);
                     var14.add(var13);
                  } else if (mapping.size() == 2) {
                     var25 = this.mappings;
                     var10001 = new TextureMatcher.Mapping;
                     var24 = (String)mapping.get(0);
                     var12 = var10001;
                     var13 = var10001;
                     var14 = var25;
                     if (var24 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var11 = StringsKt.trim((CharSequence)var24).toString();
                     var24 = (String)mapping.get(1);
                     if (var24 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var15 = StringsKt.trim((CharSequence)var24).toString();
                     var24 = (String)line2.get(1);
                     if (var24 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                     }

                     var16 = StringsKt.trim((CharSequence)var24).toString();
                     var12.<init>(var11, var15, var16);
                     var14.add(var13);
                  }
               }
            }
         }
      }

   }

   public TextureMatcher() {
      List var2 = (List)(new ArrayList());
      this.mappings = var2;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\u000e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0018"},
      d2 = {"Lmods/betterfoliage/client/texture/TextureMatcher$Mapping;", "", "domain", "", "path", "type", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDomain", "()Ljava/lang/String;", "getPath", "getType", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "matches", "icon", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "toString", "BetterFoliage-MC1.12"}
   )
   public static final class Mapping {
      @Nullable
      private final String domain;
      @NotNull
      private final String path;
      @NotNull
      private final String type;

      public final boolean matches(@NotNull TextureAtlasSprite icon) {
         Intrinsics.checkParameterIsNotNull(icon, "icon");
         ResourceLocation iconLocation = new ResourceLocation(icon.func_94215_i());
         boolean var8;
         if (this.domain == null || Intrinsics.areEqual(this.domain, iconLocation.func_110624_b())) {
            String var10000 = iconLocation.func_110623_a();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "iconLocation.path");
            String $receiver$iv = var10000;
            String str$iv = "blocks/";
            if (StringsKt.startsWith$default($receiver$iv, str$iv, false, 2, (Object)null)) {
               int var6 = str$iv.length();
               if ($receiver$iv == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var10000 = $receiver$iv.substring(var6);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            } else {
               var10000 = $receiver$iv;
            }

            if (StringsKt.contains((CharSequence)var10000, (CharSequence)this.path, true)) {
               var8 = true;
               return var8;
            }
         }

         var8 = false;
         return var8;
      }

      @Nullable
      public final String getDomain() {
         return this.domain;
      }

      @NotNull
      public final String getPath() {
         return this.path;
      }

      @NotNull
      public final String getType() {
         return this.type;
      }

      public Mapping(@Nullable String domain, @NotNull String path, @NotNull String type) {
         Intrinsics.checkParameterIsNotNull(path, "path");
         Intrinsics.checkParameterIsNotNull(type, "type");
         super();
         this.domain = domain;
         this.path = path;
         this.type = type;
      }

      @Nullable
      public final String component1() {
         return this.domain;
      }

      @NotNull
      public final String component2() {
         return this.path;
      }

      @NotNull
      public final String component3() {
         return this.type;
      }

      @NotNull
      public final TextureMatcher.Mapping copy(@Nullable String domain, @NotNull String path, @NotNull String type) {
         Intrinsics.checkParameterIsNotNull(path, "path");
         Intrinsics.checkParameterIsNotNull(type, "type");
         return new TextureMatcher.Mapping(domain, path, type);
      }

      public String toString() {
         return "Mapping(domain=" + this.domain + ", path=" + this.path + ", type=" + this.type + ")";
      }

      public int hashCode() {
         return ((this.domain != null ? this.domain.hashCode() : 0) * 31 + (this.path != null ? this.path.hashCode() : 0)) * 31 + (this.type != null ? this.type.hashCode() : 0);
      }

      public boolean equals(Object var1) {
         if (this != var1) {
            if (var1 instanceof TextureMatcher.Mapping) {
               TextureMatcher.Mapping var2 = (TextureMatcher.Mapping)var1;
               if (Intrinsics.areEqual(this.domain, var2.domain) && Intrinsics.areEqual(this.path, var2.path) && Intrinsics.areEqual(this.type, var2.type)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }
}
