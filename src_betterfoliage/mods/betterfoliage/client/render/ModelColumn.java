package mods.betterfoliage.client.render;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.CornerSingleFallback;
import mods.octarinecore.client.render.EdgeInterpolateFallback;
import mods.octarinecore.client.render.FaceCenter;
import mods.octarinecore.client.render.FaceFlat;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.Shader;
import mods.octarinecore.client.render.ShadersKt;
import mods.octarinecore.client.render.ShadingKt;
import mods.octarinecore.client.render.UV;
import mods.octarinecore.client.render.Vertex;
import mods.octarinecore.common.Double3;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n\u001a\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n\u001a(\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007\u001a \u0010\u0011\u001a\u00020\r*\u00020\u000e2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007\u001a8\u0010\u0012\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007\u001a0\u0010\u0015\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\n2\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0011\u0010\u0002\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0016"},
   d2 = {"chamferAffinity", "", "zProtectionScale", "Lmods/octarinecore/common/Double3;", "getZProtectionScale", "()Lmods/octarinecore/common/Double3;", "bottomExtension", "Lkotlin/Function1;", "Lmods/octarinecore/client/render/Quad;", "size", "", "topExtension", "columnLid", "", "Lmods/octarinecore/client/render/Model;", "radius", "transform", "columnLidSquare", "columnSide", "yBottom", "yTop", "columnSideSquare", "BetterFoliage-MC1.12"}
)
@JvmName(
   name = "ModelColumn"
)
public final class ModelColumn {
   public static final float chamferAffinity = 0.9F;

   @NotNull
   public static final Double3 getZProtectionScale() {
      return new Double3(Config.roundLogs.INSTANCE.getZProtection(), 1.0D, Config.roundLogs.INSTANCE.getZProtection());
   }

