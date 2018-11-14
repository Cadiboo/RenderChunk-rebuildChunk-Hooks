package net.optifine;

import api.a;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.config.VillagerProfession;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorField;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;

public class CustomGuiProperties {
   private String fileName = null;
   private String basePath = null;
   private CustomGuiProperties.EnumContainer container = null;
   private Map textureLocations = null;
   private NbtTagValue nbtName = null;
   private anh[] biomes = null;
   private RangeListInt heights = null;
   private Boolean large = null;
   private Boolean trapped = null;
   private Boolean christmas = null;
   private Boolean ender = null;
   private RangeListInt levels = null;
   private VillagerProfession[] professions = null;
   private CustomGuiProperties.EnumVariant[] variants = null;
   private ahs[] colors = null;
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_HORSE;
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_DISPENSER;
   private static final CustomGuiProperties.EnumVariant[] VARIANTS_INVALID;
   private static final ahs[] COLORS_INVALID;
   private static final nf ANVIL_GUI_TEXTURE;
   private static final nf BEACON_GUI_TEXTURE;
   private static final nf BREWING_STAND_GUI_TEXTURE;
   private static final nf CHEST_GUI_TEXTURE;
   private static final nf CRAFTING_TABLE_GUI_TEXTURE;
   private static final nf HORSE_GUI_TEXTURE;
   private static final nf DISPENSER_GUI_TEXTURE;
   private static final nf ENCHANTMENT_TABLE_GUI_TEXTURE;
   private static final nf FURNACE_GUI_TEXTURE;
   private static final nf HOPPER_GUI_TEXTURE;
   private static final nf INVENTORY_GUI_TEXTURE;
   private static final nf SHULKER_BOX_GUI_TEXTURE;
   private static final nf VILLAGER_GUI_TEXTURE;

   public CustomGuiProperties(Properties props, String path) {
      ConnectedParser cp = new ConnectedParser("CustomGuis");
      this.fileName = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.container = (CustomGuiProperties.EnumContainer)cp.parseEnum(props.getProperty("container"), CustomGuiProperties.EnumContainer.values(), "container");
      this.textureLocations = parseTextureLocations(props, "texture", this.container, "textures/gui/", this.basePath);
      this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name"));
      this.biomes = cp.parseBiomes(props.getProperty("biomes"));
      this.heights = cp.parseRangeListInt(props.getProperty("heights"));
      this.large = cp.parseBooleanObject(props.getProperty("large"));
      this.trapped = cp.parseBooleanObject(props.getProperty("trapped"));
      this.christmas = cp.parseBooleanObject(props.getProperty("christmas"));
      this.ender = cp.parseBooleanObject(props.getProperty("ender"));
      this.levels = cp.parseRangeListInt(props.getProperty("levels"));
      this.professions = cp.parseProfessions(props.getProperty("professions"));
      CustomGuiProperties.EnumVariant[] vars = getContainerVariants(this.container);
      this.variants = (CustomGuiProperties.EnumVariant[])((CustomGuiProperties.EnumVariant[])cp.parseEnums(props.getProperty("variants"), vars, "variants", VARIANTS_INVALID));
      this.colors = parseEnumDyeColors(props.getProperty("colors"));
   }

   private static CustomGuiProperties.EnumVariant[] getContainerVariants(CustomGuiProperties.EnumContainer cont) {
      if (cont == CustomGuiProperties.EnumContainer.HORSE) {
         return VARIANTS_HORSE;
      } else {
         return cont == CustomGuiProperties.EnumContainer.DISPENSER ? VARIANTS_DISPENSER : new CustomGuiProperties.EnumVariant[0];
      }
   }

