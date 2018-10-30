package cadiboo.renderchunkrebuildchunkhooks;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.google.common.collect.ImmutableList;

import cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;

/**
 * @author Cadiboo
 */
public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int FLAGS = ClassWriter.COMPUTE_MAXS;

	public static final boolean DEOBFUSCATED = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

	@Override
	public byte[] transform(final String unTransformedName, final String transformedName, final byte[] basicClass) {
		if (!transformedName.equals("net.minecraft.client.renderer.chunk.RenderChunk")) {
			return basicClass;
		}

		LogManager.getLogger().info("Preparing to redirect minecraft.client.renderer.chunk.RenderChunk#rebuildChunk to cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks#rebuildChunk");

		final ClassReader classReader = new ClassReader(basicClass);
		final ClassWriter classWriter = new ClassWriter(classReader, FLAGS);

		final ClassVisitor classVisitor = new RebuildChunkHooksClassVisitor(classWriter);

		LogManager.getLogger().info("Attempting to redirect...");

		try {
			classReader.accept(classVisitor, 0);

			LogManager.getLogger().info("Redirected sucessfully!");
			return classWriter.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("FAILED to redirect!!! Discarding changes.");
			return basicClass;
		}
	}

	public static class RebuildChunkHooksClassVisitor extends ClassVisitor {

		public static final Type	REBUILD_CHUNK_TYPE			= Type.getMethodType(Type.VOID_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)));
		public static final String	REBUILD_CHUNK_DESCRIPTOR	= REBUILD_CHUNK_TYPE.getDescriptor();

		public RebuildChunkHooksClassVisitor(final ClassVisitor classVisitor) {
			super(Opcodes.ASM5, classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {

			final MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

			LogManager.getLogger().info(REBUILD_CHUNK_TYPE);
			LogManager.getLogger().info(REBUILD_CHUNK_DESCRIPTOR);
			LogManager.getLogger().info(name);
			LogManager.getLogger().info(desc);

			if (!desc.equals(REBUILD_CHUNK_DESCRIPTOR)) {
				return originalVisitor;
			}

			LogManager.getLogger().info("Attempting to overwrite method " + name + " (rebuildChunk) with descriptor " + desc + "...");

			return new RebuildChunkHooksMethodVisitor(originalVisitor);

		}

	}

	public static class RebuildChunkHooksMethodVisitor extends MethodVisitor {

		public static final Type	VIS_GRAPH_TYPE				= Type.getObjectType(Type.getInternalName(VisGraph.class));
		public static final String	VIS_GRAPH_INTERNAL_NAME		= Type.getInternalName(VisGraph.class);
		public static final String	HOOKS_CLASS_INTERNAL_NAME	= Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);
		// can't reference renderChunk because the class loader is still loading it at this time
		public static final String	RENDER_CHUNK_INTERNAL_NAME		= DEOBFUSCATED ? "net/minecraft/client/renderer/chunk/RenderChunk" : "bxp";
		public static final String	RENDER_GLOBAL_INTERNAL_NAME		= Type.getInternalName(RenderGlobal.class);
		public static final String	CHUNK_CACHE_INTERNAL_NAME		= Type.getInternalName(ChunkCache.class);
		public static final String	MUTABLE_BLOCK_POS_INTERNAL_NAME	= Type.getInternalName(MutableBlockPos.class);

		public static final String ON_REBUILD_CHUNK_EVENT_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(RENDER_GLOBAL_INTERNAL_NAME), Type.getObjectType(CHUNK_CACHE_INTERNAL_NAME), Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)), Type.getObjectType(Type.getInternalName(CompiledChunk.class)), Type.getObjectType(MUTABLE_BLOCK_POS_INTERNAL_NAME));

		public static final String	COMPILED_CHUNK_CLASS	= "net/minecraft/client/renderer/chunk/CompiledChunk";
		public static final String	RENDER_CHUNK_CLASS		= "net/minecraft/client/renderer/chunk/RenderChunk";
		public static final String	RENDER_GLOBAL_CLASS		= "net/minecraft/client/renderer/RenderGlobal";
		public static final String	CLIENT_MINECRAFT_CLASS	= "net/minecraft/client/Minecraft";

		public static final String	HOOKS_CLASS			= "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks";
		public static final String	BLOCKS_EVENT_CLASS	= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent";
		public static final String	BLOCK_EVENT			= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent";

		public static final String	FIELD_POSITION_NAME			= DEOBFUSCATED ? "position" : "field_178586_f";
		public static final String	FIELD_RENDER_GLOBAL_NAME	= DEOBFUSCATED ? "renderGlobal" : "field_178589_e";
		public static final String	FIELD_WORLD_VIEW_NAME		= DEOBFUSCATED ? "worldView" : "field_189564_r";
		public static final String	FIELD_lockCompileTask		= DEOBFUSCATED ? "lockCompileTask" : "field_178587_g";
		public static final String	FIELD_setTileEntities		= DEOBFUSCATED ? "setTileEntities" : "field_181056_j";

		public static final String	STATIC_FIELD_renderChunksUpdated	= DEOBFUSCATED ? "renderChunksUpdated" : "field_178592_a";
		public static final String	STATIC_FIELD_TERD_instance			= DEOBFUSCATED ? "instance" : "field_147556_a";

		// methods
		public static final String	RENDER_CHUNK_preRenderBlocks							= DEOBFUSCATED ? "preRenderBlocks" : "func_178573_a";
		public static final String	RENDER_CHUNK_postRenderBlocks							= DEOBFUSCATED ? "postRenderBlocks" : "func_178584_a";
		public static final String	ChunkCompileTaskGenerator_getLock						= DEOBFUSCATED ? "getLock" : "func_178540_f";
		public static final String	BLOCKPOS_add_III										= DEOBFUSCATED ? "add" : "func_177982_a";
		public static final String	ChunkCompileTaskGenerator_getStatus						= DEOBFUSCATED ? "getStatus" : "func_178546_a";
		public static final String	ChunkCompileTaskGenerator_setCompiledChunk				= DEOBFUSCATED ? "setCompiledChunk" : "func_178543_a";
		public static final String	CHUNK_CACHE_isEmpty										= DEOBFUSCATED ? "isEmpty" : "func_72806_N";
		public static final String	MINECRAFT_getBlockRendererDispatcher					= DEOBFUSCATED ? "getBlockRendererDispatcher" : "func_175602_ab";
		public static final String	CHUNK_CACHE_getBlockState								= DEOBFUSCATED ? "getBlockState" : "func_180495_p";					// uhh... Forge Bot says no info for 1.12, using 1.13 name
		public static final String	BLOCK_hasTileEntity_IBlockState							= "hasTileEntity";													// forge added method
		public static final String	VIS_GRAPH_setOpaqueCube									= DEOBFUSCATED ? "setOpaqueCube" : "func_178606_a";
		public static final String	CHUNK_CACHE_getTileEntity								= DEOBFUSCATED ? "getTileEntity" : "func_190300_a";
		public static final String	TileEntityRendererDispatcher_getRenderer_TE_TESR		= DEOBFUSCATED ? "getRenderer" : "func_147547_b";
		public static final String	TileEntitySpecialRenderer_isGlobalRenderer				= "isGlobalRenderer";												// uhh... Forge Bot says no info for the entire TileEntitySpecialRenderer class
		public static final String	COMPILED_CHUNK_addTileEntity							= DEOBFUSCATED ? "addTileEntity" : "func_178490_a";
		public static final String	BLOCK_canRenderInLayer									= "canRenderInLayer";												// forge added method
		public static final String	BLOCK_getDefaultState									= DEOBFUSCATED ? "getDefaultState" : "func_176223_P";
		public static final String	ChunkCompileTaskGenerator_getRegionRenderCacheBuilder	= DEOBFUSCATED ? "getRegionRenderCacheBuilder" : "func_178545_d";
		public static final String	RegionRenderCacheBuilder_getWorldRendererByLayerId		= DEOBFUSCATED ? "getWorldRendererByLayerId" : "func_179039_a";
		public static final String	COMPILED_CHUNK_isLayerStarted							= DEOBFUSCATED ? "isLayerStarted" : "func_178492_d";
		public static final String	COMPILED_CHUNK_setLayerStarted							= DEOBFUSCATED ? "setLayerStarted" : "func_178493_c";
		public static final String	COMPILED_CHUNK_setLayerUsed								= DEOBFUSCATED ? "setLayerUsed" : "func_178486_a";
		public static final String	BlockRendererDispatcher_renderBlock						= DEOBFUSCATED ? "renderBlock" : "func_175018_a";
		public static final String	RegionRenderCacheBuilder_getWorldRendererByLayer		= DEOBFUSCATED ? "getWorldRendererByLayer" : "func_179038_a";
		public static final String	VIS_GRAPH_computeVisibility								= DEOBFUSCATED ? "computeVisibility" : "func_178607_a";
		public static final String	COMPILED_CHUNK_setVisibility							= DEOBFUSCATED ? "setVisibility" : "func_178488_a";
		public static final String	RENDER_GLOBAL_updateTileEntities						= DEOBFUSCATED ? "updateTileEntities" : "func_181023_a";

		// INVOKESTATIC
		public static final String	BlockPos_getAllInBoxMutable_BP_BP_Iterable	= DEOBFUSCATED ? "getAllInBoxMutable" : "";
		public static final String	Minecraft_getMinecraft						= DEOBFUSCATED ? "getMinecraft" : "func_71410_x";

		// INVOKEINTERFACE
		public static final String	IBlockState_getBlock		= DEOBFUSCATED ? "getBlock" : "func_177230_c";
		public static final String	IBlockState_isOpaqueCube	= DEOBFUSCATED ? "isOpaqueCube" : "func_185914_p";	// from IBlockProperties
		public static final String	IBlockState_getRenderType	= DEOBFUSCATED ? "getRenderType" : "func_185901_i";	// from IBlockProperties

		public RebuildChunkHooksMethodVisitor(final MethodVisitor mv) {
			super(Opcodes.ASM5, mv);
		}

