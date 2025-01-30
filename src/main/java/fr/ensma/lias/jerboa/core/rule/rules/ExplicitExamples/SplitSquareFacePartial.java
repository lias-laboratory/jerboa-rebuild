package fr.ensma.lias.jerboa.core.rule.rules.ExplicitExamples;


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



public class SplitSquareFacePartial extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public SplitSquareFacePartial(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "SplitSquareFacePartial", "ExplicitExamples");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        left.add(ln4);
        left.add(ln5);
        hooks.add(ln0);
        ln0.setAlpha(0, ln1);
        ln1.setAlpha(1, ln2);
        ln0.setAlpha(1, ln3);
        ln3.setAlpha(0, ln4);
        ln4.setAlpha(1, ln5);
        ln1.setAlpha(3, ln1);
        ln4.setAlpha(3, ln4);
        ln5.setAlpha(3, ln5);
        ln2.setAlpha(3, ln2);

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
        rn0.setAlpha(0, rn1);
        rn3.setAlpha(0, rn4);
        rn0.setAlpha(1, rn3);
        rn6.setAlpha(0, rn8);
        rn7.setAlpha(0, rn9);
        rn4.setAlpha(1, rn8);
        rn5.setAlpha(1, rn9);
        rn2.setAlpha(1, rn7);
        rn1.setAlpha(1, rn6);
        rn6.setAlpha(2, rn7);
        rn8.setAlpha(2, rn9);
        rn6.setAlpha(3, rn6);
        rn7.setAlpha(3, rn7);
        rn9.setAlpha(3, rn9);
        rn8.setAlpha(3, rn8);
        rn1.setAlpha(3, rn1);
        rn4.setAlpha(3, rn4);
        rn5.setAlpha(3, rn5);
        rn2.setAlpha(3, rn2);
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
        case 6: return -1;
        case 7: return -1;
        case 8: return -1;
        case 9: return -1;
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

} // end rule Class