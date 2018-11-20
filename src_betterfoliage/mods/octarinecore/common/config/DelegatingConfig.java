package mods.octarinecore.common.config;

import com.google.common.collect.LinkedListMultimap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.Utils;
import mods.octarinecore.metaprog.MethodRef;
import mods.octarinecore.metaprog.Reflection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.PostConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u0007J)\u0010\u0016\u001a\u00020\u00152\u001e\u0010\u0017\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00150\u0018H\u0086\bJ\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0012\u0010\u001d\u001a\u00020\u001e2\n\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030 J\u0010\u0010!\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH&J\u0006\u0010\"\u001a\u00020\u0015J\u0017\u0010#\u001a\u0004\u0018\u00010\u0019*\u00020\u00012\u0006\u0010$\u001a\u00020\u0003H\u0086\u0002R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006%"},
   d2 = {"Lmods/octarinecore/common/config/DelegatingConfig;", "", "modId", "", "langPrefix", "(Ljava/lang/String;Ljava/lang/String;)V", "config", "Lnet/minecraftforge/common/config/Configuration;", "getConfig", "()Lnet/minecraftforge/common/config/Configuration;", "setConfig", "(Lnet/minecraftforge/common/config/Configuration;)V", "getLangPrefix", "()Ljava/lang/String;", "getModId", "rootGuiElements", "", "Lnet/minecraftforge/fml/client/config/IConfigElement;", "getRootGuiElements", "()Ljava/util/List;", "attach", "", "forEachProperty", "init", "Lkotlin/Function3;", "Lmods/octarinecore/common/config/ConfigPropertyBase;", "handleConfigChange", "event", "Lnet/minecraftforge/fml/client/event/ConfigChangedEvent$PostConfigChangedEvent;", "hasChanged", "", "elements", "", "onChange", "save", "get", "name", "BetterFoliage-MC1.12"}
)
public abstract class DelegatingConfig {
   @Nullable
   private Configuration config;
   @NotNull
   private final List rootGuiElements;
   @NotNull
   private final String modId;
   @NotNull
   private final String langPrefix;

   @Nullable
   public final Configuration getConfig() {
      return this.config;
   }

   public final void setConfig(@Nullable Configuration var1) {
      this.config = var1;
   }

   @NotNull
   public final List getRootGuiElements() {
      return this.rootGuiElements;
   }

   public final void attach(@NotNull Configuration config) {
      Intrinsics.checkParameterIsNotNull(config, "config");
      this.config = config;
      LinkedListMultimap subProperties = LinkedListMultimap.create();
      this.rootGuiElements.clear();
      Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(this, ConfigPropertyBase.class);
      Iterator var5 = $receiver$iv$iv.iterator();

      ConfigPropertyBase property;
      String name;
      String category;
      Iterable $receiver$iv;
      Iterator var12;
      Object element$iv;
      Property guiProperty;
      Class var15;
      Class var10000;
      Object var10001;
      Object var10002;
      String var36;
      while(var5.hasNext()) {
         Object element$iv$iv = var5.next();
         Pair property$iv = (Pair)element$iv$iv;
         var10001 = property$iv.getFirst();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
         var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
         var10002 = property$iv.getSecond();
         if (var10002 == null) {
            throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
         }

         property = (ConfigPropertyBase)var10002;
         name = (String)var10001;
         category = "global";
         var36 = property.getLang();
         if (var36 == null) {
            var36 = "" + category + '.' + name;
         }

         property.setLang(var36);
         property.attach(config, this.langPrefix, category, name);
         $receiver$iv = (Iterable)property.getGuiProperties();
         var12 = $receiver$iv.iterator();

         while(var12.hasNext()) {
            element$iv = var12.next();
            guiProperty = (Property)element$iv;
            var10000 = property.getGuiClass();
            if (var10000 != null) {
               var15 = var10000;
               guiProperty.setConfigEntryClass(var15);
            }

            if (Intrinsics.areEqual(category, "global")) {
               this.rootGuiElements.add(new ConfigElement(guiProperty));
            } else {
               subProperties.put(category, guiProperty.getName());
            }
         }
      }

      var5 = Reflection.getReflectNestedObjects(this).iterator();

      while(var5.hasNext()) {
         Pair category$iv = (Pair)var5.next();
         Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(category$iv.getSecond(), ConfigPropertyBase.class);
         Iterator var33 = $receiver$iv$iv.iterator();

         while(var33.hasNext()) {
            Object element$iv$iv = var33.next();
            Pair property$iv = (Pair)element$iv$iv;
            Object var34 = category$iv.getFirst();
            var10001 = property$iv.getFirst();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
            var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
            var10002 = property$iv.getSecond();
            if (var10002 == null) {
               throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
            }

            property = (ConfigPropertyBase)var10002;
            name = (String)var10001;
            category = (String)var34;
            var36 = property.getLang();
            if (var36 == null) {
               var36 = "" + category + '.' + name;
            }

            property.setLang(var36);
            property.attach(config, this.langPrefix, category, name);
            $receiver$iv = (Iterable)property.getGuiProperties();
            var12 = $receiver$iv.iterator();

            while(var12.hasNext()) {
               element$iv = var12.next();
               guiProperty = (Property)element$iv;
               var10000 = property.getGuiClass();
               if (var10000 != null) {
                  var15 = var10000;
                  guiProperty.setConfigEntryClass(var15);
               }

               if (Intrinsics.areEqual(category, "global")) {
                  this.rootGuiElements.add(new ConfigElement(guiProperty));
               } else {
                  subProperties.put(category, guiProperty.getName());
               }
            }
         }
      }

      Iterator var28 = subProperties.keySet().iterator();

      while(var28.hasNext()) {
         String category = (String)var28.next();
         ConfigCategory configCategory = config.getCategory(category);
         configCategory.setLanguageKey("" + this.langPrefix + '.' + category);
         configCategory.setPropertyOrder(subProperties.get(category));
         this.rootGuiElements.add(new ConfigElement(configCategory));
      }

      this.save();
      Set var35 = config.getCategoryNames();
      Intrinsics.checkExpressionValueIsNotNull(var35, "config.categoryNames");
      Iterable $receiver$iv = (Iterable)var35;
      var28 = $receiver$iv.iterator();

      while(var28.hasNext()) {
         Object element$iv = var28.next();
         String it = (String)element$iv;
         config.getCategory(it).setShowInGui(subProperties.keySet().contains(it));
      }

   }

