package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
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
   d2 = {"<anonymous>", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "<anonymous parameter 0>", "Lmods/octarinecore/client/render/ShadingContext;", "qi", "", "<anonymous parameter 2>", "Lmods/octarinecore/client/render/Quad;", "invoke"}
)
final class RenderAlgae$render$$inlined$grass$lambda$2 extends Lambda implements Function3 {
   // $FF: synthetic field
   final RenderAlgae this$0;
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;
   // $FF: synthetic field
   final Integer[] $rand$inlined;

   RenderAlgae$render$$inlined$grass$lambda$2(RenderAlgae var1, BufferBuilder var2, Integer[] var3) {
      super(3);
      this.this$0 = var1;
      this.$renderer$inlined = var2;
      this.$rand$inlined = var3;
   }

   @NotNull
   public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int qi, @NotNull Quad $noName_2) {
      Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
      Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
      TextureAtlasSprite var10000 = this.this$0.getAlgaeIcons().get(this.$rand$inlined[qi & 1].intValue());
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }
}
