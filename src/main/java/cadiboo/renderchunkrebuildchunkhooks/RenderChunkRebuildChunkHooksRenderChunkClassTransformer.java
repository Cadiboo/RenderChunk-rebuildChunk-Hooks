package cadiboo.renderchunkrebuildchunkhooks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import net.minecraft.launchwrapper.IClassTransformer;

public class RenderChunkRebuildChunkHooksRenderChunkClassTransformer implements IClassTransformer {

	public static final int ALOAD = Opcodes.ALOAD;
	public static final int INVOKESTATIC = Opcodes.INVOKESTATIC;
	public static final int GETFIELD = Opcodes.GETFIELD;
	public static final int FLOAD = Opcodes.FLOAD;
	public static final int INVOKEVIRTUAL = Opcodes.INVOKEVIRTUAL;
	public static final int IFEQ = Opcodes.IFEQ;
	public static final int RETURN = Opcodes.RETURN;
	public static final int F_SAME = Opcodes.F_SAME;
	public static final int GETSTATIC = Opcodes.GETSTATIC;

	// RAW JAVA METHOD \/
	//@formatter:off
	//	public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator)
	//    {
	//        CompiledChunk compiledchunk = new CompiledChunk();
	//        int i = 1;
	//        BlockPos blockpos = this.position;
	//        BlockPos blockpos1 = blockpos.add(15, 15, 15);
	//        generator.getLock().lock();
	//
	//        try
	//        {
	//            if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING)
	//            {
	//                return;
	//            }
	//
	//            generator.setCompiledChunk(compiledchunk);
	//        }
	//        finally
	//        {
	//            generator.getLock().unlock();
	//        }
	//
	//        VisGraph lvt_9_1_ = new VisGraph();
	//        HashSet lvt_10_1_ = Sets.newHashSet();
	//
	//        if (!this.worldView.isEmpty())
	//        {
	//            ++renderChunksUpdated;
	//            boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
	//            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
	//
	//            for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos1))
	//            {
	//                IBlockState iblockstate = this.worldView.getBlockState(blockpos$mutableblockpos);
	//                Block block = iblockstate.getBlock();
	//
	//                if (iblockstate.isOpaqueCube())
	//                {
	//                    lvt_9_1_.setOpaqueCube(blockpos$mutableblockpos);
	//                }
	//
	//                if (block.hasTileEntity(iblockstate))
	//                {
	//                    TileEntity tileentity = this.worldView.getTileEntity(blockpos$mutableblockpos, Chunk.EnumCreateEntityType.CHECK);
	//
	//                    if (tileentity != null)
	//                    {
	//                        TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.<TileEntity>getRenderer(tileentity);
	//
	//                        if (tileentityspecialrenderer != null)
	//                        {
	//
	//                            if (tileentityspecialrenderer.isGlobalRenderer(tileentity))
	//                            {
	//                                lvt_10_1_.add(tileentity);
	//                            }
	//                            else compiledchunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
	//                        }
	//                    }
	//                }
	//
	//                for(BlockRenderLayer blockrenderlayer1 : BlockRenderLayer.values()) {
	//                     if(!block.canRenderInLayer(iblockstate, blockrenderlayer1)) continue;
	//                     net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
	//                int j = blockrenderlayer1.ordinal();
	//
	//                if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	//                {
	//                    BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(j);
	//
	//                    if (!compiledchunk.isLayerStarted(blockrenderlayer1))
	//                    {
	//                        compiledchunk.setLayerStarted(blockrenderlayer1);
	//                        this.preRenderBlocks(bufferbuilder, blockpos);
	//                    }
	//
	//                    aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
	//                }
	//                }
	//                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
	//            }
	//
	//            for (BlockRenderLayer blockrenderlayer : BlockRenderLayer.values())
	//            {
	//                if (aboolean[blockrenderlayer.ordinal()])
	//                {
	//                    compiledchunk.setLayerUsed(blockrenderlayer);
	//                }
	//
	//                if (compiledchunk.isLayerStarted(blockrenderlayer))
	//                {
	//                    this.postRenderBlocks(blockrenderlayer, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer), compiledchunk);
	//                }
	//            }
	//        }
	//
	//        compiledchunk.setVisibility(lvt_9_1_.computeVisibility());
	//        this.lockCompileTask.lock();
	//
	//        try
	//        {
	//            Set<TileEntity> set = Sets.newHashSet(lvt_10_1_);
	//            Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
	//            set.removeAll(this.setTileEntities);
	//            set1.removeAll(lvt_10_1_);
	//            this.setTileEntities.clear();
	//            this.setTileEntities.addAll(lvt_10_1_);
	//            this.renderGlobal.updateTileEntities(set1, set);
	//        }
	//        finally
	//        {
	//            this.lockCompileTask.unlock();
	//        }
	//    }
	//@formatter:on

	// DESIRED JAVA METHOD \/ (.patch FORMAT)
	//@formatter:off
	//	public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator)
	//    {
	//        CompiledChunk compiledchunk = new CompiledChunk();
	//        int i = 1;
	//        BlockPos blockpos = this.position;
	//        BlockPos blockpos1 = blockpos.add(15, 15, 15);
	//        generator.getLock().lock();
	//
	//        try
	//        {
	//            if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING)
	//            {
	//                return;
	//            }
	//
	//            generator.setCompiledChunk(compiledchunk);
	//        }
	//        finally
	//        {
	//            generator.getLock().unlock();
	//        }

	//+       if (net.minecraftforge.client.ForgeHooksClient.onRebuildChunkEvent(renderGlobal, worldView, generator, compiledchunk, this.position, x, y, z).isCanceled()) return;

	//
	//        VisGraph lvt_9_1_ = new VisGraph();
	//        HashSet lvt_10_1_ = Sets.newHashSet();
	//
	//        if (!this.worldView.isEmpty())
	//        {

	//+           final net.minecraftforge.client.event.RebuildChunkEvent.RebuildChunkBlocksEvent rebuildBlocksEvent = net.minecraftforge.client.ForgeHooksClient.onRebuildChunkBlocksEvent(renderGlobal, worldView, generator, compiledchunk, BlockPos.getAllInBoxMutable(blockpos, blockpos1),Minecraft.getMinecraft().getBlockRendererDispatcher(), this.position, x, y, z, lvt_10_1_, lvt_9_1_);

	//            ++renderChunksUpdated;

	//-           boolean[] aboolean = new boolean[BlockRenderLayer.values().length];
	//+           boolean[] aboolean = rebuildBlocksEvent.getUsedBlockRenderLayers();

	//            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
	//
	//            for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos1))
	//            {
	//                IBlockState iblockstate = this.worldView.getBlockState(blockpos$mutableblockpos);
	//                Block block = iblockstate.getBlock();
	//
	//                if (iblockstate.isOpaqueCube())
	//                {
	//                    lvt_9_1_.setOpaqueCube(blockpos$mutableblockpos);
	//                }
	//
	//                if (block.hasTileEntity(iblockstate))
	//                {
	//                    TileEntity tileentity = this.worldView.getTileEntity(blockpos$mutableblockpos, Chunk.EnumCreateEntityType.CHECK);
	//
	//                    if (tileentity != null)
	//                    {
	//                        TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.<TileEntity>getRenderer(tileentity);
	//
	//                        if (tileentityspecialrenderer != null)
	//                        {
	//
	//                            if (tileentityspecialrenderer.isGlobalRenderer(tileentity))
	//                            {
	//                                lvt_10_1_.add(tileentity);
	//                            }
	//                            else compiledchunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
	//                        }
	//                    }
	//                }
	//
	//                for(BlockRenderLayer blockrenderlayer1 : BlockRenderLayer.values()) {
	//                     if(!block.canRenderInLayer(iblockstate, blockrenderlayer1)) continue;
	//                     net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockrenderlayer1);
	//                int j = blockrenderlayer1.ordinal();
	//
	//                if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE)
	//                {
	//                    BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(j);
	//
	//                    if (!compiledchunk.isLayerStarted(blockrenderlayer1))
	//                    {
	//                        compiledchunk.setLayerStarted(blockrenderlayer1);
	//                        this.preRenderBlocks(bufferbuilder, blockpos);
	//                    }
	//

		//TODO: add renderBlock event (for singular block);

	//+                   if (!rebuildBlocksEvent.isCanceled())

	//                    aboolean[j] |= blockrendererdispatcher.renderBlock(iblockstate, blockpos$mutableblockpos, this.worldView, bufferbuilder);
	//                }
	//                }
	//                net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
	//            }
	//
	//            for (BlockRenderLayer blockrenderlayer : BlockRenderLayer.values())
	//            {
	//                if (aboolean[blockrenderlayer.ordinal()])
	//                {
	//                    compiledchunk.setLayerUsed(blockrenderlayer);
	//                }
	//
	//                if (compiledchunk.isLayerStarted(blockrenderlayer))
	//                {
	//                    this.postRenderBlocks(blockrenderlayer, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer), compiledchunk);
	//                }
	//            }
	//        }
	//
	//        compiledchunk.setVisibility(lvt_9_1_.computeVisibility());
	//        this.lockCompileTask.lock();
	//
	//        try
	//        {
	//            Set<TileEntity> set = Sets.newHashSet(lvt_10_1_);
	//            Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
	//            set.removeAll(this.setTileEntities);
	//            set1.removeAll(lvt_10_1_);
	//            this.setTileEntities.clear();
	//            this.setTileEntities.addAll(lvt_10_1_);
	//            this.renderGlobal.updateTileEntities(set1, set);
	//        }
	//        finally
	//        {
	//            this.lockCompileTask.unlock();
	//        }
	//    }
	//@formatter:on

