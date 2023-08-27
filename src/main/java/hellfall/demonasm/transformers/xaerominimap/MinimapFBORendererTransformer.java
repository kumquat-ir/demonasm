package hellfall.demonasm.transformers.xaerominimap;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("xaero.common.minimap.render.MinimapFBORenderer")
public class MinimapFBORendererTransformer extends DAMiniTransformer {
    @Patch.Method("renderPlayerHeadToFBO(Lxaero/common/minimap/MinimapProcessor;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/entity/AbstractClientPlayer;DDDDFZLxaero/common/minimap/MinimapRadar;ZZDLxaero/common/settings/ModSettings;)V")
    public void patchPlayerHeadRender(PatchContext ctx) {
        ctx.search(LDC(32.0F)).jumpAfter();
        ctx.add(
                POP(),
                LDC(64.0F)
        );
    }
}
