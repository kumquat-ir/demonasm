package hellfall.demonasm;

import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;

public class DAPremain implements Runnable {
	public static final NilLogger log = NilLogger.get("demonasm");
	
	@Override
	public void run() {
		log.info("Fixing player head rendering");
		
		// 1.7 forge is a non-obfuscated environment for some reason, ignore mappings
		ModRemapper.setTargetMapping(null);

		ClassTransformer.register(new TileEntitySkullRendererTransformer());
	}

}
