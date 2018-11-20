package mods.octarinecore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000f\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aK\u0010\u0002\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00030\nH\u0086\b\u001a\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f\u001a\u0016\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001\u001a&\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\f\u001a'\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u00162\u0006\u0010\u0017\u001a\u0002H\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0019¢\u0006\u0002\u0010\u001a\u001a<\u0010\u001b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u0002H\u001f0\u001d0\u001c\"\u0004\b\u0000\u0010\u001e\"\u0004\b\u0001\u0010\u001f*\b\u0012\u0004\u0012\u0002H\u001e0\u00072\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u001f0\u0007\u001a)\u0010!\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0016*\b\u0012\u0004\u0012\u0002H\u00160\"2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\fH\u0086\b\u001a3\u0010%\u001a\u000e\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H(0&\"\u0004\b\u0000\u0010'\"\u0004\b\u0001\u0010(*\u0010\u0012\u0004\u0012\u0002H'\u0012\u0006\u0012\u0004\u0018\u0001H(0&H\u0087\b\u001a>\u0010)\u001a\u0004\u0018\u0001H*\"\u0006\b\u0000\u0010\u0016\u0018\u0001\"\u0004\b\u0001\u0010**\b\u0012\u0004\u0012\u0002H\u00160\u00072\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0016\u0012\u0006\u0012\u0004\u0018\u0001H*0+H\u0086\b¢\u0006\u0002\u0010,\u001a;\u0010-\u001a\u00020\u0003\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00160\u00072\u001e\u0010\t\u001a\u001a\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u00020\u00030.H\u0086\b\u001a?\u0010/\u001a\b\u0012\u0004\u0012\u0002H*0\u001c\"\u0004\b\u0000\u00100\"\u0004\b\u0001\u0010*\"\u0004\b\u0002\u0010\u0016*\b\u0012\u0004\u0012\u0002H\u00160\u00072\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H0\u0012\u0004\u0012\u0002H*0+H\u0086\b\u001a\u001a\u00102\u001a\u00020\u0001*\u00020\u00012\u0006\u00103\u001a\u00020\u00012\u0006\u00104\u001a\u00020\u0001\u001a\u001a\u00102\u001a\u00020\f*\u00020\f2\u0006\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020\f\u001a/\u00105\u001a\u00020\u0003\"\u0006\b\u0000\u0010\u0016\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00160\"2\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H\u0016\u0012\u0004\u0012\u0002H\u00160+H\u0086\b\u001a\u0015\u00106\u001a\u000207*\u0002072\u0006\u00108\u001a\u000207H\u0086\b\u001a\u0015\u00106\u001a\u000209*\u0002092\u0006\u00108\u001a\u000207H\u0086\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006:"},
   d2 = {"PI2", "", "forEachNested", "", "T1", "T2", "list1", "", "list2", "func", "Lkotlin/Function2;", "nextPowerOf2", "", "x", "random", "min", "max", "semiRandom", "y", "z", "seed", "tryDefault", "T", "default", "work", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "cross", "", "Lkotlin/Pair;", "A", "B", "other", "exchange", "", "idx1", "idx2", "filterValuesNotNull", "", "K", "V", "findFirst", "R", "Lkotlin/Function1;", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "forEachPairIndexed", "Lkotlin/Function3;", "mapAs", "C", "transform", "minmax", "minVal", "maxVal", "replace", "stripStart", "", "str", "Lnet/minecraft/util/ResourceLocation;", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Utils"
)
public final class Utils {
   public static final double PI2 = 6.283185307179586D;

   @NotNull
   public static final String stripStart(@NotNull String $receiver, @NotNull String str) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(str, "str");
      String var10000;
      if (StringsKt.startsWith$default($receiver, str, false, 2, (Object)null)) {
         int var4 = str.length();
         var10000 = $receiver.substring(var4);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
      } else {
         var10000 = $receiver;
      }

