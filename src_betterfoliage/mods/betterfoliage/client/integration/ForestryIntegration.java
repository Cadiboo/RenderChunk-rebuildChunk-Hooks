package mods.betterfoliage.client.integration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.client.Client;
import mods.betterfoliage.client.render.LogRegistry;
import mods.betterfoliage.client.texture.LeafRegistry;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.metaprog.ClassRef;
import mods.octarinecore.metaprog.FieldRef;
import mods.octarinecore.metaprog.MethodRef;
import mods.octarinecore.metaprog.Reflection;
import mods.octarinecore.metaprog.Resolvable;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u000f\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0015\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\u0017\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u0019\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0011\u0010\u001b\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u0011\u0010%\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0006R\u0011\u0010'\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\"R\u0011\u0010)\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0011\u0010+\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\"R\u0011\u0010-\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\"¨\u0006/"},
   d2 = {"Lmods/betterfoliage/client/integration/ForestryIntegration;", "", "()V", "IAlleleTreeSpecies", "Lmods/octarinecore/metaprog/ClassRef;", "getIAlleleTreeSpecies", "()Lmods/octarinecore/metaprog/ClassRef;", "ILeafSpriteProvider", "getILeafSpriteProvider", "IWoodType", "getIWoodType", "PropertyTreeType", "getPropertyTreeType", "PropertyWoodType", "getPropertyWoodType", "TdSpecies", "Lmods/octarinecore/metaprog/FieldRef;", "getTdSpecies", "()Lmods/octarinecore/metaprog/FieldRef;", "TeLfancy", "getTeLfancy", "TeLleafTextures", "getTeLleafTextures", "TeLplain", "getTeLplain", "TeLpollfancy", "getTeLpollfancy", "TeLpollplain", "getTeLpollplain", "TextureLeaves", "getTextureLeaves", "TiLgetLeaveSprite", "Lmods/octarinecore/metaprog/MethodRef;", "getTiLgetLeaveSprite", "()Lmods/octarinecore/metaprog/MethodRef;", "TileLeaves", "getTileLeaves", "TreeDefinition", "getTreeDefinition", "barkTex", "getBarkTex", "getLeafSpriteProvider", "getGetLeafSpriteProvider", "getSprite", "getGetSprite", "heartTex", "getHeartTex", "BetterFoliage-MC1.12"}
)
public final class ForestryIntegration {
   @NotNull
   private static final ClassRef TextureLeaves;
   @NotNull
   private static final FieldRef TeLleafTextures;
   @NotNull
   private static final FieldRef TeLplain;
   @NotNull
   private static final FieldRef TeLfancy;
   @NotNull
   private static final FieldRef TeLpollplain;
   @NotNull
   private static final FieldRef TeLpollfancy;
   @NotNull
   private static final ClassRef TileLeaves;
   @NotNull
   private static final MethodRef TiLgetLeaveSprite;
   @NotNull
   private static final ClassRef PropertyWoodType;
   @NotNull
   private static final ClassRef IWoodType;
   @NotNull
   private static final MethodRef barkTex;
   @NotNull
   private static final MethodRef heartTex;
   @NotNull
   private static final ClassRef PropertyTreeType;
   @NotNull
   private static final ClassRef TreeDefinition;
   @NotNull
   private static final ClassRef IAlleleTreeSpecies;
   @NotNull
   private static final ClassRef ILeafSpriteProvider;
   @NotNull
   private static final FieldRef TdSpecies;
   @NotNull
   private static final MethodRef getLeafSpriteProvider;
   @NotNull
   private static final MethodRef getSprite;
   public static final ForestryIntegration INSTANCE;

   @NotNull
   public final ClassRef getTextureLeaves() {
      return TextureLeaves;
   }

   @NotNull
   public final FieldRef getTeLleafTextures() {
      return TeLleafTextures;
   }

   @NotNull
   public final FieldRef getTeLplain() {
      return TeLplain;
   }

   @NotNull
   public final FieldRef getTeLfancy() {
      return TeLfancy;
   }

   @NotNull
   public final FieldRef getTeLpollplain() {
      return TeLpollplain;
   }

   @NotNull
   public final FieldRef getTeLpollfancy() {
      return TeLpollfancy;
   }

   @NotNull
   public final ClassRef getTileLeaves() {
      return TileLeaves;
   }

   @NotNull
   public final MethodRef getTiLgetLeaveSprite() {
      return TiLgetLeaveSprite;
   }

