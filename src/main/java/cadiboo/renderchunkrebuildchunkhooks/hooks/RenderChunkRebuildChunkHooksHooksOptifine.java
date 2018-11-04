package cadiboo.renderchunkrebuildchunkhooks.hooks;

/**
 * @author Cadiboo
 */
public final class RenderChunkRebuildChunkHooksHooksOptifine {

//	/**
//	 * FOR INTERNAL USE ONLY, CALLED BY INJECTED CODE IN RenderChunk#rebuildChunk
//	 *
//	 * @param x                         the translation X passed in from RenderChunk#rebuildChunk
//	 * @param y                         the translation Y passed in from RenderChunk#rebuildChunk
//	 * @param z                         the translation Z passed in from RenderChunk#rebuildChunk
//	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
//	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
//	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
//	 * @param renderGlobal              the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
//	 * @param renderChunksUpdated       the "renderChunksUpdated" variable passed in from RenderChunk#rebuildChunk
//	 * @param lockCompileTask           the {@link ReentrantLock} passed in from RenderChunk#rebuildChunk
//	 * @param setTileEntities           the {@link HashSet} of {@link TileEntity TileEntities} passed in from RenderChunk#rebuildChunk
//	 * @return renderChunksUpdated the amount of Render Chunks Updated (usually renderChunksUpdated+1)
//	 */
//	public static int rebuildChunk(final float x, final float y, final float z, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final MutableBlockPos renderChunkPosition, final ChunkCache worldView, final RenderGlobal renderGlobal, int renderChunksUpdated, final ReentrantLock lockCompileTask, final Set<TileEntity> setTileEntities) {
//
//		final CompiledChunk compiledChunk = new CompiledChunk();
//
//		chunkCompileTaskGenerator.getLock().lock();
//		try {
//			if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
//				return renderChunksUpdated;
//			}
//			chunkCompileTaskGenerator.setCompiledChunk(compiledChunk);
//		} finally {
//			chunkCompileTaskGenerator.getLock().unlock();
//		}
//
//		if (RenderChunkRebuildChunkHooksHooks.onRebuildChunkEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, renderChunkPosition, x, y, z)) {
//			return renderChunksUpdated;
//		}
//
//		final VisGraph visGraph = new VisGraph();
//		final HashSet<TileEntity> tileEntitiesWithGlobalRenderers = Sets.<TileEntity>newHashSet();
//
//		if (!worldView.isEmpty()) {
//			++renderChunksUpdated;
//
//			final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
//
//			final RebuildChunkBlocksEvent rebuildBlocksEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)), blockRendererDispatcher, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
//
//			final boolean[] usedBlockRenderLayers = rebuildBlocksEvent.getUsedBlockRenderLayers();
//
//			for (final MutableBlockPos currentPos : BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15))) {
//				final IBlockState blockState = worldView.getBlockState(currentPos);
//				final Block block = blockState.getBlock();
//
//				if (blockState.isOpaqueCube()) {
//					visGraph.setOpaqueCube(currentPos);
//				}
//
//				if (block.hasTileEntity(blockState)) {
//					final TileEntity tileentity = worldView.getTileEntity(currentPos, Chunk.EnumCreateEntityType.CHECK);
//
//					if (tileentity != null) {
//						final TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.<TileEntity>getRenderer(tileentity);
//
//						if (tileentityspecialrenderer != null) {
//
//							if (tileentityspecialrenderer.isGlobalRenderer(tileentity)) {
//								tileEntitiesWithGlobalRenderers.add(tileentity);
//							} else {
//								compiledChunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
//							}
//						}
//					}
//				}
//
//				for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
//					if (!block.canRenderInLayer(blockState, blockRenderLayer) && !RenderChunkRebuildChunkHooksHooks.canRenderInLayer(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, currentPos, blockState, blockRenderLayer)) {
//						continue;
//					}
//					net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);
//					final int blockRenderLayerId = blockRenderLayer.ordinal();
//
//					if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
//						final BufferBuilder bufferbuilder = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(blockRenderLayerId);
//
//						if (!compiledChunk.isLayerStarted(blockRenderLayer)) {
//							compiledChunk.setLayerStarted(blockRenderLayer);
//							RenderChunkRebuildChunkHooksHooks.preRenderBlocks(bufferbuilder, renderChunkPosition);
//						}
//
//						if (!rebuildBlocksEvent.isCanceled()) {
//							final RebuildChunkBlockEvent rebuildBlockEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, blockState, currentPos, bufferbuilder, renderChunkPosition, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
//
//							for (final BlockRenderLayer blockRenderLayer2 : BlockRenderLayer.values()) {
//								final int blockRenderLayer2Id = blockRenderLayer2.ordinal();
//								usedBlockRenderLayers[blockRenderLayer2Id] |= rebuildBlockEvent.getUsedBlockRenderLayers()[blockRenderLayer2Id];
//							}
//
//							if (!rebuildBlockEvent.isCanceled()) {
//								usedBlockRenderLayers[blockRenderLayerId] |= blockRendererDispatcher.renderBlock(blockState, currentPos, worldView, bufferbuilder);
//							}
//						}
//					}
//				}
//				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
//			}
//
//			for (final BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
//				if (usedBlockRenderLayers[blockRenderLayer.ordinal()]) {
////					compiledChunk.setLayerUsed(blockRenderLayer);
//					RenderChunkRebuildChunkHooksHooks.compiledChunkSetLayerUsed(compiledChunk, blockRenderLayer);
//				}
//
//				if (compiledChunk.isLayerStarted(blockRenderLayer)) {
//					RenderChunkRebuildChunkHooksHooks.postRenderBlocks(blockRenderLayer, x, y, z, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockRenderLayer), compiledChunk);
//				}
//			}
//		}
//
//		compiledChunk.setVisibility(visGraph.computeVisibility());
//		lockCompileTask.lock();
//
//		try {
//			final Set<TileEntity> set = Sets.newHashSet(tileEntitiesWithGlobalRenderers);
//			final Set<TileEntity> set1 = Sets.newHashSet(setTileEntities);
//			set.removeAll(setTileEntities);
//			set1.removeAll(tileEntitiesWithGlobalRenderers);
//			setTileEntities.clear();
//			setTileEntities.addAll(tileEntitiesWithGlobalRenderers);
//			renderGlobal.updateTileEntities(set1, set);
//		} finally {
//			lockCompileTask.unlock();
//		}
//
//		return renderChunksUpdated;
//
//	}
//
//	/**
//	 * FOR INTERNAL USE ONLY, CALLED BY INJECTED CODE IN RenderChunk#rebuildChunk if Optifine is present
//	 *
//	 * @param x                         the translation X passed in from RenderChunk#rebuildChunk
//	 * @param y                         the translation Y passed in from RenderChunk#rebuildChunk
//	 * @param z                         the translation Z passed in from RenderChunk#rebuildChunk
//	 * @param chunkCompileTaskGenerator the {@link ChunkCompileTaskGenerator} passed in from RenderChunk#rebuildChunk
//	 * @param renderChunkPosition       the {@link MutableBlockPos position} passed in from RenderChunk#rebuildChunk
//	 * @param worldView                 the {@link ChunkCache} passed in from RenderChunk#rebuildChunk
//	 * @param renderGlobal              the {@link RenderGlobal} passed in from RenderChunk#rebuildChunk
//	 * @param renderChunksUpdated       the "renderChunksUpdated" variable passed in from RenderChunk#rebuildChunk
//	 * @param lockCompileTask           the {@link ReentrantLock} passed in from RenderChunk#rebuildChunk
//	 * @param setTileEntities           the {@link HashSet} of {@link TileEntity TileEntities} passed in from RenderChunk#rebuildChunk
//	 * @return renderChunksUpdated the amount of Render Chunks Updated (usually renderChunksUpdated+1)
//	 */
//	public static int rebuildChunkOptifine(final float x, final float y, final float z, final ChunkCompileTaskGenerator chunkCompileTaskGenerator, final MutableBlockPos renderChunkPosition, final ChunkCache worldView, final RenderGlobal renderGlobal, int renderChunksUpdated, final ReentrantLock lockCompileTask, final Set<TileEntity> setTileEntities) {
//
////		bxo compiledchunk = new bxo();
//		final CompiledChunk compiledChunk = new CompiledChunk();
////		int i = 1;
//
////		et blockpos = new et(this.o);
////		et blockpos1 = blockpos.a(15, 15, 15);
////		generator.f().lock();
//		chunkCompileTaskGenerator.getLock().lock();
//		try {
////			if (generator.a() != bxl.a.b) {
//			if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
////				return;
//				return renderChunksUpdated;
//			}
////			generator.a(compiledchunk);
//			chunkCompileTaskGenerator.setCompiledChunk(compiledChunk);
//		} finally {
////			generator.f().unlock();
//			chunkCompileTaskGenerator.getLock().unlock();
//		}
//
//		if (RenderChunkRebuildChunkHooksHooks.onRebuildChunkEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, renderChunkPosition, x, y, z)) {
//			return renderChunksUpdated;
//		}
//
////		bxu lvt_9_1_ = new bxu();
//		final VisGraph visGraph = new VisGraph();
////		HashSet lvt_10_1_ = Sets.newHashSet();
//		final HashSet<TileEntity> tileEntitiesWithGlobalRenderers = Sets.<TileEntity>newHashSet();
//
////		if (!isChunkRegionEmpty(blockpos)) {
//		if (!isChunkRegionEmpty(renderChunkPosition)) {
////			a += 1;
//			++renderChunksUpdated;
//
////			ChunkCacheOF blockAccess = makeChunkCacheOF(blockpos);
////			ChunkCacheOF blockAccess = makeChunkCacheOF(renderChunkPosition);
//			final ChunkCache blockAccess = worldView;
//
//			final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
//
//			final RebuildChunkBlocksEvent rebuildBlocksEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlocksEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)), blockRendererDispatcher, renderChunkPosition, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
//
//			final boolean[] usedBlockRenderLayers = rebuildBlocksEvent.getUsedBlockRenderLayers();
//
////			blockAccess.renderStart();
//
////			boolean[] aboolean = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
////			bvm blockrendererdispatcher = bib.z().ab();
//
////			boolean forgeBlockCanRenderInLayerExists = Reflector.ForgeBlock_canRenderInLayer.exists();
////			boolean forgeHooksSetRenderLayerExists = Reflector.ForgeHooksClient_setRenderLayer.exists();
//
////			Iterator iterBlocks = BlockPosM.getAllInBoxMutable(blockpos, blockpos1).iterator();
//			final Iterator<MutableBlockPos> iterBlocks = BlockPos.getAllInBoxMutable(renderChunkPosition, renderChunkPosition.add(15, 15, 15)).iterator();
//			while (iterBlocks.hasNext()) {
////				blockpos$mutableblockpos = (BlockPosM) iterBlocks.next();
//				final MutableBlockPos currentPos = iterBlocks.next();
//
////				iblockstate = blockAccess.o(blockpos$mutableblockpos);
//				final IBlockState blockState = worldView.getBlockState(currentPos);
////				block = iblockstate.u();
//				final Block block = blockState.getBlock();
//
////				if (iblockstate.p()) {
//				if (blockState.isOpaqueCube()) {
////					lvt_9_1_.a(blockpos$mutableblockpos);
//					visGraph.setOpaqueCube(currentPos);
//				}
////				if (ReflectorForge.blockHasTileEntity(iblockstate)) {
//				if (block.hasTileEntity(blockState)) {
////					avj tileentity = blockAccess.getTileEntity(blockpos$mutableblockpos, axw.a.c);
//					final TileEntity tileentity = worldView.getTileEntity(currentPos, Chunk.EnumCreateEntityType.CHECK);
//					if (tileentity != null) {
////						bwy<avj> tileentityspecialrenderer = bwx.a.a(tileentity);
//						final TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.<TileEntity>getRenderer(tileentity);
//						if (tileentityspecialrenderer != null) {
////							if (tileentityspecialrenderer.a(tileentity)) {
//							if (tileentityspecialrenderer.isGlobalRenderer(tileentity)) {
////								lvt_10_1_.add(tileentity);
//								tileEntitiesWithGlobalRenderers.add(tileentity);
//							} else {
////								compiledchunk.a(tileentity);
//								compiledChunk.addTileEntity(tileentity); // FORGE: Fix MC-112730
//							}
//						}
//					}
//				}
//
////				amm[] blockLayers;
////				amm[] blockLayers;
////				if (forgeBlockCanRenderInLayerExists) {
////					blockLayers = ENUM_WORLD_BLOCK_LAYERS;
////				} else {
////					blockLayers = this.blockLayersSingle;
////					blockLayers[0] = block.f();
////				}
////				for (int ix = 0; ix < blockLayers.length; ix++) {
////					amm blockrenderlayer1 = blockLayers[ix];
////					if (forgeBlockCanRenderInLayerExists) {
////						boolean canRenderInLayer = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { iblockstate, blockrenderlayer1 });
////						if (!canRenderInLayer) {
////						}
////					} else {
////						if (forgeHooksSetRenderLayerExists) {
////							Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { blockrenderlayer1 });
////						}
//
//				for (BlockRenderLayer blockRenderLayer : BlockRenderLayer.values()) {
//					if (!block.canRenderInLayer(blockState, blockRenderLayer) && !RenderChunkRebuildChunkHooksHooks.canRenderInLayer(worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, renderChunkPosition, visGraph, currentPos, blockState, blockRenderLayer)) {
//						continue;
//					}
//					net.minecraftforge.client.ForgeHooksClient.setRenderLayer(blockRenderLayer);
//					final int blockRenderLayerId = blockRenderLayer.ordinal();
//
////					blockrenderlayer1 = fixBlockLayer(iblockstate, blockrenderlayer1);
//					blockRenderLayer = fixBlockLayer(blockState, blockRenderLayer);
//
//					final int j = blockRenderLayer.ordinal();
////					if (block.t().i() != atj.a) {
//					if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
////						buk bufferbuilder = generator.d().a(j);
//						final BufferBuilder bufferbuilder = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(blockRenderLayerId);
//
////						bufferbuilder.setBlockLayer(blockrenderlayer1);
//
////						RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockAccess, iblockstate, blockpos$mutableblockpos);
////						RenderEnv renderEnv = bufferbuilder.getRenderEnv(blockAccess, blockState, currentPos);
////						renderEnv.setRegionRenderCacheBuilder(generator.d());
////						renderEnv.setRegionRenderCacheBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
////						if (!compiledchunk.d(blockrenderlayer1)) {
//						if (!compiledChunk.isLayerStarted(blockRenderLayer)) {
////							compiledchunk.c(blockrenderlayer1);
//							compiledChunk.setLayerStarted(blockRenderLayer);
////							a(bufferbuilder, blockpos);
//							RenderChunkRebuildChunkHooksHooks.preRenderBlocks(bufferbuilder, renderChunkPosition);
//						}
//
//						if (!rebuildBlocksEvent.isCanceled()) {
//							final RebuildChunkBlockEvent rebuildBlockEvent = RenderChunkRebuildChunkHooksHooks.onRebuildChunkBlockEvent(renderGlobal, worldView, chunkCompileTaskGenerator, compiledChunk, blockRendererDispatcher, blockState, currentPos, bufferbuilder, renderChunkPosition, blockRenderLayer, x, y, z, tileEntitiesWithGlobalRenderers, visGraph);
//
//							for (final BlockRenderLayer blockRenderLayer2 : BlockRenderLayer.values()) {
//								final int blockRenderLayer2Id = blockRenderLayer2.ordinal();
//								usedBlockRenderLayers[blockRenderLayer2Id] |= rebuildBlockEvent.getUsedBlockRenderLayers()[blockRenderLayer2Id];
//							}
//
//							if (!rebuildBlockEvent.isCanceled()) {
////								aboolean[j] |= blockrendererdispatcher.a(iblockstate, blockpos$mutableblockpos, blockAccess, bufferbuilder);
//								usedBlockRenderLayers[blockRenderLayerId] |= blockRendererDispatcher.renderBlock(blockState, currentPos, worldView, bufferbuilder);
////								if (renderEnv.isOverlaysRendered()) {
////									postRenderOverlays(generator.d(), compiledchunk, aboolean);
////									renderEnv.setOverlaysRendered(false);
////								}
//							}
//						}
//
//					}
////					}
//				}
////				if (forgeHooksSetRenderLayerExists) {
////					Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { null });
////				}
//				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
//			}
//
////			BlockPosM blockpos$mutableblockpos = ENUM_WORLD_BLOCK_LAYERS;
////			awt iblockstate = blockpos$mutableblockpos.length;
////			for (aow block = 0; block < iblockstate; block++) {
////				amm blockrenderlayer = blockpos$mutableblockpos[block];
////				if (aboolean[blockrenderlayer.ordinal()] != 0) {
////					compiledchunk.a(blockrenderlayer);
////				}
////				if (compiledchunk.d(blockrenderlayer)) {
////					if (Config.isShaders()) {
////						SVertexBuilder.calcNormalChunkLayer(generator.d().a(blockrenderlayer));
////					}
////					buk bufferBuilder = generator.d().a(blockrenderlayer);
////					a(blockrenderlayer, x, y, z, bufferBuilder, compiledchunk);
////					if (bufferBuilder.animatedSprites != null) {
////						compiledchunk.setAnimatedSprites(blockrenderlayer, (BitSet) bufferBuilder.animatedSprites.clone());
////					}
////				} else {
////					compiledchunk.setAnimatedSprites(blockrenderlayer, null);
////				}
////			}
////			blockAccess.renderFinish();
//		}
//
//		/**
//		 * Okay first of all I have not much experience in java. I'm like just guessing. My theory is starting at line 361: This line gets some sort of length, which means it should be a number (maybe an integer or float). In the next line (line 362) there's a loop that counts up (by one) until the length of the previous-said blockpos thing. Then in the first if satement (line 364) it checks if the blockrenderlayer ordinal (what is an ordinal?) is not 0 and then just executes the method. In the
//		 * second statement (line 367) it looks like blockrenderlayer (whatever this is) is casted to compiledchunk.d (d is most likely an obfuscated method). Maybe the next if statement (line 368) checks if shaders are enabled and then skips some optimizations (because of the word calcNormalChunkLayer). Next step is to build some buffers (line 371 + 372); some graphical cache stuff that I don't understand yet). Then it checks if the animated sprites in the bufferBuilder aren't 0 (maybe some
//		 * optimization like SmartAnimations). Then it sets the animated sprites. Else (line 376) it sets those to null. At last it finishes rendering (blockAccess.renderFinish();)
//		 */
//
////		compiledchunk.a(lvt_9_1_.a());
//		compiledChunk.setVisibility(visGraph.computeVisibility());
////		this.f.lock();
//		lockCompileTask.lock();
//		try
//
//		{
////			Set<avj> set = Sets.newHashSet(lvt_10_1_);
//			final Set<TileEntity> set = Sets.newHashSet(tileEntitiesWithGlobalRenderers);
////			Set<avj> set1 = Sets.newHashSet(this.i);
//			final Set<TileEntity> set1 = Sets.newHashSet(setTileEntities);
////			set.removeAll(this.i);
//			set.removeAll(setTileEntities);
////			set1.removeAll(lvt_10_1_);
//			set1.removeAll(tileEntitiesWithGlobalRenderers);
////			this.i.clear();
//			setTileEntities.clear();
////			this.i.addAll(lvt_10_1_);
//			setTileEntities.addAll(tileEntitiesWithGlobalRenderers);
////			this.e.a(set1, set);
//			renderGlobal.updateTileEntities(set1, set);
//		} finally {
////			this.f.unlock();
//			lockCompileTask.unlock();
//		}
//
//		return renderChunksUpdated;
//
//	}
//
//	private static BlockRenderLayer fixBlockLayer(final IBlockState blockState, final BlockRenderLayer layer) {
//		if (CustomBlockLayers.isActive()) {
//			final amm layerCustom = CustomBlockLayers.getRenderLayer(blockState);
//			if (layerCustom != null) {
//				return layerCustom;
//			}
//		}
//		if (!this.fixBlockLayer) {
//			return layer;
//		}
//		if (this.isMipmaps) {
//			if (layer == amm.c) {
//				final aow block = blockState.u();
//				if ((block instanceof atf)) {
//					return layer;
//				}
//				if ((block instanceof ape)) {
//					return layer;
//				}
//				return amm.b;
//			}
//		} else if (layer == amm.b) {
//			return amm.c;
//		}
//		return layer;
//	}
//
//	private void postRenderOverlays(final RegionRenderCacheBuilder regionRenderCacheBuilder, final CompiledChunk compiledChunk, final boolean[] layerFlags) {
//		this.postRenderOverlay(amm.c, regionRenderCacheBuilder, compiledChunk, layerFlags);
//		this.postRenderOverlay(amm.b, regionRenderCacheBuilder, compiledChunk, layerFlags);
//		this.postRenderOverlay(amm.d, regionRenderCacheBuilder, compiledChunk, layerFlags);
//	}
//
//	private void postRenderOverlay(final BlockRenderLayer layer, final RegionRenderCacheBuilder regionRenderCacheBuilder, final CompiledChunk compiledchunk, final boolean[] layerFlags) {
//		final buk bufferOverlay = regionRenderCacheBuilder.a(layer);
//		if (bufferOverlay.isDrawing()) {
//			compiledchunk.c(layer);
//			layerFlags[layer.ordinal()] = true;
//		}
//	}
//
//	private static ChunkCacheOF makeChunkCacheOF(final BlockPos posIn) {
//		final et posFrom = posIn.a(-1, -1, -1);
//		final et posTo = posIn.a(16, 16, 16);
//		final and chunkCache = this.createRegionRenderCache(this.d, posFrom, posTo, 1);
//		if (Reflector.MinecraftForgeClient_onRebuildChunk.exists()) {
//			Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, new Object[] { this.d, posIn, chunkCache });
//		}
//		final ChunkCacheOF chunkCacheOF = new ChunkCacheOF(chunkCache, posFrom, posTo, 1);
//
//		return chunkCacheOF;
//	}
//
//	public RenderChunk getRenderChunkOffset16(final ViewFrustum viewFrustum, final EnumFacing facing) {
//		if (!this.renderChunksOffset16Updated) {
//			for (int i = 0; i < fa.n.length; i++) {
//				final fa ef = fa.n[i];
//				final et posOffset16 = a(ef);
//				this.renderChunksOfset16[i] = viewFrustum.a(posOffset16);
//			}
//			this.renderChunksOffset16Updated = true;
//		}
//		return this.renderChunksOfset16[facing.ordinal()];
//	}
//
//	public axw getChunk() {
//		return this.getChunk(this.o);
//	}
//
//	private axw getChunk(final BlockPos posIn) {
//		axw chunkLocal = this.chunk;
//		if ((chunkLocal != null) && (chunkLocal.p())) {
//			return chunkLocal;
//		}
//		chunkLocal = this.d.f(posIn);
//
//		this.chunk = chunkLocal;
//
//		return chunkLocal;
//	}
//
//	public boolean isChunkRegionEmpty() {
//		return isChunkRegionEmpty(this.o);
//	}
//
//	private static boolean isChunkRegionEmpty(final BlockPos posIn) {
//		final int yStart = posIn.q();
//		final int yEnd = yStart + 15;
//		return this.getChunk(posIn).c(yStart, yEnd);
//	}
//
//	public void setRenderChunkNeighbour(final EnumFacing facing, final RenderChunk neighbour) {
//		this.renderChunkNeighbours[facing.ordinal()] = neighbour;
//		this.renderChunkNeighboursValid[facing.ordinal()] = neighbour;
//	}
//
//	public RenderChunk getRenderChunkNeighbour(final EnumFacing facing) {
//		if (!this.renderChunkNeighboursUpated) {
//			this.updateRenderChunkNeighboursValid();
//		}
//		return this.renderChunkNeighboursValid[facing.ordinal()];
//	}
//
//	public buy.a getRenderInfo() {
//		return this.renderInfo;
//	}
//
//	private void updateRenderChunkNeighboursValid() {
//		final int x = k().p();
//		final int z = k().r();
//
//		final int north = fa.c.ordinal();
//		final int south = fa.d.ordinal();
//		final int west = fa.e.ordinal();
//		final int east = fa.f.ordinal();
//
//		this.renderChunkNeighboursValid[north] = (this.renderChunkNeighbours[north].k().r() == (z - 16) ? this.renderChunkNeighbours[north] : null);
//		this.renderChunkNeighboursValid[south] = (this.renderChunkNeighbours[south].k().r() == (z + 16) ? this.renderChunkNeighbours[south] : null);
//		this.renderChunkNeighboursValid[west] = (this.renderChunkNeighbours[west].k().p() == (x - 16) ? this.renderChunkNeighbours[west] : null);
//		this.renderChunkNeighboursValid[east] = (this.renderChunkNeighbours[east].k().p() == (x + 16) ? this.renderChunkNeighbours[east] : null);
//
//		this.renderChunkNeighboursUpated = true;
//	}
//
//	public boolean isBoundingBoxInFrustum(final ViewFrustum camera, final int frameCount) {
//		if (this.getBoundingBoxParent().isBoundingBoxInFrustumFully(camera, frameCount)) {
//			return true;
//		}
//		return camera.a(this.c);
//	}
//
//	public AabbFrame getBoundingBoxParent() {
//		if (this.boundingBoxParent == null) {
//			final et pos = k();
//			final int x = pos.p();
//			final int y = pos.q();
//			final int z = pos.r();
//
//			final int bits = 5;
//			final int xp = (x >> bits) << bits;
//			final int yp = (y >> bits) << bits;
//			final int zp = (z >> bits) << bits;
//			if ((xp != x) || (yp != y) || (zp != z)) {
//				final AabbFrame bbp = this.e.getRenderChunk(new et(xp, yp, zp)).getBoundingBoxParent();
//				if ((bbp != null) && (bbp.a == xp) && (bbp.b == yp) && (bbp.c == zp)) {
//					this.boundingBoxParent = bbp;
//				}
//			}
//			if (this.boundingBoxParent == null) {
//				final int delta = 1 << bits;
//				this.boundingBoxParent = new AabbFrame(xp, yp, zp, xp + delta, yp + delta, zp + delta);
//			}
//		}
//		return this.boundingBoxParent;
//	}
//
//	@Override
//	public String toString() {
//		return "pos: " + k() + ", frameIndex: " + this.m;
//	}
//
//	public ChunkCache createRegionRenderCache(final World world, final BlockPos from, final BlockPos to, final int subtract) {
//		return new ChunkCache(world, from, to, subtract);
//	}

}
