package cadiboo.renderchunkrebuildchunkhooks.mod;

import net.minecraftforge.fml.client.DefaultGuiFactory;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;
import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_NAME;

public class RenderChunkRebuildChunkHooksGuiFactory extends DefaultGuiFactory {

	public RenderChunkRebuildChunkHooksGuiFactory() {

		super(MOD_ID, MOD_NAME);

	}

}
