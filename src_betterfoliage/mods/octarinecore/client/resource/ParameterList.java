package mods.octarinecore.client.resource;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B#\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0006J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0086\u0002J\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0086\u0002J\u001d\u0010\u000f\u001a\u00020\u00002\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0011H\u0086\u0002J\b\u0010\u0012\u001a\u00020\u0004H\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lmods/octarinecore/client/resource/ParameterList;", "", "params", "", "", "value", "(Ljava/util/Map;Ljava/lang/String;)V", "getParams", "()Ljava/util/Map;", "getValue", "()Ljava/lang/String;", "contains", "", "key", "get", "plus", "pair", "Lkotlin/Pair;", "toString", "Companion", "BetterFoliage-MC1.12"}
)
public final class ParameterList {
   @NotNull
   private final Map params;
   @Nullable
   private final String value;
   public static final ParameterList.Companion Companion = new ParameterList.Companion((DefaultConstructorMarker)null);

   @NotNull
   public String toString() {
      StringBuilder var10000 = new StringBuilder();
      Iterable $receiver$iv = (Iterable)this.params.entrySet();
      StringBuilder var10 = var10000;
      Comparator var3 = (Comparator)(new ParameterList$toString$$inlined$sortedBy$1());
      List var11 = CollectionsKt.sortedWith($receiver$iv, var3);
      $receiver$iv = (Iterable)var11;
      Object initial$iv = "";
      Object accumulator$iv = initial$iv;

      Entry entry;
      for(Iterator var4 = $receiver$iv.iterator(); var4.hasNext(); accumulator$iv = accumulator$iv + '|' + (String)entry.getKey() + '=' + (String)entry.getValue()) {
         Object element$iv = var4.next();
         entry = (Entry)element$iv;
      }

      var10000 = var10.append(accumulator$iv);
      String var10001 = this.value;
      if (this.value != null) {
         String var13 = var10001;
         var10 = var10000;
         String var12 = '|' + var13;
         var10000 = var10;
         var10001 = var12;
         if (var12 != null) {
            return var10000.append(var10001).toString();
         }
      }

      var10001 = "";
      return var10000.append(var10001).toString();
   }

   @Nullable
   public final String get(@NotNull String key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      return (String)this.params.get(key);
   }

   public final boolean contains(@NotNull String key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      Map var2 = this.params;
      return var2.containsKey(key);
   }

   @NotNull
   public final ParameterList plus(@NotNull Pair pair) {
      Intrinsics.checkParameterIsNotNull(pair, "pair");
      return new ParameterList(MapsKt.plus(this.params, pair), this.value);
   }

   @NotNull
   public final Map getParams() {
      return this.params;
   }

   @Nullable
   public final String getValue() {
      return this.value;
   }

   public ParameterList(@NotNull Map params, @Nullable String value) {
      Intrinsics.checkParameterIsNotNull(params, "params");
      super();
      this.params = params;
      this.value = value;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lmods/octarinecore/client/resource/ParameterList$Companion;", "", "()V", "fromString", "Lmods/octarinecore/client/resource/ParameterList;", "input", "", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final ParameterList fromString(@NotNull String input) {
         Intrinsics.checkParameterIsNotNull(input, "input");
         HashMap params = new HashMap();
         Object value = (String)null;
         String $receiver$iv = input;
         int index$iv = 0;
         int var7 = ((CharSequence)input).length();

         String var10000;
         while(true) {
            if (index$iv >= var7) {
               var10000 = "";
               break;
            }

            char it = $receiver$iv.charAt(index$iv);
            if (it == '|') {
               var10000 = $receiver$iv.substring(index$iv);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
               break;
            }

            ++index$iv;
         }

         List slices = StringsKt.split$default((CharSequence)var10000, new char[]{'|'}, false, 0, 6, (Object)null);
         Iterable $receiver$iv = (Iterable)slices;
         Iterator var13 = $receiver$iv.iterator();

         while(var13.hasNext()) {
            Object element$iv = var13.next();
            String it = (String)element$iv;
            if (StringsKt.contains$default((CharSequence)it, '=', false, 2, (Object)null)) {
               List keyValue = StringsKt.split$default((CharSequence)it, new char[]{'='}, false, 0, 6, (Object)null);
               if (keyValue.size() == 2) {
                  params.put(keyValue.get(0), keyValue.get(1));
               }
            } else {
               value = it;
            }
         }

         return new ParameterList((Map)params, value);
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
