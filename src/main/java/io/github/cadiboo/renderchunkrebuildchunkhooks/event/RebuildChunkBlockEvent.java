package io.github.cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.HashSet;

/**
 * Called when a {@link net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk RenderChunk.rebuildChunk} is called.<br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS EVENT_BUS} for every block inside the chunk to be rebuilt and for every {@link net.minecraft.util.BlockRenderLayer BlockRenderLayer} the block renders in.<br>
 * Canceling this event prevents the block from being rebuilt to the chunk (and therefore rendered).<br>
 * You can perform your own rendering in this event.<br>
 *
 * @author Cadiboo
 * @see net.minecraft.client.renderer.chunk.RenderChunk#rebuildChunk(float, float, float, ChunkCompileTaskGenerator)
 */
@Cancelable
public class RebuildChunkBlockEvent extends Event {

	private final RenderChunk renderChunk;
	private final RenderGlobal renderGlobal;
	private final ChunkCache chunkCache;
	private final ChunkCompileTaskGenerator generator;
	private final CompiledChunk compiledchunk;
	private final BlockRendererDispatcher blockRendererDispatcher;
	private final IBlockState blockState;
	private final MutableBlockPos blockPos;
	private final BufferBuilder bufferBuilder;
	private final MutableBlockPos renderChunkPosition;
	private final BlockRenderLayer blockRenderLayer;
	private final boolean[] usedBlockRenderLayers;
	private final float x;
	private final float y;
	private final float z;
	private final HashSet<TileEntity> tileEntitiesWithGlobalRenderers;
	private final VisGraph visGraph;

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal                    the {@link RenderGlobal} passed in
	 * @param chunkCache                      the {@link ChunkCache} passed in
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in
	 * @param compiledchunk                   the {@link CompiledChunk} passed in
	 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in
	 * @param blockState                      the {@link IBlockState state} of the block being rendered
	 * @param blockPos                        the {@link MutableBlockPos position} of the block being rendered
	 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
	 * @param renderChunkPosition             the {@link MutableBlockPos position} passed in
	 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
	 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
	 * @param x                               the translation X passed in
	 * @param y                               the translation Y passed in
	 * @param z                               the translation Z passed in
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in
	 * @param visGraph                        the {@link VisGraph} passed in
	 */
	public RebuildChunkBlockEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache chunkCache, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledchunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final MutableBlockPos renderChunkPosition, boolean[] usedBlockRenderLayers, final BlockRenderLayer blockRenderLayer, final float x,
	                              final float y, final float z, final HashSet<TileEntity> tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		this.renderChunk = renderChunk;
		this.renderGlobal = renderGlobal;
		this.chunkCache = chunkCache;
		this.generator = generator;
		this.compiledchunk = compiledchunk;
		this.blockRendererDispatcher = blockRendererDispatcher;
		this.blockState = blockState;
		this.blockPos = blockPos;
		this.bufferBuilder = bufferBuilder;
		this.renderChunkPosition = renderChunkPosition;
		this.usedBlockRenderLayers = usedBlockRenderLayers;
		this.blockRenderLayer = blockRenderLayer;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tileEntitiesWithGlobalRenderers = tileEntitiesWithGlobalRenderers;
		this.visGraph = visGraph;
	}

	/**
	 * FOR INTERNAL USE ONLY<br>
	 * Sets translation for and tarts the {@link BufferBuilder}
	 *
	 * @param bufferBuilderIn the {@link BufferBuilder} to set translation for and start
	 * @param pos             the pos to get translations from
	 */
	private static void preRenderBlocks(final BufferBuilder bufferBuilderIn, final BlockPos pos) {
		bufferBuilderIn.begin(7, DefaultVertexFormats.BLOCK);
		bufferBuilderIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
	}

	/**
	 * @return the instance of {@link RenderChunk} the event is being fired for
	 */
	public RenderChunk getRenderChunk() {
		return this.renderChunk;
	}

	/**
	 * @return the {@link RenderGlobal} passed in
	 */
	public RenderGlobal getRenderGlobal() {
		return renderGlobal;
	}

	/**
	 * @return the {@link ChunkCache} passed in
	 */
	public ChunkCache getChunkCache() {
		return this.chunkCache;
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
		return this.compiledchunk;
	}

	/**
	 * @return the {@link BlockRendererDispatcher} passed in
	 */
	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return this.blockRendererDispatcher;
	}

	/**
	 * @return the {@link IBlockState state} of the block passed in
	 */
	public IBlockState getBlockState() {
		return this.blockState;
	}

	/**
	 * @return the position of the block passed in
	 */
	public MutableBlockPos getBlockPos() {
		return this.blockPos;
	}

	/**
	 * @return the {@link BufferBuilder} passed in
	 */
	public BufferBuilder getBufferBuilder() {
		return this.bufferBuilder;
	}

	/**
	 * @return the position passed in
	 */
	public MutableBlockPos getRenderChunkPosition() {
		return this.renderChunkPosition;
	}

	/**
	 * @return the {@link BlockRenderLayer} passed in
	 */
	public BlockRenderLayer getBlockRenderLayer() {
		return this.blockRenderLayer;
	}

	/**
	 * If a boolean is true then the corresponding {@link BlockRenderLayer} will be rendered
	 *
	 * @return an array of booleans mapped to {@link BlockRenderLayer#ordinal()}
	 */
	public boolean[] getUsedBlockRenderLayers() {
		return this.usedBlockRenderLayers;
	}

	/**
	 * @return the X passed in
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * @return the Y passed in
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * @return the Z passed in
	 */
	public float getZ() {
		return this.z;
	}

	/**
	 * @return the {@link HashSet} of all {@link TileEntity TileEntities} with global renderers
	 */
	public HashSet<TileEntity> getTileEntitiesWithGlobalRenderers() {
		return this.tileEntitiesWithGlobalRenderers;
	}

	/**
	 * @return the {@link VisGraph} passed in
	 */
	public VisGraph getVisGraph() {
		return this.visGraph;
	}

	/**
	 * FOR INTERNAL USE ONLY<br>
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer} to get the {@link BufferBuilder}
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	private BufferBuilder getBufferBuilderForBlockRenderLayer(final BlockRenderLayer blockRenderLayer) {
		return this.getGenerator().getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer);
	}

	/**
	 * Only used BlockRenderLayers will be part of the rebuilt chunk
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @param used             if the {@link BlockRenderLayer} will be rendered
	 */
	public void setBlockRenderLayerUsed(final BlockRenderLayer blockRenderLayer, final boolean used) {
		this.getUsedBlockRenderLayers()[blockRenderLayer.ordinal()] = used;
	}

	/**
	 * Only used BlockRenderLayers will be part of the rebuilt chunk
	 *
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @param used             if the {@link BlockRenderLayer} will be rendered (if false will not make it false if it was previously true)
	 */
	public void setBlockRenderLayerUsedWithOrOpperation(final BlockRenderLayer blockRenderLayer, final boolean used) {
		this.getUsedBlockRenderLayers()[blockRenderLayer.ordinal()] |= used;
	}

	/**
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 * @return the {@link BufferBuilder} for the {@link BlockRenderLayer}
	 */
	public BufferBuilder startOrContinueLayer(final BlockRenderLayer blockRenderLayer) {
		final BufferBuilder bufferbuilder = this.getBufferBuilderForBlockRenderLayer(blockRenderLayer);

		if (!this.getCompiledChunk().isLayerStarted(blockRenderLayer)) {
			this.getCompiledChunk().setLayerStarted(blockRenderLayer);
			preRenderBlocks(bufferbuilder, this.getRenderChunkPosition());
		}

		return bufferbuilder;
	}

}
