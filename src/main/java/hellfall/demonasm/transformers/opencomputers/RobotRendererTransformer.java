package hellfall.demonasm.transformers.opencomputers;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("li.cil.oc.client.renderer.tileentity.RobotRenderer$")
public class RobotRendererTransformer extends DAMiniTransformer {
    @Patch.Method("normal(Lnet/minecraft/util/Vec3;)V")
    @Patch.Method.AffectsControlFlow
    public void patchNormal(PatchContext ctx) {
        ctx.jumpToStart();
        ctx.add(RETURN());
    }
}
