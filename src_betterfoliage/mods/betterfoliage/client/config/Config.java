package mods.betterfoliage.client.config;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import mods.octarinecore.common.config.ConfigPropertyBase;
import mods.octarinecore.common.config.ConfigPropertyBoolean;
import mods.octarinecore.common.config.ConfigPropertyDelegate;
import mods.octarinecore.common.config.ConfigPropertyDouble;
import mods.octarinecore.common.config.ConfigPropertyFloat;
import mods.octarinecore.common.config.ConfigPropertyInt;
import mods.octarinecore.common.config.ConfigPropertyIntList;
import mods.octarinecore.common.config.ConfigurableBlockMatcher;
import mods.octarinecore.common.config.DelegatingConfig;
import mods.octarinecore.common.config.DelegatingConfigKt;
import mods.octarinecore.common.config.ModelTextureListConfigOption;
import mods.octarinecore.common.config.ObsoleteConfigProperty;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.PostConfigChangedEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\bÇ\u0002\u0018\u00002\u00020\u0001:\r\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R+\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0012\u0010\u0007\"\u0004\b\u0013\u0010\t¨\u0006&"},
   d2 = {"Lmods/betterfoliage/client/config/Config;", "Lmods/octarinecore/common/config/DelegatingConfig;", "()V", "<set-?>", "", "enabled", "getEnabled", "()Z", "setEnabled", "(Z)V", "enabled$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "forceReloadOptions", "", "Lmods/octarinecore/common/config/ConfigPropertyBase;", "getForceReloadOptions", "()Ljava/util/List;", "nVidia", "getNVidia", "setNVidia", "nVidia$delegate", "onChange", "", "event", "Lnet/minecraftforge/fml/client/event/ConfigChangedEvent$PostConfigChangedEvent;", "algae", "blocks", "cactus", "connectedGrass", "coral", "fallingLeaves", "leaves", "lilypad", "netherrack", "reed", "risingSoul", "roundLogs", "shortGrass", "BetterFoliage-MC1.12"}
)
public final class Config extends DelegatingConfig {
   // $FF: synthetic field
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.class), "nVidia", "getNVidia()Z"))};
   @NotNull
   private static final ConfigPropertyBoolean enabled$delegate;
   @NotNull
   private static final ConfigPropertyBoolean nVidia$delegate;
   @NotNull
   private static final List forceReloadOptions;
   public static final Config INSTANCE;

   static {
      Config var0 = new Config();
      INSTANCE = var0;
      enabled$delegate = DelegatingConfigKt.boolean(true);
      String var10000 = GL11.glGetString(7936);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "GL11.glGetString(GL11.GL_VENDOR)");
      String var1 = var10000;
      if (var1 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var10000 = var1.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         nVidia$delegate = DelegatingConfigKt.boolean(StringsKt.contains$default((CharSequence)var10000, (CharSequence)"nvidia", false, 2, (Object)null));
         forceReloadOptions = CollectionsKt.listOf(new ConfigPropertyBase[]{(ConfigPropertyBase)Config.blocks.INSTANCE.getLeavesClasses(), (ConfigPropertyBase)Config.blocks.INSTANCE.getLeavesModels(), (ConfigPropertyBase)Config.blocks.INSTANCE.getGrassClasses(), (ConfigPropertyBase)Config.blocks.INSTANCE.getGrassModels(), var0.get(Config.shortGrass.INSTANCE, "saturationThreshold")});
      }
   }

   public final boolean getEnabled() {
      return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
   }

   public final void setEnabled(boolean var1) {
      enabled$delegate.setValue(this, $$delegatedProperties[0], var1);
   }

   public final boolean getNVidia() {
      return ((Boolean)nVidia$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
   }

   public final void setNVidia(boolean var1) {
      nVidia$delegate.setValue(this, $$delegatedProperties[1], var1);
   }

   @NotNull
   public final List getForceReloadOptions() {
      return forceReloadOptions;
   }

   public void onChange(@NotNull PostConfigChangedEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (this.hasChanged(forceReloadOptions)) {
         Minecraft.func_71410_x().func_110436_a();
      } else {
         Minecraft.func_71410_x().field_71438_f.func_72712_a();
      }

   }

   private Config() {
      super("betterfoliage", "betterfoliage");
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001d\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u0011\u0010\u0017\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000eR\u0011\u0010\u0019\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0006R\u0011\u0010\u001b\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0014R\u0011\u0010\u001d\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000eR\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0014R\u0011\u0010%\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u000eR\u0011\u0010'\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u000eR\u0011\u0010)\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0006R\u0011\u0010+\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0006R\u0011\u0010-\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0006¨\u0006/"},
      d2 = {"Lmods/betterfoliage/client/config/Config$blocks;", "", "()V", "cactus", "Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "getCactus", "()Lmods/octarinecore/common/config/ConfigurableBlockMatcher;", "crops", "getCrops", "dirt", "getDirt", "grassBlacklist", "Lmods/octarinecore/common/config/ObsoleteConfigProperty;", "getGrassBlacklist", "()Lmods/octarinecore/common/config/ObsoleteConfigProperty;", "grassClasses", "getGrassClasses", "grassModels", "Lmods/octarinecore/common/config/ModelTextureListConfigOption;", "getGrassModels", "()Lmods/octarinecore/common/config/ModelTextureListConfigOption;", "grassWhitelist", "getGrassWhitelist", "leavesBlacklist", "getLeavesBlacklist", "leavesClasses", "getLeavesClasses", "leavesModels", "getLeavesModels", "leavesWhitelist", "getLeavesWhitelist", "lilypad", "getLilypad", "logClasses", "getLogClasses", "logModels", "getLogModels", "logsBlacklist", "getLogsBlacklist", "logsWhitelist", "getLogsWhitelist", "mycelium", "getMycelium", "netherrack", "getNetherrack", "sand", "getSand", "BetterFoliage-MC1.12"}
   )
   public static final class blocks {
      @NotNull
      private static final ConfigurableBlockMatcher leavesClasses;
      @NotNull
      private static final ModelTextureListConfigOption leavesModels;
      @NotNull
      private static final ConfigurableBlockMatcher grassClasses;
      @NotNull
      private static final ModelTextureListConfigOption grassModels;
      @NotNull
      private static final ConfigurableBlockMatcher mycelium;
      @NotNull
      private static final ConfigurableBlockMatcher dirt;
      @NotNull
      private static final ConfigurableBlockMatcher crops;
      @NotNull
      private static final ConfigurableBlockMatcher logClasses;
      @NotNull
      private static final ModelTextureListConfigOption logModels;
      @NotNull
      private static final ConfigurableBlockMatcher sand;
      @NotNull
      private static final ConfigurableBlockMatcher lilypad;
      @NotNull
      private static final ConfigurableBlockMatcher cactus;
      @NotNull
      private static final ConfigurableBlockMatcher netherrack;
      @NotNull
      private static final ObsoleteConfigProperty leavesWhitelist;
      @NotNull
      private static final ObsoleteConfigProperty leavesBlacklist;
      @NotNull
      private static final ObsoleteConfigProperty grassWhitelist;
      @NotNull
      private static final ObsoleteConfigProperty grassBlacklist;
      @NotNull
      private static final ObsoleteConfigProperty logsWhitelist;
      @NotNull
      private static final ObsoleteConfigProperty logsBlacklist;
      public static final Config.blocks INSTANCE;

      @NotNull
      public final ConfigurableBlockMatcher getLeavesClasses() {
         return leavesClasses;
      }

      @NotNull
      public final ModelTextureListConfigOption getLeavesModels() {
         return leavesModels;
      }

      @NotNull
      public final ConfigurableBlockMatcher getGrassClasses() {
         return grassClasses;
      }

      @NotNull
      public final ModelTextureListConfigOption getGrassModels() {
         return grassModels;
      }

      @NotNull
      public final ConfigurableBlockMatcher getMycelium() {
         return mycelium;
      }

      @NotNull
      public final ConfigurableBlockMatcher getDirt() {
         return dirt;
      }

      @NotNull
      public final ConfigurableBlockMatcher getCrops() {
         return crops;
      }

      @NotNull
      public final ConfigurableBlockMatcher getLogClasses() {
         return logClasses;
      }

      @NotNull
      public final ModelTextureListConfigOption getLogModels() {
         return logModels;
      }

      @NotNull
      public final ConfigurableBlockMatcher getSand() {
         return sand;
      }

      @NotNull
      public final ConfigurableBlockMatcher getLilypad() {
         return lilypad;
      }

      @NotNull
      public final ConfigurableBlockMatcher getCactus() {
         return cactus;
      }

      @NotNull
      public final ConfigurableBlockMatcher getNetherrack() {
         return netherrack;
      }

      @NotNull
      public final ObsoleteConfigProperty getLeavesWhitelist() {
         return leavesWhitelist;
      }

      @NotNull
      public final ObsoleteConfigProperty getLeavesBlacklist() {
         return leavesBlacklist;
      }

      @NotNull
      public final ObsoleteConfigProperty getGrassWhitelist() {
         return grassWhitelist;
      }

      @NotNull
      public final ObsoleteConfigProperty getGrassBlacklist() {
         return grassBlacklist;
      }

      @NotNull
      public final ObsoleteConfigProperty getLogsWhitelist() {
         return logsWhitelist;
      }

      @NotNull
      public final ObsoleteConfigProperty getLogsBlacklist() {
         return logsBlacklist;
      }

      static {
         Config.blocks var0 = new Config.blocks();
         INSTANCE = var0;
         leavesClasses = new ConfigurableBlockMatcher("betterfoliage", "leaves_blocks_default.cfg");
         leavesModels = new ModelTextureListConfigOption("betterfoliage", "leaves_models_default.cfg", 1);
         grassClasses = new ConfigurableBlockMatcher("betterfoliage", "grass_blocks_default.cfg");
         grassModels = new ModelTextureListConfigOption("betterfoliage", "grass_models_default.cfg", 1);
         mycelium = new ConfigurableBlockMatcher("betterfoliage", "mycelium_blocks_default.cfg");
         dirt = new ConfigurableBlockMatcher("betterfoliage", "dirt_default.cfg");
         crops = new ConfigurableBlockMatcher("betterfoliage", "crop_default.cfg");
         logClasses = new ConfigurableBlockMatcher("betterfoliage", "log_blocks_default.cfg");
         logModels = new ModelTextureListConfigOption("betterfoliage", "log_models_default.cfg", 3);
         sand = new ConfigurableBlockMatcher("betterfoliage", "sand_default.cfg");
         lilypad = new ConfigurableBlockMatcher("betterfoliage", "lilypad_default.cfg");
         cactus = new ConfigurableBlockMatcher("betterfoliage", "cactus_default.cfg");
         netherrack = new ConfigurableBlockMatcher("betterfoliage", "netherrack_blocks_default.cfg");
         leavesWhitelist = ConfigKt.access$getOBSOLETE$p();
         leavesBlacklist = ConfigKt.access$getOBSOLETE$p();
         grassWhitelist = ConfigKt.access$getOBSOLETE$p();
         grassBlacklist = ConfigKt.access$getOBSOLETE$p();
         logsWhitelist = ConfigKt.access$getOBSOLETE$p();
         logsBlacklist = ConfigKt.access$getOBSOLETE$p();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000e\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0017\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000e\u001a\u0004\b\u0018\u0010\u0015R\u001b\u0010\u001a\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\b\u001a\u0004\b\u001b\u0010\u0006R\u001b\u0010\u001d\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u000e\u001a\u0004\b\u001e\u0010\u0015¨\u0006 "},
      d2 = {"Lmods/betterfoliage/client/config/Config$leaves;", "", "()V", "dense", "", "getDense", "()Z", "dense$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "getEnabled", "enabled$delegate", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "size", "getSize", "size$delegate", "snowEnabled", "getSnowEnabled", "snowEnabled$delegate", "vOffset", "getVOffset", "vOffset$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class leaves {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "snowEnabled", "getSnowEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "vOffset", "getVOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.leaves.class), "dense", "getDense()Z"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyBoolean snowEnabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate vOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyBoolean dense$delegate;
      public static final Config.leaves INSTANCE;

      static {
         Config.leaves var0 = new Config.leaves();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         snowEnabled$delegate = DelegatingConfigKt.boolean(true);
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.2D, 1, (Object)null).lang("hOffset");
         vOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.1D, 1, (Object)null).lang("vOffset");
         size$delegate = DelegatingConfigKt.double(0.75D, 2.5D, 1.4D).lang("size");
         dense$delegate = DelegatingConfigKt.boolean(false);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final boolean getSnowEnabled() {
         return ((Boolean)snowEnabled$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[2])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getVOffset() {
         return ((Number)vOffset$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }

      public final boolean getDense() {
         return ((Boolean)dense$delegate.getValue(this, $$delegatedProperties[6])).booleanValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b \bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\b\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0015\u0010\u0012R\u001b\u0010\u0017\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\b\u001a\u0004\b\u0018\u0010\u0012R\u001b\u0010\u001a\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000e\u001a\u0004\b\u001b\u0010\fR\u001b\u0010\u001d\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\b\u001a\u0004\b\u001e\u0010\u0006R\u001b\u0010 \u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b!\u0010\u0012R\u001b\u0010$\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\b\u001a\u0004\b%\u0010\fR\u001b\u0010'\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b)\u0010\b\u001a\u0004\b(\u0010\u0012R\u001b\u0010*\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b,\u0010\u000e\u001a\u0004\b+\u0010\fR\u001b\u0010-\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b/\u0010\u000e\u001a\u0004\b.\u0010\f¨\u00060"},
      d2 = {"Lmods/betterfoliage/client/config/Config$shortGrass;", "", "()V", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "grassEnabled", "", "getGrassEnabled", "()Z", "grassEnabled$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "heightMax", "getHeightMax", "heightMax$delegate", "heightMin", "getHeightMin", "heightMin$delegate", "myceliumEnabled", "getMyceliumEnabled", "myceliumEnabled$delegate", "population", "getPopulation", "population$delegate", "saturationThreshold", "getSaturationThreshold", "saturationThreshold$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "shaderWind", "getShaderWind", "shaderWind$delegate", "size", "getSize", "size$delegate", "snowEnabled", "getSnowEnabled", "snowEnabled$delegate", "useGenerated", "getUseGenerated", "useGenerated$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class shortGrass {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "grassEnabled", "getGrassEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "myceliumEnabled", "getMyceliumEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "snowEnabled", "getSnowEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "heightMin", "getHeightMin()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "heightMax", "getHeightMax()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "population", "getPopulation()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "useGenerated", "getUseGenerated()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "shaderWind", "getShaderWind()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.shortGrass.class), "saturationThreshold", "getSaturationThreshold()D"))};
      @NotNull
      private static final ConfigPropertyBoolean grassEnabled$delegate;
      @NotNull
      private static final ConfigPropertyBoolean myceliumEnabled$delegate;
      @NotNull
      private static final ConfigPropertyBoolean snowEnabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMin$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMax$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyDelegate population$delegate;
      @NotNull
      private static final ConfigPropertyBoolean useGenerated$delegate;
      @NotNull
      private static final ConfigPropertyDelegate shaderWind$delegate;
      @NotNull
      private static final ConfigPropertyDouble saturationThreshold$delegate;
      public static final Config.shortGrass INSTANCE;

      static {
         Config.shortGrass var0 = new Config.shortGrass();
         INSTANCE = var0;
         grassEnabled$delegate = DelegatingConfigKt.boolean(true);
         myceliumEnabled$delegate = DelegatingConfigKt.boolean(true);
         snowEnabled$delegate = DelegatingConfigKt.boolean(true);
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.2D, 1, (Object)null).lang("hOffset");
         heightMin$delegate = DelegatingConfigKt.double(0.1D, 2.5D, 0.6D).lang("heightMin");
         heightMax$delegate = DelegatingConfigKt.double(0.1D, 2.5D, 0.8D).lang("heightMax");
         size$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 1.0D).lang("size");
         population$delegate = DelegatingConfigKt.int$default(0, 64, 64, 1, (Object)null).lang("population");
         useGenerated$delegate = DelegatingConfigKt.boolean(false);
         shaderWind$delegate = DelegatingConfigKt.boolean(true).lang("shaderWind");
         saturationThreshold$delegate = DelegatingConfigKt.double$default(0.0D, 0.0D, 0.1D, 3, (Object)null);
      }

      public final boolean getGrassEnabled() {
         return ((Boolean)grassEnabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final boolean getMyceliumEnabled() {
         return ((Boolean)myceliumEnabled$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
      }

      public final boolean getSnowEnabled() {
         return ((Boolean)snowEnabled$delegate.getValue(this, $$delegatedProperties[2])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[3])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getHeightMin() {
         return ((Number)heightMin$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }

      public final double getHeightMax() {
         return ((Number)heightMax$delegate.getValue(this, $$delegatedProperties[6])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[7])).doubleValue();
      }

      public final int getPopulation() {
         return ((Number)population$delegate.getValue(this, $$delegatedProperties[8])).intValue();
      }

      public final boolean getUseGenerated() {
         return ((Boolean)useGenerated$delegate.getValue(this, $$delegatedProperties[9])).booleanValue();
      }

      public final boolean getShaderWind() {
         return ((Boolean)shaderWind$delegate.getValue(this, $$delegatedProperties[10])).booleanValue();
      }

      public final double getSaturationThreshold() {
         return ((Number)saturationThreshold$delegate.getValue(this, $$delegatedProperties[11])).doubleValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006¨\u0006\f"},
      d2 = {"Lmods/betterfoliage/client/config/Config$connectedGrass;", "", "()V", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "snowEnabled", "getSnowEnabled", "snowEnabled$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class connectedGrass {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.connectedGrass.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.connectedGrass.class), "snowEnabled", "getSnowEnabled()Z"))};
      @NotNull
      private static final ConfigPropertyBoolean enabled$delegate;
      @NotNull
      private static final ConfigPropertyBoolean snowEnabled$delegate;
      public static final Config.connectedGrass INSTANCE;

      static {
         Config.connectedGrass var0 = new Config.connectedGrass();
         INSTANCE = var0;
         enabled$delegate = DelegatingConfigKt.boolean(true);
         snowEnabled$delegate = DelegatingConfigKt.boolean(false);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final boolean getSnowEnabled() {
         return ((Boolean)snowEnabled$delegate.getValue(this, $$delegatedProperties[1])).booleanValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0006\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\r\u0010\u0006R\u001b\u0010\u000f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001e\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u001d\u001a\u0004\b\u001f\u0010\u0006R\u001b\u0010!\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b\"\u0010\u0006R\u001b\u0010$\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b&\u0010'R\u001b\u0010*\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b,\u0010)\u001a\u0004\b+\u0010'R\u001b\u0010-\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b/\u0010)\u001a\u0004\b.\u0010'¨\u00060"},
      d2 = {"Lmods/betterfoliage/client/config/Config$roundLogs;", "", "()V", "connectGrass", "", "getConnectGrass", "()Z", "connectGrass$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "connectPerpendicular", "getConnectPerpendicular", "connectPerpendicular$delegate", "connectSolids", "getConnectSolids", "connectSolids$delegate", "defaultY", "getDefaultY", "defaultY$delegate", "dimming", "", "getDimming", "()F", "dimming$delegate", "Lmods/octarinecore/common/config/ConfigPropertyFloat;", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "getEnabled", "enabled$delegate", "lenientConnect", "getLenientConnect", "lenientConnect$delegate", "radiusLarge", "", "getRadiusLarge", "()D", "radiusLarge$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "radiusSmall", "getRadiusSmall", "radiusSmall$delegate", "zProtection", "getZProtection", "zProtection$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class roundLogs {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "radiusSmall", "getRadiusSmall()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "radiusLarge", "getRadiusLarge()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "dimming", "getDimming()F")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "connectSolids", "getConnectSolids()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "lenientConnect", "getLenientConnect()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "connectPerpendicular", "getConnectPerpendicular()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "connectGrass", "getConnectGrass()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "defaultY", "getDefaultY()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.roundLogs.class), "zProtection", "getZProtection()D"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDouble radiusSmall$delegate;
      @NotNull
      private static final ConfigPropertyDouble radiusLarge$delegate;
      @NotNull
      private static final ConfigPropertyFloat dimming$delegate;
      @NotNull
      private static final ConfigPropertyBoolean connectSolids$delegate;
      @NotNull
      private static final ConfigPropertyBoolean lenientConnect$delegate;
      @NotNull
      private static final ConfigPropertyBoolean connectPerpendicular$delegate;
      @NotNull
      private static final ConfigPropertyBoolean connectGrass$delegate;
      @NotNull
      private static final ConfigPropertyBoolean defaultY$delegate;
      @NotNull
      private static final ConfigPropertyDouble zProtection$delegate;
      public static final Config.roundLogs INSTANCE;

      static {
         Config.roundLogs var0 = new Config.roundLogs();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         radiusSmall$delegate = DelegatingConfigKt.double$default(0.0D, 0.5D, 0.25D, 1, (Object)null);
         radiusLarge$delegate = DelegatingConfigKt.double$default(0.0D, 0.5D, 0.44D, 1, (Object)null);
         dimming$delegate = DelegatingConfigKt.float$default(0.0D, 0.0D, 0.7D, 3, (Object)null);
         connectSolids$delegate = DelegatingConfigKt.boolean(false);
         lenientConnect$delegate = DelegatingConfigKt.boolean(true);
         connectPerpendicular$delegate = DelegatingConfigKt.boolean(true);
         connectGrass$delegate = DelegatingConfigKt.boolean(true);
         defaultY$delegate = DelegatingConfigKt.boolean(false);
         zProtection$delegate = DelegatingConfigKt.double$default(0.9D, 0.0D, 0.99D, 2, (Object)null);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getRadiusSmall() {
         return ((Number)radiusSmall$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getRadiusLarge() {
         return ((Number)radiusLarge$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final float getDimming() {
         return ((Number)dimming$delegate.getValue(this, $$delegatedProperties[4])).floatValue();
      }

      public final boolean getConnectSolids() {
         return ((Boolean)connectSolids$delegate.getValue(this, $$delegatedProperties[5])).booleanValue();
      }

      public final boolean getLenientConnect() {
         return ((Boolean)lenientConnect$delegate.getValue(this, $$delegatedProperties[6])).booleanValue();
      }

      public final boolean getConnectPerpendicular() {
         return ((Boolean)connectPerpendicular$delegate.getValue(this, $$delegatedProperties[7])).booleanValue();
      }

      public final boolean getConnectGrass() {
         return ((Boolean)connectGrass$delegate.getValue(this, $$delegatedProperties[8])).booleanValue();
      }

      public final boolean getDefaultY() {
         return ((Boolean)defaultY$delegate.getValue(this, $$delegatedProperties[9])).booleanValue();
      }

      public final double getZProtection() {
         return ((Number)zProtection$delegate.getValue(this, $$delegatedProperties[10])).doubleValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\u0011R\u001b\u0010\u0016\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0017\u0010\u0011¨\u0006\u001a"},
      d2 = {"Lmods/betterfoliage/client/config/Config$cactus;", "", "()V", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "size", "getSize", "size$delegate", "sizeVariation", "getSizeVariation", "sizeVariation$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "BetterFoliage-MC1.12"}
   )
   public static final class cactus {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.cactus.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.cactus.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.cactus.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.cactus.class), "sizeVariation", "getSizeVariation()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.cactus.class), "hOffset", "getHOffset()D"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyDouble sizeVariation$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      public static final Config.cactus INSTANCE;

      static {
         Config.cactus var0 = new Config.cactus();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         size$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 0.8D).lang("size");
         sizeVariation$delegate = DelegatingConfigKt.double$default(0.0D, 0.5D, 0.1D, 1, (Object)null);
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.5D, 0.1D, 1, (Object)null).lang("hOffset");
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getSizeVariation() {
         return ((Number)sizeVariation$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000f\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\b\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0017"},
      d2 = {"Lmods/betterfoliage/client/config/Config$lilypad;", "", "()V", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "flowerChance", "getFlowerChance", "flowerChance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyInt;", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class lilypad {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.lilypad.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.lilypad.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.lilypad.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.lilypad.class), "flowerChance", "getFlowerChance()I"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyInt flowerChance$delegate;
      public static final Config.lilypad INSTANCE;

      static {
         Config.lilypad var0 = new Config.lilypad();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.25D, 0.1D, 1, (Object)null).lang("hOffset");
         byte var1 = 0;
         byte var2 = 16;
         byte var3 = 64;
         flowerChance$delegate = DelegatingConfigKt.int(var1, var3, var2);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final int getFlowerChance() {
         return ((Number)flowerChance$delegate.getValue(this, $$delegatedProperties[3])).intValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000e\u001a\u0004\b\u001a\u0010\u0017R\u001b\u0010\u001c\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u000e\u001a\u0004\b\u001d\u0010\u0017R\u001b\u0010\u001f\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000e\u001a\u0004\b \u0010\fR\u001b\u0010\"\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u000e\u001a\u0004\b#\u0010\u0012¨\u0006%"},
      d2 = {"Lmods/betterfoliage/client/config/Config$reed;", "", "()V", "biomes", "", "", "getBiomes", "()[Ljava/lang/Integer;", "biomes$delegate", "Lmods/octarinecore/common/config/ConfigPropertyIntList;", "distance", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "heightMax", "getHeightMax", "heightMax$delegate", "heightMin", "getHeightMin", "heightMin$delegate", "population", "getPopulation", "population$delegate", "shaderWind", "getShaderWind", "shaderWind$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class reed {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "heightMin", "getHeightMin()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "heightMax", "getHeightMax()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "population", "getPopulation()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "biomes", "getBiomes()[Ljava/lang/Integer;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.reed.class), "shaderWind", "getShaderWind()Z"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMin$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMax$delegate;
      @NotNull
      private static final ConfigPropertyDelegate population$delegate;
      @NotNull
      private static final ConfigPropertyIntList biomes$delegate;
      @NotNull
      private static final ConfigPropertyDelegate shaderWind$delegate;
      public static final Config.reed INSTANCE;

      static {
         Config.reed var0 = new Config.reed();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.2D, 1, (Object)null).lang("hOffset");
         heightMin$delegate = DelegatingConfigKt.double(1.5D, 3.5D, 1.7D).lang("heightMin");
         heightMax$delegate = DelegatingConfigKt.double(1.5D, 3.5D, 2.2D).lang("heightMax");
         population$delegate = DelegatingConfigKt.int$default(0, 64, 32, 1, (Object)null).lang("population");
         biomes$delegate = ConfigKt.biomeList((Function1)null.INSTANCE);
         shaderWind$delegate = DelegatingConfigKt.boolean(true).lang("shaderWind");
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getHeightMin() {
         return ((Number)heightMin$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getHeightMax() {
         return ((Number)heightMax$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final int getPopulation() {
         return ((Number)population$delegate.getValue(this, $$delegatedProperties[5])).intValue();
      }

      @NotNull
      public final Integer[] getBiomes() {
         return (Integer[])biomes$delegate.getValue(this, $$delegatedProperties[6]);
      }

      public final boolean getShaderWind() {
         return ((Boolean)shaderWind$delegate.getValue(this, $$delegatedProperties[7])).booleanValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000e\u001a\u0004\b\u001a\u0010\u0017R\u001b\u0010\u001c\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u000e\u001a\u0004\b\u001d\u0010\u0017R\u001b\u0010\u001f\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000e\u001a\u0004\b \u0010\fR\u001b\u0010\"\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u000e\u001a\u0004\b#\u0010\u0012R\u001b\u0010%\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b'\u0010\u000e\u001a\u0004\b&\u0010\u0017¨\u0006("},
      d2 = {"Lmods/betterfoliage/client/config/Config$algae;", "", "()V", "biomes", "", "", "getBiomes", "()[Ljava/lang/Integer;", "biomes$delegate", "Lmods/octarinecore/common/config/ConfigPropertyIntList;", "distance", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "heightMax", "getHeightMax", "heightMax$delegate", "heightMin", "getHeightMin", "heightMin$delegate", "population", "getPopulation", "population$delegate", "shaderWind", "getShaderWind", "shaderWind$delegate", "size", "getSize", "size$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class algae {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "heightMin", "getHeightMin()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "heightMax", "getHeightMax()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "population", "getPopulation()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "biomes", "getBiomes()[Ljava/lang/Integer;")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.algae.class), "shaderWind", "getShaderWind()Z"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMin$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMax$delegate;
      @NotNull
      private static final ConfigPropertyDelegate population$delegate;
      @NotNull
      private static final ConfigPropertyIntList biomes$delegate;
      @NotNull
      private static final ConfigPropertyDelegate shaderWind$delegate;
      public static final Config.algae INSTANCE;

      static {
         Config.algae var0 = new Config.algae();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.25D, 0.1D, 1, (Object)null).lang("hOffset");
         size$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 1.0D).lang("size");
         heightMin$delegate = DelegatingConfigKt.double(0.1D, 1.5D, 0.5D).lang("heightMin");
         heightMax$delegate = DelegatingConfigKt.double(0.1D, 1.5D, 1.0D).lang("heightMax");
         population$delegate = DelegatingConfigKt.int$default(0, 64, 48, 1, (Object)null).lang("population");
         biomes$delegate = ConfigKt.biomeList((Function1)null.INSTANCE);
         shaderWind$delegate = DelegatingConfigKt.boolean(true).lang("shaderWind");
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getHeightMin() {
         return ((Number)heightMin$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getHeightMax() {
         return ((Number)heightMax$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }

      public final int getPopulation() {
         return ((Number)population$delegate.getValue(this, $$delegatedProperties[6])).intValue();
      }

      @NotNull
      public final Integer[] getBiomes() {
         return (Integer[])biomes$delegate.getValue(this, $$delegatedProperties[7]);
      }

      public final boolean getShaderWind() {
         return ((Boolean)shaderWind$delegate.getValue(this, $$delegatedProperties[8])).booleanValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0014\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\n\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0015\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0016\u0010\fR\u001b\u0010\u0019\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u0018\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001e\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0018\u001a\u0004\b\u001f\u0010\u0012R\u001b\u0010!\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0018\u001a\u0004\b\"\u0010\fR\u001b\u0010$\u001a\u00020\u001a8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b%\u0010\u001cR\u001b\u0010(\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0018\u001a\u0004\b)\u0010\u0012R\u001b\u0010+\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u0018\u001a\u0004\b,\u0010\u0012¨\u0006."},
      d2 = {"Lmods/betterfoliage/client/config/Config$coral;", "", "()V", "biomes", "", "", "getBiomes", "()[Ljava/lang/Integer;", "biomes$delegate", "Lmods/octarinecore/common/config/ConfigPropertyIntList;", "chance", "getChance", "()I", "chance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyInt;", "crustSize", "", "getCrustSize", "()D", "crustSize$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "distance", "getDistance", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "hOffset", "getHOffset", "hOffset$delegate", "population", "getPopulation", "population$delegate", "shallowWater", "getShallowWater", "shallowWater$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "size", "getSize", "size$delegate", "vOffset", "getVOffset", "vOffset$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class coral {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "shallowWater", "getShallowWater()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "vOffset", "getVOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "crustSize", "getCrustSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "chance", "getChance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "population", "getPopulation()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.coral.class), "biomes", "getBiomes()[Ljava/lang/Integer;"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyBoolean shallowWater$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate vOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyDouble crustSize$delegate;
      @NotNull
      private static final ConfigPropertyInt chance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate population$delegate;
      @NotNull
      private static final ConfigPropertyIntList biomes$delegate;
      public static final Config.coral INSTANCE;

      static {
         Config.coral var0 = new Config.coral();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         shallowWater$delegate = DelegatingConfigKt.boolean(false);
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.2D, 1, (Object)null).lang("hOffset");
         vOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.1D, 1, (Object)null).lang("vOffset");
         size$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 0.7D).lang("size");
         crustSize$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 1.4D);
         chance$delegate = DelegatingConfigKt.int$default(0, 64, 32, 1, (Object)null);
         population$delegate = DelegatingConfigKt.int$default(0, 64, 48, 1, (Object)null).lang("population");
         biomes$delegate = ConfigKt.biomeList((Function1)null.INSTANCE);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final boolean getShallowWater() {
         return ((Boolean)shallowWater$delegate.getValue(this, $$delegatedProperties[2])).booleanValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getVOffset() {
         return ((Number)vOffset$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }

      public final double getCrustSize() {
         return ((Number)crustSize$delegate.getValue(this, $$delegatedProperties[6])).doubleValue();
      }

      public final int getChance() {
         return ((Number)chance$delegate.getValue(this, $$delegatedProperties[7])).intValue();
      }

      public final int getPopulation() {
         return ((Number)population$delegate.getValue(this, $$delegatedProperties[8])).intValue();
      }

      @NotNull
      public final Integer[] getBiomes() {
         return (Integer[])biomes$delegate.getValue(this, $$delegatedProperties[9]);
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\b\u001a\u0004\b\u0014\u0010\u0011R\u001b\u0010\u0016\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0017\u0010\u0011R\u001b\u0010\u0019\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\b\u001a\u0004\b\u001a\u0010\u0011¨\u0006\u001c"},
      d2 = {"Lmods/betterfoliage/client/config/Config$netherrack;", "", "()V", "distance", "", "getDistance", "()I", "distance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "hOffset", "", "getHOffset", "()D", "hOffset$delegate", "heightMax", "getHeightMax", "heightMax$delegate", "heightMin", "getHeightMin", "heightMin$delegate", "size", "getSize", "size$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class netherrack {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "distance", "getDistance()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "hOffset", "getHOffset()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "heightMin", "getHeightMin()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "heightMax", "getHeightMax()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.netherrack.class), "size", "getSize()D"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDelegate distance$delegate;
      @NotNull
      private static final ConfigPropertyDelegate hOffset$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMin$delegate;
      @NotNull
      private static final ConfigPropertyDelegate heightMax$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      public static final Config.netherrack INSTANCE;

      static {
         Config.netherrack var0 = new Config.netherrack();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         distance$delegate = ConfigKt.access$distanceLimit();
         hOffset$delegate = DelegatingConfigKt.double$default(0.0D, 0.4D, 0.2D, 1, (Object)null).lang("hOffset");
         heightMin$delegate = DelegatingConfigKt.double(0.1D, 1.5D, 0.6D).lang("heightMin");
         heightMax$delegate = DelegatingConfigKt.double(0.1D, 1.5D, 0.8D).lang("heightMax");
         size$delegate = DelegatingConfigKt.double(0.5D, 1.5D, 1.0D).lang("size");
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final int getDistance() {
         return ((Number)distance$delegate.getValue(this, $$delegatedProperties[1])).intValue();
      }

      public final double getHOffset() {
         return ((Number)hOffset$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getHeightMin() {
         return ((Number)heightMin$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getHeightMax() {
         return ((Number)heightMax$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u001b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0013\u0010\fR\u001b\u0010\u0016\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\b\u001a\u0004\b\u0017\u0010\u0006R\u001b\u0010\u0019\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u000e\u001a\u0004\b\u001a\u0010\u0006R\u001b\u0010\u001c\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\b\u001a\u0004\b\u001d\u0010\u0006R\u001b\u0010\u001f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b \u0010\u0006R\u001b\u0010\"\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\b\u001a\u0004\b#\u0010\u0006¨\u0006%"},
      d2 = {"Lmods/betterfoliage/client/config/Config$fallingLeaves;", "", "()V", "chance", "", "getChance", "()D", "chance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "lifetime", "getLifetime", "lifetime$delegate", "opacityHack", "getOpacityHack", "opacityHack$delegate", "Lmods/octarinecore/common/config/ConfigPropertyBoolean;", "perturb", "getPerturb", "perturb$delegate", "size", "getSize", "size$delegate", "speed", "getSpeed", "speed$delegate", "stormStrength", "getStormStrength", "stormStrength$delegate", "windStrength", "getWindStrength", "windStrength$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class fallingLeaves {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "speed", "getSpeed()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "windStrength", "getWindStrength()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "stormStrength", "getStormStrength()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "size", "getSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "chance", "getChance()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "perturb", "getPerturb()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "lifetime", "getLifetime()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.fallingLeaves.class), "opacityHack", "getOpacityHack()Z"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDouble speed$delegate;
      @NotNull
      private static final ConfigPropertyDouble windStrength$delegate;
      @NotNull
      private static final ConfigPropertyDouble stormStrength$delegate;
      @NotNull
      private static final ConfigPropertyDelegate size$delegate;
      @NotNull
      private static final ConfigPropertyDouble chance$delegate;
      @NotNull
      private static final ConfigPropertyDouble perturb$delegate;
      @NotNull
      private static final ConfigPropertyDouble lifetime$delegate;
      @NotNull
      private static final ConfigPropertyBoolean opacityHack$delegate;
      public static final Config.fallingLeaves INSTANCE;

      static {
         Config.fallingLeaves var0 = new Config.fallingLeaves();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         speed$delegate = DelegatingConfigKt.double(0.01D, 0.15D, 0.05D);
         windStrength$delegate = DelegatingConfigKt.double(0.1D, 2.0D, 0.5D);
         stormStrength$delegate = DelegatingConfigKt.double(0.1D, 2.0D, 0.8D);
         size$delegate = DelegatingConfigKt.double(0.25D, 1.5D, 0.75D).lang("size");
         chance$delegate = DelegatingConfigKt.double(0.001D, 1.0D, 0.05D);
         perturb$delegate = DelegatingConfigKt.double(0.01D, 1.0D, 0.25D);
         lifetime$delegate = DelegatingConfigKt.double(1.0D, 15.0D, 5.0D);
         opacityHack$delegate = DelegatingConfigKt.boolean(true);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final double getSpeed() {
         return ((Number)speed$delegate.getValue(this, $$delegatedProperties[1])).doubleValue();
      }

      public final double getWindStrength() {
         return ((Number)windStrength$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getStormStrength() {
         return ((Number)stormStrength$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getSize() {
         return ((Number)size$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final double getChance() {
         return ((Number)chance$delegate.getValue(this, $$delegatedProperties[5])).doubleValue();
      }

      public final double getPerturb() {
         return ((Number)perturb$delegate.getValue(this, $$delegatedProperties[6])).doubleValue();
      }

      public final double getLifetime() {
         return ((Number)lifetime$delegate.getValue(this, $$delegatedProperties[7])).doubleValue();
      }

      public final boolean getOpacityHack() {
         return ((Boolean)opacityHack$delegate.getValue(this, $$delegatedProperties[8])).booleanValue();
      }
   }

   @Metadata(
      mv = {1, 1, 9},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006R\u001b\u0010\u0012\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\b\u001a\u0004\b\u0013\u0010\u0006R\u001b\u0010\u0015\u001a\u00020\u00168FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u001b\u001a\u00020\u00168FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001c\u0010\u0018R\u001b\u0010\u001e\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b \u0010\b\u001a\u0004\b\u001f\u0010\u0006R\u001b\u0010!\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\b\u001a\u0004\b\"\u0010\u0006R\u001b\u0010$\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b&\u0010'R\u001b\u0010*\u001a\u00020%8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b,\u0010)\u001a\u0004\b+\u0010'R\u001b\u0010-\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b/\u0010\b\u001a\u0004\b.\u0010\u0006¨\u00060"},
      d2 = {"Lmods/betterfoliage/client/config/Config$risingSoul;", "", "()V", "chance", "", "getChance", "()D", "chance$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDouble;", "enabled", "", "getEnabled", "()Z", "enabled$delegate", "Lmods/octarinecore/common/config/ConfigPropertyDelegate;", "headSize", "getHeadSize", "headSize$delegate", "lifetime", "getLifetime", "lifetime$delegate", "opacity", "", "getOpacity", "()F", "opacity$delegate", "Lmods/octarinecore/common/config/ConfigPropertyFloat;", "opacityDecay", "getOpacityDecay", "opacityDecay$delegate", "perturb", "getPerturb", "perturb$delegate", "sizeDecay", "getSizeDecay", "sizeDecay$delegate", "trailDensity", "", "getTrailDensity", "()I", "trailDensity$delegate", "Lmods/octarinecore/common/config/ConfigPropertyInt;", "trailLength", "getTrailLength", "trailLength$delegate", "trailSize", "getTrailSize", "trailSize$delegate", "BetterFoliage-MC1.12"}
   )
   public static final class risingSoul {
      // $FF: synthetic field
      static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "enabled", "getEnabled()Z")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "chance", "getChance()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "perturb", "getPerturb()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "headSize", "getHeadSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "trailSize", "getTrailSize()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "opacity", "getOpacity()F")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "sizeDecay", "getSizeDecay()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "opacityDecay", "getOpacityDecay()F")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "lifetime", "getLifetime()D")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "trailLength", "getTrailLength()I")), (KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Config.risingSoul.class), "trailDensity", "getTrailDensity()I"))};
      @NotNull
      private static final ConfigPropertyDelegate enabled$delegate;
      @NotNull
      private static final ConfigPropertyDouble chance$delegate;
      @NotNull
      private static final ConfigPropertyDouble perturb$delegate;
      @NotNull
      private static final ConfigPropertyDouble headSize$delegate;
      @NotNull
      private static final ConfigPropertyDouble trailSize$delegate;
      @NotNull
      private static final ConfigPropertyFloat opacity$delegate;
      @NotNull
      private static final ConfigPropertyDouble sizeDecay$delegate;
      @NotNull
      private static final ConfigPropertyFloat opacityDecay$delegate;
      @NotNull
      private static final ConfigPropertyDouble lifetime$delegate;
      @NotNull
      private static final ConfigPropertyInt trailLength$delegate;
      @NotNull
      private static final ConfigPropertyInt trailDensity$delegate;
      public static final Config.risingSoul INSTANCE;

      static {
         Config.risingSoul var0 = new Config.risingSoul();
         INSTANCE = var0;
         enabled$delegate = ConfigKt.access$featureEnable();
         chance$delegate = DelegatingConfigKt.double(0.001D, 1.0D, 0.02D);
         perturb$delegate = DelegatingConfigKt.double(0.01D, 0.25D, 0.05D);
         headSize$delegate = DelegatingConfigKt.double(0.25D, 1.5D, 1.0D);
         trailSize$delegate = DelegatingConfigKt.double(0.25D, 1.5D, 0.75D);
         opacity$delegate = DelegatingConfigKt.float(0.05D, 1.0D, 0.5D);
         sizeDecay$delegate = DelegatingConfigKt.double(0.5D, 1.0D, 0.97D);
         opacityDecay$delegate = DelegatingConfigKt.float(0.5D, 1.0D, 0.97D);
         lifetime$delegate = DelegatingConfigKt.double(1.0D, 15.0D, 4.0D);
         trailLength$delegate = DelegatingConfigKt.int(2, 128, 48);
         trailDensity$delegate = DelegatingConfigKt.int(1, 16, 3);
      }

      public final boolean getEnabled() {
         return ((Boolean)enabled$delegate.getValue(this, $$delegatedProperties[0])).booleanValue();
      }

      public final double getChance() {
         return ((Number)chance$delegate.getValue(this, $$delegatedProperties[1])).doubleValue();
      }

      public final double getPerturb() {
         return ((Number)perturb$delegate.getValue(this, $$delegatedProperties[2])).doubleValue();
      }

      public final double getHeadSize() {
         return ((Number)headSize$delegate.getValue(this, $$delegatedProperties[3])).doubleValue();
      }

      public final double getTrailSize() {
         return ((Number)trailSize$delegate.getValue(this, $$delegatedProperties[4])).doubleValue();
      }

      public final float getOpacity() {
         return ((Number)opacity$delegate.getValue(this, $$delegatedProperties[5])).floatValue();
      }

      public final double getSizeDecay() {
         return ((Number)sizeDecay$delegate.getValue(this, $$delegatedProperties[6])).doubleValue();
      }

      public final float getOpacityDecay() {
         return ((Number)opacityDecay$delegate.getValue(this, $$delegatedProperties[7])).floatValue();
      }

      public final double getLifetime() {
         return ((Number)lifetime$delegate.getValue(this, $$delegatedProperties[8])).doubleValue();
      }

      public final int getTrailLength() {
         return ((Number)trailLength$delegate.getValue(this, $$delegatedProperties[9])).intValue();
      }

      public final int getTrailDensity() {
         return ((Number)trailDensity$delegate.getValue(this, $$delegatedProperties[10])).intValue();
      }
   }
}
