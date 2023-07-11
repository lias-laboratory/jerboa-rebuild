package fr.ensma.lias.jerboa;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.ensma.lias.jerboa.bridge.JerboaBridgeDynaOrTracking;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.core.tracking.JerboaModelerDynOrTrack;
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
		String initinputfile = "cube.jba";
		
		if(args.length > 0 && "dynamic".equals(args[0]))
			forceDynamic = true;
		
		JerboaRuleAtomic operation = (JerboaRuleAtomic) modtrack.getRule("SubdivQuad");
		if(args.length > 1) {
			JerboaRuleAtomic at = (JerboaRuleAtomic) modtrack.getRule(args[1]);
			if(at != null)
				operation = at;
		}
		
		final String inputfile;
		if(args.length > 2)
			inputfile = args[2];
		else
			inputfile = initinputfile;
		
		File file = new File(inputfile);
		if(file.exists()) {
			bridge.loadFile(inputfile);
		}
		
		modtrack.setForceDynamic(forceDynamic);
		
		JerboaGMap gmap = modtrack.getGMap();
		
		StopWatch sw = new StopWatch();
		sw.display("====================================GMAP SIZE: "+gmap.size());
		for(int i = 0; i < 8; i++) {
			sw.start();
			sw.display("=================================== ITERATION: "+i);
			operation.apply(modtrack.getGMap(), JerboaInputHooksAtomic.wrap(gmap.getNode(gmap.size() - 1)));
			sw.display("================================END ITERATION: "+i);
			sw.display("====================================GMAP SIZE: "+gmap.size());
			
		}
		
		String rep = getBaseName(inputfile);
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
		File cur = new File(".");
		File dir = new File(cur, rep +"_" + operation.getName() +"_" + args[0] + "_" + format.format(now));
		
		boolean res = dir.mkdir();
		System.out.print("attempt create outputdir ("+rep+") " + res);
		IntStream.range(0, modtrack.getTrackedOrbit().size()).sequential().forEach(oi -> {
			JerboaOrbit orbit = modtrack.getTrackedOrbit().get(oi);
			
			String name = Arrays.stream(orbit.tab()).mapToObj(a -> "a"+a).collect(Collectors.joining());
			
			File fileout = new File(dir, rep+ "_"+name+".csv");
			modtrack.exportTracking(fileout, oi);
		});
		
	}

	
	public static String getBaseName(String fileName) {
	    int index = fileName.lastIndexOf('.');
	    if (index == -1) {
	        return fileName;
	    } else {
	        return fileName.substring(0, index);
	    }
	}
}
