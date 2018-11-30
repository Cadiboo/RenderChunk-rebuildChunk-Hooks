package cadiboo.renderchunkrebuildchunkhooks.mod;

import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_ID;
import static cadiboo.renderchunkrebuildchunkhooks.mod.RenderChunkRebuildChunkHooksDummyModContainer.MOD_NAME;

public class RenderChunkRebuildChunkHooksGuiFactory implements IModGuiFactory
{
	public static class RenderChunkRebuildChunkHooksConfigGuiScreen extends GuiConfig
	{

		public RenderChunkRebuildChunkHooksConfigGuiScreen(GuiScreen parent)
		{
			super(parent, getConfigElements(), MOD_ID, false, false, I18n.format(MOD_ID+".config.title"));
		}

		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<>();
			List<IConfigElement> listsList = new ArrayList<>();
			List<IConfigElement> stringsList = new ArrayList<>();
			List<IConfigElement> numbersList = new ArrayList<>();

			// Top Level Settings
			list.add(new DummyConfigElement("imABoolean", true, ConfigGuiType.BOOLEAN, "fml.config.sample.imABoolean").setRequiresMcRestart(true));
			list.add(new DummyConfigElement("imAnInteger", 42, ConfigGuiType.INTEGER, "fml.config.sample.imAnInteger", -1, 256).setRequiresMcRestart(true));
			list.add(new DummyConfigElement("imADouble", 42.4242D, ConfigGuiType.DOUBLE, "fml.config.sample.imADouble", -1.0D, 256.256D).setRequiresMcRestart(true));
			list.add(new DummyConfigElement("imAString", "http://www.montypython.net/scripts/string.php", ConfigGuiType.STRING, "fml.config.sample.imAString").setRequiresMcRestart(true));

			return list;
		}
	}


	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	public static class CustomArrayEntry extends GuiEditArrayEntries.StringEntry
	{
		public CustomArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value)
		{
			super(owningScreen, owningEntryList, configElement, value);
		}

		@Override
		public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partial)
		{
			textFieldValue.setTextColor((int) (Math.random() * 0xFFFFFF));
			super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
		}
	}

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new RenderChunkRebuildChunkHooksConfigGuiScreen(parentScreen);
	}

	private static final Set<RuntimeOptionCategoryElement> fmlCategories = ImmutableSet.of(new RuntimeOptionCategoryElement("HELP", "FML"));

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return fmlCategories;
	}
}