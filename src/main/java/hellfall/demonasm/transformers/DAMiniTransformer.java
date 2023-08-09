package hellfall.demonasm.transformers;

import nilloader.api.lib.mini.MiniTransformer;

public class DAMiniTransformer extends MiniTransformer {
    protected String hooks() {
        return getClass().getName().replace('.', '/')+"$Hooks";
    }
}
