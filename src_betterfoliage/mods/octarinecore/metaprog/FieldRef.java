package mods.octarinecore.metaprog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
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
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\bB'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\nJ\u000e\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015J\b\u0010\u0017\u001a\u0004\u0018\u00010\u0015J\u000e\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e¨\u0006\u001d"},
   d2 = {"Lmods/octarinecore/metaprog/FieldRef;", "Lmods/octarinecore/metaprog/Resolvable;", "Ljava/lang/reflect/Field;", "parentClass", "Lmods/octarinecore/metaprog/ClassRef;", "mcpName", "", "type", "(Lmods/octarinecore/metaprog/ClassRef;Ljava/lang/String;Lmods/octarinecore/metaprog/ClassRef;)V", "srgName", "(Lmods/octarinecore/metaprog/ClassRef;Ljava/lang/String;Ljava/lang/String;Lmods/octarinecore/metaprog/ClassRef;)V", "getMcpName", "()Ljava/lang/String;", "getParentClass", "()Lmods/octarinecore/metaprog/ClassRef;", "getSrgName", "getType", "asmDescriptor", "namespace", "Lmods/octarinecore/metaprog/Namespace;", "get", "", "receiver", "getStatic", "name", "resolve", "set", "", "obj", "BetterFoliage-MC1.12"}
)
public final class FieldRef extends Resolvable {
   @NotNull
   private final ClassRef parentClass;
   @NotNull
   private final String mcpName;
   @NotNull
   private final String srgName;
   @Nullable
   private final ClassRef type;

   @NotNull
   public final String name(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      String var10000;
      switch(FieldRef$WhenMappings.$EnumSwitchMapping$0[namespace.ordinal()]) {
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
      ClassRef var10000 = this.type;
      if (this.type == null) {
         Intrinsics.throwNpe();
      }

      return var10000.asmDescriptor(namespace);
   }

   @Nullable
   public Field resolve() {
      Field var10000;
      if (this.parentClass.getElement() == null) {
         var10000 = null;
      } else {
         Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new String[]{this.srgName, this.mcpName});
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
         Iterator var4 = $receiver$iv.iterator();

         while(var4.hasNext()) {
            Object item$iv$iv = var4.next();
            String it = (String)item$iv$iv;
            Field var11 = (Field)Utils.tryDefault((Object)null, (Function0)(new FieldRef$resolve$$inlined$map$lambda$1(it, this)));
            destination$iv$iv.add(var11);
         }

         var10000 = (Field)CollectionsKt.firstOrNull(CollectionsKt.filterNotNull((Iterable)((List)destination$iv$iv)));
         if (var10000 != null) {
            Field var12 = var10000;
            var12.setAccessible(true);
            var10000 = var12;
         } else {
            var10000 = null;
         }
      }

      return var10000;
   }

   @Nullable
   public final Object get(@Nullable Object receiver) {
      Field var10000 = (Field)this.getElement();
      return var10000 != null ? var10000.get(receiver) : null;
   }

   @Nullable
   public final Object getStatic() {
      return this.get((Object)null);
   }

   public final void set(@Nullable Object receiver, @Nullable Object obj) {
      Field var10000 = (Field)this.getElement();
      if (var10000 != null) {
         var10000.set(receiver, obj);
      }

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

   @Nullable
   public final ClassRef getType() {
      return this.type;
   }

   public FieldRef(@NotNull ClassRef parentClass, @NotNull String mcpName, @NotNull String srgName, @Nullable ClassRef type) {
      Intrinsics.checkParameterIsNotNull(parentClass, "parentClass");
      Intrinsics.checkParameterIsNotNull(mcpName, "mcpName");
      Intrinsics.checkParameterIsNotNull(srgName, "srgName");
      super();
      this.parentClass = parentClass;
      this.mcpName = mcpName;
      this.srgName = srgName;
      this.type = type;
   }

   public FieldRef(@NotNull ClassRef parentClass, @NotNull String mcpName, @Nullable ClassRef type) {
      Intrinsics.checkParameterIsNotNull(parentClass, "parentClass");
      Intrinsics.checkParameterIsNotNull(mcpName, "mcpName");
      this(parentClass, mcpName, mcpName, type);
   }
}
