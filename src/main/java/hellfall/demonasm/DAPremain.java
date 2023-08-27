package hellfall.demonasm;

import hellfall.demonasm.transformers.bloodmagic.SigilSeerTransformer;
import hellfall.demonasm.transformers.botania.ModelSkullOverrideTransformer;
import hellfall.demonasm.transformers.botania.NEIInputHandlerTransformer;
import hellfall.demonasm.transformers.botania.RenderDopplegangerTransformer;
import hellfall.demonasm.transformers.forge.GuiModListTransformer;
import hellfall.demonasm.transformers.galacticraft.RenderPlayerGCTransformer;
import hellfall.demonasm.transformers.gt4.GT_OreDictHandlerTransformer;
import hellfall.demonasm.transformers.iguanatweaks.IguanaSkullTransformer;
import hellfall.demonasm.transformers.minecraft.TileEntitySkullRendererTransformer;
import hellfall.demonasm.transformers.opencomputers.RobotRendererTransformer;
import hellfall.demonasm.transformers.twilightforest.RenderTFGiantTransformer;
import hellfall.demonasm.transformers.xaerominimap.MinimapFBORendererTransformer;
import hellfall.zeroconfig.ZeroConfig;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;
import nilloader.api.NilModList;

public class DAPremain implements Runnable {
	public static final NilLogger log = NilLogger.get("demonasm");
	
	@Override
	public void run() {
		ZeroConfig.sync(DAConfig.class);

//		ModRemapper.setTargetMapping("srg-mcp");

		if (DAConfig.addToModList) {
			ForgeModListHacks.parseFrom(NilModList.getAll());
			ClassTransformer.register(new GuiModListTransformer());
		}

		if (DAConfig.fixHeadRendering) {
			log.info("Fixing player head rendering");
			ClassTransformer.register(new TileEntitySkullRendererTransformer());
			// botania overrides the renderer, fix it
			ClassTransformer.register(new ModelSkullOverrideTransformer());
			// gaia guardian uses player skin
			ClassTransformer.register(new RenderDopplegangerTransformer());
			// twilight forest giants use player skin
			ClassTransformer.register(new RenderTFGiantTransformer());
			// xaeros minimap player head rendering
			ClassTransformer.register(new MinimapFBORendererTransformer());
		}

		if (DAConfig.fixOcRobot) {
			log.info("Fixing OC robot rendering");
			ClassTransformer.register(new RobotRendererTransformer());
		}

		if (DAConfig.removeIguanaSkullContainer) {
			log.info("Making Iguana Tweaks skulls not return skeleton skulls");
			ClassTransformer.register(new IguanaSkullTransformer());
		}

		if (DAConfig.fixSeerSigil) {
			log.info("Making Seer Sigil more useful");
			ClassTransformer.register(new SigilSeerTransformer());
		}

		if (DAConfig.corporeaBookmarks) {
			log.info("Integrating Corporea with NEI bookmark sizes");
			ClassTransformer.register(new NEIInputHandlerTransformer());
		}

		if (DAConfig.shutupGT) {
			log.info("Silencing GT4 Oredict spam");
			ClassTransformer.register(new GT_OreDictHandlerTransformer());
		}

		if (DAConfig.shutupRuins) {
			log.info("Silencing Ruins creation spam");
			//TODO
		}

		if (DAConfig.noThermalPadding) {
			log.info("Thermal padding will not render. drip++");
			ClassTransformer.register(new RenderPlayerGCTransformer());
		}
	}
}
