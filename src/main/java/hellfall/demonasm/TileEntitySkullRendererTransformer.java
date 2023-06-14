package hellfall.demonasm;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer")
public class TileEntitySkullRendererTransformer extends MiniTransformer {

	@Patch.Method("func_152674_a(FFFIFILcom/mojang/authlib/GameProfile;)V")
	public void patchRender(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.search(ASTORE(9)).jumpAfter(); // first line of player head branch
		ctx.add( // set skull renderer to the version with correct texture coords for 64x64 textures
			ALOAD(0),
			GETFIELD("net/minecraft/client/renderer/tileentity/TileEntitySkullRenderer", "field_147538_h", "Lnet/minecraft/client/model/ModelSkeletonHead;"),
			ASTORE(8)
		);
	}
}
