package hellfall.demonasm.transformers.ftblib;

import hellfall.demonasm.transformers.DAMiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("ftb.lib.api.gui.GuiLM")
public class GuiLMTransformer extends DAMiniTransformer {
    @Patch.Method("drawPlayerHead(Ljava/lang/String;DDDDD)V")
    public void patchRenderHead(PatchContext ctx) {
        ctx.search(LDC(0.25D)).jumpAfter();
        ctx.add(
                POP2(),
                LDC(0.125D)
        );
        ctx.search(LDC(0.5D)).jumpAfter();
        ctx.add(
                POP2(),
                LDC(0.25D)
        );
        ctx.search(LDC(0.25D)).jumpAfter();
        ctx.add(
                POP2(),
                LDC(0.125D)
        );
        ctx.search(LDC(0.5D)).jumpAfter();
        ctx.add(
                POP2(),
                LDC(0.25D)
        );
    }
}
