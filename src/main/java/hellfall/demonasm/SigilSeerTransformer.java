package hellfall.demonasm;

import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("WayofTime.alchemicalWizardry.common.items.sigil.SigilSeer")
public class SigilSeerTransformer extends MiniTransformer {
    @Override
    protected boolean modifyClassStructure(ClassNode clazz) {
        // is this the right way to do this? who knows, it works
        clazz.visit(clazz.version, clazz.access, clazz.name, clazz.signature, "WayofTime/alchemicalWizardry/common/items/sigil/SigilDivination", clazz.interfaces.toArray(new String[0]));
        return false;
    }

    @SuppressWarnings("deprecation")
    @Patch.Method("<init>()V")
    public void patchInit(PatchContext ctx) {
        // replace with new correct super constructor
        ctx.search(INVOKESPECIAL("net/minecraft/item/Item", "<init>", "()V")).jumpBefore();
        ctx.erase();
        ctx.add(INVOKESPECIAL("WayofTime/alchemicalWizardry/common/items/sigil/SigilDivination", "<init>", "()V"));
    }

    @Patch.Method("func_77659_a(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;")
    @Patch.Method.AffectsControlFlow
    public void patchRightClick(PatchContext ctx) {
        ctx.jumpToStart();
        ctx.add(
                ALOAD(0),
                ALOAD(1),
                ALOAD(2),
                ALOAD(3),
                INVOKESPECIAL("WayofTime/alchemicalWizardry/common/items/sigil/SigilDivination", "func_77659_a", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;"),
                ARETURN()
        );
    }
}
