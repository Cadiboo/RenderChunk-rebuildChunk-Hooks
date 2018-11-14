package net.minecraftforge.fml.common.registry;

public interface RegistryDelegate {
   Object get();

   nf name();

   Class type();
}
