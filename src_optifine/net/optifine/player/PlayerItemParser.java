package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.util.Json;

public class PlayerItemParser {
   private static JsonParser jsonParser = new JsonParser();
   public static final String ITEM_TYPE = "type";
   public static final String ITEM_TEXTURE_SIZE = "textureSize";
   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
   public static final String ITEM_MODELS = "models";
   public static final String MODEL_ID = "id";
   public static final String MODEL_BASE_ID = "baseId";
   public static final String MODEL_TYPE = "type";
   public static final String MODEL_TEXTURE = "texture";
   public static final String MODEL_TEXTURE_SIZE = "textureSize";
   public static final String MODEL_ATTACH_TO = "attachTo";
   public static final String MODEL_INVERT_AXIS = "invertAxis";
   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
   public static final String MODEL_TRANSLATE = "translate";
   public static final String MODEL_ROTATE = "rotate";
   public static final String MODEL_SCALE = "scale";
   public static final String MODEL_BOXES = "boxes";
   public static final String MODEL_SPRITES = "sprites";
   public static final String MODEL_SUBMODEL = "submodel";
   public static final String MODEL_SUBMODELS = "submodels";
   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
   public static final String BOX_COORDINATES = "coordinates";
   public static final String BOX_SIZE_ADD = "sizeAdd";
   public static final String BOX_UV_DOWN = "uvDown";
   public static final String BOX_UV_UP = "uvUp";
   public static final String BOX_UV_NORTH = "uvNorth";
   public static final String BOX_UV_SOUTH = "uvSouth";
   public static final String BOX_UV_WEST = "uvWest";
   public static final String BOX_UV_EAST = "uvEast";
   public static final String BOX_UV_FRONT = "uvFront";
   public static final String BOX_UV_BACK = "uvBack";
   public static final String BOX_UV_LEFT = "uvLeft";
   public static final String BOX_UV_RIGHT = "uvRight";
   public static final String ITEM_TYPE_MODEL = "PlayerItem";
   public static final String MODEL_TYPE_BOX = "ModelBox";

   public static PlayerItemModel parseItemModel(JsonObject obj) {
      String type = Json.getString(obj, "type");
      if (!.Config.equals(type, "PlayerItem")) {
         throw new JsonParseException("Unknown model type: " + type);
      } else {
         int[] textureSize = Json.parseIntArray(obj.get("textureSize"), 2);
         checkNull(textureSize, "Missing texture size");
         Dimension textureDim = new Dimension(textureSize[0], textureSize[1]);
         boolean usePlayerTexture = Json.getBoolean(obj, "usePlayerTexture", false);
         JsonArray models = (JsonArray)obj.get("models");
         checkNull(models, "Missing elements");
         Map mapModelJsons = new HashMap();
         List listModels = new ArrayList();
         new ArrayList();

         for(int i = 0; i < models.size(); ++i) {
            JsonObject elem = (JsonObject)models.get(i);
            String baseId = Json.getString(elem, "baseId");
            if (baseId != null) {
               JsonObject baseObj = (JsonObject)mapModelJsons.get(baseId);
               if (baseObj == null) {
                  .Config.warn("BaseID not found: " + baseId);
                  continue;
               }

               Set setEntries = baseObj.entrySet();
               Iterator iterator = setEntries.iterator();

               while(iterator.hasNext()) {
                  Entry entry = (Entry)iterator.next();
                  if (!elem.has((String)entry.getKey())) {
                     elem.add((String)entry.getKey(), (JsonElement)entry.getValue());
                  }
               }
            }

            String id = Json.getString(elem, "id");
            if (id != null) {
               if (!mapModelJsons.containsKey(id)) {
                  mapModelJsons.put(id, elem);
               } else {
                  .Config.warn("Duplicate model ID: " + id);
               }
            }

            PlayerItemRenderer mr = parseItemRenderer(elem, textureDim);
            if (mr != null) {
               listModels.add(mr);
            }
         }

         PlayerItemRenderer[] modelRenderers = (PlayerItemRenderer[])((PlayerItemRenderer[])listModels.toArray(new PlayerItemRenderer[listModels.size()]));
         return new PlayerItemModel(textureDim, usePlayerTexture, modelRenderers);
      }
   }

