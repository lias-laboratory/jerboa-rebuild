package fr.ensma.lias.jerboa.core.tracking;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.ensma.lias.jerboa.tracking.rule.rules.RawDynaOrTrackModeler;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;
import up.jerboa.util.StopWatch;

public class JerboaModelerDynOrTrack extends JerboaModelerGeneric {

	private List<List<JerboaDynaOrTrackingItem>> trackedItems;
	private List<JerboaOrbit> trackedOrbits;
	private RawDynaOrTrackModeler delegate;
	

	public JerboaModelerDynOrTrack(RawDynaOrTrackModeler modorigin, JerboaOrbit ...orbits) throws JerboaException {
		super(modorigin.getDimension());
		StopWatch sw = new StopWatch();
		this.delegate = modorigin;
		
		trackedItems = new ArrayList<>();
		trackedOrbits = new ArrayList<JerboaOrbit>();
		for (JerboaOrbit orbit : orbits) {
			trackedOrbits.add(orbit);
		}
		sw.display("= prepration of tracked orbit");
		
		registerEbdsAndResetGMAP(modorigin.getAllEmbedding().toArray(new JerboaEmbeddingInfo[0]));
		
		
		for (JerboaRuleOperation op : modorigin.getRules()) {
			sw.display("= add rule: " + op);
			if(op instanceof JerboaRuleGenerated) {
				JerboaRuleDynOrTrack rule = new JerboaRuleDynOrTrack(this, (JerboaRuleGenerated)op);
				registerRule(rule);
				sw.display("== as new rule with tracking operation");
			}
			else {
				registerRule(op);
				sw.display("== as normal old rule");
			}
			
		}
	}

	@Override
	public void registerEbdsAndResetGMAP(JerboaEmbeddingInfo... ebdsold) throws JerboaException {
		super.registerEbdsAndResetGMAP(ebdsold);
	}
	
	public final List<JerboaDynaOrTrackingItem> getTrackedOrbit(int step) {
		return trackedItems.get(step);
	}

	public int push(JerboaRuleDynOrTrack rule, JerboaGMap gmap) {
		List<JerboaDynaOrTrackingItem> items = new ArrayList<JerboaDynaOrTrackingItem>();
		StopWatch sw = new StopWatch();
		sw.display("= begin register tracking orbits done");
		for (JerboaOrbit orbit : trackedOrbits) {
			JerboaDynaOrTrackingItem item = new JerboaDynaOrTrackingItem(rule.getFullname(), orbit );
			sw.display(" == track orbit "+orbit);
			item.fetch(gmap);
			items.add(item);
			
		}
		trackedItems.add(items);
		sw.display("= end register tracking orbits done");
		return items.size();
	}
	
	public void exportTracking(String filename, int i) {
		try {
			
			PrintStream ps = new PrintStream(filename);
			int l = 0;
			for (List<JerboaDynaOrTrackingItem> items : trackedItems) {
				{
					JerboaDynaOrTrackingItem item = items.get(i); 
					ps.println((l++)+","+item.getName()+","+item.getOrbit());
					List<Integer> islet = item.getIslet();
					String line = islet.stream().map(v -> v.toString()).collect(Collectors.joining(","));
					ps.println(line);
				}
			}
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public JerboaEmbeddingInfo getColor() {
		return delegate.getColor();
	}
	
	public JerboaEmbeddingInfo getPos() {
		return delegate.getPos();
	}
}
