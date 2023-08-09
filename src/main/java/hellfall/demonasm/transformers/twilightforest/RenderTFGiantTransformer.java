package hellfall.demonasm.transformers.twilightforest;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("twilightforest.client.renderer.entity.RenderTFGiant")
public class RenderTFGiantTransformer extends DAMiniTransformer {
    @SuppressWarnings("deprecation")
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        ctx.search(
                INVOKESPECIAL("net/minecraft/client/model/ModelBiped", "<init>", "()V")
        ).jumpBefore();
        ctx.erase();
        ctx.add(
                LDC(0.0f),
                LDC(0.0f),
                BIPUSH(64),
                BIPUSH(64),
                INVOKESPECIAL("net/minecraft/client/model/ModelBiped", "<init>", "(FFII)V")
        );
    }
}
