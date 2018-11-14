package net.optifine.render;

import bvo.b;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import net.optifine.BlockPosM;
import net.optifine.model.ListQuadsOverlay;

public class RenderEnv {
   private amy blockAccess;
   private awt blockState;
   private et blockPos;
   private int blockId = -1;
   private int metadata = -1;
   private int breakingAnimation = -1;
   private int smartLeaves = -1;
   private float[] quadBounds;
   private BitSet boundsFlags;
   private b aoFace;
   private BlockPosM colorizerBlockPosM;
   private boolean[] borderFlags;
   private boolean[] borderFlags2;
   private boolean[] borderFlags3;
   private fa[] borderDirections;
   private List listQuadsCustomizer;
   private List listQuadsCtmMultipass;
   private bvp[] arrayQuadsCtm1;
   private bvp[] arrayQuadsCtm2;
   private bvp[] arrayQuadsCtm3;
   private bvp[] arrayQuadsCtm4;
   private bum regionRenderCacheBuilder;
   private ListQuadsOverlay[] listsQuadsOverlay;
   private boolean overlaysRendered;
   private static final int UNKNOWN = -1;
   private static final int FALSE = 0;
   private static final int TRUE = 1;

   public RenderEnv(amy blockAccess, awt blockState, et blockPos) {
      this.quadBounds = new float[fa.n.length * 2];
      this.boundsFlags = new BitSet(3);
      this.aoFace = new b();
      this.colorizerBlockPosM = null;
      this.borderFlags = null;
      this.borderFlags2 = null;
      this.borderFlags3 = null;
      this.borderDirections = null;
      this.listQuadsCustomizer = new ArrayList();
      this.listQuadsCtmMultipass = new ArrayList();
      this.arrayQuadsCtm1 = new bvp[1];
      this.arrayQuadsCtm2 = new bvp[2];
      this.arrayQuadsCtm3 = new bvp[3];
      this.arrayQuadsCtm4 = new bvp[4];
      this.regionRenderCacheBuilder = null;
      this.listsQuadsOverlay = new ListQuadsOverlay[amm.values().length];
      this.overlaysRendered = false;
      this.blockAccess = blockAccess;
      this.blockState = blockState;
      this.blockPos = blockPos;
   }

   public void reset(amy blockAccessIn, awt blockStateIn, et blockPosIn) {
      if (this.blockAccess != blockAccessIn || this.blockState != blockStateIn || this.blockPos != blockPosIn) {
         this.blockAccess = blockAccessIn;
         this.blockState = blockStateIn;
         this.blockPos = blockPosIn;
         this.blockId = -1;
         this.metadata = -1;
         this.breakingAnimation = -1;
         this.smartLeaves = -1;
         this.boundsFlags.clear();
      }
   }

   public int getBlockId() {
      if (this.blockId < 0) {
         if (this.blockState instanceof awp) {
            awp bsb = (awp)this.blockState;
            this.blockId = bsb.getBlockId();
         } else {
            this.blockId = aow.a(this.blockState.u());
         }
      }

      return this.blockId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         if (this.blockState instanceof awp) {
            awp bsb = (awp)this.blockState;
            this.metadata = bsb.getMetadata();
         } else {
            this.metadata = this.blockState.u().e(this.blockState);
         }
      }