//		public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator)
//	    {
//	        CompiledChunk compiledchunk = new CompiledChunk();
//	        int i = 1;
//	        BlockPos blockpos = this.position;
//	        BlockPos blockpos1 = blockpos.add(15, 15, 15);
//	        generator.getLock().lock();

//		public void rebuildChunk(final float x, final float y, final float z, final ChunkCompileTaskGenerator generator)
//	    {
//>	    	renderChunksUpdated = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.rebuildChunk(x,y,z ,generator,  this.position,  this.worldView,  this.renderGlobal, renderChunksUpdated, this.lockCompileTask, this.setTileEntities);
//>	    	if (true) return; //Everything below this is dead code and the compiler doesn't generate instructions for it
//	        CompiledChunk compiledchunk = new CompiledChunk();
//	        int i = 1;
//	        BlockPos blockpos = this.position;
//	        BlockPos blockpos1 = blockpos.add(15, 15, 15);
//	        generator.getLock().lock();

//	    TRYCATCHBLOCK L0 L1 L2
//	    TRYCATCHBLOCK L3 L2 L2
//	    TRYCATCHBLOCK L4 L5 L5
//	   L6
//	    LINENUMBER 127 L6
//	    NEW net/minecraft/client/renderer/chunk/CompiledChunk
//	    DUP
//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/CompiledChunk.<init>()V
//	    ASTORE 5
//	   L7
//	    LINENUMBER 128 L7
//	    ICONST_1
//	    ISTORE 6
//	   L8
//	    LINENUMBER 129 L8
//	    ALOAD 0
//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//	    ASTORE 7
//	   L9
//	    LINENUMBER 130 L9
//	    ALOAD 7
//	    BIPUSH 15
//	    BIPUSH 15
//	    BIPUSH 15
//	    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.add(III)Lnet/minecraft/util/math/BlockPos;
//	    ASTORE 8
//	   L10
//	    LINENUMBER 131 L10
//	    ALOAD 4
//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.lock()V

