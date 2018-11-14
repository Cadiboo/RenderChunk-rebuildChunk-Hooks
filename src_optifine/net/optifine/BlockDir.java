package net.optifine;

public enum BlockDir {
   DOWN(fa.a),
   UP(fa.b),
   NORTH(fa.c),
   SOUTH(fa.d),
   WEST(fa.e),
   EAST(fa.f),
   NORTH_WEST(fa.c, fa.e),
   NORTH_EAST(fa.c, fa.f),
   SOUTH_WEST(fa.d, fa.e),
   SOUTH_EAST(fa.d, fa.f),
   DOWN_NORTH(fa.a, fa.c),
   DOWN_SOUTH(fa.a, fa.d),
   UP_NORTH(fa.b, fa.c),
   UP_SOUTH(fa.b, fa.d),
   DOWN_WEST(fa.a, fa.e),
   DOWN_EAST(fa.a, fa.f),
   UP_WEST(fa.b, fa.e),
   UP_EAST(fa.b, fa.f);

   private fa facing1;
   private fa facing2;

   private BlockDir(fa facing1) {
      this.facing1 = facing1;
   }

   private BlockDir(fa facing1, fa facing2) {
      this.facing1 = facing1;
      this.facing2 = facing2;
   }

   public fa getFacing1() {
      return this.facing1;
   }

   public fa getFacing2() {
      return this.facing2;
   }

   et offset(et pos) {
      pos = pos.a(this.facing1, 1);
      if (this.facing2 != null) {
         pos = pos.a(this.facing2, 1);
      }

      return pos;
   }

   public int getOffsetX() {
      int offset = this.facing1.g();
      if (this.facing2 != null) {
         offset += this.facing2.g();
      }

      return offset;
   }

   public int getOffsetY() {
      int offset = this.facing1.h();
      if (this.facing2 != null) {
         offset += this.facing2.h();
      }

      return offset;
   }

   public int getOffsetZ() {
      int offset = this.facing1.i();
      if (this.facing2 != null) {
         offset += this.facing2.i();
      }

      return offset;
   }

   public boolean isDouble() {
      return this.facing2 != null;
   }
}
