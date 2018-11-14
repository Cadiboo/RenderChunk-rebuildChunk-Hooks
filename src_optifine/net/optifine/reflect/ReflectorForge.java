package net.optifine.reflect;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReflectorForge {
   public static Object EVENT_RESULT_ALLOW;
   public static Object EVENT_RESULT_DENY;
   public static Object EVENT_RESULT_DEFAULT;

   public static void FMLClientHandler_trackBrokenTexture(nf loc, String message) {
      if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
         Object instance = Reflector.call(Reflector.FMLClientHandler_instance);
         Reflector.call(instance, Reflector.FMLClientHandler_trackBrokenTexture, loc, message);
      }
   }

   public static void FMLClientHandler_trackMissingTexture(nf loc) {
      if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
         Object instance = Reflector.call(Reflector.FMLClientHandler_instance);
         Reflector.call(instance, Reflector.FMLClientHandler_trackMissingTexture, loc);
      }
   }

   public static void putLaunchBlackboard(String key, Object value) {
      Map blackboard = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
      if (blackboard != null) {
         blackboard.put(key, value);
      }
   }

   public static boolean renderFirstPersonHand(buy renderGlobal, float partialTicks, int pass) {
      return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, renderGlobal, partialTicks, pass);
   }

   public static InputStream getOptiFineResourceStream(String path) {
      if (!Reflector.OptiFineClassTransformer_instance.exists()) {
         return null;
      } else {
         Object instance = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
         if (instance == null) {
            return null;
         } else {
            if (path.startsWith("/")) {
               path = path.substring(1);
            }

            byte[] bytes = (byte[])((byte[])Reflector.call(instance, Reflector.OptiFineClassTransformer_getOptiFineResource, path));
            if (bytes == null) {
               return null;
            } else {
               InputStream in = new ByteArrayInputStream(bytes);
               return in;
            }
         }
      }
   }

   public static boolean blockHasTileEntity(awt state) {
      aow block = state.u();
      return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.l() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, state);
   }

   public static boolean isItemDamaged(aip stack) {
      return !Reflector.ForgeItem_showDurabilityBar.exists() ? stack.h() : Reflector.callBoolean(stack.c(), Reflector.ForgeItem_showDurabilityBar, stack);
   }

   public static boolean armorHasOverlay(agv itemArmor, aip itemStack) {
      if (Reflector.ForgeItemArmor_hasOverlay.exists()) {
         return Reflector.callBoolean(itemArmor, Reflector.ForgeItemArmor_hasOverlay, itemStack);
      } else {
         int i = itemArmor.c(itemStack);
         return i != 16777215;
      }
   }

   public static int getLightValue(awt stateIn, amy worldIn, et posIn) {
      return Reflector.ForgeIBlockProperties_getLightValue2.exists() ? Reflector.callInt(stateIn, Reflector.ForgeIBlockProperties_getLightValue2, worldIn, posIn) : stateIn.d();
   }

   public static bev getMapData(aiw itemMap, aip stack, amu world) {
      return Reflector.ForgeHooksClient.exists() ? ((aiw)stack.c()).a(stack, world) : itemMap.a(stack, world);
   }

   public static String[] getForgeModIds() {
      if (!Reflector.Loader.exists()) {
         return new String[0];
      } else {
         Object loader = Reflector.call(Reflector.Loader_instance);
         List listActiveMods = (List)Reflector.call(loader, Reflector.Loader_getActiveModList);
         if (listActiveMods == null) {
            return new String[0];
         } else {
            List listModIds = new ArrayList();
            Iterator it = listActiveMods.iterator();

            while(it.hasNext()) {
               Object modContainer = it.next();
               if (Reflector.ModContainer.isInstance(modContainer)) {
                  String modId = Reflector.callString(modContainer, Reflector.ModContainer_getModId);
                  if (modId != null) {
                     listModIds.add(modId);
                  }
               }
            }

            String[] modIds = (String[])((String[])listModIds.toArray(new String[listModIds.size()]));
            return modIds;
         }
      }
   }

   public static boolean canEntitySpawn(vq entityliving, amu world, float x, float y, float z) {
      Object canSpawn = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, entityliving, world, x, y, z, false);
      return canSpawn == EVENT_RESULT_ALLOW || canSpawn == EVENT_RESULT_DEFAULT && entityliving.P() && entityliving.Q();
   }

   public static boolean doSpecialSpawn(vq entityliving, amu world, float x, int y, float z) {
      return Reflector.ForgeEventFactory_doSpecialSpawn.exists() ? Reflector.callBoolean(Reflector.ForgeEventFactory_doSpecialSpawn, entityliving, world, x, y, z) : false;
   }

   public static boolean isAmbientOcclusion(cfy model, awt state) {
      return Reflector.ForgeIBakedModel_isAmbientOcclusion2.exists() ? Reflector.callBoolean(model, Reflector.ForgeIBakedModel_isAmbientOcclusion2, state) : model.a();
   }

   static {
      EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
      EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
      EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
   }
}
