package io.github.cadiboo.renderchunkrebuildchunkhooks;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformingClassLoader;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * @author Cadiboo
 */
@Mod(Refs.MOD_ID)
public class Main {

	static {
//		final TransformingClassLoader loader = FMLLoader.getLaunchClassLoader();
//		final byte[] classBytes = loader.
		int x = RenderChunk.renderChunksUpdated;

	}
	
}
