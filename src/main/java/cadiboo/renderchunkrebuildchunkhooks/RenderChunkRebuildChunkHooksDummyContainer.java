package cadiboo.renderchunkrebuildchunkhooks;

import java.util.Arrays;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

//@Mod(modid = RenderChunkRebuildChunkHooks.MODID, name = RenderChunkRebuildChunkHooks.NAME, version = RenderChunkRebuildChunkHooks.VERSION)
public class RenderChunkRebuildChunkHooksDummyContainer extends DummyModContainer {

	public RenderChunkRebuildChunkHooksDummyContainer() {

		super(new ModMetadata());
		final ModMetadata meta = this.getMetadata();
		meta.modId = "CreeperBurnCore";
		meta.name = "CreeperBurnCore";
		meta.version = "@VERSION@";
		meta.credits = "Roll Credits ...";
		meta.authorList = Arrays.asList("culegooner");
		meta.description = "";
		meta.url = "https://github.com/culegooner/CreeperBurnCore";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";

	}
}
