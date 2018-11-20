package mods.octarinecore.common.config;

import java.util.List;
import kotlin.Metadata;
import net.minecraftforge.common.config.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H&J\b\u0010 \u001a\u00020\u001aH\u0016R$\u0010\u0003\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006!"},
   d2 = {"Lmods/octarinecore/common/config/ConfigPropertyBase;", "", "()V", "guiClass", "Ljava/lang/Class;", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries$IConfigEntry;", "getGuiClass", "()Ljava/lang/Class;", "setGuiClass", "(Ljava/lang/Class;)V", "guiProperties", "", "Lnet/minecraftforge/common/config/Property;", "getGuiProperties", "()Ljava/util/List;", "hasChanged", "", "getHasChanged", "()Z", "lang", "", "getLang", "()Ljava/lang/String;", "setLang", "(Ljava/lang/String;)V", "attach", "", "target", "Lnet/minecraftforge/common/config/Configuration;", "langPrefix", "categoryName", "propertyName", "read", "BetterFoliage-MC1.12"}
)
public abstract class ConfigPropertyBase {
   @Nullable
   private String lang;
   @Nullable
   private Class guiClass;

   @Nullable
   public final String getLang() {
      return this.lang;
   }

   public final void setLang(@Nullable String var1) {
      this.lang = var1;
   }

   @Nullable
   public final Class getGuiClass() {
      return this.guiClass;
   }

   public final void setGuiClass(@Nullable Class var1) {
      this.guiClass = var1;
   }

   public abstract boolean getHasChanged();

   public abstract void attach(@NotNull Configuration var1, @NotNull String var2, @NotNull String var3, @NotNull String var4);

   @NotNull
   public abstract List getGuiProperties();

   public void read() {
   }
}
