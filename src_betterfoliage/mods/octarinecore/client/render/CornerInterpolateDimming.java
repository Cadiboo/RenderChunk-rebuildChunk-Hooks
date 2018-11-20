package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\u0010\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\f¨\u0006 "},
   d2 = {"Lmods/octarinecore/client/render/CornerInterpolateDimming;", "Lmods/octarinecore/client/render/Shader;", "face1", "Lnet/minecraft/util/EnumFacing;", "face2", "edgeDir", "weight", "", "dimming", "fallbackDimming", "(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;FFF)V", "getDimming", "()F", "getEdgeDir", "()Lnet/minecraft/util/EnumFacing;", "getFace1", "getFace2", "getFallbackDimming", "offset", "Lmods/octarinecore/common/Int3;", "getOffset", "()Lmods/octarinecore/common/Int3;", "getWeight", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class CornerInterpolateDimming implements Shader {
   @NotNull
   private final Int3 offset;
   @NotNull
   private final EnumFacing face1;
   @NotNull
   private final EnumFacing face2;
   @NotNull
   private final EnumFacing edgeDir;
   private final float weight;
   private final float dimming;
   private final float fallbackDimming;

   @NotNull
   public final Int3 getOffset() {
      return this.offset;
   }

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      AoData shading1 = context.aoShading(this.face1, this.edgeDir, this.face2);
      AoData shading2 = context.aoShading(this.face2, this.edgeDir, this.face1);
      float weight1 = this.weight;
      float weight2 = 1.0F - this.weight;
      if (!shading1.getValid() && !shading2.getValid()) {
         BlockData var7 = context.blockData(this.offset);
         this.shade(vertex, PixelFormat.brMul(var7.getBrightness(), this.fallbackDimming), PixelFormat.colorMul(var7.getColor(), this.fallbackDimming));
      } else {
         if (!shading1.getValid()) {
            shading1 = shading2;
            weight1 *= this.dimming;
         }

         if (!shading2.getValid()) {
            shading2 = shading1;
            weight2 *= this.dimming;
         }

         this.shade(vertex, shading1, shading2, weight1, weight2);
      }
   }

   @NotNull
   public CornerInterpolateDimming rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return new CornerInterpolateDimming(GeometryKt.rotate(this.face1, rot), GeometryKt.rotate(this.face2, rot), GeometryKt.rotate(this.edgeDir, rot), this.weight, this.dimming, this.fallbackDimming);
   }

   @NotNull
   public final EnumFacing getFace1() {
      return this.face1;
   }

   @NotNull
   public final EnumFacing getFace2() {
      return this.face2;
   }

   @NotNull
   public final EnumFacing getEdgeDir() {
      return this.edgeDir;
   }

   public final float getWeight() {
      return this.weight;
   }

   public final float getDimming() {
      return this.dimming;
   }

   public final float getFallbackDimming() {
      return this.fallbackDimming;
   }

   public CornerInterpolateDimming(@NotNull EnumFacing face1, @NotNull EnumFacing face2, @NotNull EnumFacing edgeDir, float weight, float dimming, float fallbackDimming) {
      Intrinsics.checkParameterIsNotNull(face1, "face1");
      Intrinsics.checkParameterIsNotNull(face2, "face2");
      Intrinsics.checkParameterIsNotNull(edgeDir, "edgeDir");
      super();
      this.face1 = face1;
      this.face2 = face2;
      this.edgeDir = edgeDir;
      this.weight = weight;
      this.dimming = dimming;
      this.fallbackDimming = fallbackDimming;
      this.offset = new Int3(this.edgeDir);
   }

   // $FF: synthetic method
   public CornerInterpolateDimming(EnumFacing var1, EnumFacing var2, EnumFacing var3, float var4, float var5, float var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 32) != 0) {
         var6 = 0.5F;
      }

      this(var1, var2, var3, var4, var5, var6);
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
