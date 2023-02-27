package fr.ensma.lias.jerboa.tracking.rule.rules.Merge;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.tracking.rule.rules.RawDynaOrTrackModeler;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;



/**
 * 
 */



public class MergeEdge2 extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public MergeEdge2(RawDynaOrTrackModeler modeler) throws JerboaException {

        super(modeler, "MergeEdge2", "Merge");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(1,2,3), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(-1,2,3), 3);
        left.add(ln0);
        left.add(ln1);
        hooks.add(ln0);
        ln0.setAlpha(0, ln1);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 0, JerboaOrbit.orbit(0,2,3), 3);
        right.add(rn1);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        return applyRule(gmap, ____jme_hooks);
	}

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

    private JerboaDart n1() {
        return curleftPattern.getNode(1);
    }

} // end rule Class