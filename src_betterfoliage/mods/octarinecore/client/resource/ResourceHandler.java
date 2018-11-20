package mods.octarinecore.client.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u001f\u0010\u001b\u001a\u00020\u001c2\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\f0\u001e¢\u0006\u0002\b J-\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u001d\u0010\u001d\u001a\u0019\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\f0%¢\u0006\u0002\b J\u0010\u0010&\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020'H\u0007J\u0006\u0010(\u001a\u00020)J\"\u0010*\u001a\u00020+2\u0006\u0010#\u001a\u00020$2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020,0\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006-"},
   d2 = {"Lmods/octarinecore/client/resource/ResourceHandler;", "", "modId", "", "(Ljava/lang/String;)V", "getModId", "()Ljava/lang/String;", "resources", "", "getResources", "()Ljava/util/List;", "afterStitch", "", "handleConfigChange", "event", "Lnet/minecraftforge/fml/client/event/ConfigChangedEvent$OnConfigChangedEvent;", "handleWorldLoad", "Lnet/minecraftforge/event/world/WorldEvent$Load;", "iconSet", "Lmods/octarinecore/client/resource/IconSet;", "domain", "pathPattern", "location", "Lnet/minecraft/util/ResourceLocation;", "iconStatic", "Lmods/octarinecore/client/resource/IconHolder;", "path", "model", "Lmods/octarinecore/client/resource/ModelHolder;", "init", "Lkotlin/Function1;", "Lmods/octarinecore/client/render/Model;", "Lkotlin/ExtensionFunctionType;", "modelSet", "Lmods/octarinecore/client/resource/ModelSet;", "num", "", "Lkotlin/Function2;", "onStitch", "Lnet/minecraftforge/client/event/TextureStitchEvent$Pre;", "simplexNoise", "Lmods/octarinecore/client/resource/SimplexNoise;", "vectorSet", "Lmods/octarinecore/client/resource/VectorSet;", "Lmods/octarinecore/common/Double3;", "BetterFoliage-MC1.12"}
)
public class ResourceHandler {
   @NotNull
   private final List resources;
   @NotNull
   private final String modId;

   @NotNull
   public final List getResources() {
      return this.resources;
   }

   public void afterStitch() {
   }

   @NotNull
   public final IconHolder iconStatic(@NotNull String domain, @NotNull String path) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(path, "path");
      IconHolder var3 = new IconHolder(domain, path);
      this.resources.add(var3);
      return var3;
   }

   @NotNull
   public final IconHolder iconStatic(@NotNull ResourceLocation location) {
      Intrinsics.checkParameterIsNotNull(location, "location");
      String var10001 = location.func_110624_b();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "location.namespace");
      String var10002 = location.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "location.path");
      return this.iconStatic(var10001, var10002);
   }

   @NotNull
   public final IconSet iconSet(@NotNull String domain, @NotNull String pathPattern) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(pathPattern, "pathPattern");
      IconSet var3 = new IconSet(domain, pathPattern);
      this.resources.add(var3);
      return var3;
   }

   @NotNull
   public final IconSet iconSet(@NotNull ResourceLocation location) {
      Intrinsics.checkParameterIsNotNull(location, "location");
      String var10001 = location.func_110624_b();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "location.namespace");
      String var10002 = location.func_110623_a();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "location.path");
      return this.iconSet(var10001, var10002);
   }

   @NotNull
   public final ModelHolder model(@NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      ModelHolder var2 = new ModelHolder(init);
      this.resources.add(var2);
      return var2;
   }

   @NotNull
   public final ModelSet modelSet(int num, @NotNull Function2 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      ModelSet var3 = new ModelSet(num, init);
      this.resources.add(var3);
      return var3;
   }

   @NotNull
   public final VectorSet vectorSet(int num, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      VectorSet var3 = new VectorSet(num, init);
      this.resources.add(var3);
      return var3;
   }

   @NotNull
   public final SimplexNoise simplexNoise() {
      SimplexNoise var1 = new SimplexNoise();
      this.resources.add(var1);
      return var1;
   }

   @SubscribeEvent
   public final void onStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      Iterable $receiver$iv = (Iterable)this.resources;
      Iterator var3 = $receiver$iv.iterator();

      while(var3.hasNext()) {
         Object element$iv = var3.next();
         Object var10000 = element$iv;
         if (!(element$iv instanceof IStitchListener)) {
            var10000 = null;
         }

         IStitchListener var8 = (IStitchListener)var10000;
         if (var8 != null) {
            TextureMap var10001 = event.getMap();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "event.map");
            var8.onStitch(var10001);
         }
      }

      this.afterStitch();
   }

   @SubscribeEvent
   public final void handleConfigChange(@NotNull OnConfigChangedEvent event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      if (Intrinsics.areEqual(event.getModID(), this.modId)) {
         Iterable $receiver$iv = (Iterable)this.resources;
         Iterator var3 = $receiver$iv.iterator();

         while(var3.hasNext()) {
            Object element$iv = var3.next();
            Object var10000 = element$iv;
            if (!(element$iv instanceof IConfigChangeListener)) {
               var10000 = null;
            }

            IConfigChangeListener var8 = (IConfigChangeListener)var10000;
            if (var8 != null) {
               var8.onConfigChange();
            }
         }
      }

   }

   @SubscribeEvent
   public final void handleWorldLoad(@NotNull Load event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      Iterable $receiver$iv = (Iterable)this.resources;
      Iterator var3 = $receiver$iv.iterator();

      while(var3.hasNext()) {
         Object element$iv = var3.next();
         Object var10000 = element$iv;
         if (!(element$iv instanceof IWorldLoadListener)) {
            var10000 = null;
         }

         IWorldLoadListener var8 = (IWorldLoadListener)var10000;
         if (var8 != null) {
            World var10001 = event.getWorld();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "event.world");
            var8.onWorldLoad(var10001);
         }
      }

   }

   @NotNull
   public final String getModId() {
      return this.modId;
   }

   public ResourceHandler(@NotNull String modId) {
      Intrinsics.checkParameterIsNotNull(modId, "modId");
      super();
      this.modId = modId;
      List var3 = (List)(new ArrayList());
      this.resources = var3;
      MinecraftForge.EVENT_BUS.register(this);
   }
}
