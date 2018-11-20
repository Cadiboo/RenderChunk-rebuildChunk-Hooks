package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.texture.LeafInfo;
import mods.betterfoliage.client.texture.LeafRegistry;
import mods.octarinecore.client.render.AbstractEntityFX;
import mods.octarinecore.client.render.HSB;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.common.Double3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0007\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\fJ\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u0019H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\tR\u0014\u0010\n\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\t\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\t\"\u0004\b\u0017\u0010\u0014¨\u0006#"},
   d2 = {"Lmods/betterfoliage/client/render/EntityFallingLeavesFX;", "Lmods/octarinecore/client/render/AbstractEntityFX;", "world", "Lnet/minecraft/world/World;", "pos", "Lnet/minecraft/util/math/BlockPos;", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", "isMirrored", "", "()Z", "isValid", "particleRot", "", "getParticleRot", "()I", "setParticleRot", "(I)V", "rotPositive", "getRotPositive", "setRotPositive", "(Z)V", "wasCollided", "getWasCollided", "setWasCollided", "calculateParticleColor", "", "textureAvgColor", "blockColor", "render", "worldRenderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "partialTickTime", "", "update", "Companion", "BetterFoliage-MC1.12"}
)
public final class EntityFallingLeavesFX extends AbstractEntityFX {
   private int particleRot;
   private boolean rotPositive;
   private final boolean isMirrored;
   private boolean wasCollided;
   private static final float biomeBrightnessMultiplier = 0.5F;
   public static final EntityFallingLeavesFX.Companion Companion = new EntityFallingLeavesFX.Companion((DefaultConstructorMarker)null);

   public final int getParticleRot() {
      return this.particleRot;
   }

   public final void setParticleRot(int var1) {
      this.particleRot = var1;
   }

   public final boolean getRotPositive() {
      return this.rotPositive;
   }

   public final void setRotPositive(boolean var1) {
      this.rotPositive = var1;
   }

   public final boolean isMirrored() {
      return this.isMirrored;
   }

   public final boolean getWasCollided() {
      return this.wasCollided;
   }

   public final void setWasCollided(boolean var1) {
      this.wasCollided = var1;
   }

   public boolean isValid() {
      return this.field_187119_C != null;
   }

   public void update() {
      if (this.field_187136_p.nextFloat() > 0.95F) {
         this.rotPositive = !this.rotPositive;
      }

      if (this.field_70546_d > this.field_70547_e - 20) {
         this.field_82339_as = 0.05F * (float)(this.field_70547_e - this.field_70546_d);
      }

      if (!this.field_187132_l && !this.wasCollided) {
         this.getVelocity().setTo(AbstractEntityFX.Companion.getCos()[this.particleRot].doubleValue(), 0.0D, AbstractEntityFX.Companion.getSin()[this.particleRot].doubleValue()).mul(Config.fallingLeaves.INSTANCE.getPerturb()).add(LeafWindTracker.INSTANCE.getCurrent()).add(0.0D, -1.0D, 0.0D).mul(Config.fallingLeaves.INSTANCE.getSpeed());
         this.particleRot = this.particleRot + (this.rotPositive ? 1 : -1) & 63;
      } else {
         this.getVelocity().setTo(0.0D, 0.0D, 0.0D);
         if (!this.wasCollided) {
            this.field_70546_d = Math.max(this.field_70546_d, this.field_70547_e - 20);
            this.wasCollided = true;
         }
      }

   }

   public void render(@NotNull BufferBuilder worldRenderer, float partialTickTime) {
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      if (Config.fallingLeaves.INSTANCE.getOpacityHack()) {
         GL11.glDepthMask(true);
      }

      AbstractEntityFX.renderParticleQuad$default(this, worldRenderer, partialTickTime, (Double3)null, (Double3)null, 0.0D, this.particleRot, (TextureAtlasSprite)null, this.isMirrored, 0.0F, 348, (Object)null);
   }

   public final void calculateParticleColor(int textureAvgColor, int blockColor) {
      HSB texture = HSB.Companion.fromColor(textureAvgColor);
      HSB block = HSB.Companion.fromColor(blockColor);
      float weightTex = texture.getSaturation() / (texture.getSaturation() + block.getSaturation());
      float weightBlock = 1.0F - weightTex;
      HSB particle = new HSB(weightTex * texture.getHue() + weightBlock * block.getHue(), weightTex * texture.getSaturation() + weightBlock * block.getSaturation(), weightTex * texture.getBrightness() + weightBlock * block.getBrightness() * Companion.getBiomeBrightnessMultiplier());
      this.setColor(particle.getAsColor());
   }

   public EntityFallingLeavesFX(@NotNull World world, @NotNull BlockPos pos) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      super(world, (double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o(), (double)pos.func_177952_p() + 0.5D);
      this.particleRot = this.field_187136_p.nextInt(64);
      this.rotPositive = true;
      this.isMirrored = (this.field_187136_p.nextInt() & 1) == 1;
      this.field_70547_e = MathHelper.func_76128_c(mods.octarinecore.Utils.random(0.6D, 1.0D) * Config.fallingLeaves.INSTANCE.getLifetime() * 20.0D);
      this.field_187130_j = -Config.fallingLeaves.INSTANCE.getSpeed();
      this.field_70544_f = (float)Config.fallingLeaves.INSTANCE.getSize() * 0.1F;
      IBlockState state = world.func_180495_p(pos);
      Minecraft var10000 = Minecraft.func_71410_x();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Minecraft.getMinecraft()");
      int blockColor = var10000.func_184125_al().func_186724_a(state, (IBlockAccess)world, pos, 0);
      LeafRegistry var6 = LeafRegistry.INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(state, "state");
      LeafInfo leafInfo = var6.get(state, (IBlockAccess)world, pos, EnumFacing.DOWN, mods.octarinecore.Utils.semiRandom(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), 0));
      IconSet var10001;
      if (leafInfo != null) {
         var10001 = leafInfo.getParticleTextures();
         this.field_187119_C = var10001 != null ? var10001.get(this.field_187136_p.nextInt(1024)) : null;
         this.calculateParticleColor(leafInfo.getAverageColor(), blockColor);
      } else {
         var10001 = (IconSet)LeafRegistry.INSTANCE.getParticles().get("default");
         this.field_187119_C = var10001 != null ? var10001.get(this.field_187136_p.nextInt(1024)) : null;
         this.setColor(blockColor);
      }

   }

   public static final float getBiomeBrightnessMultiplier() {
      return Companion.getBiomeBrightnessMultiplier();
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087D¢\u0006\u000e\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
      d2 = {"Lmods/betterfoliage/client/render/EntityFallingLeavesFX$Companion;", "", "()V", "biomeBrightnessMultiplier", "", "biomeBrightnessMultiplier$annotations", "getBiomeBrightnessMultiplier", "()F", "BetterFoliage-MC1.12"}
   )
   public static final class Companion {
      /** @deprecated */
      // $FF: synthetic method
      @JvmStatic
      public static void biomeBrightnessMultiplier$annotations() {
      }

      public final float getBiomeBrightnessMultiplier() {
         return EntityFallingLeavesFX.biomeBrightnessMultiplier;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
