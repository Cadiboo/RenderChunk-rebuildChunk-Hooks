package mods.octarinecore.common.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mods.octarinecore.client.gui.NonVerboseArrayEntry;
import mods.octarinecore.client.resource.Utils;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J(\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0004H\u0016J\u0017\u0010\"\u001a\u0004\u0018\u00018\u00002\u0006\u0010#\u001a\u00020\u0004H&¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u001cH\u0016J!\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00040'2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010(R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u001a\u0010\u0015\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\b¨\u0006)"},
   d2 = {"Lmods/octarinecore/common/config/StringListConfigOption;", "VALUE", "Lmods/octarinecore/common/config/ConfigPropertyBase;", "domain", "", "path", "(Ljava/lang/String;Ljava/lang/String;)V", "getDomain", "()Ljava/lang/String;", "guiProperties", "", "Lnet/minecraftforge/common/config/Property;", "getGuiProperties", "()Ljava/util/List;", "hasChanged", "", "getHasChanged", "()Z", "list", "", "getList", "listProperty", "getListProperty", "()Lnet/minecraftforge/common/config/Property;", "setListProperty", "(Lnet/minecraftforge/common/config/Property;)V", "getPath", "attach", "", "target", "Lnet/minecraftforge/common/config/Configuration;", "langPrefix", "categoryName", "propertyName", "convertValue", "line", "(Ljava/lang/String;)Ljava/lang/Object;", "read", "readDefaults", "", "(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;", "BetterFoliage-MC1.12"}
)
public abstract class StringListConfigOption extends ConfigPropertyBase {
   @NotNull
   private final List list;
   @NotNull
   public Property listProperty;
   @NotNull
   private final String domain;
   @NotNull
   private final String path;

   @NotNull
   public final List getList() {
      return this.list;
   }

   @NotNull
   public final Property getListProperty() {
      Property var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      return var10000;
   }

   public final void setListProperty(@NotNull Property var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.listProperty = var1;
   }

   public boolean getHasChanged() {
      Property var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      return var10000.hasChanged();
   }

   @NotNull
   public List getGuiProperties() {
      Property var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      return CollectionsKt.listOf(var10000);
   }

   public void attach(@NotNull Configuration target, @NotNull String langPrefix, @NotNull String categoryName, @NotNull String propertyName) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(langPrefix, "langPrefix");
      Intrinsics.checkParameterIsNotNull(categoryName, "categoryName");
      Intrinsics.checkParameterIsNotNull(propertyName, "propertyName");
      this.setLang((String)null);
      String[] defaults = this.readDefaults(this.domain, this.path);
      Property var10001 = target.get(categoryName, "" + propertyName, defaults);
      Intrinsics.checkExpressionValueIsNotNull(var10001, "target.get(categoryName,…propertyName}\", defaults)");
      this.listProperty = var10001;
      Property var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      var10000.setConfigEntryClass(NonVerboseArrayEntry.class);
      var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      StringBuilder var6 = (new StringBuilder()).append("").append(langPrefix).append('.').append(categoryName).append('.');
      Property var10002 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      var10000.setLanguageKey(var6.append(var10002.getName()).toString());
      this.read();
   }

   @Nullable
   public abstract Object convertValue(@NotNull String var1);

   public void read() {
      this.list.clear();
      Property var10000 = this.listProperty;
      if (this.listProperty == null) {
         Intrinsics.throwUninitializedPropertyAccessException("listProperty");
      }

      String[] var9 = var10000.getStringList();
      Intrinsics.checkExpressionValueIsNotNull(var9, "listProperty.stringList");
      Object[] $receiver$iv = (Object[])var9;
      int var2 = $receiver$iv.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object element$iv = $receiver$iv[var3];
         String line = (String)element$iv;
         Intrinsics.checkExpressionValueIsNotNull(line, "line");
         Object value = this.convertValue(line);
         if (value != null) {
            this.list.add(value);
         }
      }

   }

   @NotNull
   public final String[] readDefaults(@NotNull String domain, @NotNull String path) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      ArrayList list = new ArrayList();
      IResource var10000 = Utils.get((IResourceManager)Utils.getResourceManager(), domain, path);
      List defaults = var10000 != null ? Utils.getLines(var10000) : null;
      if (defaults != null) {
         Iterable $receiver$iv = (Iterable)defaults;
         Collection destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)));
         Iterator var8 = $receiver$iv.iterator();

         Object element$iv$iv;
         String it;
         while(var8.hasNext()) {
            element$iv$iv = var8.next();
            it = (String)element$iv$iv;
            if (it == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }

            String var16 = StringsKt.trim((CharSequence)it).toString();
            destination$iv$iv.add(var16);
         }

         $receiver$iv = (Iterable)((List)destination$iv$iv);
         destination$iv$iv = (Collection)(new ArrayList());
         var8 = $receiver$iv.iterator();

         while(var8.hasNext()) {
            boolean var20;
            label50: {
               element$iv$iv = var8.next();
               it = (String)element$iv$iv;
               if (!StringsKt.startsWith$default(it, "//", false, 2, (Object)null)) {
                  CharSequence var11 = (CharSequence)it;
                  if (var11.length() > 0) {
                     var20 = true;
                     break label50;
                  }
               }

               var20 = false;
            }

            if (var20) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         $receiver$iv = (Iterable)((List)destination$iv$iv);
         Iterator var6 = $receiver$iv.iterator();

         while(var6.hasNext()) {
            Object element$iv = var6.next();
            String it = (String)element$iv;
            list.add(it);
         }
      }

      Collection $receiver$iv = (Collection)list;
      Object[] var21 = $receiver$iv.toArray(new String[0]);
      if (var21 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      } else {
         return (String[])var21;
      }
   }

   @NotNull
   public final String getDomain() {
      return this.domain;
   }

   @NotNull
   public final String getPath() {
      return this.path;
   }

   public StringListConfigOption(@NotNull String domain, @NotNull String path) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      super();
      this.domain = domain;
      this.path = path;
      List var4 = (List)(new ArrayList());
      this.list = var4;
   }
}
