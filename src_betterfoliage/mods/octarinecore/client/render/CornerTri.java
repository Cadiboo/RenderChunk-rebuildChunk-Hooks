package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Rotation;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR#\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019"},
   d2 = {"Lmods/octarinecore/client/render/CornerTri;", "Lmods/octarinecore/client/render/Shader;", "face", "Lnet/minecraft/util/EnumFacing;", "dir1", "dir2", "func", "Lkotlin/Function2;", "Lmods/octarinecore/client/render/AoData;", "(Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/EnumFacing;Lkotlin/jvm/functions/Function2;)V", "getDir1", "()Lnet/minecraft/util/EnumFacing;", "getDir2", "getFace", "getFunc", "()Lkotlin/jvm/functions/Function2;", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "shade", "", "context", "Lmods/octarinecore/client/render/ShadingContext;", "vertex", "Lmods/octarinecore/client/render/RenderVertex;", "BetterFoliage-MC1.12"}
)
public final class CornerTri implements Shader {
   @NotNull
   private final EnumFacing face;
   @NotNull
   private final EnumFacing dir1;
   @NotNull
   private final EnumFacing dir2;
   @NotNull
   private final Function2 func;

   public void shade(@NotNull ShadingContext context, @NotNull RenderVertex vertex) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(vertex, "vertex");
      AoData v2$iv = context.aoShading(this.face, this.dir1, this.dir2);
      AoData v2$iv = context.aoShading(this.dir1, this.face, this.dir2);
      Function2 func$iv = this.func;
      boolean v2ok$iv = v2$iv != null && v2$iv.getValid();
      boolean v2ok$iv = v2$iv != null && v2$iv.getValid();
      AoData var10000;
      if (v2ok$iv && v2ok$iv) {
         if (v2$iv == null) {
            Intrinsics.throwNpe();
         }

         if (v2$iv == null) {
            Intrinsics.throwNpe();
         }

         var10000 = (AoData)func$iv.invoke(v2$iv, v2$iv);
      } else {
         var10000 = v2ok$iv ? v2$iv : (v2ok$iv ? v2$iv : null);
      }

      AoData acc = var10000;
      v2$iv = context.aoShading(this.dir2, this.face, this.dir1);
      Function2 func$iv = this.func;
      boolean v1ok$iv = acc != null && acc.getValid();
      v2ok$iv = v2$iv != null && v2$iv.getValid();
      if (v1ok$iv && v2ok$iv) {
         if (acc == null) {
            Intrinsics.throwNpe();
         }

         if (v2$iv == null) {
            Intrinsics.throwNpe();
         }

         var10000 = (AoData)func$iv.invoke(acc, v2$iv);
      } else {
         var10000 = v1ok$iv ? acc : (v2ok$iv ? v2$iv : null);
      }

      acc = var10000;
      AoData var10002 = acc;
      if (acc == null) {
         var10002 = AoData.Companion.getBlack();
      }

      this.shade(vertex, var10002);
   }

   @NotNull
   public CornerTri rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      return new CornerTri(GeometryKt.rotate(this.face, rot), GeometryKt.rotate(this.dir1, rot), GeometryKt.rotate(this.dir2, rot), this.func);
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
   public final Function2 getFunc() {
      return this.func;
   }

   public CornerTri(@NotNull EnumFacing face, @NotNull EnumFacing dir1, @NotNull EnumFacing dir2, @NotNull Function2 func) {
      Intrinsics.checkParameterIsNotNull(face, "face");
      Intrinsics.checkParameterIsNotNull(dir1, "dir1");
      Intrinsics.checkParameterIsNotNull(dir2, "dir2");
      Intrinsics.checkParameterIsNotNull(func, "func");
      super();
      this.face = face;
      this.dir1 = dir1;
      this.dir2 = dir2;
      this.func = func;
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
