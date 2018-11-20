package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.IntRef;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.ShadingContext;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0002\b\b"},
   d2 = {"<anonymous>", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "<anonymous parameter 0>", "Lmods/octarinecore/client/render/ShadingContext;", "qi", "", "<anonymous parameter 2>", "Lmods/octarinecore/client/render/Quad;", "invoke"}
)
final class RenderCoral$render$$inlined$forEachIndexed$lambda$1 extends Lambda implements Function3 {
   // $FF: synthetic field
   final IntRef $variation;
   // $FF: synthetic field
   final RenderCoral this$0;
   // $FF: synthetic field
   final BlockContext $ctx$inlined;
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;

   RenderCoral$render$$inlined$forEachIndexed$lambda$1(IntRef var1, RenderCoral var2, BlockContext var3, BufferBuilder var4) {
      super(3);
      this.$variation = var1;
      this.this$0 = var2;
      this.$ctx$inlined = var3;
      this.$renderer$inlined = var4;
   }

   @Nullable
   public final TextureAtlasSprite invoke(@NotNull ShadingContext $noName_0, int qi, @NotNull Quad $noName_2) {
      Intrinsics.checkParameterIsNotNull($noName_0, "<anonymous parameter 0>");
      Intrinsics.checkParameterIsNotNull($noName_2, "<anonymous parameter 2>");
      TextureAtlasSprite var10000;
      if (qi == 4) {
         var10000 = this.this$0.getCrustIcons().get(this.$variation.element);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }
      } else {
         var10000 = this.this$0.getCoralIcons().get(this.$variation.element + (qi & 1));
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }
      }

      return var10000;
   }
}
