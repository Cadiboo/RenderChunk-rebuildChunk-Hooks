package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.config.Config;
import mods.octarinecore.client.render.BlockContext;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0006R\u0014\u0010\u000e\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0006R\u0014\u0010\u0010\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0006R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00040\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000b¨\u0006!"},
   d2 = {"Lmods/betterfoliage/client/render/RenderLog;", "Lmods/betterfoliage/client/render/AbstractRenderColumn;", "()V", "addToCutout", "", "getAddToCutout", "()Z", "blockPredicate", "Lkotlin/Function1;", "Lnet/minecraft/block/state/IBlockState;", "getBlockPredicate", "()Lkotlin/jvm/functions/Function1;", "connectPerpendicular", "getConnectPerpendicular", "connectSolids", "getConnectSolids", "lenientConnect", "getLenientConnect", "radiusLarge", "", "getRadiusLarge", "()D", "radiusSmall", "getRadiusSmall", "registry", "Lmods/betterfoliage/client/render/IColumnRegistry;", "getRegistry", "()Lmods/betterfoliage/client/render/IColumnRegistry;", "surroundPredicate", "getSurroundPredicate", "isEligible", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "BetterFoliage-MC1.12"}
)
public final class RenderLog extends AbstractRenderColumn {
   @NotNull
   private final Function1 blockPredicate;
   @NotNull
   private final Function1 surroundPredicate;

   public boolean getAddToCutout() {
      return false;
   }

   public boolean isEligible(@NotNull BlockContext ctx) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      return Config.INSTANCE.getEnabled() && Config.roundLogs.INSTANCE.getEnabled() && ctx.getCameraDistance() < Config.roundLogs.INSTANCE.getDistance() && Config.blocks.INSTANCE.getLogClasses().matchesClass(ctx.getBlock());
   }

   @NotNull
   public IColumnRegistry getRegistry() {
      return (IColumnRegistry)LogRegistry.INSTANCE;
   }

   @NotNull
   public Function1 getBlockPredicate() {
      return this.blockPredicate;
   }

   @NotNull
   public Function1 getSurroundPredicate() {
      return this.surroundPredicate;
   }

   public boolean getConnectPerpendicular() {
      return Config.roundLogs.INSTANCE.getConnectPerpendicular();
   }

   public boolean getConnectSolids() {
      return Config.roundLogs.INSTANCE.getConnectSolids();
   }

   public boolean getLenientConnect() {
      return Config.roundLogs.INSTANCE.getLenientConnect();
   }

   public double getRadiusLarge() {
      return Config.roundLogs.INSTANCE.getRadiusLarge();
   }

   public double getRadiusSmall() {
      return Config.roundLogs.INSTANCE.getRadiusSmall();
   }

   public RenderLog() {
      super("betterfoliage");
      this.blockPredicate = (Function1)null.INSTANCE;
      this.surroundPredicate = (Function1)null.INSTANCE;
   }
}
