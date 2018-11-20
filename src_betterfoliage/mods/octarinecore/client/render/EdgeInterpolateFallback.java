package mods.octarinecore.client.render;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0011\u0010\u001b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012¨\u0006&"},
   d2 = {"Lmods/octarinecore/client/render/EdgeInterpolateFallback;", "Lmods/octarinecore/client/render/Shader;", "face", "Lnet/minecraft/util/EnumFacing;", "edgeDir", "pos", "", "fallbackDimming", "", "(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;DF)V", "edgeAxis", "Lnet/minecraft/util/EnumFacing$Axis;", "getEdgeAxis", "()Lnet/minecraft/util/EnumFacing$Axis;", "getEdgeDir", "()Lnet/minecraft/util/EnumFacing;", "getFace", "getFallbackDimming", "()F", "offset", "Lmods/octarinecore/common/Int3;", "getOffset", "()Lmods/octarinecore/common/Int3;", "getPos", "()D", "weightN", "getWeightN", "weightP", "getWeightP", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class EdgeInterpolateFallback implements Shader {
   @NotNull
   private final Int3 offset;
   @NotNull
   private final Axis edgeAxis;
   private final float weightN;
   private final float weightP;
   @NotNull
   private final EnumFacing face;
   @NotNull
   private final EnumFacing edgeDir;
   private final double pos;
   private final float fallbackDimming;

   @NotNull
   public final Int3 getOffset() {
      return this.offset;
   }

   @NotNull
   public final Axis getEdgeAxis() {
      return this.edgeAxis;
   }

   public final float getWeightN() {
      return this.weightN;
   }

   public final float getWeightP() {
      return this.weightP;
   }

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      AoData shadingP = context.aoShading(this.face, this.edgeDir, GeometryKt.getFace(TuplesKt.to(this.edgeAxis, AxisDirection.POSITIVE)));
      AoData shadingN = context.aoShading(this.face, this.edgeDir, GeometryKt.getFace(TuplesKt.to(this.edgeAxis, AxisDirection.NEGATIVE)));
      if (!shadingP.getValid() && !shadingN.getValid()) {
         BlockData var5 = context.blockData(this.offset);
         this.shade(vertex, PixelFormat.brMul(var5.getBrightness(), this.fallbackDimming), PixelFormat.colorMul(var5.getColor(), this.fallbackDimming));
      } else if (!shadingP.getValid()) {
         this.shade(vertex, shadingN);
      } else if (!shadingN.getValid()) {
         this.shade(vertex, shadingP);
      } else {
         this.shade(vertex, shadingP, shadingN, this.weightP, this.weightN);
      }
   }

   @NotNull
   public EdgeInterpolateFallback rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return new EdgeInterpolateFallback(GeometryKt.rotate(this.face, rot), GeometryKt.rotate(this.edgeDir, rot), this.pos, 0.0F, 8, (DefaultConstructorMarker)null);
   }

   @NotNull
   public final EnumFacing getFace() {
      return this.face;
   }

   @NotNull
   public final EnumFacing getEdgeDir() {
      return this.edgeDir;
   }

   public final double getPos() {
      return this.pos;
   }

   public final float getFallbackDimming() {
      return this.fallbackDimming;
   }

   public EdgeInterpolateFallback(@NotNull EnumFacing face, @NotNull EnumFacing edgeDir, double pos, float fallbackDimming) {
      Intrinsics.checkParameterIsNotNull(face, "face");
      Intrinsics.checkParameterIsNotNull(edgeDir, "edgeDir");
      super();
      this.face = face;
      this.edgeDir = edgeDir;
      this.pos = pos;
      this.fallbackDimming = fallbackDimming;
      this.offset = new Int3(this.edgeDir);
      Iterable var6 = (Iterable)GeometryKt.getAxes();
      Iterator var8 = var6.iterator();

      Object var10000;
      while(true) {
         if (!var8.hasNext()) {
            var10000 = null;
            break;
         }

         Object var9 = var8.next();
         Axis it = (Axis)var9;
         if (Intrinsics.areEqual(it, this.face.func_176740_k()) ^ true && Intrinsics.areEqual(it, this.edgeDir.func_176740_k()) ^ true) {
            var10000 = var9;
            break;
         }
      }

      Object var13 = var10000;
      if (var13 == null) {
         Intrinsics.throwNpe();
      }

      this.edgeAxis = (Axis)var13;
      this.weightN = (float)(0.5D - this.pos);
      this.weightP = (float)(0.5D + this.pos);
   }

   // $FF: synthetic method
   public EdgeInterpolateFallback(EnumFacing var1, EnumFacing var2, double var3, float var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 8) != 0) {
         var5 = 0.8F;
      }

      this(var1, var2, var3, var5);
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
