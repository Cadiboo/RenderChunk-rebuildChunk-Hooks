package mods.octarinecore.metaprog;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00102\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"},
   d2 = {"Lmods/octarinecore/metaprog/ClassRef;", "Lmods/octarinecore/metaprog/Resolvable;", "Ljava/lang/Class;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "asmDescriptor", "namespace", "Lmods/octarinecore/metaprog/Namespace;", "isInstance", "", "obj", "", "resolve", "Companion", "BetterFoliage-MC1.12"}
)
public class ClassRef extends Resolvable {
   @NotNull
   private final String name;
   @NotNull
   private static final ClassRefPrimitive int;
   @NotNull
   private static final ClassRefPrimitive long;
   @NotNull
   private static final ClassRefPrimitive float;
   @NotNull
   private static final ClassRefPrimitive boolean;
   @NotNull
   private static final ClassRefPrimitive void;
   public static final ClassRef.Companion Companion = new ClassRef.Companion((DefaultConstructorMarker)null);

   @NotNull
   public String asmDescriptor(@NotNull Namespace namespace) {
      Intrinsics.checkParameterIsNotNull(namespace, "namespace");
      return 'L' + StringsKt.replace$default(this.name, ".", "/", false, 4, (Object)null) + ';';
   }

   @Nullable
   public Class resolve() {
      return Reflection.getJavaClass(this.name);
   }

   public final boolean isInstance(@NotNull Object obj) {
      Intrinsics.checkParameterIsNotNull(obj, "obj");
      Class var10000 = (Class)this.getElement();
      return var10000 != null ? var10000.isInstance(obj) : false;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public ClassRef(@NotNull String name) {
      Intrinsics.checkParameterIsNotNull(name, "name");
      super();
      this.name = name;
   }

   static {
      int = new ClassRefPrimitive("I", Integer.TYPE);
      long = new ClassRefPrimitive("J", Long.TYPE);
      float = new ClassRefPrimitive("F", Float.TYPE);
      boolean = new ClassRefPrimitive("Z", Boolean.TYPE);
      void = new ClassRefPrimitive("V", (Class)null);
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006¨\u0006\u000f"},
      d2 = {"Lmods/octarinecore/metaprog/ClassRef$Companion;", "", "()V", "boolean", "Lmods/octarinecore/metaprog/ClassRefPrimitive;", "getBoolean", "()Lmods/octarinecore/metaprog/ClassRefPrimitive;", "float", "getFloat", "int", "getInt", "long", "getLong", "void", "getVoid", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      @NotNull
      public final ClassRefPrimitive getInt() {
         return ClassRef.int;
      }

      @NotNull
      public final ClassRefPrimitive getLong() {
         return ClassRef.long;
      }

      @NotNull
      public final ClassRefPrimitive getFloat() {
         return ClassRef.float;
      }

      @NotNull
      public final ClassRefPrimitive getBoolean() {
         return ClassRef.boolean;
      }

      @NotNull
      public final ClassRefPrimitive getVoid() {
         return ClassRef.void;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
