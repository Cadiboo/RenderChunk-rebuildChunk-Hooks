package net.optifine.util;

import net.optifine.reflect.Reflector;

public class TileEntityUtils {
   public static String getTileEntityName(amy blockAccess, et blockPos) {
      avj te = blockAccess.r(blockPos);
      return getTileEntityName(te);
   }

   public static String getTileEntityName(avj te) {
      if (!(te instanceof ui)) {
         return null;
      } else {
         ui iwn = (ui)te;
         updateTileEntityName(te);
         return !iwn.n_() ? null : iwn.h_();
      }
   }

   public static void updateTileEntityName(avj te) {
      et pos = te.w();
      String name = getTileEntityRawName(te);
      if (name == null) {
         String nameServer = getServerTileEntityRawName(pos);
         nameServer = .Config.normalize(nameServer);
         setTileEntityRawName(te, nameServer);
      }
   }

   public static String getServerTileEntityRawName(et blockPos) {
      avj tes = IntegratedServerUtils.getTileEntity(blockPos);
      return tes == null ? null : getTileEntityRawName(tes);
   }

   public static String getTileEntityRawName(avj te) {
      if (te instanceof avh) {
         return (String)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName);
      } else if (te instanceof avk) {
         return (String)Reflector.getFieldValue(te, Reflector.TileEntityBrewingStand_customName);
      } else if (te instanceof avr) {
         return (String)Reflector.getFieldValue(te, Reflector.TileEntityEnchantmentTable_customName);
      } else if (te instanceof avu) {
         return (String)Reflector.getFieldValue(te, Reflector.TileEntityFurnace_customName);
      } else {
         return te instanceof awa ? (String)Reflector.getFieldValue(te, Reflector.TileEntityLockableLoot_customName) : null;
      }
   }

   public static boolean setTileEntityRawName(avj te, String name) {
      if (te instanceof avh) {
         return Reflector.setFieldValue(te, Reflector.TileEntityBeacon_customName, name);
      } else if (te instanceof avk) {
         return Reflector.setFieldValue(te, Reflector.TileEntityBrewingStand_customName, name);
      } else if (te instanceof avr) {
         return Reflector.setFieldValue(te, Reflector.TileEntityEnchantmentTable_customName, name);
      } else if (te instanceof avu) {
         return Reflector.setFieldValue(te, Reflector.TileEntityFurnace_customName, name);
      } else {
         return te instanceof awa ? Reflector.setFieldValue(te, Reflector.TileEntityLockableLoot_customName, name) : false;
      }
   }
}