//>		L0
//>	    LINENUMBER 112 L0
//>	    FLOAD 1
//>	    FLOAD 2
//>	    FLOAD 3
//>	    ALOAD 4
//>	    ALOAD 0
//>	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//>	    ALOAD 0
//>	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//>	    ALOAD 0
//>	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//>	    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//>	    ALOAD 0
//>	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
//>	    ALOAD 0
//>	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
//>	    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.rebuildChunk(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/RenderGlobal;ILjava/util/concurrent/locks/ReentrantLock;Ljava/util/Set;)I
//>	    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//>	   L1
//>	    LINENUMBER 113 L1
//>	    RETURN

//		mv.visitCode();
//		Label l0 = new Label();
//		Label l1 = new Label();
//		Label l2 = new Label();
//		mv.visitTryCatchBlock(l0, l1, l2, null);
//		Label l3 = new Label();
//		mv.visitTryCatchBlock(l3, l2, l2, null);
//		Label l4 = new Label();
//		Label l5 = new Label();
//		mv.visitTryCatchBlock(l4, l5, l5, null);
//		Label l6 = new Label();
//		mv.visitLabel(l6);
//		mv.visitLineNumber(127, l6);
//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/CompiledChunk");
//		mv.visitInsn(DUP);
//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "<init>", "()V", false);
//		mv.visitVarInsn(ASTORE, 5);
//		Label l7 = new Label();
//		mv.visitLabel(l7);
//		mv.visitLineNumber(128, l7);
//		mv.visitInsn(ICONST_1);
//		mv.visitVarInsn(ISTORE, 6);
//		Label l8 = new Label();
//		mv.visitLabel(l8);
//		mv.visitLineNumber(129, l8);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//		mv.visitVarInsn(ASTORE, 7);
//		Label l9 = new Label();
//		mv.visitLabel(l9);
//		mv.visitLineNumber(130, l9);
//		mv.visitVarInsn(ALOAD, 7);
//		mv.visitIntInsn(BIPUSH, 15);
//		mv.visitIntInsn(BIPUSH, 15);
//		mv.visitIntInsn(BIPUSH, 15);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", "add", "(III)Lnet/minecraft/util/math/BlockPos;", false);
//		mv.visitVarInsn(ASTORE, 8);
//		Label l10 = new Label();
//		mv.visitLabel(l10);
//		mv.visitLineNumber(131, l10);
//		mv.visitVarInsn(ALOAD, 4);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);

