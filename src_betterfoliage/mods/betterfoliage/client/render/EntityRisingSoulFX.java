package mods.betterfoliage.client.render;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.AbstractEntityFX;
import mods.octarinecore.common.Double3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"},
   d2 = {"Lmods/betterfoliage/client/render/EntityRisingSoulFX;", "Lmods/octarinecore/client/render/AbstractEntityFX;", "world", "Lnet/minecraft/world/World;", "pos", "Lnet/minecraft/util/math/BlockPos;", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V", "initialPhase", "", "getInitialPhase", "()I", "isValid", "", "()Z", "particleTrail", "Ljava/util/Deque;", "Lmods/octarinecore/common/Double3;", "getParticleTrail", "()Ljava/util/Deque;", "render", "", "worldRenderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "partialTickTime", "", "update", "BetterFoliage-MC1.12"}
)
public final class EntityRisingSoulFX extends AbstractEntityFX {
   @NotNull
   private final Deque particleTrail;
   private final int initialPhase;

   @NotNull
   public final Deque getParticleTrail() {
      return this.particleTrail;
   }

   public final int getInitialPhase() {
      return this.initialPhase;
   }

   public boolean isValid() {
      return true;
   }

   public void update() {
      int phase = (this.initialPhase + this.field_70546_d) % 64;
      this.getVelocity().setTo(AbstractEntityFX.Companion.getCos()[phase].doubleValue() * Config.risingSoul.INSTANCE.getPerturb(), 0.1D, AbstractEntityFX.Companion.getSin()[phase].doubleValue() * Config.risingSoul.INSTANCE.getPerturb());
      this.particleTrail.addFirst(Double3.copy$default(this.getCurrentPos(), 0.0D, 0.0D, 0.0D, 7, (Object)null));

      while(this.particleTrail.size() > Config.risingSoul.INSTANCE.getTrailLength()) {
         this.particleTrail.removeLast();
      }

      if (!Config.INSTANCE.getEnabled()) {
         this.func_187112_i();
      }

   }

   public void render(@NotNull BufferBuilder worldRenderer, float partialTickTime) {
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      float alpha = Config.risingSoul.INSTANCE.getOpacity();
      if (this.field_70546_d > this.field_70547_e - 40) {
         alpha *= (float)(this.field_70547_e - this.field_70546_d) / 40.0F;
      }

      AbstractEntityFX.renderParticleQuad$default(this, worldRenderer, partialTickTime, (Double3)null, (Double3)null, Config.risingSoul.INSTANCE.getHeadSize() * 0.25D, 0, (TextureAtlasSprite)null, false, alpha, 236, (Object)null);
      double scale = Config.risingSoul.INSTANCE.getTrailSize() * 0.25D;
      Iterable $receiver$iv = (Iterable)this.particleTrail;
      Object previous$iv = null;
      int index$iv$iv = 0;

      Object item$iv$iv;
      for(Iterator var8 = $receiver$iv.iterator(); var8.hasNext(); previous$iv = item$iv$iv) {
         item$iv$iv = var8.next();
         int idx$iv = index$iv$iv++;
         if (previous$iv != null) {
            Double3 previous = (Double3)previous$iv;
            Double3 current = (Double3)item$iv$iv;
            scale *= Config.risingSoul.INSTANCE.getSizeDecay();
            alpha *= Config.risingSoul.INSTANCE.getOpacityDecay();
            if (idx$iv % Config.risingSoul.INSTANCE.getTrailDensity() == 0) {
               Intrinsics.checkExpressionValueIsNotNull(current, "current");
               TextureAtlasSprite var10009 = RisingSoulTextures.INSTANCE.getTrackIcon().getIcon();
               if (var10009 == null) {
                  Intrinsics.throwNpe();
               }

               TextureAtlasSprite var15 = var10009;
               boolean var16 = false;
               AbstractEntityFX.renderParticleQuad$default(this, worldRenderer, partialTickTime, current, previous, scale, 0, var15, var16, alpha, 160, (Object)null);
            }
         }
      }

   }

   public EntityRisingSoulFX(@NotNull World world, @NotNull BlockPos pos) {
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      super(world, (double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 1.0D, (double)pos.func_177952_p() + 0.5D);
      this.particleTrail = (Deque)(new LinkedList());
      this.initialPhase = this.field_187136_p.nextInt(64);
      this.field_187130_j = 0.1D;
      this.field_70545_g = 0.0F;
      this.field_187119_C = RisingSoulTextures.INSTANCE.getHeadIcons().get(this.field_187136_p.nextInt(256));
      this.field_70547_e = MathHelper.func_76128_c((0.6D + 0.4D * this.field_187136_p.nextDouble()) * Config.risingSoul.INSTANCE.getLifetime() * 20.0D);
   }
}
