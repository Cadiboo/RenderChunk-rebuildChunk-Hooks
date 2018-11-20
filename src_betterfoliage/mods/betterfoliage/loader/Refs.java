package mods.betterfoliage.loader;

import kotlin.Metadata;
import mods.octarinecore.metaprog.ClassRef;
import mods.octarinecore.metaprog.FieldRef;
import mods.octarinecore.metaprog.MethodRef;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\bc\n\u0002\u0010\u000e\n\u0002\b,\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\r\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u000f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\nR\u0011\u0010\u0011\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u0011\u0010\u0013\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\nR\u0011\u0010\u0015\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\nR\u0011\u0010\u0017\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\nR\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\nR\u0011\u0010\u001b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\nR\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u0011\u0010'\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b(\u0010$R\u0011\u0010)\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\nR\u0011\u0010+\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\nR\u0011\u0010-\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\nR\u0011\u0010/\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\nR\u0011\u00101\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\nR\u0011\u00103\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\nR\u0011\u00105\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\nR\u0011\u00107\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\nR\u0011\u00109\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\nR\u0011\u0010;\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b<\u0010\nR\u0011\u0010=\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b>\u0010\nR\u0011\u0010?\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b@\u0010\nR\u0011\u0010A\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bB\u0010\nR\u0011\u0010C\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bD\u0010\nR\u0011\u0010E\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bF\u0010\nR\u0011\u0010G\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\nR\u0011\u0010I\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\nR\u0011\u0010K\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\nR\u0011\u0010M\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\nR\u0011\u0010O\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bP\u0010\nR\u0011\u0010Q\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\nR\u0011\u0010S\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\u0006R\u0011\u0010U\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bV\u0010\nR\u0011\u0010W\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\nR\u0011\u0010Y\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010\nR\u0011\u0010[\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010\nR\u0011\u0010]\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b^\u0010\nR\u0011\u0010_\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b`\u0010\nR\u0011\u0010a\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bb\u0010\nR\u0011\u0010c\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bd\u0010\nR\u0011\u0010e\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bf\u0010\nR\u0011\u0010g\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bh\u0010\nR\u0011\u0010i\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\bj\u0010$R\u0011\u0010k\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010\u0006R\u0011\u0010m\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bn\u0010\u0006R\u0011\u0010o\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bp\u0010\u0006R\u0011\u0010q\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\br\u0010\u0006R\u0011\u0010s\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010\u0006R\u0011\u0010u\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bv\u0010\u0006R\u0011\u0010w\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bx\u0010\u0006R\u0011\u0010y\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010\u0006R\u0011\u0010{\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010\u0006R\u0011\u0010}\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010\u0006R\u0012\u0010\u007f\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0006R\u0013\u0010\u0081\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0081\u0001\u0010\u0006R\u0013\u0010\u0082\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0001\u0010\u0006R\u0013\u0010\u0083\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010$R\u0015\u0010\u0085\u0001\u001a\u00030\u0086\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0087\u0001\u0010\u0088\u0001R\u0013\u0010\u0089\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010$R\u0013\u0010\u008b\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u008c\u0001\u0010$R\u0013\u0010\u008d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010\u0006R\u0013\u0010\u008f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u0006R\u0013\u0010\u0091\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0006R\u0013\u0010\u0093\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010$R\u0013\u0010\u0095\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0006R\u0013\u0010\u0097\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010\u0006R\u0013\u0010\u0099\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009a\u0001\u0010\u0006R\u0013\u0010\u009b\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u009c\u0001\u0010$R\u0013\u0010\u009d\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u009e\u0001\u0010\u0006R\u0013\u0010\u009f\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b \u0001\u0010\u0006R\u0013\u0010¡\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b¢\u0001\u0010\u0006R\u0013\u0010£\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b¤\u0001\u0010\u0006R\u0013\u0010¥\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b¦\u0001\u0010\u0006R\u0012\u0010§\u0001\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\bX\u0010$R\u0013\u0010¨\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b©\u0001\u0010\u0006R\u0013\u0010ª\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b«\u0001\u0010\u0006R\u0013\u0010¬\u0001\u001a\u00020\"¢\u0006\t\n\u0000\u001a\u0005\b\u00ad\u0001\u0010$R\u0013\u0010®\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b¯\u0001\u0010\u0006R\u0013\u0010°\u0001\u001a\u00020\u0004¢\u0006\t\n\u0000\u001a\u0005\b±\u0001\u0010\u0006¨\u0006²\u0001"},
   d2 = {"Lmods/betterfoliage/loader/Refs;", "", "()V", "AOF_constructor", "Lmods/octarinecore/metaprog/MethodRef;", "getAOF_constructor", "()Lmods/octarinecore/metaprog/MethodRef;", "AmbientOcclusionFace", "Lmods/octarinecore/metaprog/ClassRef;", "getAmbientOcclusionFace", "()Lmods/octarinecore/metaprog/ClassRef;", "BakedQuad", "getBakedQuad", "BetterFoliageHooks", "getBetterFoliageHooks", "Block", "getBlock", "BlockModelRenderer", "getBlockModelRenderer", "BlockPos", "getBlockPos", "BlockRenderLayer", "getBlockRenderLayer", "BlockRendererDispatcher", "getBlockRendererDispatcher", "BlockStateBase", "getBlockStateBase", "BufferBuilder", "getBufferBuilder", "CPmatchesBlock", "getCPmatchesBlock", "CPmatchesIcon", "getCPmatchesIcon", "CPtileIcons", "Lmods/octarinecore/metaprog/FieldRef;", "getCPtileIcons", "()Lmods/octarinecore/metaprog/FieldRef;", "CTblockProperties", "getCTblockProperties", "CTtileProperties", "getCTtileProperties", "ChunkCompileTaskGenerator", "getChunkCompileTaskGenerator", "ConnectedProperties", "getConnectedProperties", "ConnectedTextures", "getConnectedTextures", "CustomColors", "getCustomColors", "EnumFacing", "getEnumFacing", "IBlockAccess", "getIBlockAccess", "IBlockState", "getIBlockState", "IModel", "getIModel", "IRegistry", "getIRegistry", "List", "getList", "Map", "getMap", "ModelBlock", "getModelBlock", "ModelLoader", "getModelLoader", "ModelResourceLocation", "getModelResourceLocation", "MultiModel", "getMultiModel", "MultipartModel", "getMultipartModel", "MutableBlockPos", "getMutableBlockPos", "OptifineClassTransformer", "getOptifineClassTransformer", "Random", "getRandom", "RenderChunk", "getRenderChunk", "RenderEnv", "getRenderEnv", "RenderEnv_reset", "getRenderEnv_reset", "ResourceLocation", "getResourceLocation", "SVertexBuilder", "getSVertexBuilder", "ShadersModIntegration", "getShadersModIntegration", "StateImplementation", "getStateImplementation", "String", "getString", "TextureAtlasSprite", "getTextureAtlasSprite", "VanillaModelWrapper", "getVanillaModelWrapper", "WeightedRandomModel", "getWeightedRandomModel", "World", "getWorld", "WorldClient", "getWorldClient", "base_MM", "getBase_MM", "canRenderBlockInLayer", "getCanRenderBlockInLayer", "canRenderInLayer", "getCanRenderInLayer", "doesSideBlockRendering", "getDoesSideBlockRendering", "doesSideBlockRenderingOverride", "getDoesSideBlockRenderingOverride", "getAmbientOcclusionLightValue", "getGetAmbientOcclusionLightValue", "getAmbientOcclusionLightValueOverride", "getGetAmbientOcclusionLightValueOverride", "getBlockId", "getGetBlockId", "getBlockIdOverride", "getGetBlockIdOverride", "getColorMultiplier", "getGetColorMultiplier", "getConnectedTexture", "getGetConnectedTexture", "getMetadata", "getGetMetadata", "isOpaqueCube", "isOpaqueCubeOverride", "location_VMW", "getLocation_VMW", "mcVersion", "", "getMcVersion", "()Ljava/lang/String;", "model_VMW", "getModel_VMW", "models_WRM", "getModels_WRM", "onAfterBakeModels", "getOnAfterBakeModels", "onAfterLoadModelDefinitions", "getOnAfterLoadModelDefinitions", "onRandomDisplayTick", "getOnRandomDisplayTick", "partModels_MPM", "getPartModels_MPM", "popEntity", "getPopEntity", "pushEntity_num", "getPushEntity_num", "pushEntity_state", "getPushEntity_state", "quadSprite", "getQuadSprite", "randomDisplayTick", "getRandomDisplayTick", "rebuildChunk", "getRebuildChunk", "renderBlock", "getRenderBlock", "renderWorldBlock", "getRenderWorldBlock", "resetChangedState", "getResetChangedState", "sVertexBuilder", "setupModelRegistry", "getSetupModelRegistry", "showBarrierParticles", "getShowBarrierParticles", "stateModels", "getStateModels", "useNeighborBrightness", "getUseNeighborBrightness", "useNeighborBrightnessOverride", "getUseNeighborBrightnessOverride", "BetterFoliage-MC1.12"}
)
public final class Refs {
   @NotNull
   private static final String mcVersion;
   @NotNull
   private static final ClassRef String;
   @NotNull
   private static final ClassRef Map;
   @NotNull
   private static final ClassRef List;
   @NotNull
   private static final ClassRef Random;
   @NotNull
   private static final ClassRef IBlockAccess;
   @NotNull
   private static final ClassRef IBlockState;
   @NotNull
   private static final ClassRef BlockStateBase;
   @NotNull
   private static final ClassRef BlockPos;
   @NotNull
   private static final ClassRef MutableBlockPos;
   @NotNull
   private static final ClassRef BlockRenderLayer;
   @NotNull
   private static final ClassRef EnumFacing;
   @NotNull
   private static final ClassRef World;
   @NotNull
   private static final ClassRef WorldClient;
   @NotNull
   private static final MethodRef showBarrierParticles;
   @NotNull
   private static final ClassRef Block;
   @NotNull
   private static final ClassRef StateImplementation;
   @NotNull
   private static final MethodRef canRenderInLayer;
   @NotNull
   private static final MethodRef getAmbientOcclusionLightValue;
   @NotNull
   private static final MethodRef useNeighborBrightness;
   @NotNull
   private static final MethodRef doesSideBlockRendering;
   @NotNull
   private static final MethodRef isOpaqueCube;
   @NotNull
   private static final MethodRef randomDisplayTick;
   @NotNull
   private static final ClassRef BlockModelRenderer;
   @NotNull
   private static final ClassRef AmbientOcclusionFace;
   @NotNull
   private static final ClassRef ChunkCompileTaskGenerator;
   @NotNull
   private static final ClassRef BufferBuilder;
   @NotNull
   private static final MethodRef AOF_constructor;
   @NotNull
   private static final ClassRef RenderChunk;
   @NotNull
   private static final MethodRef rebuildChunk;
   @NotNull
   private static final ClassRef BlockRendererDispatcher;
   @NotNull
   private static final MethodRef renderBlock;
   @NotNull
   private static final ClassRef TextureAtlasSprite;
   @NotNull
   private static final ClassRef IRegistry;
   @NotNull
   private static final ClassRef ModelLoader;
   @NotNull
   private static final FieldRef stateModels;
   @NotNull
   private static final MethodRef setupModelRegistry;
   @NotNull
   private static final ClassRef IModel;
   @NotNull
   private static final ClassRef ModelBlock;
   @NotNull
   private static final ClassRef ResourceLocation;
   @NotNull
   private static final ClassRef ModelResourceLocation;
   @NotNull
   private static final ClassRef VanillaModelWrapper;
   @NotNull
   private static final FieldRef model_VMW;
   @NotNull
   private static final FieldRef location_VMW;
   @NotNull
   private static final ClassRef WeightedRandomModel;
   @NotNull
   private static final FieldRef models_WRM;
   @NotNull
   private static final ClassRef MultiModel;
   @NotNull
   private static final FieldRef base_MM;
   @NotNull
   private static final ClassRef MultipartModel;
   @NotNull
   private static final FieldRef partModels_MPM;
   @NotNull
   private static final ClassRef BakedQuad;
   @NotNull
   private static final MethodRef resetChangedState;
   @NotNull
   private static final ClassRef BetterFoliageHooks;
   @NotNull
   private static final MethodRef getAmbientOcclusionLightValueOverride;
   @NotNull
   private static final MethodRef useNeighborBrightnessOverride;
   @NotNull
   private static final MethodRef doesSideBlockRenderingOverride;
   @NotNull
   private static final MethodRef isOpaqueCubeOverride;
   @NotNull
   private static final MethodRef onRandomDisplayTick;
   @NotNull
   private static final MethodRef onAfterLoadModelDefinitions;
   @NotNull
   private static final MethodRef onAfterBakeModels;
   @NotNull
   private static final MethodRef renderWorldBlock;
   @NotNull
   private static final MethodRef canRenderBlockInLayer;
   @NotNull
   private static final ClassRef OptifineClassTransformer;
   @NotNull
   private static final MethodRef getBlockId;
   @NotNull
   private static final MethodRef getMetadata;
   @NotNull
   private static final ClassRef RenderEnv;
   @NotNull
   private static final MethodRef RenderEnv_reset;
   @NotNull
   private static final ClassRef ConnectedTextures;
   @NotNull
   private static final MethodRef getConnectedTexture;
   @NotNull
   private static final FieldRef CTblockProperties;
   @NotNull
   private static final FieldRef CTtileProperties;
   @NotNull
   private static final ClassRef ConnectedProperties;
   @NotNull
   private static final FieldRef CPtileIcons;
   @NotNull
   private static final MethodRef CPmatchesBlock;
   @NotNull
   private static final MethodRef CPmatchesIcon;
   @NotNull
   private static final FieldRef quadSprite;
   @NotNull
   private static final ClassRef CustomColors;
   @NotNull
   private static final MethodRef getColorMultiplier;
   @NotNull
   private static final ClassRef SVertexBuilder;
   @NotNull
   private static final FieldRef sVertexBuilder;
   @NotNull
   private static final MethodRef pushEntity_state;
   @NotNull
   private static final MethodRef pushEntity_num;
   @NotNull
   private static final MethodRef popEntity;
   @NotNull
   private static final ClassRef ShadersModIntegration;
   @NotNull
   private static final MethodRef getBlockIdOverride;
   public static final Refs INSTANCE;

