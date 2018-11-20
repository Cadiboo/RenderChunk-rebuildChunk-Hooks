package mods.octarinecore.common;

import kotlin.Metadata;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.AxisDirection;

// $FF: synthetic class
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 3
)
public final class GeometryKt$WhenMappings {
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$0 = new int[AxisDirection.values().length];
   // $FF: synthetic field
   public static final int[] $EnumSwitchMapping$1;

   static {
      $EnumSwitchMapping$0[AxisDirection.POSITIVE.ordinal()] = 1;
      $EnumSwitchMapping$0[AxisDirection.NEGATIVE.ordinal()] = 2;
      $EnumSwitchMapping$1 = new int[EnumFacing.values().length];
      $EnumSwitchMapping$1[EnumFacing.DOWN.ordinal()] = 1;
      $EnumSwitchMapping$1[EnumFacing.UP.ordinal()] = 2;
      $EnumSwitchMapping$1[EnumFacing.NORTH.ordinal()] = 3;
      $EnumSwitchMapping$1[EnumFacing.SOUTH.ordinal()] = 4;
      $EnumSwitchMapping$1[EnumFacing.WEST.ordinal()] = 5;
      $EnumSwitchMapping$1[EnumFacing.EAST.ordinal()] = 6;
   }
}
