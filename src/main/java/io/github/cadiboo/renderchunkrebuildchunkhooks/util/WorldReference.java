package io.github.cadiboo.renderchunkrebuildchunkhooks.util;

import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author Cadiboo
 */
public final class WorldReference {

	@Nullable
	private World reference;

	public WorldReference(@Nullable final World reference) {
		this.reference = reference;
	}

	@Nullable
	public World get() {
		return reference;
	}

	@Nullable
	public World set(final World reference) {
		final World oldReference = this.reference;
		this.reference = reference;
		return oldReference;
	}

}
