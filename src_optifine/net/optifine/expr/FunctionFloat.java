package net.optifine.expr;

import net.optifine.shaders.uniform.Smoother;

public class FunctionFloat implements IExpressionFloat {
   private FunctionType type;
   private IExpression[] arguments;
   private int smoothId = -1;

   public FunctionFloat(FunctionType type, IExpression[] arguments) {
      this.type = type;
      this.arguments = arguments;
   }

   public float eval() {
      IExpression[] args = this.arguments;
      switch(this.type) {
      case SMOOTH:
         IExpression expr0 = args[0];
         if (!(expr0 instanceof ConstantFloat)) {
            float valRaw = evalFloat(args, 0);
            float valFadeUp = args.length > 1 ? evalFloat(args, 1) : 1.0F;
            float valFadeDown = args.length > 2 ? evalFloat(args, 2) : valFadeUp;
            if (this.smoothId < 0) {
               this.smoothId = Smoother.getNextId();
            }

            float valSmooth = Smoother.getSmoothValue(this.smoothId, valRaw, valFadeUp, valFadeDown);
            return valSmooth;
         }
      default:
         return this.type.evalFloat(this.arguments);
      }
   }

   private static float evalFloat(IExpression[] exprs, int index) {
      IExpressionFloat ef = (IExpressionFloat)exprs[index];
      float val = ef.eval();
      return val;
   }

   public String toString() {
      return "" + this.type + "()";
   }
}
