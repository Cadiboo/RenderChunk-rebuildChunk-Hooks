package net.minecraft.client.renderer.chunk;

import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

/**
 * @author Cadiboo
 */
public abstract class ChunkCacheOF extends Chunk implements IWorldReader {

	public ChunkCacheOF(final World p_i48701_1_, final int p_i48701_2_, final int p_i48701_3_, final Biome[] p_i48701_4_) {
		super(p_i48701_1_, p_i48701_2_, p_i48701_3_, p_i48701_4_);
	}

}
