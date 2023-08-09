package hellfall.demonasm.transformers.botania;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.BookmarkPanel;
import codechicken.nei.ItemPanel;
import codechicken.nei.LayoutManager;
import hellfall.demonasm.DAConfig;
import hellfall.demonasm.transformers.DAMiniTransformer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;
import vazkii.botania.api.corporea.CorporeaHelper;

import java.awt.*;

@Patch.Class("vazkii.botania.client.integration.nei.NEIInputHandler")
public class NEIInputHandlerTransformer extends DAMiniTransformer {
    @Patch.Method("keyTyped(Lnet/minecraft/client/gui/inventory/GuiContainer;CI)Z")
    public void patchKeyTyped(PatchContext ctx) {
        ctx.search(ICONST_4()).jumpAfter();
        ctx.search(ISTORE(8)).jumpBefore();
        ctx.add(
                INVOKESTATIC(hooks(), "setBookmarkAmount", "(I)I")
        );
    }

    @SuppressWarnings("unused")
    public static class Hooks {
        public static int setBookmarkAmount(int originalSize) {
            Point mouse = GuiDraw.getMousePosition();
            ItemPanel.ItemPanelSlot slot = LayoutManager.bookmarkPanel.getGrid().getSlotMouseOver(mouse.x, mouse.y);
            if (slot == null) {
                return originalSize;
            }
            BookmarkPanel.BookmarkGrid BGrid = ((BookmarkPanel.BookmarkGrid) LayoutManager.bookmarkPanel.getGrid());
            BookmarkPanel.BookmarkStackMeta meta = BGrid.getMetadata(slot.slotIndex);
            if (meta.factor < 0) {
                return originalSize;
            }
            boolean foundRecipe = false;
            Minecraft mc = Minecraft.getMinecraft();
            int iterdir = BGrid.getViewMode() == BookmarkPanel.BookmarkViewMode.TODO_LIST ? 1 : -1;
            for (int i = slot.slotIndex + iterdir; i < BGrid.metadata.size() && BGrid.getMetadata(i).ingredient; i += iterdir) {
                foundRecipe = true;
                if (BGrid.getMetadata(i).factor > 0 || DAConfig.corporeaIncludeNoAmount) {
                    ItemStack item = BGrid.getItem(i);
                    String name = CorporeaHelper.stripControlCodes(item.getDisplayName());
                    String full = item.stackSize + " " + name;
                    mc.ingameGUI.getChatGUI().addToSentMessages(full);
                    mc.thePlayer.sendChatMessage(full);
                }
            }
            return foundRecipe ? 0 : slot.item.stackSize;
        }
    }
}
