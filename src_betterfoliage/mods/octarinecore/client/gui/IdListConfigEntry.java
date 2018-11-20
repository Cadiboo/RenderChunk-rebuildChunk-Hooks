package mods.octarinecore.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.ConfigGuiType;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u001cB\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0016\u001a\u00020\u0017H\u0014J\u0016\u0010\u0018\u001a\u0012\u0012\u000e\u0012\f0\u0019R\b\u0012\u0004\u0012\u00028\u00000\u00000\u000bJ\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u00020\u000f*\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u00020\u0013*\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001d"},
   d2 = {"Lmods/octarinecore/client/gui/IdListConfigEntry;", "T", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries$CategoryEntry;", "owningScreen", "Lnet/minecraftforge/fml/client/config/GuiConfig;", "owningEntryList", "Lnet/minecraftforge/fml/client/config/GuiConfigEntries;", "configElement", "Lnet/minecraftforge/fml/client/config/IConfigElement;", "(Lnet/minecraftforge/fml/client/config/GuiConfig;Lnet/minecraftforge/fml/client/config/GuiConfigEntries;Lnet/minecraftforge/fml/client/config/IConfigElement;)V", "baseSet", "", "getBaseSet", "()Ljava/util/List;", "itemId", "", "getItemId", "(Ljava/lang/Object;)I", "itemName", "", "getItemName", "(Ljava/lang/Object;)Ljava/lang/String;", "buildChildScreen", "Lnet/minecraft/client/gui/GuiScreen;", "createChildren", "Lmods/octarinecore/client/gui/IdListConfigEntry$ItemWrapperElement;", "saveConfigElement", "", "ItemWrapperElement", "BetterFoliage-MC1.12"}
)
public abstract class IdListConfigEntry extends CategoryEntry {
   @NotNull
   public final List createChildren() {
      Iterable $receiver$iv = (Iterable)this.getBaseSet();
      Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
      Iterator var4 = $receiver$iv.iterator();

      while(var4.hasNext()) {
         Object item$iv$iv = var4.next();
         IConfigElement var10004 = this.configElement;
         Intrinsics.checkExpressionValueIsNotNull(this.configElement, "configElement");
         Object[] var13 = var10004.getList();
         Intrinsics.checkExpressionValueIsNotNull(var13, "configElement.list");
         boolean var14 = ArraysKt.contains(var13, this.getItemId(item$iv$iv));
         IConfigElement var10005 = this.configElement;
         Intrinsics.checkExpressionValueIsNotNull(this.configElement, "configElement");
         Object[] var12 = var10005.getDefaults();
         Intrinsics.checkExpressionValueIsNotNull(var12, "configElement.defaults");
         IdListConfigEntry.ItemWrapperElement var11 = new IdListConfigEntry.ItemWrapperElement(item$iv$iv, var14, ArraysKt.contains(var12, this.getItemId(item$iv$iv)));
         destination$iv$iv.add(var11);
      }

      return (List)destination$iv$iv;
   }

