package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.common.Double3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000 42\u00020\u0001:\u00014B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0019H\u0016J\u0018\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H&JH\u0010\"\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010 \u001a\u00020!2\u0006\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020!H\u0016J\\\u0010*\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\b\b\u0002\u0010\u000e\u001a\u00020\u000b2\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010+\u001a\u00020\u00052\b\b\u0002\u0010,\u001a\u00020\u001b2\b\b\u0002\u0010-\u001a\u00020.2\b\b\u0002\u0010/\u001a\u00020\u00122\b\b\u0002\u00100\u001a\u00020!J\u000e\u00101\u001a\u00020\u00192\u0006\u00102\u001a\u00020\u001bJ\b\u00103\u001a\u00020\u0019H&R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\u0016\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010¨\u00065"},
   d2 = {"Lmods/octarinecore/client/render/AbstractEntityFX;", "Lnet/minecraft/client/particle/Particle;", "world", "Lnet/minecraft/world/World;", "x", "", "y", "z", "(Lnet/minecraft/world/World;DDD)V", "billboardRot", "Lkotlin/Pair;", "Lmods/octarinecore/common/Double3;", "getBillboardRot", "()Lkotlin/Pair;", "currentPos", "getCurrentPos", "()Lmods/octarinecore/common/Double3;", "isValid", "", "()Z", "prevPos", "getPrevPos", "velocity", "getVelocity", "addIfValid", "", "getFXLayer", "", "onUpdate", "render", "worldRenderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "partialTickTime", "", "renderParticle", "entity", "Lnet/minecraft/entity/Entity;", "rotX", "rotZ", "rotYZ", "rotXY", "rotXZ", "renderParticleQuad", "size", "rotation", "icon", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "isMirrored", "alpha", "setColor", "color", "update", "Companion", "BetterFoliage-MC1.12"}
)
public abstract class AbstractEntityFX extends Particle {
   @NotNull
   private final Pair billboardRot;
   @NotNull
   private final Double3 currentPos;
   @NotNull
   private final Double3 prevPos;
   @NotNull
   private final Double3 velocity;
   @NotNull
   private static final Double[] sin;
   @NotNull
   private static final Double[] cos;
   public static final AbstractEntityFX.Companion Companion = new AbstractEntityFX.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final Pair getBillboardRot() {
      return this.billboardRot;
   }

   @NotNull
   public final Double3 getCurrentPos() {
      return this.currentPos;
   }

   @NotNull
   public final Double3 getPrevPos() {
      return this.prevPos;
   }

   @NotNull
   public final Double3 getVelocity() {
      return this.velocity;
   }

