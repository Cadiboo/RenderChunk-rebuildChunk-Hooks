package mods.betterfoliage.client.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import mods.betterfoliage.client.texture.GrassInfo;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.ShadingContext;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0002\b\b"},
   d2 = {"<anonymous>", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "<anonymous parameter 0>", "Lmods/octarinecore/client/render/ShadingContext;", "<anonymous parameter 1>", "", "<anonymous parameter 2>", "Lmods/octarinecore/client/render/Quad;", "invoke"}
)
final class RenderGrass$render$$inlined$renderAs$lambda$5 extends Lambda implements Function3 {
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;
   // $FF: synthetic field
   final List $isHidden$inlined;
   // $FF: synthetic field
   final GrassInfo $grassInfo$inlined;
   // $FF: synthetic field
   final boolean $isSnowed$inlined;
   // $FF: synthetic field
   final int $blockColor$inlined;

   RenderGrass$render$$inlined$renderAs$lambda$5(BufferBuilder var1, List var2, GrassInfo var3, boolean var4, int var5) {
      super(3);
      this.$renderer$inlined = var1;
      this.$isHidden$inlined = var2;
      this.$grassInfo$inlined = var3;
      this.$isSnowed$inlined = var4;
      this.$blockColor$inlined = var5;
   }

   @NotNull
   public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int $noName_1, @NotNull Quad $noName_2) {
      Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
      Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
      return this.$grassInfo$inlined.getGrassTopTexture();
   }
}
