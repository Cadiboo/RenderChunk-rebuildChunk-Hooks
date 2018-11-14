package net.optifine.config;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.optifine.ConnectedProperties;

public class ConnectedParser {
   private String context = null;
   public static final VillagerProfession[] PROFESSIONS_INVALID = new VillagerProfession[0];

   public ConnectedParser(String context) {
      this.context = context;
   }

   public String parseName(String path) {
      String str = path;
      int pos = path.lastIndexOf(47);
      if (pos >= 0) {
         str = path.substring(pos + 1);
      }

      int pos2 = str.lastIndexOf(46);
      if (pos2 >= 0) {
         str = str.substring(0, pos2);
      }

      return str;
   }

   public String parseBasePath(String path) {
      int pos = path.lastIndexOf(47);
      return pos < 0 ? "" : path.substring(0, pos);
   }

   public MatchBlock[] parseMatchBlocks(String propMatchBlocks) {
      if (propMatchBlocks == null) {
         return null;
      } else {
         List list = new ArrayList();
         String[] blockStrs = .Config.tokenize(propMatchBlocks, " ");

         for(int i = 0; i < blockStrs.length; ++i) {
            String blockStr = blockStrs[i];
            MatchBlock[] mbs = this.parseMatchBlock(blockStr);
            if (mbs != null) {
               list.addAll(Arrays.asList(mbs));
            }
         }

         MatchBlock[] mbs = (MatchBlock[])((MatchBlock[])list.toArray(new MatchBlock[list.size()]));
         return mbs;
      }
   }

   public awt parseBlockState(String str, awt def) {
      MatchBlock[] mbs = this.parseMatchBlock(str);
      if (mbs == null) {
         return def;
      } else if (mbs.length != 1) {
         return def;
      } else {
         MatchBlock mb = mbs[0];
         int blockId = mb.getBlockId();
         aow block = aow.c(blockId);
         return block.t();
      }
   }

   public MatchBlock[] parseMatchBlock(String blockStr) {
      if (blockStr == null) {
         return null;
      } else {
         blockStr = blockStr.trim();
         if (blockStr.length() <= 0) {
            return null;
         } else {
            String[] parts = .Config.tokenize(blockStr, ":");
            String domain = "minecraft";
            int blockIndex = false;
            byte blockIndex;
            if (parts.length > 1 && this.isFullBlockName(parts)) {
               domain = parts[0];
               blockIndex = 1;
            } else {
               domain = "minecraft";
               blockIndex = 0;
            }

            String blockPart = parts[blockIndex];
            String[] params = (String[])Arrays.copyOfRange(parts, blockIndex + 1, parts.length);
            aow[] blocks = this.parseBlockPart(domain, blockPart);
            if (blocks == null) {
               return null;
            } else {
               MatchBlock[] datas = new MatchBlock[blocks.length];

               for(int i = 0; i < blocks.length; ++i) {
                  aow block = blocks[i];
                  int blockId = aow.a(block);
                  int[] metadatas = null;
                  if (params.length > 0) {
                     metadatas = this.parseBlockMetadatas(block, params);
                     if (metadatas == null) {
                        return null;
                     }
                  }

                  MatchBlock bd = new MatchBlock(blockId, metadatas);
                  datas[i] = bd;
               }

               return datas;
            }
         }
      }
   }

   public boolean isFullBlockName(String[] parts) {
      if (parts.length < 2) {
         return false;
      } else {
         String part1 = parts[1];
         if (part1.length() < 1) {
            return false;
         } else if (this.startsWithDigit(part1)) {
            return false;
         } else {
            return !part1.contains("=");
         }
      }
   }

   public boolean startsWithDigit(String str) {
      if (str == null) {
         return false;
      } else if (str.length() < 1) {
         return false;
      } else {
         char ch = str.charAt(0);
         return Character.isDigit(ch);
      }
   }

   public aow[] parseBlockPart(String domain, String blockPart) {
      if (this.startsWithDigit(blockPart)) {
         int[] ids = this.parseIntList(blockPart);
         if (ids == null) {
            return null;
         } else {
            aow[] blocks = new aow[ids.length];

            for(int i = 0; i < ids.length; ++i) {
               int id = ids[i];
               aow block = aow.c(id);
               if (block == null) {
                  this.warn("Block not found for id: " + id);
                  return null;
               }

               blocks[i] = block;
            }

            return blocks;
         }
      } else {
         String fullName = domain + ":" + blockPart;
         aow block = aow.b(fullName);
         if (block == null) {
            this.warn("Block not found for name: " + fullName);
            return null;
         } else {
            aow[] blocks = new aow[]{block};
            return blocks;
         }
      }
   }

