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



public class MergeEdge extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public MergeEdge(RawDynaOrTrackModeler modeler) throws JerboaException {

        super(modeler, "MergeEdge", "Merge");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 1, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 2, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(2,3), 3);
        left.add(ln0);
        left.add(ln2);
        left.add(ln1);
        left.add(ln3);
        hooks.add(ln0);
        ln0.setAlpha(0, ln2);
        ln1.setAlpha(1, ln0);
        ln3.setAlpha(0, ln1);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 0, JerboaOrbit.orbit(2,3), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 1, JerboaOrbit.orbit(2,3), 3);
        right.add(rn2);
        right.add(rn3);
        rn3.setAlpha(0, rn2);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 1;
        case 1: return 3;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
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

    private JerboaDart n2() {
        return curleftPattern.getNode(1);
    }

    private JerboaDart n1() {
        return curleftPattern.getNode(2);
    }

    private JerboaDart n3() {
        return curleftPattern.getNode(3);
    }

} // end rule Class