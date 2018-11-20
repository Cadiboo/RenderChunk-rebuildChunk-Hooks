package mods.betterfoliage.client.gui;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.octarinecore.client.gui.IdListConfigEntry;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"},
   d2 = {"Lmods/betterfoliage/client/gui/BiomeListConfigEntry;", "Lmods/octarinecore/client/gui/IdListConfigEntry;", "Lnet/minecraft/world/biome/Biome;", "owningScreen", "Lnet/minecraftforge/fml/client/config/GuiConfig;", "owningEntryList", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries;", "configElement", "Lnet/minecraftforge/fml/client/config/IConfigElement;", "(Lnet/minecraftforge/fml/client/config/GuiConfig;Lnet/minecraftforge/fml/client/config/GuiConfigEntries;Lnet/minecraftforge/fml/client/config/IConfigElement;)V", "baseSet", "", "getBaseSet", "()Ljava/util/List;", "itemId", "", "getItemId", "(Lnet/minecraft/world/biome/Biome;)I", "itemName", "", "getItemName", "(Lnet/minecraft/world/biome/Biome;)Ljava/lang/String;", "BetterFoliage-MC1.12"}
)
public final class BiomeListConfigEntry extends IdListConfigEntry {
   @NotNull
   public List getBaseSet() {
      RegistryNamespaced var10000 = Biome.field_185377_q;
      Intrinsics.checkExpressionValueIsNotNull(Biome.field_185377_q, "Biome.REGISTRY");
      return CollectionsKt.filterNotNull((Iterable)var10000);
   }

   public int getItemId(@NotNull Biome $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return Biome.field_185377_q.func_148757_b($receiver);
   }

   @NotNull
   public String getItemName(@NotNull Biome $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      String var10000 = $receiver.func_185359_l();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "this.biomeName");
      return var10000;
   }

   public BiomeListConfigEntry(@NotNull GuiConfig owningScreen, @NotNull GuiConfigEntries owningEntryList, @NotNull IConfigElement configElement) {
      Intrinsics.checkParameterIsNotNull(owningScreen, "owningScreen");
      Intrinsics.checkParameterIsNotNull(owningEntryList, "owningEntryList");
      Intrinsics.checkParameterIsNotNull(configElement, "configElement");
      super(owningScreen, owningEntryList, configElement);
   }
}
