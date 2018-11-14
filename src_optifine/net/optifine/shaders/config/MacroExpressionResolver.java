package net.optifine.shaders.config;

import java.util.Map;
import net.optifine.expr.ConstantFloat;
import net.optifine.expr.FunctionBool;
import net.optifine.expr.FunctionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionResolver;

public class MacroExpressionResolver implements IExpressionResolver {
   private Map mapMacroValues = null;

   public MacroExpressionResolver(Map mapMacroValues) {
      this.mapMacroValues = mapMacroValues;
   }

   public IExpression getExpression(String name) {
      String PREFIX_DEFINED = "defined_";
      String nameNext;
      if (name.startsWith(PREFIX_DEFINED)) {
         nameNext = name.substring(PREFIX_DEFINED.length());
         return this.mapMacroValues.containsKey(nameNext) ? new FunctionBool(FunctionType.TRUE, (IExpression[])null) : new FunctionBool(FunctionType.FALSE, (IExpression[])null);
      } else {
         while(this.mapMacroValues.containsKey(name)) {
            nameNext = (String)this.mapMacroValues.get(name);
            if (nameNext == null || nameNext.equals(name)) {
               break;
            }

            name = nameNext;
         }

         int valInt = .Config.parseInt(name, Integer.MIN_VALUE);
         if (valInt == Integer.MIN_VALUE) {
            .Config.warn("Unknown macro value: " + name);
            return new ConstantFloat(0.0F);
         } else {
            return new ConstantFloat((float)valInt);
         }
      }
   }
}
