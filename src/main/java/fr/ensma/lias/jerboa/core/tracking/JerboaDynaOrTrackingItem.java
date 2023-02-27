package fr.ensma.lias.jerboa.core.tracking;

import java.util.List;
import java.util.stream.IntStream;

import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaIslet;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.util.StopWatch;

public class JerboaDynaOrTrackingItem {
	
	private JerboaOrbit orbit;
	private String name;
	private List<Integer> islet;
	
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
	
	@Override
	public String toString() {
		return name + orbit;
	}
	
	public List<Integer> getIslet() {
		return islet;
	}
	
	public String getName() { return name; }
	public JerboaOrbit getOrbit() { return orbit; }
	
}
