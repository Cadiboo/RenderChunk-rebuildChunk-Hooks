package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Rotation;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J.\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010\u001d\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u001bJ\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J1\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u000e\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&J\u0013\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020&HÖ\u0001J\u0016\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020(2\u0006\u0010-\u001a\u00020(J\u001a\u0010.\u001a\u00020\u00002\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020100J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010/\u001a\u00020\fJ\u000e\u00102\u001a\u00020\u00002\u0006\u00103\u001a\u000204J\u000e\u00105\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&J\u000e\u00106\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u001bJ\u000e\u00106\u001a\u00020\u00002\u0006\u00106\u001a\u00020\fJ\u000e\u00107\u001a\u00020\u00002\u0006\u00106\u001a\u00020\u001bJ@\u00108\u001a\u00020\u00002\u001c\u00109\u001a\u0018\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020;0:j\u0002`<2\u001a\b\u0002\u0010=\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020(0:J\u000e\u0010>\u001a\u00020\u00002\u0006\u0010?\u001a\u00020;J@\u0010>\u001a\u00020\u00002\u001c\u00109\u001a\u0018\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020;0:j\u0002`<2\u001a\b\u0002\u0010=\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020(0:J\t\u0010@\u001a\u00020AHÖ\u0001J\u001d\u0010B\u001a\u00020\u00002\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030CH\u0086\bJ#\u0010D\u001a\u00020\u00002\u0018\u0010/\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00030:H\u0086\bR\u0011\u0010\b\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0019\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0015¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017¨\u0006E"},
   d2 = {"Lmods/octarinecore/client/render/Quad;", "", "v1", "Lmods/octarinecore/client/render/Vertex;", "v2", "v3", "v4", "(Lmods/octarinecore/client/render/Vertex;Lmods/octarinecore/client/render/Vertex;Lmods/octarinecore/client/render/Vertex;Lmods/octarinecore/client/render/Vertex;)V", "flipped", "getFlipped", "()Lmods/octarinecore/client/render/Quad;", "normal", "Lmods/octarinecore/common/Double3;", "getNormal", "()Lmods/octarinecore/common/Double3;", "getV1", "()Lmods/octarinecore/client/render/Vertex;", "getV2", "getV3", "getV4", "verts", "", "getVerts", "()[Lmods/octarinecore/client/render/Vertex;", "[Lmods/octarinecore/client/render/Vertex;", "clampUV", "minU", "", "maxU", "minV", "maxV", "component1", "component2", "component3", "component4", "copy", "cycleVertices", "n", "", "equals", "", "other", "hashCode", "mirrorUV", "mirrorU", "mirrorV", "move", "trans", "Lkotlin/Pair;", "Lnet/minecraft/util/EnumFacing;", "rotate", "rot", "Lmods/octarinecore/common/Rotation;", "rotateUV", "scale", "scaleUV", "setAoShader", "factory", "Lkotlin/Function2;", "Lmods/octarinecore/client/render/Shader;", "Lmods/octarinecore/client/render/ShaderFactory;", "predicate", "setFlatShader", "shader", "toString", "", "transformV", "Lkotlin/Function1;", "transformVI", "BetterFoliage-MC1.12"}
)
public final class Quad {
   @NotNull
   private final Vertex[] verts;
   @NotNull
   private final Vertex v1;
   @NotNull
   private final Vertex v2;
   @NotNull
   private final Vertex v3;
   @NotNull
   private final Vertex v4;

   @NotNull
   public final Vertex[] getVerts() {
      return this.verts;
   }

   @NotNull
   public final Quad transformV(@NotNull Function1 trans) {
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx = false;
      Vertex vertex = var10002;
      Quad var9 = var10000;
      Quad var8 = var10000;
      Vertex var10 = (Vertex)trans.invoke(vertex);
      Vertex var10003 = this.getV2();
      idx = true;
      vertex = var10003;
      Vertex var11 = (Vertex)trans.invoke(vertex);
      Vertex var10004 = this.getV3();
      idx = true;
      vertex = var10004;
      Vertex var12 = (Vertex)trans.invoke(vertex);
      Vertex var10005 = this.getV4();
      idx = true;
      vertex = var10005;
      Vertex var13 = (Vertex)trans.invoke(vertex);
      var9.<init>(var10, var11, var12, var13);
      return var8;
   }

   @NotNull
   public final Quad transformVI(@NotNull Function2 trans) {
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      return new Quad((Vertex)trans.invoke(this.getV1(), Integer.valueOf(0)), (Vertex)trans.invoke(this.getV2(), Integer.valueOf(1)), (Vertex)trans.invoke(this.getV3(), Integer.valueOf(2)), (Vertex)trans.invoke(this.getV4(), Integer.valueOf(3)));
   }

   @NotNull
   public final Double3 getNormal() {
      return this.v2.getXyz().minus(this.v1.getXyz()).cross(this.v4.getXyz().minus(this.v1.getXyz())).getNormalize();
   }

   @NotNull
   public final Quad move(@NotNull Double3 trans) {
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var6 = var10000;
      Quad var7 = var10000;
      Vertex var8 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().plus(trans), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var9 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().plus(trans), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var10 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().plus(trans), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var11 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().plus(trans), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      var6.<init>(var8, var9, var10, var11);
      return var7;
   }

   @NotNull
   public final Quad move(@NotNull Pair trans) {
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      return this.move((new Double3((EnumFacing)trans.getSecond())).times(((Number)trans.getFirst()).doubleValue()));
   }

   @NotNull
   public final Quad scale(double scale) {
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var7 = var10000;
      Quad var8 = var10000;
      Vertex var9 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().times(scale), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var10 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().times(scale), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var11 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().times(scale), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var12 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().times(scale), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      var7.<init>(var9, var10, var11, var12);
      return var8;
   }

   @NotNull
   public final Quad scale(@NotNull Double3 scale) {
      Intrinsics.checkParameterIsNotNull(scale, "scale");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var6 = var10000;
      Quad var7 = var10000;
      Vertex var8 = Vertex.copy$default(vertex$iv, new Double3(vertex$iv.getXyz().getX() * scale.getX(), vertex$iv.getXyz().getY() * scale.getY(), vertex$iv.getXyz().getZ() * scale.getZ()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var9 = Vertex.copy$default(vertex$iv, new Double3(vertex$iv.getXyz().getX() * scale.getX(), vertex$iv.getXyz().getY() * scale.getY(), vertex$iv.getXyz().getZ() * scale.getZ()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var10 = Vertex.copy$default(vertex$iv, new Double3(vertex$iv.getXyz().getX() * scale.getX(), vertex$iv.getXyz().getY() * scale.getY(), vertex$iv.getXyz().getZ() * scale.getZ()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var11 = Vertex.copy$default(vertex$iv, new Double3(vertex$iv.getXyz().getX() * scale.getX(), vertex$iv.getXyz().getY() * scale.getY(), vertex$iv.getXyz().getZ() * scale.getZ()), (UV)null, (Shader)null, (Shader)null, 14, (Object)null);
      var6.<init>(var8, var9, var10, var11);
      return var7;
   }

   @NotNull
   public final Quad scaleUV(double scale) {
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var7 = var10000;
      Quad var8 = var10000;
      Vertex var9 = Vertex.copy$default(vertex$iv, (Double3)null, new UV(vertex$iv.getUv().getU() * scale, vertex$iv.getUv().getV() * scale), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var10 = Vertex.copy$default(vertex$iv, (Double3)null, new UV(vertex$iv.getUv().getU() * scale, vertex$iv.getUv().getV() * scale), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var11 = Vertex.copy$default(vertex$iv, (Double3)null, new UV(vertex$iv.getUv().getU() * scale, vertex$iv.getUv().getV() * scale), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var12 = Vertex.copy$default(vertex$iv, (Double3)null, new UV(vertex$iv.getUv().getU() * scale, vertex$iv.getUv().getV() * scale), (Shader)null, (Shader)null, 13, (Object)null);
      var7.<init>(var9, var10, var11, var12);
      return var8;
   }

   @NotNull
   public final Quad rotate(@NotNull Rotation rot) {
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var6 = var10000;
      Quad var7 = var10000;
      Vertex var8 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().rotate(rot), (UV)null, vertex$iv.getAoShader().rotate(rot), vertex$iv.getFlatShader().rotate(rot), 2, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var9 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().rotate(rot), (UV)null, vertex$iv.getAoShader().rotate(rot), vertex$iv.getFlatShader().rotate(rot), 2, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var10 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().rotate(rot), (UV)null, vertex$iv.getAoShader().rotate(rot), vertex$iv.getFlatShader().rotate(rot), 2, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var11 = Vertex.copy$default(vertex$iv, vertex$iv.getXyz().rotate(rot), (UV)null, vertex$iv.getAoShader().rotate(rot), vertex$iv.getFlatShader().rotate(rot), 2, (Object)null);
      var6.<init>(var8, var9, var10, var11);
      return var7;
   }

   @NotNull
   public final Quad rotateUV(int n) {
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var6 = var10000;
      Quad var7 = var10000;
      Vertex var8 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().rotate(n), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var9 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().rotate(n), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var10 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().rotate(n), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var11 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().rotate(n), (Shader)null, (Shader)null, 13, (Object)null);
      var6.<init>(var8, var9, var10, var11);
      return var7;
   }

   @NotNull
   public final Quad clampUV(double minU, double maxU, double minV, double maxV) {
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var13 = var10000;
      Quad var14 = var10000;
      Vertex var17 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().clamp(minU, maxU, minV, maxV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var18 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().clamp(minU, maxU, minV, maxV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var19 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().clamp(minU, maxU, minV, maxV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var20 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().clamp(minU, maxU, minV, maxV), (Shader)null, (Shader)null, 13, (Object)null);
      var13.<init>(var17, var18, var19, var20);
      return var14;
   }

   @NotNull
   public final Quad mirrorUV(boolean mirrorU, boolean mirrorV) {
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx$iv = false;
      Vertex vertex$iv = var10002;
      Quad var7 = var10000;
      Quad var8 = var10000;
      Vertex var9 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().mirror(mirrorU, mirrorV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10003 = this.getV2();
      idx$iv = true;
      vertex$iv = var10003;
      Vertex var10 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().mirror(mirrorU, mirrorV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10004 = this.getV3();
      idx$iv = true;
      vertex$iv = var10004;
      Vertex var11 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().mirror(mirrorU, mirrorV), (Shader)null, (Shader)null, 13, (Object)null);
      Vertex var10005 = this.getV4();
      idx$iv = true;
      vertex$iv = var10005;
      Vertex var12 = Vertex.copy$default(vertex$iv, (Double3)null, vertex$iv.getUv().mirror(mirrorU, mirrorV), (Shader)null, (Shader)null, 13, (Object)null);
      var7.<init>(var9, var10, var11, var12);
      return var8;
   }

   @NotNull
   public final Quad setAoShader(@NotNull Function2 factory, @NotNull Function2 predicate) {
      Intrinsics.checkParameterIsNotNull(factory, "factory");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx = 0;
      Vertex vertex = var10002;
      Quad var9 = var10000;
      Quad var8 = var10000;
      Vertex var10 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)factory.invoke(this, vertex), (Shader)null, 11, (Object)null);
      Vertex var10003 = this.getV2();
      idx = 1;
      vertex = var10003;
      Vertex var11 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)factory.invoke(this, vertex), (Shader)null, 11, (Object)null);
      Vertex var10004 = this.getV3();
      idx = 2;
      vertex = var10004;
      Vertex var12 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)factory.invoke(this, vertex), (Shader)null, 11, (Object)null);
      Vertex var10005 = this.getV4();
      idx = 3;
      vertex = var10005;
      Vertex var13 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)factory.invoke(this, vertex), (Shader)null, 11, (Object)null);
      var9.<init>(var10, var11, var12, var13);
      return var8;
   }

   @NotNull
   public final Quad setFlatShader(@NotNull Function2 factory, @NotNull Function2 predicate) {
      Intrinsics.checkParameterIsNotNull(factory, "factory");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx = 0;
      Vertex vertex = var10002;
      Quad var9 = var10000;
      Quad var8 = var10000;
      Vertex var10 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, (Shader)factory.invoke(this, vertex), 7, (Object)null);
      Vertex var10003 = this.getV2();
      idx = 1;
      vertex = var10003;
      Vertex var11 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, (Shader)factory.invoke(this, vertex), 7, (Object)null);
      Vertex var10004 = this.getV3();
      idx = 2;
      vertex = var10004;
      Vertex var12 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, (Shader)factory.invoke(this, vertex), 7, (Object)null);
      Vertex var10005 = this.getV4();
      idx = 3;
      vertex = var10005;
      Vertex var13 = !((Boolean)predicate.invoke(vertex, Integer.valueOf(idx))).booleanValue() ? vertex : Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, (Shader)factory.invoke(this, vertex), 7, (Object)null);
      var9.<init>(var10, var11, var12, var13);
      return var8;
   }

   @NotNull
   public final Quad setFlatShader(@NotNull Shader shader) {
      Intrinsics.checkParameterIsNotNull(shader, "shader");
      Quad var10000 = new Quad;
      Vertex var10002 = this.getV1();
      int idx = false;
      Vertex vertex = var10002;
      Quad var8 = var10000;
      Quad var7 = var10000;
      Vertex var9 = Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, shader, 7, (Object)null);
      Vertex var10003 = this.getV2();
      idx = true;
      vertex = var10003;
      Vertex var10 = Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, shader, 7, (Object)null);
      Vertex var10004 = this.getV3();
      idx = true;
      vertex = var10004;
      Vertex var11 = Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, shader, 7, (Object)null);
      Vertex var10005 = this.getV4();
      idx = true;
      vertex = var10005;
      Vertex var12 = Vertex.copy$default(vertex, (Double3)null, (UV)null, (Shader)null, shader, 7, (Object)null);
      var8.<init>(var9, var10, var11, var12);
      return var7;
   }

   @NotNull
   public final Quad getFlipped() {
      return new Quad(this.v4, this.v3, this.v2, this.v1);
   }

   @NotNull
   public final Quad cycleVertices(int n) {
      Quad var10000;
      switch(n % 4) {
      case 1:
         var10000 = new Quad(this.v2, this.v3, this.v4, this.v1);
         break;
      case 2:
         var10000 = new Quad(this.v3, this.v4, this.v1, this.v2);
         break;
      case 3:
         var10000 = new Quad(this.v4, this.v1, this.v2, this.v3);
         break;
      default:
         var10000 = copy$default(this, (Vertex)null, (Vertex)null, (Vertex)null, (Vertex)null, 15, (Object)null);
      }

      return var10000;
   }

   @NotNull
   public final Vertex getV1() {
      return this.v1;
   }

   @NotNull
   public final Vertex getV2() {
      return this.v2;
   }

   @NotNull
   public final Vertex getV3() {
      return this.v3;
   }

   @NotNull
   public final Vertex getV4() {
      return this.v4;
   }

   public Quad(@NotNull Vertex v1, @NotNull Vertex v2, @NotNull Vertex v3, @NotNull Vertex v4) {
      Intrinsics.checkParameterIsNotNull(v1, "v1");
      Intrinsics.checkParameterIsNotNull(v2, "v2");
      Intrinsics.checkParameterIsNotNull(v3, "v3");
      Intrinsics.checkParameterIsNotNull(v4, "v4");
      super();
      this.v1 = v1;
      this.v2 = v2;
      this.v3 = v3;
      this.v4 = v4;
      this.verts = new Vertex[]{this.v1, this.v2, this.v3, this.v4};
   }

   @NotNull
   public final Vertex component1() {
      return this.v1;
   }

   @NotNull
   public final Vertex component2() {
      return this.v2;
   }

   @NotNull
   public final Vertex component3() {
      return this.v3;
   }

   @NotNull
   public final Vertex component4() {
      return this.v4;
   }

   @NotNull
   public final Quad copy(@NotNull Vertex v1, @NotNull Vertex v2, @NotNull Vertex v3, @NotNull Vertex v4) {
      Intrinsics.checkParameterIsNotNull(v1, "v1");
      Intrinsics.checkParameterIsNotNull(v2, "v2");
      Intrinsics.checkParameterIsNotNull(v3, "v3");
      Intrinsics.checkParameterIsNotNull(v4, "v4");
      return new Quad(v1, v2, v3, v4);
   }

   public String toString() {
      return "Quad(v1=" + this.v1 + ", v2=" + this.v2 + ", v3=" + this.v3 + ", v4=" + this.v4 + ")";
   }

   public int hashCode() {
      return (((this.v1 != null ? this.v1.hashCode() : 0) * 31 + (this.v2 != null ? this.v2.hashCode() : 0)) * 31 + (this.v3 != null ? this.v3.hashCode() : 0)) * 31 + (this.v4 != null ? this.v4.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof Quad) {
            Quad var2 = (Quad)var1;
            if (Intrinsics.areEqual(this.v1, var2.v1) && Intrinsics.areEqual(this.v2, var2.v2) && Intrinsics.areEqual(this.v3, var2.v3) && Intrinsics.areEqual(this.v4, var2.v4)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
