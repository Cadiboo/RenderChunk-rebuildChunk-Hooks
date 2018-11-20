package mods.octarinecore.metaprog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000<\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a'\u0010\u0007\u001a\u00020\b2\u001a\u0010\t\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u000b0\n\"\u0006\u0012\u0002\b\u00030\u000b¢\u0006\u0002\u0010\f\u001a\u0014\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0003\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0012\u001a$\u0010\u0013\u001a\u0004\u0018\u0001H\u0014\"\u0006\b\u0000\u0010\u0014\u0018\u0001*\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u0016\u001aM\u0010\u0017\u001a$\u0012 \u0012\u001e\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00030\u0003\u0012\f\u0012\n \u0018*\u0004\u0018\u00010\u00040\u00040\u00020\u0001*\u00020\u00042\u001a\u0010\u0019\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u000e0\n\"\u0006\u0012\u0002\b\u00030\u000e¢\u0006\u0002\u0010\u001a\u001a(\u0010\u001b\u001a\u0004\u0018\u0001H\u0014\"\u0006\b\u0000\u0010\u0014\u0018\u0001*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0015\u001a\u00020\u0003H\u0086\b¢\u0006\u0002\u0010\u001c\"'\u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001d"},
   d2 = {"reflectNestedObjects", "", "Lkotlin/Pair;", "", "", "getReflectNestedObjects", "(Ljava/lang/Object;)Ljava/util/List;", "allAvailable", "", "codeElement", "", "Lmods/octarinecore/metaprog/Resolvable;", "([Lmods/octarinecore/metaprog/Resolvable;)Z", "getJavaClass", "Ljava/lang/Class;", "name", "array", "Lmods/octarinecore/metaprog/ClassRefArray;", "Lmods/octarinecore/metaprog/ClassRef;", "reflectField", "T", "field", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", "reflectFieldsOfType", "kotlin.jvm.PlatformType", "types", "(Ljava/lang/Object;[Ljava/lang/Class;)Ljava/util/List;", "reflectStaticField", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Reflection"
)
public final class Reflection {
   @Nullable
   public static final Class getJavaClass(@NotNull final String name) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      return (Class)Utils.tryDefault((Object)null, (Function0)(new Function0() {
         public final Class invoke() {
            return Class.forName(name);
         }
      }));
   }

   private static final Object reflectField(@NotNull final Object $receiver, final String field) {
      Field var10000 = (Field)Utils.tryDefault((Object)null, (Function0)(new Function0() {
         public final Field invoke() {
            return $receiver.getClass().getDeclaredField(field);
         }
      }));
      Object var6;
      if (var10000 != null) {
         Field var3 = var10000;
         var3.setAccessible(true);
         var6 = var3.get($receiver);
         Intrinsics.reifiedOperationMarker(1, "T");
         var6 = (Object)var6;
      } else {
         var6 = null;
      }

      return var6;
   }

   private static final Object reflectStaticField(@NotNull final Class $receiver, final String field) {
      Field var10000 = (Field)Utils.tryDefault((Object)null, (Function0)(new Function0() {
         public final Field invoke() {
            return $receiver.getDeclaredField(field);
         }
      }));
      Object var6;
      if (var10000 != null) {
         Field var3 = var10000;
         var3.setAccessible(true);
         var6 = var3.get((Object)null);
         Intrinsics.reifiedOperationMarker(1, "T");
         var6 = (Object)var6;
      } else {
         var6 = null;
      }

      return var6;
   }

   @NotNull
   public static final List getReflectNestedObjects(@NotNull Object $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Class[] var10000 = $receiver.getClass().getDeclaredClasses();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.javaClass.declaredClasses");
      Object[] $receiver$iv = (Object[])var10000;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      int var4 = $receiver$iv.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object item$iv$iv = $receiver$iv$iv[var5];
         Class it = (Class)item$iv$iv;
         Pair var12 = (Pair)Utils.tryDefault((Object)null, (Function0)(new Reflection$reflectNestedObjects$1$1(it)));
         destination$iv$iv.add(var12);
      }

      return CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv));
   }

   @NotNull
   public static final List reflectFieldsOfType(@NotNull Object $receiver, @NotNull Class... types) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(types, "types");
      Field[] var10000 = $receiver.getClass().getDeclaredFields();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.javaClass.declaredFields");
      Object[] $receiver$iv = (Object[])var10000;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int var5 = $receiver$iv.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object element$iv$iv = $receiver$iv$iv[var6];
         Field field = (Field)element$iv$iv;
         Object[] $receiver$iv = (Object[])types;
         int var10 = $receiver$iv.length;
         int var11 = 0;

         boolean var27;
         while(true) {
            if (var11 >= var10) {
               var27 = false;
               break;
            }

            Object element$iv = $receiver$iv[var11];
            Class it = (Class)element$iv;
            Intrinsics.checkExpressionValueIsNotNull(field, "field");
            if (it.isAssignableFrom(field.getType())) {
               var27 = true;
               break;
            }

            ++var11;
         }

         if (var27) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      Iterable $receiver$iv = (Iterable)((List)destination$iv$iv);
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var22 = $receiver$iv.iterator();

      while(var22.hasNext()) {
         Object item$iv$iv = var22.next();
         Field field = (Field)item$iv$iv;
         Intrinsics.checkExpressionValueIsNotNull(field, "field");
         String var25 = field.getName();
         field.setAccessible(true);
         Object var26 = field.get($receiver);
         Pair var20 = TuplesKt.to(var25, var26);
         destination$iv$iv.add(var20);
      }

      return CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv));
   }

   public static final boolean allAvailable(@NotNull Resolvable... codeElement) {
      Intrinsics.checkParameterIsNotNull(codeElement, "codeElement");
      Object[] $receiver$iv = (Object[])codeElement;
      int var2 = $receiver$iv.length;
      int var3 = 0;

      boolean var10000;
      while(true) {
         if (var3 >= var2) {
            var10000 = true;
            break;
         }

         Object element$iv = $receiver$iv[var3];
         Resolvable it = (Resolvable)element$iv;
         if (it.getElement() == null) {
            var10000 = false;
            break;
         }

         ++var3;
      }

      return var10000;
   }

   @NotNull
   public static final ClassRefArray array(@NotNull ClassRef $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return new ClassRefArray($receiver.getName());
   }
}
