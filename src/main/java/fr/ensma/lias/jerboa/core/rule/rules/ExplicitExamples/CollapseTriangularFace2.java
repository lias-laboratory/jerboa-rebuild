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



public class CollapseTriangularFace2 extends JerboaRebuiltRule {

    private transient JerboaRowPattern curleftPattern;


	// BEGIN PARAMETERS Transformed 


	// END PARAMETERS 



    public CollapseTriangularFace2(ModelerGenerated modeler) throws JerboaException {

        super(modeler, "CollapseTriangularFace2", "ExplicitExamples");

        // -------- LEFT GRAPH
        JerboaRuleNode ln0 = new JerboaRuleNode("n0", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln1 = new JerboaRuleNode("n1", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln2 = new JerboaRuleNode("n2", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln3 = new JerboaRuleNode("n3", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln4 = new JerboaRuleNode("n4", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln5 = new JerboaRuleNode("n5", 5, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln6 = new JerboaRuleNode("n6", 6, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln7 = new JerboaRuleNode("n7", 7, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln8 = new JerboaRuleNode("n8", 8, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln9 = new JerboaRuleNode("n9", 9, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln10 = new JerboaRuleNode("n10", 10, JerboaOrbit.orbit(), 3);
        JerboaRuleNode ln11 = new JerboaRuleNode("n11", 11, JerboaOrbit.orbit(), 3);
        left.add(ln0);
        left.add(ln1);
        left.add(ln2);
        left.add(ln3);
        left.add(ln4);
        left.add(ln5);
        left.add(ln6);
        left.add(ln7);
        left.add(ln8);
        left.add(ln9);
        left.add(ln10);
        left.add(ln11);
        hooks.add(ln0);
        ln1.setAlpha(0, ln2);
        ln2.setAlpha(1, ln3);
        ln3.setAlpha(0, ln4);
        ln4.setAlpha(1, ln5);
        ln5.setAlpha(0, ln0);
        ln0.setAlpha(1, ln1);
        ln1.setAlpha(2, ln7);
        ln2.setAlpha(2, ln8);
        ln3.setAlpha(2, ln9);
        ln4.setAlpha(2, ln10);
        ln5.setAlpha(2, ln11);
        ln0.setAlpha(2, ln6);
        ln7.setAlpha(0, ln8);
        ln9.setAlpha(0, ln10);
        ln11.setAlpha(0, ln6);
        ln0.setAlpha(3, ln0);
        ln1.setAlpha(3, ln1);
        ln2.setAlpha(3, ln2);
        ln3.setAlpha(3, ln3);
        ln4.setAlpha(3, ln4);
        ln5.setAlpha(3, ln5);
        ln11.setAlpha(3, ln11);
        ln6.setAlpha(3, ln6);
        ln7.setAlpha(3, ln7);
        ln8.setAlpha(3, ln8);
        ln9.setAlpha(3, ln9);
        ln10.setAlpha(3, ln10);
        ln6.setAlpha(1, ln6);
        ln7.setAlpha(1, ln7);
        ln8.setAlpha(1, ln8);
        ln9.setAlpha(1, ln9);
        ln10.setAlpha(1, ln10);
        ln11.setAlpha(1, ln11);

        // -------- RIGHT GRAPH
        JerboaRuleNode rn6 = new JerboaRuleNode("n6", 0, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn7 = new JerboaRuleNode("n7", 1, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn8 = new JerboaRuleNode("n8", 2, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn9 = new JerboaRuleNode("n9", 3, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn10 = new JerboaRuleNode("n10", 4, JerboaOrbit.orbit(), 3);
        JerboaRuleNode rn11 = new JerboaRuleNode("n11", 5, JerboaOrbit.orbit(), 3);
        right.add(rn6);
        right.add(rn7);
        right.add(rn8);
        right.add(rn9);
        right.add(rn10);
        right.add(rn11);
        rn6.setAlpha(2, rn7);
        rn8.setAlpha(2, rn10);
        rn9.setAlpha(2, rn11);
        rn8.setAlpha(1, rn6);
        rn7.setAlpha(1, rn9);
        rn11.setAlpha(1, rn10);
        rn6.setAlpha(3, rn6);
        rn7.setAlpha(3, rn7);
        rn9.setAlpha(3, rn9);
        rn11.setAlpha(3, rn11);
        rn10.setAlpha(3, rn10);
        rn8.setAlpha(3, rn8);
        rn6.setAlpha(0, rn6);
        rn7.setAlpha(0, rn7);
        rn9.setAlpha(0, rn9);
        rn11.setAlpha(0, rn11);
        rn10.setAlpha(0, rn10);
        rn8.setAlpha(0, rn8);
;
        // ------- SPECIFIED FEATURE
        computeEfficientTopoStructure();
        computeSpreadOperation();
        // ------- USER DECLARATION 
    }

    public int reverseAssoc(int i) {
        switch(i) {
        case 0: return 6;
        case 1: return 7;
        case 2: return 8;
        case 3: return 9;
        case 4: return 10;
        case 5: return 11;
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

    private JerboaDart n8() {
        return curleftPattern.getNode(8);
    }

    private JerboaDart n9() {
        return curleftPattern.getNode(9);
    }

    private JerboaDart n10() {
        return curleftPattern.getNode(10);
    }

    private JerboaDart n11() {
        return curleftPattern.getNode(11);
    }

} // end rule Class