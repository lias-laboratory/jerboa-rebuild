package fr.ensma.lias.jerboa.core.rule.rules.Suppression;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;
import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;
import fr.ensma.lias.jerboa.embeddings.Vec3;



/**
 * 
 */



public class DeleteFace extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 

	protected List visites; 

	// END PARAMETERS 



    public DeleteFace(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "DeleteFace", "Suppression");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(0,1,3), 3);
        left.add(ln0);
        hooks.add(ln0);

        // -------- RIGHT GRAPH
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
        visites = new ArrayList<Integer>();;    }

    public int reverseAssoc(int i) {
        return -1;
    }

    public int attachedNode(int i) {
        return -1;
    }

    @Override
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaInputHooks _hooks) throws JerboaException {
        JerboaRuleResult res = super.applyRule(gmap, _hooks);
        postprocess(gmap,res);
        return res;
    }
    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, List visites) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        setVisites(visites);
        return applyRule(gmap, ____jme_hooks);
	}

    @Override
    public boolean hasPostprocess() { return true; }
    @Override
    public void postprocess(JerboaGMap gmap, JerboaRuleResult res) throws JerboaException {
	// BEGIN POSTPROCESS CODE
for(var label : visites){
   System.out.print("Half-face Delete Label: ");
   System.out.println(label);
}


	// END POSTPROCESS CODE
    }

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

	public List getVisites(){
		return visites;
	}
	public void setVisites(List _visites){
		this.visites = _visites;
	}
} // end rule Class