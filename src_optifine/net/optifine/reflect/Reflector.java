package net.optifine.reflect;

import anh.c;
import ceb.b;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import javax.vecmath.Matrix4f;
import net.minecraftforge.common.property.IUnlistedProperty;

public class Reflector {
   private static boolean logForge = logEntry("*** Reflector Forge ***");
   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT;
   public static ReflectorClass BetterFoliageClient;
   public static ReflectorClass BlamingTransformer;
   public static ReflectorMethod BlamingTransformer_onCrash;
   public static ReflectorClass ChunkWatchEvent_UnWatch;
   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
   public static ReflectorClass CoreModManager;
   public static ReflectorMethod CoreModManager_onCrash;
   public static ReflectorClass DimensionManager;
   public static ReflectorMethod DimensionManager_createProviderFor;
   public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
   public static ReflectorClass DrawScreenEvent_Pre;
   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor;
   public static ReflectorClass DrawScreenEvent_Post;
   public static ReflectorConstructor DrawScreenEvent_Post_Constructor;
   public static ReflectorClass EntityViewRenderEvent_CameraSetup;
   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor;
   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getRoll;
   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getPitch;
   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getYaw;
   public static ReflectorClass EntityViewRenderEvent_FogColors;
   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor;
   public static ReflectorMethod EntityViewRenderEvent_FogColors_getRed;
   public static ReflectorMethod EntityViewRenderEvent_FogColors_getGreen;
   public static ReflectorMethod EntityViewRenderEvent_FogColors_getBlue;
   public static ReflectorClass EntityViewRenderEvent_RenderFogEvent;
   public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor;
   public static ReflectorClass Event;
   public static ReflectorMethod Event_isCanceled;
   public static ReflectorClass EventBus;
   public static ReflectorMethod EventBus_post;
   public static ReflectorClass Event_Result;
   public static ReflectorField Event_Result_DENY;
   public static ReflectorField Event_Result_ALLOW;
   public static ReflectorField Event_Result_DEFAULT;
   public static ReflectorClass ExtendedBlockState;
   public static ReflectorConstructor ExtendedBlockState_Constructor;
   public static ReflectorClass FMLClientHandler;
   public static ReflectorMethod FMLClientHandler_instance;
   public static ReflectorMethod FMLClientHandler_handleLoadingScreen;
   public static ReflectorMethod FMLClientHandler_isLoading;
   public static ReflectorMethod FMLClientHandler_renderClouds;
   public static ReflectorMethod FMLClientHandler_trackBrokenTexture;
   public static ReflectorMethod FMLClientHandler_trackMissingTexture;
   public static ReflectorClass FMLCommonHandler;
   public static ReflectorMethod FMLCommonHandler_callFuture;
   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport;
   public static ReflectorMethod FMLCommonHandler_getBrandings;
   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
   public static ReflectorMethod FMLCommonHandler_handleServerStarting;
   public static ReflectorMethod FMLCommonHandler_instance;
   public static ReflectorClass ActiveRenderInfo;
   public static ReflectorMethod ActiveRenderInfo_getCameraPosition;
   public static ReflectorMethod ActiveRenderInfo_updateRenderInfo2;
   public static ReflectorClass ForgeBiome;
   public static ReflectorMethod ForgeBiome_getWaterColorMultiplier;
   public static ReflectorClass ForgeBiomeSpawnListEntry;
   public static ReflectorMethod ForgeBiomeSpawnListEntry_newInstance;
   public static ReflectorClass ForgeBlock;
   public static ReflectorMethod ForgeBlock_addDestroyEffects;
   public static ReflectorMethod ForgeBlock_addHitEffects;
   public static ReflectorMethod ForgeBlock_canCreatureSpawn;
   public static ReflectorMethod ForgeBlock_canRenderInLayer;
   public static ReflectorMethod ForgeBlock_doesSideBlockRendering;
   public static ReflectorMethod ForgeBlock_doesSideBlockChestOpening;
   public static ReflectorMethod ForgeBlock_getBedDirection;
   public static ReflectorMethod ForgeBlock_getExtendedState;
   public static ReflectorMethod ForgeBlock_getFogColor;
   public static ReflectorMethod ForgeBlock_getLightOpacity;
   public static ReflectorMethod ForgeBlock_getLightValue;
   public static ReflectorMethod ForgeBlock_getSoundType;
   public static ReflectorMethod ForgeBlock_hasTileEntity;
   public static ReflectorMethod ForgeBlock_isAir;
   public static ReflectorMethod ForgeBlock_isBed;
   public static ReflectorMethod ForgeBlock_isBedFoot;
   public static ReflectorMethod ForgeBlock_isSideSolid;
   public static ReflectorClass ForgeIBakedModel;
   public static ReflectorMethod ForgeIBakedModel_isAmbientOcclusion2;
   public static ReflectorClass ForgeIBlockProperties;
   public static ReflectorMethod ForgeIBlockProperties_getLightValue2;
   public static ReflectorClass ForgeChunkCache;
   public static ReflectorMethod ForgeChunkCache_isSideSolid;
   public static ReflectorClass ForgeEntity;
   public static ReflectorMethod ForgeEntity_canRiderInteract;
   public static ReflectorField ForgeEntity_captureDrops;
   public static ReflectorField ForgeEntity_capturedDrops;
   public static ReflectorMethod ForgeEntity_shouldRenderInPass;
   public static ReflectorMethod ForgeEntity_shouldRiderSit;
   public static ReflectorClass ForgeEventFactory;
   public static ReflectorMethod ForgeEventFactory_canEntitySpawn;
   public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
   public static ReflectorMethod ForgeEventFactory_doSpecialSpawn;
   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize;
   public static ReflectorMethod ForgeEventFactory_getMobGriefingEvent;
   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay;
   public static ReflectorMethod ForgeEventFactory_renderFireOverlay;
   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay;
   public static ReflectorClass ForgeHooks;
   public static ReflectorMethod ForgeHooks_onLivingAttack;
   public static ReflectorMethod ForgeHooks_onLivingDeath;
   public static ReflectorMethod ForgeHooks_onLivingDrops;
   public static ReflectorMethod ForgeHooks_onLivingFall;
   public static ReflectorMethod ForgeHooks_onLivingHurt;
   public static ReflectorMethod ForgeHooks_onLivingJump;
   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget;
   public static ReflectorMethod ForgeHooks_onLivingUpdate;
   public static ReflectorClass ForgeHooksClient;
   public static ReflectorMethod ForgeHooksClient_applyTransform_M4;
   public static ReflectorMethod ForgeHooksClient_applyTransform_MR;
   public static ReflectorMethod ForgeHooksClient_applyUVLock;
   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
   public static ReflectorMethod ForgeHooksClient_drawScreen;
   public static ReflectorMethod ForgeHooksClient_fillNormal;
   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms;
   public static ReflectorMethod ForgeHooksClient_getArmorModel;
   public static ReflectorMethod ForgeHooksClient_getArmorTexture;
   public static ReflectorMethod ForgeHooksClient_getFogDensity;
   public static ReflectorMethod ForgeHooksClient_getFOVModifier;
   public static ReflectorMethod ForgeHooksClient_getMatrix;
   public static ReflectorMethod ForgeHooksClient_getOffsetFOV;
   public static ReflectorMethod ForgeHooksClient_loadEntityShader;
   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
   public static ReflectorMethod ForgeHooksClient_onFogRender;
   public static ReflectorMethod ForgeHooksClient_onScreenshot;
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
   public static ReflectorMethod ForgeHooksClient_orientBedCamera;
   public static ReflectorMethod ForgeHooksClient_putQuadColor;
   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand;
   public static ReflectorMethod ForgeHooksClient_renderMainMenu;
   public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand;
   public static ReflectorMethod ForgeHooksClient_setRenderLayer;
   public static ReflectorMethod ForgeHooksClient_setRenderPass;
   public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation;
   public static ReflectorMethod ForgeHooksClient_transform;
   public static ReflectorClass ForgeItem;
   public static ReflectorField ForgeItem_delegate;
   public static ReflectorMethod ForgeItem_getDurabilityForDisplay;
   public static ReflectorMethod ForgeItem_getEquipmentSlot;
   public static ReflectorMethod ForgeItem_getTileEntityItemStackRenderer;
   public static ReflectorMethod ForgeItem_getRGBDurabilityForDisplay;
   public static ReflectorMethod ForgeItem_isShield;
   public static ReflectorMethod ForgeItem_onEntitySwing;
   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation;
   public static ReflectorMethod ForgeItem_showDurabilityBar;
   public static ReflectorClass ForgeItemArmor;
   public static ReflectorMethod ForgeItemArmor_hasOverlay;
   public static ReflectorClass ForgeKeyBinding;
   public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext;
   public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode;
   public static ReflectorMethod ForgeKeyBinding_getKeyModifier;
   public static ReflectorClass ForgeModContainer;
   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled;
   public static ReflectorClass ForgeModelBlockDefinition;
   public static ReflectorMethod ForgeModelBlockDefinition_parseFromReader2;
   public static ReflectorClass ForgePotion;
   public static ReflectorMethod ForgePotion_shouldRenderHUD;
   public static ReflectorMethod ForgePotion_renderHUDEffect;
   public static ReflectorClass ForgePotionEffect;
   public static ReflectorMethod ForgePotionEffect_isCurativeItem;
   public static ReflectorClass ForgeTileEntity;
   public static ReflectorMethod ForgeTileEntity_canRenderBreaking;
   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox;
   public static ReflectorMethod ForgeTileEntity_hasFastRenderer;
   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass;
   public static ReflectorClass ForgeVertexFormatElementEnumUseage;
   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw;
   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw;
   public static ReflectorClass ForgeWorld;
   public static ReflectorMethod ForgeWorld_countEntities;
   public static ReflectorMethod ForgeWorld_getPerWorldStorage;
   public static ReflectorMethod ForgeWorld_initCapabilities;
   public static ReflectorClass ForgeWorldProvider;
   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer;
   public static ReflectorMethod ForgeWorldProvider_getLightmapColors;
   public static ReflectorMethod ForgeWorldProvider_getSaveFolder;
   public static ReflectorClass GuiModList;
   public static ReflectorConstructor GuiModList_Constructor;
   public static ReflectorClass IExtendedBlockState;
   public static ReflectorMethod IExtendedBlockState_getClean;
   public static ReflectorClass IModel;
   public static ReflectorMethod IModel_getTextures;
   public static ReflectorClass IRenderHandler;
   public static ReflectorMethod IRenderHandler_render;
   public static ReflectorClass ItemModelMesherForge;
   public static ReflectorConstructor ItemModelMesherForge_Constructor;
   public static ReflectorClass KeyConflictContext;
   public static ReflectorField KeyConflictContext_IN_GAME;
   public static ReflectorClass KeyModifier;
   public static ReflectorMethod KeyModifier_valueFromString;
   public static ReflectorField KeyModifier_NONE;
   public static ReflectorClass Launch;
   public static ReflectorField Launch_blackboard;
   public static ReflectorClass LightUtil;
   public static ReflectorField LightUtil_itemConsumer;
   public static ReflectorMethod LightUtil_putBakedQuad;
   public static ReflectorMethod LightUtil_renderQuadColor;
   public static ReflectorField LightUtil_tessellator;
   public static ReflectorClass Loader;
   public static ReflectorMethod Loader_getActiveModList;
   public static ReflectorMethod Loader_instance;
   public static ReflectorClass MinecraftForge;
   public static ReflectorField MinecraftForge_EVENT_BUS;
   public static ReflectorClass MinecraftForgeClient;
   public static ReflectorMethod MinecraftForgeClient_getRenderPass;
   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk;
   public static ReflectorClass ModContainer;
   public static ReflectorMethod ModContainer_getModId;
   public static ReflectorClass ModelLoader;
   public static ReflectorField ModelLoader_stateModels;
   public static ReflectorMethod ModelLoader_onRegisterItems;
   public static ReflectorMethod ModelLoader_getInventoryVariant;
   public static ReflectorClass ModelLoader_VanillaLoader;
   public static ReflectorField ModelLoader_VanillaLoader_INSTANCE;
   public static ReflectorMethod ModelLoader_VanillaLoader_loadModel;
   public static ReflectorClass ModelLoaderRegistry;
   public static ReflectorField ModelLoaderRegistry_textures;
   public static ReflectorClass NotificationModUpdateScreen;
   public static ReflectorMethod NotificationModUpdateScreen_init;
   public static ReflectorClass RenderBlockOverlayEvent_OverlayType;
   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK;
   public static ReflectorClass RenderingRegistry;
   public static ReflectorMethod RenderingRegistry_loadEntityRenderers;
   public static ReflectorClass RenderItemInFrameEvent;
   public static ReflectorConstructor RenderItemInFrameEvent_Constructor;
   public static ReflectorClass RenderLivingEvent_Pre;
   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor;
   public static ReflectorClass RenderLivingEvent_Post;
   public static ReflectorConstructor RenderLivingEvent_Post_Constructor;
   public static ReflectorClass RenderLivingEvent_Specials_Pre;
   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor;
   public static ReflectorClass RenderLivingEvent_Specials_Post;
   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor;
   public static ReflectorClass ScreenshotEvent;
   public static ReflectorMethod ScreenshotEvent_getCancelMessage;
   public static ReflectorMethod ScreenshotEvent_getScreenshotFile;
   public static ReflectorMethod ScreenshotEvent_getResultMessage;
   public static ReflectorClass SplashScreen;
   public static ReflectorClass WorldEvent_Load;
   public static ReflectorConstructor WorldEvent_Load_Constructor;
   private static boolean logVanilla;
   public static ReflectorClass ChunkProviderClient;
   public static ReflectorField ChunkProviderClient_chunkMapping;
   public static ReflectorClass EntityVillager;
   public static ReflectorField EntityVillager_careerId;
   public static ReflectorField EntityVillager_careerLevel;
   public static ReflectorClass GuiBeacon;
   public static ReflectorField GuiBeacon_tileBeacon;
   public static ReflectorClass GuiBrewingStand;
   public static ReflectorField GuiBrewingStand_tileBrewingStand;
   public static ReflectorClass GuiChest;
   public static ReflectorField GuiChest_lowerChestInventory;
   public static ReflectorClass GuiEnchantment;
   public static ReflectorField GuiEnchantment_nameable;
   public static ReflectorClass GuiFurnace;
   public static ReflectorField GuiFurnace_tileFurnace;
   public static ReflectorClass GuiHopper;
   public static ReflectorField GuiHopper_hopperInventory;
   public static ReflectorClass GuiMainMenu;
   public static ReflectorField GuiMainMenu_splashText;
   public static ReflectorClass GuiShulkerBox;
   public static ReflectorField GuiShulkerBox_inventory;
   public static ReflectorClass ItemOverride;
   public static ReflectorField ItemOverride_mapResourceValues;
   public static ReflectorClass LegacyV2Adapter;
   public static ReflectorField LegacyV2Adapter_pack;
   public static ReflectorClass Minecraft;
   public static ReflectorField Minecraft_defaultResourcePack;
   public static ReflectorField Minecraft_actionKeyF3;
   public static ReflectorClass ModelHumanoidHead;
   public static ReflectorField ModelHumanoidHead_head;
   public static ReflectorClass ModelBat;
   public static ReflectorFields ModelBat_ModelRenderers;
   public static ReflectorClass ModelBlaze;
   public static ReflectorField ModelBlaze_blazeHead;
   public static ReflectorField ModelBlaze_blazeSticks;
   public static ReflectorClass ModelDragon;
   public static ReflectorFields ModelDragon_ModelRenderers;
   public static ReflectorClass ModelEnderCrystal;
   public static ReflectorFields ModelEnderCrystal_ModelRenderers;
   public static ReflectorClass RenderEnderCrystal;
   public static ReflectorField RenderEnderCrystal_modelEnderCrystal;
   public static ReflectorField RenderEnderCrystal_modelEnderCrystalNoBase;
   public static ReflectorClass ModelEnderMite;
   public static ReflectorField ModelEnderMite_bodyParts;
   public static ReflectorClass ModelEvokerFangs;
   public static ReflectorFields ModelEvokerFangs_ModelRenderers;
   public static ReflectorClass ModelGhast;
   public static ReflectorField ModelGhast_body;
   public static ReflectorField ModelGhast_tentacles;
   public static ReflectorClass ModelGuardian;
   public static ReflectorField ModelGuardian_body;
   public static ReflectorField ModelGuardian_eye;
   public static ReflectorField ModelGuardian_spines;
   public static ReflectorField ModelGuardian_tail;
   public static ReflectorClass ModelDragonHead;
   public static ReflectorField ModelDragonHead_head;
   public static ReflectorField ModelDragonHead_jaw;
   public static ReflectorClass ModelHorse;
   public static ReflectorFields ModelHorse_ModelRenderers;
   public static ReflectorClass RenderLeashKnot;
   public static ReflectorField RenderLeashKnot_leashKnotModel;
   public static ReflectorClass ModelMagmaCube;
   public static ReflectorField ModelMagmaCube_core;
   public static ReflectorField ModelMagmaCube_segments;
   public static ReflectorClass ModelOcelot;
   public static ReflectorFields ModelOcelot_ModelRenderers;
   public static ReflectorClass ModelParrot;
   public static ReflectorFields ModelParrot_ModelRenderers;
   public static ReflectorClass ModelRabbit;
   public static ReflectorFields ModelRabbit_renderers;
   public static ReflectorClass ModelSilverfish;
   public static ReflectorField ModelSilverfish_bodyParts;
   public static ReflectorField ModelSilverfish_wingParts;
   public static ReflectorClass ModelSlime;
   public static ReflectorFields ModelSlime_ModelRenderers;
   public static ReflectorClass ModelSquid;
   public static ReflectorField ModelSquid_body;
   public static ReflectorField ModelSquid_tentacles;
   public static ReflectorClass ModelVex;
   public static ReflectorField ModelVex_leftWing;
   public static ReflectorField ModelVex_rightWing;
   public static ReflectorClass ModelWitch;
   public static ReflectorField ModelWitch_mole;
   public static ReflectorField ModelWitch_hat;
   public static ReflectorClass ModelWither;
   public static ReflectorField ModelWither_bodyParts;
   public static ReflectorField ModelWither_heads;
   public static ReflectorClass ModelWolf;
   public static ReflectorField ModelWolf_tail;
   public static ReflectorField ModelWolf_mane;
   public static ReflectorClass OptiFineClassTransformer;
   public static ReflectorField OptiFineClassTransformer_instance;
   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource;
   public static ReflectorClass RenderBoat;
   public static ReflectorField RenderBoat_modelBoat;
   public static ReflectorClass RenderEvokerFangs;
   public static ReflectorField RenderEvokerFangs_model;
   public static ReflectorClass RenderMinecart;
   public static ReflectorField RenderMinecart_modelMinecart;
   public static ReflectorClass RenderShulkerBullet;
   public static ReflectorField RenderShulkerBullet_model;
   public static ReflectorClass RenderWitherSkull;
   public static ReflectorField RenderWitherSkull_model;
   public static ReflectorClass TileEntityBannerRenderer;
   public static ReflectorField TileEntityBannerRenderer_bannerModel;
   public static ReflectorClass TileEntityBedRenderer;
   public static ReflectorField TileEntityBedRenderer_model;
   public static ReflectorClass TileEntityBeacon;
   public static ReflectorField TileEntityBeacon_customName;
   public static ReflectorClass TileEntityBrewingStand;
   public static ReflectorField TileEntityBrewingStand_customName;
   public static ReflectorClass TileEntityChestRenderer;
   public static ReflectorField TileEntityChestRenderer_simpleChest;
   public static ReflectorField TileEntityChestRenderer_largeChest;
   public static ReflectorClass TileEntityEnchantmentTable;
   public static ReflectorField TileEntityEnchantmentTable_customName;
   public static ReflectorClass TileEntityEnchantmentTableRenderer;
   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook;
   public static ReflectorClass TileEntityEnderChestRenderer;
   public static ReflectorField TileEntityEnderChestRenderer_modelChest;
   public static ReflectorClass TileEntityFurnace;
   public static ReflectorField TileEntityFurnace_customName;
   public static ReflectorClass TileEntityLockableLoot;
   public static ReflectorField TileEntityLockableLoot_customName;
   public static ReflectorClass TileEntityShulkerBoxRenderer;
   public static ReflectorField TileEntityShulkerBoxRenderer_model;
   public static ReflectorClass TileEntitySignRenderer;
   public static ReflectorField TileEntitySignRenderer_model;
   public static ReflectorClass TileEntitySkullRenderer;
   public static ReflectorField TileEntitySkullRenderer_dragonHead;
   public static ReflectorField TileEntitySkullRenderer_skeletonHead;
   public static ReflectorField TileEntitySkullRenderer_humanoidHead;

