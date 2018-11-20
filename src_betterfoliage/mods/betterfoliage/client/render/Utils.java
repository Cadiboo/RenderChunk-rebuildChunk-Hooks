package mods.betterfoliage.client.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.Shader;
import mods.octarinecore.client.render.UV;
import mods.octarinecore.client.render.Vertex;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u000e\u001a\n\u0010-\u001a\u00020%*\u00020)\u001a\u0012\u0010.\u001a\u00020%*\u00020)2\u0006\u0010/\u001a\u00020&\u001a.\u00100\u001a\u00020\u0011*\u0002012\u0006\u00102\u001a\u0002012\u0006\u00103\u001a\u0002012\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020%05\u001a\u0018\u00106\u001a\b\u0012\u0004\u0012\u00020\u000f07*\u00020\u000f2\u0006\u00108\u001a\u000209\u001a,\u00106\u001a\b\u0012\u0004\u0012\u00020\u000f07*\u00020\u000f2\u0006\u00108\u001a\u0002092\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f05\"\u0019\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0003\u0010\u0004\"\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\"D\u0010\n\u001a5\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000bj\u0002`\u0012¢\u0006\u0002\b\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0019\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0017\u0010\u0004\"\u0019\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\n\n\u0002\u0010\u0005\u001a\u0004\b\u0019\u0010\u0004\"\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0011\u0010\u001e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\t\"\u0011\u0010 \u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\t\"D\u0010\"\u001a5\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000bj\u0002`\u0012¢\u0006\u0002\b\u0013¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015\"\u0015\u0010$\u001a\u00020%*\u00020&8F¢\u0006\u0006\u001a\u0004\b$\u0010'\"\u0015\u0010(\u001a\u00020%*\u00020)8F¢\u0006\u0006\u001a\u0004\b(\u0010*¨\u0006;"},
   d2 = {"denseLeavesRot", "", "Lmods/octarinecore/common/Rotation;", "getDenseLeavesRot", "()[Lmods/octarinecore/common/Rotation;", "[Lmods/octarinecore/common/Rotation;", "down1", "Lmods/octarinecore/common/Int3;", "getDown1", "()Lmods/octarinecore/common/Int3;", "greywash", "Lkotlin/Function6;", "Lmods/octarinecore/client/render/RenderVertex;", "Lmods/octarinecore/client/render/ShadingContext;", "", "Lmods/octarinecore/client/render/Quad;", "Lmods/octarinecore/client/render/Vertex;", "", "Lmods/octarinecore/client/render/PostProcessLambda;", "Lkotlin/ExtensionFunctionType;", "getGreywash", "()Lkotlin/jvm/functions/Function6;", "normalLeavesRot", "getNormalLeavesRot", "rotationFromUp", "getRotationFromUp", "snowOffset", "Lmods/octarinecore/common/Double3;", "getSnowOffset", "()Lmods/octarinecore/common/Double3;", "up1", "getUp1", "up2", "getUp2", "whitewash", "getWhitewash", "isCutout", "", "Lnet/minecraft/util/BlockRenderLayer;", "(Lnet/minecraft/util/BlockRenderLayer;)Z", "isSnow", "Lnet/minecraft/block/state/IBlockState;", "(Lnet/minecraft/block/state/IBlockState;)Z", "xzDisk", "modelIdx", "canRenderInCutout", "canRenderInLayer", "layer", "mix", "Lmods/octarinecore/client/render/Model;", "first", "second", "predicate", "Lkotlin/Function1;", "toCross", "", "rotAxis", "Lnet/minecraft/util/EnumFacing;", "trans", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "Utils"
)
public final class Utils {
   @NotNull
   private static final Int3 up1;
   @NotNull
   private static final Int3 up2;
   @NotNull
   private static final Int3 down1;
   @NotNull
   private static final Double3 snowOffset;
   @NotNull
   private static final Rotation[] normalLeavesRot;
   @NotNull
   private static final Rotation[] denseLeavesRot;
   @NotNull
   private static final Function6 whitewash;
   @NotNull
   private static final Function6 greywash;
   @NotNull
   private static final Rotation[] rotationFromUp;

   @NotNull
   public static final Int3 getUp1() {
      return up1;
   }

   @NotNull
   public static final Int3 getUp2() {
      return up2;
   }

   @NotNull
   public static final Int3 getDown1() {
      return down1;
   }

   @NotNull
   public static final Double3 getSnowOffset() {
      return snowOffset;
   }

   @NotNull
   public static final Rotation[] getNormalLeavesRot() {
      return normalLeavesRot;
   }

   @NotNull
   public static final Rotation[] getDenseLeavesRot() {
      return denseLeavesRot;
   }

   @NotNull
   public static final Function6 getWhitewash() {
      return whitewash;
   }

   @NotNull
   public static final Function6 getGreywash() {
      return greywash;
   }

   public static final boolean isSnow(@NotNull IBlockState $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Material var1 = $receiver.func_185904_a();
      return Intrinsics.areEqual(var1, Material.field_151597_y) || Intrinsics.areEqual(var1, Material.field_151596_z);
   }