   @NotNull
   public final String getMcVersion() {
      return mcVersion;
   }

   @NotNull
   public final ClassRef getString() {
      return String;
   }

   @NotNull
   public final ClassRef getMap() {
      return Map;
   }

   @NotNull
   public final ClassRef getList() {
      return List;
   }

   @NotNull
   public final ClassRef getRandom() {
      return Random;
   }

   @NotNull
   public final ClassRef getIBlockAccess() {
      return IBlockAccess;
   }

   @NotNull
   public final ClassRef getIBlockState() {
      return IBlockState;
   }

   @NotNull
   public final ClassRef getBlockStateBase() {
      return BlockStateBase;
   }

   @NotNull
   public final ClassRef getBlockPos() {
      return BlockPos;
   }

   @NotNull
   public final ClassRef getMutableBlockPos() {
      return MutableBlockPos;
   }

   @NotNull
   public final ClassRef getBlockRenderLayer() {
      return BlockRenderLayer;
   }

   @NotNull
   public final ClassRef getEnumFacing() {
      return EnumFacing;
   }

   @NotNull
   public final ClassRef getWorld() {
      return World;
   }

   @NotNull
   public final ClassRef getWorldClient() {
      return WorldClient;
   }

   @NotNull
   public final MethodRef getShowBarrierParticles() {
      return showBarrierParticles;
   }

