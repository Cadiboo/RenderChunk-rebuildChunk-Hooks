package net.optifine.entity.model.anim;

import net.optifine.expr.IExpressionFloat;

public enum RenderEntityParameterFloat implements IExpressionFloat {
   LIMB_SWING("limb_swing"),
   LIMB_SWING_SPEED("limb_speed"),
   AGE("age"),
   HEAD_YAW("head_yaw"),
   HEAD_PITCH("head_pitch"),
   SCALE("scale"),
   HEALTH("health"),
   HURT_TIME("hurt_time"),
   IDLE_TIME("idle_time"),
   MAX_HEALTH("max_health"),
   MOVE_FORWARD("move_forward"),
   MOVE_STRAFING("move_strafing"),
   PARTIAL_TICKS("partial_ticks"),
   POS_X("pos_x"),
   POS_Y("pos_Y"),
   POS_Z("pos_Z"),
   REVENGE_TIME("revenge_time"),
   SWING_PROGRESS("swing_progress");

   private String name;
   private bzf renderManager;
   private static final RenderEntityParameterFloat[] VALUES = values();

   private RenderEntityParameterFloat(String name) {
      this.name = name;
      this.renderManager = bib.z().ac();
   }

   public String getName() {
      return this.name;
   }

   public float eval() {
      bzg render = this.renderManager.renderRender;
      if (render == null) {
         return 0.0F;
      } else {
         if (render instanceof caa) {
            caa rlb = (caa)render;
            switch(this) {
            case LIMB_SWING:
               return rlb.renderLimbSwing;
            case LIMB_SWING_SPEED:
               return rlb.renderLimbSwingAmount;
            case AGE:
               return rlb.renderAgeInTicks;
            case HEAD_YAW:
               return rlb.renderHeadYaw;
            case HEAD_PITCH:
               return rlb.renderHeadPitch;
            case SCALE:
               return rlb.renderScaleFactor;
            default:
               vp entity = rlb.renderEntity;
               if (entity == null) {
                  return 0.0F;
               }

               switch(this) {
               case HEALTH:
                  return entity.cd();
               case HURT_TIME:
                  return (float)entity.ay;
               case IDLE_TIME:
                  return (float)entity.bW();
               case MAX_HEALTH:
                  return entity.cj();
               case MOVE_FORWARD:
                  return entity.bf;
               case MOVE_STRAFING:
                  return entity.be;
               case POS_X:
                  return (float)entity.p;
               case POS_Y:
                  return (float)entity.q;
               case POS_Z:
                  return (float)entity.r;
               case REVENGE_TIME:
                  return (float)entity.bT();
               case SWING_PROGRESS:
                  return entity.l(rlb.renderPartialTicks);
               }
            }
         }

         return 0.0F;
      }
   }

   public static RenderEntityParameterFloat parse(String str) {
      if (str == null) {
         return null;
      } else {
         for(int i = 0; i < VALUES.length; ++i) {
            RenderEntityParameterFloat type = VALUES[i];
            if (type.getName().equals(str)) {
               return type;
            }
         }

         return null;
      }
   }
}