      return this.metadata;
   }

   public float[] getQuadBounds() {
      return this.quadBounds;
   }

   public BitSet getBoundsFlags() {
      return this.boundsFlags;
   }

   public b getAoFace() {
      return this.aoFace;
   }

   public boolean isBreakingAnimation(List listQuads) {
      if (this.breakingAnimation == -1 && listQuads.size() > 0) {
         if (listQuads.get(0) instanceof bvw) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public boolean isBreakingAnimation(bvp quad) {
      if (this.breakingAnimation < 0) {
         if (quad instanceof bvw) {
            this.breakingAnimation = 1;
         } else {
            this.breakingAnimation = 0;
         }
      }

      return this.breakingAnimation == 1;
   }

   public boolean isBreakingAnimation() {
      return this.breakingAnimation == 1;
   }

   public awt getBlockState() {
      return this.blockState;
   }

   public BlockPosM getColorizerBlockPosM() {
      if (this.colorizerBlockPosM == null) {
         this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
      }

      return this.colorizerBlockPosM;
   }

   public boolean[] getBorderFlags() {
      if (this.borderFlags == null) {
         this.borderFlags = new boolean[4];
      }

      return this.borderFlags;
   }

   public boolean[] getBorderFlags2() {
      if (this.borderFlags2 == null) {
         this.borderFlags2 = new boolean[4];
      }

      return this.borderFlags2;
   }

   public boolean[] getBorderFlags3() {
      if (this.borderFlags3 == null) {
         this.borderFlags3 = new boolean[4];
      }

      return this.borderFlags3;
   }

   public fa[] getBorderDirections() {
      if (this.borderDirections == null) {
         this.borderDirections = new fa[4];
      }

      return this.borderDirections;
   }

   public fa[] getBorderDirections(fa dir0, fa dir1, fa dir2, fa dir3) {
      fa[] dirs = this.getBorderDirections();
      dirs[0] = dir0;
      dirs[1] = dir1;
      dirs[2] = dir2;
      dirs[3] = dir3;
      return dirs;
   }

   public boolean isSmartLeaves() {
      if (this.smartLeaves == -1) {
         if (.Config.isTreesSmart() && this.blockState.u() instanceof arr) {
            this.smartLeaves = 1;
         } else {
            this.smartLeaves = 0;
         }
      }

      return this.smartLeaves == 1;
   }

   public List getListQuadsCustomizer() {
      return this.listQuadsCustomizer;
   }

   public bvp[] getArrayQuadsCtm(bvp quad) {
      this.arrayQuadsCtm1[0] = quad;
      return this.arrayQuadsCtm1;
   }

   public bvp[] getArrayQuadsCtm(bvp quad0, bvp quad1) {
      this.arrayQuadsCtm2[0] = quad0;
      this.arrayQuadsCtm2[1] = quad1;
      return this.arrayQuadsCtm2;
   }

   public bvp[] getArrayQuadsCtm(bvp quad0, bvp quad1, bvp quad2) {
      this.arrayQuadsCtm3[0] = quad0;
      this.arrayQuadsCtm3[1] = quad1;
      this.arrayQuadsCtm3[2] = quad2;
      return this.arrayQuadsCtm3;
   }

   public bvp[] getArrayQuadsCtm(bvp quad0, bvp quad1, bvp quad2, bvp quad3) {
      this.arrayQuadsCtm4[0] = quad0;
      this.arrayQuadsCtm4[1] = quad1;
      this.arrayQuadsCtm4[2] = quad2;
      this.arrayQuadsCtm4[3] = quad3;
      return this.arrayQuadsCtm4;
   }

   public List getListQuadsCtmMultipass(bvp[] quads) {
      this.listQuadsCtmMultipass.clear();
      if (quads != null) {
         for(int i = 0; i < quads.length; ++i) {
            bvp quad = quads[i];
            this.listQuadsCtmMultipass.add(quad);
         }
      }

      return this.listQuadsCtmMultipass;
   }

   public bum getRegionRenderCacheBuilder() {
      return this.regionRenderCacheBuilder;
   }

   public void setRegionRenderCacheBuilder(bum regionRenderCacheBuilder) {
      this.regionRenderCacheBuilder = regionRenderCacheBuilder;
   }

   public ListQuadsOverlay getListQuadsOverlay(amm layer) {
      ListQuadsOverlay list = this.listsQuadsOverlay[layer.ordinal()];
      if (list == null) {
         list = new ListQuadsOverlay();
         this.listsQuadsOverlay[layer.ordinal()] = list;
      }

      return list;
   }

   public boolean isOverlaysRendered() {
      return this.overlaysRendered;
   }

   public void setOverlaysRendered(boolean overlaysRendered) {
      this.overlaysRendered = overlaysRendered;
   }
}
