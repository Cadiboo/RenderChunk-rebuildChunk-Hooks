package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"},
   d2 = {"Lmods/octarinecore/client/render/FaceFlat;", "Lmods/octarinecore/client/render/Shader;", "face", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/EnumFacing;)V", "getFace", "()Lnet/minecraft/util/EnumFacing;", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class FaceFlat implements Shader {
   @NotNull
   private final EnumFacing face;

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      int color = context.blockData(Int3.Companion.getZero()).getColor();
      this.shade(vertex, context.blockData(GeometryKt.getOffset(this.face)).getBrightness(), color);
   }

   @NotNull
   public Shader rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return (Shader)(new FaceFlat(GeometryKt.rotate(this.face, rot)));
   }

   @NotNull
   public final EnumFacing getFace() {
      return this.face;
   }

   public FaceFlat(@NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(face, "face");
      super();
      this.face = face;
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
