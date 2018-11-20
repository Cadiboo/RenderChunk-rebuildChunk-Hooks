package mods.betterfoliage.client.integration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.ThreadLocalDelegate;
import mods.octarinecore.Utils;
import mods.octarinecore.client.render.BlockContext;
import mods.octarinecore.metaprog.Resolvable;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u0018J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u001e\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!2\u0006\u0010$\u001a\u00020%J&\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u00182\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010$\u001a\u00020%R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\u000e\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u000f\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u001b\u0010\u0010\u001a\u00020\u00118FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013¨\u0006*"},
   d2 = {"Lmods/betterfoliage/client/integration/OptifineCTM;", "", "()V", "connectedProperties", "", "getConnectedProperties", "()Ljava/lang/Iterable;", "fakeQuad", "Lnet/minecraft/client/renderer/block/model/BakedQuad;", "getFakeQuad", "()Lnet/minecraft/client/renderer/block/model/BakedQuad;", "isCTMAvailable", "", "()Z", "isColorAvailable", "isCustomColors", "renderEnv", "Lmods/betterfoliage/client/integration/OptifineRenderEnv;", "getRenderEnv", "()Lmods/betterfoliage/client/integration/OptifineRenderEnv;", "renderEnv$delegate", "Lmods/octarinecore/ThreadLocalDelegate;", "getAllCTM", "", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "states", "", "Lnet/minecraft/block/state/IBlockState;", "icon", "state", "getBlockColor", "", "ctx", "Lmods/octarinecore/client/render/BlockContext;", "override", "texture", "face", "Lnet/minecraft/util/EnumFacing;", "world", "Lnet/minecraft/world/IBlockAccess;", "pos", "Lnet/minecraft/util/math/BlockPos;", "BetterFoliage-MC1.12"}
)
public final class OptifineCTM {
   // $FF: synthetic field
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(OptifineCTM.class), "renderEnv", "getRenderEnv()Lmods/betterfoliage/client/integration/OptifineRenderEnv;"))};
   private static final boolean isCTMAvailable;
   private static final boolean isColorAvailable;
   @NotNull
   private static final ThreadLocalDelegate renderEnv$delegate;
   @NotNull
   private static final BakedQuad fakeQuad;
   public static final OptifineCTM INSTANCE;

   static {
      OptifineCTM var0 = new OptifineCTM();
      INSTANCE = var0;
      isCTMAvailable = mods.octarinecore.metaprog.Reflection.allAvailable((Resolvable)Refs.INSTANCE.getConnectedTextures(), (Resolvable)Refs.INSTANCE.getConnectedProperties(), (Resolvable)Refs.INSTANCE.getGetConnectedTexture(), (Resolvable)Refs.INSTANCE.getCTblockProperties(), (Resolvable)Refs.INSTANCE.getCTtileProperties(), (Resolvable)Refs.INSTANCE.getCPtileIcons(), (Resolvable)Refs.INSTANCE.getCPmatchesIcon());
      isColorAvailable = mods.octarinecore.metaprog.Reflection.allAvailable((Resolvable)Refs.INSTANCE.getCustomColors(), (Resolvable)Refs.INSTANCE.getGetColorMultiplier());
      Client var10000 = Client.INSTANCE;
      Level var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Optifine CTM support is " + (isCTMAvailable ? "enabled" : "disabled"));
      var10000 = Client.INSTANCE;
      var10001 = Level.INFO;
      Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
      var10000.log(var10001, "Optifine custom color support is " + (isColorAvailable ? "enabled" : "disabled"));
      renderEnv$delegate = new ThreadLocalDelegate((Function0)null.INSTANCE);
      fakeQuad = new BakedQuad(new int[0], 1, EnumFacing.UP, (TextureAtlasSprite)null, true, DefaultVertexFormats.field_176600_a);
   }

   public final boolean isCTMAvailable() {
      return isCTMAvailable;
   }

   public final boolean isColorAvailable() {
      return isColorAvailable;
   }

   @NotNull
   public final OptifineRenderEnv getRenderEnv() {
      return (OptifineRenderEnv)renderEnv$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final BakedQuad getFakeQuad() {
      return fakeQuad;
   }

   public final boolean isCustomColors() {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public final Iterable getConnectedProperties() {
      HashSet result = new HashSet();
      Object[][] var10000 = (Object[][])Refs.INSTANCE.getCTblockProperties().getStatic();
      Object[] $receiver$iv;
      int var3;
      int var4;
      Object element$iv;
      Object[] cpArray;
      Object[] $receiver$iv;
      int var8;
      int var9;
      Object element$iv;
      if (var10000 != null) {
         $receiver$iv = (Object[])var10000;
         var3 = $receiver$iv.length;

         for(var4 = 0; var4 < var3; ++var4) {
            element$iv = $receiver$iv[var4];
            cpArray = (Object[])element$iv;
            if (cpArray != null) {
               $receiver$iv = cpArray;
               var8 = cpArray.length;

               for(var9 = 0; var9 < var8; ++var9) {
                  element$iv = $receiver$iv[var9];
                  if (element$iv != null) {
                     result.add(element$iv);
                  }
               }
            }
         }
      }

      var10000 = (Object[][])Refs.INSTANCE.getCTtileProperties().getStatic();
      if (var10000 != null) {
         $receiver$iv = (Object[])var10000;
         var3 = $receiver$iv.length;

         for(var4 = 0; var4 < var3; ++var4) {
            element$iv = $receiver$iv[var4];
            cpArray = (Object[])element$iv;
            if (cpArray != null) {
               $receiver$iv = cpArray;
               var8 = cpArray.length;

               for(var9 = 0; var9 < var8; ++var9) {
                  element$iv = $receiver$iv[var9];
                  if (element$iv != null) {
                     result.add(element$iv);
                  }
               }
            }
         }
      }

      return (Iterable)result;
   }

   @NotNull
   public final Collection getAllCTM(@NotNull IBlockState state, @NotNull TextureAtlasSprite icon) {
      Intrinsics.checkParameterIsNotNull(state, "state");
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      HashSet result = new HashSet();
      if (state instanceof BlockStateBase && isCTMAvailable) {
         Iterable $receiver$iv = this.getConnectedProperties();
         Iterator var5 = $receiver$iv.iterator();

         while(var5.hasNext()) {
            Object element$iv = var5.next();
            Object var10000 = Refs.INSTANCE.getCPmatchesBlock().invoke(element$iv, Refs.INSTANCE.getGetBlockId().invoke(state), Refs.INSTANCE.getGetMetadata().invoke(state));
            if (var10000 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
            }

            if (((Boolean)var10000).booleanValue()) {
               var10000 = Refs.INSTANCE.getCPmatchesIcon().invoke(element$iv, icon);
               if (var10000 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
               }

               if (((Boolean)var10000).booleanValue()) {
                  Client var10 = Client.INSTANCE;
                  Level var10001 = Level.INFO;
                  Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "INFO");
                  var10.log(var10001, "Match for block: " + state.toString() + ", icon: " + icon.func_94215_i() + " -> CP: " + element$iv.toString());
                  Collection var11 = (Collection)result;
                  Object var12 = Refs.INSTANCE.getCPtileIcons().get(element$iv);
                  if (var12 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<net.minecraft.client.renderer.texture.TextureAtlasSprite>");
                  }

                  CollectionsKt.addAll(var11, (Object[])((TextureAtlasSprite[])var12));
               }
            }
         }

         return (Collection)result;
      } else {
         return (Collection)result;
      }
   }

   @NotNull
   public final Collection getAllCTM(@NotNull List states, @NotNull TextureAtlasSprite icon) {
      Intrinsics.checkParameterIsNotNull(states, "states");
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      Iterable $receiver$iv = (Iterable)states;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      Iterator var6 = $receiver$iv.iterator();

      while(var6.hasNext()) {
         Object element$iv$iv = var6.next();
         IBlockState it = (IBlockState)element$iv$iv;
         Iterable list$iv$iv = (Iterable)INSTANCE.getAllCTM(it, icon);
         CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
      }

      return (Collection)CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
   }

   @NotNull
   public final TextureAtlasSprite override(@NotNull TextureAtlasSprite texture, @NotNull BlockContext ctx, @NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(ctx, "ctx");
      Intrinsics.checkParameterIsNotNull(face, "face");
      IBlockAccess var10002 = ctx.getWorld();
      if (var10002 == null) {
         Intrinsics.throwNpe();
      }

      BlockPos var10003 = ctx.getPos();
      Intrinsics.checkExpressionValueIsNotNull(var10003, "ctx.pos");
      return this.override(texture, var10002, var10003, face);
   }

   @NotNull
   public final TextureAtlasSprite override(@NotNull TextureAtlasSprite texture, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing face) {
      Intrinsics.checkParameterIsNotNull(texture, "texture");
      Intrinsics.checkParameterIsNotNull(world, "world");
      Intrinsics.checkParameterIsNotNull(pos, "pos");
      Intrinsics.checkParameterIsNotNull(face, "face");
      if (!isCTMAvailable) {
         return texture;
      } else {
         IBlockState state = world.func_180495_p(pos);
         OptifineRenderEnv var6 = this.getRenderEnv();
         Intrinsics.checkExpressionValueIsNotNull(state, "state");
         var6.reset(world, state, pos);
         Object var10000 = Refs.INSTANCE.getGetConnectedTexture().invokeStatic(world, state, pos, face, texture, var6.getWrapped());
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.client.renderer.texture.TextureAtlasSprite");
         } else {
            return (TextureAtlasSprite)var10000;
         }
      }
   }

   public final int getBlockColor(@NotNull BlockContext ctx) {
      // $FF: Couldn't be decompiled
   }
}
