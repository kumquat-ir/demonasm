package hellfall.demonasm;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("gregtechmod.common.GT_OreDictHandler")
public class GT_OreDictHandlerTransformer extends MiniTransformer {
    @Patch.Method("registerOre(Lnet/minecraftforge/oredict/OreDictionary$OreRegisterEvent;)V")
    public void patchRegisterOre(PatchContext ctx) {

    }
}