   public void func_189213_a() {
      super.func_189213_a();
      this.currentPos.setTo(this.field_187126_f, this.field_187127_g, this.field_187128_h);
      this.prevPos.setTo(this.field_187123_c, this.field_187124_d, this.field_187125_e);
      this.velocity.setTo(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.update();
      this.field_187126_f = this.currentPos.getX();
      this.field_187127_g = this.currentPos.getY();
      this.field_187128_h = this.currentPos.getZ();
      this.field_187129_i = this.velocity.getX();
      this.field_187130_j = this.velocity.getY();
      this.field_187131_k = this.velocity.getZ();
   }

   public abstract void render(@NotNull BufferBuilder var1, float var2);

   public abstract void update();

   public abstract boolean isValid();

   public final void addIfValid() {
      if (this.isValid()) {
         Minecraft.func_71410_x().field_71452_i.func_78873_a((Particle)this);
      }

   }

   public void func_180434_a(@NotNull BufferBuilder worldRenderer, @NotNull Entity entity, float partialTickTime, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      Intrinsics.checkParameterIsNotNull(entity, "entity");
      ((Double3)this.billboardRot.getFirst()).setTo(rotX + rotXY, rotZ, rotYZ + rotXZ);
      ((Double3)this.billboardRot.getSecond()).setTo(rotX - rotXY, -rotZ, rotYZ - rotXZ);
      this.render(worldRenderer, partialTickTime);
   }

   public final void renderParticleQuad(@NotNull BufferBuilder worldRenderer, float partialTickTime, @NotNull Double3 currentPos, @NotNull Double3 prevPos, double size, int rotation, @NotNull TextureAtlasSprite icon, boolean isMirrored, float alpha) {
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      Intrinsics.checkParameterIsNotNull(currentPos, "currentPos");
      Intrinsics.checkParameterIsNotNull(prevPos, "prevPos");
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      double minU = (double)(isMirrored ? icon.func_94209_e() : icon.func_94212_f());
      double maxU = (double)(isMirrored ? icon.func_94212_f() : icon.func_94209_e());
      double minV = (double)icon.func_94206_g();
      double maxV = (double)icon.func_94210_h();
      Double3 center = Double3.copy$default(currentPos, 0.0D, 0.0D, 0.0D, 7, (Object)null).sub(prevPos).mul((double)partialTickTime).add(prevPos).sub(Particle.field_70556_an, Particle.field_70554_ao, Particle.field_70555_ap);
      Double3 v1 = rotation == 0 ? ((Double3)this.billboardRot.getFirst()).times(size) : Double3.Companion.weight((Double3)this.billboardRot.getFirst(), Companion.getCos()[rotation & 63].doubleValue() * size, (Double3)this.billboardRot.getSecond(), Companion.getSin()[rotation & 63].doubleValue() * size);
      Double3 v2 = rotation == 0 ? ((Double3)this.billboardRot.getSecond()).times(size) : Double3.Companion.weight((Double3)this.billboardRot.getFirst(), -Companion.getSin()[rotation & 63].doubleValue() * size, (Double3)this.billboardRot.getSecond(), Companion.getCos()[rotation & 63].doubleValue() * size);
      int renderBrightness = this.func_189214_a(partialTickTime);
      int brLow = renderBrightness >> 16 & '\uffff';
      int brHigh = renderBrightness & '\uffff';
      worldRenderer.func_181662_b(center.getX() - v1.getX(), center.getY() - v1.getY(), center.getZ() - v1.getZ()).func_187315_a(maxU, maxV).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_187314_a(brLow, brHigh).func_181675_d();
      worldRenderer.func_181662_b(center.getX() - v2.getX(), center.getY() - v2.getY(), center.getZ() - v2.getZ()).func_187315_a(maxU, minV).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_187314_a(brLow, brHigh).func_181675_d();
      worldRenderer.func_181662_b(center.getX() + v1.getX(), center.getY() + v1.getY(), center.getZ() + v1.getZ()).func_187315_a(minU, minV).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_187314_a(brLow, brHigh).func_181675_d();
      worldRenderer.func_181662_b(center.getX() + v2.getX(), center.getY() + v2.getY(), center.getZ() + v2.getZ()).func_187315_a(minU, maxV).func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, alpha).func_187314_a(brLow, brHigh).func_181675_d();
   }

   public int func_70537_b() {
      return 1;
   }

   public final void setColor(int color) {
      this.field_70551_j = (float)(color & 255) / 256.0F;
      this.field_70553_i = (float)(color >> 8 & 255) / 256.0F;
      this.field_70552_h = (float)(color >> 16 & 255) / 256.0F;
   }

   public AbstractEntityFX(@NotNull World world, double x, double y, double z) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      super(world, x, y, z);
      this.billboardRot = new Pair(Double3.Companion.getZero(), Double3.Companion.getZero());
      this.currentPos = Double3.Companion.getZero();
      this.prevPos = Double3.Companion.getZero();
      this.velocity = Double3.Companion.getZero();
   }

   static {
      int size$iv = 64;
      Object[] result$iv = new Double[size$iv];
      int i$iv = 0;

      int var3;
      Double var9;
      for(var3 = result$iv.length; i$iv < var3; ++i$iv) {
         var9 = Math.sin(0.09817477042468103D * (double)i$iv);
         result$iv[i$iv] = var9;
      }

      sin = result$iv;
      size$iv = 64;
      result$iv = new Double[size$iv];
      i$iv = 0;

      for(var3 = result$iv.length; i$iv < var3; ++i$iv) {
         var9 = Math.cos(0.09817477042468103D * (double)i$iv);
         result$iv[i$iv] = var9;
      }

      cos = result$iv;
   }

   @NotNull
   public static final Double[] getSin() {
      return Companion.getSin();
   }

   @NotNull
   public static final Double[] getCos() {
      return Companion.getCos();
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0006\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0010\n\u0002\u0010\t\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR$\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0010\n\u0002\u0010\t\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\b¨\u0006\r"},
      d2 = {"Lmods/octarinecore/client/render/AbstractEntityFX$Companion;", "", "()V", "cos", "", "", "cos$annotations", "getCos", "()[Ljava/lang/Double;", "[Ljava/lang/Double;", "sin", "sin$annotations", "getSin", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      /** @deprecated */
      // $FF: synthetic method
      @JvmStatic
      public static void sin$annotations() {
      }

      @NotNull
      public final Double[] getSin() {
         return AbstractEntityFX.sin;
      }

      /** @deprecated */
      // $FF: synthetic method
      @JvmStatic
      public static void cos$annotations() {
      }

      @NotNull
      public final Double[] getCos() {
         return AbstractEntityFX.cos;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
