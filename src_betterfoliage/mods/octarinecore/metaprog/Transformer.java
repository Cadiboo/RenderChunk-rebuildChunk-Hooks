package mods.octarinecore.metaprog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u0015H\u0016J'\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\b\u000fR\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R=\u0010\b\u001a%\u0012!\u0012\u001f\u0012\u0004\u0012\u00020\u000b\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f¢\u0006\u0002\b\u000f0\n0\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001e"},
   d2 = {"Lmods/octarinecore/metaprog/Transformer;", "Lnet/minecraft/launchwrapper/IClassTransformer;", "()V", "log", "Lorg/apache/logging/log4j/Logger;", "kotlin.jvm.PlatformType", "getLog", "()Lorg/apache/logging/log4j/Logger;", "methodTransformers", "", "Lkotlin/Pair;", "Lmods/octarinecore/metaprog/MethodRef;", "Lkotlin/Function1;", "Lmods/octarinecore/metaprog/MethodTransformContext;", "", "Lkotlin/ExtensionFunctionType;", "getMethodTransformers", "()Ljava/util/List;", "setMethodTransformers", "(Ljava/util/List;)V", "transform", "", "name", "", "transformedName", "classData", "transformMethod", "", "method", "trans", "BetterFoliage-MC1.12"}
)
public class Transformer implements IClassTransformer {
   private final Logger log = LogManager.getLogger(this);
   @NotNull
   private List methodTransformers;

   public final Logger getLog() {
      return this.log;
   }

   @NotNull
   public final List getMethodTransformers() {
      return this.methodTransformers;
   }

   public final void setMethodTransformers(@NotNull List var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.methodTransformers = var1;
   }

   public final boolean transformMethod(@NotNull MethodRef method, @NotNull Function1 trans) {
      Intrinsics.checkParameterIsNotNull(method, "method");
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      return this.methodTransformers.add(TuplesKt.to(method, trans));
   }

   @Nullable
   public byte[] transform(@Nullable String name, @Nullable String transformedName, @Nullable byte[] classData) {
      if (classData == null) {
         return null;
      } else {
         ClassNode var5 = new ClassNode();
         ClassReader reader = new ClassReader(classData);
         reader.accept((ClassVisitor)var5, 0);
         ClassNode classNode = var5;
         boolean workDone = false;
         int writerFlags = 0;
         synchronized(this){}

         try {
            Iterable $receiver$iv = (Iterable)this.methodTransformers;
            Iterator var9 = $receiver$iv.iterator();

            while(true) {
               if (!var9.hasNext()) {
                  Unit var32 = Unit.INSTANCE;
                  break;
               }

               Object element$iv = var9.next();
               Pair $targetMethod_transform = (Pair)element$iv;
               MethodRef targetMethod = (MethodRef)$targetMethod_transform.component1();
               Function1 transform = (Function1)$targetMethod_transform.component2();
               if (!(Intrinsics.areEqual(transformedName, targetMethod.getParentClass().getName()) ^ true)) {
                  Iterator var14 = classNode.methods.iterator();

                  while(var14.hasNext()) {
                     MethodNode method = (MethodNode)var14.next();
                     Object[] var16 = (Object[])Namespace.values();
                     Object[] var17 = var16;
                     int var18 = var16.length;
                     int var19 = 0;

                     Object var10000;
                     while(true) {
                        if (var19 >= var18) {
                           var10000 = null;
                           break;
                        }

                        Object var20 = var17[var19];
                        Namespace it = (Namespace)var20;
                        if (Intrinsics.areEqual(method.name, targetMethod.name(it)) && Intrinsics.areEqual(method.desc, targetMethod.asmDescriptor(it))) {
                           var10000 = var20;
                           break;
                        }

                        ++var19;
                     }

                     Namespace var34 = (Namespace)var10000;
                     if ((Namespace)var10000 != null) {
                        Namespace namespace = var34;
                        switch(Transformer$WhenMappings.$EnumSwitchMapping$0[namespace.ordinal()]) {
                        case 1:
                           this.log.info("Found method " + targetMethod.getParentClass().getName() + '.' + targetMethod.name(Namespace.MCP) + ' ' + targetMethod.asmDescriptor(Namespace.MCP));
                           break;
                        case 2:
                           this.log.info("Found method " + targetMethod.getParentClass().getName() + '.' + targetMethod.name(namespace) + ' ' + targetMethod.asmDescriptor(namespace) + " (matching " + targetMethod.name(Namespace.MCP) + ')');
                        }

                        Intrinsics.checkExpressionValueIsNotNull(method, "method");
                        MethodTransformContext var33 = new MethodTransformContext(method, namespace, writerFlags);
                        transform.invoke(var33);
                        writerFlags = var33.getWriterFlags();
                        workDone = true;
                     }
                  }
               }
            }
         } finally {
            ;
         }

         byte[] var35;
         if (!workDone) {
            var35 = classData;
         } else {
            ClassWriter var31 = new ClassWriter(writerFlags);
            classNode.accept((ClassVisitor)var31);
            var35 = var31.toByteArray();
         }

         return var35;
      }
   }

   public Transformer() {
      ArrayList var2 = new ArrayList();
      this.methodTransformers = (List)var2;
   }
}
