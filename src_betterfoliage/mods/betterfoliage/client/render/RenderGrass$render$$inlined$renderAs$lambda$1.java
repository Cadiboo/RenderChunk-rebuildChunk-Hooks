package mods.betterfoliage.client.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import mods.betterfoliage.client.texture.GrassInfo;
import mods.octarinecore.client.render.Quad;
import net.minecraft.client.renderer.BufferBuilder;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"},
   d2 = {"<anonymous>", "", "qi", "", "<anonymous parameter 1>", "Lmods/octarinecore/client/render/Quad;", "invoke"}
)
final class RenderGrass$render$$inlined$renderAs$lambda$1 extends Lambda implements Function2 {
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

   RenderGrass$render$$inlined$renderAs$lambda$1(BufferBuilder var1, List var2, GrassInfo var3, boolean var4, int var5) {
      super(2);
      this.$renderer$inlined = var1;
      this.$isHidden$inlined = var2;
      this.$grassInfo$inlined = var3;
      this.$isSnowed$inlined = var4;
      this.$blockColor$inlined = var5;
   }

   public final boolean invoke(int qi, @NotNull Quad $noName_1) {
      Intrinsics.checkParameterIsNotNull($noName_1, "<anonymous parameter 1>");
      return !((Boolean)this.$isHidden$inlined.get(qi)).booleanValue();
   }
}