	// RAW BYTECODE METHOD \/
	//@formatter:off
	//	  // access flags 0x1
	//	  public rebuildChunk(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V
	//	    TRYCATCHBLOCK L0 L1 L2
	//	    TRYCATCHBLOCK L3 L4 L2
	//	    TRYCATCHBLOCK L2 L5 L2
	//	    TRYCATCHBLOCK L6 L7 L8
	//	    TRYCATCHBLOCK L8 L9 L8
	//	   L10
	//	    LINENUMBER 129 L10
	//	    NEW net/minecraft/client/renderer/chunk/CompiledChunk
	//	    DUP
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/CompiledChunk.<init>()V
	//	    ASTORE 5
	//	   L11
	//	    LINENUMBER 130 L11
	//	    ICONST_1
	//	    ISTORE 6
	//	   L12
	//	    LINENUMBER 131 L12
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
	//	    ASTORE 7
	//	   L13
	//	    LINENUMBER 132 L13
	//	    ALOAD 7
	//	    BIPUSH 15
	//	    BIPUSH 15
	//	    BIPUSH 15
	//	    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.add(III)Lnet/minecraft/util/math/BlockPos;
	//	    ASTORE 8
	//	   L14
	//	    LINENUMBER 133 L14
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.lock()V
	//	   L0
	//	    LINENUMBER 137 L0
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getStatus()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
	//	    GETSTATIC net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status.COMPILING : Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
	//	    IF_ACMPEQ L3
	//	   L1
	//	    LINENUMBER 146 L1
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L15
	//	    LINENUMBER 139 L15
	//	    RETURN
	//	   L3
	//	    LINENUMBER 142 L3
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos] []
	//	    ALOAD 4
	//	    ALOAD 5
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.setCompiledChunk(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
	//	   L4
	//	    LINENUMBER 146 L4
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L16
	//	    LINENUMBER 147 L16
	//	    GOTO L17
	//	   L2
	//	    LINENUMBER 146 L2
	//	   FRAME SAME1 java/lang/Throwable
	//	    ASTORE 9
	//	   L5
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L18
	//	    LINENUMBER 147 L18
	//	    ALOAD 9
	//	    ATHROW

	//	   L17
	//	    LINENUMBER 149 L17
	//	   FRAME SAME
	//	    NEW net/minecraft/client/renderer/chunk/VisGraph
	//	    DUP
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/VisGraph.<init>()V
	//	    ASTORE 9
	//	   L19
	//	    LINENUMBER 150 L19
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet()Ljava/util/HashSet;
	//	    ASTORE 10
	//	   L20
	//	    LINENUMBER 152 L20
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.isEmpty()Z
	//	    IFNE L21
	//	   L22
	//	    LINENUMBER 154 L22
	//	    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
	//	    ICONST_1
	//	    IADD
	//	    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
	//	   L23
	//	    LINENUMBER 155 L23
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ARRAYLENGTH
	//	    NEWARRAY T_BOOLEAN
	//	    ASTORE 11
	//	   L24
	//	    LINENUMBER 156 L24
	//	    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
	//	    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
	//	    ASTORE 12
	//	   L25
	//	    LINENUMBER 158 L25
	//	    ALOAD 7
	//	    ALOAD 8
	//	    INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
	//	    INVOKEINTERFACE java/lang/Iterable.iterator()Ljava/util/Iterator;
	//	    ASTORE 13
	//	   L26
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet [Z net/minecraft/client/renderer/BlockRendererDispatcher java/util/Iterator] []
	//	    ALOAD 13
	//	    INVOKEINTERFACE java/util/Iterator.hasNext()Z
	//	    IFEQ L27
	//	    ALOAD 13
	//	    INVOKEINTERFACE java/util/Iterator.next()Ljava/lang/Object;
	//	    CHECKCAST net/minecraft/util/math/BlockPos$MutableBlockPos
	//	    ASTORE 14
	//	   L28
	//	    LINENUMBER 160 L28
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 14
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
	//	    ASTORE 15
	//	   L29
	//	    LINENUMBER 161 L29
	//	    ALOAD 15
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.getBlock()Lnet/minecraft/block/Block;
	//	    ASTORE 16
	//	   L30
	//	    LINENUMBER 163 L30
	//	    ALOAD 15
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.isOpaqueCube()Z
	//	    IFEQ L31
	//	   L32
	//	    LINENUMBER 165 L32
	//	    ALOAD 9
	//	    ALOAD 14
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/VisGraph.setOpaqueCube(Lnet/minecraft/util/math/BlockPos;)V
	//	   L31
	//	    LINENUMBER 168 L31
	//	   FRAME APPEND [net/minecraft/util/math/BlockPos$MutableBlockPos net/minecraft/block/state/IBlockState net/minecraft/block/Block]
	//	    ALOAD 16
	//	    ALOAD 15
	//	    INVOKEVIRTUAL net/minecraft/block/Block.hasTileEntity(Lnet/minecraft/block/state/IBlockState;)Z
	//	    IFEQ L33
	//	   L34
	//	    LINENUMBER 170 L34
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 14
	//	    GETSTATIC net/minecraft/world/chunk/Chunk$EnumCreateEntityType.CHECK : Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.getTileEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;)Lnet/minecraft/tileentity/TileEntity;
	//	    ASTORE 17
	//	   L35
	//	    LINENUMBER 172 L35
	//	    ALOAD 17
	//	    IFNULL L33
	//	   L36
	//	    LINENUMBER 174 L36
	//	    GETSTATIC net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.getRenderer(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;
	//	    ASTORE 18
	//	   L37
	//	    LINENUMBER 176 L37
	//	    ALOAD 18
	//	    IFNULL L33
	//	   L38
	//	    LINENUMBER 179 L38
	//	    ALOAD 18
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer.isGlobalRenderer(Lnet/minecraft/tileentity/TileEntity;)Z
	//	    IFEQ L39
	//	   L40
	//	    LINENUMBER 181 L40
	//	    ALOAD 10
	//	    ALOAD 17
	//	    INVOKEVIRTUAL java/util/HashSet.add(Ljava/lang/Object;)Z
	//	    POP
	//	    GOTO L33
	//	   L39
	//	    LINENUMBER 183 L39
	//	   FRAME APPEND [net/minecraft/tileentity/TileEntity net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer]
	//	    ALOAD 5
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.addTileEntity(Lnet/minecraft/tileentity/TileEntity;)V
	//	   L33
	//	    LINENUMBER 188 L33
	//	   FRAME CHOP 2
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ASTORE 17
	//	    ALOAD 17
	//	    ARRAYLENGTH
	//	    ISTORE 18
	//	    ICONST_0
	//	    ISTORE 19
	//	   L41
	//	   FRAME APPEND [[Lnet/minecraft/util/BlockRenderLayer; I I]
	//	    ILOAD 19
	//	    ILOAD 18
	//	    IF_ICMPGE L42
	//	    ALOAD 17
	//	    ILOAD 19
	//	    AALOAD
	//	    ASTORE 20
	//	   L43
	//	    LINENUMBER 189 L43
	//	    ALOAD 16
	//	    ALOAD 15
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFNE L44
	//	    GOTO L45
	//	   L44
	//	    LINENUMBER 190 L44
	//	   FRAME APPEND [net/minecraft/util/BlockRenderLayer]
	//	    ALOAD 20
	//	    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L46
	//	    LINENUMBER 191 L46
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal()I
	//	    ISTORE 21
	//	   L47
	//	    LINENUMBER 193 L47
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/block/Block.getDefaultState()Lnet/minecraft/block/state/IBlockState;
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.getRenderType()Lnet/minecraft/util/EnumBlockRenderType;
	//	    GETSTATIC net/minecraft/util/EnumBlockRenderType.INVISIBLE : Lnet/minecraft/util/EnumBlockRenderType;
	//	    IF_ACMPEQ L45
	//	   L48
	//	    LINENUMBER 195 L48
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
	//	    ILOAD 21
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayerId(I)Lnet/minecraft/client/renderer/BufferBuilder;
	//	    ASTORE 22
	//	   L49
	//	    LINENUMBER 197 L49
	//	    ALOAD 5
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFNE L50
	//	   L51
	//	    LINENUMBER 199 L51
	//	    ALOAD 5
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L52
	//	    LINENUMBER 200 L52
	//	    ALOAD 0
	//	    ALOAD 22
	//	    ALOAD 7
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
	//	   L50
	//	    LINENUMBER 203 L50
	//	   FRAME APPEND [I net/minecraft/client/renderer/BufferBuilder]
	//	    ALOAD 11
	//	    ILOAD 21
	//	    DUP2
	//	    BALOAD
	//	    ALOAD 12
	//	    ALOAD 15
	//	    ALOAD 14
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 22
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z
	//	    IOR
	//	    BASTORE
	//	   L45
	//	    LINENUMBER 188 L45
	//	   FRAME CHOP 3
	//	    IINC 19 1
	//	    GOTO L41
	//	   L42
	//	    LINENUMBER 206 L42
	//	   FRAME CHOP 3
	//	    ACONST_NULL
	//	    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L53
	//	    LINENUMBER 207 L53
	//	    GOTO L26
	//	   L27
	//	    LINENUMBER 209 L27
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet [Z net/minecraft/client/renderer/BlockRendererDispatcher] []
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ASTORE 13
	//	    ALOAD 13
	//	    ARRAYLENGTH
	//	    ISTORE 14
	//	    ICONST_0
	//	    ISTORE 15
	//	   L54
	//	   FRAME APPEND [[Lnet/minecraft/util/BlockRenderLayer; I I]
	//	    ILOAD 15
	//	    ILOAD 14
	//	    IF_ICMPGE L21
	//	    ALOAD 13
	//	    ILOAD 15
	//	    AALOAD
	//	    ASTORE 16
	//	   L55
	//	    LINENUMBER 211 L55
	//	    ALOAD 11
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal()I
	//	    BALOAD
	//	    IFEQ L56
	//	   L57
	//	    LINENUMBER 213 L57
	//	    ALOAD 5
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerUsed(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L56
	//	    LINENUMBER 216 L56
	//	   FRAME APPEND [net/minecraft/util/BlockRenderLayer]
	//	    ALOAD 5
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFEQ L58
	//	   L59
	//	    LINENUMBER 218 L59
	//	    ALOAD 0
	//	    ALOAD 16
	//	    FLOAD 1
	//	    FLOAD 2
	//	    FLOAD 3
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayer(Lnet/minecraft/util/BlockRenderLayer;)Lnet/minecraft/client/renderer/BufferBuilder;
	//	    ALOAD 5
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.postRenderBlocks(Lnet/minecraft/util/BlockRenderLayer;FFFLnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
	//	   L58
	//	    LINENUMBER 209 L58
	//	   FRAME CHOP 1
	//	    IINC 15 1
	//	    GOTO L54
	//	   L21
	//	    LINENUMBER 223 L21
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet] []
	//	    ALOAD 5
	//	    ALOAD 9
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/VisGraph.computeVisibility()Lnet/minecraft/client/renderer/chunk/SetVisibility;
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setVisibility(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
	//	   L60
	//	    LINENUMBER 224 L60
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.lock()V
	//	   L6
	//	    LINENUMBER 228 L6
	//	    ALOAD 10
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet(Ljava/lang/Iterable;)Ljava/util/HashSet;
	//	    ASTORE 11
	//	   L61
	//	    LINENUMBER 229 L61
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet(Ljava/lang/Iterable;)Ljava/util/HashSet;
	//	    ASTORE 12
	//	   L62
	//	    LINENUMBER 230 L62
	//	    ALOAD 11
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKEINTERFACE java/util/Set.removeAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L63
	//	    LINENUMBER 231 L63
	//	    ALOAD 12
	//	    ALOAD 10
	//	    INVOKEINTERFACE java/util/Set.removeAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L64
	//	    LINENUMBER 232 L64
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKEINTERFACE java/util/Set.clear()V
	//	   L65
	//	    LINENUMBER 233 L65
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    ALOAD 10
	//	    INVOKEINTERFACE java/util/Set.addAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L66
	//	    LINENUMBER 234 L66
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
	//	    ALOAD 12
	//	    ALOAD 11
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RenderGlobal.updateTileEntities(Ljava/util/Collection;Ljava/util/Collection;)V
	//	   L7
	//	    LINENUMBER 238 L7
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L67
	//	    LINENUMBER 239 L67
	//	    GOTO L68
	//	   L8
	//	    LINENUMBER 238 L8
	//	   FRAME SAME1 java/lang/Throwable
	//	    ASTORE 23
	//	   L9
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L69
	//	    LINENUMBER 239 L69
	//	    ALOAD 23
	//	    ATHROW
	//	   L68
	//	    LINENUMBER 240 L68
	//	   FRAME SAME
	//	    RETURN
	//	   L70
	//	    LOCALVARIABLE tileentityspecialrenderer Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer; L37 L33 18
	//	    // signature Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE tileentity Lnet/minecraft/tileentity/TileEntity; L35 L33 17
	//	    LOCALVARIABLE bufferbuilder Lnet/minecraft/client/renderer/BufferBuilder; L49 L45 22
	//	    LOCALVARIABLE j I L47 L45 21
	//	    LOCALVARIABLE blockrenderlayer1 Lnet/minecraft/util/BlockRenderLayer; L43 L45 20
	//	    LOCALVARIABLE iblockstate Lnet/minecraft/block/state/IBlockState; L29 L53 15
	//	    LOCALVARIABLE block Lnet/minecraft/block/Block; L30 L53 16
	//	    LOCALVARIABLE blockpos$mutableblockpos Lnet/minecraft/util/math/BlockPos$MutableBlockPos; L28 L53 14
	//	    LOCALVARIABLE blockrenderlayer Lnet/minecraft/util/BlockRenderLayer; L55 L58 16
	//	    LOCALVARIABLE aboolean [Z L24 L21 11
	//	    LOCALVARIABLE blockrendererdispatcher Lnet/minecraft/client/renderer/BlockRendererDispatcher; L25 L21 12
	//	    LOCALVARIABLE set Ljava/util/Set; L61 L7 11
	//	    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: java.util.Set<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE set1 Ljava/util/Set; L62 L7 12
	//	    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: java.util.Set<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE this Lnet/minecraft/client/renderer/chunk/RenderChunk; L10 L70 0
	//	    LOCALVARIABLE x F L10 L70 1
	//	    LOCALVARIABLE y F L10 L70 2
	//	    LOCALVARIABLE z F L10 L70 3
	//	    LOCALVARIABLE generator Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator; L10 L70 4
	//	    LOCALVARIABLE compiledchunk Lnet/minecraft/client/renderer/chunk/CompiledChunk; L11 L70 5
	//	    LOCALVARIABLE i I L12 L70 6
	//	    LOCALVARIABLE blockpos Lnet/minecraft/util/math/BlockPos; L13 L70 7
	//	    LOCALVARIABLE blockpos1 Lnet/minecraft/util/math/BlockPos; L14 L70 8
	//	    LOCALVARIABLE lvt_9_1_ Lnet/minecraft/client/renderer/chunk/VisGraph; L19 L70 9
	//	    LOCALVARIABLE lvt_10_1_ Ljava/util/HashSet; L20 L70 10
	//	    MAXSTACK = 8
	//	    MAXLOCALS = 24
	//@formatter:on

	// DESIRED BYTECODE METHOD \/
	//@formatter:off
	//	  // access flags 0x1
	//	  public rebuildChunk(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V
	//	    TRYCATCHBLOCK L0 L1 L2
	//	    TRYCATCHBLOCK L3 L4 L2
	//	    TRYCATCHBLOCK L2 L5 L2
	//	    TRYCATCHBLOCK L6 L7 L8
	//	    TRYCATCHBLOCK L8 L9 L8
	//	   L10
	//	    LINENUMBER 129 L10
	//	    NEW net/minecraft/client/renderer/chunk/CompiledChunk
	//	    DUP
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/CompiledChunk.<init>()V
	//	    ASTORE 5
	//	   L11
	//	    LINENUMBER 130 L11
	//	    ICONST_1
	//	    ISTORE 6
	//	   L12
	//	    LINENUMBER 131 L12
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
	//	    ASTORE 7
	//	   L13
	//	    LINENUMBER 132 L13
	//	    ALOAD 7
	//	    BIPUSH 15
	//	    BIPUSH 15
	//	    BIPUSH 15
	//	    INVOKEVIRTUAL net/minecraft/util/math/BlockPos.add(III)Lnet/minecraft/util/math/BlockPos;
	//	    ASTORE 8
	//	   L14
	//	    LINENUMBER 133 L14
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.lock()V
	//	   L0
	//	    LINENUMBER 137 L0
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getStatus()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
	//	    GETSTATIC net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status.COMPILING : Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;
	//	    IF_ACMPEQ L3
	//	   L1
	//	    LINENUMBER 146 L1
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L15
	//	    LINENUMBER 139 L15
	//	    RETURN
	//	   L3
	//	    LINENUMBER 142 L3
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos] []
	//	    ALOAD 4
	//	    ALOAD 5
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.setCompiledChunk(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
	//	   L4
	//	    LINENUMBER 146 L4
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L16
	//	    LINENUMBER 147 L16
	//	    GOTO L17
	//	   L2
	//	    LINENUMBER 146 L2
	//	   FRAME SAME1 java/lang/Throwable
	//	    ASTORE 9
	//	   L5
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getLock()Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L18
	//	    LINENUMBER 147 L18
	//	    ALOAD 9
	//	    ATHROW

	//+	   L16
	//+	    LINENUMBER 148 L16
	//+	    ALOAD 0
	//+	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
	//+	    ALOAD 0
	//+	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//+	    ALOAD 4
	//+	    ALOAD 5
	//+	    ALOAD 0
	//+	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
	//+	    FLOAD 1
	//+	    FLOAD 2
	//+	    FLOAD 3
	//+	    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.onRebuildChunkEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Lnet/minecraftforge/client/event/RebuildChunkEvent;
	//+	    INVOKEVIRTUAL net/minecraftforge/client/event/RebuildChunkEvent.isCanceled()Z
	//+	    IFEQ L17
	//+	    RETURN

	//	   L17
	//	    LINENUMBER 149 L17
	//	   FRAME SAME
	//	    NEW net/minecraft/client/renderer/chunk/VisGraph
	//	    DUP
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/VisGraph.<init>()V
	//	    ASTORE 9
	//	   L19
	//	    LINENUMBER 150 L19
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet()Ljava/util/HashSet;
	//	    ASTORE 10
	//	   L20
	//	    LINENUMBER 152 L20
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.isEmpty()Z
	//	    IFNE L21
	//	   L22
	//	    LINENUMBER 154 L22
	//	    GETSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
	//	    ICONST_1
	//	    IADD
	//	    PUTSTATIC net/minecraft/client/renderer/chunk/RenderChunk.renderChunksUpdated : I
	//	   L23
	//	    LINENUMBER 155 L23
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ARRAYLENGTH
	//	    NEWARRAY T_BOOLEAN
	//	    ASTORE 11
	//	   L24
	//	    LINENUMBER 156 L24
	//	    INVOKESTATIC net/minecraft/client/Minecraft.getMinecraft()Lnet/minecraft/client/Minecraft;
	//	    INVOKEVIRTUAL net/minecraft/client/Minecraft.getBlockRendererDispatcher()Lnet/minecraft/client/renderer/BlockRendererDispatcher;
	//	    ASTORE 12
	//	   L25
	//	    LINENUMBER 158 L25
	//	    ALOAD 7
	//	    ALOAD 8
	//	    INVOKESTATIC net/minecraft/util/math/BlockPos.getAllInBoxMutable(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;
	//	    INVOKEINTERFACE java/lang/Iterable.iterator()Ljava/util/Iterator;
	//	    ASTORE 13
	//	   L26
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet [Z net/minecraft/client/renderer/BlockRendererDispatcher java/util/Iterator] []
	//	    ALOAD 13
	//	    INVOKEINTERFACE java/util/Iterator.hasNext()Z
	//	    IFEQ L27
	//	    ALOAD 13
	//	    INVOKEINTERFACE java/util/Iterator.next()Ljava/lang/Object;
	//	    CHECKCAST net/minecraft/util/math/BlockPos$MutableBlockPos
	//	    ASTORE 14
	//	   L28
	//	    LINENUMBER 160 L28
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 14
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
	//	    ASTORE 15
	//	   L29
	//	    LINENUMBER 161 L29
	//	    ALOAD 15
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.getBlock()Lnet/minecraft/block/Block;
	//	    ASTORE 16
	//	   L30
	//	    LINENUMBER 163 L30
	//	    ALOAD 15
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.isOpaqueCube()Z
	//	    IFEQ L31
	//	   L32
	//	    LINENUMBER 165 L32
	//	    ALOAD 9
	//	    ALOAD 14
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/VisGraph.setOpaqueCube(Lnet/minecraft/util/math/BlockPos;)V
	//	   L31
	//	    LINENUMBER 168 L31
	//	   FRAME APPEND [net/minecraft/util/math/BlockPos$MutableBlockPos net/minecraft/block/state/IBlockState net/minecraft/block/Block]
	//	    ALOAD 16
	//	    ALOAD 15
	//	    INVOKEVIRTUAL net/minecraft/block/Block.hasTileEntity(Lnet/minecraft/block/state/IBlockState;)Z
	//	    IFEQ L33
	//	   L34
	//	    LINENUMBER 170 L34
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 14
	//	    GETSTATIC net/minecraft/world/chunk/Chunk$EnumCreateEntityType.CHECK : Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;
	//	    INVOKEVIRTUAL net/minecraft/world/ChunkCache.getTileEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;)Lnet/minecraft/tileentity/TileEntity;
	//	    ASTORE 17
	//	   L35
	//	    LINENUMBER 172 L35
	//	    ALOAD 17
	//	    IFNULL L33
	//	   L36
	//	    LINENUMBER 174 L36
	//	    GETSTATIC net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.getRenderer(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;
	//	    ASTORE 18
	//	   L37
	//	    LINENUMBER 176 L37
	//	    ALOAD 18
	//	    IFNULL L33
	//	   L38
	//	    LINENUMBER 179 L38
	//	    ALOAD 18
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer.isGlobalRenderer(Lnet/minecraft/tileentity/TileEntity;)Z
	//	    IFEQ L39
	//	   L40
	//	    LINENUMBER 181 L40
	//	    ALOAD 10
	//	    ALOAD 17
	//	    INVOKEVIRTUAL java/util/HashSet.add(Ljava/lang/Object;)Z
	//	    POP
	//	    GOTO L33
	//	   L39
	//	    LINENUMBER 183 L39
	//	   FRAME APPEND [net/minecraft/tileentity/TileEntity net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer]
	//	    ALOAD 5
	//	    ALOAD 17
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.addTileEntity(Lnet/minecraft/tileentity/TileEntity;)V
	//	   L33
	//	    LINENUMBER 188 L33
	//	   FRAME CHOP 2
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ASTORE 17
	//	    ALOAD 17
	//	    ARRAYLENGTH
	//	    ISTORE 18
	//	    ICONST_0
	//	    ISTORE 19
	//	   L41
	//	   FRAME APPEND [[Lnet/minecraft/util/BlockRenderLayer; I I]
	//	    ILOAD 19
	//	    ILOAD 18
	//	    IF_ICMPGE L42
	//	    ALOAD 17
	//	    ILOAD 19
	//	    AALOAD
	//	    ASTORE 20
	//	   L43
	//	    LINENUMBER 189 L43
	//	    ALOAD 16
	//	    ALOAD 15
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/block/Block.canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFNE L44
	//	    GOTO L45
	//	   L44
	//	    LINENUMBER 190 L44
	//	   FRAME APPEND [net/minecraft/util/BlockRenderLayer]
	//	    ALOAD 20
	//	    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L46
	//	    LINENUMBER 191 L46
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal()I
	//	    ISTORE 21
	//	   L47
	//	    LINENUMBER 193 L47
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/block/Block.getDefaultState()Lnet/minecraft/block/state/IBlockState;
	//	    INVOKEINTERFACE net/minecraft/block/state/IBlockState.getRenderType()Lnet/minecraft/util/EnumBlockRenderType;
	//	    GETSTATIC net/minecraft/util/EnumBlockRenderType.INVISIBLE : Lnet/minecraft/util/EnumBlockRenderType;
	//	    IF_ACMPEQ L45
	//	   L48
	//	    LINENUMBER 195 L48
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
	//	    ILOAD 21
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayerId(I)Lnet/minecraft/client/renderer/BufferBuilder;
	//	    ASTORE 22
	//	   L49
	//	    LINENUMBER 197 L49
	//	    ALOAD 5
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFNE L50
	//	   L51
	//	    LINENUMBER 199 L51
	//	    ALOAD 5
	//	    ALOAD 20
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L52
	//	    LINENUMBER 200 L52
	//	    ALOAD 0
	//	    ALOAD 22
	//	    ALOAD 7
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.preRenderBlocks(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V
	//	   L50
	//	    LINENUMBER 203 L50
	//	   FRAME APPEND [I net/minecraft/client/renderer/BufferBuilder]
	//	    ALOAD 11
	//	    ILOAD 21
	//	    DUP2
	//	    BALOAD
	//	    ALOAD 12
	//	    ALOAD 15
	//	    ALOAD 14
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
	//	    ALOAD 22
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/BlockRendererDispatcher.renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z
	//	    IOR
	//	    BASTORE
	//	   L45
	//	    LINENUMBER 188 L45
	//	   FRAME CHOP 3
	//	    IINC 19 1
	//	    GOTO L41
	//	   L42
	//	    LINENUMBER 206 L42
	//	   FRAME CHOP 3
	//	    ACONST_NULL
	//	    INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.setRenderLayer(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L53
	//	    LINENUMBER 207 L53
	//	    GOTO L26
	//	   L27
	//	    LINENUMBER 209 L27
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet [Z net/minecraft/client/renderer/BlockRendererDispatcher] []
	//	    INVOKESTATIC net/minecraft/util/BlockRenderLayer.values()[Lnet/minecraft/util/BlockRenderLayer;
	//	    ASTORE 13
	//	    ALOAD 13
	//	    ARRAYLENGTH
	//	    ISTORE 14
	//	    ICONST_0
	//	    ISTORE 15
	//	   L54
	//	   FRAME APPEND [[Lnet/minecraft/util/BlockRenderLayer; I I]
	//	    ILOAD 15
	//	    ILOAD 14
	//	    IF_ICMPGE L21
	//	    ALOAD 13
	//	    ILOAD 15
	//	    AALOAD
	//	    ASTORE 16
	//	   L55
	//	    LINENUMBER 211 L55
	//	    ALOAD 11
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/util/BlockRenderLayer.ordinal()I
	//	    BALOAD
	//	    IFEQ L56
	//	   L57
	//	    LINENUMBER 213 L57
	//	    ALOAD 5
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setLayerUsed(Lnet/minecraft/util/BlockRenderLayer;)V
	//	   L56
	//	    LINENUMBER 216 L56
	//	   FRAME APPEND [net/minecraft/util/BlockRenderLayer]
	//	    ALOAD 5
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.isLayerStarted(Lnet/minecraft/util/BlockRenderLayer;)Z
	//	    IFEQ L58
	//	   L59
	//	    LINENUMBER 218 L59
	//	    ALOAD 0
	//	    ALOAD 16
	//	    FLOAD 1
	//	    FLOAD 2
	//	    FLOAD 3
	//	    ALOAD 4
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator.getRegionRenderCacheBuilder()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;
	//	    ALOAD 16
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RegionRenderCacheBuilder.getWorldRendererByLayer(Lnet/minecraft/util/BlockRenderLayer;)Lnet/minecraft/client/renderer/BufferBuilder;
	//	    ALOAD 5
	//	    INVOKESPECIAL net/minecraft/client/renderer/chunk/RenderChunk.postRenderBlocks(Lnet/minecraft/util/BlockRenderLayer;FFFLnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V
	//	   L58
	//	    LINENUMBER 209 L58
	//	   FRAME CHOP 1
	//	    IINC 15 1
	//	    GOTO L54
	//	   L21
	//	    LINENUMBER 223 L21
	//	   FRAME FULL [net/minecraft/client/renderer/chunk/RenderChunk F F F net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator net/minecraft/client/renderer/chunk/CompiledChunk I net/minecraft/util/math/BlockPos net/minecraft/util/math/BlockPos net/minecraft/client/renderer/chunk/VisGraph java/util/HashSet] []
	//	    ALOAD 5
	//	    ALOAD 9
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/VisGraph.computeVisibility()Lnet/minecraft/client/renderer/chunk/SetVisibility;
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/chunk/CompiledChunk.setVisibility(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V
	//	   L60
	//	    LINENUMBER 224 L60
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.lock()V
	//	   L6
	//	    LINENUMBER 228 L6
	//	    ALOAD 10
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet(Ljava/lang/Iterable;)Ljava/util/HashSet;
	//	    ASTORE 11
	//	   L61
	//	    LINENUMBER 229 L61
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKESTATIC com/google/common/collect/Sets.newHashSet(Ljava/lang/Iterable;)Ljava/util/HashSet;
	//	    ASTORE 12
	//	   L62
	//	    LINENUMBER 230 L62
	//	    ALOAD 11
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKEINTERFACE java/util/Set.removeAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L63
	//	    LINENUMBER 231 L63
	//	    ALOAD 12
	//	    ALOAD 10
	//	    INVOKEINTERFACE java/util/Set.removeAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L64
	//	    LINENUMBER 232 L64
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    INVOKEINTERFACE java/util/Set.clear()V
	//	   L65
	//	    LINENUMBER 233 L65
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.setTileEntities : Ljava/util/Set;
	//	    ALOAD 10
	//	    INVOKEINTERFACE java/util/Set.addAll(Ljava/util/Collection;)Z
	//	    POP
	//	   L66
	//	    LINENUMBER 234 L66
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
	//	    ALOAD 12
	//	    ALOAD 11
	//	    INVOKEVIRTUAL net/minecraft/client/renderer/RenderGlobal.updateTileEntities(Ljava/util/Collection;Ljava/util/Collection;)V
	//	   L7
	//	    LINENUMBER 238 L7
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L67
	//	    LINENUMBER 239 L67
	//	    GOTO L68
	//	   L8
	//	    LINENUMBER 238 L8
	//	   FRAME SAME1 java/lang/Throwable
	//	    ASTORE 23
	//	   L9
	//	    ALOAD 0
	//	    GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.lockCompileTask : Ljava/util/concurrent/locks/ReentrantLock;
	//	    INVOKEVIRTUAL java/util/concurrent/locks/ReentrantLock.unlock()V
	//	   L69
	//	    LINENUMBER 239 L69
	//	    ALOAD 23
	//	    ATHROW
	//	   L68
	//	    LINENUMBER 240 L68
	//	   FRAME SAME
	//	    RETURN
	//	   L70
	//	    LOCALVARIABLE tileentityspecialrenderer Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer; L37 L33 18
	//	    // signature Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE tileentity Lnet/minecraft/tileentity/TileEntity; L35 L33 17
	//	    LOCALVARIABLE bufferbuilder Lnet/minecraft/client/renderer/BufferBuilder; L49 L45 22
	//	    LOCALVARIABLE j I L47 L45 21
	//	    LOCALVARIABLE blockrenderlayer1 Lnet/minecraft/util/BlockRenderLayer; L43 L45 20
	//	    LOCALVARIABLE iblockstate Lnet/minecraft/block/state/IBlockState; L29 L53 15
	//	    LOCALVARIABLE block Lnet/minecraft/block/Block; L30 L53 16
	//	    LOCALVARIABLE blockpos$mutableblockpos Lnet/minecraft/util/math/BlockPos$MutableBlockPos; L28 L53 14
	//	    LOCALVARIABLE blockrenderlayer Lnet/minecraft/util/BlockRenderLayer; L55 L58 16
	//	    LOCALVARIABLE aboolean [Z L24 L21 11
	//	    LOCALVARIABLE blockrendererdispatcher Lnet/minecraft/client/renderer/BlockRendererDispatcher; L25 L21 12
	//	    LOCALVARIABLE set Ljava/util/Set; L61 L7 11
	//	    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: java.util.Set<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE set1 Ljava/util/Set; L62 L7 12
	//	    // signature Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;
	//	    // declaration: java.util.Set<net.minecraft.tileentity.TileEntity>
	//	    LOCALVARIABLE this Lnet/minecraft/client/renderer/chunk/RenderChunk; L10 L70 0
	//	    LOCALVARIABLE x F L10 L70 1
	//	    LOCALVARIABLE y F L10 L70 2
	//	    LOCALVARIABLE z F L10 L70 3
	//	    LOCALVARIABLE generator Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator; L10 L70 4
	//	    LOCALVARIABLE compiledchunk Lnet/minecraft/client/renderer/chunk/CompiledChunk; L11 L70 5
	//	    LOCALVARIABLE i I L12 L70 6
	//	    LOCALVARIABLE blockpos Lnet/minecraft/util/math/BlockPos; L13 L70 7
	//	    LOCALVARIABLE blockpos1 Lnet/minecraft/util/math/BlockPos; L14 L70 8
	//	    LOCALVARIABLE lvt_9_1_ Lnet/minecraft/client/renderer/chunk/VisGraph; L19 L70 9
	//	    LOCALVARIABLE lvt_10_1_ Ljava/util/HashSet; L20 L70 10
	//	    MAXSTACK = 8
	//	    MAXLOCALS = 24
	//@formatter:on

	// RAW ASM METHOD \/
	//@formatter:off
	//		{
	//		mv = cw.visitMethod(ACC_PUBLIC, "rebuildChunk", "(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V", null, null);
	//		mv.visitCode();
	//		Label l0 = new Label();
	//		Label l1 = new Label();
	//		Label l2 = new Label();
	//		mv.visitTryCatchBlock(l0, l1, l2, null);
	//		Label l3 = new Label();
	//		Label l4 = new Label();
	//		mv.visitTryCatchBlock(l3, l4, l2, null);
	//		Label l5 = new Label();
	//		mv.visitTryCatchBlock(l2, l5, l2, null);
	//		Label l6 = new Label();
	//		Label l7 = new Label();
	//		Label l8 = new Label();
	//		mv.visitTryCatchBlock(l6, l7, l8, null);
	//		Label l9 = new Label();
	//		mv.visitTryCatchBlock(l8, l9, l8, null);
	//		Label l10 = new Label();
	//		mv.visitLabel(l10);
	//		mv.visitLineNumber(129, l10);
	//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/CompiledChunk");
	//		mv.visitInsn(DUP);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "<init>", "()V", false);
	//		mv.visitVarInsn(ASTORE, 5);
	//		Label l11 = new Label();
	//		mv.visitLabel(l11);
	//		mv.visitLineNumber(130, l11);
	//		mv.visitInsn(ICONST_1);
	//		mv.visitVarInsn(ISTORE, 6);
	//		Label l12 = new Label();
	//		mv.visitLabel(l12);
	//		mv.visitLineNumber(131, l12);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
	//		mv.visitVarInsn(ASTORE, 7);
	//		Label l13 = new Label();
	//		mv.visitLabel(l13);
	//		mv.visitLineNumber(132, l13);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", "add", "(III)Lnet/minecraft/util/math/BlockPos;", false);
	//		mv.visitVarInsn(ASTORE, 8);
	//		Label l14 = new Label();
	//		mv.visitLabel(l14);
	//		mv.visitLineNumber(133, l14);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
	//		mv.visitLabel(l0);
	//		mv.visitLineNumber(137, l0);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getStatus", "()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;", false);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status", "COMPILING", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;");
	//		mv.visitJumpInsn(IF_ACMPEQ, l3);
	//		mv.visitLabel(l1);
	//		mv.visitLineNumber(146, l1);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l15 = new Label();
	//		mv.visitLabel(l15);
	//		mv.visitLineNumber(139, l15);
	//		mv.visitInsn(RETURN);
	//		mv.visitLabel(l3);
	//		mv.visitLineNumber(142, l3);
	//		mv.visitFrame(Opcodes.F_FULL, 9, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "setCompiledChunk", "(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
	//		mv.visitLabel(l4);
	//		mv.visitLineNumber(146, l4);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l16 = new Label();
	//		mv.visitLabel(l16);
	//		mv.visitLineNumber(147, l16);
	//		Label l17 = new Label();
	//		mv.visitJumpInsn(GOTO, l17);
	//		mv.visitLabel(l2);
	//		mv.visitLineNumber(146, l2);
	//		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
	//		mv.visitVarInsn(ASTORE, 9);
	//		mv.visitLabel(l5);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l18 = new Label();
	//		mv.visitLabel(l18);
	//		mv.visitLineNumber(147, l18);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitInsn(ATHROW);

	//		mv.visitLabel(l17);
	//		mv.visitLineNumber(149, l17);
	//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");
	//		mv.visitInsn(DUP);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/VisGraph", "<init>", "()V", false);
	//		mv.visitVarInsn(ASTORE, 9);
	//		Label l19 = new Label();
	//		mv.visitLabel(l19);
	//		mv.visitLineNumber(150, l19);
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "()Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 10);
	//		Label l20 = new Label();
	//		mv.visitLabel(l20);
	//		mv.visitLineNumber(152, l20);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "isEmpty", "()Z", false);
	//		Label l21 = new Label();
	//		mv.visitJumpInsn(IFNE, l21);
	//		Label l22 = new Label();
	//		mv.visitLabel(l22);
	//		mv.visitLineNumber(154, l22);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
	//		mv.visitInsn(ICONST_1);
	//		mv.visitInsn(IADD);
	//		mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
	//		Label l23 = new Label();
	//		mv.visitLabel(l23);
	//		mv.visitLineNumber(155, l23);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
	//		mv.visitVarInsn(ASTORE, 11);
	//		Label l24 = new Label();
	//		mv.visitLabel(l24);
	//		mv.visitLineNumber(156, l24);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
	//		mv.visitVarInsn(ASTORE, 12);
	//		Label l25 = new Label();
	//		mv.visitLabel(l25);
	//		mv.visitLineNumber(158, l25);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitVarInsn(ALOAD, 8);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/lang/Iterable", "iterator", "()Ljava/util/Iterator;", true);
	//		mv.visitVarInsn(ASTORE, 13);
	//		Label l26 = new Label();
	//		mv.visitLabel(l26);
	//		mv.visitFrame(Opcodes.F_FULL, 14, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "java/util/Iterator"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
	//		Label l27 = new Label();
	//		mv.visitJumpInsn(IFEQ, l27);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
	//		mv.visitTypeInsn(CHECKCAST, "net/minecraft/util/math/BlockPos$MutableBlockPos");
	//		mv.visitVarInsn(ASTORE, 14);
	//		Label l28 = new Label();
	//		mv.visitLabel(l28);
	//		mv.visitLineNumber(160, l28);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "getBlockState", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
	//		mv.visitVarInsn(ASTORE, 15);
	//		Label l29 = new Label();
	//		mv.visitLabel(l29);
	//		mv.visitLineNumber(161, l29);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "getBlock", "()Lnet/minecraft/block/Block;", true);
	//		mv.visitVarInsn(ASTORE, 16);
	//		Label l30 = new Label();
	//		mv.visitLabel(l30);
	//		mv.visitLineNumber(163, l30);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "isOpaqueCube", "()Z", true);
	//		Label l31 = new Label();
	//		mv.visitJumpInsn(IFEQ, l31);
	//		Label l32 = new Label();
	//		mv.visitLabel(l32);
	//		mv.visitLineNumber(165, l32);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", "setOpaqueCube", "(Lnet/minecraft/util/math/BlockPos;)V", false);
	//		mv.visitLabel(l31);
	//		mv.visitLineNumber(168, l31);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"net/minecraft/util/math/BlockPos$MutableBlockPos", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "hasTileEntity", "(Lnet/minecraft/block/state/IBlockState;)Z", false);
	//		Label l33 = new Label();
	//		mv.visitJumpInsn(IFEQ, l33);
	//		Label l34 = new Label();
	//		mv.visitLabel(l34);
	//		mv.visitLineNumber(170, l34);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/world/chunk/Chunk$EnumCreateEntityType", "CHECK", "Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "getTileEntity", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;)Lnet/minecraft/tileentity/TileEntity;", false);
	//		mv.visitVarInsn(ASTORE, 17);
	//		Label l35 = new Label();
	//		mv.visitLabel(l35);
	//		mv.visitLineNumber(172, l35);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitJumpInsn(IFNULL, l33);
	//		Label l36 = new Label();
	//		mv.visitLabel(l36);
	//		mv.visitLineNumber(174, l36);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "instance", "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;");
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "getRenderer", "(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", false);
	//		mv.visitVarInsn(ASTORE, 18);
	//		Label l37 = new Label();
	//		mv.visitLabel(l37);
	//		mv.visitLineNumber(176, l37);
	//		mv.visitVarInsn(ALOAD, 18);
	//		mv.visitJumpInsn(IFNULL, l33);
	//		Label l38 = new Label();
	//		mv.visitLabel(l38);
	//		mv.visitLineNumber(179, l38);
	//		mv.visitVarInsn(ALOAD, 18);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer", "isGlobalRenderer", "(Lnet/minecraft/tileentity/TileEntity;)Z", false);
	//		Label l39 = new Label();
	//		mv.visitJumpInsn(IFEQ, l39);
	//		Label l40 = new Label();
	//		mv.visitLabel(l40);
	//		mv.visitLineNumber(181, l40);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashSet", "add", "(Ljava/lang/Object;)Z", false);
	//		mv.visitInsn(POP);
	//		mv.visitJumpInsn(GOTO, l33);
	//		mv.visitLabel(l39);
	//		mv.visitLineNumber(183, l39);
	//		mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"net/minecraft/tileentity/TileEntity", "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "addTileEntity", "(Lnet/minecraft/tileentity/TileEntity;)V", false);
	//		mv.visitLabel(l33);
	//		mv.visitLineNumber(188, l33);
	//		mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitVarInsn(ASTORE, 17);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitVarInsn(ISTORE, 18);
	//		mv.visitInsn(ICONST_0);
	//		mv.visitVarInsn(ISTORE, 19);
	//		Label l41 = new Label();
	//		mv.visitLabel(l41);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
	//		mv.visitVarInsn(ILOAD, 19);
	//		mv.visitVarInsn(ILOAD, 18);
	//		Label l42 = new Label();
	//		mv.visitJumpInsn(IF_ICMPGE, l42);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitVarInsn(ILOAD, 19);
	//		mv.visitInsn(AALOAD);
	//		mv.visitVarInsn(ASTORE, 20);
	//		Label l43 = new Label();
	//		mv.visitLabel(l43);
	//		mv.visitLineNumber(189, l43);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "canRenderInLayer", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l44 = new Label();
	//		mv.visitJumpInsn(IFNE, l44);
	//		Label l45 = new Label();
	//		mv.visitJumpInsn(GOTO, l45);
	//		mv.visitLabel(l44);
	//		mv.visitLineNumber(190, l44);
	//		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/util/BlockRenderLayer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l46 = new Label();
	//		mv.visitLabel(l46);
	//		mv.visitLineNumber(191, l46);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
	//		mv.visitVarInsn(ISTORE, 21);
	//		Label l47 = new Label();
	//		mv.visitLabel(l47);
	//		mv.visitLineNumber(193, l47);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "getDefaultState", "()Lnet/minecraft/block/state/IBlockState;", false);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "getRenderType", "()Lnet/minecraft/util/EnumBlockRenderType;", true);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/util/EnumBlockRenderType", "INVISIBLE", "Lnet/minecraft/util/EnumBlockRenderType;");
	//		mv.visitJumpInsn(IF_ACMPEQ, l45);
	//		Label l48 = new Label();
	//		mv.visitLabel(l48);
	//		mv.visitLineNumber(195, l48);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getRegionRenderCacheBuilder", "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
	//		mv.visitVarInsn(ILOAD, 21);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", "getWorldRendererByLayerId", "(I)Lnet/minecraft/client/renderer/BufferBuilder;", false);
	//		mv.visitVarInsn(ASTORE, 22);
	//		Label l49 = new Label();
	//		mv.visitLabel(l49);
	//		mv.visitLineNumber(197, l49);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l50 = new Label();
	//		mv.visitJumpInsn(IFNE, l50);
	//		Label l51 = new Label();
	//		mv.visitLabel(l51);
	//		mv.visitLineNumber(199, l51);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l52 = new Label();
	//		mv.visitLabel(l52);
	//		mv.visitLineNumber(200, l52);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitVarInsn(ALOAD, 22);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "preRenderBlocks", "(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V", false);
	//		mv.visitLabel(l50);
	//		mv.visitLineNumber(203, l50);
	//		mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ILOAD, 21);
	//		mv.visitInsn(DUP2);
	//		mv.visitInsn(BALOAD);
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 22);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/BlockRendererDispatcher", "renderBlock", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z", false);
	//		mv.visitInsn(IOR);
	//		mv.visitInsn(BASTORE);
	//		mv.visitLabel(l45);
	//		mv.visitLineNumber(188, l45);
	//		mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
	//		mv.visitIincInsn(19, 1);
	//		mv.visitJumpInsn(GOTO, l41);
	//		mv.visitLabel(l42);
	//		mv.visitLineNumber(206, l42);
	//		mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
	//		mv.visitInsn(ACONST_NULL);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l53 = new Label();
	//		mv.visitLabel(l53);
	//		mv.visitLineNumber(207, l53);
	//		mv.visitJumpInsn(GOTO, l26);
	//		mv.visitLabel(l27);
	//		mv.visitLineNumber(209, l27);
	//		mv.visitFrame(Opcodes.F_FULL, 13, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher"}, 0, new Object[] {});
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitVarInsn(ASTORE, 13);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitVarInsn(ISTORE, 14);
	//		mv.visitInsn(ICONST_0);
	//		mv.visitVarInsn(ISTORE, 15);
	//		Label l54 = new Label();
	//		mv.visitLabel(l54);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
	//		mv.visitVarInsn(ILOAD, 15);
	//		mv.visitVarInsn(ILOAD, 14);
	//		mv.visitJumpInsn(IF_ICMPGE, l21);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitVarInsn(ILOAD, 15);
	//		mv.visitInsn(AALOAD);
	//		mv.visitVarInsn(ASTORE, 16);
	//		Label l55 = new Label();
	//		mv.visitLabel(l55);
	//		mv.visitLineNumber(211, l55);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
	//		mv.visitInsn(BALOAD);
	//		Label l56 = new Label();
	//		mv.visitJumpInsn(IFEQ, l56);
	//		Label l57 = new Label();
	//		mv.visitLabel(l57);
	//		mv.visitLineNumber(213, l57);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerUsed", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		mv.visitLabel(l56);
	//		mv.visitLineNumber(216, l56);
	//		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/util/BlockRenderLayer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l58 = new Label();
	//		mv.visitJumpInsn(IFEQ, l58);
	//		Label l59 = new Label();
	//		mv.visitLabel(l59);
	//		mv.visitLineNumber(218, l59);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(FLOAD, 1);
	//		mv.visitVarInsn(FLOAD, 2);
	//		mv.visitVarInsn(FLOAD, 3);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getRegionRenderCacheBuilder", "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", "getWorldRendererByLayer", "(Lnet/minecraft/util/BlockRenderLayer;)Lnet/minecraft/client/renderer/BufferBuilder;", false);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "postRenderBlocks", "(Lnet/minecraft/util/BlockRenderLayer;FFFLnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
	//		mv.visitLabel(l58);
	//		mv.visitLineNumber(209, l58);
	//		mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
	//		mv.visitIincInsn(15, 1);
	//		mv.visitJumpInsn(GOTO, l54);
	//		mv.visitLabel(l21);
	//		mv.visitLineNumber(223, l21);
	//		mv.visitFrame(Opcodes.F_FULL, 11, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", "computeVisibility", "()Lnet/minecraft/client/renderer/chunk/SetVisibility;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setVisibility", "(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V", false);
	//		Label l60 = new Label();
	//		mv.visitLabel(l60);
	//		mv.visitLineNumber(224, l60);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
	//		mv.visitLabel(l6);
	//		mv.visitLineNumber(228, l6);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 11);
	//		Label l61 = new Label();
	//		mv.visitLabel(l61);
	//		mv.visitLineNumber(229, l61);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 12);
	//		Label l62 = new Label();
	//		mv.visitLabel(l62);
	//		mv.visitLineNumber(230, l62);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l63 = new Label();
	//		mv.visitLabel(l63);
	//		mv.visitLineNumber(231, l63);
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l64 = new Label();
	//		mv.visitLabel(l64);
	//		mv.visitLineNumber(232, l64);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "clear", "()V", true);
	//		Label l65 = new Label();
	//		mv.visitLabel(l65);
	//		mv.visitLineNumber(233, l65);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "addAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l66 = new Label();
	//		mv.visitLabel(l66);
	//		mv.visitLineNumber(234, l66);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RenderGlobal", "updateTileEntities", "(Ljava/util/Collection;Ljava/util/Collection;)V", false);
	//		mv.visitLabel(l7);
	//		mv.visitLineNumber(238, l7);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l67 = new Label();
	//		mv.visitLabel(l67);
	//		mv.visitLineNumber(239, l67);
	//		Label l68 = new Label();
	//		mv.visitJumpInsn(GOTO, l68);
	//		mv.visitLabel(l8);
	//		mv.visitLineNumber(238, l8);
	//		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
	//		mv.visitVarInsn(ASTORE, 23);
	//		mv.visitLabel(l9);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l69 = new Label();
	//		mv.visitLabel(l69);
	//		mv.visitLineNumber(239, l69);
	//		mv.visitVarInsn(ALOAD, 23);
	//		mv.visitInsn(ATHROW);
	//		mv.visitLabel(l68);
	//		mv.visitLineNumber(240, l68);
	//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	//		mv.visitInsn(RETURN);
	//		Label l70 = new Label();
	//		mv.visitLabel(l70);
	//		mv.visitLocalVariable("tileentityspecialrenderer", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer<Lnet/minecraft/tileentity/TileEntity;>;", l37, l33, 18);
	//		mv.visitLocalVariable("tileentity", "Lnet/minecraft/tileentity/TileEntity;", null, l35, l33, 17);
	//		mv.visitLocalVariable("bufferbuilder", "Lnet/minecraft/client/renderer/BufferBuilder;", null, l49, l45, 22);
	//		mv.visitLocalVariable("j", "I", null, l47, l45, 21);
	//		mv.visitLocalVariable("blockrenderlayer1", "Lnet/minecraft/util/BlockRenderLayer;", null, l43, l45, 20);
	//		mv.visitLocalVariable("iblockstate", "Lnet/minecraft/block/state/IBlockState;", null, l29, l53, 15);
	//		mv.visitLocalVariable("block", "Lnet/minecraft/block/Block;", null, l30, l53, 16);
	//		mv.visitLocalVariable("blockpos$mutableblockpos", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;", null, l28, l53, 14);
	//		mv.visitLocalVariable("blockrenderlayer", "Lnet/minecraft/util/BlockRenderLayer;", null, l55, l58, 16);
	//		mv.visitLocalVariable("aboolean", "[Z", null, l24, l21, 11);
	//		mv.visitLocalVariable("blockrendererdispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", null, l25, l21, 12);
	//		mv.visitLocalVariable("set", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l61, l7, 11);
	//		mv.visitLocalVariable("set1", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l62, l7, 12);
	//		mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/chunk/RenderChunk;", null, l10, l70, 0);
	//		mv.visitLocalVariable("x", "F", null, l10, l70, 1);
	//		mv.visitLocalVariable("y", "F", null, l10, l70, 2);
	//		mv.visitLocalVariable("z", "F", null, l10, l70, 3);
	//		mv.visitLocalVariable("generator", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;", null, l10, l70, 4);
	//		mv.visitLocalVariable("compiledchunk", "Lnet/minecraft/client/renderer/chunk/CompiledChunk;", null, l11, l70, 5);
	//		mv.visitLocalVariable("i", "I", null, l12, l70, 6);
	//		mv.visitLocalVariable("blockpos", "Lnet/minecraft/util/math/BlockPos;", null, l13, l70, 7);
	//		mv.visitLocalVariable("blockpos1", "Lnet/minecraft/util/math/BlockPos;", null, l14, l70, 8);
	//		mv.visitLocalVariable("lvt_9_1_", "Lnet/minecraft/client/renderer/chunk/VisGraph;", null, l19, l70, 9);
	//		mv.visitLocalVariable("lvt_10_1_", "Ljava/util/HashSet;", null, l20, l70, 10);
	//		mv.visitMaxs(8, 24);
	//		mv.visitEnd();
	//		}
	//@formatter:on

	// RAW ASM METHOD \/
	//@formatter:off
	//		{
	//		mv = cw.visitMethod(ACC_PUBLIC, "rebuildChunk", "(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V", null, null);
	//		mv.visitCode();
	//		Label l0 = new Label();
	//		Label l1 = new Label();
	//		Label l2 = new Label();
	//		mv.visitTryCatchBlock(l0, l1, l2, null);
	//		Label l3 = new Label();
	//		Label l4 = new Label();
	//		mv.visitTryCatchBlock(l3, l4, l2, null);
	//		Label l5 = new Label();
	//		mv.visitTryCatchBlock(l2, l5, l2, null);
	//		Label l6 = new Label();
	//		Label l7 = new Label();
	//		Label l8 = new Label();
	//		mv.visitTryCatchBlock(l6, l7, l8, null);
	//		Label l9 = new Label();
	//		mv.visitTryCatchBlock(l8, l9, l8, null);
	//		Label l10 = new Label();
	//		mv.visitLabel(l10);
	//		mv.visitLineNumber(129, l10);
	//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/CompiledChunk");
	//		mv.visitInsn(DUP);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "<init>", "()V", false);
	//		mv.visitVarInsn(ASTORE, 5);
	//		Label l11 = new Label();
	//		mv.visitLabel(l11);
	//		mv.visitLineNumber(130, l11);
	//		mv.visitInsn(ICONST_1);
	//		mv.visitVarInsn(ISTORE, 6);
	//		Label l12 = new Label();
	//		mv.visitLabel(l12);
	//		mv.visitLineNumber(131, l12);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
	//		mv.visitVarInsn(ASTORE, 7);
	//		Label l13 = new Label();
	//		mv.visitLabel(l13);
	//		mv.visitLineNumber(132, l13);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitIntInsn(BIPUSH, 15);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/math/BlockPos", "add", "(III)Lnet/minecraft/util/math/BlockPos;", false);
	//		mv.visitVarInsn(ASTORE, 8);
	//		Label l14 = new Label();
	//		mv.visitLabel(l14);
	//		mv.visitLineNumber(133, l14);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
	//		mv.visitLabel(l0);
	//		mv.visitLineNumber(137, l0);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getStatus", "()Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;", false);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status", "COMPILING", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator$Status;");
	//		mv.visitJumpInsn(IF_ACMPEQ, l3);
	//		mv.visitLabel(l1);
	//		mv.visitLineNumber(146, l1);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l15 = new Label();
	//		mv.visitLabel(l15);
	//		mv.visitLineNumber(139, l15);
	//		mv.visitInsn(RETURN);
	//		mv.visitLabel(l3);
	//		mv.visitLineNumber(142, l3);
	//		mv.visitFrame(Opcodes.F_FULL, 9, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "setCompiledChunk", "(Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
	//		mv.visitLabel(l4);
	//		mv.visitLineNumber(146, l4);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l16 = new Label();
	//		mv.visitLabel(l16);
	//		mv.visitLineNumber(147, l16);
	//		Label l17 = new Label();
	//		mv.visitJumpInsn(GOTO, l17);
	//		mv.visitLabel(l2);
	//		mv.visitLineNumber(146, l2);
	//		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
	//		mv.visitVarInsn(ASTORE, 9);
	//		mv.visitLabel(l5);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l18 = new Label();
	//		mv.visitLabel(l18);
	//		mv.visitLineNumber(147, l18);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitInsn(ATHROW);

	//+		Label l16 = new Label();
	//+		mv.visitLabel(l16);
	//+		mv.visitLineNumber(148, l16);
	//+		mv.visitVarInsn(ALOAD, 0);
	//+		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
	//+		mv.visitVarInsn(ALOAD, 0);
	//+		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//+		mv.visitVarInsn(ALOAD, 4);
	//+		mv.visitVarInsn(ALOAD, 5);
	//+		mv.visitVarInsn(ALOAD, 0);
	//+		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "position", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;");
	//+		mv.visitVarInsn(FLOAD, 1);
	//+		mv.visitVarInsn(FLOAD, 2);
	//+		mv.visitVarInsn(FLOAD, 3);
	//+		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "onRebuildChunkEvent", "(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Lnet/minecraftforge/client/event/RebuildChunkEvent;", false);
	//+		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/client/event/RebuildChunkEvent", "isCanceled", "()Z", false);
	//+		Label l17 = new Label();
	//+		mv.visitJumpInsn(IFEQ, l17);
	//+		mv.visitInsn(RETURN);

	//		mv.visitLabel(l17);
	//		mv.visitLineNumber(149, l17);
	//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	//		mv.visitTypeInsn(NEW, "net/minecraft/client/renderer/chunk/VisGraph");
	//		mv.visitInsn(DUP);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/VisGraph", "<init>", "()V", false);
	//		mv.visitVarInsn(ASTORE, 9);
	//		Label l19 = new Label();
	//		mv.visitLabel(l19);
	//		mv.visitLineNumber(150, l19);
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "()Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 10);
	//		Label l20 = new Label();
	//		mv.visitLabel(l20);
	//		mv.visitLineNumber(152, l20);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "isEmpty", "()Z", false);
	//		Label l21 = new Label();
	//		mv.visitJumpInsn(IFNE, l21);
	//		Label l22 = new Label();
	//		mv.visitLabel(l22);
	//		mv.visitLineNumber(154, l22);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
	//		mv.visitInsn(ICONST_1);
	//		mv.visitInsn(IADD);
	//		mv.visitFieldInsn(PUTSTATIC, "net/minecraft/client/renderer/chunk/RenderChunk", "renderChunksUpdated", "I");
	//		Label l23 = new Label();
	//		mv.visitLabel(l23);
	//		mv.visitLineNumber(155, l23);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
	//		mv.visitVarInsn(ASTORE, 11);
	//		Label l24 = new Label();
	//		mv.visitLabel(l24);
	//		mv.visitLineNumber(156, l24);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getMinecraft", "()Lnet/minecraft/client/Minecraft;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getBlockRendererDispatcher", "()Lnet/minecraft/client/renderer/BlockRendererDispatcher;", false);
	//		mv.visitVarInsn(ASTORE, 12);
	//		Label l25 = new Label();
	//		mv.visitLabel(l25);
	//		mv.visitLineNumber(158, l25);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitVarInsn(ALOAD, 8);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/math/BlockPos", "getAllInBoxMutable", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Iterable;", false);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/lang/Iterable", "iterator", "()Ljava/util/Iterator;", true);
	//		mv.visitVarInsn(ASTORE, 13);
	//		Label l26 = new Label();
	//		mv.visitLabel(l26);
	//		mv.visitFrame(Opcodes.F_FULL, 14, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher", "java/util/Iterator"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
	//		Label l27 = new Label();
	//		mv.visitJumpInsn(IFEQ, l27);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
	//		mv.visitTypeInsn(CHECKCAST, "net/minecraft/util/math/BlockPos$MutableBlockPos");
	//		mv.visitVarInsn(ASTORE, 14);
	//		Label l28 = new Label();
	//		mv.visitLabel(l28);
	//		mv.visitLineNumber(160, l28);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "getBlockState", "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false);
	//		mv.visitVarInsn(ASTORE, 15);
	//		Label l29 = new Label();
	//		mv.visitLabel(l29);
	//		mv.visitLineNumber(161, l29);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "getBlock", "()Lnet/minecraft/block/Block;", true);
	//		mv.visitVarInsn(ASTORE, 16);
	//		Label l30 = new Label();
	//		mv.visitLabel(l30);
	//		mv.visitLineNumber(163, l30);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "isOpaqueCube", "()Z", true);
	//		Label l31 = new Label();
	//		mv.visitJumpInsn(IFEQ, l31);
	//		Label l32 = new Label();
	//		mv.visitLabel(l32);
	//		mv.visitLineNumber(165, l32);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", "setOpaqueCube", "(Lnet/minecraft/util/math/BlockPos;)V", false);
	//		mv.visitLabel(l31);
	//		mv.visitLineNumber(168, l31);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"net/minecraft/util/math/BlockPos$MutableBlockPos", "net/minecraft/block/state/IBlockState", "net/minecraft/block/Block"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "hasTileEntity", "(Lnet/minecraft/block/state/IBlockState;)Z", false);
	//		Label l33 = new Label();
	//		mv.visitJumpInsn(IFEQ, l33);
	//		Label l34 = new Label();
	//		mv.visitLabel(l34);
	//		mv.visitLineNumber(170, l34);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/world/chunk/Chunk$EnumCreateEntityType", "CHECK", "Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/ChunkCache", "getTileEntity", "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/chunk/Chunk$EnumCreateEntityType;)Lnet/minecraft/tileentity/TileEntity;", false);
	//		mv.visitVarInsn(ASTORE, 17);
	//		Label l35 = new Label();
	//		mv.visitLabel(l35);
	//		mv.visitLineNumber(172, l35);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitJumpInsn(IFNULL, l33);
	//		Label l36 = new Label();
	//		mv.visitLabel(l36);
	//		mv.visitLineNumber(174, l36);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "instance", "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;");
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher", "getRenderer", "(Lnet/minecraft/tileentity/TileEntity;)Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", false);
	//		mv.visitVarInsn(ASTORE, 18);
	//		Label l37 = new Label();
	//		mv.visitLabel(l37);
	//		mv.visitLineNumber(176, l37);
	//		mv.visitVarInsn(ALOAD, 18);
	//		mv.visitJumpInsn(IFNULL, l33);
	//		Label l38 = new Label();
	//		mv.visitLabel(l38);
	//		mv.visitLineNumber(179, l38);
	//		mv.visitVarInsn(ALOAD, 18);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer", "isGlobalRenderer", "(Lnet/minecraft/tileentity/TileEntity;)Z", false);
	//		Label l39 = new Label();
	//		mv.visitJumpInsn(IFEQ, l39);
	//		Label l40 = new Label();
	//		mv.visitLabel(l40);
	//		mv.visitLineNumber(181, l40);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashSet", "add", "(Ljava/lang/Object;)Z", false);
	//		mv.visitInsn(POP);
	//		mv.visitJumpInsn(GOTO, l33);
	//		mv.visitLabel(l39);
	//		mv.visitLineNumber(183, l39);
	//		mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {"net/minecraft/tileentity/TileEntity", "net/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "addTileEntity", "(Lnet/minecraft/tileentity/TileEntity;)V", false);
	//		mv.visitLabel(l33);
	//		mv.visitLineNumber(188, l33);
	//		mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitVarInsn(ASTORE, 17);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitVarInsn(ISTORE, 18);
	//		mv.visitInsn(ICONST_0);
	//		mv.visitVarInsn(ISTORE, 19);
	//		Label l41 = new Label();
	//		mv.visitLabel(l41);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
	//		mv.visitVarInsn(ILOAD, 19);
	//		mv.visitVarInsn(ILOAD, 18);
	//		Label l42 = new Label();
	//		mv.visitJumpInsn(IF_ICMPGE, l42);
	//		mv.visitVarInsn(ALOAD, 17);
	//		mv.visitVarInsn(ILOAD, 19);
	//		mv.visitInsn(AALOAD);
	//		mv.visitVarInsn(ASTORE, 20);
	//		Label l43 = new Label();
	//		mv.visitLabel(l43);
	//		mv.visitLineNumber(189, l43);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "canRenderInLayer", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l44 = new Label();
	//		mv.visitJumpInsn(IFNE, l44);
	//		Label l45 = new Label();
	//		mv.visitJumpInsn(GOTO, l45);
	//		mv.visitLabel(l44);
	//		mv.visitLineNumber(190, l44);
	//		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/util/BlockRenderLayer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l46 = new Label();
	//		mv.visitLabel(l46);
	//		mv.visitLineNumber(191, l46);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
	//		mv.visitVarInsn(ISTORE, 21);
	//		Label l47 = new Label();
	//		mv.visitLabel(l47);
	//		mv.visitLineNumber(193, l47);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/block/Block", "getDefaultState", "()Lnet/minecraft/block/state/IBlockState;", false);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", "getRenderType", "()Lnet/minecraft/util/EnumBlockRenderType;", true);
	//		mv.visitFieldInsn(GETSTATIC, "net/minecraft/util/EnumBlockRenderType", "INVISIBLE", "Lnet/minecraft/util/EnumBlockRenderType;");
	//		mv.visitJumpInsn(IF_ACMPEQ, l45);
	//		Label l48 = new Label();
	//		mv.visitLabel(l48);
	//		mv.visitLineNumber(195, l48);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getRegionRenderCacheBuilder", "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
	//		mv.visitVarInsn(ILOAD, 21);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", "getWorldRendererByLayerId", "(I)Lnet/minecraft/client/renderer/BufferBuilder;", false);
	//		mv.visitVarInsn(ASTORE, 22);
	//		Label l49 = new Label();
	//		mv.visitLabel(l49);
	//		mv.visitLineNumber(197, l49);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l50 = new Label();
	//		mv.visitJumpInsn(IFNE, l50);
	//		Label l51 = new Label();
	//		mv.visitLabel(l51);
	//		mv.visitLineNumber(199, l51);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 20);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l52 = new Label();
	//		mv.visitLabel(l52);
	//		mv.visitLineNumber(200, l52);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitVarInsn(ALOAD, 22);
	//		mv.visitVarInsn(ALOAD, 7);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "preRenderBlocks", "(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/util/math/BlockPos;)V", false);
	//		mv.visitLabel(l50);
	//		mv.visitLineNumber(203, l50);
	//		mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, "net/minecraft/client/renderer/BufferBuilder"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ILOAD, 21);
	//		mv.visitInsn(DUP2);
	//		mv.visitInsn(BALOAD);
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 15);
	//		mv.visitVarInsn(ALOAD, 14);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "worldView", "Lnet/minecraft/world/ChunkCache;");
	//		mv.visitVarInsn(ALOAD, 22);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/BlockRendererDispatcher", "renderBlock", "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z", false);
	//		mv.visitInsn(IOR);
	//		mv.visitInsn(BASTORE);
	//		mv.visitLabel(l45);
	//		mv.visitLineNumber(188, l45);
	//		mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
	//		mv.visitIincInsn(19, 1);
	//		mv.visitJumpInsn(GOTO, l41);
	//		mv.visitLabel(l42);
	//		mv.visitLineNumber(206, l42);
	//		mv.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
	//		mv.visitInsn(ACONST_NULL);
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraftforge/client/ForgeHooksClient", "setRenderLayer", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		Label l53 = new Label();
	//		mv.visitLabel(l53);
	//		mv.visitLineNumber(207, l53);
	//		mv.visitJumpInsn(GOTO, l26);
	//		mv.visitLabel(l27);
	//		mv.visitLineNumber(209, l27);
	//		mv.visitFrame(Opcodes.F_FULL, 13, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet", "[Z", "net/minecraft/client/renderer/BlockRendererDispatcher"}, 0, new Object[] {});
	//		mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/util/BlockRenderLayer", "values", "()[Lnet/minecraft/util/BlockRenderLayer;", false);
	//		mv.visitVarInsn(ASTORE, 13);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitInsn(ARRAYLENGTH);
	//		mv.visitVarInsn(ISTORE, 14);
	//		mv.visitInsn(ICONST_0);
	//		mv.visitVarInsn(ISTORE, 15);
	//		Label l54 = new Label();
	//		mv.visitLabel(l54);
	//		mv.visitFrame(Opcodes.F_APPEND,3, new Object[] {"[Lnet/minecraft/util/BlockRenderLayer;", Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
	//		mv.visitVarInsn(ILOAD, 15);
	//		mv.visitVarInsn(ILOAD, 14);
	//		mv.visitJumpInsn(IF_ICMPGE, l21);
	//		mv.visitVarInsn(ALOAD, 13);
	//		mv.visitVarInsn(ILOAD, 15);
	//		mv.visitInsn(AALOAD);
	//		mv.visitVarInsn(ASTORE, 16);
	//		Label l55 = new Label();
	//		mv.visitLabel(l55);
	//		mv.visitLineNumber(211, l55);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/util/BlockRenderLayer", "ordinal", "()I", false);
	//		mv.visitInsn(BALOAD);
	//		Label l56 = new Label();
	//		mv.visitJumpInsn(IFEQ, l56);
	//		Label l57 = new Label();
	//		mv.visitLabel(l57);
	//		mv.visitLineNumber(213, l57);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setLayerUsed", "(Lnet/minecraft/util/BlockRenderLayer;)V", false);
	//		mv.visitLabel(l56);
	//		mv.visitLineNumber(216, l56);
	//		mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/util/BlockRenderLayer"}, 0, null);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "isLayerStarted", "(Lnet/minecraft/util/BlockRenderLayer;)Z", false);
	//		Label l58 = new Label();
	//		mv.visitJumpInsn(IFEQ, l58);
	//		Label l59 = new Label();
	//		mv.visitLabel(l59);
	//		mv.visitLineNumber(218, l59);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitVarInsn(FLOAD, 1);
	//		mv.visitVarInsn(FLOAD, 2);
	//		mv.visitVarInsn(FLOAD, 3);
	//		mv.visitVarInsn(ALOAD, 4);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "getRegionRenderCacheBuilder", "()Lnet/minecraft/client/renderer/RegionRenderCacheBuilder;", false);
	//		mv.visitVarInsn(ALOAD, 16);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RegionRenderCacheBuilder", "getWorldRendererByLayer", "(Lnet/minecraft/util/BlockRenderLayer;)Lnet/minecraft/client/renderer/BufferBuilder;", false);
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/client/renderer/chunk/RenderChunk", "postRenderBlocks", "(Lnet/minecraft/util/BlockRenderLayer;FFFLnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/client/renderer/chunk/CompiledChunk;)V", false);
	//		mv.visitLabel(l58);
	//		mv.visitLineNumber(209, l58);
	//		mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
	//		mv.visitIincInsn(15, 1);
	//		mv.visitJumpInsn(GOTO, l54);
	//		mv.visitLabel(l21);
	//		mv.visitLineNumber(223, l21);
	//		mv.visitFrame(Opcodes.F_FULL, 11, new Object[] {"net/minecraft/client/renderer/chunk/RenderChunk", Opcodes.FLOAT, Opcodes.FLOAT, Opcodes.FLOAT, "net/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator", "net/minecraft/client/renderer/chunk/CompiledChunk", Opcodes.INTEGER, "net/minecraft/util/math/BlockPos", "net/minecraft/util/math/BlockPos", "net/minecraft/client/renderer/chunk/VisGraph", "java/util/HashSet"}, 0, new Object[] {});
	//		mv.visitVarInsn(ALOAD, 5);
	//		mv.visitVarInsn(ALOAD, 9);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/VisGraph", "computeVisibility", "()Lnet/minecraft/client/renderer/chunk/SetVisibility;", false);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/chunk/CompiledChunk", "setVisibility", "(Lnet/minecraft/client/renderer/chunk/SetVisibility;)V", false);
	//		Label l60 = new Label();
	//		mv.visitLabel(l60);
	//		mv.visitLineNumber(224, l60);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "lock", "()V", false);
	//		mv.visitLabel(l6);
	//		mv.visitLineNumber(228, l6);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 11);
	//		Label l61 = new Label();
	//		mv.visitLabel(l61);
	//		mv.visitLineNumber(229, l61);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKESTATIC, "com/google/common/collect/Sets", "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
	//		mv.visitVarInsn(ASTORE, 12);
	//		Label l62 = new Label();
	//		mv.visitLabel(l62);
	//		mv.visitLineNumber(230, l62);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l63 = new Label();
	//		mv.visitLabel(l63);
	//		mv.visitLineNumber(231, l63);
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "removeAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l64 = new Label();
	//		mv.visitLabel(l64);
	//		mv.visitLineNumber(232, l64);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "clear", "()V", true);
	//		Label l65 = new Label();
	//		mv.visitLabel(l65);
	//		mv.visitLineNumber(233, l65);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "setTileEntities", "Ljava/util/Set;");
	//		mv.visitVarInsn(ALOAD, 10);
	//		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "addAll", "(Ljava/util/Collection;)Z", true);
	//		mv.visitInsn(POP);
	//		Label l66 = new Label();
	//		mv.visitLabel(l66);
	//		mv.visitLineNumber(234, l66);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "renderGlobal", "Lnet/minecraft/client/renderer/RenderGlobal;");
	//		mv.visitVarInsn(ALOAD, 12);
	//		mv.visitVarInsn(ALOAD, 11);
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/renderer/RenderGlobal", "updateTileEntities", "(Ljava/util/Collection;Ljava/util/Collection;)V", false);
	//		mv.visitLabel(l7);
	//		mv.visitLineNumber(238, l7);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l67 = new Label();
	//		mv.visitLabel(l67);
	//		mv.visitLineNumber(239, l67);
	//		Label l68 = new Label();
	//		mv.visitJumpInsn(GOTO, l68);
	//		mv.visitLabel(l8);
	//		mv.visitLineNumber(238, l8);
	//		mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/lang/Throwable"});
	//		mv.visitVarInsn(ASTORE, 23);
	//		mv.visitLabel(l9);
	//		mv.visitVarInsn(ALOAD, 0);
	//		mv.visitFieldInsn(GETFIELD, "net/minecraft/client/renderer/chunk/RenderChunk", "lockCompileTask", "Ljava/util/concurrent/locks/ReentrantLock;");
	//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/concurrent/locks/ReentrantLock", "unlock", "()V", false);
	//		Label l69 = new Label();
	//		mv.visitLabel(l69);
	//		mv.visitLineNumber(239, l69);
	//		mv.visitVarInsn(ALOAD, 23);
	//		mv.visitInsn(ATHROW);
	//		mv.visitLabel(l68);
	//		mv.visitLineNumber(240, l68);
	//		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	//		mv.visitInsn(RETURN);
	//		Label l70 = new Label();
	//		mv.visitLabel(l70);
	//		mv.visitLocalVariable("tileentityspecialrenderer", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer;", "Lnet/minecraft/client/renderer/tileentity/TileEntitySpecialRenderer<Lnet/minecraft/tileentity/TileEntity;>;", l37, l33, 18);
	//		mv.visitLocalVariable("tileentity", "Lnet/minecraft/tileentity/TileEntity;", null, l35, l33, 17);
	//		mv.visitLocalVariable("bufferbuilder", "Lnet/minecraft/client/renderer/BufferBuilder;", null, l49, l45, 22);
	//		mv.visitLocalVariable("j", "I", null, l47, l45, 21);
	//		mv.visitLocalVariable("blockrenderlayer1", "Lnet/minecraft/util/BlockRenderLayer;", null, l43, l45, 20);
	//		mv.visitLocalVariable("iblockstate", "Lnet/minecraft/block/state/IBlockState;", null, l29, l53, 15);
	//		mv.visitLocalVariable("block", "Lnet/minecraft/block/Block;", null, l30, l53, 16);
	//		mv.visitLocalVariable("blockpos$mutableblockpos", "Lnet/minecraft/util/math/BlockPos$MutableBlockPos;", null, l28, l53, 14);
	//		mv.visitLocalVariable("blockrenderlayer", "Lnet/minecraft/util/BlockRenderLayer;", null, l55, l58, 16);
	//		mv.visitLocalVariable("aboolean", "[Z", null, l24, l21, 11);
	//		mv.visitLocalVariable("blockrendererdispatcher", "Lnet/minecraft/client/renderer/BlockRendererDispatcher;", null, l25, l21, 12);
	//		mv.visitLocalVariable("set", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l61, l7, 11);
	//		mv.visitLocalVariable("set1", "Ljava/util/Set;", "Ljava/util/Set<Lnet/minecraft/tileentity/TileEntity;>;", l62, l7, 12);
	//		mv.visitLocalVariable("this", "Lnet/minecraft/client/renderer/chunk/RenderChunk;", null, l10, l70, 0);
	//		mv.visitLocalVariable("x", "F", null, l10, l70, 1);
	//		mv.visitLocalVariable("y", "F", null, l10, l70, 2);
	//		mv.visitLocalVariable("z", "F", null, l10, l70, 3);
	//		mv.visitLocalVariable("generator", "Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;", null, l10, l70, 4);
	//		mv.visitLocalVariable("compiledchunk", "Lnet/minecraft/client/renderer/chunk/CompiledChunk;", null, l11, l70, 5);
	//		mv.visitLocalVariable("i", "I", null, l12, l70, 6);
	//		mv.visitLocalVariable("blockpos", "Lnet/minecraft/util/math/BlockPos;", null, l13, l70, 7);
	//		mv.visitLocalVariable("blockpos1", "Lnet/minecraft/util/math/BlockPos;", null, l14, l70, 8);
	//		mv.visitLocalVariable("lvt_9_1_", "Lnet/minecraft/client/renderer/chunk/VisGraph;", null, l19, l70, 9);
	//		mv.visitLocalVariable("lvt_10_1_", "Ljava/util/HashSet;", null, l20, l70, 10);
	//		mv.visitMaxs(8, 24);
	//		mv.visitEnd();
	//		}
	//@formatter:on

	@Override
	public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
		if (name.equals("net.minecraft.client.renderer.chunk.RenderChunk")) {
			System.out.println("********* INSIDE DEOBFUSCATED RENDER_CHUNK TRANSFORMER ABOUT TO PATCH: " + name);

			final byte[] newBasicClass = this.constructReadAndLogClassFromBytes(name, basicClass, true);

			final byte[] hookRebuildChunkEvent = this.addRebuildChunkEvent(name, newBasicClass, true);
			// final byte[] hookRebuildChunkBlocksEvent = this.addRebuildChunkBlocksEvent(name, hookRebuildChunkEvent, true);
			final byte[] hookRebuildChunkBlocksEvent = this.constructReadAndLogClassFromBytes(name, hookRebuildChunkEvent, true);
			final byte[] hookRebuildChunkBlockEvent = this.addRebuildChunkBlockEvent(name, hookRebuildChunkBlocksEvent, true);
			return hookRebuildChunkBlockEvent;
		}

		if (name.equals("cws")) {
			System.out.println("********* INSIDE OBFUSCATED RENDER_CHUNK TRANSFORMER ABOUT TO PATCH: " + name);

			final byte[] newBasicClass = this.constructReadAndLogClassFromBytes(name, basicClass, false);

			final byte[] hookRebuildChunkEvent = this.addRebuildChunkEvent(name, newBasicClass, false);
			// final byte[] hookRebuildChunkBlocksEvent = this.addRebuildChunkBlocksEvent(name, hookRebuildChunkEvent, false);
			final byte[] hookRebuildChunkBlocksEvent = this.constructReadAndLogClassFromBytes(name, hookRebuildChunkEvent, false);
			final byte[] hookRebuildChunkBlockEvent = this.addRebuildChunkBlockEvent(name, hookRebuildChunkBlocksEvent, false);
			return hookRebuildChunkBlockEvent;
		}
		return basicClass;
	}

	private byte[] addRebuildChunkEvent(final String name, final byte[] bytes, final boolean deobfuscated) {

		final String targetMethodName = this.getRebuildChunkMethodName(deobfuscated);

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		final ClassNode classNodeRenderChunk = new ClassNode();
		final ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNodeRenderChunk, 0);

		// loop over all of the methods declared inside the class until we get to the @link{targetMethodName}
		final Iterator<MethodNode> methods = classNodeRenderChunk.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode methodRebuildChunk = methods.next();
			if (!methodRebuildChunk.name.equals(targetMethodName)) {
				continue;
			}
			// TODO: obfuscated
			if (!methodRebuildChunk.desc.equals("(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V")) {
				continue;
			}

			System.out.println("********* Inside target method! " + targetMethodName + " | " + methodRebuildChunk.name);

			AbstractInsnNode targetNode = null;
			final LabelNode labelNode;

			// Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
			final Iterator<AbstractInsnNode> iter = methodRebuildChunk.instructions.iterator();
			// 8 times, 10 for good measure
			iter.next();
			iter.next();//
			iter.next();
			iter.next();//
			iter.next();
			iter.next();//
			iter.next();
			iter.next();//
			iter.next();
			iter.next();//
			while (iter.hasNext()) {

				// L17 <<<< target node
				// LINENUMBER 150 L17
				// FRAME SAME
				// NEW net/minecraft/client/renderer/chunk/VisGraph <<<<<<<<<<<<<<<< NEW Opcode Insert Point
				// DUP

				final AbstractInsnNode currentNode = iter.next();
				if (currentNode.getOpcode() == Opcodes.NEW) {
					targetNode = currentNode.getPrevious().getPrevious().getPrevious();
					System.out.println("********* Got target Node! " + targetNode + " | " + currentNode);
					break;
				}
			}

			if (!(targetNode instanceof LabelNode)) {
				System.out.println("********* ERROR FINDING CORRECT TARGET NODE! " + targetNode);
			}

			labelNode = (LabelNode) targetNode;
			System.out.println("********* Got correct target Node! " + targetNode);

			// HOLY FUCK
			final InsnList testInjectedInstructions = new InsnList();

			// System.out.println("rebuildChunk"); <<<<<

			// final Label l37 = new Label();
			// methodRebuildChunk.visitLabel(l37);
			// methodRebuildChunk.visitLineNumber(148, l37);
			// methodRebuildChunk.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			// methodRebuildChunk.visitLdcInsn("rebuildChunk");
			// methodRebuildChunk.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

			final LabelNode labelNodeToInject = new LabelNode(new Label());
			testInjectedInstructions.add(labelNodeToInject);
			testInjectedInstructions.add(new LineNumberNode(148, labelNodeToInject));
			testInjectedInstructions.add(new FrameNode(F_SAME, 0, null, 0, null));
			testInjectedInstructions.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
			testInjectedInstructions.add(new LdcInsnNode("rebuildChunk"));
			testInjectedInstructions.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

			methodRebuildChunk.instructions.insertBefore(targetNode, testInjectedInstructions);
			// IT WORKS!!!

			//

			//

			// ATHROW

			// + L16
			// + LINENUMBER 148 L16
			// + ALOAD 0
			// + GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.renderGlobal : Lnet/minecraft/client/renderer/RenderGlobal;
			// + ALOAD 0
			// + GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.worldView : Lnet/minecraft/world/ChunkCache;
			// + ALOAD 4
			// + ALOAD 5
			// + ALOAD 0
			// + GETFIELD net/minecraft/client/renderer/chunk/RenderChunk.position : Lnet/minecraft/util/math/BlockPos$MutableBlockPos;
			// + FLOAD 1
			// + FLOAD 2
			// + FLOAD 3
			// + INVOKESTATIC net/minecraftforge/client/ForgeHooksClient.onRebuildChunkEvent(Lnet/minecraft/client/renderer/RenderGlobal;Lnet/minecraft/world/ChunkCache;Lnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;Lnet/minecraft/client/renderer/chunk/CompiledChunk;Lnet/minecraft/util/math/BlockPos$MutableBlockPos;FFF)Lnet/minecraftforge/client/event/RebuildChunkEvent;
			// + INVOKEVIRTUAL net/minecraftforge/client/event/RebuildChunkEvent.isCanceled()Z
			// + IFEQ L17
			// + RETURN

			// L17

			System.out.println("********* Generating Instruction list!");

			// inject new instruction list into method instruction list
			// methodRebuildChunk.instructions.insertBefore(targetNode, toInject);
			System.out.println("********* added Instruction list to method " + methodRebuildChunk + " | " + targetNode + " | " + testInjectedInstructions);

			// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

			/*
			 * To add new instructions, such as calling a static method can be done like so:
			 *
			 * // make new instruction list InsnList toInject = new InsnList();
			 *
			 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
			 *
			 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
			 */

			// final InsnList toInject = new InsnList();
			// toInject.add(new VarInsnNode(ALOAD, 0));
			// toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));

			System.out.println("********* FINISHED PATCHING");
			break;
		}

		// ASM specific for cleaning up and returning the final bytes for JVM processing.
		final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNodeRenderChunk.accept(writer);
		return writer.toByteArray();
	}

	private String getRebuildChunkMethodName(final boolean deobfuscated) {
		if (deobfuscated == false) {
			return "func_178581_b";
		} else {
			return "rebuildChunk";
		}
	}

	private byte[] constructReadAndLogClassFromBytes(final String name, final byte[] bytes, final boolean deobfuscated) {

		final String targetMethodName = this.getRebuildChunkMethodName(deobfuscated);

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		final ClassNode classNodeRenderChunk = new ClassNode();
		final ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNodeRenderChunk, 0);

		// loop over all of the methods declared inside the class until we get to the @link{targetMethodName}
		final Iterator<MethodNode> methods = classNodeRenderChunk.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode methodRebuildChunk = methods.next();
			if (!methodRebuildChunk.name.equals(targetMethodName)) {
				continue;
			}
			// TODO: obfuscated
			if (!methodRebuildChunk.desc.equals("(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V")) {
				continue;
			}

			final Printer printer = new Textifier();
			final TraceMethodVisitor methodTracerPrinter = new TraceMethodVisitor(printer);
			methodRebuildChunk.accept(methodTracerPrinter);
			final StringWriter stringWriter = new StringWriter();
			printer.print(new PrintWriter(stringWriter));
			printer.getText().clear();

			final Path file = Paths.get("/Users/" + System.getProperty("user.name") + "/Desktop/" + "Patch" + stringWriter.toString().hashCode() + ".txt");
			try {
				Files.write(file, Arrays.asList(StringUtils.split(stringWriter.toString(), "\n")), Charset.forName("UTF-8"));
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		}

		// ASM specific for cleaning up and returning the final bytes for JVM processing.
		final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNodeRenderChunk.accept(writer);
		return writer.toByteArray();
	}

	private byte[] addRebuildChunkBlockEvent(final String name, final byte[] bytes, final boolean deobfuscated) {
		// TODO Auto-generated method stub
		return bytes;
	}

	// copied from a (1.6.2) tutorial somewhere (https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing)
	public byte[] patchClassASM(final String name, final byte[] bytes, final boolean deobfuscated) {

		String targetMethodName = "";

		// Our target method
		if (deobfuscated == false) {
			targetMethodName = "NULL";
		} else {
			targetMethodName = "rebuildChunk";
		}

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		final ClassNode classNodeRenderChunk = new ClassNode();
		final ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNodeRenderChunk, 0);

		// Now we loop over all of the methods declared inside the Explosion class until we get to the targetMethodName "doExplosionB"

		final Iterator<MethodNode> methods = classNodeRenderChunk.methods.iterator();
		while (methods.hasNext()) {
			final MethodNode methodRebuildChunk = methods.next();
			int fdiv_index = -1;

			// Check if this is doExplosionB and it's method signature is (FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V which means that it accepts a 3 floats (F), and an Object (L) of type ChunkCompileTaskGenerator and returns a void (V)
			if ((methodRebuildChunk.name.equals(targetMethodName) && methodRebuildChunk.desc.equals("(FFFLnet/minecraft/client/renderer/chunk/ChunkCompileTaskGenerator;)V"))) {
				System.out.println("********* Inside target method!");

				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				// RENDER CHUNK METHOD ->
				// LINE - CODE
				// 147 - }
				// 148 -
				// 149 - VisGraph lvt_9_1_ = new VisGraph();

				// AFTER ASM
				// LINE - CODE
				// 147 - }
				// 148 - if (net.minecraftforge.client.ForgeHooksClient.onRebuildChunkEvent(field_178589_e, field_189564_r, p_178581_4_, compiledchunk, this.field_178586_f, p_178581_1_, p_178581_2_, p_178581_3_).isCanceled()) return;
				// 149 - VisGraph lvt_9_1_ = new VisGraph();

				// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

				/*
				 * To add new instructions, such as calling a static method can be done like so:
				 *
				 * // make new instruction list InsnList toInject = new InsnList();
				 *
				 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
				 *
				 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
				 */

				final InsnList toInject = new InsnList();
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));

				final Iterator<AbstractInsnNode> iter = methodRebuildChunk.instructions.iterator();

				int index = -1;

				// Loop over the instruction set and find the instruction FDIV which does the division of 1/explosionSize
				while (iter.hasNext()) {
					index++;
					currentNode = iter.next();

					final int FDIV = Opcodes.FDIV;
					// Found it! save the index location of instruction FDIV and the node for this instruction
					if (currentNode.getOpcode() == FDIV) {
						targetNode = currentNode;
						fdiv_index = index;
					}
				}

				// now we want the save nods that load the variable explosionSize and the division instruction:

				/*
				 * mv.visitInsn(FCONST_1); mv.visitVarInsn(ALOAD, 0); mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F"); mv.visitInsn(FDIV); mv.visitInsn(ICONST_0); mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/src/Block", "dropBlockAsItemWithChance", "(Lnet/minecraft/src/World;IIIIFI)V");
				 */

				final AbstractInsnNode remNode1 = methodRebuildChunk.instructions.get(fdiv_index - 2); // mv.visitVarInsn(ALOAD, 0);
				final AbstractInsnNode remNode2 = methodRebuildChunk.instructions.get(fdiv_index - 1); // mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion", "explosionSize", "F");
				final AbstractInsnNode remNode3 = methodRebuildChunk.instructions.get(fdiv_index); // mv.visitInsn(FDIV);

				// just remove these nodes from the instruction set, this will prevent the instruction FCONST_1 to be divided.

				methodRebuildChunk.instructions.remove(remNode1);
				methodRebuildChunk.instructions.remove(remNode2);
				methodRebuildChunk.instructions.remove(remNode3);

				// in this section, i'll just illustrate how to inject a call to a static method if your instruction is a little more advanced than just removing a couple of instruction:

				/*
				 * To add new instructions, such as calling a static method can be done like so:
				 *
				 * // make new instruction list InsnList toInject = new InsnList();
				 *
				 * //add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE* toInject.add(new VarInsnNode(ALOAD, 0)); toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));
				 *
				 * // add the added code to the nstruction list // You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore) m.instructions.insert(targetNode, toInject);
				 */

				System.out.println("Patching Complete!");
				break;
			}
		}

		// ASM specific for cleaning up and returning the final bytes for JVM processing.
		final ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNodeRenderChunk.accept(writer);
		return writer.toByteArray();
	}
}