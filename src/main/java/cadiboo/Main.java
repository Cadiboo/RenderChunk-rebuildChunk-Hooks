package cadiboo;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Cadiboo
 */
@Mod(Refs.MOD_ID)
public class Main {

	static {
		int x = RenderChunk.renderChunksUpdated;
	}
	
}
