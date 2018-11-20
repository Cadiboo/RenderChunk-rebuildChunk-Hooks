package mods.octarinecore.metaprog;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u00030\u0001H\n¢\u0006\u0002\b\u0005"},
   d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "kotlin.jvm.PlatformType", "invoke"}
)
final class Reflection$reflectNestedObjects$1$1 extends Lambda implements Function0 {
   // $FF: synthetic field
   final Class $it;

   @NotNull
   public final Pair invoke() {
      Class var10000 = this.$it;
      Intrinsics.checkExpressionValueIsNotNull(this.$it, "it");
      String var1 = var10000.getName();
      Intrinsics.checkExpressionValueIsNotNull(var1, "it.name");
      return TuplesKt.to(StringsKt.split$default((CharSequence)var1, new String[]{"$"}, false, 0, 6, (Object)null).get(1), this.$it.getField("INSTANCE").get((Object)null));
   }

   Reflection$reflectNestedObjects$1$1(Class var1) {
      super(0);
      this.$it = var1;
   }
}
