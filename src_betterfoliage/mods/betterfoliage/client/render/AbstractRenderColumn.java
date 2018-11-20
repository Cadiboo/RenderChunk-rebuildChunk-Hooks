package mods.betterfoliage.client.render;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.BooleanRef;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.config.Config;
import mods.betterfoliage.client.integration.ShadersModIntegration;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.client.render.AbstractBlockRenderingHandler;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.render.Model;
import mods.octarinecore.client.render.ModelRenderer;
import mods.octarinecore.client.render.ModelRendererKt;
import mods.octarinecore.client.render.RendererHolder;
import mods.octarinecore.client.resource.ModelHolder;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import mods.octarinecore.common.Rotation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b'\u0018\u00002\u00020\u0001:\u0002lmB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010H\u001a\u00020\b2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020JH\u0086\bJ\u0013\u0010L\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020JH\u0086\bJ\u0013\u0010O\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020JH\u0086\bJ\u0013\u0010P\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020JH\u0086\bJ\u0013\u0010Q\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020JH\u0086\bJ(\u0010R\u001a\u00020\b2\u0006\u0010S\u001a\u00020T2\u0006\u0010U\u001a\u00020V2\u0006\u0010W\u001a\u00020X2\u0006\u0010Y\u001a\u00020ZH\u0017J\"\u0010[\u001a\u00020\\*\u00020T2\u0006\u0010]\u001a\u00020(2\u0006\u0010^\u001a\u00020_2\u0006\u0010`\u001a\u00020aJ;\u0010b\u001a\b\u0012\u0004\u0012\u00020J0'*\b\u0012\u0004\u0012\u00020J0'2\u0006\u0010S\u001a\u00020T2\u0006\u0010]\u001a\u00020(2\u0006\u0010c\u001a\u00020_2\u0006\u0010d\u001a\u00020e¢\u0006\u0002\u0010fJ(\u0010g\u001a\u00020h*\b\u0012\u0004\u0012\u00020J0'2\u0006\u0010i\u001a\u00020e2\u0006\u0010j\u001a\u00020JH\u0086\b¢\u0006\u0002\u0010kR\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0012\u0010\u0013\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0018\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000eR\u0011\u0010\u001a\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000eR\u0011\u0010\u001c\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000eR\u0011\u0010\u001e\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000eR\u0011\u0010 \u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000eR\u0011\u0010\"\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000eR\u0012\u0010$\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u0015R\u0019\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'¢\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u0012\u0010,\u001a\u00020-X¦\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/R\u0012\u00100\u001a\u00020-X¦\u0004¢\u0006\u0006\u001a\u0004\b1\u0010/R\u0012\u00102\u001a\u000203X¦\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0011\u00106\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b7\u0010\u000eR\u0011\u00108\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b9\u0010\u000eR\u0011\u0010:\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u000eR\u001e\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b=\u0010\nR\u0011\u0010>\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u000eR\u0011\u0010@\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u000eR\u0011\u0010B\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u000eR\u0011\u0010D\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\u000eR\u0011\u0010F\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\u000e¨\u0006n"},
   d2 = {"Lmods/betterfoliage/client/render/AbstractRenderColumn;", "Lmods/octarinecore/client/render/AbstractBlockRenderingHandler;", "modId", "", "(Ljava/lang/String;)V", "blockPredicate", "Lkotlin/Function1;", "Lnet/minecraft/block/state/IBlockState;", "", "getBlockPredicate", "()Lkotlin/jvm/functions/Function1;", "bottomRoundLarge", "Lmods/octarinecore/client/resource/ModelHolder;", "getBottomRoundLarge", "()Lmods/octarinecore/client/resource/ModelHolder;", "bottomRoundSmall", "getBottomRoundSmall", "bottomSquare", "getBottomSquare", "connectPerpendicular", "getConnectPerpendicular", "()Z", "connectSolids", "getConnectSolids", "extendBottomRoundLarge", "getExtendBottomRoundLarge", "extendBottomRoundSmall", "getExtendBottomRoundSmall", "extendBottomSquare", "getExtendBottomSquare", "extendTopRoundLarge", "getExtendTopRoundLarge", "extendTopRoundSmall", "getExtendTopRoundSmall", "extendTopSquare", "getExtendTopSquare", "lenientConnect", "getLenientConnect", "quadrantRotations", "", "Lmods/octarinecore/common/Rotation;", "getQuadrantRotations", "()[Lmods/octarinecore/common/Rotation;", "[Lmods/octarinecore/common/Rotation;", "radiusLarge", "", "getRadiusLarge", "()D", "radiusSmall", "getRadiusSmall", "registry", "Lmods/betterfoliage/client/render/IColumnRegistry;", "getRegistry", "()Lmods/betterfoliage/client/render/IColumnRegistry;", "sideRoundLarge", "getSideRoundLarge", "sideRoundSmall", "getSideRoundSmall", "sideSquare", "getSideSquare", "surroundPredicate", "getSurroundPredicate", "topRoundLarge", "getTopRoundLarge", "topRoundSmall", "getTopRoundSmall", "topSquare", "getTopSquare", "transitionBottom", "getTransitionBottom", "transitionTop", "getTransitionTop", "continuous", "q1", "Lmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;", "q2", "extendBottom", "Lmods/octarinecore/client/render/Model;", "type", "extendTop", "flatBottom", "flatTop", "render", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "dispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", "renderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "layer", "Lnet/minecraft/util/BlockRenderLayer;", "blockType", "Lmods/betterfoliage/client/render/AbstractRenderColumn$BlockType;", "rotation", "axis", "Lnet/minecraft/util/EnumFacing$Axis;", "offset", "Lmods/octarinecore/common/Int3;", "checkNeighbors", "logAxis", "yOff", "", "([Lmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;Lmods/octarinecore/client/render/BlockContext;Lmods/octarinecore/common/Rotation;Lnet/minecraft/util/EnumFacing$Axis;I)[Lmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;", "upgrade", "", "idx", "value", "([Lmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;ILmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;)V", "BlockType", "QuadrantType", "BetterFoliage-MC1.12"}
)
public abstract class AbstractRenderColumn extends AbstractBlockRenderingHandler {
   @NotNull
   private final Rotation[] quadrantRotations;
   @NotNull
   private final ModelHolder sideSquare;
   @NotNull
   private final ModelHolder sideRoundSmall;
   @NotNull
   private final ModelHolder sideRoundLarge;
   @NotNull
   private final ModelHolder extendTopSquare;
   @NotNull
   private final ModelHolder extendTopRoundSmall;
   @NotNull
   private final ModelHolder extendTopRoundLarge;
   @NotNull
   private final ModelHolder extendBottomSquare;
   @NotNull
   private final ModelHolder extendBottomRoundSmall;
   @NotNull
   private final ModelHolder extendBottomRoundLarge;
   @NotNull
   private final ModelHolder topSquare;
   @NotNull
   private final ModelHolder topRoundSmall;
   @NotNull
   private final ModelHolder topRoundLarge;
   @NotNull
   private final ModelHolder bottomSquare;
   @NotNull
   private final ModelHolder bottomRoundSmall;
   @NotNull
   private final ModelHolder bottomRoundLarge;
   @NotNull
   private final ModelHolder transitionTop;
   @NotNull
   private final ModelHolder transitionBottom;

   @NotNull
   public final Rotation[] getQuadrantRotations() {
      return this.quadrantRotations;
   }

   public abstract double getRadiusSmall();

   public abstract double getRadiusLarge();

   @NotNull
   public abstract Function1 getSurroundPredicate();

   public abstract boolean getConnectPerpendicular();

   public abstract boolean getConnectSolids();

   public abstract boolean getLenientConnect();

   @NotNull
   public final ModelHolder getSideSquare() {
      return this.sideSquare;
   }

   @NotNull
   public final ModelHolder getSideRoundSmall() {
      return this.sideRoundSmall;
   }

   @NotNull
   public final ModelHolder getSideRoundLarge() {
      return this.sideRoundLarge;
   }

   @NotNull
   public final ModelHolder getExtendTopSquare() {
      return this.extendTopSquare;
   }

   @NotNull
   public final ModelHolder getExtendTopRoundSmall() {
      return this.extendTopRoundSmall;
   }

   @NotNull
   public final ModelHolder getExtendTopRoundLarge() {
      return this.extendTopRoundLarge;
   }

   @Nullable
   public final Model extendTop(@NotNull AbstractRenderColumn.QuadrantType type) {
      Intrinsics.checkParameterIsNotNull(type, "type");
      Model var10000;
      switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
      case 1:
         var10000 = this.getExtendTopRoundSmall().getModel();
         break;
      case 2:
         var10000 = this.getExtendTopRoundLarge().getModel();
         break;
      case 3:
         var10000 = this.getExtendTopSquare().getModel();
         break;
      case 4:
         var10000 = this.getExtendTopSquare().getModel();
         break;
      default:
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public final ModelHolder getExtendBottomSquare() {
      return this.extendBottomSquare;
   }

   @NotNull
   public final ModelHolder getExtendBottomRoundSmall() {
      return this.extendBottomRoundSmall;
   }

   @NotNull
   public final ModelHolder getExtendBottomRoundLarge() {
      return this.extendBottomRoundLarge;
   }

   @Nullable
   public final Model extendBottom(@NotNull AbstractRenderColumn.QuadrantType type) {
      Intrinsics.checkParameterIsNotNull(type, "type");
      Model var10000;
      switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$1[type.ordinal()]) {
      case 1:
         var10000 = this.getExtendBottomRoundSmall().getModel();
         break;
      case 2:
         var10000 = this.getExtendBottomRoundLarge().getModel();
         break;
      case 3:
         var10000 = this.getExtendBottomSquare().getModel();
         break;
      case 4:
         var10000 = this.getExtendBottomSquare().getModel();
         break;
      default:
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public final ModelHolder getTopSquare() {
      return this.topSquare;
   }

   @NotNull
   public final ModelHolder getTopRoundSmall() {
      return this.topRoundSmall;
   }

   @NotNull
   public final ModelHolder getTopRoundLarge() {
      return this.topRoundLarge;
   }

   @Nullable
   public final Model flatTop(@NotNull AbstractRenderColumn.QuadrantType type) {
      Intrinsics.checkParameterIsNotNull(type, "type");
      Model var10000;
      switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$2[type.ordinal()]) {
      case 1:
         var10000 = this.getTopRoundSmall().getModel();
         break;
      case 2:
         var10000 = this.getTopRoundLarge().getModel();
         break;
      case 3:
         var10000 = this.getTopSquare().getModel();
         break;
      case 4:
         var10000 = this.getTopSquare().getModel();
         break;
      default:
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public final ModelHolder getBottomSquare() {
      return this.bottomSquare;
   }

   @NotNull
   public final ModelHolder getBottomRoundSmall() {
      return this.bottomRoundSmall;
   }

   @NotNull
   public final ModelHolder getBottomRoundLarge() {
      return this.bottomRoundLarge;
   }

   @Nullable
   public final Model flatBottom(@NotNull AbstractRenderColumn.QuadrantType type) {
      Intrinsics.checkParameterIsNotNull(type, "type");
      Model var10000;
      switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$3[type.ordinal()]) {
      case 1:
         var10000 = this.getBottomRoundSmall().getModel();
         break;
      case 2:
         var10000 = this.getBottomRoundLarge().getModel();
         break;
      case 3:
         var10000 = this.getBottomSquare().getModel();
         break;
      case 4:
         var10000 = this.getBottomSquare().getModel();
         break;
      default:
         var10000 = null;
      }

      return var10000;
   }

   @NotNull
   public final ModelHolder getTransitionTop() {
      return this.transitionTop;
   }

   @NotNull
   public final ModelHolder getTransitionBottom() {
      return this.transitionBottom;
   }

   public final boolean continuous(@NotNull AbstractRenderColumn.QuadrantType q1, @NotNull AbstractRenderColumn.QuadrantType q2) {
      Intrinsics.checkParameterIsNotNull(q1, "q1");
      Intrinsics.checkParameterIsNotNull(q2, "q2");
      return Intrinsics.areEqual(q1, q2) || (Intrinsics.areEqual(q1, AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(q1, AbstractRenderColumn.QuadrantType.INVISIBLE)) && (Intrinsics.areEqual(q2, AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(q2, AbstractRenderColumn.QuadrantType.INVISIBLE));
   }

   @NotNull
   public abstract Function1 getBlockPredicate();

   @NotNull
   public abstract IColumnRegistry getRegistry();

   public boolean render(@NotNull BlockContext ctx, @NotNull BlockRendererDispatcher dispatcher, @NotNull BufferBuilder renderer, @NotNull BlockRenderLayer layer) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
      Intrinsics.checkParameterIsNotNull(renderer, "renderer");
      Intrinsics.checkParameterIsNotNull(layer, "layer");
      if (ctx.isSurroundedBy(this.getSurroundPredicate())) {
         return false;
      } else {
         IColumnRegistry var10000 = this.getRegistry();
         IBlockState var10001 = ctx.blockState(Int3.Companion.getZero());
         Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
         IColumnTextureInfo columnTextures = var10000.get(var10001, ctx.random(0));
         if (columnTextures == null) {
            Client var61 = Client.INSTANCE;
            var10001 = ctx.blockState(Int3.Companion.getZero());
            Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
            BlockPos var10002 = ctx.getPos();
            Intrinsics.checkExpressionValueIsNotNull(var10002, "ctx.pos");
            var61.logRenderError(var10001, var10002);
            return this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
         } else {
            RendererHolder.getModelRenderer().updateShading(Int3.Companion.getZero(), ModelRendererKt.getAllFaces());
            Axis var56 = columnTextures.getAxis();
            if (var56 == null) {
               if (!Config.roundLogs.INSTANCE.getDefaultY()) {
                  return this.renderWorldBlockBase(ctx, dispatcher, renderer, (BlockRenderLayer)null);
               }

               var56 = Axis.Y;
            }

            Axis logAxis = var56;
            Rotation baseRotation = Utils.getRotationFromUp()[GeometryKt.getFace(TuplesKt.to(logAxis, AxisDirection.POSITIVE)).ordinal()];
            AbstractRenderColumn.BlockType upType = this.blockType(ctx, baseRotation, logAxis, new Int3(0, 1, 0));
            AbstractRenderColumn.BlockType downType = this.blockType(ctx, baseRotation, logAxis, new Int3(0, -1, 0));
            int size$iv = 4;
            Object[] quadrantsBottom = new AbstractRenderColumn.QuadrantType[size$iv];
            int i$iv = 0;

            int i$iv;
            for(i$iv = quadrantsBottom.length; i$iv < i$iv; ++i$iv) {
               AbstractRenderColumn.QuadrantType var47 = AbstractRenderColumn.QuadrantType.SMALL_RADIUS;
               quadrantsBottom[i$iv] = var47;
            }

            AbstractRenderColumn.QuadrantType[] quadrants = this.checkNeighbors(quadrantsBottom, ctx, baseRotation, logAxis, 0);
            int size$iv = 4;
            Object[] result$iv = new AbstractRenderColumn.QuadrantType[size$iv];
            i$iv = 0;

            int i$iv;
            AbstractRenderColumn.QuadrantType var46;
            for(i$iv = result$iv.length; i$iv < i$iv; ++i$iv) {
               var46 = AbstractRenderColumn.QuadrantType.SMALL_RADIUS;
               result$iv[i$iv] = var46;
            }

            AbstractRenderColumn.QuadrantType[] quadrantsTop = result$iv;
            if (Intrinsics.areEqual(upType, AbstractRenderColumn.BlockType.PARALLEL)) {
               this.checkNeighbors(result$iv, ctx, baseRotation, logAxis, 1);
            }

            int size$iv = 4;
            Object[] result$iv = new AbstractRenderColumn.QuadrantType[size$iv];
            i$iv = 0;

            for(int var16 = result$iv.length; i$iv < var16; ++i$iv) {
               var46 = AbstractRenderColumn.QuadrantType.SMALL_RADIUS;
               result$iv[i$iv] = var46;
            }

            quadrantsBottom = result$iv;
            if (Intrinsics.areEqual(downType, AbstractRenderColumn.BlockType.PARALLEL)) {
               this.checkNeighbors(result$iv, ctx, baseRotation, logAxis, -1);
            }

            ShadersModIntegration this_$iv = ShadersModIntegration.INSTANCE;
            IBlockState var57 = ctx.blockState(Int3.Companion.getZero());
            Intrinsics.checkExpressionValueIsNotNull(var57, "ctx.blockState(Int3.zero)");
            IBlockState state$iv = var57;
            boolean enabled$iv = true;
            long blockEntityData$iv$iv = this_$iv.entityDataFor(state$iv);
            Object[] $receiver$iv;
            int index$iv;
            int var23;
            int var24;
            Object item$iv;
            Rotation quadrantRotation;
            int idx;
            Rotation rotation;
            Model sideModel;
            Model upModel;
            Model downModel;
            Function3 upIcon;
            Function3 downIcon;
            BooleanRef isLidUp;
            BooleanRef isLidDown;
            AbstractRenderColumn.QuadrantType type$iv;
            AbstractRenderColumn.QuadrantType q2$iv;
            int var59;
            Model var60;
            if (ShadersModIntegration.isPresent()) {
               Object var58 = Refs.INSTANCE.getSVertexBuilder().get(renderer);
               if (var58 == null) {
                  Intrinsics.throwNpe();
               }

               Object vertexBuilder$iv$iv = var58;
               Refs.INSTANCE.getPushEntity_num().invoke(vertexBuilder$iv$iv, blockEntityData$iv$iv);
               $receiver$iv = (Object[])this.quadrantRotations;
               index$iv = 0;
               var23 = $receiver$iv.length;

               for(var24 = 0; var24 < var23; ++var24) {
                  item$iv = $receiver$iv[var24];
                  var59 = index$iv++;
                  quadrantRotation = (Rotation)item$iv;
                  idx = var59;
                  rotation = baseRotation.plus(quadrantRotation);
                  if (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) && Intrinsics.areEqual(upType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsTop[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) ^ true && Intrinsics.areEqual(downType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsBottom[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) ^ true) {
                     quadrants[idx] = AbstractRenderColumn.QuadrantType.SMALL_RADIUS;
                  }

                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$4[quadrants[idx].ordinal()]) {
                  case 1:
                     var60 = this.sideRoundSmall.getModel();
                     break;
                  case 2:
                     if (Intrinsics.areEqual(upType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsTop[idx], AbstractRenderColumn.QuadrantType.SMALL_RADIUS)) {
                        var60 = this.transitionTop.getModel();
                     } else {
                        if (Intrinsics.areEqual(downType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsBottom[idx], AbstractRenderColumn.QuadrantType.SMALL_RADIUS)) {
                           var60 = this.transitionBottom.getModel();
                           break;
                        }

                        var60 = this.sideRoundLarge.getModel();
                     }
                     break;
                  case 3:
                     var60 = this.sideSquare.getModel();
                     break;
                  default:
                     var60 = null;
                  }

                  sideModel = var60;
                  if (sideModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, sideModel, rotation, (Double3)null, false, (Function2)null, columnTextures.getSide(), ModelRendererKt.getNoPost(), 56, (Object)null);
                  }

                  upModel = (Model)null;
                  downModel = (Model)null;
                  upIcon = columnTextures.getTop();
                  downIcon = columnTextures.getBottom();
                  isLidUp = new BooleanRef();
                  isLidUp.element = true;
                  isLidDown = new BooleanRef();
                  isLidDown.element = true;
                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$5[upType.ordinal()]) {
                  case 1:
                     type$iv = quadrants[idx];
                     switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$2[type$iv.ordinal()]) {
                     case 1:
                        var60 = this.getTopRoundSmall().getModel();
                        break;
                     case 2:
                        var60 = this.getTopRoundLarge().getModel();
                        break;
                     case 3:
                        var60 = this.getTopSquare().getModel();
                        break;
                     case 4:
                        var60 = this.getTopSquare().getModel();
                        break;
                     default:
                        var60 = null;
                     }

                     upModel = var60;
                     break;
                  case 2:
                     if (!this.getConnectPerpendicular()) {
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$2[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getTopRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getTopRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getTopSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getTopSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        upModel = var60;
                     } else {
                        upIcon = columnTextures.getSide();
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$0[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getExtendTopRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getExtendTopRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getExtendTopSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getExtendTopSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        upModel = var60;
                        isLidUp.element = false;
                     }
                     break;
                  case 3:
                     type$iv = quadrants[idx];
                     q2$iv = quadrantsTop[idx];
                     if (!Intrinsics.areEqual(type$iv, q2$iv) && (!Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.INVISIBLE) || !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.INVISIBLE)) && (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.INVISIBLE))) {
                        upModel = this.topSquare.getModel();
                     }
                  }

                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$6[downType.ordinal()]) {
                  case 1:
                     type$iv = quadrants[idx];
                     switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$3[type$iv.ordinal()]) {
                     case 1:
                        var60 = this.getBottomRoundSmall().getModel();
                        break;
                     case 2:
                        var60 = this.getBottomRoundLarge().getModel();
                        break;
                     case 3:
                        var60 = this.getBottomSquare().getModel();
                        break;
                     case 4:
                        var60 = this.getBottomSquare().getModel();
                        break;
                     default:
                        var60 = null;
                     }

                     downModel = var60;
                     break;
                  case 2:
                     if (!this.getConnectPerpendicular()) {
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$3[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getBottomRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getBottomRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getBottomSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getBottomSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        downModel = var60;
                     } else {
                        downIcon = columnTextures.getSide();
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$1[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getExtendBottomRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getExtendBottomRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getExtendBottomSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getExtendBottomSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        downModel = var60;
                        isLidDown.element = false;
                     }
                     break;
                  case 3:
                     type$iv = quadrants[idx];
                     q2$iv = quadrantsBottom[idx];
                     if (!Intrinsics.areEqual(type$iv, q2$iv) && (!Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.INVISIBLE) || !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.INVISIBLE)) && (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.INVISIBLE))) {
                        downModel = this.bottomSquare.getModel();
                     }
                  }

                  if (upModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, upModel, rotation, (Double3)null, false, (Function2)null, upIcon, (Function6)(new AbstractRenderColumn$render$$inlined$renderAs$lambda$1(isLidUp, idx, this, baseRotation, quadrants, upType, quadrantsTop, downType, quadrantsBottom, renderer, columnTextures, logAxis)), 56, (Object)null);
                  }

                  if (downModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, downModel, rotation, (Double3)null, false, (Function2)null, downIcon, (Function6)(new AbstractRenderColumn$render$$inlined$renderAs$lambda$2(isLidDown, idx, this, baseRotation, quadrants, upType, quadrantsTop, downType, quadrantsBottom, renderer, columnTextures, logAxis)), 56, (Object)null);
                  }
               }

               Refs.INSTANCE.getPopEntity().invoke(vertexBuilder$iv$iv);
            } else {
               $receiver$iv = (Object[])this.quadrantRotations;
               index$iv = 0;
               var23 = $receiver$iv.length;

               for(var24 = 0; var24 < var23; ++var24) {
                  item$iv = $receiver$iv[var24];
                  var59 = index$iv++;
                  quadrantRotation = (Rotation)item$iv;
                  idx = var59;
                  rotation = baseRotation.plus(quadrantRotation);
                  if (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) && Intrinsics.areEqual(upType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsTop[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) ^ true && Intrinsics.areEqual(downType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsBottom[idx], AbstractRenderColumn.QuadrantType.LARGE_RADIUS) ^ true) {
                     quadrants[idx] = AbstractRenderColumn.QuadrantType.SMALL_RADIUS;
                  }

                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$4[quadrants[idx].ordinal()]) {
                  case 1:
                     var60 = this.sideRoundSmall.getModel();
                     break;
                  case 2:
                     if (Intrinsics.areEqual(upType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsTop[idx], AbstractRenderColumn.QuadrantType.SMALL_RADIUS)) {
                        var60 = this.transitionTop.getModel();
                     } else {
                        if (Intrinsics.areEqual(downType, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(quadrantsBottom[idx], AbstractRenderColumn.QuadrantType.SMALL_RADIUS)) {
                           var60 = this.transitionBottom.getModel();
                           break;
                        }

                        var60 = this.sideRoundLarge.getModel();
                     }
                     break;
                  case 3:
                     var60 = this.sideSquare.getModel();
                     break;
                  default:
                     var60 = null;
                  }

                  sideModel = var60;
                  if (sideModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, sideModel, rotation, (Double3)null, false, (Function2)null, columnTextures.getSide(), ModelRendererKt.getNoPost(), 56, (Object)null);
                  }

                  upModel = (Model)null;
                  downModel = (Model)null;
                  upIcon = columnTextures.getTop();
                  downIcon = columnTextures.getBottom();
                  isLidUp = new BooleanRef();
                  isLidUp.element = true;
                  isLidDown = new BooleanRef();
                  isLidDown.element = true;
                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$5[upType.ordinal()]) {
                  case 1:
                     type$iv = quadrants[idx];
                     switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$2[type$iv.ordinal()]) {
                     case 1:
                        var60 = this.getTopRoundSmall().getModel();
                        break;
                     case 2:
                        var60 = this.getTopRoundLarge().getModel();
                        break;
                     case 3:
                        var60 = this.getTopSquare().getModel();
                        break;
                     case 4:
                        var60 = this.getTopSquare().getModel();
                        break;
                     default:
                        var60 = null;
                     }

                     upModel = var60;
                     break;
                  case 2:
                     if (!this.getConnectPerpendicular()) {
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$2[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getTopRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getTopRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getTopSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getTopSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        upModel = var60;
                     } else {
                        upIcon = columnTextures.getSide();
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$0[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getExtendTopRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getExtendTopRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getExtendTopSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getExtendTopSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        upModel = var60;
                        isLidUp.element = false;
                     }
                     break;
                  case 3:
                     type$iv = quadrants[idx];
                     q2$iv = quadrantsTop[idx];
                     if (!Intrinsics.areEqual(type$iv, q2$iv) && (!Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.INVISIBLE) || !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.INVISIBLE)) && (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.INVISIBLE))) {
                        upModel = this.topSquare.getModel();
                     }
                  }

                  switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$6[downType.ordinal()]) {
                  case 1:
                     type$iv = quadrants[idx];
                     switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$3[type$iv.ordinal()]) {
                     case 1:
                        var60 = this.getBottomRoundSmall().getModel();
                        break;
                     case 2:
                        var60 = this.getBottomRoundLarge().getModel();
                        break;
                     case 3:
                        var60 = this.getBottomSquare().getModel();
                        break;
                     case 4:
                        var60 = this.getBottomSquare().getModel();
                        break;
                     default:
                        var60 = null;
                     }

                     downModel = var60;
                     break;
                  case 2:
                     if (!this.getConnectPerpendicular()) {
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$3[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getBottomRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getBottomRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getBottomSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getBottomSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        downModel = var60;
                     } else {
                        downIcon = columnTextures.getSide();
                        type$iv = quadrants[idx];
                        switch(AbstractRenderColumn$WhenMappings.$EnumSwitchMapping$1[type$iv.ordinal()]) {
                        case 1:
                           var60 = this.getExtendBottomRoundSmall().getModel();
                           break;
                        case 2:
                           var60 = this.getExtendBottomRoundLarge().getModel();
                           break;
                        case 3:
                           var60 = this.getExtendBottomSquare().getModel();
                           break;
                        case 4:
                           var60 = this.getExtendBottomSquare().getModel();
                           break;
                        default:
                           var60 = null;
                        }

                        downModel = var60;
                        isLidDown.element = false;
                     }
                     break;
                  case 3:
                     type$iv = quadrants[idx];
                     q2$iv = quadrantsBottom[idx];
                     if (!Intrinsics.areEqual(type$iv, q2$iv) && (!Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(type$iv, AbstractRenderColumn.QuadrantType.INVISIBLE) || !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.SQUARE) && !Intrinsics.areEqual(q2$iv, AbstractRenderColumn.QuadrantType.INVISIBLE)) && (Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.SQUARE) || Intrinsics.areEqual(quadrants[idx], AbstractRenderColumn.QuadrantType.INVISIBLE))) {
                        downModel = this.bottomSquare.getModel();
                     }
                  }

                  if (upModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, upModel, rotation, (Double3)null, false, (Function2)null, upIcon, (Function6)(new AbstractRenderColumn$render$$inlined$renderAs$lambda$3(isLidUp, idx, this, baseRotation, quadrants, upType, quadrantsTop, downType, quadrantsBottom, renderer, columnTextures, logAxis)), 56, (Object)null);
                  }

                  if (downModel != null) {
                     ModelRenderer.render$default(RendererHolder.getModelRenderer(), renderer, downModel, rotation, (Double3)null, false, (Function2)null, downIcon, (Function6)(new AbstractRenderColumn$render$$inlined$renderAs$lambda$4(isLidDown, idx, this, baseRotation, quadrants, upType, quadrantsTop, downType, quadrantsBottom, renderer, columnTextures, logAxis)), 56, (Object)null);
                  }
               }
            }

            return true;
         }
      }
   }

   public final void upgrade(@NotNull AbstractRenderColumn.QuadrantType[] $receiver, int idx, @NotNull AbstractRenderColumn.QuadrantType value) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(value, "value");
      if ($receiver[idx].ordinal() < value.ordinal()) {
         $receiver[idx] = value;
      }

   }

   @NotNull
   public final AbstractRenderColumn.QuadrantType[] checkNeighbors(@NotNull AbstractRenderColumn.QuadrantType[] $receiver, @NotNull BlockContext ctx, @NotNull Rotation rotation, @NotNull Axis logAxis, int yOff) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(rotation, "rotation");
      Intrinsics.checkParameterIsNotNull(logAxis, "logAxis");
      AbstractRenderColumn.BlockType blkS = this.blockType(ctx, rotation, logAxis, new Int3(0, yOff, 1));
      AbstractRenderColumn.BlockType blkE = this.blockType(ctx, rotation, logAxis, new Int3(1, yOff, 0));
      AbstractRenderColumn.BlockType blkN = this.blockType(ctx, rotation, logAxis, new Int3(0, yOff, -1));
      AbstractRenderColumn.BlockType blkW = this.blockType(ctx, rotation, logAxis, new Int3(-1, yOff, 0));
      if (this.getConnectSolids()) {
         byte idx$iv;
         AbstractRenderColumn.QuadrantType value$iv;
         if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 3;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 0;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 0;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 1;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 1;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 2;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 2;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 3;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.SOLID) && Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 0;
            value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.SOLID) && Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 1;
            value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.SOLID) && Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 2;
            value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.SOLID) && Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.SOLID)) {
            idx$iv = 3;
            value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }
      }

      AbstractRenderColumn.BlockType blkSE = this.blockType(ctx, rotation, logAxis, new Int3(1, yOff, 1));
      AbstractRenderColumn.BlockType blkNE = this.blockType(ctx, rotation, logAxis, new Int3(1, yOff, -1));
      AbstractRenderColumn.BlockType blkNW = this.blockType(ctx, rotation, logAxis, new Int3(-1, yOff, -1));
      AbstractRenderColumn.BlockType blkSW = this.blockType(ctx, rotation, logAxis, new Int3(-1, yOff, 1));
      byte idx$iv;
      AbstractRenderColumn.QuadrantType value$iv;
      if (this.getLenientConnect()) {
         if (Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.PARALLEL) && (Intrinsics.areEqual(blkSE, AbstractRenderColumn.BlockType.PARALLEL) || Intrinsics.areEqual(blkNE, AbstractRenderColumn.BlockType.PARALLEL))) {
            idx$iv = 0;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 1;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.PARALLEL) && (Intrinsics.areEqual(blkNE, AbstractRenderColumn.BlockType.PARALLEL) || Intrinsics.areEqual(blkNW, AbstractRenderColumn.BlockType.PARALLEL))) {
            idx$iv = 1;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 2;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.PARALLEL) && (Intrinsics.areEqual(blkNW, AbstractRenderColumn.BlockType.PARALLEL) || Intrinsics.areEqual(blkSW, AbstractRenderColumn.BlockType.PARALLEL))) {
            idx$iv = 2;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 3;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }

         if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.PARALLEL) && (Intrinsics.areEqual(blkSE, AbstractRenderColumn.BlockType.PARALLEL) || Intrinsics.areEqual(blkSW, AbstractRenderColumn.BlockType.PARALLEL))) {
            idx$iv = 3;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }

            idx$iv = 0;
            value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
            if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
               $receiver[idx$iv] = value$iv;
            }
         }
      }

      if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.PARALLEL) && (this.getLenientConnect() || Intrinsics.areEqual(blkNW, AbstractRenderColumn.BlockType.PARALLEL))) {
         idx$iv = 0;
         value$iv = AbstractRenderColumn.QuadrantType.LARGE_RADIUS;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 1;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 3;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 2;
         value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }
      }

      if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(blkW, AbstractRenderColumn.BlockType.PARALLEL) && (this.getLenientConnect() || Intrinsics.areEqual(blkSW, AbstractRenderColumn.BlockType.PARALLEL))) {
         idx$iv = 1;
         value$iv = AbstractRenderColumn.QuadrantType.LARGE_RADIUS;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 0;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 2;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 3;
         value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }
      }

      if (Intrinsics.areEqual(blkS, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.PARALLEL) && (this.getLenientConnect() || Intrinsics.areEqual(blkSE, AbstractRenderColumn.BlockType.PARALLEL))) {
         idx$iv = 2;
         value$iv = AbstractRenderColumn.QuadrantType.LARGE_RADIUS;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 1;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 3;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 0;
         value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }
      }

      if (Intrinsics.areEqual(blkN, AbstractRenderColumn.BlockType.PARALLEL) && Intrinsics.areEqual(blkE, AbstractRenderColumn.BlockType.PARALLEL) && (this.getLenientConnect() || Intrinsics.areEqual(blkNE, AbstractRenderColumn.BlockType.PARALLEL))) {
         idx$iv = 3;
         value$iv = AbstractRenderColumn.QuadrantType.LARGE_RADIUS;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 0;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 2;
         value$iv = AbstractRenderColumn.QuadrantType.SQUARE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }

         idx$iv = 1;
         value$iv = AbstractRenderColumn.QuadrantType.INVISIBLE;
         if ($receiver[idx$iv].ordinal() < value$iv.ordinal()) {
            $receiver[idx$iv] = value$iv;
         }
      }

      return $receiver;
   }

   @NotNull
   public final AbstractRenderColumn.BlockType blockType(@NotNull BlockContext $receiver, @NotNull Rotation rotation, @NotNull Axis axis, @NotNull Int3 offset) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      Intrinsics.checkParameterIsNotNull(rotation, "rotation");
      Intrinsics.checkParameterIsNotNull(axis, "axis");
      Intrinsics.checkParameterIsNotNull(offset, "offset");
      Int3 offsetRot = offset.rotate(rotation);
      IBlockState state = $receiver.blockState(offsetRot);
      Function1 var10000 = this.getBlockPredicate();
      Intrinsics.checkExpressionValueIsNotNull(state, "state");
      AbstractRenderColumn.BlockType var10;
      if (!((Boolean)var10000.invoke(state)).booleanValue()) {
         var10 = state.func_185914_p() ? AbstractRenderColumn.BlockType.SOLID : AbstractRenderColumn.BlockType.NONSOLID;
      } else {
         Axis var12;
         label34: {
            IColumnTextureInfo var11 = this.getRegistry().get(state, $receiver.random(0));
            if (var11 != null) {
               var12 = var11.getAxis();
               if (var12 != null) {
                  break label34;
               }
            }

            var12 = Config.roundLogs.INSTANCE.getDefaultY() ? Axis.Y : null;
         }

         if (var12 != null) {
            Axis var7 = var12;
            var10 = Intrinsics.areEqual(var7, axis) ? AbstractRenderColumn.BlockType.PARALLEL : AbstractRenderColumn.BlockType.PERPENDICULAR;
            if (var10 != null) {
               return var10;
            }
         }

         var10 = AbstractRenderColumn.BlockType.SOLID;
      }

      return var10;
   }

   public AbstractRenderColumn(@NotNull String modId) {
      Intrinsics.checkParameterIsNotNull(modId, "modId");
      super(modId);
      int size$iv = 4;
      Object[] result$iv = new Rotation[size$iv];
      int i$iv = 0;

      for(int var5 = result$iv.length; i$iv < var5; ++i$iv) {
         Rotation var12 = Rotation.Companion.getRot90()[EnumFacing.UP.ordinal()].times(i$iv);
         result$iv[i$iv] = var12;
      }

      this.quadrantRotations = result$iv;
      this.sideSquare = this.model((Function1)null.INSTANCE);
      this.sideRoundSmall = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide$default($receiver, AbstractRenderColumn.this.getRadiusSmall(), -0.5D, 0.5D, (Function1)null, 8, (Object)null);
         }
      }));
      this.sideRoundLarge = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide$default($receiver, AbstractRenderColumn.this.getRadiusLarge(), -0.5D, 0.5D, (Function1)null, 8, (Object)null);
         }
      }));
      this.extendTopSquare = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSideSquare($receiver, 0.5D, 0.5D + AbstractRenderColumn.this.getRadiusLarge(), ModelColumn.topExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.extendTopRoundSmall = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide($receiver, AbstractRenderColumn.this.getRadiusSmall(), 0.5D, 0.5D + AbstractRenderColumn.this.getRadiusLarge(), ModelColumn.topExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.extendTopRoundLarge = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide($receiver, AbstractRenderColumn.this.getRadiusLarge(), 0.5D, 0.5D + AbstractRenderColumn.this.getRadiusLarge(), ModelColumn.topExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.extendBottomSquare = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSideSquare($receiver, -0.5D - AbstractRenderColumn.this.getRadiusLarge(), -0.5D, ModelColumn.bottomExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.extendBottomRoundSmall = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide($receiver, AbstractRenderColumn.this.getRadiusSmall(), -0.5D - AbstractRenderColumn.this.getRadiusLarge(), -0.5D, ModelColumn.bottomExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.extendBottomRoundLarge = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnSide($receiver, AbstractRenderColumn.this.getRadiusLarge(), -0.5D - AbstractRenderColumn.this.getRadiusLarge(), -0.5D, ModelColumn.bottomExtension(AbstractRenderColumn.this.getRadiusLarge()));
         }
      }));
      this.topSquare = this.model((Function1)null.INSTANCE);
      this.topRoundSmall = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnLid$default($receiver, AbstractRenderColumn.this.getRadiusSmall(), (Function1)null, 2, (Object)null);
         }
      }));
      this.topRoundLarge = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnLid$default($receiver, AbstractRenderColumn.this.getRadiusLarge(), (Function1)null, 2, (Object)null);
         }
      }));
      this.bottomSquare = this.model((Function1)null.INSTANCE);
      this.bottomRoundSmall = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnLid($receiver, AbstractRenderColumn.this.getRadiusSmall(), (Function1)null.INSTANCE);
         }
      }));
      this.bottomRoundLarge = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            ModelColumn.columnLid($receiver, AbstractRenderColumn.this.getRadiusLarge(), (Function1)null.INSTANCE);
         }
      }));
      this.transitionTop = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            Utils.mix($receiver, AbstractRenderColumn.this.getSideRoundLarge().getModel(), AbstractRenderColumn.this.getSideRoundSmall().getModel(), (Function1)null.INSTANCE);
         }
      }));
      this.transitionBottom = this.model((Function1)(new Function1() {
         public final void invoke(@NotNull Model $receiver) {
            Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
            Utils.mix($receiver, AbstractRenderColumn.this.getSideRoundSmall().getModel(), AbstractRenderColumn.this.getSideRoundLarge().getModel(), (Function1)null.INSTANCE);
         }
      }));
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lmods/betterfoliage/client/render/AbstractRenderColumn$BlockType;", "", "(Ljava/lang/String;I)V", "SOLID", "NONSOLID", "PARALLEL", "PERPENDICULAR", "BetterFoliage-MC1.12"}
   )
   public static enum BlockType {
      SOLID,
      NONSOLID,
      PARALLEL,
      PERPENDICULAR;
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lmods/betterfoliage/client/render/AbstractRenderColumn$QuadrantType;", "", "(Ljava/lang/String;I)V", "SMALL_RADIUS", "LARGE_RADIUS", "SQUARE", "INVISIBLE", "BetterFoliage-MC1.12"}
   )
   public static enum QuadrantType {
      SMALL_RADIUS,
      LARGE_RADIUS,
      SQUARE,
      INVISIBLE;
   }
}