   public int[] parseBlockMetadatas(aow block, String[] params) {
      if (params.length <= 0) {
         return null;
      } else {
         String param0 = params[0];
         if (this.startsWithDigit(param0)) {
            int[] mds = this.parseIntList(param0);
            return mds;
         } else {
            awt stateDefault = block.t();
            Collection properties = stateDefault.s();
            Map mapPropValues = new HashMap();

            for(int i = 0; i < params.length; ++i) {
               String param = params[i];
               if (param.length() > 0) {
                  String[] parts = .Config.tokenize(param, "=");
                  if (parts.length != 2) {
                     this.warn("Invalid block property: " + param);
                     return null;
                  }

                  String key = parts[0];
                  String valStr = parts[1];
                  axj prop = ConnectedProperties.getProperty(key, properties);
                  if (prop == null) {
                     this.warn("Property not found: " + key + ", block: " + block);
                     return null;
                  }

                  List list = (List)mapPropValues.get(key);
                  if (list == null) {
                     list = new ArrayList();
                     mapPropValues.put(prop, list);
                  }

                  String[] vals = .Config.tokenize(valStr, ",");

                  for(int v = 0; v < vals.length; ++v) {
                     String val = vals[v];
                     Comparable propVal = parsePropertyValue(prop, val);
                     if (propVal == null) {
                        this.warn("Property value not found: " + val + ", property: " + key + ", block: " + block);
                        return null;
                     }

                     ((List)list).add(propVal);
                  }
               }
            }

            if (mapPropValues.isEmpty()) {
               return null;
            } else {
               List listMetadatas = new ArrayList();

               int i;
               for(int i = 0; i < 16; ++i) {
                  i = i;

                  try {
                     awt bs = this.getStateFromMeta(block, i);
                     if (this.matchState(bs, mapPropValues)) {
                        listMetadatas.add(i);
                     }
                  } catch (IllegalArgumentException var18) {
                     ;
                  }
               }

               if (listMetadatas.size() == 16) {
                  return null;
               } else {
                  int[] metadatas = new int[listMetadatas.size()];

                  for(i = 0; i < metadatas.length; ++i) {
                     metadatas[i] = ((Integer)listMetadatas.get(i)).intValue();
                  }

                  return metadatas;
               }
            }
         }
      }
   }

   private awt getStateFromMeta(aow block, int md) {
      try {
         awt bs = block.a(md);
         if (block == aox.cF && md > 7) {
            awt bsLow = block.a(md & 7);
            bs = bs.a(aqb.a, bsLow.c(aqb.a));
         }

         if (block == aox.dk && (md & 8) != 0) {
            bs = bs.a(asl.a, true);
         }

         return bs;
      } catch (IllegalArgumentException var5) {
         return block.t();
      }
   }

   public static Comparable parsePropertyValue(axj prop, String valStr) {
      Class valueClass = prop.b();
      Comparable valueObj = parseValue(valStr, valueClass);
      if (valueObj == null) {
         Collection propertyValues = prop.c();
         valueObj = getPropertyValue(valStr, propertyValues);
      }

      return valueObj;
   }

   public static Comparable getPropertyValue(String value, Collection propertyValues) {
      Iterator it = propertyValues.iterator();

      Comparable obj;
      do {
         if (!it.hasNext()) {
            return null;
         }

         obj = (Comparable)it.next();
      } while(!getValueName(obj).equals(value));

      return obj;
   }

   private static Object getValueName(Comparable obj) {
      if (obj instanceof ro) {
         ro iss = (ro)obj;
         return iss.m();
      } else {
         return obj.toString();
      }
   }

   public static Comparable parseValue(String str, Class cls) {
      if (cls == String.class) {
         return str;
      } else if (cls == Boolean.class) {
         return Boolean.valueOf(str);
      } else if (cls == Float.class) {
         return Float.valueOf(str);
      } else if (cls == Double.class) {
         return Double.valueOf(str);
      } else if (cls == Integer.class) {
         return Integer.valueOf(str);
      } else {
         return cls == Long.class ? Long.valueOf(str) : null;
      }
   }

