package cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

/**
 * Called when a {@link net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link net.minecraft.util.BlockRenderLayer BlockRenderLayer} the block renders in.<br>
 * Setting the result of this event to {@link net.minecraftforge.fml.common.eventhandler.Event.Result#DENY} prevents the parts of the block in this {@link net.minecraft.util.BlockRenderLayer BlockRenderLayer} from being rebuilt to the chunk (and therefore rendered).<br>
 * You can perform your own rendering in this event.<br>
 *
 * @author Cadiboo
 * @see net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@HasResult
public class RebuildChunkBlockRenderInLayerEvent extends Event {

	private final RenderChunk               renderChunk;
	private final ChunkCache                worldView;
	private final ChunkCompileTaskGenerator generator;
	private final CompiledChunk             compiledChunk;
	private final BlockRendererDispatcher   blockRendererDispatcher;
	private final MutableBlockPos           renderChunkPosition;
	private final VisGraph                  visGraph;
	private final MutableBlockPos           blockPos;
	private final IBlockState               blockState;
	private final BlockRenderLayer          blockRenderLayer;

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPos                  the {@link MutableBlockPos position} of the block being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
	 */
	public RebuildChunkBlockRenderInLayerEvent(final RenderChunk renderChunk, final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final MutableBlockPos blockPos, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {

		this.renderChunk = renderChunk;
		this.worldView = worldView;
		this.generator = chunkCompileTaskGenerator;
		this.compiledChunk = compiledChunk;
		this.blockRendererDispatcher = blockRendererDispatcher;
		this.renderChunkPosition = renderChunkPosition;
		this.visGraph = visGraph;
		this.blockPos = blockPos;
		this.blockState = blockState;
		this.blockRenderLayer = blockRenderLayer;
	}

	/**
	 * @return the instance of {@link RenderChunk} the event is being fired for
	 */
	public RenderChunk getRenderChunk() {

		return this.renderChunk;
	}

	/**
	 * @return the {@link ChunkCache} passed in
	 */
	public ChunkCache getWorldView() {

		return this.worldView;
	}

	/**
	 * @return the {@link ChunkCompileTaskGenerator} passed in
	 */
	public ChunkCompileTaskGenerator getGenerator() {

		return this.generator;
	}

	/**
	 * @return the {@link CompiledChunk} passed in
	 */
	public CompiledChunk getCompiledChunk() {

		return this.compiledChunk;
	}

	/**
	 * @return the {@link BlockRendererDispatcher} passed in
	 */
	public BlockRendererDispatcher getBlockRendererDispatcher() {

		return this.blockRendererDispatcher;
	}

	/**
	 * @return the position passed in
	 */
	public MutableBlockPos getRenderChunkPosition() {

		return this.renderChunkPosition;
	}

	/**
	 * @return the {@link VisGraph} passed in
	 */
	public VisGraph getVisGraph() {

		return this.visGraph;
	}

	/**
	 * @return the position of the block passed in
	 */
	public MutableBlockPos getBlockPos() {

		return this.blockPos;
	}

	/**
	 * @return the {@link IBlockState state} of the block passed in
	 */
	public IBlockState getBlockState() {

		return this.blockState;
	}

	/**
	 * @return the {@link BlockRenderLayer} of the block passed in
	 */
	public BlockRenderLayer getBlockRenderLayer() {

		return this.blockRenderLayer;
	}

}
