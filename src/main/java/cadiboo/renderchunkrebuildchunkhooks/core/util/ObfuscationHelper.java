package cadiboo.renderchunkrebuildchunkhooks.core.util;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantLock;

import static cadiboo.renderchunkrebuildchunkhooks.core.RenderChunkRebuildChunkHooksLoadingPlugin1_12_2.OBFUSCATION_LEVEL;
import static cadiboo.renderchunkrebuildchunkhooks.core.util.ObfuscationHelper.ObfuscationClass.*;
import static org.objectweb.asm.Type.*;

public class ObfuscationHelper {

	public enum ObfuscationLevel {

		DEOBFUSCATED,
		SRG,
		OBFUSCATED;

	}

	public enum ObfuscationClass {

		//um, MCPBot says that RenderChunk is bxp (on 1.12 mappings), but when I run Minecraft (on 1.12.2) it says that RenderChunk is bxr. Using Minecraft 1.12.2 obfname
		RENDER_CHUNK("net/minecraft/client/renderer/chunk/RenderChunk", "net/minecraft/client/renderer/chunk/RenderChunk", "bxr"),
		CHUNK_COMPILE_TASK_GENERATOR("net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "bxl"),
		VIS_GRAPH("net/minecraft/client/renderer/chunk/VisGraph", "net/minecraft/client/renderer/chunk/VisGraph", "bxs"),
		RENDER_GLOBAL("net/minecraft/client/renderer/RenderGlobal", "net/minecraft/client/renderer/RenderGlobal", "buw"),
		CHUNK_CACHE("net/minecraft/world/ChunkCache", "net/minecraft/world/ChunkCache", "anb"),
		BLOCK_POS("net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "et"),
		MUTABLE_BLOCK_POS(BLOCK_POS.deobfuscatedName + "$MutableBlockPos", BLOCK_POS.srgName + "$MutableBlockPos", BLOCK_POS.obfuscatedName + "$a"),
		COMPILED_CHUNK("net/minecraft/client/renderer/chunk/CompiledChunk", "net/minecraft/client/renderer/chunk/CompiledChunk", "bxm"),
		BLOCK_RENDERER_DISPATCHER("net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/client/renderer/BlockRendererDispatcher", "bvk"),
		I_BLOCK_STATE("net/minecraft/block/state/IBlockState", "net/minecraft/block/state/IBlockState", "awr"),
		BLOCK_RENDER_LAYER("net/minecraft/util/BlockRenderLayer", "net/minecraft/util/BlockRenderLayer", "amk"),
		I_BLOCK_ACCESS("net/minecraft/world/IBlockAccess", "net/minecraft/world/IBlockAccess", "amw"),
		BUFFER_BUILDER("net/minecraft/client/renderer/BufferBuilder", "net/minecraft/client/renderer/BufferBuilder", "bui"),
		TILE_ENTITY_RENDERER_DISPATCHER("net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "bwv"),
		BLOCK("net/minecraft/block/Block", "net/minecraft/block/Block", "aou");

		private final String deobfuscatedName;
		private final String srgName;
		private final String obfuscatedName;

		ObfuscationClass(String deobfuscatedName, String srgName, String obfuscatedName) {

			this.deobfuscatedName = deobfuscatedName;
			this.srgName = srgName;
			this.obfuscatedName = obfuscatedName;

		}

		/**
		 * gets the internal name for the ObfuscationClass based on the current environment
		 *
		 * @return the correct internal name for the current environment
		 */
		public String getInternalName() {

			switch (OBFUSCATION_LEVEL) {
				case DEOBFUSCATED:
					return this.deobfuscatedName;
				default: //1.12.2
				case SRG:
					return this.srgName;
				// default: //1.13
				case OBFUSCATED:
					return this.obfuscatedName;
			}

		}

		/**
		 * gets the name for the ObfuscationClass based on the current environment
		 *
		 * @return the correct name for the current environment
		 */
		public String getClassName() {

			return Type.getObjectType(this.getInternalName()).getClassName();

		}
	}

	public enum ObfuscationField {

		// instance fields
		RENDER_CHUNK_POSITION("position", "field_178586_f", "o", MUTABLE_BLOCK_POS),
		RENDER_CHUNK_RENDER_GLOBAL("renderGlobal", "field_178589_e", "e", RENDER_GLOBAL),
		RENDER_CHUNK_WORLD_VIEW("worldView", "field_189564_r", "r", CHUNK_CACHE),
		RENDER_CHUNK_LOCK_COMPILE_TASK("lockCompileTask", "field_178587_g", "f", Type.getObjectType(Type.getInternalName(ReentrantLock.class))),
		RENDER_CHUNK_SET_TILE_ENTITIES("setTileEntities", "field_181056_j", "i", Type.getObjectType(Type.getInternalName(HashSet.class))),

		// static fields
		RENDER_CHUNK_RENDER_CHUNKS_UPDATED("renderChunksUpdated", "field_178592_a", "a", INT_TYPE),
		TILE_ENTITY_RENDERER_DISPATCHER_INSTANCE("instance", "field_147556_a", "a", TILE_ENTITY_RENDERER_DISPATCHER),

		;

		private final String deobfuscatedName;
		private final String srgName;
		private final String obfuscatedName;
		private final Object type;

		ObfuscationField(String deobfuscatedName, String srgName, String obfuscatedName, Object type) {

			this.deobfuscatedName = deobfuscatedName;
			this.srgName = srgName;
			this.obfuscatedName = obfuscatedName;
			this.type = type;
		}

		/**
		 * gets the name based on the current environment
		 *
		 * @return the correct name for the current environment
		 */
		public String getName() {

			switch (OBFUSCATION_LEVEL) {
				case DEOBFUSCATED:
					return this.deobfuscatedName;
				default: //1.12.2
				case SRG:
					return this.srgName;
				// default: //1.13
				case OBFUSCATED:
					return this.obfuscatedName;
			}

		}

		public String getDescriptor() {

			final Type type;
			if (this.type instanceof ObfuscationClass) {
				type = Type.getObjectType(((ObfuscationClass) this.type).getInternalName());
			} else if (this.type instanceof Type) {
				type = (Type) this.type;
			} else {
				throw new RuntimeException("Illegal Field Type!");
			}

			return type.getDescriptor();

		}

	}

	//		// methods
	//		String RENDER_CHUNK_preRenderBlocks                          = DEOBFUSCATED ? "preRenderBlocks" : "func_178573_a";                // a
	//		String RENDER_CHUNK_postRenderBlocks                         = DEOBFUSCATED ? "postRenderBlocks" : "func_178584_a";                // a
	//		String ChunkCompileTaskGenerator_getLock                     = DEOBFUSCATED ? "getLock" : "func_178540_f";
	//		String BLOCKPOS_add_III                                      = DEOBFUSCATED ? "add" : "func_177982_a";
	//		String ChunkCompileTaskGenerator_getStatus                   = DEOBFUSCATED ? "getStatus" : "func_178546_a";
	//		String ChunkCompileTaskGenerator_setCompiledChunk            = DEOBFUSCATED ? "setCompiledChunk" : "func_178543_a";
	//		String CHUNK_CACHE_isEmpty                                   = DEOBFUSCATED ? "isEmpty" : "func_72806_N";
	//		String MINECRAFT_getBlockRendererDispatcher                  = DEOBFUSCATED ? "getBlockRendererDispatcher" : "func_175602_ab";
	//		String CHUNK_CACHE_getBlockState                             = DEOBFUSCATED ? "getBlockState" : "func_180495_p";                    // uhh... Forge Bot says no info for 1.12, using 1.13 name
	//		String BLOCK_hasTileEntity_IBlockState                       = "hasTileEntity";                                                    // forge added method
	//		String VIS_GRAPH_setOpaqueCube                               = DEOBFUSCATED ? "setOpaqueCube" : "func_178606_a";
	//		String CHUNK_CACHE_getTileEntity                             = DEOBFUSCATED ? "getTileEntity" : "func_190300_a";
	//		String TileEntityRendererDispatcher_getRenderer_TE_TESR      = DEOBFUSCATED ? "getRenderer" : "func_147547_b";
	//		String TileEntitySpecialRenderer_isGlobalRenderer            = "isGlobalRenderer";                                                // uhh... Forge Bot says no info for the entire TileEntitySpecialRenderer class
	//		String COMPILED_CHUNK_addTileEntity                          = DEOBFUSCATED ? "addTileEntity" : "func_178490_a";
	//		String BLOCK_canRenderInLayer                                = "canRenderInLayer";                                                // forge added method
	//		String BLOCK_getDefaultState                                 = DEOBFUSCATED ? "getDefaultState" : "func_176223_P";
	//		String ChunkCompileTaskGenerator_getRegionRenderCacheBuilder = DEOBFUSCATED ? "getRegionRenderCacheBuilder" : "func_178545_d";
	//		String RegionRenderCacheBuilder_getWorldRendererByLayerId    = DEOBFUSCATED ? "getWorldRendererByLayerId" : "func_179039_a";
	//		String COMPILED_CHUNK_isLayerStarted                         = DEOBFUSCATED ? "isLayerStarted" : "func_178492_d";
	//		String COMPILED_CHUNK_setLayerStarted                        = DEOBFUSCATED ? "setLayerStarted" : "func_178493_c";
	//		String COMPILED_CHUNK_setLayerUsed                           = DEOBFUSCATED ? "setLayerUsed" : "func_178486_a";
	//		String BlockRendererDispatcher_renderBlock                   = DEOBFUSCATED ? "renderBlock" : "func_175018_a";
	//		String RegionRenderCacheBuilder_getWorldRendererByLayer      = DEOBFUSCATED ? "getWorldRendererByLayer" : "func_179038_a";
	//		String VIS_GRAPH_computeVisibility                           = DEOBFUSCATED ? "computeVisibility" : "func_178607_a";
	//		String COMPILED_CHUNK_setVisibility                          = DEOBFUSCATED ? "setVisibility" : "func_178488_a";
	//		String RENDER_GLOBAL_updateTileEntities                      = DEOBFUSCATED ? "updateTileEntities" : "func_181023_a";
	//
	//		// INVOKESTATIC
	//		String BlockPos_getAllInBoxMutable_BP_BP_Iterable = DEOBFUSCATED ? "getAllInBoxMutable" : "";
	//		String Minecraft_getMinecraft                     = DEOBFUSCATED ? "getMinecraft" : "func_71410_x";
	//
	//		// INVOKEINTERFACE
	//		String IBlockState_getBlock      = DEOBFUSCATED ? "getBlock" : "func_177230_c";
	//		String IBlockState_isOpaqueCube  = DEOBFUSCATED ? "isOpaqueCube" : "func_185914_p";    // from IBlockProperties
	//		String IBlockState_getRenderType = DEOBFUSCATED ? "getRenderType" : "func_185901_i";    // from IBlockProperties

	public enum ObfuscationMethod {

		RENDER_CHUNK_REBUILD_CHUNK(RENDER_CHUNK, "rebuildChunk", "func_178581_b", "b", VOID_TYPE, new Object[] {
			FLOAT_TYPE, FLOAT_TYPE, FLOAT_TYPE, CHUNK_COMPILE_TASK_GENERATOR
		}, false),
		RENDER_CHUNK_PRE_RENDER_BLOCKS(RENDER_CHUNK, "preRenderBlocks", "func_178573_a", "a", VOID_TYPE, new Object[] {
			BUFFER_BUILDER, BLOCK_POS
		}, false),
		RENDER_CHUNK_POST_RENDER_BLOCKS(RENDER_CHUNK, "postRenderBlocks", "func_178584_a", "a", VOID_TYPE, new Object[] {
			BLOCK_RENDER_LAYER, FLOAT_TYPE, FLOAT_TYPE, FLOAT_TYPE, BUFFER_BUILDER, COMPILED_CHUNK
		}, false),
		BLOCK_RENDERER_DISPATCHER_RENDER_BLOCK(BLOCK_RENDERER_DISPATCHER, "renderBlock", "func_175018_a", "a", BOOLEAN_TYPE, new Object[] {
			I_BLOCK_STATE, BLOCK_POS, I_BLOCK_ACCESS, BUFFER_BUILDER
		}, false),
		// forge added method
		BLOCK_CAN_RENDER_IN_LAYER(BLOCK, "canRenderInLayer", "canRenderInLayer", "canRenderInLayer", BOOLEAN_TYPE, new Object[] {
			I_BLOCK_STATE, BLOCK_RENDER_LAYER
		}, false)

		//		CHUNK_COMPILE_TASK_GENERATOR_GET_LOCK("add", "func_177982_a", "f"),
		//		BLOCKPOS_ADD_int("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		//		NAME("deobf", "srg", "obf"),
		;

		private final ObfuscationClass owner;
		private final String           deobfuscatedName;
		private final String           srgName;
		private final String           obfuscatedName;
		private final Object           returnType;
		private final Object[]         params;
		private final boolean          isInterface;

		ObfuscationMethod(ObfuscationClass owner, String deobfuscatedName, String srgName, String obfuscatedName, Object returnType, Object[] params, boolean isInterface) {

			this.owner = owner;
			this.deobfuscatedName = deobfuscatedName;
			this.srgName = srgName;
			this.obfuscatedName = obfuscatedName;
			this.returnType = returnType;
			this.params = params;
			this.isInterface = isInterface;

		}

		public ObfuscationClass getOwner() {

			return owner;
		}

		/**
		 * gets the name based on the current environment
		 *
		 * @return the correct name for the current environment
		 */
		public String getName() {

			switch (OBFUSCATION_LEVEL) {
				case DEOBFUSCATED:
					return this.deobfuscatedName;
				default: //1.12.2
				case SRG:
					return this.srgName;
				// default: //1.13
				case OBFUSCATED:
					return this.obfuscatedName;
			}

		}

		public String getDescriptor() {

			final Type returnType;
			if (this.returnType instanceof ObfuscationClass) {
				returnType = Type.getObjectType(((ObfuscationClass) this.returnType).getInternalName());
			} else if (this.returnType instanceof Type) {
				returnType = (Type) this.returnType;
			} else {
				throw new RuntimeException("Illegal Return Type!");
			}

			final ArrayList<Type> params = new ArrayList<>();

			for (Object paramObject : this.params) {

				final Type param;
				if (paramObject instanceof ObfuscationClass) {
					param = Type.getObjectType(((ObfuscationClass) paramObject).getInternalName());
				} else if (paramObject instanceof Type) {
					param = (Type) paramObject;
				} else {
					throw new RuntimeException("Illegal Parameter!");
				}

				params.add(param);

			}

			return Type.getMethodDescriptor(returnType, params.toArray(new Type[0]));

		}

		public Type getType() {

			return Type.getMethodType(this.getDescriptor());
		}

		public boolean isInterface() {

			return isInterface;
		}
	}

}