   @NotNull
   protected GuiScreen buildChildScreen() {
      return (GuiScreen)(new GuiConfig((GuiScreen)this.owningScreen, this.createChildren(), this.owningScreen.modID, this.owningScreen.allRequireWorldRestart || this.configElement.requiresWorldRestart(), this.owningScreen.allRequireMcRestart || this.configElement.requiresMcRestart(), this.owningScreen.title, (this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
   }

   public boolean saveConfigElement() {
      GuiScreen var10000 = this.childScreen;
      if (this.childScreen == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.minecraftforge.fml.client.config.GuiConfig");
      } else {
         boolean requiresRestart = ((GuiConfig)var10000).entryList.saveConfigElements();
         var10000 = this.childScreen;
         if (this.childScreen == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraftforge.fml.client.config.GuiConfig");
         } else {
            List var17 = ((GuiConfig)var10000).configElements;
            if (var17 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<mods.octarinecore.client.gui.IdListConfigEntry<T>.ItemWrapperElement>");
            } else {
               List children = var17;
               Iterable $receiver$iv = (Iterable)children;
               Collection destination$iv$iv = (Collection)(new ArrayList());
               Iterator var7 = $receiver$iv.iterator();

               Object item$iv$iv;
               IdListConfigEntry.ItemWrapperElement it;
               while(var7.hasNext()) {
                  item$iv$iv = var7.next();
                  it = (IdListConfigEntry.ItemWrapperElement)item$iv$iv;
                  if (it.getBooleanValue()) {
                     destination$iv$iv.add(item$iv$iv);
                  }
               }

               $receiver$iv = (Iterable)((List)destination$iv$iv);
               destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
               var7 = $receiver$iv.iterator();

               while(var7.hasNext()) {
                  item$iv$iv = var7.next();
                  it = (IdListConfigEntry.ItemWrapperElement)item$iv$iv;
                  Integer var14 = this.getItemId(it.getItem());
                  destination$iv$iv.add(var14);
               }

               List ids = (List)destination$iv$iv;
               IConfigElement var18 = this.configElement;
               Collection $receiver$iv = (Collection)CollectionsKt.sorted((Iterable)ids);
               IConfigElement var13 = var18;
               if ($receiver$iv == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
               } else {
                  Object[] var19 = $receiver$iv.toArray(new Integer[0]);
                  if (var19 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                  } else {
                     Object[] var16 = var19;
                     var13.set(var16);
                     return requiresRestart;
                  }
               }
            }
         }
      }
   }

   @NotNull
   public abstract List getBaseSet();

   public abstract int getItemId(Object var1);

   @NotNull
   public abstract String getItemName(Object var1);

   public IdListConfigEntry(@NotNull GuiConfig owningScreen, @NotNull GuiConfigEntries owningEntryList, @NotNull IConfigElement configElement) {
      Intrinsics.checkParameterIsNotNull(owningScreen, "owningScreen");
      Intrinsics.checkParameterIsNotNull(owningEntryList, "owningEntryList");
      Intrinsics.checkParameterIsNotNull(configElement, "configElement");
      super(owningScreen, owningEntryList, configElement);
      List var10000 = this.toolTip;
      if (this.toolTip == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<kotlin.String>");
      } else {
         Utils.stripTooltipDefaultText(TypeIntrinsics.asMutableList(var10000));
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00028\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000fH\u0016R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0002\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"},
      d2 = {"Lmods/octarinecore/client/gui/IdListConfigEntry$ItemWrapperElement;", "Lnet/minecraftforge/fml/client/config/DummyConfigElement;", "item", "value", "", "default", "(Lmods/octarinecore/client/gui/IdListConfigEntry;Ljava/lang/Object;ZZ)V", "booleanValue", "getBooleanValue", "()Z", "getDefault", "getItem", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getComment", "", "kotlin.jvm.PlatformType", "BetterFoliage-MC1.12"}
   )
   public final class ItemWrapperElement extends DummyConfigElement {
      private final Object item;
      private final boolean default;

      public String getComment() {
         StringBuilder var10000 = (new StringBuilder()).append("");
         IConfigElement var10001 = IdListConfigEntry.this.configElement;
         Intrinsics.checkExpressionValueIsNotNull(var10001, "configElement");
         return I18n.func_135052_a(var10000.append(var10001.getLanguageKey()).append(".tooltip.element").toString(), new Object[]{"" + TextFormatting.GOLD + "" + IdListConfigEntry.this.getItemName(this.item) + "" + TextFormatting.YELLOW});
      }

      public final boolean getBooleanValue() {
         Object var10000 = this.defaultValue;
         if (this.defaultValue == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
         } else {
            return ((Boolean)var10000).booleanValue();
         }
      }

      public final Object getItem() {
         return this.item;
      }

      public final boolean getDefault() {
         return this.default;
      }

      public ItemWrapperElement(Object item, boolean value, boolean var4) {
         super(IdListConfigEntry.this.getItemName(item), var4, ConfigGuiType.BOOLEAN, IdListConfigEntry.this.getItemName(item));
         this.item = item;
         this.default = var4;
         this.value = value;
         this.defaultValue = this.default;
      }
   }
}