   public static final void columnSide(@NotNull Model $receiver, double radius, double yBottom, double yTop, @NotNull Function1 transform) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      double halfRadius = radius * 0.5D;
      Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new Quad[]{Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.0D, 0.5D, 0.5D - radius, 0.5D, yBottom, yTop), 0.0D, 0.5D - radius, 0.0D, 0.0D, 12, (Object)null), ShadingKt.faceOrientedInterpolate(EnumFacing.SOUTH), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 5, (Object)null), (Function2)null.INSTANCE), Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.5D - radius, 0.5D, 0.5D - halfRadius, 0.5D - halfRadius, yBottom, yTop), 0.5D - radius, 0.0D, 0.0D, 0.0D, 14, (Object)null), ShadingKt.faceOrientedAuto$default(EnumFacing.SOUTH, ShadersKt.cornerInterpolate(Axis.Y, 0.9F, Config.roundLogs.INSTANCE.getDimming()), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default(EnumFacing.SOUTH, ShadersKt.cornerInterpolate(Axis.Y, 0.5F, Config.roundLogs.INSTANCE.getDimming()), (Function2)null, 4, (Object)null), (Function2)null.INSTANCE)});
      Iterator var11 = $receiver$iv.iterator();

      Object element$iv;
      Quad it;
      while(var11.hasNext()) {
         element$iv = var11.next();
         it = (Quad)element$iv;
         $receiver.add((Quad)transform.invoke(it.setFlatShader((Shader)(new FaceFlat(EnumFacing.SOUTH)))));
      }

      $receiver$iv = (Iterable)CollectionsKt.listOf(new Quad[]{Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.5D - halfRadius, 0.5D - halfRadius, 0.5D, 0.5D - radius, yBottom, yTop), 0.0D, radius - 0.5D, 0.0D, 0.0D, 13, (Object)null), ShadingKt.faceOrientedAuto$default(EnumFacing.EAST, ShadersKt.cornerInterpolate(Axis.Y, 0.9F, Config.roundLogs.INSTANCE.getDimming()), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default(EnumFacing.EAST, ShadersKt.cornerInterpolate(Axis.Y, 0.5F, Config.roundLogs.INSTANCE.getDimming()), (Function2)null, 4, (Object)null), (Function2)null.INSTANCE), Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.5D, 0.5D - radius, 0.5D, 0.0D, yBottom, yTop), radius - 0.5D, 0.0D, 0.0D, 0.0D, 12, (Object)null), ShadingKt.faceOrientedInterpolate(EnumFacing.EAST), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 5, (Object)null), (Function2)null.INSTANCE)});
      var11 = $receiver$iv.iterator();

      while(var11.hasNext()) {
         element$iv = var11.next();
         it = (Quad)element$iv;
         $receiver.add((Quad)transform.invoke(it.setFlatShader((Shader)(new FaceFlat(EnumFacing.EAST)))));
      }

      List $receiver$iv = $receiver.getQuads();
      byte idx1$iv = 1;
      int idx2$iv = 2;
      Object e$iv = $receiver$iv.get(idx1$iv);
      $receiver$iv.set(idx1$iv, $receiver$iv.get(idx2$iv));
      $receiver$iv.set(idx2$iv, e$iv);
   }

   public static final void columnSideSquare(@NotNull Model $receiver, double yBottom, double yTop, @NotNull Function1 transform) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new Quad[]{Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.0D, 0.5D, 0.5D, 0.5D, yBottom, yTop), 0.0D, 0.0D, 0.0D, 0.0D, 14, (Object)null), ShadingKt.faceOrientedInterpolate(EnumFacing.SOUTH), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 5, (Object)null), (Function2)null.INSTANCE), Quad.setAoShader$default(Quad.clampUV$default($receiver.verticalRectangle(0.5D, 0.5D, 0.5D, 0.0D, yBottom, yTop), 0.0D, 0.0D, 0.0D, 0.0D, 13, (Object)null), ShadingKt.faceOrientedInterpolate(EnumFacing.EAST), (Function2)null, 2, (Object)null).setAoShader(ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.cornerAo(Axis.Y), (Function2)null, 5, (Object)null), (Function2)null.INSTANCE)});
      Iterator var7 = $receiver$iv.iterator();

      while(var7.hasNext()) {
         Object element$iv = var7.next();
         Quad it = (Quad)element$iv;
         $receiver.add((Quad)transform.invoke(Quad.setFlatShader$default(it, ShadingKt.faceOrientedAuto$default((EnumFacing)null, ShadersKt.getCornerFlat(), (Function2)null, 5, (Object)null), (Function2)null, 2, (Object)null)));
      }

   }

   public static final void columnLid(@NotNull Model $receiver, double radius, @NotNull Function1 transform) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Vertex v1 = new Vertex(new Double3(0.0D, 0.5D, 0.0D), new UV(0.0D, 0.0D), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Vertex v2 = new Vertex(new Double3(0.0D, 0.5D, 0.5D), new UV(0.0D, 0.5D), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Vertex v3 = new Vertex(new Double3(0.5D - radius, 0.5D, 0.5D), new UV(0.5D - radius, 0.5D), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Vertex v4 = new Vertex(new Double3(0.5D - radius * 0.5D, 0.5D, 0.5D - radius * 0.5D), new UV(0.5D, 0.5D), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Vertex v5 = new Vertex(new Double3(0.5D, 0.5D, 0.5D - radius), new UV(0.5D, 0.5D - radius), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Vertex v6 = new Vertex(new Double3(0.5D, 0.5D, 0.0D), new UV(0.5D, 0.0D), (Shader)null, (Shader)null, 12, (DefaultConstructorMarker)null);
      Quad q2 = Quad.setAoShader$default(new Quad(v1, v2, v3, v4), ShadingKt.faceOrientedAuto$default(EnumFacing.UP, ShadersKt.cornerAo(Axis.Y), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null);
      Quad var10000 = new Quad;
      Vertex var10002 = q2.getV1();
      int idx = 0;
      Vertex vertex = var10002;
      Quad var19 = var10000;
      Quad var18 = var10000;
      Shader var10003;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      Vertex var20 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      Vertex var29 = q2.getV2();
      idx = 1;
      vertex = var29;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      Vertex var21 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      Vertex var10004 = q2.getV3();
      idx = 2;
      vertex = var10004;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      Vertex var22 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      Vertex var10005 = q2.getV4();
      idx = 3;
      vertex = var10005;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      Vertex var23 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      var19.<init>(var20, var21, var22, var23);
      Quad q1 = var18.cycleVertices(Config.INSTANCE.getNVidia() ? 0 : 1);
      Quad this_$iv = Quad.setAoShader$default(new Quad(v1, v4, v5, v6), ShadingKt.faceOrientedAuto$default(EnumFacing.UP, ShadersKt.cornerAo(Axis.Y), (Function2)null, 4, (Object)null), (Function2)null, 2, (Object)null);
      var10000 = new Quad;
      var10002 = this_$iv.getV1();
      int idx = 0;
      Vertex vertex = var10002;
      var19 = var10000;
      var18 = var10000;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 3:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      var20 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      var29 = this_$iv.getV2();
      idx = 1;
      vertex = var29;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 3:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      var21 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      var10004 = this_$iv.getV3();
      idx = 2;
      vertex = var10004;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 3:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      var22 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      var10005 = this_$iv.getV4();
      idx = 3;
      vertex = var10005;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 3:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = vertex.getAoShader();
      }

      var23 = Vertex.copy$default(vertex, (Double3)null, (UV)null, var10003, (Shader)null, 11, (Object)null);
      var19.<init>(var20, var21, var22, var23);
      q2 = var18.cycleVertices(Config.INSTANCE.getNVidia() ? 0 : 1);
      Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(new Quad[]{q1, q2});
      Iterator var28 = $receiver$iv.iterator();

      while(var28.hasNext()) {
         Object element$iv = var28.next();
         Quad it = (Quad)element$iv;
         $receiver.add((Quad)transform.invoke(it.setFlatShader((Shader)(new FaceFlat(EnumFacing.UP)))));
      }

   }

   public static final void columnLidSquare(@NotNull Model $receiver, @NotNull Function1 transform) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      double var2 = 0.5D;
      double var4 = 0.5D;
      double var6 = 0.0D;
      double var8 = 0.5D;
      Quad this_$iv = $receiver.horizontalRectangle(0.0D, var6, var8, var4, var2);
      Quad var10000 = new Quad;
      Vertex var10002 = this_$iv.getV1();
      int idx = 0;
      Vertex vertex = var10002;
      Quad var13 = var10000;
      Quad var12 = var10000;
      UV var20 = new UV(vertex.getXyz().getX(), vertex.getXyz().getZ());
      Shader var10003;
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      case 2:
         var10003 = (Shader)(new CornerSingleFallback(EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP, 0.0F, 16, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
      }

      Vertex var14 = Vertex.copy$default(vertex, (Double3)null, var20, var10003, (Shader)null, 9, (Object)null);
      Vertex var21 = this_$iv.getV2();
      idx = 1;
      vertex = var21;
      var20 = new UV(vertex.getXyz().getX(), vertex.getXyz().getZ());
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      case 2:
         var10003 = (Shader)(new CornerSingleFallback(EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP, 0.0F, 16, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
      }

      Vertex var15 = Vertex.copy$default(vertex, (Double3)null, var20, var10003, (Shader)null, 9, (Object)null);
      Vertex var10004 = this_$iv.getV3();
      idx = 2;
      vertex = var10004;
      var20 = new UV(vertex.getXyz().getX(), vertex.getXyz().getZ());
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      case 2:
         var10003 = (Shader)(new CornerSingleFallback(EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP, 0.0F, 16, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
      }

      Vertex var16 = Vertex.copy$default(vertex, (Double3)null, var20, var10003, (Shader)null, 9, (Object)null);
      Vertex var10005 = this_$iv.getV4();
      idx = 3;
      vertex = var10005;
      var20 = new UV(vertex.getXyz().getX(), vertex.getXyz().getZ());
      switch(idx) {
      case 0:
         var10003 = (Shader)(new FaceCenter(EnumFacing.UP));
         break;
      case 1:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.SOUTH, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
         break;
      case 2:
         var10003 = (Shader)(new CornerSingleFallback(EnumFacing.UP, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.UP, 0.0F, 16, (DefaultConstructorMarker)null));
         break;
      default:
         var10003 = (Shader)(new EdgeInterpolateFallback(EnumFacing.UP, EnumFacing.EAST, 0.0D, 0.0F, 8, (DefaultConstructorMarker)null));
      }

      Vertex var17 = Vertex.copy$default(vertex, (Double3)null, var20, var10003, (Shader)null, 9, (Object)null);
      var13.<init>(var14, var15, var16, var17);
      $receiver.add((Quad)transform.invoke(var12.setFlatShader((Shader)(new FaceFlat(EnumFacing.UP)))));
   }

   @NotNull
   public static final Function1 topExtension(final double size) {
      return (Function1)(new Function1() {
         @NotNull
         public final Quad invoke(@NotNull Quad q) {
            Intrinsics.checkParameterIsNotNull(q, "q");
            Quad this_$iv = Quad.clampUV$default(q, 0.0D, 0.0D, 0.5D - size, 0.0D, 11, (Object)null);
            Quad var10000 = new Quad;
            Vertex var10002 = this_$iv.getV1();
            int idx = 0;
            Vertex vertex = var10002;
            Quad var8 = var10000;
            Quad var7 = var10000;
            Vertex var9 = idx < 2 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10003 = this_$iv.getV2();
            idx = 1;
            vertex = var10003;
            Vertex var10 = idx < 2 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10004 = this_$iv.getV3();
            idx = 2;
            vertex = var10004;
            Vertex var11 = idx < 2 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10005 = this_$iv.getV4();
            idx = 3;
            vertex = var10005;
            Vertex var12 = idx < 2 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            var8.<init>(var9, var10, var11, var12);
            return var7;
         }
      });
   }

   @NotNull
   public static final Function1 bottomExtension(final double size) {
      return (Function1)(new Function1() {
         @NotNull
         public final Quad invoke(@NotNull Quad q) {
            Intrinsics.checkParameterIsNotNull(q, "q");
            Quad this_$iv = Quad.clampUV$default(q, 0.0D, 0.0D, 0.0D, -0.5D + size, 7, (Object)null);
            Quad var10000 = new Quad;
            Vertex var10002 = this_$iv.getV1();
            int idx = 0;
            Vertex vertex = var10002;
            Quad var8 = var10000;
            Quad var7 = var10000;
            Vertex var9 = idx > 1 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10003 = this_$iv.getV2();
            idx = 1;
            vertex = var10003;
            Vertex var10 = idx > 1 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10004 = this_$iv.getV3();
            idx = 2;
            vertex = var10004;
            Vertex var11 = idx > 1 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            Vertex var10005 = this_$iv.getV4();
            idx = 3;
            vertex = var10005;
            Vertex var12 = idx > 1 ? vertex : Vertex.copy$default(vertex, vertex.getXyz().times(ModelColumn.getZProtectionScale()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
            var8.<init>(var9, var10, var11, var12);
            return var7;
         }
      });
   }
}
