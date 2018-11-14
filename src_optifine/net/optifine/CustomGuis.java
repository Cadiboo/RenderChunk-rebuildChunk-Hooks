package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import net.optifine.override.PlayerControllerOF;
import net.optifine.util.ResUtils;

public class CustomGuis {
   private static bib mc = .Config.getMinecraft();
   private static PlayerControllerOF playerControllerOF = null;
   private static CustomGuiProperties[][] guiProperties = (CustomGuiProperties[][])null;
   public static boolean isChristmas = isChristmas();

   public static nf getTextureLocation(nf loc) {
      if (guiProperties == null) {
         return loc;
      } else {
         blk screen = mc.m;
         if (!(screen instanceof bmg)) {
            return loc;
         } else if (loc.b().equals("minecraft") && loc.a().startsWith("textures/gui/")) {
            if (playerControllerOF == null) {
               return loc;
            } else {
               amy world = mc.f;
               if (world == null) {
                  return loc;
               } else if (screen instanceof bmp) {
                  return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.h.c(), world, loc, screen);
               } else if (screen instanceof bmx) {
                  return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.h.c(), world, loc, screen);
               } else {
                  et pos = playerControllerOF.getLastClickBlockPos();
                  if (pos != null) {
                     if (screen instanceof bmh) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, pos, world, loc, screen);
                     }

                     if (screen instanceof bmi) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, pos, world, loc, screen);
                     }

                     if (screen instanceof bmk) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, pos, world, loc, screen);
                     }

                     if (screen instanceof bmm) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, pos, world, loc, screen);
                     }

                     if (screen instanceof bmn) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, pos, world, loc, screen);
                     }

                     if (screen instanceof bmq) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, pos, world, loc, screen);
                     }

                     if (screen instanceof bmt) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, pos, world, loc, screen);
                     }

                     if (screen instanceof bmu) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, pos, world, loc, screen);
                     }

                     if (screen instanceof bmv) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, pos, world, loc, screen);
                     }

                     if (screen instanceof bna) {
                        return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, pos, world, loc, screen);
                     }
                  }

                  vg entity = playerControllerOF.getLastClickEntity();
                  if (entity != null) {
                     if (screen instanceof bmw) {
                        return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, world, loc);
                     }

                     if (screen instanceof bmy) {
                        return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, world, loc);
                     }
                  }

                  return loc;
               }
            }
         } else {
            return loc;
         }
      }
   }

   private static nf getTexturePos(CustomGuiProperties.EnumContainer container, et pos, amy blockAccess, nf loc, blk screen) {
      CustomGuiProperties[] props = guiProperties[container.ordinal()];
      if (props == null) {
         return loc;
      } else {
         for(int i = 0; i < props.length; ++i) {
            CustomGuiProperties prop = props[i];
            if (prop.matchesPos(container, pos, blockAccess, screen)) {
               return prop.getTextureLocation(loc);
            }
         }

         return loc;
      }
   }

   private static nf getTextureEntity(CustomGuiProperties.EnumContainer container, vg entity, amy blockAccess, nf loc) {
      CustomGuiProperties[] props = guiProperties[container.ordinal()];
      if (props == null) {
         return loc;
      } else {
         for(int i = 0; i < props.length; ++i) {
            CustomGuiProperties prop = props[i];
            if (prop.matchesEntity(container, entity, blockAccess)) {
               return prop.getTextureLocation(loc);
            }
         }

         return loc;
      }
   }

   public static void update() {
      guiProperties = (CustomGuiProperties[][])null;
      if (.Config.isCustomGuis()) {
         List listProps = new ArrayList();
         cer[] rps = .Config.getResourcePacks();

         for(int i = rps.length - 1; i >= 0; --i) {
            cer rp = rps[i];
            update(rp, listProps);
         }

         guiProperties = propertyListToArray(listProps);
      }
   }

   private static CustomGuiProperties[][] propertyListToArray(List listProps) {
      if (listProps.isEmpty()) {
         return (CustomGuiProperties[][])null;
      } else {
         CustomGuiProperties[][] cgps = new CustomGuiProperties[CustomGuiProperties.EnumContainer.values().length][];

         for(int i = 0; i < cgps.length; ++i) {
            if (listProps.size() > i) {
               List subList = (List)listProps.get(i);
               if (subList != null) {
                  CustomGuiProperties[] subArr = (CustomGuiProperties[])((CustomGuiProperties[])subList.toArray(new CustomGuiProperties[subList.size()]));
                  cgps[i] = subArr;
               }
            }
         }

         return cgps;
      }
   }

   private static void update(cer rp, List listProps) {
      String[] paths = ResUtils.collectFiles(rp, (String)"optifine/gui/container/", (String)".properties", (String[])null);
      Arrays.sort(paths);

      for(int i = 0; i < paths.length; ++i) {
         String name = paths[i];
         .Config.dbg("CustomGuis: " + name);

         try {
            nf locFile = new nf(name);
            InputStream in = rp.a(locFile);
            if (in == null) {
               .Config.warn("CustomGuis file not found: " + name);
            } else {
               Properties props = new Properties();
               props.load(in);
               in.close();
               CustomGuiProperties cgp = new CustomGuiProperties(props, name);
               if (cgp.isValid(name)) {
                  addToList(cgp, listProps);
               }
            }
         } catch (FileNotFoundException var9) {
            .Config.warn("CustomGuis file not found: " + name);
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

   }

   private static void addToList(CustomGuiProperties cgp, List listProps) {
      if (cgp.getContainer() == null) {
         warn("Invalid container: " + cgp.getContainer());
      } else {
         int indexContainer = cgp.getContainer().ordinal();

         while(listProps.size() <= indexContainer) {
            listProps.add((Object)null);
         }

         List subList = (List)listProps.get(indexContainer);
         if (subList == null) {
            subList = new ArrayList();
            listProps.set(indexContainer, subList);
         }

         ((List)subList).add(cgp);
      }
   }

   public static PlayerControllerOF getPlayerControllerOF() {
      return playerControllerOF;
   }

   public static void setPlayerControllerOF(PlayerControllerOF playerControllerOF) {
      playerControllerOF = playerControllerOF;
   }

   private static boolean isChristmas() {
      Calendar calendar = Calendar.getInstance();
      return calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26;
   }

   private static void warn(String str) {
      .Config.warn("[CustomGuis] " + str);
   }
}
