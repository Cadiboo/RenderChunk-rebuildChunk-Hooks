package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.optifine.config.Matches;
import net.optifine.model.BlockModelUtils;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.util.ResUtils;
import net.optifine.util.TileEntityUtils;

public class ConnectedTextures {
   private static Map[] spriteQuadMaps = null;
   private static Map[] spriteQuadFullMaps = null;
   private static Map[][] spriteQuadCompactMaps = (Map[][])null;
   private static ConnectedProperties[][] blockProperties = (ConnectedProperties[][])null;
   private static ConnectedProperties[][] tileProperties = (ConnectedProperties[][])null;
   private static boolean multipass = false;
   protected static final int UNKNOWN = -1;
   protected static final int Y_NEG_DOWN = 0;
   protected static final int Y_POS_UP = 1;
   protected static final int Z_NEG_NORTH = 2;
   protected static final int Z_POS_SOUTH = 3;
   protected static final int X_NEG_WEST = 4;
   protected static final int X_POS_EAST = 5;
   private static final int Y_AXIS = 0;
   private static final int Z_AXIS = 1;
   private static final int X_AXIS = 2;
   public static final awt AIR_DEFAULT_STATE;
   private static cdq emptySprite;
   private static final BlockDir[] SIDES_Y_NEG_DOWN;
   private static final BlockDir[] SIDES_Y_POS_UP;
   private static final BlockDir[] SIDES_Z_NEG_NORTH;
   private static final BlockDir[] SIDES_Z_POS_SOUTH;
   private static final BlockDir[] SIDES_X_NEG_WEST;
   private static final BlockDir[] SIDES_X_POS_EAST;
   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS;
   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS;
   private static final BlockDir[] EDGES_Y_NEG_DOWN;
   private static final BlockDir[] EDGES_Y_POS_UP;
   private static final BlockDir[] EDGES_Z_NEG_NORTH;
   private static final BlockDir[] EDGES_Z_POS_SOUTH;
   private static final BlockDir[] EDGES_X_NEG_WEST;
   private static final BlockDir[] EDGES_X_POS_EAST;
   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS;
   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS;
   public static final cdq SPRITE_DEFAULT;

