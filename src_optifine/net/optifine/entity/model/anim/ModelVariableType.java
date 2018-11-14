package net.optifine.entity.model.anim;

public enum ModelVariableType {
   POS_X("tx"),
   POS_Y("ty"),
   POS_Z("tz"),
   ANGLE_X("rx"),
   ANGLE_Y("ry"),
   ANGLE_Z("rz"),
   OFFSET_X("ox"),
   OFFSET_Y("oy"),
   OFFSET_Z("oz"),
   SCALE_X("sx"),
   SCALE_Y("sy"),
   SCALE_Z("sz");

   private String name;
   public static ModelVariableType[] VALUES = values();

   private ModelVariableType(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public float getFloat(brs mr) {
      switch(this) {
      case POS_X:
         return mr.c;
      case POS_Y:
         return mr.d;
      case POS_Z:
         return mr.e;
      case ANGLE_X:
         return mr.f;
      case ANGLE_Y:
         return mr.g;
      case ANGLE_Z:
         return mr.h;
      case OFFSET_X:
         return mr.o;
      case OFFSET_Y:
         return mr.p;
      case OFFSET_Z:
         return mr.q;
      case SCALE_X:
         return mr.scaleX;
      case SCALE_Y:
         return mr.scaleY;
      case SCALE_Z:
         return mr.scaleZ;
      default:
         .Config.warn("GetFloat not supported for: " + this);
         return 0.0F;
      }
   }

   public void setFloat(brs mr, float val) {
      switch(this) {
      case POS_X:
         mr.c = val;
         return;
      case POS_Y:
         mr.d = val;
         return;
      case POS_Z:
         mr.e = val;
         return;
      case ANGLE_X:
         mr.f = val;
         return;
      case ANGLE_Y:
         mr.g = val;
         return;
      case ANGLE_Z:
         mr.h = val;
         return;
      case OFFSET_X:
         mr.o = val;
         return;
      case OFFSET_Y:
         mr.p = val;
         return;
      case OFFSET_Z:
         mr.q = val;
         return;
      case SCALE_X:
         mr.scaleX = val;
         return;
      case SCALE_Y:
         mr.scaleY = val;
         return;
      case SCALE_Z:
         mr.scaleZ = val;
         return;
      default:
         .Config.warn("SetFloat not supported for: " + this);
      }
   }

   public static ModelVariableType parse(String str) {
      for(int i = 0; i < VALUES.length; ++i) {
         ModelVariableType var = VALUES[i];
         if (var.getName().equals(str)) {
            return var;
         }
      }

      return null;
   }
}
