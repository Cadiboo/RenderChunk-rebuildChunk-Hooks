package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Rotation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"},
   d2 = {"Lmods/octarinecore/client/render/NoShader;", "Lmods/octarinecore/client/render/Shader;", "()V", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class NoShader implements Shader {
   public static final NoShader INSTANCE;

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      this.shade(vertex, AoData.Companion.getBlack());
   }

   @NotNull
   public NoShader rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return this;
   }

   static {
      NoShader var0 = new NoShader();
      INSTANCE = var0;
   }

   public void shade(@NotNull RenderVertex $receiver, @NotNull AoData shading) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(shading, "shading");
      Shader.DefaultImpls.shade(this, $receiver, shading);
   }

   public void shade(@NotNull RenderVertex $receiver, @NotNull AoData shading1, @NotNull AoData shading2, float weight1, float weight2) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(shading1, "shading1");
      Intrinsics.checkParameterIsNotNull(shading2, "shading2");
      Shader.DefaultImpls.shade(this, $receiver, shading1, shading2, weight1, weight2);
   }

   public void shade(@NotNull RenderVertex $receiver, int brightness, int color) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Shader.DefaultImpls.shade(this, $receiver, brightness, color);
   }
}
