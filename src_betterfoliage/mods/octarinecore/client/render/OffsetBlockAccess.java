package mods.octarinecore.client.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0015\u0010\f\u001a\u0004\u0018\u00010\u00042\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0086\bJ\u001a\u0010\u000e\u001a\n \u0010*\u0004\u0018\u00010\u000f0\u000f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J\u001a\u0010\u0011\u001a\n \u0010*\u0004\u0018\u00010\u00120\u00122\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J\u001a\u0010\u0013\u001a\u00020\u00142\b\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J\u001c\u0010\u0016\u001a\u00020\u00142\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J\u0010\u0010\u001b\u001a\n \u0010*\u0004\u0018\u00010\u001c0\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u001e2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0016J$\u0010\u001f\u001a\u00020\u001e2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\b\u0010 \u001a\u0004\u0018\u00010\u00182\u0006\u0010!\u001a\u00020\u001eH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\b¨\u0006\""},
   d2 = {"Lmods/octarinecore/client/render/OffsetBlockAccess;", "Lnet/minecraft/world/IBlockAccess;", "original", "modded", "Lnet/minecraft/util/math/BlockPos;", "target", "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)V", "getModded", "()Lnet/minecraft/util/math/BlockPos;", "getOriginal", "()Lnet/minecraft/world/IBlockAccess;", "getTarget", "actualPos", "pos", "getBiome", "Lnet/minecraft/world/biome/Biome;", "kotlin.jvm.PlatformType", "getBlockState", "Lnet/minecraft/block/state/IBlockState;", "getCombinedLight", "", "lightValue", "getStrongPower", "direction", "Lnet/minecraft/util/EnumFacing;", "getTileEntity", "Lnet/minecraft/tileentity/TileEntity;", "getWorldType", "Lnet/minecraft/world/WorldType;", "isAirBlock", "", "isSideSolid", "side", "_default", "BetterFoliage-MC1.12"}
)
public final class OffsetBlockAccess implements IBlockAccess {
   @NotNull
   private final IBlockAccess original;
   @NotNull
   private final BlockPos modded;
   @NotNull
   private final BlockPos target;

   @Nullable
   public final BlockPos actualPos(@Nullable BlockPos pos) {
      return pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
   }

   public Biome func_180494_b(@Nullable BlockPos pos) {
      IBlockAccess var4 = this.original;
      BlockPos var5 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var4.func_180494_b(var5);
   }

   public IBlockState func_180495_p(@Nullable BlockPos pos) {
      IBlockAccess var4 = this.original;
      BlockPos var5 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var4.func_180495_p(var5);
   }

   public int func_175626_b(@Nullable BlockPos pos, int lightValue) {
      IBlockAccess var5 = this.original;
      BlockPos var6 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var5.func_175626_b(var6, lightValue);
   }

   public int func_175627_a(@Nullable BlockPos pos, @Nullable EnumFacing direction) {
      IBlockAccess var5 = this.original;
      BlockPos var6 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var5.func_175627_a(var6, direction);
   }

   @Nullable
   public TileEntity func_175625_s(@Nullable BlockPos pos) {
      IBlockAccess var4 = this.original;
      BlockPos var5 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var4.func_175625_s(var5);
   }

   public WorldType func_175624_G() {
      return this.original.func_175624_G();
   }

   public boolean func_175623_d(@Nullable BlockPos pos) {
      IBlockAccess var4 = this.original;
      BlockPos var5 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var4.func_175623_d(var5);
   }

   public boolean isSideSolid(@Nullable BlockPos pos, @Nullable EnumFacing side, boolean _default) {
      IBlockAccess var6 = this.original;
      BlockPos var7 = pos != null && pos.func_177958_n() == this.getModded().func_177958_n() && pos.func_177956_o() == this.getModded().func_177956_o() && pos.func_177952_p() == this.getModded().func_177952_p() ? this.getTarget() : pos;
      return var6.isSideSolid(var7, side, _default);
   }

   @NotNull
   public final IBlockAccess getOriginal() {
      return this.original;
   }

   @NotNull
   public final BlockPos getModded() {
      return this.modded;
   }

   @NotNull
   public final BlockPos getTarget() {
      return this.target;
   }

   public OffsetBlockAccess(@NotNull IBlockAccess original, @NotNull BlockPos modded, @NotNull BlockPos target) {
      Intrinsics.checkParameterIsNotNull(original, "original");
      Intrinsics.checkParameterIsNotNull(modded, "modded");
      Intrinsics.checkParameterIsNotNull(target, "target");
      super();
      this.original = original;
      this.modded = modded;
      this.target = target;
   }
}