   public static synchronized bvp[] getConnectedTexture(amy blockAccess, awt blockState, et blockPos, bvp quad, RenderEnv renderEnv) {
      cdq spriteIn = quad.a();
      if (spriteIn == null) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         aow block = blockState.u();
         if (skipConnectedTexture(blockAccess, blockState, blockPos, quad, renderEnv)) {
            quad = getQuad(emptySprite, quad);
            return renderEnv.getArrayQuadsCtm(quad);
         } else {
            fa side = quad.e();
            bvp[] quads = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, quad, renderEnv);
            return quads;
         }
      }
   }

   private static boolean skipConnectedTexture(amy blockAccess, awt blockState, et blockPos, bvp quad, RenderEnv renderEnv) {
      aow block = blockState.u();
      if (block instanceof auo) {
         fa face = quad.e();
         if (face != fa.b && face != fa.a) {
            return false;
         }

         if (!quad.isFaceQuad()) {
            return false;
         }

         et posNeighbour = blockPos.a(quad.e());
         awt stateNeighbour = blockAccess.o(posNeighbour);
         if (stateNeighbour.u() != block) {
            return false;
         }

         if (block == aox.cH && stateNeighbour.c(aub.a) != blockState.c(aub.a)) {
            return false;
         }

         stateNeighbour = stateNeighbour.c(blockAccess, posNeighbour);
         double midX = (double)quad.getMidX();
         if (midX < 0.4D) {
            if (((Boolean)stateNeighbour.c(auo.e)).booleanValue()) {
               return true;
            }
         } else if (midX > 0.6D) {
            if (((Boolean)stateNeighbour.c(auo.c)).booleanValue()) {
               return true;
            }
         } else {
            double midZ = quad.getMidZ();
            if (midZ < 0.4D) {
               if (((Boolean)stateNeighbour.c(auo.b)).booleanValue()) {
                  return true;
               }
            } else {
               if (midZ <= 0.6D) {
                  return true;
               }

               if (((Boolean)stateNeighbour.c(auo.d)).booleanValue()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   protected static bvp[] getQuads(cdq sprite, bvp quadIn, RenderEnv renderEnv) {
      if (sprite == null) {
         return null;
      } else if (sprite == SPRITE_DEFAULT) {
         return renderEnv.getArrayQuadsCtm(quadIn);
      } else {
         bvp quad = getQuad(sprite, quadIn);
         bvp[] quads = renderEnv.getArrayQuadsCtm(quad);
         return quads;
      }
   }

   private static bvp getQuad(cdq sprite, bvp quadIn) {
      if (spriteQuadMaps == null) {
         return quadIn;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadMaps.length) {
            Map quadMap = spriteQuadMaps[spriteIndex];
            if (quadMap == null) {
               quadMap = new IdentityHashMap(1);
               spriteQuadMaps[spriteIndex] = (Map)quadMap;
            }

            bvp quad = (bvp)((Map)quadMap).get(quadIn);
            if (quad == null) {
               quad = makeSpriteQuad(quadIn, sprite);
               ((Map)quadMap).put(quadIn, quad);
            }

            return quad;
         } else {
            return quadIn;
         }
      }
   }

   private static bvp getQuadFull(cdq sprite, bvp quadIn, int tintIndex) {
      if (spriteQuadFullMaps == null) {
         return quadIn;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadFullMaps.length) {
            Map quadMap = spriteQuadFullMaps[spriteIndex];
            if (quadMap == null) {
               quadMap = new EnumMap(fa.class);
               spriteQuadFullMaps[spriteIndex] = (Map)quadMap;
            }

            fa face = quadIn.e();
            bvp quad = (bvp)((Map)quadMap).get(face);
            if (quad == null) {
               quad = BlockModelUtils.makeBakedQuad(face, sprite, tintIndex);
               ((Map)quadMap).put(face, quad);
            }

            return quad;
         } else {
            return quadIn;
         }
      }
   }

   private static bvp makeSpriteQuad(bvp quad, cdq sprite) {
      int[] data = (int[])quad.b().clone();
      cdq spriteFrom = quad.a();

      for(int i = 0; i < 4; ++i) {
         fixVertex(data, i, spriteFrom, sprite);
      }

      bvp bq = new bvp(data, quad.d(), quad.e(), sprite);
      return bq;
   }

   private static void fixVertex(int[] data, int vertex, cdq spriteFrom, cdq spriteTo) {
      int mul = data.length / 4;
      int pos = mul * vertex;
      float u = Float.intBitsToFloat(data[pos + 4]);
      float v = Float.intBitsToFloat(data[pos + 4 + 1]);
      double su16 = spriteFrom.getSpriteU16(u);
      double sv16 = spriteFrom.getSpriteV16(v);
      data[pos + 4] = Float.floatToRawIntBits(spriteTo.a(su16));
      data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.b(sv16));
   }

   private static bvp[] getConnectedTextureMultiPass(amy blockAccess, awt blockState, et blockPos, fa side, bvp quad, RenderEnv renderEnv) {
      bvp[] quads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, quad, true, 0, renderEnv);
      if (!multipass) {
         return quads;
      } else if (quads.length == 1 && quads[0] == quad) {
         return quads;
      } else {
         List listQuads = renderEnv.getListQuadsCtmMultipass(quads);

         int q;
         for(q = 0; q < listQuads.size(); ++q) {
            bvp newQuad = (bvp)listQuads.get(q);
            bvp mpQuad = newQuad;

            for(int i = 0; i < 3; ++i) {
               bvp[] newMpQuads = getConnectedTextureSingle(blockAccess, blockState, blockPos, side, mpQuad, false, i + 1, renderEnv);
               if (newMpQuads.length != 1 || newMpQuads[0] == mpQuad) {
                  break;
               }

               mpQuad = newMpQuads[0];
            }

            listQuads.set(q, mpQuad);
         }

         for(q = 0; q < quads.length; ++q) {
            quads[q] = (bvp)listQuads.get(q);
         }

         return quads;
      }
   }

   public static bvp[] getConnectedTextureSingle(amy blockAccess, awt blockState, et blockPos, fa facing, bvp quad, boolean checkBlocks, int pass, RenderEnv renderEnv) {
      aow block = blockState.u();
      if (!(blockState instanceof awp)) {
         return renderEnv.getArrayQuadsCtm(quad);
      } else {
         awp blockStateBase = (awp)blockState;
         cdq icon = quad.a();
         int blockId;
         ConnectedProperties[] cps;
         int side;
         int i;
         ConnectedProperties cp;
         bvp[] newQuads;
         if (tileProperties != null) {
            blockId = icon.getIndexInMap();
            if (blockId >= 0 && blockId < tileProperties.length) {
               cps = tileProperties[blockId];
               if (cps != null) {
                  side = getSide(facing);

                  for(i = 0; i < cps.length; ++i) {
                     cp = cps[i];
                     if (cp != null && cp.matchesBlockId(blockStateBase.getBlockId())) {
                        newQuads = getConnectedTexture(cp, blockAccess, blockStateBase, blockPos, side, quad, pass, renderEnv);
                        if (newQuads != null) {
                           return newQuads;
                        }
                     }
                  }
               }
            }
         }

         if (blockProperties != null && checkBlocks) {
            blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < blockProperties.length) {
               cps = blockProperties[blockId];
               if (cps != null) {
                  side = getSide(facing);

                  for(i = 0; i < cps.length; ++i) {
                     cp = cps[i];
                     if (cp != null && cp.matchesIcon(icon)) {
                        newQuads = getConnectedTexture(cp, blockAccess, blockStateBase, blockPos, side, quad, pass, renderEnv);
                        if (newQuads != null) {
                           return newQuads;
                        }
                     }
                  }
               }
            }
         }

         return renderEnv.getArrayQuadsCtm(quad);
      }
   }

   public static int getSide(fa facing) {
      if (facing == null) {
         return -1;
      } else {
         switch(facing) {
         case a:
            return 0;
         case b:
            return 1;
         case f:
            return 5;
         case e:
            return 4;
         case c:
            return 2;
         case d:
            return 3;
         default:
            return -1;
         }
      }
   }

   private static fa getFacing(int side) {
      switch(side) {
      case 0:
         return fa.a;
      case 1:
         return fa.b;
      case 2:
         return fa.c;
      case 3:
         return fa.d;
      case 4:
         return fa.e;
      case 5:
         return fa.f;
      default:
         return fa.b;
      }
   }

   private static bvp[] getConnectedTexture(ConnectedProperties cp, amy blockAccess, awp blockState, et blockPos, int side, bvp quad, int pass, RenderEnv renderEnv) {
      int vertAxis = 0;
      int metadata = blockState.getMetadata();
      int metadataCheck = metadata;
      aow block = blockState.u();
      if (block instanceof atl) {
         vertAxis = getWoodAxis(side, metadata);
         if (cp.getMetadataMax() <= 3) {
            metadataCheck = metadata & 3;
         }
      }

      if (block instanceof ata) {
         vertAxis = getQuartzAxis(side, metadata);
         if (cp.getMetadataMax() <= 2 && metadataCheck > 2) {
            metadataCheck = 2;
         }
      }

      if (!cp.matchesBlock(blockState.getBlockId(), metadataCheck)) {
         return null;
      } else {
         int sideCheck;
         if (side >= 0 && cp.faces != 63) {
            sideCheck = side;
            if (vertAxis != 0) {
               sideCheck = fixSideByAxis(side, vertAxis);
            }

            if ((1 << sideCheck & cp.faces) == 0) {
               return null;
            }
         }

         sideCheck = blockPos.q();
         if (cp.heights != null && !cp.heights.isInRange(sideCheck)) {
            return null;
         } else {
            if (cp.biomes != null) {
               anh blockBiome = blockAccess.b(blockPos);
               if (!cp.matchesBiome(blockBiome)) {
                  return null;
               }
            }

            if (cp.nbtName != null) {
               String name = TileEntityUtils.getTileEntityName(blockAccess, blockPos);
               if (!cp.nbtName.matchesValue(name)) {
                  return null;
               }
            }

            cdq icon = quad.a();
            switch(cp.method) {
            case 1:
               return getQuads(getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv), quad, renderEnv);
            case 2:
               return getQuads(getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            case 3:
               return getQuads(getConnectedTextureTop(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            case 4:
               return getQuads(getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side), quad, renderEnv);
            case 5:
               return getQuads(getConnectedTextureRepeat(cp, blockPos, side), quad, renderEnv);
            case 6:
               return getQuads(getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            case 7:
               return getQuads(getConnectedTextureFixed(cp), quad, renderEnv);
            case 8:
               return getQuads(getConnectedTextureHorizontalVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            case 9:
               return getQuads(getConnectedTextureVerticalHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata), quad, renderEnv);
            case 10:
               if (pass == 0) {
                  return getConnectedTextureCtmCompact(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
               }
            default:
               return null;
            case 11:
               return getConnectedTextureOverlay(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            case 12:
               return getConnectedTextureOverlayFixed(cp, quad, renderEnv);
            case 13:
               return getConnectedTextureOverlayRandom(cp, blockAccess, blockState, blockPos, side, quad, renderEnv);
            case 14:
               return getConnectedTextureOverlayRepeat(cp, blockPos, side, quad, renderEnv);
            case 15:
               return getConnectedTextureOverlayCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad, metadata, renderEnv);
            }
         }
      }
   }

   private static int fixSideByAxis(int side, int vertAxis) {
      switch(vertAxis) {
      case 0:
         return side;
      case 1:
         switch(side) {
         case 0:
            return 2;
         case 1:
            return 3;
         case 2:
            return 1;
         case 3:
            return 0;
         default:
            return side;
         }
      case 2:
         switch(side) {
         case 0:
            return 4;
         case 1:
            return 5;
         case 2:
         case 3:
         default:
            return side;
         case 4:
            return 1;
         case 5:
            return 0;
         }
      default:
         return side;
      }
   }

   private static int getWoodAxis(int side, int metadata) {
      int orient = (metadata & 12) >> 2;
      switch(orient) {
      case 1:
         return 2;
      case 2:
         return 1;
      default:
         return 0;
      }
   }

   private static int getQuartzAxis(int side, int metadata) {
      switch(metadata) {
      case 3:
         return 2;
      case 4:
         return 1;
      default:
         return 0;
      }
   }

   private static cdq getConnectedTextureRandom(ConnectedProperties cp, amy blockAccess, awp blockState, et blockPos, int side) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int face = side / cp.symmetry * cp.symmetry;
         if (cp.linked) {
            et posDown = blockPos.b();

            for(awt bsDown = blockAccess.o(posDown); bsDown.u() == blockState.u(); bsDown = blockAccess.o(posDown)) {
               blockPos = posDown;
               posDown = posDown.b();
               if (posDown.q() < 0) {
                  break;
               }
            }
         }

         int rand = .Config.getRandom(blockPos, face) & Integer.MAX_VALUE;

         int index;
         for(index = 0; index < cp.randomLoops; ++index) {
            rand = .Config.intHash(rand);
         }

         index = 0;
         if (cp.weights == null) {
            index = rand % cp.tileIcons.length;
         } else {
            int randWeight = rand % cp.sumAllWeights;
            int[] sumWeights = cp.sumWeights;

            for(int i = 0; i < sumWeights.length; ++i) {
               if (randWeight < sumWeights[i]) {
                  index = i;
                  break;
               }
            }
         }

         return cp.tileIcons[index];
      }
   }

   private static cdq getConnectedTextureFixed(ConnectedProperties cp) {
      return cp.tileIcons[0];
   }

   private static cdq getConnectedTextureRepeat(ConnectedProperties cp, et blockPos, int side) {
      if (cp.tileIcons.length == 1) {
         return cp.tileIcons[0];
      } else {
         int x = blockPos.p();
         int y = blockPos.q();
         int z = blockPos.r();
         int nx = 0;
         int ny = 0;
         switch(side) {
         case 0:
            nx = x;
            ny = z;
            break;
         case 1:
            nx = x;
            ny = z;
            break;
         case 2:
            nx = -x - 1;
            ny = -y;
            break;
         case 3:
            nx = x;
            ny = -y;
            break;
         case 4:
            nx = z;
            ny = -y;
            break;
         case 5:
            nx = -z - 1;
            ny = -y;
         }

         nx %= cp.width;
         ny %= cp.height;
         if (nx < 0) {
            nx += cp.width;
         }

         if (ny < 0) {
            ny += cp.height;
         }

         int index = ny * cp.width + nx;
         return cp.tileIcons[index];
      }
   }

   private static cdq getConnectedTextureCtm(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata, RenderEnv renderEnv) {
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return cp.tileIcons[index];
   }

   private static bvp[] getConnectedTextureCtmCompact(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, bvp quad, int metadata, RenderEnv renderEnv) {
      cdq icon = quad.a();
      int index = getConnectedTextureCtmIndex(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
      return ConnectedTexturesCompact.getConnectedTextureCtmCompact(index, cp, side, quad, renderEnv);
   }

   private static bvp[] getConnectedTextureOverlay(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, bvp quad, int metadata, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         cdq icon = quad.a();
         BlockDir[] dirSides = getSideDirections(side, vertAxis);
         boolean[] sides = renderEnv.getBorderFlags();

         for(int i = 0; i < 4; ++i) {
            sides[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata);
         }

         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         BlockDir[] dirEdges;
         try {
            if (sides[0] && sides[1] && sides[2] && sides[3]) {
               listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[8], quad, cp.tintIndex), cp.tintBlockState);
               dirEdges = null;
               return dirEdges;
            }

            if (sides[0] && sides[1] && sides[2]) {
               listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[5], quad, cp.tintIndex), cp.tintBlockState);
               dirEdges = null;
               return dirEdges;
            }

            if (sides[0] && sides[2] && sides[3]) {
               listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[6], quad, cp.tintIndex), cp.tintBlockState);
               dirEdges = null;
               return dirEdges;
            }

            if (!sides[1] || !sides[2] || !sides[3]) {
               if (sides[0] && sides[1] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[13], quad, cp.tintIndex), cp.tintBlockState);
                  dirEdges = null;
                  return dirEdges;
               }

               dirEdges = getEdgeDirections(side, vertAxis);
               boolean[] edges = renderEnv.getBorderFlags2();

               for(int i = 0; i < 4; ++i) {
                  edges[i] = isNeighbourOverlay(cp, blockAccess, blockState, dirEdges[i].offset(blockPos), side, icon, metadata);
               }

               Object var22;
               if (sides[1] && sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[3], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[3]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  var22 = null;
                  return (bvp[])var22;
               }

               if (sides[0] && sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[4], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[2]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  var22 = null;
                  return (bvp[])var22;
               }

               if (sides[1] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[10], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[1]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  var22 = null;
                  return (bvp[])var22;
               }

               if (sides[0] && sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[11], quad, cp.tintIndex), cp.tintBlockState);
                  if (edges[0]) {
                     listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
                  }

                  var22 = null;
                  return (bvp[])var22;
               }

               boolean[] sidesMatch = renderEnv.getBorderFlags3();

               for(int i = 0; i < 4; ++i) {
                  sidesMatch[i] = isNeighbourMatching(cp, blockAccess, blockState, dirSides[i].offset(blockPos), side, icon, metadata);
               }

               if (sides[0]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[9], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[1]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[7], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[1], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[15], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[0] && (sidesMatch[1] || sidesMatch[2]) && !sides[1] && !sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[0], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[1] && (sidesMatch[0] || sidesMatch[2]) && !sides[0] && !sides[2]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[2], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[2] && (sidesMatch[1] || sidesMatch[3]) && !sides[1] && !sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[14], quad, cp.tintIndex), cp.tintBlockState);
               }

               if (edges[3] && (sidesMatch[0] || sidesMatch[3]) && !sides[0] && !sides[3]) {
                  listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[16], quad, cp.tintIndex), cp.tintBlockState);
               }

               Object var23 = null;
               return (bvp[])var23;
            }

            listQuadsOverlay.addQuad(getQuadFull(cp.tileIcons[12], quad, cp.tintIndex), cp.tintBlockState);
            dirEdges = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }

         }

         return dirEdges;
      }
   }

   private static bvp[] getConnectedTextureOverlayFixed(ConnectedProperties cp, bvp quad, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var5;
         try {
            cdq sprite = getConnectedTextureFixed(cp);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var5 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }

         }

         return (bvp[])var5;
      }
   }

   private static bvp[] getConnectedTextureOverlayRandom(ConnectedProperties cp, amy blockAccess, awp blockState, et blockPos, int side, bvp quad, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var9;
         try {
            cdq sprite = getConnectedTextureRandom(cp, blockAccess, blockState, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var9 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }

         }

         return (bvp[])var9;
      }
   }

   private static bvp[] getConnectedTextureOverlayRepeat(ConnectedProperties cp, et blockPos, int side, bvp quad, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var7;
         try {
            cdq sprite = getConnectedTextureRepeat(cp, blockPos, side);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var7 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }

         }

         return (bvp[])var7;
      }
   }

   private static bvp[] getConnectedTextureOverlayCtm(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, bvp quad, int metadata, RenderEnv renderEnv) {
      if (!quad.isFullQuad()) {
         return null;
      } else {
         ListQuadsOverlay listQuadsOverlay = renderEnv.getListQuadsOverlay(cp.layer);

         Object var11;
         try {
            cdq sprite = getConnectedTextureCtm(cp, blockAccess, blockState, blockPos, vertAxis, side, quad.a(), metadata, renderEnv);
            if (sprite != null) {
               listQuadsOverlay.addQuad(getQuadFull(sprite, quad, cp.tintIndex), cp.tintBlockState);
            }

            var11 = null;
         } finally {
            if (listQuadsOverlay.size() > 0) {
               renderEnv.setOverlaysRendered(true);
            }

         }

         return (bvp[])var11;
      }
   }

   private static BlockDir[] getSideDirections(int side, int vertAxis) {
      switch(side) {
      case 0:
         return SIDES_Y_NEG_DOWN;
      case 1:
         return SIDES_Y_POS_UP;
      case 2:
         if (vertAxis == 1) {
            return SIDES_Z_NEG_NORTH_Z_AXIS;
         }

         return SIDES_Z_NEG_NORTH;
      case 3:
         return SIDES_Z_POS_SOUTH;
      case 4:
         return SIDES_X_NEG_WEST;
      case 5:
         if (vertAxis == 2) {
            return SIDES_X_POS_EAST_X_AXIS;
         }

         return SIDES_X_POS_EAST;
      default:
         throw new IllegalArgumentException("Unknown side: " + side);
      }
   }

   private static BlockDir[] getEdgeDirections(int side, int vertAxis) {
      switch(side) {
      case 0:
         return EDGES_Y_NEG_DOWN;
      case 1:
         return EDGES_Y_POS_UP;
      case 2:
         if (vertAxis == 1) {
            return EDGES_Z_NEG_NORTH_Z_AXIS;
         }

         return EDGES_Z_NEG_NORTH;
      case 3:
         return EDGES_Z_POS_SOUTH;
      case 4:
         return EDGES_X_NEG_WEST;
      case 5:
         if (vertAxis == 2) {
            return EDGES_X_POS_EAST_X_AXIS;
         }

         return EDGES_X_POS_EAST;
      default:
         throw new IllegalArgumentException("Unknown side: " + side);
      }
   }

   protected static Map[][] getSpriteQuadCompactMaps() {
      return spriteQuadCompactMaps;
   }

   private static int getConnectedTextureCtmIndex(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata, RenderEnv renderEnv) {
      boolean[] borders = renderEnv.getBorderFlags();
      et posFront;
      switch(side) {
      case 0:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.b();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata);
         }
         break;
      case 1:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.a();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata);
         }
         break;
      case 2:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.c();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.b(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.a(), side, icon, metadata);
         }

         if (vertAxis == 1) {
            switchValues(0, 1, borders);
            switchValues(2, 3, borders);
         }
         break;
      case 3:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.d();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.e(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.f(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.b(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.a(), side, icon, metadata);
         }
         break;
      case 4:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.e();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.b(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.a(), side, icon, metadata);
         }
         break;
      case 5:
         borders[0] = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         borders[1] = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         borders[2] = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         borders[3] = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         if (cp.innerSeams) {
            posFront = blockPos.f();
            borders[0] = borders[0] && !isNeighbour(cp, blockAccess, blockState, posFront.d(), side, icon, metadata);
            borders[1] = borders[1] && !isNeighbour(cp, blockAccess, blockState, posFront.c(), side, icon, metadata);
            borders[2] = borders[2] && !isNeighbour(cp, blockAccess, blockState, posFront.b(), side, icon, metadata);
            borders[3] = borders[3] && !isNeighbour(cp, blockAccess, blockState, posFront.a(), side, icon, metadata);
         }

         if (vertAxis == 2) {
            switchValues(0, 1, borders);
            switchValues(2, 3, borders);
         }
      }

      int index = 0;
      if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
         index = 3;
      } else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
         index = 1;
      } else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
         index = 12;
      } else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
         index = 36;
      } else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
         index = 2;
      } else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
         index = 24;
      } else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
         index = 15;
      } else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
         index = 39;
      } else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
         index = 13;
      } else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
         index = 37;
      } else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
         index = 25;
      } else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
         index = 27;
      } else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
         index = 38;
      } else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
         index = 14;
      } else if (borders[0] & borders[1] & borders[2] & borders[3]) {
         index = 26;
      }

      if (index == 0) {
         return index;
      } else if (!.Config.isConnectedTexturesFancy()) {
         return index;
      } else {
         switch(side) {
         case 0:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().c(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().c(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().d(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().d(), side, icon, metadata);
            break;
         case 1:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().d(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().d(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().c(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().c(), side, icon, metadata);
            break;
         case 2:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().b(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().b(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().a(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().a(), side, icon, metadata);
            if (vertAxis == 1) {
               switchValues(0, 3, borders);
               switchValues(1, 2, borders);
            }
            break;
         case 3:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().b(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().b(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.f().a(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.e().a(), side, icon, metadata);
            break;
         case 4:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.b().d(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.b().c(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.a().d(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.a().c(), side, icon, metadata);
            break;
         case 5:
            borders[0] = !isNeighbour(cp, blockAccess, blockState, blockPos.b().c(), side, icon, metadata);
            borders[1] = !isNeighbour(cp, blockAccess, blockState, blockPos.b().d(), side, icon, metadata);
            borders[2] = !isNeighbour(cp, blockAccess, blockState, blockPos.a().c(), side, icon, metadata);
            borders[3] = !isNeighbour(cp, blockAccess, blockState, blockPos.a().d(), side, icon, metadata);
            if (vertAxis == 2) {
               switchValues(0, 3, borders);
               switchValues(1, 2, borders);
            }
         }

         if (index == 13 && borders[0]) {
            index = 4;
         } else if (index == 15 && borders[1]) {
            index = 5;
         } else if (index == 37 && borders[2]) {
            index = 16;
         } else if (index == 39 && borders[3]) {
            index = 17;
         } else if (index == 14 && borders[0] && borders[1]) {
            index = 7;
         } else if (index == 25 && borders[0] && borders[2]) {
            index = 6;
         } else if (index == 27 && borders[3] && borders[1]) {
            index = 19;
         } else if (index == 38 && borders[3] && borders[2]) {
            index = 18;
         } else if (index == 14 && !borders[0] && borders[1]) {
            index = 31;
         } else if (index == 25 && borders[0] && !borders[2]) {
            index = 30;
         } else if (index == 27 && !borders[3] && borders[1]) {
            index = 41;
         } else if (index == 38 && borders[3] && !borders[2]) {
            index = 40;
         } else if (index == 14 && borders[0] && !borders[1]) {
            index = 29;
         } else if (index == 25 && !borders[0] && borders[2]) {
            index = 28;
         } else if (index == 27 && borders[3] && !borders[1]) {
            index = 43;
         } else if (index == 38 && !borders[3] && borders[2]) {
            index = 42;
         } else if (index == 26 && borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 46;
         } else if (index == 26 && !borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 9;
         } else if (index == 26 && borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 21;
         } else if (index == 26 && borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 8;
         } else if (index == 26 && borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 20;
         } else if (index == 26 && borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 11;
         } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 22;
         } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 23;
         } else if (index == 26 && borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 10;
         } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 34;
         } else if (index == 26 && !borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 35;
         } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && !borders[3]) {
            index = 32;
         } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 33;
         } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 44;
         } else if (index == 26 && !borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 45;
         }

         return index;
      }
   }

   private static void switchValues(int ix1, int ix2, boolean[] arr) {
      boolean prev1 = arr[ix1];
      arr[ix1] = arr[ix2];
      arr[ix2] = prev1;
   }

   private static boolean isNeighbourOverlay(ConnectedProperties cp, amy iblockaccess, awt blockState, et blockPos, int side, cdq icon, int metadata) {
      awt neighbourState = iblockaccess.o(blockPos);
      if (!isFullCubeModel(neighbourState)) {
         return false;
      } else {
         if (cp.connectBlocks != null) {
            awp neighbourStateBase = (awp)neighbourState;
            if (!Matches.block(neighbourStateBase.getBlockId(), neighbourStateBase.getMetadata(), cp.connectBlocks)) {
               return false;
            }
         }

         if (cp.connectTileIcons != null) {
            cdq neighbourIcon = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
            if (!.Config.isSameOne(neighbourIcon, cp.connectTileIcons)) {
               return false;
            }
         }

         awt neighbourStateAbove = iblockaccess.o(blockPos.a(getFacing(side)));
         if (neighbourStateAbove.p()) {
            return false;
         } else if (side == 1 && neighbourStateAbove.u() == aox.aH) {
            return false;
         } else {
            return !isNeighbour(cp, iblockaccess, blockState, blockPos, neighbourState, side, icon, metadata);
         }
      }
   }

   private static boolean isFullCubeModel(awt state) {
      if (state.g()) {
         return true;
      } else {
         aow block = state.u();
         if (block instanceof aqy) {
            return true;
         } else {
            return block instanceof aua;
         }
      }
   }

   private static boolean isNeighbourMatching(ConnectedProperties cp, amy iblockaccess, awt blockState, et blockPos, int side, cdq icon, int metadata) {
      awt neighbourState = iblockaccess.o(blockPos);
      if (neighbourState == AIR_DEFAULT_STATE) {
         return false;
      } else {
         if (cp.matchBlocks != null && neighbourState instanceof awp) {
            awp neighbourStateBase = (awp)neighbourState;
            if (!cp.matchesBlock(neighbourStateBase.getBlockId(), neighbourStateBase.getMetadata())) {
               return false;
            }
         }

         if (cp.matchTileIcons != null) {
            cdq neighbourIcon = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
            if (neighbourIcon != icon) {
               return false;
            }
         }

         awt neighbourStateAbove = iblockaccess.o(blockPos.a(getFacing(side)));
         if (neighbourStateAbove.p()) {
            return false;
         } else {
            return side != 1 || neighbourStateAbove.u() != aox.aH;
         }
      }
   }

   private static boolean isNeighbour(ConnectedProperties cp, amy iblockaccess, awt blockState, et blockPos, int side, cdq icon, int metadata) {
      awt neighbourState = iblockaccess.o(blockPos);
      return isNeighbour(cp, iblockaccess, blockState, blockPos, neighbourState, side, icon, metadata);
   }

   private static boolean isNeighbour(ConnectedProperties cp, amy iblockaccess, awt blockState, et blockPos, awt neighbourState, int side, cdq icon, int metadata) {
      if (blockState == neighbourState) {
         return true;
      } else if (cp.connect == 2) {
         if (neighbourState == null) {
            return false;
         } else if (neighbourState == AIR_DEFAULT_STATE) {
            return false;
         } else {
            cdq neighbourIcon = getNeighbourIcon(iblockaccess, blockState, blockPos, neighbourState, side);
            return neighbourIcon == icon;
         }
      } else if (cp.connect == 3) {
         if (neighbourState == null) {
            return false;
         } else if (neighbourState == AIR_DEFAULT_STATE) {
            return false;
         } else {
            return neighbourState.a() == blockState.a();
         }
      } else if (!(neighbourState instanceof awp)) {
         return false;
      } else {
         awp neighbourStateBase = (awp)neighbourState;
         aow neighbourBlock = neighbourStateBase.u();
         int neighbourMetadata = neighbourStateBase.getMetadata();
         return neighbourBlock == blockState.u() && neighbourMetadata == metadata;
      }
   }

   private static cdq getNeighbourIcon(amy iblockaccess, awt blockState, et blockPos, awt neighbourState, int side) {
      neighbourState = neighbourState.u().d(neighbourState, iblockaccess, blockPos);
      cfy model = bib.z().ab().a().b(neighbourState);
      if (model == null) {
         return null;
      } else {
         if (Reflector.ForgeBlock_getExtendedState.exists()) {
            neighbourState = (awt)Reflector.call(neighbourState.u(), Reflector.ForgeBlock_getExtendedState, neighbourState, iblockaccess, blockPos);
         }

         fa facing = getFacing(side);
         List quads = model.a(neighbourState, facing, 0L);
         if (quads == null) {
            return null;
         } else {
            if (.Config.isBetterGrass()) {
               quads = BetterGrass.getFaceQuads(iblockaccess, neighbourState, blockPos, facing, quads);
            }

            if (quads.size() > 0) {
               bvp quad = (bvp)quads.get(0);
               return quad.a();
            } else {
               List quadsGeneral = model.a(neighbourState, (fa)null, 0L);
               if (quadsGeneral == null) {
                  return null;
               } else {
                  for(int i = 0; i < quadsGeneral.size(); ++i) {
                     bvp quad = (bvp)quadsGeneral.get(i);
                     if (quad.e() == facing) {
                        return quad.a();
                     }
                  }

                  return null;
               }
            }
         }
      }
   }

   private static cdq getConnectedTextureHorizontal(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata) {
      boolean left;
      boolean right;
      left = false;
      right = false;
      label49:
      switch(vertAxis) {
      case 0:
         switch(side) {
         case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            break label49;
         case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break label49;
         case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         default:
            break label49;
         }
      case 1:
         switch(side) {
         case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            break label49;
         case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
            break label49;
         case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            break label49;
         case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         default:
            break label49;
         }
      case 2:
         switch(side) {
         case 0:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            break;
         case 1:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break;
         case 2:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            break;
         case 3:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            break;
         case 4:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break;
         case 5:
            left = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            right = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         }
      }

      int index = true;
      byte index;
      if (left) {
         if (right) {
            index = 1;
         } else {
            index = 2;
         }
      } else if (right) {
         index = 0;
      } else {
         index = 3;
      }

      return cp.tileIcons[index];
   }

   private static cdq getConnectedTextureVertical(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata) {
      boolean bottom = false;
      boolean top = false;
      switch(vertAxis) {
      case 0:
         if (side == 1) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         } else if (side == 0) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
         } else {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         }
         break;
      case 1:
         if (side == 3) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         } else if (side == 2) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         } else {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.c(), side, icon, metadata);
         }
         break;
      case 2:
         if (side == 5) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
         } else if (side == 4) {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.b(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         } else {
            bottom = isNeighbour(cp, blockAccess, blockState, blockPos.e(), side, icon, metadata);
            top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
         }
      }

      int index = true;
      byte index;
      if (bottom) {
         if (top) {
            index = 1;
         } else {
            index = 2;
         }
      } else if (top) {
         index = 0;
      } else {
         index = 3;
      }

      return cp.tileIcons[index];
   }

   private static cdq getConnectedTextureHorizontalVertical(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata) {
      cdq[] tileIcons = cp.tileIcons;
      cdq iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
      if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
         return iconH;
      } else {
         cdq iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
         if (iconV == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconV == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconV == tileIcons[2] ? tileIcons[6] : iconV;
         }
      }
   }

   private static cdq getConnectedTextureVerticalHorizontal(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata) {
      cdq[] tileIcons = cp.tileIcons;
      cdq iconV = getConnectedTextureVertical(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
      if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
         return iconV;
      } else {
         cdq iconH = getConnectedTextureHorizontal(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
         if (iconH == tileIcons[0]) {
            return tileIcons[4];
         } else if (iconH == tileIcons[1]) {
            return tileIcons[5];
         } else {
            return iconH == tileIcons[2] ? tileIcons[6] : iconH;
         }
      }
   }

   private static cdq getConnectedTextureTop(ConnectedProperties cp, amy blockAccess, awt blockState, et blockPos, int vertAxis, int side, cdq icon, int metadata) {
      boolean top = false;
      switch(vertAxis) {
      case 0:
         if (side == 1 || side == 0) {
            return null;
         }

         top = isNeighbour(cp, blockAccess, blockState, blockPos.a(), side, icon, metadata);
         break;
      case 1:
         if (side != 3 && side != 2) {
            top = isNeighbour(cp, blockAccess, blockState, blockPos.d(), side, icon, metadata);
            break;
         }

         return null;
      case 2:
         if (side == 5 || side == 4) {
            return null;
         }

         top = isNeighbour(cp, blockAccess, blockState, blockPos.f(), side, icon, metadata);
      }

      if (top) {
         return cp.tileIcons[0];
      } else {
         return null;
      }
   }

   public static void updateIcons(cdp textureMap) {
      blockProperties = (ConnectedProperties[][])null;
      tileProperties = (ConnectedProperties[][])null;
      spriteQuadMaps = null;
      spriteQuadCompactMaps = (Map[][])null;
      if (.Config.isConnectedTextures()) {
         cer[] rps = .Config.getResourcePacks();

         for(int i = rps.length - 1; i >= 0; --i) {
            cer rp = rps[i];
            updateIcons(textureMap, rp);
         }

         updateIcons(textureMap, .Config.getDefaultResourcePack());
         nf locEmpty = new nf("mcpatcher/ctm/default/empty");
         emptySprite = textureMap.a(locEmpty);
         spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadFullMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
         spriteQuadCompactMaps = new Map[textureMap.getCountRegisteredSprites() + 1][];
         if (blockProperties.length <= 0) {
            blockProperties = (ConnectedProperties[][])null;
         }

         if (tileProperties.length <= 0) {
            tileProperties = (ConnectedProperties[][])null;
         }

      }
   }

   private static void updateIconEmpty(cdp textureMap) {
   }

   public static void updateIcons(cdp textureMap, cer rp) {
      String[] names = ResUtils.collectFiles(rp, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
      Arrays.sort(names);
      List tileList = makePropertyList(tileProperties);
      List blockList = makePropertyList(blockProperties);

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         .Config.dbg("ConnectedTextures: " + name);

         try {
            nf locFile = new nf(name);
            InputStream in = rp.a(locFile);
            if (in == null) {
               .Config.warn("ConnectedTextures file not found: " + name);
            } else {
               Properties props = new Properties();
               props.load(in);
               ConnectedProperties cp = new ConnectedProperties(props, name);
               if (cp.isValid(name)) {
                  cp.updateIcons(textureMap);
                  addToTileList(cp, tileList);
                  addToBlockList(cp, blockList);
               }
            }
         } catch (FileNotFoundException var11) {
            .Config.warn("ConnectedTextures file not found: " + name);
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      blockProperties = propertyListToArray(blockList);
      tileProperties = propertyListToArray(tileList);
      multipass = detectMultipass();
      .Config.dbg("Multipass connected textures: " + multipass);
   }

   private static List makePropertyList(ConnectedProperties[][] propsArr) {
      List list = new ArrayList();
      if (propsArr != null) {
         for(int i = 0; i < propsArr.length; ++i) {
            ConnectedProperties[] props = propsArr[i];
            List propList = null;
            if (props != null) {
               propList = new ArrayList(Arrays.asList(props));
            }

            list.add(propList);
         }
      }

      return list;
   }

   private static boolean detectMultipass() {
      List propList = new ArrayList();

      int i;
      ConnectedProperties[] cps;
      for(i = 0; i < tileProperties.length; ++i) {
         cps = tileProperties[i];
         if (cps != null) {
            propList.addAll(Arrays.asList(cps));
         }
      }

      for(i = 0; i < blockProperties.length; ++i) {
         cps = blockProperties[i];
         if (cps != null) {
            propList.addAll(Arrays.asList(cps));
         }
      }

      ConnectedProperties[] props = (ConnectedProperties[])((ConnectedProperties[])propList.toArray(new ConnectedProperties[propList.size()]));
      Set matchIconSet = new HashSet();
      Set tileIconSet = new HashSet();

      for(int i = 0; i < props.length; ++i) {
         ConnectedProperties cp = props[i];
         if (cp.matchTileIcons != null) {
            matchIconSet.addAll(Arrays.asList(cp.matchTileIcons));
         }

         if (cp.tileIcons != null) {
            tileIconSet.addAll(Arrays.asList(cp.tileIcons));
         }
      }

      matchIconSet.retainAll(tileIconSet);
      return !matchIconSet.isEmpty();
   }

   private static ConnectedProperties[][] propertyListToArray(List list) {
      ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];

      for(int i = 0; i < list.size(); ++i) {
         List subList = (List)list.get(i);
         if (subList != null) {
            ConnectedProperties[] subArr = (ConnectedProperties[])((ConnectedProperties[])subList.toArray(new ConnectedProperties[subList.size()]));
            propArr[i] = subArr;
         }
      }

      return propArr;
   }

   private static void addToTileList(ConnectedProperties cp, List tileList) {
      if (cp.matchTileIcons != null) {
         for(int i = 0; i < cp.matchTileIcons.length; ++i) {
            cdq icon = cp.matchTileIcons[i];
            if (!(icon instanceof cdq)) {
               .Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + icon + ", name: " + icon.i());
            } else {
               int tileId = icon.getIndexInMap();
               if (tileId < 0) {
                  .Config.warn("Invalid tile ID: " + tileId + ", icon: " + icon.i());
               } else {
                  addToList(cp, tileList, tileId);
               }
            }
         }

      }
   }

   private static void addToBlockList(ConnectedProperties cp, List blockList) {
      if (cp.matchBlocks != null) {
         for(int i = 0; i < cp.matchBlocks.length; ++i) {
            int blockId = cp.matchBlocks[i].getBlockId();
            if (blockId < 0) {
               .Config.warn("Invalid block ID: " + blockId);
            } else {
               addToList(cp, blockList, blockId);
            }
         }

      }
   }

   private static void addToList(ConnectedProperties cp, List list, int id) {
      while(id >= list.size()) {
         list.add((Object)null);
      }

      List subList = (List)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      ((List)subList).add(cp);
   }

   private static String[] getDefaultCtmPaths() {
      List list = new ArrayList();
      String defPath = "mcpatcher/ctm/default/";
      if (.Config.isFromDefaultResourcePack(new nf("textures/blocks/glass.png"))) {
         list.add(defPath + "glass.properties");
         list.add(defPath + "glasspane.properties");
      }

      if (.Config.isFromDefaultResourcePack(new nf("textures/blocks/bookshelf.png"))) {
         list.add(defPath + "bookshelf.properties");
      }

      if (.Config.isFromDefaultResourcePack(new nf("textures/blocks/sandstone_normal.png"))) {
         list.add(defPath + "sandstone.properties");
      }

      String[] colors = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};

      for(int i = 0; i < colors.length; ++i) {
         String color = colors[i];
         if (.Config.isFromDefaultResourcePack(new nf("textures/blocks/glass_" + color + ".png"))) {
            list.add(defPath + i + "_glass_" + color + "/glass_" + color + ".properties");
            list.add(defPath + i + "_glass_" + color + "/glass_pane_" + color + ".properties");
         }
      }

      String[] paths = (String[])((String[])list.toArray(new String[list.size()]));
      return paths;
   }

   static {
      AIR_DEFAULT_STATE = aox.a.t();
      emptySprite = null;
      SIDES_Y_NEG_DOWN = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH};
      SIDES_Y_POS_UP = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH};
      SIDES_Z_NEG_NORTH = new BlockDir[]{BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP};
      SIDES_Z_POS_SOUTH = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP};
      SIDES_X_NEG_WEST = new BlockDir[]{BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP};
      SIDES_X_POS_EAST = new BlockDir[]{BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP};
      SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[]{BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN};
      SIDES_X_POS_EAST_X_AXIS = new BlockDir[]{BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN};
      EDGES_Y_NEG_DOWN = new BlockDir[]{BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST};
      EDGES_Y_POS_UP = new BlockDir[]{BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST};
      EDGES_Z_NEG_NORTH = new BlockDir[]{BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST};
      EDGES_Z_POS_SOUTH = new BlockDir[]{BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST};
      EDGES_X_NEG_WEST = new BlockDir[]{BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH};
      EDGES_X_POS_EAST = new BlockDir[]{BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH};
      EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[]{BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST};
      EDGES_X_POS_EAST_X_AXIS = new BlockDir[]{BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH};
      SPRITE_DEFAULT = new cdq("<default>");
   }
}
