package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListQuadsOverlay {
   private List listQuads = new ArrayList();
   private List listBlockStates = new ArrayList();
   private List listQuadsSingle = Arrays.asList();

   public void addQuad(bvp quad, awt blockState) {
      this.listQuads.add(quad);
      this.listBlockStates.add(blockState);
   }

   public int size() {
      return this.listQuads.size();
   }

   public bvp getQuad(int index) {
      return (bvp)this.listQuads.get(index);
   }

   public awt getBlockState(int index) {
      return index >= 0 && index < this.listBlockStates.size() ? (awt)this.listBlockStates.get(index) : aox.a.t();
   }

   public List getListQuadsSingle(bvp quad) {
      this.listQuadsSingle.set(0, quad);
      return this.listQuadsSingle;
   }

   public void clear() {
      this.listQuads.clear();
      this.listBlockStates.clear();
   }
}