   @NotNull
   public final ClassRef getBlock() {
      return Block;
   }

   @NotNull
   public final ClassRef getStateImplementation() {
      return StateImplementation;
   }

   @NotNull
   public final MethodRef getCanRenderInLayer() {
      return canRenderInLayer;
   }

   @NotNull
   public final MethodRef getGetAmbientOcclusionLightValue() {
      return getAmbientOcclusionLightValue;
   }

   @NotNull
   public final MethodRef getUseNeighborBrightness() {
      return useNeighborBrightness;
   }

   @NotNull
   public final MethodRef getDoesSideBlockRendering() {
      return doesSideBlockRendering;
   }

   @NotNull
   public final MethodRef isOpaqueCube() {
      return isOpaqueCube;
   }

   @NotNull
   public final MethodRef getRandomDisplayTick() {
      return randomDisplayTick;
   }

   @NotNull
   public final ClassRef getBlockModelRenderer() {
      return BlockModelRenderer;
   }

   @NotNull
   public final ClassRef getAmbientOcclusionFace() {
      return AmbientOcclusionFace;
   }

   @NotNull
   public final ClassRef getChunkCompileTaskGenerator() {
      return ChunkCompileTaskGenerator;
   }

   @NotNull
   public final ClassRef getBufferBuilder() {
      return BufferBuilder;
   }