   @NotNull
   public final ClassRef getPropertyWoodType() {
      return PropertyWoodType;
   }

   @NotNull
   public final ClassRef getIWoodType() {
      return IWoodType;
   }

   @NotNull
   public final MethodRef getBarkTex() {
      return barkTex;
   }

   @NotNull
   public final MethodRef getHeartTex() {
      return heartTex;
   }

   @NotNull
   public final ClassRef getPropertyTreeType() {
      return PropertyTreeType;
   }

   @NotNull
   public final ClassRef getTreeDefinition() {
      return TreeDefinition;
   }

   @NotNull
   public final ClassRef getIAlleleTreeSpecies() {
      return IAlleleTreeSpecies;
   }

   @NotNull
   public final ClassRef getILeafSpriteProvider() {
      return ILeafSpriteProvider;
   }

   @NotNull
   public final FieldRef getTdSpecies() {
      return TdSpecies;
   }

   @NotNull
   public final MethodRef getGetLeafSpriteProvider() {
      return getLeafSpriteProvider;
   }

   @NotNull
   public final MethodRef getGetSprite() {
      return getSprite;
   }

   static {
      ForestryIntegration var0 = new ForestryIntegration();
      INSTANCE = var0;
      TextureLeaves = new ClassRef("forestry.arboriculture.models.TextureLeaves");
      TeLleafTextures = new FieldRef(TextureLeaves, "leafTextures", Refs.INSTANCE.getMap());
      TeLplain = new FieldRef(TextureLeaves, "plain", Refs.INSTANCE.getResourceLocation());
      TeLfancy = new FieldRef(TextureLeaves, "fancy", Refs.INSTANCE.getResourceLocation());
      TeLpollplain = new FieldRef(TextureLeaves, "pollinatedPlain", Refs.INSTANCE.getResourceLocation());
      TeLpollfancy = new FieldRef(TextureLeaves, "pollinatedFancy", Refs.INSTANCE.getResourceLocation());
      TileLeaves = new ClassRef("forestry.arboriculture.tiles.TileLeaves");
      TiLgetLeaveSprite = new MethodRef(TileLeaves, "getLeaveSprite", Refs.INSTANCE.getResourceLocation(), new ClassRef[]{(ClassRef)ClassRef.Companion.getBoolean()});
      PropertyWoodType = new ClassRef("forestry.arboriculture.blocks.PropertyWoodType");
      IWoodType = new ClassRef("forestry.api.arboriculture.IWoodType");
      barkTex = new MethodRef(IWoodType, "getBarkTexture", Refs.INSTANCE.getString(), new ClassRef[0]);
      heartTex = new MethodRef(IWoodType, "getHeartTexture", Refs.INSTANCE.getString(), new ClassRef[0]);
      PropertyTreeType = new ClassRef("forestry.arboriculture.blocks.PropertyTreeType");
      TreeDefinition = new ClassRef("forestry.arboriculture.genetics.TreeDefinition");
      IAlleleTreeSpecies = new ClassRef("forestry.api.arboriculture.IAlleleTreeSpecies");
      ILeafSpriteProvider = new ClassRef("forestry.api.arboriculture.ILeafSpriteProvider");
      TdSpecies = new FieldRef(TreeDefinition, "species", IAlleleTreeSpecies);
      getLeafSpriteProvider = new MethodRef(IAlleleTreeSpecies, "getLeafSpriteProvider", ILeafSpriteProvider, new ClassRef[0]);
      getSprite = new MethodRef(ILeafSpriteProvider, "getSprite", Refs.INSTANCE.getResourceLocation(), new ClassRef[]{(ClassRef)ClassRef.Companion.getBoolean(), (ClassRef)ClassRef.Companion.getBoolean()});
      if (Loader.isModLoaded("forestry") && Reflection.allAvailable((Resolvable)TiLgetLeaveSprite, (Resolvable)getLeafSpriteProvider, (Resolvable)getSprite)) {
         Client var10000 = Client.INSTANCE;
         Level var10001 = Level.INFO;
         Intrinsics.checkExpressionValueIsNotNull(Level.INFO, "Level.INFO");
         var10000.log(var10001, "Forestry support initialized");
         LeafRegistry.INSTANCE.getSubRegistries().add(ForestryLeavesSupport.INSTANCE);
         LogRegistry.INSTANCE.getSubRegistries().add(ForestryLogSupport.INSTANCE);
      }

   }
}
