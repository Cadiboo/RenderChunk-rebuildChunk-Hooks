package mods.betterfoliage.client.integration;

import com.google.common.collect.ImmutableSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.texture.ILeafRegistry;
import mods.betterfoliage.client.texture.LeafGenerator;
import mods.betterfoliage.client.texture.LeafInfo;
import mods.betterfoliage.client.texture.LeafRegistry;
import mods.betterfoliage.client.texture.TextureMatcher;
import mods.octarinecore.Utils;
import mods.octarinecore.metaprog.ClassRef;
import mods.octarinecore.metaprog.MethodRef;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002J3\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0016\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cR\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001d"},
   d2 = {"Lmods/betterfoliage/client/integration/ForestryLeavesSupport;", "Lmods/betterfoliage/client/texture/ILeafRegistry;", "()V", "textureToValue", "", "Lnet/minecraft/util/ResourceLocation;", "Lmods/betterfoliage/client/texture/LeafInfo;", "getTextureToValue", "()Ljava/util/Map;", "get", "", "state", "Lnet/minecraft/block/state/IBlockState;", "rand", "", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "face", "Lnet/minecraft/util/EnumFacing;", "handlePreStitch", "", "event", "Lnet/minecraftforge/client/event/TextureStitchEvent$Pre;", "registerLeaf", "textureLocation", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public final class ForestryLeavesSupport implements ILeafRegistry {
   @NotNull
   private static final Map textureToValue;
   public static final ForestryLeavesSupport INSTANCE;

   @NotNull
   public final Map getTextureToValue() {
      return textureToValue;
   }

   @SubscribeEvent
   public final void handlePreStitch(@NotNull Pre event) {
      Intrinsics.checkParameterIsNotNull(event, "event");
      textureToValue.clear();
      Object var10000 = ForestryIntegration.INSTANCE.getTeLleafTextures().getStatic();
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<*, *>");
      } else {
         Map allLeaves = (Map)var10000;
         Iterable $receiver$iv = (Iterable)allLeaves.entrySet();
         Iterator var4 = $receiver$iv.iterator();

         while(var4.hasNext()) {
            Object element$iv = var4.next();
            Entry it = (Entry)element$iv;
            Client.INSTANCE.logDetail("ForestryLeavesSupport: base leaf type " + String.valueOf(it.getKey()));
            ResourceLocation[] var15 = new ResourceLocation[4];
            Object var10003 = ForestryIntegration.INSTANCE.getTeLplain().get(it.getValue());
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.util.ResourceLocation");
            }

            var15[0] = (ResourceLocation)var10003;
            var10003 = ForestryIntegration.INSTANCE.getTeLfancy().get(it.getValue());
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.util.ResourceLocation");
            }

            var15[1] = (ResourceLocation)var10003;
            var10003 = ForestryIntegration.INSTANCE.getTeLpollplain().get(it.getValue());
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.util.ResourceLocation");
            }

            var15[2] = (ResourceLocation)var10003;
            var10003 = ForestryIntegration.INSTANCE.getTeLpollfancy().get(it.getValue());
            if (var10003 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.util.ResourceLocation");
            }

            var15[3] = (ResourceLocation)var10003;
            Iterable $receiver$iv = (Iterable)CollectionsKt.listOf(var15);
            Iterator var8 = $receiver$iv.iterator();

            while(var8.hasNext()) {
               Object element$iv = var8.next();
               ResourceLocation leafLocation = (ResourceLocation)element$iv;
               ForestryLeavesSupport var16 = INSTANCE;
               TextureMap var10002 = event.getMap();
               Intrinsics.checkExpressionValueIsNotNull(var10002, "event.map");
               var16.registerLeaf(leafLocation, var10002);
            }
         }

      }
   }

   public final void registerLeaf(@NotNull ResourceLocation textureLocation, @NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(textureLocation, "textureLocation");
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      TextureAtlasSprite texture = atlas.func_174942_a(textureLocation);
      TextureMatcher var10000 = LeafRegistry.INSTANCE.getTypeMappings();
      Intrinsics.checkExpressionValueIsNotNull(texture, "texture");
      String var8 = var10000.getType(texture);
      if (var8 == null) {
         var8 = "default";
      }

      String leafType = var8;
      Client.INSTANCE.logDetail("ForestryLeavesSupport:        texture " + texture.func_94215_i());
      Client.INSTANCE.logDetail("ForestryLeavesSupport:        particle " + leafType);
      LeafGenerator var10001 = Client.INSTANCE.getGenLeaves();
      String var10002 = texture.func_94215_i();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "texture.iconName");
      TextureAtlasSprite generated = atlas.func_174942_a(var10001.generatedResource(var10002, new Pair[]{TuplesKt.to("type", leafType)}));
      Map var6 = textureToValue;
      Intrinsics.checkExpressionValueIsNotNull(generated, "generated");
      LeafInfo var7 = new LeafInfo(generated, LeafRegistry.INSTANCE.getParticleType(texture, atlas), 0, 4, (DefaultConstructorMarker)null);
      var6.put(textureLocation, var7);
   }

   @Nullable
   public Void get(@NotNull IBlockState state, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      return null;
   }

   @Nullable
   public LeafInfo get(@NotNull IBlockState state, @NotNull final IBlockAccess world, @NotNull final BlockPos pos, @NotNull EnumFacing face, int rand) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(face, "face");
      ImmutableSet var10000 = (ImmutableSet)state.func_177228_b().entrySet();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "state.properties.entries");
      Iterable var6 = (Iterable)var10000;
      Iterator var8 = var6.iterator();

      Object spriteProvider;
      ClassRef var18;
      Object var20;
      while(true) {
         if (!var8.hasNext()) {
            var20 = null;
            break;
         }

         boolean var19;
         label47: {
            spriteProvider = var8.next();
            Entry it = (Entry)spriteProvider;
            var18 = ForestryIntegration.INSTANCE.getPropertyTreeType();
            Object var10001 = it.getKey();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "it.key");
            if (var18.isInstance(var10001)) {
               var18 = ForestryIntegration.INSTANCE.getTreeDefinition();
               var10001 = it.getValue();
               Intrinsics.checkExpressionValueIsNotNull(var10001, "it.value");
               if (var18.isInstance(var10001)) {
                  var19 = true;
                  break label47;
               }
            }

            var19 = false;
         }

         if (var19) {
            var20 = spriteProvider;
            break;
         }
      }

      Entry var21 = (Entry)var20;
      if (var21 != null) {
         Entry var14 = var21;
         Object species = ForestryIntegration.INSTANCE.getTdSpecies().get(var14.getValue());
         MethodRef var23 = ForestryIntegration.INSTANCE.getGetLeafSpriteProvider();
         if (species == null) {
            Intrinsics.throwNpe();
         }

         spriteProvider = var23.invoke(species);
         var23 = ForestryIntegration.INSTANCE.getGetSprite();
         if (spriteProvider == null) {
            Intrinsics.throwNpe();
         }

         Object textureLoc = var23.invoke(spriteProvider, false, Minecraft.func_71375_t());
         Map var11 = textureToValue;
         return (LeafInfo)var11.get(textureLoc);
      } else {
         TileEntity var22 = (TileEntity)Utils.tryDefault((Object)null, (Function0)(new Function0() {
            @Nullable
            public final TileEntity invoke() {
               return world.func_175625_s(pos);
            }
         }));
         if (var22 != null) {
            TileEntity tile = var22;
            var18 = ForestryIntegration.INSTANCE.getTileLeaves();
            Intrinsics.checkExpressionValueIsNotNull(tile, "tile");
            if (!var18.isInstance(tile)) {
               return null;
            } else {
               var20 = ForestryIntegration.INSTANCE.getTiLgetLeaveSprite().invoke(tile, Minecraft.func_71375_t());
               if (var20 != null) {
                  Object textureLoc = var20;
                  Map var15 = textureToValue;
                  return (LeafInfo)var15.get(textureLoc);
               } else {
                  return null;
               }
            }
         } else {
            return null;
         }
      }
   }

   static {
      ForestryLeavesSupport var0 = new ForestryLeavesSupport();
      INSTANCE = var0;
      textureToValue = (Map)(new LinkedHashMap());
      MinecraftForge.EVENT_BUS.register(var0);
   }
}
