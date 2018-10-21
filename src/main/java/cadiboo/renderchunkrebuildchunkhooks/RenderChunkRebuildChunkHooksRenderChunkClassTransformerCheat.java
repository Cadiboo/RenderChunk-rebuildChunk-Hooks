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

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformerCheat implements IClassTransformer {

	public static final List<String> IGNORED_PREFIXES = ImmutableList.of("cpw", "net.minecraftforge", "io", "org", "gnu", "com", "joptsimple");

	public static final int FLAGS = ClassWriter.COMPUTE_FRAMES;// ClassWriter.COMPUTE_MAXS;

	public static boolean isDeobfuscated = false;

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (!name.equals("net.minecraft.client.renderer.chunk.RenderChunk") && !name.equals("cws")) {
			return basicClass;
		}

		this.isDeobfuscated = name.equals("net.minecraft.client.renderer.chunk.RenderChunk");

		final ClassReader classReader = new ClassReader(basicClass);
		final ClassWriter classWriter = new ClassWriter(classReader, FLAGS);

		LogManager.getLogger().info("creating custom method visitor to make the following hooks. If you don't see output that the hook was created theres an error");
		LogManager.getLogger().info("RebuildChunkEvent hook (rewrite method)");
		LogManager.getLogger().info("RebuildChunkBlocksEvent hook (rewrite method)");
		LogManager.getLogger().info("RebuildChunkBlockEvent hook (rewrite method)");

		final ClassVisitor classVisitor = new RebuildChunkHooksClassVisitor(classWriter);

		LogManager.getLogger().info("Attempting to make hooks...");

		try {
			classReader.accept(classVisitor, 0);
			LogManager.getLogger().info("Made hooks!");
			return classWriter.toByteArray();
		} catch (final Exception e) {
			LogManager.getLogger().error("FAILED to make hooks!!!");
		}

		return basicClass;
	}

	public class RebuildChunkHooksClassVisitor extends ClassVisitor {

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

		public static final boolean IS_DEOBF = RenderChunkRebuildChunkHooksRenderChunkClassTransformerCheat.isDeobfuscated;

		public static final String	COMPILED_CHUNK_CLASS	= "net/minecraft/client/renderer/chunk/CompiledChunk";
		public static final String	RENDER_CHUNK_CLASS		= "net/minecraft/client/renderer/chunk/RenderChunk";
		private static final String	RENDER_GLOBAL_CLASS		= "net/minecraft/client/renderer/RenderGlobal";
		private static final String	CLIENT_MINECRAFT_CLASS	= "net/minecraft/client/Minecraft";

		private static final String	HOOKS_CLASS			= "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks";
		public static final String	BLOCKS_EVENT_CLASS	= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent";
		public static final String	BLOCK_EVENT			= "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent";

		public static final Type	VIS_GRAPH_TYPE				= Type.getObjectType(Type.getInternalName(VisGraph.class));
		public static final String	VIS_GRAPH_INTERNAL_NAME		= Type.getInternalName(VisGraph.class);
		public static final String	HOOKS_CLASS_INTERNAL_NAME	= Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);
		// cant reference renderChunk because the class loader is still loading it at this time
		public static final String	RENDER_CHUNK_INTERNAL_NAME		= IS_DEOBF ? RENDER_CHUNK_CLASS : "bxp";
		public static final String	RENDER_GLOBAL_INTERNAL_NAME		= Type.getInternalName(RenderGlobal.class);
		public static final String	CHUNK_CACHE_INTERNAL_NAME		= Type.getInternalName(ChunkCache.class);
		public static final String	MUTABLE_BLOCK_POS_INTERNAL_NAME	= Type.getInternalName(MutableBlockPos.class);

		public static final String ON_REBUILD_CHUNK_EVENT_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(RENDER_GLOBAL_INTERNAL_NAME), Type.getObjectType(CHUNK_CACHE_INTERNAL_NAME), Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)), Type.getObjectType(Type.getInternalName(CompiledChunk.class)), Type.getObjectType(MUTABLE_BLOCK_POS_INTERNAL_NAME));

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

		@Override
		public void visitCode() {

			final int ASTORE = Opcodes.ASTORE;
			final int NEW = Opcodes.NEW;
			final int DUP = Opcodes.DUP;
			final int INVOKESPECIAL = Opcodes.INVOKESPECIAL;
			final int ICONST_1 = Opcodes.ICONST_1;
			final int ISTORE = Opcodes.ISTORE;
			final int GETFIELD = Opcodes.GETFIELD;
			final int ALOAD = Opcodes.ALOAD;
			final int BIPUSH = Opcodes.BIPUSH;
			final int INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
			final int GETSTATIC = Opcodes.GETSTATIC;
			final int IF_ACMPEQ = Opcodes.IF_ACMPEQ;
			final int RETURN = Opcodes.RETURN;
			final int ATHROW = Opcodes.ATHROW;
			final int GOTO = Opcodes.GOTO;
			final int FLOAD = Opcodes.FLOAD;
			final int INVOKESTATIC = Opcodes.INVOKESTATIC;
			final int IFEQ = Opcodes.IFEQ;
			final int IFNE = Opcodes.IFNE;
			final int IADD = Opcodes.IADD;
			final int INVOKEINTERFACE = Opcodes.INVOKEINTERFACE;
			final int IFNULL = Opcodes.IFNULL;
			final int CHECKCAST = Opcodes.CHECKCAST;
			final int ILOAD = Opcodes.ILOAD;
			final int PUTSTATIC = Opcodes.PUTSTATIC;
			final int ARRAYLENGTH = Opcodes.ARRAYLENGTH;
			final int POP = Opcodes.POP;
			final int AALOAD = Opcodes.AALOAD;
			final int ICONST_0 = Opcodes.ICONST_0;
			final int BALOAD = Opcodes.BALOAD;
			final int DUP2 = Opcodes.DUP2;
			final int IF_ICMPLT = Opcodes.IF_ICMPLT;
			final int IOR = Opcodes.IOR;
			final int BASTORE = Opcodes.BASTORE;
			final int ACONST_NULL = Opcodes.ACONST_NULL;

			// TODO: obfuscated environment (actually don't, write the mod properly and don't take this cheaty way!)

			final Label l0 = new Label();
			final Label l1 = new Label();
			final Label l2 = new Label();
			this.mv.visitTryCatchBlock(l0, l1, l2, null);
			final Label l3 = new Label();
			this.mv.visitTryCatchBlock(l3, l2, l2, null);
			final Label l4 = new Label();
			final Label l5 = new Label();
			this.mv.visitTryCatchBlock(l4, l5, l5, null);
			final Label l6 = new Label();
			this.mv.visitLabel(l6);
			this.mv.visitLineNumber(126, l6);
			this.mv.visitTypeInsn(NEW, COMPILED_CHUNK_CLASS);
			this.mv.visitInsn(DUP);
			this.mv.visitMethodInsn(INVOKESPECIAL, COMPILED_CHUNK_CLASS, "<init>", "()V", false);
			this.mv.visitVarInsn(ASTORE, 5);
			final Label l7 = new Label();
			this.mv.visitLabel(l7);
			this.mv.visitLineNumber(127, l7);
			this.mv.visitInsn(ICONST_1);
			this.mv.visitVarInsn(ISTORE, 6);
			final Label l8 = new Label();
			this.mv.visitLabel(l8);
			this.mv.visitLineNumber(128, l8);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			this.mv.visitVarInsn(ASTORE, 7);
			final Label l9 = new Label();
			this.mv.visitLabel(l9);
			this.mv.visitLineNumber(129, l9);
			this.mv.visitVarInsn(ALOAD, 7);
			this.mv.visitIntInsn(BIPUSH, 15);
			this.mv.visitIntInsn(BIPUSH, 15);
			this.mv.visitIntInsn(BIPUSH, 15);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", BLOCKPOS_add_III, "(III)Lnet/minecraft/util/math/BlockPos;", false);
			this.mv.visitVarInsn(ASTORE, 8);
			final Label l10 = new Label();
			this.mv.visitLabel(l10);
			this.mv.visitLineNumber(130, l10);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getLock, "()Ljava/util/concurrent/locks/ReentrantLock;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
			this.mv.visitLabel(l0);
			this.mv.visitLineNumber(134, l0);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getStatus, "()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;", false);
			this.mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status", "COMPILING", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;");
			this.mv.visitJumpInsn(IF_ACMPEQ, l3);
			this.mv.visitLabel(l1);
			this.mv.visitLineNumber(143, l1);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getLock, "()Ljava/util/concurrent/locks/ReentrantLock;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
			final Label l11 = new Label();
			this.mv.visitLabel(l11);
			this.mv.visitLineNumber(136, l11);
			this.mv.visitInsn(RETURN);
			this.mv.visitLabel(l3);
			this.mv.visitLineNumber(139, l3);
			this.mv.visitFrame(Opcodes.F_FULL, 9, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_setCompiledChunk, "(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
			final Label l12 = new Label();
			this.mv.visitLabel(l12);
			this.mv.visitLineNumber(140, l12);
			final Label l13 = new Label();
			this.mv.visitJumpInsn(GOTO, l13);
			this.mv.visitLabel(l2);
			this.mv.visitLineNumber(142, l2);
			this.mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/Throwable" });
			this.mv.visitVarInsn(ASTORE, 9);
			final Label l14 = new Label();
			this.mv.visitLabel(l14);
			this.mv.visitLineNumber(143, l14);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getLock, "()Ljava/util/concurrent/locks/ReentrantLock;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
			final Label l15 = new Label();
			this.mv.visitLabel(l15);
			this.mv.visitLineNumber(144, l15);
			this.mv.visitVarInsn(ALOAD, 9);
			this.mv.visitInsn(ATHROW);
			this.mv.visitLabel(l13);
			this.mv.visitLineNumber(143, l13);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getLock, "()Ljava/util/concurrent/locks/ReentrantLock;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
			final Label l16 = new Label();
			this.mv.visitLabel(l16);
			this.mv.visitLineNumber(146, l16);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;");
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			this.mv.visitVarInsn(FLOAD, 1);
			this.mv.visitVarInsn(FLOAD, 2);
			this.mv.visitVarInsn(FLOAD, 3);
			this.mv.visitMethodInsn(INVOKESTATIC, HOOKS_CLASS, "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false);
			final Label l17 = new Label();
			this.mv.visitJumpInsn(IFEQ, l17);
			this.mv.visitInsn(RETURN);
			this.mv.visitLabel(l17);
			this.mv.visitLineNumber(147, l17);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");
			this.mv.visitInsn(DUP);
			this.mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/VisGraph", "<init>", "()V", false);
			this.mv.visitVarInsn(ASTORE, 9);
			final Label l18 = new Label();
			this.mv.visitLabel(l18);
			this.mv.visitLineNumber(148, l18);
			this.mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "()Ljava/util/HashSet;", false);
			this.mv.visitVarInsn(ASTORE, 10);
			final Label l19 = new Label();
			this.mv.visitLabel(l19);
			this.mv.visitLineNumber(150, l19);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", CHUNK_CACHE_isEmpty, "()Z", false);
			final Label l20 = new Label();
			this.mv.visitJumpInsn(IFNE, l20);
			final Label l21 = new Label();
			this.mv.visitLabel(l21);
			this.mv.visitLineNumber(152, l21);
			this.mv.visitFieldInsn(GETSTATIC, RENDER_CHUNK_CLASS, STATIC_FIELD_renderChunksUpdated, "I");
			this.mv.visitInsn(ICONST_1);
			this.mv.visitInsn(IADD);
			this.mv.visitFieldInsn(PUTSTATIC, RENDER_CHUNK_CLASS, STATIC_FIELD_renderChunksUpdated, "I");
			final Label l22 = new Label();
			this.mv.visitLabel(l22);
			this.mv.visitLineNumber(153, l22);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;");
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 7);
			this.mv.visitVarInsn(ALOAD, 8);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", BlockPos_getAllInBoxMutable_BP_BP_Iterable, "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
			this.mv.visitMethodInsn(INVOKESTATIC, CLIENT_MINECRAFT_CLASS, Minecraft_getMinecraft, "()Lnet/minecraft/client/Minecraft;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, CLIENT_MINECRAFT_CLASS, MINECRAFT_getBlockRendererDispatcher, "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			this.mv.visitVarInsn(FLOAD, 1);
			this.mv.visitVarInsn(FLOAD, 2);
			this.mv.visitVarInsn(FLOAD, 3);
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitVarInsn(ALOAD, 9);
			this.mv.visitMethodInsn(INVOKESTATIC, HOOKS_CLASS, "onRebuildChunkBlocksEvent",
					"(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
			this.mv.visitVarInsn(ASTORE, 11);
			final Label l23 = new Label();
			this.mv.visitLabel(l23);
			this.mv.visitLineNumber(154, l23);
			this.mv.visitVarInsn(ALOAD, 11);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, BLOCKS_EVENT_CLASS, "getUsedBlockRenderLayers", "()[Z", false);
			this.mv.visitVarInsn(ASTORE, 12);
			final Label l24 = new Label();
			this.mv.visitLabel(l24);
			this.mv.visitLineNumber(155, l24);
			this.mv.visitMethodInsn(INVOKESTATIC, CLIENT_MINECRAFT_CLASS, Minecraft_getMinecraft, "()Lnet/minecraft/client/Minecraft;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, CLIENT_MINECRAFT_CLASS, MINECRAFT_getBlockRendererDispatcher, "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
			this.mv.visitVarInsn(ASTORE, 13);
			final Label l25 = new Label();
			this.mv.visitLabel(l25);
			this.mv.visitLineNumber(157, l25);
			this.mv.visitVarInsn(ALOAD, 7);
			this.mv.visitVarInsn(ALOAD, 8);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", BlockPos_getAllInBoxMutable_BP_BP_Iterable, "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/lang/Iterable", "iterator", "()Ljava/util/Iterator;", true);
			this.mv.visitVarInsn(ASTORE, 15);
			final Label l26 = new Label();
			this.mv.visitJumpInsn(GOTO, l26);
			final Label l27 = new Label();
			this.mv.visitLabel(l27);
			this.mv.visitFrame(Opcodes.F_FULL, 16, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", Opcodes.TOP, "java/util/Iterator" }, 0,
					new Object[] {});
			this.mv.visitVarInsn(ALOAD, 15);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			this.mv.visitTypeInsn(CHECKCAST, "net/minecraft/util/math/BlockPos$MutableBlockPos");
			this.mv.visitVarInsn(ASTORE, 14);
			final Label l28 = new Label();
			this.mv.visitLabel(l28);
			this.mv.visitLineNumber(159, l28);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", CHUNK_CACHE_getBlockState, "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
			this.mv.visitVarInsn(ASTORE, 16);
			final Label l29 = new Label();
			this.mv.visitLabel(l29);
			this.mv.visitLineNumber(160, l29);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", IBlockState_getBlock, "()Lnet/minecraft/block/Block;", true);
			this.mv.visitVarInsn(ASTORE, 17);
			final Label l30 = new Label();
			this.mv.visitLabel(l30);
			this.mv.visitLineNumber(162, l30);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", IBlockState_isOpaqueCube, "()Z", true);
			final Label l31 = new Label();
			this.mv.visitJumpInsn(IFEQ, l31);
			final Label l32 = new Label();
			this.mv.visitLabel(l32);
			this.mv.visitLineNumber(164, l32);
			this.mv.visitVarInsn(ALOAD, 9);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", VIS_GRAPH_setOpaqueCube, "(Lnet/minecraft/util/math/BlockPos;)V", false);
			this.mv.visitLabel(l31);
			this.mv.visitLineNumber(167, l31);
			this.mv.visitFrame(Opcodes.F_FULL, 18, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 17);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", BLOCK_hasTileEntity_IBlockState, "(Lnet/minecraft/block/state/IBlockState;)Z", false);
			final Label l33 = new Label();
			this.mv.visitJumpInsn(IFEQ, l33);
			final Label l34 = new Label();
			this.mv.visitLabel(l34);
			this.mv.visitLineNumber(169, l34);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitFieldInsn(GETSTATIC, "net/minecraft/world/chunk/Chunk$EnumCreateEntityType", "CHECK", "Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;");
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", CHUNK_CACHE_getTileEntity, "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;)Lnet/minecraft/tileentity/TileEntity;", false);
			this.mv.visitVarInsn(ASTORE, 18);
			final Label l35 = new Label();
			this.mv.visitLabel(l35);
			this.mv.visitLineNumber(171, l35);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitJumpInsn(IFNULL, l33);
			final Label l36 = new Label();
			this.mv.visitLabel(l36);
			this.mv.visitLineNumber(173, l36);
			this.mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", STATIC_FIELD_TERD_instance, "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;");
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", TileEntityRendererDispatcher_getRenderer_TE_TESR, "(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", false);
			this.mv.visitVarInsn(ASTORE, 19);
			final Label l37 = new Label();
			this.mv.visitLabel(l37);
			this.mv.visitLineNumber(175, l37);
			this.mv.visitVarInsn(ALOAD, 19);
			this.mv.visitJumpInsn(IFNULL, l33);
			final Label l38 = new Label();
			this.mv.visitLabel(l38);
			this.mv.visitLineNumber(178, l38);
			this.mv.visitVarInsn(ALOAD, 19);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer", TileEntitySpecialRenderer_isGlobalRenderer, "(Lnet/minecraft/tileentity/TileEntity;)Z", false);
			final Label l39 = new Label();
			this.mv.visitJumpInsn(IFEQ, l39);
			final Label l40 = new Label();
			this.mv.visitLabel(l40);
			this.mv.visitLineNumber(180, l40);
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashSet", "add", "(Ljava/lang/Object;)Z", false);
			this.mv.visitInsn(POP);
			final Label l41 = new Label();
			this.mv.visitLabel(l41);
			this.mv.visitLineNumber(181, l41);
			this.mv.visitJumpInsn(GOTO, l33);
			this.mv.visitLabel(l39);
			this.mv.visitLineNumber(182, l39);
			this.mv.visitFrame(Opcodes.F_APPEND, 2, new Object[] { "net/minecraft/tileentity/TileEntity", "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer" }, 0, null);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_addTileEntity, "(Lnet/minecraft/tileentity/TileEntity;)V", false);
			this.mv.visitLabel(l33);
			this.mv.visitLineNumber(187, l33);
			this.mv.visitFrame(Opcodes.F_CHOP, 2, null, 0, null);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
			this.mv.visitInsn(DUP);
			this.mv.visitVarInsn(ASTORE, 21);
			this.mv.visitInsn(ARRAYLENGTH);
			this.mv.visitVarInsn(ISTORE, 20);
			this.mv.visitInsn(ICONST_0);
			this.mv.visitVarInsn(ISTORE, 19);
			final Label l42 = new Label();
			this.mv.visitJumpInsn(GOTO, l42);
			final Label l43 = new Label();
			this.mv.visitLabel(l43);
			this.mv.visitFrame(Opcodes.F_FULL, 22, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 21);
			this.mv.visitVarInsn(ILOAD, 19);
			this.mv.visitInsn(AALOAD);
			this.mv.visitVarInsn(ASTORE, 18);
			final Label l44 = new Label();
			this.mv.visitLabel(l44);
			this.mv.visitLineNumber(188, l44);
			this.mv.visitVarInsn(ALOAD, 17);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", BLOCK_canRenderInLayer, "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z", false);
			final Label l45 = new Label();
			this.mv.visitJumpInsn(IFNE, l45);
			final Label l46 = new Label();
			this.mv.visitJumpInsn(GOTO, l46);
			this.mv.visitLabel(l45);
			this.mv.visitLineNumber(189, l45);
			this.mv.visitFrame(Opcodes.F_FULL, 22, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
			final Label l47 = new Label();
			this.mv.visitLabel(l47);
			this.mv.visitLineNumber(190, l47);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
			this.mv.visitVarInsn(ISTORE, 22);
			final Label l48 = new Label();
			this.mv.visitLabel(l48);
			this.mv.visitLineNumber(192, l48);
			this.mv.visitVarInsn(ALOAD, 17);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", BLOCK_getDefaultState, "()Lnet/minecraft/block/state/IBlockState;", false);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", IBlockState_getRenderType, "()Lnet/minecraft/util/EnumBlockRenderType;", true);
			this.mv.visitFieldInsn(GETSTATIC, "net/minecraft/util/EnumBlockRenderType", "INVISIBLE", "Lnet/minecraft/util/EnumBlockRenderType;");
			this.mv.visitJumpInsn(IF_ACMPEQ, l46);
			final Label l49 = new Label();
			this.mv.visitLabel(l49);
			this.mv.visitLineNumber(194, l49);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getRegionRenderCacheBuilder, "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
			this.mv.visitVarInsn(ILOAD, 22);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", RegionRenderCacheBuilder_getWorldRendererByLayerId, "(I)Lnet/minecraft/client/renderer/BufferBuilder;", false);
			this.mv.visitVarInsn(ASTORE, 23);
			final Label l50 = new Label();
			this.mv.visitLabel(l50);
			this.mv.visitLineNumber(196, l50);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_isLayerStarted, "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
			final Label l51 = new Label();
			this.mv.visitJumpInsn(IFNE, l51);
			final Label l52 = new Label();
			this.mv.visitLabel(l52);
			this.mv.visitLineNumber(198, l52);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 18);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_setLayerStarted, "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
			final Label l53 = new Label();
			this.mv.visitLabel(l53);
			this.mv.visitLineNumber(199, l53);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitVarInsn(ALOAD, 23);
			this.mv.visitVarInsn(ALOAD, 7);
			this.mv.visitMethodInsn(INVOKESPECIAL, RENDER_CHUNK_CLASS, RENDER_CHUNK_preRenderBlocks, "(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V", false);
			this.mv.visitLabel(l51);
			this.mv.visitLineNumber(202, l51);
			this.mv.visitFrame(Opcodes.F_APPEND, 2, new Object[] { Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder" }, 0, null);
			this.mv.visitVarInsn(ALOAD, 11);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, BLOCKS_EVENT_CLASS, "isCanceled", "()Z", false);
			this.mv.visitJumpInsn(IFNE, l46);
			final Label l54 = new Label();
			this.mv.visitLabel(l54);
			this.mv.visitLineNumber(203, l54);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;");
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 13);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitVarInsn(ALOAD, 23);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_POSITION_NAME, "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
			this.mv.visitVarInsn(FLOAD, 1);
			this.mv.visitVarInsn(FLOAD, 2);
			this.mv.visitVarInsn(FLOAD, 3);
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitVarInsn(ALOAD, 9);
			this.mv.visitMethodInsn(INVOKESTATIC, HOOKS_CLASS, "onRebuildChunkBlockEvent",
					"(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent;",
					false);
			this.mv.visitVarInsn(ASTORE, 24);
			final Label l55 = new Label();
			this.mv.visitLabel(l55);
			this.mv.visitLineNumber(204, l55);
			this.mv.visitVarInsn(ALOAD, 24);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, BLOCK_EVENT, "isCanceled", "()Z", false);
			final Label l56 = new Label();
			this.mv.visitJumpInsn(IFEQ, l56);
			final Label l57 = new Label();
			this.mv.visitLabel(l57);
			this.mv.visitLineNumber(205, l57);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
			this.mv.visitInsn(DUP);
			this.mv.visitVarInsn(ASTORE, 28);
			this.mv.visitInsn(ARRAYLENGTH);
			this.mv.visitVarInsn(ISTORE, 27);
			this.mv.visitInsn(ICONST_0);
			this.mv.visitVarInsn(ISTORE, 26);
			final Label l58 = new Label();
			this.mv.visitJumpInsn(GOTO, l58);
			final Label l59 = new Label();
			this.mv.visitLabel(l59);
			this.mv.visitFrame(Opcodes.F_FULL, 29, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder", BLOCK_EVENT, Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 28);
			this.mv.visitVarInsn(ILOAD, 26);
			this.mv.visitInsn(AALOAD);
			this.mv.visitVarInsn(ASTORE, 25);
			final Label l60 = new Label();
			this.mv.visitLabel(l60);
			this.mv.visitLineNumber(206, l60);
			this.mv.visitVarInsn(ALOAD, 24);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, BLOCK_EVENT, "getUsedBlockRenderLayers", "()[Z", false);
			this.mv.visitVarInsn(ALOAD, 25);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
			this.mv.visitInsn(BALOAD);
			final Label l61 = new Label();
			this.mv.visitJumpInsn(IFEQ, l61);
			final Label l62 = new Label();
			this.mv.visitLabel(l62);
			this.mv.visitLineNumber(207, l62);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 25);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_setLayerUsed, "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
			this.mv.visitLabel(l61);
			this.mv.visitLineNumber(205, l61);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitIincInsn(26, 1);
			this.mv.visitLabel(l58);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitVarInsn(ILOAD, 26);
			this.mv.visitVarInsn(ILOAD, 27);
			this.mv.visitJumpInsn(IF_ICMPLT, l59);
			final Label l63 = new Label();
			this.mv.visitLabel(l63);
			this.mv.visitLineNumber(210, l63);
			this.mv.visitJumpInsn(GOTO, l46);
			this.mv.visitLabel(l56);
			this.mv.visitLineNumber(211, l56);
			this.mv.visitFrame(Opcodes.F_FULL, 25, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder", BLOCK_EVENT }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 12);
			this.mv.visitVarInsn(ILOAD, 22);
			this.mv.visitInsn(DUP2);
			this.mv.visitInsn(BALOAD);
			this.mv.visitVarInsn(ALOAD, 13);
			this.mv.visitVarInsn(ALOAD, 16);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_WORLD_VIEW_NAME, "Lnet/minecraft/world/ChunkCache;");
			this.mv.visitVarInsn(ALOAD, 23);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/BlockRendererDispatcher", BlockRendererDispatcher_renderBlock, "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z", false);
			this.mv.visitInsn(IOR);
			this.mv.visitInsn(BASTORE);
			this.mv.visitLabel(l46);
			this.mv.visitLineNumber(187, l46);
			this.mv.visitFrame(Opcodes.F_FULL, 22, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher",
					"net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitIincInsn(19, 1);
			this.mv.visitLabel(l42);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitVarInsn(ILOAD, 19);
			this.mv.visitVarInsn(ILOAD, 20);
			this.mv.visitJumpInsn(IF_ICMPLT, l43);
			final Label l64 = new Label();
			this.mv.visitLabel(l64);
			this.mv.visitLineNumber(216, l64);
			this.mv.visitInsn(ACONST_NULL);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
			this.mv.visitLabel(l26);
			this.mv.visitLineNumber(157, l26);
			this.mv.visitFrame(Opcodes.F_FULL, 16, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", Opcodes.TOP, "java/util/Iterator" }, 0,
					new Object[] {});
			this.mv.visitVarInsn(ALOAD, 15);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			this.mv.visitJumpInsn(IFNE, l27);
			final Label l65 = new Label();
			this.mv.visitLabel(l65);
			this.mv.visitLineNumber(219, l65);
			this.mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
			this.mv.visitInsn(DUP);
			this.mv.visitVarInsn(ASTORE, 17);
			this.mv.visitInsn(ARRAYLENGTH);
			this.mv.visitVarInsn(ISTORE, 16);
			this.mv.visitInsn(ICONST_0);
			this.mv.visitVarInsn(ISTORE, 15);
			final Label l66 = new Label();
			this.mv.visitJumpInsn(GOTO, l66);
			final Label l67 = new Label();
			this.mv.visitLabel(l67);
			this.mv.visitFrame(Opcodes.F_FULL, 18, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", Opcodes.TOP, Opcodes.INTEGER,
					Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 17);
			this.mv.visitVarInsn(ILOAD, 15);
			this.mv.visitInsn(AALOAD);
			this.mv.visitVarInsn(ASTORE, 14);
			final Label l68 = new Label();
			this.mv.visitLabel(l68);
			this.mv.visitLineNumber(221, l68);
			this.mv.visitVarInsn(ALOAD, 12);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
			this.mv.visitInsn(BALOAD);
			final Label l69 = new Label();
			this.mv.visitJumpInsn(IFEQ, l69);
			final Label l70 = new Label();
			this.mv.visitLabel(l70);
			this.mv.visitLineNumber(223, l70);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_setLayerUsed, "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
			this.mv.visitLabel(l69);
			this.mv.visitLineNumber(226, l69);
			this.mv.visitFrame(Opcodes.F_FULL, 18, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/BlockRenderLayer",
					Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_isLayerStarted, "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
			final Label l71 = new Label();
			this.mv.visitJumpInsn(IFEQ, l71);
			final Label l72 = new Label();
			this.mv.visitLabel(l72);
			this.mv.visitLineNumber(228, l72);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitVarInsn(FLOAD, 1);
			this.mv.visitVarInsn(FLOAD, 2);
			this.mv.visitVarInsn(FLOAD, 3);
			this.mv.visitVarInsn(ALOAD, 4);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", ChunkCompileTaskGenerator_getRegionRenderCacheBuilder, "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
			this.mv.visitVarInsn(ALOAD, 14);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", RegionRenderCacheBuilder_getWorldRendererByLayer, "(Lnet/minecraft/util/BlockRenderLayer;)Lnet/minecraft/client/renderer/BufferBuilder;", false);
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitMethodInsn(INVOKESPECIAL, RENDER_CHUNK_CLASS, RENDER_CHUNK_postRenderBlocks, "(Lnet/minecraft/util/BlockRenderLayer;FFFLnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
			this.mv.visitLabel(l71);
			this.mv.visitLineNumber(219, l71);
			this.mv.visitFrame(Opcodes.F_FULL, 18, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", BLOCKS_EVENT_CLASS, "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", Opcodes.TOP, Opcodes.INTEGER,
					Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
			this.mv.visitIincInsn(15, 1);
			this.mv.visitLabel(l66);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitVarInsn(ILOAD, 15);
			this.mv.visitVarInsn(ILOAD, 16);
			this.mv.visitJumpInsn(IF_ICMPLT, l67);
			this.mv.visitLabel(l20);
			this.mv.visitLineNumber(233, l20);
			this.mv.visitFrame(Opcodes.F_FULL, 11, new Object[] { RENDER_CHUNK_CLASS, Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", COMPILED_CHUNK_CLASS, Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet" }, 0, new Object[] {});
			this.mv.visitVarInsn(ALOAD, 5);
			this.mv.visitVarInsn(ALOAD, 9);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", VIS_GRAPH_computeVisibility, "()Lnet/minecraft/client/renderer/chunk/SetVisibility;", false);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, COMPILED_CHUNK_CLASS, COMPILED_CHUNK_setVisibility, "(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V", false);
			final Label l73 = new Label();
			this.mv.visitLabel(l73);
			this.mv.visitLineNumber(234, l73);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_lockCompileTask, "Ljava/util/concurrent/locks/ReentrantLock;");
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
			this.mv.visitLabel(l4);
			this.mv.visitLineNumber(238, l4);
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
			this.mv.visitVarInsn(ASTORE, 11);
			final Label l74 = new Label();
			this.mv.visitLabel(l74);
			this.mv.visitLineNumber(239, l74);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_setTileEntities, "Ljava/util/Set;");
			this.mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
			this.mv.visitVarInsn(ASTORE, 12);
			final Label l75 = new Label();
			this.mv.visitLabel(l75);
			this.mv.visitLineNumber(240, l75);
			this.mv.visitVarInsn(ALOAD, 11);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_setTileEntities, "Ljava/util/Set;");
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
			this.mv.visitInsn(POP);
			final Label l76 = new Label();
			this.mv.visitLabel(l76);
			this.mv.visitLineNumber(241, l76);
			this.mv.visitVarInsn(ALOAD, 12);
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
			this.mv.visitInsn(POP);
			final Label l77 = new Label();
			this.mv.visitLabel(l77);
			this.mv.visitLineNumber(242, l77);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_setTileEntities, "Ljava/util/Set;");
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "clear", "()V", true);
			final Label l78 = new Label();
			this.mv.visitLabel(l78);
			this.mv.visitLineNumber(243, l78);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_setTileEntities, "Ljava/util/Set;");
			this.mv.visitVarInsn(ALOAD, 10);
			this.mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "addAll", "(Ljava/util/Collection;)Z", true);
			this.mv.visitInsn(POP);
			final Label l79 = new Label();
			this.mv.visitLabel(l79);
			this.mv.visitLineNumber(244, l79);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_RENDER_GLOBAL_NAME, "Lnet/minecraft/client/renderer/RenderGlobal;");
			this.mv.visitVarInsn(ALOAD, 12);
			this.mv.visitVarInsn(ALOAD, 11);
			this.mv.visitMethodInsn(INVOKEVIRTUAL, RENDER_GLOBAL_CLASS, RENDER_GLOBAL_updateTileEntities, "(Ljava/util/Collection;Ljava/util/Collection;)V", false);
			final Label l80 = new Label();
			this.mv.visitLabel(l80);
			this.mv.visitLineNumber(245, l80);
			final Label l81 = new Label();
			this.mv.visitJumpInsn(GOTO, l81);
			this.mv.visitLabel(l5);
			this.mv.visitLineNumber(247, l5);
			this.mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/Throwable" });
			this.mv.visitVarInsn(ASTORE, 13);
			final Label l82 = new Label();
			this.mv.visitLabel(l82);
			this.mv.visitLineNumber(248, l82);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_lockCompileTask, "Ljava/util/concurrent/locks/ReentrantLock;");
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
			final Label l83 = new Label();
			this.mv.visitLabel(l83);
			this.mv.visitLineNumber(249, l83);
			this.mv.visitVarInsn(ALOAD, 13);
			this.mv.visitInsn(ATHROW);
			this.mv.visitLabel(l81);
			this.mv.visitLineNumber(248, l81);
			this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			this.mv.visitVarInsn(ALOAD, 0);
			this.mv.visitFieldInsn(GETFIELD, RENDER_CHUNK_CLASS, FIELD_lockCompileTask, "Ljava/util/concurrent/locks/ReentrantLock;");
			this.mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
			final Label l84 = new Label();
			this.mv.visitLabel(l84);
			this.mv.visitLineNumber(250, l84);
			this.mv.visitInsn(RETURN);
			final Label l85 = new Label();
			this.mv.visitLabel(l85);