   private static void checkNull(Object obj, String msg) {
      if (obj == null) {
         throw new JsonParseException(msg);
      }
   }

   private static nf makeResourceLocation(String texture) {
      int pos = texture.indexOf(58);
      if (pos < 0) {
         return new nf(texture);
      } else {
         String domain = texture.substring(0, pos);
         String path = texture.substring(pos + 1);
         return new nf(domain, path);
      }
   }

   private static int parseAttachModel(String attachModelStr) {
      if (attachModelStr == null) {
         return 0;
      } else if (attachModelStr.equals("body")) {
         return 0;
      } else if (attachModelStr.equals("head")) {
         return 1;
      } else if (attachModelStr.equals("leftArm")) {
         return 2;
      } else if (attachModelStr.equals("rightArm")) {
         return 3;
      } else if (attachModelStr.equals("leftLeg")) {
         return 4;
      } else if (attachModelStr.equals("rightLeg")) {
         return 5;
      } else if (attachModelStr.equals("cape")) {
         return 6;
      } else {
         .Config.warn("Unknown attachModel: " + attachModelStr);
         return 0;
      }
   }

   public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
      String type = Json.getString(elem, "type");
      if (!.Config.equals(type, "ModelBox")) {
         .Config.warn("Unknown model type: " + type);
         return null;
      } else {
         String attachToStr = Json.getString(elem, "attachTo");
         int attachTo = parseAttachModel(attachToStr);
         bqf modelBase = new ModelPlayerItem();
         modelBase.s = textureDim.width;
         modelBase.t = textureDim.height;
         brs mr = parseModelRenderer(elem, modelBase, (int[])null, (String)null);
         PlayerItemRenderer pir = new PlayerItemRenderer(attachTo, mr);
         return pir;
      }
   }

   public static brs parseModelRenderer(JsonObject elem, bqf modelBase, int[] parentTextureSize, String basePath) {
      brs mr = new brs(modelBase);
      String id = Json.getString(elem, "id");
      mr.setId(id);
      float scale = Json.getFloat(elem, "scale", 1.0F);
      mr.scaleX = scale;
      mr.scaleY = scale;
      mr.scaleZ = scale;
      String texture = Json.getString(elem, "texture");
      if (texture != null) {
         mr.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, texture, ".png"));
      }

      int[] textureSize = Json.parseIntArray(elem.get("textureSize"), 2);
      if (textureSize == null) {
         textureSize = parentTextureSize;
      }

      if (textureSize != null) {
         mr.b(textureSize[0], textureSize[1]);
      }

      String invertAxis = Json.getString(elem, "invertAxis", "").toLowerCase();
      boolean invertX = invertAxis.contains("x");
      boolean invertY = invertAxis.contains("y");
      boolean invertZ = invertAxis.contains("z");
      float[] translate = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
      if (invertX) {
         translate[0] = -translate[0];
      }

      if (invertY) {
         translate[1] = -translate[1];
      }

      if (invertZ) {
         translate[2] = -translate[2];
      }

      float[] rotateAngles = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);

      for(int i = 0; i < rotateAngles.length; ++i) {
         rotateAngles[i] = rotateAngles[i] / 180.0F * 3.1415927F;
      }

      if (invertX) {
         rotateAngles[0] = -rotateAngles[0];
      }

      if (invertY) {
         rotateAngles[1] = -rotateAngles[1];
      }

      if (invertZ) {
         rotateAngles[2] = -rotateAngles[2];
      }

      mr.a(translate[0], translate[1], translate[2]);
      mr.f = rotateAngles[0];
      mr.g = rotateAngles[1];
      mr.h = rotateAngles[2];
      String mirrorTexture = Json.getString(elem, "mirrorTexture", "").toLowerCase();
      boolean invertU = mirrorTexture.contains("u");
      boolean invertV = mirrorTexture.contains("v");
      if (invertU) {
         mr.i = true;
      }

      if (invertV) {
         mr.mirrorV = true;
      }

      JsonArray boxes = elem.getAsJsonArray("boxes");
      JsonObject box;
      float[] coordinates;
      float sizeAdd;
      if (boxes != null) {
         for(int i = 0; i < boxes.size(); ++i) {
            box = boxes.get(i).getAsJsonObject();
            int[] textureOffset = Json.parseIntArray(box.get("textureOffset"), 2);
            int[][] faceUvs = parseFaceUvs(box);
            if (textureOffset == null && faceUvs == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            coordinates = Json.parseFloatArray(box.get("coordinates"), 6);
            if (coordinates == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (invertX) {
               coordinates[0] = -coordinates[0] - coordinates[3];
            }

            if (invertY) {
               coordinates[1] = -coordinates[1] - coordinates[4];
            }

            if (invertZ) {
               coordinates[2] = -coordinates[2] - coordinates[5];
            }

            sizeAdd = Json.getFloat(box, "sizeAdd", 0.0F);
            if (faceUvs != null) {
               mr.addBox(faceUvs, coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[4], coordinates[5], sizeAdd);
            } else {
               mr.a(textureOffset[0], textureOffset[1]);
               mr.a(coordinates[0], coordinates[1], coordinates[2], (int)coordinates[3], (int)coordinates[4], (int)coordinates[5], sizeAdd);
            }
         }
      }

      JsonArray sprites = elem.getAsJsonArray("sprites");
      if (sprites != null) {
         for(int i = 0; i < sprites.size(); ++i) {
            JsonObject sprite = sprites.get(i).getAsJsonObject();
            int[] textureOffset = Json.parseIntArray(sprite.get("textureOffset"), 2);
            if (textureOffset == null) {
               throw new JsonParseException("Texture offset not specified");
            }

            coordinates = Json.parseFloatArray(sprite.get("coordinates"), 6);
            if (coordinates == null) {
               throw new JsonParseException("Coordinates not specified");
            }

            if (invertX) {
               coordinates[0] = -coordinates[0] - coordinates[3];
            }

            if (invertY) {
               coordinates[1] = -coordinates[1] - coordinates[4];
            }

            if (invertZ) {
               coordinates[2] = -coordinates[2] - coordinates[5];
            }

            sizeAdd = Json.getFloat(sprite, "sizeAdd", 0.0F);
            mr.a(textureOffset[0], textureOffset[1]);
            mr.addSprite(coordinates[0], coordinates[1], coordinates[2], (int)coordinates[3], (int)coordinates[4], (int)coordinates[5], sizeAdd);
         }
      }

      box = (JsonObject)elem.get("submodel");
      if (box != null) {
         brs subMr = parseModelRenderer(box, modelBase, textureSize, basePath);
         mr.a(subMr);
      }

      JsonArray submodels = (JsonArray)elem.get("submodels");
      if (submodels != null) {
         for(int i = 0; i < submodels.size(); ++i) {
            JsonObject sm = (JsonObject)submodels.get(i);
            brs subMr = parseModelRenderer(sm, modelBase, textureSize, basePath);
            if (subMr.getId() != null) {
               brs subMrId = mr.getChild(subMr.getId());
               if (subMrId != null) {
                  .Config.warn("Duplicate model ID: " + subMr.getId());
               }
            }

            mr.a(subMr);
         }
      }

      return mr;
   }

   private static int[][] parseFaceUvs(JsonObject box) {
      int[][] uvs = new int[][]{Json.parseIntArray(box.get("uvDown"), 4), Json.parseIntArray(box.get("uvUp"), 4), Json.parseIntArray(box.get("uvNorth"), 4), Json.parseIntArray(box.get("uvSouth"), 4), Json.parseIntArray(box.get("uvWest"), 4), Json.parseIntArray(box.get("uvEast"), 4)};
      if (uvs[2] == null) {
         uvs[2] = Json.parseIntArray(box.get("uvFront"), 4);
      }

      if (uvs[3] == null) {
         uvs[3] = Json.parseIntArray(box.get("uvBack"), 4);
      }

      if (uvs[4] == null) {
         uvs[4] = Json.parseIntArray(box.get("uvLeft"), 4);
      }

      if (uvs[5] == null) {
         uvs[5] = Json.parseIntArray(box.get("uvRight"), 4);
      }

      boolean defined = false;

      for(int i = 0; i < uvs.length; ++i) {
         if (uvs[i] != null) {
            defined = true;
         }
      }

      if (!defined) {
         return (int[][])null;
      } else {
         return uvs;
      }
   }
}
