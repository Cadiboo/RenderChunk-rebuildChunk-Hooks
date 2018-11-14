package net.optifine.entity.model.anim;

import net.optifine.expr.IExpression;

public class RenderResolverEntity implements IRenderResolver {
   public IExpression getParameter(String name) {
      RenderEntityParameterBool parBool = RenderEntityParameterBool.parse(name);
      if (parBool != null) {
         return parBool;
      } else {
         RenderEntityParameterFloat parFloat = RenderEntityParameterFloat.parse(name);
         return parFloat != null ? parFloat : null;
      }
   }
}