   public boolean matchState(awt bs, Map mapPropValues) {
      Set keys = mapPropValues.keySet();
      Iterator it = keys.iterator();

      List vals;
      Comparable bsVal;
      do {
         if (!it.hasNext()) {
            return true;
         }

         axj prop = (axj)it.next();
         vals = (List)mapPropValues.get(prop);
         bsVal = bs.c(prop);
         if (bsVal == null) {
            return false;
         }
      } while(vals.contains(bsVal));

      return false;
   }

   public anh[] parseBiomes(String str) {
      if (str == null) {
         return null;
      } else {
         str = str.trim();
         boolean negative = false;
         if (str.startsWith("!")) {
            negative = true;
            str = str.substring(1);
         }

         String[] biomeNames = .Config.tokenize(str, " ");
         List list = new ArrayList();

         for(int i = 0; i < biomeNames.length; ++i) {
            String biomeName = biomeNames[i];
            anh biome = this.findBiome(biomeName);
            if (biome == null) {
               this.warn("Biome not found: " + biomeName);
            } else {
               list.add(biome);
            }
         }

         if (negative) {
            List listAllBiomes = Lists.newArrayList(anh.p.iterator());
            listAllBiomes.removeAll(list);
            list = listAllBiomes;
         }

         anh[] biomeArr = (anh[])((anh[])list.toArray(new anh[list.size()]));
         return biomeArr;
      }
   }

   public anh findBiome(String biomeName) {
      biomeName = biomeName.toLowerCase();
      if (biomeName.equals("nether")) {
         return anm.j;
      } else {
         Set biomeIds = anh.p.c();
         Iterator it = biomeIds.iterator();

         while(it.hasNext()) {
            nf loc = (nf)it.next();
            anh biome = (anh)anh.p.c(loc);
            if (biome != null) {
               String name = biome.l().replace(" ", "").toLowerCase();
               if (name.equals(biomeName)) {
                  return biome;
               }
            }
         }

         return null;
      }
   }