   private static ahs[] parseEnumDyeColors(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase();
         String[] tokens = .Config.tokenize(str, " ");
         ahs[] cols = new ahs[tokens.length];

         for(int i = 0; i < tokens.length; ++i) {
            String token = tokens[i];
            ahs col = parseEnumDyeColor(token);
            if (col == null) {
               warn("Invalid color: " + token);
               return COLORS_INVALID;
            }

            cols[i] = col;
         }

         return cols;
      }
   }

   private static ahs parseEnumDyeColor(String str) {
      if (str == null) {
         return null;
      } else {
         ahs[] colors = ahs.values();

         for(int i = 0; i < colors.length; ++i) {
            ahs enumDyeColor = colors[i];
            if (enumDyeColor.m().equals(str)) {
               return enumDyeColor;
            }

            if (enumDyeColor.d().equals(str)) {
               return enumDyeColor;
            }
         }

         return null;
      }
   }

   private static nf parseTextureLocation(String str, String basePath) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         String tex = TextureUtils.fixResourcePath(str, basePath);
         if (!tex.endsWith(".png")) {
            tex = tex + ".png";
         }

         return new nf(basePath + "/" + tex);
      }
   }

   private static Map parseTextureLocations(Properties props, String property, CustomGuiProperties.EnumContainer container, String pathPrefix, String basePath) {
      Map map = new HashMap();
      String propVal = props.getProperty(property);
      if (propVal != null) {
         nf locKey = getGuiTextureLocation(container);
         nf locVal = parseTextureLocation(propVal, basePath);
         if (locKey != null && locVal != null) {
            map.put(locKey, locVal);
         }
      }

      String keyPrefix = property + ".";
      Set keys = props.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         if (key.startsWith(keyPrefix)) {
            String pathRel = key.substring(keyPrefix.length());
            pathRel = pathRel.replace('\\', '/');
            pathRel = StrUtils.removePrefixSuffix(pathRel, "/", ".png");
            String path = pathPrefix + pathRel + ".png";
            String val = props.getProperty(key);
            nf locKey = new nf(path);
            nf locVal = parseTextureLocation(val, basePath);
            map.put(locKey, locVal);
         }
      }

      return map;
   }

   private static nf getGuiTextureLocation(CustomGuiProperties.EnumContainer container) {
      switch(container) {
      case ANVIL:
         return ANVIL_GUI_TEXTURE;
      case BEACON:
         return BEACON_GUI_TEXTURE;
      case BREWING_STAND:
         return BREWING_STAND_GUI_TEXTURE;
      case CHEST:
         return CHEST_GUI_TEXTURE;
      case CRAFTING:
         return CRAFTING_TABLE_GUI_TEXTURE;
      case CREATIVE:
         return null;
      case DISPENSER:
         return DISPENSER_GUI_TEXTURE;
      case ENCHANTMENT:
         return ENCHANTMENT_TABLE_GUI_TEXTURE;
      case FURNACE:
         return FURNACE_GUI_TEXTURE;
      case HOPPER:
         return HOPPER_GUI_TEXTURE;
      case HORSE:
         return HORSE_GUI_TEXTURE;
      case INVENTORY:
         return INVENTORY_GUI_TEXTURE;
      case SHULKER_BOX:
         return SHULKER_BOX_GUI_TEXTURE;
      case VILLAGER:
         return VILLAGER_GUI_TEXTURE;
      default:
         return null;
      }
   }

   public boolean isValid(String path) {
      if (this.fileName != null && this.fileName.length() > 0) {
         if (this.basePath == null) {
            warn("No base path found: " + path);
            return false;
         } else if (this.container == null) {
            warn("No container found: " + path);
            return false;
         } else if (this.textureLocations.isEmpty()) {
            warn("No texture found: " + path);
            return false;
         } else if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
            warn("Invalid professions or careers: " + path);
            return false;
         } else if (this.variants == VARIANTS_INVALID) {
            warn("Invalid variants: " + path);
            return false;
         } else if (this.colors == COLORS_INVALID) {
            warn("Invalid colors: " + path);
            return false;
         } else {
            return true;
         }
      } else {
         warn("No name found: " + path);
         return false;
      }
   }

   private static void warn(String str) {
      .Config.warn("[CustomGuis] " + str);
   }

   private boolean matchesGeneral(CustomGuiProperties.EnumContainer ec, et pos, amy blockAccess) {
      if (this.container != ec) {
         return false;
      } else {
         if (this.biomes != null) {
            anh biome = blockAccess.b(pos);
            if (!Matches.biome(biome, this.biomes)) {
               return false;
            }
         }

         return this.heights == null || this.heights.isInRange(pos.q());
      }
   }

   public boolean matchesPos(CustomGuiProperties.EnumContainer ec, et pos, amy blockAccess, blk screen) {
      if (!this.matchesGeneral(ec, pos, blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String name = getName(screen);
            if (!this.nbtName.matchesValue(name)) {
               return false;
            }
         }

         switch(ec) {
         case BEACON:
            return this.matchesBeacon(pos, blockAccess);
         case CHEST:
            return this.matchesChest(pos, blockAccess);
         case DISPENSER:
            return this.matchesDispenser(pos, blockAccess);
         case SHULKER_BOX:
            return this.matchesShulker(pos, blockAccess);
         default:
            return true;
         }
      }
   }

   public static String getName(blk screen) {
      ui nameable = getWorldNameable(screen);
      return nameable == null ? null : nameable.i_().c();
   }

   private static ui getWorldNameable(blk screen) {
      if (screen instanceof bmi) {
         return getWorldNameable(screen, Reflector.GuiBeacon_tileBeacon);
      } else if (screen instanceof bmk) {
         return getWorldNameable(screen, Reflector.GuiBrewingStand_tileBrewingStand);
      } else if (screen instanceof bmm) {
         return getWorldNameable(screen, Reflector.GuiChest_lowerChestInventory);
      } else if (screen instanceof bmq) {
         return ((bmq)screen).v;
      } else if (screen instanceof bmt) {
         return getWorldNameable(screen, Reflector.GuiEnchantment_nameable);
      } else if (screen instanceof bmu) {
         return getWorldNameable(screen, Reflector.GuiFurnace_tileFurnace);
      } else if (screen instanceof bmv) {
         return getWorldNameable(screen, Reflector.GuiHopper_hopperInventory);
      } else {
         return screen instanceof bna ? getWorldNameable(screen, Reflector.GuiShulkerBox_inventory) : null;
      }
   }

   private static ui getWorldNameable(blk screen, ReflectorField fieldInventory) {
      Object obj = Reflector.getFieldValue(screen, fieldInventory);
      return !(obj instanceof ui) ? null : (ui)obj;
   }

   private boolean matchesBeacon(et pos, amy blockAccess) {
      avj te = blockAccess.r(pos);
      if (!(te instanceof avh)) {
         return false;
      } else {
         avh teb = (avh)te;
         if (this.levels != null) {
            int l = teb.s();
            if (!this.levels.isInRange(l)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesChest(et pos, amy blockAccess) {
      avj te = blockAccess.r(pos);
      if (te instanceof avl) {
         avl tec = (avl)te;
         return this.matchesChest(tec, pos, blockAccess);
      } else if (te instanceof avs) {
         avs teec = (avs)te;
         return this.matchesEnderChest(teec, pos, blockAccess);
      } else {
         return false;
      }
   }

   private boolean matchesChest(avl tec, et pos, amy blockAccess) {
      boolean isLarge = tec.h != null || tec.g != null || tec.f != null || tec.i != null;
      boolean isTrapped = tec.p() == a.b;
      boolean isChristmas = CustomGuis.isChristmas;
      boolean isEnder = false;
      return this.matchesChest(isLarge, isTrapped, isChristmas, isEnder);
   }

   private boolean matchesEnderChest(avs teec, et pos, amy blockAccess) {
      return this.matchesChest(false, false, false, true);
   }

   private boolean matchesChest(boolean isLarge, boolean isTrapped, boolean isChristmas, boolean isEnder) {
      if (this.large != null && this.large.booleanValue() != isLarge) {
         return false;
      } else if (this.trapped != null && this.trapped.booleanValue() != isTrapped) {
         return false;
      } else if (this.christmas != null && this.christmas.booleanValue() != isChristmas) {
         return false;
      } else {
         return this.ender == null || this.ender.booleanValue() == isEnder;
      }
   }

   private boolean matchesDispenser(et pos, amy blockAccess) {
      avj te = blockAccess.r(pos);
      if (!(te instanceof avp)) {
         return false;
      } else {
         avp ted = (avp)te;
         if (this.variants != null) {
            CustomGuiProperties.EnumVariant var = this.getDispenserVariant(ted);
            if (!.Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         return true;
      }
   }

   private CustomGuiProperties.EnumVariant getDispenserVariant(avp ted) {
      return ted instanceof avq ? CustomGuiProperties.EnumVariant.DROPPER : CustomGuiProperties.EnumVariant.DISPENSER;
   }

   private boolean matchesShulker(et pos, amy blockAccess) {
      avj te = blockAccess.r(pos);
      if (!(te instanceof awb)) {
         return false;
      } else {
         awb tesb = (awb)te;
         if (this.colors != null) {
            ahs col = tesb.s();
            if (!.Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean matchesEntity(CustomGuiProperties.EnumContainer ec, vg entity, amy blockAccess) {
      if (!this.matchesGeneral(ec, entity.c(), blockAccess)) {
         return false;
      } else {
         if (this.nbtName != null) {
            String entityName = entity.h_();
            if (!this.nbtName.matchesValue(entityName)) {
               return false;
            }
         }

         switch(ec) {
         case HORSE:
            return this.matchesHorse(entity, blockAccess);
         case VILLAGER:
            return this.matchesVillager(entity, blockAccess);
         default:
            return true;
         }
      }
   }

   private boolean matchesVillager(vg entity, amy blockAccess) {
      if (!(entity instanceof ady)) {
         return false;
      } else {
         ady entityVillager = (ady)entity;
         if (this.professions != null) {
            int profInt = entityVillager.dl();
            int careerInt = Reflector.getFieldValueInt(entityVillager, Reflector.EntityVillager_careerId, -1);
            if (careerInt < 0) {
               return false;
            }

            boolean matchProfession = false;

            for(int i = 0; i < this.professions.length; ++i) {
               VillagerProfession prof = this.professions[i];
               if (prof.matches(profInt, careerInt)) {
                  matchProfession = true;
                  break;
               }
            }

            if (!matchProfession) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesHorse(vg entity, amy blockAccess) {
      if (!(entity instanceof aao)) {
         return false;
      } else {
         aao ah = (aao)entity;
         if (this.variants != null) {
            CustomGuiProperties.EnumVariant var = this.getHorseVariant(ah);
            if (!.Config.equalsOne(var, this.variants)) {
               return false;
            }
         }

         if (this.colors != null && ah instanceof aas) {
            aas el = (aas)ah;
            ahs col = el.dT();
            if (!.Config.equalsOne(col, this.colors)) {
               return false;
            }
         }

         return true;
      }
   }

   private CustomGuiProperties.EnumVariant getHorseVariant(aao entity) {
      if (entity instanceof aaq) {
         return CustomGuiProperties.EnumVariant.HORSE;
      } else if (entity instanceof aap) {
         return CustomGuiProperties.EnumVariant.DONKEY;
      } else if (entity instanceof aat) {
         return CustomGuiProperties.EnumVariant.MULE;
      } else {
         return entity instanceof aas ? CustomGuiProperties.EnumVariant.LLAMA : null;
      }
   }

   public CustomGuiProperties.EnumContainer getContainer() {
      return this.container;
   }

   public nf getTextureLocation(nf loc) {
      nf locNew = (nf)this.textureLocations.get(loc);
      return locNew == null ? loc : locNew;
   }

   public String toString() {
      return "name: " + this.fileName + ", container: " + this.container + ", textures: " + this.textureLocations;
   }

   static {
      VARIANTS_HORSE = new CustomGuiProperties.EnumVariant[]{CustomGuiProperties.EnumVariant.HORSE, CustomGuiProperties.EnumVariant.DONKEY, CustomGuiProperties.EnumVariant.MULE, CustomGuiProperties.EnumVariant.LLAMA};
      VARIANTS_DISPENSER = new CustomGuiProperties.EnumVariant[]{CustomGuiProperties.EnumVariant.DISPENSER, CustomGuiProperties.EnumVariant.DROPPER};
      VARIANTS_INVALID = new CustomGuiProperties.EnumVariant[0];
      COLORS_INVALID = new ahs[0];
      ANVIL_GUI_TEXTURE = new nf("textures/gui/container/anvil.png");
      BEACON_GUI_TEXTURE = new nf("textures/gui/container/beacon.png");
      BREWING_STAND_GUI_TEXTURE = new nf("textures/gui/container/brewing_stand.png");
      CHEST_GUI_TEXTURE = new nf("textures/gui/container/generic_54.png");
      CRAFTING_TABLE_GUI_TEXTURE = new nf("textures/gui/container/crafting_table.png");
      HORSE_GUI_TEXTURE = new nf("textures/gui/container/horse.png");
      DISPENSER_GUI_TEXTURE = new nf("textures/gui/container/dispenser.png");
      ENCHANTMENT_TABLE_GUI_TEXTURE = new nf("textures/gui/container/enchanting_table.png");
      FURNACE_GUI_TEXTURE = new nf("textures/gui/container/furnace.png");
      HOPPER_GUI_TEXTURE = new nf("textures/gui/container/hopper.png");
      INVENTORY_GUI_TEXTURE = new nf("textures/gui/container/inventory.png");
      SHULKER_BOX_GUI_TEXTURE = new nf("textures/gui/container/shulker_box.png");
      VILLAGER_GUI_TEXTURE = new nf("textures/gui/container/villager.png");
   }

   private static enum EnumVariant {
      HORSE,
      DONKEY,
      MULE,
      LLAMA,
      DISPENSER,
      DROPPER;
   }

   public static enum EnumContainer {
      ANVIL,
      BEACON,
      BREWING_STAND,
      CHEST,
      CRAFTING,
      DISPENSER,
      ENCHANTMENT,
      FURNACE,
      HOPPER,
      HORSE,
      VILLAGER,
      SHULKER_BOX,
      CREATIVE,
      INVENTORY;
   }
}
