package fr.ensma.lias.jerboa.tracking.rule.rules.ExplicitExamples;


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



public class InsertEdgeReduced extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public InsertEdgeReduced(RawDynaOrTrackModeler modeler) throws JerboaException {

        super(modeler, "InsertEdgeReduced", "ExplicitExamples");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        hooks.add(ln0);
        hooks.add(ln2);
        ln0.setAlpha(1, ln1);
        ln0.setAlpha(3, ln0);
        ln1.setAlpha(3, ln1);
        ln2.setAlpha(1, ln3);
        ln2.setAlpha(3, ln2);
        ln3.setAlpha(3, ln3);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        rn4.setAlpha(0, rn6);
        rn5.setAlpha(0, rn7);
        rn4.setAlpha(1, rn0);
        rn6.setAlpha(1, rn2);
        rn7.setAlpha(1, rn3);
        rn5.setAlpha(1, rn1);
        rn4.setAlpha(2, rn5);
        rn6.setAlpha(2, rn7);
        rn4.setAlpha(3, rn4);
        rn5.setAlpha(3, rn5);
        rn6.setAlpha(3, rn6);
        rn7.setAlpha(3, rn7);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
        rn1.setAlpha(3, rn1);
        rn0.setAlpha(3, rn0);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 1;
        case 2: return 2;
        case 3: return 3;
        case 4: return -1;
        case 5: return -1;
        case 6: return -1;
        case 7: return -1;
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return 0;
        case 1: return 0;
        case 2: return 0;
        case 3: return 0;
        case 4: return 0;
        case 5: return 0;
        case 6: return 0;
        case 7: return 0;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n0, JerboaDart n2) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n0);
        ____jme_hooks.addCol(n2);
        return applyRule(gmap, ____jme_hooks);
	}

    // Facility for accessing to the dart
    private JerboaDart n0() {
        return curleftPattern.getNode(0);
    }

    private JerboaDart n1() {
        return curleftPattern.getNode(1);
    }

    private JerboaDart n2() {
        return curleftPattern.getNode(2);
    }

    private JerboaDart n3() {
        return curleftPattern.getNode(3);
    }

} // end rule Class