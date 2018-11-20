package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import mods.octarinecore.client.render.BlockContext;
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
   d2 = {"<anonymous>", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "ctx", "Lmods/octarinecore/client/render/ShadingContext;", "qi", "", "q", "Lmods/octarinecore/client/render/Quad;", "invoke"}
)
final class RenderLilypad$render$$inlined$grass$lambda$2 extends Lambda implements Function3 {
   // $FF: synthetic field
   final RenderLilypad this$0;
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;
   // $FF: synthetic field
   final BlockContext $ctx$inlined;
   // $FF: synthetic field
   final Integer[] $rand$inlined;

   RenderLilypad$render$$inlined$grass$lambda$2(RenderLilypad var1, BufferBuilder var2, BlockContext var3, Integer[] var4) {
      super(3);
      this.this$0 = var1;
      this.$renderer$inlined = var2;
      this.$ctx$inlined = var3;
      this.$rand$inlined = var4;
   }

   @NotNull
   public final TextureAtlasSprite invoke(@NotNull ShadingContext ctx, int qi, @NotNull Quad q) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(q, "q");
      TextureAtlasSprite var10000 = this.this$0.getRootIcon().get(this.$rand$inlined[qi & 1].intValue());
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }
}
