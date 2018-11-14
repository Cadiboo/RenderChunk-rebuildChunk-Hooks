package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionCached;

public class CustomUniforms {
   private CustomUniform[] uniforms;
   private IExpressionCached[] expressionsCached;

   public CustomUniforms(CustomUniform[] uniforms, Map mapExpressions) {
      this.uniforms = uniforms;
      List list = new ArrayList();
      Set keys = mapExpressions.keySet();
      Iterator it = keys.iterator();

      while(it.hasNext()) {
         String key = (String)it.next();
         IExpression expr = (IExpression)mapExpressions.get(key);
         if (expr instanceof IExpressionCached) {
            IExpressionCached exprCached = (IExpressionCached)expr;
            list.add(exprCached);
         }
      }

      this.expressionsCached = (IExpressionCached[])((IExpressionCached[])list.toArray(new IExpressionCached[list.size()]));
   }

   public void setProgram(int program) {
      for(int i = 0; i < this.uniforms.length; ++i) {
         CustomUniform uniform = this.uniforms[i];
         uniform.setProgram(program);
      }

   }

   public void update() {
      this.resetCache();

      for(int i = 0; i < this.uniforms.length; ++i) {
         CustomUniform uniform = this.uniforms[i];
         uniform.update();
      }

   }

   private void resetCache() {
      for(int i = 0; i < this.expressionsCached.length; ++i) {
         IExpressionCached exprCached = this.expressionsCached[i];
         exprCached.reset();
      }

   }

   public void reset() {
      for(int i = 0; i < this.uniforms.length; ++i) {
         CustomUniform cu = this.uniforms[i];
         cu.reset();
      }

   }
}
