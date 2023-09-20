package hellfall.demonasm.transformers.warptheory;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("shukaro.warptheory.entity.RenderDoppelganger")
public class RenderDoppelgangerTransformer extends DAMiniTransformer {
    @SuppressWarnings("deprecation")
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        ctx.search(
                INVOKESPECIAL("net/minecraft/client/model/ModelBiped", "<init>", "(F)V")
        ).jumpBefore();
        ctx.erase();
        ctx.add(
                LDC(0.0f),
                BIPUSH(64),
                BIPUSH(64),
                INVOKESPECIAL("net/minecraft/client/model/ModelBiped", "<init>", "(FFII)V")
        );
    }
}