   public int parseInt(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();
         int num = .Config.parseInt(str, -1);
         if (num < 0) {
            this.warn("Invalid number: " + str);
            return defVal;
         } else {
            return num;
         }
      }
   }

   public int[] parseIntList(String str) {
      if (str == null) {
         return null;
      } else {
         List list = new ArrayList();
         String[] intStrs = .Config.tokenize(str, " ,");

         for(int i = 0; i < intStrs.length; ++i) {
            String intStr = intStrs[i];
            if (intStr.contains("-")) {
               String[] subStrs = .Config.tokenize(intStr, "-");
               if (subStrs.length != 2) {
                  this.warn("Invalid interval: " + intStr + ", when parsing: " + str);
               } else {
                  int min = .Config.parseInt(subStrs[0], -1);
                  int max = .Config.parseInt(subStrs[1], -1);
                  if (min >= 0 && max >= 0 && min <= max) {
                     for(int n = min; n <= max; ++n) {
                        list.add(n);
                     }
                  } else {
                     this.warn("Invalid interval: " + intStr + ", when parsing: " + str);
                  }
               }
            } else {
               int val = .Config.parseInt(intStr, -1);
               if (val < 0) {
                  this.warn("Invalid number: " + intStr + ", when parsing: " + str);
               } else {
                  list.add(val);
               }
            }
         }

         int[] ints = new int[list.size()];

         for(int i = 0; i < ints.length; ++i) {
            ints[i] = ((Integer)list.get(i)).intValue();
         }

         return ints;
      }
   }

   public boolean[] parseFaces(String str, boolean[] defVal) {
      if (str == null) {
         return defVal;
      } else {
         EnumSet setFaces = EnumSet.allOf(fa.class);
         String[] faceStrs = .Config.tokenize(str, " ,");

         for(int i = 0; i < faceStrs.length; ++i) {
            String faceStr = faceStrs[i];
            if (faceStr.equals("sides")) {
               setFaces.add(fa.c);
               setFaces.add(fa.d);
               setFaces.add(fa.e);
               setFaces.add(fa.f);
            } else if (faceStr.equals("all")) {
               setFaces.addAll(Arrays.asList(fa.n));
            } else {
               fa face = this.parseFace(faceStr);
               if (face != null) {
                  setFaces.add(face);
               }
            }
         }

         boolean[] faces = new boolean[fa.n.length];

         for(int i = 0; i < faces.length; ++i) {
            faces[i] = setFaces.contains(fa.n[i]);
         }

         return faces;
      }
   }

   public fa parseFace(String str) {
      str = str.toLowerCase();
      if (!str.equals("bottom") && !str.equals("down")) {
         if (!str.equals("top") && !str.equals("up")) {
            if (str.equals("north")) {
               return fa.c;
            } else if (str.equals("south")) {
               return fa.d;
            } else if (str.equals("east")) {
               return fa.f;
            } else if (str.equals("west")) {
               return fa.e;
            } else {
               .Config.warn("Unknown face: " + str);
               return null;
            }
         } else {
            return fa.b;
         }
      } else {
         return fa.a;
      }
   }

   public void dbg(String str) {
      .Config.dbg("" + this.context + ": " + str);
   }

   public void warn(String str) {
      .Config.warn("" + this.context + ": " + str);
   }

   public RangeListInt parseRangeListInt(String str) {
      if (str == null) {
         return null;
      } else {
         RangeListInt list = new RangeListInt();
         String[] parts = .Config.tokenize(str, " ,");

         for(int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            RangeInt ri = this.parseRangeInt(part);
            if (ri == null) {
               return null;
            }

            list.addRange(ri);
         }

         return list;
      }
   }

   private RangeInt parseRangeInt(String str) {
      if (str == null) {
         return null;
      } else if (str.indexOf(45) >= 0) {
         String[] parts = .Config.tokenize(str, "-");
         if (parts.length != 2) {
            this.warn("Invalid range: " + str);
            return null;
         } else {
            int min = .Config.parseInt(parts[0], -1);
            int max = .Config.parseInt(parts[1], -1);
            if (min >= 0 && max >= 0) {
               return new RangeInt(min, max);
            } else {
               this.warn("Invalid range: " + str);
               return null;
            }
         }
      } else {
         int val = .Config.parseInt(str, -1);
         if (val < 0) {
            this.warn("Invalid integer: " + str);
            return null;
         } else {
            return new RangeInt(val, val);
         }
      }
   }

   public boolean parseBoolean(String str, boolean defVal) {
      if (str == null) {
         return defVal;
      } else {
         String strLower = str.toLowerCase().trim();
         if (strLower.equals("true")) {
            return true;
         } else if (strLower.equals("false")) {
            return false;
         } else {
            this.warn("Invalid boolean: " + str);
            return defVal;
         }
      }
   }

   public Boolean parseBooleanObject(String str) {
      if (str == null) {
         return null;
      } else {
         String strLower = str.toLowerCase().trim();
         if (strLower.equals("true")) {
            return Boolean.TRUE;
         } else if (strLower.equals("false")) {
            return Boolean.FALSE;
         } else {
            this.warn("Invalid boolean: " + str);
            return null;
         }
      }
   }

   public static int parseColor(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();

         try {
            int val = Integer.parseInt(str, 16) & 16777215;
            return val;
         } catch (NumberFormatException var3) {
            return defVal;
         }
      }
   }

   public static int parseColor4(String str, int defVal) {
      if (str == null) {
         return defVal;
      } else {
         str = str.trim();

         try {
            int val = (int)(Long.parseLong(str, 16) & -1L);
            return val;
         } catch (NumberFormatException var3) {
            return defVal;
         }
      }
   }

   public amm parseBlockRenderLayer(String str, amm def) {
      if (str == null) {
         return def;
      } else {
         str = str.toLowerCase().trim();
         amm[] layers = amm.values();

         for(int i = 0; i < layers.length; ++i) {
            amm layer = layers[i];
            if (str.equals(layer.name().toLowerCase())) {
               return layer;
            }
         }

         return def;
      }
   }

   public Enum parseEnum(String str, Enum[] enums, String property) {
      if (str == null) {
         return null;
      } else {
         String strLower = str.toLowerCase().trim();

         for(int i = 0; i < enums.length; ++i) {
            Enum en = enums[i];
            if (en.name().toLowerCase().equals(strLower)) {
               return en;
            }
         }

         this.warn("Invalid " + property + ": " + str);
         return null;
      }
   }

   public Enum[] parseEnums(String str, Enum[] enums, String property, Enum[] errValue) {
      if (str == null) {
         return null;
      } else {
         str = str.toLowerCase().trim();
         String[] parts = .Config.tokenize(str, " ");
         Enum[] arr = (Enum[])((Enum[])Array.newInstance(enums.getClass().getComponentType(), parts.length));

         for(int i = 0; i < parts.length; ++i) {
            String part = parts[i];
            Enum en = this.parseEnum(part, enums, property);
            if (en == null) {
               return errValue;
            }

            arr[i] = en;
         }

         return arr;
      }
   }

   public NbtTagValue parseNbtTagValue(String path, String value) {
      return path != null && value != null ? new NbtTagValue(path, value) : null;
   }

   public VillagerProfession[] parseProfessions(String profStr) {
      if (profStr == null) {
         return null;
      } else {
         List list = new ArrayList();
         String[] tokens = .Config.tokenize(profStr, " ");

         for(int i = 0; i < tokens.length; ++i) {
            String str = tokens[i];
            VillagerProfession prof = this.parseProfession(str);
            if (prof == null) {
               this.warn("Invalid profession: " + str);
               return PROFESSIONS_INVALID;
            }

            list.add(prof);
         }

         if (list.isEmpty()) {
            return null;
         } else {
            VillagerProfession[] arr = (VillagerProfession[])((VillagerProfession[])list.toArray(new VillagerProfession[list.size()]));
            return arr;
         }
      }
   }

   private VillagerProfession parseProfession(String str) {
      str = str.toLowerCase();
      String[] parts = .Config.tokenize(str, ":");
      if (parts.length > 2) {
         return null;
      } else {
         String profStr = parts[0];
         String carStr = null;
         if (parts.length > 1) {
            carStr = parts[1];
         }

         int prof = parseProfessionId(profStr);
         if (prof < 0) {
            return null;
         } else {
            int[] cars = null;
            if (carStr != null) {
               cars = parseCareerIds(prof, carStr);
               if (cars == null) {
                  return null;
               }
            }

            return new VillagerProfession(prof, cars);
         }
      }
   }

   private static int parseProfessionId(String str) {
      int id = .Config.parseInt(str, -1);
      if (id >= 0) {
         return id;
      } else if (str.equals("farmer")) {
         return 0;
      } else if (str.equals("librarian")) {
         return 1;
      } else if (str.equals("priest")) {
         return 2;
      } else if (str.equals("blacksmith")) {
         return 3;
      } else if (str.equals("butcher")) {
         return 4;
      } else {
         return str.equals("nitwit") ? 5 : -1;
      }
   }

   private static int[] parseCareerIds(int prof, String str) {
      IntSet set = new IntArraySet();
      String[] parts = .Config.tokenize(str, ",");

      for(int i = 0; i < parts.length; ++i) {
         String part = parts[i];
         int id = parseCareerId(prof, part);
         if (id < 0) {
            return null;
         }

         set.add(id);
      }

      int[] arr = set.toIntArray();
      return arr;
   }

   private static int parseCareerId(int prof, String str) {
      int id = .Config.parseInt(str, -1);
      if (id >= 0) {
         return id;
      } else {
         if (prof == 0) {
            if (str.equals("farmer")) {
               return 1;
            }

            if (str.equals("fisherman")) {
               return 2;
            }

            if (str.equals("shepherd")) {
               return 3;
            }

            if (str.equals("fletcher")) {
               return 4;
            }
         }

         if (prof == 1) {
            if (str.equals("librarian")) {
               return 1;
            }

            if (str.equals("cartographer")) {
               return 2;
            }
         }

         if (prof == 2 && str.equals("cleric")) {
            return 1;
         } else {
            if (prof == 3) {
               if (str.equals("armor")) {
                  return 1;
               }

               if (str.equals("weapon")) {
                  return 2;
               }

               if (str.equals("tool")) {
                  return 3;
               }
            }

            if (prof == 4) {
               if (str.equals("butcher")) {
                  return 1;
               }

               if (str.equals("leather")) {
                  return 2;
               }
            }

            return prof == 5 && str.equals("nitwit") ? 1 : -1;
         }
      }
   }
}