   @NotNull
   public final MethodRef getAOF_constructor() {
      return AOF_constructor;
   }

   @NotNull
   public final ClassRef getRenderChunk() {
      return RenderChunk;
   }

   @NotNull
   public final MethodRef getRebuildChunk() {
      return rebuildChunk;
   }

   @NotNull
   public final ClassRef getBlockRendererDispatcher() {
      return BlockRendererDispatcher;
   }

   @NotNull
   public final MethodRef getRenderBlock() {
      return renderBlock;
   }

   @NotNull
   public final ClassRef getTextureAtlasSprite() {
      return TextureAtlasSprite;
   }

   @NotNull
   public final ClassRef getIRegistry() {
      return IRegistry;
   }

   @NotNull
   public final ClassRef getModelLoader() {
      return ModelLoader;
   }

   @NotNull
   public final FieldRef getStateModels() {
      return stateModels;
   }

   @NotNull
   public final MethodRef getSetupModelRegistry() {
      return setupModelRegistry;
   }

   @NotNull
   public final ClassRef getIModel() {
      return IModel;
   }

   @NotNull
   public final ClassRef getModelBlock() {
      return ModelBlock;
   }

   @NotNull
   public final ClassRef getResourceLocation() {
      return ResourceLocation;
   }

   @NotNull
   public final ClassRef getModelResourceLocation() {
      return ModelResourceLocation;
   }

