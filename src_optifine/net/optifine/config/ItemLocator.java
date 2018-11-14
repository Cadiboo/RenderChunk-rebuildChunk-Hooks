package net.optifine.config;

public class ItemLocator implements IObjectLocator {
   public Object getObject(nf loc) {
      ain item = ain.b(loc.toString());
      return item;
   }
}