//		mv.visitCode();
//		Label l0 = new Label();
//		mv.visitLabel(l0);
//		mv.visitLineNumber(127, l0);
//		mv.visitVarInsn(FLOAD, 1);
//		mv.visitVarInsn(FLOAD, 2);
//		mv.visitVarInsn(FLOAD, 3);
//		mv.visitVarInsn(ALOAD, 4);
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
//		mv.visitVarInsn(ALOAD, 0);
//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
//		mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "rebuildChunk", "(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/RenderGlobal;ILjava/util/concurrent/locks/ReentrantLock;Ljava/util/Set;)I", false);
//		mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//		Label l1 = new Label();
//		mv.visitLabel(l1);
//		mv.visitLineNumber(128, l1);
//		mv.visitInsn(RETURN);
//		Label l2 = new Label();
//		mv.visitLabel(l2);
//		mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/chunk/RenderChunk;", null, l0, l2, 0);
//		mv.visitLocalVariable("x", "F", null, l0, l2, 1);
//		mv.visitLocalVariable("y", "F", null, l0, l2, 2);
//		mv.visitLocalVariable("z", "F", null, l0, l2, 3);
//		mv.visitLocalVariable("generator", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;", null, l0, l2, 4);
//		mv.visitMaxs(10, 5);
//		mv.visitEnd();

		@Override
		public void visitCode() {
//			super.visitCode();

			LogManager.getLogger().info("Preparing to inject...");

			final int FLOAD = Opcodes.FLOAD;
			final int ALOAD = Opcodes.ALOAD;
			final int GETFIELD = Opcodes.GETFIELD;
			final int GETSTATIC = Opcodes.GETSTATIC;
			final int INVOKESTATIC = Opcodes.INVOKESTATIC;
			final int PUTSTATIC = Opcodes.PUTSTATIC;
			final int RETURN = Opcodes.RETURN;
			final MethodVisitor mv = this.mv;
			// noop the exisiting MethodVisitor to stop the normal method being generated
			this.mv = new MethodVisitor(Opcodes.ASM5) {
			};

			LogManager.getLogger().info("Starting injection.");

			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(127, l0);
			mv.visitVarInsn(FLOAD, 1);
			mv.visitVarInsn(FLOAD, 2);
			mv.visitVarInsn(FLOAD, 3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;");
			mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", STATIC_FIELD_renderChunksUpdated, "I");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", FIELD_setTileEntities, "Ljava/util/Set;");
			mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "rebuildChunk", "(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/RenderGlobal;ILjava/util/concurrent/locks/ReentrantLock;Ljava/util/Set;)I", false);
			mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", STATIC_FIELD_renderChunksUpdated, "I");
			final Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(128, l1);
			mv.visitInsn(RETURN);
			final Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/chunk/RenderChunk;", null, l0, l2, 0);
			mv.visitLocalVariable("x", "F", null, l0, l2, 1);
			mv.visitLocalVariable("y", "F", null, l0, l2, 2);
			mv.visitLocalVariable("z", "F", null, l0, l2, 3);
			mv.visitLocalVariable("generator", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;", null, l0, l2, 4);
			mv.visitMaxs(10, 5);
			mv.visitEnd();
			this.visitEnd();

			LogManager.getLogger().info("Finished injection.");

		}

	}

}