   @NotNull
   public final ClassRef getVanillaModelWrapper() {
      return VanillaModelWrapper;
   }

   @NotNull
   public final FieldRef getModel_VMW() {
      return model_VMW;
   }

   @NotNull
   public final FieldRef getLocation_VMW() {
      return location_VMW;
   }

   @NotNull
   public final ClassRef getWeightedRandomModel() {
      return WeightedRandomModel;
   }

   @NotNull
   public final FieldRef getModels_WRM() {
      return models_WRM;
   }

   @NotNull
   public final ClassRef getMultiModel() {
      return MultiModel;
   }

   @NotNull
   public final FieldRef getBase_MM() {
      return base_MM;
   }

   @NotNull
   public final ClassRef getMultipartModel() {
      return MultipartModel;
   }

   @NotNull
   public final FieldRef getPartModels_MPM() {
      return partModels_MPM;
   }

   @NotNull
   public final ClassRef getBakedQuad() {
      return BakedQuad;
   }

   @NotNull
   public final MethodRef getResetChangedState() {
      return resetChangedState;
   }

   @NotNull
   public final ClassRef getBetterFoliageHooks() {
      return BetterFoliageHooks;
   }

   @NotNull
   public final MethodRef getGetAmbientOcclusionLightValueOverride() {
      return getAmbientOcclusionLightValueOverride;
   }

   @NotNull
   public final MethodRef getUseNeighborBrightnessOverride() {
      return useNeighborBrightnessOverride;
   }

   @NotNull
   public final MethodRef getDoesSideBlockRenderingOverride() {
      return doesSideBlockRenderingOverride;
   }

   @NotNull
   public final MethodRef isOpaqueCubeOverride() {
      return isOpaqueCubeOverride;
   }

   @NotNull
   public final MethodRef getOnRandomDisplayTick() {
      return onRandomDisplayTick;
   }

   @NotNull
   public final MethodRef getOnAfterLoadModelDefinitions() {
      return onAfterLoadModelDefinitions;
   }

   @NotNull
   public final MethodRef getOnAfterBakeModels() {
      return onAfterBakeModels;
   }

   @NotNull
   public final MethodRef getRenderWorldBlock() {
      return renderWorldBlock;
   }

   @NotNull
   public final MethodRef getCanRenderBlockInLayer() {
      return canRenderBlockInLayer;
   }

   @NotNull
   public final ClassRef getOptifineClassTransformer() {
      return OptifineClassTransformer;
   }

   @NotNull
   public final MethodRef getGetBlockId() {
      return getBlockId;
   }

   @NotNull
   public final MethodRef getGetMetadata() {
      return getMetadata;
   }

