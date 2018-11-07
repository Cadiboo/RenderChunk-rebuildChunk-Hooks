package cadiboo.renderchunkrebuildchunkhooks.mod;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class RenderChunkRebuildChunkHooksDummyContainer extends DummyModContainer {

	public static final String	MOD_ID		= "renderchunkrebuildchunkhooks";
	public static final String	MOD_NAME	= "RenderChunk rebuildChunk Hooks";
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
		meta.url = "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks";
		meta.updateJSON = "https://github.com/Cadiboo/RenderChunk-rebuildChunk-Hooks/update.json";
		meta.screenshots = new String[0];
		meta.logoFile = "/" + MOD_ID + "_logo.png";

	}

	@Override
	public boolean shouldLoadInEnvironment() {
		return FMLCommonHandler.instance().getSide().isClient();
	}

	@Subscribe
	public void preInit(final FMLPreInitializationEvent event) {
		if (event.getSide().isClient()) {
			LOGGER.info("Preloading RenderChunk...");
			RenderChunk.class.getName();
			LOGGER.info("Sucessfully preloaded RenderChunk!");
		}
	}

	@Override
	public boolean registerBus(final EventBus bus, final LoadController controller) {
		bus.register(this);
		return true;
	}

}