   @NotNull
   public static final List toCross(@NotNull Quad $receiver, @NotNull EnumFacing rotAxis, @NotNull Function1 trans) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(rotAxis, "rotAxis");
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      byte var3 = 0;
      Iterable $receiver$iv = (Iterable)(new IntRange(var3, 3));
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var6 = $receiver$iv.iterator();

      while(var6.hasNext()) {
         int item$iv$iv = ((IntIterator)var6).nextInt();
         Quad var13 = (Quad)trans.invoke($receiver.rotate(Rotation.Companion.getRot90()[rotAxis.ordinal()].times(item$iv$iv)).mirrorUV(item$iv$iv > 1, false));
         destination$iv$iv.add(var13);
      }

      return (List)destination$iv$iv;
   }

   @NotNull
   public static final List toCross(@NotNull Quad $receiver, @NotNull EnumFacing rotAxis) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(rotAxis, "rotAxis");
      return toCross($receiver, rotAxis, (Function1)null.INSTANCE);
   }

   @NotNull
   public static final Double3 xzDisk(int modelIdx) {
      double var1 = 6.283185307179586D * (double)modelIdx / 64.0D;
      return new Double3(Math.cos(var1), 0.0D, Math.sin(var1));
   }

   @NotNull
   public static final Rotation[] getRotationFromUp() {
      return rotationFromUp;
   }

   public static final void mix(@NotNull Model $receiver, @NotNull Model first, @NotNull Model second, @NotNull Function1 predicate) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(first, "first");
      Intrinsics.checkParameterIsNotNull(second, "second");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Iterable $receiver$iv = (Iterable)first.getQuads();
      int index$iv = 0;
      Iterator var6 = $receiver$iv.iterator();

      while(var6.hasNext()) {
         Object item$iv = var6.next();
         int var10000 = index$iv++;
         Quad quad = (Quad)item$iv;
         int qi = var10000;
         Quad otherQuad = (Quad)second.getQuads().get(qi);
         $receiver.add(new Quad(((Boolean)predicate.invoke(Integer.valueOf(0))).booleanValue() ? Vertex.copy$default(otherQuad.getV1(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null) : Vertex.copy$default(quad.getV1(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null), ((Boolean)predicate.invoke(Integer.valueOf(1))).booleanValue() ? Vertex.copy$default(otherQuad.getV2(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null) : Vertex.copy$default(quad.getV2(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null), ((Boolean)predicate.invoke(Integer.valueOf(2))).booleanValue() ? Vertex.copy$default(otherQuad.getV3(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null) : Vertex.copy$default(quad.getV3(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null), ((Boolean)predicate.invoke(Integer.valueOf(3))).booleanValue() ? Vertex.copy$default(otherQuad.getV4(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null) : Vertex.copy$default(quad.getV4(), (Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (Object)null)));
      }

   }

   public static final boolean isCutout(@NotNull BlockRenderLayer $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return Intrinsics.areEqual($receiver, BlockRenderLayer.CUTOUT) || Intrinsics.areEqual($receiver, BlockRenderLayer.CUTOUT_MIPPED);
   }

   public static final boolean canRenderInLayer(@NotNull IBlockState $receiver, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      return $receiver.func_177230_c().canRenderInLayer($receiver, layer);
   }

   public static final boolean canRenderInCutout(@NotNull IBlockState $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return $receiver.func_177230_c().canRenderInLayer($receiver, BlockRenderLayer.CUTOUT) || $receiver.func_177230_c().canRenderInLayer($receiver, BlockRenderLayer.CUTOUT_MIPPED);
   }

   static {
      up1 = new Int3(TuplesKt.to(Integer.valueOf(1), EnumFacing.UP));
      up2 = new Int3(TuplesKt.to(Integer.valueOf(2), EnumFacing.UP));
      down1 = new Int3(TuplesKt.to(Integer.valueOf(1), EnumFacing.DOWN));
      snowOffset = GeometryKt.times(EnumFacing.UP, 0.0625D);
      normalLeavesRot = new Rotation[]{Rotation.Companion.getIdentity()};
      denseLeavesRot = new Rotation[]{Rotation.Companion.getIdentity(), Rotation.Companion.getRot90()[EnumFacing.EAST.ordinal()], Rotation.Companion.getRot90()[EnumFacing.SOUTH.ordinal()]};
      whitewash = (Function6)null.INSTANCE;
      greywash = (Function6)null.INSTANCE;
      rotationFromUp = new Rotation[]{Rotation.Companion.getRot90()[EnumFacing.EAST.ordinal()].times(2), Rotation.Companion.getIdentity(), Rotation.Companion.getRot90()[EnumFacing.WEST.ordinal()], Rotation.Companion.getRot90()[EnumFacing.EAST.ordinal()], Rotation.Companion.getRot90()[EnumFacing.SOUTH.ordinal()], Rotation.Companion.getRot90()[EnumFacing.NORTH.ordinal()]};
   }
}
