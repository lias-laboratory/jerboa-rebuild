package fr.ensma.lias.jerboa;

import java.io.File;

import fr.ensma.lias.jerboa.bridge.JerboaBridgeDynaOrTracking;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaModelerDynOrTrack;
import fr.up.xlim.sic.ig.jerboa.trigger.tools.JerboaMonitorInfoConsole;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleAtomic;
import up.jerboa.core.rule.JerboaInputHooksAtomic;
import up.jerboa.exception.JerboaException;
import up.jerboa.util.StopWatch;

public class JerboaTrackedOrbitPerfStudy {

	final static JerboaOrbit[] alltrackedOrbit = new JerboaOrbit[] {
			JerboaOrbit.orbit(),
			
			JerboaOrbit.orbit(0),
			JerboaOrbit.orbit(1),
			JerboaOrbit.orbit(2),
			JerboaOrbit.orbit(3),
			
			JerboaOrbit.orbit(0,1),
			JerboaOrbit.orbit(0,2),
			JerboaOrbit.orbit(0,3),
			
			JerboaOrbit.orbit(1,2),
			JerboaOrbit.orbit(1,3),
			
			JerboaOrbit.orbit(2,3),
			
			JerboaOrbit.orbit(0,1,2),
			JerboaOrbit.orbit(0,1,3),
			JerboaOrbit.orbit(0,2,3),
			
			JerboaOrbit.orbit(1,2,3),
			
			JerboaOrbit.orbit(0,1,2,3)
	};
	
	public static void main(String[] args) throws JerboaException {
		// RawDynaOrTrackModeler modeler = new RawDynaOrTrackModeler();
		ModelerGenerated modeler = new ModelerGenerated();
		JerboaModelerDynOrTrack modtrack = new JerboaModelerDynOrTrack(modeler, alltrackedOrbit);

		JerboaBridgeDynaOrTracking bridge = new JerboaBridgeDynaOrTracking(modtrack);
		
		boolean forceDynamic = false;
		String inputfile = "cube.jba";
		
		if(args.length > 0 && "dynamic".equals(args[0]))
			forceDynamic = true;
			
		if(args.length > 1)
			inputfile = args[1];
		
		File file = new File(inputfile);
		if(file.exists()) {
			bridge.loadFile(inputfile);
		}
		
		modtrack.setForceDynamic(forceDynamic);
		
		JerboaRuleAtomic subdiv = (JerboaRuleAtomic) modtrack.getRule("SubdivQuad");
		JerboaGMap gmap = modtrack.getGMap();
		
		StopWatch sw = new StopWatch();
		sw.display("====================================GMAP SIZE: "+gmap.size());
		for(int i = 0; i < 6; i++) {
			sw.start();
			sw.display("=================================== ITERATION: "+i);
			subdiv.apply(modtrack.getGMap(), JerboaInputHooksAtomic.wrap(gmap.getNode(0)));
			sw.display("================================END ITERATION: "+i);
			sw.display("====================================GMAP SIZE: "+gmap.size());
			
		}
		
	}

}
