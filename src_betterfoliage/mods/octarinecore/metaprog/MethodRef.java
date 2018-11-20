package mods.octarinecore.metaprog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\t\"\u00020\u0004¢\u0006\u0002\u0010\nB9\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\t\"\u00020\u0004¢\u0006\u0002\u0010\fJ\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018J-\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0016\u0010\u001c\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u001a0\t\"\u0004\u0018\u00010\u001a¢\u0006\u0002\u0010\u001dJ!\u0010\u001e\u001a\u0004\u0018\u00010\u001a2\u0012\u0010\u001c\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\t\"\u00020\u001a¢\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0018J\n\u0010!\u001a\u0004\u0018\u00010\u0002H\u0016R\u001b\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\t¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011¨\u0006\""},
   d2 = {"Lmods/octarinecore/metaprog/MethodRef;", "Lmods/octarinecore/metaprog/Resolvable;", "Ljava/lang/reflect/Method;", "parentClass", "Lmods/octarinecore/metaprog/ClassRef;", "mcpName", "", "returnType", "argTypes", "", "(Lmods/octarinecore/metaprog/ClassRef;Ljava/lang/String;Lmods/octarinecore/metaprog/ClassRef;[Lmods/octarinecore/metaprog/ClassRef;)V", "srgName", "(Lmods/octarinecore/metaprog/ClassRef;Ljava/lang/String;Ljava/lang/String;Lmods/octarinecore/metaprog/ClassRef;[Lmods/octarinecore/metaprog/ClassRef;)V", "getArgTypes", "()[Lmods/octarinecore/metaprog/ClassRef;", "[Lmods/octarinecore/metaprog/ClassRef;", "getMcpName", "()Ljava/lang/String;", "getParentClass", "()Lmods/octarinecore/metaprog/ClassRef;", "getReturnType", "getSrgName", "asmDescriptor", "namespace", "Lmods/octarinecore/metaprog/Namespace;", "invoke", "", "receiver", "args", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", "invokeStatic", "([Ljava/lang/Object;)Ljava/lang/Object;", "name", "resolve", "BetterFoliage-MC1.12"}
)
public final class MethodRef extends Resolvable {
   @NotNull
   private final ClassRef parentClass;
   @NotNull
   private final String mcpName;
   @NotNull
   private final String srgName;
   @NotNull
   private final ClassRef returnType;
   @NotNull
   private final ClassRef[] argTypes;

   @NotNull
   public final String name(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      String var10000;
      switch(MethodRef$WhenMappings.$EnumSwitchMapping$0[namespace.ordinal()]) {
      case 1:
         var10000 = this.srgName;
         break;
      case 2:
         var10000 = this.mcpName;
         break;
      default:
         throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   @NotNull
   public final String asmDescriptor(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      StringBuilder var10000 = (new StringBuilder()).append('(');
      Object[] $receiver$iv = (Object[])this.argTypes;
      StringBuilder var12 = var10000;
      Object[] $receiver$iv$iv = $receiver$iv;
      Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
      int var5 = $receiver$iv.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object item$iv$iv = $receiver$iv$iv[var6];
         ClassRef it = (ClassRef)item$iv$iv;
         String var14 = it.asmDescriptor(namespace);
         destination$iv$iv.add(var14);
      }

      List var13 = (List)destination$iv$iv;
      Iterable $receiver$iv = (Iterable)var13;
      Object initial$iv = "";
      Object accumulator$iv = initial$iv;

      String s2;
      for(Iterator var18 = $receiver$iv.iterator(); var18.hasNext(); accumulator$iv = accumulator$iv + s2) {
         Object element$iv = var18.next();
         s2 = (String)element$iv;
      }

      return var12.append(accumulator$iv).append(')').append(this.returnType.asmDescriptor(namespace)).toString();
   }

   @Nullable
   public Method resolve() {
      Method var26;
      if (this.parentClass.getElement() != null) {
         Object[] $receiver$iv = (Object[])this.argTypes;
         int var2 = $receiver$iv.length;

         boolean var10000;
         label58: {
            for(int var3 = 0; var3 < var2; ++var3) {
               Object element$iv = $receiver$iv[var3];
               ClassRef it = (ClassRef)element$iv;
               if (it.getElement() == null) {
                  var10000 = true;
                  break label58;
               }
            }

            var10000 = false;
         }

         if (!var10000) {
            Object[] $receiver$iv = (Object[])this.argTypes;
            Object[] $receiver$iv$iv = $receiver$iv;
            Collection destination$iv$iv = (Collection)(new ArrayList($receiver$iv.length));
            int var21 = $receiver$iv.length;

            for(int var6 = 0; var6 < var21; ++var6) {
               Object item$iv$iv = $receiver$iv$iv[var6];
               ClassRef it = (ClassRef)item$iv$iv;
               Object var27 = it.getElement();
               if (var27 == null) {
                  Intrinsics.throwNpe();
               }

               Class var13 = (Class)var27;
               destination$iv$iv.add(var13);
            }

            Collection $receiver$iv = (Collection)((List)destination$iv$iv);
            Object[] var28 = $receiver$iv.toArray(new Class[0]);
            if (var28 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }

            Class[] args = (Class[])var28;
            Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{this.srgName, this.mcpName});
            destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
            Iterator var22 = $receiver$iv.iterator();

            while(var22.hasNext()) {
               Object item$iv$iv = var22.next();
               String it = (String)item$iv$iv;
               Method var25 = (Method)Utils.tryDefault((Object)null, (Function0)(new MethodRef$resolve$$inlined$map$lambda$1(it, this, args)));
               destination$iv$iv.add(var25);
            }

            var26 = (Method)CollectionsKt.firstOrNull(CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv)));
            if (var26 != null) {
               Method var18 = var26;
               var18.setAccessible(true);
               var26 = var18;
            } else {
               var26 = null;
            }

            return var26;
         }
      }

      var26 = null;
      return var26;
   }

   @Nullable
   public final Object invoke(@NotNull Object receiver, @NotNull Object... args) {
      Intrinsics.checkParameterIsNotNull(receiver, "receiver");
      Intrinsics.checkParameterIsNotNull(args, "args");
      Method var10000 = (Method)this.getElement();
      return var10000 != null ? var10000.invoke(receiver, Arrays.copyOf(args, args.length)) : null;
   }

   @Nullable
   public final Object invokeStatic(@NotNull Object... args) {
      Intrinsics.checkParameterIsNotNull(args, "args");
      Method var10000 = (Method)this.getElement();
      return var10000 != null ? var10000.invoke((Object)null, Arrays.copyOf(args, args.length)) : null;
   }

   @NotNull
   public final ClassRef getParentClass() {
      return this.parentClass;
   }

   @NotNull
   public final String getMcpName() {
      return this.mcpName;
   }

   @NotNull
   public final String getSrgName() {
      return this.srgName;
   }

   @NotNull
   public final ClassRef getReturnType() {
      return this.returnType;
   }

   @NotNull
   public final ClassRef[] getArgTypes() {
      return this.argTypes;
   }

   public MethodRef(@NotNull ClassRef parentClass, @NotNull String mcpName, @NotNull String srgName, @NotNull ClassRef returnType, @NotNull ClassRef... argTypes) {
      Intrinsics.checkParameterIsNotNull(parentClass, "parentClass");
      Intrinsics.checkParameterIsNotNull(mcpName, "mcpName");
      Intrinsics.checkParameterIsNotNull(srgName, "srgName");
      Intrinsics.checkParameterIsNotNull(returnType, "returnType");
      Intrinsics.checkParameterIsNotNull(argTypes, "argTypes");
      super();
      this.parentClass = parentClass;
      this.mcpName = mcpName;
      this.srgName = srgName;
      this.returnType = returnType;
      this.argTypes = argTypes;
   }

   public MethodRef(@NotNull ClassRef parentClass, @NotNull String mcpName, @NotNull ClassRef returnType, @NotNull ClassRef... argTypes) {
      Intrinsics.checkParameterIsNotNull(parentClass, "parentClass");
      Intrinsics.checkParameterIsNotNull(mcpName, "mcpName");
      Intrinsics.checkParameterIsNotNull(returnType, "returnType");
      Intrinsics.checkParameterIsNotNull(argTypes, "argTypes");
      this(parentClass, mcpName, mcpName, returnType, (ClassRef[])Arrays.copyOf(argTypes, argTypes.length));
   }
}
