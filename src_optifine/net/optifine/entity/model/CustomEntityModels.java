package net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomEntityModels {
   private static boolean active = false;
   private static Map originalEntityRenderMap = null;
   private static Map originalTileEntityRenderMap = null;

   public static void update() {
      Map entityRenderMap = getEntityRenderMap();
      Map tileEntityRenderMap = getTileEntityRenderMap();
      if (entityRenderMap == null) {
         .Config.warn("Entity render map not found, custom entity models are DISABLED.");
      } else if (tileEntityRenderMap == null) {
         .Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
      } else {
         active = false;
         entityRenderMap.clear();
         tileEntityRenderMap.clear();
         entityRenderMap.putAll(originalEntityRenderMap);
         tileEntityRenderMap.putAll(originalTileEntityRenderMap);
         if (.Config.isCustomEntityModels()) {
            nf[] locs = getModelLocations();

            for(int i = 0; i < locs.length; ++i) {
               nf loc = locs[i];
               .Config.dbg("CustomEntityModel: " + loc.a());
               IEntityRenderer rc = parseEntityRender(loc);
               if (rc != null) {
                  Class entityClass = rc.getEntityClass();
                  if (entityClass != null) {
                     if (rc instanceof bzg) {
                        entityRenderMap.put(entityClass, (bzg)rc);
                     } else if (rc instanceof bwy) {
                        tileEntityRenderMap.put(entityClass, (bwy)rc);
                     } else {
                        .Config.warn("Unknown renderer type: " + rc.getClass().getName());
                     }

                     active = true;
                  }
               }
            }

         }
      }
   }

   private static Map getEntityRenderMap() {
      bzf rm = bib.z().ac();
      Map entityRenderMap = rm.getEntityRenderMap();
      if (entityRenderMap == null) {
         return null;
      } else {
         if (originalEntityRenderMap == null) {
            originalEntityRenderMap = new HashMap(entityRenderMap);
         }

         return entityRenderMap;
      }
   }

   private static Map getTileEntityRenderMap() {
      Map tileEntityRenderMap = bwx.a.n;
      if (originalTileEntityRenderMap == null) {
         originalTileEntityRenderMap = new HashMap(tileEntityRenderMap);
      }

      return tileEntityRenderMap;
   }

   private static nf[] getModelLocations() {
      String prefix = "optifine/cem/";
      String suffix = ".jem";
      List resourceLocations = new ArrayList();
      String[] names = CustomModelRegistry.getModelNames();

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         String path = prefix + name + suffix;
         nf loc = new nf(path);
         if (.Config.hasResource(loc)) {
            resourceLocations.add(loc);
         }
      }

      nf[] locs = (nf[])((nf[])resourceLocations.toArray(new nf[resourceLocations.size()]));
      return locs;
   }

   private static IEntityRenderer parseEntityRender(nf location) {
      try {
         JsonObject jo = CustomEntityModelParser.loadJson(location);
         IEntityRenderer render = parseEntityRender(jo, location.a());
         return render;
      } catch (IOException var3) {
         .Config.error("" + var3.getClass().getName() + ": " + var3.getMessage());
         return null;
      } catch (JsonParseException var4) {
         .Config.error("" + var4.getClass().getName() + ": " + var4.getMessage());
         return null;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }

   private static IEntityRenderer parseEntityRender(JsonObject obj, String path) {
      CustomEntityRenderer cer = CustomEntityModelParser.parseEntityRender(obj, path);
      String name = cer.getName();
      ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
      checkNull(modelAdapter, "Entity not found: " + name);
      Class entityClass = modelAdapter.getEntityClass();
      checkNull(entityClass, "Entity class not found: " + name);
      IEntityRenderer render = makeEntityRender(modelAdapter, cer);
      if (render == null) {
         return null;
      } else {
         render.setEntityClass(entityClass);
         return render;
      }
   }

   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer) {
      nf textureLocation = cer.getTextureLocation();
      CustomModelRenderer[] modelRenderers = cer.getCustomModelRenderers();
      float shadowSize = cer.getShadowSize();
      if (shadowSize < 0.0F) {
         shadowSize = modelAdapter.getShadowSize();
      }

      bqf model = modelAdapter.makeModel();
      if (model == null) {
         return null;
      } else {
         ModelResolver mr = new ModelResolver(modelAdapter, model, modelRenderers);
         if (!modifyModel(modelAdapter, model, modelRenderers, mr)) {
            return null;
         } else {
            IEntityRenderer r = modelAdapter.makeEntityRender(model, shadowSize);
            if (r == null) {
               throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
            } else {
               if (textureLocation != null) {
                  r.setLocationTextureCustom(textureLocation);
               }

               return r;
            }
         }
      }
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, bqf model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
      for(int i = 0; i < modelRenderers.length; ++i) {
         CustomModelRenderer cmr = modelRenderers[i];
         if (!modifyModel(modelAdapter, model, cmr, mr)) {
            return false;
         }
      }

      return true;
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, bqf model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
      String modelPart = customModelRenderer.getModelPart();
      brs parent = modelAdapter.getModelRenderer(model, modelPart);
      if (parent == null) {
         .Config.warn("Model part not found: " + modelPart + ", model: " + model);
         return false;
      } else {
         if (!customModelRenderer.isAttach()) {
            if (parent.l != null) {
               parent.l.clear();
            }

            if (parent.spriteList != null) {
               parent.spriteList.clear();
            }

            if (parent.m != null) {
               parent.m.clear();
            }
         }

         parent.a(customModelRenderer.getModelRenderer());
         ModelUpdater mu = customModelRenderer.getModelUpdater();
         if (mu != null) {
            modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
            modelResolver.setPartModelRenderer(parent);
            if (!mu.initialize(modelResolver)) {
               return false;
            }

            customModelRenderer.getModelRenderer().setModelUpdater(mu);
         }

         return true;
      }
   }

   private static void checkNull(Object obj, String msg) {
      if (obj == null) {
         throw new JsonParseException(msg);
      }
   }

   public static boolean isActive() {
      return active;
   }
}
