package cadiboo.renderchunkrebuildchunkhooks.event;

import java.util.HashSet;

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
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} right before any rebuilding is done. Canceling this event prevents all Blocks from being rebuilt to the chunk (and therefore rendered).<br>
 * TileEntities will still be rendered if this event is cancelled<br>
 * <br>
 * To rebuild blocks using this event<br>
 * Iterate over all the positions in getChunkBlockPositions()<br>
 * for each block you want to render<br>
 * 1) get the Block's BlockRenderLayer<br>
 * 2) call {@link net.minecraftforge.client.ForgeHooksClient#setRenderLayer(BlockRenderLayer) net.minecraftforge.client.ForgeHooksClient#setRenderLayer(Block's BlockRenderLayer)};<br>
 * 3) get a BufferBuilder instance with {@link RebuildChunkBlocksEvent#startOrContinueLayer(BlockRenderLayer) RebuildChunkBlocksEvent#startOrContinueLayer(Block's BlockRenderLayer)};<br>
 * 4) put any data into the BufferBuilder<br>
 * 5) call {@link RebuildChunkBlocksEvent#setBlockRenderLayerUsed(BlockRenderLayer, boolean) RebuildChunkBlocksEvent#setBlockRenderLayerUsed(Block's BlockRenderLayer, boolean)} with true if you want all the data in this BlockRenderLayer to be rendered or with false if you want none of the data in this BlockRenderLayer to be rendered<br>
 * 6) call {@link net.minecraftforge.client.ForgeHooksClient#setRenderLayer(BlockRenderLayer) net.minecraftforge.client.ForgeHooksClient#setRenderLayer(null)};<br>
 * Here is an example of how to replace vanilla's rendering with a function using this event that does the exact same thing<br>
 *
 * <pre>
 * &#64;SubscribeEvent
 * public static void rebuildChunkVanillaModded(final RebuildChunkBlocksEvent event) {
 *
 * 	event.setCancelled(true);
 *
 * 	for (final BlockPos.MutableBlockPos currentBlockPos : event.getChunkBlockPositions()) {
 *
 * 		final IBlockState blockState = event.getWorldView().getBlockState(currentBlockPos);
 * 		final Block block = blockState.getBlock();
 *
 * 		for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
 *
 * 			if (!block.canRenderInLayer(blockState, blockRenderLayer)) {
 * 				continue;
 * 			}
 *
 * 			net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);
 *
 * 			if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
 *
 * 				final BufferBuilder bufferbuilder = event.startOrContinueLayer(blockRenderLayer);
 *
 * 				final boolean used = event.getBlockRendererDispatcher().renderBlock(blockState, currentBlockPos, event.getWorldView(), bufferbuilder);
 *
 * 				event.setBlockRenderLayerUsedWithOrOpperation(blockRenderLayer, used);
 *
 * 			}
 * 		}
 * 		net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
 * 	}
 * }
 * </pre>
 *
 * @see net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkBlocksEvent extends Event {

	private final RenderGlobal				context;
	private final ChunkCache				worldView;
	private final ChunkCompileTaskGenerator	generator;
	private final CompiledChunk				compiledChunk;
	private final Iterable<MutableBlockPos>	chunkBlockPositions;
	private final BlockRendererDispatcher	blockRendererDispatcher;
	private final MutableBlockPos			renderChunkPosition;
	private final float						x;
	private final float						y;
	private final float						z;
	private final HashSet					tileEntitiesWithGlobalRenderers;
	private final VisGraph					visGraph;

	private final boolean[] usedBlockRenderLayers = new boolean[BlockRenderLayer.values().length];

	public RebuildChunkBlocksEvent(final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final Iterable<MutableBlockPos> chunkBlockPositions, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z, final HashSet tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		this.context = renderGlobal;
		this.worldView = worldView;
		this.generator = generator;
		this.compiledChunk = compiledChunk;
		this.chunkBlockPositions = chunkBlockPositions;
		this.blockRendererDispatcher = blockRendererDispatcher;
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

	public Iterable<MutableBlockPos> getChunkBlockPositions() {
		return this.chunkBlockPositions;
	}

	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return this.blockRendererDispatcher;
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

	private BufferBuilder getBufferBuilder(final BlockRenderLayer blockRenderLayer) {
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
		final BufferBuilder bufferbuilder = this.getBufferBuilder(blockRenderLayer);

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