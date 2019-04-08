package io.github.cadiboo.renderchunkrebuildchunkhooks.util;

import net.minecraft.world.World;

/**
 * @author Cadiboo
 */
public final class WorldReference {

	private World reference;

	public WorldReference(final World reference) {
		this.reference = reference;
	}

	public World get() {
		return reference;
	}

	public World set(final World reference) {
		final World oldReference = this.reference;
		this.reference = reference;
		return oldReference;
	}

}
