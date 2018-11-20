package mods.octarinecore.client.resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\"\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J/\u0010\u0013\u001a\u0004\u0018\u0001H\u0014\"\n\b\u0000\u0010\u0014*\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u0019J\b\u0010\u001a\u001a\u00020\u0003H\u0016J\u0016\u0010\u001b\u001a\u0010\u0012\f\u0012\n \u001d*\u0004\u0018\u00010\u00030\u00030\u001cH\u0016J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010 \u001a\u00020!2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u001b\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\""},
   d2 = {"Lmods/octarinecore/client/resource/GeneratorPack;", "Lnet/minecraft/client/resources/IResourcePack;", "name", "", "generators", "", "Lmods/octarinecore/client/resource/GeneratorBase;", "(Ljava/lang/String;[Lmods/octarinecore/client/resource/GeneratorBase;)V", "getGenerators", "()[Lmods/octarinecore/client/resource/GeneratorBase;", "[Lmods/octarinecore/client/resource/GeneratorBase;", "getName", "()Ljava/lang/String;", "getInputStream", "Ljava/io/InputStream;", "location", "Lnet/minecraft/util/ResourceLocation;", "getPackImage", "", "getPackMetadata", "T", "Lnet/minecraft/client/resources/data/IMetadataSection;", "serializer", "Lnet/minecraft/client/resources/data/MetadataSerializer;", "sectionName", "(Lnet/minecraft/client/resources/data/MetadataSerializer;Ljava/lang/String;)Lnet/minecraft/client/resources/data/IMetadataSection;", "getPackName", "getResourceDomains", "Ljava/util/HashSet;", "kotlin.jvm.PlatformType", "inject", "", "resourceExists", "", "BetterFoliage-MC1.12"}
)
public final class GeneratorPack implements IResourcePack {
   @NotNull
   private final String name;
   @NotNull
   private final GeneratorBase[] generators;

   public final void inject() {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public String func_130077_b() {
      return this.name;
   }

   @Nullable
   public Void getPackImage() {
      return null;
   }

   @NotNull
   public HashSet getResourceDomains() {
      HashSet var10000 = new HashSet;
      Object[] $receiver$iv = (Object[])this.generators;
      HashSet var12 = var10000;
      HashSet var11 = var10000;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      int var4 = $receiver$iv.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object item$iv$iv = $receiver$iv$iv[var5];
         GeneratorBase it = (GeneratorBase)item$iv$iv;
         String var14 = it.getDomain();
         destination$iv$iv.add(var14);
      }

      List var13 = (List)destination$iv$iv;
      var12.<init>((Collection)var13);
      return var11;
   }

   @Nullable
   public IMetadataSection func_135058_a(@Nullable MetadataSerializer serializer, @Nullable String sectionName) {
      IMetadataSection var3;
      if (Intrinsics.areEqual(sectionName, "pack")) {
         PackMetadataSection var10000 = new PackMetadataSection((ITextComponent)(new TextComponentString("Generated resources")), 1);
         if (!(var10000 instanceof IMetadataSection)) {
            var10000 = null;
         }

         var3 = (IMetadataSection)var10000;
      } else {
         var3 = null;
      }

      return var3;
   }

   public boolean func_110589_b(@Nullable ResourceLocation location) {
      boolean var10000;
      if (location == null) {
         var10000 = false;
      } else {
         Object[] var2 = (Object[])this.generators;
         Object[] var3 = var2;
         int var4 = var2.length;
         int var5 = 0;

         Object var9;
         while(true) {
            if (var5 >= var4) {
               var9 = null;
               break;
            }

            Object var6 = var3[var5];
            GeneratorBase it = (GeneratorBase)var6;
            if (Intrinsics.areEqual(it.getDomain(), location.func_110624_b()) && it.resourceExists(location)) {
               var9 = var6;
               break;
            }

            ++var5;
         }

         var10000 = var9 != null;
      }

      return var10000;
   }

   @Nullable
   public InputStream func_110590_a(@Nullable ResourceLocation location) {
      InputStream var10000;
      if (location == null) {
         var10000 = null;
      } else {
         Object[] $receiver$iv = (Object[])this.generators;
         Object[] $receiver$iv$iv = $receiver$iv;
         Collection destination$iv$iv = (Collection)(new ArrayList());
         int var5 = $receiver$iv.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object element$iv$iv = $receiver$iv$iv[var6];
            GeneratorBase it = (GeneratorBase)element$iv$iv;
            if (Intrinsics.areEqual(it.getDomain(), location.func_110624_b()) && it.resourceExists(location)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         Iterable $receiver$iv = (Iterable)((List)destination$iv$iv);
         destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
         Iterator var15 = $receiver$iv.iterator();

         while(var15.hasNext()) {
            Object item$iv$iv = var15.next();
            GeneratorBase it = (GeneratorBase)item$iv$iv;
            InputStream var13 = it.getInputStream(location);
            destination$iv$iv.add(var13);
         }

         var10000 = (InputStream)CollectionsKt.first(CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv)));
      }

      return var10000;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final GeneratorBase[] getGenerators() {
      return this.generators;
   }

   public GeneratorPack(@NotNull String name, @NotNull GeneratorBase... generators) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      Intrinsics.checkParameterIsNotNull(generators, "generators");
      super();
      this.name = name;
      this.generators = generators;
   }
}
