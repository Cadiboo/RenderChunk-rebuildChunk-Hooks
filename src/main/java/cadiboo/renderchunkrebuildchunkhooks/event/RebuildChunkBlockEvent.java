package cadiboo.renderchunkrebuildchunkhooks.event;

import java.util.HashSet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Called when a {@link net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link net.minecraft.util.BlockRenderLayer BlockRenderLayer} the block renders in.<br>
 * Canceling this event prevents the block from being rebuilt to the chunk (and therefore rendered).<br>
 *
 * @see net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 * @author Cadiboo
 */
@Cancelable
public class RebuildChunkBlockEvent extends Event {

	private final RenderGlobal				context;
	private final ChunkCache				worldView;
	private final ChunkCompileTaskGenerator	generator;
	private final CompiledChunk				compiledChunk;
	private final BlockRendererDispatcher	blockRendererDispatcher;
	private final IBlockState				blockState;
	private final MutableBlockPos			blockPos;
	private final BufferBuilder				bufferBuilder;
	private final MutableBlockPos			renderChunkPosition;
	private final float						x;
	private final float						y;
	private final float						z;
	private final HashSet					tileEntitiesWithGlobalRenderers;
	private final VisGraph					visGraph;

	private final boolean[] usedBlockRenderLayers = new boolean[BlockRenderLayer.values().length];

	public RebuildChunkBlockEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z, final HashSet tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		this.context = renderGlobal;
		this.worldView = worldView;
		this.generator = generator;
		this.compiledChunk = compiledChunk;
		this.blockRendererDispatcher = blockRendererDispatcher;
		this.blockState = blockState;
		this.blockPos = blockPos;
		this.bufferBuilder = bufferBuilder;
		this.renderChunkPosition = renderChunkPosition;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileEntitiesWithGlobalRenderers = tileEntitiesWithGlobalRenderers;
		this.visGraph = visGraph;
	}

	public RenderGlobal getContext() {
		return this.context;
	}

	public ChunkCache getWorldView() {
		return this.worldView;
	}

	public ChunkCompileTaskGenerator getGenerator() {
		return this.generator;
	}

	public CompiledChunk getCompiledChunk() {
		return this.compiledChunk;
	}

	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return this.blockRendererDispatcher;
	}

	public IBlockState getBlockState() {
		return this.blockState;
	}

	public MutableBlockPos getBlockPos() {
		return this.blockPos;
	}

	public BufferBuilder getBufferBuilder() {
		return this.bufferBuilder;
	}

	public MutableBlockPos getRenderChunkPosition() {
		return this.renderChunkPosition;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getZ() {
		return this.z;
	}

	public HashSet getTileEntitiesWithGlobalRenderers() {
		return this.tileEntitiesWithGlobalRenderers;
	}

	public VisGraph getVisGraph() {
		return this.visGraph;
	}

	private BufferBuilder getBufferBuilderForBlockRenderLayer(final BlockRenderLayer blockRenderLayer) {
		return this.getGenerator().getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer);
	}

	/** only used BlockRenderLayers will be part of the rebuilt chunk */
	public void setBlockRenderLayerUsed(final BlockRenderLayer blockRenderLayer, final boolean used) {
		this.usedBlockRenderLayers[blockRenderLayer.ordinal()] = used;
	}

	/** only used BlockRenderLayers will be part of the rebuilt chunk */
	public void setBlockRenderLayerUsedWithOrOpperation(final BlockRenderLayer blockRenderLayer, final boolean used) {
		this.usedBlockRenderLayers[blockRenderLayer.ordinal()] |= used;
	}

	public BufferBuilder startOrContinueLayer(final BlockRenderLayer blockRenderLayer) {
		final BufferBuilder bufferbuilder = this.getBufferBuilderForBlockRenderLayer(blockRenderLayer);

		if (!this.getCompiledChunk().isLayerStarted(blockRenderLayer)) {
			this.getCompiledChunk().setLayerStarted(blockRenderLayer);
			this.preRenderBlocks(bufferbuilder, this.getRenderChunkPosition());
		}

		return bufferbuilder;
	}

	private void preRenderBlocks(final BufferBuilder bufferBuilderIn, final BlockPos pos) {
		bufferBuilderIn.begin(7, DefaultVertexFormats.BLOCK);
		bufferBuilderIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
	}

	/**
	 * if the boolean is true then the {@link BlockRenderLayer} will be rendered
	 *
	 * @return an array of booleans mapped to {@link BlockRenderLayer#ordinal()}
	 */
	public boolean[] getUsedBlockRenderLayers() {
		return this.usedBlockRenderLayers;
	}

}