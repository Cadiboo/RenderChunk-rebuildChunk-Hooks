package cadiboo.renderchunkrebuildchunkhooks.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

@HasResult
public class RebuildChunkBlockRenderInLayerEvent extends Event {

	private final ChunkCache				worldView;
	private final ChunkCompileTaskGenerator	chunkCompileTaskGenerator;
	private final CompiledChunk				compiledChunk;
	private final BlockRendererDispatcher	blockRendererDispatcher;
	private final MutableBlockPos			renderChunkPosition;
	private final VisGraph					visGraph;
	private final IBlockState				blockState;
	private final BlockRenderLayer			blockRenderLayer;

	public RebuildChunkBlockRenderInLayerEvent(final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {
		this.worldView = worldView;
		this.chunkCompileTaskGenerator = chunkCompileTaskGenerator;
		this.compiledChunk = compiledChunk;
		this.blockRendererDispatcher = blockRendererDispatcher;
		this.renderChunkPosition = renderChunkPosition;
		this.visGraph = visGraph;
		this.blockState = blockState;
		this.blockRenderLayer = blockRenderLayer;
	}

	public ChunkCache getWorldView() {
		return this.worldView;
	}

	public ChunkCompileTaskGenerator getChunkCompileTaskGenerator() {
		return this.chunkCompileTaskGenerator;
	}

	public CompiledChunk getCompiledChunk() {
		return this.compiledChunk;
	}

	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return this.blockRendererDispatcher;
	}

	public MutableBlockPos getRenderChunkPosition() {
		return this.renderChunkPosition;
	}

	public VisGraph getVisGraph() {
		return this.visGraph;
	}

	public IBlockState getBlockState() {
		return this.blockState;
	}

	public BlockRenderLayer getBlockRenderLayer() {
		return this.blockRenderLayer;
	}

}
