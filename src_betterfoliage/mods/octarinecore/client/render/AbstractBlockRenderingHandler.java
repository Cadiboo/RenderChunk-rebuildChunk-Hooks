package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.render.Utils;
import mods.octarinecore.client.resource.ResourceHandler;
import mods.octarinecore.common.Int3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH&J(\u0010\f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J(\u0010\u0013\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"},
   d2 = {"Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "Lmods/octarinecore/client/resource/ResourceHandler;", "modId", "", "(Ljava/lang/String;)V", "addToCutout", "", "getAddToCutout", "()Z", "isEligible", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "render", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "renderWorldBlockBase", "BetterFoliage-MC1.12"}
)
public abstract class AbstractBlockRenderingHandler extends ResourceHandler {
   public boolean getAddToCutout() {
      return true;
   }

   public abstract boolean isEligible(@NotNull BlockContext var1);

   public abstract boolean render(@NotNull BlockContext var1, @NotNull BlockRendererDispatcher var2, @NotNull BufferBuilder var3, @NotNull BlockRenderLayer var4);

   public final boolean renderWorldBlockBase(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @Nullable BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      IBlockState var5 = ctx.blockState(Int3.Companion.getZero());
      if (layer != null) {
         Intrinsics.checkExpressionValueIsNotNull(var5, "state");
         if (!Utils.canRenderInLayer(var5, layer) && (!Utils.canRenderInCutout(var5) || !Utils.isCutout(layer))) {
            return false;
         }
      }

      return dispatcher.func_175018_a(var5, ctx.getPos(), ctx.getWorld(), renderer);
   }

   public AbstractBlockRenderingHandler(@NotNull String modId) {
      Intrinsics.checkParameterIsNotNull(modId, "modId");
      super(modId);
   }
}
