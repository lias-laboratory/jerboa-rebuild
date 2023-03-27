package fr.ensma.lias.jerboa.core.tracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.plaf.basic.BasicComboBoxEditor;

import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaIslet;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.util.Pair;
import up.jerboa.util.StopWatch;

public class JerboaDynaOrTrackingItem {
	
	private JerboaOrbit orbit;
	private JerboaRuleDynOrTrack rule;
	private List<Integer> islet;
	
	private Map<Integer, Set<Integer>> oldstate;
	private Map<Integer, Set<Integer>> newstate;
	
	
	private List<DynOrTrackOrbitInst> states; // <OrbitID , state>
	
	private boolean isStatic;
	
	
	public JerboaDynaOrTrackingItem(JerboaRuleDynOrTrack rule, JerboaOrbit orbit) {
		this.rule = rule;
		this.orbit = orbit;
		this.states = new ArrayList<DynOrTrackOrbitInst>();
	}
	
	public void fetch(JerboaGMap gmap) {
		StopWatch sw = new StopWatch();
		sw.display("== start tracking: (initial) - " + orbit);
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
		sw.display("== start tracking: " + rule.getFullname() + " - " + orbit);
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
		
		
		Map<Integer, List<Integer>> old_orbits = IntStream.range(0, prev.islet.size()).sequential().boxed().collect(Collectors.groupingByConcurrent(i -> {
			try {
				return  prev.islet.get(i);
			} catch(Exception e) { return -1; } // cas des creation
		}));
		
		oldstate = old_orbits.entrySet().stream().map(entry -> {
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

		Map<Integer, List<Integer>> new_orbits = IntStream.range(0, islet.size()).sequential().boxed().collect(Collectors.groupingByConcurrent(i -> {
			try {
				return  islet.get(i);
			} catch(Exception e) { return -1; } // cas des creation
		}));
		
		newstate = new_orbits.entrySet().stream().map(entry -> {
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
		
		
		this.states = newstate.entrySet().stream().map(entry -> {
			int orbitID = entry.getKey();
			Set<Integer> ancetres = entry.getValue();
			
			int state = 0;
			switch(ancetres.size()) {
			case 0: state = state | DynOrTrackOrbitInst.ENCODED_CREATED ; break;
			case 1: break; //state = DynOrTrackState.NOMODIF; break; // pas de nomodif pour l'instant
			default: state = state | DynOrTrackOrbitInst.ENCODED_MERGE ; break;
			}
			Optional<Integer> astate = ancetres.stream().map(oid -> {
				List<Integer> futur = old_orbits.get(oid);
				int locstate = 0;
				switch(futur.size()) {
				case 0: locstate = DynOrTrackOrbitInst.ENCODED_DELETED; break;
				case 1: break; //state = DynOrTrackState.NOMODIF; break;
				default: locstate =DynOrTrackOrbitInst.ENCODED_SPLIT; break;
				}
				return locstate;
			}).reduce((a,b) -> (a|b));
			
			state = state | astate.orElse(0);
			
			return new DynOrTrackOrbitInst(orbitID, state);
		}).collect(Collectors.toList());
		
		// maintenant on fait la diffusion
		sw.display("end analysis of orbit "+orbit);
	}
	
	
	public void fetchStatic(JerboaGMap gmap, JerboaDynaOrTrackingItem prev, int oi, JerboaRuleResult res) {
		isStatic = true;
		StopWatch sw = new StopWatch();
		sw.display("== start tracking: " + rule.getFullname() + " - " + orbit);
		islet = JerboaIslet.islet_par(gmap, orbit);
		sw.display("==== islet done");
		IntStream.range(0, islet.size()).parallel().forEach(i -> {
			if(i == islet.get(i) && !gmap.existNode(i)) {
				islet.set(i, -1);
			}
		});
		sw.display("==== fix convention");
		
		sw.start();
		sw.display("Start static analysis evolution of orbit...");
		
		Map<String, Integer> states = rule.getTrackOrbitFacts(oi);
		
		this.states = states.entrySet().stream().flatMap(entry -> {
			String rulenodename = entry.getKey();
			// int nodecol = rule.getRightIndexRuleNode(rulenodename);
			int state = entry.getValue();
			List<JerboaDart> darts = res.get(rulenodename);
			
			Set<Integer> orbits = darts.stream().mapToInt(d -> islet.get(d.getID())).boxed().collect(Collectors.toSet());
			
			return orbits.stream().map(orbitID -> new DynOrTrackOrbitInst(orbitID, state));
		}).collect(Collectors.toList());
		sw.display("end static analysis of orbit "+orbit);
	}
	
	@Override
	public String toString() {
		return getName() + orbit + (isStatic? " <static>" : "");
	}
	
	public List<Integer> getIslet() {
		return islet;
	}
	
	public String getName() { return rule != null? rule.getFullname() : "(init)"; }
	public JerboaOrbit getOrbit() { return orbit; }
	
	
	public List<DynOrTrackOrbitInst> getStates() { return states; }
	
}
