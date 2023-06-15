package hellfall.demonasm;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("gregtechmod.common.GT_OreDictHandler")
public class GT_OreDictHandlerTransformer extends MiniTransformer {
    @SuppressWarnings("deprecation")
    @Patch.Method("registerOre(Lnet/minecraftforge/oredict/OreDictionary$OreRegisterEvent;)V")
    public void patchRegisterOre(PatchContext ctx) {
        ctx.jumpToStart();
        PatchContext.SearchResult search = ctx.search(
            INVOKEINTERFACE("org/apache/logging/log4j/Logger", "warn", "(Ljava/lang/String;)V")
        );
        while (search.isSuccessful()) {
            search.jumpBefore();
            ctx.erase();
            ctx.add(
                    POP(),
                    POP()
            );
            search = ctx.search(
                    INVOKEINTERFACE("org/apache/logging/log4j/Logger", "warn", "(Ljava/lang/String;)V")
            );
        }
        ctx.jumpToStart();
        search = ctx.search(
                INVOKEINTERFACE("org/apache/logging/log4j/Logger", "error", "(Ljava/lang/String;)V")
        );
        while (search.isSuccessful()) {
            search.jumpBefore();
            ctx.erase();
            ctx.add(
                    POP(),
                    POP()
            );
            search = ctx.search(
                    INVOKEINTERFACE("org/apache/logging/log4j/Logger", "error", "(Ljava/lang/String;)V")
            );
        }
    }

    @SuppressWarnings("deprecation")
    @Patch.Method("activateHandler()V")
    public void patchActivateHandler(PatchContext ctx) {
        ctx.search(
                INVOKEINTERFACE("org/apache/logging/log4j/Logger", "warn", "(Ljava/lang/String;)V")
        ).jumpBefore();
        ctx.erase();
        ctx.add(
                POP(),
                POP()
        );
    }
}
