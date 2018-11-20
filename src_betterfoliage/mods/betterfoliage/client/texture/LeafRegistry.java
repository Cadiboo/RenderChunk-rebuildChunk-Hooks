package mods.betterfoliage.client.texture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.client.resource.IconSet;
import mods.octarinecore.common.Int3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0002J\u001b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0096\u0002J3\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0096\u0002J\u0016\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0007R-\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006)"},
   d2 = {"Lmods/betterfoliage/client/texture/LeafRegistry;", "Lmods/betterfoliage/client/texture/ILeafRegistry;", "()V", "particles", "Ljava/util/HashMap;", "", "Lmods/octarinecore/client/resource/IconSet;", "Lkotlin/collections/HashMap;", "getParticles", "()Ljava/util/HashMap;", "subRegistries", "", "getSubRegistries", "()Ljava/util/List;", "typeMappings", "Lmods/betterfoliage/client/texture/TextureMatcher;", "getTypeMappings", "()Lmods/betterfoliage/client/texture/TextureMatcher;", "get", "Lmods/betterfoliage/client/texture/LeafInfo;", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "face", "Lnet/minecraft/util/EnumFacing;", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "getParticleType", "texture", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "handlePreStitch", "", "event", "Lnet/minecraftforge/client/event/TextureStitchEvent$Pre;", "BetterFoliage-MC1.12"}
)
public final class LeafRegistry implements ILeafRegistry {
   @NotNull
   private static final List subRegistries;
   @NotNull
   private static final TextureMatcher typeMappings;
   @NotNull
   private static final HashMap particles;
   public static final LeafRegistry INSTANCE;

   @NotNull
   public final List getSubRegistries() {
      return subRegistries;
   }

   @NotNull
   public final TextureMatcher getTypeMappings() {
      return typeMappings;
   }

   @NotNull
   public final HashMap getParticles() {
      return particles;
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public final void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      particles.clear();
      typeMappings.loadMappings(new ResourceLocation("betterfoliage", "leaf_texture_mappings.cfg"));
   }

   @Nullable
   public LeafInfo get(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(face, "face");
      Iterable $receiver$iv = (Iterable)subRegistries;
      Iterator var8 = $receiver$iv.iterator();

      LeafInfo var10000;
      while(true) {
         if (!var8.hasNext()) {
            var10000 = null;
            break;
         }

         Object element$iv$iv = var8.next();
         ILeafRegistry it = (ILeafRegistry)element$iv$iv;
         var10000 = it.get(state, world, pos, face, rand);
         if (var10000 != null) {
            LeafInfo var13 = var10000;
            var10000 = var13;
            break;
         }
      }

      return var10000;
   }

   @Nullable
   public final LeafInfo get(@NotNull BlockContext ctx, @NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(face, "face");
      IBlockState var10001 = ctx.blockState(Int3.Companion.getZero());
      Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.blockState(Int3.zero)");
      IBlockAccess var10002 = ctx.getWorld();
      if (var10002 == null) {
         Intrinsics.throwNpe();
      }

      BlockPos var10003 = ctx.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "ctx.pos");
      return this.get(var10001, var10002, var10003, face, ctx.random(0));
   }

   @Nullable
   public LeafInfo get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Iterable $receiver$iv = (Iterable)subRegistries;
      Iterator var5 = $receiver$iv.iterator();

      LeafInfo var10000;
      while(true) {
         if (!var5.hasNext()) {
            var10000 = null;
            break;
         }

         Object element$iv$iv = var5.next();
         ILeafRegistry it = (ILeafRegistry)element$iv$iv;
         var10000 = it.get(state, rand);
         if (var10000 != null) {
            LeafInfo var10 = var10000;
            var10000 = var10;
            break;
         }
      }

      return var10000;
   }

   @NotNull
   public final String getParticleType(@NotNull TextureAtlasSprite texture, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      String var10000 = typeMappings.getType(texture);
      if (var10000 == null) {
         var10000 = "default";
      }

      String leafType = var10000;
      if (!particles.keySet().contains(leafType)) {
         IconSet particleSet = new IconSet("betterfoliage", "blocks/falling_leaf_" + leafType + "_%d");
         particleSet.onStitch(atlas);
         if (particleSet.getNum() == 0) {
            Client var5 = Client.INSTANCE;
            Level var10001 = Level.WARN;
            Intrinsics.checkExpressionValueIsNotNull(Level.WARN, "Level.WARN");
            var5.log(var10001, "Leaf particle textures not found for leaf type: " + leafType);
            leafType = "default";
         } else {
            particles.put(leafType, particleSet);
         }
      }

      return leafType;
   }

   static {
      LeafRegistry var0 = new LeafRegistry();
      INSTANCE = var0;
      subRegistries = CollectionsKt.mutableListOf(new ILeafRegistry[]{(ILeafRegistry)StandardLeafSupport.INSTANCE});
      typeMappings = new TextureMatcher();
      particles = new HashMap();
      MinecraftForge.EVENT_BUS.register(var0);
   }
}
