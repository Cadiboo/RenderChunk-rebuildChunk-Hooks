package mods.betterfoliage.loader;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"mods.betterfoliage.loader", "mods.octarinecore.metaprog", "kotlin"})
@MCVersion("1.12.2")
@SortingIndex(1400)
public class BetterFoliageLoader implements IFMLLoadingPlugin {
   public String[] getASMTransformerClass() {
      return new String[]{"mods.betterfoliage.loader.BetterFoliageTransformer"};
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