   @NotNull
   public final ClassRef getRenderEnv() {
      return RenderEnv;
   }

   @NotNull
   public final MethodRef getRenderEnv_reset() {
      return RenderEnv_reset;
   }

   @NotNull
   public final ClassRef getConnectedTextures() {
      return ConnectedTextures;
   }

   @NotNull
   public final MethodRef getGetConnectedTexture() {
      return getConnectedTexture;
   }

   @NotNull
   public final FieldRef getCTblockProperties() {
      return CTblockProperties;
   }

   @NotNull
   public final FieldRef getCTtileProperties() {
      return CTtileProperties;
   }

   @NotNull
   public final ClassRef getConnectedProperties() {
      return ConnectedProperties;
   }

   @NotNull
   public final FieldRef getCPtileIcons() {
      return CPtileIcons;
   }

   @NotNull
   public final MethodRef getCPmatchesBlock() {
      return CPmatchesBlock;
   }

   @NotNull
   public final MethodRef getCPmatchesIcon() {
      return CPmatchesIcon;
   }

   @NotNull
   public final FieldRef getQuadSprite() {
      return quadSprite;
   }

   @NotNull
   public final ClassRef getCustomColors() {
      return CustomColors;
   }

   @NotNull
   public final MethodRef getGetColorMultiplier() {
      return getColorMultiplier;
   }

   @NotNull
   public final ClassRef getSVertexBuilder() {
      return SVertexBuilder;
   }

   @NotNull
   public final FieldRef getSVertexBuilder() {
      return sVertexBuilder;
   }

   @NotNull
   public final MethodRef getPushEntity_state() {
      return pushEntity_state;
   }

   @NotNull
   public final MethodRef getPushEntity_num() {
      return pushEntity_num;
   }

   @NotNull
   public final MethodRef getPopEntity() {
      return popEntity;
   }

   @NotNull
   public final ClassRef getShadersModIntegration() {
      return ShadersModIntegration;
   }

   @NotNull
   public final MethodRef getGetBlockIdOverride() {
      return getBlockIdOverride;
   }

