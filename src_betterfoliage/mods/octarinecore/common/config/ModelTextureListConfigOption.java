package mods.octarinecore.common.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u0004H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"},
   d2 = {"Lmods/octarinecore/common/config/ModelTextureListConfigOption;", "Lmods/octarinecore/common/config/StringListConfigOption;", "Lmods/octarinecore/common/config/ModelTextureList;", "domain", "", "path", "minTextures", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getMinTextures", "()I", "convertValue", "line", "BetterFoliage-MC1.12"}
)
public final class ModelTextureListConfigOption extends StringListConfigOption {
   private final int minTextures;

   @Nullable
   public ModelTextureList convertValue(@NotNull String line) {
      Intrinsics.checkParameterIsNotNull(line, "line");
      List elements = StringsKt.split$default((CharSequence)line, new String[]{","}, false, 0, 6, (Object)null);
      return elements.size() < this.minTextures + 1 ? null : new ModelTextureList(new ResourceLocation((String)CollectionsKt.first(elements)), CollectionsKt.drop((Iterable)elements, 1));
   }

   public final int getMinTextures() {
      return this.minTextures;
   }

   public ModelTextureListConfigOption(@NotNull String domain, @NotNull String path, int minTextures) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      super(domain, path);
      this.minTextures = minTextures;
   }
}
