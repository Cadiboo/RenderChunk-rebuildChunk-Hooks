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
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.ChunkCache;

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int FLAGS = ClassWriter.COMPUTE_MAXS;

	public static boolean isDeobfuscated = false;

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (!name.equals("net.minecraft.client.renderer.chunk.RenderChunk") && !name.equals("cws")) {
			return basicClass;
		}

		this.isDeobfuscated = name.equals("net.minecraft.client.renderer.chunk.RenderChunk");

		LogManager.getLogger().info("redirecting minecraft.client.renderer.chunk.RenderChunk#rebuildChunk to cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks#rebuildChunk");

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

	public class RebuildChunkHooksClassVisitor extends ClassVisitor {

		public final Type	REBUILD_CHUNK_TYPE			= Type.getMethodType(Type.VOID_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.FLOAT_TYPE, Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)));
		public final String	REBUILD_CHUNK_DESCRIPTOR	= this.REBUILD_CHUNK_TYPE.getDescriptor();

		public RebuildChunkHooksClassVisitor(final ClassVisitor classVisitor) {
			super(Opcodes.ASM5, classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {

			final MethodVisitor originalVisitor = super.visitMethod(access, name, desc, signature, exceptions);

			if (!name.equals("rebuildChunk") && !name.equals("func_178581_b")) {
				return originalVisitor;
			}

			return new RebuildChunkHooksMethodVisitor(originalVisitor);

		}

	}

	public static class RebuildChunkHooksMethodVisitor extends MethodVisitor {

		public final Type	VIS_GRAPH_TYPE				= Type.getObjectType(Type.getInternalName(VisGraph.class));
		public final String	VIS_GRAPH_INTERNAL_NAME		= Type.getInternalName(VisGraph.class);
		public final String	HOOKS_CLASS_INTERNAL_NAME	= Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);
		// cant reference renderChunk because the class loader is still loading it at this time
		public final String	RENDER_CHUNK_INTERNAL_NAME		= RenderChunkRebuildChunkHooksRenderChunkClassTransformer.isDeobfuscated ? "net/minecraft/client/renderer/chunk/RenderChunk" : "bxp";
		public final String	RENDER_GLOBAL_INTERNAL_NAME		= Type.getInternalName(RenderGlobal.class);
		public final String	CHUNK_CACHE_INTERNAL_NAME		= Type.getInternalName(ChunkCache.class);
		public final String	MUTABLE_BLOCK_POS_INTERNAL_NAME	= Type.getInternalName(MutableBlockPos.class);

		public final String ON_REBUILD_CHUNK_EVENT_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME), Type.getObjectType(this.CHUNK_CACHE_INTERNAL_NAME), Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)), Type.getObjectType(Type.getInternalName(CompiledChunk.class)), Type.getObjectType(this.MUTABLE_BLOCK_POS_INTERNAL_NAME));

		public static final String	COMPILED_CHUNK_CLASS	= "net/minecraft/client/renderer/chunk/CompiledChunk";
		public static final String	RENDER_CHUNK_CLASS		= "net/minecraft/client/renderer/chunk/RenderChunk";
		private static final String	RENDER_GLOBAL_CLASS		= "net/minecraft/client/renderer/RenderGlobal";
		private static final String	CLIENT_MINECRAFT_CLASS	= "net/minecraft/client/Minecraft";

		private static final String	HOOKS_CLASS			= "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks";
		public static final String	BLOCKS_EVENT_CLASS	= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent";
		public static final String	BLOCK_EVENT			= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent";

		private static final boolean IS_DEOBF = isDeobfuscated;

		private static final String	FIELD_POSITION_NAME			= IS_DEOBF ? "position" : "field_178586_f";
		private static final String	FIELD_RENDER_GLOBAL_NAME	= IS_DEOBF ? "renderGlobal" : "field_178589_e";
		private static final String	FIELD_WORLD_VIEW_NAME		= IS_DEOBF ? "worldView" : "field_189564_r";
		private static final String	FIELD_lockCompileTask		= IS_DEOBF ? "lockCompileTask" : "field_178587_g";
		private static final String	FIELD_setTileEntities		= IS_DEOBF ? "setTileEntities" : "field_181056_j";

		private static final String	STATIC_FIELD_renderChunksUpdated	= IS_DEOBF ? "renderChunksUpdated" : "field_178592_a";
		private static final String	STATIC_FIELD_TERD_instance			= IS_DEOBF ? "instance" : "field_147556_a";

		// methods
		private static final String	RENDER_CHUNK_preRenderBlocks							= IS_DEOBF ? "preRenderBlocks" : "func_178573_a";
		private static final String	RENDER_CHUNK_postRenderBlocks							= IS_DEOBF ? "postRenderBlocks" : "func_178584_a";
		private static final String	ChunkCompileTaskGenerator_getLock						= IS_DEOBF ? "getLock" : "func_178540_f";
		private static final String	BLOCKPOS_add_III										= IS_DEOBF ? "add" : "func_177982_a";
		private static final String	ChunkCompileTaskGenerator_getStatus						= IS_DEOBF ? "getStatus" : "func_178546_a";
		private static final String	ChunkCompileTaskGenerator_setCompiledChunk				= IS_DEOBF ? "setCompiledChunk" : "func_178543_a";
		private static final String	CHUNK_CACHE_isEmpty										= IS_DEOBF ? "isEmpty" : "func_72806_N";
		private static final String	MINECRAFT_getBlockRendererDispatcher					= IS_DEOBF ? "getBlockRendererDispatcher" : "func_175602_ab";
		private static final String	CHUNK_CACHE_getBlockState								= IS_DEOBF ? "getBlockState" : "func_180495_p";					// uhh... Forge Bot says no info for 1.12, using 1.13 name
		private static final String	BLOCK_hasTileEntity_IBlockState							= "hasTileEntity";												// forge added method
		private static final String	VIS_GRAPH_setOpaqueCube									= IS_DEOBF ? "setOpaqueCube" : "func_178606_a";
		private static final String	CHUNK_CACHE_getTileEntity								= IS_DEOBF ? "getTileEntity" : "func_190300_a";
		private static final String	TileEntityRendererDispatcher_getRenderer_TE_TESR		= IS_DEOBF ? "getRenderer" : "func_147547_b";
		private static final String	TileEntitySpecialRenderer_isGlobalRenderer				= "isGlobalRenderer";											// uhh... Forge Bot says no info for the entire TileEntitySpecialRenderer class
		private static final String	COMPILED_CHUNK_addTileEntity							= IS_DEOBF ? "addTileEntity" : "func_178490_a";
		private static final String	BLOCK_canRenderInLayer									= "canRenderInLayer";											// forge added method
		private static final String	BLOCK_getDefaultState									= IS_DEOBF ? "getDefaultState" : "func_176223_P";
		private static final String	ChunkCompileTaskGenerator_getRegionRenderCacheBuilder	= IS_DEOBF ? "getRegionRenderCacheBuilder" : "func_178545_d";
		private static final String	RegionRenderCacheBuilder_getWorldRendererByLayerId		= IS_DEOBF ? "getWorldRendererByLayerId" : "func_179039_a";
		private static final String	COMPILED_CHUNK_isLayerStarted							= IS_DEOBF ? "isLayerStarted" : "func_178492_d";
		private static final String	COMPILED_CHUNK_setLayerStarted							= IS_DEOBF ? "setLayerStarted" : "func_178493_c";
		private static final String	COMPILED_CHUNK_setLayerUsed								= IS_DEOBF ? "setLayerUsed" : "func_178486_a";
		private static final String	BlockRendererDispatcher_renderBlock						= IS_DEOBF ? "renderBlock" : "func_175018_a";
		private static final String	RegionRenderCacheBuilder_getWorldRendererByLayer		= IS_DEOBF ? "getWorldRendererByLayer" : "func_179038_a";
		private static final String	VIS_GRAPH_computeVisibility								= IS_DEOBF ? "computeVisibility" : "func_178607_a";
		private static final String	COMPILED_CHUNK_setVisibility							= IS_DEOBF ? "setVisibility" : "func_178488_a";
		private static final String	RENDER_GLOBAL_updateTileEntities						= IS_DEOBF ? "updateTileEntities" : "func_181023_a";

		// INVOKESTATIC
		private static final String	BlockPos_getAllInBoxMutable_BP_BP_Iterable	= IS_DEOBF ? "getAllInBoxMutable" : "";
		private static final String	Minecraft_getMinecraft						= IS_DEOBF ? "getMinecraft" : "func_71410_x";

		// INVOKEINTERFACE
		private static final String	IBlockState_getBlock		= IS_DEOBF ? "getBlock" : "func_177230_c";
		private static final String	IBlockState_isOpaqueCube	= IS_DEOBF ? "isOpaqueCube" : "func_185914_p";	// from IBlockProperties
		private static final String	IBlockState_getRenderType	= IS_DEOBF ? "getRenderType" : "func_185901_i";	// from IBlockProperties

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
		}

	}

}
