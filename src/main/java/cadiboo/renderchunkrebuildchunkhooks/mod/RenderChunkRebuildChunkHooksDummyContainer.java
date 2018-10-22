package cadiboo.renderchunkrebuildchunkhooks.mod;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

//@Mod(modid = RenderChunkRebuildChunkHooks.MODID, name = RenderChunkRebuildChunkHooks.NAME, version = RenderChunkRebuildChunkHooks.VERSION)
public class RenderChunkRebuildChunkHooksDummyContainer extends DummyModContainer {

	public static final String	MOD_ID		= "renderchunkrebuildchunkhooks";
	public static final String	MOD_NAME	= "Render Chunk Rebuild Chunk Hooks";

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public RenderChunkRebuildChunkHooksDummyContainer() {

		super(new ModMetadata());
		final ModMetadata meta = this.getMetadata();
		meta.modId = MOD_ID;
		meta.name = MOD_NAME;
		meta.version = "1.0.0.0";
		meta.credits = "Me, Myself and I";
		meta.authorList = Arrays.asList("Cadiboo");
		meta.description = "";
		meta.url = "https://github.com/Cadiboo/RenderChunkRebuildChunkHooks";
		meta.updateUrl = "";
		meta.screenshots = new String[0];
		meta.logoFile = "";

		RenderChunk.class.getName();

	}

}
