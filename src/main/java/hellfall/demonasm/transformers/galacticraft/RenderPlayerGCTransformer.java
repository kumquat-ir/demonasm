package hellfall.demonasm.transformers.galacticraft;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("micdoodle8.mods.galacticraft.core.client.render.entities.RenderPlayerGC")
public class RenderPlayerGCTransformer extends DAMiniTransformer {
    @Patch.Method("renderModelS(Lnet/minecraft/client/renderer/entity/RendererLivingEntity;Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V")
    public void patchRenderModel(PatchContext ctx) {
        ctx.search(GETSTATIC("micdoodle8/mods/galacticraft/core/client/render/entities/RenderPlayerGC", "flagThermalOverride", "Z")).jumpAfter();
        ctx.add(
                POP(),
                ICONST_1()
        );
    }
}
