package mods.octarinecore.metaprog;

import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"},
   d2 = {"<anonymous>", "Ljava/lang/reflect/Field;", "kotlin.jvm.PlatformType", "invoke"}
)
final class FieldRef$resolve$$inlined$map$lambda$1 extends Lambda implements Function0 {
   // $FF: synthetic field
   final String $it;
   // $FF: synthetic field
   final FieldRef this$0;

   FieldRef$resolve$$inlined$map$lambda$1(String var1, FieldRef var2) {
      super(0);
      this.$it = var1;
      this.this$0 = var2;
   }

   public final Field invoke() {
      Object var10000 = this.this$0.getParentClass().getElement();
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return ((Class)var10000).getDeclaredField(this.$it);
   }
}
