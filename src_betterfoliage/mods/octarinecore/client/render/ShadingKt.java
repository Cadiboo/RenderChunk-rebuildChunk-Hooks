package mods.octarinecore.client.render;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.FaceCorners;
import mods.octarinecore.common.GeometryKt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 2,
   d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aT\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0016\b\u0002\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\"\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\tj\u0002`\n\u001ar\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00072(\b\u0002\u0010\b\u001a\"\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0004\u0018\u00010\tj\u0004\u0018\u0001`\n2\"\b\u0002\u0010\r\u001a\u001c\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0001j\u0004\u0018\u0001`\u000e\u001a$\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0007*:\u0010\u0010\"\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\t2\u001a\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\t*.\u0010\u0011\"\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u00012\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00040\u0001*.\u0010\u0012\"\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00012\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001¨\u0006\u0013"},
   d2 = {"edgeOrientedAuto", "Lkotlin/Function2;", "Lmods/octarinecore/client/render/Quad;", "Lmods/octarinecore/client/render/Vertex;", "Lmods/octarinecore/client/render/Shader;", "overrideEdge", "Lkotlin/Pair;", "Lnet/minecraft/util/EnumFacing;", "corner", "Lkotlin/Function3;", "Lmods/octarinecore/client/render/CornerShaderFactory;", "faceOrientedAuto", "overrideFace", "edge", "Lmods/octarinecore/client/render/EdgeShaderFactory;", "faceOrientedInterpolate", "CornerShaderFactory", "EdgeShaderFactory", "ShaderFactory", "BetterFoliage-MC1.12"}
)
public final class ShadingKt {
   @NotNull
   public static final Function2 faceOrientedAuto(@Nullable final EnumFacing overrideFace, @Nullable final Function3 corner, @Nullable final Function2 edge) {
      return (Function2)(new Function2() {
         @NotNull
         public final Shader invoke(@NotNull Quad quad, @NotNull Vertex vertex) {
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            Intrinsics.checkParameterIsNotNull(vertex, "vertex");
            EnumFacing var10000 = overrideFace;
            if (overrideFace == null) {
               var10000 = quad.getNormal().getNearestCardinal();
            }

            final EnumFacing quadFace = var10000;
            Pair nearestCorner = GeometryKt.nearestPosition(vertex.getXyz(), (Iterable)((FaceCorners)GeometryKt.getFaceCorners().get(quadFace.ordinal())).getAsList(), (Function1)(new Function1() {
               @NotNull
               public final Double3 invoke(@NotNull Pair it) {
                  Intrinsics.checkParameterIsNotNull(it, "it");
                  return GeometryKt.getVec(quadFace).plus(GeometryKt.getVec((EnumFacing)it.getFirst())).plus(GeometryKt.getVec((EnumFacing)it.getSecond())).times(0.5D);
               }
            }));
            Pair nearestEdge = GeometryKt.nearestPosition(vertex.getXyz(), (Iterable)GeometryKt.getPerpendiculars(quadFace), (Function1)(new Function1() {
               @NotNull
               public final Double3 invoke(@NotNull EnumFacing it) {
                  Intrinsics.checkParameterIsNotNull(it, "it");
                  return GeometryKt.getVec(quadFace).plus(GeometryKt.getVec(it)).times(0.5D);
               }
            }));
            if (edge == null || ((Number)nearestEdge.getSecond()).doubleValue() >= ((Number)nearestCorner.getSecond()).doubleValue() && corner != null) {
               Function3 var6 = corner;
               if (corner == null) {
                  Intrinsics.throwNpe();
               }

               return (Shader)var6.invoke(quadFace, ((Pair)nearestCorner.getFirst()).getFirst(), ((Pair)nearestCorner.getFirst()).getSecond());
            } else {
               return (Shader)edge.invoke(quadFace, nearestEdge.getFirst());
            }
         }
      });
   }

   @NotNull
   public static final Function2 edgeOrientedAuto(@Nullable final Pair overrideEdge, @NotNull final Function3 corner) {
      Intrinsics.checkParameterIsNotNull(corner, "corner");
      return (Function2)(new Function2() {
         @NotNull
         public final Shader invoke(@NotNull Quad quad, @NotNull Vertex vertex) {
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            Intrinsics.checkParameterIsNotNull(vertex, "vertex");
            Pair var10000 = overrideEdge;
            if (overrideEdge == null) {
               var10000 = (Pair)GeometryKt.nearestAngle(quad.getNormal(), (Iterable)GeometryKt.getBoxEdges(), (Function1)null.INSTANCE).getFirst();
            }

            Pair edgeDir = var10000;
            final EnumFacing nearestFace = (EnumFacing)GeometryKt.nearestPosition(vertex.getXyz(), (Iterable)TuplesKt.toList(edgeDir), (Function1)null.INSTANCE).getFirst();
            Pair nearestCorner = (Pair)GeometryKt.nearestPosition(vertex.getXyz(), (Iterable)((FaceCorners)GeometryKt.getFaceCorners().get(nearestFace.ordinal())).getAsList(), (Function1)(new Function1() {
               @NotNull
               public final Double3 invoke(@NotNull Pair it) {
                  Intrinsics.checkParameterIsNotNull(it, "it");
                  return GeometryKt.getVec(nearestFace).plus(GeometryKt.getVec((EnumFacing)it.getFirst())).plus(GeometryKt.getVec((EnumFacing)it.getSecond())).times(0.5D);
               }
            })).getFirst();
            return (Shader)corner.invoke(nearestFace, nearestCorner.getFirst(), nearestCorner.getSecond());
         }
      });
   }

   @NotNull
   public static final Function2 faceOrientedInterpolate(@Nullable final EnumFacing overrideFace) {
      return (Function2)(new Function2() {
         @NotNull
         public final Shader invoke(@NotNull Quad quad, @NotNull final Vertex vertex) {
            Intrinsics.checkParameterIsNotNull(quad, "quad");
            Intrinsics.checkParameterIsNotNull(vertex, "vertex");
            Function2 resolver = ShadingKt.faceOrientedAuto$default(overrideFace, (Function3)null, (Function2)(new Function2() {
               @NotNull
               public final EdgeInterpolateFallback invoke(@NotNull EnumFacing face, @NotNull EnumFacing edgeDir) {
                  Intrinsics.checkParameterIsNotNull(face, "face");
                  Intrinsics.checkParameterIsNotNull(edgeDir, "edgeDir");
                  Iterable var4 = (Iterable)GeometryKt.getAxes();
                  Iterator var6 = var4.iterator();

                  Object var10000;
                  while(true) {
                     if (!var6.hasNext()) {
                        var10000 = null;
                        break;
                     }

                     Object var7 = var6.next();
                     Axis it = (Axis)var7;
                     if (Intrinsics.areEqual(it, face.func_176740_k()) ^ true && Intrinsics.areEqual(it, edgeDir.func_176740_k()) ^ true) {
                        var10000 = var7;
                        break;
                     }
                  }

                  if (var10000 == null) {
                     Intrinsics.throwNpe();
                  }

                  Axis axis = (Axis)var10000;
                  Double3 vec = new Double3(GeometryKt.getFace(TuplesKt.to(axis, AxisDirection.POSITIVE)));
                  double pos = vertex.getXyz().dot(vec);
                  return new EdgeInterpolateFallback(face, edgeDir, pos, 0.0F, 8, (DefaultConstructorMarker)null);
               }
            }), 2, (Object)null);
            return (Shader)resolver.invoke(quad, vertex);
         }
      });
   }
}
