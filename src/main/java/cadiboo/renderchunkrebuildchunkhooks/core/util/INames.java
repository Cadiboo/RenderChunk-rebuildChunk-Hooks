package cadiboo.renderchunkrebuildchunkhooks.core.util;

import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.Type;

//TODO: rewrite this SOON PLEASE
public interface INames {

	//for <1.12 deobfuscated means human readable deobfuscated, if not deobfuscated then SRG names
	//for >1.13 deobfuscated means human readable deobfuscated, if not deobfuscated then RAW MINECRAFT names!! will require a rewrite
	boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	//	boolean DEOBFUSCATED = RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.DEOBFUSCATED;

	String RENDER_CHUNK_TRANSFORMED_NAME = "net.minecraft.client.renderer.chunk.RenderChunk";

	//	String	ChunkCompileTaskGenerator_INTERNAL_NAME	= DEOBFUSCATED ? "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator" : "bxl";
	String ChunkCompileTaskGenerator_INTERNAL_NAME = "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator";
	Type   REBUILD_CHUNK_TYPE                      = Type.getMethodType(Type.VOID_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.getObjectType(ChunkCompileTaskGenerator_INTERNAL_NAME));
	String REBUILD_CHUNK_DESCRIPTOR                = REBUILD_CHUNK_TYPE.getDescriptor();

