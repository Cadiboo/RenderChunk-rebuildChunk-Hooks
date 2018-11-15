package cadiboo.renderchunkrebuildchunkhooks.hooks;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashSet;

import cadiboo.renderchunkrebuildchunkhooks.config.ModConfig;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkBlockRenderInLayerEvent;
import cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkPreEvent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooks {

	/**
	 * An accessible reference to the method {@link CompiledChunk#setLayerUsed(BlockRenderLayer)}
	 */
	@SuppressWarnings("javadoc") // remove warning "The method setLayerUsed(BlockRenderLayer) from the type CompiledChunk is not visible"
	public static final Method COMPILED_CHUNK_SET_LAYER_USED = getMethodAndMakeItAccessable();

	/**
	 * An invokable reference to the method {@link CompiledChunk#setLayerUsed(BlockRenderLayer)}
	 *
	 * @see <a href="http://www.minecraftforge.net/forum/topic/66980-1122-access-transformer-problem/?do=findComment&comment=322130">More explanation here</a>
	 */
	@SuppressWarnings("javadoc") // remove warning "The method setLayerUsed(BlockRenderLayer) from the type CompiledChunk is not visible"
	public static final MethodHandle COMPILED_CHUNK_SET_LAYER_USED_HANDLE = getMethodHandle();

	private static Method getMethodAndMakeItAccessable() {
		final Method method = ReflectionHelper.findMethod(CompiledChunk.class, "setLayerUsed", "func_178486_a", BlockRenderLayer.class);
		method.setAccessible(true);
		return method;
	}

	static MethodHandle getMethodHandle() {
		try {
			return MethodHandles.publicLookup().unreflect(COMPILED_CHUNK_SET_LAYER_USED);
		} catch (final IllegalAccessException illegalAccessException) {
			throw new RuntimeException(illegalAccessException);
		}
	}

	/**
	 * @param compiledChunk    the {@link CompiledChunk}
	 * @param blockRenderLayer the {@link BlockRenderLayer}
	 */
	public static void compiledChunk_setLayerUsed(final CompiledChunk compiledChunk, final BlockRenderLayer blockRenderLayer) {
		try {
			COMPILED_CHUNK_SET_LAYER_USED_HANDLE.invokeExact(compiledChunk, blockRenderLayer);
		} catch (final Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	/**
	 * @param renderChunk         the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal        the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param worldView           the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param generator           the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk       the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param x                   the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                   the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                   the translation Z passed in from RenderChunk#rebuildChunk
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 * @return If vanilla rendering should be stopped
	 */
	public static boolean onRebuildChunkPreEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final MutableBlockPos renderChunkPosition, final float x, final float y, final float z) {
		final RebuildChunkPreEvent event = new RebuildChunkPreEvent(renderChunk, renderGlobal, worldView, generator, compiledChunk, renderChunkPosition, x, y, z);

		if (ModConfig.enableRebuildChunkPreEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

	/**
	 * @param renderChunk               the instance of {@link RenderChunk} the event is being fired for
	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk             the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher   the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param visGraph                  the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @param blockPos                  the {@link MutableBlockPos position} of the block being assessed
	 * @param block                     the {@link Block block} being assessed
	 * @param blockState                the {@link IBlockState state} of the block being assessed
	 * @param blockRenderLayer          the {@link BlockRenderLayer} of the block being assessed
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 * @return If the block can render in the layer
	 */
	public static boolean canBlockRenderInLayer(final RenderChunk renderChunk, final ChunkCache worldView, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final MutableBlockPos renderChunkPosition, final VisGraph visGraph, final MutableBlockPos blockPos, final Block block, final IBlockState blockState, final BlockRenderLayer blockRenderLayer) {
		final RebuildChunkBlockRenderInLayerEvent event = new RebuildChunkBlockRenderInLayerEvent(renderChunk, worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, blockPos, blockState, blockRenderLayer);

		if (ModConfig.enableRebuildChunkBlockRenderInLayerEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		if (event.getResult() == Event.Result.ALLOW) {
			return true;
		} else if (event.getResult() == Event.Result.DEFAULT) {
			return block.canRenderInLayer(blockState, blockRenderLayer);
		} else {
			return false;
		}
	}

	/**
	 * @param renderChunk                     the instance of {@link RenderChunk} the event is being fired for
	 * @param renderGlobal                    the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
	 * @param worldView                       the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
	 * @param generator                       the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
	 * @param compiledChunk                   the {@link CompiledChunk} passed in from RenderChunk#rebuildChunk
	 * @param blockRendererDispatcher         the {@link BlockRendererDispatcher} passed in from RenderChunk#rebuildChunk
	 * @param blockState                      the {@link IBlockState state} of the block being rendered
	 * @param blockPos                        the {@link MutableBlockPos position} of the block being rendered
	 * @param bufferBuilder                   the {@link BufferBuilder} for the BlockRenderLayer
	 * @param renderChunkPosition             the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
	 * @param usedBlockRenderLayers           the array of {@link BlockRenderLayer} that are being used
	 * @param blockRenderLayer                the {@link BlockRenderLayer} of the block being rendered
	 * @param x                               the translation X passed in from RenderChunk#rebuildChunk
	 * @param y                               the translation Y passed in from RenderChunk#rebuildChunk
	 * @param z                               the translation Z passed in from RenderChunk#rebuildChunk
	 * @param tileEntitiesWithGlobalRenderers the {@link HashSet} of {@link TileEntity TileEntities} with global renderers passed in from RenderChunk#rebuildChunk
	 * @param visGraph                        the {@link VisGraph} passed in from RenderChunk#rebuildChunk
	 * @return If the block should NOT be rebuilt to the chunk by vanilla
	 * @see cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunk_diff and cadiboo.renderchunkrebuildchunkhooks.core.util.rebuildChunkOptifine_diff
	 */
	public static boolean onRebuildChunkBlockEvent(final RenderChunk renderChunk, final RenderGlobal renderGlobal, final ChunkCache worldView, final ChunkCompileTaskGenerator generator, final CompiledChunk compiledChunk, final BlockRendererDispatcher blockRendererDispatcher, final IBlockState blockState, final MutableBlockPos blockPos, final BufferBuilder bufferBuilder, final MutableBlockPos renderChunkPosition, boolean[] usedBlockRenderLayers, final BlockRenderLayer blockRenderLayer, final float x,
												   final float y, final float z, final HashSet<TileEntity> tileEntitiesWithGlobalRenderers, final VisGraph visGraph) {
		final RebuildChunkBlockEvent event = new RebuildChunkBlockEvent(renderChunk, renderGlobal, worldView, generator, compiledChunk, blockRendererDispatcher, blockState, blockPos, bufferBuilder, renderChunkPosition, usedBlockRenderLayers, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);

		if (ModConfig.enableRebuildChunkBlockEvent) {
			MinecraftForge.EVENT_BUS.post(event);
		}

		return event.isCanceled();
	}

}