//			this.mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/chunk/RenderChunk;", null, l6, l85, 0);
//			this.mv.visitLocalVariable("x", "F", null, l6, l85, 1);
//			this.mv.visitLocalVariable("y", "F", null, l6, l85, 2);
//			this.mv.visitLocalVariable("z", "F", null, l6, l85, 3);
//			this.mv.visitLocalVariable("generator", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;", null, l6, l85, 4);
//			this.mv.visitLocalVariable("compiledchunk", "Lnet/minecraft/client/renderer/chunk/CompiledChunk;", null, l7, l85, 5);
//			this.mv.visitLocalVariable("i", "I", null, l8, l85, 6);
//			this.mv.visitLocalVariable("blockpos", "Lnet/minecraft/util/math/BlockPos;", null, l9, l85, 7);
//			this.mv.visitLocalVariable("blockpos1", "Lnet/minecraft/util/math/BlockPos;", null, l10, l85, 8);
//			this.mv.visitLocalVariable("lvt_9_1_", "Lnet/minecraft/client/renderer/chunk/VisGraph;", null, l18, l85, 9);
//			this.mv.visitLocalVariable("lvt_10_1_", "Ljava/util/HashSet;", null, l19, l85, 10);
//			this.mv.visitLocalVariable("rebuildBlocksEvent", "Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", null, l23, l20, 11);
//			this.mv.visitLocalVariable("aboolean", "[Z", null, l24, l20, 12);
//			this.mv.visitLocalVariable("blockrendererdispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", null, l25, l20, 13);
//			this.mv.visitLocalVariable("blockpos$mutableblockpos", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;", null, l28, l26, 14);
//			this.mv.visitLocalVariable("iblockstate", "Lnet/minecraft/block/state/IBlockState;", null, l29, l26, 16);
//			this.mv.visitLocalVariable("block", "Lnet/minecraft/block/Block;", null, l30, l26, 17);
//			this.mv.visitLocalVariable("tileentity", "Lnet/minecraft/tileentity/TileEntity;", null, l35, l33, 18);
//			this.mv.visitLocalVariable("tileentityspecialrenderer", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer<Lnet/minecraft/tileentity/TileEntity;>;", l37, l33, 19);
//			this.mv.visitLocalVariable("blockrenderlayer1", "Lnet/minecraft/util/BlockRenderLayer;", null, l44, l46, 18);
//			this.mv.visitLocalVariable("j", "I", null, l48, l46, 22);
//			this.mv.visitLocalVariable("bufferbuilder", "Lnet/minecraft/client/renderer/BufferBuilder;", null, l50, l46, 23);
//			this.mv.visitLocalVariable("rebuildBlockEvent", "Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlockEvent;", null, l55, l46, 24);
//			this.mv.visitLocalVariable("blockrenderlayer", "Lnet/minecraft/util/BlockRenderLayer;", null, l60, l61, 25);
//			this.mv.visitLocalVariable("blockrenderlayer", "Lnet/minecraft/util/BlockRenderLayer;", null, l68, l71, 14);
//			this.mv.visitLocalVariable("set", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l74, l80, 11);
//			this.mv.visitLocalVariable("set1", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l75, l80, 12);
//			this.mv.visitMaxs(14, 29);

		}

	}

}
