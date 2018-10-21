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

	public boolean isDeobfuscated = false;

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (!name.equals("net.minecraft.client.renderer.chunk.RenderChunk") && !name.equals("cws")) {
			return basicClass;
		}

		this.isDeobfuscated = name.equals("net.minecraft.client.renderer.chunk.RenderChunk");

		LogManager.getLogger().info("creating custom method visitor to make the following hooks. If you don't see output that the hook was created theres an error");
		LogManager.getLogger().info("RebuildChunkEvent hook (in visitTypeInsn)");
		LogManager.getLogger().info("RebuildChunkBlocksEvent hook (in visitFieldInsn and visitEnd)");
		LogManager.getLogger().info("RebuildChunkBlockEvent hook (in visitEnd)");

		final ClassReader classReader = new ClassReader(basicClass);
		final ClassWriter classWriter = new ClassWriter(classReader, FLAGS);

		final ClassVisitor classVisitor = new RebuildChunkHooksClassVisitor(classWriter);

		LogManager.getLogger().info("Attempting to make hooks...");

		try {
			classReader.accept(classVisitor, 0);

			LogManager.getLogger().info("Made hooks!");
			return classWriter.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("FAILED to make hooks!!! Discarding changes.");
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

	public class RebuildChunkHooksMethodVisitor extends MethodVisitor {

		public final Type	VIS_GRAPH_TYPE				= Type.getObjectType(Type.getInternalName(VisGraph.class));
		public final String	VIS_GRAPH_INTERNAL_NAME		= Type.getInternalName(VisGraph.class);
		public final String	HOOKS_CLASS_INTERNAL_NAME	= Type.getInternalName(RenderChunkRebuildChunkHooksHooks.class);
		// cant reference renderChunk because the class loader is still loading it at this time
		public final String	RENDER_CHUNK_INTERNAL_NAME		= RenderChunkRebuildChunkHooksRenderChunkClassTransformer.this.isDeobfuscated ? "net/minecraft/client/renderer/chunk/RenderChunk" : "bxp";
		public final String	RENDER_GLOBAL_INTERNAL_NAME		= Type.getInternalName(RenderGlobal.class);
		public final String	CHUNK_CACHE_INTERNAL_NAME		= Type.getInternalName(ChunkCache.class);
		public final String	MUTABLE_BLOCK_POS_INTERNAL_NAME	= Type.getInternalName(MutableBlockPos.class);

		public final String ON_REBUILD_CHUNK_EVENT_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME), Type.getObjectType(this.CHUNK_CACHE_INTERNAL_NAME), Type.getObjectType(Type.getInternalName(ChunkCompileTaskGenerator.class)), Type.getObjectType(Type.getInternalName(CompiledChunk.class)), Type.getObjectType(this.MUTABLE_BLOCK_POS_INTERNAL_NAME));

		public RebuildChunkHooksMethodVisitor(final MethodVisitor mv) {
			super(Opcodes.ASM5, mv);
		}

		@Override
		public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {

			// inject RebuildChunkBlocksEvent hook variable
			if ((opcode == Opcodes.GETSTATIC) && (owner.equals(this.RENDER_CHUNK_INTERNAL_NAME)) && (name.equals("renderChunksUpdated") || name.equals("field_178592_a")) && (desc.equals(Type.INT_TYPE.getDescriptor()))) {
				LogManager.getLogger().info("injecting RebuildChunkBlocksEvent hook variable...");

// KEY:
//>>>>			= header
//>>>>>>>>		= header
//>				= my insertion
//#				= ASM Injection Point
//*				= insertion by compiler for reasons I don't understand
//	  ______	= read this line it explains itself

//>>>>			ORIGINAL SOURCE CODE:
//		        if (!this.worldView.isEmpty())
//		        {
//		            ++renderChunksUpdated;

//>>>>			MODIFIED SOURCE CODE:
//			 	if (!this.worldView.isEmpty())
//		        {
//>		            final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(renderGlobal, worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1),Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//		            ++renderChunksUpdated;

//>>>>>>>>		TURNED INTO BYTECODE INSTRUCTIONS:

//>>>>			ORIGINAL BYTECODE INSTRUCTIONS:
//			   L20
//			    LINENUMBER 152 L20
//			    ALOAD 0
//			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//			    INVOKEVIRTUAL net/minecraft/world/ChunkCache.isEmpty()Z
//			    IFNE L21
//			   L22
//			    LINENUMBER 154 L22
//#			    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//			    ICONST_1
//			    IADD
//			    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I

//>>>>			MODIFIED BYTECODE INSTRUCTIONS:
//			  L19
//			    LINENUMBER 152 L19
//			    ALOAD 0
//			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//			    INVOKEVIRTUAL net/minecraft/world/ChunkCache.isEmpty()Z
//			    IFNE L20
//>			   L21
//>			    LINENUMBER 154 L21
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//>			    ALOAD 4
//>			    ALOAD 5
//>			    ALOAD 7
//>			    ALOAD 8
//>			    INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
//>			    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//>			    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//>			    FLOAD 1
//>			    FLOAD 2
//>			    FLOAD 3
//>			    ALOAD 10
//>			    ALOAD 9
//>			    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;
//>			    ASTORE 11
//			   L22
//			    LINENUMBER 155 L22
//#			    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//			    ICONST_1
//			    IADD
//			    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I

//>>>>>>>>		TURNED INTO ASM INSTRUCTIONS:

//>>>>			ORIGINAL ASM INSTRUCTIONS:
//				Label l20 = new Label();
//				mv.visitLabel(l20);
//				mv.visitLineNumber(152, l20);
//				mv.visitVarInsn(ALOAD, 0);
//				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "isEmpty", "()Z", false);
//				Label l21 = new Label();
//				mv.visitJumpInsn(IFNE, l21);
//				Label l22 = new Label();
//				mv.visitLabel(l22);
//				mv.visitLineNumber(154, l22);
//				mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//				mv.visitInsn(ICONST_1);
//				mv.visitInsn(IADD);
//				mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");

//>>>>			MODIFIED ASM INSTRUCTIONS:
//				Label l19 = new Label();
//				mv.visitLabel(l19);
//				mv.visitLineNumber(152, l19);
//				mv.visitVarInsn(ALOAD, 0);
//				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "isEmpty", "()Z", false);
//				Label l20 = new Label();
//				mv.visitJumpInsn(IFNE, l20);
//>				Label l21 = new Label();
//>				mv.visitLabel(l21);
//>				mv.visitLineNumber(154, l21);
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//>				mv.visitVarInsn(ALOAD, 4);
//>				mv.visitVarInsn(ALOAD, 5);
//>				mv.visitVarInsn(ALOAD, 7);
//>				mv.visitVarInsn(ALOAD, 8);
//>				mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
//>				mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//>				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//>				mv.visitVarInsn(FLOAD, 1);
//>				mv.visitVarInsn(FLOAD, 2);
//>				mv.visitVarInsn(FLOAD, 3);
//>				mv.visitVarInsn(ALOAD, 10);
//>				mv.visitVarInsn(ALOAD, 9);
//>				mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlocksEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
//>				mv.visitVarInsn(ASTORE, 11);
//				Label l22 = new Label();
//				mv.visitLabel(l22);
//				mv.visitLineNumber(155, l22);
//				mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//				mv.visitInsn(ICONST_1);
//				mv.visitInsn(IADD);
//				mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");

				final Label l21 = new Label();
				this.mv.visitLabel(l21);
				this.mv.visitLineNumber(154, l21);
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				this.mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				this.mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
				this.mv.visitVarInsn(Opcodes.ALOAD, 4);
				this.mv.visitVarInsn(Opcodes.ALOAD, 5);
				this.mv.visitVarInsn(Opcodes.ALOAD, 7);
				this.mv.visitVarInsn(Opcodes.ALOAD, 8);
				this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
				this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
				this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				this.mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
				this.mv.visitVarInsn(Opcodes.FLOAD, 1);
				this.mv.visitVarInsn(Opcodes.FLOAD, 2);
				this.mv.visitVarInsn(Opcodes.FLOAD, 3);
				this.mv.visitVarInsn(Opcodes.ALOAD, 10);
				this.mv.visitVarInsn(Opcodes.ALOAD, 9);
				this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlocksEvent",
						"(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
				this.mv.visitVarInsn(Opcodes.ASTORE, 11);

				LogManager.getLogger().info("finished injecting RebuildChunkBlocksEvent hook variable");

			}

			super.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitTypeInsn(final int opcode, final String type) {

			// inject RebuildChunkEvent hook
			if ((opcode == Opcodes.NEW) && type.equals(this.VIS_GRAPH_INTERNAL_NAME)) {
				LogManager.getLogger().info("injecting RebuildChunkEvent hook...");

//				final Label l16 = new Label();
//				this.visitLabel(l16);
//				this.visitVarInsn(Opcodes.ALOAD, 0);
//				if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer2.this.isDeobfuscated) {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "renderGlobal", Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME).getDescriptor());
//				} else {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "field_178589_e", Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME).getDescriptor());
//				}
//				this.visitVarInsn(Opcodes.ALOAD, 0);
//				if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer2.this.isDeobfuscated) {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "worldView", Type.getObjectType(this.CHUNK_CACHE_INTERNAL_NAME).getDescriptor());
//				} else {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "field_189564_r", Type.getObjectType(this.CHUNK_CACHE_INTERNAL_NAME).getDescriptor());
//				}
//				this.visitVarInsn(Opcodes.ALOAD, 4);
//				this.visitVarInsn(Opcodes.ALOAD, 5);
//				this.visitVarInsn(Opcodes.ALOAD, 0);
//				if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer2.this.isDeobfuscated) {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "position", Type.getObjectType(this.MUTABLE_BLOCK_POS_INTERNAL_NAME).getDescriptor());
//				} else {
//					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "field_178586_f", Type.getObjectType(this.MUTABLE_BLOCK_POS_INTERNAL_NAME).getDescriptor());
//				}
//

// KEY:
//>>>>			= header
//>>>>>>>>		= header
//>				= my insertion
//#				= ASM Injection Point
//*				= insertion by compiler for reasons I don't understand
//	  ______	= read this line it explains itself

//>>>>			ORIGINAL SOURCE CODE:
//				finally
//		        {
//		            generator.getLock().unlock();
//		        }
//
//		        VisGraph lvt_9_1_ = new VisGraph();

//>>>>			MODIFIED SOURCE CODE:
//				finally
//		        {
//		            generator.getLock().unlock();
//		        }
//>		        if (cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkEvent(renderGlobal, worldView, generator, compiledchunk, this.position, x, y, z)) return;
//		        VisGraph lvt_9_1_ = new VisGraph();

//>>>>>>>>		TURNED INTO BYTECODE INSTRUCTIONS:

//>>>>			ORIGINAL BYTECODE INSTRUCTIONS:
//				FRAME SAME1 java/lang/Throwable
//			    ASTORE 9
//			   L5
//			    ALOAD 4
//			    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
//			    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
//			   L18
//			    LINENUMBER 147 L18
//			    ALOAD 9
//			    ATHROW
//			   L17
//			    LINENUMBER 149 L17
//			   FRAME SAME
//#			    NEW net/minecraft/client/renderer/chunk/VisGraph
//			    DUP

//>>>>			MODIFIED BYTECODE INSTRUCTIONS:
//				FRAME SAME1 java/lang/Throwable
//			    ASTORE 9
//			   L14
//			    LINENUMBER 146 L14
//			    ALOAD 4
//			    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
//			    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
//			   L15
//			    LINENUMBER 147 L15
//			    ALOAD 9
//			    ATHROW
//*			   L13
//*			    LINENUMBER 146 L13
//*			   FRAME SAME
//*			    ALOAD 4
//*			    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
//*			    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
//>			   L16
//>			    LINENUMBER 148 L16
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//>			    ALOAD 4
//>			    ALOAD 5
//>			    ALOAD 0
//>			    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//>			    FLOAD 1
//>			    FLOAD 2
//>			    FLOAD 3
//>			    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z
//>			    IFEQ L17
//>			    RETURN
//			   L17
//			    LINENUMBER 149 L17
//			   FRAME SAME
//#			    NEW net/minecraft/client/renderer/chunk/VisGraph
//			    DUP

//>>>>>>>>		TURNED INTO ASM INSTRUCTIONS:

//>>>>			ORIGINAL ASM INSTRUCTIONS:
//				mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
//				mv.visitVarInsn(ASTORE, 9);
//				mv.visitLabel(l5);
//				mv.visitVarInsn(ALOAD, 4);
//				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
//				mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//				Label l18 = new Label();
//				mv.visitLabel(l18);
//				mv.visitLineNumber(147, l18);
//				mv.visitVarInsn(ALOAD, 9);
//				mv.visitInsn(ATHROW);
//				mv.visitLabel(l17);
//				mv.visitLineNumber(149, l17);
//				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//				mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");
//				mv.visitInsn(DUP);

//>>>>			MODIFIED ASM INSTRUCTIONS:
//				mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
//				mv.visitVarInsn(ASTORE, 9);
//				Label l14 = new Label();
//				mv.visitLabel(l14);
//				mv.visitLineNumber(146, l14);
//				mv.visitVarInsn(ALOAD, 4);
//				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
//				mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//				Label l15 = new Label();
//				mv.visitLabel(l15);
//				mv.visitLineNumber(147, l15);
//				mv.visitVarInsn(ALOAD, 9);
//				mv.visitInsn(ATHROW);
//*				mv.visitLabel(l13);
//*				mv.visitLineNumber(146, l13);
//*				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//*				mv.visitVarInsn(ALOAD, 4);
//*				mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
//*				mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
//>				Label l16 = new Label();
//>				mv.visitLabel(l16);
//>				mv.visitLineNumber(148, l16);
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//>				mv.visitVarInsn(ALOAD, 4);
//>				mv.visitVarInsn(ALOAD, 5);
//>				mv.visitVarInsn(ALOAD, 0);
//>				mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//>				mv.visitVarInsn(FLOAD, 1);
//>				mv.visitVarInsn(FLOAD, 2);
//>				mv.visitVarInsn(FLOAD, 3);
//>				mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false);
//>				Label l17 = new Label();
//>				mv.visitJumpInsn(IFEQ, l17);
//>				mv.visitInsn(RETURN);
//>				mv.visitLabel(l17);
//>				mv.visitLineNumber(149, l17);
//	  ______	we add another visit frame SAME	here that isn't in the generated ASM:			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//				mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//				mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");
//				mv.visitInsn(DUP);

				final Label l16 = new Label();
				this.mv.visitLabel(l16);
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				if (RenderChunkRebuildChunkHooksRenderChunkClassTransformer.this.isDeobfuscated) {
					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "renderGlobal", Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME).getDescriptor());
				} else {
					this.visitFieldInsn(Opcodes.GETFIELD, this.RENDER_CHUNK_INTERNAL_NAME, "field_178589_e", Type.getObjectType(this.RENDER_GLOBAL_INTERNAL_NAME).getDescriptor());
				}
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				this.mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
				this.mv.visitVarInsn(Opcodes.ALOAD, 4);
				this.mv.visitVarInsn(Opcodes.ALOAD, 5);
				this.mv.visitVarInsn(Opcodes.ALOAD, 0);
				this.mv.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
				this.mv.visitVarInsn(Opcodes.FLOAD, 1);
				this.mv.visitVarInsn(Opcodes.FLOAD, 2);
				this.mv.visitVarInsn(Opcodes.FLOAD, 3);
				this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Z", false);
				final Label l17 = new Label();
				this.mv.visitJumpInsn(Opcodes.IFEQ, l17);
				this.mv.visitInsn(Opcodes.RETURN);
				this.mv.visitLabel(l17);
				this.mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

				LogManager.getLogger().info("finished injecting RebuildChunkEvent hook");

			}
			super.visitTypeInsn(opcode, type);

			RenderChunkRebuildChunkHooksHooks.dummyEventVoid();
		}

