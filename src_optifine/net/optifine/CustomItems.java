package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.optifine.config.NbtTagValue;
import net.optifine.render.Blender;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class CustomItems {
   private static CustomItemProperties[][] itemProperties = (CustomItemProperties[][])null;
   private static CustomItemProperties[][] enchantmentProperties = (CustomItemProperties[][])null;
   private static Map mapPotionIds = null;
   private static bvy itemModelGenerator = new bvy();
   private static boolean useGlint = true;
   private static boolean renderOffHand = false;
   public static final int MASK_POTION_SPLASH = 16384;
   public static final int MASK_POTION_NAME = 63;
   public static final int MASK_POTION_EXTENDED = 64;
   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
   public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
   public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
   public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
   private static final Map mapPotionDamages = makeMapPotionDamages();
   private static final String TYPE_POTION_NORMAL = "normal";
   private static final String TYPE_POTION_SPLASH = "splash";
   private static final String TYPE_POTION_LINGER = "linger";

   public static void update() {
      itemProperties = (CustomItemProperties[][])null;
      enchantmentProperties = (CustomItemProperties[][])null;
      useGlint = true;
      if (.Config.isCustomItems()) {
         readCitProperties("mcpatcher/cit.properties");
         cer[] rps = .Config.getResourcePacks();

         for(int i = rps.length - 1; i >= 0; --i) {
            cer rp = rps[i];
            update(rp);
         }

         update(.Config.getDefaultResourcePack());
         if (itemProperties.length <= 0) {
            itemProperties = (CustomItemProperties[][])null;
         }

         if (enchantmentProperties.length <= 0) {
            enchantmentProperties = (CustomItemProperties[][])null;
         }

      }
   }

   private static void readCitProperties(String fileName) {
      try {
         nf loc = new nf(fileName);
         InputStream in = .Config.getResourceStream(loc);
         if (in == null) {
            return;
         }

         .Config.dbg("CustomItems: Loading " + fileName);
         Properties props = new Properties();
         props.load(in);
         in.close();
         useGlint = .Config.parseBoolean(props.getProperty("useGlint"), true);
      } catch (FileNotFoundException var4) {
         return;
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private static void update(cer rp) {
      String[] names = ResUtils.collectFiles(rp, (String)"mcpatcher/cit/", (String)".properties", (String[])null);
      Map mapAutoProperties = makeAutoImageProperties(rp);
      if (mapAutoProperties.size() > 0) {
         Set keySetAuto = mapAutoProperties.keySet();
         String[] keysAuto = (String[])((String[])keySetAuto.toArray(new String[keySetAuto.size()]));
         names = (String[])((String[]).Config.addObjectsToArray(names, keysAuto));
      }

      Arrays.sort(names);
      List itemList = makePropertyList(itemProperties);
      List enchantmentList = makePropertyList(enchantmentProperties);

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         .Config.dbg("CustomItems: " + name);

         try {
            CustomItemProperties cip = null;
            if (mapAutoProperties.containsKey(name)) {
               cip = (CustomItemProperties)mapAutoProperties.get(name);
            }

            if (cip == null) {
               nf locFile = new nf(name);
               InputStream in = rp.a(locFile);
               if (in == null) {
                  .Config.warn("CustomItems file not found: " + name);
                  continue;
               }

               Properties props = new Properties();
               props.load(in);
               cip = new CustomItemProperties(props, name);
            }

            if (cip.isValid(name)) {
               addToItemList(cip, itemList);
               addToEnchantmentList(cip, enchantmentList);
            }
         } catch (FileNotFoundException var11) {
            .Config.warn("CustomItems file not found: " + name);
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      itemProperties = propertyListToArray(itemList);
      enchantmentProperties = propertyListToArray(enchantmentList);
      Comparator comp = getPropertiesComparator();

      int i;
      CustomItemProperties[] cips;
      for(i = 0; i < itemProperties.length; ++i) {
         cips = itemProperties[i];
         if (cips != null) {
            Arrays.sort(cips, comp);
         }
      }

      for(i = 0; i < enchantmentProperties.length; ++i) {
         cips = enchantmentProperties[i];
         if (cips != null) {
            Arrays.sort(cips, comp);
         }
      }

   }

   private static Comparator getPropertiesComparator() {
      Comparator comp = new Comparator() {
         public int compare(Object o1, Object o2) {
            CustomItemProperties cip1 = (CustomItemProperties)o1;
            CustomItemProperties cip2 = (CustomItemProperties)o2;
            if (cip1.layer != cip2.layer) {
               return cip1.layer - cip2.layer;
            } else if (cip1.weight != cip2.weight) {
               return cip2.weight - cip1.weight;
            } else {
               return !cip1.basePath.equals(cip2.basePath) ? cip1.basePath.compareTo(cip2.basePath) : cip1.name.compareTo(cip2.name);
            }
         }
      };
      return comp;
   }

   public static void updateIcons(cdp textureMap) {
      List cips = getAllProperties();
      Iterator it = cips.iterator();

      while(it.hasNext()) {
         CustomItemProperties cip = (CustomItemProperties)it.next();
         cip.updateIcons(textureMap);
      }

   }

   public static void loadModels(cgb modelBakery) {
      List cips = getAllProperties();
      Iterator it = cips.iterator();

      while(it.hasNext()) {
         CustomItemProperties cip = (CustomItemProperties)it.next();
         cip.loadModels(modelBakery);
      }

   }

   public static void updateModels() {
      List cips = getAllProperties();
      Iterator it = cips.iterator();

      while(it.hasNext()) {
         CustomItemProperties cip = (CustomItemProperties)it.next();
         if (cip.type == 1) {
            cdp textureMap = bib.z().R();
            cip.updateModelTexture(textureMap, itemModelGenerator);
            cip.updateModelsFull();
         }
      }

   }

   private static List getAllProperties() {
      List list = new ArrayList();
      addAll(itemProperties, list);
      addAll(enchantmentProperties, list);
      return list;
   }

   private static void addAll(CustomItemProperties[][] cipsArr, List list) {
      if (cipsArr != null) {
         for(int i = 0; i < cipsArr.length; ++i) {
            CustomItemProperties[] cips = cipsArr[i];
            if (cips != null) {
               for(int k = 0; k < cips.length; ++k) {
                  CustomItemProperties cip = cips[k];
                  if (cip != null) {
                     list.add(cip);
                  }
               }
            }
         }

      }
   }

   private static Map makeAutoImageProperties(cer rp) {
      Map map = new HashMap();
      map.putAll(makePotionImageProperties(rp, "normal", ain.a(air.bH)));
      map.putAll(makePotionImageProperties(rp, "splash", ain.a(air.bI)));
      map.putAll(makePotionImageProperties(rp, "linger", ain.a(air.bJ)));
      return map;
   }

   private static Map makePotionImageProperties(cer rp, String type, int itemId) {
      Map map = new HashMap();
      String typePrefix = type + "/";
      String[] prefixes = new String[]{"mcpatcher/cit/potion/" + typePrefix, "mcpatcher/cit/Potion/" + typePrefix};
      String[] suffixes = new String[]{".png"};
      String[] names = ResUtils.collectFiles(rp, prefixes, suffixes);

      for(int i = 0; i < names.length; ++i) {
         String path = names[i];
         String name = StrUtils.removePrefixSuffix(path, prefixes, suffixes);
         Properties props = makePotionProperties(name, type, itemId, path);
         if (props != null) {
            String pathProp = StrUtils.removeSuffix(path, suffixes) + ".properties";
            CustomItemProperties cip = new CustomItemProperties(props, pathProp);
            map.put(pathProp, cip);
         }
      }

      return map;
   }

   private static Properties makePotionProperties(String name, String type, int itemId, String path) {
      if (StrUtils.endsWith(name, new String[]{"_n", "_s"})) {
         return null;
      } else if (name.equals("empty") && type.equals("normal")) {
         itemId = ain.a(air.bK);
         Properties props = new Properties();
         props.put("type", "item");
         props.put("items", "" + itemId);
         return props;
      } else {
         int[] damages = (int[])((int[])getMapPotionIds().get(name));
         if (damages == null) {
            .Config.warn("Potion not found for image: " + path);
            return null;
         } else {
            StringBuffer bufDamage = new StringBuffer();

            int i;
            for(i = 0; i < damages.length; ++i) {
               int damage = damages[i];
               if (type.equals("splash")) {
                  damage |= 16384;
               }

               if (i > 0) {
                  bufDamage.append(" ");
               }

               bufDamage.append(damage);
            }

            i = 16447;
            if (name.equals("water") || name.equals("mundane")) {
               i |= 64;
            }

            Properties props = new Properties();
            props.put("type", "item");
            props.put("items", "" + itemId);
            props.put("damage", "" + bufDamage.toString());
            props.put("damageMask", "" + i);
            if (type.equals("splash")) {
               props.put("texture.potion_bottle_splash", name);
            } else {
               props.put("texture.potion_bottle_drinkable", name);
            }

            return props;
         }
      }
   }

   private static Map getMapPotionIds() {
      if (mapPotionIds == null) {
         mapPotionIds = new LinkedHashMap();
         mapPotionIds.put("water", getPotionId(0, 0));
         mapPotionIds.put("awkward", getPotionId(0, 1));
         mapPotionIds.put("thick", getPotionId(0, 2));
         mapPotionIds.put("potent", getPotionId(0, 3));
         mapPotionIds.put("regeneration", getPotionIds(1));
         mapPotionIds.put("movespeed", getPotionIds(2));
         mapPotionIds.put("fireresistance", getPotionIds(3));
         mapPotionIds.put("poison", getPotionIds(4));
         mapPotionIds.put("heal", getPotionIds(5));
         mapPotionIds.put("nightvision", getPotionIds(6));
         mapPotionIds.put("clear", getPotionId(7, 0));
         mapPotionIds.put("bungling", getPotionId(7, 1));
         mapPotionIds.put("charming", getPotionId(7, 2));
         mapPotionIds.put("rank", getPotionId(7, 3));
         mapPotionIds.put("weakness", getPotionIds(8));
         mapPotionIds.put("damageboost", getPotionIds(9));
         mapPotionIds.put("moveslowdown", getPotionIds(10));
         mapPotionIds.put("leaping", getPotionIds(11));
         mapPotionIds.put("harm", getPotionIds(12));
         mapPotionIds.put("waterbreathing", getPotionIds(13));
         mapPotionIds.put("invisibility", getPotionIds(14));
         mapPotionIds.put("thin", getPotionId(15, 0));
         mapPotionIds.put("debonair", getPotionId(15, 1));
         mapPotionIds.put("sparkling", getPotionId(15, 2));
         mapPotionIds.put("stinky", getPotionId(15, 3));
         mapPotionIds.put("mundane", getPotionId(0, 4));
         mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
         mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
         mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
         mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
         mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
         mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
         mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
         mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
      }

      return mapPotionIds;
   }

   private static int[] getPotionIds(int baseId) {
      return new int[]{baseId, baseId + 16, baseId + 32, baseId + 48};
   }

   private static int[] getPotionId(int baseId, int subId) {
      return new int[]{baseId + subId * 16};
   }

   private static int getPotionNameDamage(String name) {
      String fullName = "effect." + name;
      Set keys = uz.b.c();
      Iterator it = keys.iterator();

      uz potion;
      String potionName;
      do {
         if (!it.hasNext()) {
            return -1;
         }

         nf rl = (nf)it.next();
         potion = (uz)uz.b.c(rl);
         potionName = potion.a();
      } while(!fullName.equals(potionName));

      return uz.a(potion);
   }

   private static List makePropertyList(CustomItemProperties[][] propsArr) {
      List list = new ArrayList();
      if (propsArr != null) {
         for(int i = 0; i < propsArr.length; ++i) {
            CustomItemProperties[] props = propsArr[i];
            List propList = null;
            if (props != null) {
               propList = new ArrayList(Arrays.asList(props));
            }

            list.add(propList);
         }
      }

      return list;
   }

   private static CustomItemProperties[][] propertyListToArray(List list) {
      CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];

      for(int i = 0; i < list.size(); ++i) {
         List subList = (List)list.get(i);
         if (subList != null) {
            CustomItemProperties[] subArr = (CustomItemProperties[])((CustomItemProperties[])subList.toArray(new CustomItemProperties[subList.size()]));
            Arrays.sort(subArr, new CustomItemsComparator());
            propArr[i] = subArr;
         }
      }

      return propArr;
   }

   private static void addToItemList(CustomItemProperties cp, List itemList) {
      if (cp.items != null) {
         for(int i = 0; i < cp.items.length; ++i) {
            int itemId = cp.items[i];
            if (itemId <= 0) {
               .Config.warn("Invalid item ID: " + itemId);
            } else {
               addToList(cp, itemList, itemId);
            }
         }

      }
   }

   private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
      if (cp.type == 2) {
         if (cp.enchantmentIds != null) {
            for(int i = 0; i < 256; ++i) {
               if (cp.enchantmentIds.isInRange(i)) {
                  addToList(cp, enchantmentList, i);
               }
            }

         }
      }
   }

   private static void addToList(CustomItemProperties cp, List list, int id) {
      while(id >= list.size()) {
         list.add((Object)null);
      }

      List subList = (List)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      ((List)subList).add(cp);
   }

   public static cfy getCustomItemModel(aip itemStack, cfy model, nf modelLocation, boolean fullModel) {
      if (!fullModel && model.b()) {
         return model;
      } else if (itemProperties == null) {
         return model;
      } else {
         CustomItemProperties props = getCustomItemProperties(itemStack, 1);
         if (props == null) {
            return model;
         } else {
            cfy customModel = props.getBakedModel(modelLocation, fullModel);
            return customModel != null ? customModel : model;
         }
      }
   }

   public static boolean bindCustomArmorTexture(aip itemStack, vl slot, String overlay) {
      if (itemProperties == null) {
         return false;
      } else {
         nf loc = getCustomArmorLocation(itemStack, slot, overlay);
         if (loc == null) {
            return false;
         } else {
            .Config.getTextureManager().a(loc);
            return true;
         }
      }
   }

   private static nf getCustomArmorLocation(aip itemStack, vl slot, String overlay) {
      CustomItemProperties props = getCustomItemProperties(itemStack, 3);
      if (props == null) {
         return null;
      } else if (props.mapTextureLocations == null) {
         return props.textureLocation;
      } else {
         ain item = itemStack.c();
         if (!(item instanceof agv)) {
            return null;
         } else {
            agv itemArmor = (agv)item;
            String material = itemArmor.d().d();
            int layer = slot == vl.d ? 2 : 1;
            StringBuffer sb = new StringBuffer();
            sb.append("texture.");
            sb.append(material);
            sb.append("_layer_");
            sb.append(layer);
            if (overlay != null) {
               sb.append("_");
               sb.append(overlay);
            }

            String key = sb.toString();
            nf loc = (nf)props.mapTextureLocations.get(key);
            return loc == null ? props.textureLocation : loc;
         }
      }
   }

   public static nf getCustomElytraTexture(aip itemStack, nf locElytra) {
      if (itemProperties == null) {
         return locElytra;
      } else {
         CustomItemProperties props = getCustomItemProperties(itemStack, 4);
         if (props == null) {
            return locElytra;
         } else {
            return props.textureLocation == null ? locElytra : props.textureLocation;
         }
      }
   }

   private static CustomItemProperties getCustomItemProperties(aip itemStack, int type) {
      if (itemProperties == null) {
         return null;
      } else if (itemStack == null) {
         return null;
      } else {
         ain item = itemStack.c();
         int itemId = ain.a(item);
         if (itemId >= 0 && itemId < itemProperties.length) {
            CustomItemProperties[] cips = itemProperties[itemId];
            if (cips != null) {
               for(int i = 0; i < cips.length; ++i) {
                  CustomItemProperties cip = cips[i];
                  if (cip.type == type && matchesProperties(cip, itemStack, (int[][])null)) {
                     return cip;
                  }
               }
            }
         }

         return null;
      }
   }

   private static boolean matchesProperties(CustomItemProperties cip, aip itemStack, int[][] enchantmentIdLevels) {
      ain item = itemStack.c();
      if (cip.damage != null) {
         int damage = getItemStackDamage(itemStack);
         if (damage < 0) {
            return false;
         }

         if (cip.damageMask != 0) {
            damage &= cip.damageMask;
         }

         if (cip.damagePercent) {
            int damageMax = item.l();
            damage = (int)((double)(damage * 100) / (double)damageMax);
         }

         if (!cip.damage.isInRange(damage)) {
            return false;
         }
      }

      if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.E())) {
         return false;
      } else {
         int[][] idLevels = enchantmentIdLevels;
         int i;
         int level;
         boolean levelMatch;
         if (cip.enchantmentIds != null) {
            if (enchantmentIdLevels == null) {
               idLevels = getEnchantmentIdLevels(itemStack);
            }

            levelMatch = false;

            for(i = 0; i < idLevels.length; ++i) {
               level = idLevels[i][0];
               if (cip.enchantmentIds.isInRange(level)) {
                  levelMatch = true;
                  break;
               }
            }

            if (!levelMatch) {
               return false;
            }
         }

         if (cip.enchantmentLevels != null) {
            if (idLevels == null) {
               idLevels = getEnchantmentIdLevels(itemStack);
            }

            levelMatch = false;

            for(i = 0; i < idLevels.length; ++i) {
               level = idLevels[i][1];
               if (cip.enchantmentLevels.isInRange(level)) {
                  levelMatch = true;
                  break;
               }
            }

            if (!levelMatch) {
               return false;
            }
         }

         if (cip.nbtTagValues != null) {
            fy nbt = itemStack.p();

            for(i = 0; i < cip.nbtTagValues.length; ++i) {
               NbtTagValue ntv = cip.nbtTagValues[i];
               if (!ntv.matches(nbt)) {
                  return false;
               }
            }
         }

         if (cip.hand != 0) {
            if (cip.hand == 1 && renderOffHand) {
               return false;
            }

            if (cip.hand == 2 && !renderOffHand) {
               return false;
            }
         }

         return true;
      }
   }

   private static int getItemStackDamage(aip itemStack) {
      ain item = itemStack.c();
      return item instanceof ajd ? getPotionDamage(itemStack) : itemStack.i();
   }

   private static int getPotionDamage(aip itemStack) {
      fy nbt = itemStack.p();
      if (nbt == null) {
         return 0;
      } else {
         String name = nbt.l("Potion");
         if (name != null && !name.equals("")) {
            Integer value = (Integer)mapPotionDamages.get(name);
            if (value == null) {
               return -1;
            } else {
               int val = value.intValue();
               if (itemStack.c() == air.bI) {
                  val |= 16384;
               }

               return val;
            }
         } else {
            return 0;
         }
      }
   }

   private static Map makeMapPotionDamages() {
      Map map = new HashMap();
      addPotion("water", 0, false, map);
      addPotion("awkward", 16, false, map);
      addPotion("thick", 32, false, map);
      addPotion("mundane", 64, false, map);
      addPotion("regeneration", 1, true, map);
      addPotion("swiftness", 2, true, map);
      addPotion("fire_resistance", 3, true, map);
      addPotion("poison", 4, true, map);
      addPotion("healing", 5, true, map);
      addPotion("night_vision", 6, true, map);
      addPotion("weakness", 8, true, map);
      addPotion("strength", 9, true, map);
      addPotion("slowness", 10, true, map);
      addPotion("leaping", 11, true, map);
      addPotion("harming", 12, true, map);
      addPotion("water_breathing", 13, true, map);
      addPotion("invisibility", 14, true, map);
      return map;
   }

   private static void addPotion(String name, int value, boolean extended, Map map) {
      if (extended) {
         value |= 8192;
      }

      map.put("minecraft:" + name, value);
      if (extended) {
         int valueStrong = value | 32;
         map.put("minecraft:strong_" + name, valueStrong);
         int valueLong = value | 64;
         map.put("minecraft:long_" + name, valueLong);
      }

   }

   private static int[][] getEnchantmentIdLevels(aip itemStack) {
      ain item = itemStack.c();
      ge var8;
      if (item == air.co) {
         ahy var10000 = (ahy)((ahy)air.co);
         var8 = ahy.h(itemStack);
      } else {
         var8 = itemStack.q();
      }

      ge nbt = var8;
      if (nbt != null && nbt.c() > 0) {
         int[][] arr = new int[nbt.c()][2];

         for(int i = 0; i < nbt.c(); ++i) {
            fy tag = nbt.b(i);
            int id = tag.g("id");
            int lvl = tag.g("lvl");
            arr[i][0] = id;
            arr[i][1] = lvl;
         }

         return arr;
      } else {
         return EMPTY_INT2_ARRAY;
      }
   }

   public static boolean renderCustomEffect(bzw renderItem, aip itemStack, cfy model) {
      if (enchantmentProperties == null) {
         return false;
      } else if (itemStack == null) {
         return false;
      } else {
         int[][] idLevels = getEnchantmentIdLevels(itemStack);
         if (idLevels.length <= 0) {
            return false;
         } else {
            Set layersRendered = null;
            boolean rendered = false;
            cdr textureManager = .Config.getTextureManager();

            for(int i = 0; i < idLevels.length; ++i) {
               int id = idLevels[i][0];
               if (id >= 0 && id < enchantmentProperties.length) {
                  CustomItemProperties[] cips = enchantmentProperties[id];
                  if (cips != null) {
                     for(int p = 0; p < cips.length; ++p) {
                        CustomItemProperties cip = cips[p];
                        if (layersRendered == null) {
                           layersRendered = new HashSet();
                        }

                        if (layersRendered.add(id) && matchesProperties(cip, itemStack, idLevels) && cip.textureLocation != null) {
                           textureManager.a(cip.textureLocation);
                           float width = cip.getTextureWidth(textureManager);
                           if (!rendered) {
                              rendered = true;
                              bus.a(false);
                              bus.c(514);
                              bus.g();
                              bus.n(5890);
                           }

                           Blender.setupBlend(cip.blend, 1.0F);
                           bus.G();
                           bus.b(width / 2.0F, width / 2.0F, width / 2.0F);
                           float offset = cip.speed * (float)(bib.I() % 3000L) / 3000.0F / 8.0F;
                           bus.c(offset, 0.0F, 0.0F);
                           bus.b(cip.rotation, 0.0F, 0.0F, 1.0F);
                           renderItem.a(model, -1);
                           bus.H();
                        }
                     }
                  }
               }
            }

            if (rendered) {
               bus.e();
               bus.m();
               bus.b(770, 771);
               bus.c(1.0F, 1.0F, 1.0F, 1.0F);
               bus.n(5888);
               bus.f();
               bus.c(515);
               bus.a(true);
               textureManager.a(cdp.g);
            }

            return rendered;
         }
      }
   }

   public static boolean renderCustomArmorEffect(vp entity, aip itemStack, bqf model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
      if (enchantmentProperties == null) {
         return false;
      } else if (.Config.isShaders() && Shaders.isShadowPass) {
         return false;
      } else if (itemStack == null) {
         return false;
      } else {
         int[][] idLevels = getEnchantmentIdLevels(itemStack);
         if (idLevels.length <= 0) {
            return false;
         } else {
            Set layersRendered = null;
            boolean rendered = false;
            cdr textureManager = .Config.getTextureManager();

            for(int i = 0; i < idLevels.length; ++i) {
               int id = idLevels[i][0];
               if (id >= 0 && id < enchantmentProperties.length) {
                  CustomItemProperties[] cips = enchantmentProperties[id];
                  if (cips != null) {
                     for(int p = 0; p < cips.length; ++p) {
                        CustomItemProperties cip = cips[p];
                        if (layersRendered == null) {
                           layersRendered = new HashSet();
                        }

                        if (layersRendered.add(id) && matchesProperties(cip, itemStack, idLevels) && cip.textureLocation != null) {
                           textureManager.a(cip.textureLocation);
                           float width = cip.getTextureWidth(textureManager);
                           if (!rendered) {
                              rendered = true;
                              if (.Config.isShaders()) {
                                 ShadersRender.renderEnchantedGlintBegin();
                              }

                              bus.m();
                              bus.c(514);
                              bus.a(false);
                           }

                           Blender.setupBlend(cip.blend, 1.0F);
                           bus.g();
                           bus.n(5890);
                           bus.F();
                           bus.b(cip.rotation, 0.0F, 0.0F, 1.0F);
                           float texScale = width / 8.0F;
                           bus.b(texScale, texScale / 2.0F, texScale);
                           float offset = cip.speed * (float)(bib.I() % 3000L) / 3000.0F / 8.0F;
                           bus.c(0.0F, offset, 0.0F);
                           bus.n(5888);
                           model.a(entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
                        }
                     }
                  }
               }
            }

            if (rendered) {
               bus.e();
               bus.m();
               bus.b(770, 771);
               bus.c(1.0F, 1.0F, 1.0F, 1.0F);
               bus.n(5890);
               bus.F();
               bus.n(5888);
               bus.f();
               bus.a(true);
               bus.c(515);
               bus.l();
               if (.Config.isShaders()) {
                  ShadersRender.renderEnchantedGlintEnd();
               }
            }

            return rendered;
         }
      }
   }

   public static boolean isUseGlint() {
      return useGlint;
   }

   public static void setRenderOffHand(boolean renderOffHand) {
      renderOffHand = renderOffHand;
   }
}
