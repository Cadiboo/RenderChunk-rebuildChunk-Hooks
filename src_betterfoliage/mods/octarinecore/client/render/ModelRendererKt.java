package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0014\u001a\u00020\r*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n\"\u001d\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"D\u0010\u0006\u001a5\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u0007j\u0002`\u000e¢\u0006\u0002\b\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u001d\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0005*h\u0010\u0017\"1\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u0007¢\u0006\u0002\b\u000f21\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u0007¢\u0006\u0002\b\u000f*>\u0010\u0018\"\u001c\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00192\u001c\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0019¨\u0006\u001b"},
   d2 = {"allFaces", "Lkotlin/Function1;", "Lnet/minecraft/util/EnumFacing;", "", "getAllFaces", "()Lkotlin/jvm/functions/Function1;", "noPost", "Lkotlin/Function6;", "Lmods/octarinecore/client/render/RenderVertex;", "Lmods/octarinecore/client/render/ShadingContext;", "", "Lmods/octarinecore/client/render/Quad;", "Lmods/octarinecore/client/render/Vertex;", "", "Lmods/octarinecore/client/render/PostProcessLambda;", "Lkotlin/ExtensionFunctionType;", "getNoPost", "()Lkotlin/jvm/functions/Function6;", "topOnly", "getTopOnly", "ensureSpaceForQuads", "Lnet/minecraft/client/renderer/BufferBuilder;", "num", "PostProcessLambda", "QuadIconResolver", "Lkotlin/Function3;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "BetterFoliage-MC1.12"}
)
public final class ModelRendererKt {
   @NotNull
   private static final Function1 allFaces;
   @NotNull
   private static final Function1 topOnly;
   @NotNull
   private static final Function6 noPost;

   public static final void ensureSpaceForQuads(@NotNull BufferBuilder $receiver, int num) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      $receiver.field_178999_b.position($receiver.func_181664_j());
      VertexFormat var10002 = $receiver.func_178973_g();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "vertexFormat");
      $receiver.func_181670_b(num * var10002.func_177338_f());
   }

   @NotNull
   public static final Function1 getAllFaces() {
      return allFaces;
   }

   @NotNull
   public static final Function1 getTopOnly() {
      return topOnly;
   }

   @NotNull
   public static final Function6 getNoPost() {
      return noPost;
   }

   static {
      allFaces = (Function1)null.INSTANCE;
      topOnly = (Function1)null.INSTANCE;
      noPost = (Function6)null.INSTANCE;
   }
}
