package net.optifine.entity.model;

import java.util.Iterator;
import java.util.List;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(aag.class, "sheep_wool", 0.7F);
   }

   public bqf makeModel() {
      return new bqo();
   }

   public IEntityRenderer makeEntityRender(bqf modelBase, float shadowSize) {
      bzf renderManager = bib.z().ac();
      bzg render = (bzg)renderManager.getEntityRenderMap().get(aag.class);
      if (!(render instanceof cao)) {
         .Config.warn("Not a RenderSheep: " + render);
         return null;
      } else {
         cao renderSheep;
         if (((bzg)render).getEntityClass() == null) {
            renderSheep = new cao(renderManager);
            renderSheep.f = new bqp();
            renderSheep.c = 0.7F;
            render = renderSheep;
         }

         renderSheep = (cao)render;
         List list = renderSheep.getLayerRenderers();
         Iterator it = list.iterator();

         while(it.hasNext()) {
            ccg layerRenderer = (ccg)it.next();
            if (layerRenderer instanceof cch) {
               it.remove();
            }
         }

         cch layer = new cch(renderSheep);
         layer.c = (bqo)modelBase;
         renderSheep.a(layer);
         return renderSheep;
      }
   }
}