//		if (!compiledchunk.isLayerStarted(blockrenderlayer1))
//        {
//            compiledchunk.setLayerStarted(blockrenderlayer1);
//            this.preRenderBlocks(bufferbuilder, blockpos);
//        }
//		if (!rebuildBlocksEvent.isCanceled()) {
//			final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlockEvent rebuildBlockEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(this.renderGlobal, this.worldView, generator, compiledchunk, blockrendererdispatcher, iblockstate, blockpos$mutableblockpos, bufferbuilder, this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//			if (rebuildBlockEvent.isCanceled()) {
//				for (final BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
//					if (rebuildBlockEvent.getUsedBlockRenderLayers()[blockrenderlayer.ordinal()]) {
//						compiledchunk.setLayerUsed(blockrenderlayer);
//					}
//				}
//			} else {
//				aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
//			}
//		}

		@Override
		public void visitIntInsn(final int opcode, final int operand) {

// KEY:
//>>>>		= header
//>>>>>>>>	= header
//>			= my insertion
//#			= ASM Injection Point
//*			= insertion by compiler for reasons I don't understand
//  ______  = read this line it explains itself
//$			= my hook that has been already inserted

//>>>>		ORIGINAL SOURCE CODE:
//	        if (!this.worldView.isEmpty())
//	        {
//	            ++renderChunksUpdated;
//	            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		ORIGINAL SOURCE CODE (MODIFIED BY ALREADY CREATED HOOK):
//			if (!this.worldView.isEmpty())
//	        {
//	            ++renderChunksUpdated;
//$	            final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//	            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		MODIFIED SOURCE CODE:
//			if (!this.worldView.isEmpty())
//	        {
//	            ++renderChunksUpdated;
//	            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//>	            aboolean = rebuildBlocksEvent.getUsedBlockRenderLayers();
//	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		MODIFIED SOURCE CODE (MODIFIED BY ALREADY CREATED HOOK):
//			if (!this.worldView.isEmpty())
//	        {
//	            ++renderChunksUpdated;
//$	            final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//	            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//>	            aboolean = rebuildBlocksEvent.getUsedBlockRenderLayers();
//	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>>>>>	TURNED INTO BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):

//>>>>		ORIGINAL BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//		   L21
//		    LINENUMBER 154 L21
//		    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		    ICONST_1
//		    IADD
//		    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//$		   L22
//$		    LINENUMBER 155 L22
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//$		    ALOAD 4
//$		    ALOAD 5
//$		    ALOAD 7
//$		    ALOAD 8
//$		    INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
//$		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//$		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//$		    FLOAD 1
//$		    FLOAD 2
//$		    FLOAD 3
//$		    ALOAD 10
//$		    ALOAD 9
//$		    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;
//$		    ASTORE 11
//		   L23
//		    LINENUMBER 156 L23
//		    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
//		    ARRAYLENGTH
//#		    NEWARRAY T_BOOLEAN
//		    ASTORE 12
//		   L24
//		    LINENUMBER 158 L24
//		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//		    ASTORE 13

//>>>>		MODIFIED BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			L21
//		    LINENUMBER 154 L21
//		    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		    ICONST_1
//		    IADD
//		    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//$		   L22
//$		    LINENUMBER 155 L22
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//$		    ALOAD 4
//$		    ALOAD 5
//$		    ALOAD 7
//$		    ALOAD 8
//$		    INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
//$		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//$		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//$		    ALOAD 0
//$		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//$		    FLOAD 1
//$		    FLOAD 2
//$		    FLOAD 3
//$		    ALOAD 10
//$		    ALOAD 9
//$		    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;
//$		    ASTORE 11
//		   L23
//		    LINENUMBER 156 L23
//		    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
//		    ARRAYLENGTH
//#		    NEWARRAY T_BOOLEAN
//		    ASTORE 12
//>		   L24
//>		    LINENUMBER 157 L24
//>		    ALOAD 11
//>		    INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent.getUsedBlockRenderLayers()[Z
//>		    ASTORE 12
//		   L25
//		    LINENUMBER 158 L25
//		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//		    ASTORE 13

//>>>>>>>>	TURNED INTO ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):

//>>>>		ORIGINAL ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			Label l21 = new Label();
//			mv.visitLabel(l21);
//			mv.visitLineNumber(154, l21);
//			mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			mv.visitInsn(ICONST_1);
//			mv.visitInsn(IADD);
//			mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//$			Label l22 = new Label();
//$			mv.visitLabel(l22);
//$			mv.visitLineNumber(155, l22);
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//$			mv.visitVarInsn(ALOAD, 4);
//$			mv.visitVarInsn(ALOAD, 5);
//$			mv.visitVarInsn(ALOAD, 7);
//$			mv.visitVarInsn(ALOAD, 8);
//$			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
//$			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//$			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//$			mv.visitVarInsn(FLOAD, 1);
//$			mv.visitVarInsn(FLOAD, 2);
//$			mv.visitVarInsn(FLOAD, 3);
//$			mv.visitVarInsn(ALOAD, 10);
//$			mv.visitVarInsn(ALOAD, 9);
//$			mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlocksEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
//$			mv.visitVarInsn(ASTORE, 11);
//			Label l23 = new Label();
//			mv.visitLabel(l23);
//			mv.visitLineNumber(156, l23);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
//			mv.visitInsn(ARRAYLENGTH);
//#			mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
//			mv.visitVarInsn(ASTORE, 12);
//			Label l24 = new Label();
//			mv.visitLabel(l24);
//			mv.visitLineNumber(158, l24);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//			mv.visitVarInsn(ASTORE, 13);

//>>>>		MODIFIED ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			Label l21 = new Label();
//			mv.visitLabel(l21);
//			mv.visitLineNumber(154, l21);
//			mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			mv.visitInsn(ICONST_1);
//			mv.visitInsn(IADD);
//			mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//$			Label l22 = new Label();
//$			mv.visitLabel(l22);
//$			mv.visitLineNumber(155, l22);
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//$			mv.visitVarInsn(ALOAD, 4);
//$			mv.visitVarInsn(ALOAD, 5);
//$			mv.visitVarInsn(ALOAD, 7);
//$			mv.visitVarInsn(ALOAD, 8);
//$			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
//$			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//$			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//$			mv.visitVarInsn(ALOAD, 0);
//$			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//$			mv.visitVarInsn(FLOAD, 1);
//$			mv.visitVarInsn(FLOAD, 2);
//$			mv.visitVarInsn(FLOAD, 3);
//$			mv.visitVarInsn(ALOAD, 10);
//$			mv.visitVarInsn(ALOAD, 9);
//$			mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlocksEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
//$			mv.visitVarInsn(ASTORE, 11);
//			Label l23 = new Label();
//			mv.visitLabel(l23);
//			mv.visitLineNumber(156, l23);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
//			mv.visitInsn(ARRAYLENGTH);
//#			mv.visitIntInsn(NEWARRAY, T_BOOLEAN);// | | | | BECAUSE WE INSERT JUST UNDER HERE (1 INSTRUCTION HIGHER THAN WE WOULD LIKE TO INJECT) WE INJECT
//			mv.visitVarInsn(ASTORE, 12); // | | | | | | | | THIS LINE, AND THIS LINE BECOMES
//>			Label l24 = new Label();
//>			mv.visitLabel(l24);
//>			mv.visitLineNumber(157, l24);
//>			mv.visitVarInsn(ALOAD, 11);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false);
//>			mv.visitVarInsn(ASTORE, 12); // | | | | | | | | THIS LINE. SO WE DON'T INJECT THIS LINE
//			Label l25 = new Label();
//			mv.visitLabel(l25);
//			mv.visitLineNumber(158, l25);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//			mv.visitVarInsn(ASTORE, 13);
			if ((opcode == Opcodes.NEWARRAY) && (operand == Opcodes.T_BOOLEAN)) {
				LogManager.getLogger().info("injecting RebuildChunkBlocksEvent hook logic part 1...");

				super.visitIntInsn(opcode, operand);
				this.mv.visitVarInsn(Opcodes.ASTORE, 12); // because we have
				final Label l24 = new Label();
				this.mv.visitLabel(l24);
				this.mv.visitLineNumber(157, l24);
				this.mv.visitVarInsn(Opcodes.ALOAD, 11);
				this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false);

				LogManager.getLogger().info("finished injecting RebuildChunkBlocksEvent hook logic part 1");
				return;
			}

			super.visitIntInsn(opcode, operand);
		}

		Label l47_ForLoopLabel = null;

		@Override
		public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {

//			L47
//		    LINENUMBER 190 L47
//		   FRAME FULL [RenderChunk float float float ChunkCompileTaskGenerator CompiledChunk int BlockPos BlockPos VisGraph HashSet RebuildChunkBlocksEvent boolean[] BlockRendererDispatcher BlockPos$MutableBlockPos Iterator IBlockState Block top int int BlockRenderLayer[]] []
//		   mv.visitFrame(Opcodes.F_FULL, 22, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;"}, 0, new Object[] {});

			if ((type == Opcodes.F_FULL) && (nStack == 0)) {
				this.l47_ForLoopLabel = new Label();
				this.mv.visitLabel(this.l47_ForLoopLabel);
				LogManager.getLogger().info("ehehehehheheheheheh {}, {}, {}, {}, {}, {}, {}", type, nLocal, local, nStack, stack);
				;
			}

// KEY:
//>>>>		= header
//>>>>>>>>	= header
//>			= my insertion
//#			= ASM Injection Point
//*			= insertion by compiler for reasons I don't understand
//  ______  = read this line it explains itself
//$			= my hook that has been already inserted

//>>>>		ORIGINAL SOURCE CODE:
//			if (!compiledchunk.isLayerStarted(blockrenderlayer1))
//          {
//              compiledchunk.setLayerStarted(blockrenderlayer1);
//              this.preRenderBlocks(bufferbuilder, blockpos);
//          }
//
//          aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
//      }

//>>>>		MODIFIED SOURCE CODE:
//			if (!compiledchunk.isLayerStarted(blockrenderlayer1))
//          {
//              compiledchunk.setLayerStarted(blockrenderlayer1);
//              this.preRenderBlocks(bufferbuilder, blockpos);
//          }
//			if (!rebuildBlocksEvent.isCanceled()) {
//				final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlockEvent rebuildBlockEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(this.renderGlobal, this.worldView, generator, compiledchunk, blockrendererdispatcher, iblockstate, blockpos$mutableblockpos, bufferbuilder, this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//				if (rebuildBlockEvent.isCanceled()) {
//					for (final BlockRenderLayer blockrenderlayer : BlockRenderLayer.values()) {
//						if (rebuildBlockEvent.getUsedBlockRenderLayers()[blockrenderlayer.ordinal()]) {
//							compiledchunk.setLayerUsed(blockrenderlayer);
//						}
//					}
//				} else {
//					aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
//				}
//			}
//      }

//>>>>>>>>	TURNED INTO BYTECODE INSTRUCTIONS:

//>>>>		ORIGINAL BYTECODE INSTRUCTIONS:
//		   L49
//		    LINENUMBER 197 L49
//		    ALOAD 5
//		    ALOAD 20
//		    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
//		    IFNE L50
//		   L51
//		    LINENUMBER 199 L51
//		    ALOAD 5
//		    ALOAD 20
//		    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)V
//		   L52
//		    LINENUMBER 200 L52
//		    ALOAD 0
//		    ALOAD 22
//		    ALOAD 7
//		    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
//		   L50
//		    LINENUMBER 203 L50
//#		   FRAME APPEND [I net/minecraft/client/renderer/BufferBuilder]
//		    ALOAD 11
//		    ILOAD 21
//		    DUP2
//		    BALOAD
//		    ALOAD 12
//		    ALOAD 15
//		    ALOAD 14
//		    ALOAD 0
//		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//		    ALOAD 22
//		    INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z
//		    IOR
//		    BASTORE
//		   L45
//		    LINENUMBER 188 L45
//		   FRAME CHOP 3
//		    IINC 19 1
//		    GOTO L41
//		   L42
//		    LINENUMBER 206 L42
//		   FRAME CHOP 3
//		    ACONST_NULL
//		    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V

//>>>>		MODIFIED BYTECODE INSTRUCTIONS:
//		   L51
//		    LINENUMBER 199 L51
//		    ALOAD 5
//		    ALOAD 18
//		    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
//		    IFNE L52
//		   L53
//		    LINENUMBER 201 L53
//		    ALOAD 5
//		    ALOAD 18
//		    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)V
//		   L54
//		    LINENUMBER 202 L54
//		    ALOAD 0
//		    ALOAD 23
//		    ALOAD 7
//		    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
//		   L52
//		    LINENUMBER 204 L52
//#		   FRAME APPEND [I net/minecraft/client/renderer/BufferBuilder]
//>		    ALOAD 11
//>		    INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent.isCanceled()Z
//>		    IFNE L47
//>		   L55
//>		    LINENUMBER 205 L55
//>		    ALOAD 0
//>		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
//>		    ALOAD 0
//>		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//>		    ALOAD 4
//>		    ALOAD 5
//>		    ALOAD 13
//>		    ALOAD 16
//>		    ALOAD 14
//>		    ALOAD 23
//>		    ALOAD 0
//>		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
//>		    FLOAD 1
//>		    FLOAD 2
//>		    FLOAD 3
//>		    ALOAD 10
//>		    ALOAD 9
//>		    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;
//>		    ASTORE 24
//>		   L56
//>		    LINENUMBER 206 L56
//>		    ALOAD 24
//>		    INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent.isCanceled()Z
//>		    IFEQ L57
//>		   L58
//>		    LINENUMBER 207 L58
//>		    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
//>		    DUP
//>		    ASTORE 28
//>		    ARRAYLENGTH
//>		    ISTORE 27
//>		    ICONST_0
//>		    ISTORE 26
//>		    GOTO L59
//>		   L60
//>		   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent [Z net/minecraft/client/renderer/BlockRendererDispatcher net/minecraft/util/math/BlockPos$MutableBlockPos java/util/Iterator net/minecraft/block/state/IBlockState net/minecraft/block/Block net/minecraft/util/BlockRenderLayer I I [Lnet/minecraft/util/BlockRenderLayer; I net/minecraft/client/renderer/BufferBuilder cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent T I I [Lnet/minecraft/util/BlockRenderLayer;] []
//>		    ALOAD 28
//>		    ILOAD 26
//>		    AALOAD
//>		    ASTORE 25
//>		   L61
//>		    LINENUMBER 208 L61
//>		    ALOAD 24
//>		    INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent.getUsedBlockRenderLayers()[Z
//>		    ALOAD 25
//>		    INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal()I
//>		    BALOAD
//>		    IFEQ L62
//>		   L63
//>		    LINENUMBER 209 L63
//>		    ALOAD 5
//>		    ALOAD 25
//>		    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerUsed(Lnet/minecraft/util/BlockRenderLayer;)V
//>		   L62
//>		    LINENUMBER 207 L62
//>		   FRAME SAME
//>		    IINC 26 1
//>		   L59
//>		   FRAME SAME
//>		    ILOAD 26
//>		    ILOAD 27
//>		    IF_ICMPLT L60
//>		   L64
//>		    LINENUMBER 212 L64
//>		    GOTO L47
//>		   L57
//>		    LINENUMBER 213 L57
//		   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent [Z net/minecraft/client/renderer/BlockRendererDispatcher net/minecraft/util/math/BlockPos$MutableBlockPos java/util/Iterator net/minecraft/block/state/IBlockState net/minecraft/block/Block net/minecraft/util/BlockRenderLayer I I [Lnet/minecraft/util/BlockRenderLayer; I net/minecraft/client/renderer/BufferBuilder cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent] []
//		    ALOAD 12
//		    ILOAD 22
//		    DUP2
//		    BALOAD
//		    ALOAD 13
//		    ALOAD 16
//		    ALOAD 14
//		    ALOAD 0
//		    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
//		    ALOAD 23
//		    INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z
//		    IOR
//		    BASTORE
//		   L47
//		    LINENUMBER 190 L47
//		   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent [Z net/minecraft/client/renderer/BlockRendererDispatcher net/minecraft/util/math/BlockPos$MutableBlockPos java/util/Iterator net/minecraft/block/state/IBlockState net/minecraft/block/Block T I I [Lnet/minecraft/util/BlockRenderLayer;] []
//		    IINC 19 1
//		   L43
//		   FRAME SAME
//		    ILOAD 19
//		    ILOAD 20
//		    IF_ICMPLT L44
//		   L65
//		    LINENUMBER 218 L65
//		    ACONST_NULL
//		    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V

//>>>>>>>>	TURNED INTO ASM INSTRUCTIONS:

//>>>>		ORIGINAL ASM INSTRUCTIONS:
//			Label l49 = new Label();
//			mv.visitLabel(l49);
//			mv.visitLineNumber(197, l49);
//			mv.visitVarInsn(ALOAD, 5);
//			mv.visitVarInsn(ALOAD, 20);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
//			Label l50 = new Label();
//			mv.visitJumpInsn(IFNE, l50);
//			Label l51 = new Label();
//			mv.visitLabel(l51);
//			mv.visitLineNumber(199, l51);
//			mv.visitVarInsn(ALOAD, 5);
//			mv.visitVarInsn(ALOAD, 20);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
//			Label l52 = new Label();
//			mv.visitLabel(l52);
//			mv.visitLineNumber(200, l52);
//			mv.visitVarInsn(ALOAD, 0);
//			mv.visitVarInsn(ALOAD, 22);
//			mv.visitVarInsn(ALOAD, 7);
//			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "preRenderBlocks", "(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V", false);
//			mv.visitLabel(l50);
//			mv.visitLineNumber(203, l50);
//#			mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder"}, 0, null);
//			mv.visitVarInsn(ALOAD, 11);
//			mv.visitVarInsn(ILOAD, 21);
//			mv.visitInsn(DUP2);
//			mv.visitInsn(BALOAD);
//			mv.visitVarInsn(ALOAD, 12);
//			mv.visitVarInsn(ALOAD, 15);
//			mv.visitVarInsn(ALOAD, 14);
//			mv.visitVarInsn(ALOAD, 0);
//			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//			mv.visitVarInsn(ALOAD, 22);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/BlockRendererDispatcher", "renderBlock", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z", false);
//			mv.visitInsn(IOR);
//			mv.visitInsn(BASTORE);
//			mv.visitLabel(l45);
//			mv.visitLineNumber(188, l45);
//			mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
//			mv.visitIincInsn(19, 1);
//			mv.visitJumpInsn(GOTO, l41);
//			mv.visitLabel(l42);
//			mv.visitLineNumber(206, l42);
//			mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
//			mv.visitInsn(ACONST_NULL);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);

//>>>>		MODIFIED ASM INSTRUCTIONS:
//			Label l51 = new Label();
//			mv.visitLabel(l51);
//			mv.visitLineNumber(199, l51);
//			mv.visitVarInsn(ALOAD, 5);
//			mv.visitVarInsn(ALOAD, 18);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
//			Label l52 = new Label();
//			mv.visitJumpInsn(IFNE, l52);
//			Label l53 = new Label();
//			mv.visitLabel(l53);
//			mv.visitLineNumber(201, l53);
//			mv.visitVarInsn(ALOAD, 5);
//			mv.visitVarInsn(ALOAD, 18);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
//			Label l54 = new Label();
//			mv.visitLabel(l54);
//			mv.visitLineNumber(202, l54);
//			mv.visitVarInsn(ALOAD, 0);
//			mv.visitVarInsn(ALOAD, 23);
//			mv.visitVarInsn(ALOAD, 7);
//			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "preRenderBlocks", "(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V", false);
//			mv.visitLabel(l52);
//			mv.visitLineNumber(204, l52);
//#			mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder"}, 0, null);
//>			mv.visitVarInsn(ALOAD, 11);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "isCanceled", "()Z", false);
//>			mv.visitJumpInsn(IFNE, l47);
//>			Label l55 = new Label();
//>			mv.visitLabel(l55);
//>			mv.visitLineNumber(205, l55);
//>			mv.visitVarInsn(ALOAD, 0);
//>			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
//>			mv.visitVarInsn(ALOAD, 0);
//>			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//>			mv.visitVarInsn(ALOAD, 4);
//>			mv.visitVarInsn(ALOAD, 5);
//>			mv.visitVarInsn(ALOAD, 13);
//>			mv.visitVarInsn(ALOAD, 16);
//>			mv.visitVarInsn(ALOAD, 14);
//>			mv.visitVarInsn(ALOAD, 23);
//>			mv.visitVarInsn(ALOAD, 0);
//>			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
//>			mv.visitVarInsn(FLOAD, 1);
//>			mv.visitVarInsn(FLOAD, 2);
//>			mv.visitVarInsn(FLOAD, 3);
//>			mv.visitVarInsn(ALOAD, 10);
//>			mv.visitVarInsn(ALOAD, 9);
//>			mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlockEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent;", false);
//>			mv.visitVarInsn(ASTORE, 24);
//>			Label l56 = new Label();
//>			mv.visitLabel(l56);
//>			mv.visitLineNumber(206, l56);
//>			mv.visitVarInsn(ALOAD, 24);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "isCanceled", "()Z", false);
//>			Label l57 = new Label();
//>			mv.visitJumpInsn(IFEQ, l57);
//>			Label l58 = new Label();
//>			mv.visitLabel(l58);
//>			mv.visitLineNumber(207, l58);
//>			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
//>			mv.visitInsn(DUP);
//>			mv.visitVarInsn(ASTORE, 28);
//>			mv.visitInsn(ARRAYLENGTH);
//>			mv.visitVarInsn(ISTORE, 27);
//>			mv.visitInsn(ICONST_0);
//>			mv.visitVarInsn(ISTORE, 26);
//>			Label l59 = new Label();
//>			mv.visitJumpInsn(GOTO, l59);
//>			Label l60 = new Label();
//>			mv.visitLabel(l60);
//>			mv.visitFrame(Opcodes.F_FULL, 29, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;"}, 0, new Object[] {});
//>			mv.visitVarInsn(ALOAD, 28);
//>			mv.visitVarInsn(ILOAD, 26);
//>			mv.visitInsn(AALOAD);
//>			mv.visitVarInsn(ASTORE, 25);
//>			Label l61 = new Label();
//>			mv.visitLabel(l61);
//>			mv.visitLineNumber(208, l61);
//>			mv.visitVarInsn(ALOAD, 24);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false);
//>			mv.visitVarInsn(ALOAD, 25);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
//>			mv.visitInsn(BALOAD);
//>			Label l62 = new Label();
//>			mv.visitJumpInsn(IFEQ, l62);
//>			Label l63 = new Label();
//>			mv.visitLabel(l63);
//>			mv.visitLineNumber(209, l63);
//>			mv.visitVarInsn(ALOAD, 5);
//>			mv.visitVarInsn(ALOAD, 25);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerUsed", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
//>			mv.visitLabel(l62);
//>			mv.visitLineNumber(207, l62);
//>			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//>			mv.visitIincInsn(26, 1);
//>			mv.visitLabel(l59);
//>			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//>			mv.visitVarInsn(ILOAD, 26);
//>			mv.visitVarInsn(ILOAD, 27);
//>			mv.visitJumpInsn(IF_ICMPLT, l60);
//>			Label l64 = new Label();
//>			mv.visitLabel(l64);
//>			mv.visitLineNumber(212, l64);
//>			mv.visitJumpInsn(GOTO, l47);
//>			mv.visitLabel(l57);
//>			mv.visitLineNumber(213, l57);
//>			mv.visitFrame(Opcodes.F_FULL, 25, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", "net/minecraft/util/BlockRenderLayer", Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent"}, 0, new Object[] {});
//			mv.visitVarInsn(ALOAD, 12);
//			mv.visitVarInsn(ILOAD, 22);
//			mv.visitInsn(DUP2);
//			mv.visitInsn(BALOAD);
//			mv.visitVarInsn(ALOAD, 13);
//			mv.visitVarInsn(ALOAD, 16);
//			mv.visitVarInsn(ALOAD, 14);
//			mv.visitVarInsn(ALOAD, 0);
//			mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
//			mv.visitVarInsn(ALOAD, 23);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/BlockRendererDispatcher", "renderBlock", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z", false);
//			mv.visitInsn(IOR);
//			mv.visitInsn(BASTORE);
//			mv.visitLabel(l47);
//			mv.visitLineNumber(190, l47);
//			mv.visitFrame(Opcodes.F_FULL, 22, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;"}, 0, new Object[] {});
//			mv.visitIincInsn(19, 1);
//			mv.visitLabel(l43);
//			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
//			mv.visitVarInsn(ILOAD, 19);
//			mv.visitVarInsn(ILOAD, 20);
//			mv.visitJumpInsn(IF_ICMPLT, l44);
//			Label l65 = new Label();
//			mv.visitLabel(l65);
//			mv.visitLineNumber(218, l65);
//			mv.visitInsn(ACONST_NULL);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);

//			FRAME APPEND [I net/minecraft/client/renderer/BufferBuilder]
//			mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder"}, 0, null);
			LogManager.getLogger().info("{}, {}, {}, {}, {}, {}, {}", type, nLocal, local, nStack, stack);
			if ((type == Opcodes.F_APPEND) && (nLocal == 2) && (local.length >= 2) && (local[0] == Opcodes.INTEGER) && (local[1].equals("net/minecraft/client/renderer/BufferBuilder")) && (nStack == 0)) {

				super.visitFrame(type, nLocal, local, nStack, stack);

				final int ALOAD = Opcodes.ALOAD;
				final int FLOAD = Opcodes.FLOAD;
				final int GETFIELD = Opcodes.GETFIELD;

				this.mv.visitVarInsn(ALOAD, 13);
				this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "isCanceled", "()Z", false);
//				this.mv.visitJumpInsn(Opcodes.IFNE, this.l47_ForLoopLabel);

//				this.mv.visitJumpInsn(Opcodes.GOTO, this.l47_ForLoopLabel);
//				this.mv.visitLabel(this.l47);
//				this.mv.visitFrame(Opcodes.F_FULL, 22, new Object[] { "net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet",
//						"cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkBlocksEvent", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "net/minecraft/util/math/BlockPos$MutableBlockPos", "java/util/Iterator", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[Lnet/minecraft/util/BlockRenderLayer;" }, 0, new Object[] {});
//				this.mv.visitIincInsn(19, 1);
				return;

			}

			super.visitFrame(type, nLocal, local, nStack, stack);
		}

//		@Override
//		public void visitEnd() {
//
//			// loop through instruction to find NEWARRAY T_BOOLEAN
//			// remove instruction above
//			// remove instruction below
//			// remove instruction
//
//			LogManager.getLogger().info("injecting RebuildChunkBlocksEvent hook logic part 2 ...");
//			LogManager.getLogger().info("injecting RebuildChunkBlockEvent hook...");
//
//			LogManager.getLogger().info("finished injecting RebuildChunkBlocksEvent hook logic");
//			LogManager.getLogger().info("finished injecting RebuildChunkBlockEvent hook");
//
//			super.visitEnd();
//		}

	}

}
