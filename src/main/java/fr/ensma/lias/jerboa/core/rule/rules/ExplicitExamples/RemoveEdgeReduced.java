package fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples;
import fr.ensma.lias.jerboa.core.rule.JerboaRebuiltRule;


import java.util.List;
import java.util.ArrayList;
import up.jerboa.core.rule.*;
import up.jerboa.core.util.*;
import up.jerboa.core.*;
import up.jerboa.exception.JerboaException;

import fr.ensma.lias.jerboa.core.rule.rules.ModelerGenerated;
import fr.ensma.lias.jerboa.embeddings.Vec3;
import java.awt.Color;
import fr.ensma.lias.jerboa.embeddings.Vec3;



/**
 * 
 */



public class RemoveEdgeReduced extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public RemoveEdgeReduced(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "RemoveEdgeReduced", "ExplicitExamples");

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
        hooks.add(ln4);
        ln4.setAlpha(0, ln6);
        ln5.setAlpha(0, ln7);
        ln4.setAlpha(1, ln0);
        ln6.setAlpha(1, ln2);
        ln7.setAlpha(1, ln3);
        ln5.setAlpha(1, ln1);
        ln4.setAlpha(2, ln5);
        ln6.setAlpha(2, ln7);
        ln4.setAlpha(3, ln4);
        ln5.setAlpha(3, ln5);
        ln6.setAlpha(3, ln6);
        ln7.setAlpha(3, ln7);
        ln2.setAlpha(3, ln2);
        ln3.setAlpha(3, ln3);
        ln1.setAlpha(3, ln1);
        ln0.setAlpha(3, ln0);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        right.add(rn0);
        right.add(rn1);
        right.add(rn2);
        right.add(rn3);
        rn2.setAlpha(3, rn2);
        rn3.setAlpha(3, rn3);
        rn1.setAlpha(3, rn1);
        rn0.setAlpha(3, rn0);
        rn0.setAlpha(1, rn1);
        rn2.setAlpha(1, rn3);
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
        }
        return -1;
    }

    public int attachedNode(int i) {
        switch(i) {
        case 0: return -1;
        case 1: return -1;
        case 2: return -1;
        case 3: return -1;
        }
        return -1;
    }

    public JerboaRuleResult applyRule(JerboaGMap gmap, JerboaDart n4) throws JerboaException {
        JerboaInputHooksGeneric ____jme_hooks = new JerboaInputHooksGeneric();
        ____jme_hooks.addCol(n4);
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