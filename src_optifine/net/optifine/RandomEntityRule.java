package net.optifine;

import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.NbtTagValue;
import net.optifine.config.RangeListInt;
import net.optifine.config.VillagerProfession;
import net.optifine.reflect.Reflector;
import net.optifine.util.MathUtils;

public class RandomEntityRule {
   private String pathProps = null;
   private nf baseResLoc = null;
   private int index;
   private int[] textures = null;
   private nf[] resourceLocations = null;
   private int[] weights = null;
   private anh[] biomes = null;
   private RangeListInt heights = null;
   private NbtTagValue nbtName = null;
   public int[] sumWeights = null;
   public int sumAllWeights = 1;
   private VillagerProfession[] professions = null;

   public RandomEntityRule(String pathProps, nf baseResLoc, int index, int[] textures, int[] weights, anh[] biomes, RangeListInt heights, NbtTagValue nbtName, VillagerProfession[] professions) {
      this.pathProps = pathProps;
      this.baseResLoc = baseResLoc;
      this.index = index;
      this.textures = textures;
      this.weights = weights;
      this.biomes = biomes;
      this.heights = heights;
      this.nbtName = nbtName;
      this.professions = professions;
   }

   public boolean isValid(String path) {
      if (this.textures != null && this.textures.length != 0) {
         if (this.resourceLocations != null) {
            return true;
         } else {
            this.resourceLocations = new nf[this.textures.length];
            boolean mcpatcher = this.pathProps.startsWith("mcpatcher/mob/");
            nf locMcp = RandomEntities.getLocationRandom(this.baseResLoc, mcpatcher);
            if (locMcp == null) {
               .Config.warn("Invalid path: " + this.baseResLoc.a());
               return false;
            } else {
               int sum;
               int i;
               for(sum = 0; sum < this.resourceLocations.length; ++sum) {
                  i = this.textures[sum];
                  if (i <= 1) {
                     this.resourceLocations[sum] = this.baseResLoc;
                  } else {
                     nf locNew = RandomEntities.getLocationIndexed(locMcp, i);
                     if (locNew == null) {
                        .Config.warn("Invalid path: " + this.baseResLoc.a());
                        return false;
                     }

                     if (!.Config.hasResource(locNew)) {
                        .Config.warn("Texture not found: " + locNew.a());
                        return false;
                     }

                     this.resourceLocations[sum] = locNew;
                  }
               }

               if (this.weights != null) {
                  int[] weights2;
                  if (this.weights.length > this.resourceLocations.length) {
                     .Config.warn("More weights defined than skins, trimming weights: " + path);
                     weights2 = new int[this.resourceLocations.length];
                     System.arraycopy(this.weights, 0, weights2, 0, weights2.length);
                     this.weights = weights2;
                  }

                  if (this.weights.length < this.resourceLocations.length) {
                     .Config.warn("Less weights defined than skins, expanding weights: " + path);
                     weights2 = new int[this.resourceLocations.length];
                     System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                     i = MathUtils.getAverage(this.weights);

                     for(int i = this.weights.length; i < weights2.length; ++i) {
                        weights2[i] = i;
                     }

                     this.weights = weights2;
                  }

                  this.sumWeights = new int[this.weights.length];
                  sum = 0;

                  for(i = 0; i < this.weights.length; ++i) {
                     if (this.weights[i] < 0) {
                        .Config.warn("Invalid weight: " + this.weights[i]);
                        return false;
                     }

                     sum += this.weights[i];
                     this.sumWeights[i] = sum;
                  }

                  this.sumAllWeights = sum;
                  if (this.sumAllWeights <= 0) {
                     .Config.warn("Invalid sum of all weights: " + sum);
                     this.sumAllWeights = 1;
                  }
               }

               if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
                  .Config.warn("Invalid professions or careers: " + path);
                  return false;
               } else {
                  return true;
               }
            }
         }
      } else {
         .Config.warn("Invalid skins for rule: " + this.index);
         return false;
      }
   }

   public boolean matches(IRandomEntity randomEntity) {
      if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes)) {
         return false;
      } else {
         if (this.heights != null) {
            et pos = randomEntity.getSpawnPosition();
            if (pos != null && !this.heights.isInRange(pos.q())) {
               return false;
            }
         }

         if (this.nbtName != null) {
            String name = randomEntity.getName();
            if (!this.nbtName.matchesValue(name)) {
               return false;
            }
         }

         if (this.professions != null && randomEntity instanceof RandomEntity) {
            RandomEntity rme = (RandomEntity)randomEntity;
            vg entity = rme.getEntity();
            if (entity instanceof ady) {
               ady entityVillager = (ady)entity;
               int profInt = entityVillager.dl();
               int careerInt = Reflector.getFieldValueInt(entityVillager, Reflector.EntityVillager_careerId, -1);
               if (profInt < 0 || careerInt < 0) {
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
         }

         return true;
      }
   }

   public nf getTextureLocation(nf loc, int randomId) {
      if (this.resourceLocations != null && this.resourceLocations.length != 0) {
         int index = 0;
         if (this.weights == null) {
            index = randomId % this.resourceLocations.length;
         } else {
            int randWeight = randomId % this.sumAllWeights;

            for(int i = 0; i < this.sumWeights.length; ++i) {
               if (this.sumWeights[i] > randWeight) {
                  index = i;
                  break;
               }
            }
         }

         return this.resourceLocations[index];
      } else {
         return loc;
      }
   }
}
