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
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001e"},
   d2 = {"Lmods/octarinecore/client/render/CornerSingleFallback;", "Lmods/octarinecore/client/render/Shader;", "face", "Lnet/minecraft/util/EnumFacing;", "dir1", "dir2", "fallbackDir", "fallbackDimming", "", "(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;F)V", "getDir1", "()Lnet/minecraft/util/EnumFacing;", "getDir2", "getFace", "getFallbackDimming", "()F", "getFallbackDir", "offset", "Lmods/octarinecore/common/Int3;", "getOffset", "()Lmods/octarinecore/common/Int3;", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class CornerSingleFallback implements Shader {
   @NotNull
   private final Int3 offset;
   @NotNull
   private final EnumFacing face;
   @NotNull
   private final EnumFacing dir1;
   @NotNull
   private final EnumFacing dir2;
   @NotNull
   private final EnumFacing fallbackDir;
   private final float fallbackDimming;

   @NotNull
   public final Int3 getOffset() {
      return this.offset;
   }

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      AoData shading = context.aoShading(this.face, this.dir1, this.dir2);
      if (shading.getValid()) {
         this.shade(vertex, shading);
      } else {
         BlockData var4 = context.blockData(this.offset);
         this.shade(vertex, PixelFormat.brMul(var4.getBrightness(), this.fallbackDimming), PixelFormat.colorMul(var4.getColor(), this.fallbackDimming));
      }

   }

   @NotNull
   public CornerSingleFallback rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return new CornerSingleFallback(GeometryKt.rotate(this.face, rot), GeometryKt.rotate(this.dir1, rot), GeometryKt.rotate(this.dir2, rot), GeometryKt.rotate(this.fallbackDir, rot), this.fallbackDimming);
   }

   @NotNull
   public final EnumFacing getFace() {
      return this.face;
   }

   @NotNull
   public final EnumFacing getDir1() {
      return this.dir1;
   }

   @NotNull
   public final EnumFacing getDir2() {
      return this.dir2;
   }

   @NotNull
   public final EnumFacing getFallbackDir() {
      return this.fallbackDir;
   }

   public final float getFallbackDimming() {
      return this.fallbackDimming;
   }

   public CornerSingleFallback(@NotNull EnumFacing face, @NotNull EnumFacing dir1, @NotNull EnumFacing dir2, @NotNull EnumFacing fallbackDir, float fallbackDimming) {
      Intrinsics.checkParameterIsNotNull(face, "face");
      Intrinsics.checkParameterIsNotNull(dir1, "dir1");
      Intrinsics.checkParameterIsNotNull(dir2, "dir2");
      Intrinsics.checkParameterIsNotNull(fallbackDir, "fallbackDir");
      super();
      this.face = face;
      this.dir1 = dir1;
      this.dir2 = dir2;
      this.fallbackDir = fallbackDir;
      this.fallbackDimming = fallbackDimming;
      this.offset = new Int3(this.fallbackDir);
   }

   // $FF: synthetic method
   public CornerSingleFallback(EnumFacing var1, EnumFacing var2, EnumFacing var3, EnumFacing var4, float var5, int var6, DefaultConstructorMarker var7) {
      if ((var6 & 16) != 0) {
         var5 = 0.5F;
      }

      this(var1, var2, var3, var4, var5);
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
