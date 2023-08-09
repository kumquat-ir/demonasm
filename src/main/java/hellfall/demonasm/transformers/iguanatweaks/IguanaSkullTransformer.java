package hellfall.demonasm.transformers.iguanatweaks;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("iguanaman.iguanatweakstconstruct.mobheads.items.IguanaSkull")
public class IguanaSkullTransformer extends DAMiniTransformer {
    @Patch.Method("<init>()V")
    @Patch.Method.AffectsControlFlow
    public void patchInit(PatchContext ctx) {
        ctx.search(POP()).jumpAfter();
        ctx.add(RETURN());
    }
}
