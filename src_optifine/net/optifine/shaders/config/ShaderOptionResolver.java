package net.optifine.shaders.config;

import java.util.HashMap;
import java.util.Map;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;

public class ShaderOptionResolver implements IExpressionResolver {
   private Map mapOptions = new HashMap();

   public ShaderOptionResolver(ShaderOption[] options) {
      for(int i = 0; i < options.length; ++i) {
         ShaderOption so = options[i];
         if (so instanceof ShaderOptionSwitch) {
            ShaderOptionSwitch sos = (ShaderOptionSwitch)so;
            ExpressionShaderOptionSwitch esos = new ExpressionShaderOptionSwitch(sos);
            this.mapOptions.put(so.getName(), esos);
         }
      }

   }

   public IExpression getExpression(String name) {
      ExpressionShaderOptionSwitch esos = (ExpressionShaderOptionSwitch)this.mapOptions.get(name);
      return esos;
   }
}
