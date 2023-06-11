package hellfall.earsheadfix;

import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;

public class EHFPremain implements Runnable {
	public static final NilLogger log = NilLogger.get("ears head fix");
	
	@Override
	public void run() {
		log.info("Fixing player head rendering");
		
		// 1.7 forge is a non-obfuscated environment for some reason, ignore mappings
		ModRemapper.setTargetMapping(null);

		ClassTransformer.register(new TileEntitySkullRendererTransformer());
	}

}
