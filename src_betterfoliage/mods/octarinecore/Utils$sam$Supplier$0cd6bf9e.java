package mods.octarinecore;

import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3
)
final class Utils$sam$Supplier$0cd6bf9e implements Supplier {
   // $FF: synthetic field
   private final Function0 function;

   Utils$sam$Supplier$0cd6bf9e(Function0 var1) {
      this.function = var1;
   }

   // $FF: synthetic method
   public final Object get() {
      return this.function.invoke();
   }
}
