package mods.octarinecore.common.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J(\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dH\u0016J\"\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00020\"2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0086\u0002¢\u0006\u0002\u0010%J\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010&\u001a\u00020\u001dJ\b\u0010'\u001a\u00020\u0019H\u0016J \u0010(\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u001dH&J*\u0010+\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\"2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$2\u0006\u0010,\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010-J\u0011\u0010'\u001a\u00028\u0000*\u00020\fH&¢\u0006\u0002\u0010.J\u0019\u0010/\u001a\u00020\u0019*\u00020\f2\u0006\u0010,\u001a\u00028\u0000H&¢\u0006\u0002\u00100R\u001e\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u00061"},
   d2 = {"Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "T", "Lmods/octarinecore/common/config/ConfigPropertyBase;", "()V", "cached", "getCached", "()Ljava/lang/Object;", "setCached", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "guiProperties", "", "Lnet/minecraftforge/common/config/Property;", "getGuiProperties", "()Ljava/util/List;", "hasChanged", "", "getHasChanged", "()Z", "property", "getProperty", "()Lnet/minecraftforge/common/config/Property;", "setProperty", "(Lnet/minecraftforge/common/config/Property;)V", "attach", "", "target", "Lnet/minecraftforge/common/config/Configuration;", "langPrefix", "", "categoryName", "propertyName", "getValue", "thisRef", "", "delegator", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "lang", "read", "resolve", "category", "name", "setValue", "value", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "(Lnet/minecraftforge/common/config/Property;)Ljava/lang/Object;", "write", "(Lnet/minecraftforge/common/config/Property;Ljava/lang/Object;)V", "BetterFoliage-MC1.12"}
)
public abstract class ConfigPropertyDelegate extends ConfigPropertyBase {
   @Nullable
   private Object cached;
   @Nullable
   private Property property;

   @Nullable
   public final Object getCached() {
      return this.cached;
   }

   public final void setCached(@Nullable Object var1) {
      this.cached = var1;
   }

   @Nullable
   public final Property getProperty() {
      return this.property;
   }

   public final void setProperty(@Nullable Property var1) {
      this.property = var1;
   }

   @NotNull
   public List getGuiProperties() {
      Property var10000 = this.property;
      if (this.property == null) {
         Intrinsics.throwNpe();
      }

      return CollectionsKt.listOf(var10000);
   }

   public boolean getHasChanged() {
      return this.property != null ? this.property.hasChanged() : false;
   }

   @NotNull
   public final ConfigPropertyDelegate lang(@NotNull String lang) {
      Intrinsics.checkParameterIsNotNull(lang, "lang");
      ConfigPropertyDelegate $receiver = (ConfigPropertyDelegate)this;
      $receiver.setLang(lang);
      return (ConfigPropertyDelegate)this;
   }

   public abstract Object read(@NotNull Property var1);

   public abstract void write(@NotNull Property var1, Object var2);

   @NotNull
   public abstract Property resolve(@NotNull Configuration var1, @NotNull String var2, @NotNull String var3);

   public final Object getValue(@NotNull Object thisRef, @NotNull KProperty delegator) {
      Intrinsics.checkParameterIsNotNull(thisRef, "thisRef");
      Intrinsics.checkParameterIsNotNull(delegator, "delegator");
      Object var10000;
      if (this.cached != null) {
         var10000 = this.cached;
         if (this.cached == null) {
            Intrinsics.throwNpe();
         }

         return var10000;
      } else {
         Property var10002 = this.property;
         if (this.property == null) {
            Intrinsics.throwNpe();
         }

         this.cached = this.read(var10002);
         var10000 = this.cached;
         if (this.cached == null) {
            Intrinsics.throwNpe();
         }

         return var10000;
      }
   }

   public final void setValue(@NotNull Object thisRef, @NotNull KProperty delegator, Object value) {
      Intrinsics.checkParameterIsNotNull(thisRef, "thisRef");
      Intrinsics.checkParameterIsNotNull(delegator, "delegator");
      this.cached = value;
      Property var10001 = this.property;
      if (this.property == null) {
         Intrinsics.throwNpe();
      }

      this.write(var10001, value);
   }

   public void read() {
      this.cached = null;
   }

   public void attach(@NotNull Configuration target, @NotNull String langPrefix, @NotNull String categoryName, @NotNull String propertyName) {
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(langPrefix, "langPrefix");
      Intrinsics.checkParameterIsNotNull(categoryName, "categoryName");
      Intrinsics.checkParameterIsNotNull(propertyName, "propertyName");
      this.cached = null;
      this.property = this.resolve(target, categoryName, propertyName);
      Property var10000 = this.property;
      if (this.property == null) {
         Intrinsics.throwNpe();
      }

      var10000.setLanguageKey("" + langPrefix + '.' + this.getLang());
   }
}
