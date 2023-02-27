package fr.ensma.lias.jerboa.tracking.rule.rules.Split;


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



public class SplitFaceDiag extends JerboaRuleGenerated {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public SplitFaceDiag(RawDynaOrTrackModeler modeler) throws JerboaException {

        super(modeler, "SplitFaceDiag", "Split");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        left.add(ln4);
        left.add(ln5);
        left.add(ln6);
        left.add(ln7);
        hooks.add(ln0);
        ln1.setAlpha(0, ln2);
        ln2.setAlpha(1, ln3);
        ln3.setAlpha(0, ln4);
        ln4.setAlpha(1, ln5);
        ln5.setAlpha(0, ln6);
        ln6.setAlpha(1, ln7);
        ln7.setAlpha(0, ln0);
        ln0.setAlpha(1, ln1);
        ln1.setAlpha(2, ln1);
        ln1.setAlpha(3, ln1);
        ln2.setAlpha(2, ln2);
        ln2.setAlpha(3, ln2);
        ln3.setAlpha(2, ln3);
        ln3.setAlpha(3, ln3);
        ln4.setAlpha(2, ln4);
        ln4.setAlpha(3, ln4);
        ln5.setAlpha(2, ln5);
        ln5.setAlpha(3, ln5);
        ln6.setAlpha(2, ln6);
        ln6.setAlpha(3, ln6);
        ln7.setAlpha(2, ln7);
        ln7.setAlpha(3, ln7);
        ln0.setAlpha(2, ln0);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn8 = new JerboaRuleNode("n8", 8, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn9 = new JerboaRuleNode("n9", 9, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn10 = new JerboaRuleNode("n10", 10, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn11 = new JerboaRuleNode("n11", 11, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        right.add(rn4);
        right.add(rn5);
        right.add(rn6);
        right.add(rn7);
        right.add(rn8);
        right.add(rn9);
        right.add(rn10);
        right.add(rn11);
        rn0.setAlpha(0, rn7);
        rn7.setAlpha(1, rn6);
        rn6.setAlpha(0, rn5);
        rn1.setAlpha(0, rn2);
        rn2.setAlpha(1, rn3);
        rn3.setAlpha(0, rn4);
        rn8.setAlpha(1, rn0);
        rn8.setAlpha(0, rn10);
        rn10.setAlpha(1, rn5);
        rn9.setAlpha(1, rn1);
        rn9.setAlpha(0, rn11);
        rn11.setAlpha(1, rn4);
        rn10.setAlpha(2, rn11);
        rn9.setAlpha(2, rn8);
        rn0.setAlpha(2, rn0);
        rn8.setAlpha(3, rn8);
        rn10.setAlpha(3, rn10);
        rn11.setAlpha(3, rn11);
        rn9.setAlpha(3, rn9);
        rn1.setAlpha(2, rn1);
        rn1.setAlpha(3, rn1);
        rn2.setAlpha(2, rn2);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(2, rn3);
        rn3.setAlpha(3, rn3);
        rn4.setAlpha(2, rn4);
        rn4.setAlpha(3, rn4);
        rn5.setAlpha(2, rn5);
        rn5.setAlpha(3, rn5);
        rn6.setAlpha(2, rn6);
        rn6.setAlpha(3, rn6);
        rn7.setAlpha(2, rn7);
        rn7.setAlpha(3, rn7);
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
        case 4: return 4;
        case 5: return 5;
        case 6: return 6;
        case 7: return 7;
        case 8: return -1;
        case 9: return -1;
        case 10: return -1;
        case 11: return -1;
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
        case 8: return 0;
        case 9: return 0;
        case 10: return 0;
        case 11: return 0;
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

    private JerboaDart n2() {
        return curleftPattern.getNode(2);
    }

    private JerboaDart n3() {
        return curleftPattern.getNode(3);
    }

    private JerboaDart n4() {
        return curleftPattern.getNode(4);
    }

    private JerboaDart n5() {
        return curleftPattern.getNode(5);
    }

    private JerboaDart n6() {
        return curleftPattern.getNode(6);
    }

    private JerboaDart n7() {
        return curleftPattern.getNode(7);
    }

} // end rule Class