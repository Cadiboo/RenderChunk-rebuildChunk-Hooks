package io.github.cadiboo.renderchunkrebuildchunkhooks.mod;

import io.github.cadiboo.renderchunkrebuildchunkhooks.config.RenderChunkRebuildChunkHooksConfigGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public final class RenderChunkRebuildChunkHooksGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(final Minecraft minecraftInstance) {
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	@Nullable
	public GuiScreen createConfigGui(final GuiScreen parentScreen) {
		return new RenderChunkRebuildChunkHooksConfigGui(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return new HashSet<RuntimeOptionCategoryElement>();
	}

}