	//	String VIS_GRAPH_INTERNAL_NAME   = DEOBFUSCATED ? "net/minecraft/client/renderer/chunk/VisGraph" : "bxs";
	String VIS_GRAPH_INTERNAL_NAME   = "net/minecraft/client/renderer/chunk/VisGraph";
	Type   VIS_GRAPH_TYPE            = Type.getObjectType(VIS_GRAPH_INTERNAL_NAME);
	String HOOKS_CLASS_INTERNAL_NAME = Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);
	//crashes if not optifine enabled
	//	String HOOKS_OPTIFINE_CLASS_INTERNAL_NAME = Type.getInternalName(RenderChunkRebuildChunkHooksHooksOptifine.class);

	// can't reference renderChunk because the class loader is still loading it at this time
	//	String	RENDER_CHUNK_INTERNAL_NAME		= DEOBFUSCATED ? "net/minecraft/client/renderer/chunk/RenderChunk" : "bxp";
	//	String	RENDER_GLOBAL_INTERNAL_NAME		= DEOBFUSCATED ? "net/minecraft/client/renderer/RenderGlobal" : "buw";
	//	String	CHUNK_CACHE_INTERNAL_NAME		= DEOBFUSCATED ? "net/minecraft/world/ChunkCache" : "anb";
	//	String	MUTABLE_BLOCK_POS_INTERNAL_NAME	= DEOBFUSCATED ? "net/minecraft/util/math/BlockPos$MutableBlockPos" : "et$a";
	//	String	CompiledChunk_INTERNAL_NAME		= DEOBFUSCATED ? "net/minecraft/client/renderer/chunk/CompiledChunk" : "bxm";
	String RENDER_CHUNK_INTERNAL_NAME            = "net/minecraft/client/renderer/chunk/RenderChunk";
	String RENDER_GLOBAL_INTERNAL_NAME           = "net/minecraft/client/renderer/RenderGlobal";
	String CHUNK_CACHE_INTERNAL_NAME             = "net/minecraft/world/ChunkCache";
	String MUTABLE_BLOCK_POS_INTERNAL_NAME       = "net/minecraft/util/math/BlockPos$MutableBlockPos";
	String BLOCK_POS_INTERNAL_NAME               = "net/minecraft/util/math/BlockPos";
	String CompiledChunk_INTERNAL_NAME           = "net/minecraft/client/renderer/chunk/CompiledChunk";
	String BlockRendererDispatcher_INTERNAL_NAME = "net/minecraft/client/renderer/BlockRendererDispatcher";

	//	String I_BLOCK_STATE_INTERNAL_NAME = DEOBFUSCATED ? "net/minecraft/block/state/IBlockState" : "awr";
	//
	//	String BLOCK_RENDER_LAYER_INTERNAL_NAME = DEOBFUSCATED ? "net/minecraft/util/BlockRenderLayer" : "amk";

	String I_BLOCK_STATE_INTERNAL_NAME = "net/minecraft/block/state/IBlockState";

	String BLOCK_RENDER_LAYER_INTERNAL_NAME = "net/minecraft/util/BlockRenderLayer";

	String I_BLOCK_ACCESS_INTERNAL_NAME = "net/minecraft/world/IBlockAccess";
	String BUFFER_BUILDER_INTERNAL_NAME = "net/minecraft/client/renderer/BufferBuilder";

	String BlockRendererDispatcher_renderBlock_DESCRIPTION = Type.getMethodDescriptor(Type.getObjectType(I_BLOCK_STATE_INTERNAL_NAME), Type.getObjectType(BLOCK_POS_INTERNAL_NAME), Type.getObjectType(I_BLOCK_ACCESS_INTERNAL_NAME), Type.getObjectType(BUFFER_BUILDER_INTERNAL_NAME));

	// (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z
	String Block_canRenderInLayer_DESC = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(I_BLOCK_STATE_INTERNAL_NAME), Type.getObjectType(BLOCK_RENDER_LAYER_INTERNAL_NAME));

	String ON_REBUILD_CHUNK_EVENT_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(RENDER_GLOBAL_INTERNAL_NAME), Type.getObjectType(CHUNK_CACHE_INTERNAL_NAME), Type.getObjectType(ChunkCompileTaskGenerator_INTERNAL_NAME), Type.getObjectType(CompiledChunk_INTERNAL_NAME), Type.getObjectType(MUTABLE_BLOCK_POS_INTERNAL_NAME));

	String FIELD_POSITION_NAME      = DEOBFUSCATED ? "position" : "field_178586_f";            // o
	String FIELD_RENDER_GLOBAL_NAME = DEOBFUSCATED ? "renderGlobal" : "field_178589_e";        // e
	String FIELD_WORLD_VIEW_NAME    = DEOBFUSCATED ? "worldView" : "field_189564_r";        // r
	String FIELD_lockCompileTask    = DEOBFUSCATED ? "lockCompileTask" : "field_178587_g";    // f
	String FIELD_setTileEntities    = DEOBFUSCATED ? "setTileEntities" : "field_181056_j";    // i //why i not j

	String STATIC_FIELD_renderChunksUpdated = DEOBFUSCATED ? "renderChunksUpdated" : "field_178592_a";
	String STATIC_FIELD_TERD_instance       = DEOBFUSCATED ? "instance" : "field_147556_a";

	// methods
	String RENDER_CHUNK_preRenderBlocks                          = DEOBFUSCATED ? "preRenderBlocks" : "func_178573_a";                // a
	String RENDER_CHUNK_postRenderBlocks                         = DEOBFUSCATED ? "postRenderBlocks" : "func_178584_a";                // a
	String ChunkCompileTaskGenerator_getLock                     = DEOBFUSCATED ? "getLock" : "func_178540_f";
	String BLOCKPOS_add_III                                      = DEOBFUSCATED ? "add" : "func_177982_a";
	String ChunkCompileTaskGenerator_getStatus                   = DEOBFUSCATED ? "getStatus" : "func_178546_a";
	String ChunkCompileTaskGenerator_setCompiledChunk            = DEOBFUSCATED ? "setCompiledChunk" : "func_178543_a";
	String CHUNK_CACHE_isEmpty                                   = DEOBFUSCATED ? "isEmpty" : "func_72806_N";
	String MINECRAFT_getBlockRendererDispatcher                  = DEOBFUSCATED ? "getBlockRendererDispatcher" : "func_175602_ab";
	String CHUNK_CACHE_getBlockState                             = DEOBFUSCATED ? "getBlockState" : "func_180495_p";                    // uhh... Forge Bot says no info for 1.12, using 1.13 name
	String BLOCK_hasTileEntity_IBlockState                       = "hasTileEntity";                                                    // forge added method
	String VIS_GRAPH_setOpaqueCube                               = DEOBFUSCATED ? "setOpaqueCube" : "func_178606_a";
	String CHUNK_CACHE_getTileEntity                             = DEOBFUSCATED ? "getTileEntity" : "func_190300_a";
	String TileEntityRendererDispatcher_getRenderer_TE_TESR      = DEOBFUSCATED ? "getRenderer" : "func_147547_b";
	String TileEntitySpecialRenderer_isGlobalRenderer            = "isGlobalRenderer";                                                // uhh... Forge Bot says no info for the entire TileEntitySpecialRenderer class
	String COMPILED_CHUNK_addTileEntity                          = DEOBFUSCATED ? "addTileEntity" : "func_178490_a";
	String BLOCK_canRenderInLayer                                = "canRenderInLayer";                                                // forge added method
	String BLOCK_getDefaultState                                 = DEOBFUSCATED ? "getDefaultState" : "func_176223_P";
	String ChunkCompileTaskGenerator_getRegionRenderCacheBuilder = DEOBFUSCATED ? "getRegionRenderCacheBuilder" : "func_178545_d";
	String RegionRenderCacheBuilder_getWorldRendererByLayerId    = DEOBFUSCATED ? "getWorldRendererByLayerId" : "func_179039_a";
	String COMPILED_CHUNK_isLayerStarted                         = DEOBFUSCATED ? "isLayerStarted" : "func_178492_d";
	String COMPILED_CHUNK_setLayerStarted                        = DEOBFUSCATED ? "setLayerStarted" : "func_178493_c";
	String COMPILED_CHUNK_setLayerUsed                           = DEOBFUSCATED ? "setLayerUsed" : "func_178486_a";
	String BlockRendererDispatcher_renderBlock                   = DEOBFUSCATED ? "renderBlock" : "func_175018_a";
	String RegionRenderCacheBuilder_getWorldRendererByLayer      = DEOBFUSCATED ? "getWorldRendererByLayer" : "func_179038_a";
	String VIS_GRAPH_computeVisibility                           = DEOBFUSCATED ? "computeVisibility" : "func_178607_a";
	String COMPILED_CHUNK_setVisibility                          = DEOBFUSCATED ? "setVisibility" : "func_178488_a";
	String RENDER_GLOBAL_updateTileEntities                      = DEOBFUSCATED ? "updateTileEntities" : "func_181023_a";

	// INVOKESTATIC
	String BlockPos_getAllInBoxMutable_BP_BP_Iterable = DEOBFUSCATED ? "getAllInBoxMutable" : "";
	String Minecraft_getMinecraft                     = DEOBFUSCATED ? "getMinecraft" : "func_71410_x";

	// INVOKEINTERFACE
	String IBlockState_getBlock      = DEOBFUSCATED ? "getBlock" : "func_177230_c";
	String IBlockState_isOpaqueCube  = DEOBFUSCATED ? "isOpaqueCube" : "func_185914_p";    // from IBlockProperties
	String IBlockState_getRenderType = DEOBFUSCATED ? "getRenderType" : "func_185901_i";    // from IBlockProperties

}
