package mods.octarinecore.client.resource;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import mods.octarinecore.client.render.Model;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\t\u0018\u00002\u00020\u0001B,\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001d\u0010\u0004\u001a\u0019\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0003H\u0086\u0002J\b\u0010\u0015\u001a\u00020\u0007H\u0016R(\u0010\u0004\u001a\u0019\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"},
   d2 = {"Lmods/octarinecore/client/resource/ModelSet;", "Lmods/octarinecore/client/resource/IConfigChangeListener;", "num", "", "init", "Lkotlin/Function2;", "Lmods/octarinecore/client/render/Model;", "", "Lkotlin/ExtensionFunctionType;", "(ILkotlin/jvm/functions/Function2;)V", "getInit", "()Lkotlin/jvm/functions/Function2;", "models", "", "getModels", "()[Lmods/octarinecore/client/render/Model;", "[Lmods/octarinecore/client/render/Model;", "getNum", "()I", "get", "idx", "onConfigChange", "BetterFoliage-MC1.12"}
)
public final class ModelSet implements IConfigChangeListener {
   @NotNull
   private final Model[] models;
   private final int num;
   @NotNull
   private final Function2 init;

   @NotNull
   public final Model[] getModels() {
      return this.models;
   }

   public void onConfigChange() {
      Iterable $receiver$iv = (Iterable)RangesKt.until(0, this.num);

      int element$iv;
      Model var5;
      Model[] var7;
      for(Iterator var2 = $receiver$iv.iterator(); var2.hasNext(); var7[element$iv] = var5) {
         element$iv = ((IntIterator)var2).nextInt();
         Model[] var10000 = this.models;
         var5 = new Model();
         var7 = var10000;
         this.init.invoke(var5, element$iv);
      }

   }

   @NotNull
   public final Model get(int idx) {
      return this.models[idx % this.num];
   }

   public final int getNum() {
      return this.num;
   }

   @NotNull
   public final Function2 getInit() {
      return this.init;
   }

   public ModelSet(int num, @NotNull Function2 init) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      super();
      this.num = num;
      this.init = init;
      int size$iv = this.num;
      Object[] result$iv = new Model[size$iv];
      int i$iv = 0;

      for(int var6 = result$iv.length; i$iv < var6; ++i$iv) {
         Model var8 = new Model();
         this.init.invoke(var8, i$iv);
         result$iv[i$iv] = var8;
      }

      this.models = result$iv;
   }
}