   public static void callVoid(ReflectorMethod refMethod, Object... params) {
      try {
         Method m = refMethod.getTargetMethod();
         if (m == null) {
            return;
         }

         m.invoke((Object)null, params);
      } catch (Throwable var3) {
         handleException(var3, (Object)null, refMethod, params);
      }

   }

   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return false;
         } else {
            Boolean retVal = (Boolean)method.invoke((Object)null, params);
            return retVal.booleanValue();
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return false;
      }
   }

   public static int callInt(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0;
         } else {
            Integer retVal = (Integer)method.invoke((Object)null, params);
            return retVal.intValue();
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0;
      }
   }

   public static float callFloat(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0F;
         } else {
            Float retVal = (Float)method.invoke((Object)null, params);
            return retVal.floatValue();
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0.0F;
      }
   }

   public static double callDouble(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0D;
         } else {
            Double retVal = (Double)method.invoke((Object)null, params);
            return retVal.doubleValue();
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return 0.0D;
      }
   }

   public static String callString(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            String retVal = (String)method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return null;
      }
   }

   public static Object call(ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            Object retVal = method.invoke((Object)null, params);
            return retVal;
         }
      } catch (Throwable var4) {
         handleException(var4, (Object)null, refMethod, params);
         return null;
      }
   }

   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         if (obj == null) {
            return;
         }

         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return;
         }

         method.invoke(obj, params);
      } catch (Throwable var4) {
         handleException(var4, obj, refMethod, params);
      }

   }

   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return false;
         } else {
            Boolean retVal = (Boolean)method.invoke(obj, params);
            return retVal.booleanValue();
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return false;
      }
   }

   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0;
         } else {
            Integer retVal = (Integer)method.invoke(obj, params);
            return retVal.intValue();
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0;
      }
   }

   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0F;
         } else {
            Float retVal = (Float)method.invoke(obj, params);
            return retVal.floatValue();
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0.0F;
      }
   }

   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return 0.0D;
         } else {
            Double retVal = (Double)method.invoke(obj, params);
            return retVal.doubleValue();
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return 0.0D;
      }
   }

   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            String retVal = (String)method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
      try {
         Method method = refMethod.getTargetMethod();
         if (method == null) {
            return null;
         } else {
            Object retVal = method.invoke(obj, params);
            return retVal;
         }
      } catch (Throwable var5) {
         handleException(var5, obj, refMethod, params);
         return null;
      }
   }

   public static Object getFieldValue(ReflectorField refField) {
      return getFieldValue((Object)null, refField);
   }

   public static Object getFieldValue(Object obj, ReflectorField refField) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return null;
         } else {
            Object value = field.get(obj);
            return value;
         }
      } catch (Throwable var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            boolean value = field.getBoolean(obj);
            return value;
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
         return def;
      }
   }

   public static Object getFieldValue(ReflectorFields refFields, int index) {
      ReflectorField refField = refFields.getReflectorField(index);
      return refField == null ? null : getFieldValue(refField);
   }

   public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
      ReflectorField refField = refFields.getReflectorField(index);
      return refField == null ? null : getFieldValue(obj, refField);
   }

   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            float value = field.getFloat(obj);
            return value;
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
         return def;
      }
   }

   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return def;
         } else {
            int value = field.getInt(obj);
            return value;
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
         return def;
      }
   }

   public static boolean setFieldValue(ReflectorField refField, Object value) {
      return setFieldValue((Object)null, refField, value);
   }

   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return false;
         } else {
            field.set(obj, value);
            return true;
         }
      } catch (Throwable var4) {
         var4.printStackTrace();
         return false;
      }
   }

   public static boolean setFieldValueInt(ReflectorField refField, int value) {
      return setFieldValueInt((Object)null, refField, value);
   }

   public static boolean setFieldValueInt(Object obj, ReflectorField refField, int value) {
      try {
         Field field = refField.getTargetField();
         if (field == null) {
            return false;
         } else {
            field.setInt(obj, value);
            return true;
         }
      } catch (Throwable var4) {
         var4.printStackTrace();
         return false;
      }
   }

   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
      Object event = newInstance(constr, params);
      return event == null ? false : postForgeBusEvent(event);
   }

   public static boolean postForgeBusEvent(Object event) {
      if (event == null) {
         return false;
      } else {
         Object eventBus = getFieldValue(MinecraftForge_EVENT_BUS);
         if (eventBus == null) {
            return false;
         } else {
            Object ret = call(eventBus, EventBus_post, event);
            if (!(ret instanceof Boolean)) {
               return false;
            } else {
               Boolean retBool = (Boolean)ret;
               return retBool.booleanValue();
            }
         }
      }
   }

   public static Object newInstance(ReflectorConstructor constr, Object... params) {
      Constructor c = constr.getTargetConstructor();
      if (c == null) {
         return null;
      } else {
         try {
            Object obj = c.newInstance(params);
            return obj;
         } catch (Throwable var4) {
            handleException(var4, constr, params);
            return null;
         }
      }
   }

   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
      if (pTypes.length != cTypes.length) {
         return false;
      } else {
         for(int i = 0; i < cTypes.length; ++i) {
            Class pType = pTypes[i];
            Class cType = cTypes[i];
            if (pType != cType) {
               return false;
            }
         }

         return true;
      }
   }

   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
      String className = refMethod.getTargetMethod().getDeclaringClass().getName();
      String methodName = refMethod.getTargetMethod().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      .Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + .Config.arrayToString(params) + ") => " + retVal);
   }

   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
      String className = refMethod.getTargetMethod().getDeclaringClass().getName();
      String methodName = refMethod.getTargetMethod().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      .Config.dbg(callType + staticStr + " " + className + "." + methodName + "(" + .Config.arrayToString(params) + ")");
   }

   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
      String className = refField.getTargetField().getDeclaringClass().getName();
      String fieldName = refField.getTargetField().getName();
      String staticStr = "";
      if (isStatic) {
         staticStr = " static";
      }

      .Config.dbg(accessType + staticStr + " " + className + "." + fieldName + " => " + val);
   }

   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
      if (e instanceof InvocationTargetException) {
         Throwable cause = e.getCause();
         if (cause instanceof RuntimeException) {
            RuntimeException causeRuntime = (RuntimeException)cause;
            throw causeRuntime;
         } else {
            e.printStackTrace();
         }
      } else {
         if (e instanceof IllegalArgumentException) {
            .Config.warn("*** IllegalArgumentException ***");
            .Config.warn("Method: " + refMethod.getTargetMethod());
            .Config.warn("Object: " + obj);
            .Config.warn("Parameter classes: " + .Config.arrayToString(getClasses(params)));
            .Config.warn("Parameters: " + .Config.arrayToString(params));
         }

         .Config.warn("*** Exception outside of method ***");
         .Config.warn("Method deactivated: " + refMethod.getTargetMethod());
         refMethod.deactivate();
         e.printStackTrace();
      }
   }

   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
      if (e instanceof InvocationTargetException) {
         e.printStackTrace();
      } else {
         if (e instanceof IllegalArgumentException) {
            .Config.warn("*** IllegalArgumentException ***");
            .Config.warn("Constructor: " + refConstr.getTargetConstructor());
            .Config.warn("Parameter classes: " + .Config.arrayToString(getClasses(params)));
            .Config.warn("Parameters: " + .Config.arrayToString(params));
         }

         .Config.warn("*** Exception outside of constructor ***");
         .Config.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
         refConstr.deactivate();
         e.printStackTrace();
      }
   }

   private static Object[] getClasses(Object[] objs) {
      if (objs == null) {
         return new Class[0];
      } else {
         Class[] classes = new Class[objs.length];

         for(int i = 0; i < classes.length; ++i) {
            Object obj = objs[i];
            if (obj != null) {
               classes[i] = obj.getClass();
            }
         }

         return classes;
      }
   }

   private static ReflectorField[] getReflectorFields(ReflectorClass parentClass, Class fieldType, int count) {
      ReflectorField[] rfs = new ReflectorField[count];

      for(int i = 0; i < rfs.length; ++i) {
         rfs[i] = new ReflectorField(parentClass, fieldType, i);
      }

      return rfs;
   }

   private static boolean logEntry(String str) {
      .Config.dbg(str);
      return true;
   }

   static {
      Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
      BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
      BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
      BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
      ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
      ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[]{amn.class, oq.class});
      CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
      CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
      DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
      DimensionManager_createProviderFor = new ReflectorMethod(DimensionManager, "createProviderFor");
      DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
      DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
      DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[]{blk.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
      DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
      DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[]{blk.class, Integer.TYPE, Integer.TYPE, Float.TYPE});
      EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
      EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[]{buq.class, vg.class, awt.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
      EntityViewRenderEvent_CameraSetup_getRoll = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getRoll");
      EntityViewRenderEvent_CameraSetup_getPitch = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getPitch");
      EntityViewRenderEvent_CameraSetup_getYaw = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getYaw");
      EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
      EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[]{buq.class, vg.class, awt.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE});
      EntityViewRenderEvent_FogColors_getRed = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getRed");
      EntityViewRenderEvent_FogColors_getGreen = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getGreen");
      EntityViewRenderEvent_FogColors_getBlue = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getBlue");
      EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
      EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(EntityViewRenderEvent_RenderFogEvent, new Class[]{buq.class, vg.class, awt.class, Double.TYPE, Integer.TYPE, Float.TYPE});
      Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
      Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
      EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
      EventBus_post = new ReflectorMethod(EventBus, "post");
      Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
      Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
      Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
      Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
      ExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.ExtendedBlockState");
      ExtendedBlockState_Constructor = new ReflectorConstructor(ExtendedBlockState, new Class[]{aow.class, axj[].class, IUnlistedProperty[].class});
      FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
      FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
      FMLClientHandler_handleLoadingScreen = new ReflectorMethod(FMLClientHandler, "handleLoadingScreen");
      FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
      FMLClientHandler_renderClouds = new ReflectorMethod(FMLClientHandler, "renderClouds");
      FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
      FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
      FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
      FMLCommonHandler_callFuture = new ReflectorMethod(FMLCommonHandler, "callFuture");
      FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
      FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
      FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
      FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
      FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
      ActiveRenderInfo = new ReflectorClass(bhv.class);
      ActiveRenderInfo_getCameraPosition = new ReflectorMethod(ActiveRenderInfo, "getCameraPosition");
      ActiveRenderInfo_updateRenderInfo2 = new ReflectorMethod(ActiveRenderInfo, "updateRenderInfo", new Class[]{vg.class, Boolean.TYPE});
      ForgeBiome = new ReflectorClass(anh.class);
      ForgeBiome_getWaterColorMultiplier = new ReflectorMethod(ForgeBiome, "getWaterColorMultiplier");
      ForgeBiomeSpawnListEntry = new ReflectorClass(c.class);
      ForgeBiomeSpawnListEntry_newInstance = new ReflectorMethod(ForgeBiomeSpawnListEntry, "newInstance");
      ForgeBlock = new ReflectorClass(aow.class);
      ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
      ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
      ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
      ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer", new Class[]{awt.class, amm.class});
      ForgeBlock_doesSideBlockRendering = new ReflectorMethod(ForgeBlock, "doesSideBlockRendering");
      ForgeBlock_doesSideBlockChestOpening = new ReflectorMethod(ForgeBlock, "doesSideBlockChestOpening");
      ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
      ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
      ForgeBlock_getFogColor = new ReflectorMethod(ForgeBlock, "getFogColor");
      ForgeBlock_getLightOpacity = new ReflectorMethod(ForgeBlock, "getLightOpacity", new Class[]{awt.class, amy.class, et.class});
      ForgeBlock_getLightValue = new ReflectorMethod(ForgeBlock, "getLightValue", new Class[]{awt.class, amy.class, et.class});
      ForgeBlock_getSoundType = new ReflectorMethod(ForgeBlock, "getSoundType", new Class[]{awt.class, amu.class, et.class, vg.class});
      ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[]{awt.class});
      ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
      ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
      ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
      ForgeBlock_isSideSolid = new ReflectorMethod(ForgeBlock, "isSideSolid");
      ForgeIBakedModel = new ReflectorClass(cfy.class);
      ForgeIBakedModel_isAmbientOcclusion2 = new ReflectorMethod(ForgeIBakedModel, "isAmbientOcclusion", new Class[]{awt.class});
      ForgeIBlockProperties = new ReflectorClass(aws.class);
      ForgeIBlockProperties_getLightValue2 = new ReflectorMethod(ForgeIBlockProperties, "getLightValue", new Class[]{amy.class, et.class});
      ForgeChunkCache = new ReflectorClass(and.class);
      ForgeChunkCache_isSideSolid = new ReflectorMethod(ForgeChunkCache, "isSideSolid");
      ForgeEntity = new ReflectorClass(vg.class);
      ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
      ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
      ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
      ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
      ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
      ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
      ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn", new Class[]{vq.class, amu.class, Float.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE});
      ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
      ForgeEventFactory_doSpecialSpawn = new ReflectorMethod(ForgeEventFactory, "doSpecialSpawn", new Class[]{vq.class, amu.class, Float.TYPE, Float.TYPE, Float.TYPE});
      ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
      ForgeEventFactory_getMobGriefingEvent = new ReflectorMethod(ForgeEventFactory, "getMobGriefingEvent");
      ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
      ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
      ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
      ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
      ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
      ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
      ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
      ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
      ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
      ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
      ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
      ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
      ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
      ForgeHooksClient_applyTransform_M4 = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[]{Matrix4f.class, Optional.class});
      ForgeHooksClient_applyTransform_MR = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[]{cfz.class, Optional.class});
      ForgeHooksClient_applyUVLock = new ReflectorMethod(ForgeHooksClient, "applyUVLock");
      ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
      ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
      ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
      ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
      ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
      ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
      ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
      ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
      ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[]{cfz.class});
      ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
      ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
      ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
      ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
      ForgeHooksClient_onScreenshot = new ReflectorMethod(ForgeHooksClient, "onScreenshot");
      ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
      ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
      ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
      ForgeHooksClient_putQuadColor = new ReflectorMethod(ForgeHooksClient, "putQuadColor");
      ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
      ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
      ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
      ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
      ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
      ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
      ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
      ForgeItem = new ReflectorClass(ain.class);
      ForgeItem_delegate = new ReflectorField(ForgeItem, "delegate");
      ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
      ForgeItem_getEquipmentSlot = new ReflectorMethod(ForgeItem, "getEquipmentSlot");
      ForgeItem_getTileEntityItemStackRenderer = new ReflectorMethod(ForgeItem, "getTileEntityItemStackRenderer");
      ForgeItem_getRGBDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getRGBDurabilityForDisplay");
      ForgeItem_isShield = new ReflectorMethod(ForgeItem, "isShield");
      ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
      ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
      ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
      ForgeItemArmor = new ReflectorClass(agv.class);
      ForgeItemArmor_hasOverlay = new ReflectorMethod(ForgeItemArmor, "hasOverlay");
      ForgeKeyBinding = new ReflectorClass(bhy.class);
      ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
      ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
      ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
      ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
      ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
      ForgeModelBlockDefinition = new ReflectorClass(bvv.class);
      ForgeModelBlockDefinition_parseFromReader2 = new ReflectorMethod(ForgeModelBlockDefinition, "parseFromReader", new Class[]{Reader.class, nf.class});
      ForgePotion = new ReflectorClass(uz.class);
      ForgePotion_shouldRenderHUD = ForgePotion.makeMethod("shouldRenderHUD");
      ForgePotion_renderHUDEffect = ForgePotion.makeMethod("renderHUDEffect");
      ForgePotionEffect = new ReflectorClass(va.class);
      ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
      ForgeTileEntity = new ReflectorClass(avj.class);
      ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
      ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
      ForgeTileEntity_hasFastRenderer = new ReflectorMethod(ForgeTileEntity, "hasFastRenderer");
      ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
      ForgeVertexFormatElementEnumUseage = new ReflectorClass(b.class);
      ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
      ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
      ForgeWorld = new ReflectorClass(amu.class);
      ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[]{vr.class, Boolean.TYPE});
      ForgeWorld_getPerWorldStorage = new ReflectorMethod(ForgeWorld, "getPerWorldStorage");
      ForgeWorld_initCapabilities = new ReflectorMethod(ForgeWorld, "initCapabilities");
      ForgeWorldProvider = new ReflectorClass(aym.class);
      ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
      ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
      ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
      ForgeWorldProvider_getLightmapColors = new ReflectorMethod(ForgeWorldProvider, "getLightmapColors");
      ForgeWorldProvider_getSaveFolder = new ReflectorMethod(ForgeWorldProvider, "getSaveFolder");
      GuiModList = new ReflectorClass("net.minecraftforge.fml.client.GuiModList");
      GuiModList_Constructor = new ReflectorConstructor(GuiModList, new Class[]{blk.class});
      IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
      IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
      IModel = new ReflectorClass("net.minecraftforge.client.model.IModel");
      IModel_getTextures = new ReflectorMethod(IModel, "getTextures");
      IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
      IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
      ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
      ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[]{cgc.class});
      KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
      KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
      KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
      KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
      KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
      Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
      Launch_blackboard = new ReflectorField(Launch, "blackboard");
      LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
      LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
      LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
      LightUtil_renderQuadColor = new ReflectorMethod(LightUtil, "renderQuadColor");
      LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
      Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
      Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
      Loader_instance = new ReflectorMethod(Loader, "instance");
      MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
      MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
      MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
      MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
      MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
      ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
      ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
      ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
      ModelLoader_stateModels = new ReflectorField(ModelLoader, "stateModels");
      ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
      ModelLoader_getInventoryVariant = new ReflectorMethod(ModelLoader, "getInventoryVariant");
      ModelLoader_VanillaLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader$VanillaLoader", true);
      ModelLoader_VanillaLoader_INSTANCE = new ReflectorField(ModelLoader_VanillaLoader, "INSTANCE", true);
      ModelLoader_VanillaLoader_loadModel = new ReflectorMethod(ModelLoader_VanillaLoader, "loadModel", (Class[])null, true);
      ModelLoaderRegistry = new ReflectorClass("net.minecraftforge.client.model.ModelLoaderRegistry", true);
      ModelLoaderRegistry_textures = new ReflectorField(ModelLoaderRegistry, "textures", true);
      NotificationModUpdateScreen = new ReflectorClass("net.minecraftforge.client.gui.NotificationModUpdateScreen");
      NotificationModUpdateScreen_init = new ReflectorMethod(NotificationModUpdateScreen, "init");
      RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
      RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
      RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
      RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[]{bzf.class, Map.class});
      RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
      RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[]{acb.class, bzv.class});
      RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
      RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[]{vp.class, caa.class, Float.TYPE, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
      RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[]{vp.class, caa.class, Float.TYPE, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
      RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[]{vp.class, caa.class, Double.TYPE, Double.TYPE, Double.TYPE});
      RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
      RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[]{vp.class, caa.class, Double.TYPE, Double.TYPE, Double.TYPE});
      ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
      ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
      ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
      ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
      SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
      WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
      WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[]{amu.class});
      logVanilla = logEntry("*** Reflector Vanilla ***");
      ChunkProviderClient = new ReflectorClass(brx.class);
      ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, Long2ObjectMap.class);
      EntityVillager = new ReflectorClass(ady.class);
      EntityVillager_careerId = new ReflectorField(new FieldLocatorTypes(ady.class, new Class[0], Integer.TYPE, new Class[]{Integer.TYPE, Boolean.TYPE, Boolean.TYPE, uk.class}, "EntityVillager.careerId"));
      EntityVillager_careerLevel = new ReflectorField(new FieldLocatorTypes(ady.class, new Class[]{Integer.TYPE}, Integer.TYPE, new Class[]{Boolean.TYPE, Boolean.TYPE, uk.class}, "EntityVillager.careerLevel"));
      GuiBeacon = new ReflectorClass(bmi.class);
      GuiBeacon_tileBeacon = new ReflectorField(GuiBeacon, tv.class);
      GuiBrewingStand = new ReflectorClass(bmk.class);
      GuiBrewingStand_tileBrewingStand = new ReflectorField(GuiBrewingStand, tv.class);
      GuiChest = new ReflectorClass(bmm.class);
      GuiChest_lowerChestInventory = new ReflectorField(GuiChest, tv.class, 1);
      GuiEnchantment = new ReflectorClass(bmt.class);
      GuiEnchantment_nameable = new ReflectorField(GuiEnchantment, ui.class);
      GuiFurnace = new ReflectorClass(bmu.class);
      GuiFurnace_tileFurnace = new ReflectorField(GuiFurnace, tv.class);
      GuiHopper = new ReflectorClass(bmv.class);
      GuiHopper_hopperInventory = new ReflectorField(GuiHopper, tv.class, 1);
      GuiMainMenu = new ReflectorClass(blr.class);
      GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
      GuiShulkerBox = new ReflectorClass(bna.class);
      GuiShulkerBox_inventory = new ReflectorField(GuiShulkerBox, tv.class);
      ItemOverride = new ReflectorClass(bvz.class);
      ItemOverride_mapResourceValues = new ReflectorField(ItemOverride, Map.class);
      LegacyV2Adapter = new ReflectorClass(ces.class);
      LegacyV2Adapter_pack = new ReflectorField(LegacyV2Adapter, cer.class);
      Minecraft = new ReflectorClass(bib.class);
      Minecraft_defaultResourcePack = new ReflectorField(Minecraft, ceg.class);
      Minecraft_actionKeyF3 = new ReflectorField(new FieldLocatorActionKeyF3());
      ModelHumanoidHead = new ReflectorClass(bpw.class);
      ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, brs.class);
      ModelBat = new ReflectorClass(bpg.class);
      ModelBat_ModelRenderers = new ReflectorFields(ModelBat, brs.class, 6);
      ModelBlaze = new ReflectorClass(bpi.class);
      ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, brs.class);
      ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, brs[].class);
      ModelDragon = new ReflectorClass(brn.class);
      ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, brs.class, 12);
      ModelEnderCrystal = new ReflectorClass(bro.class);
      ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, brs.class, 3);
      RenderEnderCrystal = new ReflectorClass(bzb.class);
      RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, bqf.class, 0);
      RenderEnderCrystal_modelEnderCrystalNoBase = new ReflectorField(RenderEnderCrystal, bqf.class, 1);
      ModelEnderMite = new ReflectorClass(bpr.class);
      ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, brs[].class);
      ModelEvokerFangs = new ReflectorClass(bps.class);
      ModelEvokerFangs_ModelRenderers = new ReflectorFields(ModelEvokerFangs, brs.class, 3);
      ModelGhast = new ReflectorClass(bpt.class);
      ModelGhast_body = new ReflectorField(ModelGhast, brs.class);
      ModelGhast_tentacles = new ReflectorField(ModelGhast, brs[].class);
      ModelGuardian = new ReflectorClass(bpu.class);
      ModelGuardian_body = new ReflectorField(ModelGuardian, brs.class, 0);
      ModelGuardian_eye = new ReflectorField(ModelGuardian, brs.class, 1);
      ModelGuardian_spines = new ReflectorField(ModelGuardian, brs[].class, 0);
      ModelGuardian_tail = new ReflectorField(ModelGuardian, brs[].class, 1);
      ModelDragonHead = new ReflectorClass(brm.class);
      ModelDragonHead_head = new ReflectorField(ModelDragonHead, brs.class, 0);
      ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, brs.class, 1);
      ModelHorse = new ReflectorClass(bpv.class);
      ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, brs.class, 39);
      RenderLeashKnot = new ReflectorClass(bzy.class);
      RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, bqb.class);
      ModelMagmaCube = new ReflectorClass(bqa.class);
      ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, brs.class);
      ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, brs[].class);
      ModelOcelot = new ReflectorClass(bqg.class);
      ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, brs.class, 8);
      ModelParrot = new ReflectorClass(bqh.class);
      ModelParrot_ModelRenderers = new ReflectorFields(ModelParrot, brs.class, 11);
      ModelRabbit = new ReflectorClass(bqn.class);
      ModelRabbit_renderers = new ReflectorFields(ModelRabbit, brs.class, 12);
      ModelSilverfish = new ReflectorClass(bqu.class);
      ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, brs[].class, 0);
      ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, brs[].class, 1);
      ModelSlime = new ReflectorClass(bqy.class);
      ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, brs.class, 4);
      ModelSquid = new ReflectorClass(brb.class);
      ModelSquid_body = new ReflectorField(ModelSquid, brs.class);
      ModelSquid_tentacles = new ReflectorField(ModelSquid, brs[].class);
      ModelVex = new ReflectorClass(bre.class);
      ModelVex_leftWing = new ReflectorField(ModelVex, brs.class, 0);
      ModelVex_rightWing = new ReflectorField(ModelVex, brs.class, 1);
      ModelWitch = new ReflectorClass(bri.class);
      ModelWitch_mole = new ReflectorField(ModelWitch, brs.class, 0);
      ModelWitch_hat = new ReflectorField(ModelWitch, brs.class, 1);
      ModelWither = new ReflectorClass(brj.class);
      ModelWither_bodyParts = new ReflectorField(ModelWither, brs[].class, 0);
      ModelWither_heads = new ReflectorField(ModelWither, brs[].class, 1);
      ModelWolf = new ReflectorClass(brk.class);
      ModelWolf_tail = new ReflectorField(ModelWolf, brs.class, 6);
      ModelWolf_mane = new ReflectorField(ModelWolf, brs.class, 7);
      OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
      OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
      OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
      RenderBoat = new ReflectorClass(byt.class);
      RenderBoat_modelBoat = new ReflectorField(RenderBoat, bqf.class);
      RenderEvokerFangs = new ReflectorClass(bzh.class);
      RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, bps.class);
      RenderMinecart = new ReflectorClass(cad.class);
      RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, bqf.class);
      RenderShulkerBullet = new ReflectorClass(cap.class);
      RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, bqr.class);
      RenderWitherSkull = new ReflectorClass(cbl.class);
      RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, bqv.class);
      TileEntityBannerRenderer = new ReflectorClass(bwu.class);
      TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, bpf.class);
      TileEntityBedRenderer = new ReflectorClass(bww.class);
      TileEntityBedRenderer_model = new ReflectorField(TileEntityBedRenderer, bph.class);
      TileEntityBeacon = new ReflectorClass(avh.class);
      TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, String.class);
      TileEntityBrewingStand = new ReflectorClass(avk.class);
      TileEntityBrewingStand_customName = new ReflectorField(TileEntityBrewingStand, String.class);
      TileEntityChestRenderer = new ReflectorClass(bwz.class);
      TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, bpl.class, 0);
      TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, bpl.class, 1);
      TileEntityEnchantmentTable = new ReflectorClass(avr.class);
      TileEntityEnchantmentTable_customName = new ReflectorField(TileEntityEnchantmentTable, String.class);
      TileEntityEnchantmentTableRenderer = new ReflectorClass(bxa.class);
      TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, bpk.class);
      TileEntityEnderChestRenderer = new ReflectorClass(bxb.class);
      TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, bpl.class);
      TileEntityFurnace = new ReflectorClass(avu.class);
      TileEntityFurnace_customName = new ReflectorField(TileEntityFurnace, String.class);
      TileEntityLockableLoot = new ReflectorClass(awa.class);
      TileEntityLockableLoot_customName = new ReflectorField(TileEntityLockableLoot, String.class);
      TileEntityShulkerBoxRenderer = new ReflectorClass(bxe.class);
      TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, bqs.class);
      TileEntitySignRenderer = new ReflectorClass(bxf.class);
      TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, bqt.class);
      TileEntitySkullRenderer = new ReflectorClass(bxg.class);
      TileEntitySkullRenderer_dragonHead = new ReflectorField(TileEntitySkullRenderer, brm.class, 0);
      TileEntitySkullRenderer_skeletonHead = new ReflectorField(TileEntitySkullRenderer, bqv.class, 0);
      TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, bqv.class, 1);
   }
}
