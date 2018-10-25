package cadiboo.renderchunkrebuildchunkhooks.mod;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class RenderChunkRebuildChunkHooksDummyContainer extends DummyModContainer {

	public static final String	MOD_ID		= "renderchunkrebuildchunkhooks";
	public static final String	MOD_NAME	= "RenderChunk rebuild Chunk Hooks";
	public static final String	MOD_VERSION	= "1.0.0.0";

	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public RenderChunkRebuildChunkHooksDummyContainer() {

		super(new ModMetadata());
		final ModMetadata meta = this.getMetadata();
		meta.modId = MOD_ID;
		meta.name = MOD_NAME;
		meta.version = MOD_VERSION;
		meta.credits = "Me, Myself and I";
		meta.authorList = Arrays.asList("Cadiboo");
		meta.description = "";
		meta.url = "https://github.com/Cadiboo/RenderChunkRebuildChunkHooks";
		meta.updateJSON = "https://github.com/Cadiboo/RenderChunkRebuildChunkHooks/update.json";
		meta.screenshots = new String[0];
		meta.logoFile = "/" + MOD_ID + "_logo.png";

	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}

}
