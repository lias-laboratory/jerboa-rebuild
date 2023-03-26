package fr.ensma.lias.jerboa.core.tracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaOrbit;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.rule.JerboaRuleNode;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;
import up.jerboa.util.StopWatch;

public class JerboaRuleDynOrTrack extends JerboaRuleGenerated {
	private JerboaModelerDynOrTrack modtrack;
	private JerboaRuleGenerated delegate;
	private JerboaStaticDetection detector;
	private List<Map<String,Integer>> actionTrack; // Map <nodename, STATE> for an indexed trackedOrbit
    
    public JerboaRuleDynOrTrack(JerboaModelerDynOrTrack modeler, JerboaRuleGenerated rule)
            throws JerboaException {
    	super(modeler, rule.getName(), rule.getCategory());
    	this.modtrack = modeler;
        this.delegate = rule;
        this.detector = new JerboaStaticDetection(rule);
        
        List<JerboaOrbit> trackedOrbits = modtrack.getTrackedOrbit();
        actionTrack = trackedOrbits.stream().map(orbit -> prepareStaticDetection(orbit)).collect(Collectors.toList());
        
    }

    private Map<String, Integer> prepareStaticDetection(JerboaOrbit trackOrbit) {

    	Map<String, Integer> res = new HashMap<>();
    	List<JerboaRuleNode> rightnodes = delegate.getRightGraph();

    	/*Set<JerboaRuleNode> ens = rightnodes.stream().map(rnode -> {
    		List<JerboaRuleNode> orb = JerboaRuleNode.orbit(rnode, trackOrbit);
    		List<Integer> names = orb.stream().map(n -> n.getID()).collect(Collectors.toList());
    		names.sort(Integer::compare);
    		return names.get(0);
    	}).map(nodeid -> delegate.getRightRuleNode(nodeid)).collect(Collectors.toSet());*/

    	for (JerboaRuleNode rnode : rightnodes) {
    		int state = 0;
    		
    		List<JerboaRuleNode> orb = JerboaRuleNode.orbit(rnode, trackOrbit);
    		List<Integer> names = orb.stream().map(n -> n.getID()).collect(Collectors.toList());
    		names.sort(Integer::compare);
    		String nodename = delegate.getRightRuleNode(names.get(0)).getName();
    		if(res.containsKey(nodename))
    			state = res.get(nodename);
    		
    		
    		if(detector.isNodeCreated(rnode)) {
    			state |= DynOrTrackOrbitInst.ENCODED_CREATED;
    		}

    		if(detector.isNodeDeleted(rnode)) {
    			state |= DynOrTrackOrbitInst.ENCODED_DELETED;
    		}

    		if(detector.splittedOrbit(rnode, trackOrbit)) {
    			state |= DynOrTrackOrbitInst.ENCODED_SPLIT;
    		}

    		if(detector.mergedOrbit(rnode, trackOrbit)) {
    			state |= DynOrTrackOrbitInst.ENCODED_MERGE;
    		}

    		if(detector.modifiedOrbit(rnode, trackOrbit)) {
    			state |= DynOrTrackOrbitInst.ENCODED_MODIFIED;
    		}
			//res.put(rnode.getName(), state);
			res.put(nodename, state);
			System.out.println("Rule: "+ delegate.getFullname()+" TrackedOrbit: "+trackOrbit+" rightnode: "+ nodename+" (from "+ rnode.getName()+") state: "+state);
    	}
    	
    	
    	return res;
    }

	/**
     * Override of the applyRule method from JerboaRuleGenerated. Retrieve hooks PIs and add an
     * entry in spec before trying to apply the current rule.
     */
    @Override
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaInputHooks hooks)
            throws JerboaException {
        JerboaRuleResult res;
        StopWatch sw = new StopWatch();
        sw.display("=== Start delegate rule to track just after.");
        res = delegate.applyRule(gmap, hooks);
        sw.display("=== End delegate rule to track just after.");
        modtrack.push(this,gmap,res);
        sw.display("=== tracking finished");
        return res;
    }
    
    @Deprecated
    @Override
	public JerboaRuleResult applyRule(JerboaGMap gmap, List<JerboaDart> hooks) throws JerboaException {
    	JerboaRuleResult res;
    	System.err.println("pas de BOLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        res = delegate.applyRule(gmap, hooks);
        modtrack.push(this,gmap,res);
        
        return res;
	}
	@Override
	public int reverseAssoc(int i) {
		return delegate.reverseAssoc(i);
	}

	@Override
	public int attachedNode(int i) {
		return delegate.attachedNode(i);
	}
	
	public boolean hasStaticDataForTrackedOrbit(int indexTrackedOrbit) {
		return actionTrack.get(indexTrackedOrbit).size() > 0;
	}

	public Map<String, Integer> getTrackOrbitFacts(int indexTrackedOrbit) {
		return actionTrack.get(indexTrackedOrbit);
	}

}

