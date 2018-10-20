package cadiboo.renderchunkrebuildchunkhooks;

import java.io.PrintWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceClassVisitor;

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

		final TraceClassVisitor classVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));

		final RebuildChunkHooksClassVisitor modifyingClassVisitor = new RebuildChunkHooksClassVisitor(classVisitor);

//		ClassReader reader = new ClassReader("Target");
//		ClassWriter writer = new ClassWriter(reader, 0);
//		TraceClassVisitor printer = new TraceClassVisitor(writer,
//		        new PrintWriter(System.getProperty("java.io.tmpdir") + File.separator + "Target.log"));
//		ClassAdapter adapter = new ClassAdapter(printer);
//		reader.accept(adapter, 0);
//		byte[] b = writer.toByteArray(); // The modified bytecode

		LogManager.getLogger().info("Attempting to make hooks...");

		try {
			classReader.accept(modifyingClassVisitor, 0);

			LogManager.getLogger().info("Made hooks!");
			return classWriter.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("FAILED to make hooks!!!");
		}

		return basicClass;
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

			return new MethodNode(Opcodes.ASM5);

//			return new RebuildChunkHooksMethodVisitor(originalVisitor);

		}

	}

	public class RebuildChunkHooksMethodVisitor extends MethodNode {

		@Override
		public void visitEnd() {
			LogManager.getLogger().info("eh??");
			super.visitEnd();
		}

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
			super(Opcodes.ASM5);
			this.mv = mv;
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
//>			    INVOKESTATIC cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent;
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
//>				mv.visitMethodInsn(INVOKESTATIC, "cadiboo/renderchunkrebuildchunkhooks/hooks/RenderChunkRebuildChunkHooksHooks", "onRebuildChunkBlocksEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent;", false);
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
						"(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Ljava/lang/Iterable;Lnet/minecraft/client/renderer/BlockRendererDispatcher;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFFLjava/util/HashSet;Lnet/minecraft/client/renderer/chunk/VisGraph;)Lcadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent;",
						false);
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

//		@Override
		public void visitEnd2() {

			LogManager.getLogger().info("injecting RebuildChunkBlocksEvent hook logic part 1...");

// KEY:
//>>>>		= header
//>>>>>>>>	= header
//>			= my insertion
//#			= ASM Injection Point
//*			= insertion by compiler for reasons I don't understand
//  ______  = read this line it explains itself
//$			= my hook that has been already inserted
//<			= removed instructions

//>>>>		ORIGINAL SOURCE CODE:
//			if (!this.worldView.isEmpty())
//	        {
//	            ++renderChunksUpdated;
//	            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//	            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		ORIGINAL SOURCE CODE (MODIFIED BY ALREADY CREATED HOOK):
//			if (!this.worldView.isEmpty()) {
//$				final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//				++renderChunksUpdated;
//				boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
//				final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		MODIFIED SOURCE CODE:
//			if (!this.worldView.isEmpty()) {
//				++renderChunksUpdated;
//>				final boolean[] aboolean = rebuildBlocksEvent.getUsedBlockRenderLayers();
//				final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>		MODIFIED SOURCE CODE (MODIFIED BY ALREADY CREATED HOOK):
//			if (!this.worldView.isEmpty()) {
//$				final cadiboo.renderchunkrebuildchunkhooks.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = cadiboo.renderchunkrebuildchunkhooks.hooks.RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(this.renderGlobal, this.worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1), Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);
//				++renderChunksUpdated;
//>				final boolean[] aboolean = rebuildBlocksEvent.getUsedBlockRenderLayers();
//				final BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

//>>>>>>>>	TURNED INTO BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):

//>>>>		ORIGINAL BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			L23
//		    LINENUMBER 139 L23
//		    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		    ICONST_1
//		    IADD
//		    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		   L24
//		    LINENUMBER 140 L24
//<		    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
//<		    ARRAYLENGTH
//<#	    NEWARRAY T_BOOLEAN
//		    ASTORE 12
//		   L25
//		    LINENUMBER 141 L25
//		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//		    ASTORE 13

//>>>>		MODIFIED BYTECODE INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			L23
//		    LINENUMBER 139 L23
//		    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		    ICONST_1
//		    IADD
//		    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
//		   L24
//		    LINENUMBER 140 L24
//>		    ALOAD 11
//>		    INVOKEVIRTUAL cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent.getUsedBlockRenderLayers()[Z
//		    ASTORE 12
//		   L25
//		    LINENUMBER 141 L25
//		    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
//		    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
//		    ASTORE 13

//>>>>>>>>	TURNED INTO ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):

//>>>>		ORIGINAL ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			Label l23 = new Label();
//			mv.visitLabel(l23);
//			mv.visitLineNumber(139, l23);
//			mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			mv.visitInsn(ICONST_1);
//			mv.visitInsn(IADD);
//			mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			Label l24 = new Label();
//			mv.visitLabel(l24);
//			mv.visitLineNumber(140, l24);
//<			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
//<			mv.visitInsn(ARRAYLENGTH);
//<			mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
//			mv.visitVarInsn(ASTORE, 12);
//			Label l25 = new Label();
//			mv.visitLabel(l25);
//			mv.visitLineNumber(141, l25);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//			mv.visitVarInsn(ASTORE, 13);

//>>>>		MODIFIED ASM INSTRUCTIONS (MODIFIED BY ALREADY CREATED HOOK):
//			Label l23 = new Label();
//			mv.visitLabel(l23);
//			mv.visitLineNumber(139, l23);
//			mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			mv.visitInsn(ICONST_1);
//			mv.visitInsn(IADD);
//			mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
//			Label l24 = new Label();
//			mv.visitLabel(l24);
//			mv.visitLineNumber(140, l24);
//>			mv.visitVarInsn(ALOAD, 11);
//>			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false);
//			mv.visitVarInsn(ASTORE, 12);
//			Label l25 = new Label();
//			mv.visitLabel(l25);
//			mv.visitLineNumber(141, l25);
//			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
//			mv.visitVarInsn(ASTORE, 13);

//			Label l24 = new Label();
//			mv.visitLabel(l24);
//			mv.visitLineNumber(140, l24);
//			mv.visitVarInsn(ALOAD, 11);
//			mv.visitMethodInsn(INVOKEVIRTUAL, "cadiboo/renderchunkrebuildchunkhooks/event/RebuildChunkEvent$RebuildChunkBlocksEvent", "getUsedBlockRenderLayers", "()[Z", false);
//			mv.visitVarInsn(ASTORE, 12);

			// loop through instruction to find NEWARRAY T_BOOLEAN
			// remove instruction above
			// remove instruction below
			// remove instruction

			LogManager.getLogger().info("finished injecting RebuildChunkBlocksEvent hook logic part 1");

			LogManager.getLogger().info("injecting RebuildChunkBlocksEvent hook logic part 2 ...");
			LogManager.getLogger().info("injecting RebuildChunkBlockEvent hook...");

			LogManager.getLogger().info("finished injecting RebuildChunkBlocksEvent hook logic");
			LogManager.getLogger().info("finished injecting RebuildChunkBlockEvent hook");

			super.visitEnd();
		}

	}

}
