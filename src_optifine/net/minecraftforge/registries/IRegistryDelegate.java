package net.minecraftforge.registries;

public interface IRegistryDelegate {
   Object get();

   nf name();

   Class type();
}
