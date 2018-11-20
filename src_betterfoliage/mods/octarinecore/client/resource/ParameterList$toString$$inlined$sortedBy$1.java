package mods.octarinecore.client.resource;

import java.util.Comparator;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;

@Metadata(
   mv = {1, 1, 10},
   bv = {1, 0, 2},
   k = 3,
   d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}
)
public final class ParameterList$toString$$inlined$sortedBy$1 implements Comparator {
   public final int compare(Object a, Object b) {
      Entry it = (Entry)a;
      Comparable var10000 = (Comparable)((String)it.getKey());
      it = (Entry)b;
      Comparable var5 = var10000;
      String var6 = (String)it.getKey();
      return ComparisonsKt.compareValues(var5, (Comparable)var6);
   }
}
