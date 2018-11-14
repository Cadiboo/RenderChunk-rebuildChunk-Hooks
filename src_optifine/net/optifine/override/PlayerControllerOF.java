package net.optifine.override;

public class PlayerControllerOF extends bsa {
   private boolean acting = false;
   private et lastClickBlockPos = null;
   private vg lastClickEntity = null;

   public PlayerControllerOF(bib mcIn, brz netHandler) {
      super(mcIn, netHandler);
   }

   public boolean a(et loc, fa face) {
      this.acting = true;
      this.lastClickBlockPos = loc;
      boolean res = super.a(loc, face);
      this.acting = false;
      return res;
   }

   public boolean b(et posBlock, fa directionFacing) {
      this.acting = true;
      this.lastClickBlockPos = posBlock;
      boolean res = super.b(posBlock, directionFacing);
      this.acting = false;
      return res;
   }

   public ud a(aed player, amu worldIn, ub hand) {
      this.acting = true;
      ud res = super.a(player, worldIn, hand);
      this.acting = false;
      return res;
   }

   public ud a(bud player, bsb worldIn, et pos, fa facing, bhe vec, ub hand) {
      this.acting = true;
      this.lastClickBlockPos = pos;
      ud res = super.a(player, worldIn, pos, facing, vec, hand);
      this.acting = false;
      return res;
   }

   public ud a(aed player, vg target, ub hand) {
      this.lastClickEntity = target;
      return super.a(player, target, hand);
   }

   public ud a(aed player, vg target, bhc ray, ub hand) {
      this.lastClickEntity = target;
      return super.a(player, target, ray, hand);
   }

   public boolean isActing() {
      return this.acting;
   }

   public et getLastClickBlockPos() {
      return this.lastClickBlockPos;
   }

   public vg getLastClickEntity() {
      return this.lastClickEntity;
   }
}
