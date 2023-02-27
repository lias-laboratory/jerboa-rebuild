package fr.ensma.lias.jerboa.core.tracking;

import java.util.List;

import up.jerboa.core.JerboaDart;
import up.jerboa.core.JerboaGMap;
import up.jerboa.core.JerboaInputHooks;
import up.jerboa.core.JerboaRuleResult;
import up.jerboa.core.util.JerboaRuleGenerated;
import up.jerboa.exception.JerboaException;
import up.jerboa.util.StopWatch;

public class JerboaRuleDynOrTrack extends JerboaRuleGenerated {

	private JerboaModelerDynOrTrack modtrack;
	private JerboaRuleGenerated delegate;
    
    public JerboaRuleDynOrTrack(JerboaModelerDynOrTrack modeler, JerboaRuleGenerated rule)
            throws JerboaException {
    	super(modeler, rule.getName(), rule.getCategory());
    	this.modtrack = modeler;
        this.delegate = rule;
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
        modtrack.push(this,gmap);
        sw.display("=== tracking finished");
        return res;
    }
    
    @Deprecated
    @Override
	public JerboaRuleResult applyRule(JerboaGMap gmap, List<JerboaDart> hooks) throws JerboaException {
    	JerboaRuleResult res;
    	System.err.println("pas de BOLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        res = delegate.applyRule(gmap, hooks);
        modtrack.push(this,gmap);
        
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
	
	

}

