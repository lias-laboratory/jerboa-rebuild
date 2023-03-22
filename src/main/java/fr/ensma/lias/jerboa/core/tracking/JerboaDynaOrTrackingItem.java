package fr.ensma.lias.jerboa.core.tracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaIslet;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.util.Pair;
import up.jerboa.util.StopWatch;

public class JerboaDynaOrTrackingItem {
	
	private JerboaOrbit orbit;
	private String name;
	private List<Integer> islet;
	
	private Map<Integer, Set<Integer>> oldstate;
	private Map<Integer, Set<Integer>> newstate;
	
	
	public JerboaDynaOrTrackingItem(String name, JerboaOrbit orbit) {
		this.name = name;
		this.orbit = orbit;
	}
	
	public void fetch(JerboaGMap gmap) {
		StopWatch sw = new StopWatch();
		sw.display("== start tracking: " + name + " - " + orbit);
		islet = JerboaIslet.islet_par(gmap, orbit);
		sw.display("==== islet done");
		IntStream.range(0, islet.size()).parallel().forEach(i -> {
			if(i == islet.get(i) && !gmap.existNode(i)) {
				islet.set(i, -1);
			}
		});
		sw.display("==== fix convention");
	}
	
	
	public void fetch(JerboaGMap gmap, JerboaDynaOrTrackingItem prev) {
		StopWatch sw = new StopWatch();
		sw.display("== start tracking: " + name + " - " + orbit);
		islet = JerboaIslet.islet_par(gmap, orbit);
		sw.display("==== islet done");
		IntStream.range(0, islet.size()).parallel().forEach(i -> {
			if(i == islet.get(i) && !gmap.existNode(i)) {
				islet.set(i, -1);
			}
		});
		sw.display("==== fix convention");
		
		
		
		sw.display("Start analysis evolution of orbit...");
		HashSet<Integer> prevIsletID = new HashSet<>(prev.islet);
		HashSet<Integer> newIsletID = new HashSet<>(islet);
		// tous les brins de l'ancienne orbite donne une orbite => nomodif
		// tous les brins de l'ancienne orbite donne plusieurs orbits => split
		// tous les brins de l'ancienne orbite ne donne aucune orbit => deleted
		
		
		Map<Integer, List<Integer>> old_orbits = IntStream.range(0, prev.islet.size()).parallel().boxed().collect(Collectors.groupingByConcurrent(i -> {
			try {
				return  prev.islet.get(i);
			} catch(Exception e) { return -1; } // cas des creation
		}));
		
		oldstate = old_orbits.entrySet().parallelStream().map(entry -> {
			int oid = entry.getKey();
			List<Integer> darts = entry.getValue();
			Set<Integer> futur = darts.stream().map(did -> {
				try { return islet.get(did); } catch(Exception e) { return -1; } // cas des suppressions
			}).filter(i -> i != -1).collect(Collectors.toSet());
			
			/*DynOrTrackState state;
			switch(futur.size()) {
			case 0: state = DynOrTrackState.DELETED; break;
			case 1: state = DynOrTrackState.NOMODIF; break;
			default: state = DynOrTrackState.SPLIT; break;
			}*/
			return new Pair<>(oid, futur);
		}).collect(Collectors.toConcurrentMap(e -> e.l(), e -> e.r())) ;
		
		
		
		
		// tous les brins d'une nouvelle orbite provient de plusieurs ancienne orbite => merge
		// tous les brins d'une nouvelle orbite provient d'AUCUNE orbit => creation

		Map<Integer, List<Integer>> new_orbits = IntStream.range(0, islet.size()).parallel().boxed().collect(Collectors.groupingByConcurrent(i -> {
			try {
				return  islet.get(i);
			} catch(Exception e) { return -1; } // cas des creation
		}));
		
		newstate = new_orbits.entrySet().parallelStream().map(entry -> {
			int oid = entry.getKey();
			List<Integer> darts = entry.getValue();
			Set<Integer> passe = darts.stream().map(did -> {
				try { return prev.islet.get(did); } catch(Exception e) { return -1; } // cas des suppressions
			}).filter(i -> i != -1).collect(Collectors.toSet());
			
			/*DynOrTrackState state;
			switch(passe.size()) {
			case 0: state = DynOrTrackState.CREATED; break;
			case 1: state = DynOrTrackState.NOMODIF; break;
			default: state = DynOrTrackState.MERGE; break;
			}*/
			return new Pair<>(oid, passe);
		}).collect(Collectors.toConcurrentMap(e -> e.l(), e -> e.r())) ;
		
		
		
		// maintenant on fait la diffusion
		sw.display("end analysis");
	}
	
	
	@Override
	public String toString() {
		return name + orbit;
	}
	
	public List<Integer> getIslet() {
		return islet;
	}
	
	public String getName() { return name; }
	public JerboaOrbit getOrbit() { return orbit; }

	public Map<Integer, Set<Integer>> getOldstate() {
		return oldstate;
	}

	public Map<Integer, Set<Integer>> getNewstate() {
		return newstate;
	}
	
	
	
}
