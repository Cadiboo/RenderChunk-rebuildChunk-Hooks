package mods.betterfoliage.client.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mods.betterfoliage.client.gui.BiomeListConfigEntry;
import mods.octarinecore.common.config.ConfigPropertyDelegate;
import mods.octarinecore.common.config.ConfigPropertyIntList;
import mods.octarinecore.common.config.DelegatingConfigKt;
import mods.octarinecore.common.config.ObsoleteConfigProperty;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\u001a\u001a\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u001a\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002\u001a\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0002\u001a%\u0010\f\u001a\u00020\u0007*\u00020\u00062\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000f0\u000e\"\u00020\u000fH\u0002¢\u0006\u0002\u0010\u0010\u001a%\u0010\u0011\u001a\u00020\u0007*\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u0002¢\u0006\u0002\u0010\u0015\u001a%\u0010\u0016\u001a\u00020\u0007*\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u0002¢\u0006\u0002\u0010\u0015\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"OBSOLETE", "Lmods/octarinecore/common/config/ObsoleteConfigProperty;", "biomeList", "Lmods/octarinecore/common/config/ConfigPropertyIntList;", "defaults", "Lkotlin/Function1;", "Lnet/minecraft/world/biome/Biome;", "", "distanceLimit", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "", "featureEnable", "filterClass", "name", "", "", "(Lnet/minecraft/world/biome/Biome;[Ljava/lang/String;)Z", "filterRain", "min", "", "max", "(Lnet/minecraft/world/biome/Biome;Ljava/lang/Float;Ljava/lang/Float;)Z", "filterTemp", "BetterFoliage-MC1.12"}
)
public final class ConfigKt {
   private static final ObsoleteConfigProperty OBSOLETE = new ObsoleteConfigProperty();

   private static final ConfigPropertyDelegate featureEnable() {
      return DelegatingConfigKt.boolean(true).lang("enabled");
   }

   private static final ConfigPropertyDelegate distanceLimit() {
      return DelegatingConfigKt.int(1, 1000, 1000).lang("distance");
   }

   @NotNull
   public static final ConfigPropertyIntList biomeList(@NotNull final Function1 defaults) {
      Intrinsics.checkParameterIsNotNull(defaults, "defaults");
      ConfigPropertyIntList var1 = DelegatingConfigKt.intList((Function0)(new Function0() {
         @NotNull
         public final Integer[] invoke() {
            RegistryNamespaced var10000 = Biome.field_185377_q;
            Intrinsics.checkExpressionValueIsNotNull(Biome.field_185377_q, "Biome.REGISTRY");
            Iterable $receiver$iv = (Iterable)var10000;
            Collection destination$iv$iv = (Collection)(new ArrayList());
            Iterator var4 = $receiver$iv.iterator();

            Object item$iv$iv;
            Biome it;
            while(var4.hasNext()) {
               item$iv$iv = var4.next();
               it = (Biome)item$iv$iv;
               if (it != null && ((Boolean)defaults.invoke(it)).booleanValue()) {
                  destination$iv$iv.add(item$iv$iv);
               }
            }

            $receiver$iv = (Iterable)((List)destination$iv$iv);
            destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
            var4 = $receiver$iv.iterator();

            while(var4.hasNext()) {
               item$iv$iv = var4.next();
               it = (Biome)item$iv$iv;
               Integer var11 = Biome.field_185377_q.func_148757_b(it);
               destination$iv$iv.add(var11);
            }

            Collection $receiver$ivx = (Collection)((List)destination$iv$iv);
            Object[] var13 = $receiver$ivx.toArray(new Integer[0]);
            if (var13 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            } else {
               return (Integer[])var13;
            }
         }
      }));
      var1.setGuiClass(BiomeListConfigEntry.class);
      return var1;
   }

   private static final boolean filterTemp(@NotNull Biome $receiver, Float min, Float max) {
      return (min == null || min.floatValue() <= $receiver.func_185353_n()) && (max == null || max.floatValue() >= $receiver.func_185353_n());
   }

   private static final boolean filterRain(@NotNull Biome $receiver, Float min, Float max) {
      return (min == null || min.floatValue() <= $receiver.func_76727_i()) && (max == null || max.floatValue() >= $receiver.func_76727_i());
   }

   private static final boolean filterClass(@NotNull Biome $receiver, String... name) {
      Object[] $receiver$iv = (Object[])name;
      int var3 = $receiver$iv.length;
      int var4 = 0;

      boolean var10;
      while(true) {
         if (var4 >= var3) {
            var10 = false;
            break;
         }

         Object element$iv = $receiver$iv[var4];
         String it = (String)element$iv;
         String var10000 = $receiver.getClass().getName();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "this.javaClass.name");
         String var7 = var10000;
         if (var7 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var10000 = var7.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         if (StringsKt.contains$default((CharSequence)var10000, (CharSequence)it, false, 2, (Object)null)) {
            var10 = true;
            break;
         }

         ++var4;
      }

      return var10;
   }

   // $FF: synthetic method
   @NotNull
   public static final ObsoleteConfigProperty access$getOBSOLETE$p() {
      return OBSOLETE;
   }

   // $FF: synthetic method
   @NotNull
   public static final ConfigPropertyDelegate access$featureEnable() {
      return featureEnable();
   }

   // $FF: synthetic method
   @NotNull
   public static final ConfigPropertyDelegate access$distanceLimit() {
      return distanceLimit();
   }

   // $FF: synthetic method
   public static final boolean access$filterTemp(@NotNull Biome $receiver, @Nullable Float min, @Nullable Float max) {
      return filterTemp($receiver, min, max);
   }

   // $FF: synthetic method
   public static final boolean access$filterRain(@NotNull Biome $receiver, @Nullable Float min, @Nullable Float max) {
      return filterRain($receiver, min, max);
   }

   // $FF: synthetic method
   public static final boolean access$filterClass(@NotNull Biome $receiver, @NotNull String... name) {
      return filterClass($receiver, name);
   }
}
