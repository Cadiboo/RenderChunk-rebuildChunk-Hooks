package net.optifine;

import asr.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.optifine.model.ModelUtils;

public class SmartLeaves {
   private static cfy modelLeavesCullAcacia = null;
   private static cfy modelLeavesCullBirch = null;
   private static cfy modelLeavesCullDarkOak = null;
   private static cfy modelLeavesCullJungle = null;
   private static cfy modelLeavesCullOak = null;
   private static cfy modelLeavesCullSpruce = null;
   private static List generalQuadsCullAcacia = null;
   private static List generalQuadsCullBirch = null;
   private static List generalQuadsCullDarkOak = null;
   private static List generalQuadsCullJungle = null;
   private static List generalQuadsCullOak = null;
   private static List generalQuadsCullSpruce = null;
   private static cfy modelLeavesDoubleAcacia = null;
   private static cfy modelLeavesDoubleBirch = null;
   private static cfy modelLeavesDoubleDarkOak = null;
   private static cfy modelLeavesDoubleJungle = null;
   private static cfy modelLeavesDoubleOak = null;
   private static cfy modelLeavesDoubleSpruce = null;

   public static cfy getLeavesModel(cfy model, awt stateIn) {
      if (!.Config.isTreesSmart()) {
         return model;
      } else {
         List generalQuads = model.a(stateIn, (fa)null, 0L);
         if (generalQuads == generalQuadsCullAcacia) {
            return modelLeavesDoubleAcacia;
         } else if (generalQuads == generalQuadsCullBirch) {
            return modelLeavesDoubleBirch;
         } else if (generalQuads == generalQuadsCullDarkOak) {
            return modelLeavesDoubleDarkOak;
         } else if (generalQuads == generalQuadsCullJungle) {
            return modelLeavesDoubleJungle;
         } else if (generalQuads == generalQuadsCullOak) {
            return modelLeavesDoubleOak;
         } else {
            return generalQuads == generalQuadsCullSpruce ? modelLeavesDoubleSpruce : model;
         }
      }
   }

   public static boolean isSameLeaves(awt state1, awt state2) {
      if (state1 == state2) {
         return true;
      } else {
         aow block1 = state1.u();
         aow block2 = state2.u();
         if (block1 != block2) {
            return false;
         } else if (block1 instanceof asn) {
            return ((a)state1.c(asn.e)).equals(state2.c(asn.e));
         } else {
            return block1 instanceof asg ? ((a)state1.c(asg.e)).equals(state2.c(asg.e)) : false;
         }
      }
   }

   public static void updateLeavesModels() {
      List updatedTypes = new ArrayList();
      modelLeavesCullAcacia = getModelCull("acacia", updatedTypes);
      modelLeavesCullBirch = getModelCull("birch", updatedTypes);
      modelLeavesCullDarkOak = getModelCull("dark_oak", updatedTypes);
      modelLeavesCullJungle = getModelCull("jungle", updatedTypes);
      modelLeavesCullOak = getModelCull("oak", updatedTypes);
      modelLeavesCullSpruce = getModelCull("spruce", updatedTypes);
      generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
      generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
      generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
      generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
      generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
      generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
      modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
      modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
      modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
      modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
      modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
      modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);
      if (updatedTypes.size() > 0) {
         .Config.dbg("Enable face culling: " + .Config.arrayToString(updatedTypes.toArray()));
      }

   }

   private static List getGeneralQuadsSafe(cfy model) {
      return model == null ? null : model.a((awt)null, (fa)null, 0L);
   }

   static cfy getModelCull(String type, List updatedTypes) {
      cgc modelManager = .Config.getModelManager();
      if (modelManager == null) {
         return null;
      } else {
         nf locState = new nf("blockstates/" + type + "_leaves.json");
         if (.Config.getDefiningResourcePack(locState) != .Config.getDefaultResourcePack()) {
            return null;
         } else {
            nf locModel = new nf("models/block/" + type + "_leaves.json");
            if (.Config.getDefiningResourcePack(locModel) != .Config.getDefaultResourcePack()) {
               return null;
            } else {
               cgd mrl = new cgd(type + "_leaves", "normal");
               cfy model = modelManager.a(mrl);
               if (model != null && model != modelManager.a()) {
                  List listGeneral = model.a((awt)null, (fa)null, 0L);
                  if (listGeneral.size() == 0) {
                     return model;
                  } else if (listGeneral.size() != 6) {
                     return null;
                  } else {
                     Iterator it = listGeneral.iterator();

                     while(it.hasNext()) {
                        bvp quad = (bvp)it.next();
                        List listFace = model.a((awt)null, quad.e(), 0L);
                        if (listFace.size() > 0) {
                           return null;
                        }

                        listFace.add(quad);
                     }

                     listGeneral.clear();
                     updatedTypes.add(type + "_leaves");
                     return model;
                  }
               } else {
                  return null;
               }
            }
         }
      }
   }

   private static cfy getModelDoubleFace(cfy model) {
      if (model == null) {
         return null;
      } else if (model.a((awt)null, (fa)null, 0L).size() > 0) {
         .Config.warn("SmartLeaves: Model is not cube, general quads: " + model.a((awt)null, (fa)null, 0L).size() + ", model: " + model);
         return model;
      } else {
         fa[] faces = fa.n;

         for(int i = 0; i < faces.length; ++i) {
            fa face = faces[i];
            List quads = model.a((awt)null, face, 0L);
            if (quads.size() != 1) {
               .Config.warn("SmartLeaves: Model is not cube, side: " + face + ", quads: " + quads.size() + ", model: " + model);
               return model;
            }
         }

         cfy model2 = ModelUtils.duplicateModel(model);
         List[] faceQuads = new List[faces.length];

         for(int i = 0; i < faces.length; ++i) {
            fa face = faces[i];
            List quads = model2.a((awt)null, face, 0L);
            bvp quad = (bvp)quads.get(0);
            bvp quad2 = new bvp((int[])quad.b().clone(), quad.d(), quad.e(), quad.a());
            int[] vd = quad2.b();
            int[] vd2 = (int[])vd.clone();
            int step = vd.length / 4;
            System.arraycopy(vd, 0 * step, vd2, 3 * step, step);
            System.arraycopy(vd, 1 * step, vd2, 2 * step, step);
            System.arraycopy(vd, 2 * step, vd2, 1 * step, step);
            System.arraycopy(vd, 3 * step, vd2, 0 * step, step);
            System.arraycopy(vd2, 0, vd, 0, vd2.length);
            quads.add(quad2);
         }

         return model2;
      }
   }
}
