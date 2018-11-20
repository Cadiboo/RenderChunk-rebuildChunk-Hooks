package mods.betterfoliage.client.texture;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.resource.IconSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"},
   d2 = {"Lmods/betterfoliage/client/texture/LeafInfo;", "", "roundLeafTexture", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "leafType", "", "averageColor", "", "(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Ljava/lang/String;I)V", "getAverageColor", "()I", "getLeafType", "()Ljava/lang/String;", "particleTextures", "Lmods/octarinecore/client/resource/IconSet;", "getParticleTextures", "()Lmods/octarinecore/client/resource/IconSet;", "getRoundLeafTexture", "()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "BetterFoliage-MC1.12"}
)
public final class LeafInfo {
   @NotNull
   private final TextureAtlasSprite roundLeafTexture;
   @NotNull
   private final String leafType;
   private final int averageColor;

   @Nullable
   public final IconSet getParticleTextures() {
      return (IconSet)LeafRegistry.INSTANCE.getParticles().get(this.leafType);
   }

   @NotNull
   public final TextureAtlasSprite getRoundLeafTexture() {
      return this.roundLeafTexture;
   }

   @NotNull
   public final String getLeafType() {
      return this.leafType;
   }

   public final int getAverageColor() {
      return this.averageColor;
   }

   public LeafInfo(@NotNull TextureAtlasSprite roundLeafTexture, @NotNull String leafType, int averageColor) {
      Intrinsics.checkParameterIsNotNull(roundLeafTexture, "roundLeafTexture");
      Intrinsics.checkParameterIsNotNull(leafType, "leafType");
      super();
      this.roundLeafTexture = roundLeafTexture;
      this.leafType = leafType;
      this.averageColor = averageColor;
   }

   // $FF: synthetic method
   public LeafInfo(TextureAtlasSprite var1, String var2, int var3, int var4, DefaultConstructorMarker var5) {
      if ((var4 & 4) != 0) {
         Integer var10000 = mods.octarinecore.client.resource.Utils.getAverageColor(var1);
         var3 = var10000 != null ? var10000.intValue() : 0;
      }

      this(var1, var2, var3);
   }
}
