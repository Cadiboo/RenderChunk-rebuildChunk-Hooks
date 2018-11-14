package net.optifine.config;

public class EntityClassLocator implements IObjectLocator {
   public Object getObject(nf loc) {
      Class cls = vi.a(loc.toString());
      return cls;
   }
}
