package mods.octarinecore.client.render;

import java.lang.reflect.Constructor;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.common.FaceCorners;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.metaprog.Reflection;
import mods.octarinecore.metaprog.Resolvable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelRenderer.AmbientOcclusionFace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u0003H\u0086\u0002J\"\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\b\u0002\u0010%\u001a\u00020&2\b\b\u0002\u0010'\u001a\u00020(R\u0015\u0010\u0005\u001a\u00060\u0006R\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u001a\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\rR\u0011\u0010\u001c\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\r¨\u0006)"},
   d2 = {"Lmods/octarinecore/client/render/AoFaceData;", "", "face", "Lnet/minecraft/util/EnumFacing;", "(Lnet/minecraft/util/EnumFacing;)V", "ao", "Lnet/minecraft/client/renderer/BlockModelRenderer$AmbientOcclusionFace;", "Lnet/minecraft/client/renderer/BlockModelRenderer;", "getAo", "()Lnet/minecraft/client/renderer/BlockModelRenderer$AmbientOcclusionFace;", "bottomLeft", "Lmods/octarinecore/client/render/AoData;", "getBottomLeft", "()Lmods/octarinecore/client/render/AoData;", "bottomRight", "getBottomRight", "getFace", "()Lnet/minecraft/util/EnumFacing;", "left", "getLeft", "ordered", "", "getOrdered", "()Ljava/util/List;", "top", "getTop", "topLeft", "getTopLeft", "topRight", "getTopRight", "get", "dir1", "dir2", "update", "", "offset", "Lmods/octarinecore/common/Int3;", "useBounds", "", "multiplier", "", "BetterFoliage-MC1.12"}
)
public final class AoFaceData {
   @NotNull
   private final AmbientOcclusionFace ao;
   @NotNull
   private final EnumFacing top;
   @NotNull
   private final EnumFacing left;
   @NotNull
   private final AoData topLeft;
   @NotNull
   private final AoData topRight;
   @NotNull
   private final AoData bottomLeft;
   @NotNull
   private final AoData bottomRight;
   @NotNull
   private final List ordered;
   @NotNull
   private final EnumFacing face;

   @NotNull
   public final AmbientOcclusionFace getAo() {
      return this.ao;
   }

   @NotNull
   public final EnumFacing getTop() {
      return this.top;
   }

   @NotNull
   public final EnumFacing getLeft() {
      return this.left;
   }

   @NotNull
   public final AoData getTopLeft() {
      return this.topLeft;
   }

   @NotNull
   public final AoData getTopRight() {
      return this.topRight;
   }

   @NotNull
   public final AoData getBottomLeft() {
      return this.bottomLeft;
   }

   @NotNull
   public final AoData getBottomRight() {
      return this.bottomRight;
   }

   @NotNull
   public final List getOrdered() {
      return this.ordered;
   }

   public final void update(@NotNull Int3 offset, boolean useBounds, float multiplier) {
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      BlockContext ctx = RendererHolder.getBlockContext();
      IBlockState blockState = ctx.blockState(offset);
      float[] quadBounds = new float[12];
      BitSet var8 = new BitSet(3);
      var8.set(0);
      AmbientOcclusionFace var10000 = this.ao;
      IBlockAccess var10001 = ctx.getWorld();
      BlockPos var10003 = ctx.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "ctx.pos");
      var10000.func_187491_a(var10001, blockState, GeometryKt.plus(var10003, offset), this.face, quadBounds, var8);
      Iterable $receiver$iv = (Iterable)this.ordered;
      int index$iv = 0;
      Iterator var10 = $receiver$iv.iterator();

      while(var10.hasNext()) {
         Object item$iv = var10.next();
         int var16 = index$iv++;
         AoData aoData = (AoData)item$iv;
         int idx = var16;
         aoData.set(this.ao.field_178207_c[idx], this.ao.field_178206_b[idx] * multiplier);
      }

   }

   @NotNull
   public final AoData get(@NotNull EnumFacing dir1, @NotNull EnumFacing dir2) {
      Intrinsics.checkParameterIsNotNull(dir1, "dir1");
      Intrinsics.checkParameterIsNotNull(dir2, "dir2");
      boolean isTop = Intrinsics.areEqual(this.top, dir1) || Intrinsics.areEqual(this.top, dir2);
      boolean isLeft = Intrinsics.areEqual(this.left, dir1) || Intrinsics.areEqual(this.left, dir2);
      return isTop ? (isLeft ? this.topLeft : this.topRight) : (isLeft ? this.bottomLeft : this.bottomRight);
   }

   @NotNull
   public final EnumFacing getFace() {
      return this.face;
   }

   public AoFaceData(@NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(face, "face");
      super();
      this.face = face;
      Object var10001 = Refs.INSTANCE.getAmbientOcclusionFace().getElement();
      if (var10001 == null) {
         Intrinsics.throwNpe();
      }

      Object var2 = var10001;
      Class it = (Class)var2;
      Object var10000;
      if (Reflection.allAvailable((Resolvable)Refs.INSTANCE.getOptifineClassTransformer())) {
         var10000 = it.getDeclaredConstructor().newInstance();
      } else {
         Class[] var8 = new Class[1];
         Object var10004 = Refs.INSTANCE.getBlockModelRenderer().getElement();
         if (var10004 == null) {
            Intrinsics.throwNpe();
         }

         var8[0] = (Class)var10004;
         Constructor var7 = it.getDeclaredConstructor(var8);
         Object[] var9 = new Object[1];
         Minecraft var10006 = Minecraft.func_71410_x();
         Intrinsics.checkExpressionValueIsNotNull(var10006, "Minecraft.getMinecraft()");
         var9[0] = new BlockModelRenderer(var10006.func_184125_al());
         var10000 = var7.newInstance(var9);
      }

      Object var6 = var10000;
      if (var6 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.renderer.BlockModelRenderer.AmbientOcclusionFace");
      } else {
         this.ao = (AmbientOcclusionFace)var6;
         this.top = (EnumFacing)((FaceCorners)GeometryKt.getFaceCorners().get(this.face.ordinal())).getTopLeft().getFirst();
         this.left = (EnumFacing)((FaceCorners)GeometryKt.getFaceCorners().get(this.face.ordinal())).getTopLeft().getSecond();
         this.topLeft = new AoData();
         this.topRight = new AoData();
         this.bottomLeft = new AoData();
         this.bottomRight = new AoData();
         List var10;
         switch(AoFaceData$WhenMappings.$EnumSwitchMapping$0[this.face.ordinal()]) {
         case 1:
            var10 = CollectionsKt.listOf(new AoData[]{this.topLeft, this.bottomLeft, this.bottomRight, this.topRight});
            break;
         case 2:
            var10 = CollectionsKt.listOf(new AoData[]{this.bottomRight, this.topRight, this.topLeft, this.bottomLeft});
            break;
         case 3:
            var10 = CollectionsKt.listOf(new AoData[]{this.bottomLeft, this.bottomRight, this.topRight, this.topLeft});
            break;
         case 4:
            var10 = CollectionsKt.listOf(new AoData[]{this.topLeft, this.bottomLeft, this.bottomRight, this.topRight});
            break;
         case 5:
            var10 = CollectionsKt.listOf(new AoData[]{this.bottomLeft, this.bottomRight, this.topRight, this.topLeft});
            break;
         case 6:
            var10 = CollectionsKt.listOf(new AoData[]{this.topRight, this.topLeft, this.bottomLeft, this.bottomRight});
            break;
         default:
            throw new NoWhenBranchMatchedException();
         }

         this.ordered = var10;
      }
   }
}
