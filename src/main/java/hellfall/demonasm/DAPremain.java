package hellfall.demonasm;

import hellfall.zeroconfig.ZeroConfig;
import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;
import nilloader.api.NilModList;

public class DAPremain implements Runnable {
	public static final NilLogger log = NilLogger.get("demonasm");
	
	@Override
	public void run() {
		ZeroConfig.sync(DAConfig.class);

		ModRemapper.setTargetMapping("org.archive.minecraftcoderpack.1.7.10-908");

		if (DAConfig.addToModList) {
			ForgeModListHacks.parseFrom(NilModList.getAll());
			ClassTransformer.register(new GuiModListTransformer());
		}

		if (DAConfig.fixHeadRendering) {
			log.info("Fixing player head rendering");
			ClassTransformer.register(new TileEntitySkullRendererTransformer());
		}

		if (DAConfig.removeIguanaSkullContainer) {
			log.info("Making Iguana Tweaks skulls sane");
			ClassTransformer.register(new IguanaSkullTransformer());
		}

		if (DAConfig.fixSeerSigil) {
			log.info("Making Seer Sigil more useful");
			ClassTransformer.register(new SigilSeerTransformer());
		}

		if (DAConfig.shutupGT) {
			log.info("Silencing GT4 Oredict spam");
			//TODO
//			ClassTransformer.register(new GT_OreDictHandlerTransformer());
		}

		if (DAConfig.shutupRuins) {
			log.info("Silencing Ruins creation spam");
			//TODO
		}
	}
}
