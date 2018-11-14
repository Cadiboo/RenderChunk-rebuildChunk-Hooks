package net.optifine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.optifine.config.ConnectedParser;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeInt;
import net.optifine.config.RangeListInt;
import net.optifine.config.VillagerProfession;

public class RandomEntityProperties {
   public String name = null;
   public String basePath = null;
   public nf[] resourceLocations = null;
   public RandomEntityRule[] rules = null;

   public RandomEntityProperties(String path, nf[] variants) {
      ConnectedParser cp = new ConnectedParser("RandomEntities");
      this.name = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.resourceLocations = variants;
   }

   public RandomEntityProperties(Properties props, String path, nf baseResLoc) {
      ConnectedParser cp = new ConnectedParser("RandomEntities");
      this.name = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.rules = this.parseRules(props, path, baseResLoc, cp);
   }

   public nf getTextureLocation(nf loc, IRandomEntity randomEntity) {
      int randomId;
      if (this.rules != null) {
         for(randomId = 0; randomId < this.rules.length; ++randomId) {
            RandomEntityRule rule = this.rules[randomId];
            if (rule.matches(randomEntity)) {
               return rule.getTextureLocation(loc, randomEntity.getId());
            }
         }
      }

      if (this.resourceLocations != null) {
         randomId = randomEntity.getId();
         int index = randomId % this.resourceLocations.length;
         return this.resourceLocations[index];
      } else {
         return loc;
      }
   }

   private RandomEntityRule[] parseRules(Properties props, String pathProps, nf baseResLoc, ConnectedParser cp) {
      List list = new ArrayList();
      int count = props.size();

      for(int i = 0; i < count; ++i) {
         int index = i + 1;
         String valTextures = props.getProperty("textures." + index);
         if (valTextures == null) {
            valTextures = props.getProperty("skins." + index);
         }

         if (valTextures != null) {
            int[] textures = cp.parseIntList(valTextures);
            int[] weights = cp.parseIntList(props.getProperty("weights." + index));
            anh[] biomes = cp.parseBiomes(props.getProperty("biomes." + index));
            RangeListInt heights = cp.parseRangeListInt(props.getProperty("heights." + index));
            if (heights == null) {
               heights = this.parseMinMaxHeight(props, index);
            }

            NbtTagValue nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
            VillagerProfession[] professions = cp.parseProfessions(props.getProperty("professions." + index));
            RandomEntityRule rule = new RandomEntityRule(pathProps, baseResLoc, index, textures, weights, biomes, heights, nbtName, professions);
            if (rule.isValid(pathProps)) {
               list.add(rule);
            }
         }
      }

      RandomEntityRule[] rules = (RandomEntityRule[])((RandomEntityRule[])list.toArray(new RandomEntityRule[list.size()]));
      return rules;
   }

   private RangeListInt parseMinMaxHeight(Properties props, int index) {
      String minHeightStr = props.getProperty("minHeight." + index);
      String maxHeightStr = props.getProperty("maxHeight." + index);
      if (minHeightStr == null && maxHeightStr == null) {
         return null;
      } else {
         int minHeight = 0;
         if (minHeightStr != null) {
            minHeight = .Config.parseInt(minHeightStr, -1);
            if (minHeight < 0) {
               .Config.warn("Invalid minHeight: " + minHeightStr);
               return null;
            }
         }

         int maxHeight = 256;
         if (maxHeightStr != null) {
            maxHeight = .Config.parseInt(maxHeightStr, -1);
            if (maxHeight < 0) {
               .Config.warn("Invalid maxHeight: " + maxHeightStr);
               return null;
            }
         }

         if (maxHeight < 0) {
            .Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
            return null;
         } else {
            RangeListInt list = new RangeListInt();
            list.addRange(new RangeInt(minHeight, maxHeight));
            return list;
         }
      }
   }

   public boolean isValid(String path) {
      if (this.resourceLocations == null && this.rules == null) {
         .Config.warn("No skins specified: " + path);
         return false;
      } else {
         int i;
         if (this.rules != null) {
            for(i = 0; i < this.rules.length; ++i) {
               RandomEntityRule rule = this.rules[i];
               if (!rule.isValid(path)) {
                  return false;
               }
            }
         }

         if (this.resourceLocations != null) {
            for(i = 0; i < this.resourceLocations.length; ++i) {
               nf loc = this.resourceLocations[i];
               if (!.Config.hasResource(loc)) {
                  .Config.warn("Texture not found: " + loc.a());
                  return false;
               }
            }
         }

         return true;
      }
   }

   public boolean isDefault() {
      if (this.rules != null) {
         return false;
      } else {
         return this.resourceLocations == null;
      }
   }
}
