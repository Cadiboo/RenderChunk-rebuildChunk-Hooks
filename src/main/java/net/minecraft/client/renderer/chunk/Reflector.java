package net.minecraft.client.renderer.chunk;

import net.minecraft.block.Block;

/**
 * @author Cadiboo
 */
public class Reflector {

	public static ReflectorThing ForgeBlock_canRenderInLayer;
	public static ReflectorThing ForgeHooksClient_setRenderLayer;

	public static boolean callBoolean(final Block block, final ReflectorThing forgeBlock_canRenderInLayer, final Object[] objects) {
		return false;
	}

	public static void callVoid(final ReflectorThing forgeHooksClient_setRenderLayer, final Object[] objects) {

	}

	public abstract static class ReflectorThing {

		public abstract boolean exists();

	}

}