      return var10000;
   }

   @NotNull
   public static final ResourceLocation stripStart(@NotNull ResourceLocation $receiver, @NotNull String str) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(str, "str");
      ResourceLocation var10000 = new ResourceLocation;
      String var10002 = $receiver.func_110624_b();
      String var10003 = $receiver.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "path");
      String $receiver$iv = var10003;
      String var9 = var10002;
      ResourceLocation var8 = var10000;
      ResourceLocation var7 = var10000;
      String var11;
      if (StringsKt.startsWith$default($receiver$iv, str, false, 2, (Object)null)) {
         int var5 = str.length();
         if ($receiver$iv == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var11 = $receiver$iv.substring(var5);
         Intrinsics.checkExpressionValueIsNotNull(var11, "(this as java.lang.String).substring(startIndex)");
      } else {
         var11 = $receiver$iv;
      }

      String var10 = var11;
      var8.<init>(var9, var10);
      return var7;
   }

   private static final void replace(@NotNull List $receiver, Function1 transform) {
      Iterable $receiver$iv = (Iterable)$receiver;
      int index$iv = 0;
      Iterator var5 = $receiver$iv.iterator();

      while(var5.hasNext()) {
         Object item$iv = var5.next();
         int idx = index$iv++;
         $receiver.set(idx, transform.invoke(item$iv));
      }

   }

   public static final void exchange(@NotNull List $receiver, int idx1, int idx2) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Object e = $receiver.get(idx1);
      $receiver.set(idx1, $receiver.get(idx2));
      $receiver.set(idx2, e);
   }

   @NotNull
   public static final List cross(@NotNull Iterable $receiver, @NotNull Iterable other) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(other, "other");
      Collection destination$iv$iv = (Collection)(new ArrayList());
      Iterator var5 = $receiver.iterator();

      while(var5.hasNext()) {
         Object element$iv$iv = var5.next();
         Object a = element$iv$iv;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(other, 10)));
         Iterator var11 = other.iterator();

         while(var11.hasNext()) {
            Object item$iv$iv = var11.next();
            Pair var15 = TuplesKt.to(a, item$iv$iv);
            destination$iv$iv.add(var15);
         }

         Iterable list$iv$iv = (Iterable)((List)destination$iv$iv);
         CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
      }

      return (List)destination$iv$iv;
   }

   @NotNull
   public static final List mapAs(@NotNull Iterable $receiver, @NotNull Function1 transform) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver, 10)));
      Iterator var6 = $receiver.iterator();

      while(var6.hasNext()) {
         Object item$iv$iv = var6.next();
         Object var13 = transform.invoke(item$iv$iv);
         destination$iv$iv.add(var13);
      }

      return (List)destination$iv$iv;
   }

   public static final void forEachNested(@NotNull Iterable list1, @NotNull Iterable list2, @NotNull Function2 func) {
      Intrinsics.checkParameterIsNotNull(list1, "list1");
      Intrinsics.checkParameterIsNotNull(list2, "list2");
      Intrinsics.checkParameterIsNotNull(func, "func");
      Iterator var5 = list1.iterator();

      while(var5.hasNext()) {
         Object element$iv = var5.next();
         Object e1 = element$iv;
         Iterator var9 = list2.iterator();

         while(var9.hasNext()) {
            Object element$iv = var9.next();
            func.invoke(e1, element$iv);
         }
      }

   }

   @NotNull
   public static final Map filterValuesNotNull(@NotNull Map $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      LinkedHashMap result$iv = new LinkedHashMap();
      Iterator var5 = $receiver.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry$iv = (Entry)var5.next();
         Object it = entry$iv.getValue();
         if (it != null) {
            result$iv.put(entry$iv.getKey(), entry$iv.getValue());
         }
      }

      return (Map)result$iv;
   }

   private static final Object findFirst(@NotNull Iterable $receiver, Function1 func) {
      Iterator var4 = $receiver.iterator();

      Object var10000;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         Object element$iv = var4.next();
         var10000 = func.invoke(element$iv);
      } while(var10000 == null);

      Object var7 = var10000;
      return var7;
   }

   private static final void forEachPairIndexed(@NotNull Iterable $receiver, Function3 func) {
      Object previous = null;
      int index$iv = 0;

      Object item$iv;
      for(Iterator var6 = $receiver.iterator(); var6.hasNext(); previous = item$iv) {
         item$iv = var6.next();
         int idx = index$iv++;
         if (previous != null) {
            func.invoke(idx, item$iv, previous);
         }
      }

   }

   public static final Object tryDefault(Object var0, @NotNull Function0 work) {
      Intrinsics.checkParameterIsNotNull(work, "work");

      Object var2;
      try {
         var2 = work.invoke();
      } catch (Throwable var4) {
         var2 = var0;
      }

      return var2;
   }

   public static final double random(double min, double max) {
      double var4 = Math.random();
      return min + (max - min) * var4;
   }

   public static final int semiRandom(int x, int y, int z, int seed) {
      int value = x * x + y * y + z * z + x * y + y * z + z * x + seed * seed & 63;
      value = 3 * x * value + 5 * y * value + 7 * z * value + 11 * seed & 63;
      return value;
   }

   public static final double minmax(double $receiver, double minVal, double maxVal) {
      return Math.min(Math.max($receiver, minVal), maxVal);
   }

   public static final int minmax(int $receiver, int minVal, int maxVal) {
      return Math.min(Math.max($receiver, minVal), maxVal);
   }

   public static final int nextPowerOf2(int x) {
      return 1 << (x == 0 ? 0 : 32 - Integer.numberOfLeadingZeros(x - 1));
   }
}
