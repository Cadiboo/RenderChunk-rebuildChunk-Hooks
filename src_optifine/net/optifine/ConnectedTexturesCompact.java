package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.optifine.render.RenderEnv;

public class ConnectedTexturesCompact {
   private static final int COMPACT_NONE = 0;
   private static final int COMPACT_ALL = 1;
   private static final int COMPACT_V = 2;
   private static final int COMPACT_H = 3;
   private static final int COMPACT_HV = 4;

   public static bvp[] getConnectedTextureCtmCompact(int ctmIndex, ConnectedProperties cp, int side, bvp quad, RenderEnv renderEnv) {
      if (cp.ctmTileIndexes != null && ctmIndex >= 0 && ctmIndex < cp.ctmTileIndexes.length) {
         int tileIndex = cp.ctmTileIndexes[ctmIndex];
         if (tileIndex >= 0 && tileIndex <= cp.tileIcons.length) {
            return getQuadsCompact(tileIndex, cp.tileIcons, quad, renderEnv);
         }
      }

      switch(ctmIndex) {
      case 1:
         return getQuadsCompactH(0, 3, cp.tileIcons, side, quad, renderEnv);
      case 2:
         return getQuadsCompact(3, cp.tileIcons, quad, renderEnv);
      case 3:
         return getQuadsCompactH(3, 0, cp.tileIcons, side, quad, renderEnv);
      case 4:
         return getQuadsCompact4(0, 3, 2, 4, cp.tileIcons, side, quad, renderEnv);
      case 5:
         return getQuadsCompact4(3, 0, 4, 2, cp.tileIcons, side, quad, renderEnv);
      case 6:
         return getQuadsCompact4(2, 4, 2, 4, cp.tileIcons, side, quad, renderEnv);
      case 7:
         return getQuadsCompact4(3, 3, 4, 4, cp.tileIcons, side, quad, renderEnv);
      case 8:
         return getQuadsCompact4(4, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
      case 9:
         return getQuadsCompact4(4, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
      case 10:
         return getQuadsCompact4(1, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
      case 11:
         return getQuadsCompact4(1, 1, 4, 4, cp.tileIcons, side, quad, renderEnv);
      case 12:
         return getQuadsCompactV(0, 2, cp.tileIcons, side, quad, renderEnv);
      case 13:
         return getQuadsCompact4(0, 3, 2, 1, cp.tileIcons, side, quad, renderEnv);
      case 14:
         return getQuadsCompactV(3, 1, cp.tileIcons, side, quad, renderEnv);
      case 15:
         return getQuadsCompact4(3, 0, 1, 2, cp.tileIcons, side, quad, renderEnv);
      case 16:
         return getQuadsCompact4(2, 4, 0, 3, cp.tileIcons, side, quad, renderEnv);
      case 17:
         return getQuadsCompact4(4, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
      case 18:
         return getQuadsCompact4(4, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
      case 19:
         return getQuadsCompact4(4, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
      case 20:
         return getQuadsCompact4(1, 4, 4, 4, cp.tileIcons, side, quad, renderEnv);
      case 21:
         return getQuadsCompact4(4, 4, 1, 4, cp.tileIcons, side, quad, renderEnv);
      case 22:
         return getQuadsCompact4(4, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
      case 23:
         return getQuadsCompact4(4, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
      case 24:
         return getQuadsCompact(2, cp.tileIcons, quad, renderEnv);
      case 25:
         return getQuadsCompactH(2, 1, cp.tileIcons, side, quad, renderEnv);
      case 26:
         return getQuadsCompact(1, cp.tileIcons, quad, renderEnv);
      case 27:
         return getQuadsCompactH(1, 2, cp.tileIcons, side, quad, renderEnv);
      case 28:
         return getQuadsCompact4(2, 4, 2, 1, cp.tileIcons, side, quad, renderEnv);
      case 29:
         return getQuadsCompact4(3, 3, 1, 4, cp.tileIcons, side, quad, renderEnv);
      case 30:
         return getQuadsCompact4(2, 1, 2, 4, cp.tileIcons, side, quad, renderEnv);
      case 31:
         return getQuadsCompact4(3, 3, 4, 1, cp.tileIcons, side, quad, renderEnv);
      case 32:
         return getQuadsCompact4(1, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
      case 33:
         return getQuadsCompact4(1, 1, 4, 1, cp.tileIcons, side, quad, renderEnv);
      case 34:
         return getQuadsCompact4(4, 1, 1, 4, cp.tileIcons, side, quad, renderEnv);
      case 35:
         return getQuadsCompact4(1, 4, 4, 1, cp.tileIcons, side, quad, renderEnv);
      case 36:
         return getQuadsCompactV(2, 0, cp.tileIcons, side, quad, renderEnv);
      case 37:
         return getQuadsCompact4(2, 1, 0, 3, cp.tileIcons, side, quad, renderEnv);
      case 38:
         return getQuadsCompactV(1, 3, cp.tileIcons, side, quad, renderEnv);
      case 39:
         return getQuadsCompact4(1, 2, 3, 0, cp.tileIcons, side, quad, renderEnv);
      case 40:
         return getQuadsCompact4(4, 1, 3, 3, cp.tileIcons, side, quad, renderEnv);
      case 41:
         return getQuadsCompact4(1, 2, 4, 2, cp.tileIcons, side, quad, renderEnv);
      case 42:
         return getQuadsCompact4(1, 4, 3, 3, cp.tileIcons, side, quad, renderEnv);
      case 43:
         return getQuadsCompact4(4, 2, 1, 2, cp.tileIcons, side, quad, renderEnv);
      case 44:
         return getQuadsCompact4(1, 4, 1, 1, cp.tileIcons, side, quad, renderEnv);
      case 45:
         return getQuadsCompact4(4, 1, 1, 1, cp.tileIcons, side, quad, renderEnv);
      case 46:
         return getQuadsCompact(4, cp.tileIcons, quad, renderEnv);
      default:
         return getQuadsCompact(0, cp.tileIcons, quad, renderEnv);
      }
   }

   private static bvp[] getQuadsCompactH(int indexLeft, int indexRight, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      return getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, indexLeft, ConnectedTexturesCompact.Dir.RIGHT, indexRight, sprites, side, quad, renderEnv);
   }

   private static bvp[] getQuadsCompactV(int indexUp, int indexDown, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      return getQuadsCompact(ConnectedTexturesCompact.Dir.UP, indexUp, ConnectedTexturesCompact.Dir.DOWN, indexDown, sprites, side, quad, renderEnv);
   }

   private static bvp[] getQuadsCompact4(int upLeft, int upRight, int downLeft, int downRight, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      if (upLeft == upRight) {
         return downLeft == downRight ? getQuadsCompact(ConnectedTexturesCompact.Dir.UP, upLeft, ConnectedTexturesCompact.Dir.DOWN, downLeft, sprites, side, quad, renderEnv) : getQuadsCompact(ConnectedTexturesCompact.Dir.UP, upLeft, ConnectedTexturesCompact.Dir.DOWN_LEFT, downLeft, ConnectedTexturesCompact.Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
      } else if (downLeft == downRight) {
         return getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, upLeft, ConnectedTexturesCompact.Dir.UP_RIGHT, upRight, ConnectedTexturesCompact.Dir.DOWN, downLeft, sprites, side, quad, renderEnv);
      } else if (upLeft == downLeft) {
         return upRight == downRight ? getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, upLeft, ConnectedTexturesCompact.Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, upLeft, ConnectedTexturesCompact.Dir.UP_RIGHT, upRight, ConnectedTexturesCompact.Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
      } else {
         return upRight == downRight ? getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, upLeft, ConnectedTexturesCompact.Dir.DOWN_LEFT, downLeft, ConnectedTexturesCompact.Dir.RIGHT, upRight, sprites, side, quad, renderEnv) : getQuadsCompact(ConnectedTexturesCompact.Dir.UP_LEFT, upLeft, ConnectedTexturesCompact.Dir.UP_RIGHT, upRight, ConnectedTexturesCompact.Dir.DOWN_LEFT, downLeft, ConnectedTexturesCompact.Dir.DOWN_RIGHT, downRight, sprites, side, quad, renderEnv);
      }
   }

   private static bvp[] getQuadsCompact(int index, cdq[] sprites, bvp quad, RenderEnv renderEnv) {
      cdq sprite = sprites[index];
      return ConnectedTextures.getQuads(sprite, quad, renderEnv);
   }

   private static bvp[] getQuadsCompact(ConnectedTexturesCompact.Dir dir1, int index1, ConnectedTexturesCompact.Dir dir2, int index2, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      bvp quad1 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
      bvp quad2 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
      return renderEnv.getArrayQuadsCtm(quad1, quad2);
   }

   private static bvp[] getQuadsCompact(ConnectedTexturesCompact.Dir dir1, int index1, ConnectedTexturesCompact.Dir dir2, int index2, ConnectedTexturesCompact.Dir dir3, int index3, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      bvp quad1 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
      bvp quad2 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
      bvp quad3 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
      return renderEnv.getArrayQuadsCtm(quad1, quad2, quad3);
   }

   private static bvp[] getQuadsCompact(ConnectedTexturesCompact.Dir dir1, int index1, ConnectedTexturesCompact.Dir dir2, int index2, ConnectedTexturesCompact.Dir dir3, int index3, ConnectedTexturesCompact.Dir dir4, int index4, cdq[] sprites, int side, bvp quad, RenderEnv renderEnv) {
      bvp quad1 = getQuadCompact(sprites[index1], dir1, side, quad, renderEnv);
      bvp quad2 = getQuadCompact(sprites[index2], dir2, side, quad, renderEnv);
      bvp quad3 = getQuadCompact(sprites[index3], dir3, side, quad, renderEnv);
      bvp quad4 = getQuadCompact(sprites[index4], dir4, side, quad, renderEnv);
      return renderEnv.getArrayQuadsCtm(quad1, quad2, quad3, quad4);
   }

   private static bvp getQuadCompact(cdq sprite, ConnectedTexturesCompact.Dir dir, int side, bvp quad, RenderEnv renderEnv) {
      switch(dir) {
      case UP:
         return getQuadCompact(sprite, dir, 0, 0, 16, 8, side, quad, renderEnv);
      case UP_RIGHT:
         return getQuadCompact(sprite, dir, 8, 0, 16, 8, side, quad, renderEnv);
      case RIGHT:
         return getQuadCompact(sprite, dir, 8, 0, 16, 16, side, quad, renderEnv);
      case DOWN_RIGHT:
         return getQuadCompact(sprite, dir, 8, 8, 16, 16, side, quad, renderEnv);
      case DOWN:
         return getQuadCompact(sprite, dir, 0, 8, 16, 16, side, quad, renderEnv);
      case DOWN_LEFT:
         return getQuadCompact(sprite, dir, 0, 8, 8, 16, side, quad, renderEnv);
      case LEFT:
         return getQuadCompact(sprite, dir, 0, 0, 8, 16, side, quad, renderEnv);
      case UP_LEFT:
         return getQuadCompact(sprite, dir, 0, 0, 8, 8, side, quad, renderEnv);
      default:
         return quad;
      }
   }

   private static bvp getQuadCompact(cdq sprite, ConnectedTexturesCompact.Dir dir, int x1, int y1, int x2, int y2, int side, bvp quadIn, RenderEnv renderEnv) {
      Map[][] spriteQuadCompactMaps = ConnectedTextures.getSpriteQuadCompactMaps();
      if (spriteQuadCompactMaps == null) {
         return quadIn;
      } else {
         int spriteIndex = sprite.getIndexInMap();
         if (spriteIndex >= 0 && spriteIndex < spriteQuadCompactMaps.length) {
            Map[] quadMaps = spriteQuadCompactMaps[spriteIndex];
            if (quadMaps == null) {
               quadMaps = new Map[ConnectedTexturesCompact.Dir.VALUES.length];
               spriteQuadCompactMaps[spriteIndex] = quadMaps;
            }

            Map quadMap = quadMaps[dir.ordinal()];
            if (quadMap == null) {
               quadMap = new IdentityHashMap(1);
               quadMaps[dir.ordinal()] = (Map)quadMap;
            }

            bvp quad = (bvp)((Map)quadMap).get(quadIn);
            if (quad == null) {
               quad = makeSpriteQuadCompact(quadIn, sprite, side, x1, y1, x2, y2);
               ((Map)quadMap).put(quadIn, quad);
            }

            return quad;
         } else {
            return quadIn;
         }
      }
   }

   private static bvp makeSpriteQuadCompact(bvp quad, cdq sprite, int side, int x1, int y1, int x2, int y2) {
      int[] data = (int[])quad.b().clone();
      cdq spriteFrom = quad.a();

      for(int i = 0; i < 4; ++i) {
         fixVertexCompact(data, i, spriteFrom, sprite, side, x1, y1, x2, y2);
      }

      bvp bq = new bvp(data, quad.d(), quad.e(), sprite);
      return bq;
   }

   private static void fixVertexCompact(int[] data, int vertex, cdq spriteFrom, cdq spriteTo, int side, int x1, int y1, int x2, int y2) {
      int mul = data.length / 4;
      int pos = mul * vertex;
      float u = Float.intBitsToFloat(data[pos + 4]);
      float v = Float.intBitsToFloat(data[pos + 4 + 1]);
      double su16 = spriteFrom.getSpriteU16(u);
      double sv16 = spriteFrom.getSpriteV16(v);
      float x = Float.intBitsToFloat(data[pos + 0]);
      float y = Float.intBitsToFloat(data[pos + 1]);
      float z = Float.intBitsToFloat(data[pos + 2]);
      float cu;
      float cv;
      switch(side) {
      case 0:
         cu = x;
         cv = 1.0F - z;
         break;
      case 1:
         cu = x;
         cv = z;
         break;
      case 2:
         cu = 1.0F - x;
         cv = 1.0F - y;
         break;
      case 3:
         cu = x;
         cv = 1.0F - y;
         break;
      case 4:
         cu = z;
         cv = 1.0F - y;
         break;
      case 5:
         cu = 1.0F - z;
         cv = 1.0F - y;
         break;
      default:
         return;
      }

      float u16F = 15.968F;
      float v16F = 15.968F;
      if (su16 < (double)x1) {
         cu = (float)((double)cu + ((double)x1 - su16) / (double)u16F);
         su16 = (double)x1;
      }

      if (su16 > (double)x2) {
         cu = (float)((double)cu - (su16 - (double)x2) / (double)u16F);
         su16 = (double)x2;
      }

      if (sv16 < (double)y1) {
         cv = (float)((double)cv + ((double)y1 - sv16) / (double)v16F);
         sv16 = (double)y1;
      }

      if (sv16 > (double)y2) {
         cv = (float)((double)cv - (sv16 - (double)y2) / (double)v16F);
         sv16 = (double)y2;
      }

      switch(side) {
      case 0:
         x = cu;
         z = 1.0F - cv;
         break;
      case 1:
         x = cu;
         z = cv;
         break;
      case 2:
         x = 1.0F - cu;
         y = 1.0F - cv;
         break;
      case 3:
         x = cu;
         y = 1.0F - cv;
         break;
      case 4:
         z = cu;
         y = 1.0F - cv;
         break;
      case 5:
         z = 1.0F - cu;
         y = 1.0F - cv;
         break;
      default:
         return;
      }

      data[pos + 4] = Float.floatToRawIntBits(spriteTo.a(su16));
      data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.b(sv16));
      data[pos + 0] = Float.floatToRawIntBits(x);
      data[pos + 1] = Float.floatToRawIntBits(y);
      data[pos + 2] = Float.floatToRawIntBits(z);
   }

   private static enum Dir {
      UP,
      UP_RIGHT,
      RIGHT,
      DOWN_RIGHT,
      DOWN,
      DOWN_LEFT,
      LEFT,
      UP_LEFT;

      public static final ConnectedTexturesCompact.Dir[] VALUES = values();
   }
}
