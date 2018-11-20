package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Double3;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"},
   d2 = {"Lmods/octarinecore/client/render/Vertex;", "", "xyz", "Lmods/octarinecore/common/Double3;", "uv", "Lmods/octarinecore/client/render/UV;", "aoShader", "Lmods/octarinecore/client/render/Shader;", "flatShader", "(Lmods/octarinecore/common/Double3;Lmods/octarinecore/client/render/UV;Lmods/octarinecore/client/render/Shader;Lmods/octarinecore/client/render/Shader;)V", "getAoShader", "()Lmods/octarinecore/client/render/Shader;", "getFlatShader", "getUv", "()Lmods/octarinecore/client/render/UV;", "getXyz", "()Lmods/octarinecore/common/Double3;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "BetterFoliage-MC1.12"}
)
public final class Vertex {
   @NotNull
   private final Double3 xyz;
   @NotNull
   private final UV uv;
   @NotNull
   private final Shader aoShader;
   @NotNull
   private final Shader flatShader;

   @NotNull
   public final Double3 getXyz() {
      return this.xyz;
   }

   @NotNull
   public final UV getUv() {
      return this.uv;
   }

   @NotNull
   public final Shader getAoShader() {
      return this.aoShader;
   }

   @NotNull
   public final Shader getFlatShader() {
      return this.flatShader;
   }

   public Vertex(@NotNull Double3 xyz, @NotNull UV uv, @NotNull Shader aoShader, @NotNull Shader flatShader) {
      Intrinsics.checkParameterIsNotNull(xyz, "xyz");
      Intrinsics.checkParameterIsNotNull(uv, "uv");
      Intrinsics.checkParameterIsNotNull(aoShader, "aoShader");
      Intrinsics.checkParameterIsNotNull(flatShader, "flatShader");
      super();
      this.xyz = xyz;
      this.uv = uv;
      this.aoShader = aoShader;
      this.flatShader = flatShader;
   }

   // $FF: synthetic method
   public Vertex(Double3 var1, UV var2, Shader var3, Shader var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 1) != 0) {
         var1 = new Double3(0.0D, 0.0D, 0.0D);
      }

      if ((var5 & 2) != 0) {
         var2 = new UV(0.0D, 0.0D);
      }

      if ((var5 & 4) != 0) {
         var3 = (Shader)NoShader.INSTANCE;
      }

      if ((var5 & 8) != 0) {
         var4 = (Shader)NoShader.INSTANCE;
      }

      this(var1, var2, var3, var4);
   }

   public Vertex() {
      this((Double3)null, (UV)null, (Shader)null, (Shader)null, 15, (DefaultConstructorMarker)null);
   }

   @NotNull
   public final Double3 component1() {
      return this.xyz;
   }

   @NotNull
   public final UV component2() {
      return this.uv;
   }

   @NotNull
   public final Shader component3() {
      return this.aoShader;
   }

   @NotNull
   public final Shader component4() {
      return this.flatShader;
   }

   @NotNull
   public final Vertex copy(@NotNull Double3 xyz, @NotNull UV uv, @NotNull Shader aoShader, @NotNull Shader flatShader) {
      Intrinsics.checkParameterIsNotNull(xyz, "xyz");
      Intrinsics.checkParameterIsNotNull(uv, "uv");
      Intrinsics.checkParameterIsNotNull(aoShader, "aoShader");
      Intrinsics.checkParameterIsNotNull(flatShader, "flatShader");
      return new Vertex(xyz, uv, aoShader, flatShader);
   }

   public String toString() {
      return "Vertex(xyz=" + this.xyz + ", uv=" + this.uv + ", aoShader=" + this.aoShader + ", flatShader=" + this.flatShader + ")";
   }

   public int hashCode() {
      return (((this.xyz != null ? this.xyz.hashCode() : 0) * 31 + (this.uv != null ? this.uv.hashCode() : 0)) * 31 + (this.aoShader != null ? this.aoShader.hashCode() : 0)) * 31 + (this.flatShader != null ? this.flatShader.hashCode() : 0);
   }

   public boolean equals(Object var1) {
      if (this != var1) {
         if (var1 instanceof Vertex) {
            Vertex var2 = (Vertex)var1;
            if (Intrinsics.areEqual(this.xyz, var2.xyz) && Intrinsics.areEqual(this.uv, var2.uv) && Intrinsics.areEqual(this.aoShader, var2.aoShader) && Intrinsics.areEqual(this.flatShader, var2.flatShader)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
