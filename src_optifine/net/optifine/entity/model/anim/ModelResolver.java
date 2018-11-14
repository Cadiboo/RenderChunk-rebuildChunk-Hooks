package net.optifine.entity.model.anim;

import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.expr.IExpression;

public class ModelResolver implements IModelResolver {
   private ModelAdapter modelAdapter;
   private bqf model;
   private CustomModelRenderer[] customModelRenderers;
   private brs thisModelRenderer;
   private brs partModelRenderer;
   private IRenderResolver renderResolver;

   public ModelResolver(ModelAdapter modelAdapter, bqf model, CustomModelRenderer[] customModelRenderers) {
      this.modelAdapter = modelAdapter;
      this.model = model;
      this.customModelRenderers = customModelRenderers;
      Class entityClass = modelAdapter.getEntityClass();
      if (avj.class.isAssignableFrom(entityClass)) {
         this.renderResolver = new RenderResolverTileEntity();
      } else {
         this.renderResolver = new RenderResolverEntity();
      }

   }

   public IExpression getExpression(String name) {
      IExpression mv = this.getModelVariable(name);
      if (mv != null) {
         return mv;
      } else {
         IExpression param = this.renderResolver.getParameter(name);
         return param != null ? param : null;
      }
   }

   public brs getModelRenderer(String name) {
      if (name == null) {
         return null;
      } else {
         brs mrChild;
         if (name.indexOf(":") >= 0) {
            String[] parts = .Config.tokenize(name, ":");
            brs mr = this.getModelRenderer(parts[0]);

            for(int i = 1; i < parts.length; ++i) {
               String part = parts[i];
               mrChild = mr.getChildDeep(part);
               if (mrChild == null) {
                  return null;
               }

               mr = mrChild;
            }

            return mr;
         } else if (this.thisModelRenderer != null && name.equals("this")) {
            return this.thisModelRenderer;
         } else if (this.partModelRenderer != null && name.equals("part")) {
            return this.partModelRenderer;
         } else {
            brs mrPart = this.modelAdapter.getModelRenderer(this.model, name);
            if (mrPart != null) {
               return mrPart;
            } else {
               for(int i = 0; i < this.customModelRenderers.length; ++i) {
                  CustomModelRenderer cmr = this.customModelRenderers[i];
                  brs mr = cmr.getModelRenderer();
                  if (name.equals(mr.getId())) {
                     return mr;
                  }

                  mrChild = mr.getChildDeep(name);
                  if (mrChild != null) {
                     return mrChild;
                  }
               }

               return null;
            }
         }
      }
   }

   public ModelVariableFloat getModelVariable(String name) {
      String[] parts = .Config.tokenize(name, ".");
      if (parts.length != 2) {
         return null;
      } else {
         String modelName = parts[0];
         String varName = parts[1];
         brs mr = this.getModelRenderer(modelName);
         if (mr == null) {
            return null;
         } else {
            ModelVariableType varType = ModelVariableType.parse(varName);
            return varType == null ? null : new ModelVariableFloat(name, mr, varType);
         }
      }
   }

   public void setPartModelRenderer(brs partModelRenderer) {
      this.partModelRenderer = partModelRenderer;
   }

   public void setThisModelRenderer(brs thisModelRenderer) {
      this.thisModelRenderer = thisModelRenderer;
   }
}
