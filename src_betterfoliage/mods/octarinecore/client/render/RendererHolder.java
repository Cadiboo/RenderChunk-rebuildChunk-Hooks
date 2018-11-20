package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import mods.octarinecore.ThreadLocalDelegate;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\"\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"},
   d2 = {"blockColors", "Ljava/lang/ThreadLocal;", "Lnet/minecraft/client/renderer/color/BlockColors;", "getBlockColors", "()Ljava/lang/ThreadLocal;", "blockContext", "Lmods/octarinecore/client/render/BlockContext;", "getBlockContext", "()Lmods/octarinecore/client/render/BlockContext;", "blockContext$delegate", "Lmods/octarinecore/ThreadLocalDelegate;", "modelRenderer", "Lmods/octarinecore/client/render/ModelRenderer;", "getModelRenderer", "()Lmods/octarinecore/client/render/ModelRenderer;", "modelRenderer$delegate", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "RendererHolder"
)
public final class RendererHolder {
   // $FF: synthetic field
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(RendererHolder.class, "BetterFoliage-MC1.12"), "blockContext", "getBlockContext()Lmods/octarinecore/client/render/BlockContext;")), (KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(RendererHolder.class, "BetterFoliage-MC1.12"), "modelRenderer", "getModelRenderer()Lmods/octarinecore/client/render/ModelRenderer;"))};
   @NotNull
   private static final ThreadLocalDelegate blockContext$delegate;
   @NotNull
   private static final ThreadLocalDelegate modelRenderer$delegate;
   @NotNull
   private static final ThreadLocal blockColors;

   static {
      blockContext$delegate = new ThreadLocalDelegate((Function0)null.INSTANCE);
      modelRenderer$delegate = new ThreadLocalDelegate((Function0)null.INSTANCE);
      blockColors = new ThreadLocal();
   }

   @NotNull
   public static final BlockContext getBlockContext() {
      return (BlockContext)blockContext$delegate.getValue((Object)null, $$delegatedProperties[0]);
   }

   @NotNull
   public static final ModelRenderer getModelRenderer() {
      return (ModelRenderer)modelRenderer$delegate.getValue((Object)null, $$delegatedProperties[1]);
   }

   @NotNull
   public static final ThreadLocal getBlockColors() {
      return blockColors;
   }
}