   static {
      Refs var0 = new Refs();
      INSTANCE = var0;
      mcVersion = FMLInjectionData.data()[4].toString();
      String = new ClassRef("java.lang.String");
      Map = new ClassRef("java.util.Map");
      List = new ClassRef("java.util.List");
      Random = new ClassRef("java.util.Random");
      IBlockAccess = new ClassRef("net.minecraft.world.IBlockAccess");
      IBlockState = new ClassRef("net.minecraft.block.state.IBlockState");
      BlockStateBase = new ClassRef("net.minecraft.block.state.BlockStateBase");
      BlockPos = new ClassRef("net.minecraft.util.math.BlockPos");
      MutableBlockPos = new ClassRef("net.minecraft.util.math.BlockPos$MutableBlockPos");
      BlockRenderLayer = new ClassRef("net.minecraft.util.BlockRenderLayer");
      EnumFacing = new ClassRef("net.minecraft.util.EnumFacing");
      World = new ClassRef("net.minecraft.world.World");
      WorldClient = new ClassRef("net.minecraft.client.multiplayer.WorldClient");
      showBarrierParticles = new MethodRef(WorldClient, "showBarrierParticles", "func_184153_a", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{(ClassRef)ClassRef.Companion.getInt(), (ClassRef)ClassRef.Companion.getInt(), (ClassRef)ClassRef.Companion.getInt(), (ClassRef)ClassRef.Companion.getInt(), Random, (ClassRef)ClassRef.Companion.getBoolean(), MutableBlockPos});
      Block = new ClassRef("net.minecraft.block.Block");
      StateImplementation = new ClassRef("net.minecraft.block.state.BlockStateContainer$StateImplementation");
      canRenderInLayer = new MethodRef(Block, "canRenderInLayer", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{IBlockState, BlockRenderLayer});
      getAmbientOcclusionLightValue = new MethodRef(StateImplementation, "getAmbientOcclusionLightValue", "func_185892_j", (ClassRef)ClassRef.Companion.getFloat(), new ClassRef[0]);
      useNeighborBrightness = new MethodRef(StateImplementation, "useNeighborBrightness", "func_185916_f", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[0]);
      doesSideBlockRendering = new MethodRef(StateImplementation, "doesSideBlockRendering", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{IBlockAccess, BlockPos, EnumFacing});
      isOpaqueCube = new MethodRef(StateImplementation, "isOpaqueCube", "func_185914_p", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[0]);
      randomDisplayTick = new MethodRef(Block, "randomDisplayTick", "func_180655_c", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{IBlockState, World, BlockPos, Random});
      BlockModelRenderer = new ClassRef("net.minecraft.client.renderer.BlockModelRenderer");
      AmbientOcclusionFace = new ClassRef("net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace");
      ChunkCompileTaskGenerator = new ClassRef("net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator");
      BufferBuilder = new ClassRef("net.minecraft.client.renderer.BufferBuilder");
      AOF_constructor = new MethodRef(AmbientOcclusionFace, "<init>", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{BlockModelRenderer});
      RenderChunk = new ClassRef("net.minecraft.client.renderer.chunk.RenderChunk");
      rebuildChunk = new MethodRef(RenderChunk, "rebuildChunk", "func_178581_b", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{(ClassRef)ClassRef.Companion.getFloat(), (ClassRef)ClassRef.Companion.getFloat(), (ClassRef)ClassRef.Companion.getFloat(), ChunkCompileTaskGenerator});
      BlockRendererDispatcher = new ClassRef("net.minecraft.client.renderer.BlockRendererDispatcher");
      renderBlock = new MethodRef(BlockRendererDispatcher, "renderBlock", "func_175018_a", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{IBlockState, BlockPos, IBlockAccess, BufferBuilder});
      TextureAtlasSprite = new ClassRef("net.minecraft.client.renderer.texture.TextureAtlasSprite");
      IRegistry = new ClassRef("net.minecraft.util.registry.IRegistry");
      ModelLoader = new ClassRef("net.minecraftforge.client.model.ModelLoader");
      stateModels = new FieldRef(ModelLoader, "stateModels", Map);
      setupModelRegistry = new MethodRef(ModelLoader, "setupModelRegistry", "func_177570_a", IRegistry, new ClassRef[0]);
      IModel = new ClassRef("net.minecraftforge.client.model.IModel");
      ModelBlock = new ClassRef("net.minecraft.client.renderer.block.model.ModelBlock");
      ResourceLocation = new ClassRef("net.minecraft.util.ResourceLocation");
      ModelResourceLocation = new ClassRef("net.minecraft.client.renderer.block.model.ModelResourceLocation");
      VanillaModelWrapper = new ClassRef("net.minecraftforge.client.model.ModelLoader$VanillaModelWrapper");
      model_VMW = new FieldRef(VanillaModelWrapper, "model", ModelBlock);
      location_VMW = new FieldRef(VanillaModelWrapper, "location", ModelBlock);
      WeightedRandomModel = new ClassRef("net.minecraftforge.client.model.ModelLoader$WeightedRandomModel");
      models_WRM = new FieldRef(WeightedRandomModel, "models", List);
      MultiModel = new ClassRef("net.minecraftforge.client.model.MultiModel");
      base_MM = new FieldRef(MultiModel, "base", IModel);
      MultipartModel = new ClassRef("net.minecraftforge.client.model.ModelLoader$MultipartModel");
      partModels_MPM = new FieldRef(MultipartModel, "partModels", List);
      BakedQuad = new ClassRef("net.minecraft.client.renderer.block.model.BakedQuad");
      resetChangedState = new MethodRef(new ClassRef("net.minecraftforge.common.config.Configuration"), "resetChangedState", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[0]);
      BetterFoliageHooks = new ClassRef("mods.betterfoliage.client.Hooks");
      getAmbientOcclusionLightValueOverride = new MethodRef(BetterFoliageHooks, "getAmbientOcclusionLightValueOverride", (ClassRef)ClassRef.Companion.getFloat(), new ClassRef[]{(ClassRef)ClassRef.Companion.getFloat(), IBlockState});
      useNeighborBrightnessOverride = new MethodRef(BetterFoliageHooks, "getUseNeighborBrightnessOverride", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{(ClassRef)ClassRef.Companion.getBoolean(), IBlockState});
      doesSideBlockRenderingOverride = new MethodRef(BetterFoliageHooks, "doesSideBlockRenderingOverride", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{(ClassRef)ClassRef.Companion.getBoolean(), IBlockAccess, BlockPos, EnumFacing});
      isOpaqueCubeOverride = new MethodRef(BetterFoliageHooks, "isOpaqueCubeOverride", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{(ClassRef)ClassRef.Companion.getBoolean(), IBlockState});
      onRandomDisplayTick = new MethodRef(BetterFoliageHooks, "onRandomDisplayTick", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{World, IBlockState, BlockPos});
      onAfterLoadModelDefinitions = new MethodRef(BetterFoliageHooks, "onAfterLoadModelDefinitions", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{ModelLoader});
      onAfterBakeModels = new MethodRef(BetterFoliageHooks, "onAfterBakeModels", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{Map});
      renderWorldBlock = new MethodRef(BetterFoliageHooks, "renderWorldBlock", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{BlockRendererDispatcher, IBlockState, BlockPos, IBlockAccess, BufferBuilder, BlockRenderLayer});
      canRenderBlockInLayer = new MethodRef(BetterFoliageHooks, "canRenderBlockInLayer", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{Block, IBlockState, BlockRenderLayer});
      OptifineClassTransformer = new ClassRef("optifine.OptiFineClassTransformer");
      getBlockId = new MethodRef(BlockStateBase, "getBlockId", (ClassRef)ClassRef.Companion.getInt(), new ClassRef[0]);
      getMetadata = new MethodRef(BlockStateBase, "getMetadata", (ClassRef)ClassRef.Companion.getInt(), new ClassRef[0]);
      RenderEnv = new ClassRef("RenderEnv");
      RenderEnv_reset = new MethodRef(RenderEnv, "reset", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{IBlockAccess, IBlockState, BlockPos});
      ConnectedTextures = new ClassRef("ConnectedTextures");
      getConnectedTexture = new MethodRef(ConnectedTextures, "getConnectedTextureMultiPass", TextureAtlasSprite, new ClassRef[]{IBlockAccess, IBlockState, BlockPos, EnumFacing, TextureAtlasSprite, RenderEnv});
      CTblockProperties = new FieldRef(ConnectedTextures, "blockProperties", (ClassRef)null);
      CTtileProperties = new FieldRef(ConnectedTextures, "tileProperties", (ClassRef)null);
      ConnectedProperties = new ClassRef("ConnectedProperties");
      CPtileIcons = new FieldRef(ConnectedProperties, "tileIcons", (ClassRef)null);
      CPmatchesBlock = new MethodRef(ConnectedProperties, "matchesBlock", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{(ClassRef)ClassRef.Companion.getInt(), (ClassRef)ClassRef.Companion.getInt()});
      CPmatchesIcon = new MethodRef(ConnectedProperties, "matchesIcon", (ClassRef)ClassRef.Companion.getBoolean(), new ClassRef[]{TextureAtlasSprite});
      quadSprite = new FieldRef(BufferBuilder, "quadSprite", TextureAtlasSprite);
      CustomColors = new ClassRef("CustomColors");
      getColorMultiplier = new MethodRef(CustomColors, "getColorMultiplier", (ClassRef)ClassRef.Companion.getInt(), new ClassRef[]{BakedQuad, IBlockState, IBlockAccess, BlockPos, RenderEnv});
      SVertexBuilder = new ClassRef("shadersmod.client.SVertexBuilder");
      sVertexBuilder = new FieldRef(BufferBuilder, "sVertexBuilder", SVertexBuilder);
      pushEntity_state = new MethodRef(SVertexBuilder, "pushEntity", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{IBlockState, BlockPos, IBlockAccess, BufferBuilder});
      pushEntity_num = new MethodRef(SVertexBuilder, "pushEntity", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[]{(ClassRef)ClassRef.Companion.getLong()});
      popEntity = new MethodRef(SVertexBuilder, "popEntity", (ClassRef)ClassRef.Companion.getVoid(), new ClassRef[0]);
      ShadersModIntegration = new ClassRef("mods.betterfoliage.client.integration.ShadersModIntegration");
      getBlockIdOverride = new MethodRef(ShadersModIntegration, "getBlockIdOverride", (ClassRef)ClassRef.Companion.getLong(), new ClassRef[]{(ClassRef)ClassRef.Companion.getLong(), IBlockState});
   }
}
