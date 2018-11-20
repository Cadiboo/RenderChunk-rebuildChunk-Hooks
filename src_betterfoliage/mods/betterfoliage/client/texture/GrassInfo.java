package mods.betterfoliage.client.texture;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\f"},
   d2 = {"Lmods/betterfoliage/client/texture/GrassInfo;", "", "grassTopTexture", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "overrideColor", "", "(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Ljava/lang/Integer;)V", "getGrassTopTexture", "()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getOverrideColor", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "BetterFoliage-MC1.12"}
)
public final class GrassInfo {
   @NotNull
   private final TextureAtlasSprite grassTopTexture;
   @Nullable
   private final Integer overrideColor;

   @NotNull
   public final TextureAtlasSprite getGrassTopTexture() {
      return this.grassTopTexture;
   }

   @Nullable
   public final Integer getOverrideColor() {
      return this.overrideColor;
   }

   public GrassInfo(@NotNull TextureAtlasSprite grassTopTexture, @Nullable Integer overrideColor) {
      Intrinsics.checkParameterIsNotNull(grassTopTexture, "grassTopTexture");
      super();
      this.grassTopTexture = grassTopTexture;
      this.overrideColor = overrideColor;
   }
}
