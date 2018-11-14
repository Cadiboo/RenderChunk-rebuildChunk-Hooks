package net.optifine.model;

import aow.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.lwjgl.util.vector.Vector3f;

public class BlockModelUtils {
   private static final float VERTEX_COORD_ACCURACY = 1.0E-6F;

   public static cfy makeModelCube(String spriteName, int tintIndex) {
      cdq sprite = .Config.getMinecraft().R().a(spriteName);
      return makeModelCube(sprite, tintIndex);
   }

   public static cfy makeModelCube(cdq sprite, int tintIndex) {
      List generalQuads = new ArrayList();
      fa[] facings = fa.n;
      Map faceQuads = new HashMap();

      for(int i = 0; i < facings.length; ++i) {
         fa facing = facings[i];
         List quads = new ArrayList();
         quads.add(makeBakedQuad(facing, sprite, tintIndex));
         faceQuads.put(facing, quads);
      }

      bwa itemOverrideList = new bwa(new ArrayList());
      cfy bakedModel = new cgf(generalQuads, faceQuads, true, true, sprite, bwc.a, itemOverrideList);
      return bakedModel;
   }

   public static cfy joinModelsCube(cfy modelBase, cfy modelAdd) {
      List generalQuads = new ArrayList();
      generalQuads.addAll(modelBase.a((awt)null, (fa)null, 0L));
      generalQuads.addAll(modelAdd.a((awt)null, (fa)null, 0L));
      fa[] facings = fa.n;
      Map faceQuads = new HashMap();

      for(int i = 0; i < facings.length; ++i) {
         fa facing = facings[i];
         List quads = new ArrayList();
         quads.addAll(modelBase.a((awt)null, facing, 0L));
         quads.addAll(modelAdd.a((awt)null, facing, 0L));
         faceQuads.put(facing, quads);
      }

      boolean ao = modelBase.a();
      boolean builtIn = modelBase.c();
      cdq sprite = modelBase.d();
      bwc transforms = modelBase.e();
      bwa itemOverrideList = modelBase.f();
      cfy bakedModel = new cgf(generalQuads, faceQuads, ao, builtIn, sprite, transforms, itemOverrideList);
      return bakedModel;
   }

   public static bvp makeBakedQuad(fa facing, cdq sprite, int tintIndex) {
      Vector3f posFrom = new Vector3f(0.0F, 0.0F, 0.0F);
      Vector3f posTo = new Vector3f(16.0F, 16.0F, 16.0F);
      bvt uv = new bvt(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0);
      bvr face = new bvr(facing, tintIndex, "#" + facing.m(), uv);
      cfz modelRotation = cfz.a;
      bvs partRotation = null;
      boolean uvLocked = false;
      boolean shade = true;
      bvx faceBakery = new bvx();
      bvp quad = faceBakery.a(posFrom, posTo, face, sprite, facing, modelRotation, (bvs)partRotation, uvLocked, shade);
      return quad;
   }

   public static cfy makeModel(String modelName, String spriteOldName, String spriteNewName) {
      cdp textureMap = .Config.getMinecraft().R();
      cdq spriteOld = textureMap.getSpriteSafe(spriteOldName);
      cdq spriteNew = textureMap.getSpriteSafe(spriteNewName);
      return makeModel(modelName, spriteOld, spriteNew);
   }

   public static cfy makeModel(String modelName, cdq spriteOld, cdq spriteNew) {
      if (spriteOld != null && spriteNew != null) {
         cgc modelManager = .Config.getModelManager();
         if (modelManager == null) {
            return null;
         } else {
            cgd mrl = new cgd(modelName, "normal");
            cfy model = modelManager.a(mrl);
            if (model != null && model != modelManager.a()) {
               cfy modelNew = ModelUtils.duplicateModel(model);
               fa[] faces = fa.n;

               for(int i = 0; i < faces.length; ++i) {
                  fa face = faces[i];
                  List quads = modelNew.a((awt)null, face, 0L);
                  replaceTexture(quads, spriteOld, spriteNew);
               }

               List quadsGeneral = modelNew.a((awt)null, (fa)null, 0L);
               replaceTexture(quadsGeneral, spriteOld, spriteNew);
               return modelNew;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private static void replaceTexture(List quads, cdq spriteOld, cdq spriteNew) {
      List quadsNew = new ArrayList();
      Iterator it = quads.iterator();

      while(it.hasNext()) {
         bvp quad = (bvp)it.next();
         if (quad.a() != spriteOld) {
            quadsNew.add(quad);
            break;
         }

         bvp quadNew = new bvw(quad, spriteNew);
         quadsNew.add(quadNew);
      }

      quads.clear();
      quads.addAll(quadsNew);
   }

   public static void snapVertexPosition(Vector3f pos) {
      pos.setX(snapVertexCoord(pos.getX()));
      pos.setY(snapVertexCoord(pos.getY()));
      pos.setZ(snapVertexCoord(pos.getZ()));
   }

   private static float snapVertexCoord(float x) {
      if (x > -1.0E-6F && x < 1.0E-6F) {
         return 0.0F;
      } else {
         return x > 0.999999F && x < 1.000001F ? 1.0F : x;
      }
   }

   public static bhb getOffsetBoundingBox(bhb aabb, a offsetType, et pos) {
      int x = pos.p();
      int z = pos.r();
      long k = (long)(x * 3129871) ^ (long)z * 116129781L;
      k = k * k * 42317861L + k * 11L;
      double dx = ((double)((float)(k >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
      double dz = ((double)((float)(k >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
      double dy = 0.0D;
      if (offsetType == a.c) {
         dy = ((double)((float)(k >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
      }

      return aabb.d(dx, dy, dz);
   }
}
