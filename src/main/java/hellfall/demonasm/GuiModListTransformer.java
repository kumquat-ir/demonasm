package hellfall.demonasm;

import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.ModMetadata;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

import java.util.Collections;

@Patch.Class("cpw.mods.fml.client.GuiModList")
public class GuiModListTransformer extends MiniTransformer {

	@Patch.Method("<init>(Lnet/minecraft/client/gui/GuiScreen;)V")
	public void patchModAdders(PatchContext ctx) {
		ctx.jumpToLastReturn();

		ctx.add(
			ALOAD(0),
			INVOKESTATIC("hellfall/demonasm/GuiModListTransformer$Hooks", "addNilmods", "(Lcpw/mods/fml/client/GuiModList;)V")
		);
	}

	@SuppressWarnings("unused")
	public static class Hooks {
		
		public static void addNilmods(GuiModList modList) {
			DAPremain.log.info("Adding {} mod list entries", ForgeModListHacks.ids.size());
			for (int i = 0; i < ForgeModListHacks.ids.size(); i++) {
				ModMetadata forgeMeta = new ModMetadata();
				forgeMeta.modId = "Nilmod{" + ForgeModListHacks.ids.get(i) + "}";
				forgeMeta.name = ForgeModListHacks.names.get(i);
				forgeMeta.description = ForgeModListHacks.descs.get(i);
				forgeMeta.version = ForgeModListHacks.versions.get(i);
				forgeMeta.authorList = Collections.singletonList(ForgeModListHacks.authors.get(i));
				modList.mods.add(new DummyModContainer(forgeMeta));
			}
		}
		
	}
	
}
