package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.BooleanRef;
import mods.octarinecore.client.render.Quad;
import mods.octarinecore.client.render.RenderVertex;
import mods.octarinecore.client.render.ShadingContext;
import mods.octarinecore.client.render.Vertex;
import mods.octarinecore.common.Rotation;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing.Axis;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\n¢\u0006\u0002\b\f"},
   d2 = {"<anonymous>", "", "Lmods/octarinecore/client/render/RenderVertex;", "<anonymous parameter 0>", "Lmods/octarinecore/client/render/ShadingContext;", "<anonymous parameter 1>", "", "<anonymous parameter 2>", "Lmods/octarinecore/client/render/Quad;", "<anonymous parameter 3>", "<anonymous parameter 4>", "Lmods/octarinecore/client/render/Vertex;", "invoke"}
)
final class AbstractRenderColumn$render$$inlined$renderAs$lambda$1 extends Lambda implements Function6 {
   // $FF: synthetic field
   final BooleanRef $isLidUp;
   // $FF: synthetic field
   final int $idx;
   // $FF: synthetic field
   final AbstractRenderColumn this$0;
   // $FF: synthetic field
   final Rotation $baseRotation$inlined;
   // $FF: synthetic field
   final AbstractRenderColumn.QuadrantType[] $quadrants$inlined;
   // $FF: synthetic field
   final AbstractRenderColumn.BlockType $upType$inlined;
   // $FF: synthetic field
   final AbstractRenderColumn.QuadrantType[] $quadrantsTop$inlined;
   // $FF: synthetic field
   final AbstractRenderColumn.BlockType $downType$inlined;
   // $FF: synthetic field
   final AbstractRenderColumn.QuadrantType[] $quadrantsBottom$inlined;
   // $FF: synthetic field
   final BufferBuilder $renderer$inlined;
   // $FF: synthetic field
   final IColumnTextureInfo $columnTextures$inlined;
   // $FF: synthetic field
   final Axis $logAxis$inlined;

   AbstractRenderColumn$render$$inlined$renderAs$lambda$1(BooleanRef var1, int var2, AbstractRenderColumn var3, Rotation var4, AbstractRenderColumn.QuadrantType[] var5, AbstractRenderColumn.BlockType var6, AbstractRenderColumn.QuadrantType[] var7, AbstractRenderColumn.BlockType var8, AbstractRenderColumn.QuadrantType[] var9, BufferBuilder var10, IColumnTextureInfo var11, Axis var12) {
      super(6);
      this.$isLidUp = var1;
      this.$idx = var2;
      this.this$0 = var3;
      this.$baseRotation$inlined = var4;
      this.$quadrants$inlined = var5;
      this.$upType$inlined = var6;
      this.$quadrantsTop$inlined = var7;
      this.$downType$inlined = var8;
      this.$quadrantsBottom$inlined = var9;
      this.$renderer$inlined = var10;
      this.$columnTextures$inlined = var11;
      this.$logAxis$inlined = var12;
   }

   public final void invoke(@NotNull RenderVertex $receiver, @NotNull ShadingContext $noName_1, int $noName_2, @NotNull Quad $noName_3, int $noName_4, @NotNull Vertex $noName_5) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull($noName_1, "<anonymous parameter 0>");
      Intrinsics.checkParameterIsNotNull($noName_3, "<anonymous parameter 2>");
      Intrinsics.checkParameterIsNotNull($noName_5, "<anonymous parameter 4>");
      if (this.$isLidUp.element) {
         int n$iv = this.$idx + (Intrinsics.areEqual(this.$logAxis$inlined, Axis.X) ? 1 : 0);
         double t$iv;
         switch(n$iv % 4) {
         case 1:
            t$iv = $receiver.getV();
            $receiver.setV(-$receiver.getU());
            $receiver.setU(t$iv);
            break;
         case 2:
            $receiver.setU(-$receiver.getU());
            $receiver.setV(-$receiver.getV());
            break;
         case 3:
            t$iv = -$receiver.getV();
            $receiver.setV($receiver.getU());
            $receiver.setU(t$iv);
         }
      }

   }
}
