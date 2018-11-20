package mods.octarinecore.client.resource;

import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0015\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u0010H\u0086\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0007R\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001b"},
   d2 = {"Lmods/octarinecore/client/resource/IconSet;", "Lmods/octarinecore/client/resource/IStitchListener;", "domain", "", "namePattern", "(Ljava/lang/String;Ljava/lang/String;)V", "getDomain", "()Ljava/lang/String;", "icons", "", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getIcons", "()[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "[Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "getNamePattern", "num", "", "getNum", "()I", "setNum", "(I)V", "get", "idx", "onStitch", "", "atlas", "Lnet/minecraft/client/renderer/texture/TextureMap;", "BetterFoliage-MC1.12"}
)
public final class IconSet implements IStitchListener {
   @NotNull
   private final TextureAtlasSprite[] icons;
   private int num;
   @NotNull
   private final String domain;
   @NotNull
   private final String namePattern;

   @NotNull
   public final TextureAtlasSprite[] getIcons() {
      return this.icons;
   }

   public final int getNum() {
      return this.num;
   }

   public final void setNum(int var1) {
      this.num = var1;
   }

   public void onStitch(@NotNull TextureMap atlas) {
      Intrinsics.checkParameterIsNotNull(atlas, "atlas");
      this.num = 0;
      byte var2 = 0;
      Iterable $receiver$iv = (Iterable)(new IntRange(var2, 15));
      Iterator var3 = $receiver$iv.iterator();

      while(var3.hasNext()) {
         int element$iv = ((IntIterator)var3).nextInt();
         this.icons[element$iv] = (TextureAtlasSprite)null;
         ResourceLocation var10000 = new ResourceLocation;
         String var10002 = this.domain;
         StringBuilder var10003 = (new StringBuilder()).append("textures/");
         String var6 = this.namePattern;
         Object[] var7 = new Object[]{element$iv};
         StringBuilder var8 = var10003;
         String var9 = var10002;
         ResourceLocation var10 = var10000;
         ResourceLocation var11 = var10000;
         String var24 = String.format(var6, Arrays.copyOf(var7, var7.length));
         Intrinsics.checkExpressionValueIsNotNull(var24, "java.lang.String.format(this, *args)");
         String var12 = var24;
         var10.<init>(var9, var8.append(var12).append(".png").toString());
         if (Utils.get((IResourceManager)Utils.getResourceManager(), var11) != null) {
            TextureAtlasSprite[] var25 = this.icons;
            int var19 = this.num++;
            int var10001 = var19;
            ResourceLocation var26 = new ResourceLocation;
            String var10005 = this.domain;
            var6 = this.namePattern;
            var7 = new Object[]{element$iv};
            String var14 = var10005;
            ResourceLocation var23 = var26;
            ResourceLocation var20 = var26;
            int var21 = var10001;
            TextureAtlasSprite[] var22 = var25;
            var24 = String.format(var6, Arrays.copyOf(var7, var7.length));
            Intrinsics.checkExpressionValueIsNotNull(var24, "java.lang.String.format(this, *args)");
            String var15 = var24;
            var23.<init>(var14, var15);
            var22[var21] = atlas.func_174942_a(var20);
         }
      }

   }

   @Nullable
   public final TextureAtlasSprite get(int idx) {
      return this.num == 0 ? null : this.icons[idx % this.num];
   }

   @NotNull
   public final String getDomain() {
      return this.domain;
   }

   @NotNull
   public final String getNamePattern() {
      return this.namePattern;
   }

   public IconSet(@NotNull String domain, @NotNull String namePattern) {
      Intrinsics.checkParameterIsNotNull(domain, "domain");
      Intrinsics.checkParameterIsNotNull(namePattern, "namePattern");
      super();
      this.domain = domain;
      this.namePattern = namePattern;
      this.icons = new TextureAtlasSprite[16];
   }
}
