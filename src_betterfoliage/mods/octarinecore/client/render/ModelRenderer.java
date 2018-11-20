package mods.octarinecore.client.render;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.Intrinsics;
import mods.betterfoliage.loader.Refs;
import mods.octarinecore.common.Double3;
import mods.octarinecore.common.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 9},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J±\u0001\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u001a\b\u0002\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00120\u00142$\u0010\u0017\u001a \u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0018j\u0002`\u001a29\u0010\u001b\u001a5\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\b0\u001cj\u0002`\u001e¢\u0006\u0002\b\u001fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006 "},
   d2 = {"Lmods/octarinecore/client/render/ModelRenderer;", "Lmods/octarinecore/client/render/ShadingContext;", "()V", "temp", "Lmods/octarinecore/client/render/RenderVertex;", "getTemp", "()Lmods/octarinecore/client/render/RenderVertex;", "render", "", "worldRenderer", "Lnet/minecraft/client/renderer/BufferBuilder;", "model", "Lmods/octarinecore/client/render/Model;", "rot", "Lmods/octarinecore/common/Rotation;", "trans", "Lmods/octarinecore/common/Double3;", "forceFlat", "", "quadFilter", "Lkotlin/Function2;", "", "Lmods/octarinecore/client/render/Quad;", "icon", "Lkotlin/Function3;", "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "Lmods/octarinecore/client/render/QuadIconResolver;", "postProcess", "Lkotlin/Function6;", "Lmods/octarinecore/client/render/Vertex;", "Lmods/octarinecore/client/render/PostProcessLambda;", "Lkotlin/ExtensionFunctionType;", "BetterFoliage-MC1.12"}
)
public final class ModelRenderer extends ShadingContext {
   @NotNull
   private final RenderVertex temp = new RenderVertex();

   @NotNull
   public final RenderVertex getTemp() {
      return this.temp;
   }

   public final void render(@NotNull BufferBuilder worldRenderer, @NotNull Model model, @NotNull Rotation rot, @NotNull Double3 trans, boolean forceFlat, @NotNull Function2 quadFilter, @NotNull Function3 icon, @NotNull Function6 postProcess) {
      Intrinsics.checkParameterIsNotNull(worldRenderer, "worldRenderer");
      Intrinsics.checkParameterIsNotNull(model, "model");
      Intrinsics.checkParameterIsNotNull(rot, "rot");
      Intrinsics.checkParameterIsNotNull(trans, "trans");
      Intrinsics.checkParameterIsNotNull(quadFilter, "quadFilter");
      Intrinsics.checkParameterIsNotNull(icon, "icon");
      Intrinsics.checkParameterIsNotNull(postProcess, "postProcess");
      this.setRotation(rot);
      this.setAoEnabled(Minecraft.func_71379_u());
      ModelRendererKt.ensureSpaceForQuads(worldRenderer, model.getQuads().size() + 1);
      Iterable $receiver$iv = (Iterable)model.getQuads();
      int index$iv = 0;
      Iterator var11 = $receiver$iv.iterator();

      while(var11.hasNext()) {
         Object item$iv = var11.next();
         int var10000 = index$iv++;
         Quad quad = (Quad)item$iv;
         int quadIdx = var10000;
         if (((Boolean)quadFilter.invoke(quadIdx, quad)).booleanValue()) {
            TextureAtlasSprite drawIcon = (TextureAtlasSprite)icon.invoke(this, quadIdx, quad);
            if (drawIcon != null) {
               Refs.INSTANCE.getQuadSprite().set(worldRenderer, drawIcon);
               Object[] $receiver$iv = (Object[])quad.getVerts();
               int index$iv = 0;
               int var18 = $receiver$iv.length;

               for(int var19 = 0; var19 < var18; ++var19) {
                  Object item$iv = $receiver$iv[var19];
                  var10000 = index$iv++;
                  Vertex vert = (Vertex)item$iv;
                  int vertIdx = var10000;
                  this.temp.init(vert).rotate(this.getRotation()).translate(trans);
                  Shader shader = this.getAoEnabled() && !forceFlat ? vert.getAoShader() : vert.getFlatShader();
                  shader.shade((ShadingContext)this, this.temp);
                  postProcess.invoke(this.temp, this, quadIdx, quad, vertIdx, vert);
                  RenderVertex this_$iv = this.temp;
                  this_$iv.setU((double)(drawIcon.func_94212_f() - drawIcon.func_94209_e()) * (this_$iv.getU() + 0.5D) + (double)drawIcon.func_94209_e());
                  this_$iv.setV((double)(drawIcon.func_94210_h() - drawIcon.func_94206_g()) * (this_$iv.getV() + 0.5D) + (double)drawIcon.func_94206_g());
                  worldRenderer.func_181662_b(this.temp.getX(), this.temp.getY(), this.temp.getZ()).func_181666_a(this.temp.getRed(), this.temp.getGreen(), this.temp.getBlue(), 1.0F).func_187315_a(this.temp.getU(), this.temp.getV()).func_187314_a(this.temp.getBrightness() >> 16 & '\uffff', this.temp.getBrightness() & '\uffff').func_181675_d();
               }
            }
         }
      }

   }
}