   public final void forEachProperty(@NotNull Function3 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      Iterable $receiver$iv = (Iterable)Reflection.reflectFieldsOfType(this, ConfigPropertyBase.class);
      Iterator var4 = $receiver$iv.iterator();

      Object var10002;
      Object var10003;
      while(var4.hasNext()) {
         Object element$iv = var4.next();
         Pair property = (Pair)element$iv;
         var10002 = property.getFirst();
         Intrinsics.checkExpressionValueIsNotNull(var10002, "property.first");
         var10002 = StringsKt.split$default((CharSequence)var10002, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
         var10003 = property.getSecond();
         if (var10003 == null) {
            throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
         }

         init.invoke("global", var10002, (ConfigPropertyBase)var10003);
      }

      var4 = Reflection.getReflectNestedObjects(this).iterator();

      while(var4.hasNext()) {
         Pair category = (Pair)var4.next();
         Iterable $receiver$iv = (Iterable)Reflection.reflectFieldsOfType(category.getSecond(), ConfigPropertyBase.class);
         Iterator var13 = $receiver$iv.iterator();

         while(var13.hasNext()) {
            Object element$iv = var13.next();
            Pair property = (Pair)element$iv;
            Object var10001 = category.getFirst();
            var10002 = property.getFirst();
            Intrinsics.checkExpressionValueIsNotNull(var10002, "property.first");
            var10002 = StringsKt.split$default((CharSequence)var10002, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
            var10003 = property.getSecond();
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
            }

            init.invoke(var10001, var10002, (ConfigPropertyBase)var10003);
         }
      }

   }

   public final void save() {
      if (this.config != null ? this.config.hasChanged() : false) {
         Configuration var10000 = this.config;
         if (this.config == null) {
            Intrinsics.throwNpe();
         }

         var10000.save();
      }

   }

   public final boolean hasChanged(@NotNull List elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      Iterable $receiver$iv = (Iterable)Reflection.getReflectNestedObjects(this);
      Iterator var3 = $receiver$iv.iterator();

      while(var3.hasNext()) {
         Object element$iv = var3.next();
         Pair category = (Pair)element$iv;
         if (elements.contains(category.getSecond())) {
            boolean var23;
            label68: {
               Configuration var10000 = this.config;
               if (this.config != null) {
                  ConfigCategory var22 = var10000.getCategory((String)category.getFirst());
                  if (var22 != null) {
                     var23 = var22.hasChanged();
                     break label68;
                  }
               }

               var23 = false;
            }

            if (var23) {
               return true;
            }
         }
      }

      Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(this, ConfigPropertyBase.class);
      Iterator var18 = $receiver$iv$iv.iterator();

      ConfigPropertyBase property;
      String name;
      String category;
      Object var10001;
      Object var10002;
      while(var18.hasNext()) {
         Object element$iv$iv = var18.next();
         Pair property$iv = (Pair)element$iv$iv;
         var10001 = property$iv.getFirst();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
         var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
         var10002 = property$iv.getSecond();
         if (var10002 == null) {
            throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
         }

         property = (ConfigPropertyBase)var10002;
         name = (String)var10001;
         category = "global";
         if (elements.contains(property) && property.getHasChanged()) {
            return true;
         }
      }

      var18 = Reflection.getReflectNestedObjects(this).iterator();

      while(var18.hasNext()) {
         Pair category$iv = (Pair)var18.next();
         Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(category$iv.getSecond(), ConfigPropertyBase.class);
         Iterator var21 = $receiver$iv$iv.iterator();

         while(var21.hasNext()) {
            Object element$iv$iv = var21.next();
            Pair property$iv = (Pair)element$iv$iv;
            Object var24 = category$iv.getFirst();
            var10001 = property$iv.getFirst();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
            var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
            var10002 = property$iv.getSecond();
            if (var10002 == null) {
               throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
            }

            property = (ConfigPropertyBase)var10002;
            name = (String)var10001;
            category = (String)var24;
            if (elements.contains(property) && property.getHasChanged()) {
               return true;
            }
         }
      }

      return false;
   }

   public abstract void onChange(@NotNull PostConfigChangedEvent var1);

   @SubscribeEvent
   public final void handleConfigChange(@NotNull PostConfigChangedEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (Intrinsics.areEqual(event.getModID(), this.modId)) {
         Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(this, ConfigPropertyBase.class);
         Iterator var4 = $receiver$iv$iv.iterator();

         ConfigPropertyBase prop;
         String n;
         String c;
         Object var10001;
         Object var10002;
         while(var4.hasNext()) {
            Object element$iv$iv = var4.next();
            Pair property$iv = (Pair)element$iv$iv;
            var10001 = property$iv.getFirst();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
            var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
            var10002 = property$iv.getSecond();
            if (var10002 == null) {
               throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
            }

            prop = (ConfigPropertyBase)var10002;
            n = (String)var10001;
            c = "global";
            prop.read();
         }

         var4 = Reflection.getReflectNestedObjects(this).iterator();

         while(var4.hasNext()) {
            Pair category$iv = (Pair)var4.next();
            Iterable $receiver$iv$iv = (Iterable)Reflection.reflectFieldsOfType(category$iv.getSecond(), ConfigPropertyBase.class);
            Iterator var18 = $receiver$iv$iv.iterator();

            while(var18.hasNext()) {
               Object element$iv$iv = var18.next();
               Pair property$iv = (Pair)element$iv$iv;
               Object var10000 = category$iv.getFirst();
               var10001 = property$iv.getFirst();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "property.first");
               var10001 = StringsKt.split$default((CharSequence)var10001, new String[]{"$"}, false, 0, 6, (Object)null).get(0);
               var10002 = property$iv.getSecond();
               if (var10002 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type mods.octarinecore.common.config.ConfigPropertyBase");
               }

               prop = (ConfigPropertyBase)var10002;
               n = (String)var10001;
               c = (String)var10000;
               prop.read();
            }
         }

         this.onChange(event);
         this.save();
         MethodRef var19 = Refs.INSTANCE.getResetChangedState();
         Configuration var20 = this.config;
         if (this.config == null) {
            Intrinsics.throwNpe();
         }

         var19.invoke(var20);
      }

   }

   @Nullable
   public final ConfigPropertyBase get(@NotNull Object $receiver, @NotNull String name) {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public final String getModId() {
      return this.modId;
   }

   @NotNull
   public final String getLangPrefix() {
      return this.langPrefix;
   }

   public DelegatingConfig(@NotNull String modId, @NotNull String langPrefix) {
      Intrinsics.checkParameterIsNotNull(modId, "modId");
      Intrinsics.checkParameterIsNotNull(langPrefix, "langPrefix");
      super();
      this.modId = modId;
      this.langPrefix = langPrefix;
      MinecraftForge.EVENT_BUS.register(this);
      List var4 = (List)(new ArrayList());
      this.rootGuiElements = var4;
   }
}
