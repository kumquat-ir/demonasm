package hellfall.demonasm;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("vazkii.botania.client.model.ModelSkullOverride")
public class ModelSkullOverrideTransformer extends MiniTransformer {
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        ctx.search(BIPUSH(32)).jumpAfter();
        ctx.add(
                POP(),
                BIPUSH(64)
        );
    }
}
