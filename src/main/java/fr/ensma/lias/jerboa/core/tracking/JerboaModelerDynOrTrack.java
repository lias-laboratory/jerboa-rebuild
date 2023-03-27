package fr.ensma.lias.jerboa.core.tracking;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import up.jerboa.core.JerboaEmbeddingInfo;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleOperation;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.util.JerboaModelerGeneric;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;
import up.jerboa.util.StopWatch;

public class JerboaModelerDynOrTrack extends JerboaModelerGeneric {

	private List<List<JerboaDynaOrTrackingItem>> trackedItems;
	private List<JerboaOrbit> trackedOrbits;
	private ModelerGenerated delegate;
	private boolean forceDynamic = true;
	

	public JerboaModelerDynOrTrack(ModelerGenerated modorigin, JerboaOrbit ...orbits) throws JerboaException {
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
		
		{
			List<JerboaDynaOrTrackingItem> items = new ArrayList<JerboaDynaOrTrackingItem>();
			for (JerboaOrbit orbit : trackedOrbits) {
				JerboaDynaOrTrackingItem init = new JerboaDynaOrTrackingItem(null, orbit);
				init.fetch(gmap);
				items.add(init);
			}
			trackedItems.add(items);
		}
	}
	
	public List<JerboaOrbit> getTrackedOrbit() {
		return trackedOrbits;
	}

	@Override
	public void registerEbdsAndResetGMAP(JerboaEmbeddingInfo... ebdsold) throws JerboaException {
		super.registerEbdsAndResetGMAP(ebdsold);
	}
	
	public final List<JerboaDynaOrTrackingItem> getTrackedOrbit(int step) {
		return trackedItems.get(step);
	}

	public int push(JerboaRuleDynOrTrack rule, JerboaGMap gmap, JerboaRuleResult res) {
		List<JerboaDynaOrTrackingItem> items = new ArrayList<JerboaDynaOrTrackingItem>();
		StopWatch sw = new StopWatch();
		sw.display("= begin register tracking orbits done");
		for (int oi = 0;oi < trackedOrbits.size(); ++oi) {
			JerboaOrbit orbit = trackedOrbits.get(oi);
			JerboaDynaOrTrackingItem item = new JerboaDynaOrTrackingItem(rule, orbit );
			sw.display(" == track orbit "+orbit);
			if(trackedItems.size() == 0)
				item.fetch(gmap);
			else if(rule.hasStaticDataForTrackedOrbit(oi) && !forceDynamic)
				item.fetchStatic(gmap, trackedItems.get(trackedItems.size()-1).get(oi), oi, res);
			else
				item.fetch(gmap, trackedItems.get(trackedItems.size()-1).get(oi));
			items.add(item);
		}
		trackedItems.add(items);
		sw.display("= end register tracking orbits done");
		
		for (int oi = 0;oi < trackedOrbits.size(); ++oi) {
			// displayHistoric(System.out,oi);
		}
		return items.size();
	}
	
	/*public int push(JerboaRuleDynOrTrack rule, JerboaGMap gmap) {
		List<JerboaDynaOrTrackingItem> items = new ArrayList<JerboaDynaOrTrackingItem>();
		StopWatch sw = new StopWatch();
		sw.display("= begin register tracking orbits done");
		for (int oi = 0;oi < trackedOrbits.size(); ++oi) {
			JerboaOrbit orbit = trackedOrbits.get(oi);
			JerboaDynaOrTrackingItem item = new JerboaDynaOrTrackingItem(rule.getFullname(), orbit );
			sw.display(" == track orbit "+orbit);
			item.fetch(gmap);
			items.add(item);
			
		}
		trackedItems.add(items);
		sw.display("= end register tracking orbits done");
		return items.size();
	}*/
	
	private void displayHistoric(PrintStream out, int oi) {
		for (List<JerboaDynaOrTrackingItem> list : trackedItems) {
			JerboaDynaOrTrackingItem cur = list.get(oi);
			out.println("OPERATION: " + cur);
			cur.getStates().stream().forEach(out::println);
			
		}
	}

	public void exportTracking(String filename, int i) {
		final String sep = ";";
		try {
			
			int max = trackedItems.parallelStream().mapToInt(items -> items.get(i).getIslet().size()).max().orElse(0);
			
			PrintStream ps = new PrintStream(filename);
			int l = 0;
			for(int j = 0; j < max; ++j) {
				ps.print(j);
				if(j != max-1)
					ps.print(sep);
			}
			ps.println();
			
			List<JerboaDynaOrTrackingItem> previtems = null;
			for (List<JerboaDynaOrTrackingItem> items : trackedItems) {
				{
					List<JerboaDynaOrTrackingItem> fprevitems = previtems;
					JerboaDynaOrTrackingItem item = items.get(i); 
					ps.println((l++)+sep+item.getName()+sep+item.getOrbit());
					List<Integer> islet = item.getIslet();
					String line = islet.stream().map(v -> v.toString()).collect(Collectors.joining(sep));
					ps.println(line);
					if(previtems != null) {
						line = IntStream.range(0, islet.size()).mapToObj(col -> {
							int old_state = -1;
							int new_state = -1; 
							try {
								new_state = items.get(i).getIslet().get(col);
							} catch(Exception e) { }
							
							try {
								old_state = fprevitems.get(i).getIslet().get(col);
							} catch(Exception e) { }
							
							if(old_state == new_state) {
								return DynOrTrackState.NOMODIF; 
							}
							else if(old_state == -1 && new_state != -1) {
								return DynOrTrackState.CREATED;
							}
							else if(old_state != -1 && new_state == -1) {
								return DynOrTrackState.DELETED;
							}
							else if(old_state != -1 && new_state != -1 && old_state != new_state) {
								return DynOrTrackState.MODIF;
							}
							return DynOrTrackState.NOMODIF;
						}).map(state -> state.toString()).collect(Collectors.joining(sep));
						ps.println(line);
					}
					previtems = items;
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
	
	public boolean isForceDynamic() {
		return forceDynamic;
	}
	
	public void setForceDynamic(boolean b) {
		forceDynamic = b;
	}
	
	public ModelerGenerated getModeler() { return delegate; }
}
