package mods.betterfoliage;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.Hooks;
import mods.betterfoliage.client.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod(
   modid = "betterfoliage",
   name = "Better Foliage",
   acceptedMinecraftVersions = "[1.12]",
   guiFactory = "mods.betterfoliage.client.gui.ConfigGuiFactory",
   dependencies = "after:forgelin",
   modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter",
   clientSideOnly = true
)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0019\u001a\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015¨\u0006#"},
   d2 = {"Lmods/betterfoliage/BetterFoliageMod;", "", "()V", "DOMAIN", "", "GUI_FACTORY", "LEGACY_DOMAIN", "MC_VERSIONS", "MOD_ID", "MOD_NAME", "config", "Lnet/minecraftforge/common/config/Configuration;", "getConfig", "()Lnet/minecraftforge/common/config/Configuration;", "setConfig", "(Lnet/minecraftforge/common/config/Configuration;)V", "log", "Lorg/apache/logging/log4j/Logger;", "getLog", "()Lorg/apache/logging/log4j/Logger;", "setLog", "(Lorg/apache/logging/log4j/Logger;)V", "logDetail", "getLogDetail", "setLogDetail", "checkVersion", "", "mods", "", "side", "Lnet/minecraftforge/fml/relauncher/Side;", "preInit", "", "event", "Lnet/minecraftforge/fml/common/event/FMLPreInitializationEvent;", "BetterFoliage-MC1.12"}
)
public final class BetterFoliageMod {
   @NotNull
   public static final String MOD_ID = "betterfoliage";
   @NotNull
   public static final String MOD_NAME = "Better Foliage";
   @NotNull
   public static final String DOMAIN = "betterfoliage";
   @NotNull
   public static final String LEGACY_DOMAIN = "bettergrassandleaves";
   @NotNull
   public static final String MC_VERSIONS = "[1.12]";
   @NotNull
   public static final String GUI_FACTORY = "mods.betterfoliage.client.gui.ConfigGuiFactory";
   @NotNull
   public static Logger log;
   @NotNull
   public static Logger logDetail;
   @Nullable
   private static Configuration config;
   public static final BetterFoliageMod INSTANCE;

   @NotNull
   public final Logger getLog() {
      Logger var10000 = log;
      if (log == null) {
         Intrinsics.throwUninitializedPropertyAccessException("log");
      }

      return var10000;
   }

   public final void setLog(@NotNull Logger var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      log = var1;
   }

   @NotNull
   public final Logger getLogDetail() {
      Logger var10000 = logDetail;
      if (logDetail == null) {
         Intrinsics.throwUninitializedPropertyAccessException("logDetail");
      }

      return var10000;
   }

   public final void setLogDetail(@NotNull Logger var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      logDetail = var1;
   }

   @Nullable
   public final Configuration getConfig() {
      return config;
   }

   public final void setConfig(@Nullable Configuration var1) {
      config = var1;
   }

   @EventHandler
   public final void preInit(@NotNull FMLPreInitializationEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      Logger var10000 = event.getModLog();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "event.modLog");
      log = var10000;
      File var10002 = event.getModConfigurationDirectory();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "event.modConfigurationDirectory");
      File var3 = new File(var10002.getParentFile(), "logs/betterfoliage.log");
      var3.getParentFile().mkdirs();
      if (!var3.exists()) {
         var3.createNewFile();
      }

      logDetail = (Logger)(new SimpleLogger("BetterFoliage", Level.DEBUG, false, false, true, false, "yyyy-MM-dd HH:mm:ss", (MessageFactory)null, new PropertiesUtil(new Properties()), new PrintStream(var3)));
      config = new Configuration(event.getSuggestedConfigurationFile(), (String)null, true);
      Config var6 = Config.INSTANCE;
      Configuration var10001 = config;
      if (config == null) {
         Intrinsics.throwNpe();
      }

      var6.attach(var10001);
      Client.INSTANCE.init();
      Client var7 = Client.INSTANCE;
      Level var8 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var7.log(var8, "BetterFoliage initialized");
      Hooks.setAfterPostInit(true);
   }

   @NetworkCheckHandler
   public final boolean checkVersion(@NotNull Map mods, @NotNull Side side) {
      Intrinsics.checkParameterIsNotNull(mods, "mods");
      Intrinsics.checkParameterIsNotNull(side, "side");
      return true;
   }

   static {
      BetterFoliageMod var0 = new BetterFoliageMod();
      INSTANCE = var0;
      Client.INSTANCE.getGeneratorPack().inject();
   }
}
